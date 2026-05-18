package com.magtek.mobile.android.mtusdk.mms;

import com.magtek.mobile.android.mtusdk.common.TLVObject;
import com.magtek.mobile.android.mtusdk.messages.Message;

/* JADX INFO: loaded from: classes.dex */
public class MMSDataFile {
    protected Message mMessage;

    public MMSDataFile() {
        this.mMessage = null;
    }

    public MMSDataFile(Message message) {
        this.mMessage = null;
        this.mMessage = message;
    }

    public byte[] getCommandID() {
        return this.mMessage.getRequestID();
    }

    public byte[] getFileType() {
        byte[] valueByteArray;
        byte[] bArr = {0, 0, 0, 0};
        TLVObject msgInfo = this.mMessage.getMsgInfo();
        if (msgInfo != null && (valueByteArray = msgInfo.getValueByteArray()) != null && valueByteArray.length >= 8) {
            bArr[0] = valueByteArray[4];
            bArr[1] = valueByteArray[5];
            bArr[2] = valueByteArray[6];
            bArr[3] = valueByteArray[7];
        }
        return bArr;
    }

    public byte[] getPayload() {
        Message message = this.mMessage;
        TLVObject payload = message != null ? message.getPayload() : null;
        if (payload != null) {
            return payload.getValueByteArray();
        }
        return null;
    }
}
