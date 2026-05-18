package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public interface IConfigurationCallback {
    IResult OnCalculateMAC(byte b, byte[] bArr);

    void OnProgress(int i);

    void OnResult(StatusCode statusCode, byte[] bArr);
}
