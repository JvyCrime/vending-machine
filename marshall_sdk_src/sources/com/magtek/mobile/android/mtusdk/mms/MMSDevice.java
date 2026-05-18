package com.magtek.mobile.android.mtusdk.mms;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import com.magtek.mobile.android.mtusdk.BaseData;
import com.magtek.mobile.android.mtusdk.BaseDevice;
import com.magtek.mobile.android.mtusdk.ConnectionInfo;
import com.magtek.mobile.android.mtusdk.ConnectionState;
import com.magtek.mobile.android.mtusdk.ConnectionType;
import com.magtek.mobile.android.mtusdk.DeviceEvent;
import com.magtek.mobile.android.mtusdk.DeviceFeature;
import com.magtek.mobile.android.mtusdk.DeviceInfo;
import com.magtek.mobile.android.mtusdk.EventType;
import com.magtek.mobile.android.mtusdk.FeatureStatus;
import com.magtek.mobile.android.mtusdk.IConfigurationCallback;
import com.magtek.mobile.android.mtusdk.IData;
import com.magtek.mobile.android.mtusdk.IDeviceCapabilities;
import com.magtek.mobile.android.mtusdk.IDeviceConfiguration;
import com.magtek.mobile.android.mtusdk.IDeviceControl;
import com.magtek.mobile.android.mtusdk.IResult;
import com.magtek.mobile.android.mtusdk.ITransaction;
import com.magtek.mobile.android.mtusdk.OperationStatus;
import com.magtek.mobile.android.mtusdk.OperationStatusBuilder;
import com.magtek.mobile.android.mtusdk.PANRequest;
import com.magtek.mobile.android.mtusdk.PINRequest;
import com.magtek.mobile.android.mtusdk.PaymentMethod;
import com.magtek.mobile.android.mtusdk.Result;
import com.magtek.mobile.android.mtusdk.StatusCode;
import com.magtek.mobile.android.mtusdk.TransactionBuilder;
import com.magtek.mobile.android.mtusdk.TransactionStatus;
import com.magtek.mobile.android.mtusdk.UserEvent;
import com.magtek.mobile.android.mtusdk.common.TLVObject;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import com.magtek.mobile.android.mtusdk.messages.Command;
import com.magtek.mobile.android.mtusdk.messages.Message;
import com.magtek.mobile.android.mtusdk.messages.MessageBuilder;
import com.magtek.mobile.android.mtusdk.messages.MessageParser;
import com.magtek.mobile.android.mtusdk.messages.NotificationPayload;
import com.magtek.mobile.android.mtusdk.messages.ResponsePayload;
import com.magtek.mobile.android.mtusdk.mmx.IMMXDeviceAdapter;
import com.magtek.mobile.android.mtusdk.mmx.MMXConnectionState;
import com.magtek.mobile.android.mtusdk.mmx.MMXConnectionType;
import com.magtek.mobile.android.mtusdk.mmx.MMXDevice;
import com.magtek.mobile.android.mtusdk.mmx.MMXMessage;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: loaded from: classes.dex */
public class MMSDevice extends BaseDevice implements IMMXDeviceAdapter, IConfigurationCallback {
    private static final String TAG = "MMSDevice";
    private MMXDevice mMMXDevice = null;
    protected ITransaction mTransaction = null;

    @Override // com.magtek.mobile.android.mtusdk.IConfigurationCallback
    public void OnProgress(int i) {
    }

    public MMSDevice(Context context, ConnectionInfo connectionInfo) {
        init(context, connectionInfo, new DeviceInfo("", "", ""));
    }

