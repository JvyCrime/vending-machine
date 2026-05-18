package com.magtek.mobile.android.mtusdk.cms;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.magtek.mobile.android.mtcms.MTCMSMessage;
import com.magtek.mobile.android.mtcms.MTCMSNotificationMessage;
import com.magtek.mobile.android.mtcms.MTCMSRequestMessage;
import com.magtek.mobile.android.mtcms.MTCMSResponseMessage;
import com.magtek.mobile.android.mtcms.MTConnectionState;
import com.magtek.mobile.android.mtcms.MTConnectionType;
import com.magtek.mobile.android.mtcms.MTDevice;
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
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: loaded from: classes.dex */
public class CMSDevice extends BaseDevice {
    protected static final int BIG_BLOCK_DATA_SIZE = 900;
    protected static final int BIG_BLOCK_DATA_SMALL_SIZE = 45;
    private static final String TAG = "CMSDevice";
    protected int mReceivingBigBlockDataLastPacketID;
    protected int mReceivingBigBlockDataReceivedLength;
    protected int mReceivingBigBlockDataTotalLength;
    protected byte[] mRecevingBigBlockData;
    protected boolean mSendingBigBlockData = false;
    protected byte[] mBigBlockData = null;
    protected int mBigBlockByteCount = 0;
    protected int mBigBlockPacketCount = 0;
    private MTDevice mMTDevice = null;
    private String mDeviceSerial = "";
    private Object mDeviceSerialObject = null;
    protected ITransaction mTransaction = null;
    private Handler mCMSHandler = new Handler(new CMSHandlerCallback());

    private void processTransactionStatus(byte[] bArr) {
    }

    protected void OnDeviceDataBytes(byte[] bArr) {
    }

    public CMSDevice(Context context, ConnectionInfo connectionInfo) {
        init(context, connectionInfo, new DeviceInfo("", "", ""));
    }

    public CMSDevice(Context context, ConnectionInfo connectionInfo, DeviceInfo deviceInfo) {
        init(context, connectionInfo, deviceInfo);
    }

