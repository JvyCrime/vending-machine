package com.magtek.mobile.android.mtlib;

/* JADX INFO: loaded from: classes.dex */
public interface MTServiceAdapter {
    void OnBondingFailure();

    void OnCardData(byte[] bArr);

    void OnCardDataError();

    void OnCommandData(byte[] bArr);

    void OnDeviceData(byte[] bArr);

    void OnDeviceError();

    void OnServiceState(MTServiceState mTServiceState);
}
