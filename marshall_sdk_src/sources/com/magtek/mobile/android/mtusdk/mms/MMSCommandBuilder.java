package com.magtek.mobile.android.mtusdk.mms;

import com.magtek.mobile.android.mtusdk.common.TLVObject;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import com.magtek.mobile.android.mtusdk.messages.Command;
import com.magtek.mobile.android.mtusdk.messages.MessageBuilder;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;

/* JADX INFO: loaded from: classes.dex */
public class MMSCommandBuilder {
    public static byte BLOBTYPE_ARQC = 2;
    public static byte BLOBTYPE_BATCH_DATA = 3;
    public static byte BLOBTYPE_CARD_DATA = 1;
    public static String COMMAND_ACTIVATE_DEVICE = "F016";
    public static String COMMAND_CANCEL_TRANSACTION = "1008";
    public static String COMMAND_DISPLAY_MESSAGE = "1803";
    public static String COMMAND_ECHO = "DF01";
    public static String COMMAND_ENABLE_BCR = "1804";
    public static String COMMAND_ESTABLISH_EP_KBPK = "F017";
    public static String COMMAND_GET_CHALLENGE = "E001";
    public static String COMMAND_GET_FILE = "D821";
    public static String COMMAND_GET_FILE_INFO = "D825";
    public static String COMMAND_GET_ITEMS = "D101";
    public static String COMMAND_GET_KEY_SLOT_INFO = "EF11";
    public static String COMMAND_GET_TRANSACTION_DATA = "1011";
    public static String COMMAND_INITIATE_TRANSACTION = "1001";
    public static String COMMAND_LOAD_FW_FILE = "D801";
    public static String COMMAND_PRETTY_PRINT_PAYLOAD = "DF02";
    public static String COMMAND_REPORT_SELECTION = "1802";
    public static String COMMAND_REQUEST_PAN = "1831";
    public static String COMMAND_REQUEST_PIN = "1831";
    public static String COMMAND_REQUEST_SIGNATURE = "1801";
    public static String COMMAND_RESET_DEVICE = "1F01";
    public static String COMMAND_RESUME_TRANSACTION = "1002";
    public static String COMMAND_SECURE_WRAPPER = "EEEE";
    public static String COMMAND_SEND_FILE = "D811";
    public static String COMMAND_SEND_FILE_UNSECURED = "D812";
    public static String COMMAND_SET_ITEMS = "D111";
    public static String COMMAND_SHOW_BITMAP_IMAGE = "1823";
    public static String COMMAND_SHOW_IMAGE = "1821";
    public static String COMMAND_SHOW_QR_CODE = "1822";
    public static String COMMAND_TR31_KEY_INJECTION = "EF01";
    public static String COMMAND_UPDATE_FW_FROM_FILE = "D901";
    public static String COMMAND_WRITE_OTP = "F013";
    public static byte NOTIFICATION_EVENT_COMPLETED = 1;
    public static byte NOTIFICATION_EVENT_HALTED = 2;
    public static byte NOTIFICATION_EVENT_PAUSED = 3;
    public static byte NOTIFICATION_REASON_CARD_SWIPE = 1;
    public static byte NOTIFICATION_REASON_EMV_ARQC = 2;
    public static byte NOTIFICATION_REASON_EMV_BATCH = 3;
    public static byte NOTIFICATION_SECTION_OPERATION = 1;
    public static byte NOTIFICATION_TYPE_TRANSACTION = 16;

