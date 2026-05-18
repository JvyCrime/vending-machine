package com.fazecast.jSerialComm;

import java.util.EventObject;

/* JADX INFO: loaded from: classes.dex */
public final class SerialPortEvent extends EventObject {
    private static final long serialVersionUID = 3060830619653354150L;
    private final int eventType;
    private final byte[] serialData;

    public SerialPortEvent(SerialPort serialPort, int i) {
        super(serialPort);
        this.eventType = i;
        this.serialData = null;
    }

    public SerialPortEvent(SerialPort serialPort, int i, byte[] bArr) {
        super(serialPort);
        this.eventType = i;
        this.serialData = bArr;
    }

    public final SerialPort getSerialPort() {
        return (SerialPort) this.source;
    }

    public final int getEventType() {
        return this.eventType;
    }

    public final byte[] getReceivedData() {
        return this.serialData;
    }
}
