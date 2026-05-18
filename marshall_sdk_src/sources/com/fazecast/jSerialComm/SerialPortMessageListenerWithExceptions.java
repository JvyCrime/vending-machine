package com.fazecast.jSerialComm;

/* JADX INFO: loaded from: classes.dex */
public interface SerialPortMessageListenerWithExceptions extends SerialPortMessageListener {
    void catchException(Exception exc);
}
