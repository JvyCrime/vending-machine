package com.felhr.usbserial;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbRequest;
import android.os.Build;
import com.felhr.deviceids.CH34xIds;
import com.felhr.deviceids.CP210xIds;
import com.felhr.deviceids.FTDISioIds;
import com.felhr.deviceids.PL2303Ids;
import com.felhr.usbserial.UsbSerialInterface;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public abstract class UsbSerialDevice implements UsbSerialInterface {
    private static final String CLASS_ID = "UsbSerialDevice";
    protected static final int USB_TIMEOUT = 5000;
    private static boolean mr1Version;
    protected final UsbDeviceConnection connection;
    protected final UsbDevice device;
    private UsbEndpoint inEndpoint;
    private UsbEndpoint outEndpoint;
    protected ReadThread readThread;
    protected WorkerThread workerThread;
    protected WriteThread writeThread;
    protected boolean asyncMode = true;
    protected SerialBuffer serialBuffer = new SerialBuffer(mr1Version);

    @Override // com.felhr.usbserial.UsbSerialInterface
    public abstract void close();

    @Override // com.felhr.usbserial.UsbSerialInterface
    public abstract boolean open();

    @Override // com.felhr.usbserial.UsbSerialInterface
    public abstract void setBaudRate(int i);

    @Override // com.felhr.usbserial.UsbSerialInterface
    public abstract void setDataBits(int i);

    @Override // com.felhr.usbserial.UsbSerialInterface
    public abstract void setFlowControl(int i);

    @Override // com.felhr.usbserial.UsbSerialInterface
    public abstract void setParity(int i);

    @Override // com.felhr.usbserial.UsbSerialInterface
    public abstract void setStopBits(int i);

    @Override // com.felhr.usbserial.UsbSerialInterface
    public abstract void syncClose();

    @Override // com.felhr.usbserial.UsbSerialInterface
    public abstract boolean syncOpen();

    static {
        if (Build.VERSION.SDK_INT > 17) {
            mr1Version = true;
        } else {
            mr1Version = false;
        }
    }

    public UsbSerialDevice(UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection) {
        this.device = usbDevice;
        this.connection = usbDeviceConnection;
    }

    public static UsbSerialDevice createUsbSerialDevice(UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection) {
        return createUsbSerialDevice(usbDevice, usbDeviceConnection, -1);
    }

    public static UsbSerialDevice createUsbSerialDevice(UsbDevice usbDevice, UsbDeviceConnection usbDeviceConnection, int i) {
        int vendorId = usbDevice.getVendorId();
        int productId = usbDevice.getProductId();
        if (FTDISioIds.isDeviceSupported(vendorId, productId)) {
            return new FTDISerialDevice(usbDevice, usbDeviceConnection, i);
        }
        if (CP210xIds.isDeviceSupported(vendorId, productId)) {
            return new CP2102SerialDevice(usbDevice, usbDeviceConnection, i);
        }
        if (PL2303Ids.isDeviceSupported(vendorId, productId)) {
            return new PL2303SerialDevice(usbDevice, usbDeviceConnection, i);
        }
        if (CH34xIds.isDeviceSupported(vendorId, productId)) {
            return new CH34xSerialDevice(usbDevice, usbDeviceConnection, i);
        }
        if (isCdcDevice(usbDevice)) {
            return new CDCSerialDevice(usbDevice, usbDeviceConnection, i);
        }
        return null;
    }

    public static boolean isSupported(UsbDevice usbDevice) {
        int vendorId = usbDevice.getVendorId();
        int productId = usbDevice.getProductId();
        return FTDISioIds.isDeviceSupported(vendorId, productId) || CP210xIds.isDeviceSupported(vendorId, productId) || PL2303Ids.isDeviceSupported(vendorId, productId) || CH34xIds.isDeviceSupported(vendorId, productId) || isCdcDevice(usbDevice);
    }

    @Override // com.felhr.usbserial.UsbSerialInterface
    public void write(byte[] bArr) {
        if (this.asyncMode) {
            this.serialBuffer.putWriteBuffer(bArr);
        }
    }

    @Override // com.felhr.usbserial.UsbSerialInterface
    public int read(UsbSerialInterface.UsbReadCallback usbReadCallback) {
        if (!this.asyncMode) {
            return -1;
        }
        if (mr1Version) {
            WorkerThread workerThread = this.workerThread;
            if (workerThread == null) {
                return 0;
            }
            workerThread.setCallback(usbReadCallback);
            this.workerThread.getUsbRequest().queue(this.serialBuffer.getReadBuffer(), 16384);
            return 0;
        }
        this.readThread.setCallback(usbReadCallback);
        return 0;
    }

    @Override // com.felhr.usbserial.UsbSerialInterface
    public int syncWrite(byte[] bArr, int i) {
        if (this.asyncMode) {
            return -1;
        }
        if (bArr == null) {
            return 0;
        }
        return this.connection.bulkTransfer(this.outEndpoint, bArr, bArr.length, i);
    }

    @Override // com.felhr.usbserial.UsbSerialInterface
    public int syncRead(byte[] bArr, int i) {
        if (this.asyncMode) {
            return -1;
        }
        if (bArr == null) {
            return 0;
        }
        return this.connection.bulkTransfer(this.inEndpoint, bArr, bArr.length, i);
    }

    public void debug(boolean z) {
        SerialBuffer serialBuffer = this.serialBuffer;
        if (serialBuffer != null) {
            serialBuffer.debug(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isFTDIDevice() {
        return this instanceof FTDISerialDevice;
    }

    public static boolean isCdcDevice(UsbDevice usbDevice) {
        int interfaceCount = usbDevice.getInterfaceCount();
        for (int i = 0; i <= interfaceCount - 1; i++) {
            if (usbDevice.getInterface(i).getInterfaceClass() == 10) {
                return true;
            }
        }
        return false;
    }

    protected class WorkerThread extends Thread {
        private UsbSerialInterface.UsbReadCallback callback;
        private UsbRequest requestIN;
        private UsbSerialDevice usbSerialDevice;
        private AtomicBoolean working = new AtomicBoolean(true);

        public WorkerThread(UsbSerialDevice usbSerialDevice) {
            this.usbSerialDevice = usbSerialDevice;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (this.working.get()) {
                UsbRequest usbRequestRequestWait = UsbSerialDevice.this.connection.requestWait();
                if (usbRequestRequestWait != null && usbRequestRequestWait.getEndpoint().getType() == 2 && usbRequestRequestWait.getEndpoint().getDirection() == 128) {
                    byte[] dataReceived = UsbSerialDevice.this.serialBuffer.getDataReceived();
                    if (UsbSerialDevice.this.isFTDIDevice()) {
                        ((FTDISerialDevice) this.usbSerialDevice).ftdiUtilities.checkModemStatus(dataReceived);
                        UsbSerialDevice.this.serialBuffer.clearReadBuffer();
                        if (dataReceived.length > 2) {
                            onReceivedData(((FTDISerialDevice) this.usbSerialDevice).ftdiUtilities.adaptArray(dataReceived));
                        }
                    } else {
                        UsbSerialDevice.this.serialBuffer.clearReadBuffer();
                        onReceivedData(dataReceived);
                    }
                    this.requestIN.queue(UsbSerialDevice.this.serialBuffer.getReadBuffer(), 16384);
                }
            }
        }

        public void setCallback(UsbSerialInterface.UsbReadCallback usbReadCallback) {
            this.callback = usbReadCallback;
        }

        public void setUsbRequest(UsbRequest usbRequest) {
            this.requestIN = usbRequest;
        }

        public UsbRequest getUsbRequest() {
            return this.requestIN;
        }

        private void onReceivedData(byte[] bArr) {
            UsbSerialInterface.UsbReadCallback usbReadCallback = this.callback;
            if (usbReadCallback != null) {
                usbReadCallback.onReceivedData(bArr);
            }
        }

        public void stopWorkingThread() {
            this.working.set(false);
        }
    }

    protected class WriteThread extends Thread {
        private UsbEndpoint outEndpoint;
        private AtomicBoolean working = new AtomicBoolean(true);

        public WriteThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (this.working.get()) {
                byte[] writeBuffer = UsbSerialDevice.this.serialBuffer.getWriteBuffer();
                if (writeBuffer.length > 0) {
                    UsbSerialDevice.this.connection.bulkTransfer(this.outEndpoint, writeBuffer, writeBuffer.length, 5000);
                }
            }
        }

        public void setUsbEndpoint(UsbEndpoint usbEndpoint) {
            this.outEndpoint = usbEndpoint;
        }

        public void stopWriteThread() {
            this.working.set(false);
        }
    }

    protected class ReadThread extends Thread {
        private UsbSerialInterface.UsbReadCallback callback;
        private UsbEndpoint inEndpoint;
        private UsbSerialDevice usbSerialDevice;
        private AtomicBoolean working = new AtomicBoolean(true);

        public ReadThread(UsbSerialDevice usbSerialDevice) {
            this.usbSerialDevice = usbSerialDevice;
        }

        public void setCallback(UsbSerialInterface.UsbReadCallback usbReadCallback) {
            this.callback = usbReadCallback;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (this.working.get()) {
                int iBulkTransfer = this.inEndpoint != null ? UsbSerialDevice.this.connection.bulkTransfer(this.inEndpoint, UsbSerialDevice.this.serialBuffer.getBufferCompatible(), 16384, 0) : 0;
                if (iBulkTransfer > 0) {
                    byte[] dataReceivedCompatible = UsbSerialDevice.this.serialBuffer.getDataReceivedCompatible(iBulkTransfer);
                    if (UsbSerialDevice.this.isFTDIDevice()) {
                        ((FTDISerialDevice) this.usbSerialDevice).ftdiUtilities.checkModemStatus(dataReceivedCompatible);
                        if (dataReceivedCompatible.length > 2) {
                            onReceivedData(((FTDISerialDevice) this.usbSerialDevice).ftdiUtilities.adaptArray(dataReceivedCompatible));
                        }
                    } else {
                        onReceivedData(dataReceivedCompatible);
                    }
                }
            }
        }

        public void setUsbEndpoint(UsbEndpoint usbEndpoint) {
            this.inEndpoint = usbEndpoint;
        }

        public void stopReadThread() {
            this.working.set(false);
        }

        private void onReceivedData(byte[] bArr) {
            UsbSerialInterface.UsbReadCallback usbReadCallback = this.callback;
            if (usbReadCallback != null) {
                usbReadCallback.onReceivedData(bArr);
            }
        }
    }

    protected void setSyncParams(UsbEndpoint usbEndpoint, UsbEndpoint usbEndpoint2) {
        this.inEndpoint = usbEndpoint;
        this.outEndpoint = usbEndpoint2;
    }

    protected void setThreadsParams(UsbRequest usbRequest, UsbEndpoint usbEndpoint) {
        if (mr1Version) {
            this.workerThread.setUsbRequest(usbRequest);
            this.writeThread.setUsbEndpoint(usbEndpoint);
        } else {
            this.readThread.setUsbEndpoint(usbRequest.getEndpoint());
            this.writeThread.setUsbEndpoint(usbEndpoint);
        }
    }

    protected void killWorkingThread() {
        ReadThread readThread;
        WorkerThread workerThread;
        boolean z = mr1Version;
        if (z && (workerThread = this.workerThread) != null) {
            workerThread.stopWorkingThread();
            this.workerThread = null;
        } else {
            if (z || (readThread = this.readThread) == null) {
                return;
            }
            readThread.stopReadThread();
            this.readThread = null;
        }
    }

    protected void restartWorkingThread() {
        boolean z = mr1Version;
        if (z && this.workerThread == null) {
            WorkerThread workerThread = new WorkerThread(this);
            this.workerThread = workerThread;
            workerThread.start();
            while (!this.workerThread.isAlive()) {
            }
            return;
        }
        if (z || this.readThread != null) {
            return;
        }
        ReadThread readThread = new ReadThread(this);
        this.readThread = readThread;
        readThread.start();
        while (!this.readThread.isAlive()) {
        }
    }

    protected void killWriteThread() {
        WriteThread writeThread = this.writeThread;
        if (writeThread != null) {
            writeThread.stopWriteThread();
            this.writeThread = null;
            this.serialBuffer.resetWriteBuffer();
        }
    }

    protected void restartWriteThread() {
        if (this.writeThread == null) {
            WriteThread writeThread = new WriteThread();
            this.writeThread = writeThread;
            writeThread.start();
            while (!this.writeThread.isAlive()) {
            }
        }
    }
}
