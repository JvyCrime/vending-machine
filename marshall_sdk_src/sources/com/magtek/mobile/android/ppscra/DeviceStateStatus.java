package com.magtek.mobile.android.ppscra;

/* JADX INFO: loaded from: classes.dex */
public class DeviceStateStatus {
    public byte DeviceCertificateStatus;
    public byte DeviceState;
    public byte DeviceStatus;
    public byte HardwareStatus;
    public byte ICCMasterAndSessionKeyStatus;
    public byte SessionState;

    DeviceStateStatus() {
        clearData();
    }

    public void clearData() {
        this.DeviceState = (byte) 0;
        this.SessionState = (byte) 0;
        this.DeviceStatus = (byte) 0;
        this.DeviceCertificateStatus = (byte) 0;
        this.HardwareStatus = (byte) 0;
        this.ICCMasterAndSessionKeyStatus = (byte) 0;
    }
}
