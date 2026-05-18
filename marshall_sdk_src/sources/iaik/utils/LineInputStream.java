package iaik.utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/* JADX INFO: loaded from: classes2.dex */
public class LineInputStream extends FilterInputStream {
    int a;
    private final byte[] b;
    protected byte[] buffer;
    private final byte[] c;
    private final byte[] d;
    private byte[] e;

    public LineInputStream(InputStream inputStream) {
        super(inputStream);
        this.b = new byte[]{13};
        this.c = new byte[]{10};
        this.d = new byte[]{13, 10};
        this.a = -1;
        this.buffer = new byte[128];
    }

    public LineInputStream(InputStream inputStream, int i) {
        super(inputStream);
        this.b = new byte[]{13};
        this.c = new byte[]{10};
        this.d = new byte[]{13, 10};
        this.a = -1;
        if (i <= 0) {
            throw new IllegalArgumentException("Buffer size must be poitive!");
        }
        this.buffer = new byte[i];
    }

    private int a() throws IOException {
        int i = 0;
        while (true) {
            int i2 = read();
            if (i2 == -1) {
                if (i > 0) {
                    return i;
                }
                return -1;
            }
            if (i2 == 13) {
                int i3 = read();
                if (i3 == 10 || i3 == -1) {
                    this.e = this.d;
                } else {
                    if (!(this.in instanceof PushbackInputStream)) {
                        this.in = new PushbackInputStream(this.in);
                    }
                    ((PushbackInputStream) this.in).unread(i3);
                    this.e = this.b;
                }
                return i;
            }
            if (i2 == 10) {
                this.e = this.c;
                return i;
            }
            byte[] bArr = this.buffer;
            int i4 = i + 1;
            bArr[i] = (byte) i2;
            if (i4 == bArr.length) {
                byte[] bArr2 = new byte[bArr.length * 2];
                System.arraycopy(bArr, 0, bArr2, 0, i4);
                this.buffer = bArr2;
            }
            i = i4;
        }
    }

    public byte[] getBuffer() throws IOException {
        int i = this.a;
        if (i < 0) {
            throw new IOException("No data in buffer!");
        }
        byte[] bArr = new byte[i];
        System.arraycopy(this.buffer, 0, bArr, 0, i);
        return bArr;
    }

    public byte[] getLineDelimiter() {
        return this.e;
    }

    public String readLine() throws IOException {
        int iA = a();
        this.a = iA;
        if (iA == -1) {
            return null;
        }
        return Util.toASCIIString(this.buffer, 0, iA);
    }
}
