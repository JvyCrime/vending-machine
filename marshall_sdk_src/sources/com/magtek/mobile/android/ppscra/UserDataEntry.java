package com.magtek.mobile.android.ppscra;

/* JADX INFO: loaded from: classes.dex */
public class UserDataEntry {
    public byte[] EncryptedUserDataBlock;
    public byte[] MSRKSN;
    public byte Status;

    UserDataEntry() {
        clearData();
    }

    public void clearData() {
        this.Status = (byte) 0;
        this.MSRKSN = null;
        this.EncryptedUserDataBlock = null;
    }

    public void setStatus(byte b) {
        this.Status = b;
    }

    public void setMSRKSN(byte[] bArr) {
        this.MSRKSN = bArr;
    }

    public void setEncryptedUserDataBlock(byte[] bArr) {
        this.EncryptedUserDataBlock = bArr;
    }

    public byte getStatus() {
        return this.Status;
    }

    public byte[] getMSRKSN() {
        return this.MSRKSN;
    }

    public byte[] getEncryptedUserDataBlock() {
        return this.EncryptedUserDataBlock;
    }
}
