package iaik.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/* JADX INFO: loaded from: classes2.dex */
public class ExtByteArrayOutputStream extends ByteArrayOutputStream {
    public ExtByteArrayOutputStream() {
    }

    public ExtByteArrayOutputStream(int i) {
        super(i);
    }

    public ByteArrayInputStream getInputStream() {
        return new ByteArrayInputStream(this.buf, 0, this.count);
    }

    public byte[] getInternalByteArray() {
        return this.buf;
    }
}
