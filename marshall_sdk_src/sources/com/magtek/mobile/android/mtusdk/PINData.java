package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public class PINData {
    private byte mEncryptionType;
    private byte mFormat;
    private byte[] mKSN;
    private byte[] mPINBlock;

    public byte[] PINBlock() {
        return this.mPINBlock;
    }

    public byte[] KSN() {
        return this.mKSN;
    }

    public byte Format() {
        return this.mFormat;
    }

    public byte EncryptionType() {
        return this.mEncryptionType;
    }

    public PINData(byte[] bArr, byte[] bArr2, byte b, byte b2) {
        this.mPINBlock = bArr;
        this.mKSN = bArr2;
        this.mFormat = b;
        this.mEncryptionType = b2;
    }
}
