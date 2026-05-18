package com.magtek.mobile.android.mtlib;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* JADX INFO: loaded from: classes.dex */
public class MTSCRA implements IMTSCRA, IMTEMV, MTDeviceAdapter {
    public static final long COMMAND_TIMEOUT = 5000;
    private static String SDK_VERSION = "107.01";
    public static final int SEND_COMMAND_BUSY = 15;
    public static final int SEND_COMMAND_ERROR = 9;
    public static final int SEND_COMMAND_SUCCESS = 0;
    private static final String TAG = "MTSCRA";
    private boolean mCommandPending;
    private Handler mCommandTimeoutHandler;
    private Runnable mCommandTimeoutRunnable;
    private boolean mEMVCommandPending;
    private boolean mExtendedCommandPending;
    private final Lock mReadLock;
    private final ReentrantReadWriteLock mReadWriteLock;
    private boolean mSwipeOutputToBLECommandPending;
    private boolean mSwipeOutputToUSBCommandPending;
    private final Lock mWriteLock;
    private String m_address;
    private Context m_appContext;
    private IMTCardData m_cardData;
    private boolean m_connectionRetry = false;
    private int m_connectionTimeout = 5000;
    private MTConnectionType m_connectionType;
    private MTDataFormat m_dataFormat;
    private int m_dataThreshold;
    private MTSCRADevice m_device;
    private MTDeviceAdapter m_deviceAdapter;
    private Handler m_scraEventHandler;
    private IMTService m_service;

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public long getCardDataCRC() {
        return 0L;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public int getDataFieldCount() {
        return 0;
    }

    protected void createAudioService() {
        IMTService iMTService;
        if (this.m_service != null) {
            Log.i(TAG, "*** Service is not NULL [" + toString() + "]");
            this.m_service = null;
        }
        this.m_service = new MTAudioService();
        MTDataFormat mTDataFormat = MTDataFormat.SCRA_TLV;
        this.m_dataFormat = mTDataFormat;
        MTSCRADevice mTSCRADevice = this.m_device;
        if (mTSCRADevice == null || (iMTService = this.m_service) == null) {
            return;
        }
        mTSCRADevice.initialize(this.m_deviceAdapter, iMTService, mTDataFormat, this.m_dataThreshold);
        this.m_device.open();
    }

    protected void createBLEService() {
        if (this.m_service != null) {
            Log.i(TAG, "*** Service is not NULL [" + toString() + "]");
            this.m_service = null;
        }
        MTBLEService mTBLEService = new MTBLEService();
        this.m_service = mTBLEService;
        mTBLEService.setAddress(this.m_address);
        if (this.m_device != null) {
            this.m_service.setConnectionTimeout(this.m_connectionTimeout);
            this.m_service.setConnectionRetry(this.m_connectionRetry);
            this.m_service.setAddress(this.m_address);
            if (this.m_connectionType == MTConnectionType.BLEEMV) {
                this.m_service.setServiceUUID(MTDeviceConstants.UUID_SCRA_BLE_EMV_DEVICE_READER_SERVICE);
            } else if (this.m_connectionType == MTConnectionType.BLEEMVT) {
                this.m_service.setServiceUUID(MTDeviceConstants.UUID_SCRA_BLE_EMV_T_DEVICE_READER_SERVICE);
            } else {
                this.m_service.setServiceUUID(MTDeviceConstants.UUID_SCRA_BLE_DEVICE_READER_SERVICE);
            }
            MTDataFormat mTDataFormat = MTDataFormat.SCRA_HID;
            this.m_dataFormat = mTDataFormat;
            this.m_dataThreshold = 100;
            this.m_device.initialize(this.m_deviceAdapter, this.m_service, mTDataFormat, 100);
            this.m_device.open();
        }
    }

    protected void createBTHService() {
        IMTService iMTService;
        if (this.m_service != null) {
            Log.i(TAG, "*** Service is not NULL [" + toString() + "]");
            this.m_service = null;
        }
        MTBTHService mTBTHService = new MTBTHService();
        this.m_service = mTBTHService;
        mTBTHService.setAddress(this.m_address);
        this.m_service.setServiceUUID(MTDeviceConstants.UUID_SCRA_BLUETOOTH_DEVICE_SPP_SERVICE);
        MTDataFormat mTDataFormat = MTDataFormat.SCRA_ASC;
        this.m_dataFormat = mTDataFormat;
        MTSCRADevice mTSCRADevice = this.m_device;
        if (mTSCRADevice == null || (iMTService = this.m_service) == null) {
            return;
        }
        mTSCRADevice.initialize(this.m_deviceAdapter, iMTService, mTDataFormat, this.m_dataThreshold);
        this.m_device.open();
    }

    protected void createUSBService() {
        IMTService iMTService;
        if (this.m_service != null) {
            Log.i(TAG, "*** Service is not NULL [" + toString() + "]");
            this.m_service = null;
        }
        MTUSBService mTUSBService = new MTUSBService();
        this.m_service = mTUSBService;
        mTUSBService.setAddress(this.m_address);
        MTDataFormat mTDataFormat = MTDataFormat.SCRA_HID;
        this.m_dataFormat = mTDataFormat;
        this.m_dataThreshold = 337;
        MTSCRADevice mTSCRADevice = this.m_device;
        if (mTSCRADevice == null || (iMTService = this.m_service) == null) {
            return;
        }
        mTSCRADevice.initialize(this.m_deviceAdapter, iMTService, mTDataFormat, 337);
        this.m_device.open();
    }

    protected void createSerialService() {
        IMTService iMTService;
        if (this.m_service != null) {
            Log.i(TAG, "*** Service is not NULL [" + toString() + "]");
            this.m_service = null;
        }
        MTSerialService mTSerialService = new MTSerialService();
        this.m_service = mTSerialService;
        mTSerialService.setAddress(this.m_address);
        MTDataFormat mTDataFormat = MTDataFormat.SCRA_HID;
        this.m_dataFormat = mTDataFormat;
        this.m_dataThreshold = 337;
        MTSCRADevice mTSCRADevice = this.m_device;
        if (mTSCRADevice == null || (iMTService = this.m_service) == null) {
            return;
        }
        mTSCRADevice.initialize(this.m_deviceAdapter, iMTService, mTDataFormat, 337);
        this.m_device.open();
    }

    protected void createAIDLService() {
        IMTService iMTService;
        if (this.m_service != null) {
            Log.i(TAG, "*** Service is not NULL [" + toString() + "]");
            this.m_service = null;
        }
        MTAIDLService mTAIDLService = new MTAIDLService();
        this.m_service = mTAIDLService;
        mTAIDLService.setAddress(this.m_address);
        MTDataFormat mTDataFormat = MTDataFormat.SCRA_HID;
        this.m_dataFormat = mTDataFormat;
        this.m_dataThreshold = 337;
        MTSCRADevice mTSCRADevice = this.m_device;
        if (mTSCRADevice == null || (iMTService = this.m_service) == null) {
            return;
        }
        mTSCRADevice.initialize(this.m_deviceAdapter, iMTService, mTDataFormat, 337);
        this.m_device.open();
    }

    @Override // com.magtek.mobile.android.mtlib.MTDeviceAdapter
    public void OnMessage(int i, int i2, int i3, Object obj) {
        try {
            switch (i) {
                case 0:
                    OnConnectionState((MTConnectionState) obj);
                    break;
                case 1:
                    OnCardDataState((MTCardDataState) obj);
                    break;
                case 2:
                    OnCardData((IMTCardData) obj);
                    break;
                case 3:
                    OnDeviceResponse((byte[]) obj);
                    break;
                case 4:
                    OnEMVData((byte[]) obj);
                    break;
                case 5:
                    OnDeviceExtendedResponse((byte[]) obj);
                    break;
                case 6:
                    OnDeviceError();
                    break;
                case 7:
                    OnDeviceBondingFailed();
                    break;
                default:
                    return;
            }
        } catch (Exception unused) {
        }
    }

    protected void StartCommandTimeout() {
        Runnable runnable = this.mCommandTimeoutRunnable;
        if (runnable != null) {
            this.mCommandTimeoutHandler.removeCallbacks(runnable);
            this.mCommandTimeoutRunnable = null;
        }
        Runnable runnable2 = new Runnable() { // from class: com.magtek.mobile.android.mtlib.MTSCRA.1
            @Override // java.lang.Runnable
            public void run() {
                MTSCRA.this.ClearAllPendingCommands();
            }
        };
        this.mCommandTimeoutRunnable = runnable2;
        this.mCommandTimeoutHandler.postDelayed(runnable2, 5000L);
    }

    protected void StopCommandTimeout() {
        Runnable runnable = this.mCommandTimeoutRunnable;
        if (runnable != null) {
            this.mCommandTimeoutHandler.removeCallbacks(runnable);
            this.mCommandTimeoutRunnable = null;
        }
    }

    protected void SetCommandPending() {
        this.mWriteLock.lock();
        this.mCommandPending = true;
        this.mWriteLock.unlock();
        StartCommandTimeout();
    }

    protected void SetExtendedCommandPending() {
        this.mWriteLock.lock();
        this.mExtendedCommandPending = true;
        this.mWriteLock.unlock();
        StartCommandTimeout();
    }

    protected void SetEMVCommandPending() {
        this.mWriteLock.lock();
        this.mEMVCommandPending = true;
        this.mWriteLock.unlock();
        StartCommandTimeout();
    }

    protected boolean HasCommandPending() {
        this.mReadLock.lock();
        boolean z = this.mCommandPending;
        this.mReadLock.unlock();
        return z;
    }

    protected boolean HasExtendedCommandPending() {
        this.mReadLock.lock();
        boolean z = this.mExtendedCommandPending;
        this.mReadLock.unlock();
        return z;
    }

    protected boolean HasEMVCommandPending() {
        this.mReadLock.lock();
        boolean z = this.mEMVCommandPending;
        this.mReadLock.unlock();
        return z;
    }

    protected boolean HasAnyCommandPending() {
        this.mReadLock.lock();
        boolean z = this.mCommandPending || this.mExtendedCommandPending || this.mEMVCommandPending;
        this.mReadLock.unlock();
        return z;
    }

    protected void ClearAllPendingCommands() {
        StopCommandTimeout();
        this.mWriteLock.lock();
        this.mCommandPending = false;
        this.mExtendedCommandPending = false;
        this.mEMVCommandPending = false;
        this.mWriteLock.unlock();
    }

    public MTSCRA(Context context, Handler handler) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.mReadWriteLock = reentrantReadWriteLock;
        this.mReadLock = reentrantReadWriteLock.readLock();
        this.mWriteLock = reentrantReadWriteLock.writeLock();
        this.mCommandPending = false;
        this.mExtendedCommandPending = false;
        this.mEMVCommandPending = false;
        this.mSwipeOutputToUSBCommandPending = false;
        this.mSwipeOutputToBLECommandPending = false;
        this.mCommandTimeoutHandler = new Handler(Looper.getMainLooper());
        this.mCommandTimeoutRunnable = null;
        this.m_appContext = context;
        this.m_scraEventHandler = handler;
        this.m_device = new MTSCRADevice(this.m_appContext);
    }

