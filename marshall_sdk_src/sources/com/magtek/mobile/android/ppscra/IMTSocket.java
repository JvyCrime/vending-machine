package com.magtek.mobile.android.ppscra;

/* JADX INFO: loaded from: classes.dex */
public interface IMTSocket {
    void sendData(byte[] bArr);

    void setClientCertificate(String str, byte[] bArr, String str2);

    void setTrustAll(boolean z);

    void startClient(int i);

    void stopClient();
}
