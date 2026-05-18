package com.fazecast.jSerialComm;

/* JADX INFO: loaded from: classes.dex */
public final class SerialPortInvalidPortException extends RuntimeException {
    private static final long serialVersionUID = 3420177672598538224L;

    public SerialPortInvalidPortException() {
    }

    public SerialPortInvalidPortException(String str) {
        super(str);
    }

    public SerialPortInvalidPortException(String str, Throwable th) {
        super(str, th);
    }

    public SerialPortInvalidPortException(Throwable th) {
        super(th);
    }
}
