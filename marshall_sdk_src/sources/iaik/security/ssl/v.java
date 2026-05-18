package iaik.security.ssl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
class v extends ByteArrayOutputStream implements ak {
    static final byte[] a = new byte[2];

    public v() {
    }

    v(int i) {
        super(i);
    }

    byte[] a() {
        return ((ByteArrayOutputStream) this).buf;
    }

    synchronized void a(int i, int i2) {
        if (i2 >= ((ByteArrayOutputStream) this).count) {
            throw new IndexOutOfBoundsException("Writing into buffer only allowed for pos < count!");
        }
        ((ByteArrayOutputStream) this).buf[i2] = (byte) i;
    }

    public void d(int i) throws IOException {
        write(i);
    }

    public void c(int i, int i2) throws IOException {
        a(i, i2);
    }

    @Override // iaik.security.ssl.ak
    public void a(int i) throws IOException {
        write((byte) (i >> 8));
        write((byte) i);
    }

    public void b(int i, int i2) throws IOException {
        a((byte) (i >> 8), i2);
        a((byte) i, i2 + 1);
    }

    public void b(int i) throws IOException {
        write((byte) (i >> 16));
        write((byte) (i >> 8));
        write((byte) i);
    }

    public void c(int i) throws IOException {
        write((byte) (i >> 24));
        write((byte) (i >> 16));
        write((byte) (i >> 8));
        write((byte) i);
    }

    public void c(byte[] bArr) throws IOException {
        if (bArr == null || bArr.length == 0) {
            d(0);
        } else {
            d(bArr.length);
            write(bArr, 0, bArr.length);
        }
    }

    public void a(byte[] bArr) throws IOException {
        if (bArr == null || bArr.length == 0) {
            a(0);
        } else {
            a(bArr.length);
            write(bArr, 0, bArr.length);
        }
    }

    public void b(byte[] bArr) throws IOException {
        if (bArr == null || bArr.length == 0) {
            b(0);
        } else {
            b(bArr.length);
            write(bArr, 0, bArr.length);
        }
    }
}
