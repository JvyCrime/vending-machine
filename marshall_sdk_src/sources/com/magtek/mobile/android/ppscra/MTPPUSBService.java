package com.magtek.mobile.android.ppscra;

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
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class MTPPUSBService extends MTPPBaseService {
    private static final String a = "MTPPUSBService";
    private static Map<Integer, Integer> x;
    private String b;
    private byte[] c;
    private String d;
    private UsbManager e;
    private UsbDevice f;
    private UsbEndpoint g;
    private UsbInterface h;
    private UsbDeviceConnection i;
    private Thread l;
    private PendingIntent m;
    private UsbRequest j = null;
    private boolean k = true;
    private final int n = 200;
    private final int o = 512;
    private final int p = 512;
    private final int q = 512;
    private final int r = 64;
    private final int s = 500;
    private final int t = 10;
    private int u = 500;
    private int v = 10;
    private int w = 490;
    private final BroadcastReceiver y = new BroadcastReceiver() { // from class: com.magtek.mobile.android.ppscra.MTPPUSBService.1
        private void c() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("com.magtek.mobile.android.ppscra.USB_PERMISSION".equals(action)) {
                synchronized (this) {
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                    if (MTPPUSBService.b(usbDevice)) {
                        if (intent.getBooleanExtra("permission", false)) {
                            if (MTPPUSBService.this.m_state != MTServiceState.Connected) {
                                if (a(usbDevice)) {
                                    a();
                                } else {
                                    b();
                                }
                            }
                        } else {
                            c();
                        }
                    }
                }
                return;
            }
            if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(action)) {
                UsbDevice usbDevice2 = (UsbDevice) intent.getParcelableExtra("device");
                if (!MTPPUSBService.b(usbDevice2) || usbDevice2 == null || MTPPUSBService.this.m_state == MTServiceState.Connected) {
                    return;
                }
                if (a(usbDevice2)) {
                    a();
                    return;
                } else {
                    b();
                    return;
                }
            }
            if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action) && MTPPUSBService.b((UsbDevice) intent.getParcelableExtra("device"))) {
                d();
            }
        }

        private void a() {
            e();
            MTPPUSBService.this.setState(MTServiceState.Connected);
        }

        private void b() {
            MTPPUSBService.this.setState(MTServiceState.Disconnected);
        }

        private void d() {
            MTPPUSBService.this.disconnect();
        }

        private void e() {
            int i;
            int iIntValue;
            int stringDescriptor;
            int stringDescriptor2;
            byte[] bArr = new byte[512];
            byte[] bArr2 = new byte[512];
            byte[] bArr3 = new byte[512];
            int deviceDescriptor = MTPPUSBService.this.getDeviceDescriptor((byte) 0, bArr, 200);
            if (deviceDescriptor > 10) {
                MTPPUSBService.this.b = PPSCRACommon.getHexString(new byte[]{bArr[11], bArr[10]}, 0, null);
                Log.i(MTPPUSBService.a, "Descriptor ProductID=" + MTPPUSBService.this.b);
            }
            if (deviceDescriptor > 16 && (stringDescriptor2 = MTPPUSBService.this.getStringDescriptor(bArr[16], bArr2, 200)) > 0) {
                byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr2, 2, stringDescriptor2);
                Log.i(MTPPUSBService.a, "Descriptor SN=" + PPSCRACommon.getHexString(bArrCopyOfRange));
                try {
                    String str = new String(bArrCopyOfRange, "UTF16LE");
                    Log.i(MTPPUSBService.a, "Descriptor SN=" + str);
                    MTPPUSBService.this.c = PPSCRACommon.getByteArrayFromHexString(str, null);
                    Log.i(MTPPUSBService.a, "Device SN=" + PPSCRACommon.getHexString(MTPPUSBService.this.c));
                } catch (Exception unused) {
                }
            }
            if (deviceDescriptor > 15 && (stringDescriptor = MTPPUSBService.this.getStringDescriptor(bArr[15], bArr2, 200)) > 0) {
                byte[] bArrCopyOfRange2 = Arrays.copyOfRange(bArr2, 2, stringDescriptor);
                Log.i(MTPPUSBService.a, "Product=" + PPSCRACommon.getHexString(bArrCopyOfRange2));
                try {
                    MTPPUSBService.this.d = new String(bArrCopyOfRange2, "UTF16LE");
                    Log.i(MTPPUSBService.a, "Descriptor Product=" + MTPPUSBService.this.d);
                } catch (Exception unused2) {
                }
            }
            try {
                byte[] bArrCopyOfRange3 = Arrays.copyOfRange(bArr2, 2, MTPPUSBService.this.getStringDescriptor((byte) 4, bArr2, 200));
                Log.i(MTPPUSBService.a, "Descriptor FW=" + PPSCRACommon.getHexString(bArrCopyOfRange3));
                String str2 = new String(bArrCopyOfRange3, "UTF16LE");
                Log.i(MTPPUSBService.a, "Descriptor FW=" + str2);
            } catch (Exception unused3) {
            }
            int hIDDescriptor = MTPPUSBService.this.getHIDDescriptor((byte) 0, bArr3, 200);
            if (hIDDescriptor >= 8) {
                i = bArr3[7] & 255;
                if (hIDDescriptor >= 9) {
                    i += bArr3[8] << 8;
                }
            } else {
                i = 0;
            }
            if (i > 0) {
                Log.i(MTPPUSBService.a, "Descriptor Size=" + i);
                char c = 1;
                byte[] bArr4 = new byte[i + 1];
                int reportDescriptor = MTPPUSBService.this.getReportDescriptor((byte) 0, bArr4, 200);
                if (reportDescriptor > 0) {
                    int i2 = 14;
                    while (i2 < reportDescriptor) {
                        int i3 = i2 + 9;
                        byte[] bArrCopyOfRange4 = Arrays.copyOfRange(bArr4, i2, i3);
                        byte b = bArrCopyOfRange4[c];
                        byte b2 = bArrCopyOfRange4[5];
                        if (bArrCopyOfRange4[6] == -126) {
                            Log.i(MTPPUSBService.a, "USBService Descriptor for Input ReportId=0x" + String.format("%02X", Byte.valueOf(b)) + ", Size=" + ((int) b2));
                            if (MTPPUSBService.x.containsKey(Integer.valueOf(b)) && (iIntValue = ((Integer) MTPPUSBService.x.get(Integer.valueOf(b))).intValue()) != b2) {
                                Log.i(MTPPUSBService.a, "  Updating Report Size from " + iIntValue + " to " + ((int) b2));
                                MTPPUSBService.x.put(Integer.valueOf(b), Integer.valueOf(b2));
                            }
                        }
                        i2 = i3;
                        c = 1;
                    }
                }
            }
        }

        private boolean a(UsbDevice usbDevice) {
            if (usbDevice != null) {
                try {
                    if (!MTPPUSBService.this.e.hasPermission(usbDevice)) {
                        MTPPUSBService.this.e.requestPermission(usbDevice, MTPPUSBService.this.m);
                    }
                } catch (Exception unused) {
                    Log.i(MTPPUSBService.a, "Exception caught in setDevice()");
                    return false;
                }
            }
            if (usbDevice.getInterfaceCount() != 1) {
                Log.i(MTPPUSBService.a, "InterfaceCount() != 1");
            }
            MTPPUSBService.this.h = usbDevice.getInterface(0);
            if (MTPPUSBService.this.h.getEndpointCount() != 1) {
                Log.i(MTPPUSBService.a, "EndpointCount() != 1");
            }
            UsbEndpoint endpoint = MTPPUSBService.this.h.getEndpoint(0);
            if (endpoint.getType() != 3) {
                Log.i(MTPPUSBService.a, "Endpoint Type != XFER_INT");
            }
            MTPPUSBService.this.g = endpoint;
            if (usbDevice != null && MTPPUSBService.this.i == null) {
                UsbDeviceConnection usbDeviceConnectionOpenDevice = MTPPUSBService.this.e.openDevice(usbDevice);
                if (usbDeviceConnectionOpenDevice == null || !usbDeviceConnectionOpenDevice.claimInterface(MTPPUSBService.this.h, true)) {
                    MTPPUSBService.this.i = null;
                } else {
                    MTPPUSBService.this.i = usbDeviceConnectionOpenDevice;
                    MTPPUSBService.this.k = false;
                    Thread thread = new Thread(MTPPUSBService.this);
                    MTPPUSBService.this.l = thread;
                    thread.start();
                }
            }
            return true;
        }
    };

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public void close() {
    }

    static {
        HashMap map = new HashMap();
        x = map;
        map.put(0, 16);
        x.put(1, 13);
        x.put(63, 14);
        x.put(64, 15);
        x.put(32, 6);
        x.put(33, 20);
        x.put(34, 16);
        x.put(35, 127);
        x.put(36, 20);
        x.put(37, 3);
        x.put(39, 2);
        x.put(40, 4);
        x.put(41, 127);
        x.put(42, 3);
        x.put(44, 127);
        x.put(45, 63);
        x.put(46, 13);
    }

    private void a(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        if (this.m_serviceAdapter != null) {
            this.m_serviceAdapter.OnDeviceData(bArr);
        } else {
            Log.i(a, "ServiceAdapter is NULL");
        }
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public void initialize(Context context, MTServiceAdapter mTServiceAdapter) {
        this.m_context = context;
        this.m_serviceAdapter = mTServiceAdapter;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public void connect() {
        String str = a;
        Log.i(str, "connect");
        if (this.m_state != MTServiceState.Disconnected) {
            Log.i(str, "State is not Disconnected, cannot connect at this time.");
            return;
        }
        this.c = null;
        setState(MTServiceState.Connecting);
        this.e = (UsbManager) this.m_context.getSystemService("usb");
        UsbDevice usbDeviceC = c();
        this.f = usbDeviceC;
        if (usbDeviceC == null) {
            Log.w(str, "USB Device was not found");
            setState(MTServiceState.Disconnected);
            return;
        }
        Log.i(str, "preparing mPermissionIntent ");
        this.m = PendingIntent.getBroadcast(this.m_context, 0, new Intent("com.magtek.mobile.android.ppscra.USB_PERMISSION"), 0);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        intentFilter.addAction("com.magtek.mobile.android.ppscra.USB_PERMISSION");
        Log.i(str, "registering usbReceiver");
        this.m_context.registerReceiver(this.y, intentFilter);
        if (this.f != null) {
            Log.i(str, "requestPermission");
            this.e.requestPermission(this.f, this.m);
        }
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public void disconnect() {
        if (this.m_state == MTServiceState.Disconnecting) {
            Log.i(a, "State is Disconnecting, cannot disconnect at this time.");
            return;
        }
        if (this.m_state == MTServiceState.Disconnected) {
            Log.i(a, "State is Disconnected, cannot disconnect at this time.");
            return;
        }
        setState(MTServiceState.Disconnecting);
        if (this.f != null) {
            try {
                Thread thread = this.l;
                if (thread != null) {
                    this.k = true;
                    this.l = null;
                    Log.i(a, "Interrupt Thread");
                    thread.interrupt();
                }
                if (this.i != null) {
                    try {
                        if (this.j != null) {
                            String str = a;
                            Log.i(str, "Cancelling USB request...");
                            this.j.cancel();
                            Log.i(str, "Done cancelling USB request.");
                        }
                    } catch (Exception e) {
                        Log.i(a, "Exception cancelling USB request: " + e.toString());
                    }
                    if (this.h != null) {
                        String str2 = a;
                        Log.i(str2, "Closing Connection");
                        this.i.close();
                        Log.i(str2, "Releasing Connection Interface ");
                        this.i.releaseInterface(this.h);
                        this.h = null;
                    }
                    this.f = null;
                    this.i = null;
                    setState(MTServiceState.Disconnected);
                }
            } catch (Exception e2) {
                Log.i(a, "Exception: " + e2.toString());
            }
            setState(MTServiceState.Disconnected);
        }
        if (this.y != null) {
            Log.i(a, "unregistering usbReceiver");
            try {
                this.m_context.unregisterReceiver(this.y);
            } catch (Exception e3) {
                Log.i(a, "Exception: " + e3.toString());
            }
        }
    }

    private UsbDevice c() {
        String str = a;
        Log.i(str, "getDevice");
        UsbAccessory[] accessoryList = this.e.getAccessoryList();
        if (accessoryList != null) {
            Log.i(str, "USB Accessory Count=" + accessoryList.length);
            for (int i = 0; i < accessoryList.length; i++) {
                String str2 = a;
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
        HashMap<String, UsbDevice> deviceList = this.e.getDeviceList();
        if (!this.m_address.isEmpty()) {
            if (deviceList != null) {
                return deviceList.get(this.m_address);
            }
            return null;
        }
        for (UsbDevice usbDevice : deviceList.values()) {
            String str3 = a;
            Log.i(str3, "USB Device : VendorID=" + usbDevice.getVendorId() + ", ProductID=" + usbDevice.getProductId());
            if (b(usbDevice)) {
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
                    String str4 = a;
                    Log.i(str4, "  *****     *****");
                    Log.i(str4, "  Interface index: " + i2);
                    Log.i(str4, "  Interface ID: " + usbInterface.getId());
                    Log.i(str4, "  Inteface class: " + usbInterface.getInterfaceClass());
                    Log.i(str4, "  Interface protocol: " + usbInterface.getInterfaceProtocol());
                    Log.i(str4, "  Endpoint count: " + usbInterface.getEndpointCount());
                    for (int i3 = 0; i3 < usbInterface.getEndpointCount(); i3++) {
                        UsbEndpoint endpoint = usbInterface.getEndpoint(i3);
                        String str5 = a;
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
    public static boolean b(UsbDevice usbDevice) {
        if (usbDevice.getVendorId() == 2049) {
            return usbDevice.getProductId() == 12297 || usbDevice.getProductId() == 12292;
        }
        return false;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public void sendData(byte[] bArr) {
        byte[] bArr2;
        int featureReport;
        String str = a;
        Log.i(str, "USBService writeData data.length=" + bArr.length);
        Log.i(str, "USBService writeData data=" + PPSCRACommon.getHexString(bArr));
        if (bArr == null || bArr.length <= 1) {
            return;
        }
        byte b = bArr[0];
        byte b2 = bArr[1];
        byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 1, bArr.length);
        if (b != 1) {
            if (b != 0 || (featureReport = getFeatureReport(b2, (bArr2 = new byte[64]), 1000)) <= 0) {
                return;
            }
            a(Arrays.copyOfRange(bArr2, 0, featureReport));
            return;
        }
        if (setFeatureReport(b2, bArrCopyOfRange)) {
            byte[] bArr3 = new byte[3];
            if (getFeatureReport((byte) 1, bArr3, 1000) > 0) {
                Log.i(str, "USBService Command ACKed=" + PPSCRACommon.getHexString(bArr3));
                a(bArr3);
            }
        }
    }

    public boolean setFeatureReport(byte b, byte[] bArr) {
        String str = a;
        Log.i(str, "USBService setFeatureReport ReportId=0x" + String.format("%02X", Byte.valueOf(b)));
        Log.i(str, "USBService setFeatureReport Report=" + PPSCRACommon.getHexString(bArr));
        if (this.i == null) {
            return false;
        }
        int i = (b & 255) | SSLContext.VERSION_SSL30;
        Log.i(str, "USBService setFeatureReport ReportTypeAndId=0x" + String.format("%04X", Integer.valueOf(i)));
        Log.i(str, "USBService setFeatureReport Written=" + this.i.controlTransfer(33, 9, i, 0, bArr, bArr.length, 0));
        return true;
    }

    public int getFeatureReport(byte b, byte[] bArr, int i) {
        String str = a;
        Log.i(str, "USBService getFeatureReport ReportId=0x" + String.format("%02X", Byte.valueOf(b)));
        if (this.i == null) {
            return 0;
        }
        int i2 = (b & 255) | SSLContext.VERSION_SSL30;
        Log.i(str, "USBService getFeatureReport ReportTypeAndId=0x" + String.format("%04X", Integer.valueOf(i2)));
        int iControlTransfer = this.i.controlTransfer(FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_DS_CTL1_REG, 1, i2, 0, bArr, bArr.length, i);
        Log.i(str, "USBService getFeatureReport Length=" + iControlTransfer);
        if (iControlTransfer > 0) {
            Log.i(str, "USBService response=" + PPSCRACommon.getHexString(Arrays.copyOfRange(bArr, 0, iControlTransfer)));
        }
        return iControlTransfer;
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
        String str = a;
        Log.i(str, "USBService getDescriptor DescriptorTypeAndId=0x" + String.format("%04X", Integer.valueOf(i)));
        UsbDeviceConnection usbDeviceConnection = this.i;
        if (usbDeviceConnection == null) {
            return 0;
        }
        int iControlTransfer = usbDeviceConnection.controlTransfer(129, 6, i, 0, bArr, bArr.length, i2);
        Log.i(str, "USBService getDescriptor Length=" + iControlTransfer);
        if (iControlTransfer > 0) {
            Log.i(str, "USBService descriptor=" + PPSCRACommon.getHexString(Arrays.copyOfRange(bArr, 0, iControlTransfer)));
        }
        return iControlTransfer;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public String getProductID() {
        return this.b;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public byte[] getDeviceSerialNumber() {
        return this.c;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public String getProductName() {
        return this.d;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, com.magtek.mobile.android.ppscra.IMTPPService
    public String getFirmwareVersion() {
        return this.d;
    }

    private int d() {
        return this.u;
    }

    private int e() {
        return this.v;
    }

    @Override // com.magtek.mobile.android.ppscra.MTPPBaseService, java.lang.Runnable
    public void run() {
        int iPosition;
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(129);
        UsbRequest usbRequest = new UsbRequest();
        this.j = usbRequest;
        usbRequest.initialize(this.i, this.g);
        while (!this.k) {
            if (this.i == null || this.m_state != MTServiceState.Connected) {
                Thread.sleep(d());
            } else {
                try {
                    if (this.i != null && this.m_state == MTServiceState.Connected) {
                        usbRequest.queue(byteBufferAllocate, 128);
                        if (this.i.requestWait() == usbRequest && (iPosition = byteBufferAllocate.position()) > 0) {
                            byte[] bArr = new byte[iPosition];
                            System.arraycopy(byteBufferAllocate.array(), 0, bArr, 0, iPosition);
                            b(bArr);
                        }
                    }
                } catch (Exception e) {
                    Log.e(a, "InputReport Thread USBRequest Exception Caught: " + e.getMessage());
                }
                byteBufferAllocate.clear();
                try {
                    Thread.sleep(e());
                } catch (InterruptedException unused) {
                }
            }
        }
        Log.i(a, "*** Thread stops");
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.magtek.mobile.android.ppscra.MTPPUSBService$2] */
    private void b(byte[] bArr) {
        Log.i(a, "startProcessInputReport....");
        new Thread(new Runnable() { // from class: com.magtek.mobile.android.ppscra.MTPPUSBService.2
            byte[] a = null;

            @Override // java.lang.Runnable
            public void run() {
                MTPPUSBService.this.c(this.a);
                Log.i(MTPPUSBService.a, "Done startProcessInputReport.");
            }

            public Runnable a(byte[] bArr2) {
                if (bArr2 != null && bArr2.length > 0) {
                    this.a = Arrays.copyOf(bArr2, bArr2.length);
                }
                return this;
            }
        }.a(bArr)).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(byte[] bArr) {
        int length = bArr != null ? bArr.length : 0;
        if (length >= 0) {
            try {
                byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 0, length);
                if (bArrCopyOfRange != null) {
                    a(bArrCopyOfRange);
                }
            } catch (Exception e) {
                Log.e(a, "ProcessInputReport Exception: " + e.toString());
            }
        }
    }
}
