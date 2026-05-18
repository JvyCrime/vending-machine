package com.magtek.mobile.android.mtlib;

/* JADX INFO: loaded from: classes.dex */
public interface IMTSCRADevice {
    void close();

    boolean initialize(MTDeviceAdapter mTDeviceAdapter, IMTService iMTService, MTDataFormat mTDataFormat, int i);

    boolean open();

    boolean sendCommand(byte[] bArr);

    void setConfiguration(String str);
}
