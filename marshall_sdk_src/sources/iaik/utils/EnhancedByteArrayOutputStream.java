package iaik.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/* JADX INFO: loaded from: classes2.dex */
public class EnhancedByteArrayOutputStream extends ByteArrayOutputStream {
    public EnhancedByteArrayOutputStream() {
    }

    public EnhancedByteArrayOutputStream(int i) {
        super(i);
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.buf, 0, this.count);
    }

    @Override // java.io.ByteArrayOutputStream
    public byte[] toByteArray() {
        return this.buf.length == this.count ? this.buf : super.toByteArray();
    }
}
