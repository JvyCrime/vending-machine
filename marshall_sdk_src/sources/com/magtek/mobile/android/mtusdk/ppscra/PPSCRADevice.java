package com.magtek.mobile.android.mtusdk.ppscra;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.magtek.mobile.android.mtusdk.PINRequest;
import com.magtek.mobile.android.mtusdk.PaymentMethod;
import com.magtek.mobile.android.mtusdk.TransactionBuilder;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import com.magtek.mobile.android.ppscra.MTConnectionState;
import com.magtek.mobile.android.ppscra.MTConnectionType;
import com.magtek.mobile.android.ppscra.MagTekPPSCRA;
import com.magtek.mobile.android.ppscra.PINData;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: loaded from: classes.dex */
public class PPSCRADevice extends BaseDevice {
    private static final String TAG = "PPSCRADevice";
    private MagTekPPSCRA mMagTekPPSCRA = null;
    protected ITransaction mTransaction = null;
    private Handler mPPSCRAHandler = new Handler(new PPSCRAHandlerCallback());

    public PPSCRADevice(Context context, ConnectionInfo connectionInfo) {
        init(context, connectionInfo, new DeviceInfo("", "", ""));
    }

    public PPSCRADevice(Context context, ConnectionInfo connectionInfo, DeviceInfo deviceInfo) {
        init(context, connectionInfo, deviceInfo);
    }

