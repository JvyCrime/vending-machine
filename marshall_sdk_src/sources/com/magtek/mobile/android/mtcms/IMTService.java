package com.magtek.mobile.android.mtcms;

import android.content.Context;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public interface IMTService {
    void connect();

    void disconnect();

    MTServiceState getState();

    void initialize(Context context, MTServiceAdapter mTServiceAdapter);

    void sendData(byte[] bArr);

    void setAddress(String str);

    void setDeviceID(String str);

    void setServiceUUID(UUID uuid);
}
