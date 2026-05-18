package android_serialport_api;

import android.text.TextUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.Timer;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
public class Serial {
    public static boolean DBG = true;
    private static final String TAG = "Serial";
    public static boolean VDBG = true;
    public static boolean VVDBG = true;
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private OnserialHandle mSerialHandle;
    private SerialPort mSerialPort;
    private CircleQueue mCircleQueue = new CircleQueue();
    private ReadThread mReadThread = null;
    private String mSerialName = null;
    private Timer mHandleTimer = null;
    private HandleTimerTask mHandleTimerTask = null;

    public interface OnserialHandle {
        void onSerialHandle(CircleQueue mCircleQueue);
    }

    private class ReadThread extends Thread {
        private ReadThread() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            byte[] bArr = new byte[512];
            super.run();
            while (!isInterrupted()) {
                try {
                    if (Serial.this.mInputStream == null) {
                        return;
                    }
                    int i = Serial.this.mInputStream.read(bArr);
                    if (i > 0) {
                        Serial.this.mCircleQueue.write(bArr, i);
                    }
                    if (Serial.this.mHandleTimerTask != null) {
                        Serial.this.mHandleTimerTask.cancel();
                    }
                    Serial.this.mHandleTimerTask = Serial.this.new HandleTimerTask();
                    Serial.this.mHandleTimer.schedule(Serial.this.mHandleTimerTask, 20L);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    public class HandleTimerTask extends TimerTask {
        public HandleTimerTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            Serial.this.mSerialHandle.onSerialHandle(Serial.this.mCircleQueue);
        }
    }

    public void openSerialAndRead(String serialName, int baudrate, OnserialHandle sh) {
        this.mSerialName = serialName;
        this.mSerialHandle = sh;
        if (TextUtils.isEmpty(serialName)) {
            return;
        }
        try {
            SerialPort serialPort = getSerialPort(serialName, baudrate);
            this.mSerialPort = serialPort;
            this.mOutputStream = serialPort.getOutputStream();
            this.mInputStream = this.mSerialPort.getInputStream();
            this.mHandleTimer = new Timer();
            ReadThread readThread = new ReadThread();
            this.mReadThread = readThread;
            readThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SecurityException e2) {
            e2.printStackTrace();
        } catch (InvalidParameterException e3) {
            e3.printStackTrace();
        }
    }

    public void closeSerialPortAndStopRead() {
        ReadThread readThread = this.mReadThread;
        if (readThread != null) {
            readThread.interrupt();
            this.mReadThread = null;
        }
        OutputStream outputStream = this.mOutputStream;
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.mOutputStream = null;
        }
        InputStream inputStream = this.mInputStream;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            this.mInputStream = null;
        }
        if (this.mSerialPort != null) {
            closeSerialPort();
            this.mSerialPort = null;
        }
    }

    public SerialPort getSerialPort(String serialName, int baudrate) throws InvalidParameterException, SecurityException, IOException {
        if (this.mSerialPort == null) {
            String str = "/dev/" + serialName;
            if (str.length() == 0 || baudrate == -1) {
                throw new InvalidParameterException();
            }
            this.mSerialPort = new SerialPort(new File(str), baudrate, 0);
        }
        return this.mSerialPort;
    }

    public void closeSerialPort() {
        SerialPort serialPort = this.mSerialPort;
        if (serialPort != null) {
            serialPort.close();
            this.mSerialPort = null;
        }
    }

    public String getSerialName() {
        return this.mSerialName;
    }

    public OutputStream getOutputStream() {
        return this.mOutputStream;
    }

    public void onDestroy() {
        Timer timer = this.mHandleTimer;
        if (timer != null) {
            timer.cancel();
        }
        closeSerialPortAndStopRead();
    }
}
