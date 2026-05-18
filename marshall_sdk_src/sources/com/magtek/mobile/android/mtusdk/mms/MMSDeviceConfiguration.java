package com.magtek.mobile.android.mtusdk.mms;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.magtek.mobile.android.mtusdk.BaseDeviceConfiguration;
import com.magtek.mobile.android.mtusdk.IConfigurationCallback;
import com.magtek.mobile.android.mtusdk.IData;
import com.magtek.mobile.android.mtusdk.IResult;
import com.magtek.mobile.android.mtusdk.InfoType;
import com.magtek.mobile.android.mtusdk.Result;
import com.magtek.mobile.android.mtusdk.StatusCode;
import com.magtek.mobile.android.mtusdk.common.TLVObject;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import com.magtek.mobile.android.mtusdk.messages.Command;
import com.magtek.mobile.android.mtusdk.messages.Message;
import com.magtek.mobile.android.mtusdk.messages.MessageBuilder;
import com.magtek.mobile.android.mtusdk.messages.MessageParser;
import com.magtek.mobile.android.mtusdk.messages.ResponsePayload;
import com.magtek.mobile.android.mtusdk.mmx.MMXDevice;
import com.magtek.mobile.android.mtusdk.mmx.MMXMessage;
import java.security.MessageDigest;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MMSDeviceConfiguration extends BaseDeviceConfiguration {
    private static int COMMAND_RESPONSE_TIMEOUT = 2000;
    private static int KEY_UPDATE_COMMAND_RESPONSE_TIMEOUT = 15000;
    private Object mCommitFirmwareEvent;
    private Object mLoadFirmwareEvent;
    private MMXDevice mMMXDevice;
    private Object mResponseEvent;
    private byte mResponseOperationStatus = -1;
    private ResponsePayload mResponsePayload = null;
    private String mDeviceSerial = "";
    private String mChallengeToken = "";
    private IConfigurationCallback mConfigurationCallback = null;
    private byte mConfigType = 0;
    private byte mKeyInfoType = 0;
    private int mFirmwareType = 0;
    private byte[] mData = null;
    private byte[] mFileID = null;
    private int mProgressValue = 0;
    private boolean mLoadFirmwareResult = false;
    private boolean mCommitFirmwareResult = false;

    public MMSDeviceConfiguration(MMXDevice mMXDevice) {
        this.mMMXDevice = null;
        this.mMMXDevice = mMXDevice;
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtusdk.mms.MMSDeviceConfiguration$7, reason: invalid class name */
    static /* synthetic */ class AnonymousClass7 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtusdk$InfoType;

        static {
            int[] iArr = new int[InfoType.values().length];
            $SwitchMap$com$magtek$mobile$android$mtusdk$InfoType = iArr;
            try {
                iArr[InfoType.DeviceSerialNumber.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$InfoType[InfoType.FirmwareVersion.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$InfoType[InfoType.DeviceCapabilities.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$InfoType[InfoType.Boot1Version.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$InfoType[InfoType.Boot0Version.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$InfoType[InfoType.FirmwareHash.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$InfoType[InfoType.TamperStatus.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$InfoType[InfoType.OperationStatus.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$InfoType[InfoType.OfflineDetail.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceConfiguration, com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public String getDeviceInfo(InfoType infoType) {
        switch (AnonymousClass7.$SwitchMap$com$magtek$mobile$android$mtusdk$InfoType[infoType.ordinal()]) {
            case 1:
                return getDeviceSerial();
            case 2:
                return getFirmwareVersion();
            case 3:
                return getDeviceCapabilities();
            case 4:
                return getBoot1Version();
            case 5:
                return getBoot0Version();
            case 6:
                return getFirmwareHash();
            case 7:
                return getTamperStatus();
            case 8:
                return getOperationStatus();
            case 9:
                return getOfflineDetail();
            default:
                return "";
        }
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceConfiguration, com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public byte[] getConfigInfo(byte b, byte[] bArr) {
        return getItemsValue(b, bArr);
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceConfiguration, com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public byte[] getKeyInfo(byte b, byte[] bArr) {
        this.mKeyInfoType = b;
        ResponsePayload responsePayloadSendCommandAndReceive = sendCommandAndReceive(MMSCommandBuilder.GetKeySlotInfoCommand(bArr));
        if (responsePayloadSendCommandAndReceive != null) {
            return responsePayloadSendCommandAndReceive.getValueByteArray();
        }
        return null;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceConfiguration, com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public int updateKeyInfo(byte b, byte[] bArr, IConfigurationCallback iConfigurationCallback) {
        this.mKeyInfoType = b;
        this.mConfigurationCallback = iConfigurationCallback;
        this.mData = (byte[]) bArr.clone();
        new Thread() { // from class: com.magtek.mobile.android.mtusdk.mms.MMSDeviceConfiguration.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                MMSDeviceConfiguration.this.sendCallbackOnResult(MMSDeviceConfiguration.this.sendCommandSync(MMSCommandBuilder.TR31KeyInjectionCommand(MMSDeviceConfiguration.this.mData), MMSDeviceConfiguration.KEY_UPDATE_COMMAND_RESPONSE_TIMEOUT), null);
            }
        }.start();
        return 0;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceConfiguration, com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public byte[] getChallengeToken(byte[] bArr) {
        if (executeGetChallege(bArr)) {
            return TLVParser.getByteArrayFromHexString(this.mChallengeToken);
        }
        return null;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceConfiguration, com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public int setConfigInfo(byte b, byte[] bArr, IConfigurationCallback iConfigurationCallback) {
        this.mConfigType = b;
        this.mConfigurationCallback = iConfigurationCallback;
        this.mData = (byte[]) bArr.clone();
        new Thread() { // from class: com.magtek.mobile.android.mtusdk.mms.MMSDeviceConfiguration.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                StatusCode statusCode = StatusCode.ERROR;
                MMSDeviceConfiguration mMSDeviceConfiguration = MMSDeviceConfiguration.this;
                byte[] itemsData = mMSDeviceConfiguration.setItemsData(mMSDeviceConfiguration.mConfigType, MMSDeviceConfiguration.this.mData);
                if (itemsData != null) {
                    statusCode = StatusCode.SUCCESS;
                }
                MMSDeviceConfiguration.this.sendCallbackOnResult(statusCode, itemsData);
            }
        }.start();
        return 0;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceConfiguration, com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public int updateFirmware(int i, byte[] bArr, IConfigurationCallback iConfigurationCallback) {
        this.mFirmwareType = i;
        this.mData = (byte[]) bArr.clone();
        this.mConfigurationCallback = iConfigurationCallback;
        this.mProgressValue = -1;
        new Thread() { // from class: com.magtek.mobile.android.mtusdk.mms.MMSDeviceConfiguration.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() throws Throwable {
                MMSDeviceConfiguration.this.sendAndUpdateFirmware();
            }
        }.start();
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0111  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void sendAndUpdateFirmware() throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 287
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.magtek.mobile.android.mtusdk.mms.MMSDeviceConfiguration.sendAndUpdateFirmware():void");
    }

    protected byte[] sendFileWithProgress(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        Message messageBuildMessage = MessageBuilder.BuildMessage();
        messageBuildMessage.addMessageInfoForDataFile(bArr, bArr2);
        messageBuildMessage.addPayload(bArr3);
        byte[] byteArray = messageBuildMessage.getByteArray();
        if (byteArray != null) {
            return this.mMMXDevice.sendAndReceive(byteArray, 60000, new Handler(Looper.getMainLooper(), new ProgressHandlerCallback()));
        }
        return null;
    }

    protected byte[] sendFirmwareWithProgress(Command command) {
        Message messageBuildMessage = MessageBuilder.BuildMessage();
        messageBuildMessage.addMessageInfoForCommand(command.getTagByteArray());
        messageBuildMessage.addPayload(command.getByteArray());
        byte[] byteArray = messageBuildMessage.getByteArray();
        Log.d("UpdateFirmware", "sendFirmwareWithProgress" + byteArray.length);
        if (byteArray != null) {
            return this.mMMXDevice.sendAndReceive(byteArray, 60000, new Handler(Looper.getMainLooper(), new ProgressHandlerCallback()));
        }
        return null;
    }

    protected byte[] sendAndReceiveCommand(Command command) {
        Message messageBuildMessage = MessageBuilder.BuildMessage();
        messageBuildMessage.addMessageInfoForCommand(command.getTagByteArray());
        messageBuildMessage.addPayload(command.getByteArray());
        return sendAndReceiveMessage(messageBuildMessage);
    }

    protected byte[] sendAndReceiveSecuredCommand(byte[] bArr, byte[] bArr2) {
        Message messageBuildMessage = MessageBuilder.BuildMessage();
        messageBuildMessage.addMessageInfoForCommand(bArr);
        messageBuildMessage.addPayload(bArr2);
        return sendAndReceiveMessage(messageBuildMessage);
    }

    protected byte[] sendAndReceiveMessage(Message message) {
        byte[] byteArray = message.getByteArray();
        if (byteArray != null) {
            return this.mMMXDevice.sendAndReceive(byteArray, 5000, null);
        }
        return null;
    }

    private class ProgressHandlerCallback implements Handler.Callback {
        private ProgressHandlerCallback() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(android.os.Message message) {
            try {
                MMSDeviceConfiguration.this.updateProgress(message.what);
                return true;
            } catch (Exception unused) {
                return true;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateProgress(int i) {
        int i2 = this.mProgressValue;
        if (i >= i2 + 1) {
            if (i > i2) {
                try {
                    this.mProgressValue = i;
                } catch (Exception unused) {
                    return;
                }
            }
            sendCallbackOnProgress(this.mProgressValue);
        }
    }

    protected void sendCallbackOnProgress(int i) {
        try {
            IConfigurationCallback iConfigurationCallback = this.mConfigurationCallback;
            if (iConfigurationCallback != null) {
                iConfigurationCallback.OnProgress(i);
            }
        } catch (Exception unused) {
        }
    }

    protected void sendCallbackOnResult(StatusCode statusCode, byte[] bArr) {
        try {
            IConfigurationCallback iConfigurationCallback = this.mConfigurationCallback;
            if (iConfigurationCallback != null) {
                iConfigurationCallback.OnResult(statusCode, bArr);
            }
        } catch (Exception unused) {
        }
    }

    protected IResult sendCallbackOnCalculateMAC(byte b, byte[] bArr) {
        Result result = new Result(StatusCode.UNAVAILABLE);
        try {
            IConfigurationCallback iConfigurationCallback = this.mConfigurationCallback;
            return iConfigurationCallback != null ? iConfigurationCallback.OnCalculateMAC(b, bArr) : result;
        } catch (Exception unused) {
            return result;
        }
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceConfiguration, com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public int getFile(byte[] bArr, IConfigurationCallback iConfigurationCallback) {
        this.mFileID = (byte[]) bArr.clone();
        this.mConfigurationCallback = iConfigurationCallback;
        this.mProgressValue = -1;
        new Thread() { // from class: com.magtek.mobile.android.mtusdk.mms.MMSDeviceConfiguration.4
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                MMSDeviceConfiguration.this.prepareAndGetFile();
            }
        }.start();
        return 0;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceConfiguration, com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public int sendFile(byte[] bArr, byte[] bArr2, IConfigurationCallback iConfigurationCallback) {
        this.mFileID = (byte[]) bArr.clone();
        this.mData = (byte[]) bArr2.clone();
        this.mConfigurationCallback = iConfigurationCallback;
        this.mProgressValue = -1;
        new Thread() { // from class: com.magtek.mobile.android.mtusdk.mms.MMSDeviceConfiguration.5
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                MMSDeviceConfiguration.this.prepareAndSendFile();
            }
        }.start();
        return 0;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceConfiguration, com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public int sendSecureFile(byte[] bArr, byte[] bArr2, IConfigurationCallback iConfigurationCallback) {
        this.mFileID = (byte[]) bArr.clone();
        this.mData = (byte[]) bArr2.clone();
        this.mConfigurationCallback = iConfigurationCallback;
        this.mProgressValue = -1;
        new Thread() { // from class: com.magtek.mobile.android.mtusdk.mms.MMSDeviceConfiguration.6
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                MMSDeviceConfiguration.this.prepareAndSendSecureFile();
            }
        }.start();
        return 0;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceConfiguration, com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public int sendImage(byte b, byte[] bArr, IConfigurationCallback iConfigurationCallback) {
        return sendFile(new byte[]{2, 0, 0, (byte) (b - 1)}, bArr, iConfigurationCallback);
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceConfiguration, com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public int setDisplayImage(byte b) {
        return setItemsData((byte) 1, new byte[]{-30, 9, -29, 7, -31, 5, -31, 3, -63, 1, b}) != null ? 0 : -1;
    }

    protected void prepareAndGetFile() {
        byte[] bArr = this.mFileID;
        if (bArr != null && bArr.length >= 4) {
            try {
                updateProgress(2);
                ResponsePayload responsePayloadSendCommandAndReceive = sendCommandAndReceive(MMSCommandBuilder.GetFileCommand(this.mFileID));
                if (responsePayloadSendCommandAndReceive != null) {
                    TLVParser.getHexString(responsePayloadSendCommandAndReceive.getParamValue("81"));
                    TLVObject paramObject = responsePayloadSendCommandAndReceive.getParamObject("A2");
                    if (paramObject != null) {
                        paramObject.findByTagHexString("81").getValueByteArray();
                        paramObject.findByTagHexString("82").getValueByteArray();
                        paramObject.findByTagHexString("83").getValueByteArray();
                        updateProgress(10);
                        return;
                    }
                }
            } catch (Exception unused) {
            }
            sendCallbackOnResult(StatusCode.ERROR, null);
            return;
        }
        sendCallbackOnResult(StatusCode.UNAVAILABLE, null);
    }

    protected void prepareAndSendFile() {
        Message messageGetMessage;
        byte[] bArr = this.mData;
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        try {
            updateProgress(2);
            Command commandSendFileUnsecuredCommand = MMSCommandBuilder.SendFileUnsecuredCommand(this.mFileID, MessageDigest.getInstance("SHA-256").digest(this.mData), this.mData);
            boolean z = true;
            if (sendCommandSync(commandSendFileUnsecuredCommand, COMMAND_RESPONSE_TIMEOUT) == StatusCode.SUCCESS) {
                updateProgress(4);
                byte[] bArrSendFileWithProgress = sendFileWithProgress(commandSendFileUnsecuredCommand.getTagByteArray(), this.mFileID, this.mData);
                if (bArrSendFileWithProgress == null || (messageGetMessage = MessageParser.GetMessage(bArrSendFileWithProgress)) == null || !messageGetMessage.isResponse() || new MMSResponse(messageGetMessage).getOperationStatus() != 0) {
                    z = false;
                }
                if (z) {
                    updateProgress(100);
                    sendCallbackOnResult(StatusCode.SUCCESS, null);
                    return;
                } else {
                    sendCallbackOnResult(StatusCode.ERROR, null);
                    return;
                }
            }
            sendCallbackOnResult(StatusCode.ERROR, null);
        } catch (Exception e) {
            Log.d("Exception", e.getMessage());
        }
    }

    protected void prepareAndSendSecureFile() {
        Message messageGetMessage;
        Message messageGetMessage2;
        IData iDataData;
        byte[] bArr = this.mData;
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        try {
            boolean z = false;
            if (!executeGetChallege(new byte[]{-33, 0})) {
                sendCallbackOnResult(StatusCode.ERROR, null);
                return;
            }
            updateProgress(2);
            Command commandSendFileCommand = MMSCommandBuilder.SendFileCommand(this.mFileID, MessageDigest.getInstance("SHA-256").digest(this.mData), this.mData);
            Command commandSecureWrapperCommand = MMSCommandBuilder.SecureWrapperCommand("1111", "0303060208", this.mDeviceSerial, this.mChallengeToken, commandSendFileCommand.getByteArray());
            byte[] byteArray = commandSecureWrapperCommand.getByteArray();
            if (byteArray != null) {
                IResult iResultSendCallbackOnCalculateMAC = sendCallbackOnCalculateMAC((byte) 0, byteArray);
                byte[] bArrByteArray = (iResultSendCallbackOnCalculateMAC.Status() != StatusCode.SUCCESS || (iDataData = iResultSendCallbackOnCalculateMAC.Data()) == null) ? null : iDataData.ByteArray();
                if (bArrByteArray != null) {
                    commandSecureWrapperCommand.addParam((byte) -98, bArrByteArray);
                }
            }
            byte[] tagByteArray = commandSendFileCommand.getTagByteArray();
            byte[] bArrSendAndReceiveSecuredCommand = sendAndReceiveSecuredCommand(tagByteArray, commandSecureWrapperCommand.getByteArray());
            if (bArrSendAndReceiveSecuredCommand != null && (messageGetMessage2 = MessageParser.GetMessage(bArrSendAndReceiveSecuredCommand)) != null && messageGetMessage2.isResponse() && new MMSResponse(messageGetMessage2).getOperationStatus() == 0) {
                updateProgress(4);
                byte[] bArrSendFileWithProgress = sendFileWithProgress(tagByteArray, this.mFileID, this.mData);
                if (bArrSendFileWithProgress != null && (messageGetMessage = MessageParser.GetMessage(bArrSendFileWithProgress)) != null && messageGetMessage.isResponse() && new MMSResponse(messageGetMessage).getOperationStatus() == 0) {
                    z = true;
                }
                if (z) {
                    updateProgress(100);
                    sendCallbackOnResult(StatusCode.SUCCESS, null);
                    return;
                } else {
                    sendCallbackOnResult(StatusCode.ERROR, null);
                    return;
                }
            }
            sendCallbackOnResult(StatusCode.ERROR, null);
        } catch (Exception unused) {
        }
    }

    protected boolean executeGetChallege(byte[] bArr) {
        ResponsePayload responsePayloadSendCommandAndReceive = sendCommandAndReceive(MMSCommandBuilder.GetChallengeCommand(bArr));
        if (responsePayloadSendCommandAndReceive == null) {
            return false;
        }
        this.mDeviceSerial = TLVParser.getHexString(responsePayloadSendCommandAndReceive.getParamValue("82"));
        this.mChallengeToken = TLVParser.getHexString(responsePayloadSendCommandAndReceive.getParamValue("83"));
        return true;
    }

    protected String getDeviceSerial() {
        byte[] itemsValue = getItemsValue((byte) 2, "E208E106E104E102C100");
        String hexString = itemsValue != null ? TLVParser.getHexString(itemsValue) : "";
        return hexString.length() > 7 ? hexString.substring(0, 7) : hexString;
    }

    protected String getFirmwareVersion() {
        byte[] itemsValue = getItemsValue((byte) 2, "E108E206E204E202C200");
        return itemsValue != null ? new String(itemsValue) : "";
    }

    protected String getDeviceCapabilities() {
        byte[] itemsValue = getItemsValue((byte) 2, "E208E106E104E102C200");
        return itemsValue != null ? new String(itemsValue) : "";
    }

    protected String getBoot1Version() {
        byte[] itemsValue = getItemsValue((byte) 2, "E108E206E104E102C200");
        return itemsValue != null ? new String(itemsValue) : "";
    }

    protected String getBoot0Version() {
        byte[] itemsValue = getItemsValue((byte) 2, "E108E206E104E202C200");
        return itemsValue != null ? new String(itemsValue) : "";
    }

    protected String getFirmwareHash() {
        byte[] itemsValue = getItemsValue((byte) 2, "E108E206E604E102C100");
        return itemsValue != null ? new String(itemsValue) : "";
    }

    protected String getTamperStatus() {
        byte[] itemsValue = getItemsValue((byte) 2, "E308E106E104E202C300");
        return itemsValue != null ? TLVParser.getHexString(itemsValue) : "";
    }

    protected String getOperationStatus() {
        byte[] itemsValue = getItemsValue((byte) 2, "E308E106E204E102C100");
        return itemsValue != null ? TLVParser.getHexString(itemsValue) : "";
    }

    protected String getOfflineDetail() {
        byte[] itemsValue = getItemsValue((byte) 2, "E308E106E204E102C200");
        return itemsValue != null ? TLVParser.getHexString(itemsValue) : "";
    }

    protected byte[] getItemsValue(byte b, String str) {
        return getItemsValue(b, TLVParser.getByteArrayFromHexString(str));
    }

    protected byte[] getItemsValue(byte b, byte[] bArr) {
        List<TLVObject> tLVByteArray;
        TLVObject tLVObject;
        int length;
        TLVObject tLVObjectFindByTagByteArray;
        byte[] itemsData = getItemsData(b, bArr);
        if (itemsData == null || (tLVByteArray = TLVParser.parseTLVByteArray(itemsData)) == null || tLVByteArray.size() <= 0 || (tLVObject = tLVByteArray.get(0)) == null || bArr == null || (length = bArr.length) < 2 || (tLVObjectFindByTagByteArray = tLVObject.findByTagByteArray(new byte[]{bArr[length - 2]})) == null) {
            return null;
        }
        return tLVObjectFindByTagByteArray.getValueByteArray();
    }

    protected byte[] getItemsData(byte b, String str) {
        return getItemsData(b, TLVParser.getByteArrayFromHexString(str));
    }

    protected byte[] getItemsData(byte b, byte[] bArr) {
        ResponsePayload responsePayloadSendCommandAndReceive = sendCommandAndReceive(MMSCommandBuilder.GetItemsCommand(b, bArr));
        if (responsePayloadSendCommandAndReceive != null) {
            return responsePayloadSendCommandAndReceive.getParamValue("89");
        }
        return null;
    }

    protected byte[] setItemsData(byte b, byte[] bArr) {
        ResponsePayload responsePayloadSendCommandAndReceive = sendCommandAndReceive(MMSCommandBuilder.SetItemsCommand(b, bArr));
        if (responsePayloadSendCommandAndReceive != null) {
            return responsePayloadSendCommandAndReceive.getParamValue("89");
        }
        return null;
    }

    protected void sendCommand(Command command) {
        Message messageBuildMessage = MessageBuilder.BuildMessage();
        messageBuildMessage.addMessageInfo(command.getTagByteArray());
        messageBuildMessage.addPayload(command.getByteArray());
        byte[] byteArray = messageBuildMessage.getByteArray();
        if (byteArray != null) {
            this.mMMXDevice.sendMessage(new MMXMessage(48, byteArray));
        }
    }

    protected StatusCode sendCommandSync(Command command, int i) {
        StatusCode statusCode;
        StatusCode statusCode2 = StatusCode.TIMEOUT;
        this.mResponseEvent = new Object();
        sendCommand(command);
        synchronized (this.mResponseEvent) {
            try {
                this.mResponseEvent.wait(i);
                if (this.mResponseOperationStatus == 0) {
                    statusCode = StatusCode.SUCCESS;
                } else {
                    statusCode = StatusCode.ERROR;
                }
                statusCode2 = statusCode;
            } catch (Exception unused) {
            }
        }
        this.mResponseEvent = null;
        return statusCode2;
    }

    protected ResponsePayload sendCommandAndReceive(Command command) {
        ResponsePayload responsePayload;
        this.mResponseEvent = new Object();
        sendCommand(command);
        synchronized (this.mResponseEvent) {
            try {
                this.mResponseEvent.wait(COMMAND_RESPONSE_TIMEOUT);
                responsePayload = this.mResponsePayload;
            } catch (Exception unused) {
                responsePayload = null;
            }
        }
        this.mResponseEvent = null;
        return responsePayload;
    }

    public void processResponse(Message message) {
        MMSResponse mMSResponse = new MMSResponse(message);
        if (this.mResponseEvent != null) {
            this.mResponseOperationStatus = mMSResponse.getOperationStatus();
            this.mResponsePayload = MessageParser.GetResponsePayload(mMSResponse.getPayload());
            this.mResponseEvent.notifyAll();
        }
    }

    protected void notifyLoadFirmwareResult(boolean z) {
        Object obj = this.mLoadFirmwareEvent;
        if (obj != null) {
            this.mLoadFirmwareResult = z;
            obj.notifyAll();
        }
    }

    protected void notifyCommitFirmwareResult(boolean z) {
        Object obj = this.mCommitFirmwareEvent;
        if (obj != null) {
            this.mCommitFirmwareResult = z;
            obj.notifyAll();
        }
    }

    public void processDataFile(Message message) {
        MMSDataFile mMSDataFile = new MMSDataFile(message);
        mMSDataFile.getCommandID();
        mMSDataFile.getFileType();
        byte[] payload = mMSDataFile.getPayload();
        updateProgress(100);
        sendCallbackOnResult(StatusCode.SUCCESS, payload);
    }

    public void processNotification(Message message) {
        MMSNotification mMSNotification = new MMSNotification(message);
        byte[] notificationID = mMSNotification.getNotificationID();
        byte[] notificationCode = mMSNotification.getNotificationCode();
        if (notificationID[0] == 9) {
            if (notificationID[1] == 5) {
                if (notificationCode[2] == 9) {
                    notifyLoadFirmwareResult(true);
                    return;
                } else {
                    if (notificationCode[2] == 10) {
                        notifyCommitFirmwareResult(true);
                        return;
                    }
                    return;
                }
            }
            if (notificationID[1] == 6) {
                if (notificationCode[2] == 9) {
                    notifyLoadFirmwareResult(false);
                } else if (notificationCode[2] == 10) {
                    notifyCommitFirmwareResult(false);
                }
            }
        }
    }
}
