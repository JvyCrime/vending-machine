package com.magtek.mobile.android.mtusdk.mmx;

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
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import java.nio.ByteBuffer;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MMXUSBService extends MMXBaseService {
    private static final String ACTION_USB_PERMISSION = "com.magtek.mobile.android.mmx.USB_PERMISSION";
    public static short PID_DYNAFLEX = 8224;
    public static short PID_DYNAPROX = 8227;
    private static final String TAG = "MMXUSBService";
    public static short VID_MAGTEK = 2049;
    private PendingIntent mPermissionIntent;
    private UsbDeviceConnection mUsbConnection;
    private UsbDevice mUsbDevice;
    private UsbEndpoint mUsbInEndpoint;
    private UsbInterface mUsbInterface;
    private UsbManager mUsbManager;
    private UsbEndpoint mUsbOutEndpoint;
    private int mInterfaceProtocol = 0;
    private Object mStateSyncToken = new Object();
    private Object mInputReportSyncToken = new Object();
    private Object mOutputReportSyncToken = new Object();
    private boolean mThreadStop = true;
    private Thread mThread = null;
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() { // from class: com.magtek.mobile.android.mtusdk.mmx.MMXUSBService.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (MMXUSBService.ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                    if (MMXUSBService.isDeviceFound(usbDevice)) {
                        if (intent.getBooleanExtra("permission", false)) {
                            if (MMXUSBService.this.mConnectionState != MMXConnectionState.Connected) {
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
                if (!MMXUSBService.isDeviceFound(usbDevice2) || usbDevice2 == null || MMXUSBService.this.mConnectionState == MMXConnectionState.Connected) {
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
            if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action) && MMXUSBService.isDeviceFound((UsbDevice) intent.getParcelableExtra("device"))) {
                onDetached();
            }
        }

        private void onConnected() {
            MMXUSBService.this.setConnectionState(MMXConnectionState.Connected);
        }

        private void onDisconnected() {
            MMXUSBService.this.setConnectionState(MMXConnectionState.Disconnected);
        }

        private void onDenied() {
            MMXUSBService.this.setConnectionState(MMXConnectionState.Disconnected);
        }

        private void onDetached() {
            MMXUSBService.this.disconnect();
        }

        private boolean setDevice(UsbDevice usbDevice) {
            if (usbDevice != null) {
                try {
                    if (!MMXUSBService.this.mUsbManager.hasPermission(usbDevice)) {
                        MMXUSBService.this.mUsbManager.requestPermission(usbDevice, MMXUSBService.this.mPermissionIntent);
                    }
                } catch (Exception unused) {
                    Log.i(MMXUSBService.TAG, "Exception caught in setDevice()");
                    return false;
                }
            }
            Log.i(MMXUSBService.TAG, "DeviceName=" + usbDevice.getDeviceName());
            if (usbDevice.getInterfaceCount() != 1) {
                Log.i(MMXUSBService.TAG, "InterfaceCount() != 1");
            }
            MMXUSBService.this.mUsbInterface = usbDevice.getInterface(0);
            Log.i(MMXUSBService.TAG, "EndpointCount=" + MMXUSBService.this.mUsbInterface.getEndpointCount());
            for (int i = 0; i < MMXUSBService.this.mUsbInterface.getEndpointCount(); i++) {
                UsbEndpoint endpoint = MMXUSBService.this.mUsbInterface.getEndpoint(i);
                int direction = endpoint.getDirection() & 128;
                int type = endpoint.getType();
                if (direction == 128) {
                    Log.i(MMXUSBService.TAG, "IN Endpoint Type" + type);
                    MMXUSBService.this.mUsbInEndpoint = endpoint;
                } else if (direction == 0) {
                    Log.i(MMXUSBService.TAG, "OUT Endpoint Type" + type);
                    MMXUSBService.this.mUsbOutEndpoint = endpoint;
                }
            }
            MMXUSBService mMXUSBService = MMXUSBService.this;
            mMXUSBService.mInterfaceProtocol = mMXUSBService.mUsbInterface.getInterfaceProtocol();
            if (usbDevice != null && MMXUSBService.this.mUsbConnection == null) {
                UsbDeviceConnection usbDeviceConnectionOpenDevice = MMXUSBService.this.mUsbManager.openDevice(usbDevice);
                if (usbDeviceConnectionOpenDevice != null) {
                    Log.i(MMXUSBService.TAG, "*** Interface Protocol=" + MMXUSBService.this.mInterfaceProtocol);
                    if (MMXUSBService.this.mInterfaceProtocol == 0) {
                        Log.i(MMXUSBService.TAG, "*** HID Interface Protocol");
                        boolean zClaimInterface = usbDeviceConnectionOpenDevice.claimInterface(MMXUSBService.this.mUsbInterface, true);
                        Log.i(MMXUSBService.TAG, "*** Claimed interface=" + zClaimInterface);
                    } else if (MMXUSBService.this.mInterfaceProtocol == 1) {
                        Log.i(MMXUSBService.TAG, "*** HID KB Interface Protocol");
                        boolean zClaimInterface2 = usbDeviceConnectionOpenDevice.claimInterface(MMXUSBService.this.mUsbInterface, true);
                        Log.i(MMXUSBService.TAG, "*** Claimed interface=" + zClaimInterface2);
                    } else {
                        boolean zClaimInterface3 = usbDeviceConnectionOpenDevice.claimInterface(MMXUSBService.this.mUsbInterface, false);
                        Log.i(MMXUSBService.TAG, "*** Claimed interface=" + zClaimInterface3);
                    }
                    MMXUSBService.this.mUsbConnection = usbDeviceConnectionOpenDevice;
                    if (MMXUSBService.this.mInterfaceProtocol == 0) {
                        MMXUSBService.this.mThreadStop = false;
                        Thread thread = new Thread(MMXUSBService.this);
                        MMXUSBService.this.mThread = thread;
                        thread.start();
                    }
                } else {
                    MMXUSBService.this.mUsbConnection = null;
                }
            }
            return true;
        }
    };

    public static String[] GetDeviceList() {
        return new String[0];
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.MMXBaseService, com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public void initialize(Context context, IMMXServiceAdapter iMMXServiceAdapter) {
        this.mContext = context;
        this.mServiceAdapter = iMMXServiceAdapter;
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.MMXBaseService, com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public void connect() {
        synchronized (this.mStateSyncToken) {
            if (this.mConnectionState != MMXConnectionState.Disconnected) {
                Log.i(TAG, "State is not Disconnected, cannot connect at this time.");
                return;
            }
            setConnectionState(MMXConnectionState.Connecting);
            String str = TAG;
            Log.i(str, "connecting");
            this.mUsbManager = (UsbManager) this.mContext.getSystemService("usb");
            UsbDevice device = getDevice();
            this.mUsbDevice = device;
            if (device == null) {
                Log.w(str, "USB Device was not found");
                setConnectionState(MMXConnectionState.Disconnected);
                return;
            }
            Log.i(str, "preparing mPermissionIntent ");
            this.mPermissionIntent = PendingIntent.getBroadcast(this.mContext, 0, new Intent(ACTION_USB_PERMISSION), 0);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
            intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
            intentFilter.addAction(ACTION_USB_PERMISSION);
            Log.i(str, "registering usbReceiver");
            this.mContext.registerReceiver(this.mUsbReceiver, intentFilter);
            if (this.mUsbDevice != null) {
                Log.i(str, "requestPermission");
                this.mUsbManager.requestPermission(this.mUsbDevice, this.mPermissionIntent);
            }
        }
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.MMXBaseService, com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public void disconnect() {
        if (this.mUsbDevice != null) {
            setConnectionState(MMXConnectionState.Disconnecting);
            try {
                Thread thread = this.mThread;
                if (thread != null) {
                    this.mThreadStop = true;
                    this.mThread = null;
                    Log.i(TAG, "Interrupt Thread");
                    thread.interrupt();
                }
                if (this.mUsbConnection != null) {
                    if (this.mUsbInterface != null) {
                        Log.i(TAG, "Releasing Connection Interface ");
                        this.mUsbConnection.releaseInterface(this.mUsbInterface);
                        this.mUsbInterface = null;
                    }
                    Log.i(TAG, "Closing Connection");
                    this.mUsbConnection.close();
                    this.mUsbDevice = null;
                    this.mUsbConnection = null;
                }
            } catch (Exception e) {
                Log.i(TAG, "Exception: " + e.toString());
            }
            setConnectionState(MMXConnectionState.Disconnected);
        }
        if (this.mUsbReceiver != null) {
            Log.i(TAG, "unregistering usbReceiver");
            try {
                this.mContext.unregisterReceiver(this.mUsbReceiver);
            } catch (Exception e2) {
                Log.i(TAG, "Exception: " + e2.toString());
            }
        }
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.MMXBaseService, com.magtek.mobile.android.mtusdk.mmx.IMMXService
    public void sendData(byte[] bArr) {
        String str = TAG;
        Log.d(str, "sendData data=" + TLVParser.getHexString(bArr));
        List<byte[]> packets = getPackets(bArr);
        int size = packets.size();
        Log.d(str, "sendData: nPackets=" + size);
        for (int i = 0; i < size; i++) {
            raiseOnDataProgress((i * 100) / size);
            String str2 = TAG;
            Log.d(str2, "Sending Packet " + i);
            byte[] bArr2 = packets.get(i);
            Log.d(str2, "Packet=" + TLVParser.getHexString(bArr2));
            writeOutputReport(bArr2);
            try {
                Thread.sleep(1L);
            } catch (Exception unused) {
            }
        }
        Log.d(TAG, "Packets Sent=" + size);
        raiseOnDataProgress(100);
    }

    public boolean writeOutputReport(byte[] bArr) {
        if (this.mUsbConnection == null || bArr == null) {
            return false;
        }
        byte[] bArr2 = new byte[64];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        this.mUsbConnection.controlTransfer(33, 9, 512, 0, bArr2, 64, PathInterpolatorCompat.MAX_NUM_POINTS);
        return true;
    }

    public void writeOutputReport_0(byte[] bArr) {
        if (this.mConnectionState != MMXConnectionState.Connected || this.mUsbConnection == null || this.mUsbOutEndpoint == null || bArr == null) {
            return;
        }
        UsbRequest usbRequest = new UsbRequest();
        usbRequest.initialize(this.mUsbConnection, this.mUsbOutEndpoint);
        String str = TAG;
        Log.i(str, "writeOutputReport data.length=" + bArr.length);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(64);
        byteBufferAllocate.put(bArr, 0, bArr.length);
        int length = 64 - bArr.length;
        if (length > 0) {
            byteBufferAllocate.put(new byte[length], 0, length);
        }
        Log.i(str, "writeOutputReport result=" + usbRequest.queue(byteBufferAllocate, 64));
        try {
            if (this.mUsbConnection.requestWait() == usbRequest) {
                Log.i(str, "writeOutputReport requestWait completed");
            } else {
                Log.i(str, "writeOutputReport requestWait NOT the same request");
            }
        } catch (Exception e) {
            Log.i(TAG, "writeOutputReport requestWait Exception: " + e.toString());
        }
    }

    private UsbDevice getDevice() {
        String str = TAG;
        Log.i(str, "getDevice");
        UsbAccessory[] accessoryList = this.mUsbManager.getAccessoryList();
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
        for (UsbDevice usbDevice : this.mUsbManager.getDeviceList().values()) {
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
        if (usbDevice.getVendorId() != VID_MAGTEK) {
            return false;
        }
        int productId = usbDevice.getProductId();
        return productId == PID_DYNAFLEX || productId == PID_DYNAPROX;
    }

    @Override // com.magtek.mobile.android.mtusdk.mmx.MMXBaseService, java.lang.Runnable
    public void run() {
        int maxPacketSize = this.mUsbInEndpoint.getMaxPacketSize();
        Log.i(TAG, "*** inputReportSize=" + maxPacketSize);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(maxPacketSize);
        UsbRequest usbRequest = new UsbRequest();
        usbRequest.initialize(this.mUsbConnection, this.mUsbInEndpoint);
        boolean z = false;
        while (!this.mThreadStop) {
            if (this.mConnectionState == null || this.mConnectionState != MMXConnectionState.Connected) {
                Thread.sleep(1L);
            } else {
                try {
                    if (this.mConnectionState != null && this.mConnectionState == MMXConnectionState.Connected) {
                        if (!z) {
                            try {
                                usbRequest.queue(byteBufferAllocate, maxPacketSize);
                                z = true;
                            } catch (Exception e) {
                                Log.i(TAG, "*** Input request.queue exception: " + e.getMessage());
                            }
                        }
                        String str = TAG;
                        Log.i(str, "*** Input USBRequest requestWait");
                        if (this.mUsbConnection.requestWait() == usbRequest) {
                            try {
                                int iPosition = byteBufferAllocate.position();
                                if (iPosition > 0) {
                                    byte[] bArr = new byte[iPosition];
                                    System.arraycopy(byteBufferAllocate.array(), 0, bArr, 0, iPosition);
                                    synchronized (this.mInputReportSyncToken) {
                                        startProcessInputData(bArr);
                                    }
                                }
                                z = false;
                            } catch (Exception e2) {
                                e = e2;
                                z = false;
                                Log.e(TAG, "InputReport Thread USBRequest Exception: " + e.toString());
                            }
                        } else {
                            Log.i(str, "*** Input USBRequest not the same");
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                }
                byteBufferAllocate.clear();
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException unused) {
                }
            }
        }
        Log.i(TAG, "*** Thread stops");
    }
}
