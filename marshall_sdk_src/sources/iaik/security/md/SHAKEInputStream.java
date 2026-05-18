package iaik.security.md;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class SHAKEInputStream extends InputStream {
    private int a;
    private byte[] b;
    private int c;
    private int d;
    private int e;
    private boolean f;
    private AbstractC0047p g;
    private int h;
    private int i;
    private boolean j;

    SHAKEInputStream(AbstractC0047p abstractC0047p) {
        this(abstractC0047p, -1);
    }

    SHAKEInputStream(AbstractC0047p abstractC0047p, int i) {
        Objects.requireNonNull(abstractC0047p, "shake MessageDigest engine must not be null!");
        if (!(abstractC0047p instanceof H)) {
            throw new IllegalArgumentException("Expected RawSHAKE instance!");
        }
        this.g = abstractC0047p;
        this.f = false;
        this.h = 0;
        int iB = abstractC0047p.b();
        this.a = iB;
        this.b = new byte[iB];
        this.d = 0;
        this.e = 0;
        this.c = 0;
        if (i == -1) {
            this.i = -1;
        } else {
            setMaxReadSize(i);
        }
        this.j = false;
    }

    private int a() throws IOException {
        if (this.h == 2) {
            ((H) this.g).a(this.b, false);
        } else {
            ((H) this.g).a(this.b, true);
            this.h = 2;
        }
        int i = this.a;
        this.d = i;
        this.c = 0;
        return i;
    }

    private int a(int i) {
        if (i == -1) {
            i = this.a;
        }
        if (this.j) {
            return -1;
        }
        int i2 = this.i;
        if (i2 == -1 || (i = Math.min(i2 - this.e, i)) != 0) {
            return i;
        }
        this.j = true;
        return -1;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        if (this.f) {
            throw new IOException("Stream already closed!");
        }
        int iA = a(-1);
        if (iA == -1) {
            return 0;
        }
        return iA;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.f = true;
        reset();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this.f) {
            throw new IOException("Stream already closed!");
        }
        if (a(1) == -1) {
            return -1;
        }
        do {
            int i = this.d;
            if (i > 0) {
                this.d = i - 1;
                if (this.i != -1) {
                    this.e++;
                }
                byte[] bArr = this.b;
                int i2 = this.c;
                this.c = i2 + 1;
                return bArr[i2] & 255;
            }
        } while (a() != -1);
        return -1;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (this.f) {
            throw new IOException("Stream already closed!");
        }
        int iA = a(i2);
        if (iA == -1) {
            return -1;
        }
        int i3 = 0;
        do {
            int i4 = this.d;
            if (i4 >= iA) {
                System.arraycopy(this.b, this.c, bArr, i + i3, iA);
                this.d -= iA;
                this.c += iA;
                int i5 = i3 + iA;
                if (this.i != -1) {
                    this.e += iA;
                }
                return i5;
            }
            if (i4 > 0) {
                System.arraycopy(this.b, this.c, bArr, i + i3, i4);
                int i6 = this.d;
                iA -= i6;
                i3 += i6;
                if (this.i != -1) {
                    this.e += i6;
                }
                this.d = 0;
            }
        } while (a() != -1);
        if (this.i != -1) {
            this.e += i3;
        }
        if (i3 == 0) {
            return -1;
        }
        return i3;
    }

    @Override // java.io.InputStream
    public void reset() {
        this.f = false;
        this.h = 0;
        this.d = 0;
        this.c = 0;
        this.i = -1;
        this.e = 0;
        this.j = false;
        this.g.engineReset();
    }

    public void setMaxReadSize(int i) {
        if (this.h > 1) {
            throw new IllegalStateException("Stream already in read phase!");
        }
        if (i <= 0) {
            throw new IllegalArgumentException("maxReadSize must be positive!");
        }
        this.i = i;
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        return read(new byte[(int) j]);
    }

    public void update(byte b) {
        if (this.f) {
            throw new IllegalStateException("Stream already closed!");
        }
        if (this.h > 1) {
            throw new IllegalStateException("Update phase already closed!");
        }
        this.h = 1;
        this.g.engineUpdate(b);
    }

    public void update(byte[] bArr) {
        update(bArr, 0, bArr.length);
    }

    public void update(byte[] bArr, int i, int i2) {
        if (this.f) {
            throw new IllegalStateException("Stream already closed!");
        }
        if (this.h > 1) {
            throw new IllegalStateException("Update phase already closed!");
        }
        if (bArr == null) {
            throw new IllegalArgumentException("input must not be null");
        }
        if (bArr.length - i < i2) {
            throw new IllegalArgumentException("input array too short");
        }
        this.h = 1;
        this.g.engineUpdate(bArr, i, i2);
    }
}
