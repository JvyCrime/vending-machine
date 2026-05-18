package com.magtek.mobile.android.mtusdk.mmx;

/* JADX INFO: loaded from: classes.dex */
public class MMXMessage {
    protected byte[] mData;
    protected int mTag;

    public MMXMessage(int i, byte[] bArr) {
        this.mTag = i;
        this.mData = bArr;
    }

    public int getTag() {
        return this.mTag;
    }

    public byte[] getData() {
        return this.mData;
    }
}