    protected void finalize() {
        Log.i(TAG, "**** finalize");
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public void setConnectionType(MTConnectionType mTConnectionType) {
        this.m_connectionType = mTConnectionType;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public void setAddress(String str) {
        this.m_address = str;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public void setDeviceConfiguration(String str) {
        MTSCRADevice mTSCRADevice = this.m_device;
        if (mTSCRADevice != null) {
            mTSCRADevice.setConfiguration(str);
        }
    }

    public void setConnectionTimeout(int i) {
        if (i >= 0) {
            this.m_connectionTimeout = i;
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public void setConnectionRetry(boolean z) {
        this.m_connectionRetry = z;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public void openDevice() {
        String str = TAG;
        Log.i(str, "openDevice:ConnectionType:" + this.m_connectionType);
        this.m_deviceAdapter = this;
        IMTService iMTService = this.m_service;
        if (iMTService != null && iMTService.getState() != MTServiceState.Disconnected) {
            Log.i(str, "openDevice: *** Connection already exists.");
        }
        this.m_service = null;
        switch (AnonymousClass2.$SwitchMap$com$magtek$mobile$android$mtlib$MTConnectionType[this.m_connectionType.ordinal()]) {
            case 1:
                createAudioService();
                break;
            case 2:
            case 3:
            case 4:
                createBLEService();
                break;
            case 5:
                createBTHService();
                break;
            case 6:
                createUSBService();
                break;
            case 7:
                createAIDLService();
                break;
            case 8:
                createSerialService();
                break;
        }
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtlib.MTSCRA$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtlib$MTConnectionType;

        static {
            int[] iArr = new int[MTConnectionType.values().length];
            $SwitchMap$com$magtek$mobile$android$mtlib$MTConnectionType = iArr;
            try {
                iArr[MTConnectionType.Audio.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTConnectionType[MTConnectionType.BLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTConnectionType[MTConnectionType.BLEEMV.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTConnectionType[MTConnectionType.BLEEMVT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTConnectionType[MTConnectionType.Bluetooth.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTConnectionType[MTConnectionType.USB.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTConnectionType[MTConnectionType.AIDL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtlib$MTConnectionType[MTConnectionType.Serial.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public void closeDevice() {
        IMTService iMTService;
        Log.i(TAG, "MTSCRA closeDevice");
        if (this.m_device != null) {
            if ((this.m_connectionType == MTConnectionType.BLE || this.m_connectionType == MTConnectionType.BLEEMV || this.m_connectionType == MTConnectionType.BLEEMVT) && (iMTService = this.m_service) != null) {
                if (iMTService.getState() == MTServiceState.Connected) {
                    this.m_service.sendData(MTDeviceConstants.SCRA_DEVICE_COMMAND_BLE_DISCONNECT);
                } else {
                    this.m_service.getState();
                    MTServiceState mTServiceState = MTServiceState.Connecting;
                }
            }
            this.m_device.close();
            this.m_service = null;
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public boolean isDeviceConnected() {
        IMTService iMTService = this.m_service;
        return iMTService != null && iMTService.getState() == MTServiceState.Connected;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public boolean isDeviceEMV() {
        if (this.m_connectionType == MTConnectionType.BLEEMV || this.m_connectionType == MTConnectionType.BLEEMVT) {
            return true;
        }
        if (this.m_connectionType == MTConnectionType.USB) {
            IMTService iMTService = this.m_service;
            if (iMTService != null) {
                return iMTService.isServiceEMV();
            }
        } else if (this.m_connectionType == MTConnectionType.Serial || this.m_connectionType == MTConnectionType.AIDL) {
            return true;
        }
        return false;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public boolean isDeviceOEM() {
        IMTService iMTService;
        if (this.m_connectionType != MTConnectionType.USB || (iMTService = this.m_service) == null) {
            return false;
        }
        return iMTService.isServiceOEM();
    }

    public String getPowerManagementValue() {
        IMTService iMTService = this.m_service;
        return iMTService != null ? iMTService.getDevicePMValue() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public MTDeviceFeatures getDeviceFeatures() {
        MTDeviceFeatures mTDeviceFeatures = new MTDeviceFeatures();
        IMTService iMTService = this.m_service;
        return iMTService != null ? iMTService.getDeviceFeatures() : mTDeviceFeatures;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getMaskedTracks() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getMaskedTracks() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getTrack1() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getTrack1() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getTrack2() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getTrack2() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getTrack3() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getTrack3() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getTrack1Masked() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getTrack1Masked() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getTrack2Masked() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getTrack2Masked() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getTrack3Masked() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getTrack3Masked() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getMagnePrint() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getMagnePrint() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getMagnePrintStatus() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getMagnePrintStatus() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getDeviceSerial() {
        IMTService iMTService;
        IMTCardData iMTCardData = this.m_cardData;
        String deviceSerial = iMTCardData != null ? iMTCardData.getDeviceSerial() : "";
        return (!deviceSerial.isEmpty() || (iMTService = this.m_service) == null) ? deviceSerial : iMTService.getDeviceSerial();
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getSessionID() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getSessionID() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getKSN() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getKSN() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getDeviceName() {
        IMTService iMTService;
        IMTCardData iMTCardData = this.m_cardData;
        String deviceName = iMTCardData != null ? iMTCardData.getDeviceName() : "";
        return (!deviceName.isEmpty() || (iMTService = this.m_service) == null) ? deviceName : iMTService.getDeviceName();
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public void clearBuffers() {
        IMTCardData iMTCardData = this.m_cardData;
        if (iMTCardData != null) {
            iMTCardData.clearBuffers();
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public long getBatteryLevel() {
        IMTService iMTService;
        long batteryLevel = MTDeviceConstants.BATTERY_LEVEL_NA;
        IMTCardData iMTCardData = this.m_cardData;
        if (iMTCardData != null) {
            batteryLevel = iMTCardData.getBatteryLevel();
        }
        return ((batteryLevel < MTDeviceConstants.BATTERY_LEVEL_MIN || batteryLevel > MTDeviceConstants.BATTERY_LEVEL_MAX) && (iMTService = this.m_service) != null) ? iMTService.getBatteryLevel() : batteryLevel;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public long getSwipeCount() {
        long j = MTDeviceConstants.SWIPE_COUNT_NA;
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getSwipeCount() : j;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getCapMagnePrint() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getCapMagnePrint() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getCapMagnePrintEncryption() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getCapMagnePrintEncryption() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getCapMagneSafe20Encryption() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getCapMagneSafe20Encryption() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getCapMagStripeEncryption() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getCapMagStripeEncryption() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getCapMSR() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getCapMSR() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getCapTracks() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getCapTracks() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getCardExpDate() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getCardExpDate() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getCardIIN() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getCardIIN() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getCardLast4() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getCardLast4() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getCardName() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getCardName() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getCardPAN() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getCardPAN() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public int getCardPANLength() {
        IMTCardData iMTCardData = this.m_cardData;
        if (iMTCardData != null) {
            return iMTCardData.getCardPANLength();
        }
        return 0;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getCardServiceCode() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getCardServiceCode() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getCardStatus() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getCardStatus() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getHashCode() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getHashCode() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getDeviceConfig(String str) {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getDeviceConfig(str) : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getEncryptionStatus() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getEncryptionStatus() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getFirmware() {
        IMTService iMTService;
        IMTCardData iMTCardData = this.m_cardData;
        String firmware = iMTCardData != null ? iMTCardData.getFirmware() : "";
        return (!firmware.isEmpty() || (iMTService = this.m_service) == null) ? firmware : iMTService.getFirmwareID();
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getMagTekDeviceSerial() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getMagTekDeviceSerial() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getResponseData() {
        IMTCardData iMTCardData = this.m_cardData;
        byte[] data = iMTCardData != null ? iMTCardData.getData() : null;
        return data != null ? MTParser.getHexString(data) : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getResponseType() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getResponseType() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getTagValue(String str, String str2) {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getTagValue(str, str2) : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getTLVVersion() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getTLVVersion() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getTrackDecodeStatus() {
        IMTCardData iMTCardData = this.m_cardData;
        return iMTCardData != null ? iMTCardData.getTrackDecodeStatus() : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public String getSDKVersion() {
        return SDK_VERSION;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTSCRA
    public int sendCommandToDevice(String str) {
        if (HasAnyCommandPending()) {
            return 15;
        }
        byte[] byteArrayFromHexString = MTParser.getByteArrayFromHexString(str);
        SetCommandPending();
        if (this.m_device.sendCommand(byteArrayFromHexString)) {
            return 0;
        }
        ClearAllPendingCommands();
        return 9;
    }

    protected void SetDeviceResponse(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        ClearAllPendingCommands();
        boolean z = true;
        if (this.mSwipeOutputToUSBCommandPending || this.mSwipeOutputToBLECommandPending) {
            this.mSwipeOutputToUSBCommandPending = false;
            this.mSwipeOutputToBLECommandPending = false;
            Message message = new Message();
            message.what = 0;
            message.obj = MTConnectionState.Connected;
            this.m_scraEventHandler.sendMessage(message);
            if (bArr.length != 2 || bArr[0] != 9 || bArr[1] != 0) {
                z = false;
            }
        }
        if (!z || this.m_scraEventHandler == null) {
            return;
        }
        Message message2 = new Message();
        message2.what = 3;
        message2.obj = MTParser.getHexString(bArr);
        this.m_scraEventHandler.sendMessage(message2);
    }

    protected void SetDeviceExtendedResponse(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        if (HasEMVCommandPending()) {
            ClearAllPendingCommands();
            if (this.m_scraEventHandler != null) {
                Message message = new Message();
                message.what = MTEMVEvent.OnEMVCommandResult;
                message.obj = bArr;
                this.m_scraEventHandler.sendMessage(message);
                return;
            }
            return;
        }
        ClearAllPendingCommands();
        if (this.m_scraEventHandler != null) {
            Message message2 = new Message();
            message2.what = MTEMVEvent.OnDeviceExtendedResponse;
            message2.obj = MTParser.getHexString(bArr);
            this.m_scraEventHandler.sendMessage(message2);
        }
    }

    protected void SetCardData(IMTCardData iMTCardData) {
        if (iMTCardData != null) {
            this.m_cardData = iMTCardData;
            if (this.m_scraEventHandler != null) {
                Message message = new Message();
                message.what = 2;
                message.obj = iMTCardData;
                this.m_scraEventHandler.sendMessage(message);
            }
        }
    }

    protected void SetEMVData(byte[] bArr) {
        Log.i(TAG, "EMV Data=" + MTParser.getHexString(bArr));
        if (bArr == null || bArr.length < 2 || this.m_scraEventHandler == null) {
            return;
        }
        byte[] bArr2 = null;
        int length = bArr.length - 4;
        if (length > 0) {
            bArr2 = new byte[length];
            System.arraycopy(bArr, 4, bArr2, 0, length);
        }
        Message message = new Message();
        if (bArr[0] == 3) {
            byte b = bArr[1];
            if (b == 0) {
                message.what = 200;
            } else if (b == 1) {
                message.what = MTEMVEvent.OnDisplayMessageRequest;
            } else if (b == 2) {
                message.what = MTEMVEvent.OnUserSelectionRequest;
            } else if (b == 3) {
                message.what = MTEMVEvent.OnARQCReceived;
            } else if (b == 4) {
                message.what = MTEMVEvent.OnTransactionResult;
            }
            message.obj = bArr2;
            this.m_scraEventHandler.sendMessage(message);
            return;
        }
        SetDeviceExtendedResponse(bArr);
    }

    protected void OnConnectionState(MTConnectionState mTConnectionState) {
        String str = TAG;
        Log.i(str, "OnConnectionState");
        boolean z = true;
        if (mTConnectionState == MTConnectionState.Connected) {
            ClearAllPendingCommands();
            this.mSwipeOutputToUSBCommandPending = false;
            this.mSwipeOutputToBLECommandPending = false;
            IMTService iMTService = this.m_service;
            if (iMTService != null && iMTService.isOutputChannelConfigurable()) {
                if (this.m_connectionType == MTConnectionType.USB) {
                    this.mSwipeOutputToUSBCommandPending = true;
                    sendCommandToDevice(MTDeviceConstants.SCRA_DEVICE_COMMAND_STRING_SET_CARD_SWIPE_OUTPUT_CHANNEL_OVERRIDE_USB);
                    Log.i(str, "Set swipe output to USB");
                } else if (this.m_connectionType == MTConnectionType.BLE || this.m_connectionType == MTConnectionType.BLEEMV || this.m_connectionType == MTConnectionType.BLEEMVT) {
                    this.mSwipeOutputToBLECommandPending = true;
                    sendCommandToDevice(MTDeviceConstants.SCRA_DEVICE_COMMAND_STRING_SET_CARD_SWIPE_OUTPUT_CHANNEL_OVERRIDE_BLE);
                    Log.i(str, "Set swipe output to BLE");
                }
                z = false;
            }
        }
        if (!z || this.m_scraEventHandler == null) {
            return;
        }
        Message message = new Message();
        message.what = 0;
        message.obj = mTConnectionState;
        this.m_scraEventHandler.sendMessage(message);
        if (mTConnectionState == MTConnectionState.Disconnected) {
            Log.i(str, "OnConnectionState Disconnected");
        }
    }

    protected void OnDeviceResponse(byte[] bArr) {
        SetDeviceResponse(bArr);
    }

    protected void OnDeviceExtendedResponse(byte[] bArr) {
        SetDeviceExtendedResponse(bArr);
    }

    protected void OnCardData(IMTCardData iMTCardData) {
        SetCardData(iMTCardData);
    }

    protected void OnCardDataState(MTCardDataState mTCardDataState) {
        if (this.m_scraEventHandler != null) {
            Message message = new Message();
            message.what = 1;
            message.obj = mTCardDataState;
            this.m_scraEventHandler.sendMessage(message);
        }
    }

    protected void OnDeviceError() {
        Log.i(TAG, "OnConnectionState");
        MTConnectionState mTConnectionState = MTConnectionState.Error;
        if (this.m_scraEventHandler != null) {
            Message message = new Message();
            message.what = 0;
            message.obj = mTConnectionState;
            this.m_scraEventHandler.sendMessage(message);
        }
    }

    protected void OnDeviceBondingFailed() {
        if (this.m_scraEventHandler != null) {
            Message message = new Message();
            message.what = 4;
            this.m_scraEventHandler.sendMessage(message);
        }
    }

    protected void OnEMVData(byte[] bArr) {
        SetEMVData(bArr);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTEMV
    public int sendExtendedCommand(String str) {
        if (HasAnyCommandPending()) {
            return 15;
        }
        byte[] byteArrayFromHexString = MTParser.getByteArrayFromHexString(str);
        if (byteArrayFromHexString.length >= 4) {
            byte[] bArr = {byteArrayFromHexString[0], byteArrayFromHexString[1]};
            int length = byteArrayFromHexString.length - 4;
            if (length >= ((byteArrayFromHexString[2] & 255) << 8) + (byteArrayFromHexString[3] & 255)) {
                byte[] bArr2 = new byte[length];
                for (int i = 0; i < length; i++) {
                    bArr2[i] = byteArrayFromHexString[i + 4];
                }
                SetExtendedCommandPending();
                int iSendExtendedCommandBytes = sendExtendedCommandBytes(bArr, bArr2);
                if (iSendExtendedCommandBytes == 9) {
                    ClearAllPendingCommands();
                }
                return iSendExtendedCommandBytes;
            }
        }
        return 9;
    }

    private int sendEMVCommand(byte[] bArr, byte[] bArr2) {
        if (HasAnyCommandPending()) {
            return 15;
        }
        SetEMVCommandPending();
        int iSendExtendedCommandBytes = sendExtendedCommandBytes(bArr, bArr2);
        if (iSendExtendedCommandBytes == 9) {
            ClearAllPendingCommands();
        }
        return iSendExtendedCommandBytes;
    }

    private int sendExtendedCommandBytes(byte[] bArr, byte[] bArr2) {
        if ((bArr != null ? bArr.length : 0) != 2) {
            return 9;
        }
        int length = bArr2 != null ? bArr2.length : 0;
        int i = 0;
        while (true) {
            if (i >= length && length != 0) {
                break;
            }
            int i2 = length - i;
            if (i2 >= 52) {
                i2 = 51;
            }
            byte[] bArr3 = new byte[i2 + 8];
            Arrays.fill(bArr3, (byte) 0);
            bArr3[0] = 73;
            bArr3[1] = (byte) (i2 + 6);
            bArr3[2] = (byte) ((i >> 8) & 255);
            bArr3[3] = (byte) (i & 255);
            bArr3[4] = bArr[0];
            bArr3[5] = bArr[1];
            bArr3[6] = (byte) ((length >> 8) & 255);
            bArr3[7] = (byte) (length & 255);
            for (int i3 = 0; i3 < i2; i3++) {
                bArr3[i3 + 8] = bArr2[i + i3];
            }
            i += i2;
            if (!this.m_device.sendCommand(bArr3)) {
                return 9;
            }
            if (length == 0) {
                break;
            }
            try {
                Thread.sleep(20L);
            } catch (Exception unused) {
            }
        }
        return 0;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTEMV
    public int startTransaction(byte b, byte b2, byte b3, byte[] bArr, byte b4, byte[] bArr2, byte[] bArr3, byte b5) {
        Log.i(TAG, "MTSCRA startTransaction, cardType=" + String.format("%02X ", Byte.valueOf(b2)) + ", option=" + String.format("%02X ", Byte.valueOf(b3)) + ", transactionType=" + String.format("%02X ", Byte.valueOf(b4)));
        byte[] bArr4 = new byte[19];
        Arrays.fill(bArr4, (byte) 0);
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
        return sendEMVCommand(MTEMVDeviceConstants.EMV_COMMAND_START_TRANSACTION, bArr4);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTEMV
    public int setUserSelectionResult(byte b, byte b2) {
        return sendEMVCommand(MTEMVDeviceConstants.EMV_COMMAND_SET_USER_SELECTION_RESULT, new byte[]{b, b2});
    }

    @Override // com.magtek.mobile.android.mtlib.IMTEMV
    public int setAcquirerResponse(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return 9;
        }
        return sendEMVCommand(MTEMVDeviceConstants.EMV_COMMAND_SET_ACQUIRER_RESPONSE, bArr);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTEMV
    public int cancelTransaction() {
        return sendEMVCommand(MTEMVDeviceConstants.EMV_COMMAND_CANCEL_TRANSACTION, null);
    }
}
