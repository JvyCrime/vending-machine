package com.magtek.mobile.android.mtusdk.mmx;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public interface IMMXService {
    void connect();

    void disconnect();

    MMXConnectionState getState();

    void initialize(Context context, IMMXServiceAdapter iMMXServiceAdapter);

    void sendData(byte[] bArr);

    void setAddress(String str);
}
