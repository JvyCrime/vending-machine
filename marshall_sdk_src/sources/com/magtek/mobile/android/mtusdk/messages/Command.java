package com.magtek.mobile.android.mtusdk.messages;

import com.magtek.mobile.android.mtusdk.common.TLVObject;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import java.io.ByteArrayOutputStream;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Command extends TLVObject {
    public Command(byte[] bArr, byte[] bArr2) {
        initializeCommand(bArr, bArr2);
    }

    public Command(String str, String str2) {
        initializeCommand(TLVParser.getByteArrayFromHexString(str), TLVParser.getByteArrayFromHexString(str2));
    }

    public void initializeCommand(byte[] bArr, byte[] bArr2) {
        List<TLVObject> tLVByteArray;
        initialize(bArr, bArr2);
        if (bArr2 == null || (tLVByteArray = TLVParser.parseTLVByteArray(bArr2)) == null) {
            return;
        }
        for (int i = 0; i < tLVByteArray.size(); i++) {
            addTLVObject(tLVByteArray.get(i));
        }
    }

    public void addParam(byte b, byte b2) {
        addParam(new byte[]{b}, new byte[]{b2});
    }

    public void addParam(byte b, byte[] bArr) {
        addParam(new byte[]{b}, bArr);
    }

    public void addParam(byte[] bArr, byte[] bArr2) {
        addTLVObject(new TLVObject(bArr, bArr2));
    }

    public void addParam(String str, String str2) {
        addTLVObject(new TLVObject(str, str2));
    }

    public void addParam(TLVObject tLVObject) {
        addTLVObject(tLVObject);
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

    public TLVObject getPayload() {
        return findByTagHexString("84");
    }

    public Command getCommand() {
        TLVObject payload = getPayload();
        if (payload != null) {
            return MessageParser.GetCommand(payload.getValueByteArray());
        }
        return null;
    }
}
