package com.magtek.mobile.android.mtcms;

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
import com.ftdi.j2xx.ft4222.FT_4222_Defines;
import iaik.security.ssl.SSLContext;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class MTUSBService extends MTBaseService {
    private static final String ACTION_USB_PERMISSION = "com.magtek.mobile.android.mtlib.USB_PERMISSION";
    private static final int DEVICE_DESCRIPTOR_IDPRODUCT = 10;
    private static final int DEVICE_DESCRIPTOR_IDVENDOR = 8;
    private static final int DEVICE_DESCRIPTOR_IMANUFACTURER = 14;
    private static final int DEVICE_DESCRIPTOR_IPRODUCT = 15;
    private static final int DEVICE_DESCRIPTOR_ISERIALNUMBER = 16;
    private static final int PID_CMS = 27;
    private static final String TAG = "MTUSBService";
    private static final int USB_DESCRIPTOR_TYPE_DEVICE = 1;
    private static final int USB_DESCRIPTOR_TYPE_HID = 33;
    private static final int USB_DESCRIPTOR_TYPE_REPORT = 34;
    private static final int USB_DESCRIPTOR_TYPE_STRING = 3;
    private static final int VID_MAGTEK = 2049;
    private UsbDeviceConnection m_connection;
    private int m_featureReportCount;
    private PendingIntent m_permissionIntent;
    private UsbDevice m_usbDevice;
    private UsbEndpoint m_usbEndpoint;
    private UsbInterface m_usbInterface;
    private UsbManager m_usbManager;
    private final int GET_DESCRIPTOR_TIMEOUT = 200;
    private final int DEVICE_DESCRIPTOR_MAX_SIZE = 512;
    private final int STRING_DESCRIPTOR_MAX_SIZE = 512;
    private final int HID_DESCRIPTOR_MAX_SIZE = 512;
    private final int INPUT_REPORT_MAX_SIZE = 64;
    private final int INPUT_REPORT_WITH_ID_MAX_SIZE = 1024;
    private final int INPUT_REPORT_POLLING_INTERVAL = 500;
    private final int INPUT_REPORT_POLLING_PAUSE = 10;
    private int m_usbPollingInterval = 500;
    private int m_usbPollingPause = 10;
    private int m_usbPollingTimeout = 490;
    private boolean m_threadStop = true;
    private Thread m_thread = null;
    private Object mInputReportSyncToken = new Object();
    private final BroadcastReceiver m_usbReceiver = new BroadcastReceiver() { // from class: com.magtek.mobile.android.mtcms.MTUSBService.1
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
            try {
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
                if (usbDevice != null && MTUSBService.this.m_connection == null) {
                    UsbDeviceConnection usbDeviceConnectionOpenDevice = MTUSBService.this.m_usbManager.openDevice(usbDevice);
                    if (usbDeviceConnectionOpenDevice == null || !usbDeviceConnectionOpenDevice.claimInterface(MTUSBService.this.m_usbInterface, true)) {
                        MTUSBService.this.m_connection = null;
                    } else {
                        MTUSBService.this.m_connection = usbDeviceConnectionOpenDevice;
                        MTUSBService.this.setState(MTServiceState.Connected);
                        MTUSBService.this.m_threadStop = false;
                        Thread thread = new Thread(MTUSBService.this);
                        MTUSBService.this.m_thread = thread;
                        thread.start();
                    }
                }
                return true;
            } catch (Exception unused) {
                Log.i(MTUSBService.TAG, "Exception caught in setDevice()");
                return false;
            }
        }
    };

    private byte getFeatureReportId() {
        return (byte) 5;
    }

    @Override // com.magtek.mobile.android.mtcms.MTBaseService
    public /* bridge */ /* synthetic */ void close() {
        super.close();
    }

    @Override // com.magtek.mobile.android.mtcms.MTBaseService, com.magtek.mobile.android.mtcms.IMTService
    public /* bridge */ /* synthetic */ MTServiceState getState() {
        return super.getState();
    }

    @Override // com.magtek.mobile.android.mtcms.MTBaseService, com.magtek.mobile.android.mtcms.IMTService
    public /* bridge */ /* synthetic */ void setAddress(String str) {
        super.setAddress(str);
    }

    @Override // com.magtek.mobile.android.mtcms.MTBaseService, com.magtek.mobile.android.mtcms.IMTService
    public /* bridge */ /* synthetic */ void setDeviceID(String str) {
        super.setDeviceID(str);
    }

    @Override // com.magtek.mobile.android.mtcms.MTBaseService, com.magtek.mobile.android.mtcms.IMTService
    public /* bridge */ /* synthetic */ void setServiceUUID(UUID uuid) {
        super.setServiceUUID(uuid);
    }

    @Override // com.magtek.mobile.android.mtcms.MTBaseService, com.magtek.mobile.android.mtcms.IMTService
    public void initialize(Context context, MTServiceAdapter mTServiceAdapter) {
        this.m_context = context;
        this.m_serviceAdapter = mTServiceAdapter;
    }

    protected void handleDeviceData(byte[] bArr) {
        if (bArr != null) {
            if (this.m_serviceAdapter != null) {
                this.m_serviceAdapter.OnDeviceData(bArr);
            } else {
                Log.i(TAG, "ServiceAdapter is NULL");
            }
        }
    }

    protected void OnFeatureReportReceived(byte[] bArr) {
        if (bArr != null) {
            byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
            Log.i(TAG, "Data Copy Length=" + bArrCopyOf.length);
            handleDeviceData(bArr);
        }
    }

    protected void OnInputReportReceived(byte[] bArr) {
        if (bArr != null) {
            byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
            Log.i(TAG, "Data Copy Length=" + bArrCopyOf.length);
            handleDeviceData(bArr);
        }
    }

    protected void OnDeviceDataReceived(byte[] bArr) {
        if (bArr != null) {
            byte[] bArrCopyOf = Arrays.copyOf(bArr, bArr.length);
            String str = TAG;
            Log.i(str, "Data Copy Length=" + bArrCopyOf.length);
            if (this.m_serviceAdapter != null) {
                this.m_serviceAdapter.OnDeviceData(bArr);
            } else {
                Log.i(str, "ServiceAdapter is NULL");
            }
        }
    }

    @Override // com.magtek.mobile.android.mtcms.MTBaseService, com.magtek.mobile.android.mtcms.IMTService
    public void connect() {
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

    @Override // com.magtek.mobile.android.mtcms.MTBaseService, com.magtek.mobile.android.mtcms.IMTService
    public void disconnect() {
        if (this.m_state == MTServiceState.Disconnecting || this.m_state == MTServiceState.Disconnected) {
            return;
        }
        setState(MTServiceState.Disconnecting);
        if (this.m_usbDevice != null) {
            Thread thread = this.m_thread;
            if (thread != null) {
                this.m_threadStop = true;
                this.m_thread = null;
                Log.i(TAG, "Interrupt Thread");
                thread.interrupt();
            }
            if (this.m_connection != null) {
                if (this.m_usbInterface != null) {
                    Log.i(TAG, "Releasig Connection Interface ");
                    this.m_connection.releaseInterface(this.m_usbInterface);
                    this.m_usbInterface = null;
                }
                Log.i(TAG, "Closing Connection");
                this.m_connection.close();
                this.m_usbDevice = null;
                this.m_connection = null;
            }
            setState(MTServiceState.Disconnected);
        }
        if (this.m_usbReceiver != null) {
            Log.i(TAG, "unregistering usbReceiver");
            this.m_context.unregisterReceiver(this.m_usbReceiver);
        }
    }

    @Override // com.magtek.mobile.android.mtcms.MTBaseService, com.magtek.mobile.android.mtcms.IMTService
    public void sendData(byte[] bArr) {
        writeData(bArr);
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
        for (UsbDevice usbDevice : this.m_usbManager.getDeviceList().values()) {
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
                    Log.i(str4, "  Inteface class: " + usbInterface.getInterfaceClass());
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
        return usbDevice.getVendorId() == 2049 && usbDevice.getProductId() == 27;
    }

    private int getFeatureReportCount(byte b) {
        return this.m_featureReportCount;
    }

    public boolean writeData(byte[] bArr) {
        String str = TAG;
        Log.i(str, "writeData data.length=" + bArr.length);
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
            int featureReport2 = getFeatureReport(featureReportId, bArr3, 500);
            byte[] bArr4 = null;
            Log.i(str, "Report ID=" + ((int) featureReportId));
            if (featureReportId == 0) {
                if (featureReport2 > 1) {
                    int i = bArr3[1] + 2;
                    bArr4 = new byte[i];
                    Log.i(str, "Actual Report Length=" + featureReport2);
                    Log.i(str, "Response Data Length=" + i);
                    System.arraycopy(bArr3, 0, bArr4, 0, i);
                }
            } else if (featureReport2 > 2) {
                int i2 = bArr3[2] + 2;
                bArr4 = new byte[i2];
                Log.i(str, "Actual Report Length=" + featureReport2);
                Log.i(str, "Response Data Length=" + i2);
                System.arraycopy(bArr3, 1, bArr4, 0, i2);
            }
            if (bArr4 != null) {
                OnFeatureReportReceived(bArr4);
            }
        }
        return featureReport;
    }

    public boolean setFeatureReport(byte b, byte[] bArr) {
        String str = TAG;
        Log.i(str, "setFeatureReport ReportId=0x" + String.format("%02X", Byte.valueOf(b)));
        Log.i(str, "setFeatureReport Report=" + MTParser.getHexString(bArr));
        if (this.m_connection == null) {
            return false;
        }
        int i = (b & 255) | SSLContext.VERSION_SSL30;
        Log.i(str, "setFeatureReport ReportTypeAndId=0x" + String.format("%04X", Integer.valueOf(i)));
        Log.i(str, "setFeatureReport Written=" + this.m_connection.controlTransfer(33, 9, i, 0, bArr, bArr.length, 5000));
        return true;
    }

    public int getFeatureReport(byte b, byte[] bArr, int i) {
        String str = TAG;
        Log.i(str, "getFeatureReport ReportId=0x" + String.format("%02X", Byte.valueOf(b)));
        if (this.m_connection == null) {
            return 0;
        }
        int i2 = (b & 255) | SSLContext.VERSION_SSL30;
        Log.i(str, "getFeatureReport ReportTypeAndId=0x" + String.format("%04X", Integer.valueOf(i2)));
        int iControlTransfer = this.m_connection.controlTransfer(FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_DS_CTL1_REG, 1, i2, 0, bArr, bArr.length, i);
        Log.i(str, "getFeatureReport Length=" + iControlTransfer);
        if (iControlTransfer > 0) {
            Arrays.copyOfRange(bArr, 0, iControlTransfer);
        }
        return iControlTransfer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDecriptorInformation() {
        getDeviceDescriptor((byte) 0, new byte[512], 200);
        getHidDecriptorInformation(new byte[512]);
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

    /* JADX WARN: Removed duplicated region for block: B:21:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0106  */
    @Override // com.magtek.mobile.android.mtcms.MTBaseService, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void run() {
        /*
            Method dump skipped, instruction units count: 311
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.magtek.mobile.android.mtcms.MTUSBService.run():void");
    }

    public void run1() {
        int maxPacketSize = this.m_usbEndpoint.getMaxPacketSize();
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(maxPacketSize);
        UsbRequest usbRequest = new UsbRequest();
        usbRequest.initialize(this.m_connection, this.m_usbEndpoint);
        while (!this.m_threadStop) {
            if (this.m_connection == null || this.m_state != MTServiceState.Connected) {
                Thread.sleep(getUSBPollingInterval());
            } else {
                try {
                    if (this.m_connection != null && this.m_state == MTServiceState.Connected) {
                        String str = TAG;
                        Log.i(str, "*** Input USBRequest queue");
                        usbRequest.queue(byteBufferAllocate, maxPacketSize);
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
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.magtek.mobile.android.mtcms.MTUSBService$2] */
    private void startProcessInputReport(byte[] bArr) {
        new Thread(new Runnable() { // from class: com.magtek.mobile.android.mtcms.MTUSBService.2
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

    /* JADX INFO: Access modifiers changed from: private */
    public void processInputReport(byte[] bArr) {
        byte[] bArrCopyOfRange;
        byte[] bArrCopyOfRange2;
        int length = bArr != null ? bArr.length : 0;
        if (length < 0 || length <= 0) {
            return;
        }
        try {
            byte b = bArr[0];
            Log.i(TAG, "InputReportId=" + ((int) b));
            if (b == 1) {
                if (length > 1 && (bArrCopyOfRange2 = Arrays.copyOfRange(bArr, 1, length)) != null) {
                    OnInputReportReceived(bArrCopyOfRange2);
                }
            } else if (b == 2 && length > 1 && (bArrCopyOfRange = Arrays.copyOfRange(bArr, 1, length)) != null) {
                OnDeviceDataReceived(bArrCopyOfRange);
            }
        } catch (Exception unused) {
            Log.e(TAG, "InputReport Thread Run Exception Caught");
        }
    }
}
