package com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.util.Log;
import iaik.security.ssl.SSLContext;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class Cp21xxSerialDriver implements UsbSerialDriver {
    private static final String TAG = "Cp21xxSerialDriver";
    private final UsbDevice mDevice;
    private final UsbSerialPort mPort;

    public Cp21xxSerialDriver(UsbDevice device) {
        this.mDevice = device;
        this.mPort = new Cp21xxSerialPort(device, 0);
    }

    @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialDriver
    public UsbDevice getDevice() {
        return this.mDevice;
    }

    @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialDriver
    public List<UsbSerialPort> getPorts() {
        return Collections.singletonList(this.mPort);
    }

    public class Cp21xxSerialPort extends CommonUsbSerialPort {
        private static final int BAUD_RATE_GEN_FREQ = 3686400;
        private static final int CONTROL_WRITE_DTR = 256;
        private static final int CONTROL_WRITE_RTS = 512;
        private static final int DEFAULT_BAUD_RATE = 9600;
        private static final int FLUSH_READ_CODE = 10;
        private static final int FLUSH_WRITE_CODE = 5;
        private static final int MCR_ALL = 3;
        private static final int MCR_DTR = 1;
        private static final int MCR_RTS = 2;
        private static final int REQTYPE_HOST_TO_DEVICE = 65;
        private static final int SILABSER_FLUSH_REQUEST_CODE = 18;
        private static final int SILABSER_IFC_ENABLE_REQUEST_CODE = 0;
        private static final int SILABSER_SET_BAUDDIV_REQUEST_CODE = 1;
        private static final int SILABSER_SET_BAUDRATE = 30;
        private static final int SILABSER_SET_LINE_CTL_REQUEST_CODE = 3;
        private static final int SILABSER_SET_MHS_REQUEST_CODE = 7;
        private static final int UART_DISABLE = 0;
        private static final int UART_ENABLE = 1;
        private static final int USB_WRITE_TIMEOUT_MILLIS = 5000;
        private UsbEndpoint mReadEndpoint;
        private UsbEndpoint mWriteEndpoint;

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
        public boolean getDTR() throws IOException {
            return true;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public boolean getRI() throws IOException {
            return false;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public boolean getRTS() throws IOException {
            return true;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public void setDTR(boolean value) throws IOException {
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public void setRTS(boolean value) throws IOException {
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

        public Cp21xxSerialPort(UsbDevice device, int portNumber) {
            super(device, portNumber);
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public UsbSerialDriver getDriver() {
            return Cp21xxSerialDriver.this;
        }

        private int setConfigSingle(int request, int value) {
            return this.mConnection.controlTransfer(65, request, value, 0, null, 0, 5000);
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
                        Log.d(Cp21xxSerialDriver.TAG, "claimInterface " + i + " SUCCESS");
                    } else {
                        Log.d(Cp21xxSerialDriver.TAG, "claimInterface " + i + " FAIL");
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
            setConfigSingle(0, 1);
            setConfigSingle(7, SSLContext.VERSION_TLS12);
            setConfigSingle(1, 384);
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public void close() throws IOException {
            if (this.mConnection == null) {
                throw new IOException("Already closed");
            }
            try {
                setConfigSingle(0, 0);
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
                    Log.d(Cp21xxSerialDriver.TAG, "Wrote amt=" + iBulkTransfer + " attempted=" + iMin);
                    i += iBulkTransfer;
                } else {
                    throw new IOException("Error writing " + iMin + " bytes at offset " + i + " length=" + src.length);
                }
            }
            return i;
        }

        private void setBaudRate(int baudRate) throws IOException {
            if (this.mConnection.controlTransfer(65, 30, 0, 0, new byte[]{(byte) (baudRate & 255), (byte) ((baudRate >> 8) & 255), (byte) ((baudRate >> 16) & 255), (byte) ((baudRate >> 24) & 255)}, 4, 5000) < 0) {
                throw new IOException("Error setting baud rate.");
            }
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public void setParameters(int baudRate, int dataBits, int stopBits, int parity) throws IOException {
            setBaudRate(baudRate);
            int i = 2048;
            if (dataBits == 5) {
                i = 1280;
            } else if (dataBits == 6) {
                i = 1536;
            } else if (dataBits == 7) {
                i = 1792;
            }
            if (parity == 1) {
                i |= 16;
            } else if (parity == 2) {
                i |= 32;
            }
            if (stopBits == 1) {
                i |= 0;
            } else if (stopBits == 2) {
                i |= 2;
            }
            setConfigSingle(3, i);
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public boolean purgeHwBuffers(boolean purgeReadBuffers, boolean purgeWriteBuffers) throws IOException {
            int i = (purgeReadBuffers ? 10 : 0) | (purgeWriteBuffers ? 5 : 0);
            if (i == 0) {
                return true;
            }
            setConfigSingle(18, i);
            return true;
        }
    }

    public static Map<Integer, int[]> getSupportedDevices() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(Integer.valueOf(UsbId.VENDOR_SILABS), new int[]{60000, UsbId.SILABS_CP2105, UsbId.SILABS_CP2108, UsbId.SILABS_CP2110});
        return linkedHashMap;
    }
}
