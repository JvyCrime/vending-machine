package com.ftdi.j2xx.protocol;

/* JADX INFO: loaded from: classes.dex */
public class SpiSlaveEvent {
    private boolean m_bSync;
    private int m_iEventType;
    private Object m_pArg0;
    private Object m_pArg1;
    private Object m_pArg2;

    public SpiSlaveEvent(int i, boolean z, Object obj, Object obj2, Object obj3) {
        this.m_iEventType = i;
        this.m_bSync = z;
        this.m_pArg0 = obj;
        this.m_pArg1 = obj2;
        this.m_pArg2 = obj3;
    }

    public Object getArg0() {
        return this.m_pArg0;
    }

    public void setArg0(Object obj) {
        this.m_pArg0 = obj;
    }

    public Object getArg1() {
        return this.m_pArg1;
    }

    public void setArg1(Object obj) {
        this.m_pArg1 = obj;
    }

    public Object getArg2() {
        return this.m_pArg2;
    }

    public void setArg2(Object obj) {
        this.m_pArg2 = obj;
    }

    public int getEventType() {
        return this.m_iEventType;
    }

    public void setEventType(int i) {
        this.m_iEventType = i;
    }

    public boolean getSync() {
        return this.m_bSync;
    }

    public void setSync(boolean z) {
        this.m_bSync = z;
    }
}
