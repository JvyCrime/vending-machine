package com.felhr.usbserial;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class SerialBuffer {
    public static final int DEFAULT_READ_BUFFER_SIZE = 16384;
    public static final int DEFAULT_WRITE_BUFFER_SIZE = 16384;
    private ByteBuffer readBuffer;
    private byte[] readBuffer_compatible;
    private boolean debugging = false;
    private SynchronizedBuffer writeBuffer = new SynchronizedBuffer();

    public SerialBuffer(boolean z) {
        if (z) {
            this.readBuffer = ByteBuffer.allocate(16384);
        } else {
            this.readBuffer_compatible = new byte[16384];
        }
    }

    public void debug(boolean z) {
        this.debugging = z;
    }

    public void putReadBuffer(ByteBuffer byteBuffer) {
        synchronized (this) {
            try {
                this.readBuffer.put(byteBuffer);
            } catch (BufferOverflowException unused) {
            }
        }
    }

    public ByteBuffer getReadBuffer() {
        ByteBuffer byteBuffer;
        synchronized (this) {
            byteBuffer = this.readBuffer;
        }
        return byteBuffer;
    }

    public byte[] getDataReceived() {
        byte[] bArr;
        synchronized (this) {
            int iPosition = this.readBuffer.position();
            bArr = new byte[iPosition];
            this.readBuffer.position(0);
            this.readBuffer.get(bArr, 0, iPosition);
            if (this.debugging) {
                UsbSerialDebugger.printReadLogGet(bArr, true);
            }
        }
        return bArr;
    }

    public void clearReadBuffer() {
        synchronized (this) {
            this.readBuffer.clear();
        }
    }

    public byte[] getWriteBuffer() {
        return this.writeBuffer.get();
    }

    public void putWriteBuffer(byte[] bArr) {
        this.writeBuffer.put(bArr);
    }

    public void resetWriteBuffer() {
        this.writeBuffer.reset();
    }

    public byte[] getBufferCompatible() {
        return this.readBuffer_compatible;
    }

    public byte[] getDataReceivedCompatible(int i) {
        return Arrays.copyOfRange(this.readBuffer_compatible, 0, i);
    }

    private class SynchronizedBuffer {
        private byte[] buffer = new byte[16384];
        private int position = -1;

        public SynchronizedBuffer() {
        }

        public synchronized void put(byte[] bArr) {
            if (bArr != null) {
                if (bArr.length != 0) {
                    if (this.position == -1) {
                        this.position = 0;
                    }
                    if (SerialBuffer.this.debugging) {
                        UsbSerialDebugger.printLogPut(bArr, true);
                    }
                    int i = this.position;
                    if (bArr.length + i > 16383) {
                        if (i < 16384) {
                            System.arraycopy(bArr, 0, this.buffer, i, 16384 - i);
                        }
                        this.position = 16384;
                        notify();
                    } else {
                        System.arraycopy(bArr, 0, this.buffer, i, bArr.length);
                        this.position += bArr.length;
                        notify();
                    }
                }
            }
        }

        public synchronized byte[] get() {
            if (this.position == -1) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int i = this.position;
            if (i <= -1) {
                return new byte[0];
            }
            byte[] bArrCopyOfRange = Arrays.copyOfRange(this.buffer, 0, i);
            if (SerialBuffer.this.debugging) {
                UsbSerialDebugger.printLogGet(bArrCopyOfRange, true);
            }
            this.position = -1;
            return bArrCopyOfRange;
        }

        public synchronized void reset() {
            this.position = -1;
        }
    }
}
