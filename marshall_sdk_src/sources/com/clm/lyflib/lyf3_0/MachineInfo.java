package com.clm.lyflib.lyf3_0;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MachineInfo {
    public List<BoardInfo> boards;
    public List<Channel> chl = new ArrayList();
    public int hw_cfg;
    public String hw_cfg_desc;
    public short y_max;
    public short y_min;

    MachineInfo() {
        for (int i = 0; i < 10; i++) {
            for (int i2 = 0; i2 < 20; i2++) {
                this.chl.add(new Channel(i2, i));
            }
        }
        this.boards = new ArrayList();
    }

    public boolean check_y(short v) {
        return v >= this.y_min && v <= this.y_max;
    }

    public Channel getch2(int row, int col) {
        return this.chl.get((row * 20) + col);
    }

    public Channel getch(String srow, String scol) {
        int iCharAt = srow.charAt(0);
        if (iCharAt >= 65 && iCharAt <= 90) {
            iCharAt -= 65;
        } else if (iCharAt >= 97 && iCharAt <= 122) {
            iCharAt -= 97;
        }
        return this.chl.get((iCharAt * 20) + Integer.parseInt(scol));
    }

    public Channel getch(String s) {
        int iCharAt = s.charAt(0);
        if (iCharAt >= 65 && iCharAt <= 90) {
            iCharAt -= 65;
        } else if (iCharAt >= 97 && iCharAt <= 122) {
            iCharAt -= 97;
        }
        return this.chl.get((iCharAt * 20) + Integer.parseInt(s.substring(1, 2)));
    }
}
