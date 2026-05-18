package com.magtek.mobile.android.mtusdk.messages;

import com.magtek.mobile.android.mtusdk.common.TLVObject;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import java.io.ByteArrayOutputStream;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Message extends TLVObject {
    private static byte[] MESSAGE_TAG = {PPSCRADeviceValues.EMV_COMMAND_CONFIRM_SESSION_KEY, -1};

    public Message() {
        initialize(MESSAGE_TAG, null);
    }

    public Message(byte[] bArr) {
        initialize(bArr, null);
    }

    public Message(byte[] bArr, byte[] bArr2) {
        List<TLVObject> tLVByteArray;
        initialize(bArr, bArr2);
        if (bArr2 == null || (tLVByteArray = TLVParser.parseTLVByteArray(bArr2)) == null) {
            return;
        }
        for (int i = 0; i < tLVByteArray.size(); i++) {
            addTLVObject(tLVByteArray.get(i));
        }
    }

    public void addParam(byte[] bArr, byte[] bArr2) {
        addTLVObject(new TLVObject(bArr, bArr2));
    }

    public void addParam(String str, String str2) {
        addTLVObject(new TLVObject(str, str2));
    }

    public void addMessageInfo(byte[] bArr) {
        addTLVObject(MessageBuilder.BuildMessageInfoForCommand(bArr));
    }

    public void addMessageInfo(String str) {
        addMessageInfo(TLVParser.getByteArrayFromHexString(str));
    }

    public void addMessageInfoForCommand(byte[] bArr) {
        addTLVObject(MessageBuilder.BuildMessageInfoForCommand(bArr));
    }

    public void addMessageInfoForCommand(String str) {
        addMessageInfoForCommand(TLVParser.getByteArrayFromHexString(str));
    }

    public void addMessageInfoForDataFile(byte[] bArr, byte[] bArr2) {
        addTLVObject(MessageBuilder.BuildMessageInfoForDataFile(bArr, bArr2));
    }

    public void addMessageInfoForDataFile(String str, String str2) {
        addMessageInfoForDataFile(TLVParser.getByteArrayFromHexString(str), TLVParser.getByteArrayFromHexString(str2));
    }

    public void addPayload(byte[] bArr) {
        addParam(TLVParser.getByteArrayFromHexString("84"), bArr);
    }

    public void addPayload(String str) {
        addParam("84", str);
    }

    public byte[] getByteArray() {
        byte[] valueByteArray = getValueByteArray();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(this.mTag);
            if (valueByteArray != null) {
                byteArrayOutputStream.write(valueByteArray);
            }
        } catch (Exception unused) {
        }
        return byteArrayOutputStream.toByteArray();
    }

    public TLVObject getMsgInfo() {
        return findByTagHexString("81");
    }

    public byte getMsgInfoValue(int i) {
        byte[] valueByteArray;
        TLVObject msgInfo = getMsgInfo();
        if (msgInfo == null || (valueByteArray = msgInfo.getValueByteArray()) == null || valueByteArray.length <= i) {
            return (byte) 0;
        }
        return valueByteArray[i];
    }

    public byte getDirectionAndType() {
        return getMsgInfoValue(0);
    }

    public byte getMessageID() {
        return getMsgInfoValue(1);
    }

    public byte[] getRequestID() {
        byte[] valueByteArray;
        byte[] bArr = {0, 0};
        TLVObject msgInfo = getMsgInfo();
        if (msgInfo != null && (valueByteArray = msgInfo.getValueByteArray()) != null && valueByteArray.length >= 4) {
            bArr[0] = valueByteArray[2];
            bArr[1] = valueByteArray[3];
        }
        return bArr;
    }

    public byte getCRCType() {
        return getMsgInfoValue(4);
    }

    public boolean isRequest() {
        return getDirectionAndType() == 1;
    }

    public boolean isResponse() {
        return getDirectionAndType() == -126;
    }

    public boolean isNotification() {
        return getDirectionAndType() == -125;
    }

    public boolean isDataFile() {
        return getDirectionAndType() == -124;
    }

    public boolean isFile() {
        return getDirectionAndType() == -124;
    }

    public TLVObject getPayload() {
        return findByTagHexString("84");
    }

    public TLVObject getCRC() {
        return findByTagHexString("9E");
    }

    public Command getCommand() {
        TLVObject payload = getPayload();
        if (payload != null) {
            return MessageParser.GetCommand(payload.getValueByteArray());
        }
        return null;
    }
}
