package com.magtek.mobile.android.ppscra;

import android.content.Context;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class MTPPBaseService implements IMTPPService, Runnable {
    private static final String a = "MTPPBaseService";
    protected String m_address;
    protected Context m_context;
    protected MTServiceAdapter m_serviceAdapter;
    protected MTServiceState m_state = MTServiceState.Disconnected;
    protected boolean m_useTLS12 = false;
    protected boolean m_trustAll = false;
    protected String m_clientCertificateFormat = "";
    protected byte[] m_clientCertificateData = null;
    protected String m_clientCertificatePassword = "";

    @Override // com.magtek.mobile.android.ppscra.IMTPPService
    public void close() {
    }

    @Override // com.magtek.mobile.android.ppscra.IMTPPService
    public void connect() {
    }

    @Override // com.magtek.mobile.android.ppscra.IMTPPService
    public void disconnect() {
    }

    @Override // com.magtek.mobile.android.ppscra.IMTPPService
    public byte[] getDeviceSerialNumber() {
        return null;
    }

    @Override // com.magtek.mobile.android.ppscra.IMTPPService
    public String getFirmwareVersion() {
        return "";
    }

    @Override // com.magtek.mobile.android.ppscra.IMTPPService
    public String getProductID() {
        return "";
    }

    @Override // com.magtek.mobile.android.ppscra.IMTPPService
    public String getProductName() {
        return "";
    }

    @Override // java.lang.Runnable
    public void run() {
    }

    @Override // com.magtek.mobile.android.ppscra.IMTPPService
    public void sendData(byte[] bArr) {
    }

    @Override // com.magtek.mobile.android.ppscra.IMTPPService
    public void setConnectionRetry(boolean z) {
    }

    protected void setState(MTServiceState mTServiceState) {
        String str = a;
        Log.i(str, "setState:Current:" + this.m_state + ",New:" + mTServiceState);
        if (this.m_state != mTServiceState) {
            this.m_state = mTServiceState;
            MTServiceAdapter mTServiceAdapter = this.m_serviceAdapter;
            if (mTServiceAdapter != null) {
                mTServiceAdapter.OnServiceState(mTServiceState);
            } else {
                Log.i(str, "ServiceAdapter is NULL");
            }
        }
    }

    @Override // com.magtek.mobile.android.ppscra.IMTPPService
    public MTServiceState getState() {
        return this.m_state;
    }

    @Override // com.magtek.mobile.android.ppscra.IMTPPService
    public void initialize(Context context, MTServiceAdapter mTServiceAdapter) {
        this.m_context = context;
        this.m_serviceAdapter = mTServiceAdapter;
    }

    @Override // com.magtek.mobile.android.ppscra.IMTPPService
    public void setAddress(String str) {
        this.m_address = str;
    }

    @Override // com.magtek.mobile.android.ppscra.IMTPPService
    public void setTLS(boolean z, boolean z2) {
        this.m_useTLS12 = z;
        this.m_trustAll = z2;
    }

    @Override // com.magtek.mobile.android.ppscra.IMTPPService
    public void setClientCertificate(String str, byte[] bArr, String str2) {
        this.m_clientCertificateFormat = str;
        this.m_clientCertificateData = bArr;
        this.m_clientCertificatePassword = str2;
    }
}
