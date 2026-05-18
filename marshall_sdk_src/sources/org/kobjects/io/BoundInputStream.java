package org.kobjects.io;

import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes2.dex */
public class BoundInputStream extends InputStream {
    InputStream is;
    int remaining;

    public BoundInputStream(InputStream inputStream, int i) {
        this.is = inputStream;
        this.remaining = i;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        int iAvailable = this.is.available();
        int i = this.remaining;
        return iAvailable < i ? iAvailable : i;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        int i = this.remaining;
        if (i <= 0) {
            return -1;
        }
        this.remaining = i - 1;
        return this.is.read();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.remaining;
        if (i2 > i3) {
            i2 = i3;
        }
        int i4 = this.is.read(bArr, i, i2);
        if (i4 > 0) {
            this.remaining -= i4;
        }
        return i4;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        try {
            this.is.close();
        } catch (IOException unused) {
        }
    }
}
