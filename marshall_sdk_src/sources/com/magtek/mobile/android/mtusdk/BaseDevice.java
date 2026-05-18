package com.magtek.mobile.android.mtusdk;

import android.content.Context;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import java.text.DecimalFormat;

/* JADX INFO: loaded from: classes.dex */
public class BaseDevice extends EventPublisher implements IDevice {
    protected Context mContext = null;
    protected ConnectionInfo mConnectionInfo = null;
    protected ConnectionState mConnectionState = ConnectionState.Disconnected;
    protected DeviceInfo mDeviceInfo = null;
    protected IDeviceControl mDeviceControl = null;
    protected IDeviceConfiguration mDeviceConfiguration = null;
    protected TransactionStatus mTransactionStatus = TransactionStatus.NoTransaction;
    protected Object mConnectedEvent = new Object();

    @Override // com.magtek.mobile.android.mtusdk.IDevice
    public boolean cancelTransaction() {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDevice
    public IDeviceCapabilities getCapabilities() {
        return null;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDevice
    public boolean requestPAN(PANRequest pANRequest, PINRequest pINRequest) {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDevice
    public boolean requestPIN(PINRequest pINRequest) {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDevice
    public boolean requestSignature() {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDevice
    public boolean sendAuthorization(IData iData) {
        return false;
    }

    protected void sendCardData(String str) {
    }

    protected void sendEMVTransactionResult(String str) {
    }

    @Override // com.magtek.mobile.android.mtusdk.IDevice
    public boolean sendSelection(IData iData) {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDevice
    public boolean startTransaction(ITransaction iTransaction) {
        return false;
    }

    public BaseDevice() {
        init(null, null, new DeviceInfo("", "", ""));
    }

    public BaseDevice(Context context) {
        init(context, null, new DeviceInfo("", "", ""));
    }

    public BaseDevice(Context context, ConnectionInfo connectionInfo) {
        init(context, connectionInfo, new DeviceInfo("", "", ""));
    }

    public BaseDevice(Context context, ConnectionInfo connectionInfo, DeviceInfo deviceInfo) {
        init(context, connectionInfo, deviceInfo);
    }

    protected void init(Context context, ConnectionInfo connectionInfo, DeviceInfo deviceInfo) {
        this.mContext = context;
        this.mConnectionInfo = connectionInfo;
        this.mDeviceInfo = deviceInfo;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDevice
    public String Name() {
        DeviceInfo deviceInfo = this.mDeviceInfo;
        return deviceInfo != null ? deviceInfo.getName() : "NA";
    }

    @Override // com.magtek.mobile.android.mtusdk.IDevice
    public ConnectionInfo getConnectionInfo() {
        return this.mConnectionInfo;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDevice
    public ConnectionState getConnectionState() {
        return this.mConnectionState;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDevice
    public DeviceInfo getDeviceInfo() {
        return this.mDeviceInfo;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDevice
    public boolean subscribeAll(IEventSubscriber iEventSubscriber) {
        return addSubscriber(iEventSubscriber);
    }

    @Override // com.magtek.mobile.android.mtusdk.IDevice
    public boolean unsubscribeAll(IEventSubscriber iEventSubscriber) {
        return removeSubscriber(iEventSubscriber);
    }

    @Override // com.magtek.mobile.android.mtusdk.IDevice
    public IDeviceControl getDeviceControl() {
        return this.mDeviceControl;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDevice
    public IDeviceConfiguration getDeviceConfiguration() {
        return this.mDeviceConfiguration;
    }

    private static String leftPad(String str, int i, char c) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() + str.length() < 10) {
            sb.append(c);
        }
        sb.append(str);
        return sb.toString();
    }

    protected static String GetN12String(String str) {
        double d;
        try {
            d = Double.parseDouble(str);
        } catch (Exception unused) {
            d = 0.0d;
        }
        return leftPad(new DecimalFormat("0000000000.00").format(d).replace(",", "").replace(".", ""), 12, '0');
    }

    protected static byte[] GetN12Bytes(String str) {
        return TLVParser.getByteArrayFromHexString(GetN12String(str));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.magtek.mobile.android.mtusdk.EventPublisher
    public void sendEvent(EventType eventType, IData iData) {
        super.sendEvent(eventType, iData);
    }

    protected void updateConnectionStateValue(ConnectionState connectionState) {
        this.mConnectionState = connectionState;
        if (connectionState == ConnectionState.Connected) {
            synchronized (this.mConnectedEvent) {
                this.mConnectedEvent.notifyAll();
            }
        }
        sendEvent(EventType.ConnectionState, new BaseData(ConnectionStateBuilder.GetString(connectionState)));
    }

    protected void updateTransactionStatusValue(TransactionStatus transactionStatus) {
        this.mTransactionStatus = transactionStatus;
        sendEvent(EventType.TransactionStatus, new BaseData(TransactionStatusBuilder.GetString(transactionStatus)));
    }

    protected void updateTransactionStatusValue(TransactionStatus transactionStatus, String str, String str2) {
        this.mTransactionStatus = transactionStatus;
        sendEvent(EventType.TransactionStatus, new BaseData(TransactionStatusBuilder.GetString(transactionStatus) + "," + str + "," + str2));
    }

    protected void sendDeviceEvent(DeviceEvent deviceEvent, String str) {
        String strGetString = DeviceEventBuilder.GetString(deviceEvent);
        if (str != null && !str.isEmpty()) {
            strGetString = strGetString + "," + str;
        }
        sendEvent(EventType.DeviceEvent, new BaseData(strGetString));
    }

    protected void sendUserEventValue(UserEvent userEvent) {
        sendEvent(EventType.UserEvent, new BaseData(UserEventBuilder.GetString(userEvent)));
    }

    protected void sendFeatureStatusEvent(DeviceFeature deviceFeature, FeatureStatus featureStatus) {
        sendEvent(EventType.FeatureStatus, new BaseData(FeatureStatusBuilder.GetFeatureString(deviceFeature) + "," + FeatureStatusBuilder.GetStatusString(featureStatus)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean checkConnectedDevice() throws Throwable {
        if (this.mConnectionState == ConnectionState.Connected) {
            return true;
        }
        if (this.mConnectionState == ConnectionState.Disconnected) {
            getDeviceControl().open();
        }
        try {
            try {
                synchronized (this.mConnectedEvent) {
                    try {
                        this.mConnectedEvent.wait(5000L);
                        z = this.mConnectionState == ConnectionState.Connected;
                        return z;
                    } catch (Throwable th) {
                        th = th;
                        z = false;
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                throw th;
            } catch (Exception unused) {
                return z;
            }
        } catch (Exception unused2) {
            return false;
        }
    }
}
