package com.magtek.mobile.android.mtlib;

/* JADX INFO: loaded from: classes.dex */
public interface IMTEMV {
    int cancelTransaction();

    int sendExtendedCommand(String str);

    int setAcquirerResponse(byte[] bArr);

    int setUserSelectionResult(byte b, byte b2);

    int startTransaction(byte b, byte b2, byte b3, byte[] bArr, byte b4, byte[] bArr2, byte[] bArr3, byte b5);
}
