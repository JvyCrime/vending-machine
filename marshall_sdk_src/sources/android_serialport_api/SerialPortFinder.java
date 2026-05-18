package android_serialport_api;

import android.util.Log;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Iterator;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class SerialPortFinder {
    private static final String TAG = "SerialPort";
    private Vector<Driver> mDrivers = null;

    protected class Driver {
        private String mDeviceRoot;
        private Vector<File> mDevices = null;
        private String mDriverName;

        protected Driver(String name, String root) {
            this.mDriverName = name;
            this.mDeviceRoot = root;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Vector<File> getDevices() {
            if (this.mDevices == null) {
                this.mDevices = new Vector<>();
                File[] fileArrListFiles = new File("/dev").listFiles();
                for (int i = 0; i < fileArrListFiles.length; i++) {
                    if (fileArrListFiles[i].getAbsolutePath().startsWith(this.mDeviceRoot)) {
                        Log.d(SerialPortFinder.TAG, "Found new device: " + fileArrListFiles[i]);
                        this.mDevices.add(fileArrListFiles[i]);
                    }
                }
            }
            return this.mDevices;
        }

        private String getName() {
            return this.mDriverName;
        }
    }

    private Vector<Driver> getDrivers() throws IOException {
        if (this.mDrivers == null) {
            this.mDrivers = new Vector<>();
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader("/proc/tty/drivers"));
            while (true) {
                String line = lineNumberReader.readLine();
                if (line == null) {
                    break;
                }
                String strTrim = line.substring(0, 21).trim();
                String[] strArrSplit = line.split(" +");
                if (strArrSplit.length >= 5 && strArrSplit[strArrSplit.length - 1].equals("serial")) {
                    Log.d(TAG, "Found new driver " + strTrim + " on " + strArrSplit[strArrSplit.length - 4]);
                    this.mDrivers.add(new Driver(strTrim, strArrSplit[strArrSplit.length + (-4)]));
                }
            }
            lineNumberReader.close();
        }
        return this.mDrivers;
    }

    public String[] getAllDevicesPath() {
        Vector vector = new Vector();
        try {
            Iterator<Driver> it = getDrivers().iterator();
            while (it.hasNext()) {
                Iterator it2 = it.next().getDevices().iterator();
                while (it2.hasNext()) {
                    vector.add(((File) it2.next()).getAbsolutePath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String[]) vector.toArray(new String[vector.size()]);
    }
}
