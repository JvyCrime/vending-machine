package com.ftdi.j2xx.interfaces;

/* JADX INFO: loaded from: classes.dex */
public interface Gpio {
    int getInterruptStatus(boolean[] zArr);

    int init(int[] iArr);

    int read(int i, boolean[] zArr);

    int write(int i, boolean z);
}
