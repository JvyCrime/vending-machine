package com.magtek.mobile.android.mtlib;

import android.content.Context;
import android.util.Log;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class MTBaseService implements Runnable, IMTService {
    private static final String TAG = "MTBaseService";
    protected String m_address;
    protected String m_configuration;
    protected Context m_context;
    protected String m_deviceID;
    protected MTServiceAdapter m_serviceAdapter;
    protected UUID m_serviceUUID;
    protected MTServiceState m_state = MTServiceState.Disconnected;

    public void close() {
    }

    public void connect() {
    }

    public void disconnect() {
    }

    public String getDeviceName() {
        return "";
    }

    public String getDevicePMValue() {
        return "";
    }

    public String getDeviceSerial() {
        return "";
    }

    public String getFirmwareID() {
        return "";
    }

    public boolean isOutputChannelConfigurable() {
        return false;
    }

    public boolean isServiceEMV() {
        return false;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTService
    public boolean isServiceOEM() {
        return false;
    }

    @Override // java.lang.Runnable
    public void run() {
    }

    public boolean sendData(byte[] bArr) {
        return false;
    }

    public void setConnectionRetry(boolean z) {
    }

    public void setConnectionTimeout(int i) {
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

    @Override // com.magtek.mobile.android.mtlib.IMTService
    public MTServiceState getState() {
        return this.m_state;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTService
    public void setServiceUUID(UUID uuid) {
        this.m_serviceUUID = uuid;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTService
    public void initialize(Context context, MTServiceAdapter mTServiceAdapter) {
        this.m_context = context;
        this.m_serviceAdapter = mTServiceAdapter;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTService
    public void setConfiguration(String str) {
        this.m_configuration = str;
    }

    public void setAddress(String str) {
        this.m_address = str;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTService
    public void setDeviceID(String str) {
        this.m_deviceID = str;
    }

    public long getBatteryLevel() {
        return MTDeviceConstants.BATTERY_LEVEL_NA;
    }

    public MTDeviceFeatures getDeviceFeatures() {
        return new MTDeviceFeatures();
    }
}
