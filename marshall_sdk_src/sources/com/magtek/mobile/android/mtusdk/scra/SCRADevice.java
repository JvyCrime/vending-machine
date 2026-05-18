package com.magtek.mobile.android.mtusdk.scra;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.magtek.mobile.android.mtlib.IMTCardData;
import com.magtek.mobile.android.mtlib.MTConnectionState;
import com.magtek.mobile.android.mtlib.MTConnectionType;
import com.magtek.mobile.android.mtlib.MTDeviceFeatures;
import com.magtek.mobile.android.mtlib.MTEMVEvent;
import com.magtek.mobile.android.mtlib.MTSCRA;
import com.magtek.mobile.android.mtusdk.BaseData;
import com.magtek.mobile.android.mtusdk.BaseDevice;
import com.magtek.mobile.android.mtusdk.ConnectionInfo;
import com.magtek.mobile.android.mtusdk.ConnectionState;
import com.magtek.mobile.android.mtusdk.ConnectionType;
import com.magtek.mobile.android.mtusdk.DeviceInfo;
import com.magtek.mobile.android.mtusdk.EventType;
import com.magtek.mobile.android.mtusdk.IData;
import com.magtek.mobile.android.mtusdk.IDeviceCapabilities;
import com.magtek.mobile.android.mtusdk.IDeviceControl;
import com.magtek.mobile.android.mtusdk.ITransaction;
import com.magtek.mobile.android.mtusdk.PaymentMethod;
import com.magtek.mobile.android.mtusdk.TransactionStatus;
import com.magtek.mobile.android.mtusdk.TransactionStatusBuilder;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: loaded from: classes.dex */
public class SCRADevice extends BaseDevice implements SCRASpiMsrAdapter, SCRAUartNfcAdapter {
    private static final String TAG = "SCRADevice";
    private MTSCRA mSCRA = null;
    protected ITransaction mTransaction = null;
    private SCRASpiMsr mSpiMsr = null;
    private SCRAUartNfc mUartNfc = null;
    private boolean mOEMContactInProgress = false;
    private boolean mOEMContactlessInProgress = false;
    private Handler mSCRAHandler = new Handler(new SCRAHandlerCallback());

    @Override // com.magtek.mobile.android.mtusdk.scra.SCRASpiMsrAdapter
    public void OnDebugInfo(String str) {
    }

    @Override // com.magtek.mobile.android.mtusdk.scra.SCRAUartNfcAdapter
    public void OnUartDebugInfo(String str) {
    }

    @Override // com.magtek.mobile.android.mtusdk.scra.SCRAUartNfcAdapter
    public void OnUartDeviceResponse(String str) {
    }

    public SCRADevice(Context context, ConnectionInfo connectionInfo) {
        init(context, connectionInfo, new DeviceInfo("", "", ""));
    }

    public SCRADevice(Context context, ConnectionInfo connectionInfo, DeviceInfo deviceInfo) {
        init(context, connectionInfo, deviceInfo);
    }

