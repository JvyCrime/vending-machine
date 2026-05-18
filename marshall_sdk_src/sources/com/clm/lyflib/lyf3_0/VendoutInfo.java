package com.clm.lyflib.lyf3_0;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class VendoutInfo {
    public List<VendoutChannelInfo> chs = new ArrayList();
    public String elevator_state = " ";
    public String error_code = " ";

    VendoutInfo() {
    }

    protected void clear() {
        this.chs.clear();
        this.elevator_state = " ";
        this.error_code = " ";
    }
}
