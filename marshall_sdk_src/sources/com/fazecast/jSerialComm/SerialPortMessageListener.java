package com.fazecast.jSerialComm;

/* JADX INFO: loaded from: classes.dex */
public interface SerialPortMessageListener extends SerialPortDataListener {
    boolean delimiterIndicatesEndOfMessage();

    byte[] getMessageDelimiter();
}
