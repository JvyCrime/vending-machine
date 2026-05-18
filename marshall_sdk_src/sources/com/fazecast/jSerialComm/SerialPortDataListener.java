package com.fazecast.jSerialComm;

import java.util.EventListener;

/* JADX INFO: loaded from: classes.dex */
public interface SerialPortDataListener extends EventListener {
    int getListeningEvents();

    void serialEvent(SerialPortEvent serialPortEvent);
}
