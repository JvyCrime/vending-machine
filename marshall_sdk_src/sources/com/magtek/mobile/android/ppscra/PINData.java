package com.magtek.mobile.android.ppscra;

/* JADX INFO: loaded from: classes.dex */
public class PINData {
    public byte[] EncryptedPINBlock;
    public byte[] PINKSN;
    public byte Status;

    PINData() {
        clearData();
    }

    public void clearData() {
        this.Status = (byte) 0;
        this.PINKSN = null;
        this.EncryptedPINBlock = null;
    }

    public void setStatus(byte b) {
        this.Status = b;
    }

    public void setPINKSN(byte[] bArr) {
        this.PINKSN = bArr;
    }

    public void setEncryptedPINBlock(byte[] bArr) {
        this.EncryptedPINBlock = bArr;
    }

    public byte getStatus() {
        return this.Status;
    }

    public byte[] getPINKSN() {
        return this.PINKSN;
    }

    public byte[] getEncryptedPINBlock() {
        return this.EncryptedPINBlock;
    }
}