    public MMSDevice(Context context, ConnectionInfo connectionInfo, DeviceInfo deviceInfo) {
        init(context, connectionInfo, deviceInfo);
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice
    protected void init(Context context, ConnectionInfo connectionInfo, DeviceInfo deviceInfo) {
        this.mContext = context;
        this.mConnectionInfo = connectionInfo;
        this.mDeviceInfo = deviceInfo;
    }

    protected static MMXConnectionType getMMXConnectionType(ConnectionType connectionType) {
        MMXConnectionType mMXConnectionType = MMXConnectionType.USB;
        int i = AnonymousClass5.$SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType[connectionType.ordinal()];
        if (i == 1) {
            return MMXConnectionType.USB;
        }
        if (i == 2) {
            return MMXConnectionType.BLE;
        }
        if (i == 3) {
            return MMXConnectionType.TCP;
        }
        if (i != 4) {
            return i != 5 ? mMXConnectionType : MMXConnectionType.SERIAL;
        }
        return MMXConnectionType.WEBSOCKET;
    }

    private MMXDevice getMMXDevice() {
        String address;
        if (this.mMMXDevice == null) {
            ConnectionType connectionType = ConnectionType.USB;
            if (this.mConnectionInfo != null) {
                connectionType = this.mConnectionInfo.getConnectionType();
                address = this.mConnectionInfo.getAddress();
            } else {
                address = "";
            }
            MMXDevice mMXDevice = new MMXDevice(this.mContext, this);
            this.mMMXDevice = mMXDevice;
            mMXDevice.setConnectionType(getMMXConnectionType(connectionType));
            this.mMMXDevice.setAddress(address);
        }
        return this.mMMXDevice;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public IDeviceCapabilities getCapabilities() {
        return new MMSDeviceCapabilities(TransactionBuilder.GetPaymentMethods(true, true, true, false), true, false, true, true, false);
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public IDeviceControl getDeviceControl() {
        String address;
        if (this.mDeviceControl == null) {
            if (this.mConnectionInfo != null) {
                this.mConnectionInfo.getConnectionType();
                address = this.mConnectionInfo.getAddress();
            } else {
                address = "";
            }
            this.mDeviceControl = new MMSDeviceControl(getMMXDevice(), address);
        }
        return this.mDeviceControl;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public IDeviceConfiguration getDeviceConfiguration() {
        if (this.mDeviceConfiguration == null) {
            this.mDeviceConfiguration = new MMSDeviceConfiguration(getMMXDevice());
        }
        return this.mDeviceConfiguration;
    }

    protected void sendCommand(Command command) {
        Message messageBuildMessage = MessageBuilder.BuildMessage();
        messageBuildMessage.addMessageInfo(command.getTagByteArray());
        messageBuildMessage.addPayload(command.getByteArray());
        byte[] byteArray = messageBuildMessage.getByteArray();
        if (byteArray != null) {
            getMMXDevice().sendMessage(new MMXMessage(48, byteArray));
        }
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean startTransaction(ITransaction iTransaction) {
        if (iTransaction == null) {
            return true;
        }
        this.mTransaction = iTransaction;
        new Thread() { // from class: com.magtek.mobile.android.mtusdk.mms.MMSDevice.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                if (MMSDevice.this.checkConnectedDevice()) {
                    try {
                        Thread.sleep(100L);
                    } catch (Exception unused) {
                    }
                    MMSDevice mMSDevice = MMSDevice.this;
                    mMSDevice.startEMVTransaction(mMSDevice.mTransaction);
                }
            }
        }.start();
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean requestPIN(final PINRequest pINRequest) {
        if (this.mMMXDevice == null) {
            return true;
        }
        new Thread() { // from class: com.magtek.mobile.android.mtusdk.mms.MMSDevice.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                if (MMSDevice.this.checkConnectedDevice()) {
                    try {
                        Thread.sleep(100L);
                        String strPAN = pINRequest.PAN();
                        byte[] byteArrayFromHexString = null;
                        if (strPAN != null && strPAN.length() > 0) {
                            byteArrayFromHexString = TLVParser.getByteArrayFromHexString(String.format("%-12s", strPAN).replace(' ', '0'));
                        }
                        MMSDevice.this.sendCommand(MMSCommandBuilder.requestPINCommand(pINRequest.Timeout(), pINRequest.PINMode(), pINRequest.MinLength(), pINRequest.MaxLength(), byteArrayFromHexString, pINRequest.Format()));
                    } catch (Exception unused) {
                    }
                }
            }
        }.start();
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean requestPAN(final PANRequest pANRequest, final PINRequest pINRequest) {
        if (this.mMMXDevice == null) {
            return true;
        }
        new Thread() { // from class: com.magtek.mobile.android.mtusdk.mms.MMSDevice.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                byte b;
                byte bContains;
                Looper.prepare();
                byte b2 = 1;
                if (MMSDevice.this.checkConnectedDevice()) {
                    try {
                        Thread.sleep(100L);
                        byte bTimeout = pANRequest.Timeout();
                        List<PaymentMethod> listPaymentMethods = pANRequest.PaymentMethods();
                        byte b3 = 0;
                        if (listPaymentMethods != null) {
                            byte b4 = listPaymentMethods.contains(PaymentMethod.MSR) ? (byte) 1 : (byte) 0;
                            if (!listPaymentMethods.contains(PaymentMethod.Contact)) {
                                b2 = 0;
                            }
                            bContains = listPaymentMethods.contains(PaymentMethod.Contactless);
                            b3 = b4;
                            b = b2;
                        } else {
                            b = 0;
                            bContains = 0;
                        }
                        PINRequest pINRequest2 = pINRequest;
                        if (pINRequest2 != null) {
                            MMSDevice.this.sendCommand(MMSCommandBuilder.requestPANCommand(bTimeout, b3, b, bContains, pINRequest2.PINMode(), pINRequest.MinLength(), pINRequest.MaxLength(), pINRequest.Format()));
                        } else {
                            MMSDevice.this.sendCommand(MMSCommandBuilder.requestPANCommand(bTimeout, b3, b, bContains));
                        }
                    } catch (Exception unused) {
                    }
                }
            }
        }.start();
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean requestSignature() {
        if (this.mMMXDevice == null) {
            return true;
        }
        new Thread() { // from class: com.magtek.mobile.android.mtusdk.mms.MMSDevice.4
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Looper.prepare();
                if (MMSDevice.this.checkConnectedDevice()) {
                    try {
                        Thread.sleep(500L);
                        MMSDevice.this.sendCommand(MMSCommandBuilder.RequestSignatureCommand((byte) 30));
                    } catch (Exception unused) {
                    }
                }
            }
        }.start();
        return true;
    }

    private byte getCardTypeValue(List<PaymentMethod> list) {
        PaymentMethod next;
        int i;
        byte b = 0;
        if (list != null) {
            ListIterator<PaymentMethod> listIterator = list.listIterator();
            while (listIterator.hasNext()) {
                try {
                    next = listIterator.next();
                } catch (Exception unused) {
                }
                if (next == PaymentMethod.MSR) {
                    i = b | 1;
                } else if (next == PaymentMethod.Contact) {
                    i = b | 2;
                } else if (next == PaymentMethod.Contactless) {
                    i = b | 4;
                } else if (next == PaymentMethod.ManualEntry) {
                    i = b | 8;
                }
                b = (byte) i;
            }
        }
        return b;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x012c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected boolean startEMVTransaction(com.magtek.mobile.android.mtusdk.ITransaction r13) {
        /*
            Method dump skipped, instruction units count: 347
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.magtek.mobile.android.mtusdk.mms.MMSDevice.startEMVTransaction(com.magtek.mobile.android.mtusdk.ITransaction):boolean");
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean cancelTransaction() {
        sendCommand(MMSCommandBuilder.CancelTransactionCommand());
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean sendSelection(IData iData) {
        if (iData != null) {
            byte[] bArrByteArray = iData.ByteArray();
            sendCommand(MMSCommandBuilder.ReportSelection(bArrByteArray[0], bArrByteArray[1]));
        }
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean sendAuthorization(IData iData) {
        if (iData == null) {
            return true;
        }
        sendCommand(MMSCommandBuilder.ResumeTransactionCommand(iData.ByteArray()));
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.IMMXDeviceAdapter
    public void OnMessage(int i, int i2, int i3, Object obj) {
        try {
            if (i == 0) {
                Log.i(TAG, "OnDeviceConnectionStateChanged");
                OnConnectionStateChanged((MMXConnectionState) obj);
            } else {
                if (i != 1) {
                    return;
                }
                String str = TAG;
                Log.i(str, "OnDeviceDataReceived");
                Log.i(str, "DataReceived=" + TLVParser.getHexString((byte[]) obj));
                OnMessage((byte[]) obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void OnConnectionStateChanged(MMXConnectionState mMXConnectionState) {
        ConnectionState connectionState = ConnectionState.Unknown;
        int i = AnonymousClass5.$SwitchMap$com$magtek$mobile$android$mtusdk$mmx$MMXConnectionState[mMXConnectionState.ordinal()];
        if (i == 1) {
            connectionState = ConnectionState.Connecting;
        } else if (i == 2) {
            connectionState = ConnectionState.Connected;
        } else if (i == 3) {
            connectionState = ConnectionState.Disconnecting;
        } else if (i == 4) {
            connectionState = ConnectionState.Disconnected;
        }
        updateConnectionStateValue(connectionState);
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtusdk.mms.MMSDevice$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType;
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtusdk$mmx$MMXConnectionState;

        static {
            int[] iArr = new int[MMXConnectionState.values().length];
            $SwitchMap$com$magtek$mobile$android$mtusdk$mmx$MMXConnectionState = iArr;
            try {
                iArr[MMXConnectionState.Connecting.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$mmx$MMXConnectionState[MMXConnectionState.Connected.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$mmx$MMXConnectionState[MMXConnectionState.Disconnecting.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$mmx$MMXConnectionState[MMXConnectionState.Disconnected.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[ConnectionType.values().length];
            $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType = iArr2;
            try {
                iArr2[ConnectionType.USB.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType[ConnectionType.BLUETOOTH_LE_EMV.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType[ConnectionType.TCP.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType[ConnectionType.WEBSOCKET.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType[ConnectionType.SERIAL.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    protected void OnMessage(byte[] bArr) {
        String hexString = TLVParser.getHexString(bArr);
        Message messageGetMessage = MessageParser.GetMessage(bArr);
        if (messageGetMessage != null) {
            try {
                if (messageGetMessage.isResponse()) {
                    sendEvent(EventType.DeviceResponse, new BaseData(hexString));
                    processResponse(messageGetMessage);
                    if (this.mDeviceConfiguration == null) {
                    } else {
                        ((MMSDeviceConfiguration) this.mDeviceConfiguration).processResponse(messageGetMessage);
                    }
                } else if (messageGetMessage.isNotification()) {
                    sendEvent(EventType.DeviceNotification, new BaseData(hexString));
                    processNotification(messageGetMessage);
                    if (this.mDeviceConfiguration == null) {
                    } else {
                        ((MMSDeviceConfiguration) this.mDeviceConfiguration).processNotification(messageGetMessage);
                    }
                } else {
                    if (!messageGetMessage.isDataFile()) {
                        return;
                    }
                    sendEvent(EventType.DeviceDataFile, new BaseData(hexString));
                    if (this.mDeviceConfiguration == null) {
                    } else {
                        ((MMSDeviceConfiguration) this.mDeviceConfiguration).processDataFile(messageGetMessage);
                    }
                }
            } catch (Exception unused) {
            }
        }
    }

    protected void sendAuthorizationRequestEvent(byte[] bArr) {
        int length;
        Log.i(TAG, "sendAuthorizationRequestEvent");
        if (bArr == null || (length = bArr.length) <= 0) {
            return;
        }
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        sendEvent(EventType.AuthorizationRequest, new BaseData(bArr2));
    }

    protected void sendTransactionResultEvent(byte[] bArr) {
        int length;
        Log.i(TAG, "sendTransactionResultEvent");
        if (bArr == null || (length = bArr.length) <= 0) {
            return;
        }
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        sendEvent(EventType.TransactionResult, new BaseData(bArr2));
    }

    private byte[] getTLVPayload(byte[] bArr) {
        if (bArr == null || bArr.length <= 2) {
            return null;
        }
        int i = ((bArr[0] & 255) << 8) + (bArr[1] & 255);
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 2, bArr2, 0, i);
        return bArr2;
    }

    public void checkResultForSignatureData(byte[] bArr) {
        List<TLVObject> tLVByteArray;
        TLVObject tLVObjectFindFromListByTagHexString;
        byte[] valueByteArray;
        if (bArr.length <= 0 || (tLVByteArray = TLVParser.parseTLVByteArray(getTLVPayload(bArr))) == null || (tLVObjectFindFromListByTagHexString = TLVParser.findFromListByTagHexString(tLVByteArray, "DFDF3E")) == null || (valueByteArray = tLVObjectFindFromListByTagHexString.getValueByteArray()) == null) {
            return;
        }
        int length = valueByteArray.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(valueByteArray, 0, bArr2, 0, length);
        sendEvent(EventType.Signature, new BaseData(bArr2));
    }

    @Override // com.magtek.mobile.android.mtusdk.IConfigurationCallback
    public void OnResult(StatusCode statusCode, byte[] bArr) {
        TLVObject tLVObjectFindFromListByTagHexString;
        List<TLVObject> tLVByteArray;
        byte[] valueByteArray;
        int length = bArr.length - 8;
        if (length > 0) {
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 8, bArr2, 0, length);
            List<TLVObject> tLVByteArray2 = TLVParser.parseTLVByteArray(bArr2);
            if (tLVByteArray2 == null || (tLVObjectFindFromListByTagHexString = TLVParser.findFromListByTagHexString(tLVByteArray2, "CE")) == null || (tLVByteArray = TLVParser.parseTLVByteArray(tLVObjectFindFromListByTagHexString.getValueByteArray())) == null) {
                return;
            }
            TLVObject tLVObjectFindFromListByTagHexString2 = TLVParser.findFromListByTagHexString(tLVByteArray, "81");
            if (tLVObjectFindFromListByTagHexString2 != null) {
                tLVObjectFindFromListByTagHexString2.getValueByteArray();
            }
            TLVObject tLVObjectFindFromListByTagHexString3 = TLVParser.findFromListByTagHexString(tLVByteArray, "82");
            if (tLVObjectFindFromListByTagHexString3 == null || (valueByteArray = tLVObjectFindFromListByTagHexString3.getValueByteArray()) == null) {
                return;
            }
            int length2 = valueByteArray.length / 2;
            byte[] bArr3 = new byte[length2];
            for (int i = 0; i < length2; i++) {
                bArr3[i] = valueByteArray[(i * 2) + 1];
            }
            sendEvent(EventType.Signature, new BaseData(bArr3));
        }
    }

    @Override // com.magtek.mobile.android.mtusdk.IConfigurationCallback
    public IResult OnCalculateMAC(byte b, byte[] bArr) {
        return new Result(StatusCode.UNAVAILABLE);
    }

    protected void sendOperationStatus(OperationStatus operationStatus, String str, String str2, String str3) {
        sendEvent(EventType.OperationStatus, new BaseData(OperationStatusBuilder.GetString(operationStatus) + "," + str + "," + str2 + "," + str3));
    }

    protected void processResponse(Message message) {
        String str = TAG;
        Log.i(str, "processResponse");
        MMSResponse mMSResponse = new MMSResponse(message);
        byte[] requestID = mMSResponse.getRequestID();
        if (requestID == null || requestID.length != 2) {
            return;
        }
        Log.i(str, "requestID=" + TLVParser.getHexString(requestID));
        byte[] statusCodeValue = mMSResponse.getStatusCodeValue();
        byte b = 0;
        if (statusCodeValue != null && statusCodeValue.length >= 4) {
            byte b2 = statusCodeValue[0];
            String hexString = TLVParser.getHexString(statusCodeValue);
            String hexString2 = TLVParser.getHexString(requestID);
            if (b2 == -128) {
                sendOperationStatus(OperationStatus.Failed, hexString2, "Operation Failed", hexString);
            } else if (b2 == -127) {
                sendOperationStatus(OperationStatus.Started, hexString2, "Operation Running", hexString);
                sendOperationStatus(OperationStatus.Failed, hexString2, "Operation Failed", hexString);
            } else if (b2 == 0) {
                sendOperationStatus(OperationStatus.Done, hexString2, "Operation Done", hexString);
            } else if (b2 == 1) {
                sendOperationStatus(OperationStatus.Started, hexString2, "Operation Started", hexString);
            } else if (b2 == 64) {
                sendOperationStatus(OperationStatus.Warning, hexString2, "Operation Warning", hexString);
                sendOperationStatus(OperationStatus.Done, hexString2, "Operation Done", hexString);
            } else if (b2 == 65) {
                sendOperationStatus(OperationStatus.Started, hexString2, "Operation Started", hexString);
                sendOperationStatus(OperationStatus.Warning, hexString2, "Operation Warning", hexString);
            }
        }
        if (requestID[0] == 16 && requestID[1] == 17) {
            byte[] payload = mMSResponse.getPayload();
            Log.i(str, "GetResponsePayload");
            ResponsePayload responsePayloadGetResponsePayload = MessageParser.GetResponsePayload(payload);
            if (responsePayloadGetResponsePayload != null) {
                byte[] paramValue = responsePayloadGetResponsePayload.getParamValue("81");
                if (paramValue != null && paramValue.length > 0) {
                    b = paramValue[0];
                }
                byte[] payloadValue = responsePayloadGetResponsePayload.getPayloadValue();
                if (payloadValue != null) {
                    if (b == 2) {
                        sendAuthorizationRequestEvent(payloadValue);
                    } else {
                        if (b != 3) {
                            return;
                        }
                        sendTransactionResultEvent(payloadValue);
                    }
                }
            }
        }
    }

    protected void processNotification(Message message) {
        String textString;
        byte[] payload;
        byte[] payload2;
        byte[] payload3;
        MMSNotification mMSNotification = new MMSNotification(message);
        byte[] notificationID = mMSNotification.getNotificationID();
        byte[] notificationCode = mMSNotification.getNotificationCode();
        byte b = notificationID[0];
        byte b2 = notificationID[1];
        String hexString = TLVParser.getHexString(message.getByteArray());
        if (b == 1) {
            byte b3 = notificationCode[0];
            byte b4 = notificationCode[1];
            byte b5 = notificationCode[2];
            byte b6 = notificationCode[3];
            if (b2 == 1) {
                if (b4 == 1) {
                    if (b5 == 1) {
                        if (b3 == 8) {
                            updateTransactionStatusValue(TransactionStatus.CardSwiped, "Card Swiped", hexString);
                            return;
                        } else {
                            if (b3 == 7) {
                                updateTransactionStatusValue(TransactionStatus.DataEntered, "Data Entered", hexString);
                                return;
                            }
                            return;
                        }
                    }
                    if (b5 == 2) {
                        updateTransactionStatusValue(TransactionStatus.CardInserted, "Card Inserted", hexString);
                        return;
                    }
                    if (b5 == 3) {
                        updateTransactionStatusValue(TransactionStatus.CardRemoved, "Card Removed", hexString);
                        return;
                    } else if (b5 == 4) {
                        updateTransactionStatusValue(TransactionStatus.CardDetected, "Card Detected", hexString);
                        return;
                    } else {
                        if (b5 == 5) {
                            updateTransactionStatusValue(TransactionStatus.CardCollision, "Card Collision", hexString);
                            return;
                        }
                        return;
                    }
                }
                if (b4 == 8) {
                    if (b5 == 2) {
                        if (b6 == 1) {
                            requestARQC();
                            return;
                        } else {
                            if (b6 == 2) {
                                sendAuthorizationRequestEvent(MessageParser.GetNotificationPayload(mMSNotification.getPayload()).getPayloadValue());
                                return;
                            }
                            return;
                        }
                    }
                    if (b5 == 3) {
                        if (b6 == 1) {
                            requestBatchData();
                            return;
                        } else {
                            if (b6 == 2) {
                                byte[] payloadValue = MessageParser.GetNotificationPayload(mMSNotification.getPayload()).getPayloadValue();
                                sendTransactionResultEvent(payloadValue);
                                checkResultForSignatureData(payloadValue);
                                return;
                            }
                            return;
                        }
                    }
                    return;
                }
                return;
            }
            if (b2 == 3 || b2 == 4 || b2 != 5) {
                return;
            }
            if (b4 == 1) {
                updateTransactionStatusValue(TransactionStatus.TimedOut, "Timed Out", hexString);
                return;
            }
            if (b4 != 2) {
                if (b4 == 3) {
                    if (b5 == 1) {
                        updateTransactionStatusValue(TransactionStatus.HostCancelled, "Host Cancelled", hexString);
                        return;
                    } else if (b5 == 2) {
                        updateTransactionStatusValue(TransactionStatus.TransactionCancelled, "Transaction Cancelled (User cancel)", hexString);
                        return;
                    } else {
                        if (b5 == 5) {
                            updateTransactionStatusValue(TransactionStatus.TransactionCancelled, "Transaction Cancelled (Cancel due to card read error)", hexString);
                            return;
                        }
                        return;
                    }
                }
                if (b4 == 4) {
                    if (b5 != 0) {
                        if (b5 == 7) {
                            updateTransactionStatusValue(TransactionStatus.TransactionDeclined, "Transaction Declined", hexString);
                            return;
                        }
                        return;
                    } else {
                        updateTransactionStatusValue(TransactionStatus.TransactionCompleted, "Transaction Completed", hexString);
                        if (b6 == 1) {
                            updateTransactionStatusValue(TransactionStatus.SignatureCaptureRequested, "Signature Capture Requested", hexString);
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            if (b5 == 0) {
                if (b6 == 2) {
                    updateTransactionStatusValue(TransactionStatus.TechnicalFallback, "Technical Fallback", hexString);
                    return;
                }
                return;
            }
            if (b5 == 1) {
                updateTransactionStatusValue(TransactionStatus.TryAnotherInterface, "Try Another Interface", hexString);
                return;
            }
            if (b5 == 15) {
                if (b6 == 2) {
                    updateTransactionStatusValue(TransactionStatus.TechnicalFallback, "Technical Fallback", hexString);
                    return;
                } else {
                    if (b6 == 0) {
                        updateTransactionStatusValue(TransactionStatus.TechnicalFallback, "Technical Fallback", hexString);
                        return;
                    }
                    return;
                }
            }
            if (b5 == 2) {
                updateTransactionStatusValue(TransactionStatus.TransactionApproved, "Transaction Approved (Offline)", hexString);
                if (b6 == 1) {
                    updateTransactionStatusValue(TransactionStatus.SignatureCaptureRequested, "Signature Capture Requested", hexString);
                    return;
                }
                return;
            }
            if (b5 == 3) {
                updateTransactionStatusValue(TransactionStatus.TransactionDeclined, "Transaction Declined (Offline)", hexString);
                return;
            }
            if (b5 == 4) {
                updateTransactionStatusValue(TransactionStatus.TransactionFailed, "Transaction Failed (Offline)", hexString);
                return;
            }
            if (b5 == 5) {
                updateTransactionStatusValue(TransactionStatus.TransactionNotAccepted, "Transaction Not Accepted  (Offline)", hexString);
                return;
            }
            if (b5 == 6) {
                updateTransactionStatusValue(TransactionStatus.TransactionApproved, "Transaction Approved", hexString);
                if (b6 == 1) {
                    updateTransactionStatusValue(TransactionStatus.SignatureCaptureRequested, "Signature Capture Requested", hexString);
                    return;
                }
                return;
            }
            if (b5 == 7) {
                updateTransactionStatusValue(TransactionStatus.QuickChipDeferred, "Quick Chip Deferred", hexString);
                if (b6 == 1) {
                    updateTransactionStatusValue(TransactionStatus.SignatureCaptureRequested, "Signature Capture Requested", hexString);
                    return;
                }
                return;
            }
            if (b5 == 8) {
                updateTransactionStatusValue(TransactionStatus.TransactionFailed, "Transaction Failed", hexString);
                return;
            } else if (b5 == 9) {
                updateTransactionStatusValue(TransactionStatus.TransactionNotAccepted, "Transaction Not Accepted", hexString);
                return;
            } else {
                if (b5 == 10) {
                    updateTransactionStatusValue(TransactionStatus.TransactionCancelled, "Transaction Cancelled", hexString);
                    return;
                }
                return;
            }
        }
        if (b == 9) {
            return;
        }
        textString = "";
        if (b == 16) {
            byte b7 = notificationCode[0];
            byte b8 = notificationCode[1];
            byte b9 = notificationCode[2];
            byte b10 = notificationCode[3];
            if (b2 == 1) {
                if (b7 == 0) {
                    byte[] payload4 = mMSNotification.getPayload();
                    textString = payload4 != null ? TLVParser.getHexString(payload4) : "";
                    if (b8 == 0) {
                        sendDeviceEvent(DeviceEvent.DeviceResetOccurred, textString);
                        return;
                    } else {
                        if (b8 == 1) {
                            sendDeviceEvent(DeviceEvent.DeviceResetWillOccur, textString);
                            return;
                        }
                        return;
                    }
                }
                if (b7 == 1) {
                    if (b8 == 0) {
                        sendUserEventValue(UserEvent.ContactlessCardPresented);
                        return;
                    }
                    if (b8 == 1) {
                        sendUserEventValue(UserEvent.ContactlessCardRemoved);
                        return;
                    }
                    if (b8 == 2) {
                        sendUserEventValue(UserEvent.CardSeated);
                        return;
                    }
                    if (b8 == 3) {
                        sendUserEventValue(UserEvent.CardUnseated);
                        return;
                    }
                    if (b8 == 4) {
                        sendUserEventValue(UserEvent.CardSwiped);
                        return;
                    } else if (b8 == 5) {
                        sendUserEventValue(UserEvent.TouchPresented);
                        return;
                    } else {
                        if (b8 == 6) {
                            sendUserEventValue(UserEvent.TouchRemoved);
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            return;
        }
        if (b == 24) {
            byte b11 = notificationCode[0];
            byte b12 = notificationCode[1];
            byte b13 = notificationCode[2];
            byte b14 = notificationCode[3];
            String str = TAG;
            Log.i(str, "UI Notification Code=" + TLVParser.getHexString(notificationCode));
            if (b2 == 3) {
                Log.i(str, "User Interface Host Action Request");
                if (b11 == 2) {
                    Log.i(str, "Display");
                    if (b12 != 1) {
                        if (b12 == 2) {
                            Log.i(str, "Cardholder Selection");
                            if (b13 == 0) {
                                NotificationPayload notificationPayloadGetNotificationPayload = MessageParser.GetNotificationPayload(mMSNotification.getPayload());
                                processSelectionRequest(notificationPayloadGetNotificationPayload.getTagValue("81"), notificationPayloadGetNotificationPayload.getTagValue("82"), notificationPayloadGetNotificationPayload.getTagValue("83"));
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    if (b13 == 0) {
                        return;
                    }
                    if ((b13 == 1 || b13 == 2) && b14 == 0) {
                        NotificationPayload notificationPayloadGetNotificationPayload2 = MessageParser.GetNotificationPayload(mMSNotification.getPayload());
                        byte[] paramValue = notificationPayloadGetNotificationPayload2 != null ? notificationPayloadGetNotificationPayload2.getParamValue("83") : null;
                        if (paramValue != null) {
                            if (paramValue != null && paramValue.length > 0) {
                                textString = TLVParser.getTextString(paramValue, 0);
                            }
                            sendEvent(EventType.DisplayMessage, new BaseData(textString));
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            if (b2 == 5) {
                if (b11 == 1) {
                    if (b12 == 1) {
                        if (b13 == 1) {
                            if (b14 == 1) {
                                byte[] additionalDetails = mMSNotification.getAdditionalDetails();
                                TLVParser.getHexString(additionalDetails);
                                getDeviceConfiguration().getFile(additionalDetails, this);
                                return;
                            }
                            return;
                        }
                        if (b13 == 2) {
                            if (b14 == 1) {
                                sendFeatureStatusEvent(DeviceFeature.SignatureCapture, FeatureStatus.TimedOut);
                                return;
                            } else {
                                if (b14 == 2) {
                                    sendFeatureStatusEvent(DeviceFeature.SignatureCapture, FeatureStatus.HardwareNA);
                                    return;
                                }
                                return;
                            }
                        }
                        return;
                    }
                    if (b12 == 2) {
                        if (b13 == 1) {
                            if (b14 != 1 || (payload3 = mMSNotification.getPayload()) == null) {
                                return;
                            }
                            sendEvent(EventType.PINData, new BaseData(payload3));
                            return;
                        }
                        if (b13 == 2) {
                            if (b14 == 1) {
                                sendFeatureStatusEvent(DeviceFeature.PINEntry, FeatureStatus.TimedOut);
                                return;
                            }
                            if (b14 == 2) {
                                sendFeatureStatusEvent(DeviceFeature.PINEntry, FeatureStatus.HardwareNA);
                                return;
                            }
                            if (b14 == 3) {
                                sendFeatureStatusEvent(DeviceFeature.PINEntry, FeatureStatus.Cancelled);
                                return;
                            } else if (b14 == 4) {
                                sendFeatureStatusEvent(DeviceFeature.PINEntry, FeatureStatus.Error);
                                return;
                            } else {
                                sendFeatureStatusEvent(DeviceFeature.PINEntry, FeatureStatus.Failed);
                                return;
                            }
                        }
                        return;
                    }
                    return;
                }
                if (b11 != 3) {
                    if (b11 == 4 && b12 == 3) {
                        if (b13 != 1) {
                            if (b13 == 2) {
                                if (b14 == 1) {
                                    sendFeatureStatusEvent(DeviceFeature.ScanBarCode, FeatureStatus.TimedOut);
                                    return;
                                } else {
                                    sendFeatureStatusEvent(DeviceFeature.ScanBarCode, FeatureStatus.Failed);
                                    return;
                                }
                            }
                            return;
                        }
                        if (b14 == 0) {
                            byte[] payloadValue2 = MessageParser.GetNotificationPayload(mMSNotification.getPayload()).getPayloadValue();
                            if (payloadValue2 != null) {
                                sendEvent(EventType.BarCodeData, new BaseData(payloadValue2));
                                return;
                            }
                            return;
                        }
                        if (b14 != 1 || (payload = mMSNotification.getPayload()) == null) {
                            return;
                        }
                        sendEvent(EventType.BarCodeData, new BaseData(payload));
                        return;
                    }
                    return;
                }
                if (b12 == 2) {
                    if (b13 == 1) {
                        if (b14 == 1) {
                            byte[] payload5 = mMSNotification.getPayload();
                            if (payload5 != null) {
                                sendEvent(EventType.PANData, new BaseData(payload5));
                                return;
                            }
                            return;
                        }
                        if (b14 != 2 || (payload2 = mMSNotification.getPayload()) == null) {
                            return;
                        }
                        sendEvent(EventType.PANData, new BaseData(payload2));
                        return;
                    }
                    if (b13 == 2) {
                        if (b14 == 1) {
                            sendFeatureStatusEvent(DeviceFeature.PANEntry, FeatureStatus.TimedOut);
                            return;
                        }
                        if (b14 == 2) {
                            sendFeatureStatusEvent(DeviceFeature.PANEntry, FeatureStatus.HardwareNA);
                            return;
                        }
                        if (b14 == 3) {
                            sendFeatureStatusEvent(DeviceFeature.PANEntry, FeatureStatus.Cancelled);
                            return;
                        }
                        if (b14 == 4) {
                            sendFeatureStatusEvent(DeviceFeature.PANEntry, FeatureStatus.Error);
                        } else if (b14 == 5) {
                            sendFeatureStatusEvent(DeviceFeature.PINEntry, FeatureStatus.Failed);
                        } else {
                            sendFeatureStatusEvent(DeviceFeature.PANEntry, FeatureStatus.Failed);
                        }
                    }
                }
            }
        }
    }

    protected void processSelectionRequest(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        if (bArr3 == null || bArr3.length <= 0) {
            return;
        }
        int length = bArr3.length + 3;
        byte[] bArr4 = new byte[length];
        bArr4[0] = 0;
        bArr4[1] = bArr[0];
        System.arraycopy(bArr3, 0, bArr4, 2, bArr3.length);
        bArr4[length - 1] = 0;
        sendEvent(EventType.InputRequest, new BaseData(bArr4));
    }

    protected void requestCardData() {
        requestTransactionData(MMSCommandBuilder.BLOBTYPE_CARD_DATA);
    }

    protected void requestARQC() {
        requestTransactionData(MMSCommandBuilder.BLOBTYPE_ARQC);
    }

    protected void requestBatchData() {
        requestTransactionData(MMSCommandBuilder.BLOBTYPE_BATCH_DATA);
    }

    protected void requestTransactionData(byte b) {
        sendCommand(MMSCommandBuilder.GetTransactionDataCommand(b));
    }
}
