package com.ftdi.j2xx;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbRequest;
import android.util.Log;
import androidx.core.view.MotionEventCompat;
import com.ftdi.j2xx.D2xxManager;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: loaded from: classes.dex */
public class FT_Device {
    private static final String TAG = "FTDI_Device::";
    private BulkInWorker mBulkIn;
    UsbEndpoint mBulkInEndpoint;
    private Thread mBulkInThread;
    UsbEndpoint mBulkOutEndpoint;
    Context mContext;
    D2xxManager.FtDeviceInfoListNode mDeviceInfoNode;
    private D2xxManager.DriverParameters mDriverParams = new D2xxManager.DriverParameters();
    private FT_EE_Ctrl mEEPROM;
    long mEventMask;
    TFtEventNotify mEventNotification;
    private int mInterfaceID;
    Boolean mIsOpen;
    private byte mLatencyTimer;
    private int mMaxPacketSize;
    private ProcessInCtrl mProcessInCtrl;
    private Thread mProcessRequestThread;
    TFtSpecialChars mTftSpecialChars;
    private UsbDeviceConnection mUsbConnection;
    UsbDevice mUsbDevice;
    UsbInterface mUsbInterface;
    private UsbRequest mUsbRequest;

    public FT_Device(Context context, UsbManager usbManager, UsbDevice usbDevice, UsbInterface usbInterface) {
        this.mInterfaceID = 0;
        byte[] bArr = new byte[255];
        this.mContext = context;
        try {
            this.mUsbDevice = usbDevice;
            this.mUsbInterface = usbInterface;
            this.mBulkOutEndpoint = null;
            this.mBulkInEndpoint = null;
            this.mMaxPacketSize = 0;
            this.mTftSpecialChars = new TFtSpecialChars();
            this.mEventNotification = new TFtEventNotify();
            this.mDeviceInfoNode = new D2xxManager.FtDeviceInfoListNode();
            this.mUsbRequest = new UsbRequest();
            setConnection(usbManager.openDevice(this.mUsbDevice));
            if (getConnection() == null) {
                Log.e(TAG, "Failed to open the device!");
                throw new D2xxManager.D2xxException("Failed to open the device!");
            }
            getConnection().claimInterface(this.mUsbInterface, false);
            byte[] rawDescriptors = getConnection().getRawDescriptors();
            int deviceId = this.mUsbDevice.getDeviceId();
            int id = this.mUsbInterface.getId() + 1;
            this.mInterfaceID = id;
            this.mDeviceInfoNode.location = (deviceId << 4) | (id & 15);
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(2);
            byteBufferAllocate.order(ByteOrder.LITTLE_ENDIAN);
            byteBufferAllocate.put(rawDescriptors[12]);
            byteBufferAllocate.put(rawDescriptors[13]);
            this.mDeviceInfoNode.bcdDevice = byteBufferAllocate.getShort(0);
            this.mDeviceInfoNode.iSerialNumber = rawDescriptors[16];
            this.mDeviceInfoNode.serialNumber = getConnection().getSerial();
            this.mDeviceInfoNode.id = (this.mUsbDevice.getVendorId() << 16) | this.mUsbDevice.getProductId();
            this.mDeviceInfoNode.breakOnParam = 8;
            getConnection().controlTransfer(-128, 6, rawDescriptors[15] | 768, 0, bArr, 255, 0);
            this.mDeviceInfoNode.description = stringFromUtf16le(bArr);
            switch (this.mDeviceInfoNode.bcdDevice & 65280) {
                case 512:
                    if (this.mDeviceInfoNode.iSerialNumber == 0) {
                        this.mEEPROM = new FT_EE_232B_Ctrl(this);
                        this.mDeviceInfoNode.type = 0;
                    } else {
                        this.mDeviceInfoNode.type = 1;
                        this.mEEPROM = new FT_EE_232A_Ctrl(this);
                    }
                    break;
                case 1024:
                    this.mEEPROM = new FT_EE_232B_Ctrl(this);
                    this.mDeviceInfoNode.type = 0;
                    break;
                case 1280:
                    this.mEEPROM = new FT_EE_2232_Ctrl(this);
                    this.mDeviceInfoNode.type = 4;
                    dualQuadChannelDevice();
                    break;
                case 1536:
                    FT_EE_Ctrl fT_EE_Ctrl = new FT_EE_Ctrl(this);
                    this.mEEPROM = fT_EE_Ctrl;
                    short word = (short) (fT_EE_Ctrl.readWord((short) 0) & 1);
                    this.mEEPROM = null;
                    if (word == 0) {
                        this.mDeviceInfoNode.type = 5;
                        this.mEEPROM = new FT_EE_232R_Ctrl(this);
                    } else {
                        this.mDeviceInfoNode.type = 5;
                        this.mEEPROM = new FT_EE_245R_Ctrl(this);
                    }
                    break;
                case 1792:
                    this.mDeviceInfoNode.type = 6;
                    this.mDeviceInfoNode.flags = 2;
                    dualQuadChannelDevice();
                    this.mEEPROM = new FT_EE_2232H_Ctrl(this);
                    break;
                case 2048:
                    this.mDeviceInfoNode.type = 7;
                    this.mDeviceInfoNode.flags = 2;
                    dualQuadChannelDevice();
                    this.mEEPROM = new FT_EE_4232H_Ctrl(this);
                    break;
                case 2304:
                    this.mDeviceInfoNode.type = 8;
                    this.mDeviceInfoNode.flags = 2;
                    this.mEEPROM = new FT_EE_232H_Ctrl(this);
                    break;
                case 4096:
                    this.mDeviceInfoNode.type = 9;
                    this.mEEPROM = new FT_EE_X_Ctrl(this);
                    break;
                case 5888:
                    this.mDeviceInfoNode.type = 12;
                    this.mDeviceInfoNode.flags = 2;
                    break;
                case 6144:
                    this.mDeviceInfoNode.type = 10;
                    if (this.mInterfaceID == 1) {
                        this.mDeviceInfoNode.flags = 2;
                    } else {
                        this.mDeviceInfoNode.flags = 0;
                    }
                    break;
                case 6400:
                    this.mDeviceInfoNode.type = 11;
                    int i = this.mInterfaceID;
                    if (i == 4) {
                        int maxPacketSize = this.mUsbDevice.getInterface(i - 1).getEndpoint(0).getMaxPacketSize();
                        Log.e("dev", "mInterfaceID : " + this.mInterfaceID + "   iMaxPacketSize : " + maxPacketSize);
                        if (maxPacketSize == 8) {
                            this.mDeviceInfoNode.flags = 0;
                        } else {
                            this.mDeviceInfoNode.flags = 2;
                        }
                    } else {
                        this.mDeviceInfoNode.flags = 2;
                    }
                    break;
                default:
                    this.mDeviceInfoNode.type = 3;
                    this.mEEPROM = new FT_EE_Ctrl(this);
                    break;
            }
            int i2 = this.mDeviceInfoNode.bcdDevice & 65280;
            if ((i2 == 5888 || i2 == 6144 || i2 == 6400) && this.mDeviceInfoNode.serialNumber == null) {
                byte[] bArr2 = new byte[16];
                getConnection().controlTransfer(-64, 144, 0, 27, bArr2, 16, 0);
                String str = "";
                for (int i3 = 0; i3 < 8; i3++) {
                    str = String.valueOf(str) + ((char) bArr2[i3 * 2]);
                }
                this.mDeviceInfoNode.serialNumber = new String(str);
            }
            int i4 = this.mDeviceInfoNode.bcdDevice & 65280;
            if (i4 == 6144 || i4 == 6400) {
                int i5 = this.mInterfaceID;
                if (i5 == 1) {
                    D2xxManager.FtDeviceInfoListNode ftDeviceInfoListNode = this.mDeviceInfoNode;
                    ftDeviceInfoListNode.description = String.valueOf(ftDeviceInfoListNode.description) + " A";
                    D2xxManager.FtDeviceInfoListNode ftDeviceInfoListNode2 = this.mDeviceInfoNode;
                    ftDeviceInfoListNode2.serialNumber = String.valueOf(ftDeviceInfoListNode2.serialNumber) + "A";
                } else if (i5 == 2) {
                    D2xxManager.FtDeviceInfoListNode ftDeviceInfoListNode3 = this.mDeviceInfoNode;
                    ftDeviceInfoListNode3.description = String.valueOf(ftDeviceInfoListNode3.description) + " B";
                    D2xxManager.FtDeviceInfoListNode ftDeviceInfoListNode4 = this.mDeviceInfoNode;
                    ftDeviceInfoListNode4.serialNumber = String.valueOf(ftDeviceInfoListNode4.serialNumber) + "B";
                } else if (i5 == 3) {
                    D2xxManager.FtDeviceInfoListNode ftDeviceInfoListNode5 = this.mDeviceInfoNode;
                    ftDeviceInfoListNode5.description = String.valueOf(ftDeviceInfoListNode5.description) + " C";
                    D2xxManager.FtDeviceInfoListNode ftDeviceInfoListNode6 = this.mDeviceInfoNode;
                    ftDeviceInfoListNode6.serialNumber = String.valueOf(ftDeviceInfoListNode6.serialNumber) + "C";
                } else if (i5 == 4) {
                    D2xxManager.FtDeviceInfoListNode ftDeviceInfoListNode7 = this.mDeviceInfoNode;
                    ftDeviceInfoListNode7.description = String.valueOf(ftDeviceInfoListNode7.description) + " D";
                    D2xxManager.FtDeviceInfoListNode ftDeviceInfoListNode8 = this.mDeviceInfoNode;
                    ftDeviceInfoListNode8.serialNumber = String.valueOf(ftDeviceInfoListNode8.serialNumber) + "D";
                }
            }
            getConnection().releaseInterface(this.mUsbInterface);
            getConnection().close();
            setConnection(null);
            setClosed();
        } catch (Exception e) {
            if (e.getMessage() != null) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private final boolean isHiSpeed() {
        return isFt232h() || isFt2232h() || isFt4232h();
    }

    private final boolean isBmDevice() {
        return isFt232b() || isFt2232() || isFt232r() || isFt2232h() || isFt4232h() || isFt232h() || isFt232ex();
    }

    final boolean isMultiIfDevice() {
        return isFt2232() || isFt2232h() || isFt4232h();
    }

    private final boolean isFt232ex() {
        return (this.mDeviceInfoNode.bcdDevice & 65280) == 4096;
    }

    private final boolean isFt232h() {
        return (this.mDeviceInfoNode.bcdDevice & 65280) == 2304;
    }

    final boolean isFt4232h() {
        return (this.mDeviceInfoNode.bcdDevice & 65280) == 2048;
    }

    private final boolean isFt2232h() {
        return (this.mDeviceInfoNode.bcdDevice & 65280) == 1792;
    }

    private final boolean isFt232r() {
        return (this.mDeviceInfoNode.bcdDevice & 65280) == 1536;
    }

    private final boolean isFt2232() {
        return (this.mDeviceInfoNode.bcdDevice & 65280) == 1280;
    }

    private final boolean isFt232b() {
        if ((this.mDeviceInfoNode.bcdDevice & 65280) != 1024) {
            return (this.mDeviceInfoNode.bcdDevice & 65280) == 512 && this.mDeviceInfoNode.iSerialNumber == 0;
        }
        return true;
    }

    private final boolean ifFt8u232am() {
        return (this.mDeviceInfoNode.bcdDevice & 65280) == 512 && this.mDeviceInfoNode.iSerialNumber != 0;
    }

    private final String stringFromUtf16le(byte[] bArr) throws UnsupportedEncodingException {
        return new String(bArr, 2, bArr[0] - 2, "UTF-16LE");
    }

    UsbDeviceConnection getConnection() {
        return this.mUsbConnection;
    }

    void setConnection(UsbDeviceConnection usbDeviceConnection) {
        this.mUsbConnection = usbDeviceConnection;
    }

    synchronized boolean setContext(Context context) {
        boolean z;
        z = false;
        if (context != null) {
            this.mContext = context;
            z = true;
        }
        return z;
    }

    protected void setDriverParameters(D2xxManager.DriverParameters driverParameters) {
        this.mDriverParams.setMaxBufferSize(driverParameters.getMaxBufferSize());
        this.mDriverParams.setMaxTransferSize(driverParameters.getMaxTransferSize());
        this.mDriverParams.setBufferNumber(driverParameters.getBufferNumber());
        this.mDriverParams.setReadTimeout(driverParameters.getReadTimeout());
    }

    D2xxManager.DriverParameters getDriverParameters() {
        return this.mDriverParams;
    }

    public int getReadTimeout() {
        return this.mDriverParams.getReadTimeout();
    }

    private void dualQuadChannelDevice() {
        int i = this.mInterfaceID;
        if (i == 1) {
            D2xxManager.FtDeviceInfoListNode ftDeviceInfoListNode = this.mDeviceInfoNode;
            ftDeviceInfoListNode.serialNumber = String.valueOf(ftDeviceInfoListNode.serialNumber) + "A";
            D2xxManager.FtDeviceInfoListNode ftDeviceInfoListNode2 = this.mDeviceInfoNode;
            ftDeviceInfoListNode2.description = String.valueOf(ftDeviceInfoListNode2.description) + " A";
            return;
        }
        if (i == 2) {
            D2xxManager.FtDeviceInfoListNode ftDeviceInfoListNode3 = this.mDeviceInfoNode;
            ftDeviceInfoListNode3.serialNumber = String.valueOf(ftDeviceInfoListNode3.serialNumber) + "B";
            D2xxManager.FtDeviceInfoListNode ftDeviceInfoListNode4 = this.mDeviceInfoNode;
            ftDeviceInfoListNode4.description = String.valueOf(ftDeviceInfoListNode4.description) + " B";
            return;
        }
        if (i == 3) {
            D2xxManager.FtDeviceInfoListNode ftDeviceInfoListNode5 = this.mDeviceInfoNode;
            ftDeviceInfoListNode5.serialNumber = String.valueOf(ftDeviceInfoListNode5.serialNumber) + "C";
            D2xxManager.FtDeviceInfoListNode ftDeviceInfoListNode6 = this.mDeviceInfoNode;
            ftDeviceInfoListNode6.description = String.valueOf(ftDeviceInfoListNode6.description) + " C";
            return;
        }
        if (i == 4) {
            D2xxManager.FtDeviceInfoListNode ftDeviceInfoListNode7 = this.mDeviceInfoNode;
            ftDeviceInfoListNode7.serialNumber = String.valueOf(ftDeviceInfoListNode7.serialNumber) + "D";
            D2xxManager.FtDeviceInfoListNode ftDeviceInfoListNode8 = this.mDeviceInfoNode;
            ftDeviceInfoListNode8.description = String.valueOf(ftDeviceInfoListNode8.description) + " D";
        }
    }

    synchronized boolean openDevice(UsbManager usbManager) {
        if (isOpen()) {
            return false;
        }
        if (usbManager == null) {
            Log.e(TAG, "UsbManager cannot be null.");
            return false;
        }
        if (getConnection() != null) {
            Log.e(TAG, "There should not have an UsbConnection.");
            return false;
        }
        setConnection(usbManager.openDevice(this.mUsbDevice));
        if (getConnection() == null) {
            Log.e(TAG, "UsbConnection cannot be null.");
            return false;
        }
        if (!getConnection().claimInterface(this.mUsbInterface, true)) {
            Log.e(TAG, "ClaimInteface returned false.");
            return false;
        }
        Log.d(TAG, "open SUCCESS");
        if (!findDeviceEndpoints()) {
            Log.e(TAG, "Failed to find endpoints.");
            return false;
        }
        this.mUsbRequest.initialize(this.mUsbConnection, this.mBulkOutEndpoint);
        Log.d("D2XX::", "**********************Device Opened**********************");
        ProcessInCtrl processInCtrl = new ProcessInCtrl(this);
        this.mProcessInCtrl = processInCtrl;
        this.mBulkIn = new BulkInWorker(this, processInCtrl, getConnection(), this.mBulkInEndpoint);
        Thread thread = new Thread(this.mBulkIn);
        this.mBulkInThread = thread;
        thread.setName("bulkInThread");
        Thread thread2 = new Thread(new ProcessRequestWorker(this.mProcessInCtrl));
        this.mProcessRequestThread = thread2;
        thread2.setName("processRequestThread");
        purgeRxTx(true, true);
        this.mBulkInThread.start();
        this.mProcessRequestThread.start();
        setOpen();
        return true;
    }

    public synchronized boolean isOpen() {
        return this.mIsOpen.booleanValue();
    }

    private synchronized void setOpen() {
        this.mIsOpen = true;
        D2xxManager.FtDeviceInfoListNode ftDeviceInfoListNode = this.mDeviceInfoNode;
        ftDeviceInfoListNode.flags = 1 | ftDeviceInfoListNode.flags;
    }

    private synchronized void setClosed() {
        this.mIsOpen = false;
        this.mDeviceInfoNode.flags &= 2;
    }

    public synchronized void close() {
        Thread thread = this.mProcessRequestThread;
        if (thread != null) {
            thread.interrupt();
        }
        Thread thread2 = this.mBulkInThread;
        if (thread2 != null) {
            thread2.interrupt();
        }
        UsbDeviceConnection usbDeviceConnection = this.mUsbConnection;
        if (usbDeviceConnection != null) {
            usbDeviceConnection.releaseInterface(this.mUsbInterface);
            this.mUsbConnection.close();
            this.mUsbConnection = null;
        }
        ProcessInCtrl processInCtrl = this.mProcessInCtrl;
        if (processInCtrl != null) {
            processInCtrl.close();
        }
        this.mProcessRequestThread = null;
        this.mBulkInThread = null;
        this.mBulkIn = null;
        this.mProcessInCtrl = null;
        setClosed();
    }

    protected UsbDevice getUsbDevice() {
        return this.mUsbDevice;
    }

    public D2xxManager.FtDeviceInfoListNode getDeviceInfo() {
        return this.mDeviceInfoNode;
    }

    public int read(byte[] bArr, int i, long j) {
        if (!isOpen()) {
            return -1;
        }
        if (i <= 0) {
            return -2;
        }
        ProcessInCtrl processInCtrl = this.mProcessInCtrl;
        if (processInCtrl == null) {
            return -3;
        }
        return processInCtrl.readBulkInData(bArr, i, j);
    }

    public int read(byte[] bArr, int i) {
        return read(bArr, i, this.mDriverParams.getReadTimeout());
    }

    public int read(byte[] bArr) {
        return read(bArr, bArr.length, this.mDriverParams.getReadTimeout());
    }

    public int write(byte[] bArr, int i) {
        return write(bArr, i, true);
    }

    public int write(byte[] bArr, int i, boolean z) {
        UsbRequest usbRequestRequestWait;
        if (!isOpen() || i < 0) {
            return -1;
        }
        UsbRequest usbRequest = this.mUsbRequest;
        if (z) {
            usbRequest.setClientData(this);
        }
        if (i != 0 ? !usbRequest.queue(ByteBuffer.wrap(bArr), i) : !usbRequest.queue(ByteBuffer.wrap(new byte[1]), i)) {
            i = -1;
        }
        if (z) {
            do {
                usbRequestRequestWait = this.mUsbConnection.requestWait();
                if (usbRequestRequestWait == null) {
                    Log.e(TAG, "UsbConnection.requestWait() == null");
                    return -99;
                }
            } while (usbRequestRequestWait.getClientData() != this);
        }
        return i;
    }

    public int write(byte[] bArr) {
        return write(bArr, bArr.length, true);
    }

    public short getModemStatus() {
        if (!isOpen()) {
            return (short) -1;
        }
        if (this.mProcessInCtrl == null) {
            return (short) -2;
        }
        this.mEventMask &= -3;
        return (short) (this.mDeviceInfoNode.modemStatus & 255);
    }

    public short getLineStatus() {
        if (!isOpen()) {
            return (short) -1;
        }
        if (this.mProcessInCtrl == null) {
            return (short) -2;
        }
        return this.mDeviceInfoNode.lineStatus;
    }

    public int getQueueStatus() {
        if (!isOpen()) {
            return -1;
        }
        ProcessInCtrl processInCtrl = this.mProcessInCtrl;
        if (processInCtrl == null) {
            return -2;
        }
        return processInCtrl.getBytesAvailable();
    }

    public boolean readBufferFull() {
        return this.mProcessInCtrl.isSinkFull();
    }

    public long getEventStatus() {
        if (!isOpen()) {
            return -1L;
        }
        if (this.mProcessInCtrl == null) {
            return -2L;
        }
        long j = this.mEventMask;
        this.mEventMask = 0L;
        return j;
    }

    public boolean setBaudRate(int i) {
        byte bFT_GetDivisor;
        int[] iArr = new int[2];
        if (!isOpen()) {
            return false;
        }
        switch (i) {
            case 300:
                iArr[0] = 10000;
                bFT_GetDivisor = 1;
                break;
            case 600:
                iArr[0] = 5000;
                bFT_GetDivisor = 1;
                break;
            case 1200:
                iArr[0] = 2500;
                bFT_GetDivisor = 1;
                break;
            case 2400:
                iArr[0] = 1250;
                bFT_GetDivisor = 1;
                break;
            case 4800:
                iArr[0] = 625;
                bFT_GetDivisor = 1;
                break;
            case 9600:
                iArr[0] = 16696;
                bFT_GetDivisor = 1;
                break;
            case 19200:
                iArr[0] = 32924;
                bFT_GetDivisor = 1;
                break;
            case 38400:
                iArr[0] = 49230;
                bFT_GetDivisor = 1;
                break;
            case 57600:
                iArr[0] = 52;
                bFT_GetDivisor = 1;
                break;
            case 115200:
                iArr[0] = 26;
                bFT_GetDivisor = 1;
                break;
            case 230400:
                iArr[0] = 13;
                bFT_GetDivisor = 1;
                break;
            case 460800:
                iArr[0] = 16390;
                bFT_GetDivisor = 1;
                break;
            case 921600:
                iArr[0] = 32771;
                bFT_GetDivisor = 1;
                break;
            default:
                if (isHiSpeed() && i >= 1200) {
                    bFT_GetDivisor = FT_BaudRate.FT_GetDivisorHi(i, iArr);
                } else {
                    bFT_GetDivisor = FT_BaudRate.FT_GetDivisor(i, iArr, isBmDevice());
                }
                break;
        }
        if (isMultiIfDevice() || isFt232h() || isFt232ex()) {
            iArr[1] = iArr[1] << 8;
            iArr[1] = iArr[1] & MotionEventCompat.ACTION_POINTER_INDEX_MASK;
            iArr[1] = iArr[1] | this.mInterfaceID;
        }
        return bFT_GetDivisor == 1 && getConnection().controlTransfer(64, 3, iArr[0], iArr[1], null, 0, 0) == 0;
    }

    public boolean setDataCharacteristics(byte b, byte b2, byte b3) {
        if (!isOpen()) {
            return false;
        }
        short s = (short) (((short) (b | (b3 << 8))) | (b2 << 11));
        this.mDeviceInfoNode.breakOnParam = s;
        return getConnection().controlTransfer(64, 4, s, this.mInterfaceID, null, 0, 0) == 0;
    }

    public boolean setBreakOn() {
        return setBreak(16384);
    }

    public boolean setBreakOff() {
        return setBreak(0);
    }

    private boolean setBreak(int i) {
        return isOpen() && getConnection().controlTransfer(64, 4, this.mDeviceInfoNode.breakOnParam | i, this.mInterfaceID, null, 0, 0) == 0;
    }

    public boolean setFlowControl(short s, byte b, byte b2) {
        short s2;
        if (!isOpen()) {
            return false;
        }
        if (s == 1024) {
            s2 = (short) ((b & 255) | ((short) (b2 << 8)));
        } else {
            s2 = 0;
        }
        if (getConnection().controlTransfer(64, 2, s2, this.mInterfaceID | s, null, 0, 0) != 0) {
            return false;
        }
        if (s == 256) {
            return setRts();
        }
        if (s == 512) {
            return setDtr();
        }
        return true;
    }

    public boolean setRts() {
        return isOpen() && getConnection().controlTransfer(64, 1, 514, this.mInterfaceID, null, 0, 0) == 0;
    }

    public boolean clrRts() {
        return isOpen() && getConnection().controlTransfer(64, 1, 512, this.mInterfaceID, null, 0, 0) == 0;
    }

    public boolean setDtr() {
        return isOpen() && getConnection().controlTransfer(64, 1, 257, this.mInterfaceID, null, 0, 0) == 0;
    }

    public boolean clrDtr() {
        return isOpen() && getConnection().controlTransfer(64, 1, 256, this.mInterfaceID, null, 0, 0) == 0;
    }

    public boolean setChars(byte b, byte b2, byte b3, byte b4) {
        TFtSpecialChars tFtSpecialChars = new TFtSpecialChars();
        tFtSpecialChars.EventChar = b;
        tFtSpecialChars.EventCharEnabled = b2;
        tFtSpecialChars.ErrorChar = b3;
        tFtSpecialChars.ErrorCharEnabled = b4;
        if (!isOpen()) {
            return false;
        }
        int i = b & 255;
        if (b2 != 0) {
            i |= 256;
        }
        if (getConnection().controlTransfer(64, 6, i, this.mInterfaceID, null, 0, 0) != 0) {
            return false;
        }
        int i2 = b3 & 255;
        if (b4 > 0) {
            i2 |= 256;
        }
        if (getConnection().controlTransfer(64, 7, i2, this.mInterfaceID, null, 0, 0) != 0) {
            return false;
        }
        this.mTftSpecialChars = tFtSpecialChars;
        return true;
    }

    public boolean setBitMode(byte b, byte b2) {
        int i = this.mDeviceInfoNode.type;
        if (!isOpen() || i == 1) {
            return false;
        }
        if (i != 0 || b2 == 0) {
            if (i != 4 || b2 == 0) {
                if (i != 5 || b2 == 0) {
                    if (i != 6 || b2 == 0) {
                        if (i != 7 || b2 == 0) {
                            if (i == 8 && b2 != 0 && b2 > 64) {
                                return false;
                            }
                        } else {
                            if ((b2 & 7) == 0) {
                                return false;
                            }
                            if ((b2 == 2) & (this.mUsbInterface.getId() != 0) & (this.mUsbInterface.getId() != 1)) {
                                return false;
                            }
                        }
                    } else {
                        if ((b2 & 95) == 0) {
                            return false;
                        }
                        if (((b2 & 72) > 0) & (this.mUsbInterface.getId() != 0)) {
                            return false;
                        }
                    }
                } else if ((b2 & 37) == 0) {
                    return false;
                }
            } else {
                if ((b2 & 31) == 0) {
                    return false;
                }
                if ((b2 == 2) & (this.mUsbInterface.getId() != 0)) {
                    return false;
                }
            }
        } else if ((b2 & 1) == 0) {
            return false;
        }
        return getConnection().controlTransfer(64, 11, (b2 << 8) | (b & 255), this.mInterfaceID, null, 0, 0) == 0;
    }

    public byte getBitMode() {
        byte[] bArr = new byte[1];
        if (!isOpen()) {
            return (byte) -1;
        }
        if (!isBmDevice()) {
            return (byte) -2;
        }
        if (getConnection().controlTransfer(-64, 12, 0, this.mInterfaceID, bArr, 1, 0) == 1) {
            return bArr[0];
        }
        return (byte) -3;
    }

    public boolean resetDevice() {
        return isOpen() && getConnection().controlTransfer(64, 0, 0, 0, null, 0, 0) == 0;
    }

    public int VendorCmdSet(int i, int i2) {
        if (isOpen()) {
            return getConnection().controlTransfer(64, i, i2, this.mInterfaceID, null, 0, 0);
        }
        return -1;
    }

    public int VendorCmdSet(int i, int i2, byte[] bArr, int i3) {
        if (!isOpen()) {
            Log.e(TAG, "VendorCmdSet: Device not open");
            return -1;
        }
        if (i3 < 0) {
            Log.e(TAG, "VendorCmdSet: Invalid data length");
            return -1;
        }
        if (bArr == null) {
            if (i3 > 0) {
                Log.e(TAG, "VendorCmdSet: buf is null!");
                return -1;
            }
        } else if (bArr.length < i3) {
            Log.e(TAG, "VendorCmdSet: length of buffer is smaller than data length to set");
            return -1;
        }
        return getConnection().controlTransfer(64, i, i2, this.mInterfaceID, bArr, i3, 0);
    }

    public int VendorCmdGet(int i, int i2, byte[] bArr, int i3) {
        if (!isOpen()) {
            Log.e(TAG, "VendorCmdGet: Device not open");
            return -1;
        }
        if (i3 < 0) {
            Log.e(TAG, "VendorCmdGet: Invalid data length");
            return -1;
        }
        if (bArr == null) {
            Log.e(TAG, "VendorCmdGet: buf is null");
            return -1;
        }
        if (bArr.length < i3) {
            Log.e(TAG, "VendorCmdGet: length of buffer is smaller than data length to get");
            return -1;
        }
        return getConnection().controlTransfer(-64, i, i2, this.mInterfaceID, bArr, i3, 0);
    }

    public void stopInTask() {
        try {
            if (this.mBulkIn.paused()) {
                return;
            }
            this.mBulkIn.pause();
        } catch (InterruptedException e) {
            Log.d(TAG, "stopInTask called!");
            e.printStackTrace();
        }
    }

    public void restartInTask() {
        this.mBulkIn.restart();
    }

    public boolean stoppedInTask() {
        return this.mBulkIn.paused();
    }

    public boolean purge(byte b) {
        return purgeRxTx((b & 1) == 1, (b & 2) == 2);
    }

    private boolean purgeRxTx(boolean z, boolean z2) {
        if (!isOpen()) {
            return false;
        }
        if (z) {
            int iControlTransfer = 0;
            for (int i = 0; i < 6; i++) {
                iControlTransfer = getConnection().controlTransfer(64, 0, 1, this.mInterfaceID, null, 0, 0);
            }
            if (iControlTransfer > 0) {
                return false;
            }
            this.mProcessInCtrl.purgeINData();
        }
        return z2 && getConnection().controlTransfer(64, 0, 2, this.mInterfaceID, null, 0, 0) == 0;
    }

    public boolean setLatencyTimer(byte b) {
        int i = b & 255;
        if (!isOpen() || getConnection().controlTransfer(64, 9, i, this.mInterfaceID, null, 0, 0) != 0) {
            return false;
        }
        this.mLatencyTimer = b;
        return true;
    }

    public byte getLatencyTimer() {
        byte[] bArr = new byte[1];
        if (!isOpen()) {
            return (byte) -1;
        }
        if (getConnection().controlTransfer(-64, 10, 0, this.mInterfaceID, bArr, 1, 0) == 1) {
            return bArr[0];
        }
        return (byte) 0;
    }

    public boolean setEventNotification(long j) {
        if (!isOpen() || j == 0) {
            return false;
        }
        this.mEventMask = 0L;
        this.mEventNotification.Mask = j;
        return true;
    }

    private boolean findDeviceEndpoints() {
        for (int i = 0; i < this.mUsbInterface.getEndpointCount(); i++) {
            Log.i(TAG, "EP: " + String.format("0x%02X", Integer.valueOf(this.mUsbInterface.getEndpoint(i).getAddress())));
            if (this.mUsbInterface.getEndpoint(i).getType() == 2) {
                if (this.mUsbInterface.getEndpoint(i).getDirection() == 128) {
                    UsbEndpoint endpoint = this.mUsbInterface.getEndpoint(i);
                    this.mBulkInEndpoint = endpoint;
                    this.mMaxPacketSize = endpoint.getMaxPacketSize();
                } else {
                    this.mBulkOutEndpoint = this.mUsbInterface.getEndpoint(i);
                }
            } else {
                Log.i(TAG, "Not Bulk Endpoint");
            }
        }
        return (this.mBulkOutEndpoint == null || this.mBulkInEndpoint == null) ? false : true;
    }

    public FT_EEPROM eepromRead() {
        if (isOpen()) {
            return this.mEEPROM.readEeprom();
        }
        return null;
    }

    public short eepromWrite(FT_EEPROM ft_eeprom) {
        if (isOpen()) {
            return this.mEEPROM.programEeprom(ft_eeprom);
        }
        return (short) -1;
    }

    public boolean eepromErase() {
        return isOpen() && this.mEEPROM.eraseEeprom() == 0;
    }

    public int eepromWriteUserArea(byte[] bArr) {
        if (isOpen()) {
            return this.mEEPROM.writeUserData(bArr);
        }
        return 0;
    }

    public byte[] eepromReadUserArea(int i) {
        if (isOpen()) {
            return this.mEEPROM.readUserData(i);
        }
        return null;
    }

    public int eepromGetUserAreaSize() {
        if (isOpen()) {
            return this.mEEPROM.getUserSize();
        }
        return -1;
    }

    public int eepromReadWord(short s) {
        if (isOpen()) {
            return this.mEEPROM.readWord(s);
        }
        return -1;
    }

    public boolean eepromWriteWord(short s, short s2) {
        if (isOpen()) {
            return this.mEEPROM.writeWord(s, s2);
        }
        return false;
    }

    int getMaxPacketSize() {
        return this.mMaxPacketSize;
    }
}
