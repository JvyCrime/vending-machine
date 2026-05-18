package com.digitalmediavending.hardware.nayax_sdk_utils;

import com.bitmick.marshall.interfaces.lowlevel_i;
import com.bitmick.utils.ByteArrayUtils;
import com.bitmick.utils.Log;
import com.bitmick.utils.Utils;
import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class lowlevel_serial implements lowlevel_i {
    public static final String TAG = "lowlevel_serial";
    private Thread m_client_thread;
    private boolean m_client_thread_running;
    private lowlevel_i.link_events_t m_link_events;
    private SerialPort m_serial_port;
    private int m_serial_port_baud_rate;
    private String m_serial_port_number;

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void onLinkTimerTick(long ms) {
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void reset() {
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void init(Object i_f, Object i_f_params) {
        this.m_serial_port_number = (String) i_f;
        this.m_serial_port_baud_rate = ((Integer) i_f_params).intValue();
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void register_link_events(lowlevel_i.link_events_t events) {
        this.m_link_events = events;
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void start() {
        SerialPort commPort = SerialPort.getCommPort(this.m_serial_port_number);
        this.m_serial_port = commPort;
        commPort.setComPortParameters(this.m_serial_port_baud_rate, 8, 1, 0);
        SerialPort serialPort = this.m_serial_port;
        if (serialPort == null || !serialPort.openPort()) {
            Log.d(TAG, "failed opening serial port");
            this.m_serial_port = null;
        } else {
            Thread thread = new Thread(new ClientAltHandlingRunnable());
            this.m_client_thread = thread;
            thread.start();
        }
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void stop() {
        SerialPort serialPort = this.m_serial_port;
        if (serialPort != null) {
            serialPort.removeDataListener();
            this.m_serial_port.closePort();
        }
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public boolean transmit(byte[] data, int len) {
        this.m_serial_port.writeBytes(data, len);
        return true;
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
            synchronized (lowlevel_serial.this.m_serial_port) {
                int iMin = Math.min(lowlevel_serial.this.m_serial_port.bytesAvailable(), buf.length - offset);
                if (iMin > 0) {
                    try {
                        i = lowlevel_serial.this.m_serial_port.getInputStream().read(buf, offset, iMin);
                    } catch (IOException e) {
                        e.printStackTrace();
                        i = 0;
                    }
                } else {
                    i = 0;
                }
            }
            return i;
        }

        @Override // java.lang.Runnable
        public void run() {
            int iByteArrToShort;
            int i;
            lowlevel_serial.this.m_client_thread_running = true;
            byte[] bArr = new byte[16384];
            serial_reset();
            while (lowlevel_serial.this.m_client_thread_running) {
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
                            if (!lowlevel_serial.this.m_link_events.onReceive(bArr, this.tail, iByteArrToShort)) {
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
