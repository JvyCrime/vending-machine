package com.ftdi.j2xx.ft4222;

/* JADX INFO: compiled from: FT_4222_Gpio.java */
/* JADX INFO: loaded from: classes.dex */
class dev_ctrl {
    byte ep_in;
    byte ep_out;
    byte[] proc_io;

    public dev_ctrl(char c) {
        if (c < 'B') {
            this.proc_io = new byte[3];
        } else {
            this.proc_io = new byte[1];
        }
    }
}
