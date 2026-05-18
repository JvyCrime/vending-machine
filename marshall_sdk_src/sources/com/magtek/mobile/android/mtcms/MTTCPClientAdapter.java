package com.magtek.mobile.android.mtcms;

/* JADX INFO: loaded from: classes.dex */
public interface MTTCPClientAdapter {
    void OnConnected();

    void OnDataReceived(byte[] bArr);

    void OnDisconnected();
}
