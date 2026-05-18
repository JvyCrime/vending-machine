package com.magtek.mobile.android.mtusdk.scra;

import android.util.Log;
import com.felhr.usbserial.UsbSerialDebugger;
import com.magtek.mobile.android.mtlib.MTSCRA;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class SCRAUartNfc {
    private static final int MAX_PACKET_SIZE = 4096;
    private static final byte SLIP_ESCAPE = -37;
    private static final byte SLIP_ESCAPE_C0 = -36;
    private static final byte SLIP_ESCAPE_DB = -35;
    private static final byte SLIP_PACKET_START_END = -64;
    private static final String TAG = "SCRAUartNfc";
    private SCRAUartNfcAdapter m_adapter;
    private MTSCRA m_scra;
    private boolean mSLIP = true;
    private boolean mPacketStarted = false;
    private boolean mLastByteIsPacketStart = false;
    private boolean mLastByteIsEscape = false;
    private int mPacketSize = 0;
    private byte[] mPacket = new byte[4096];
    private byte[] m_uartDataReceived = null;
    private int m_emvDataLen = 0;
    private byte[] m_emvData = null;

    public SCRAUartNfc(MTSCRA mtscra, SCRAUartNfcAdapter sCRAUartNfcAdapter) {
        this.m_scra = mtscra;
        this.m_adapter = sCRAUartNfcAdapter;
        if (1 != 0) {
            resetSLIPDecoder();
        }
    }

    private byte[] buildExtendedCommand(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr.length < 2 || bArr2 == null) {
            return null;
        }
        int length = bArr2.length;
        byte[] bArr3 = new byte[length + 4];
        bArr3[0] = bArr[0];
        bArr3[1] = bArr[1];
        bArr3[2] = (byte) ((length >> 8) & 255);
        bArr3[3] = (byte) (length & 255);
        System.arraycopy(bArr2, 0, bArr3, 4, bArr2.length);
        return bArr3;
    }

    protected void sendDebugInfo(String str) {
        SCRAUartNfcAdapter sCRAUartNfcAdapter = this.m_adapter;
        if (sCRAUartNfcAdapter != null) {
            sCRAUartNfcAdapter.OnUartDebugInfo(str);
        }
    }

    public int sendExtendedCommandBytes(byte[] bArr, byte[] bArr2) {
        if ((bArr != null ? bArr.length : 0) != 2) {
            return 9;
        }
        int length = bArr2 != null ? bArr2.length : 0;
        int i = 0;
        int iSendData = 9;
        while (true) {
            if (i >= length && length != 0) {
                break;
            }
            int i2 = length - i;
            if (i2 >= 22) {
                i2 = 21;
            }
            byte[] bArr3 = new byte[i2 + 8];
            Arrays.fill(bArr3, (byte) 0);
            bArr3[0] = 73;
            bArr3[1] = (byte) (i2 + 6);
            bArr3[2] = (byte) ((i >> 8) & 255);
            bArr3[3] = (byte) (i & 255);
            bArr3[4] = bArr[0];
            bArr3[5] = bArr[1];
            bArr3[6] = (byte) ((length >> 8) & 255);
            bArr3[7] = (byte) (length & 255);
            for (int i3 = 0; i3 < i2; i3++) {
                bArr3[i3 + 8] = bArr2[i + i3];
            }
            i += i2;
            iSendData = sendData(TLVParser.getHexString(bArr3));
            if (iSendData == 9) {
                return iSendData;
            }
            if (length == 0) {
                break;
            }
            try {
                Thread.sleep(200L);
            } catch (Exception unused) {
            }
        }
        return iSendData;
    }

    public int sendData(String str) {
        String str2 = TAG;
        Log.i(str2, str2 + " sendData: " + str);
        if (this.m_scra == null) {
            return 9;
        }
        byte[] bArrBuildASCIIData = null;
        byte[] byteArrayFromHexString = TLVParser.getByteArrayFromHexString(str);
        if (byteArrayFromHexString != null && byteArrayFromHexString.length > 0) {
            int length = byteArrayFromHexString.length;
            byte[] bArr = new byte[length + 3];
            bArr[0] = 5;
            bArr[1] = (byte) ((length >> 8) & 255);
            bArr[2] = (byte) (length & 255);
            System.arraycopy(byteArrayFromHexString, 0, bArr, 3, length);
            if (this.mSLIP) {
                bArrBuildASCIIData = buildSLIPData(bArr);
            } else {
                bArrBuildASCIIData = buildASCIIData(bArr);
            }
        }
        byte[] bArr2 = {0};
        if (bArrBuildASCIIData != null) {
            bArr2 = new byte[bArrBuildASCIIData.length + 1];
            bArr2[0] = 0;
            System.arraycopy(bArrBuildASCIIData, 0, bArr2, 1, bArrBuildASCIIData.length);
        }
        String hexString = TLVParser.getHexString(buildExtendedCommand(new byte[]{4, 0}, bArr2));
        sendDebugInfo("Send UART Extended Command: " + hexString);
        int iSendExtendedCommand = this.m_scra.sendExtendedCommand(hexString);
        if (iSendExtendedCommand == 0) {
            return iSendExtendedCommand;
        }
        sendDebugInfo("SendExtendedCommand error=" + iSendExtendedCommand);
        return iSendExtendedCommand;
    }

    private void resetSLIPDecoder() {
        this.mPacketStarted = false;
        this.mLastByteIsPacketStart = false;
        this.mLastByteIsEscape = false;
        this.mPacketSize = 0;
        this.mPacket = new byte[4096];
    }

    private void processSLIPPacket(byte[] bArr) {
        int length;
        if (bArr == null || (length = bArr.length - 3) <= 0) {
            return;
        }
        byte b = bArr[0];
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 3, bArr2, 0, length);
        if (b == 2) {
            processNotificationData(bArr2);
            return;
        }
        if (b != 4) {
            return;
        }
        String hexString = TLVParser.getHexString(bArr2);
        SCRAUartNfcAdapter sCRAUartNfcAdapter = this.m_adapter;
        if (sCRAUartNfcAdapter != null) {
            sCRAUartNfcAdapter.OnUartDeviceResponse(hexString);
        }
    }

    private void decodeSLIPByte(byte b) {
        if (b == -64) {
            if (!this.mPacketStarted || this.mLastByteIsPacketStart) {
                this.mPacketStarted = true;
            } else {
                this.mPacketStarted = false;
                int i = this.mPacketSize;
                if (i > 0) {
                    byte[] bArr = new byte[i];
                    System.arraycopy(this.mPacket, 0, bArr, 0, i);
                    processSLIPPacket(bArr);
                }
            }
            this.mPacketSize = 0;
            this.mLastByteIsPacketStart = true;
            return;
        }
        if (this.mPacketStarted) {
            if (this.mLastByteIsEscape) {
                if (b == -36) {
                    byte[] bArr2 = this.mPacket;
                    int i2 = this.mPacketSize;
                    bArr2[i2] = -64;
                    this.mPacketSize = i2 + 1;
                } else if (b == -35) {
                    byte[] bArr3 = this.mPacket;
                    int i3 = this.mPacketSize;
                    bArr3[i3] = SLIP_ESCAPE;
                    this.mPacketSize = i3 + 1;
                }
                this.mLastByteIsEscape = false;
            } else if (b == -37) {
                this.mLastByteIsEscape = true;
            } else {
                byte[] bArr4 = this.mPacket;
                int i4 = this.mPacketSize;
                bArr4[i4] = b;
                this.mPacketSize = i4 + 1;
                this.mLastByteIsEscape = false;
            }
        }
        this.mLastByteIsPacketStart = false;
    }

    private void processSLIPData(byte[] bArr) {
        if (bArr != null) {
            for (byte b : bArr) {
                decodeSLIPByte(b);
            }
        }
    }

    private byte[] buildSLIPData(byte[] bArr) {
        int i;
        byte[] bArr2 = new byte[1024];
        if (bArr != null) {
            int length = bArr.length;
            bArr2[0] = -64;
            int i2 = 1;
            for (int i3 = 0; i3 < length; i3++) {
                if (bArr[i3] == -64) {
                    int i4 = i2 + 1;
                    bArr2[i2] = SLIP_ESCAPE;
                    i2 = i4 + 1;
                    bArr2[i4] = SLIP_ESCAPE_C0;
                } else if (bArr[i3] == -37) {
                    int i5 = i2 + 1;
                    bArr2[i2] = SLIP_ESCAPE;
                    i2 = i5 + 1;
                    bArr2[i5] = SLIP_ESCAPE_DB;
                } else {
                    bArr2[i2] = bArr[i3];
                    i2++;
                }
            }
            i = i2 + 1;
            bArr2[i2] = -64;
        } else {
            i = 0;
        }
        if (i > 0) {
            return Arrays.copyOfRange(bArr2, 0, i);
        }
        return null;
    }

    private byte[] buildASCIIData(byte[] bArr) {
        byte[] bytes;
        if (bArr == null || bArr.length <= 0 || (bytes = TLVParser.getHexString(bArr).getBytes()) == null) {
            return null;
        }
        int length = bytes.length + 2;
        byte[] bArr2 = new byte[length];
        bArr2[0] = 0;
        System.arraycopy(bytes, 0, bArr2, 1, bytes.length);
        bArr2[length - 1] = 13;
        return bArr2;
    }

    public void processDeviceExtendedResponse(byte[] bArr) {
        int length;
        if (bArr == null || !isUARTDataNotification(bArr) || (length = bArr.length - 5) <= 0) {
            return;
        }
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 5, bArr2, 0, length);
        processUARTData(bArr2);
    }

    protected boolean isUARTDataNotification(byte[] bArr) {
        return bArr != null && bArr.length >= 3 && bArr[0] == 4 && bArr[1] == 0;
    }

    protected void processUARTData(byte[] bArr) {
        if (bArr != null) {
            if (this.mSLIP) {
                processSLIPData(bArr);
            } else {
                processASCIIData(bArr);
            }
        }
    }

    protected void processASCIIData(byte[] bArr) {
        int length;
        byte[] bArr2;
        String str;
        int length2;
        if (bArr == null || (length = bArr.length) <= 0) {
            return;
        }
        byte[] bArr3 = this.m_uartDataReceived;
        if (bArr3 != null) {
            int length3 = bArr3.length;
            bArr2 = new byte[length3 + length];
            System.arraycopy(bArr3, 0, bArr2, 0, length3);
            System.arraycopy(bArr, 0, bArr2, length3, length);
        } else {
            bArr2 = new byte[length];
            System.arraycopy(bArr, 0, bArr2, 0, length);
        }
        int length4 = bArr2.length;
        if (length4 > 0) {
            int i = 0;
            for (int i2 = 0; i2 < length4; i2++) {
                if (bArr2[i2] == 13) {
                    int i3 = i2 - i;
                    if (i3 > 0) {
                        byte[] bArr4 = new byte[i3];
                        System.arraycopy(bArr2, i, bArr4, 0, i3);
                        sendDebugInfo("UART Data=" + TLVParser.getHexString(bArr4));
                        try {
                            str = new String(bArr4, UsbSerialDebugger.ENCODING);
                        } catch (Exception unused) {
                            str = "";
                        }
                        sendDebugInfo("UART Data ASCII=" + str);
                        byte[] byteArrayFromHexString = TLVParser.getByteArrayFromHexString(str);
                        if (byteArrayFromHexString != null && (length2 = byteArrayFromHexString.length) > 1) {
                            byte b = byteArrayFromHexString[0];
                            int i4 = length2 - 1;
                            byte[] bArr5 = new byte[i4];
                            System.arraycopy(byteArrayFromHexString, 1, bArr5, 0, i4);
                            if (b == 2) {
                                processNotificationData(bArr5);
                            } else if (b == 4) {
                                String hexString = TLVParser.getHexString(bArr5);
                                SCRAUartNfcAdapter sCRAUartNfcAdapter = this.m_adapter;
                                if (sCRAUartNfcAdapter != null) {
                                    sCRAUartNfcAdapter.OnUartDeviceResponse(hexString);
                                }
                            }
                        }
                    }
                    i = i2 + 1;
                }
            }
            int i5 = length4 - i;
            if (i5 > 0) {
                byte[] bArr6 = new byte[i5];
                this.m_uartDataReceived = bArr6;
                System.arraycopy(bArr2, i, bArr6, 0, i5);
                return;
            }
            this.m_uartDataReceived = null;
        }
    }

    private void processNotificationData(byte[] bArr) {
        if (bArr == null || bArr.length < 8) {
            return;
        }
        int i = ((bArr[0] & 255) << 8) + (bArr[1] & 255);
        int i2 = ((bArr[2] & 255) << 8) + (bArr[3] & 255);
        byte[] bArr2 = {bArr[4], bArr[5]};
        int i3 = ((bArr[6] & 255) << 8) + (bArr[7] & 255);
        if (this.m_emvData == null) {
            this.m_emvDataLen = 0;
            byte[] bArr3 = new byte[i3 + 4];
            this.m_emvData = bArr3;
            Arrays.fill(bArr3, (byte) 0);
            byte[] bArr4 = this.m_emvData;
            bArr4[0] = bArr2[0];
            bArr4[1] = bArr2[1];
            bArr4[2] = bArr[6];
            bArr4[3] = bArr[7];
        }
        if (bArr.length >= i + 8) {
            System.arraycopy(bArr, 8, this.m_emvData, i2 + 4, i);
            this.m_emvDataLen += i;
        }
        if (this.m_emvDataLen >= i3) {
            processEMVData(this.m_emvData);
            this.m_emvDataLen = 0;
            this.m_emvData = null;
        }
    }

    protected void processEMVData(byte[] bArr) {
        SCRAUartNfcAdapter sCRAUartNfcAdapter;
        Log.i(TAG, "UART EMV Data=" + TLVParser.getHexString(bArr));
        if (bArr == null || bArr.length <= 4) {
            return;
        }
        byte[] bArr2 = null;
        int length = bArr.length - 4;
        if (length > 0) {
            bArr2 = new byte[length];
            System.arraycopy(bArr, 4, bArr2, 0, length);
        }
        if (bArr[0] == 3) {
            byte b = bArr[1];
            if (b == 0) {
                SCRAUartNfcAdapter sCRAUartNfcAdapter2 = this.m_adapter;
                if (sCRAUartNfcAdapter2 != null) {
                    sCRAUartNfcAdapter2.OnUartTransactionStatus(bArr2);
                    return;
                }
                return;
            }
            if (b == 1) {
                SCRAUartNfcAdapter sCRAUartNfcAdapter3 = this.m_adapter;
                if (sCRAUartNfcAdapter3 != null) {
                    sCRAUartNfcAdapter3.OnUartDisplayMessageRequest(bArr2);
                    return;
                }
                return;
            }
            if (b == 2) {
                SCRAUartNfcAdapter sCRAUartNfcAdapter4 = this.m_adapter;
                if (sCRAUartNfcAdapter4 != null) {
                    sCRAUartNfcAdapter4.OnUartUserSelectionRequest(bArr2);
                    return;
                }
                return;
            }
            if (b != 3) {
                if (b == 4 && (sCRAUartNfcAdapter = this.m_adapter) != null) {
                    sCRAUartNfcAdapter.OnUartTransactionResult(bArr2);
                    return;
                }
                return;
            }
            SCRAUartNfcAdapter sCRAUartNfcAdapter5 = this.m_adapter;
            if (sCRAUartNfcAdapter5 != null) {
                sCRAUartNfcAdapter5.OnUartARQCReceived(bArr2);
            }
        }
    }
}
