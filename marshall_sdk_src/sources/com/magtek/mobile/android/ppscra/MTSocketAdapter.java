package com.magtek.mobile.android.ppscra;

/* JADX INFO: loaded from: classes.dex */
public interface MTSocketAdapter {
    void OnConnected();

    void OnConnecting();

    void OnDataReceived(byte[] bArr);

    void OnDisconnected();

    void OnDisconnecting();

    void OnListening();
}
