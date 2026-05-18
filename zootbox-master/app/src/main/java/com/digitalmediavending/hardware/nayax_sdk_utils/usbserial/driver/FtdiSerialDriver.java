package com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver;

import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class FtdiSerialDriver implements UsbSerialDriver {
    private final UsbDevice mDevice;
    private final UsbSerialPort mPort;

    private enum DeviceType {
        TYPE_BM,
        TYPE_AM,
        TYPE_2232C,
        TYPE_R,
        TYPE_2232H,
        TYPE_4232H
    }

    public FtdiSerialDriver(UsbDevice device) {
        this.mDevice = device;
        this.mPort = new FtdiSerialPort(device, 0);
    }

    @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialDriver
    public UsbDevice getDevice() {
        return this.mDevice;
    }

    @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialDriver
    public List<UsbSerialPort> getPorts() {
        return Collections.singletonList(this.mPort);
    }

    private class FtdiSerialPort extends CommonUsbSerialPort {
        private static final boolean ENABLE_ASYNC_READS = false;
        public static final int FTDI_DEVICE_IN_REQTYPE = 192;
        public static final int FTDI_DEVICE_OUT_REQTYPE = 64;
        private static final int MODEM_STATUS_HEADER_LENGTH = 2;
        private static final int SIO_MODEM_CTRL_REQUEST = 1;
        private static final int SIO_RESET_PURGE_RX = 1;
        private static final int SIO_RESET_PURGE_TX = 2;
        private static final int SIO_RESET_REQUEST = 0;
        private static final int SIO_RESET_SIO = 0;
        private static final int SIO_SET_BAUD_RATE_REQUEST = 3;
        private static final int SIO_SET_DATA_REQUEST = 4;
        private static final int SIO_SET_FLOW_CTRL_REQUEST = 2;
        public static final int USB_ENDPOINT_IN = 128;
        public static final int USB_ENDPOINT_OUT = 0;
        public static final int USB_READ_TIMEOUT_MILLIS = 5000;
        public static final int USB_RECIP_DEVICE = 0;
        public static final int USB_RECIP_ENDPOINT = 2;
        public static final int USB_RECIP_INTERFACE = 1;
        public static final int USB_RECIP_OTHER = 3;
        public static final int USB_TYPE_CLASS = 0;
        public static final int USB_TYPE_RESERVED = 0;
        public static final int USB_TYPE_STANDARD = 0;
        public static final int USB_TYPE_VENDOR = 0;
        public static final int USB_WRITE_TIMEOUT_MILLIS = 5000;
        private final String TAG;
        private int mInterface;
        private int mMaxPacketSize;
        private DeviceType mType;

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
            return false;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public boolean getRI() throws IOException {
            return false;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public boolean getRTS() throws IOException {
            return false;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public void setDTR(boolean value) throws IOException {
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public void setRTS(boolean value) throws IOException {
        }

        public FtdiSerialPort(UsbDevice device, int portNumber) {
            super(device, portNumber);
            this.TAG = FtdiSerialDriver.class.getSimpleName();
            this.mInterface = 0;
            this.mMaxPacketSize = 64;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public UsbSerialDriver getDriver() {
            return FtdiSerialDriver.this;
        }

        private final int filterStatusBytes(byte[] src, byte[] dest, int totalBytesRead, int maxPacketSize) {
            int i = totalBytesRead % maxPacketSize;
            int i2 = 0;
            int i3 = (totalBytesRead / maxPacketSize) + (i == 0 ? 0 : 1);
            while (i2 < i3) {
                int i4 = i2 == i3 + (-1) ? i - 2 : maxPacketSize - 2;
                if (i4 > 0) {
                    System.arraycopy(src, (i2 * maxPacketSize) + 2, dest, (maxPacketSize - 2) * i2, i4);
                }
                i2++;
            }
            return totalBytesRead - (i3 * 2);
        }

        public void reset() throws IOException {
            int iControlTransfer = this.mConnection.controlTransfer(64, 0, 0, 0, null, 0, 5000);
            if (iControlTransfer != 0) {
                throw new IOException("Reset failed: result=" + iControlTransfer);
            }
            this.mType = DeviceType.TYPE_R;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public void open(UsbDeviceConnection connection) throws IOException {
            if (this.mConnection != null) {
                throw new IOException("Already open");
            }
            this.mConnection = connection;
            for (int i = 0; i < this.mDevice.getInterfaceCount(); i++) {
                try {
                    if (connection.claimInterface(this.mDevice.getInterface(i), true)) {
                        Log.d(this.TAG, "claimInterface " + i + " SUCCESS");
                    } else {
                        throw new IOException("Error claiming interface " + i);
                    }
                } catch (Throwable th) {
                    close();
                    this.mConnection = null;
                    throw th;
                }
            }
            reset();
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
            int iFilterStatusBytes;
            UsbEndpoint endpoint = this.mDevice.getInterface(0).getEndpoint(0);
            synchronized (this.mReadBufferLock) {
                int iBulkTransfer = this.mConnection.bulkTransfer(endpoint, this.mReadBuffer, Math.min(dest.length, this.mReadBuffer.length), timeoutMillis);
                if (iBulkTransfer < 2) {
                    throw new IOException("Expected at least 2 bytes");
                }
                iFilterStatusBytes = filterStatusBytes(this.mReadBuffer, dest, iBulkTransfer, endpoint.getMaxPacketSize());
            }
            return iFilterStatusBytes;
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public int write(byte[] src, int timeoutMillis) throws IOException {
            int iMin;
            byte[] bArr;
            int iBulkTransfer;
            UsbEndpoint endpoint = this.mDevice.getInterface(0).getEndpoint(1);
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
                    iBulkTransfer = this.mConnection.bulkTransfer(endpoint, bArr, iMin, timeoutMillis);
                }
                if (iBulkTransfer <= 0) {
                    throw new IOException("Error writing " + iMin + " bytes at offset " + i + " length=" + src.length);
                }
                i += iBulkTransfer;
            }
            return i;
        }

        private int setBaudRate(int baudRate) throws IOException {
            long[] jArrConvertBaudrate = convertBaudrate(baudRate);
            long j = jArrConvertBaudrate[0];
            int iControlTransfer = this.mConnection.controlTransfer(64, 3, (int) jArrConvertBaudrate[2], (int) jArrConvertBaudrate[1], null, 0, 5000);
            if (iControlTransfer == 0) {
                return (int) j;
            }
            throw new IOException("Setting baudrate failed: result=" + iControlTransfer);
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public void setParameters(int baudRate, int dataBits, int stopBits, int parity) throws IOException {
            int i;
            int i2;
            setBaudRate(baudRate);
            if (parity == 0) {
                i = dataBits | 0;
            } else if (parity == 1) {
                i = dataBits | 256;
            } else if (parity == 2) {
                i = dataBits | 512;
            } else if (parity == 3) {
                i = dataBits | 768;
            } else {
                if (parity != 4) {
                    throw new IllegalArgumentException("Unknown parity value: " + parity);
                }
                i = dataBits | 1024;
            }
            if (stopBits == 1) {
                i2 = i | 0;
            } else if (stopBits == 2) {
                i2 = i | 4096;
            } else {
                if (stopBits != 3) {
                    throw new IllegalArgumentException("Unknown stopBits value: " + stopBits);
                }
                i2 = i | 2048;
            }
            int iControlTransfer = this.mConnection.controlTransfer(64, 4, i2, 0, null, 0, 5000);
            if (iControlTransfer == 0) {
                return;
            }
            throw new IOException("Setting parameters failed: result=" + iControlTransfer);
        }

        private long[] convertBaudrate(int baudrate) {
            int i = 24000000 / baudrate;
            int[] iArr = {0, 3, 2, 4, 1, 5, 6, 7};
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (true) {
                if (i2 >= 2) {
                    break;
                }
                int i6 = i + i2;
                if (i6 <= 8) {
                    i6 = 8;
                } else if (this.mType != DeviceType.TYPE_AM && i6 < 12) {
                    i6 = 12;
                } else if (i < 16) {
                    i6 = 16;
                } else if (this.mType != DeviceType.TYPE_AM && i6 > 131071) {
                    i6 = 131071;
                }
                int i7 = ((i6 / 2) + 24000000) / i6;
                int i8 = i7 < baudrate ? baudrate - i7 : i7 - baudrate;
                if (i2 == 0 || i8 < i3) {
                    i5 = i7;
                    if (i8 == 0) {
                        i4 = i6;
                        break;
                    }
                    i3 = i8;
                    i4 = i6;
                }
                i2++;
            }
            long j = (i4 >> 3) | (iArr[i4 & 7] << 14);
            if (j == 1) {
                j = 0;
            } else if (j == 16385) {
                j = 1;
            }
            return new long[]{i5, (this.mType == DeviceType.TYPE_2232C || this.mType == DeviceType.TYPE_2232H || this.mType == DeviceType.TYPE_4232H) ? ((j >> 8) & 65535 & 65280) | 0 : (j >> 16) & 65535, j & 65535};
        }

        @Override // com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.CommonUsbSerialPort, com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort
        public boolean purgeHwBuffers(boolean purgeReadBuffers, boolean purgeWriteBuffers) throws IOException {
            int iControlTransfer;
            int iControlTransfer2;
            if (purgeReadBuffers && (iControlTransfer2 = this.mConnection.controlTransfer(64, 0, 1, 0, null, 0, 5000)) != 0) {
                throw new IOException("Flushing RX failed: result=" + iControlTransfer2);
            }
            if (!purgeWriteBuffers || (iControlTransfer = this.mConnection.controlTransfer(64, 0, 2, 0, null, 0, 5000)) == 0) {
                return true;
            }
            throw new IOException("Flushing RX failed: result=" + iControlTransfer);
        }
    }

    public static Map<Integer, int[]> getSupportedDevices() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(Integer.valueOf(UsbId.VENDOR_FTDI), new int[]{UsbId.FTDI_FT232R, UsbId.FTDI_FT231X});
        return linkedHashMap;
    }
}
