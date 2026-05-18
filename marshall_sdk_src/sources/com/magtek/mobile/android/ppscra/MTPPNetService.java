package com.magtek.mobile.android.ppscra;

import android.util.Log;
import com.bitmick.marshall.vmc.vmc_link;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class MTPPNetService extends MTPPBaseService implements MTSocketAdapter {
    private static final String b = "MTPPNetService";
    private MTSocketAdapter i;
    private IMTSocket j;
    private int c = 26;
    private boolean d = false;
    private boolean e = false;
    private boolean f = false;
    private int g = 0;
    private byte[] h = new byte[1024];
    private boolean k = false;
    private boolean l = false;
    byte[] a = null;

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public void close() {
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public byte[] getDeviceSerialNumber() {
        return null;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public String getFirmwareVersion() {
        return null;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public String getProductID() {
        return null;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public String getProductName() {
        return null;
    }

    private void a(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        String str = b;
        Log.i(str, "Data=" + PPSCRACommon.getHexString(bArr));
        if (this.m_serviceAdapter != null) {
            this.m_serviceAdapter.OnDeviceData(bArr);
        } else {
            Log.i(str, "ServiceAdapter is NULL");
        }
    }

    private void a() {
        this.d = false;
        this.e = false;
        this.f = false;
        this.g = 0;
        this.h = new byte[1024];
    }

    private void a(byte b2) {
        if (b2 == -64) {
            if (!this.d || this.e) {
                this.d = true;
            } else {
                this.d = false;
                int i = this.g;
                if (i > 1) {
                    a(Arrays.copyOfRange(this.h, 1, i));
                }
            }
            this.g = 0;
            this.e = true;
            return;
        }
        if (this.d) {
            if (this.f) {
                if (b2 == -36) {
                    byte[] bArr = this.h;
                    int i2 = this.g;
                    bArr[i2] = PPSCRADeviceValues.EMV_TAG_TYPE_DRL_GROUP;
                    this.g = i2 + 1;
                } else if (b2 == -35) {
                    byte[] bArr2 = this.h;
                    int i3 = this.g;
                    bArr2[i3] = -37;
                    this.g = i3 + 1;
                }
                this.f = false;
            } else if (b2 == -37) {
                this.f = true;
            } else {
                byte[] bArr3 = this.h;
                int i4 = this.g;
                bArr3[i4] = b2;
                this.g = i4 + 1;
                this.f = false;
            }
        }
        this.e = false;
    }

    private void b(byte[] bArr) {
        if (bArr != null) {
            for (byte b2 : bArr) {
                a(b2);
            }
        }
    }

    private byte[] c(byte[] bArr) {
        int i;
        byte[] bArr2 = new byte[1024];
        if (bArr != null) {
            int length = bArr.length;
            bArr2[0] = PPSCRADeviceValues.EMV_TAG_TYPE_DRL_GROUP;
            int i2 = 1;
            for (int i3 = 0; i3 < length; i3++) {
                if (bArr[i3] == -64) {
                    int i4 = i2 + 1;
                    bArr2[i2] = -37;
                    i2 = i4 + 1;
                    bArr2[i4] = -36;
                } else if (bArr[i3] == -37) {
                    int i5 = i2 + 1;
                    bArr2[i2] = -37;
                    i2 = i5 + 1;
                    bArr2[i5] = -35;
                } else {
                    bArr2[i2] = bArr[i3];
                    i2++;
                }
            }
            i = i2 + 1;
            bArr2[i2] = PPSCRADeviceValues.EMV_TAG_TYPE_DRL_GROUP;
        } else {
            i = 0;
        }
        if (i > 0) {
            return Arrays.copyOfRange(bArr2, 0, i);
        }
        return null;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public void connect() {
        int iIndexOf;
        Log.i(b, "Connecting");
        a();
        this.c = 26;
        if (this.m_address != null && (iIndexOf = this.m_address.indexOf(":")) >= 0) {
            try {
                this.c = Integer.parseInt(this.m_address.substring(iIndexOf + 1));
            } catch (Exception unused) {
            }
        }
        this.i = this;
        try {
            new Thread() { // from class: com.magtek.mobile.android.ppscra.MTPPNetService.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    if (MTPPNetService.this.m_useTLS12) {
                        MTPPNetService.this.j = new MTTLSSocket(MTPPNetService.this.m_address, MTPPNetService.this.c, MTPPNetService.this.i);
                        MTPPNetService.this.j.setClientCertificate(MTPPNetService.this.m_clientCertificateFormat, MTPPNetService.this.m_clientCertificateData, MTPPNetService.this.m_clientCertificatePassword);
                        MTPPNetService.this.j.setTrustAll(MTPPNetService.this.m_trustAll);
                    } else {
                        MTPPNetService.this.j = new MTTCPSocket(MTPPNetService.this.m_address, MTPPNetService.this.c, MTPPNetService.this.i);
                    }
                    MTPPNetService.this.j.startClient(vmc_link.MARSHALL_NO_COMM_TIMEOUT);
                }
            }.start();
        } catch (Exception unused2) {
        }
    }

    @Override // com.magtek.mobile.android.ppscra.MTSocketAdapter
    public void OnListening() {
        setState(MTServiceState.Listening);
    }

    @Override // com.magtek.mobile.android.ppscra.MTSocketAdapter
    public void OnConnecting() {
        setState(MTServiceState.Connecting);
    }

    @Override // com.magtek.mobile.android.ppscra.MTSocketAdapter
    public void OnConnected() {
        setState(MTServiceState.Connected);
    }

    @Override // com.magtek.mobile.android.ppscra.MTSocketAdapter
    public void OnDisconnecting() {
        setState(MTServiceState.Disconnecting);
    }

    @Override // com.magtek.mobile.android.ppscra.MTSocketAdapter
    public void OnDisconnected() {
        setState(MTServiceState.Disconnected);
    }

    @Override // com.magtek.mobile.android.ppscra.MTSocketAdapter
    public void OnDataReceived(byte[] bArr) {
        b(bArr);
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public void disconnect() {
        if (this.l) {
            return;
        }
        Log.i(b, "Disconnecting");
        this.l = true;
        setState(MTServiceState.Disconnecting);
        IMTSocket iMTSocket = this.j;
        if (iMTSocket != null) {
            iMTSocket.stopClient();
        }
        this.l = false;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public void sendData(byte[] bArr) {
        String str = b;
        Log.i(str, "TCPService writeData data.length=" + bArr.length);
        Log.i(str, "TCPService writeData data=" + PPSCRACommon.getHexString(bArr));
        byte[] bArrC = c(bArr);
        Log.i(str, "TCPService SLIP data=" + PPSCRACommon.getHexString(bArrC));
        int length = bArrC.length;
        int i = 0;
        while (i < length) {
            int i2 = length - i;
            if (i2 > 255) {
                i2 = 255;
            }
            byte[] bArr2 = new byte[i2];
            this.a = bArr2;
            Arrays.fill(bArr2, (byte) 0);
            System.arraycopy(bArrC, i, this.a, 0, i2);
            try {
                Thread thread = new Thread() { // from class: com.magtek.mobile.android.ppscra.MTPPNetService.2
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        int length2;
                        if (MTPPNetService.this.a == null || (length2 = MTPPNetService.this.a.length) <= 0) {
                            return;
                        }
                        MTPPNetService.this.j.sendData(Arrays.copyOfRange(MTPPNetService.this.a, 0, length2));
                    }
                };
                thread.start();
                thread.join();
            } catch (Exception unused) {
            }
            i += i2;
        }
    }
}
