package com.clm.lyflib.lyf3_0;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android_serialport_api.SerialPort;
import androidx.core.view.MotionEventCompat;
import com.bitmick.marshall.vmc.marshall_t;
import com.clm.lyflib.R;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class sender implements Runnable {
    private cmd_to_send last_cmd;
    private SerialPort m_Port;
    private VendingMachine machine;
    private OutputStream outputStream;
    private boolean m_running = false;
    private List<cmd_to_send> to_send = new ArrayList();
    private Handler handler = new Handler();
    private boolean pause = false;

    protected sender(SerialPort p, VendingMachine m) {
        this.m_Port = p;
        this.machine = m;
        this.outputStream = p.getOutputStream();
    }

    protected void close() {
        this.m_running = false;
    }

    @Override // java.lang.Runnable
    public void run() {
        log("write thread run");
        this.m_running = true;
        request_get_dev_info();
        try {
            Thread.sleep(200L);
        } catch (InterruptedException unused) {
            log("lyf sleep error");
        }
        request_get_ac_info();
        for (int i = 0; i < 10; i++) {
            request_get_channel_xy(i);
            request_get_channel_type(i);
        }
        while (this.m_running) {
            if (!this.pause && this.to_send.size() != 0) {
                make_packet_and_send(this.to_send.get(0));
                this.last_cmd = this.to_send.remove(0);
            }
            try {
                Thread.sleep(50L);
            } catch (InterruptedException unused2) {
                log("lyf sleep error");
            }
        }
    }

    protected void try_last_cmd() {
        if (this.last_cmd.cmd == 1) {
            this.handler.postDelayed(new Runnable() { // from class: com.clm.lyflib.lyf3_0.sender.1
                @Override // java.lang.Runnable
                public void run() {
                    sender.this.to_send.add(sender.this.last_cmd);
                }
            }, 1000L);
        }
    }

    protected void pause() {
        this.pause = true;
    }

    protected void resume() {
        this.pause = false;
    }

    protected void request_vendout(List<Channel> chl) {
        this.to_send.add(new cmd_to_send(1, chl));
    }

    protected void request_light_ctrl(int which, int action) {
        this.to_send.add(new cmd_to_send(2, which, action));
    }

    protected void request_digoutput_ctrl(int which, boolean on) {
        this.to_send.add(new cmd_to_send(3, which, !on ? 0 : 1));
    }

    protected void request_motor_ctrl(int which, int action, int value) {
        this.to_send.add(new cmd_to_send(4, which, action, value));
    }

    protected void request_fault_clear() {
        this.to_send.add(new cmd_to_send(9));
    }

    protected void request_channel_scan() {
        this.to_send.add(new cmd_to_send(5));
    }

    protected void request_channel_reset(int action, Channel ch) {
        this.to_send.add(new cmd_to_send(6, action, ch));
    }

    protected void request_system_reset() {
        this.to_send.add(new cmd_to_send(7));
    }

    protected void request_ac_ctrl(int action) {
        this.to_send.add(new cmd_to_send(8, action));
    }

    protected void request_ac_cfg(ArrayList<Integer> cfg) {
        this.to_send.add(new cmd_to_send(16, cfg));
    }

    protected void request_channel_xy_cfg(int row, List<Channel> chl) {
        this.to_send.add(new cmd_to_send(17, row, chl));
    }

    protected void request_channel_type_cfg(int row, List<Channel> chl) {
        this.to_send.add(new cmd_to_send(18, row, chl));
    }

    protected void request_get_dev_info() {
        this.to_send.add(new cmd_to_send(32));
    }

    protected void request_get_ac_info() {
        this.to_send.add(new cmd_to_send(33));
    }

    protected void request_get_channel_xy(int row) {
        this.to_send.add(new cmd_to_send(34, row));
    }

    protected void request_get_channel_type(int row) {
        this.to_send.add(new cmd_to_send(35, row));
    }

    private class cmd_to_send {
        private Object arg1;
        private Object arg2;
        private Object arg3;
        int cmd;

        cmd_to_send(int c, int select, int action, int value) {
            this.cmd = c;
            this.arg1 = Integer.valueOf(select);
            this.arg2 = Integer.valueOf(action);
            this.arg3 = Integer.valueOf(value);
        }

        cmd_to_send(int c, int select, int action) {
            this.cmd = c;
            this.arg1 = Integer.valueOf(select);
            this.arg2 = Integer.valueOf(action);
        }

        cmd_to_send(int c, int action, Channel ch) {
            this.cmd = c;
            this.arg1 = Integer.valueOf(action);
            this.arg2 = ch;
        }

        cmd_to_send(int c, int action) {
            this.cmd = c;
            this.arg1 = Integer.valueOf(action);
        }

        cmd_to_send(int c) {
            this.cmd = c;
        }

        cmd_to_send(int c, List<Channel> chs) {
            this.cmd = c;
            this.arg1 = chs;
        }

        cmd_to_send(int c, ArrayList<Integer> chs) {
            this.cmd = c;
            this.arg1 = chs;
        }

        cmd_to_send(int c, int row, List<Channel> chs) {
            this.cmd = c;
            this.arg1 = Integer.valueOf(row);
            this.arg2 = chs;
        }
    }

    private void make_packet_and_send(cmd_to_send c) {
        byte[] bArrMake_vendout_ctrl_request;
        int i = c.cmd;
        switch (i) {
            case 1:
                bArrMake_vendout_ctrl_request = make_vendout_ctrl_request(c);
                break;
            case 2:
                bArrMake_vendout_ctrl_request = make_light_ctrl_request(c);
                break;
            case 3:
                bArrMake_vendout_ctrl_request = make_digoutput_ctrl_request(c);
                break;
            case 4:
                bArrMake_vendout_ctrl_request = make_moter_ctrl_request(c);
                break;
            case 5:
                bArrMake_vendout_ctrl_request = make_channel_scan_request(c);
                break;
            case 6:
                bArrMake_vendout_ctrl_request = make_channel_reset_request(c);
                break;
            case 7:
                bArrMake_vendout_ctrl_request = make_system_reset_request(c);
                break;
            case 8:
                bArrMake_vendout_ctrl_request = make_ac_ctrl_request(c);
                break;
            case 9:
                bArrMake_vendout_ctrl_request = make_fault_clear_request(c);
                break;
            default:
                switch (i) {
                    case 16:
                        bArrMake_vendout_ctrl_request = make_ac_cfg_request(c);
                        break;
                    case 17:
                        bArrMake_vendout_ctrl_request = make_channel_xy_cfg_request(c);
                        break;
                    case 18:
                        bArrMake_vendout_ctrl_request = make_channel_type_cfg_request(c);
                        break;
                    default:
                        switch (i) {
                            case 32:
                                bArrMake_vendout_ctrl_request = make_get_dev_info_request(c);
                                break;
                            case 33:
                                bArrMake_vendout_ctrl_request = make_get_ac_info_request(c);
                                break;
                            case 34:
                                bArrMake_vendout_ctrl_request = make_get_channel_xy_info_request(c);
                                break;
                            case 35:
                                bArrMake_vendout_ctrl_request = make_get_channel_type_info_request(c);
                                break;
                            default:
                                bArrMake_vendout_ctrl_request = null;
                                break;
                        }
                        break;
                }
                break;
        }
        write_to_uart(bArrMake_vendout_ctrl_request);
    }

    private byte[] make_vendout_ctrl_request(cmd_to_send c) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(72);
        arrayList.add(0);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(c.cmd));
        arrayList.add(0);
        List list = (List) c.arg1;
        arrayList.add(Integer.valueOf(list.size()));
        String string = this.machine.ctx.getString(R.string.make_vendout_ctrl_request1, this.machine.value.getCMDDesc(c.cmd), Integer.valueOf(list.size()));
        for (int i = 0; i < list.size(); i++) {
            arrayList.add(Integer.valueOf(((Channel) list.get(i)).row));
            arrayList.add(Integer.valueOf(((Channel) list.get(i)).col));
            arrayList.add(1);
            string = string + this.machine.ctx.getString(R.string.make_vendout_ctrl_request2, ((Channel) list.get(i)).get_name());
        }
        return make_len_cs(arrayList, string);
    }

    private byte[] make_light_ctrl_request(cmd_to_send c) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(72);
        arrayList.add(0);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(c.cmd));
        arrayList.add(0);
        arrayList.add((Integer) c.arg1);
        arrayList.add((Integer) c.arg2);
        return make_len_cs(arrayList, this.machine.value.getCMDDesc(c.cmd) + "," + this.machine.value.GetLightType(((Integer) c.arg1).intValue()) + "," + this.machine.value.GetLightState(((Integer) c.arg2).intValue()));
    }

    private byte[] make_digoutput_ctrl_request(cmd_to_send c) {
        Context context;
        int i;
        ArrayList arrayList = new ArrayList();
        arrayList.add(72);
        arrayList.add(0);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(c.cmd));
        arrayList.add(0);
        arrayList.add((Integer) c.arg1);
        arrayList.add((Integer) c.arg2);
        Context context2 = this.machine.ctx;
        int i2 = R.string.make_digoutput_ctrl_request;
        Object[] objArr = new Object[3];
        objArr[0] = this.machine.value.getCMDDesc(c.cmd);
        objArr[1] = (Integer) c.arg1;
        if (((Integer) c.arg2).intValue() == 0) {
            context = this.machine.ctx;
            i = R.string.v_op_action_0;
        } else {
            context = this.machine.ctx;
            i = R.string.v_op_action_1;
        }
        objArr[2] = context.getString(i);
        return make_len_cs(arrayList, context2.getString(i2, objArr));
    }

    private byte[] make_moter_ctrl_request(cmd_to_send c) {
        Context context;
        int i;
        ArrayList arrayList = new ArrayList();
        arrayList.add(72);
        arrayList.add(0);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(c.cmd));
        arrayList.add(0);
        arrayList.add((Integer) c.arg1);
        arrayList.add((Integer) c.arg2);
        arrayList.add(Integer.valueOf((((Integer) c.arg3).intValue() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) / 256));
        arrayList.add(Integer.valueOf(((Integer) c.arg3).intValue() & 255));
        Context context2 = this.machine.ctx;
        int i2 = R.string.make_moter_ctrl_request;
        Object[] objArr = new Object[4];
        objArr[0] = this.machine.value.getCMDDesc(c.cmd);
        objArr[1] = (Integer) c.arg1;
        if (((Integer) c.arg2).intValue() == 0) {
            context = this.machine.ctx;
            i = R.string.v_motor_action_0;
        } else {
            context = this.machine.ctx;
            i = R.string.v_motor_action_1;
        }
        objArr[2] = context.getString(i);
        objArr[3] = (Integer) c.arg3;
        return make_len_cs(arrayList, context2.getString(i2, objArr));
    }

    private byte[] make_fault_clear_request(cmd_to_send c) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(72);
        arrayList.add(0);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(c.cmd));
        arrayList.add(0);
        return make_len_cs(arrayList, this.machine.value.getCMDDesc(c.cmd));
    }

    private byte[] make_channel_scan_request(cmd_to_send c) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(72);
        arrayList.add(0);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(c.cmd));
        arrayList.add(0);
        return make_len_cs(arrayList, this.machine.value.getCMDDesc(c.cmd));
    }

    private byte[] make_channel_reset_request(cmd_to_send c) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(72);
        arrayList.add(0);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(c.cmd));
        arrayList.add(0);
        arrayList.add((Integer) c.arg1);
        Channel channel = (Channel) c.arg2;
        arrayList.add(Integer.valueOf(channel.row));
        arrayList.add(Integer.valueOf(channel.col));
        return make_len_cs(arrayList, this.machine.ctx.getString(R.string.make_channel_reset_request, this.machine.value.getCMDDesc(c.cmd), this.machine.value.GetChResetMode(((Integer) c.arg1).intValue()), channel.get_name()));
    }

    private byte[] make_system_reset_request(cmd_to_send c) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(72);
        arrayList.add(0);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(c.cmd));
        arrayList.add(0);
        return make_len_cs(arrayList, this.machine.value.getCMDDesc(c.cmd));
    }

    private byte[] make_ac_ctrl_request(cmd_to_send c) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(72);
        arrayList.add(0);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(c.cmd));
        arrayList.add(0);
        arrayList.add((Integer) c.arg1);
        StringBuilder sb = new StringBuilder();
        sb.append(this.machine.value.getCMDDesc(c.cmd));
        sb.append(",");
        sb.append(((Integer) c.arg1).intValue() == 0 ? "Stop" : "Run");
        return make_len_cs(arrayList, sb.toString());
    }

    private byte[] make_ac_cfg_request(cmd_to_send c) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(72);
        arrayList.add(0);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(c.cmd));
        arrayList.add(0);
        ArrayList arrayList2 = (ArrayList) c.arg1;
        String cMDDesc = this.machine.value.getCMDDesc(c.cmd);
        for (int i = 0; i < 9; i++) {
            arrayList.add((Integer) arrayList2.get(i));
            cMDDesc = cMDDesc + "," + arrayList2.get(i);
        }
        return make_len_cs(arrayList, cMDDesc);
    }

    private byte[] make_channel_xy_cfg_request(cmd_to_send c) {
        String str;
        ArrayList arrayList = new ArrayList();
        arrayList.add(72);
        arrayList.add(0);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(c.cmd));
        arrayList.add(0);
        int iIntValue = ((Integer) c.arg1).intValue();
        arrayList.add(Integer.valueOf(iIntValue));
        String cMDDesc = this.machine.value.getCMDDesc(c.cmd);
        if (iIntValue != 255) {
            str = cMDDesc + ",row=" + iIntValue;
            List list = (List) c.arg2;
            for (int i = 0; i < 20; i++) {
                arrayList.add(Integer.valueOf((((Channel) list.get(i)).x >> 8) & 255));
                arrayList.add(Integer.valueOf(((Channel) list.get(i)).x & 255));
                arrayList.add(Integer.valueOf((((Channel) list.get(i)).y >> 8) & 255));
                arrayList.add(Integer.valueOf(((Channel) list.get(i)).y & 255));
                str = str + "," + ((Channel) list.get(i)).get_name() + ":x=" + ((int) ((Channel) list.get(i)).x) + ",y=" + ((int) ((Channel) list.get(i)).y);
            }
        } else {
            str = cMDDesc + ",write to VMC ROM";
        }
        return make_len_cs(arrayList, str);
    }

    private byte[] make_channel_type_cfg_request(cmd_to_send c) {
        String str;
        ArrayList arrayList = new ArrayList();
        arrayList.add(72);
        arrayList.add(0);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(c.cmd));
        arrayList.add(0);
        int iIntValue = ((Integer) c.arg1).intValue();
        arrayList.add(Integer.valueOf(iIntValue));
        String cMDDesc = this.machine.value.getCMDDesc(c.cmd);
        if (iIntValue != 255) {
            str = cMDDesc + ",row=" + iIntValue;
            List list = (List) c.arg2;
            for (int i = 0; i < 20; i++) {
                arrayList.add(Integer.valueOf(((Channel) list.get(i)).type));
                str = str + "," + ((Channel) list.get(i)).get_name() + "=" + this.machine.value.GetChType(((Channel) list.get(i)).type);
            }
        } else {
            str = cMDDesc + ",write to VMC ROM";
        }
        return make_len_cs(arrayList, str);
    }

    private byte[] make_get_dev_info_request(cmd_to_send c) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(72);
        arrayList.add(0);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(c.cmd));
        arrayList.add(0);
        return make_len_cs(arrayList, this.machine.value.getCMDDesc(c.cmd));
    }

    private byte[] make_get_ac_info_request(cmd_to_send c) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(72);
        arrayList.add(0);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(c.cmd));
        arrayList.add(0);
        return make_len_cs(arrayList, this.machine.value.getCMDDesc(c.cmd));
    }

    private byte[] make_get_channel_xy_info_request(cmd_to_send c) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(72);
        arrayList.add(0);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(c.cmd));
        arrayList.add(0);
        arrayList.add((Integer) c.arg1);
        return make_len_cs(arrayList, this.machine.value.getCMDDesc(c.cmd) + ",row=" + ((Integer) c.arg1));
    }

    private byte[] make_get_channel_type_info_request(cmd_to_send c) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(72);
        arrayList.add(0);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(c.cmd));
        arrayList.add(0);
        arrayList.add((Integer) c.arg1);
        return make_len_cs(arrayList, this.machine.value.getCMDDesc(c.cmd) + ",row=" + ((Integer) c.arg1));
    }

    protected byte[] make_ack(int cmd) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(72);
        arrayList.add(0);
        arrayList.add(3);
        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(0);
        arrayList.add(Integer.valueOf(cmd));
        arrayList.add(0);
        arrayList.add(0);
        return make_len_cs(arrayList, "PC ACK" + this.machine.value.getCMDDesc(cmd));
    }

    private byte[] make_len_cs(List<Integer> data, String desc) {
        int size = data.size();
        data.set(1, Integer.valueOf(size));
        int i = size + 2;
        byte[] bArr = new byte[i];
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            bArr[i3] = data.get(i3).byteValue();
            i2 += bArr[i3] & 255;
        }
        bArr[size] = (byte) (i2 % 256);
        bArr[size + 1] = marshall_t.status_vpos_insert_or_swipe_another_card;
        String str = const_value.GetTime() + " TX : ";
        for (int i4 = 0; i4 < i; i4++) {
            str = str + String.format("%02x", Byte.valueOf(bArr[i4])) + " ";
        }
        this.machine.m_event_if.onTxData(str, "TX: " + desc);
        return bArr;
    }

    protected void write_to_uart(byte[] ret) {
        if (ret == null || ret.length <= 0) {
            return;
        }
        try {
            this.outputStream.write(ret);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(String s) {
        Log.e("LYF", s);
    }
}
