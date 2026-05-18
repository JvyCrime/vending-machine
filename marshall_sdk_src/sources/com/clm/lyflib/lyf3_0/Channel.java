package com.clm.lyflib.lyf3_0;

/* JADX INFO: loaded from: classes.dex */
public class Channel {
    public int col;
    public String desc;
    public int inuse;
    public int row;
    public int type;
    public short x;
    public short y;

    public Channel(int col, int row) {
        this.row = row;
        this.col = col;
        this.inuse = 0;
        this.type = 1;
        this.x = (short) 0;
        this.y = (short) 0;
        this.desc = " ";
    }

    public Channel(String s) {
        char cCharAt = s.charAt(0);
        this.row = cCharAt;
        if (cCharAt >= 'A' && cCharAt <= 'Z') {
            this.row = cCharAt - 'A';
        } else if (cCharAt >= 'a' && cCharAt <= 'z') {
            this.row = cCharAt - 'a';
        }
        this.col = Integer.parseInt(s.substring(1, 2));
        this.inuse = 0;
        this.type = 1;
        this.x = (short) 0;
        this.y = (short) 0;
        this.desc = " ";
    }

    public String get_name() {
        return const_value.row_name[this.row] + const_value.col_name[this.col];
    }
}
