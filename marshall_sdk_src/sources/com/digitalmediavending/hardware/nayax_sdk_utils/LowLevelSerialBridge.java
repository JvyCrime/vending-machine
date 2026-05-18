package com.digitalmediavending.hardware.nayax_sdk_utils;

import android_serialport_api.SerialPort;
import com.bitmick.marshall.interfaces.lowlevel_i;
import com.bitmick.utils.ByteArrayUtils;
import com.bitmick.utils.Utils;
import java.io.File;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class LowLevelSerialBridge implements lowlevel_i {
    private Thread m_client_thread;
    private boolean m_client_thread_running;
    private lowlevel_i.link_events_t m_link_events;
    private SerialPort m_serial_port;
    private int m_serial_port_baud_rate;
    private String m_serial_port_number;

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void onLinkTimerTick(long l) {
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void reset() {
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void stop() {
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public boolean transmit(byte[] bytes, int i) {
        return false;
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void init(Object serialPortNumber, Object baudRate) {
        this.m_serial_port_number = (String) serialPortNumber;
        this.m_serial_port_baud_rate = ((Integer) baudRate).intValue();
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void register_link_events(lowlevel_i.link_events_t events) {
        this.m_link_events = events;
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void start() {
        try {
            this.m_serial_port = new SerialPort(new File("/dev/ttyS4"), 115200, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(new ClientAltHandlingRunnable());
        this.m_client_thread = thread;
        thread.start();
    }

    private class ClientAltHandlingRunnable implements Runnable {
        private int head;
        private byte[] rx_buf;
        private int tail;
        private boolean thread_running;

        private ClientAltHandlingRunnable() {
        }

        public void stop() {
            this.thread_running = true;
        }

        private void reset() {
            this.head = 0;
            this.tail = 0;
        }

        private void serial_reset() {
            this.head = 0;
            this.tail = 0;
        }

        private int serial_read(byte[] buf, int offset) {
            int i;
            synchronized (LowLevelSerialBridge.this.m_serial_port) {
                try {
                    i = LowLevelSerialBridge.this.m_serial_port.getInputStream().read(buf, offset, 24);
                } catch (IOException e) {
                    e.printStackTrace();
                    i = 0;
                }
            }
            return i;
        }

        @Override // java.lang.Runnable
        public void run() {
            int iByteArrToShort;
            int i;
            LowLevelSerialBridge.this.m_client_thread_running = true;
            byte[] bArr = new byte[16384];
            serial_reset();
            while (LowLevelSerialBridge.this.m_client_thread_running) {
                int iSerial_read = this.head + serial_read(bArr, this.head);
                this.head = iSerial_read;
                if (iSerial_read != this.tail) {
                    Boolean.valueOf(true);
                    do {
                        iByteArrToShort = ByteArrayUtils.byteArrToShort(bArr, this.tail) + 2;
                        i = this.head - this.tail;
                        if (iByteArrToShort < 9 || iByteArrToShort > 512) {
                            serial_reset();
                        } else if (i >= iByteArrToShort) {
                            if (!LowLevelSerialBridge.this.m_link_events.onReceive(bArr, this.tail, iByteArrToShort)) {
                                serial_reset();
                            } else {
                                int i2 = this.tail + iByteArrToShort;
                                this.tail = i2;
                                if (16384 - i2 <= 412 && i2 == this.head) {
                                    serial_reset();
                                }
                            }
                        }
                    } while (Boolean.valueOf(this.head != this.tail && i >= iByteArrToShort).booleanValue());
                } else {
                    Utils.threadSleep(1);
                }
            }
        }
    }
}
