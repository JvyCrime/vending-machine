package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public class BaseData implements IData {
    private String mStringValue = null;
    private byte[] mByteArray = null;

    public BaseData(String str) {
        init(str, null);
    }

    public BaseData(byte[] bArr) {
        init(null, bArr);
    }

    public BaseData(String str, byte[] bArr) {
        init(str, bArr);
    }

    protected void init(String str, byte[] bArr) {
        this.mStringValue = null;
        this.mByteArray = null;
        if (str != null) {
            this.mStringValue = new String(str);
        }
        if (bArr != null) {
            this.mByteArray = (byte[]) bArr.clone();
        }
    }

    @Override // com.magtek.mobile.android.mtusdk.IData
    public String StringValue() {
        return this.mStringValue;
    }

    @Override // com.magtek.mobile.android.mtusdk.IData
    public byte[] ByteArray() {
        return this.mByteArray;
    }

    @Override // com.magtek.mobile.android.mtusdk.IData
    public IData Clone() {
        return new BaseData(this.mStringValue, this.mByteArray);
    }
}
