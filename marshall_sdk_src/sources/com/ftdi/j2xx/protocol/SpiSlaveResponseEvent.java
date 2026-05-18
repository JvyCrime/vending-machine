package com.ftdi.j2xx.protocol;

/* JADX INFO: loaded from: classes.dex */
public class SpiSlaveResponseEvent extends SpiSlaveEvent {
    public static final int DATA_CORRUPTED = 1;
    public static final int IO_ERROR = 2;
    public static final int OK = 0;
    public static final int RESET = 3;
    public static final int RES_SLAVE_READ = 3;
    private int mResponseCode;

    public SpiSlaveResponseEvent(int i, int i2, Object obj, Object obj2, Object obj3) {
        super(i, false, obj, obj2, obj3);
        this.mResponseCode = i2;
    }

    public int getResponseCode() {
        return this.mResponseCode;
    }
}
