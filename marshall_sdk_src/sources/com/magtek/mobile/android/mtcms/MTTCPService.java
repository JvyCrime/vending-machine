package com.magtek.mobile.android.mtcms;

import android.content.Context;
import android.util.Log;
import java.util.Arrays;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class MTTCPService extends MTBaseService implements MTTCPClientAdapter {
    private static final int DEFAULT_CONNECT_TIMEOUT = 15000;
    private static final int IP_SERVICE_PORT = 5000;
    private static final String TAG = "MTTCPService";
    private IMTTCPClient mTCPClient;
    private MTTCPClientAdapter mThis;

    @Override // com.magtek.mobile.android.mtcms.MTBaseService
    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    @Override // com.magtek.mobile.android.mtcms.MTBaseService, com.magtek.mobile.android.mtcms.IMTService
    public /* bridge */ /* synthetic */ MTServiceState getState() {
        return super.getState();
    }

    @Override // com.magtek.mobile.android.mtcms.MTBaseService, java.lang.Runnable
    public /* bridge */ /* synthetic */ void run() {
        super.run();
    }

    @Override // com.magtek.mobile.android.mtcms.MTBaseService, com.magtek.mobile.android.mtcms.IMTService
    public /* bridge */ /* synthetic */ void setAddress(String str) {
        super.setAddress(str);
    }

    @Override // com.magtek.mobile.android.mtcms.MTBaseService, com.magtek.mobile.android.mtcms.IMTService
    public /* bridge */ /* synthetic */ void setDeviceID(String str) {
        super.setDeviceID(str);
    }

    @Override // com.magtek.mobile.android.mtcms.MTBaseService, com.magtek.mobile.android.mtcms.IMTService
    public /* bridge */ /* synthetic */ void setServiceUUID(UUID uuid) {
        super.setServiceUUID(uuid);
    }

    @Override // com.magtek.mobile.android.mtcms.MTBaseService, com.magtek.mobile.android.mtcms.IMTService
    public void initialize(Context context, MTServiceAdapter mTServiceAdapter) {
        this.m_context = context;
        this.m_serviceAdapter = mTServiceAdapter;
    }

    @Override // com.magtek.mobile.android.mtcms.MTBaseService, com.magtek.mobile.android.mtcms.IMTService
    public void connect() {
        Log.i(TAG, "Connecting to " + this.m_address);
        setState(MTServiceState.Connecting);
        this.mThis = this;
        try {
            new Thread() { // from class: com.magtek.mobile.android.mtcms.MTTCPService.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    MTTCPService.this.mTCPClient = new MTTCPClient(MTTCPService.this.m_address, 5000, 15000, MTTCPService.this.mThis);
                    MTTCPService.this.mTCPClient.startClient();
                }
            }.start();
        } catch (Exception unused) {
        }
    }

    @Override // com.magtek.mobile.android.mtcms.MTBaseService, com.magtek.mobile.android.mtcms.IMTService
    public void disconnect() {
        Log.i(TAG, "Diconnecting");
        if (this.m_state == MTServiceState.Disconnecting || this.m_state == MTServiceState.Disconnected) {
            return;
        }
        setState(MTServiceState.Disconnecting);
        IMTTCPClient iMTTCPClient = this.mTCPClient;
        if (iMTTCPClient != null) {
            iMTTCPClient.stopClient();
        }
    }

    @Override // com.magtek.mobile.android.mtcms.MTBaseService, com.magtek.mobile.android.mtcms.IMTService
    public void sendData(byte[] bArr) {
        writeData(bArr);
    }

    public boolean writeData(byte[] bArr) {
        String str = TAG;
        Log.i(str, "TCPService writeData data.length=" + bArr.length);
        Log.i(str, "TCPService writeData data=" + MTParser.getHexString(bArr));
        int length = bArr.length;
        int i = 0;
        while (i < length) {
            int i2 = length - i;
            if (i2 > 2048) {
                i2 = 2048;
            }
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i, bArr2, 0, i2);
            try {
                this.mTCPClient.sendData(bArr2);
            } catch (Exception unused) {
            }
            i += i2;
        }
        return false;
    }

    @Override // com.magtek.mobile.android.mtcms.MTTCPClientAdapter
    public void OnConnected() {
        setState(MTServiceState.Connected);
    }

    @Override // com.magtek.mobile.android.mtcms.MTTCPClientAdapter
    public void OnDisconnected() {
        setState(MTServiceState.Disconnected);
    }

    @Override // com.magtek.mobile.android.mtcms.MTTCPClientAdapter
    public void OnDataReceived(byte[] bArr) {
        if (bArr != null) {
            byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
            String str = TAG;
            Log.i(str, "Data Copy Length=" + bArrCopyOf.length);
            if (this.m_serviceAdapter != null) {
                this.m_serviceAdapter.OnDeviceData(bArr);
            } else {
                Log.i(str, "ServiceAdapter is NULL");
            }
        }
    }
}
