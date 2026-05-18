package com.magtek.mobile.android.mtlib;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.util.Log;
import com.digitalmediavending.hardware.utils.AppConstants;
import com.ftdi.j2xx.ft4222.FT_4222_Defines;
import com.magtek.mobile.android.ppscra.MTPPDeviceConstants;
import iaik.security.ssl.SSLContext;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* JADX INFO: loaded from: classes.dex */
public class MTUSBService extends MTBaseService {
    private static final String ACTION_USB_PERMISSION = "com.magtek.mobile.android.mtlib.USB_PERMISSION";
    private static final int DEVICE_DESCRIPTOR_IDPRODUCT = 10;
    private static final int DEVICE_DESCRIPTOR_IDVENDOR = 8;
    private static final int DEVICE_DESCRIPTOR_IMANUFACTURER = 14;
    private static final int DEVICE_DESCRIPTOR_IPRODUCT = 15;
    private static final int DEVICE_DESCRIPTOR_ISERIALNUMBER = 16;
    private static final String TAG = "MTUSBService";
    private static final int USB_DESCRIPTOR_TYPE_DEVICE = 1;
    private static final int USB_DESCRIPTOR_TYPE_HID = 33;
    private static final int USB_DESCRIPTOR_TYPE_REPORT = 34;
    private static final int USB_DESCRIPTOR_TYPE_STRING = 3;
    private Object mInputReportSyncToken;
    private Object mStateSyncToken;
    private UsbDeviceConnection m_connection;
    private byte[] m_deviceSerialNumber;
    private Object m_deviceSerialObject;
    private int m_featureReportCount;
    private Object m_firmwareIDObject;
    private String m_firwareVersion;
    private PendingIntent m_permissionIntent;
    private String m_productID;
    private String m_productName;
    private UsbDevice m_usbDevice;
    private UsbEndpoint m_usbEndpoint;
    private UsbInterface m_usbInterface;
    private UsbManager m_usbManager;
    private final BroadcastReceiver m_usbReceiver;
    private Lock sReadLock;
    private ReentrantReadWriteLock sReadWriteLock;
    private Lock sWriteLock;
    private final int GET_DESCRIPTOR_TIMEOUT = 200;
    private final int DEVICE_DESCRIPTOR_MAX_SIZE = 512;
    private final int STRING_DESCRIPTOR_MAX_SIZE = 512;
    private final int HID_DESCRIPTOR_MAX_SIZE = 512;
    private final int INPUT_REPORT_MAX_SIZE = 64;
    private final int INPUT_REPORT_WITH_ID_MAX_SIZE = 1024;
    private final int INPUT_REPORT_POLLING_INTERVAL = 500;
    private final int INPUT_REPORT_POLLING_PAUSE = 5;
    private int m_usbPollingInterval = 500;
    private int m_usbPollingPause = 5;
    private int m_usbPollingTimeout = 495;
    private int m_interfaceProtocol = 0;
    private String m_firmwareID = "";
    private String m_deviceSerial = "";
    private boolean m_threadStop = true;
    private Thread m_thread = null;

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public String getDeviceName() {
        return "MagTek MSR HID Device";
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void setConnectionRetry(boolean z) {
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void setConnectionTimeout(int i) {
    }

    public MTUSBService() {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        this.sReadWriteLock = reentrantReadWriteLock;
        this.sReadLock = reentrantReadWriteLock.readLock();
        this.sWriteLock = this.sReadWriteLock.writeLock();
        this.mInputReportSyncToken = new Object();
        this.mStateSyncToken = new Object();
        this.m_usbReceiver = new BroadcastReceiver() { // from class: com.magtek.mobile.android.mtlib.MTUSBService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (MTUSBService.ACTION_USB_PERMISSION.equals(action)) {
                    synchronized (this) {
                        UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                        if (MTUSBService.isDeviceFound(usbDevice)) {
                            if (intent.getBooleanExtra("permission", false)) {
                                if (MTUSBService.this.m_state != MTServiceState.Connected) {
                                    if (setDevice(usbDevice)) {
                                        onConnected();
                                    } else {
                                        onDisconnected();
                                    }
                                }
                            } else {
                                onDenied();
                            }
                        }
                    }
                    return;
                }
                if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(action)) {
                    UsbDevice usbDevice2 = (UsbDevice) intent.getParcelableExtra("device");
                    if (!MTUSBService.isDeviceFound(usbDevice2) || usbDevice2 == null || MTUSBService.this.m_state == MTServiceState.Connected) {
                        return;
                    }
                    if (setDevice(usbDevice2)) {
                        onConnected();
                        return;
                    } else {
                        onDisconnected();
                        return;
                    }
                }
                if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action) && MTUSBService.isDeviceFound((UsbDevice) intent.getParcelableExtra("device"))) {
                    onDetached();
                }
            }

            private void onConnected() {
                MTUSBService.this.getDecriptorInformation();
                MTUSBService.this.setState(MTServiceState.Connected);
            }

            private void onDisconnected() {
                MTUSBService.this.setState(MTServiceState.Disconnected);
            }

            private void onDenied() {
                MTUSBService.this.setState(MTServiceState.Disconnected);
            }

            private void onDetached() {
                MTUSBService.this.disconnect();
            }

            private boolean setDevice(UsbDevice usbDevice) {
                if (usbDevice != null) {
                    try {
                        if (!MTUSBService.this.m_usbManager.hasPermission(usbDevice)) {
                            MTUSBService.this.m_usbManager.requestPermission(usbDevice, MTUSBService.this.m_permissionIntent);
                        }
                    } catch (Exception unused) {
                        Log.i(MTUSBService.TAG, "Exception caught in setDevice()");
                        return false;
                    }
                }
                Log.i(MTUSBService.TAG, "DeviceName=" + usbDevice.getDeviceName());
                if (usbDevice.getInterfaceCount() != 1) {
                    Log.i(MTUSBService.TAG, "InterfaceCount() != 1");
                }
                MTUSBService.this.m_usbInterface = usbDevice.getInterface(0);
                if (MTUSBService.this.m_usbInterface.getEndpointCount() != 1) {
                    Log.i(MTUSBService.TAG, "EndpointCount() != 1");
                }
                UsbEndpoint endpoint = MTUSBService.this.m_usbInterface.getEndpoint(0);
                if (endpoint.getType() != 3) {
                    Log.i(MTUSBService.TAG, "Endpoint Type != XFER_INT");
                }
                MTUSBService.this.m_usbEndpoint = endpoint;
                MTUSBService mTUSBService = MTUSBService.this;
                mTUSBService.m_interfaceProtocol = mTUSBService.m_usbInterface.getInterfaceProtocol();
                if (usbDevice != null && MTUSBService.this.m_connection == null) {
                    UsbDeviceConnection usbDeviceConnectionOpenDevice = MTUSBService.this.m_usbManager.openDevice(usbDevice);
                    if (usbDeviceConnectionOpenDevice != null) {
                        Log.i(MTUSBService.TAG, "*** Interface Protocol=" + MTUSBService.this.m_interfaceProtocol);
                        if (MTUSBService.this.m_interfaceProtocol == 0) {
                            Log.i(MTUSBService.TAG, "*** HID Interface Protocol");
                            boolean zClaimInterface = usbDeviceConnectionOpenDevice.claimInterface(MTUSBService.this.m_usbInterface, true);
                            Log.i(MTUSBService.TAG, "*** Claimed interface=" + zClaimInterface);
                        } else if (MTUSBService.this.m_interfaceProtocol == 1) {
                            Log.i(MTUSBService.TAG, "*** HID KB Interface Protocol");
                            boolean zClaimInterface2 = usbDeviceConnectionOpenDevice.claimInterface(MTUSBService.this.m_usbInterface, true);
                            Log.i(MTUSBService.TAG, "*** Claimed interface=" + zClaimInterface2);
                        } else {
                            boolean zClaimInterface3 = usbDeviceConnectionOpenDevice.claimInterface(MTUSBService.this.m_usbInterface, false);
                            Log.i(MTUSBService.TAG, "*** Claimed interface=" + zClaimInterface3);
                        }
                        MTUSBService.this.m_connection = usbDeviceConnectionOpenDevice;
                        if (MTUSBService.this.m_interfaceProtocol == 0) {
                            MTUSBService.this.m_threadStop = false;
                            Thread thread = new Thread(MTUSBService.this);
                            MTUSBService.this.m_thread = thread;
                            thread.start();
                        }
                    } else {
                        MTUSBService.this.m_connection = null;
                    }
                }
                return true;
            }
        };
    }

    protected void handleDeviceResponse(byte[] bArr) {
        if (bArr != 0) {
            if (this.m_firmwareIDObject != null) {
                if (bArr.length >= 1 && bArr[0] == 0) {
                    int i = bArr[1];
                    if (bArr.length - 2 >= i) {
                        byte[] bArr2 = new byte[i];
                        System.arraycopy(bArr, 2, bArr2, 0, i);
                        this.m_firmwareID = MTParser.getTextString(bArr2, 0, i);
                    } else {
                        this.m_firmwareID = "";
                    }
                }
                this.m_firmwareIDObject.notifyAll();
            }
            if (this.m_deviceSerialObject != null) {
                if (bArr.length >= 1 && bArr[0] == 0) {
                    int i2 = bArr[1];
                    if (bArr.length - 2 >= i2) {
                        byte[] bArr3 = new byte[i2];
                        System.arraycopy(bArr, 2, bArr3, 0, i2);
                        this.m_deviceSerial = MTParser.getTextString(bArr3, 0, i2);
                    } else {
                        this.m_deviceSerial = "";
                    }
                }
                this.m_deviceSerialObject.notifyAll();
            }
            if (this.m_serviceAdapter != null) {
                this.m_serviceAdapter.OnCommandData(bArr);
            }
        }
    }

    protected void OnFeatureReportReceived(byte[] bArr) {
        if (bArr != null) {
            Arrays.copyOf(bArr, bArr.length);
            handleDeviceResponse(bArr);
        }
    }

    protected void OnInputReportReceived(byte[] bArr) {
        if (bArr != null) {
            byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
            Log.i(TAG, "*** Card Data Length=" + bArrCopyOf.length);
            if (this.m_serviceAdapter != null) {
                this.m_serviceAdapter.OnCardData(bArrCopyOf);
            }
        }
    }

    protected void OnDeviceDataReceived(byte[] bArr) {
        if (bArr != null) {
            byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
            Log.i(TAG, "*** Device Data Length=" + bArrCopyOf.length);
            if (this.m_serviceAdapter != null) {
                this.m_serviceAdapter.OnDeviceData(bArrCopyOf);
            }
        }
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void connect() {
        synchronized (this.mStateSyncToken) {
            if (this.m_state != MTServiceState.Disconnected) {
                Log.i(TAG, "State is not Disconnected, cannot connect at this time.");
                return;
            }
            setState(MTServiceState.Connecting);
            String str = TAG;
            Log.i(str, "connecting");
            this.m_featureReportCount = -1;
            this.m_usbManager = (UsbManager) this.m_context.getSystemService("usb");
            UsbDevice device = getDevice();
            this.m_usbDevice = device;
            if (device == null) {
                Log.w(str, "USB Device was not found");
                setState(MTServiceState.Disconnected);
                return;
            }
            Log.i(str, "preparing mPermissionIntent ");
            this.m_permissionIntent = PendingIntent.getBroadcast(this.m_context, 0, new Intent(ACTION_USB_PERMISSION), 0);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
            intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
            intentFilter.addAction(ACTION_USB_PERMISSION);
            Log.i(str, "registering usbReceiver");
            this.m_context.registerReceiver(this.m_usbReceiver, intentFilter);
            if (this.m_usbDevice != null) {
                Log.i(str, "requestPermission");
                this.m_usbManager.requestPermission(this.m_usbDevice, this.m_permissionIntent);
            }
        }
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void disconnect() {
        synchronized (this.mStateSyncToken) {
            if (this.m_state == MTServiceState.Disconnecting) {
                Log.i(TAG, "State is Disconnecting, cannot disconnect at this time.");
                return;
            }
            if (this.m_state == MTServiceState.Disconnected) {
                Log.i(TAG, "State is Disconnected, cannot disconnect at this time.");
                return;
            }
            setState(MTServiceState.Disconnecting);
            if (this.m_usbDevice != null) {
                try {
                    Thread thread = this.m_thread;
                    if (thread != null) {
                        this.m_threadStop = true;
                        this.m_thread = null;
                        Log.i(TAG, "Interrupt Thread");
                        thread.interrupt();
                    }
                    if (this.m_connection != null) {
                        if (this.m_usbInterface != null) {
                            Log.i(TAG, "Releasing Connection Interface ");
                            this.m_connection.releaseInterface(this.m_usbInterface);
                            this.m_usbInterface = null;
                        }
                        Log.i(TAG, "Closing Connection");
                        this.m_connection.close();
                        this.m_usbDevice = null;
                        this.m_connection = null;
                    }
                } catch (Exception e) {
                    Log.i(TAG, "Exception: " + e.toString());
                }
                setState(MTServiceState.Disconnected);
            }
            if (this.m_usbReceiver != null) {
                Log.i(TAG, "unregistering usbReceiver");
                try {
                    this.m_context.unregisterReceiver(this.m_usbReceiver);
                } catch (Exception e2) {
                    Log.i(TAG, "Exception: " + e2.toString());
                }
            }
        }
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public boolean sendData(byte[] bArr) {
        return writeData(bArr);
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public long getBatteryLevel() {
        return MTDeviceConstants.BATTERY_LEVEL_MAX;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public String getFirmwareID() {
        if (this.m_state != MTServiceState.Connected) {
            return "";
        }
        Object obj = new Object();
        this.m_firmwareIDObject = obj;
        this.m_firmwareID = "";
        synchronized (obj) {
            try {
                sendData(MTDeviceConstants.SCRA_DEVICE_COMMAND_GET_FIRMWARE_ID);
                this.m_firmwareIDObject.wait(AppConstants.APP_IS_RUNNING_INTERVAL_VALUE);
            } catch (InterruptedException unused) {
            }
        }
        String str = this.m_firmwareID;
        this.m_firmwareIDObject = null;
        return str;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public String getDeviceSerial() {
        if (this.m_state != MTServiceState.Connected) {
            return "";
        }
        Object obj = new Object();
        this.m_deviceSerialObject = obj;
        this.m_deviceSerial = "";
        synchronized (obj) {
            try {
                sendData(MTDeviceConstants.SCRA_DEVICE_COMMAND_GET_DEVICE_SN);
                this.m_deviceSerialObject.wait(AppConstants.APP_IS_RUNNING_INTERVAL_VALUE);
            } catch (InterruptedException unused) {
            }
        }
        String str = this.m_deviceSerial;
        this.m_deviceSerialObject = null;
        return str;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public boolean isOutputChannelConfigurable() {
        int productId;
        UsbDevice usbDevice = this.m_usbDevice;
        return usbDevice != null && ((productId = usbDevice.getProductId()) == MTDeviceConstants.PID_EMV_SWIPE || productId == MTDeviceConstants.PID_SWIPE || productId == MTDeviceConstants.PID_TDYNAMO || productId == MTDeviceConstants.PID_IDYNAMO6);
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public boolean isServiceEMV() {
        int productId;
        UsbDevice usbDevice = this.m_usbDevice;
        return usbDevice != null && ((productId = usbDevice.getProductId()) == MTDeviceConstants.PID_EMV_SWIPE || productId == MTDeviceConstants.PID_EMV || productId == MTDeviceConstants.PID_TDYNAMO || productId == MTDeviceConstants.PID_DYNAWAVE || productId == MTDeviceConstants.PID_IDYNAMO6);
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public boolean isServiceOEM() {
        UsbDevice usbDevice = this.m_usbDevice;
        return usbDevice != null && usbDevice.getProductId() == MTDeviceConstants.PID_EMV;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public String getDevicePMValue() {
        int productId = this.m_usbDevice.getProductId();
        return productId == MTDeviceConstants.PID_SWIPE ? "PM2" : productId == MTDeviceConstants.PID_EMV_SWIPE ? "PM3" : productId == MTDeviceConstants.PID_AUDIO ? "PM4" : (productId == MTDeviceConstants.PID_TDYNAMO || productId == MTDeviceConstants.PID_KDYNAMO || productId == MTDeviceConstants.PID_IDYNAMO6) ? "PM5" : "";
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public MTDeviceFeatures getDeviceFeatures() {
        int productId = this.m_usbDevice.getProductId();
        MTDeviceFeatures mTDeviceFeatures = new MTDeviceFeatures();
        mTDeviceFeatures.MSR = true;
        if (productId == MTDeviceConstants.PID_EMV_SWIPE) {
            mTDeviceFeatures.Contact = true;
            mTDeviceFeatures.BatteryBackedClock = true;
        } else if (productId == MTDeviceConstants.PID_TDYNAMO || productId == MTDeviceConstants.PID_KDYNAMO || productId == MTDeviceConstants.PID_IDYNAMO6) {
            mTDeviceFeatures.Contact = true;
            mTDeviceFeatures.Contactless = true;
            mTDeviceFeatures.MSRPowerSaver = true;
        } else if (productId == MTDeviceConstants.PID_DYNAWAVE) {
            mTDeviceFeatures.MSR = false;
            mTDeviceFeatures.Contactless = true;
        }
        return mTDeviceFeatures;
    }

    private UsbDevice getDevice() {
        String str = TAG;
        Log.i(str, "getDevice");
        UsbAccessory[] accessoryList = this.m_usbManager.getAccessoryList();
        if (accessoryList != null) {
            Log.i(str, "USB Accessory Count=" + accessoryList.length);
            for (int i = 0; i < accessoryList.length; i++) {
                String str2 = TAG;
                Log.i(str2, "USB Accessory " + i + ":");
                StringBuilder sb = new StringBuilder();
                sb.append("\tManafacturer=");
                sb.append(accessoryList[i].getManufacturer());
                Log.i(str2, sb.toString());
                Log.i(str2, "\tModel=" + accessoryList[i].getModel());
            }
        } else {
            Log.i(str, "No USB Accessory found");
        }
        HashMap<String, UsbDevice> deviceList = this.m_usbManager.getDeviceList();
        if (!this.m_address.isEmpty()) {
            if (deviceList != null) {
                return deviceList.get(this.m_address);
            }
            return null;
        }
        for (UsbDevice usbDevice : deviceList.values()) {
            String str3 = TAG;
            Log.i(str3, "USB Device : VendorID=" + usbDevice.getVendorId() + ", ProductID=" + usbDevice.getProductId());
            if (isDeviceFound(usbDevice)) {
                Log.i(str3, "getDevice found: " + usbDevice.getDeviceName());
                Log.i(str3, "Model: " + usbDevice.getDeviceName());
                Log.i(str3, "ID: " + usbDevice.getDeviceId());
                Log.i(str3, "Class: " + usbDevice.getDeviceClass());
                Log.i(str3, "Protocol: " + usbDevice.getDeviceProtocol());
                Log.i(str3, "Vendor ID " + usbDevice.getVendorId());
                Log.i(str3, "Product ID: " + usbDevice.getProductId());
                Log.i(str3, "Interface count: " + usbDevice.getInterfaceCount());
                Log.i(str3, "---------------------------------------");
                for (int i2 = 0; i2 < usbDevice.getInterfaceCount(); i2++) {
                    UsbInterface usbInterface = usbDevice.getInterface(i2);
                    String str4 = TAG;
                    Log.i(str4, "  *****     *****");
                    Log.i(str4, "  Interface index: " + i2);
                    Log.i(str4, "  Interface ID: " + usbInterface.getId());
                    Log.i(str4, "  Interface class: " + usbInterface.getInterfaceClass());
                    Log.i(str4, "  Interface protocol: " + usbInterface.getInterfaceProtocol());
                    Log.i(str4, "  Endpoint count: " + usbInterface.getEndpointCount());
                    for (int i3 = 0; i3 < usbInterface.getEndpointCount(); i3++) {
                        UsbEndpoint endpoint = usbInterface.getEndpoint(i3);
                        String str5 = TAG;
                        Log.i(str5, "    ++++   ++++   ++++");
                        Log.i(str5, "    Endpoint index: " + i3);
                        Log.i(str5, "    Attributes: " + endpoint.getAttributes());
                        Log.i(str5, "    Direction: " + endpoint.getDirection());
                        Log.i(str5, "    Number: " + endpoint.getEndpointNumber());
                        Log.i(str5, "    Interval: " + endpoint.getInterval());
                        Log.i(str5, "    Packet size: " + endpoint.getMaxPacketSize());
                        Log.i(str5, "    Type: " + endpoint.getType());
                    }
                }
                return usbDevice;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isDeviceFound(UsbDevice usbDevice) {
        if (usbDevice.getVendorId() == MTDeviceConstants.VID_MAGTEK) {
            int productId = usbDevice.getProductId();
            int length = MTDeviceConstants.PID_SCRA.length;
            for (int i = 0; i < length; i++) {
                if (productId == MTDeviceConstants.PID_SCRA[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    private byte getFeatureReportId() {
        int productId;
        UsbDevice usbDevice = this.m_usbDevice;
        return (usbDevice == null || !((productId = usbDevice.getProductId()) == MTDeviceConstants.PID_EMV_SWIPE || productId == MTDeviceConstants.PID_EMV || productId == MTDeviceConstants.PID_ODM_BOOTLOADER || productId == MTDeviceConstants.PID_TDYNAMO || productId == MTDeviceConstants.PID_KDYNAMO || productId == MTDeviceConstants.PID_DYNAWAVE || productId == MTDeviceConstants.PID_IDYNAMO6)) ? (byte) 0 : (byte) 1;
    }

    private int getFeatureReportCount(byte b) {
        int i = this.m_featureReportCount;
        return i >= 0 ? b != 0 ? i + 1 : i : b != 0 ? 61 : 60;
    }

    private boolean InputReportHasReportId() {
        return getFeatureReportId() > 0;
    }

    public boolean writeData(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return false;
        }
        byte featureReportId = getFeatureReportId();
        int featureReportCount = getFeatureReportCount(featureReportId);
        byte[] bArr2 = new byte[featureReportCount];
        if (featureReportId == 0) {
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        } else {
            bArr2[0] = featureReportId;
            System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
        }
        boolean featureReport = setFeatureReport(featureReportId, bArr2);
        if (featureReport) {
            byte[] bArr3 = new byte[featureReportCount];
            int featureReport2 = getFeatureReport(featureReportId, bArr3, 1000);
            byte[] bArr4 = null;
            if (featureReportId == 0) {
                if (featureReport2 > 1) {
                    int i = bArr3[1] + 2;
                    bArr4 = new byte[i];
                    System.arraycopy(bArr3, 0, bArr4, 0, i);
                }
            } else if (featureReport2 > 2) {
                int i2 = bArr3[2] + 2;
                bArr4 = new byte[i2];
                System.arraycopy(bArr3, 1, bArr4, 0, i2);
            }
            if (bArr4 != null) {
                OnFeatureReportReceived(bArr4);
            }
        }
        return featureReport;
    }

    public boolean setFeatureReport(byte b, byte[] bArr) {
        if (this.m_connection == null) {
            return false;
        }
        int i = (b & 255) | SSLContext.VERSION_SSL30;
        String str = TAG;
        Log.i(str, "setFeatureReport ReportTypeAndId=0x" + String.format("%04X", Integer.valueOf(i)));
        Log.i(str, "setFeatureReport Written=" + this.m_connection.controlTransfer(33, 9, i, 0, bArr, bArr.length, 5000));
        return true;
    }

    public int getFeatureReport(byte b, byte[] bArr, int i) {
        UsbDeviceConnection usbDeviceConnection = this.m_connection;
        if (usbDeviceConnection == null) {
            return 0;
        }
        int iControlTransfer = usbDeviceConnection.controlTransfer(FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_DS_CTL1_REG, 1, (b & 255) | SSLContext.VERSION_SSL30, 0, bArr, bArr.length, i);
        if (iControlTransfer > 0) {
            Arrays.copyOfRange(bArr, 0, iControlTransfer);
        }
        return iControlTransfer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDecriptorInformation() {
        int stringDescriptor;
        int stringDescriptor2;
        byte[] bArr = new byte[512];
        byte[] bArr2 = new byte[512];
        byte[] bArr3 = new byte[512];
        int deviceDescriptor = getDeviceDescriptor((byte) 0, bArr, 200);
        String str = TAG;
        Log.i(str, "Descriptor Size=" + deviceDescriptor);
        if (deviceDescriptor > 10) {
            this.m_productID = MTParser.getHexString(new byte[]{bArr[11], bArr[10]}, 0, null);
            Log.i(str, "Descriptor ProductID=" + this.m_productID);
        }
        if (deviceDescriptor > 16 && (stringDescriptor2 = getStringDescriptor(bArr[16], bArr2, 200)) > 0) {
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr2, 2, stringDescriptor2);
            Log.i(str, "Descriptor SN=" + MTParser.getHexString(bArrCopyOfRange));
            if (bArrCopyOfRange != null) {
                try {
                    byte[] bArr4 = new byte[bArrCopyOfRange.length / 2];
                    int i = 0;
                    int i2 = 0;
                    while (i < bArrCopyOfRange.length) {
                        if (i == 0 && bArrCopyOfRange[0] == 0 && bArrCopyOfRange[1] == 0) {
                            bArr4 = null;
                        }
                        bArr4[i2] = bArrCopyOfRange[i];
                        i += 2;
                        i2++;
                    }
                    this.m_deviceSerialNumber = bArr4;
                    Log.i(TAG, "Device SN=" + MTParser.getHexString(this.m_deviceSerialNumber));
                } catch (Exception unused) {
                }
            }
        }
        if (deviceDescriptor > 15 && (stringDescriptor = getStringDescriptor(bArr[15], bArr2, 200)) > 0) {
            byte[] bArrCopyOfRange2 = Arrays.copyOfRange(bArr2, 2, stringDescriptor);
            String str2 = TAG;
            Log.i(str2, "Product=" + MTParser.getHexString(bArrCopyOfRange2));
            try {
                this.m_productName = new String(bArrCopyOfRange2, "UTF16LE");
                Log.i(str2, "Descriptor Product=" + this.m_productName);
            } catch (Exception unused2) {
            }
        }
        getHidDecriptorInformation(bArr3);
    }

    private int getFeatureReportCount(byte[] bArr) {
        int i;
        int hIDDescriptor = getHIDDescriptor((byte) 0, bArr, 200);
        if (hIDDescriptor >= 8) {
            i = bArr[7] & 255;
            if (hIDDescriptor >= 9) {
                i += bArr[8] << 8;
            }
        } else {
            i = 0;
        }
        if (i <= 0) {
            return -1;
        }
        Log.i(TAG, "Descriptor Size=" + i);
        for (int i2 = 0; i2 < getReportDescriptor((byte) 0, new byte[i + 1], 200); i2++) {
        }
        return -1;
    }

    private void getHidDecriptorInformation(byte[] bArr) {
        int i;
        int hIDDescriptor = getHIDDescriptor((byte) 0, bArr, 200);
        if (hIDDescriptor >= 8) {
            i = bArr[7] & 255;
            if (hIDDescriptor >= 9) {
                i += bArr[8] << 8;
            }
        } else {
            i = 0;
        }
        if (i > 0) {
            String str = TAG;
            Log.i(str, "Descriptor Size=" + i);
            byte[] bArr2 = new byte[i + 1];
            int reportDescriptor = getReportDescriptor((byte) 0, bArr2, 200);
            Log.i(str, "Report Descriptor=" + MTParser.getHexString(bArr2));
            if (reportDescriptor > 0) {
                this.m_featureReportCount = MTHIDReportParser.getFeatureReportCount(bArr2, reportDescriptor);
                Log.i(str, "*** Feature Report Count=" + this.m_featureReportCount);
            }
        }
    }

    public int getDeviceDescriptor(byte b, byte[] bArr, int i) {
        return getDescriptor((b & 255) | 256, bArr, i);
    }

    public int getStringDescriptor(byte b, byte[] bArr, int i) {
        return getDescriptor((b & 255) | SSLContext.VERSION_SSL30, bArr, i);
    }

    public int getHIDDescriptor(byte b, byte[] bArr, int i) {
        return getDescriptor((b & 255) | 8448, bArr, i);
    }

    public int getReportDescriptor(byte b, byte[] bArr, int i) {
        return getDescriptor((b & 255) | 8704, bArr, i);
    }

    public int getDescriptor(int i, byte[] bArr, int i2) {
        String str = TAG;
        Log.i(str, "getDescriptor DescriptorTypeAndId=0x" + String.format("%04X", Integer.valueOf(i)));
        UsbDeviceConnection usbDeviceConnection = this.m_connection;
        if (usbDeviceConnection == null) {
            return 0;
        }
        int iControlTransfer = usbDeviceConnection.controlTransfer(129, 6, i, 0, bArr, bArr.length, i2);
        Log.i(str, "getDescriptor Length=" + iControlTransfer);
        if (iControlTransfer > 0) {
            Log.i(str, "Service descriptor=" + MTParser.getHexString(Arrays.copyOfRange(bArr, 0, iControlTransfer)));
        }
        return iControlTransfer;
    }

    private int getUSBPollingInterval() {
        return this.m_usbPollingInterval;
    }

    private int getUSBPollingTimeout() {
        return this.m_usbPollingTimeout;
    }

    private int getUSBPollingPause() {
        return this.m_usbPollingPause;
    }

    public void run1() {
        InputReportHasReportId();
        byte[] bArr = new byte[MTPPDeviceConstants.VENDOR_ID_MAGTEK];
        while (!this.m_threadStop) {
            if (this.m_connection == null || this.m_state != MTServiceState.Connected) {
                Thread.sleep(getUSBPollingInterval());
            } else {
                int iBulkTransfer = this.m_connection.bulkTransfer(this.m_usbEndpoint, bArr, 1024, 500);
                if (iBulkTransfer >= 0) {
                    byte[] bArr2 = new byte[iBulkTransfer];
                    System.arraycopy(bArr, 0, bArr2, 0, iBulkTransfer);
                    Log.i(TAG, "*** Actual Data=" + MTParser.getHexString(bArr2));
                    startProcessInputReport(bArr2);
                }
                try {
                    Thread.sleep(getUSBPollingPause());
                } catch (InterruptedException unused) {
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00e8 A[Catch: Exception -> 0x0108, TryCatch #1 {Exception -> 0x0108, blocks: (B:31:0x00de, B:33:0x00e4, B:34:0x00e8, B:37:0x00ef, B:39:0x00f5, B:43:0x00fe, B:45:0x0104), top: B:56:0x00de }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void run2() {
        /*
            Method dump skipped, instruction units count: 298
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.magtek.mobile.android.mtlib.MTUSBService.run2():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0081 A[Catch: Exception -> 0x00a1, TryCatch #3 {Exception -> 0x00a1, blocks: (B:28:0x0077, B:30:0x007d, B:31:0x0081, B:34:0x0088, B:36:0x008e, B:40:0x0097, B:42:0x009d), top: B:58:0x0077 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void run3() {
        /*
            r8 = this;
            boolean r0 = r8.InputReportHasReportId()
            r1 = 2049(0x801, float:2.871E-42)
            java.nio.ByteBuffer r1 = java.nio.ByteBuffer.allocate(r1)
            android.hardware.usb.UsbRequest r2 = new android.hardware.usb.UsbRequest
            r2.<init>()
            android.hardware.usb.UsbDeviceConnection r3 = r8.m_connection
            android.hardware.usb.UsbEndpoint r4 = r8.m_usbEndpoint
            r2.initialize(r3, r4)
        L16:
            boolean r3 = r8.m_threadStop
            if (r3 != 0) goto Lc2
            android.hardware.usb.UsbDeviceConnection r3 = r8.m_connection
            if (r3 == 0) goto Lb8
            com.magtek.mobile.android.mtlib.MTServiceState r3 = r8.m_state
            com.magtek.mobile.android.mtlib.MTServiceState r4 = com.magtek.mobile.android.mtlib.MTServiceState.Connected
            if (r3 == r4) goto L26
            goto Lb8
        L26:
            r3 = 0
            r4 = 0
            android.hardware.usb.UsbDeviceConnection r5 = r8.m_connection     // Catch: java.lang.Exception -> L67
            if (r5 == 0) goto L65
            com.magtek.mobile.android.mtlib.MTServiceState r5 = r8.m_state     // Catch: java.lang.Exception -> L67
            com.magtek.mobile.android.mtlib.MTServiceState r6 = com.magtek.mobile.android.mtlib.MTServiceState.Connected     // Catch: java.lang.Exception -> L67
            if (r5 != r6) goto L65
            int r5 = r1.remaining()     // Catch: java.lang.Exception -> L67
            byte[] r6 = r1.array()     // Catch: java.lang.Exception -> L67
            int r7 = r1.position()     // Catch: java.lang.Exception -> L67
            java.nio.ByteBuffer r6 = java.nio.ByteBuffer.wrap(r6, r7, r5)     // Catch: java.lang.Exception -> L67
            r2.queue(r6, r5)     // Catch: java.lang.Exception -> L67
            android.hardware.usb.UsbDeviceConnection r5 = r8.m_connection     // Catch: java.lang.Exception -> L67
            android.hardware.usb.UsbRequest r5 = r5.requestWait()     // Catch: java.lang.Exception -> L67
            if (r5 != r2) goto L65
            int r5 = r6.position()     // Catch: java.lang.Exception -> L67
            if (r5 <= 0) goto L6f
            int r7 = r1.position()     // Catch: java.lang.Exception -> L68
            int r7 = r7 + r5
            r1.position(r7)     // Catch: java.lang.Exception -> L68
            byte[] r3 = new byte[r5]     // Catch: java.lang.Exception -> L68
            byte[] r6 = r6.array()     // Catch: java.lang.Exception -> L68
            java.lang.System.arraycopy(r6, r4, r3, r4, r5)     // Catch: java.lang.Exception -> L68
            goto L6f
        L65:
            r5 = 0
            goto L6f
        L67:
            r5 = 0
        L68:
            java.lang.String r6 = com.magtek.mobile.android.mtlib.MTUSBService.TAG
            java.lang.String r7 = "InputReport Thread USBRequest Exception Caught"
            android.util.Log.e(r6, r7)
        L6f:
            if (r5 < 0) goto La8
            if (r5 <= 0) goto La8
            if (r0 != 0) goto L81
            if (r5 <= 0) goto La8
            byte[] r3 = java.util.Arrays.copyOfRange(r3, r4, r5)     // Catch: java.lang.Exception -> La1
            if (r3 == 0) goto La8
            r8.OnInputReportReceived(r3)     // Catch: java.lang.Exception -> La1
            goto La8
        L81:
            r4 = r3[r4]     // Catch: java.lang.Exception -> La1
            r6 = 1
            if (r4 != r6) goto L92
            if (r5 <= r6) goto La8
            byte[] r3 = java.util.Arrays.copyOfRange(r3, r6, r5)     // Catch: java.lang.Exception -> La1
            if (r3 == 0) goto La8
            r8.OnInputReportReceived(r3)     // Catch: java.lang.Exception -> La1
            goto La8
        L92:
            r7 = 2
            if (r4 != r7) goto La8
            if (r5 <= r6) goto La8
            byte[] r3 = java.util.Arrays.copyOfRange(r3, r6, r5)     // Catch: java.lang.Exception -> La1
            if (r3 == 0) goto La8
            r8.OnDeviceDataReceived(r3)     // Catch: java.lang.Exception -> La1
            goto La8
        La1:
            java.lang.String r3 = com.magtek.mobile.android.mtlib.MTUSBService.TAG
            java.lang.String r4 = "InputReport Thread Run Exception Caught"
            android.util.Log.e(r3, r4)
        La8:
            r1.clear()
            int r3 = r8.getUSBPollingPause()     // Catch: java.lang.InterruptedException -> Lb5
            long r3 = (long) r3     // Catch: java.lang.InterruptedException -> Lb5
            java.lang.Thread.sleep(r3)     // Catch: java.lang.InterruptedException -> Lb5
            goto L16
        Lb5:
            goto L16
        Lb8:
            int r3 = r8.getUSBPollingInterval()     // Catch: java.lang.InterruptedException -> Lb5
            long r3 = (long) r3     // Catch: java.lang.InterruptedException -> Lb5
            java.lang.Thread.sleep(r3)     // Catch: java.lang.InterruptedException -> Lb5
            goto L16
        Lc2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.magtek.mobile.android.mtlib.MTUSBService.run3():void");
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, java.lang.Runnable
    public void run() {
        InputReportHasReportId();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(2048);
        UsbRequest usbRequest = new UsbRequest();
        usbRequest.initialize(this.m_connection, this.m_usbEndpoint);
        while (!this.m_threadStop) {
            String str = TAG;
            Log.i(str, "*** Thread continues");
            if (this.m_connection == null || this.m_state != MTServiceState.Connected) {
                Thread.sleep(getUSBPollingInterval());
            } else {
                try {
                    if (this.m_connection != null && this.m_state == MTServiceState.Connected) {
                        Log.i(str, "*** Input USBRequest queue");
                        usbRequest.queue(byteBufferAllocate, 2047);
                        Log.i(str, "*** Input USBRequest requestWait");
                        if (this.m_connection.requestWait() == usbRequest) {
                            Log.i(str, "*** Input USBRequest inputBuffer.pos=" + byteBufferAllocate.position());
                            Log.i(str, "*** Input USBRequest inputBuffer.remaining=" + byteBufferAllocate.remaining());
                            Log.i(str, "*** Input USBRequest inputBuffer.limit=" + byteBufferAllocate.limit());
                            Log.i(str, "*** Input USBRequest inputBuffer.capacity=" + byteBufferAllocate.capacity());
                            int iPosition = byteBufferAllocate.position();
                            if (iPosition > 0) {
                                byte[] bArr = new byte[iPosition];
                                System.arraycopy(byteBufferAllocate.array(), 0, bArr, 0, iPosition);
                                Log.i(str, "*** Input Data=" + MTParser.getHexString(bArr));
                                startProcessInputReport(bArr);
                            }
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "InputReport Thread USBRequest Exception: " + e.toString());
                }
                byteBufferAllocate.clear();
                try {
                    Thread.sleep(getUSBPollingPause());
                } catch (InterruptedException unused) {
                }
            }
        }
        Log.i(TAG, "*** Thread stops");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processInputReport(byte[] bArr) {
        byte[] bArrCopyOfRange;
        int length = bArr != null ? bArr.length : 0;
        if (length >= 0) {
            try {
                if (!InputReportHasReportId()) {
                    byte[] bArrCopyOfRange2 = Arrays.copyOfRange(bArr, 0, length);
                    if (bArrCopyOfRange2 != null) {
                        synchronized (this.mInputReportSyncToken) {
                            OnInputReportReceived(bArrCopyOfRange2);
                        }
                        return;
                    }
                    return;
                }
                byte b = bArr[0];
                if (b == 1) {
                    byte[] bArrCopyOfRange3 = Arrays.copyOfRange(bArr, 1, length);
                    if (bArrCopyOfRange3 != null) {
                        synchronized (this.mInputReportSyncToken) {
                            OnInputReportReceived(bArrCopyOfRange3);
                        }
                        return;
                    }
                    return;
                }
                if (b != 2 || (bArrCopyOfRange = Arrays.copyOfRange(bArr, 1, length)) == null) {
                    return;
                }
                synchronized (this.mInputReportSyncToken) {
                    OnDeviceDataReceived(bArrCopyOfRange);
                }
                return;
            } catch (Exception e) {
                Log.e(TAG, "ProcessInputReport Exception: " + e.toString());
            }
            Log.e(TAG, "ProcessInputReport Exception: " + e.toString());
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.magtek.mobile.android.mtlib.MTUSBService$2] */
    private void startProcessInputReport(byte[] bArr) {
        new Thread(new Runnable() { // from class: com.magtek.mobile.android.mtlib.MTUSBService.2
            byte[] mData = null;

            @Override // java.lang.Runnable
            public void run() {
                MTUSBService.this.processInputReport(this.mData);
            }

            public Runnable init(byte[] bArr2) {
                if (bArr2 != null && bArr2.length > 0) {
                    this.mData = Arrays.copyOf(bArr2, bArr2.length);
                }
                return this;
            }
        }.init(bArr)).start();
    }
}
