package com.magtek.mobile.android.mtcms;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MTTLVObject {
    private List<MTTLVObject> mTLVObjects;
    private byte[] mTag;
    private byte[] mValue;

    public MTTLVObject(String str, String str2) {
        initialize(MTTLVParser.getByteArrayFromHexString(str), MTTLVParser.getByteArrayFromHexString(str2));
    }

    public MTTLVObject(String str) {
        initialize(MTTLVParser.getByteArrayFromHexString(str), null);
    }

    public MTTLVObject(byte[] bArr, byte[] bArr2) {
        initialize(bArr, bArr2);
    }

    public MTTLVObject(byte[] bArr) {
        initialize(bArr, null);
    }

    protected void initialize(byte[] bArr, byte[] bArr2) {
        this.mTag = null;
        this.mValue = null;
        this.mTLVObjects = null;
        if (bArr != null) {
            this.mTag = (byte[]) bArr.clone();
        }
        if (bArr2 != null) {
            this.mValue = (byte[]) bArr2.clone();
        }
    }

    public boolean isPrimitiveObject() {
        return MTTLVParser.isPrimitiveTagByteArray(this.mTag);
    }

    public boolean isConstructedObject() {
        return MTTLVParser.isConstructedTagByteArray(this.mTag);
    }

    public boolean addTLVObject(MTTLVObject mTTLVObject) {
        if (this.mTLVObjects == null) {
            this.mTLVObjects = new ArrayList();
        }
        if (mTTLVObject == null) {
            return true;
        }
        this.mTLVObjects.add(mTTLVObject);
        return true;
    }

    public boolean removeTLVObject(MTTLVObject mTTLVObject) {
        List<MTTLVObject> list = this.mTLVObjects;
        if (list == null) {
            return false;
        }
        list.remove(mTTLVObject);
        return true;
    }

    public String getTLVHexString() {
        byte[] tLVByteArray = getTLVByteArray();
        return tLVByteArray != null ? MTTLVParser.getHexString(tLVByteArray) : "";
    }

    public byte[] getTLVByteArray() {
        byte[] valueByteArray = getValueByteArray();
        byte[] lengthByteArray = MTTLVParser.getLengthByteArray(valueByteArray != null ? valueByteArray.length : 0);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(this.mTag);
            byteArrayOutputStream.write(lengthByteArray);
            if (valueByteArray != null) {
                byteArrayOutputStream.write(valueByteArray, 0, valueByteArray.length);
            }
        } catch (Exception unused) {
        }
        return byteArrayOutputStream.toByteArray();
    }

    public String getTagHexString() {
        return MTTLVParser.getHexString(getTagByteArray());
    }

    public byte[] getTagByteArray() {
        return this.mTag;
    }

    public String getValueHexString() {
        byte[] valueByteArray = getValueByteArray();
        return valueByteArray != null ? MTTLVParser.getHexString(valueByteArray) : "";
    }

    public String getValueTextString() {
        byte[] valueByteArray = getValueByteArray();
        return valueByteArray != null ? getTextString(valueByteArray, valueByteArray.length) : "";
    }

    private static String getTextString(byte[] bArr, int i) {
        return (bArr == null || bArr.length <= 0) ? "" : getTextString(bArr, i, bArr.length);
    }

    private static String getTextString(byte[] bArr, int i, int i2) {
        if (bArr == null || bArr.length <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArr.length + 1);
        while (i < i2) {
            try {
                sb.append(String.format("%c", Byte.valueOf(bArr[i])));
            } catch (Exception unused) {
                sb.append("<?>");
            }
            i++;
        }
        return sb.toString();
    }

    public byte[] getValueByteArray() {
        if (this.mTLVObjects != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (int i = 0; i < this.mTLVObjects.size(); i++) {
                byte[] tLVByteArray = this.mTLVObjects.get(i).getTLVByteArray();
                byteArrayOutputStream.write(tLVByteArray, 0, tLVByteArray.length);
            }
            return byteArrayOutputStream.toByteArray();
        }
        byte[] bArr = this.mValue;
        if (bArr != null) {
            return (byte[]) bArr.clone();
        }
        return null;
    }

    public List<MTTLVObject> getValueTLVObjectList() {
        return this.mTLVObjects;
    }

    public MTTLVObject findByTagHexString(String str) {
        return findByTagByteArray(MTTLVParser.getByteArrayFromHexString(str));
    }

    public MTTLVObject findByTagByteArray(byte[] bArr) {
        MTTLVObject mTTLVObjectFindByTagByteArray;
        if (bArr == null) {
            return null;
        }
        byte[] bArr2 = this.mTag;
        if (bArr2 != null && Arrays.equals(bArr2, bArr)) {
            return this;
        }
        if (this.mTLVObjects == null) {
            return null;
        }
        for (int i = 0; i < this.mTLVObjects.size(); i++) {
            MTTLVObject mTTLVObject = this.mTLVObjects.get(i);
            if (mTTLVObject != null && (mTTLVObjectFindByTagByteArray = mTTLVObject.findByTagByteArray(bArr)) != null) {
                return mTTLVObjectFindByTagByteArray;
            }
        }
        return null;
    }
}
