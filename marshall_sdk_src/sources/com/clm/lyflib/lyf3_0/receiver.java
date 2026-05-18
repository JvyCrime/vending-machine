package com.clm.lyflib.lyf3_0;

import android.util.Log;
import android_serialport_api.SerialPort;
import com.bitmick.marshall.vmc.marshall_t;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.ShortCompanionObject;

/* JADX INFO: loaded from: classes.dex */
class receiver implements Runnable {
    private SerialPort m_Port;
    private boolean m_running = false;
    private VendingMachine machine;

    protected receiver(SerialPort p, VendingMachine m) {
        this.m_Port = p;
        this.machine = m;
    }

    protected void close() {
        this.m_running = false;
    }

    @Override // java.lang.Runnable
    public void run() {
        ArrayList arrayList = new ArrayList();
        this.m_running = true;
        log("read thread run");
        InputStream inputStream = this.m_Port.getInputStream();
        boolean z = false;
        int i = 0;
        int iIntValue = 0;
        int iIntValue2 = 0;
        while (this.m_running) {
            try {
                if (inputStream.available() > 0) {
                    int i2 = inputStream.read();
                    if (!z) {
                        if (i2 == 72 && arrayList.size() == 0) {
                            z = true;
                            i = 0;
                            iIntValue = 0;
                        }
                    } else {
                        arrayList.add(Integer.valueOf(i2));
                        i++;
                        if (i == 1) {
                            iIntValue = arrayList.get(i - 1).intValue();
                        }
                        if (i == iIntValue) {
                            iIntValue2 = arrayList.get(i - 1).intValue();
                        }
                        if (i == iIntValue + 1) {
                            if (arrayList.get(i - 1).intValue() == 84) {
                                if (check_CS(arrayList, iIntValue2)) {
                                    phase_frame(arrayList);
                                } else {
                                    log("wrong fcc");
                                }
                            }
                            arrayList.clear();
                            z = false;
                        }
                    }
                } else {
                    Thread.sleep(5L);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException unused) {
                log("lyf sleep error");
            }
        }
    }

    private boolean phase_frame(List<Integer> data) {
        String strPhase_RPT_VENDDOUT;
        data.get(0).intValue();
        data.get(5).intValue();
        String str = const_value.GetTime() + " RX : 48 ";
        for (int i = 0; i < data.size(); i++) {
            str = str + String.format("%02x", data.get(i)) + " ";
        }
        this.machine.m_event_if.onRxData(str);
        int iIntValue = data.get(5).intValue();
        if (iIntValue == 48) {
            strPhase_RPT_VENDDOUT = phase_RPT_VENDDOUT(data);
        } else if (iIntValue == 49) {
            strPhase_RPT_VENDDOUT = phase_RPT_CHANNEL_SCAN(data);
        } else if (iIntValue != 255) {
            switch (iIntValue) {
                case 0:
                    strPhase_RPT_VENDDOUT = phase_RPT_HEARTBEAT(data);
                    break;
                case 1:
                    strPhase_RPT_VENDDOUT = phase_CTRL_VENDOUT(data);
                    break;
                case 2:
                    strPhase_RPT_VENDDOUT = phase_CTRL_LIGHT(data);
                    break;
                case 3:
                    strPhase_RPT_VENDDOUT = phase_CTRL_DIGOUTPUT(data);
                    break;
                case 4:
                    strPhase_RPT_VENDDOUT = phase_CTRL_MOTOR(data);
                    break;
                case 5:
                    strPhase_RPT_VENDDOUT = phase_CTRL_CHANNEL_SCAN(data);
                    break;
                case 6:
                    strPhase_RPT_VENDDOUT = phase_CTRL_CHANNEL_RESET(data);
                    break;
                case 7:
                    strPhase_RPT_VENDDOUT = phase_CTRL_SYSTEM_RESET(data);
                    break;
                case 8:
                    strPhase_RPT_VENDDOUT = phase_CTRL_AC(data);
                    break;
                case 9:
                    strPhase_RPT_VENDDOUT = phase_CTRL_FAULT_CLEAR(data);
                    break;
                default:
                    switch (iIntValue) {
                        case 16:
                            strPhase_RPT_VENDDOUT = phase_CFG_AC(data);
                            break;
                        case 17:
                            strPhase_RPT_VENDDOUT = phase_CFG_CHANNEL_XY(data);
                            break;
                        case 18:
                            strPhase_RPT_VENDDOUT = phase_CFG_CHANNEL_TYPE(data);
                            break;
                        default:
                            switch (iIntValue) {
                                case 32:
                                    strPhase_RPT_VENDDOUT = phase_GET_DEV_INFO(data);
                                    break;
                                case 33:
                                    strPhase_RPT_VENDDOUT = phase_GET_AC_INFO(data);
                                    break;
                                case 34:
                                    strPhase_RPT_VENDDOUT = phase_GET_CHANNEL_XY(data);
                                    break;
                                case 35:
                                    strPhase_RPT_VENDDOUT = phase_GET_CHANNEL_TYPE(data);
                                    break;
                                default:
                                    strPhase_RPT_VENDDOUT = "";
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            strPhase_RPT_VENDDOUT = phase_RPT_SELFCHECK(data);
        }
        this.machine.m_event_if.onRxDataAnalyzed("RX: " + strPhase_RPT_VENDDOUT);
        return true;
    }

    private String phase_RPT_HEARTBEAT(List<Integer> data) {
        String str = this.machine.value.getCMDDesc(data.get(5).intValue()) + ":";
        this.machine.ms.run_state = data.get(7).intValue();
        String str2 = str + this.machine.value.GetMachineState(this.machine.ms.run_state);
        this.machine.ms.temperature_inside = data.get(8).intValue();
        this.machine.ms.temperature_cfm = data.get(9).intValue();
        this.machine.ms.temperature_outside = data.get(10).intValue();
        this.machine.ms.weight_sensor = (data.get(11).intValue() * 256) + data.get(12).intValue();
        this.machine.ms.input = (data.get(13).intValue() * 256 * 256 * 256) + (data.get(14).intValue() * 256 * 256) + (data.get(15).intValue() * 256) + data.get(16).intValue();
        this.machine.ms.output = (data.get(17).intValue() * 256) + data.get(18).intValue();
        this.machine.ms.x_coordinate = (short) ((data.get(19).intValue() * 256) + data.get(20).intValue());
        this.machine.ms.y_coordinate = (short) ((data.get(21).intValue() * 256) + data.get(22).intValue());
        this.machine.ms.z_coordinate = (short) ((data.get(23).intValue() * 256) + data.get(24).intValue());
        this.machine.ms.a_current = data.get(25).intValue();
        this.machine.ms.b_current = data.get(26).intValue();
        this.machine.ms.c_current = data.get(27).intValue();
        this.machine.ms.fault_state = (((long) data.get(28).intValue()) * 256 * 256 * 256 * 256 * 256 * 256 * 256) + (((long) data.get(29).intValue()) * 256 * 256 * 256 * 256 * 256 * 256) + (((long) data.get(30).intValue()) * 256 * 256 * 256 * 256 * 256) + (((long) data.get(31).intValue()) * 256 * 256 * 256 * 256) + (((long) data.get(32).intValue()) * 256 * 256 * 256) + (((long) data.get(33).intValue()) * 256 * 256) + (((long) data.get(34).intValue()) * 256) + ((long) data.get(35).intValue());
        long j = this.machine.ms.fault_state;
        if (j != 0) {
            str2 = str2 + "故障:";
            for (int i = 0; i < 46; i++) {
                if ((j & 1) == 1) {
                    str2 = str2 + this.machine.value.GetMachineFault(i) + ",";
                }
                j >>= 1;
            }
        }
        this.machine.write_ack(0);
        this.machine.m_event_if.onHeartBeat(this.machine.ms);
        return str2;
    }

    private String phase_CTRL_VENDOUT(List<Integer> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("出货应答包:");
        sb.append(data.get(7).intValue() == 0 ? "成功,立即出货" : "忙碌,不能出货");
        String string = sb.toString();
        if (data.get(7).intValue() == 1) {
            this.machine.try_last_cmd();
        }
        return string;
    }

    private String phase_CTRL_LIGHT(List<Integer> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("灯光应答包:");
        sb.append(data.get(7).intValue() == 0 ? "成功" : "失败");
        return sb.toString();
    }

    private String phase_CTRL_DIGOUTPUT(List<Integer> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("数字量应答包:");
        sb.append(data.get(7).intValue() == 0 ? "成功" : "失败");
        return sb.toString();
    }

    private String phase_CTRL_MOTOR(List<Integer> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("电机应答包:");
        sb.append(data.get(7).intValue() == 0 ? "成功" : "失败");
        return sb.toString();
    }

    private String phase_CTRL_CHANNEL_SCAN(List<Integer> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("扫描应答包:");
        sb.append(data.get(7).intValue() == 0 ? "成功,立即扫描" : "忙碌");
        return sb.toString();
    }

    private String phase_CTRL_CHANNEL_RESET(List<Integer> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("复位货道应答包:");
        sb.append(data.get(7).intValue() == 0 ? "成功,立即复位" : "忙碌");
        return sb.toString();
    }

    private String phase_CTRL_SYSTEM_RESET(List<Integer> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("系统货道应答包:");
        sb.append(data.get(7).intValue() == 0 ? "成功" : "失败");
        return sb.toString();
    }

    private String phase_CTRL_AC(List<Integer> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("温控启停应答包:");
        sb.append(data.get(7).intValue() == 0 ? "成功" : "失败");
        return sb.toString();
    }

    private String phase_CTRL_FAULT_CLEAR(List<Integer> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("故障清除应答包:");
        sb.append(data.get(7).intValue() == 0 ? "成功" : "失败");
        return sb.toString();
    }

    private String phase_CFG_AC(List<Integer> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("配置温控应答包:");
        sb.append(data.get(7).intValue() == 0 ? "成功" : "失败");
        return sb.toString();
    }

    private String phase_CFG_CHANNEL_XY(List<Integer> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("设置货道坐标应答包:");
        sb.append(data.get(7).intValue() == 0 ? "成功" : "失败");
        return sb.toString();
    }

    private String phase_CFG_CHANNEL_TYPE(List<Integer> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("设置货道类型应答包:");
        sb.append(data.get(7).intValue() == 0 ? "成功" : "失败");
        return sb.toString();
    }

    private String phase_GET_DEV_INFO(List<Integer> data) {
        int iIntValue = data.get(7).intValue();
        this.machine.mi.boards.clear();
        String str = "获取设备信息应答包:";
        for (int i = 0; i < iIntValue; i++) {
            BoardInfo boardInfo = new BoardInfo();
            int i2 = i * 4;
            int i3 = i2 + 8;
            boardInfo.board_type = (data.get(i3).intValue() & 240) >> 4;
            StringBuilder sb = new StringBuilder();
            sb.append("V");
            sb.append(data.get(i3).intValue() & 15);
            sb.append(".");
            int i4 = i2 + 9;
            sb.append((data.get(i4).intValue() & 240) >> 4);
            sb.append(".");
            sb.append(data.get(i4).intValue() & 15);
            boardInfo.hw_version = sb.toString();
            StringBuilder sb2 = new StringBuilder();
            sb2.append("V");
            sb2.append(data.get(i2 + 10).intValue() & 15);
            sb2.append(".");
            int i5 = i2 + 11;
            sb2.append((data.get(i5).intValue() & 240) >> 4);
            sb2.append(".");
            sb2.append(data.get(i5).intValue() & 15);
            boardInfo.sw_version = sb2.toString();
            this.machine.mi.boards.add(boardInfo);
            str = str + ",type=" + boardInfo.board_type + ",hw=" + boardInfo.hw_version + ",sw=" + boardInfo.sw_version;
        }
        int i6 = (iIntValue * 4) + 9;
        this.machine.mi.hw_cfg = data.get(i6).intValue() & 7;
        this.machine.mi.hw_cfg_desc = this.machine.value.GetMachineType(data.get(i6).intValue() & 7);
        int i7 = this.machine.mi.hw_cfg;
        if (i7 == 0) {
            this.machine.mi.y_max = (short) 1800;
            this.machine.mi.y_min = (short) 300;
        } else if (i7 == 1) {
            this.machine.mi.y_max = (short) 1800;
            this.machine.mi.y_min = (short) 0;
        } else {
            this.machine.mi.y_max = ShortCompanionObject.MAX_VALUE;
            this.machine.mi.y_min = ShortCompanionObject.MIN_VALUE;
        }
        String str2 = str + ",设备类型=" + this.machine.mi.hw_cfg_desc + ",y范围=" + ((int) this.machine.mi.y_min) + "~" + ((int) this.machine.mi.y_max);
        this.machine.m_event_if.onGetDevInfo(this.machine.mi);
        return str2;
    }

    private String phase_GET_AC_INFO(List<Integer> data) {
        String str = "获取温控信息应答包:温控模式=" + this.machine.value.GetACMode(data.get(7).intValue()) + ",参数=";
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(String.format("%02x", data.get(8)));
        sb.append(" ");
        String str2 = ((((((sb.toString() + String.format("%02x", data.get(9)) + " ") + String.format("%02x", data.get(10)) + " ") + String.format("%02x", data.get(11)) + " ") + String.format("%02x", data.get(12)) + " ") + String.format("%02x", data.get(13)) + " ") + String.format("%02x", data.get(14)) + " ") + String.format("%02x", data.get(15)) + " ";
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            arrayList.add(data.get(i + 7));
        }
        this.machine.m_event_if.onGetACInfo(arrayList);
        return str2;
    }

    private String phase_GET_CHANNEL_XY(List<Integer> data) {
        String str = "读取货道坐标应答包:层号=" + data.get(7) + ",";
        if (data.size() >= 40) {
            for (int i = 0; i < 20; i++) {
                int i2 = i * 4;
                short sIntValue = (short) ((data.get(i2 + 10).intValue() * 256) + data.get(i2 + 11).intValue());
                str = str + const_value.GetChName(data.get(7).intValue(), i) + "=(0," + ((int) sIntValue) + ") ";
                this.machine.mi.getch2(data.get(7).intValue(), i).x = (short) 0;
                this.machine.mi.getch2(data.get(7).intValue(), i).y = sIntValue;
            }
            this.machine.m_event_if.onGetChannelXy(data.get(7).intValue(), this.machine.mi);
        }
        return str;
    }

    private String phase_GET_CHANNEL_TYPE(List<Integer> data) {
        String str = "读取货道类型应答包:层号=" + data.get(7) + ",";
        for (int i = 0; i < 20; i++) {
            String str2 = str + const_value.GetChName(data.get(7).intValue(), i) + "=";
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            int i2 = i + 8;
            sb.append(this.machine.value.GetChType(data.get(i2).intValue()));
            str = sb.toString() + ",";
            this.machine.mi.getch2(data.get(7).intValue(), i).type = data.get(i2).intValue();
        }
        this.machine.m_event_if.onGetChannelType(data.get(7).intValue(), this.machine.mi);
        return str;
    }

    private String phase_RPT_VENDDOUT(List<Integer> data) {
        this.machine.vi.clear();
        int iIntValue = data.get(7).intValue();
        String str = "出货结果上报包:";
        for (int i = 0; i < iIntValue; i++) {
            int i2 = i * 3;
            this.machine.vi.chs.add(new VendoutChannelInfo(data.get(i2 + 8).intValue(), data.get(i2 + 9).intValue(), this.machine.value.GetVendoutState(data.get(i2 + 10).intValue())));
            str = str + "货道" + this.machine.vi.chs.get(i).get_name() + "=" + this.machine.vi.chs.get(i).result + ",";
        }
        int i3 = iIntValue * 3;
        this.machine.vi.elevator_state = this.machine.value.GetElevatorState(data.get(i3 + 8).intValue());
        this.machine.vi.error_code = this.machine.value.GetVendoutFault((data.get(i3 + 9).intValue() * 256) + data.get(i3 + 10).intValue());
        String str2 = str + this.machine.vi.elevator_state + ",故障代码=" + this.machine.vi.error_code;
        this.machine.write_ack(48);
        this.machine.m_event_if.onVendoutRpt(this.machine.vi);
        return str2;
    }

    private String phase_RPT_CHANNEL_SCAN(List<Integer> data) {
        data.get(7).intValue();
        String str = "扫描货道结果上报包:";
        for (int i = 0; i < 10; i++) {
            int i2 = i * 4;
            int iIntValue = (data.get(i2 + 8).intValue() * 256 * 256 * 256) + (data.get(i2 + 9).intValue() * 256 * 256) + (data.get(i2 + 10).intValue() * 256) + data.get(i2 + 11).intValue();
            String str2 = str + "层" + i + ":";
            for (int i3 = 0; i3 < 20; i3++) {
                if ((iIntValue & 1) == 1) {
                    str2 = str2 + const_value.GetChName(i, i3) + ",";
                    iIntValue >>= 1;
                    this.machine.mi.chl.get((i * 20) + i3).inuse = 1;
                } else {
                    iIntValue >>= 1;
                    this.machine.mi.chl.get((i * 20) + i3).inuse = 0;
                }
            }
            str = str2 + ";";
        }
        this.machine.m_event_if.onChannelScanRpt(this.machine.mi);
        this.machine.write_ack(49);
        return str;
    }

    private String phase_RPT_SELFCHECK(List<Integer> data) {
        String str = "自检应答包:" + this.machine.value.GetSelfCheckState(data.get(7).intValue());
        this.machine.write_ack(255);
        return str;
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
        return bArr;
    }

    private boolean check_CS(List<Integer> data, int CS) {
        int iIntValue = 72;
        for (int i = 0; i < data.size() - 2; i++) {
            iIntValue += data.get(i).intValue();
        }
        return CS == iIntValue % 256;
    }

    private void log(String s) {
        Log.e("LYF", s);
    }
}
