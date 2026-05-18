package com.magtek.mobile.android.mtusdk.messages;

import com.magtek.mobile.android.mtusdk.common.TLVObject;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NotificationPayload extends TLVObject {
    public NotificationPayload(byte[] bArr, byte[] bArr2) {
        initializeNotificationPayload(bArr, bArr2);
    }

    public NotificationPayload(String str, String str2) {
        initializeNotificationPayload(TLVParser.getByteArrayFromHexString(str), TLVParser.getByteArrayFromHexString(str2));
    }

    public void initializeNotificationPayload(byte[] bArr, byte[] bArr2) {
        List<TLVObject> tLVByteArray;
        initialize(bArr, bArr2);
        if (bArr2 == null || (tLVByteArray = TLVParser.parseTLVByteArray(bArr2)) == null) {
            return;
        }
        for (int i = 0; i < tLVByteArray.size(); i++) {
            addTLVObject(tLVByteArray.get(i));
        }
    }

    public byte[] getParamValue(String str) {
        TLVObject tLVObjectFindByTagHexString = findByTagHexString(str);
        if (tLVObjectFindByTagHexString != null) {
            return tLVObjectFindByTagHexString.getValueByteArray();
        }
        return null;
    }

    public TLVObject getPayload(String str) {
        return findByTagHexString(str);
    }

    public TLVObject getPayload() {
        return getPayload("84");
    }

    public byte[] getTagValue(String str) {
        TLVObject payload = getPayload(str);
        if (payload != null) {
            return payload.getValueByteArray();
        }
        return null;
    }

    public byte[] getPayloadValue() {
        return getTagValue("84");
    }
}
