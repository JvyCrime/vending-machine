package com.magtek.mobile.android.mtusdk.scra;

import android.os.Handler;
import com.magtek.mobile.android.mtlib.MTASCCardData;
import com.magtek.mobile.android.mtlib.MTSCRA;
import com.magtek.mobile.android.mtusdk.common.TLVParser;

/* JADX INFO: loaded from: classes.dex */
public class SCRASpiMsr {
    private SCRASpiMsrAdapter m_adapter;
    private MTSCRA m_scra;
    private boolean m_spiDataRequestPending;
    private boolean m_spiStatusRequestPending;
    private byte[] m_spiDataReceived = null;
    private byte[] m_spiHeadData = null;
    private int m_spiHeadDataLength = 0;

    public SCRASpiMsr(MTSCRA mtscra, SCRASpiMsrAdapter sCRASpiMsrAdapter) {
        this.m_spiStatusRequestPending = false;
        this.m_spiDataRequestPending = false;
        this.m_scra = mtscra;
        this.m_adapter = sCRASpiMsrAdapter;
        this.m_spiStatusRequestPending = false;
        this.m_spiDataRequestPending = false;
        requestSPIStatus();
    }

    protected void sendDebugInfo(String str) {
        SCRASpiMsrAdapter sCRASpiMsrAdapter = this.m_adapter;
        if (sCRASpiMsrAdapter != null) {
            sCRASpiMsrAdapter.OnDebugInfo(str);
        }
    }

    public void processDeviceExtendedResponse(byte[] bArr) {
        if (bArr != null) {
            if (this.m_spiStatusRequestPending) {
                processSPIStatus(bArr);
            } else if (this.m_spiDataRequestPending) {
                processSPIData(bArr);
            } else if (isSPIDataNotification(bArr)) {
                requestSPIData(5);
            }
        }
    }

    protected boolean isSPIDataNotification(byte[] bArr) {
        if (bArr == null || bArr.length < 6 || bArr[0] != 5 || bArr[1] != 0 || (bArr[5] & 2) == 0) {
            return false;
        }
        sendDebugInfo("[SPI Data Notification Received]");
        return true;
    }

    protected void requestSPIStatus() {
        new Handler().postDelayed(new Runnable() { // from class: com.magtek.mobile.android.mtusdk.scra.SCRASpiMsr.1
            @Override // java.lang.Runnable
            public void run() {
                SCRASpiMsr.this.sendDebugInfo("[Request SPI Status]");
                SCRASpiMsr.this.m_spiStatusRequestPending = true;
                SCRASpiMsr.this.m_scra.sendExtendedCommand("0501000100");
            }
        }, 10L);
    }

    protected void requestSPIData(int i) {
        writeSPIData(i);
    }

    protected void writeSPIData(int i) {
        if (this.m_scra == null || i <= 0) {
            return;
        }
        String strReplace = String.format("%1$" + (i * 2) + "s", "").replace(' ', 'F');
        StringBuilder sb = new StringBuilder();
        sb.append("[Request SPI Data] Length=");
        sb.append(i);
        sendDebugInfo(sb.toString());
        this.m_spiDataRequestPending = true;
        int iSendData = sendData(strReplace);
        if (iSendData != 0) {
            sendDebugInfo("[Request SPI Data] *** Result=" + iSendData);
        }
    }

    public int sendData(String str) {
        if (this.m_scra == null || str == null || str.length() <= 0) {
            return 9;
        }
        return this.m_scra.sendExtendedCommand("0500" + getTwoBytesLengthString((str.length() / 2) + 1) + "00" + str);
    }

    protected String getTwoBytesLengthString(int i) {
        return TLVParser.getHexString(new byte[]{(byte) (((i >> 8) % 256) & 255), (byte) ((i % 256) & 255)});
    }

    protected void processSPIStatus(byte[] bArr) {
        boolean z = bArr != null && bArr.length >= 5 && bArr[4] == 5;
        this.m_spiStatusRequestPending = false;
        if (z) {
            requestSPIData(5);
        }
    }