    private class SCRAHandlerCallback implements Handler.Callback {
        private SCRAHandlerCallback() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            try {
                int i = message.what;
                if (i == 0) {
                    Log.i(SCRADevice.TAG, "OnDeviceConnectionStateChanged");
                    SCRADevice.this.OnDeviceStateChanged((MTConnectionState) message.obj);
                } else if (i == 1) {
                    Log.i(SCRADevice.TAG, "OnCardDataStateChanged");
                } else if (i == 2) {
                    Log.i(SCRADevice.TAG, "OnDataReceived");
                    SCRADevice.this.OnDataReceived(this, (IMTCardData) message.obj);
                } else if (i == 3) {
                    Log.i(SCRADevice.TAG, "OnDeviceResponse");
                    SCRADevice.this.OnDeviceResponse(this, (String) message.obj);
                } else if (i != 4) {
                    switch (i) {
                        case 200:
                            Log.i(SCRADevice.TAG, "OnTransactionStatus");
                            SCRADevice.this.OnTransactionStatus(this, (byte[]) message.obj);
                            break;
                        case MTEMVEvent.OnDisplayMessageRequest /* 201 */:
                            Log.i(SCRADevice.TAG, "OnDisplayMessageRequest");
                            SCRADevice.this.OnDisplayMessageRequest(this, (byte[]) message.obj);
                            break;
                        case MTEMVEvent.OnUserSelectionRequest /* 202 */:
                            Log.i(SCRADevice.TAG, "OnUserSelectionRequest");
                            SCRADevice.this.OnUserSelectionRequest(this, (byte[]) message.obj);
                            break;
                        case MTEMVEvent.OnARQCReceived /* 203 */:
                            Log.i(SCRADevice.TAG, "OnARQCReceived");
                            SCRADevice.this.OnARQCReceived(this, (byte[]) message.obj);
                            break;
                        case MTEMVEvent.OnTransactionResult /* 204 */:
                            Log.i(SCRADevice.TAG, "OnTransactionResult");
                            SCRADevice.this.OnTransactionResult(this, (byte[]) message.obj);
                            break;
                        case MTEMVEvent.OnEMVCommandResult /* 205 */:
                            Log.i(SCRADevice.TAG, "OnEMVCommandResult");
                            SCRADevice.this.OnEMVCommandResult(this, (byte[]) message.obj);
                            break;
                        case MTEMVEvent.OnDeviceExtendedResponse /* 206 */:
                            Log.i(SCRADevice.TAG, "OnDeviceExtendedResponse");
                            SCRADevice.this.OnDeviceExtendedResponse(this, (String) message.obj);
                            break;
                    }
                } else {
                    Log.i(SCRADevice.TAG, "OnDeviceNotPaired");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice
    protected void init(Context context, ConnectionInfo connectionInfo, DeviceInfo deviceInfo) {
        this.mContext = context;
        this.mConnectionInfo = connectionInfo;
        this.mDeviceInfo = deviceInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MTSCRA getMTSCRA() {
        String address;
        if (this.mSCRA == null) {
            ConnectionType connectionType = ConnectionType.USB;
            if (this.mConnectionInfo != null) {
                connectionType = this.mConnectionInfo.getConnectionType();
                address = this.mConnectionInfo.getAddress();
            } else {
                address = "";
            }
            MTSCRA mtscra = new MTSCRA(this.mContext, this.mSCRAHandler);
            this.mSCRA = mtscra;
            mtscra.setConnectionType(getMTConnectionType(connectionType));
            this.mSCRA.setAddress(address);
        }
        return this.mSCRA;
    }

    protected static MTConnectionType getMTConnectionType(ConnectionType connectionType) {
        MTConnectionType mTConnectionType = MTConnectionType.USB;
        int i = AnonymousClass5.$SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType[connectionType.ordinal()];
        if (i == 1) {
            return MTConnectionType.USB;
        }
        if (i == 2) {
            return MTConnectionType.BLEEMV;
        }
        if (i == 3) {
            return MTConnectionType.BLEEMVT;
        }
        if (i != 4) {
            return i != 5 ? mTConnectionType : MTConnectionType.AIDL;
        }
        return MTConnectionType.BLE;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public IDeviceCapabilities getCapabilities() {
        MTDeviceFeatures deviceFeatures = getMTSCRA().getDeviceFeatures();
        if (deviceFeatures != null) {
            return new SCRADeviceCapabilities(deviceFeatures.MSR, deviceFeatures.Contact, deviceFeatures.Contactless, deviceFeatures.ManualEntry, deviceFeatures.MSRPowerSaver, deviceFeatures.BatteryBackedClock);
        }
        return null;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public IDeviceControl getDeviceControl() {
        if (this.mDeviceControl == null) {
            this.mDeviceControl = new SCRADeviceControl(getMTSCRA());
        }
        return this.mDeviceControl;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public DeviceInfo getDeviceInfo() {
        String serial = this.mDeviceInfo.getSerial();
        if (serial == null || serial.isEmpty()) {
            try {
                this.mDeviceInfo = new DeviceInfo(this.mDeviceInfo.getName(), this.mDeviceInfo.getModel(), getMTSCRA().getDeviceSerial());
            } catch (Exception unused) {
            }
        }
        return this.mDeviceInfo;
    }

    private void resetOEMDevice() {
        this.mSpiMsr = null;
        this.mUartNfc = null;
        this.mOEMContactInProgress = false;
        this.mOEMContactlessInProgress = false;
    }

    private void setupOEMDevice() {
        if (getMTSCRA().isDeviceOEM()) {
            this.mOEMContactInProgress = false;
            this.mOEMContactlessInProgress = false;
            if (this.mSpiMsr == null) {
                this.mSpiMsr = new SCRASpiMsr(this.mSCRA, this);
            }
            if (this.mUartNfc == null) {
                this.mUartNfc = new SCRAUartNfc(this.mSCRA, this);
            }
        }
    }

    private String getSetDateTimeCommand() {
        Calendar calendar = Calendar.getInstance();
        return new String("49220000030C001C0000000000000000000000000000000000") + String.format("%1$02x%2$02x%3$02x%4$02x%5$02x00%6$02x", Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(1) - 2008)) + "00000000";
    }

    private void sendSetDateTimeCommand() {
        getDeviceControl().send(new BaseData(getSetDateTimeCommand()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMSRPower(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("5801");
        sb.append(z ? "01" : "00");
        getDeviceControl().send(new BaseData(sb.toString()));
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean startTransaction(ITransaction iTransaction) {
        if (iTransaction == null) {
            return true;
        }
        this.mTransaction = iTransaction;
        new Thread() { // from class: com.magtek.mobile.android.mtusdk.scra.SCRADevice.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                List<PaymentMethod> listPaymentMethods;
                if (!SCRADevice.this.checkConnectedDevice() || (listPaymentMethods = SCRADevice.this.mTransaction.PaymentMethods()) == null) {
                    return;
                }
                if (listPaymentMethods.size() == 1 && listPaymentMethods.get(0) == PaymentMethod.MSR && !SCRADevice.this.mTransaction.EMVOnly()) {
                    if (SCRADevice.this.getMTSCRA().getDeviceFeatures().MSRPowerSaver) {
                        SCRADevice.this.setMSRPower(true);
                    }
                } else {
                    SCRADevice sCRADevice = SCRADevice.this;
                    sCRADevice.startEMVTransaction(sCRADevice.mTransaction);
                }
            }
        }.start();
        return true;
    }

    private byte getCardTypeValue(List<PaymentMethod> list) {
        int i;
        byte b = 0;
        if (list != null) {
            ListIterator<PaymentMethod> listIterator = list.listIterator();
            while (listIterator.hasNext()) {
                PaymentMethod next = listIterator.next();
                if (next == PaymentMethod.MSR) {
                    i = b | 1;
                } else if (next == PaymentMethod.Contact) {
                    i = b | 2;
                } else if (next == PaymentMethod.Contactless && getMTSCRA().getDeviceFeatures().Contactless) {
                    i = b | 4;
                }
                b = (byte) i;
            }
        }
        return b;
    }

    protected boolean startEMVTransaction(ITransaction iTransaction) {
        if (!getMTSCRA().getDeviceFeatures().BatteryBackedClock) {
            sendSetDateTimeCommand();
        }
        final byte cardTypeValue = getCardTypeValue(iTransaction.PaymentMethods());
        final boolean zQuickChip = iTransaction.QuickChip();
        iTransaction.QuickChip();
        final byte[] bArrGetN12Bytes = GetN12Bytes(iTransaction.Amount());
        final byte[] bArrGetN12Bytes2 = GetN12Bytes(iTransaction.CashBack());
        final byte[] bArr = {8, 64};
        if (this.mSCRA.isDeviceOEM()) {
            if (iTransaction.PaymentMethods().contains(PaymentMethod.Contactless)) {
                startOEMTransaction((byte) 60, (byte) 4, zQuickChip ? (byte) -128 : (byte) 0, bArrGetN12Bytes, (byte) 0, bArrGetN12Bytes2, bArr, (byte) 2);
            }
            if (iTransaction.PaymentMethods().contains(PaymentMethod.Contact)) {
                this.mOEMContactInProgress = true;
                new Thread() { // from class: com.magtek.mobile.android.mtusdk.scra.SCRADevice.2
                    @Override // java.lang.Thread, java.lang.Runnable
                    public void run() {
                        SCRADevice.this.getMTSCRA().startTransaction((byte) 60, (byte) 2, zQuickChip ? (byte) -128 : (byte) 0, bArrGetN12Bytes, (byte) 0, bArrGetN12Bytes2, bArr, (byte) 2);
                    }
                }.start();
            }
        } else {
            new Thread() { // from class: com.magtek.mobile.android.mtusdk.scra.SCRADevice.3
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    SCRADevice.this.getMTSCRA().startTransaction((byte) 60, cardTypeValue, zQuickChip ? (byte) -128 : (byte) 0, bArrGetN12Bytes, (byte) 0, bArrGetN12Bytes2, bArr, (byte) 2);
                }
            }.start();
        }
        return true;
    }

    public void startOEMTransaction(byte b, byte b2, byte b3, byte[] bArr, byte b4, byte[] bArr2, byte[] bArr3, byte b5) {
        String str = TAG;
        Log.i(str, str + " startOEMTransaction: ");
        if (this.mSCRA == null || this.mUartNfc == null) {
            return;
        }
        String setDateTimeCommand = getSetDateTimeCommand();
        Log.i(str, str + " Set date time command: " + setDateTimeCommand);
        this.mUartNfc.sendData(setDateTimeCommand);
        final byte[] bArr4 = new byte[19];
        bArr4[0] = b;
        bArr4[1] = b2;
        bArr4[2] = b3;
        int length = bArr.length;
        if (length > 6) {
            length = 6;
        }
        for (int i = 0; i < length; i++) {
            bArr4[i + 3] = bArr[i];
        }
        bArr4[9] = b4;
        int length2 = bArr2.length;
        int i2 = length2 <= 6 ? length2 : 6;
        for (int i3 = 0; i3 < i2; i3++) {
            bArr4[i3 + 10] = bArr2[i3];
        }
        bArr4[16] = bArr3[0];
        bArr4[17] = bArr3[1];
        bArr4[18] = b5;
        String str2 = TAG;
        Log.i(str2, str2 + " command: " + TLVParser.getHexString(bArr4));
        this.mOEMContactlessInProgress = true;
        new Thread() { // from class: com.magtek.mobile.android.mtusdk.scra.SCRADevice.4
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                Log.i(SCRADevice.TAG, SCRADevice.TAG + " thread command: " + TLVParser.getHexString(bArr4));
                SCRADevice.this.sendUartNfcExtendedCommand(new byte[]{3, 0}, bArr4);
            }
        }.start();
    }

    public int sendUartNfcExtendedCommand(byte[] bArr, byte[] bArr2) {
        SCRAUartNfc sCRAUartNfc;
        if (!getMTSCRA().isDeviceConnected() || (sCRAUartNfc = this.mUartNfc) == null) {
            return 9;
        }
        return sCRAUartNfc.sendExtendedCommandBytes(bArr, bArr2);
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean cancelTransaction() {
        if (getMTSCRA().isDeviceOEM()) {
            if (this.mOEMContactlessInProgress) {
                sendUartNfcExtendedCommand(new byte[]{3, 4}, null);
                this.mOEMContactlessInProgress = false;
            }
            if (!this.mOEMContactInProgress) {
                return true;
            }
            getMTSCRA().cancelTransaction();
            this.mOEMContactInProgress = false;
            return true;
        }
        getMTSCRA().cancelTransaction();
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean sendSelection(IData iData) {
        byte[] bArrByteArray = iData.ByteArray();
        if (bArrByteArray != null && bArrByteArray.length >= 2) {
            if (getMTSCRA().isDeviceOEM()) {
                sendUartNfcExtendedCommand(new byte[]{3, 2}, bArrByteArray);
            } else {
                getMTSCRA().setUserSelectionResult(bArrByteArray[0], bArrByteArray[1]);
            }
        }
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean sendAuthorization(IData iData) {
        if (getMTSCRA().isDeviceOEM()) {
            sendUartNfcExtendedCommand(new byte[]{3, 3}, iData.ByteArray());
            return true;
        }
        getMTSCRA().setAcquirerResponse(iData.ByteArray());
        return true;
    }

    protected void OnDeviceStateChanged(MTConnectionState mTConnectionState) {
        ConnectionState connectionState = ConnectionState.Unknown;
        int i = AnonymousClass5.$SwitchMap$com$magtek$mobile$android$mtlib$MTConnectionState[mTConnectionState.ordinal()];
        if (i == 1) {
            connectionState = ConnectionState.Connecting;
        } else if (i == 2) {
            connectionState = ConnectionState.Connected;
            setupOEMDevice();
        } else if (i == 3) {
            connectionState = ConnectionState.Disconnecting;
        } else if (i == 4) {
            connectionState = ConnectionState.Disconnected;
            resetOEMDevice();
        }
        updateConnectionStateValue(connectionState);
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtusdk.scra.SCRADevice$5, reason: invalid class name */
    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtlib$MTConnectionState;
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType;

        static {
            int[] iArr = new int[MTConnectionState.values().length];
            $SwitchMap$com$magtek$mobile$android$mtlib$MTConnectionState = iArr;
            try {
                iArr[MTConnectionState.Connecting.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTConnectionState[MTConnectionState.Connected.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTConnectionState[MTConnectionState.Disconnecting.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTConnectionState[MTConnectionState.Disconnected.ordinal()] = 4;
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
                $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType[ConnectionType.BLUETOOTH_LE_EMVT.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType[ConnectionType.BLUETOOTH_LE.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType[ConnectionType.AIDL.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    protected void OnDeviceResponse(Object obj, String str) {
        sendEvent(EventType.DeviceResponse, new BaseData(str));
    }

    protected void OnDeviceExtendedResponse(Object obj, String str) {
        if (str.length() > 0) {
            byte[] byteArrayFromHexString = TLVParser.getByteArrayFromHexString(str);
            if (this.mSCRA.isDeviceOEM()) {
                SCRASpiMsr sCRASpiMsr = this.mSpiMsr;
                if (sCRASpiMsr != null) {
                    sCRASpiMsr.processDeviceExtendedResponse(byteArrayFromHexString);
                }
                SCRAUartNfc sCRAUartNfc = this.mUartNfc;
                if (sCRAUartNfc != null) {
                    sCRAUartNfc.processDeviceExtendedResponse(byteArrayFromHexString);
                }
            }
        }
    }

    protected void OnDataReceived(Object obj, IMTCardData iMTCardData) {
        if (this.mSCRA.isDeviceOEM()) {
            if (this.mOEMContactlessInProgress) {
                sendUartNfcExtendedCommand(new byte[]{3, 4}, null);
                this.mOEMContactlessInProgress = false;
            }
            if (this.mOEMContactInProgress) {
                this.mSCRA.cancelTransaction();
                this.mOEMContactInProgress = false;
            }
        }
        sendEvent(EventType.DisplayMessage, new BaseData("Card Swiped"));
        sendEvent(EventType.CardData, new BaseData(SCRACardDataBuilder.buildTLVPayload(iMTCardData)));
    }

    protected void OnEMVCommandResult(Object obj, byte[] bArr) {
        if (TLVParser.getHexString(bArr).startsWith("0000")) {
            return;
        }
        sendEvent(EventType.TransactionStatus, new BaseData(TransactionStatusBuilder.TRANSACTION_ERROR));
    }

    protected void OnTransactionStatus(Object obj, byte[] bArr) {
        if (bArr == null || bArr.length < 3) {
            return;
        }
        byte b = bArr[0];
        byte b2 = bArr[2];
        if (b == 1) {
            updateTransactionStatusValue(TransactionStatus.CardInserted);
            if (this.mOEMContactInProgress && this.mOEMContactlessInProgress) {
                sendUartNfcExtendedCommand(new byte[]{3, 4}, null);
                this.mOEMContactlessInProgress = false;
            }
            return;
        }
        if (b == 3) {
            updateTransactionStatusValue(TransactionStatus.TransactionInProgress);
            return;
        }
        switch (b) {
            case 5:
                updateTransactionStatusValue(TransactionStatus.TimedOut);
                break;
            case 6:
                if (b2 == 18) {
                    updateTransactionStatusValue(TransactionStatus.TransactionError);
                } else if (b2 == 19) {
                    updateTransactionStatusValue(TransactionStatus.TransactionApproved);
                } else if (b2 == 20) {
                    updateTransactionStatusValue(TransactionStatus.TransactionDeclined);
                }
                break;
            case 7:
                updateTransactionStatusValue(TransactionStatus.HostCancelled);
                break;
            case 8:
                updateTransactionStatusValue(TransactionStatus.CardRemoved);
                break;
            case 9:
                if (this.mOEMContactlessInProgress && this.mOEMContactInProgress) {
                    this.mSCRA.cancelTransaction();
                    this.mOEMContactInProgress = false;
                    break;
                }
                break;
        }
    }

    protected void OnDisplayMessageRequest(Object obj, byte[] bArr) {
        if (getMTSCRA().isDeviceOEM()) {
            if (obj == this.mUartNfc) {
                if (!this.mOEMContactlessInProgress) {
                    return;
                }
            } else if (!this.mOEMContactInProgress) {
                return;
            }
        }
        sendEvent(EventType.DisplayMessage, new BaseData((bArr == null || bArr.length <= 0) ? "" : TLVParser.getTextString(bArr, 0)));
    }

    protected void OnUserSelectionRequest(Object obj, byte[] bArr) {
        if (getMTSCRA().isDeviceOEM()) {
            if (obj == this.mUartNfc) {
                if (!this.mOEMContactlessInProgress) {
                    return;
                }
            } else if (!this.mOEMContactInProgress) {
                return;
            }
        }
        processSelectionRequest(bArr);
    }

    protected void processSelectionRequest(byte[] bArr) {
        sendEvent(EventType.InputRequest, new BaseData(bArr));
    }

    protected void OnARQCReceived(Object obj, byte[] bArr) {
        int length;
        if (getMTSCRA().isDeviceOEM()) {
            if (obj == this.mUartNfc) {
                if (!this.mOEMContactlessInProgress) {
                    return;
                }
            } else if (!this.mOEMContactInProgress) {
                return;
            }
        }
        if (bArr == null || (length = bArr.length) <= 0) {
            return;
        }
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        sendEvent(EventType.AuthorizationRequest, new BaseData(bArr2));
    }

    protected void OnTransactionResult(Object obj, byte[] bArr) {
        int length;
        if (getMTSCRA().isDeviceOEM()) {
            if (obj == this.mUartNfc) {
                if (!this.mOEMContactlessInProgress) {
                    return;
                }
            } else if (!this.mOEMContactInProgress) {
                return;
            }
        }
        if (bArr == null || (length = bArr.length) <= 3) {
            return;
        }
        byte[] bArr2 = new byte[length - 1];
        System.arraycopy(bArr, 0, bArr2, 0, 2);
        System.arraycopy(bArr, 3, bArr2, 2, length - 3);
        sendEvent(EventType.TransactionResult, new BaseData(bArr2));
    }

    @Override // com.magtek.mobile.android.mtusdk.scra.SCRASpiMsrAdapter
    public void OnCardDataReceived(IMTCardData iMTCardData) {
        OnDataReceived(this.mSpiMsr, iMTCardData);
    }

    @Override // com.magtek.mobile.android.mtusdk.scra.SCRAUartNfcAdapter
    public void OnUartTransactionStatus(byte[] bArr) {
        OnTransactionStatus(this.mUartNfc, bArr);
    }

    @Override // com.magtek.mobile.android.mtusdk.scra.SCRAUartNfcAdapter
    public void OnUartDisplayMessageRequest(byte[] bArr) {
        OnDisplayMessageRequest(this.mUartNfc, bArr);
    }

    @Override // com.magtek.mobile.android.mtusdk.scra.SCRAUartNfcAdapter
    public void OnUartUserSelectionRequest(byte[] bArr) {
        OnUserSelectionRequest(this.mUartNfc, bArr);
    }

    @Override // com.magtek.mobile.android.mtusdk.scra.SCRAUartNfcAdapter
    public void OnUartARQCReceived(byte[] bArr) {
        OnARQCReceived(this.mUartNfc, bArr);
    }

    @Override // com.magtek.mobile.android.mtusdk.scra.SCRAUartNfcAdapter
    public void OnUartTransactionResult(byte[] bArr) {
        OnTransactionResult(this.mUartNfc, bArr);
    }
}