    private class PPSCRAHandlerCallback implements Handler.Callback {
        private PPSCRAHandlerCallback() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            try {
                int i = message.what;
                if (i == 11) {
                    Log.i(PPSCRADevice.TAG, "OnDeviceConnectionStateChanged");
                    PPSCRADevice.this.OnDeviceStateChanged((MTConnectionState) message.obj);
                } else if (i == 24) {
                    Log.i(PPSCRADevice.TAG, "OnDataChange");
                    PPSCRADevice.this.OnDataChange();
                } else if (i == 53) {
                    Log.i(PPSCRADevice.TAG, "OnPINData");
                    PPSCRADevice.this.OnPINData((PINData) message.obj);
                } else if (i == 64) {
                    Log.i(PPSCRADevice.TAG, "OnSignatureData");
                    PPSCRADevice.this.OnSignatureData((byte[]) message.obj);
                } else if (i == 71) {
                    PPSCRADevice.this.OnARQCRequest((byte[]) message.obj);
                } else if (i == 72) {
                    PPSCRADevice.this.OnEMVData((byte[]) message.obj);
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }
        }
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice
    protected void init(Context context, ConnectionInfo connectionInfo, DeviceInfo deviceInfo) {
        this.mContext = context;
        this.mConnectionInfo = connectionInfo;
        this.mDeviceInfo = deviceInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MagTekPPSCRA getMagTekPPSCRA() {
        String address;
        if (this.mMagTekPPSCRA == null) {
            ConnectionType connectionType = ConnectionType.USB;
            if (this.mConnectionInfo != null) {
                connectionType = this.mConnectionInfo.getConnectionType();
                address = this.mConnectionInfo.getAddress();
            } else {
                address = "";
            }
            MagTekPPSCRA magTekPPSCRA = new MagTekPPSCRA(this.mContext, this.mPPSCRAHandler);
            this.mMagTekPPSCRA = magTekPPSCRA;
            magTekPPSCRA.setConnectionType(getMTConnectionType(connectionType));
            this.mMagTekPPSCRA.setDeviceAddress(address);
        }
        return this.mMagTekPPSCRA;
    }

    protected static MTConnectionType getMTConnectionType(ConnectionType connectionType) {
        MTConnectionType mTConnectionType = MTConnectionType.USB;
        int i = AnonymousClass4.$SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType[connectionType.ordinal()];
        if (i == 1) {
            return MTConnectionType.USB;
        }
        if (i == 2) {
            return MTConnectionType.BLEEMV;
        }
        if (i == 3) {
            return MTConnectionType.Net;
        }
        if (i != 4) {
            return i != 5 ? mTConnectionType : MTConnectionType.Net_TLS12_Trust_All;
        }
        return MTConnectionType.Net_TLS12;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public IDeviceCapabilities getCapabilities() {
        return new PPSCRADeviceCapabilities(TransactionBuilder.GetPaymentMethods(true, true, true, true), true, true, true, true, true);
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public IDeviceControl getDeviceControl() {
        if (this.mDeviceControl == null) {
            this.mDeviceControl = new PPSCRADeviceControl(getMagTekPPSCRA());
        }
        return this.mDeviceControl;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public DeviceInfo getDeviceInfo() {
        String serial = this.mDeviceInfo.getSerial();
        if (serial == null || serial.isEmpty()) {
            try {
                byte[] deviceSerial = getMagTekPPSCRA().getDeviceSerial();
                if (deviceSerial != null) {
                    this.mDeviceInfo = new DeviceInfo(this.mDeviceInfo.getName(), this.mDeviceInfo.getModel(), TLVParser.getHexString(deviceSerial));
                }
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
        new Thread() { // from class: com.magtek.mobile.android.mtusdk.ppscra.PPSCRADevice.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                if (PPSCRADevice.this.checkConnectedDevice()) {
                    PPSCRADevice.this.sendEvent(EventType.DisplayMessage, new BaseData("PLEASE FOLLOW INSTRUCTION SHOWN ON THE READER"));
                    try {
                        Thread.sleep(500L);
                    } catch (Exception unused) {
                    }
                    List<PaymentMethod> listPaymentMethods = PPSCRADevice.this.mTransaction.PaymentMethods();
                    if (listPaymentMethods != null) {
                        if (listPaymentMethods.size() == 1 && listPaymentMethods.get(0) == PaymentMethod.ManualEntry) {
                            PPSCRADevice pPSCRADevice = PPSCRADevice.this;
                            pPSCRADevice.startManualEntry(pPSCRADevice.mTransaction);
                        } else if (listPaymentMethods.size() == 1 && listPaymentMethods.get(0) == PaymentMethod.MSR && !PPSCRADevice.this.mTransaction.EMVOnly()) {
                            PPSCRADevice pPSCRADevice2 = PPSCRADevice.this;
                            pPSCRADevice2.startMSRTransaction(pPSCRADevice2.mTransaction);
                        } else {
                            PPSCRADevice pPSCRADevice3 = PPSCRADevice.this;
                            pPSCRADevice3.startEMVTransaction(pPSCRADevice3.mTransaction);
                        }
                    }
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
                } else if (next == PaymentMethod.Contactless) {
                    i = b | 4;
                }
                b = (byte) i;
            }
        }
        return b;
    }

    protected boolean startManualEntry(ITransaction iTransaction) {
        getMagTekPPSCRA().endSession((byte) 0);
        getMagTekPPSCRA().requestManualCardData((byte) 30, (byte) 1, (byte) 0);
        return true;
    }

    protected boolean startMSRTransaction(ITransaction iTransaction) {
        getMagTekPPSCRA().endSession((byte) 0);
        getMagTekPPSCRA().requestCard((byte) 30, (byte) 1, (byte) 1);
        return true;
    }

    protected boolean startEMVTransaction(ITransaction iTransaction) {
        byte[] bArr = {0, 0, 0, 0, 0, 0};
        boolean zQuickChip = iTransaction.QuickChip();
        getMagTekPPSCRA().startEMVTransaction(getCardTypeValue(iTransaction.PaymentMethods()), (byte) 30, (byte) 30, (byte) 1, (byte) 0, new byte[]{0, 0, 0, 0, 21, 0}, (byte) 8, bArr, bArr, bArr, new byte[]{0, 0}, (byte) 0, zQuickChip ? (byte) 1 : (byte) 0, (byte) 0, new byte[]{0, 0, 0, 0, 1, 0}, new byte[]{1, 0, 0}, (byte) 0, (byte) 0, bArr, null);
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean cancelTransaction() {
        getMagTekPPSCRA().cancelOperation();
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean sendAuthorization(IData iData) {
        getMagTekPPSCRA().sendAcquirerResponse(iData.ByteArray());
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean requestPIN(PINRequest pINRequest) {
        new Thread() { // from class: com.magtek.mobile.android.mtusdk.ppscra.PPSCRADevice.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                if (PPSCRADevice.this.checkConnectedDevice()) {
                    PPSCRADevice.this.sendEvent(EventType.DisplayMessage, new BaseData("Please follow instruction shown on the reader"));
                    try {
                        Thread.sleep(500L);
                    } catch (Exception unused) {
                    }
                    PPSCRADevice.this.getMagTekPPSCRA().requestPIN((byte) 60, (byte) 0, (byte) 4, (byte) 12, (byte) 1, (byte) 1);
                }
            }
        }.start();
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDevice, com.magtek.mobile.android.mtusdk.IDevice
    public boolean requestSignature() {
        new Thread() { // from class: com.magtek.mobile.android.mtusdk.ppscra.PPSCRADevice.3
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                if (PPSCRADevice.this.checkConnectedDevice()) {
                    PPSCRADevice.this.sendEvent(EventType.DisplayMessage, new BaseData("Please follow instruction shown on the reader"));
                    try {
                        Thread.sleep(500L);
                    } catch (Exception unused) {
                    }
                    PPSCRADevice.this.getMagTekPPSCRA().requestSignature((byte) 60, (byte) 1, (byte) 1);
                }
            }
        }.start();
        return true;
    }

    protected void OnDeviceStateChanged(MTConnectionState mTConnectionState) {
        ConnectionState connectionState = ConnectionState.Unknown;
        int i = AnonymousClass4.$SwitchMap$com$magtek$mobile$android$ppscra$MTConnectionState[mTConnectionState.ordinal()];
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

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtusdk.ppscra.PPSCRADevice$4, reason: invalid class name */
    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType;
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$ppscra$MTConnectionState;

        static {
            int[] iArr = new int[MTConnectionState.values().length];
            $SwitchMap$com$magtek$mobile$android$ppscra$MTConnectionState = iArr;
            try {
                iArr[MTConnectionState.Connecting.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$ppscra$MTConnectionState[MTConnectionState.Connected.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$ppscra$MTConnectionState[MTConnectionState.Disconnecting.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$ppscra$MTConnectionState[MTConnectionState.Disconnected.ordinal()] = 4;
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
                $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType[ConnectionType.TCP_TLS.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$ConnectionType[ConnectionType.TCP_TLS_TRUST.ordinal()] = 5;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    protected void OnDataChange() {
        sendEvent(EventType.CardData, new BaseData(PPSCRACardDataBuilder.buildTLVPayload(getMagTekPPSCRA().getDeviceSerial(), getMagTekPPSCRA().getCardDataInfo())));
        getMagTekPPSCRA().endSession((byte) 0);
    }

    protected void OnPINData(PINData pINData) {
        if (pINData != null) {
            sendEvent(EventType.PINBlock, new BaseData(TLVParser.getHexString(pINData.getPINKSN()) + "," + TLVParser.getHexString(pINData.getEncryptedPINBlock()) + "," + TLVParser.getHexString(pINData.getStatus())));
        }
        getMagTekPPSCRA().endSession((byte) 0);
    }

    protected void OnSignatureData(byte[] bArr) {
        if (bArr != null && bArr.length > 0) {
            sendEvent(EventType.Signature, new BaseData(bArr));
        }
        getMagTekPPSCRA().endSession((byte) 0);
    }

    protected void OnARQCRequest(byte[] bArr) {
        int length;
        if (bArr == null || (length = bArr.length) <= 0) {
            return;
        }
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        sendEvent(EventType.AuthorizationRequest, new BaseData(bArr2));
    }

    protected void OnEMVData(byte[] bArr) {
        int length;
        if (bArr != null && (length = bArr.length) > 0) {
            byte[] bArr2 = new byte[length];
            System.arraycopy(bArr, 0, bArr2, 0, length);
            sendEvent(EventType.TransactionResult, new BaseData(bArr2));
        }
        getMagTekPPSCRA().endSession((byte) 0);
    }
}
