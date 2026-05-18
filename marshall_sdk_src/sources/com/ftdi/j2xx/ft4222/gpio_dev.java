package com.ftdi.j2xx.ft4222;

/* JADX INFO: compiled from: FT_4222_Gpio.java */
/* JADX INFO: loaded from: classes.dex */
class gpio_dev {
    byte[] dat = new byte[1];
    byte dir;
    byte mask;
    dev_ctrl usb;

    public gpio_dev(char c) {
        this.usb = new dev_ctrl(c);
    }
}
