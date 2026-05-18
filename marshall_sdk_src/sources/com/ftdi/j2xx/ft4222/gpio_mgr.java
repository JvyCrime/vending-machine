package com.ftdi.j2xx.ft4222;

/* JADX INFO: compiled from: FT_4222_Device.java */
/* JADX INFO: loaded from: classes.dex */
class gpio_mgr {
    int[] gpioStatus = new int[4];
    int[] input = new int[4];
    int intrLevel;
    byte lastGpioData;
}
