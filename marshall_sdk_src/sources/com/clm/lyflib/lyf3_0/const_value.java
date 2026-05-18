package com.clm.lyflib.lyf3_0;

import android.content.Context;
import com.clm.lyflib.R;
import java.text.SimpleDateFormat;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class const_value {
    protected static final int CFG_AC = 16;
    protected static final int CFG_CHANNEL_TYPE = 18;
    protected static final int CFG_CHANNEL_XY = 17;
    protected static final int CTRL_AC = 8;
    protected static final int CTRL_CHANNEL_RESET = 6;
    protected static final int CTRL_CHANNEL_SCAN = 5;
    protected static final int CTRL_DIGOUTPUT = 3;
    protected static final int CTRL_FAULT_CLEAR = 9;
    protected static final int CTRL_LIGHT = 2;
    protected static final int CTRL_MOTOR = 4;
    protected static final int CTRL_SYSTEM_RESET = 7;
    protected static final int CTRL_VENDOUT = 1;
    protected static final int GET_AC_INFO = 33;
    protected static final int GET_CHANNEL_TYPE = 35;
    protected static final int GET_CHANNEL_XY = 34;
    protected static final int GET_DEV_INFO = 32;
    protected static final int RPT_CHANNEL_SCAN = 49;
    protected static final int RPT_HEARTBEAT = 0;
    protected static final int RPT_SELFCHECK = 255;
    protected static final int RPT_VENDDOUT = 48;
    public static final int WRITE_TO_ROM = 255;
    private Context ctx;
    public static final String[] row_name = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    public static final String[] col_name = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19"};
    public static final String[] benchmark_times = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    public static final String[] ac_led_name = {"PTC", "PFM", "CFM", "EFM", "CP"};
    public static final String[] basic_led_name = {"X+", "X-", "Y+", "Y-", "G", "H", "I", "J", "Q+", "Q-", "QS", "C+", "C-", "CS", "X0", "X1", "X2", "X3", "X4", "X5", "D0", "D1", "D2", "S1", "S2", "L1", "L2"};

    public const_value(Context c) {
        this.ctx = c;
    }

    public static String GetTime() {
        return new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
    }

    protected String GetSelfCheckState(int st) {
        String string = this.ctx.getString(R.string.v_unknown);
        if (st == 0) {
            return this.ctx.getString(R.string.v_heartbeatstate_0);
        }
        if (st == 1) {
            return this.ctx.getString(R.string.v_machinestate_0);
        }
        if (st != 2) {
            return st != 3 ? string : this.ctx.getString(R.string.v_machinestate_1);
        }
        return this.ctx.getString(R.string.v_machinestate_2);
    }

    public String GetMachineState(int st) {
        String string = this.ctx.getString(R.string.v_unknown);
        if (st == 0) {
            return this.ctx.getString(R.string.v_machinestate_0);
        }
        if (st != 1) {
            return st != 2 ? string : this.ctx.getString(R.string.v_machinestate_2);
        }
        return this.ctx.getString(R.string.v_machinestate_1);
    }

    public static String GetChName(int row, int col) {
        return row_name[row] + col_name[col];
    }

    public String GetMachineType(int st) {
        String string = this.ctx.getString(R.string.v_unknown);
        if (st == 0) {
            return this.ctx.getString(R.string.v_machinetype_0);
        }
        if (st == 1) {
            return this.ctx.getString(R.string.v_machinetype_1);
        }
        if (st == 2) {
            return this.ctx.getString(R.string.v_machinetype_2);
        }
        if (st != 3) {
            return st != 4 ? string : this.ctx.getString(R.string.v_machinetype_4);
        }
        return this.ctx.getString(R.string.v_machinetype_3);
    }

    public String GetVendoutState(int st) {
        String string = this.ctx.getString(R.string.v_unknown);
        if (st == 0) {
            return this.ctx.getString(R.string.v_vendoutstate_0);
        }
        if (st != 1) {
            return st != 2 ? string : this.ctx.getString(R.string.v_vendoutstate_2);
        }
        return this.ctx.getString(R.string.v_vendoutstate_1);
    }

    public String GetElevatorState(int st) {
        String string = this.ctx.getString(R.string.v_unknown);
        if (st == 0) {
            return this.ctx.getString(R.string.v_elevatorstate_0);
        }
        if (st != 1) {
            return st != 2 ? string : this.ctx.getString(R.string.v_elevatorstate_2);
        }
        return this.ctx.getString(R.string.v_elevatorstate_1);
    }

    public String GetVendoutFault(int st) {
        switch (st) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return this.ctx.getResources().getStringArray(R.array.array_vendout_fault)[st];
            default:
                return this.ctx.getString(R.string.v_unknown);
        }
    }

    public String GetMachineFault(int st) {
        switch (st) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
                return this.ctx.getResources().getStringArray(R.array.array_machine_fault)[st];
            default:
                return this.ctx.getString(R.string.v_unknown);
        }
    }

    public String[] GetAcModeArray() {
        return this.ctx.getResources().getStringArray(R.array.array_acmode);
    }

    public String GetACMode(int st) {
        if (st == 0 || st == 1 || st == 2) {
            return this.ctx.getResources().getStringArray(R.array.array_acmode)[st];
        }
        return this.ctx.getString(R.string.v_unknown);
    }

    public String[] GetBenchmarkModeArray() {
        return this.ctx.getResources().getStringArray(R.array.array_benchmark_mode);
    }

    public String GetBenchmarkMode(int st) {
        if (st == 0 || st == 1 || st == 2) {
            return this.ctx.getResources().getStringArray(R.array.array_benchmark_mode)[st];
        }
        return this.ctx.getString(R.string.v_unknown);
    }

    public String[] GetChTypeArray() {
        return this.ctx.getResources().getStringArray(R.array.array_chtype);
    }

    public String GetChType(int type) {
        if (type == 0 || type == 1 || type == 2) {
            return this.ctx.getResources().getStringArray(R.array.array_chtype)[type];
        }
        return this.ctx.getString(R.string.v_unknown);
    }

    protected int GetChTypeIndex(String s) {
        String[] stringArray = this.ctx.getResources().getStringArray(R.array.array_chtype);
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(s)) {
                return i;
            }
        }
        return 255;
    }

    public String GetChResetMode(int type) {
        String string = this.ctx.getString(R.string.v_unknown);
        if (type == 0) {
            return this.ctx.getString(R.string.v_chresetmode_0);
        }
        if (type != 1) {
            return type != 2 ? string : this.ctx.getString(R.string.v_chresetmode_2);
        }
        return this.ctx.getString(R.string.v_chresetmode_1);
    }

    protected String GetLightType(int type) {
        Context context;
        int i;
        if (type == 0) {
            context = this.ctx;
            i = R.string.v_ledtype_0;
        } else {
            context = this.ctx;
            i = R.string.v_ledtype_other;
        }
        return context.getString(i);
    }

    protected String GetLightState(int st) {
        String string = this.ctx.getString(R.string.v_unknown);
        if (st == 0) {
            return this.ctx.getString(R.string.v_ledstate_0);
        }
        if (st != 1) {
            return st != 2 ? string : this.ctx.getString(R.string.v_ledstate_2);
        }
        return this.ctx.getString(R.string.v_ledstate_1);
    }

    protected String getCMDDesc(int i) {
        String string = this.ctx.getString(R.string.v_unknown);
        if (i == 48) {
            return this.ctx.getString(R.string.RPT_VENDDOUT);
        }
        if (i == 49) {
            return this.ctx.getString(R.string.RPT_CHANNEL_SCAN);
        }
        if (i != 255) {
            switch (i) {
                case 0:
                    return this.ctx.getString(R.string.RPT_HEARTBEAT);
                case 1:
                    return this.ctx.getString(R.string.CTRL_VENDOUT);
                case 2:
                    return this.ctx.getString(R.string.CTRL_LIGHT);
                case 3:
                    return this.ctx.getString(R.string.CTRL_DIGOUTPUT);
                case 4:
                    return this.ctx.getString(R.string.CTRL_MOTOR);
                case 5:
                    return this.ctx.getString(R.string.CTRL_CHANNEL_SCAN);
                case 6:
                    return this.ctx.getString(R.string.CTRL_CHANNEL_RESET);
                case 7:
                    return this.ctx.getString(R.string.CTRL_SYSTEM_RESET);
                case 8:
                    return this.ctx.getString(R.string.CTRL_AC);
                case 9:
                    return this.ctx.getString(R.string.CTRL_FAULT_CLEAR);
                default:
                    switch (i) {
                        case 16:
                            return this.ctx.getString(R.string.CFG_AC);
                        case 17:
                            return this.ctx.getString(R.string.CFG_CHANNEL_XY);
                        case 18:
                            return this.ctx.getString(R.string.CFG_CHANNEL_TYPE);
                        default:
                            switch (i) {
                                case 32:
                                    return this.ctx.getString(R.string.GET_DEV_INFO);
                                case 33:
                                    return this.ctx.getString(R.string.GET_AC_INFO);
                                case 34:
                                    return this.ctx.getString(R.string.GET_CHANNEL_XY);
                                case 35:
                                    return this.ctx.getString(R.string.GET_CHANNEL_TYPE);
                                default:
                                    return string;
                            }
                    }
            }
        }
        return this.ctx.getString(R.string.RPT_SELFCHECK);
    }
}
