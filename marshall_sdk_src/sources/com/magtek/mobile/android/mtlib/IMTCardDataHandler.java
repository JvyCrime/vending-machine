package com.magtek.mobile.android.mtlib;

/* JADX INFO: loaded from: classes.dex */
public interface IMTCardDataHandler {
    void clearData();

    void handleData(byte[] bArr);

    boolean isDataReady();

    void setConfiguration(String str);

    void setData(byte[] bArr);

    void setDataThreshold(int i);
}
