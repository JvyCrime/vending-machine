package com.magtek.mobile.android.mtusdk.mmx;

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
import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MMXSerialService extends MMXBaseService {
    private static final String ACTION_USB_SERIAL_PERMISSION = "com.magtek.mobile.android.mtlib.USB_SERIAL_PERMISSION";
    private static final int MAX_PACKET_SIZE = 4096;
    private static final byte SLIP_ESCAPE = -37;
    private static final byte SLIP_ESCAPE_C0 = -36;
    private static final byte SLIP_ESCAPE_DB = -35;
    private static final byte SLIP_PACKET_START_END = -64;
    private static final String TAG = "MMXSerialService";
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
    private int mSerialPacketSize = Integer.MAX_VALUE;
    private UsbSerialInterface.UsbReadCallback m_usbSerialDeviceReadCallback = new UsbSerialInterface.UsbReadCallback() { // from class: com.magtek.mobile.android.mtusdk.mmx.MMXSerialService.1
        @Override // com.felhr.usbserial.UsbSerialInterface.UsbReadCallback
        public void onReceivedData(byte[] bArr) {
            try {
                if (MMXSerialService.this.mSLIP) {
                    MMXSerialService.this.processSLIPData(bArr);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private final BroadcastReceiver m_usbReceiver = new BroadcastReceiver() { // from class: com.magtek.mobile.android.mtusdk.mmx.MMXSerialService.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (MMXSerialService.ACTION_USB_SERIAL_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                    if (MMXSerialService.isDeviceFound(usbDevice)) {
                        if (intent.getBooleanExtra("permission", false)) {
                            if (MMXSerialService.this.mConnectionState != MMXConnectionState.Connected) {
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
                if (!MMXSerialService.isDeviceFound(usbDevice2) || usbDevice2 == null || MMXSerialService.this.mConnectionState == MMXConnectionState.Connected) {
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
            if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action) && MMXSerialService.isDeviceFound((UsbDevice) intent.getParcelableExtra("device"))) {
                onDetached();
            }
        }

        private void onConnected() {
            MMXSerialService.this.setConnectionState(MMXConnectionState.Connected);
        }

        private void onDisconnected() {
            MMXSerialService.this.setConnectionState(MMXConnectionState.Disconnected);
        }

        private void onDenied() {
            MMXSerialService.this.setConnectionState(MMXConnectionState.Disconnected);
        }

        private void onDetached() {
            MMXSerialService.this.disconnect();
        }

        private boolean setDevice(UsbDevice usbDevice) {
            if (usbDevice != null) {
                try {
                    if (!MMXSerialService.this.m_usbManager.hasPermission(usbDevice)) {
                        MMXSerialService.this.m_usbManager.requestPermission(usbDevice, MMXSerialService.this.m_permissionIntent);
                        return false;
                    }
                } catch (Exception unused) {
                    Log.i(MMXSerialService.TAG, "Exception caught in setDevice()");
                    return false;
                }
            }
            return MMXSerialService.this.openDevice(usbDevice);
        }
    };

    @Override // com.magtek.mobile.android.mtusdk.mmx.MMXBaseService, com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public void initialize(Context context, IMMXServiceAdapter iMMXServiceAdapter) {
        this.mContext = context;
        this.mServiceAdapter = iMMXServiceAdapter;
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.MMXBaseService, com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public void connect() {
        setConnectionState(MMXConnectionState.Connecting);
        String str = TAG;
        Log.i(str, "connecting");
        if (this.mSLIP) {
            resetSLIPDecoder();
        }
        this.m_dataReceived = null;
        this.m_usbManager = (UsbManager) this.mContext.getSystemService("usb");
        UsbDevice device = getDevice();
        this.m_usbDevice = device;
        if (device == null) {
            Log.w(str, "USB Device was not found");
            setConnectionState(MMXConnectionState.Disconnected);
            return;
        }
        Log.i(str, "preparing mPermissionIntent ");
        this.m_permissionIntent = PendingIntent.getBroadcast(this.mContext, 0, new Intent(ACTION_USB_SERIAL_PERMISSION), 0);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        intentFilter.addAction(ACTION_USB_SERIAL_PERMISSION);
        Log.i(str, "registering usbReceiver");
        this.mContext.registerReceiver(this.m_usbReceiver, intentFilter);
        if (this.m_usbDevice != null) {
            Log.i(str, "requestPermission");
            this.m_usbManager.requestPermission(this.m_usbDevice, this.m_permissionIntent);
        }
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.MMXBaseService, com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public void disconnect() {
        setConnectionState(MMXConnectionState.Disconnecting);
        closeDevice();
    }

    protected List<byte[]> getSerialPackets(byte[] bArr) {
        if (bArr.length < this.mSerialPacketSize) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(bArr);
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList();
        int i = 0;
        while (i < bArr.length) {
            int length = bArr.length - i;
            int i2 = this.mSerialPacketSize;
            if (length > i2) {
                byte[] bArr2 = new byte[i2];
                System.arraycopy(bArr, i, bArr2, 0, i2);
                arrayList2.add(bArr2);
                length = this.mSerialPacketSize;
            } else {
                byte[] bArr3 = new byte[length];
                System.arraycopy(bArr, i, bArr3, 0, length);
                arrayList2.add(bArr3);
            }
            i += length;
        }
        return arrayList2;
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.MMXBaseService, com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public void sendData(byte[] bArr) {
        String str = TAG;
        Log.d(str, "sendData data=" + TLVParser.getHexString(bArr));
        List<byte[]> serialPackets = getSerialPackets(bArr);
        int size = serialPackets.size();
        Log.d(str, "sendData: nPackets=" + size);
        for (int i = 0; i < size; i++) {
            raiseOnDataProgress((i * 100) / size);
            String str2 = TAG;
            Log.d(str2, "Sending Packet " + i);
            byte[] bArr2 = serialPackets.get(i);
            Log.d(str2, "Packet=" + TLVParser.getHexString(bArr2));
            writeData(bArr2);
            try {
                Thread.sleep(1L);
            } catch (Exception unused) {
            }
        }
        Log.d(TAG, "Packets Sent=" + size);
        raiseOnDataProgress(100);
    }

    private void writeData(byte[] bArr) {
        Log.i(TAG, "Sending: " + TLVParser.getHexString(bArr));
        if (this.m_usbSerialDevice == null || bArr == null || bArr.length <= 0) {
            return;
        }
        int length = bArr.length;
        byte[] bArr2 = new byte[length + 5];
        bArr2[0] = 0;
        bArr2[1] = (byte) ((length >> 24) & 255);
        bArr2[2] = (byte) ((length >> 16) & 255);
        bArr2[3] = (byte) ((length >> 8) & 255);
        bArr2[4] = (byte) (length & 255);
        System.arraycopy(bArr, 0, bArr2, 5, length);
        byte[] bArrBuildSLIPData = this.mSLIP ? buildSLIPData(bArr2) : null;
        if (bArrBuildSLIPData != null) {
            this.m_usbSerialDevice.write(bArrBuildSLIPData);
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
            setConnectionState(MMXConnectionState.Disconnected);
        }
        if (this.m_usbReceiver != null) {
            Log.i(TAG, "unregistering usbReceiver");
            this.mContext.unregisterReceiver(this.m_usbReceiver);
        }
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
        int i;
        if (bArr == null || (length = bArr.length) <= 0 || bArr[0] != 2 || length < 5 || (i = ((bArr[1] & 255) << 24) + ((bArr[2] & 255) << 16) + ((bArr[3] & 255) << 8) + (bArr[4] & 255)) <= 0 || length - 5 < i) {
            return;
        }
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 5, bArr2, 0, i);
        if (this.mServiceAdapter != null) {
            this.mServiceAdapter.OnDataReceived(bArr2);
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
}