    public static Command InitiateTransactionCommand0(byte b, byte b2, byte b3, byte b4) {
        byte[] bArr = {b2, b3, b4};
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_INITIATE_TRANSACTION);
        commandBuildCommand.addParam((byte) -127, b);
        commandBuildCommand.addParam((byte) -126, bArr);
        return commandBuildCommand;
    }

    public static Command InitiateTransactionCommand1(byte b, byte b2, byte b3, byte b4, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_INITIATE_TRANSACTION);
        commandBuildCommand.addParam((byte) -126, b);
        TLVObject tLVObject = new TLVObject((byte) -93);
        tLVObject.addTLVObject(new TLVObject((byte) -127, b2));
        tLVObject.addTLVObject(new TLVObject((byte) -126, b3));
        tLVObject.addTLVObject(new TLVObject((byte) -125, b4));
        if (bArr != null) {
            tLVObject.addTLVObject(new TLVObject((byte) -124, bArr));
        }
        commandBuildCommand.addParam(tLVObject);
        commandBuildCommand.addParam((byte) -124, bArr2);
        commandBuildCommand.addParam((byte) -122, bArr3);
        return commandBuildCommand;
    }

    public static Command ResumeTransactionCommand(byte[] bArr) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_RESUME_TRANSACTION);
        commandBuildCommand.addParam((byte) -127, (byte) 0);
        commandBuildCommand.addParam((byte) -126, (byte) 30);
        commandBuildCommand.addParam((byte) -124, bArr);
        return commandBuildCommand;
    }

    public static Command CancelTransactionCommand() {
        return MessageBuilder.BuildCommand(COMMAND_CANCEL_TRANSACTION);
    }

    public static Command GetTransactionDataCommand(byte b) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_GET_TRANSACTION_DATA);
        commandBuildCommand.addParam((byte) -127, b);
        return commandBuildCommand;
    }

    public static Command requestPINCommand(byte b, byte b2, byte b3, byte b4, byte[] bArr, byte b5) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_REQUEST_PIN);
        commandBuildCommand.addParam((byte) -127, b);
        commandBuildCommand.addParam((byte) -126, b2);
        commandBuildCommand.addParam((byte) -125, new byte[]{b3, b4});
        commandBuildCommand.addParam((byte) -124, bArr);
        commandBuildCommand.addParam((byte) -123, b5);
        return commandBuildCommand;
    }

    public static Command requestPANCommand(byte b, byte b2, byte b3, byte b4) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_REQUEST_PAN);
        commandBuildCommand.addParam((byte) -127, b);
        TLVObject tLVObject = new TLVObject((byte) -93);
        tLVObject.addTLVObject(new TLVObject((byte) -127, b2));
        tLVObject.addTLVObject(new TLVObject((byte) -126, b3));
        tLVObject.addTLVObject(new TLVObject((byte) -125, b4));
        commandBuildCommand.addParam(tLVObject);
        return commandBuildCommand;
    }

    public static Command requestPANCommand(byte b, byte b2, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_REQUEST_PAN);
        commandBuildCommand.addParam((byte) -127, b);
        TLVObject tLVObject = new TLVObject((byte) -93);
        tLVObject.addTLVObject(new TLVObject((byte) -127, b2));
        tLVObject.addTLVObject(new TLVObject((byte) -126, b3));
        tLVObject.addTLVObject(new TLVObject((byte) -125, b4));
        commandBuildCommand.addParam(tLVObject);
        TLVObject tLVObject2 = new TLVObject((byte) -92);
        tLVObject.addTLVObject(new TLVObject((byte) -126, b5));
        tLVObject.addTLVObject(new TLVObject((byte) -125, new byte[]{b6, b7}));
        tLVObject.addTLVObject(new TLVObject((byte) -123, b8));
        commandBuildCommand.addParam(tLVObject2);
        return commandBuildCommand;
    }

    public static Command RequestSignatureCommand(byte b) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_REQUEST_SIGNATURE);
        commandBuildCommand.addParam((byte) -127, b);
        return commandBuildCommand;
    }

    public static Command ReportSelection(byte b, byte b2) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_REPORT_SELECTION);
        commandBuildCommand.addParam((byte) -127, b);
        commandBuildCommand.addParam((byte) -126, b2);
        return commandBuildCommand;
    }

    public static Command DisplayMessageCommand(byte b, byte b2) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_DISPLAY_MESSAGE);
        commandBuildCommand.addParam((byte) -127, b2);
        commandBuildCommand.addParam((byte) -126, b);
        return commandBuildCommand;
    }

    public static Command ShowImageCommand(byte b, byte b2) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_SHOW_IMAGE);
        commandBuildCommand.addParam((byte) -127, b);
        commandBuildCommand.addParam((byte) -125, b2);
        return commandBuildCommand;
    }

    public static Command ShowBitmapImageCommand(byte[] bArr, byte b, byte[] bArr2) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_SHOW_BITMAP_IMAGE);
        commandBuildCommand.addParam((byte) -127, b);
        if (bArr2 != null) {
            commandBuildCommand.addParam((byte) -126, bArr2);
        }
        commandBuildCommand.addParam((byte) -123, bArr);
        return commandBuildCommand;
    }

    public static Command ShowQRCode(byte[] bArr, byte b, byte b2, byte b3, byte b4, byte b5, byte[] bArr2, byte[] bArr3, byte[] bArr4) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_SHOW_QR_CODE);
        commandBuildCommand.addParam((byte) -127, b);
        commandBuildCommand.addParam((byte) -126, bArr);
        commandBuildCommand.addParam((byte) -125, b2);
        commandBuildCommand.addParam((byte) -124, b3);
        commandBuildCommand.addParam((byte) -123, b4);
        commandBuildCommand.addParam((byte) -122, b5);
        commandBuildCommand.addParam((byte) -121, bArr2);
        commandBuildCommand.addParam((byte) -120, bArr3);
        if (bArr4 != null) {
            commandBuildCommand.addParam(PPSCRADeviceValues.ACK_STATUS_BAD_AUTH, bArr4);
        }
        return commandBuildCommand;
    }

    public static Command EnableBarCodeReader(byte b, byte b2) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_ENABLE_BCR);
        commandBuildCommand.addParam((byte) -127, (byte) 1);
        commandBuildCommand.addParam((byte) -126, b);
        commandBuildCommand.addParam((byte) -125, b2);
        return commandBuildCommand;
    }

    public static Command EnableBarCodeReader(byte b, byte b2, byte[] bArr) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_ENABLE_BCR);
        commandBuildCommand.addParam((byte) -127, (byte) 1);
        commandBuildCommand.addParam((byte) -126, b);
        commandBuildCommand.addParam((byte) -125, b2);
        if (bArr != null) {
            commandBuildCommand.addParam((byte) -124, bArr);
        }
        return commandBuildCommand;
    }

    public static Command DisableBarCodeReader() {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_ENABLE_BCR);
        commandBuildCommand.addParam((byte) -127, (byte) 0);
        return commandBuildCommand;
    }

    public static Command ResetDeviceCommand() {
        return MessageBuilder.BuildCommand(COMMAND_RESET_DEVICE);
    }

    public static Command GetItemsCommand(byte b, byte[] bArr) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_GET_ITEMS);
        commandBuildCommand.addParam((byte) -127, TLVParser.getByteArrayFromHexString("2B06010401F609"));
        commandBuildCommand.addParam((byte) -123, b);
        commandBuildCommand.addParam(PPSCRADeviceValues.ACK_STATUS_BAD_AUTH, bArr);
        return commandBuildCommand;
    }

    public static Command SetItemsCommand(byte b, byte[] bArr) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_SET_ITEMS);
        commandBuildCommand.addParam((byte) -127, TLVParser.getByteArrayFromHexString("2B06010401F609"));
        commandBuildCommand.addParam((byte) -123, b);
        commandBuildCommand.addParam(PPSCRADeviceValues.ACK_STATUS_BAD_AUTH, bArr);
        return commandBuildCommand;
    }

    public static Command GetItemsCommand(byte b, String str) {
        return GetItemsCommand(b, TLVParser.getByteArrayFromHexString(str));
    }

    public static Command LoadFirmwareCommand(byte b, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_LOAD_FW_FILE);
        commandBuildCommand.addParam((byte) -127, b);
        commandBuildCommand.addParam((byte) -123, bArr);
        commandBuildCommand.addParam((byte) -122, bArr2);
        commandBuildCommand.addParam((byte) -121, bArr3);
        return commandBuildCommand;
    }

    public static Command LoadFirmwareCommand(byte b, String str, String str2, String str3) {
        return LoadFirmwareCommand(b, TLVParser.getByteArrayFromHexString(str), TLVParser.getByteArrayFromHexString(str2), TLVParser.getByteArrayFromHexString(str3));
    }

    public static Command UpdateFirmwareFromFileCommand(byte b, byte b2, byte[] bArr, byte[] bArr2) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_UPDATE_FW_FROM_FILE);
        commandBuildCommand.addParam((byte) -127, b);
        commandBuildCommand.addParam((byte) -126, b2);
        commandBuildCommand.addParam((byte) -123, bArr);
        commandBuildCommand.addParam((byte) -122, bArr2);
        return commandBuildCommand;
    }

    public static Command UpdateFirmwareFromFileCommand(byte b, byte b2, String str, String str2) {
        return UpdateFirmwareFromFileCommand(b, b2, TLVParser.getByteArrayFromHexString(str), TLVParser.getByteArrayFromHexString(str2));
    }

    public static Command GetFileCommand(byte[] bArr) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_GET_FILE);
        commandBuildCommand.addParam((byte) -127, bArr);
        return commandBuildCommand;
    }

    public static Command SendFileCommand(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_SEND_FILE);
        commandBuildCommand.addParam((byte) -127, bArr);
        byte[] lengthArray = getLengthArray(4, bArr3.length);
        TLVObject tLVObject = new TLVObject((byte) -94);
        tLVObject.addTLVObject(new TLVObject((byte) -127, lengthArray));
        tLVObject.addTLVObject(new TLVObject((byte) -126, new byte[]{4}));
        tLVObject.addTLVObject(new TLVObject((byte) -125, bArr2));
        commandBuildCommand.addParam(tLVObject);
        TLVObject tLVObject2 = new TLVObject((byte) -93);
        tLVObject2.addTLVObject(new TLVObject((byte) -127, TLVParser.getHexString(bArr).getBytes()));
        commandBuildCommand.addParam(tLVObject2);
        commandBuildCommand.addParam((byte) -121, (byte) 1);
        return commandBuildCommand;
    }

    public static Command SendFileCommand(String str, String str2, String str3) {
        return SendFileCommand(TLVParser.getByteArrayFromHexString(str), TLVParser.getByteArrayFromHexString(str2), TLVParser.getByteArrayFromHexString(str3));
    }

    protected static byte[] getLengthArray(int i, int i2) {
        byte[] bArr = new byte[i];
        int i3 = i;
        for (int i4 = 0; i4 < i; i4++) {
            i3--;
            bArr[i4] = (byte) ((i2 >> (i3 * 8)) & 255);
        }
        return bArr;
    }

    public static Command SendFileUnsecuredCommand(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_SEND_FILE_UNSECURED);
        commandBuildCommand.addParam((byte) -127, bArr);
        byte[] lengthArray = getLengthArray(4, bArr3.length);
        TLVObject tLVObject = new TLVObject((byte) -94);
        tLVObject.addTLVObject(new TLVObject((byte) -127, lengthArray));
        tLVObject.addTLVObject(new TLVObject((byte) -126, new byte[]{4}));
        tLVObject.addTLVObject(new TLVObject((byte) -125, bArr2));
        commandBuildCommand.addParam(tLVObject);
        TLVObject tLVObject2 = new TLVObject((byte) -93);
        tLVObject2.addTLVObject(new TLVObject((byte) -127, TLVParser.getHexString(bArr).getBytes()));
        commandBuildCommand.addParam(tLVObject2);
        commandBuildCommand.addParam((byte) -121, (byte) 1);
        return commandBuildCommand;
    }

    public static Command SendFileUnsecuredCommand(String str, String str2, String str3) {
        return SendFileUnsecuredCommand(TLVParser.getByteArrayFromHexString(str), TLVParser.getByteArrayFromHexString(str2), TLVParser.getByteArrayFromHexString(str3));
    }

    public static Command EchoCommand(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_ECHO);
        commandBuildCommand.addParam((byte) -127, bArr);
        if (bArr2 != null || bArr3 != null) {
            TLVObject tLVObject = new TLVObject((byte) -94);
            if (bArr2 != null) {
                tLVObject.addTLVObject(new TLVObject((byte) -127, bArr2));
            }
            if (bArr3 != null) {
                tLVObject.addTLVObject(new TLVObject((byte) -126, bArr3));
            }
            commandBuildCommand.addParam(tLVObject);
        }
        return commandBuildCommand;
    }

    public static Command EchoCommand(String str, byte[] bArr, byte[] bArr2) {
        return EchoCommand(TLVParser.getByteArrayFromHexString(str), bArr, bArr2);
    }

    public static Command PrettyPrintPayloadCommand(byte[] bArr) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_PRETTY_PRINT_PAYLOAD);
        commandBuildCommand.addParam((byte) -124, bArr);
        return commandBuildCommand;
    }

    public static Command PrettyPrintPayloadCommand(String str) {
        return PrettyPrintPayloadCommand(TLVParser.getByteArrayFromHexString(str));
    }

    public static Command GetChallengeCommand(byte[] bArr) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_GET_CHALLENGE);
        commandBuildCommand.addParam((byte) -127, bArr);
        return commandBuildCommand;
    }

    public static Command GetChallengeCommand(String str) {
        return GetChallengeCommand(TLVParser.getByteArrayFromHexString(str));
    }

    public static Command SecureWrapperCommand(byte[] bArr, byte[] bArr2, byte[] bArr3, byte[] bArr4, byte[] bArr5) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_SECURE_WRAPPER);
        TLVObject tLVObject = new TLVObject((byte) -95);
        tLVObject.addTLVObject(new TLVObject((byte) -127, bArr2));
        tLVObject.addTLVObject(new TLVObject((byte) -124, (byte[]) null));
        tLVObject.addTLVObject(new TLVObject((byte) -123, (byte[]) null));
        TLVObject tLVObject2 = new TLVObject(PPSCRADeviceValues.EMV_COMMAND_GET_KERNEL_INFO);
        tLVObject2.addTLVObject(new TLVObject((byte) -127, bArr));
        tLVObject2.addTLVObject(new TLVObject((byte) -126, (byte[]) null));
        tLVObject2.addTLVObject(new TLVObject((byte) -122, (byte[]) null));
        tLVObject2.addTLVObject(new TLVObject((byte) -120, (byte[]) null));
        tLVObject.addTLVObject(tLVObject2);
        tLVObject.addTLVObject(new TLVObject((byte) -87));
        commandBuildCommand.addParam(tLVObject);
        commandBuildCommand.addParam((byte) -126, bArr3);
        commandBuildCommand.addParam((byte) -125, bArr4);
        commandBuildCommand.addParam((byte) -124, bArr5);
        return commandBuildCommand;
    }

    public static Command SecureWrapperCommand(String str, String str2, String str3, String str4, byte[] bArr) {
        return SecureWrapperCommand(TLVParser.getByteArrayFromHexString(str), TLVParser.getByteArrayFromHexString(str2), TLVParser.getByteArrayFromHexString(str3), TLVParser.getByteArrayFromHexString(str4), bArr);
    }

    public static void AddSignatureParam(Command command, byte[] bArr) {
        command.addParam((byte) -98, bArr);
    }

    public static void AddSignatureParam(Command command, String str) {
        AddSignatureParam(command, TLVParser.getByteArrayFromHexString(str));
    }

    public static Command TR31KeyInjectionCommand(byte[] bArr) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_TR31_KEY_INJECTION);
        commandBuildCommand.addParam((byte) -124, bArr);
        return commandBuildCommand;
    }

    public static Command TR31KeyInjectionCommand(String str) {
        return TR31KeyInjectionCommand(TLVParser.getByteArrayFromHexString(str));
    }

    public static Command GetKeySlotInfoCommand(byte[] bArr) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_GET_KEY_SLOT_INFO);
        commandBuildCommand.addParam((byte) -127, bArr);
        return commandBuildCommand;
    }

    public static Command GetKeySlotInfoCommand(String str) {
        return GetKeySlotInfoCommand(TLVParser.getByteArrayFromHexString(str));
    }

    public static Command WriteOTPCommand() {
        return MessageBuilder.BuildCommand(COMMAND_WRITE_OTP);
    }

    public static Command ActivateDeviceCommand(byte[] bArr) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_ACTIVATE_DEVICE);
        commandBuildCommand.addParam((byte) -127, bArr);
        return commandBuildCommand;
    }

    public static Command ActivateDeviceCommand(String str) {
        return ActivateDeviceCommand(TLVParser.getByteArrayFromHexString(str));
    }

    public static Command EstablishEPKBPKCommand(byte[] bArr, byte[] bArr2) {
        Command commandBuildCommand = MessageBuilder.BuildCommand(COMMAND_ESTABLISH_EP_KBPK);
        TLVObject tLVObject = new TLVObject((byte) -95);
        tLVObject.addTLVObject(new TLVObject((byte) -127, (byte) 0));
        tLVObject.addTLVObject(new TLVObject((byte) -126, (byte) 0));
        commandBuildCommand.addParam(tLVObject);
        commandBuildCommand.addParam((byte) -125, bArr);
        commandBuildCommand.addParam((byte) -124, bArr2);
        return commandBuildCommand;
    }

    public static Command EstablishEPKBPKCommand(String str, String str2) {
        return EstablishEPKBPKCommand(TLVParser.getByteArrayFromHexString(str), TLVParser.getByteArrayFromHexString(str2));
    }
}
