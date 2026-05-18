package com.clm.lyflib.lyf3_0;

import android.content.Context;
import android.util.Log;
import android_serialport_api.SerialPort;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class VendingMachine {
    protected Context ctx;
    public SerialPort m_Port;
    protected VendingMachineInterface m_event_if;
    public MachineInfo mi;
    public MachineState ms;
    private receiver rcver;
    private sender sender;
    public const_value value;
    public VendoutInfo vi;

    public void request_vendout(List<Channel> chl) {
        this.sender.request_vendout(chl);
    }

    public void request_light_ctrl(int which, int action) {
        this.sender.request_light_ctrl(which, action);
    }

    public void request_digoutput_ctrl(int which, boolean on) {
        this.sender.request_digoutput_ctrl(which, on);
    }

    public void request_motor_ctrl(int which, int action, int value) {
        this.sender.request_motor_ctrl(which, action, value);
    }

    public void request_fault_clear() {
        this.sender.request_fault_clear();
    }

    public void request_channel_scan() {
        this.sender.request_channel_scan();
    }

    public void request_channel_reset(int action, Channel ch) {
        this.sender.request_channel_reset(action, ch);
    }

    public void request_system_reset() {
        this.sender.request_system_reset();
    }

    public void request_ac_ctrl(int action) {
        this.sender.request_ac_ctrl(action);
    }

    public void request_ac_cfg(ArrayList<Integer> cfg) {
        this.sender.request_ac_cfg(cfg);
    }

    public void request_channel_xy_cfg(int row, List<Channel> chl) {
        this.sender.request_channel_xy_cfg(row, chl);
    }

    public void request_channel_type_cfg(int row, List<Channel> chl) {
        this.sender.request_channel_type_cfg(row, chl);
    }

    public void request_get_dev_info() {
        this.sender.request_get_dev_info();
    }

    public void request_get_ac_info() {
        this.sender.request_get_ac_info();
    }

    public void request_get_channel_xy(int row) {
        this.sender.request_get_channel_xy(row);
    }

    public void request_get_channel_type(int row) {
        this.sender.request_get_channel_type(row);
    }

    public void send_pause() {
        this.sender.pause();
    }

    public void send_resume() {
        this.sender.resume();
    }

    public boolean load_ch_type_from_cfg_file(String s) {
        if (!check_ch_type_file(s, new String[]{this.value.GetChType(0), this.value.GetChType(1), this.value.GetChType(2)})) {
            return false;
        }
        String[] strArrSplit = s.split("\n");
        for (int i = 0; i < strArrSplit.length; i++) {
            String[] strArrSplit2 = strArrSplit[i].split("\\|");
            for (int i2 = 0; i2 < strArrSplit2.length; i2++) {
                this.mi.getch2(i, i2).type = this.value.GetChTypeIndex(strArrSplit2[i2]);
            }
            this.m_event_if.onGetChannelType(i, this.mi);
        }
        return true;
    }

    public String generate_ch_type_cfg_file() {
        String str = "";
        for (int i = 0; i < 10; i++) {
            for (int i2 = 0; i2 < 20; i2++) {
                str = str + this.value.GetChType(this.mi.getch2(i, i2).type) + "|";
            }
            str = str + "\n";
        }
        return str;
    }

    public boolean load_ch_xy_from_cfg_file(String s) {
        if (!check_ch_xy_file(s)) {
            return false;
        }
        String[] strArrSplit = s.split("\n");
        for (int i = 0; i < strArrSplit.length; i++) {
            String[] strArrSplit2 = strArrSplit[i].split("\\|");
            for (int i2 = 0; i2 < strArrSplit2.length; i2++) {
                String[] strArrSplit3 = strArrSplit2[i2].split(",");
                this.mi.getch2(i, i2).x = (short) Integer.parseInt(strArrSplit3[0]);
                this.mi.getch2(i, i2).y = (short) Integer.parseInt(strArrSplit3[1]);
            }
            this.m_event_if.onGetChannelXy(i, this.mi);
        }
        return true;
    }

    public String generate_ch_xy_cfg_file() {
        String str = "";
        for (int i = 0; i < 10; i++) {
            for (int i2 = 0; i2 < 20; i2++) {
                str = str + ((int) this.mi.getch2(i, i2).x) + "," + ((int) this.mi.getch2(i, i2).y) + "|";
            }
            str = str + "\n";
        }
        return str;
    }

    private void SetEventHandler(VendingMachineInterface event) {
        this.m_event_if = event;
    }

    public void start(String portName, VendingMachineInterface sif) {
        try {
            this.m_Port = new SerialPort(new File(portName), 115200, 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SecurityException e2) {
            e2.printStackTrace();
        }
        this.m_event_if = sif;
        this.ms = new MachineState();
        this.mi = new MachineInfo();
        this.vi = new VendoutInfo();
        this.rcver = new receiver(this.m_Port, this);
        this.sender = new sender(this.m_Port, this);
        new Thread(this.rcver).start();
        new Thread(this.sender).start();
    }

    public VendingMachine(Context c) {
        this.ctx = c;
        this.value = new const_value(c);
    }

    public void start(SerialPort sp, VendingMachineInterface sif) {
        this.m_Port = sp;
        this.m_event_if = sif;
        this.ms = new MachineState();
        this.mi = new MachineInfo();
        this.vi = new VendoutInfo();
        this.rcver = new receiver(this.m_Port, this);
        this.sender = new sender(this.m_Port, this);
        new Thread(this.rcver).start();
        new Thread(this.sender).start();
    }

    public void Close() {
        this.rcver.close();
        this.sender.close();
        log("close");
        this.m_Port.close();
    }

    protected void write_ack(int cmd) {
        sender senderVar = this.sender;
        senderVar.write_to_uart(senderVar.make_ack(cmd));
    }

    protected void try_last_cmd() {
        this.sender.try_last_cmd();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean check_ch_type_file(java.lang.String r9, java.lang.String[] r10) {
        /*
            r8 = this;
            r0 = 0
            if (r9 == 0) goto L3e
            java.lang.String r1 = "\n"
            java.lang.String[] r9 = r9.split(r1)
            int r1 = r9.length
            r2 = 10
            if (r1 != r2) goto L3e
            r1 = 0
            r2 = 0
        L10:
            int r3 = r9.length
            if (r1 >= r3) goto L3f
            r3 = r9[r1]
            java.lang.String r4 = "\\|"
            java.lang.String[] r3 = r3.split(r4)
            int r4 = r3.length
            r5 = 20
            if (r4 != r5) goto L3b
            r4 = 0
        L21:
            int r5 = r3.length
            if (r4 >= r5) goto L3b
            r5 = 0
        L25:
            int r6 = r10.length
            if (r5 >= r6) goto L38
            r6 = r3[r4]
            r7 = r10[r5]
            boolean r6 = r6.equals(r7)
            if (r6 == 0) goto L35
            int r2 = r2 + 1
            goto L38
        L35:
            int r5 = r5 + 1
            goto L25
        L38:
            int r4 = r4 + 1
            goto L21
        L3b:
            int r1 = r1 + 1
            goto L10
        L3e:
            r2 = 0
        L3f:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "check_file,pass="
            r9.append(r10)
            r9.append(r2)
            java.lang.String r9 = r9.toString()
            java.lang.String r10 = "clm"
            android.util.Log.e(r10, r9)
            r9 = 200(0xc8, float:2.8E-43)
            if (r2 != r9) goto L5b
            r9 = 1
            return r9
        L5b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.clm.lyflib.lyf3_0.VendingMachine.check_ch_type_file(java.lang.String, java.lang.String[]):boolean");
    }

    private boolean check_ch_xy_file(String s) {
        if (s != null) {
            String[] strArrSplit = s.split("\n");
            if (strArrSplit.length == 10) {
                for (String str : strArrSplit) {
                    String[] strArrSplit2 = str.split("\\|");
                    if (strArrSplit2.length == 20) {
                        for (String str2 : strArrSplit2) {
                            String[] strArrSplit3 = str2.split(",");
                            if (strArrSplit3.length == 2) {
                                try {
                                    Integer.parseInt(strArrSplit3[0]);
                                    Integer.parseInt(strArrSplit3[1]);
                                } catch (NumberFormatException unused) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private void log(String s) {
        Log.e("LYF", s);
    }
}
