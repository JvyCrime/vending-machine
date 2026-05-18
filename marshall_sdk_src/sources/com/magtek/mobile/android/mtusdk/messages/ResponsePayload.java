package com.magtek.mobile.android.mtusdk.messages;

import com.magtek.mobile.android.mtusdk.common.TLVObject;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ResponsePayload extends TLVObject {
    public ResponsePayload(byte[] bArr, byte[] bArr2) {
        initializeResponsePayload(bArr, bArr2);
    }

    public ResponsePayload(String str, String str2) {
        initializeResponsePayload(TLVParser.getByteArrayFromHexString(str), TLVParser.getByteArrayFromHexString(str2));
    }

    public void initializeResponsePayload(byte[] bArr, byte[] bArr2) {
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

    public TLVObject getParamObject(String str) {
        return findByTagHexString(str);
    }

    public TLVObject getPayload() {
        return findByTagHexString("82");
    }

    public byte[] getPayloadValue() {
        TLVObject payload = getPayload();
        if (payload != null) {
            return payload.getValueByteArray();
        }
        return null;
    }
}
