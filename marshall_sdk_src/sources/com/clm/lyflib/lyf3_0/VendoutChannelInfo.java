package com.clm.lyflib.lyf3_0;

/* JADX INFO: loaded from: classes.dex */
public class VendoutChannelInfo {
    public int col;
    public String result;
    public int row;

    VendoutChannelInfo(int r, int c, String res) {
        this.row = r;
        this.col = c;
        this.result = res;
    }

    public String get_name() {
        return const_value.row_name[this.row] + const_value.col_name[this.col];
    }
}
