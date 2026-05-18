package com.magtek.mobile.android.mtusdk.mmx;

/* JADX INFO: loaded from: classes.dex */
public interface IMMXServiceAdapter {
    void OnConnectionStateChanged(MMXConnectionState mMXConnectionState);

    void OnDataReceived(byte[] bArr);

    void OnSendDataProgress(int i);
}
