package com.magtek.mobile.android.mtusdk.mms;

import com.magtek.mobile.android.mtusdk.common.TLVObject;
import com.magtek.mobile.android.mtusdk.messages.Message;

/* JADX INFO: loaded from: classes.dex */
public class MMSNotification {
    protected Message mMessage;

    public MMSNotification() {
        this.mMessage = null;
    }

    public MMSNotification(Message message) {
        this.mMessage = null;
        this.mMessage = message;
    }

    public byte[] getNotificationID() {
        Message message = this.mMessage;
        if (message != null) {
            return message.getRequestID();
        }
        return null;
    }

    public byte[] getNotificationCode() {
        Message message = this.mMessage;
        TLVObject tLVObjectFindByTagHexString = message != null ? message.findByTagHexString("82") : null;
        if (tLVObjectFindByTagHexString != null) {
            return tLVObjectFindByTagHexString.getValueByteArray();
        }
        return null;
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
