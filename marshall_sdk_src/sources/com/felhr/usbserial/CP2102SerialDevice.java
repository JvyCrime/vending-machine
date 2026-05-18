package com.felhr.usbserial;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbRequest;
import android.util.Log;
import com.felhr.usbserial.UsbSerialInterface;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public class CP2102SerialDevice extends UsbSerialDevice {
    private static final String CLASS_ID = "CP2102SerialDevice";
    private static final int CP210x_GET_COMM_STATUS = 16;
    private static final int CP210x_GET_LINE_CTL = 4;
    private static final int CP210x_GET_MDMSTS = 8;
    private static final int CP210x_IFC_ENABLE = 0;
    private static final int CP210x_LINE_CTL_DEFAULT = 2048;
    private static final int CP210x_MHS_ALL = 17;
    private static final int CP210x_MHS_DEFAULT = 0;
    private static final int CP210x_MHS_DTR = 1;
    private static final int CP210x_MHS_DTR_OFF = 256;
    private static final int CP210x_MHS_DTR_ON = 257;
    private static final int CP210x_MHS_RTS = 16;
    private static final int CP210x_MHS_RTS_OFF = 512;
    private static final int CP210x_MHS_RTS_ON = 514;
    private static final int CP210x_REQTYPE_DEVICE2HOST = 193;
    private static final int CP210x_REQTYPE_HOST2DEVICE = 65;
    private static final int CP210x_SET_BAUDDIV = 1;
    private static final int CP210x_SET_BAUDRATE = 30;
    private static final int CP210x_SET_CHARS = 25;
    private static final int CP210x_SET_FLOW = 19;
    private static final int CP210x_SET_LINE_CTL = 3;
    private static final int CP210x_SET_MHS = 7;
    private static final int CP210x_SET_XOFF = 10;
    private static final int CP210x_SET_XON = 9;
    private static final int CP210x_UART_DISABLE = 0;
    private static final int CP210x_UART_ENABLE = 1;
    private static final int CP210x_XOFF = 0;
    private static final int CP210x_XON = 0;
    private static final int DEFAULT_BAUDRATE = 9600;
    private UsbSerialInterface.UsbBreakCallback breakCallback;
    private UsbSerialInterface.UsbCTSCallback ctsCallback;
    private boolean ctsState;
    private UsbSerialInterface.UsbDSRCallback dsrCallback;
    private boolean dsrState;
    private boolean dtrDsrEnabled;
    private FlowControlThread flowControlThread;
    private UsbSerialInterface.UsbFrameCallback frameCallback;
    private UsbEndpoint inEndpoint;
    private UsbInterface mInterface;
    private UsbEndpoint outEndpoint;
    private UsbSerialInterface.UsbOverrunCallback overrunCallback;
    private UsbSerialInterface.UsbParityCallback parityCallback;
    private UsbRequest requestIN;
    private boolean rtsCtsEnabled;

    public CP2102SerialDevice(UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection) {
        this(usbDevice, usbDeviceConnection, -1);
    }

    public CP2102SerialDevice(UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection, int i) {
        super(usbDevice, usbDeviceConnection);
        this.rtsCtsEnabled = false;
        this.dtrDsrEnabled = false;
        this.ctsState = true;
        this.dsrState = true;
        this.mInterface = usbDevice.getInterface(i < 0 ? 0 : i);
    }

    @Override // com.felhr.usbserial.UsbSerialDevice, com.felhr.usbserial.UsbSerialInterface
    public boolean open() {
        if (!openCP2102()) {
            return false;
        }
        UsbRequest usbRequest = new UsbRequest();
        this.requestIN = usbRequest;
        usbRequest.initialize(this.connection, this.inEndpoint);
        restartWorkingThread();
        restartWriteThread();
        createFlowControlThread();
        setThreadsParams(this.requestIN, this.outEndpoint);
        this.asyncMode = true;
        return true;
    }

    @Override // com.felhr.usbserial.UsbSerialDevice, com.felhr.usbserial.UsbSerialInterface
    public void close() {
        setControlCommand(0, 0, null);
        killWorkingThread();
        killWriteThread();
        stopFlowControlThread();
        this.connection.releaseInterface(this.mInterface);
    }

    @Override // com.felhr.usbserial.UsbSerialDevice, com.felhr.usbserial.UsbSerialInterface
    public boolean syncOpen() {
        if (!openCP2102()) {
            return false;
        }
        createFlowControlThread();
        setSyncParams(this.inEndpoint, this.outEndpoint);
        this.asyncMode = false;
        return true;
    }

    @Override // com.felhr.usbserial.UsbSerialDevice, com.felhr.usbserial.UsbSerialInterface
    public void syncClose() {
        setControlCommand(0, 0, null);
        stopFlowControlThread();
        this.connection.releaseInterface(this.mInterface);
    }

    @Override // com.felhr.usbserial.UsbSerialDevice, com.felhr.usbserial.UsbSerialInterface
    public void setBaudRate(int i) {
        setControlCommand(30, 0, new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) ((i >> 24) & 255)});
    }

    @Override // com.felhr.usbserial.UsbSerialDevice, com.felhr.usbserial.UsbSerialInterface
    public void setDataBits(int i) {
        byte[] ctl = getCTL();
        if (i == 5) {
            ctl[1] = 5;
        } else if (i == 6) {
            ctl[1] = 6;
        } else if (i == 7) {
            ctl[1] = 7;
        } else if (i != 8) {
            return;
        } else {
            ctl[1] = 8;
        }
        setControlCommand(3, (byte) ((ctl[1] << 8) | (ctl[0] & 255)), null);
    }

    @Override // com.felhr.usbserial.UsbSerialDevice, com.felhr.usbserial.UsbSerialInterface
    public void setStopBits(int i) {
        byte[] ctl = getCTL();
        if (i == 1) {
            ctl[0] = (byte) (ctl[0] & (-2));
            ctl[0] = (byte) (ctl[0] & (-3));
        } else if (i == 2) {
            ctl[0] = (byte) (ctl[0] & (-2));
            ctl[0] = (byte) (ctl[0] | 2);
        } else {
            if (i != 3) {
                return;
            }
            ctl[0] = (byte) (ctl[0] | 1);
            ctl[0] = (byte) (ctl[0] & (-3));
        }
        setControlCommand(3, (byte) ((ctl[1] << 8) | (ctl[0] & 255)), null);
    }

    @Override // com.felhr.usbserial.UsbSerialDevice, com.felhr.usbserial.UsbSerialInterface
    public void setParity(int i) {
        byte[] ctl = getCTL();
        if (i == 0) {
            ctl[0] = (byte) (ctl[0] & (-17));
            ctl[0] = (byte) (ctl[0] & (-33));
            ctl[0] = (byte) (ctl[0] & (-65));
            ctl[0] = (byte) (ctl[0] & ByteCompanionObject.MAX_VALUE);
        } else if (i == 1) {
            ctl[0] = (byte) (ctl[0] | 16);
            ctl[0] = (byte) (ctl[0] & (-33));
            ctl[0] = (byte) (ctl[0] & (-65));
            ctl[0] = (byte) (ctl[0] & ByteCompanionObject.MAX_VALUE);
        } else if (i == 2) {
            ctl[0] = (byte) (ctl[0] & (-17));
            ctl[0] = (byte) (ctl[0] | 32);
            ctl[0] = (byte) (ctl[0] & (-65));
            ctl[0] = (byte) (ctl[0] & ByteCompanionObject.MAX_VALUE);
        } else if (i == 3) {
            ctl[0] = (byte) (ctl[0] | 16);
            ctl[0] = (byte) (ctl[0] | 32);
            ctl[0] = (byte) (ctl[0] & (-65));
            ctl[0] = (byte) (ctl[0] & ByteCompanionObject.MAX_VALUE);
        } else {
            if (i != 4) {
                return;
            }
            ctl[0] = (byte) (ctl[0] & (-17));
            ctl[0] = (byte) (ctl[0] & (-33));
            ctl[0] = (byte) (ctl[0] | 64);
            ctl[0] = (byte) (ctl[0] & ByteCompanionObject.MAX_VALUE);
        }
        setControlCommand(3, (byte) ((ctl[1] << 8) | (ctl[0] & 255)), null);
    }

    @Override // com.felhr.usbserial.UsbSerialDevice, com.felhr.usbserial.UsbSerialInterface
    public void setFlowControl(int i) {
        if (i == 0) {
            this.rtsCtsEnabled = false;
            this.dtrDsrEnabled = false;
            setControlCommand(19, 0, new byte[]{1, 0, 0, 0, 64, 0, 0, 0, 0, -128, 0, 0, 0, 32, 0, 0});
            return;
        }
        if (i == 1) {
            this.rtsCtsEnabled = true;
            this.dtrDsrEnabled = false;
            setControlCommand(19, 0, new byte[]{9, 0, 0, 0, 64, 0, 0, 0, 0, -128, 0, 0, 0, 32, 0, 0});
            setControlCommand(7, CP210x_MHS_RTS_ON, null);
            this.ctsState = (getCommStatus()[4] & 1) == 0;
            startFlowControlThread();
            return;
        }
        if (i != 2) {
            if (i != 3) {
                return;
            }
            setControlCommand(25, 0, new byte[]{0, 0, 0, 0, 17, 19});
            setControlCommand(19, 0, new byte[]{1, 0, 0, 0, 67, 0, 0, 0, 0, -128, 0, 0, 0, 32, 0, 0});
            return;
        }
        this.dtrDsrEnabled = true;
        this.rtsCtsEnabled = false;
        setControlCommand(19, 0, new byte[]{17, 0, 0, 0, 64, 0, 0, 0, 0, -128, 0, 0, 0, 32, 0, 0});
        setControlCommand(7, 257, null);
        this.dsrState = (getCommStatus()[4] & 2) == 0;
        startFlowControlThread();
    }

    @Override // com.felhr.usbserial.UsbSerialInterface
    public void setRTS(boolean z) {
        if (z) {
            setControlCommand(7, CP210x_MHS_RTS_ON, null);
        } else {
            setControlCommand(7, 512, null);
        }
    }

    @Override // com.felhr.usbserial.UsbSerialInterface
    public void setDTR(boolean z) {
        if (z) {
            setControlCommand(7, 257, null);
        } else {
            setControlCommand(7, 256, null);
        }
    }

    @Override // com.felhr.usbserial.UsbSerialInterface
    public void getCTS(UsbSerialInterface.UsbCTSCallback usbCTSCallback) {
        this.ctsCallback = usbCTSCallback;
    }

    @Override // com.felhr.usbserial.UsbSerialInterface
    public void getDSR(UsbSerialInterface.UsbDSRCallback usbDSRCallback) {
        this.dsrCallback = usbDSRCallback;
    }

    @Override // com.felhr.usbserial.UsbSerialInterface
    public void getBreak(UsbSerialInterface.UsbBreakCallback usbBreakCallback) {
        this.breakCallback = usbBreakCallback;
    }

    @Override // com.felhr.usbserial.UsbSerialInterface
    public void getFrame(UsbSerialInterface.UsbFrameCallback usbFrameCallback) {
        this.frameCallback = usbFrameCallback;
    }

    @Override // com.felhr.usbserial.UsbSerialInterface
    public void getOverrun(UsbSerialInterface.UsbOverrunCallback usbOverrunCallback) {
        this.overrunCallback = usbOverrunCallback;
    }

    @Override // com.felhr.usbserial.UsbSerialInterface
    public void getParity(UsbSerialInterface.UsbParityCallback usbParityCallback) {
        this.parityCallback = usbParityCallback;
        startFlowControlThread();
    }

    private class FlowControlThread extends Thread {
        private long time = 40;
        private AtomicBoolean keep = new AtomicBoolean(true);
        private boolean firstTime = true;

        public FlowControlThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (this.keep.get()) {
                if (this.firstTime) {
                    if (CP2102SerialDevice.this.rtsCtsEnabled && CP2102SerialDevice.this.ctsCallback != null) {
                        CP2102SerialDevice.this.ctsCallback.onCTSChanged(CP2102SerialDevice.this.ctsState);
                    }
                    if (CP2102SerialDevice.this.dtrDsrEnabled && CP2102SerialDevice.this.dsrCallback != null) {
                        CP2102SerialDevice.this.dsrCallback.onDSRChanged(CP2102SerialDevice.this.dsrState);
                    }
                    this.firstTime = false;
                } else {
                    byte[] bArrPollLines = pollLines();
                    byte[] commStatus = CP2102SerialDevice.this.getCommStatus();
                    if (CP2102SerialDevice.this.rtsCtsEnabled) {
                        if (CP2102SerialDevice.this.ctsState != ((bArrPollLines[0] & 16) == 16)) {
                            CP2102SerialDevice.this.ctsState = !r3.ctsState;
                            if (CP2102SerialDevice.this.ctsCallback != null) {
                                CP2102SerialDevice.this.ctsCallback.onCTSChanged(CP2102SerialDevice.this.ctsState);
                            }
                        }
                    }
                    if (CP2102SerialDevice.this.dtrDsrEnabled) {
                        if (CP2102SerialDevice.this.dsrState != ((bArrPollLines[0] & 32) == 32)) {
                            CP2102SerialDevice.this.dsrState = !r0.dsrState;
                            if (CP2102SerialDevice.this.dsrCallback != null) {
                                CP2102SerialDevice.this.dsrCallback.onDSRChanged(CP2102SerialDevice.this.dsrState);
                            }
                        }
                    }
                    if (CP2102SerialDevice.this.parityCallback != null && (commStatus[0] & 16) == 16) {
                        CP2102SerialDevice.this.parityCallback.onParityError();
                    }
                    if (CP2102SerialDevice.this.frameCallback != null && (commStatus[0] & 2) == 2) {
                        CP2102SerialDevice.this.frameCallback.onFramingError();
                    }
                    if (CP2102SerialDevice.this.breakCallback != null && (commStatus[0] & 1) == 1) {
                        CP2102SerialDevice.this.breakCallback.onBreakInterrupt();
                    }
                    if (CP2102SerialDevice.this.overrunCallback != null && ((commStatus[0] & 4) == 4 || (commStatus[0] & 8) == 8)) {
                        CP2102SerialDevice.this.overrunCallback.onOverrunError();
                    }
                }
            }
        }

        public void stopThread() {
            this.keep.set(false);
        }

        private byte[] pollLines() {
            synchronized (this) {
                try {
                    wait(this.time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return CP2102SerialDevice.this.getModemState();
        }
    }

    private boolean openCP2102() {
        if (this.connection.claimInterface(this.mInterface, true)) {
            Log.i(CLASS_ID, "Interface succesfully claimed");
            int endpointCount = this.mInterface.getEndpointCount();
            for (int i = 0; i <= endpointCount - 1; i++) {
                UsbEndpoint endpoint = this.mInterface.getEndpoint(i);
                if (endpoint.getType() == 2 && endpoint.getDirection() == 128) {
                    this.inEndpoint = endpoint;
                } else {
                    this.outEndpoint = endpoint;
                }
            }
            if (setControlCommand(0, 1, null) < 0) {
                return false;
            }
            setBaudRate(DEFAULT_BAUDRATE);
            if (setControlCommand(3, 2048, null) < 0) {
                return false;
            }
            setFlowControl(0);
            return setControlCommand(7, 0, null) >= 0;
        }
        Log.i(CLASS_ID, "Interface could not be claimed");
        return false;
    }

    private void createFlowControlThread() {
        this.flowControlThread = new FlowControlThread();
    }

    private void startFlowControlThread() {
        if (this.flowControlThread.isAlive()) {
            return;
        }
        this.flowControlThread.start();
    }

    private void stopFlowControlThread() {
        FlowControlThread flowControlThread = this.flowControlThread;
        if (flowControlThread != null) {
            flowControlThread.stopThread();
            this.flowControlThread = null;
        }
    }

    private int setControlCommand(int i, int i2, byte[] bArr) {
        int iControlTransfer = this.connection.controlTransfer(65, i, i2, this.mInterface.getId(), bArr, bArr != null ? bArr.length : 0, 5000);
        Log.i(CLASS_ID, "Control Transfer Response: " + String.valueOf(iControlTransfer));
        return iControlTransfer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] getModemState() {
        byte[] bArr = new byte[1];
        this.connection.controlTransfer(193, 8, 0, this.mInterface.getId(), bArr, 1, 5000);
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] getCommStatus() {
        byte[] bArr = new byte[19];
        int iControlTransfer = this.connection.controlTransfer(193, 16, 0, this.mInterface.getId(), bArr, 19, 5000);
        Log.i(CLASS_ID, "Control Transfer Response (Comm status): " + String.valueOf(iControlTransfer));
        return bArr;
    }

    private byte[] getCTL() {
        byte[] bArr = new byte[2];
        int iControlTransfer = this.connection.controlTransfer(193, 4, 0, this.mInterface.getId(), bArr, 2, 5000);
        Log.i(CLASS_ID, "Control Transfer Response: " + String.valueOf(iControlTransfer));
        return bArr;
    }
}
