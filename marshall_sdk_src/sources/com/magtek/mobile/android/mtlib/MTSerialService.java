package com.magtek.mobile.android.mtlib;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;
import com.felhr.usbserial.UsbSerialDebugger;
import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class MTSerialService extends MTBaseService {
    private static final String ACTION_USB_SERIAL_PERMISSION = "com.magtek.mobile.android.mtlib.USB_SERIAL_PERMISSION";
    private static final int MAX_PACKET_SIZE = 4096;
    private static final byte SLIP_ESCAPE = -37;
    private static final byte SLIP_ESCAPE_C0 = -36;
    private static final byte SLIP_ESCAPE_DB = -35;
    private static final byte SLIP_PACKET_START_END = -64;
    private static final String TAG = "MTSerialService";
    private UsbDeviceConnection m_connection;
    private PendingIntent m_permissionIntent;
    private UsbDevice m_usbDevice;
    private UsbManager m_usbManager;
    private boolean mSLIP = true;
    private boolean mPacketStarted = false;
    private boolean mLastByteIsPacketStart = false;
    private boolean mLastByteIsEscape = false;
    private int mPacketSize = 0;
    private byte[] mPacket = new byte[4096];
    private UsbSerialDevice m_usbSerialDevice = null;
    private byte[] m_dataReceived = null;
    private UsbSerialInterface.UsbReadCallback m_usbSerialDeviceReadCallback = new UsbSerialInterface.UsbReadCallback() { // from class: com.magtek.mobile.android.mtlib.MTSerialService.1
        @Override // com.felhr.usbserial.UsbSerialInterface.UsbReadCallback
        public void onReceivedData(byte[] bArr) {
            try {
                if (MTSerialService.this.mSLIP) {
                    MTSerialService.this.processSLIPData(bArr);
                } else {
                    MTSerialService.this.processASCIIData(bArr);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private final BroadcastReceiver m_usbReceiver = new BroadcastReceiver() { // from class: com.magtek.mobile.android.mtlib.MTSerialService.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (MTSerialService.ACTION_USB_SERIAL_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                    if (MTSerialService.isDeviceFound(usbDevice)) {
                        if (intent.getBooleanExtra("permission", false)) {
                            if (MTSerialService.this.m_state != MTServiceState.Connected) {
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
                if (!MTSerialService.isDeviceFound(usbDevice2) || usbDevice2 == null || MTSerialService.this.m_state == MTServiceState.Connected) {
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
            if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action) && MTSerialService.isDeviceFound((UsbDevice) intent.getParcelableExtra("device"))) {
                onDetached();
            }
        }

        private void onConnected() {
            MTSerialService.this.setState(MTServiceState.Connected);
        }

        private void onDisconnected() {
            MTSerialService.this.setState(MTServiceState.Disconnected);
        }

        private void onDenied() {
            MTSerialService.this.setState(MTServiceState.Disconnected);
        }

        private void onDetached() {
            MTSerialService.this.disconnect();
        }

        private boolean setDevice(UsbDevice usbDevice) {
            if (usbDevice != null) {
                try {
                    if (!MTSerialService.this.m_usbManager.hasPermission(usbDevice)) {
                        MTSerialService.this.m_usbManager.requestPermission(usbDevice, MTSerialService.this.m_permissionIntent);
                        return false;
                    }
                } catch (Exception unused) {
                    Log.i(MTSerialService.TAG, "Exception caught in setDevice()");
                    return false;
                }
            }
            return MTSerialService.this.openDevice(usbDevice);
        }
    };

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void setAddress(String str) {
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void setConnectionRetry(boolean z) {
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void setConnectionTimeout(int i) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processASCIIData(byte[] bArr) {
        int length;
        byte[] bArr2;
        String str;
        int length2;
        if (bArr == null || (length = bArr.length) <= 0) {
            return;
        }
        byte[] bArr3 = this.m_dataReceived;
        if (bArr3 != null) {
            int length3 = bArr3.length;
            bArr2 = new byte[length3 + length];
            System.arraycopy(bArr3, 0, bArr2, 0, length3);
            System.arraycopy(bArr, 0, bArr2, length3, length);
        } else {
            bArr2 = new byte[length];
            System.arraycopy(bArr, 0, bArr2, 0, length);
        }
        int length4 = bArr2.length;
        if (length4 > 0) {
            int i = 0;
            for (int i2 = 0; i2 < length4; i2++) {
                if (bArr2[i2] == 13) {
                    int i3 = i2 - i;
                    if (i3 > 0) {
                        byte[] bArr4 = new byte[i3];
                        System.arraycopy(bArr2, i, bArr4, 0, i3);
                        MTParser.getHexString(bArr4);
                        try {
                            str = new String(bArr4, UsbSerialDebugger.ENCODING);
                        } catch (Exception unused) {
                            str = "";
                        }
                        byte[] byteArrayFromHexString = MTParser.getByteArrayFromHexString(str);
                        if (byteArrayFromHexString != null && (length2 = byteArrayFromHexString.length) > 1) {
                            byte b = byteArrayFromHexString[0];
                            if (b == 2) {
                                int i4 = length2 - 1;
                                byte[] bArr5 = new byte[i4];
                                System.arraycopy(byteArrayFromHexString, 1, bArr5, 0, i4);
                                if (this.m_serviceAdapter != null) {
                                    this.m_serviceAdapter.OnDeviceData(bArr5);
                                }
                            } else if (b == 4) {
                                int i5 = length2 - 1;
                                byte[] bArr6 = new byte[i5];
                                System.arraycopy(byteArrayFromHexString, 1, bArr6, 0, i5);
                                if (this.m_serviceAdapter != null) {
                                    this.m_serviceAdapter.OnCommandData(bArr6);
                                }
                            }
                        }
                    }
                    i = i2 + 1;
                }
            }
            int i6 = length4 - i;
            if (i6 > 0) {
                byte[] bArr7 = new byte[i6];
                this.m_dataReceived = bArr7;
                System.arraycopy(bArr2, i, bArr7, 0, i6);
                return;
            }
            this.m_dataReceived = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean openDevice(UsbDevice usbDevice) {
        if (usbDevice != null) {
            this.m_usbDevice = usbDevice;
            UsbDeviceConnection usbDeviceConnectionOpenDevice = this.m_usbManager.openDevice(usbDevice);
            this.m_connection = usbDeviceConnectionOpenDevice;
            if (usbDeviceConnectionOpenDevice != null) {
                UsbSerialDevice usbSerialDeviceCreateUsbSerialDevice = UsbSerialDevice.createUsbSerialDevice(this.m_usbDevice, usbDeviceConnectionOpenDevice);
                this.m_usbSerialDevice = usbSerialDeviceCreateUsbSerialDevice;
                if (usbSerialDeviceCreateUsbSerialDevice != null && usbSerialDeviceCreateUsbSerialDevice.open()) {
                    this.m_usbSerialDevice.setBaudRate(115200);
                    this.m_usbSerialDevice.setDataBits(8);
                    this.m_usbSerialDevice.setStopBits(1);
                    this.m_usbSerialDevice.setParity(0);
                    this.m_usbSerialDevice.setFlowControl(0);
                    this.m_usbSerialDevice.read(this.m_usbSerialDeviceReadCallback);
                    return true;
                }
            }
        }
        return false;
    }

    private void closeDevice() {
        UsbSerialDevice usbSerialDevice = this.m_usbSerialDevice;
        if (usbSerialDevice != null) {
            usbSerialDevice.close();
            this.m_usbDevice = null;
            this.m_connection = null;
            setState(MTServiceState.Disconnected);
        }
        if (this.m_usbReceiver != null) {
            Log.i(TAG, "unregistering usbReceiver");
            this.m_context.unregisterReceiver(this.m_usbReceiver);
        }
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public MTDeviceFeatures getDeviceFeatures() {
        MTDeviceFeatures mTDeviceFeatures = new MTDeviceFeatures();
        mTDeviceFeatures.MSR = true;
        return mTDeviceFeatures;
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void connect() {
        setState(MTServiceState.Connecting);
        String str = TAG;
        Log.i(str, "connecting");
        if (this.mSLIP) {
            resetSLIPDecoder();
        }
        this.m_dataReceived = null;
        this.m_usbManager = (UsbManager) this.m_context.getSystemService("usb");
        UsbDevice device = getDevice();
        this.m_usbDevice = device;
        if (device == null) {
            Log.w(str, "USB Device was not found");
            setState(MTServiceState.Disconnected);
            return;
        }
        Log.i(str, "preparing mPermissionIntent ");
        this.m_permissionIntent = PendingIntent.getBroadcast(this.m_context, 0, new Intent(ACTION_USB_SERIAL_PERMISSION), 0);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        intentFilter.addAction(ACTION_USB_SERIAL_PERMISSION);
        Log.i(str, "registering usbReceiver");
        this.m_context.registerReceiver(this.m_usbReceiver, intentFilter);
        if (this.m_usbDevice != null) {
            Log.i(str, "requestPermission");
            this.m_usbManager.requestPermission(this.m_usbDevice, this.m_permissionIntent);
        }
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public void disconnect() {
        setState(MTServiceState.Disconnecting);
        closeDevice();
    }

    @Override // com.magtek.mobile.android.mtlib.MTBaseService, com.magtek.mobile.android.mtlib.IMTService
    public boolean sendData(byte[] bArr) {
        byte[] bArrBuildASCIIData;
        if (this.m_usbSerialDevice != null && bArr != null && bArr.length > 0) {
            int length = bArr.length;
            byte[] bArr2 = new byte[length + 3];
            bArr2[0] = 5;
            bArr2[1] = (byte) ((length >> 8) & 255);
            bArr2[2] = (byte) (length & 255);
            System.arraycopy(bArr, 0, bArr2, 3, length);
            if (this.mSLIP) {
                bArrBuildASCIIData = buildSLIPData(bArr2);
            } else {
                bArrBuildASCIIData = buildASCIIData(bArr2);
            }
            if (bArrBuildASCIIData != null) {
                this.m_usbSerialDevice.write(bArrBuildASCIIData);
            }
        }
        return true;
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
        UsbDevice usbDevice = null;
        for (UsbDevice usbDevice2 : this.m_usbManager.getDeviceList().values()) {
            String str3 = TAG;
            Log.i(str3, "USB Device : VendorID=" + usbDevice2.getVendorId() + ", ProductID=" + usbDevice2.getProductId());
            if (isDeviceFound(usbDevice2)) {
                Log.i(str3, "getDevice found: " + usbDevice2.getDeviceName());
                usbDevice = usbDevice2;
            }
        }
        return usbDevice;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isDeviceFound(UsbDevice usbDevice) {
        int vendorId = usbDevice.getVendorId();
        int productId = usbDevice.getProductId();
        if (vendorId != 7531) {
            return (productId == 1 && productId == 2 && productId == 3) ? false : true;
        }
        return false;
    }

    private void resetSLIPDecoder() {
        this.mPacketStarted = false;
        this.mLastByteIsPacketStart = false;
        this.mLastByteIsEscape = false;
        this.mPacketSize = 0;
        this.mPacket = new byte[4096];
    }

    private void processSLIPPacket(byte[] bArr) {
        int length;
        if (bArr == null || (length = bArr.length - 3) <= 0) {
            return;
        }
        byte b = bArr[0];
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 3, bArr2, 0, length);
        if (b == 2) {
            if (this.m_serviceAdapter != null) {
                this.m_serviceAdapter.OnDeviceData(bArr2);
            }
        } else if (b == 4 && this.m_serviceAdapter != null) {
            this.m_serviceAdapter.OnCommandData(bArr2);
        }
    }

    private void decodeSLIPByte(byte b) {
        if (b == -64) {
            if (!this.mPacketStarted || this.mLastByteIsPacketStart) {
                this.mPacketStarted = true;
            } else {
                this.mPacketStarted = false;
                int i = this.mPacketSize;
                if (i > 0) {
                    byte[] bArr = new byte[i];
                    System.arraycopy(this.mPacket, 0, bArr, 0, i);
                    processSLIPPacket(bArr);
                }
            }
            this.mPacketSize = 0;
            this.mLastByteIsPacketStart = true;
            return;
        }
        if (this.mPacketStarted) {
            if (this.mLastByteIsEscape) {
                if (b == -36) {
                    byte[] bArr2 = this.mPacket;
                    int i2 = this.mPacketSize;
                    bArr2[i2] = -64;
                    this.mPacketSize = i2 + 1;
                } else if (b == -35) {
                    byte[] bArr3 = this.mPacket;
                    int i3 = this.mPacketSize;
                    bArr3[i3] = SLIP_ESCAPE;
                    this.mPacketSize = i3 + 1;
                }
                this.mLastByteIsEscape = false;
            } else if (b == -37) {
                this.mLastByteIsEscape = true;
            } else {
                byte[] bArr4 = this.mPacket;
                int i4 = this.mPacketSize;
                bArr4[i4] = b;
                this.mPacketSize = i4 + 1;
                this.mLastByteIsEscape = false;
            }
        }
        this.mLastByteIsPacketStart = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processSLIPData(byte[] bArr) {
        if (bArr != null) {
            for (byte b : bArr) {
                decodeSLIPByte(b);
            }
        }
    }

    private byte[] buildSLIPData(byte[] bArr) {
        int i;
        byte[] bArr2 = new byte[1024];
        if (bArr != null) {
            int length = bArr.length;
            bArr2[0] = -64;
            int i2 = 1;
            for (int i3 = 0; i3 < length; i3++) {
                if (bArr[i3] == -64) {
                    int i4 = i2 + 1;
                    bArr2[i2] = SLIP_ESCAPE;
                    i2 = i4 + 1;
                    bArr2[i4] = SLIP_ESCAPE_C0;
                } else if (bArr[i3] == -37) {
                    int i5 = i2 + 1;
                    bArr2[i2] = SLIP_ESCAPE;
                    i2 = i5 + 1;
                    bArr2[i5] = SLIP_ESCAPE_DB;
                } else {
                    bArr2[i2] = bArr[i3];
                    i2++;
                }
            }
            i = i2 + 1;
            bArr2[i2] = -64;
        } else {
            i = 0;
        }
        if (i > 0) {
            return Arrays.copyOfRange(bArr2, 0, i);
        }
        return null;
    }

    private byte[] buildASCIIData(byte[] bArr) {
        byte[] bytes;
        if (bArr == null || bArr.length <= 0 || (bytes = MTParser.getHexString(bArr).getBytes()) == null) {
            return null;
        }
        int length = bytes.length + 1;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bytes, 0, bArr2, 0, bytes.length);
        bArr2[length - 1] = 13;
        return bArr2;
    }
}
