package com.magtek.mobile.android.mtcms;

import com.magtek.mobile.android.mtusdk.cms.DataTypeTag;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MTCMSMessage {
    protected static byte[] TAG_C0 = {PPSCRADeviceValues.EMV_TAG_TYPE_DRL_GROUP};
    protected static byte[] TAG_C1 = {-63};
    protected static byte[] TAG_C2 = {-62};
    protected static byte[] TAG_C3 = {-61};
    protected int mApplicationID;
    protected int mCommandID;
    protected byte[] mData;
    protected int mDataTag;
    protected byte[] mMessageBytes;
    protected int mMessageType;
    protected int mResultCode;

    public MTCMSMessage(int i, int i2, int i3, int i4, int i5, byte[] bArr) {
        this.mMessageBytes = null;
        this.mMessageType = i & 255;
        this.mApplicationID = i2 & 255;
        this.mCommandID = i3 & 255;
        this.mResultCode = i4 & 255;
        this.mDataTag = i5;
        this.mData = bArr;
        buildMessageBytes();
    }

    public MTCMSMessage(byte[] bArr) {
        int length;
        this.mMessageBytes = null;
        if (bArr == null || (length = bArr.length) <= 0) {
            return;
        }
        byte[] bArr2 = new byte[length];
        this.mMessageBytes = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, length);
        parseMessageBytes();
    }

    public void setMessageType(int i) {
        this.mMessageType = i;
        buildMessageBytes();
    }

    public void setApplicationID(int i) {
        this.mApplicationID = i;
        buildMessageBytes();
    }

    public void setCommandID(int i) {
        this.mCommandID = i;
        buildMessageBytes();
    }

    public void setResultCode(int i) {
        this.mResultCode = i;
        buildMessageBytes();
    }

    public void setData(int i, byte[] bArr) {
        this.mDataTag = i;
        this.mData = bArr;
        buildMessageBytes();
    }

    public int getMessageType() {
        return this.mMessageType;
    }

    public int getApplicationID() {
        return this.mApplicationID;
    }

    public int getCommandID() {
        return this.mCommandID;
    }

    public int getResultCode() {
        return this.mResultCode;
    }

    public int getDataTag() {
        return this.mDataTag;
    }

    public byte[] getData() {
        return this.mData;
    }

    public byte[] getMessageBytes() {
        return this.mMessageBytes;
    }

    protected void buildMessageBytes() {
        MTTLVObject mTTLVObject = new MTTLVObject("FA");
        mTTLVObject.addTLVObject(new MTTLVObject(TAG_C0, new byte[]{(byte) (this.mMessageType & 255)}));
        mTTLVObject.addTLVObject(new MTTLVObject(TAG_C1, new byte[]{(byte) (this.mApplicationID & 255)}));
        mTTLVObject.addTLVObject(new MTTLVObject(TAG_C2, new byte[]{(byte) (this.mCommandID & 255)}));
        int i = this.mMessageType;
        if (i == 2 || i == 3) {
            mTTLVObject.addTLVObject(new MTTLVObject(TAG_C3, new byte[]{(byte) (this.mResultCode & 255)}));
        }
        byte[] bArr = this.mData;
        if (bArr != null && bArr.length > 0) {
            mTTLVObject.addTLVObject(new MTTLVObject(new byte[]{(byte) (this.mDataTag & 255)}, bArr));
        }
        this.mMessageBytes = mTTLVObject.getValueByteArray();
    }

    protected int getFirstByteValue(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return 0;
        }
        return bArr[0] & 255;
    }

    protected void parseMessageBytes() {
        byte[] bArr = this.mMessageBytes;
        if (bArr != null) {
            List<MTTLVObject> tLVByteArray = MTTLVParser.parseTLVByteArray(bArr, true);
            MTTLVObject mTTLVObjectFindFromListByTagHexString = MTTLVParser.findFromListByTagHexString(tLVByteArray, "C0");
            if (mTTLVObjectFindFromListByTagHexString != null) {
                this.mMessageType = getFirstByteValue(mTTLVObjectFindFromListByTagHexString.getValueByteArray());
            }
            MTTLVObject mTTLVObjectFindFromListByTagHexString2 = MTTLVParser.findFromListByTagHexString(tLVByteArray, "C1");
            if (mTTLVObjectFindFromListByTagHexString2 != null) {
                this.mApplicationID = getFirstByteValue(mTTLVObjectFindFromListByTagHexString2.getValueByteArray());
            }
            MTTLVObject mTTLVObjectFindFromListByTagHexString3 = MTTLVParser.findFromListByTagHexString(tLVByteArray, "C2");
            if (mTTLVObjectFindFromListByTagHexString3 != null) {
                this.mCommandID = getFirstByteValue(mTTLVObjectFindFromListByTagHexString3.getValueByteArray());
            }
            MTTLVObject mTTLVObjectFindFromListByTagHexString4 = MTTLVParser.findFromListByTagHexString(tLVByteArray, "C3");
            if (mTTLVObjectFindFromListByTagHexString4 != null) {
                this.mResultCode = getFirstByteValue(mTTLVObjectFindFromListByTagHexString4.getValueByteArray());
            }
            MTTLVObject mTTLVObjectFindFromListByTagHexString5 = MTTLVParser.findFromListByTagHexString(tLVByteArray, "C4");
            if (mTTLVObjectFindFromListByTagHexString5 != null) {
                this.mDataTag = 196;
                this.mData = mTTLVObjectFindFromListByTagHexString5.getValueByteArray();
                return;
            }
            MTTLVObject mTTLVObjectFindFromListByTagHexString6 = MTTLVParser.findFromListByTagHexString(tLVByteArray, "E0");
            if (mTTLVObjectFindFromListByTagHexString6 != null) {
                this.mDataTag = DataTypeTag.CONSTRUCTIVE;
                this.mData = mTTLVObjectFindFromListByTagHexString6.getValueByteArray();
            }
        }
    }
}
