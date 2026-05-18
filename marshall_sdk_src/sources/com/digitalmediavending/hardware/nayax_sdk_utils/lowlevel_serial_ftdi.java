package com.digitalmediavending.hardware.nayax_sdk_utils;

import android.util.Log;
import com.bitmick.marshall.interfaces.lowlevel_i;
import com.bitmick.utils.ByteArrayUtils;
import com.bitmick.utils.Utils;
import com.digitalmediavending.hardware.nayax_sdk_utils.usbserial.driver.UsbSerialPort;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class lowlevel_serial_ftdi implements lowlevel_i {
    public static final String TAG = "lowlevel_serial_ftdi";
    public static UsbSerialPort m_serial_port_ftdi;
    private Thread m_client_thread;
    private boolean m_client_thread_running;
    private lowlevel_i.link_events_t m_link_events;
    private int m_serial_port_baud_rate;

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void onLinkTimerTick(long ms) {
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void reset() {
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void init(Object i_f, Object i_f_params) {
        m_serial_port_ftdi = (UsbSerialPort) i_f;
        this.m_serial_port_baud_rate = ((Integer) i_f_params).intValue();
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void register_link_events(lowlevel_i.link_events_t events) {
        this.m_link_events = events;
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void start() {
        int i;
        try {
            UsbSerialPort usbSerialPort = m_serial_port_ftdi;
            if (usbSerialPort != null && (i = this.m_serial_port_baud_rate) > 0) {
                usbSerialPort.setParameters(i, 8, 1, 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            m_serial_port_ftdi = null;
        }
        if (m_serial_port_ftdi == null) {
            Log.d(TAG, "failed opening serial port");
            return;
        }
        Thread thread = new Thread(new ClientAltHandlingRunnable());
        this.m_client_thread = thread;
        thread.setPriority(10);
        this.m_client_thread.start();
        Log.d(TAG, "started");
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public void stop() {
        UsbSerialPort usbSerialPort = m_serial_port_ftdi;
        if (usbSerialPort != null) {
            try {
                usbSerialPort.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.bitmick.marshall.interfaces.lowlevel_i
    public boolean transmit(byte[] data, int len) {
        try {
            byte[] bArr = new byte[len];
            System.arraycopy(data, 0, bArr, 0, len);
            m_serial_port_ftdi.write(bArr, 0);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    private class ClientAltHandlingRunnable implements Runnable {
        private int head;
        private byte[] rx_buf;
        private int tail;
        byte[] temp_buf;
        private boolean thread_running;

        private ClientAltHandlingRunnable() {
            this.temp_buf = new byte[1024];
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
            synchronized (lowlevel_serial_ftdi.m_serial_port_ftdi) {
                i = 0;
                if (Math.min(this.temp_buf.length, buf.length - offset) > 0) {
                    try {
                        int i2 = lowlevel_serial_ftdi.m_serial_port_ftdi.read(this.temp_buf, 1000);
                        System.arraycopy(this.temp_buf, 0, buf, offset, i2);
                        i = i2;
                    } catch (Exception e) {
                        e.printStackTrace();
                        i = -1;
                    }
                }
            }
            return i;
        }

        @Override // java.lang.Runnable
        public void run() {
            int iByteArrToShort;
            int i;
            lowlevel_serial_ftdi.this.m_client_thread_running = true;
            byte[] bArr = new byte[16384];
            serial_reset();
            while (lowlevel_serial_ftdi.this.m_client_thread_running) {
                int iSerial_read = serial_read(bArr, this.head);
                if (iSerial_read < 0) {
                    lowlevel_serial_ftdi.this.m_client_thread_running = false;
                } else {
                    int i2 = this.head + iSerial_read;
                    this.head = i2;
                    if (i2 != this.tail) {
                        Boolean.valueOf(true);
                        do {
                            iByteArrToShort = ByteArrayUtils.byteArrToShort(bArr, this.tail) + 2;
                            i = this.head - this.tail;
                            if (iByteArrToShort < 9 || iByteArrToShort > 512) {
                                serial_reset();
                            } else if (i >= iByteArrToShort) {
                                if (!lowlevel_serial_ftdi.this.m_link_events.onReceive(bArr, this.tail, iByteArrToShort)) {
                                    serial_reset();
                                } else {
                                    int i3 = this.tail + iByteArrToShort;
                                    this.tail = i3;
                                    if (16384 - i3 <= 412 && i3 == this.head) {
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
            lowlevel_serial_ftdi.this.stop();
        }
    }
}
