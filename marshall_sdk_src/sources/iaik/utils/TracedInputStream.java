package iaik.utils;

import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes2.dex */
public class TracedInputStream extends FilterInputStream {
    private ByteArrayOutputStream a;

    public TracedInputStream(InputStream inputStream) {
        this(inputStream, -1);
    }

    public TracedInputStream(InputStream inputStream, int i) {
        super(inputStream);
        this.a = new EnhancedByteArrayOutputStream(i <= 0 ? 8192 : i);
    }

    public byte[] getTracedData() {
        return this.a.toByteArray();
    }

    public byte[] getTracedData(boolean z) {
        byte[] byteArray = this.a.toByteArray();
        if (z) {
            this.a.reset();
        }
        return byteArray;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        int i = this.in.read();
        if (i != -1) {
            this.a.write(i);
        }
        return i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = this.in.read(bArr, i, i2);
        if (i3 > 0) {
            this.a.write(bArr, i, i3);
        }
        return i3;
    }
}
