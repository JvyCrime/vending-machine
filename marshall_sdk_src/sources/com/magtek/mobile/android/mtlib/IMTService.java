package com.magtek.mobile.android.mtlib;

import android.content.Context;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public interface IMTService {
    void connect();

    void disconnect();

    long getBatteryLevel();

    MTDeviceFeatures getDeviceFeatures();

    String getDeviceName();

    String getDevicePMValue();

    String getDeviceSerial();

    String getFirmwareID();

    MTServiceState getState();

    void initialize(Context context, MTServiceAdapter mTServiceAdapter);

    boolean isOutputChannelConfigurable();

    boolean isServiceEMV();

    boolean isServiceOEM();

    boolean sendData(byte[] bArr);

    void setAddress(String str);

    void setConfiguration(String str);

    void setConnectionRetry(boolean z);

    void setConnectionTimeout(int i);

    void setDeviceID(String str);

    void setServiceUUID(UUID uuid);
}
