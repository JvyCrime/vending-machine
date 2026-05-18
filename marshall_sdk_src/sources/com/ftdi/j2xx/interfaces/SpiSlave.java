package com.ftdi.j2xx.interfaces;

/* JADX INFO: loaded from: classes.dex */
public interface SpiSlave {
    int getRxStatus(int[] iArr);

    int init();

    int read(byte[] bArr, int i, int[] iArr);

    int reset();

    int setMode(int i, int i2);

    int write(byte[] bArr, int i, int[] iArr);
}
