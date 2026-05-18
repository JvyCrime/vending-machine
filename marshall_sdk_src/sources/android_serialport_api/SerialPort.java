package android_serialport_api;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes.dex */
public class SerialPort {
    private static final String TAG = "SerialPort";
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;

    private static native FileDescriptor open(String paramString, int paramInt1, int paramInt2);

    public native void close();

    public SerialPort(File device, int baudrate, int flags) throws IOException, SecurityException {
        if (!device.canRead() || !device.canWrite()) {
            try {
                Process processExec = Runtime.getRuntime().exec("/system/bin/su");
                processExec.getOutputStream().write(("chmod 666 " + device.getAbsolutePath() + "\nexit\n").getBytes());
                if (processExec.waitFor() != 0 || !device.canRead() || !device.canWrite()) {
                    throw new SecurityException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new SecurityException();
            }
        }
        FileDescriptor fileDescriptorOpen = open(device.getAbsolutePath(), baudrate, flags);
        this.mFd = fileDescriptorOpen;
        if (fileDescriptorOpen == null) {
            throw new IOException();
        }
        this.mFileInputStream = new FileInputStream(this.mFd);
        this.mFileOutputStream = new FileOutputStream(this.mFd);
    }

    public InputStream getInputStream() {
        return this.mFileInputStream;
    }

    public OutputStream getOutputStream() {
        return this.mFileOutputStream;
    }

    static {
        System.loadLibrary("serial_port");
    }
}
