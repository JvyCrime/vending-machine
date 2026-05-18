package com.magtek.mobile.android.mtusdk.messages;

import com.magtek.mobile.android.mtusdk.common.TLVObject;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;

/* JADX INFO: loaded from: classes.dex */
public class MessageBuilder {
    static byte API_VER;

    public static Message BuildMessage() {
        return new Message(new byte[]{PPSCRADeviceValues.EMV_COMMAND_CONFIRM_SESSION_KEY, API_VER}, null);
    }

    public static Message BuildMessage(byte[] bArr) {
        return new Message(new byte[]{PPSCRADeviceValues.EMV_COMMAND_CONFIRM_SESSION_KEY, API_VER}, bArr);
    }

    public static Message BuildMessage(String str) {
        return BuildMessage(TLVParser.getByteArrayFromHexString(str));
    }

    public static Command BuildCommand(byte[] bArr) {
        return new Command(bArr, (byte[]) null);
    }

    public static Command BuildCommand(String str) {
        return BuildCommand(TLVParser.getByteArrayFromHexString(str));
    }

    public static TLVObject BuildMessageInfoForCommand(byte[] bArr) {
        return BuildMessageInfoForCommand(TLVParser.getHexString(bArr));
    }

    public static TLVObject BuildMessageInfoForCommand(String str) {
        return new TLVObject("81", "0100" + str);
    }

    public static TLVObject BuildMessageInfoForDataFile(byte[] bArr, byte[] bArr2) {
        return BuildMessageInfoForDataFile(TLVParser.getHexString(bArr), TLVParser.getHexString(bArr2));
    }

    public static TLVObject BuildMessageInfoForDataFile(String str, String str2) {
        return new TLVObject("81", "0400" + str + str2);
    }

    public static TLVObject BuildMessagePayload(byte[] bArr) {
        return new TLVObject(TLVParser.getByteArrayFromHexString("84"), bArr);
    }

    public static TLVObject BuildMessagePayload(String str) {
        return BuildMessagePayload(TLVParser.getByteArrayFromHexString(str));
    }
}
