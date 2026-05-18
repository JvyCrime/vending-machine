package com.fazecast.jSerialComm;

import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public final class SerialPortIOException extends IOException {
    private static final long serialVersionUID = 3353684802475494674L;

    public SerialPortIOException() {
    }

    public SerialPortIOException(String str) {
        super(str);
    }

    public SerialPortIOException(String str, Throwable th) {
        super(str, th);
    }

    public SerialPortIOException(Throwable th) {
        super(th);
    }
}