    private class CMSHandlerCallback implements Handler.Callback {
        private CMSHandlerCallback() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            try {
                Log.i(CMSDevice.TAG, "*** Callback " + message.what);
                int i = message.what;
                if (i == 0) {
                    CMSDevice.this.updateState((MTConnectionState) message.obj);
                } else if (i == 1) {
                    CMSDevice.this.OnDeviceDataString((String) message.obj);
                } else if (i == 2) {
                    CMSDevice.this.OnDeviceDataBytes((byte[]) message.obj);
                } else if (i == 3) {
                    CMSDevice.this.OnDeviceResponseMessage((MTCMSResponseMessage) message.obj);
                } else if (i == 4) {
                    CMSDevice.this.OnDeviceNotificationMessage((MTCMSNotificationMessage) message.obj);
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

    private MTDevice getMTDevice() {
        String address;
        if (this.mMTDevice == null) {
            ConnectionType connectionType = ConnectionType.USB;
            if (this.mConnectionInfo != null) {
                connectionType = this.mConnectionInfo.getConnectionType();
                address = this.mConnectionInfo.getAddress();
            } else {
                address = "";
            }
            MTDevice mTDevice = new MTDevice(this.mContext, this.mCMSHandler);
            this.mMTDevice = mTDevice;
            mTDevice.setConnectionType(getMTConnectionType(connectionType));
            this.mMTDevice.setAddress(address);
            this.mSendingBigBlockData = false;
        }
        return this.mMTDevice;
    }

    protected static MTConnectionType getMTConnectionType(ConnectionType connectionType) {
        MTConnectionType mTConnectionType = MTConnectionType.USB;
        int i = AnonymousClass3.$SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType[connectionType.ordinal()];
        if (i != 1) {
            return i != 2 ? mTConnectionType : MTConnectionType.IP;
        }
        return MTConnectionType.USB;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public IDeviceCapabilities getCapabilities() {
        return new CMSDeviceCapabilities();
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public IDeviceControl getDeviceControl() {
        if (this.mDeviceControl == null) {
            this.mDeviceControl = new CMSDeviceControl(getMTDevice());
        }
        return this.mDeviceControl;
    }

    public String getDeviceSerial() {
        String str;
        this.mDeviceSerialObject = new Object();
        try {
            sendToDevice(new MTCMSRequestMessage(0, 40, 196, null));
            this.mDeviceSerial = "QQQQ";
            synchronized (this.mDeviceSerialObject) {
                this.mDeviceSerialObject.wait(3000L);
                str = this.mDeviceSerial;
            }
        } catch (InterruptedException unused) {
            str = "YYYY";
        }
        this.mDeviceSerialObject = null;
        return str;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public DeviceInfo getDeviceInfo() {
        String serial = this.mDeviceInfo.getSerial();
        if (serial == null || serial.isEmpty()) {
            try {
                this.mDeviceInfo = new DeviceInfo(this.mDeviceInfo.getName(), this.mDeviceInfo.getModel(), getDeviceSerial());
            } catch (Exception unused) {
            }
        }
        return this.mDeviceInfo;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean startTransaction(ITransaction iTransaction) {
        if (iTransaction == null) {
            return true;
        }
        this.mTransaction = iTransaction;
        new Thread() { // from class: com.magtek.mobile.android.mtusdk.cms.CMSDevice.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                if (CMSDevice.this.checkConnectedDevice()) {
                    try {
                        Thread.sleep(1000L);
                    } catch (Exception unused) {
                    }
                    List<PaymentMethod> listPaymentMethods = CMSDevice.this.mTransaction.PaymentMethods();
                    if (listPaymentMethods != null) {
                        if (listPaymentMethods.size() == 1 && listPaymentMethods.get(0) == PaymentMethod.MSR && !CMSDevice.this.mTransaction.EMVOnly()) {
                            CMSDevice.this.sendEvent(EventType.DisplayMessage, new BaseData("READY FOR CARD SWIPE"));
                        } else {
                            CMSDevice cMSDevice = CMSDevice.this;
                            cMSDevice.startEMVTransaction(cMSDevice.mTransaction);
                        }
                    }
                }
            }
        }.start();
        return true;
    }

    protected boolean startEMVTransaction(ITransaction iTransaction) {
        byte[] bArr = new byte[19];
        for (int i = 0; i < 19; i++) {
            bArr[i] = 0;
        }
        bArr[0] = 48;
        bArr[1] = getCardTypeValue(iTransaction.PaymentMethods());
        bArr[2] = iTransaction.QuickChip() ? (byte) -128 : (byte) 0;
        System.arraycopy(GetN12Bytes(iTransaction.Amount()), 0, bArr, 3, 6);
        bArr[9] = 0;
        System.arraycopy(GetN12Bytes(iTransaction.CashBack()), 0, bArr, 10, 6);
        System.arraycopy(new byte[]{8, 64}, 0, bArr, 16, 2);
        bArr[18] = 2;
        sendToDevice(new MTCMSRequestMessage(7, 0, 196, bArr));
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean cancelTransaction() {
        sendToDevice(new MTCMSRequestMessage(7, 4, 196, null));
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean sendSelection(IData iData) {
        byte[] bArrByteArray;
        if (iData == null || (bArrByteArray = iData.ByteArray()) == null || bArrByteArray.length < 2) {
            return true;
        }
        sendToDevice(new MTCMSRequestMessage(7, 2, 196, bArrByteArray));
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean sendAuthorization(IData iData) {
        Log.i(TAG, "*** sendAuthorization");
        byte[] bArrByteArray = iData.ByteArray();
        MTCMSRequestMessage mTCMSRequestMessage = new MTCMSRequestMessage(7, 3, 196, bArrByteArray);
        if (bArrByteArray.length > getBigBlockDataSize()) {
            sendBigBlocksToDevice(mTCMSRequestMessage);
            return true;
        }
        sendToDevice(mTCMSRequestMessage);
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
                } else if (next == PaymentMethod.Contactless) {
                    i = b | 4;
                }
                b = (byte) i;
            }
        }
        return b;
    }

    private int sendToDevice(MTCMSMessage mTCMSMessage) {
        return getMTDevice().sendMTCMSMessage(mTCMSMessage);
    }

    private void setupSubscription(boolean z) {
        byte[] bArr = new byte[2];
        bArr[0] = z ? (byte) 1 : (byte) 2;
        bArr[1] = 0;
        sendToDevice(new MTCMSRequestMessage(1, 80, 196, bArr));
    }

    private void requestMSRData() {
        sendToDevice(new MTCMSRequestMessage(4, 18, 196, null));
    }

    private void requestPAN() {
        sendToDevice(new MTCMSRequestMessage(5, 1, 196, null));
    }

    protected void updateState(MTConnectionState mTConnectionState) {
        ConnectionState connectionState = ConnectionState.Unknown;
        int i = AnonymousClass3.$SwitchMap$com$magtek$mobile$android$mtcms$MTConnectionState[mTConnectionState.ordinal()];
        if (i == 1) {
            connectionState = ConnectionState.Connecting;
        } else if (i == 2) {
            connectionState = ConnectionState.Connected;
            resetReceivingBigBlockData();
            setupSubscription(true);
        } else if (i == 3) {
            connectionState = ConnectionState.Disconnecting;
        } else if (i == 4) {
            connectionState = ConnectionState.Disconnected;
        }
        updateConnectionStateValue(connectionState);
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtusdk.cms.CMSDevice$3, reason: invalid class name */
    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtcms$MTConnectionState;
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType;

        static {
            int[] iArr = new int[MTConnectionState.values().length];
            $SwitchMap$com$magtek$mobile$android$mtcms$MTConnectionState = iArr;
            try {
                iArr[MTConnectionState.Connecting.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTConnectionState[MTConnectionState.Connected.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTConnectionState[MTConnectionState.Disconnecting.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtcms$MTConnectionState[MTConnectionState.Disconnected.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[ConnectionType.values().length];
            $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType = iArr2;
            try {
                iArr2[ConnectionType.USB.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType[ConnectionType.TCP.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    protected void OnDeviceDataString(String str) {
        sendEvent(EventType.DeviceResponse, new BaseData(str));
    }

    protected void OnDeviceResponseMessage(MTCMSResponseMessage mTCMSResponseMessage) {
        processResponseMessage(mTCMSResponseMessage);
    }

    protected void OnDeviceNotificationMessage(MTCMSNotificationMessage mTCMSNotificationMessage) {
        processNotificationMessage(mTCMSNotificationMessage);
    }

    private void processResponseMessage(MTCMSResponseMessage mTCMSResponseMessage) {
        int applicationID = mTCMSResponseMessage.getApplicationID();
        int commandID = mTCMSResponseMessage.getCommandID();
        int resultCode = mTCMSResponseMessage.getResultCode();
        byte[] data = mTCMSResponseMessage.getData();
        if (applicationID == 0) {
            processDeviceInfoResponse(commandID, resultCode, data);
            return;
        }
        if (applicationID == 1) {
            processGeneralResponse(commandID, resultCode, data);
        } else if (applicationID == 4) {
            processMSRResponse(commandID, resultCode, data);
        } else {
            if (applicationID != 5) {
                return;
            }
            processPANResponse(commandID, resultCode, data);
        }
    }

    private void processNotificationMessage(MTCMSNotificationMessage mTCMSNotificationMessage) {
        int applicationID = mTCMSNotificationMessage.getApplicationID();
        int commandID = mTCMSNotificationMessage.getCommandID();
        int resultCode = mTCMSNotificationMessage.getResultCode();
        byte[] data = mTCMSNotificationMessage.getData();
        if (applicationID == 1) {
            processGeneralNotification(commandID, resultCode, data);
        } else if (applicationID == 4) {
            processMSRNotification(commandID, resultCode, data);
        } else {
            if (applicationID != 7) {
                return;
            }
            processEMVL2Notification(commandID, resultCode, data);
        }
    }

    private void processDeviceInfoResponse(int i, int i2, byte[] bArr) {
        if (i != 40) {
            return;
        }
        this.mDeviceSerial = TLVParser.getTextString(bArr, 0);
        Object obj = this.mDeviceSerialObject;
        if (obj != null) {
            synchronized (obj) {
                this.mDeviceSerialObject.notifyAll();
            }
        }
    }

    private void processGeneralResponse(int i, int i2, byte[] bArr) {
        if (i != 16) {
            return;
        }
        processSendBigBlockDataResponse(i2);
    }

    private void processMSRResponse(int i, int i2, byte[] bArr) {
        if (i != 18) {
            return;
        }
        processMSRData(i2, bArr);
    }

    private void processMSRData(int i, byte[] bArr) {
        sendMSRData(bArr);
    }

    private void sendMSRData(byte[] bArr) {
        sendEvent(EventType.CardData, new BaseData(TLVParser.getHexString(bArr)));
    }

    private void processPANResponse(int i, int i2, byte[] bArr) {
        if (i != 1) {
            return;
        }
        processRequestPANResponse(bArr);
    }

    private void processRequestPANResponse(byte[] bArr) {
        getPINCVM();
    }

    private void getPINCVM() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.magtek.mobile.android.mtusdk.cms.CMSDevice.2
            @Override // java.lang.Runnable
            public void run() {
                CMSDevice.this.sendPINCVMResponse(new byte[]{52, 18, 52, 86, PPSCRADeviceValues.FUNCTION_KEY_ENTER});
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendPINCVMResponse(byte[] bArr) {
        if (bArr != null) {
            int length = bArr.length;
            byte[] bArr2 = new byte[length + 1];
            bArr2[0] = 0;
            System.arraycopy(bArr, 0, bArr2, 1, length);
            sendToDevice(new MTCMSResponseMessage(7, 136, 0, 196, bArr2));
            return;
        }
        sendToDevice(new MTCMSResponseMessage(7, 136, 0, 196, new byte[]{2}));
    }

    private void processSendBigBlockDataResponse(int i) {
        if (this.mSendingBigBlockData) {
            if (i == 0) {
                uploadNextDataBlock();
            } else {
                this.mSendingBigBlockData = false;
            }
        }
    }

    private void processGeneralNotification(int i, int i2, byte[] bArr) {
        if (i != 16) {
            return;
        }
        processBigBlockDataNotification(bArr);
    }

    private void processMSRNotification(int i, int i2, byte[] bArr) {
        if (i == 17) {
            requestMSRData();
        } else {
            if (i != 18) {
                return;
            }
            processMSRData(i2, bArr);
        }
    }

    private void processEMVL2Notification(int i, int i2, byte[] bArr) {
        if (i != 136) {
            switch (i) {
                case 128:
                    processTransactionStatus(bArr);
                    break;
                case 129:
                    processDisplayMessageRequest(bArr);
                    break;
                case 130:
                    processUserSelectionRequest(bArr);
                    break;
                case 131:
                    processARQC(bArr);
                    break;
                case 132:
                    processTransactionResult(bArr);
                    break;
            }
        }
        processPINCVMRequest();
    }

    private void processPINCVMRequest() {
        requestPAN();
    }

    private void processDisplayMessageRequest(byte[] bArr) {
        sendEvent(EventType.DisplayMessage, new BaseData((bArr == null || bArr.length <= 0) ? "" : TLVParser.getTextString(bArr, 0)));
    }

    protected void processUserSelectionRequest(byte[] bArr) {
        sendEvent(EventType.InputRequest, new BaseData(bArr));
    }

    protected void processARQC(byte[] bArr) {
        int length;
        if (bArr == null || (length = bArr.length) <= 0) {
            return;
        }
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        sendEvent(EventType.AuthorizationRequest, new BaseData(bArr2));
    }

    protected void processTransactionResult(byte[] bArr) {
        int length;
        if (bArr == null || (length = bArr.length) <= 0) {
            return;
        }
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        sendEvent(EventType.TransactionResult, new BaseData(bArr2));
    }

    private void resetReceivingBigBlockData() {
        this.mReceivingBigBlockDataTotalLength = 0;
        this.mReceivingBigBlockDataReceivedLength = 0;
        this.mReceivingBigBlockDataLastPacketID = 0;
        this.mRecevingBigBlockData = null;
    }

    private void processBigBlockDataNotification(byte[] bArr) {
        int length;
        int i;
        int i2;
        if (bArr == null || (length = bArr.length) < 2) {
            return;
        }
        int i3 = (bArr[1] << 8) + bArr[0];
        if (i3 == 0) {
            this.mReceivingBigBlockDataLastPacketID = 0;
            this.mReceivingBigBlockDataReceivedLength = 0;
            if (length < 4 || (i2 = (bArr[3] << 8) + bArr[2]) <= 0 || length < i2 + 4) {
                return;
            }
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, 4, bArr2, 0, i2);
            int i4 = 0;
            for (int i5 = 0; i5 < i2; i5++) {
                i4 += (bArr2[i5] & 255) << (i5 * 8);
            }
            if (i4 > 0) {
                this.mRecevingBigBlockData = new byte[i4];
                for (int i6 = 0; i6 < i4; i6++) {
                    this.mRecevingBigBlockData[i6] = 0;
                }
                this.mReceivingBigBlockDataTotalLength = i4;
                return;
            }
            return;
        }
        if (i3 == this.mReceivingBigBlockDataLastPacketID + 1) {
            this.mReceivingBigBlockDataLastPacketID = i3;
            if (length < 4 || (i = (bArr[3] << 8) + bArr[2]) <= 0 || length < i + 4) {
                return;
            }
            System.arraycopy(bArr, 4, this.mRecevingBigBlockData, this.mReceivingBigBlockDataReceivedLength, i);
            int i7 = this.mReceivingBigBlockDataReceivedLength + i;
            this.mReceivingBigBlockDataReceivedLength = i7;
            if (i7 >= this.mReceivingBigBlockDataTotalLength) {
                MTCMSMessage mTCMSMessage = new MTCMSMessage(this.mRecevingBigBlockData);
                if (mTCMSMessage.getMessageType() == 2) {
                    processResponseMessage(new MTCMSResponseMessage(mTCMSMessage.getApplicationID(), mTCMSMessage.getCommandID(), mTCMSMessage.getResultCode(), mTCMSMessage.getDataTag(), mTCMSMessage.getData()));
                } else if (mTCMSMessage.getMessageType() == 3) {
                    processNotificationMessage(new MTCMSNotificationMessage(mTCMSMessage.getApplicationID(), mTCMSMessage.getCommandID(), mTCMSMessage.getResultCode(), mTCMSMessage.getDataTag(), mTCMSMessage.getData()));
                }
            }
        }
    }

    private int sendBigBlocksToDevice(MTCMSMessage mTCMSMessage) {
        if (this.mSendingBigBlockData) {
            return -1;
        }
        this.mBigBlockData = mTCMSMessage.getMessageBytes();
        this.mBigBlockByteCount = 0;
        this.mBigBlockPacketCount = 0;
        sendBigBlockDataLength();
        return 0;
    }

    private int getBigBlockDataLength() {
        byte[] bArr = this.mBigBlockData;
        if (bArr != null) {
            return bArr.length;
        }
        return 0;
    }

    private void sendBigBlockDataLength() {
        int bigBlockDataLength = getBigBlockDataLength();
        if (bigBlockDataLength > 0) {
            this.mSendingBigBlockData = true;
            byte[] arrayBytes = getArrayBytes(getPacketIDByteArray(0), getLengthByteArray(2, 4), getLengthByteArray(4, bigBlockDataLength));
            this.mBigBlockPacketCount = 1;
            sendBigBlockDataCommand(arrayBytes);
        }
    }

    private void sendBigBlockDataCommand(byte[] bArr) {
        sendToDevice(new MTCMSRequestMessage(1, 16, 196, bArr));
    }

    private byte[] buildBigBlockDataBytes(int i, byte[] bArr) {
        if (bArr != null) {
            return getArrayBytes(getPacketIDByteArray(i), getLengthByteArray(2, bArr.length), bArr);
        }
        return null;
    }

    private int getBigBlockDataSize() {
        return this.mConnectionInfo.getConnectionType() == ConnectionType.USB ? 45 : 900;
    }

    private boolean uploadNextDataBlock() {
        int bigBlockDataLength = getBigBlockDataLength();
        int i = this.mBigBlockByteCount;
        if (i < bigBlockDataLength) {
            int i2 = bigBlockDataLength - i;
            int bigBlockDataSize = getBigBlockDataSize();
            if (i2 > bigBlockDataSize) {
                i2 = bigBlockDataSize;
            }
            byte[] bArr = new byte[i2];
            System.arraycopy(this.mBigBlockData, this.mBigBlockByteCount, bArr, 0, i2);
            byte[] bArrBuildBigBlockDataBytes = buildBigBlockDataBytes(this.mBigBlockPacketCount, bArr);
            this.mBigBlockPacketCount++;
            this.mBigBlockByteCount += i2;
            sendBigBlockDataCommand(bArrBuildBigBlockDataBytes);
            return true;
        }
        this.mSendingBigBlockData = false;
        return false;
    }

    private byte[] getLengthByteArray(int i, int i2) {
        byte[] bArr = new byte[i];
        for (int i3 = 0; i3 < i; i3++) {
            bArr[i3] = 0;
        }
        if (i2 > 0) {
            for (int i4 = 0; i4 < i; i4++) {
                bArr[i4] = (byte) ((i2 >> (i4 * 8)) & 255);
            }
        }
        return bArr;
    }

    private byte[] getPacketIDByteArray(int i) {
        return getLengthByteArray(2, i);
    }

    private byte[] getArrayBytes(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        int length;
        int length2 = bArr != null ? bArr.length + 0 : 0;
        if (bArr2 != null) {
            length2 += bArr2.length;
        }
        if (bArr3 != null) {
            length2 += bArr3.length;
        }
        byte[] bArr4 = null;
        if (length2 > 0) {
            bArr4 = new byte[length2];
            if (bArr != null) {
                System.arraycopy(bArr, 0, bArr4, 0, bArr.length);
                length = bArr.length + 0;
            } else {
                length = 0;
            }
            if (bArr2 != null) {
                System.arraycopy(bArr2, 0, bArr4, length, bArr2.length);
                length += bArr2.length;
            }
            if (bArr3 != null) {
                System.arraycopy(bArr3, 0, bArr4, length, bArr3.length);
                int length3 = bArr3.length;
            }
        }
        return bArr4;
    }
}
