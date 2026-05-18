package com.magtek.mobile.android.mtusdk.common;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TLVObject {
    protected List<TLVObject> mTLVObjects;
    protected byte[] mTag;
    protected byte[] mValue;

    public TLVObject() {
    }

    public TLVObject(String str, String str2) {
        initialize(TLVParser.getByteArrayFromHexString(str), TLVParser.getByteArrayFromHexString(str2));
    }

    public TLVObject(String str) {
        initialize(TLVParser.getByteArrayFromHexString(str), null);
    }

    public TLVObject(byte b, byte[] bArr) {
        initialize(new byte[]{b}, bArr);
    }

    public TLVObject(byte[] bArr, byte[] bArr2) {
        initialize(bArr, bArr2);
    }

    public TLVObject(byte b) {
        initialize(new byte[]{b}, null);
    }

    public TLVObject(byte b, byte b2) {
        initialize(new byte[]{b}, new byte[]{b2});
    }

    public TLVObject(byte[] bArr) {
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
        return TLVParser.isPrimitiveTagByteArray(this.mTag);
    }

    public boolean isConstructedObject() {
        return TLVParser.isConstructedTagByteArray(this.mTag);
    }

    public boolean addTLVObject(TLVObject tLVObject) {
        if (this.mTLVObjects == null) {
            this.mTLVObjects = new ArrayList();
        }
        if (tLVObject == null) {
            return true;
        }
        this.mTLVObjects.add(tLVObject);
        return true;
    }

    public boolean removeTLVObject(TLVObject tLVObject) {
        List<TLVObject> list = this.mTLVObjects;
        if (list == null) {
            return false;
        }
        list.remove(tLVObject);
        return true;
    }

    public String getTLVHexString() {
        byte[] tLVByteArray = getTLVByteArray();
        return tLVByteArray != null ? TLVParser.getHexString(tLVByteArray) : "";
    }

    public byte[] getTLVByteArray() {
        byte[] valueByteArray = getValueByteArray();
        byte[] lengthByteArray = TLVParser.getLengthByteArray(valueByteArray != null ? valueByteArray.length : 0);
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
        return TLVParser.getHexString(getTagByteArray());
    }

    public byte[] getTagByteArray() {
        return this.mTag;
    }

    public String getValueHexString() {
        byte[] valueByteArray = getValueByteArray();
        return valueByteArray != null ? TLVParser.getHexString(valueByteArray) : "";
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

    public List<TLVObject> getValueTLVObjectList() {
        return this.mTLVObjects;
    }

    public TLVObject findByTopLevelTagHexString(String str) {
        return findByTopLevelTagByteArray(TLVParser.getByteArrayFromHexString(str));
    }

    public TLVObject findByTagHexString(String str) {
        return findByTagByteArray(TLVParser.getByteArrayFromHexString(str));
    }

    public TLVObject findChildByTagHexString(String str) {
        return findChildByTagByteArray(TLVParser.getByteArrayFromHexString(str));
    }

    public TLVObject findByTopLevelTagByteArray(byte[] bArr) {
        byte[] tagByteArray;
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
            TLVObject tLVObject = this.mTLVObjects.get(i);
            if (tLVObject != null && (tagByteArray = tLVObject.getTagByteArray()) != null && Arrays.equals(bArr, tagByteArray)) {
                return tLVObject;
            }
        }
        return null;
    }

    public TLVObject findByTagByteArray(byte[] bArr) {
        TLVObject tLVObjectFindByTagByteArray;
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
            TLVObject tLVObject = this.mTLVObjects.get(i);
            if (tLVObject != null && (tLVObjectFindByTagByteArray = tLVObject.findByTagByteArray(bArr)) != null) {
                return tLVObjectFindByTagByteArray;
            }
        }
        return null;
    }

    public TLVObject findChildByTagByteArray(byte[] bArr) {
        TLVObject tLVObjectFindByTagByteArray;
        if (bArr == null || this.mTLVObjects == null) {
            return null;
        }
        for (int i = 0; i < this.mTLVObjects.size(); i++) {
            TLVObject tLVObject = this.mTLVObjects.get(i);
            if (tLVObject != null && (tLVObjectFindByTagByteArray = tLVObject.findByTagByteArray(bArr)) != null) {
                return tLVObjectFindByTagByteArray;
            }
        }
        return null;
    }
}
