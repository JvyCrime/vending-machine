package com.ftdi.j2xx;

import java.nio.ByteBuffer;

/* JADX INFO: compiled from: FT_Device.java */
/* JADX INFO: loaded from: classes.dex */
class InBuffer {
    private boolean mAcquired;
    private int mBufId;
    private ByteBuffer mBuffer;
    private int mLength;

    public InBuffer(int i) {
        this.mBuffer = ByteBuffer.allocate(i);
        setLength(0);
    }

    void setBufferId(int i) {
        this.mBufId = i;
    }

    int getBufferId() {
        return this.mBufId;
    }

    ByteBuffer getInputBuffer() {
        return this.mBuffer;
    }

    int getLength() {
        return this.mLength;
    }

    void setLength(int i) {
        this.mLength = i;
    }

    synchronized void purge() {
        this.mBuffer.clear();
        setLength(0);
    }

    synchronized boolean acquired() {
        return this.mAcquired;
    }

    synchronized ByteBuffer acquire(int i) {
        ByteBuffer byteBuffer;
        byteBuffer = null;
        if (!this.mAcquired) {
            this.mAcquired = true;
            this.mBufId = i;
            byteBuffer = this.mBuffer;
        }
        return byteBuffer;
    }

    synchronized boolean release(int i) {
        boolean z;
        z = false;
        if (this.mAcquired && i == this.mBufId) {
            this.mAcquired = false;
            z = true;
        }
        return z;
    }
}
