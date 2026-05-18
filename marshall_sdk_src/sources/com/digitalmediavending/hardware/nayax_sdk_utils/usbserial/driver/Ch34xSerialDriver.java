package com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.util.Log;
import com.ftdi.j2xx.ft4222.FT_4222_Defines;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class Ch34xSerialDriver implements UsbSerialDriver {
    private static final String TAG = "Ch34xSerialDriver";
    private final UsbDevice mDevice;
    private final UsbSerialPort mPort;

    public Ch34xSerialDriver(UsbDevice device) {
        this.mDevice = device;
        this.mPort = new Ch340SerialPort(device, 0);
    }

    @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialDriver
    public UsbDevice getDevice() {
        return this.mDevice;
    }

    @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialDriver
    public List<UsbSerialPort> getPorts() {
        return Collections.singletonList(this.mPort);
    }

    public class Ch340SerialPort extends CommonUsbSerialPort {
        private static final int USB_TIMEOUT_MILLIS = 5000;
        private final int DEFAULT_BAUD_RATE;
        private boolean dtr;
        private UsbEndpoint mReadEndpoint;
        private UsbEndpoint mWriteEndpoint;
        private boolean rts;

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public boolean getCD() throws IOException {
            return false;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public boolean getCTS() throws IOException {
            return false;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public boolean getDSR() throws IOException {
            return false;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public boolean getRI() throws IOException {
            return false;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public boolean purgeHwBuffers(boolean purgeReadBuffers, boolean purgeWriteBuffers) throws IOException {
            return true;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public /* bridge */ /* synthetic */ int getPortNumber() {
            return super.getPortNumber();
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public /* bridge */ /* synthetic */ String getSerial() {
            return super.getSerial();
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort
        public /* bridge */ /* synthetic */ String toString() {
            return super.toString();
        }

        public Ch340SerialPort(UsbDevice device, int portNumber) {
            super(device, portNumber);
            this.DEFAULT_BAUD_RATE = 9600;
            this.dtr = false;
            this.rts = false;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public UsbSerialDriver getDriver() {
            return Ch34xSerialDriver.this;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public void open(UsbDeviceConnection connection) throws IOException {
            if (this.mConnection != null) {
                throw new IOException("Already opened.");
            }
            this.mConnection = connection;
            for (int i = 0; i < this.mDevice.getInterfaceCount(); i++) {
                try {
                    if (this.mConnection.claimInterface(this.mDevice.getInterface(i), true)) {
                        Log.d(Ch34xSerialDriver.TAG, "claimInterface " + i + " SUCCESS");
                    } else {
                        Log.d(Ch34xSerialDriver.TAG, "claimInterface " + i + " FAIL");
                    }
                } catch (Throwable th) {
                    try {
                        close();
                    } catch (IOException unused) {
                    }
                    throw th;
                }
            }
            UsbInterface usbInterface = this.mDevice.getInterface(this.mDevice.getInterfaceCount() - 1);
            for (int i2 = 0; i2 < usbInterface.getEndpointCount(); i2++) {
                UsbEndpoint endpoint = usbInterface.getEndpoint(i2);
                if (endpoint.getType() == 2) {
                    if (endpoint.getDirection() == 128) {
                        this.mReadEndpoint = endpoint;
                    } else {
                        this.mWriteEndpoint = endpoint;
                    }
                }
            }
            initialize();
            setBaudRate(9600);
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public void close() throws IOException {
            if (this.mConnection == null) {
                throw new IOException("Already closed");
            }
            try {
                this.mConnection.close();
            } finally {
                this.mConnection = null;
            }
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public int read(byte[] dest, int timeoutMillis) throws IOException {
            synchronized (this.mReadBufferLock) {
                int iBulkTransfer = this.mConnection.bulkTransfer(this.mReadEndpoint, this.mReadBuffer, Math.min(dest.length, this.mReadBuffer.length), timeoutMillis);
                if (iBulkTransfer < 0) {
                    return 0;
                }
                System.arraycopy(this.mReadBuffer, 0, dest, 0, iBulkTransfer);
                return iBulkTransfer;
            }
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public int write(byte[] src, int timeoutMillis) throws IOException {
            int iMin;
            byte[] bArr;
            int iBulkTransfer;
            int i = 0;
            while (i < src.length) {
                synchronized (this.mWriteBufferLock) {
                    iMin = Math.min(src.length - i, this.mWriteBuffer.length);
                    if (i == 0) {
                        bArr = src;
                    } else {
                        System.arraycopy(src, i, this.mWriteBuffer, 0, iMin);
                        bArr = this.mWriteBuffer;
                    }
                    iBulkTransfer = this.mConnection.bulkTransfer(this.mWriteEndpoint, bArr, iMin, timeoutMillis);
                }
                if (iBulkTransfer > 0) {
                    Log.d(Ch34xSerialDriver.TAG, "Wrote amt=" + iBulkTransfer + " attempted=" + iMin);
                    i += iBulkTransfer;
                } else {
                    throw new IOException("Error writing " + iMin + " bytes at offset " + i + " length=" + src.length);
                }
            }
            return i;
        }

        private int controlOut(int request, int value, int index) {
            return this.mConnection.controlTransfer(65, request, value, index, null, 0, 5000);
        }

        private int controlIn(int request, int value, int index, byte[] buffer) {
            return this.mConnection.controlTransfer(192, request, value, index, buffer, buffer.length, 5000);
        }

        private void checkState(String msg, int request, int value, int[] expected) throws IOException {
            int i;
            byte[] bArr = new byte[expected.length];
            int iControlIn = controlIn(request, value, 0, bArr);
            if (iControlIn < 0) {
                throw new IOException("Faild send cmd [" + msg + "]");
            }
            if (iControlIn != expected.length) {
                throw new IOException("Expected " + expected.length + " bytes, but get " + iControlIn + " [" + msg + "]");
            }
            for (int i2 = 0; i2 < expected.length; i2++) {
                if (expected[i2] != -1 && expected[i2] != (i = bArr[i2] & 255)) {
                    throw new IOException("Expected 0x" + Integer.toHexString(expected[i2]) + " bytes, but get 0x" + Integer.toHexString(i) + " [" + msg + "]");
                }
            }
        }

        private void writeHandshakeByte() throws IOException {
            if (controlOut(FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_SR_CTL1_REG, ~((this.dtr ? 32 : 0) | (this.rts ? 64 : 0)), 0) < 0) {
                throw new IOException("Faild to set handshake byte");
            }
        }

        private void initialize() throws IOException {
            checkState("init #1", 95, 0, new int[]{-1, 0});
            if (controlOut(FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_DS_CTL1_REG, 0, 0) < 0) {
                throw new IOException("init failed! #2");
            }
            setBaudRate(9600);
            checkState("init #4", 149, 9496, new int[]{-1, 0});
            if (controlOut(154, 9496, 80) < 0) {
                throw new IOException("init failed! #5");
            }
            checkState("init #6", 149, 1798, new int[]{255, 238});
            if (controlOut(FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_DS_CTL1_REG, 20511, 55562) < 0) {
                throw new IOException("init failed! #7");
            }
            setBaudRate(9600);
            writeHandshakeByte();
            checkState("init #10", 149, 1798, new int[]{-1, 238});
        }

        private void setBaudRate(int baudRate) throws IOException {
            int[] iArr = {2400, 55553, 56, 4800, 25602, 31, 9600, 45570, 19, 19200, 55554, 13, 38400, 25603, 10, 115200, 52227, 8};
            for (int i = 0; i < 6; i++) {
                int i2 = i * 3;
                if (iArr[i2] == baudRate) {
                    if (controlOut(154, 4882, iArr[i2 + 1]) < 0) {
                        throw new IOException("Error setting baud rate. #1");
                    }
                    if (controlOut(154, 3884, iArr[i2 + 2]) < 0) {
                        throw new IOException("Error setting baud rate. #1");
                    }
                    return;
                }
            }
            throw new IOException("Baud rate " + baudRate + " currently not supported");
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public void setParameters(int baudRate, int dataBits, int stopBits, int parity) throws IOException {
            setBaudRate(baudRate);
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public boolean getDTR() throws IOException {
            return this.dtr;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public void setDTR(boolean value) throws IOException {
            this.dtr = value;
            writeHandshakeByte();
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public boolean getRTS() throws IOException {
            return this.rts;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public void setRTS(boolean value) throws IOException {
            this.rts = value;
            writeHandshakeByte();
        }
    }

    public static Map<Integer, int[]> getSupportedDevices() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(Integer.valueOf(UsbId.VENDOR_QINHENG), new int[]{UsbId.QINHENG_HL340});
        return linkedHashMap;
    }
}
