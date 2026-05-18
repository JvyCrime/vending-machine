package com.ftdi.j2xx.protocol;

import android.os.Handler;
import android.util.Log;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public abstract class SpiSlaveThread extends Thread {
    public static final int THREAD_DESTORYED = 2;
    public static final int THREAD_INIT = 0;
    public static final int THREAD_RUNNING = 1;
    private boolean m_bResponseWaitCheck;
    private boolean m_bSendWaitCheck;
    private Handler m_pUIHandler;
    private Queue<SpiSlaveEvent> m_pMsgQueue = new LinkedList();
    private Object m_pSendWaitCond = new Object();
    private Object m_pResponseWaitCond = new Object();
    private Lock m_pMsgLock = new ReentrantLock();
    private int m_iThreadState = 0;

    protected abstract boolean isTerminateEvent(SpiSlaveEvent spiSlaveEvent);

    protected abstract boolean pollData();

    protected abstract void requestEvent(SpiSlaveEvent spiSlaveEvent);

    public SpiSlaveThread() {
        setName("SpiSlaveThread");
    }

    public boolean sendMessage(SpiSlaveEvent spiSlaveEvent) {
        while (this.m_iThreadState != 1) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException unused) {
            }
        }
        this.m_pMsgLock.lock();
        if (this.m_pMsgQueue.size() > 10) {
            this.m_pMsgLock.unlock();
            Log.d("FTDI", "SpiSlaveThread sendMessage Buffer full!!");
            return false;
        }
        this.m_pMsgQueue.add(spiSlaveEvent);
        if (this.m_pMsgQueue.size() == 1) {
            synchronized (this.m_pSendWaitCond) {
                this.m_bSendWaitCheck = true;
                this.m_pSendWaitCond.notify();
            }
        }
        this.m_pMsgLock.unlock();
        if (spiSlaveEvent.getSync()) {
            synchronized (this.m_pResponseWaitCond) {
                this.m_bResponseWaitCheck = false;
                while (!this.m_bResponseWaitCheck) {
                    try {
                        this.m_pResponseWaitCond.wait();
                    } catch (InterruptedException unused2) {
                        this.m_bResponseWaitCheck = true;
                    }
                }
            }
        }
        return true;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        this.m_iThreadState = 1;
        boolean zIsTerminateEvent = false;
        while (!Thread.interrupted() && !zIsTerminateEvent) {
            pollData();
            this.m_pMsgLock.lock();
            if (this.m_pMsgQueue.size() <= 0) {
                this.m_pMsgLock.unlock();
            } else {
                SpiSlaveEvent spiSlaveEventPeek = this.m_pMsgQueue.peek();
                this.m_pMsgQueue.remove();
                this.m_pMsgLock.unlock();
                requestEvent(spiSlaveEventPeek);
                if (spiSlaveEventPeek.getSync()) {
                    synchronized (this.m_pResponseWaitCond) {
                        while (this.m_bResponseWaitCheck) {
                            try {
                                Thread.sleep(100L);
                            } catch (InterruptedException unused) {
                            }
                        }
                        this.m_bResponseWaitCheck = true;
                        this.m_pResponseWaitCond.notify();
                    }
                }
                zIsTerminateEvent = isTerminateEvent(spiSlaveEventPeek);
            }
        }
        this.m_iThreadState = 2;
    }
}
