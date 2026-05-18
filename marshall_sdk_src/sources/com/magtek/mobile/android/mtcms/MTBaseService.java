package com.magtek.mobile.android.mtcms;

import android.content.Context;
import android.util.Log;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
abstract class MTBaseService implements Runnable, IMTService {
    private static final String TAG = "MTBaseService";
    protected String m_address;
    protected Context m_context;
    protected String m_deviceID;
    protected MTServiceAdapter m_serviceAdapter;
    protected UUID m_serviceUUID;
    protected MTServiceState m_state = MTServiceState.Disconnected;

    public void close() {
    }

    @Override // com.magtek.mobile.android.mtcms.IMTService
    public void connect() {
    }

    @Override // com.magtek.mobile.android.mtcms.IMTService
    public void disconnect() {
    }

    @Override // java.lang.Runnable
    public void run() {
    }

    @Override // com.magtek.mobile.android.mtcms.IMTService
    public void sendData(byte[] bArr) {
    }

    MTBaseService() {
    }

    protected void setState(MTServiceState mTServiceState) {
        String str = TAG;
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

    @Override // com.magtek.mobile.android.mtcms.IMTService
    public MTServiceState getState() {
        return this.m_state;
    }

    @Override // com.magtek.mobile.android.mtcms.IMTService
    public void setServiceUUID(UUID uuid) {
        this.m_serviceUUID = uuid;
    }

    @Override // com.magtek.mobile.android.mtcms.IMTService
    public void initialize(Context context, MTServiceAdapter mTServiceAdapter) {
        this.m_context = context;
        this.m_serviceAdapter = mTServiceAdapter;
    }

    @Override // com.magtek.mobile.android.mtcms.IMTService
    public void setAddress(String str) {
        this.m_address = str;
    }

    @Override // com.magtek.mobile.android.mtcms.IMTService
    public void setDeviceID(String str) {
        this.m_deviceID = str;
    }
}
