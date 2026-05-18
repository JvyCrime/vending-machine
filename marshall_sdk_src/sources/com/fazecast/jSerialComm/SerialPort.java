package com.fazecast.jSerialComm;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class SerialPort {
    public static final int EVEN_PARITY = 2;
    public static final int FLOW_CONTROL_CTS_ENABLED = 16;
    public static final int FLOW_CONTROL_DISABLED = 0;
    public static final int FLOW_CONTROL_DSR_ENABLED = 256;
    public static final int FLOW_CONTROL_DTR_ENABLED = 4096;
    public static final int FLOW_CONTROL_RTS_ENABLED = 1;
    public static final int FLOW_CONTROL_XONXOFF_IN_ENABLED = 65536;
    public static final int FLOW_CONTROL_XONXOFF_OUT_ENABLED = 1048576;
    public static final int LISTENING_EVENT_DATA_AVAILABLE = 1;
    public static final int LISTENING_EVENT_DATA_RECEIVED = 16;
    public static final int LISTENING_EVENT_DATA_WRITTEN = 256;
    public static final int MARK_PARITY = 3;
    public static final int NO_PARITY = 0;
    public static final int ODD_PARITY = 1;
    public static final int ONE_POINT_FIVE_STOP_BITS = 2;
    public static final int ONE_STOP_BIT = 1;
    public static final int SPACE_PARITY = 4;
    public static final int TIMEOUT_NONBLOCKING = 0;
    public static final int TIMEOUT_READ_BLOCKING = 16;
    public static final int TIMEOUT_READ_SEMI_BLOCKING = 1;
    public static final int TIMEOUT_SCANNER = 4096;
    public static final int TIMEOUT_WRITE_BLOCKING = 256;
    public static final int TWO_STOP_BITS = 3;
    private static volatile boolean isAndroid = false;
    private static volatile boolean isUnixBased = false;
    private static volatile boolean isWindows = false;
    private static final String versionString = "2.7.0";
    private volatile String comPort;
    private volatile String friendlyName;
    private volatile String portDescription;
    private volatile long portHandle = -1;
    private volatile int baudRate = 9600;
    private volatile int dataBits = 8;
    private volatile int stopBits = 1;
    private volatile int parity = 0;
    private volatile int eventFlags = 0;
    private volatile int timeoutMode = 0;
    private volatile int readTimeout = 0;
    private volatile int writeTimeout = 0;
    private volatile int flowControl = 0;
    private volatile int sendDeviceQueueSize = 4096;
    private volatile int receiveDeviceQueueSize = 4096;
    private volatile int safetySleepTimeMS = 200;
    private volatile int rs485DelayBefore = 0;
    private volatile int rs485DelayAfter = 0;
    private volatile SerialPortDataListener userDataListener = null;
    private volatile SerialPortEventListener serialEventListener = null;
    private volatile boolean eventListenerRunning = false;
    private volatile boolean disableConfig = false;
    private volatile boolean rs485Mode = false;
    private volatile boolean rs485ActiveHigh = true;
    private volatile boolean isRtsEnabled = true;
    private volatile boolean isDtrEnabled = true;
    private SerialPortInputStream inputStream = null;
    private SerialPortOutputStream outputStream = null;

    /* JADX INFO: Access modifiers changed from: private */
    public final native int bytesAvailable(long j);

    private final native int bytesAwaitingWrite(long j);

    private final native boolean clearBreak(long j);

    private final native boolean clearDTR(long j);

    private final native boolean clearRTS(long j);

    private final native boolean closePortNative(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public final native boolean configEventFlags(long j);

    private final native boolean configPort(long j);

    private final native boolean configTimeouts(long j);

    private final native boolean getCTS(long j);

    public static native SerialPort[] getCommPorts();

    private final native boolean getDCD(long j);

    private final native boolean getDSR(long j);

    private final native boolean getDTR(long j);

    private final native boolean getRI(long j);

    private final native boolean getRTS(long j);

    public static String getVersion() {
        return versionString;
    }

    private static native void initializeLibrary();

    private final native long openPortNative();

    private final native boolean preclearDTR();

    private final native boolean preclearRTS();

    private final native boolean presetDTR();

    private final native boolean presetRTS();

    /* JADX INFO: Access modifiers changed from: private */
    public final native int readBytes(long j, byte[] bArr, long j2, long j3);

    private final native boolean setBreak(long j);

    private final native boolean setDTR(long j);

    private final native boolean setRTS(long j);

    private static native void uninitializeLibrary();

    /* JADX INFO: Access modifiers changed from: private */
    public final native int waitForEvent(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public final native int writeBytes(long j, byte[] bArr, long j2, long j3);

    static {
        String line;
        String str;
        String str2;
        String string;
        String str3;
        String str4;
        String lowerCase = System.getProperty("os.name").toLowerCase();
        String property = System.getProperty("java.io.tmpdir");
        if (property.charAt(property.length() - 1) != '\\' && property.charAt(property.length() - 1) != '/') {
            property = property + "/";
        }
        String str5 = property + "jSerialComm/";
        deleteDirectory(new File(str5));
        String str6 = "libjSerialComm.so";
        String str7 = "";
        if (System.getProperty("java.vm.vendor").toLowerCase().contains("android")) {
            try {
                Process processExec = Runtime.getRuntime().exec("getprop");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream()));
                do {
                    line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    if (line.contains("[ro.product.cpu.abi]:") || line.contains("[ro.product.cpu.abi2]:") || line.contains("[ro.product.cpu.abilist]:") || line.contains("[ro.product.cpu.abilist64]:")) {
                        break;
                    }
                } while (!line.contains("[ro.product.cpu.abilist32]:"));
                str7 = "Android/" + line.split(":")[1].trim().replace("[", "").replace("]", "").split(",")[0];
                processExec.waitFor();
                bufferedReader.close();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (str7.isEmpty()) {
                str7 = "Android/armeabi";
            }
            isAndroid = true;
        } else if (lowerCase.indexOf("win") >= 0) {
            str7 = System.getProperty("os.arch").indexOf("64") >= 0 ? "Windows/x86_64" : "Windows/x86";
            isWindows = true;
            str6 = "jSerialComm.dll";
        } else if (lowerCase.indexOf("mac") >= 0) {
            if (System.getProperty("os.arch").equals("aarch64")) {
                str4 = "OSX/aarch64";
            } else {
                str4 = System.getProperty("os.arch").indexOf("64") >= 0 ? "OSX/x86_64" : "OSX/x86";
            }
            str7 = str4;
            isUnixBased = true;
            str6 = "libjSerialComm.jnilib";
        } else if (lowerCase.indexOf("sunos") >= 0 || lowerCase.indexOf("solaris") >= 0) {
            if (System.getProperty("os.arch").indexOf("64") >= 0) {
                str = System.getProperty("os.arch").indexOf("sparc") >= 0 ? "Solaris/sparcv9_64" : "Solaris/x86_64";
            } else {
                str = System.getProperty("os.arch").indexOf("sparc") >= 0 ? "Solaris/sparcv8plus_32" : "Solaris/x86";
            }
            str7 = str;
            isUnixBased = true;
        } else if (lowerCase.indexOf("nix") >= 0 || lowerCase.indexOf("nux") >= 0 || lowerCase.indexOf("bsd") >= 0) {
            if (!System.getProperty("os.arch_full", "").isEmpty()) {
                string = "Linux/" + System.getProperty("os.arch_full").toLowerCase();
            } else if (System.getProperty("os.arch").indexOf("arm") >= 0) {
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/proc/cpuinfo"));
                    while (true) {
                        String line2 = bufferedReader2.readLine();
                        if (line2 != null) {
                            if (line2.contains("ARMv")) {
                                str3 = "Linux/armv" + line2.substring(line2.indexOf("ARMv") + 4, line2.indexOf("ARMv") + 5);
                                break;
                            }
                            if (line2.contains("ARM") && line2.contains("(v")) {
                                str3 = "Linux/armv" + line2.substring(line2.indexOf("(v") + 2, line2.indexOf("(v") + 3);
                                break;
                            }
                            if (line2.contains("aarch")) {
                                str7 = "Linux/armv8";
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    str7 = str3;
                    bufferedReader2.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (str7.isEmpty()) {
                    string = "Linux/armv6";
                } else if (str7.contains("Linux/armv8")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(str7);
                    if (System.getProperty("sun.arch.data.model") != null) {
                        str2 = "_" + System.getProperty("sun.arch.data.model");
                    } else {
                        str2 = System.getProperty("os.arch").indexOf("64") >= 0 ? "_64" : "_32";
                    }
                    sb.append(str2);
                    string = sb.toString();
                } else {
                    try {
                        if (new File("/lib/ld-linux-armhf.so.3").exists()) {
                            str7 = str7 + "-hf";
                        } else {
                            Process processStart = new ProcessBuilder("/bin/sh", "-c", "ls /lib/ld-linux*").start();
                            processStart.waitFor();
                            String line3 = new BufferedReader(new InputStreamReader(processStart.getInputStream())).readLine();
                            if (line3 != null && line3.contains("armhf")) {
                                str7 = str7 + "-hf";
                            } else {
                                Process processStart2 = new ProcessBuilder("/bin/sh", "-c", "ldd /usr/bin/ld | grep ld-").start();
                                processStart2.waitFor();
                                String line4 = new BufferedReader(new InputStreamReader(processStart2.getInputStream())).readLine();
                                if (line4 != null && line4.contains("armhf")) {
                                    str7 = str7 + "-hf";
                                }
                            }
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    isUnixBased = true;
                }
            } else if (System.getProperty("os.arch").indexOf("aarch32") >= 0) {
                string = "Linux/armv8_32";
            } else if (System.getProperty("os.arch").indexOf("aarch64") >= 0) {
                string = "Linux/armv8_64";
            } else if (System.getProperty("os.arch").indexOf("ppc64le") >= 0) {
                string = "Linux/ppc64le";
            } else {
                string = System.getProperty("os.arch").indexOf("64") >= 0 ? "Linux/x86_64" : "Linux/x86";
            }
            str7 = string;
            isUnixBased = true;
        } else {
            System.err.println("This operating system is not supported by the jSerialComm library.");
            System.exit(-1);
            str6 = "";
        }
        try {
            File file = new File(str5 + new Date().getTime() + "-" + str6);
            file.getParentFile().mkdirs();
            file.getParentFile().setReadable(true, false);
            file.getParentFile().setWritable(true, false);
            file.getParentFile().setExecutable(true, false);
            file.deleteOnExit();
            InputStream resourceAsStream = SerialPort.class.getResourceAsStream("/" + str7 + "/" + str6);
            if (resourceAsStream == null && isAndroid) {
                resourceAsStream = SerialPort.class.getResourceAsStream("/" + str7.replace("Android/", "lib/") + "/" + str6);
            }
            if (resourceAsStream == null) {
                System.err.println("Could not locate or access the native jSerialComm shared library.");
                System.err.println("If you are using multiple projects with interdependencies, you may need to fix your build settings to ensure that library resources are copied properly.");
                return;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[4096];
            while (true) {
                int i = resourceAsStream.read(bArr);
                if (i > 0) {
                    fileOutputStream.write(bArr, 0, i);
                } else {
                    fileOutputStream.close();
                    resourceAsStream.close();
                    file.setReadable(true, false);
                    file.setWritable(true, false);
                    file.setExecutable(true, false);
                    System.load(file.getAbsolutePath());
                    initializeLibrary();
                    return;
                }
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }

    private static boolean isSymbolicLink(File file) throws IOException {
        if (file.getParent() != null) {
            file = new File(file.getParentFile().getCanonicalFile(), file.getName());
        }
        return !file.getCanonicalFile().equals(file.getAbsoluteFile());
    }

    private static void deleteDirectory(File file) {
        if (file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                deleteDirectory(file2);
            }
        }
        file.delete();
    }

    public String toString() {
        return getPortDescription();
    }

    public static SerialPort getCommPort(String str) throws SerialPortInvalidPortException {
        try {
            if (str.startsWith("~" + File.separator)) {
                str = System.getProperty("user.home") + str.substring(1);
            }
            if (isWindows) {
                str = "\\\\.\\" + str.substring(str.lastIndexOf(92) + 1);
            } else if (isSymbolicLink(new File(str))) {
                str = new File(str).getCanonicalPath();
            } else if (!new File(str).exists()) {
                str = "/dev/" + str;
                if (!new File(str).exists()) {
                    str = "/dev/" + str.substring(str.lastIndexOf(47) + 1);
                }
                if (!new File(str).exists()) {
                    throw new IOException();
                }
            }
            SerialPort serialPort = new SerialPort();
            serialPort.comPort = str;
            serialPort.friendlyName = "User-Specified Port";
            serialPort.portDescription = "User-Specified Port";
            return serialPort;
        } catch (Exception e) {
            throw new SerialPortInvalidPortException("Unable to create a serial port object from the invalid port descriptor: " + str, e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:122:0x0180  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0097 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x002e A[Catch: all -> 0x0183, TryCatch #2 {, blocks: (B:3:0x0001, B:5:0x000f, B:8:0x0017, B:10:0x001b, B:13:0x0029, B:15:0x002e, B:19:0x003a, B:21:0x0040, B:31:0x0097, B:32:0x009a, B:33:0x00a1, B:34:0x00a8, B:35:0x00af, B:37:0x00b4, B:41:0x00be, B:45:0x00c4, B:49:0x00ca, B:52:0x00cf, B:90:0x0127, B:91:0x012a, B:92:0x0131, B:93:0x0138, B:94:0x013f, B:95:0x0142, B:96:0x0143, B:100:0x014d, B:104:0x0153, B:108:0x0159, B:111:0x015e, B:62:0x00e2, B:63:0x00e5, B:64:0x00ec, B:65:0x00f3, B:66:0x00fa, B:69:0x00ff, B:73:0x0109, B:77:0x010f, B:81:0x0115, B:84:0x011a, B:114:0x0167, B:116:0x0171, B:118:0x0175, B:119:0x017a, B:12:0x0022, B:24:0x0048, B:25:0x008c, B:58:0x00db), top: B:132:0x0001, inners: #0, #1, #3, #4, #5, #6, #7, #8, #9, #10, #11, #12, #13, #14, #15, #16, #17, #18 }] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0095 A[DONT_GENERATE] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final synchronized boolean openPort(int r7, int r8, int r9) {
        /*
            Method dump skipped, instruction units count: 390
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.fazecast.jSerialComm.SerialPort.openPort(int, int, int):boolean");
    }

    public final boolean openPort(int i) {
        return openPort(i, this.sendDeviceQueueSize, this.receiveDeviceQueueSize);
    }

    public final boolean openPort() {
        return openPort(200);
    }

    public final synchronized boolean closePort() {
        if (this.serialEventListener != null) {
            this.serialEventListener.stopListening();
        }
        closePortNative(this.portHandle);
        return this.portHandle <= 0;
    }

    public final synchronized boolean isOpen() {
        return this.portHandle > 0;
    }

    public final synchronized void disablePortConfiguration() {
        this.disableConfig = true;
    }

    public final int bytesAvailable() {
        return bytesAvailable(this.portHandle);
    }

    public final int bytesAwaitingWrite() {
        return bytesAwaitingWrite(this.portHandle);
    }

    public final int readBytes(byte[] bArr, long j) {
        return readBytes(this.portHandle, bArr, j, 0L);
    }

    public final int readBytes(byte[] bArr, long j, long j2) {
        return readBytes(this.portHandle, bArr, j, j2);
    }

    public final int writeBytes(byte[] bArr, long j) {
        return writeBytes(this.portHandle, bArr, j, 0L);
    }

    public final int writeBytes(byte[] bArr, long j, long j2) {
        return writeBytes(this.portHandle, bArr, j, j2);
    }

    public final int getDeviceWriteBufferSize() {
        return this.sendDeviceQueueSize;
    }

    public final int getDeviceReadBufferSize() {
        return this.receiveDeviceQueueSize;
    }

    public final boolean setBreak() {
        return setBreak(this.portHandle);
    }

    public final boolean clearBreak() {
        return clearBreak(this.portHandle);
    }

    public final boolean setRTS() {
        this.isRtsEnabled = true;
        return this.portHandle > 0 ? setRTS(this.portHandle) : presetRTS();
    }

    public final boolean clearRTS() {
        this.isRtsEnabled = false;
        return this.portHandle > 0 ? clearRTS(this.portHandle) : preclearRTS();
    }

    public final boolean setDTR() {
        this.isDtrEnabled = true;
        return this.portHandle > 0 ? setDTR(this.portHandle) : presetDTR();
    }

    public final boolean clearDTR() {
        this.isDtrEnabled = false;
        return this.portHandle > 0 ? clearDTR(this.portHandle) : preclearDTR();
    }

    public final boolean getCTS() {
        return getCTS(this.portHandle);
    }

    public final boolean getDSR() {
        return getDSR(this.portHandle);
    }

    public final boolean getDCD() {
        return getDCD(this.portHandle);
    }

    public final boolean getDTR() {
        return getDTR(this.portHandle);
    }

    public final boolean getRTS() {
        return getRTS(this.portHandle);
    }

    public final boolean getRI() {
        return getRI(this.portHandle);
    }

    private SerialPort() {
    }

    public final synchronized boolean addDataListener(SerialPortDataListener serialPortDataListener) {
        if (this.userDataListener != null) {
            return false;
        }
        this.userDataListener = serialPortDataListener;
        this.serialEventListener = this.userDataListener instanceof SerialPortPacketListener ? new SerialPortEventListener(((SerialPortPacketListener) this.userDataListener).getPacketSize()) : this.userDataListener instanceof SerialPortMessageListener ? new SerialPortEventListener(((SerialPortMessageListener) this.userDataListener).getMessageDelimiter(), ((SerialPortMessageListener) this.userDataListener).delimiterIndicatesEndOfMessage()) : new SerialPortEventListener();
        this.eventFlags = 0;
        if ((serialPortDataListener.getListeningEvents() & 1) > 0) {
            this.eventFlags |= 1;
        }
        if ((serialPortDataListener.getListeningEvents() & 16) > 0) {
            this.eventFlags |= 16;
        }
        if ((serialPortDataListener.getListeningEvents() & 256) > 0) {
            this.eventFlags |= 256;
        }
        if (this.portHandle > 0) {
            configEventFlags(this.portHandle);
            this.serialEventListener.startListening();
        }
        return true;
    }

    public final synchronized void removeDataListener() {
        this.eventFlags = 0;
        if (this.serialEventListener != null) {
            this.serialEventListener.stopListening();
            this.serialEventListener = null;
        }
        this.userDataListener = null;
    }

    public final InputStream getInputStream() {
        SerialPortInputStream serialPortInputStream = new SerialPortInputStream(false);
        this.inputStream = serialPortInputStream;
        return serialPortInputStream;
    }

    public final InputStream getInputStreamWithSuppressedTimeoutExceptions() {
        SerialPortInputStream serialPortInputStream = new SerialPortInputStream(true);
        this.inputStream = serialPortInputStream;
        return serialPortInputStream;
    }

    public final OutputStream getOutputStream() {
        SerialPortOutputStream serialPortOutputStream = new SerialPortOutputStream();
        this.outputStream = serialPortOutputStream;
        return serialPortOutputStream;
    }

    public final boolean setComPortParameters(int i, int i2, int i3, int i4) {
        return setComPortParameters(i, i2, i3, i4, this.rs485Mode);
    }

    public final synchronized boolean setComPortParameters(int i, int i2, int i3, int i4, boolean z) {
        this.baudRate = i;
        this.dataBits = i2;
        this.stopBits = i3;
        this.parity = i4;
        this.rs485Mode = z;
        if (this.portHandle <= 0) {
            return true;
        }
        if (this.safetySleepTimeMS > 0) {
            try {
                Thread.sleep(this.safetySleepTimeMS);
            } catch (Exception unused) {
                Thread.currentThread().interrupt();
            }
            return configPort(this.portHandle);
        }
        return configPort(this.portHandle);
    }

    public final synchronized boolean setComPortTimeouts(int i, int i2, int i3) {
        this.timeoutMode = i;
        if (isWindows) {
            this.readTimeout = i2;
            this.writeTimeout = i3;
        } else if (i2 > 0 && i2 <= 100) {
            this.readTimeout = 100;
        } else {
            this.readTimeout = Math.round(i2 / 100.0f) * 100;
        }
        if (this.portHandle <= 0) {
            return true;
        }
        if (this.safetySleepTimeMS > 0) {
            try {
                Thread.sleep(this.safetySleepTimeMS);
            } catch (Exception unused) {
                Thread.currentThread().interrupt();
            }
            return configTimeouts(this.portHandle);
        }
        return configTimeouts(this.portHandle);
    }

    public final synchronized boolean setBaudRate(int i) {
        this.baudRate = i;
        if (this.portHandle <= 0) {
            return true;
        }
        if (this.safetySleepTimeMS > 0) {
            try {
                Thread.sleep(this.safetySleepTimeMS);
            } catch (Exception unused) {
                Thread.currentThread().interrupt();
            }
            return configPort(this.portHandle);
        }
        return configPort(this.portHandle);
    }

    public final synchronized boolean setNumDataBits(int i) {
        this.dataBits = i;
        if (this.portHandle <= 0) {
            return true;
        }
        if (this.safetySleepTimeMS > 0) {
            try {
                Thread.sleep(this.safetySleepTimeMS);
            } catch (Exception unused) {
                Thread.currentThread().interrupt();
            }
            return configPort(this.portHandle);
        }
        return configPort(this.portHandle);
    }

    public final synchronized boolean setNumStopBits(int i) {
        this.stopBits = i;
        if (this.portHandle <= 0) {
            return true;
        }
        if (this.safetySleepTimeMS > 0) {
            try {
                Thread.sleep(this.safetySleepTimeMS);
            } catch (Exception unused) {
                Thread.currentThread().interrupt();
            }
            return configPort(this.portHandle);
        }
        return configPort(this.portHandle);
    }

    public final synchronized boolean setFlowControl(int i) {
        this.flowControl = i;
        if (this.portHandle <= 0) {
            return true;
        }
        if (this.safetySleepTimeMS > 0) {
            try {
                Thread.sleep(this.safetySleepTimeMS);
            } catch (Exception unused) {
                Thread.currentThread().interrupt();
            }
            return configPort(this.portHandle);
        }
        return configPort(this.portHandle);
    }

    public final synchronized boolean setParity(int i) {
        this.parity = i;
        if (this.portHandle <= 0) {
            return true;
        }
        if (this.safetySleepTimeMS > 0) {
            try {
                Thread.sleep(this.safetySleepTimeMS);
            } catch (Exception unused) {
                Thread.currentThread().interrupt();
            }
            return configPort(this.portHandle);
        }
        return configPort(this.portHandle);
    }

    public final synchronized boolean setRs485ModeParameters(boolean z, boolean z2, int i, int i2) {
        this.rs485Mode = z;
        this.rs485ActiveHigh = z2;
        this.rs485DelayBefore = i;
        this.rs485DelayAfter = i2;
        if (this.portHandle <= 0) {
            return true;
        }
        if (this.safetySleepTimeMS > 0) {
            try {
                Thread.sleep(this.safetySleepTimeMS);
            } catch (Exception unused) {
                Thread.currentThread().interrupt();
            }
            return configPort(this.portHandle);
        }
        return configPort(this.portHandle);
    }

    public final String getDescriptivePortName() {
        return this.friendlyName.trim();
    }

    public final String getSystemPortName() {
        String str;
        String str2;
        int i;
        if (isWindows) {
            str = this.comPort;
            str2 = this.comPort;
            i = 92;
        } else {
            str = this.comPort;
            str2 = this.comPort;
            i = 47;
        }
        return str.substring(str2.lastIndexOf(i) + 1);
    }

    public final String getPortDescription() {
        return this.portDescription.trim();
    }

    public final int getBaudRate() {
        return this.baudRate;
    }

    public final int getNumDataBits() {
        return this.dataBits;
    }

    public final int getNumStopBits() {
        return this.stopBits;
    }

    public final int getParity() {
        return this.parity;
    }

    public final int getReadTimeout() {
        return this.readTimeout;
    }

    public final int getWriteTimeout() {
        return this.writeTimeout;
    }

    public final int getFlowControlSettings() {
        return this.flowControl;
    }

    private final class SerialPortEventListener {
        private final byte[] dataPacket;
        private volatile int dataPacketIndex;
        private volatile int delimiterIndex;
        private final byte[] delimiters;
        private volatile ByteArrayOutputStream messageBytes;
        private final boolean messageEndIsDelimited;
        private Thread serialEventThread;

        public SerialPortEventListener() {
            this.messageBytes = new ByteArrayOutputStream();
            this.dataPacketIndex = 0;
            this.delimiterIndex = 0;
            this.serialEventThread = null;
            this.dataPacket = new byte[0];
            this.delimiters = new byte[0];
            this.messageEndIsDelimited = true;
        }

        public SerialPortEventListener(int i) {
            this.messageBytes = new ByteArrayOutputStream();
            this.dataPacketIndex = 0;
            this.delimiterIndex = 0;
            this.serialEventThread = null;
            this.dataPacket = new byte[i];
            this.delimiters = new byte[0];
            this.messageEndIsDelimited = true;
        }

        public SerialPortEventListener(byte[] bArr, boolean z) {
            this.messageBytes = new ByteArrayOutputStream();
            this.dataPacketIndex = 0;
            this.delimiterIndex = 0;
            this.serialEventThread = null;
            this.dataPacket = new byte[0];
            this.delimiters = bArr;
            this.messageEndIsDelimited = z;
        }

        public final void startListening() {
            if (SerialPort.this.eventListenerRunning) {
                return;
            }
            SerialPort.this.eventListenerRunning = true;
            this.dataPacketIndex = 0;
            Thread thread = new Thread(new Runnable() { // from class: com.fazecast.jSerialComm.SerialPort.SerialPortEventListener.1
                @Override // java.lang.Runnable
                public void run() {
                    while (SerialPort.this.eventListenerRunning && SerialPort.this.portHandle > 0) {
                        try {
                            SerialPortEventListener.this.waitForSerialEvent();
                        } catch (Exception e) {
                            SerialPort.this.eventListenerRunning = false;
                            if (SerialPort.this.userDataListener instanceof SerialPortDataListenerWithExceptions) {
                                ((SerialPortDataListenerWithExceptions) SerialPort.this.userDataListener).catchException(e);
                            } else if (SerialPort.this.userDataListener instanceof SerialPortMessageListenerWithExceptions) {
                                ((SerialPortMessageListenerWithExceptions) SerialPort.this.userDataListener).catchException(e);
                            }
                        }
                    }
                    SerialPort.this.eventListenerRunning = false;
                }
            });
            this.serialEventThread = thread;
            thread.start();
        }

        public final void stopListening() {
            if (SerialPort.this.eventListenerRunning) {
                SerialPort.this.eventListenerRunning = false;
                int i = SerialPort.this.eventFlags;
                SerialPort.this.eventFlags = 0;
                SerialPort serialPort = SerialPort.this;
                serialPort.configEventFlags(serialPort.portHandle);
                SerialPort.this.eventFlags = i;
                try {
                    this.serialEventThread.join(500L);
                    if (this.serialEventThread.isAlive()) {
                        this.serialEventThread.interrupt();
                    }
                    this.serialEventThread.join();
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                }
                this.serialEventThread = null;
            }
        }

        public final void waitForSerialEvent() throws Exception {
            SerialPort serialPort = SerialPort.this;
            int iWaitForEvent = serialPort.waitForEvent(serialPort.portHandle);
            if (iWaitForEvent != 1) {
                if (iWaitForEvent == 256 && (SerialPort.this.eventFlags & 256) > 0) {
                    SerialPort.this.userDataListener.serialEvent(new SerialPortEvent(SerialPort.this, 256));
                    return;
                }
                return;
            }
            if ((SerialPort.this.eventFlags & 16) > 0) {
                while (SerialPort.this.eventListenerRunning) {
                    SerialPort serialPort2 = SerialPort.this;
                    int iBytesAvailable = serialPort2.bytesAvailable(serialPort2.portHandle);
                    if (iBytesAvailable <= 0) {
                        return;
                    }
                    byte[] bArr = new byte[iBytesAvailable];
                    SerialPort serialPort3 = SerialPort.this;
                    int bytes = serialPort3.readBytes(serialPort3.portHandle, bArr, iBytesAvailable, 0L);
                    if (this.delimiters.length > 0) {
                        int i = 0;
                        for (int i2 = 0; i2 < bytes; i2++) {
                            if (bArr[i2] == this.delimiters[this.delimiterIndex]) {
                                int i3 = this.delimiterIndex + 1;
                                this.delimiterIndex = i3;
                                if (i3 == this.delimiters.length) {
                                    int i4 = i2 + 1;
                                    this.messageBytes.write(bArr, i, i4 - i);
                                    byte[] byteArray = this.messageEndIsDelimited ? this.messageBytes.toByteArray() : Arrays.copyOf(this.messageBytes.toByteArray(), this.messageBytes.size() - this.delimiters.length);
                                    if (byteArray.length > 0 && (this.messageEndIsDelimited || this.delimiters[0] == byteArray[0])) {
                                        SerialPort.this.userDataListener.serialEvent(new SerialPortEvent(SerialPort.this, 16, byteArray));
                                    }
                                    this.messageBytes.reset();
                                    this.delimiterIndex = 0;
                                    if (!this.messageEndIsDelimited) {
                                        ByteArrayOutputStream byteArrayOutputStream = this.messageBytes;
                                        byte[] bArr2 = this.delimiters;
                                        byteArrayOutputStream.write(bArr2, 0, bArr2.length);
                                    }
                                    i = i4;
                                }
                            } else if (this.delimiterIndex != 0) {
                                this.delimiterIndex = bArr[i2] == this.delimiters[0] ? 1 : 0;
                            }
                        }
                        this.messageBytes.write(bArr, i, bytes - i);
                    } else if (this.dataPacket.length == 0) {
                        SerialPort.this.userDataListener.serialEvent(new SerialPortEvent(SerialPort.this, 16, (byte[]) bArr.clone()));
                    } else {
                        int length = 0;
                        while (bytes >= this.dataPacket.length - this.dataPacketIndex) {
                            System.arraycopy(bArr, length, this.dataPacket, this.dataPacketIndex, this.dataPacket.length - this.dataPacketIndex);
                            bytes -= this.dataPacket.length - this.dataPacketIndex;
                            length += this.dataPacket.length - this.dataPacketIndex;
                            this.dataPacketIndex = 0;
                            SerialPort.this.userDataListener.serialEvent(new SerialPortEvent(SerialPort.this, 16, (byte[]) this.dataPacket.clone()));
                        }
                        if (bytes > 0) {
                            System.arraycopy(bArr, length, this.dataPacket, this.dataPacketIndex, bytes);
                            this.dataPacketIndex += bytes;
                        }
                    }
                }
                return;
            }
            if ((SerialPort.this.eventFlags & 1) > 0) {
                SerialPort.this.userDataListener.serialEvent(new SerialPortEvent(SerialPort.this, 1));
            }
        }
    }

    private final class SerialPortInputStream extends InputStream {
        private byte[] byteBuffer = new byte[1];
        private final boolean timeoutExceptionsSuppressed;

        public SerialPortInputStream(boolean z) {
            this.timeoutExceptionsSuppressed = z;
        }

        @Override // java.io.InputStream
        public final int available() throws SerialPortIOException {
            if (SerialPort.this.portHandle <= 0) {
                throw new SerialPortIOException("This port appears to have been shutdown or disconnected.");
            }
            SerialPort serialPort = SerialPort.this;
            return serialPort.bytesAvailable(serialPort.portHandle);
        }

        @Override // java.io.InputStream
        public final int read() throws SerialPortIOException, SerialPortTimeoutException {
            if (SerialPort.this.portHandle <= 0) {
                throw new SerialPortIOException("This port appears to have been shutdown or disconnected.");
            }
            SerialPort serialPort = SerialPort.this;
            int bytes = serialPort.readBytes(serialPort.portHandle, this.byteBuffer, 1L, 0L);
            if (bytes == 0) {
                if (this.timeoutExceptionsSuppressed) {
                    return -1;
                }
                throw new SerialPortTimeoutException("The read operation timed out before any data was returned.");
            }
            if (bytes < 0) {
                return -1;
            }
            return this.byteBuffer[0] & 255;
        }

        @Override // java.io.InputStream
        public final int read(byte[] bArr) throws SerialPortIOException, SerialPortTimeoutException, NullPointerException {
            Objects.requireNonNull(bArr, "A null pointer was passed in for the read buffer.");
            if (SerialPort.this.portHandle <= 0) {
                throw new SerialPortIOException("This port appears to have been shutdown or disconnected.");
            }
            if (bArr.length == 0) {
                return 0;
            }
            SerialPort serialPort = SerialPort.this;
            int bytes = serialPort.readBytes(serialPort.portHandle, bArr, bArr.length, 0L);
            if (bytes != 0 || this.timeoutExceptionsSuppressed) {
                return bytes;
            }
            throw new SerialPortTimeoutException("The read operation timed out before any data was returned.");
        }

        @Override // java.io.InputStream
        public final int read(byte[] bArr, int i, int i2) throws SerialPortIOException, IndexOutOfBoundsException, SerialPortTimeoutException, NullPointerException {
            Objects.requireNonNull(bArr, "A null pointer was passed in for the read buffer.");
            if (i2 < 0 || i < 0 || i2 > bArr.length - i) {
                throw new IndexOutOfBoundsException("The specified read offset plus length extends past the end of the specified buffer.");
            }
            if (SerialPort.this.portHandle <= 0) {
                throw new SerialPortIOException("This port appears to have been shutdown or disconnected.");
            }
            if (bArr.length == 0 || i2 == 0) {
                return 0;
            }
            SerialPort serialPort = SerialPort.this;
            int bytes = serialPort.readBytes(serialPort.portHandle, bArr, i2, i);
            if (bytes != 0 || this.timeoutExceptionsSuppressed) {
                return bytes;
            }
            throw new SerialPortTimeoutException("The read operation timed out before any data was returned.");
        }

        @Override // java.io.InputStream
        public final long skip(long j) throws SerialPortIOException {
            if (SerialPort.this.portHandle <= 0) {
                throw new SerialPortIOException("This port appears to have been shutdown or disconnected.");
            }
            SerialPort serialPort = SerialPort.this;
            return serialPort.readBytes(serialPort.portHandle, new byte[(int) j], j, 0L);
        }
    }

    private final class SerialPortOutputStream extends OutputStream {
        private byte[] byteBuffer = new byte[1];

        public SerialPortOutputStream() {
        }

        @Override // java.io.OutputStream
        public final void write(int i) throws SerialPortIOException, SerialPortTimeoutException {
            if (SerialPort.this.portHandle <= 0) {
                throw new SerialPortIOException("This port appears to have been shutdown or disconnected.");
            }
            this.byteBuffer[0] = (byte) (i & 255);
            SerialPort serialPort = SerialPort.this;
            int iWriteBytes = serialPort.writeBytes(serialPort.portHandle, this.byteBuffer, 1L, 0L);
            if (iWriteBytes < 0) {
                throw new SerialPortIOException("No bytes written. This port appears to have been shutdown or disconnected.");
            }
            if (iWriteBytes == 0) {
                throw new SerialPortTimeoutException("The write operation timed out before all data was written.");
            }
        }

        @Override // java.io.OutputStream
        public final void write(byte[] bArr) throws SerialPortIOException, SerialPortTimeoutException, NullPointerException {
            write(bArr, 0, bArr.length);
        }

        @Override // java.io.OutputStream
        public final void write(byte[] bArr, int i, int i2) throws SerialPortIOException, IndexOutOfBoundsException, SerialPortTimeoutException, NullPointerException {
            Objects.requireNonNull(bArr, "A null pointer was passed in for the write buffer.");
            if (i2 < 0 || i < 0 || i + i2 > bArr.length) {
                throw new IndexOutOfBoundsException("The specified write offset plus length extends past the end of the specified buffer.");
            }
            if (SerialPort.this.portHandle <= 0) {
                throw new SerialPortIOException("This port appears to have been shutdown or disconnected.");
            }
            if (i2 == 0) {
                return;
            }
            SerialPort serialPort = SerialPort.this;
            int iWriteBytes = serialPort.writeBytes(serialPort.portHandle, bArr, i2, i);
            if (iWriteBytes < 0) {
                throw new SerialPortIOException("No bytes written. This port appears to have been shutdown or disconnected.");
            }
            if (iWriteBytes == 0) {
                throw new SerialPortTimeoutException("The write operation timed out before all data was written.");
            }
        }
    }
}
