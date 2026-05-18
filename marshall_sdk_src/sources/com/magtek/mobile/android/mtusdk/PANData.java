package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public class PANData {
    private byte[] mData;
    private byte mEncryptionType;
    private byte[] mKSN;
    private PINData mPINData;

    public byte[] Data() {
        return this.mData;
    }

    public byte[] KSN() {
        return this.mKSN;
    }

    public byte EncryptionType() {
        return this.mEncryptionType;
    }

    public PINData PINData() {
        return this.mPINData;
    }

    public PANData(byte[] bArr, byte[] bArr2, byte b, PINData pINData) {
        this.mData = bArr;
        this.mKSN = bArr2;
        this.mEncryptionType = b;
        this.mPINData = pINData;
    }
}
