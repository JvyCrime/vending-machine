package com.ftdi.j2xx.protocol;

/* JADX INFO: loaded from: classes.dex */
public class SpiSlaveRequestEvent extends SpiSlaveEvent {
    protected static final int REQ_DESTORY_THREAD = -1;
    protected static final int REQ_INIT_SLAVE = 1;
    protected static final int REQ_SLAVE_READ = 3;
    protected static final int REQ_SLAVE_WRITE = 2;

    public SpiSlaveRequestEvent(int i, boolean z, Object obj, Object obj2, Object obj3) {
        super(i, z, obj, obj2, obj3);
    }
}
