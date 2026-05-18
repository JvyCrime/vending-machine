package iaik.utils;

import java.io.BufferedInputStream;
import java.io.InputStream;

/* JADX INFO: loaded from: classes2.dex */
public class InitBufferedInputStream extends BufferedInputStream {
    public InitBufferedInputStream(InputStream inputStream, byte[] bArr) {
        this(inputStream, bArr, 2048);
    }

    public InitBufferedInputStream(InputStream inputStream, byte[] bArr, int i) {
        super(inputStream, i);
        this.count = bArr.length;
        if (this.buf.length < this.count) {
            this.buf = new byte[this.count];
        }
        System.arraycopy(bArr, 0, this.buf, 0, this.count);
        this.pos = 0;
    }
}
