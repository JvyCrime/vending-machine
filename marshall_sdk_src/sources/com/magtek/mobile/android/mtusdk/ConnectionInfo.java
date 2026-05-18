package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public class ConnectionInfo {
    protected String mAddress;
    protected ConnectionType mConnectionType;
    protected DeviceType mDeviceType;

    public ConnectionInfo(DeviceType deviceType, ConnectionType connectionType, String str) {
        this.mDeviceType = deviceType;
        this.mConnectionType = connectionType;
        this.mAddress = str;
    }

    public DeviceType getDeviceType() {
        return this.mDeviceType;
    }

    public ConnectionType getConnectionType() {
        return this.mConnectionType;
    }

    public String getAddress() {
        return this.mAddress;
    }
}
