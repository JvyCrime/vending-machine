package android_serialport_api;

import android.util.Log;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

/* JADX INFO: loaded from: classes.dex */
public class CircleQueue {
    private static final String TAG = "CircleQueue";
    private ArrayBlockingQueue<Byte> queue = new ArrayBlockingQueue<>(4096);

    public boolean read(int readSize, byte[] readBuffer) {
        if (this.queue.size() < readSize) {
            return false;
        }
        if (readBuffer == null || readBuffer.length < readSize) {
            Log.i(TAG, "readBuffer is null or length < readSize");
            return false;
        }
        for (int i = 0; i < readSize; i++) {
            readBuffer[i] = this.queue.poll().byteValue();
        }
        return true;
    }

    public boolean poll(int size) {
        if (this.queue.size() < size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            this.queue.poll();
        }
        return true;
    }

    public boolean prefetch(int readSize, byte[] readBuffer) {
        if (this.queue.size() < readSize) {
            return false;
        }
        if (readBuffer == null || readBuffer.length < readSize) {
            Log.i(TAG, "readBuffer is null or length < readSize");
            return false;
        }
        Iterator<Byte> it = this.queue.iterator();
        for (int i = 0; it.hasNext() && i < readSize; i++) {
            readBuffer[i] = it.next().byteValue();
        }
        return true;
    }

    public boolean contains(byte code) {
        int size = this.queue.size();
        for (int i = 0; i < size; i++) {
            if (this.queue.peek().byteValue() == code) {
                return true;
            }
            this.queue.poll();
        }
        return false;
    }

    public boolean write(byte[] writeBuffer, int length) {
        if (writeBuffer == null || writeBuffer.length < length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            this.queue.offer(Byte.valueOf(writeBuffer[i]));
        }
        return true;
    }

    public int getSize() {
        return this.queue.size();
    }

    public void clear() {
        this.queue.clear();
    }
}
