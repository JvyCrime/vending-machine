package com.magtek.mobile.android.ppscra;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public interface IMTPPService {
    void close();

    void connect();

    void disconnect();

    byte[] getDeviceSerialNumber();

    String getFirmwareVersion();

    String getProductID();

    String getProductName();

    MTServiceState getState();

    void initialize(Context context, MTServiceAdapter mTServiceAdapter);

    void sendData(byte[] bArr);

    void setAddress(String str);

    void setClientCertificate(String str, byte[] bArr, String str2);

    void setConnectionRetry(boolean z);

    void setTLS(boolean z, boolean z2);
}
