package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public class BarCodeData {
    private byte[] mData;
    private boolean mEncrypted;
    private byte mEncryptionType;
    private byte[] mKSN;

    public byte[] Data() {
        return this.mData;
    }

    public boolean Encrypted() {
        return this.mEncrypted;
    }

    public byte EncryptionType() {
        return this.mEncryptionType;
    }

    public byte[] KSN() {
        return this.mKSN;
    }

    public BarCodeData(byte[] bArr, boolean z) {
        this.mData = bArr;
        this.mEncrypted = z;
    }

    public BarCodeData(byte[] bArr, boolean z, byte b, byte[] bArr2) {
        this.mData = bArr;
        this.mEncrypted = z;
        this.mEncryptionType = b;
        this.mKSN = bArr2;
    }
}
