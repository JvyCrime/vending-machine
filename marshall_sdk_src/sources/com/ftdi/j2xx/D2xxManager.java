package com.ftdi.j2xx;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;
import com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbId;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class D2xxManager {
    protected static final String ACTION_USB_PERMISSION = "com.ftdi.j2xx";
    public static final int FTDI_BREAK_OFF = 0;
    public static final int FTDI_BREAK_ON = 16384;
    public static final byte FT_BI = 16;
    public static final byte FT_BITMODE_ASYNC_BITBANG = 1;
    public static final byte FT_BITMODE_CBUS_BITBANG = 32;
    public static final byte FT_BITMODE_FAST_SERIAL = 16;
    public static final byte FT_BITMODE_MCU_HOST = 8;
    public static final byte FT_BITMODE_MPSSE = 2;
    public static final byte FT_BITMODE_RESET = 0;
    public static final byte FT_BITMODE_SYNC_BITBANG = 4;
    public static final byte FT_BITMODE_SYNC_FIFO = 64;
    public static final byte FT_CTS = 16;
    public static final byte FT_DATA_BITS_7 = 7;
    public static final byte FT_DATA_BITS_8 = 8;
    public static final byte FT_DCD = -128;
    public static final int FT_DEVICE_2232 = 4;
    public static final int FT_DEVICE_2232H = 6;
    public static final int FT_DEVICE_232B = 0;
    public static final int FT_DEVICE_232H = 8;
    public static final int FT_DEVICE_232R = 5;
    public static final int FT_DEVICE_245R = 5;
    public static final int FT_DEVICE_4222_0 = 10;
    public static final int FT_DEVICE_4222_1_2 = 11;
    public static final int FT_DEVICE_4222_3 = 12;
    public static final int FT_DEVICE_4232H = 7;
    public static final int FT_DEVICE_8U232AM = 1;
    public static final int FT_DEVICE_UNKNOWN = 3;
    public static final int FT_DEVICE_X_SERIES = 9;
    public static final byte FT_DSR = 32;
    public static final byte FT_EVENT_LINE_STATUS = 4;
    public static final byte FT_EVENT_MODEM_STATUS = 2;
    public static final byte FT_EVENT_REMOVED = 8;
    public static final byte FT_EVENT_RXCHAR = 1;
    public static final byte FT_FE = 8;
    public static final byte FT_FLAGS_HI_SPEED = 2;
    public static final byte FT_FLAGS_OPENED = 1;
    public static final short FT_FLOW_DTR_DSR = 512;
    public static final short FT_FLOW_NONE = 0;
    public static final short FT_FLOW_RTS_CTS = 256;
    public static final short FT_FLOW_XON_XOFF = 1024;
    public static final byte FT_OE = 2;
    public static final byte FT_PARITY_EVEN = 2;
    public static final byte FT_PARITY_MARK = 3;
    public static final byte FT_PARITY_NONE = 0;
    public static final byte FT_PARITY_ODD = 1;
    public static final byte FT_PARITY_SPACE = 4;
    public static final byte FT_PE = 4;
    public static final byte FT_PURGE_RX = 1;
    public static final byte FT_PURGE_TX = 2;
    public static final byte FT_RI = 64;
    public static final byte FT_STOP_BITS_1 = 0;
    public static final byte FT_STOP_BITS_2 = 2;
    private static final String TAG = "D2xx::";
    private static boolean bRegisterBroadcast = true;
    private static boolean bRequestPermission = true;
    private static Context mContext;
    private static D2xxManager mInstance;
    private static PendingIntent mPendingIntent;
    private static IntentFilter mPermissionFilter;
    private static List<FtVidPid> mSupportedDevices = new ArrayList(Arrays.asList(new FtVidPid(UsbId.VENDOR_FTDI, UsbId.FTDI_FT231X), new FtVidPid(UsbId.VENDOR_FTDI, 24596), new FtVidPid(UsbId.VENDOR_FTDI, 24593), new FtVidPid(UsbId.VENDOR_FTDI, 24592), new FtVidPid(UsbId.VENDOR_FTDI, UsbId.FTDI_FT232R), new FtVidPid(UsbId.VENDOR_FTDI, 24582), new FtVidPid(UsbId.VENDOR_FTDI, 24604), new FtVidPid(UsbId.VENDOR_FTDI, 64193), new FtVidPid(UsbId.VENDOR_FTDI, 64194), new FtVidPid(UsbId.VENDOR_FTDI, 64195), new FtVidPid(UsbId.VENDOR_FTDI, 64196), new FtVidPid(UsbId.VENDOR_FTDI, 64197), new FtVidPid(UsbId.VENDOR_FTDI, 64198), new FtVidPid(UsbId.VENDOR_FTDI, 24594), new FtVidPid(2220, 4133), new FtVidPid(5590, 1), new FtVidPid(UsbId.VENDOR_FTDI, 24599)));
    private static BroadcastReceiver mUsbDevicePermissions = new BroadcastReceiver() { // from class: com.ftdi.j2xx.D2xxManager.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (D2xxManager.ACTION_USB_PERMISSION.equals(intent.getAction())) {
                synchronized (this) {
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                    if (!intent.getBooleanExtra("permission", false)) {
                        Log.d(D2xxManager.TAG, "permission denied for device " + usbDevice);
                    }
                }
            }
        }
    };
    private static UsbManager mUsbManager;
    private ArrayList<FT_Device> mFtdiDevices;
    private BroadcastReceiver mUsbPlugEvents = new BroadcastReceiver() { // from class: com.ftdi.j2xx.D2xxManager.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.hardware.usb.action.USB_DEVICE_DETACHED".equals(action)) {
                UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                FT_Device fT_DeviceFindDevice = D2xxManager.this.findDevice(usbDevice);
                while (fT_DeviceFindDevice != null) {
                    fT_DeviceFindDevice.close();
                    synchronized (D2xxManager.this.mFtdiDevices) {
                        D2xxManager.this.mFtdiDevices.remove(fT_DeviceFindDevice);
                    }
                    fT_DeviceFindDevice = D2xxManager.this.findDevice(usbDevice);
                }
                return;
            }
            if ("android.hardware.usb.action.USB_DEVICE_ATTACHED".equals(action)) {
                D2xxManager.this.addUsbDevice((UsbDevice) intent.getParcelableExtra("device"));
            }
        }
    };

    public static class FtDeviceInfoListNode {
        public short bcdDevice;
        public int breakOnParam;
        public String description;
        public int flags;
        public int handle;
        public byte iSerialNumber;
        public int id;
        public short lineStatus;
        public int location;
        public short modemStatus;
        public String serialNumber;
        public int type;
    }

    public static int getLibraryVersion() {
        return 545259520;
    }

    public static class DriverParameters {
        private int mBufferSize = 16384;
        private int mMaxTransferSize = 16384;
        private int mNrBuffers = 16;
        private int mRxTimeout = 5000;

        public boolean setMaxBufferSize(int i) {
            if (i >= 64 && i <= 262144) {
                this.mBufferSize = i;
                return true;
            }
            Log.e(D2xxManager.TAG, "***bufferSize Out of correct range***");
            return false;
        }

        public int getMaxBufferSize() {
            return this.mBufferSize;
        }

        public boolean setMaxTransferSize(int i) {
            if (i >= 64 && i <= 262144) {
                this.mMaxTransferSize = i;
                return true;
            }
            Log.e(D2xxManager.TAG, "***maxTransferSize Out of correct range***");
            return false;
        }

        public int getMaxTransferSize() {
            return this.mMaxTransferSize;
        }

        public boolean setBufferNumber(int i) {
            if (i >= 2 && i <= 16) {
                this.mNrBuffers = i;
                return true;
            }
            Log.e(D2xxManager.TAG, "***nrBuffers Out of correct range***");
            return false;
        }

        public int getBufferNumber() {
            return this.mNrBuffers;
        }

        public boolean setReadTimeout(int i) {
            this.mRxTimeout = i;
            return true;
        }

        public int getReadTimeout() {
            return this.mRxTimeout;
        }
    }

    public static class D2xxException extends IOException {
        private static final long serialVersionUID = 1;

        public D2xxException() {
        }

        public D2xxException(String str) {
            super(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public FT_Device findDevice(UsbDevice usbDevice) {
        FT_Device fT_Device;
        synchronized (this.mFtdiDevices) {
            int size = this.mFtdiDevices.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    fT_Device = null;
                    break;
                }
                FT_Device fT_Device2 = this.mFtdiDevices.get(i);
                if (fT_Device2.getUsbDevice().equals(usbDevice)) {
                    fT_Device = fT_Device2;
                    break;
                }
                i++;
            }
        }
        return fT_Device;
    }

    public boolean isFtDevice(UsbDevice usbDevice) {
        if (mContext == null) {
            return false;
        }
        FtVidPid ftVidPid = new FtVidPid(usbDevice.getVendorId(), usbDevice.getProductId());
        boolean zContains = mSupportedDevices.contains(ftVidPid);
        Log.v(TAG, ftVidPid.toString());
        return zContains;
    }

    private static synchronized boolean updateContext(Context context) {
        if (context == null) {
            return false;
        }
        if (mContext != context) {
            mContext = context;
            mPendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, new Intent(ACTION_USB_PERMISSION), 134217728);
            mPermissionFilter = new IntentFilter(ACTION_USB_PERMISSION);
            mContext.getApplicationContext().registerReceiver(mUsbDevicePermissions, mPermissionFilter);
        }
        return true;
    }

    private boolean isPermitted(UsbDevice usbDevice) {
        if (bRequestPermission && !mUsbManager.hasPermission(usbDevice)) {
            mUsbManager.requestPermission(usbDevice, mPendingIntent);
        }
        return mUsbManager.hasPermission(usbDevice);
    }

    private boolean checkAskPermitted(UsbDevice usbDevice) {
        if (!mUsbManager.hasPermission(usbDevice)) {
            mUsbManager.requestPermission(usbDevice, mPendingIntent);
        }
        return mUsbManager.hasPermission(usbDevice);
    }

    private D2xxManager(Context context) throws D2xxException {
        Log.v(TAG, "Start constructor");
        if (context == null) {
            throw new D2xxException("D2xx init failed: Can not find parentContext!");
        }
        updateContext(context);
        if (!findUsbManger()) {
            throw new D2xxException("D2xx init failed: Can not find UsbManager!");
        }
        this.mFtdiDevices = new ArrayList<>();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
        intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
        context.getApplicationContext().registerReceiver(this.mUsbPlugEvents, intentFilter);
        Log.v(TAG, "End constructor");
    }

    public static synchronized D2xxManager getInstance(Context context) throws D2xxException {
        if (mInstance == null) {
            mInstance = new D2xxManager(context);
        }
        if (context != null) {
            updateContext(context);
        }
        return mInstance;
    }

    private static boolean findUsbManger() {
        Context context;
        if (mUsbManager == null && (context = mContext) != null) {
            mUsbManager = (UsbManager) context.getApplicationContext().getSystemService("usb");
        }
        return mUsbManager != null;
    }

    public boolean setVIDPID(int i, int i2) {
        if (i != 0 && i2 != 0) {
            FtVidPid ftVidPid = new FtVidPid(i, i2);
            if (mSupportedDevices.contains(ftVidPid)) {
                Log.i(TAG, "Existing vid:" + i + "  pid:" + i2);
                return true;
            }
            if (mSupportedDevices.add(ftVidPid)) {
                return true;
            }
            Log.d(TAG, "Failed to add VID/PID combination to list.");
        } else {
            Log.d(TAG, "Invalid parameter to setVIDPID");
        }
        return false;
    }

    public int[][] getVIDPID() {
        int size = mSupportedDevices.size();
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) int.class, 2, size);
        for (int i = 0; i < size; i++) {
            FtVidPid ftVidPid = mSupportedDevices.get(i);
            iArr[0][i] = ftVidPid.getVid();
            iArr[1][i] = ftVidPid.getPid();
        }
        return iArr;
    }

    private void clearDevices() {
        synchronized (this.mFtdiDevices) {
            int size = this.mFtdiDevices.size();
            for (int i = 0; i < size; i++) {
                this.mFtdiDevices.remove(0);
            }
        }
    }

    public int createDeviceInfoList(Context context) {
        int size;
        ArrayList<FT_Device> arrayList = new ArrayList<>();
        if (context == null) {
            return 0;
        }
        updateContext(context);
        for (UsbDevice usbDevice : mUsbManager.getDeviceList().values()) {
            if (isFtDevice(usbDevice)) {
                int interfaceCount = usbDevice.getInterfaceCount();
                for (int i = 0; i < interfaceCount; i++) {
                    if (isPermitted(usbDevice)) {
                        synchronized (this.mFtdiDevices) {
                            FT_Device fT_DeviceFindDevice = findDevice(usbDevice);
                            if (fT_DeviceFindDevice == null) {
                                fT_DeviceFindDevice = new FT_Device(context, mUsbManager, usbDevice, usbDevice.getInterface(i));
                            } else {
                                this.mFtdiDevices.remove(fT_DeviceFindDevice);
                                fT_DeviceFindDevice.setContext(context);
                            }
                            arrayList.add(fT_DeviceFindDevice);
                        }
                    }
                }
            }
        }
        synchronized (this.mFtdiDevices) {
            clearDevices();
            this.mFtdiDevices = arrayList;
            size = arrayList.size();
        }
        return size;
    }

    public synchronized int getDeviceInfoList(int i, FtDeviceInfoListNode[] ftDeviceInfoListNodeArr) {
        for (int i2 = 0; i2 < i; i2++) {
            ftDeviceInfoListNodeArr[i2] = this.mFtdiDevices.get(i2).mDeviceInfoNode;
        }
        return this.mFtdiDevices.size();
    }

    public synchronized FtDeviceInfoListNode getDeviceInfoListDetail(int i) {
        if (i <= this.mFtdiDevices.size() && i >= 0) {
            return this.mFtdiDevices.get(i).mDeviceInfoNode;
        }
        return null;
    }

    private boolean tryOpen(Context context, FT_Device fT_Device, DriverParameters driverParameters) {
        if (fT_Device == null || context == null) {
            return false;
        }
        fT_Device.setContext(context);
        if (driverParameters != null) {
            fT_Device.setDriverParameters(driverParameters);
        }
        return fT_Device.openDevice(mUsbManager) && fT_Device.isOpen();
    }

    public synchronized FT_Device openByUsbDevice(Context context, UsbDevice usbDevice, DriverParameters driverParameters) {
        FT_Device fT_Device;
        fT_Device = null;
        if (isFtDevice(usbDevice)) {
            FT_Device fT_DeviceFindDevice = findDevice(usbDevice);
            if (tryOpen(context, fT_DeviceFindDevice, driverParameters)) {
                fT_Device = fT_DeviceFindDevice;
            }
        }
        return fT_Device;
    }

    public synchronized FT_Device openByUsbDevice(Context context, UsbDevice usbDevice) {
        return openByUsbDevice(context, usbDevice, null);
    }

    public synchronized FT_Device openByIndex(Context context, int i, DriverParameters driverParameters) {
        if (i < 0) {
            return null;
        }
        if (context == null) {
            return null;
        }
        updateContext(context);
        FT_Device fT_Device = this.mFtdiDevices.get(i);
        return tryOpen(context, fT_Device, driverParameters) ? fT_Device : null;
    }

    public synchronized FT_Device openByIndex(Context context, int i) {
        return openByIndex(context, i, null);
    }

    public synchronized FT_Device openBySerialNumber(Context context, String str, DriverParameters driverParameters) {
        FT_Device fT_Device;
        if (context == null) {
            return null;
        }
        updateContext(context);
        int i = 0;
        while (true) {
            if (i >= this.mFtdiDevices.size()) {
                fT_Device = null;
                break;
            }
            fT_Device = this.mFtdiDevices.get(i);
            if (fT_Device != null) {
                FtDeviceInfoListNode ftDeviceInfoListNode = fT_Device.mDeviceInfoNode;
                if (ftDeviceInfoListNode == null) {
                    Log.d(TAG, "***devInfo cannot be null***");
                } else if (ftDeviceInfoListNode.serialNumber.equals(str)) {
                    break;
                }
            }
            i++;
        }
        return tryOpen(context, fT_Device, driverParameters) ? fT_Device : null;
    }

    public synchronized FT_Device openBySerialNumber(Context context, String str) {
        return openBySerialNumber(context, str, null);
    }

    public synchronized FT_Device openByDescription(Context context, String str, DriverParameters driverParameters) {
        FT_Device fT_Device;
        if (context == null) {
            return null;
        }
        updateContext(context);
        int i = 0;
        while (true) {
            if (i >= this.mFtdiDevices.size()) {
                fT_Device = null;
                break;
            }
            fT_Device = this.mFtdiDevices.get(i);
            if (fT_Device != null) {
                FtDeviceInfoListNode ftDeviceInfoListNode = fT_Device.mDeviceInfoNode;
                if (ftDeviceInfoListNode == null) {
                    Log.d(TAG, "***devInfo cannot be null***");
                } else if (ftDeviceInfoListNode.description.equals(str)) {
                    break;
                }
            }
            i++;
        }
        return tryOpen(context, fT_Device, driverParameters) ? fT_Device : null;
    }

    public synchronized FT_Device openByDescription(Context context, String str) {
        return openByDescription(context, str, null);
    }

    public synchronized FT_Device openByLocation(Context context, int i, DriverParameters driverParameters) {
        FT_Device fT_Device;
        if (context == null) {
            return null;
        }
        updateContext(context);
        int i2 = 0;
        while (true) {
            if (i2 >= this.mFtdiDevices.size()) {
                fT_Device = null;
                break;
            }
            fT_Device = this.mFtdiDevices.get(i2);
            if (fT_Device != null) {
                FtDeviceInfoListNode ftDeviceInfoListNode = fT_Device.mDeviceInfoNode;
                if (ftDeviceInfoListNode == null) {
                    Log.d(TAG, "***devInfo cannot be null***");
                } else if (ftDeviceInfoListNode.location == i) {
                    break;
                }
            }
            i2++;
        }
        return tryOpen(context, fT_Device, driverParameters) ? fT_Device : null;
    }

    public synchronized FT_Device openByLocation(Context context, int i) {
        return openByLocation(context, i, null);
    }

    public int addUsbDevice(UsbDevice usbDevice) {
        if (!isFtDevice(usbDevice)) {
            return 0;
        }
        int interfaceCount = usbDevice.getInterfaceCount();
        int i = 0;
        for (int i2 = 0; i2 < interfaceCount; i2++) {
            if (checkAskPermitted(usbDevice)) {
                synchronized (this.mFtdiDevices) {
                    FT_Device fT_DeviceFindDevice = findDevice(usbDevice);
                    if (fT_DeviceFindDevice == null) {
                        fT_DeviceFindDevice = new FT_Device(mContext, mUsbManager, usbDevice, usbDevice.getInterface(i2));
                    } else {
                        fT_DeviceFindDevice.setContext(mContext);
                        this.mFtdiDevices.remove(fT_DeviceFindDevice);
                    }
                    this.mFtdiDevices.add(fT_DeviceFindDevice);
                    i++;
                }
            }
        }
        return i;
    }

    public boolean getRequestPermissionSetting() {
        return bRequestPermission;
    }

    public void setRequestPermission(boolean z) {
        bRequestPermission = z;
    }

    public boolean getUsbRegisterBroadcastSetting() {
        return bRegisterBroadcast;
    }

    public void setUsbRegisterBroadcast(boolean z) {
        if (z && !bRegisterBroadcast) {
            bRegisterBroadcast = true;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
            intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
            mContext.getApplicationContext().registerReceiver(this.mUsbPlugEvents, intentFilter);
            return;
        }
        if (z || !bRegisterBroadcast) {
            return;
        }
        bRegisterBroadcast = false;
        mContext.getApplicationContext().unregisterReceiver(this.mUsbPlugEvents);
    }
}
