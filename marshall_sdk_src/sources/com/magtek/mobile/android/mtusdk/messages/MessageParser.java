package com.magtek.mobile.android.mtusdk.messages;

/* JADX INFO: loaded from: classes.dex */
public class MessageParser {
    public static Message GetMessage(byte[] bArr) {
        int length;
        if (bArr != null && (length = bArr.length) >= 2) {
            byte[] bArr2 = {bArr[0], bArr[1]};
            int i = length - 2;
            if (i > 0) {
                byte[] bArr3 = new byte[i];
                System.arraycopy(bArr, 2, bArr3, 0, i);
                return new Message(bArr2, bArr3);
            }
        }
        return null;
    }

    public static Command GetCommand(byte[] bArr) {
        int length;
        if (bArr != null && (length = bArr.length) >= 2) {
            byte[] bArr2 = {bArr[0], bArr[1]};
            int i = length - 2;
            if (i > 0) {
                byte[] bArr3 = new byte[i];
                System.arraycopy(bArr, 2, bArr3, 0, i);
                return new Command(bArr2, bArr3);
            }
        }
        return null;
    }

    public static ResponsePayload GetResponsePayload(byte[] bArr) {
        int length;
        if (bArr != null && (length = bArr.length) >= 2) {
            byte[] bArr2 = {bArr[0], bArr[1]};
            int i = length - 2;
            if (i > 0) {
                byte[] bArr3 = new byte[i];
                System.arraycopy(bArr, 2, bArr3, 0, i);
                return new ResponsePayload(bArr2, bArr3);
            }
        }
        return null;
    }

    public static NotificationPayload GetNotificationPayload(byte[] bArr) {
        int length;
        if (bArr != null && (length = bArr.length) >= 2) {
            byte[] bArr2 = {bArr[0], bArr[1]};
            int i = length - 2;
            if (i > 0) {
                byte[] bArr3 = new byte[i];
                System.arraycopy(bArr, 2, bArr3, 0, i);
                return new NotificationPayload(bArr2, bArr3);
            }
        }
        return null;
    }
}
