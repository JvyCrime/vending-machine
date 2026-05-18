package com.digitalmediavending.wallcoilmachine.inner;

import android_serialport_api.SerialPort;
import com.digitalmediavending.wallcoilmachine.utils.Constants;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DataReceiver implements Runnable {
    private final WallCoilMachine coilMachine;
    private final SerialPort m_Port;
    private boolean m_running = true;
    private final DataParser parser = new DataParser();

    protected DataReceiver(SerialPort serialPort, WallCoilMachine wallCoilMachine) {
        this.m_Port = serialPort;
        this.coilMachine = wallCoilMachine;
    }

    protected void close() {
        this.m_running = false;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean z;
        ArrayList arrayList = new ArrayList();
        InputStream inputStream = this.m_Port.getInputStream();
        while (this.m_running) {
            try {
                if (inputStream.available() > 0) {
                    int i = inputStream.read();
                    boolean z2 = true;
                    if (i == 170) {
                        arrayList.clear();
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        arrayList.add(Integer.valueOf(i));
                    } else {
                        int iIntValue = 0;
                        for (int i2 = 0; i2 < arrayList.size(); i2++) {
                            iIntValue += arrayList.get(i2).intValue();
                        }
                        if (Constants.BinToDec(Constants.DecToBin(iIntValue, true)) != i) {
                            z2 = false;
                        }
                        arrayList.add(Integer.valueOf(i));
                        if (z2) {
                            formatDataParserUART(arrayList);
                            arrayList.clear();
                        }
                    }
                } else {
                    Thread.sleep(5L);
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.coilMachine.m_event_if.causedException(e.getMessage());
            }
        }
    }

    private boolean formatDataParserUART(List<Integer> list) {
        this.coilMachine.m_event_if.onRxData(list.toString());
        if (list.size() >= 5) {
            int iIntValue = list.get(2).intValue();
            if (iIntValue == 0) {
                this.coilMachine.m_event_if.onHeartBeatReceived(this.parser.parseHeatBeatData(list));
            } else if (iIntValue == 1) {
                this.coilMachine.m_event_if.onVendingResponseReceived(this.parser.parseVendingData(list));
            } else if (iIntValue == 2) {
                this.coilMachine.m_event_if.onDoorResponse(this.parser.parseDoorLockData(list));
            } else if (iIntValue == 3) {
                this.coilMachine.m_event_if.onScanTrays(this.parser.parseScanTrayData(list));
            } else if (iIntValue == 4) {
                this.coilMachine.m_event_if.onResetTrays(this.parser.parseResetTrayData(list));
            } else {
                this.coilMachine.m_event_if.unableToParse(list.toString());
                return false;
            }
            return true;
        }
        this.coilMachine.m_event_if.unableToParse(list.toString());
        return false;
    }
}
