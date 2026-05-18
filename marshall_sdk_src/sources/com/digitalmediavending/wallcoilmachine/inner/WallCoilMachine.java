package com.digitalmediavending.wallcoilmachine.inner;

import android.content.Context;
import android_serialport_api.SerialPort;
import com.digitalmediavending.wallcoilmachine.interfaces.EventHandlerInterface;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class WallCoilMachine {
    protected Context ctx;
    public SerialPort m_Port;
    protected EventHandlerInterface m_event_if;
    public DataReceiver serialDataReceiver;
    public DataSender serialDataSender;

    public WallCoilMachine(Context context) {
        this.ctx = context;
    }

    public void start(String str, EventHandlerInterface eventHandlerInterface) {
        try {
            SerialPort serialPort = new SerialPort(new File(str), 115200, 0);
            this.m_Port = serialPort;
            this.m_event_if = eventHandlerInterface;
            this.serialDataSender = new DataSender(serialPort, this);
            new Thread(this.serialDataSender).start();
            this.serialDataReceiver = new DataReceiver(this.m_Port, this);
            new Thread(this.serialDataReceiver).start();
        } catch (Exception e) {
            e.printStackTrace();
            this.m_event_if.causedException(e.getMessage());
        }
    }

    public void triggerVending(int i, int i2) {
        this.serialDataSender.triggerVending(i, i2);
    }

    public void controlDoorLock(int i, int i2) {
        this.serialDataSender.controlDoorLock(i, i2);
    }

    public void scanTrays(Integer num) {
        this.serialDataSender.scanTrays(num);
    }

    public void resetTrays(int i, int i2) {
        this.serialDataSender.resetTrays(Integer.valueOf(i), Integer.valueOf(i2));
    }
}
