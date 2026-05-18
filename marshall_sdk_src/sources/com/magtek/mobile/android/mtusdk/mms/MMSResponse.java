package com.magtek.mobile.android.mtusdk.mms;

import com.magtek.mobile.android.mtusdk.common.TLVObject;
import com.magtek.mobile.android.mtusdk.messages.Message;

/* JADX INFO: loaded from: classes.dex */
public class MMSResponse {
    Message mMessage;

    public MMSResponse() {
        this.mMessage = null;
    }

    public MMSResponse(Message message) {
        this.mMessage = null;
        this.mMessage = message;
    }

    public byte[] getRequestID() {
        Message message = this.mMessage;
        if (message != null) {
            return message.getRequestID();
        }
        return null;
    }

    public TLVObject getStatusCode() {
        Message message = this.mMessage;
        if (message != null) {
            return message.findByTagHexString("82");
        }
        return null;
    }

    public byte[] getStatusCodeValue() {
        TLVObject tLVObjectFindByTagHexString;
        Message message = this.mMessage;
        if (message == null || (tLVObjectFindByTagHexString = message.findByTagHexString("82")) == null) {
            return null;
        }
        return tLVObjectFindByTagHexString.getValueByteArray();
    }

    public byte getOperationStatus() {
        byte[] valueByteArray;
        TLVObject statusCode = getStatusCode();
        if (statusCode == null || (valueByteArray = statusCode.getValueByteArray()) == null || valueByteArray.length <= 0) {
            return (byte) 0;
        }
        return valueByteArray[0];
    }

    public byte[] getResultCode() {
        byte[] valueByteArray;
        byte[] bArr = {0, 0};
        TLVObject statusCode = getStatusCode();
        if (statusCode != null && (valueByteArray = statusCode.getValueByteArray()) != null && valueByteArray.length >= 3) {
            bArr[0] = valueByteArray[1];
            bArr[1] = valueByteArray[2];
        }
        return bArr;
    }

    public byte[] getAdditionalDetails() {
        Message message = this.mMessage;
        TLVObject tLVObjectFindByTagHexString = message != null ? message.findByTagHexString("83") : null;
        if (tLVObjectFindByTagHexString != null) {
            return tLVObjectFindByTagHexString.getValueByteArray();
        }
        return null;
    }

    public byte[] getPayload() {
        Message message = this.mMessage;
        TLVObject payload = message != null ? message.getPayload() : null;
        if (payload != null) {
            return payload.getValueByteArray();
        }
        return null;
    }

    public byte[] getCRC() {
        Message message = this.mMessage;
        TLVObject crc = message != null ? message.getCRC() : null;
        if (crc != null) {
            return crc.getValueByteArray();
        }
        return null;
    }
}
