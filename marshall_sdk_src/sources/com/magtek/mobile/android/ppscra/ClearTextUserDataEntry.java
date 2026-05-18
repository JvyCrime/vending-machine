package com.magtek.mobile.android.ppscra;

/* JADX INFO: loaded from: classes.dex */
public class ClearTextUserDataEntry {
    public byte[] Data;
    public byte Mode;
    public byte Status;

    ClearTextUserDataEntry() {
        clearData();
    }

    public void clearData() {
        this.Status = (byte) 0;
        this.Mode = (byte) 0;
        this.Data = null;
    }

    public void setStatus(byte b) {
        this.Status = b;
    }

    public void setMode(byte b) {
        this.Mode = b;
    }

    public void setData(byte[] bArr) {
        this.Data = bArr;
    }

    public byte getStatus() {
        return this.Status;
    }

    public byte getMode() {
        return this.Mode;
    }

    public byte[] getData() {
        return this.Data;
    }
}