    protected void processSPIData(byte[] bArr) {
        byte[] bArr2;
        this.m_spiDataRequestPending = false;
        if (bArr == null || bArr.length < 2 || bArr[0] != 0 || bArr[1] != 0 || bArr.length <= 4) {
            return;
        }
        int length = bArr.length - 4;
        byte[] bArr3 = this.m_spiDataReceived;
        if (bArr3 != null) {
            int length2 = bArr3.length;
            bArr2 = new byte[length2 + length];
            System.arraycopy(bArr3, 0, bArr2, 0, length2);
            System.arraycopy(bArr, 4, bArr2, length2, length);
        } else {
            bArr2 = new byte[length];
            System.arraycopy(bArr, 4, bArr2, 0, length);
        }
        this.m_spiDataReceived = bArr2;
        processSPIDataReceived();
    }

    protected void processSPIDataReceived() {
        sendDebugInfo("[processSPIDataReceived]");
        byte[] bArr = this.m_spiDataReceived;
        if (bArr == null) {
            return;
        }
        if (this.m_spiHeadDataLength > 0) {
            processSPIHeadData(bArr);
            this.m_spiDataReceived = null;
            return;
        }
        int i = 0;
        while (true) {
            byte[] bArr2 = this.m_spiDataReceived;
            if (i >= bArr2.length) {
                return;
            }
            if (bArr2[i] != 255) {
                if (bArr2[i] == 1) {
                    i++;
                    int i2 = i + 1;
                    if (i2 < bArr2.length) {
                        int i3 = i2 + 1;
                        this.m_spiHeadDataLength = ((bArr2[i] & 255) << 8) + (bArr2[i2] & 255);
                        int length = bArr2.length - i3;
                        if (length > 0) {
                            byte[] bArr3 = new byte[length];
                            System.arraycopy(bArr2, i3, bArr3, 0, length);
                            this.m_spiDataReceived = null;
                            processSPIHeadData(bArr3);
                            return;
                        }
                        this.m_spiDataReceived = null;
                        processSPIHeadData(null);
                        return;
                    }
                    requestSPIData(5);
                } else {
                    requestSPIData(5);
                }
            }
            i++;
        }
    }

    protected void processSPIHeadData(byte[] bArr) {
        if (bArr != null) {
            int length = bArr.length;
            byte[] bArr2 = this.m_spiHeadData;
            if (bArr2 != null) {
                int length2 = bArr2.length;
                byte[] bArr3 = new byte[length2 + length];
                System.arraycopy(bArr2, 0, bArr3, 0, length2);
                System.arraycopy(bArr, 0, bArr3, length2, length);
                this.m_spiHeadData = bArr3;
            } else {
                byte[] bArr4 = new byte[length];
                this.m_spiHeadData = bArr4;
                System.arraycopy(bArr, 0, bArr4, 0, length);
            }
        }
        int length3 = this.m_spiHeadDataLength;
        if (length3 > 0) {
            byte[] bArr5 = this.m_spiHeadData;
            if (bArr5 != null) {
                length3 -= bArr5.length;
            }
            if (length3 > 0) {
                requestSPIData(length3);
                return;
            }
            int length4 = bArr5.length;
            if (length4 > 1 && bArr5[0] == 2) {
                sendDebugInfo("Card Data: " + TLVParser.getHexString(bArr5));
                MTASCCardData mTASCCardData = new MTASCCardData();
                int i = length4 - 1;
                byte[] bArr6 = new byte[i];
                System.arraycopy(this.m_spiHeadData, 1, bArr6, 0, i);
                mTASCCardData.setData(bArr6);
                SCRASpiMsrAdapter sCRASpiMsrAdapter = this.m_adapter;
                if (sCRASpiMsrAdapter != null) {
                    sCRASpiMsrAdapter.OnCardDataReceived(mTASCCardData);
                }
            }
            this.m_spiHeadData = null;
            this.m_spiHeadDataLength = 0;
        }
    }
}
