package com.digitalmediavending.wallcoilmachine.inner;

import android_serialport_api.SerialPort;
import com.digitalmediavending.wallcoilmachine.utils.Constants;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DataSender implements Runnable {
    private final WallCoilMachine coilMachine;
    private final OutputStream outputStream;
    private boolean m_running = false;
    private boolean pause = false;
    List<Integer> messageToSend = new ArrayList();

    protected DataSender(SerialPort serialPort, WallCoilMachine wallCoilMachine) {
        this.coilMachine = wallCoilMachine;
        this.outputStream = serialPort.getOutputStream();
    }

    @Override // java.lang.Runnable
    public void run() {
        this.m_running = true;
        try {
            Thread.sleep(200L);
        } catch (InterruptedException e) {
            e.printStackTrace();
            this.coilMachine.m_event_if.causedException(e.getMessage());
        }
        while (this.m_running) {
            if (!this.pause && this.messageToSend.size() != 0) {
                write_to_uart(parseData(this.messageToSend));
                this.messageToSend.clear();
            }
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
                this.coilMachine.m_event_if.causedException(e2.getMessage());
            }
        }
    }

    protected void close() {
        this.m_running = false;
    }

    protected void pause() {
        this.pause = true;
    }

    protected void resume() {
        this.pause = false;
    }

    protected void write_to_uart(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        try {
            this.outputStream.write(bArr);
            this.coilMachine.m_event_if.onTxData(Arrays.toString(bArr));
        } catch (IOException e) {
            e.printStackTrace();
            this.coilMachine.m_event_if.causedException(e.getMessage());
        }
    }

    private byte[] parseData(List<Integer> list) {
        int size = list.size();
        byte[] bArr = new byte[size];
        for (int i = 0; i < size; i++) {
            bArr[i] = list.get(i).byteValue();
        }
        return bArr;
    }

    protected void triggerVending(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(170);
        arrayList.add(3);
        arrayList.add(1);
        arrayList.add(Integer.valueOf(i));
        arrayList.add(Integer.valueOf(i2));
        Iterator it = arrayList.iterator();
        int iIntValue = 0;
        while (it.hasNext()) {
            iIntValue += ((Integer) it.next()).intValue();
        }
        arrayList.add(Integer.valueOf(Constants.BinToDec(Constants.DecToBin(iIntValue, true))));
        this.messageToSend.addAll(arrayList);
    }

    protected void controlDoorLock(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(170);
        arrayList.add(3);
        arrayList.add(2);
        arrayList.add(Integer.valueOf(i));
        arrayList.add(Integer.valueOf(i2));
        Iterator it = arrayList.iterator();
        int iIntValue = 0;
        while (it.hasNext()) {
            iIntValue += ((Integer) it.next()).intValue();
        }
        arrayList.add(Integer.valueOf(Constants.BinToDec(Constants.DecToBin(iIntValue, true))));
        this.messageToSend.addAll(arrayList);
    }

    protected void scanTrays(Integer num) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(170);
        arrayList.add(2);
        arrayList.add(3);
        int iIntValue = 0;
        if (num == null) {
            num = 0;
        }
        arrayList.add(num);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            iIntValue += ((Integer) it.next()).intValue();
        }
        arrayList.add(Integer.valueOf(Constants.BinToDec(Constants.DecToBin(iIntValue, true))));
        this.messageToSend.addAll(arrayList);
    }

    protected void resetTrays(Integer num, Integer num2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(170);
        arrayList.add(3);
        arrayList.add(4);
        if (num == null) {
            num = 255;
        }
        arrayList.add(num);
        if (num2 == null) {
            num2 = 255;
        }
        arrayList.add(num2);
        int iIntValue = 0;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            iIntValue += ((Integer) it.next()).intValue();
        }
        arrayList.add(Integer.valueOf(Constants.BinToDec(Constants.DecToBin(iIntValue, true))));
        this.messageToSend.addAll(arrayList);
    }
}
