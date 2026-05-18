package com.fazecast.jSerialComm;

import java.io.InterruptedIOException;

/* JADX INFO: loaded from: classes.dex */
public final class SerialPortTimeoutException extends InterruptedIOException {
    private static final long serialVersionUID = 3209035213903386044L;

    public SerialPortTimeoutException() {
        this.bytesTransferred = 0;
    }

    public SerialPortTimeoutException(String str) {
        super(str);
        this.bytesTransferred = 0;
    }
}
