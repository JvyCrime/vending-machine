package iaik.security.ssl;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import javax.crypto.Cipher;

/* JADX INFO: loaded from: classes.dex */
class ab extends ByteArrayInputStream {
    InputStream a;
    boolean b;
    int c;
    int d;
    int e;
    int f;
    int g;
    boolean h;
    private SSLTransport i;
    private ag j;
    private int k;
    private int l;
    private Cipher m;
    private ad n;
    private a o;
    private CompressionMethod p;
    private int q;
    private int r;
    private int s;
    private int t;
    private av u;
    private byte[] v;
    private boolean w;

    ab(SSLTransport sSLTransport, int i, boolean z) throws IOException {
        super(new byte[i]);
        this.i = sSLTransport;
        this.a = sSLTransport.e();
        this.j = this.i.c();
        this.k = 1;
        this.q = 0;
        this.w = z;
        this.h = this.i.b != null;
    }

    ab(byte[] bArr) {
        super(bArr);
    }

    void a(av avVar) {
        this.u = avVar;
    }

    void a(int i) {
        this.q = i;
        if (i >= 768) {
            this.k = 3;
        } else {
            this.k = 2;
        }
    }

    int b() {
        return this.q;
    }

    int a() {
        return this.t;
    }

    int k() throws IOException {
        return d();
    }

    int f() throws IOException {
        return (k() << 8) | k();
    }

    int h() throws IOException {
        return (f() << 8) | k();
    }

    int j() throws IOException {
        return (f() << 16) | f();
    }

    byte[] l() throws IOException {
        byte[] bArr = new byte[k()];
        a(bArr);
        return bArr;
    }

    byte[] g() throws IOException {
        byte[] bArr = new byte[f()];
        a(bArr);
        return bArr;
    }

    byte[] i() throws IOException {
        byte[] bArr = new byte[h()];
        a(bArr);
        return bArr;
    }

    int d() throws IOException {
        int i = read();
        if (i == -1) {
            SSLTransport sSLTransport = this.i;
            if (sSLTransport != null && ((sSLTransport.a == 2 || this.i.a == 4) && this.u != null)) {
                try {
                    m();
                } catch (ArrayIndexOutOfBoundsException unused) {
                    throw new SSLException("Invalid SSL message (EOF)!");
                }
            }
            e();
            i = read();
        }
        if (i != -1) {
            return i & 255;
        }
        throw new SSLException("Invalid SSL message (EOF)!");
    }

    int a(byte[] bArr) throws IOException {
        return a(bArr, 0, bArr.length);
    }

    int a(byte[] bArr, int i, int i2) throws IOException {
        if (i2 > 0) {
            int i3 = read(bArr, i, i2);
            i = i3 != -1 ? i3 : 0;
            while (i < i2) {
                SSLTransport sSLTransport = this.i;
                if (sSLTransport != null && ((sSLTransport.a == 2 || this.i.a == 4) && this.u != null)) {
                    try {
                        m();
                    } catch (ArrayIndexOutOfBoundsException unused) {
                        throw new SSLException("Invalid SSL message (EOF)!");
                    }
                }
                e();
                int i4 = read(bArr, i + i, i2 - i);
                if (i4 == -1) {
                    throw new SSLException("Invalid SSL message (EOF)!");
                }
                i += i4;
            }
        }
        return i;
    }

    void a(Cipher cipher, ad adVar, a aVar, int i, CompressionMethod compressionMethod) {
        this.m = cipher;
        if (cipher != null) {
            int blockSize = cipher.getBlockSize();
            this.s = blockSize;
            this.v = new byte[blockSize];
        }
        this.n = adVar;
        this.o = aVar;
        this.r = i;
        if (compressionMethod.getID() != 0) {
            this.p = compressionMethod;
        }
    }

    void m() {
        int i = ((ByteArrayInputStream) this).pos - this.l;
        if (i > 0) {
            this.u.update(((ByteArrayInputStream) this).buf, this.l, i);
            this.l = ((ByteArrayInputStream) this).pos;
        }
    }

    int c() throws IOException {
        return this.a.available();
    }

    int e() throws IOException {
        int iB;
        boolean z = true;
        this.b = true;
        if (this.k == 1) {
            Utils.a(this.a, ((ByteArrayInputStream) this).buf, 0, 2);
            this.b = false;
            if (((ByteArrayInputStream) this).buf[0] == 22 || ((ByteArrayInputStream) this).buf[0] == 21) {
                this.k = 3;
            } else if ((((ByteArrayInputStream) this).buf[0] & 128) != 0) {
                this.k = 2;
            } else {
                throw new SSLException("Invalid SSL message, peer seems to be talking plain!");
            }
        } else {
            z = false;
        }
        int i = this.k;
        if (i == 2) {
            return a(z);
        }
        if (i == 3) {
            do {
                iB = b(z);
                if (iB != 23) {
                    break;
                }
            } while (((ByteArrayInputStream) this).count - ((ByteArrayInputStream) this).pos == 0);
            return iB;
        }
        throw new SSLException("Unknown record version!");
    }

    private int a(boolean z) throws IOException {
        int i;
        int i2;
        int i3 = 2;
        if (!z) {
            try {
                Utils.a(this.a, ((ByteArrayInputStream) this).buf, 0, 2);
            } catch (EOFException unused) {
                this.i.a(true, false);
                return 257;
            }
        }
        this.b = false;
        int i4 = ((((ByteArrayInputStream) this).buf[0] & 255) << 8) | (((ByteArrayInputStream) this).buf[1] & 255);
        if ((i4 & 32768) != 0) {
            i = i4 & 32767;
            if (i > ((ByteArrayInputStream) this).buf.length) {
                byte[] bArr = new byte[32768];
                bArr[0] = ((ByteArrayInputStream) this).buf[0];
                bArr[1] = ((ByteArrayInputStream) this).buf[1];
                ((ByteArrayInputStream) this).buf = bArr;
            }
            Utils.a(this.a, ((ByteArrayInputStream) this).buf, 2, i);
            i2 = 0;
        } else {
            i = i4 & 16383;
            Utils.a(this.a, ((ByteArrayInputStream) this).buf, 2, i + 1);
            i2 = ((ByteArrayInputStream) this).buf[2] & 255;
            i3 = 3;
        }
        ((ByteArrayInputStream) this).pos = i3;
        Cipher cipher = this.m;
        if (cipher != null) {
            try {
                cipher.update(((ByteArrayInputStream) this).buf, ((ByteArrayInputStream) this).pos, i, ((ByteArrayInputStream) this).buf, ((ByteArrayInputStream) this).pos);
                int iA = this.n.a();
                ((ByteArrayInputStream) this).pos = i3 + iA;
                int i5 = i - iA;
                if (this.n.a(((ByteArrayInputStream) this).buf, ((ByteArrayInputStream) this).pos, i5, 0) < 0) {
                    throw new SSLException("V2 mac verification error");
                }
                i = i5 - i2;
            } catch (Exception e) {
                StringBuffer stringBuffer = new StringBuffer("V2 message decryption error: ");
                stringBuffer.append(e);
                throw new SSLException(stringBuffer.toString());
            }
        }
        ((ByteArrayInputStream) this).count = ((ByteArrayInputStream) this).pos + i;
        this.l = i3;
        this.t++;
        return 257;
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x0176 A[Catch: Exception -> 0x01c9, SSLException -> 0x01e2, DataFormatException -> 0x01e4, TryCatch #3 {Exception -> 0x01c9, blocks: (B:26:0x00b8, B:86:0x0194, B:88:0x0198, B:33:0x00cd, B:34:0x00db, B:35:0x00dc, B:37:0x00e0, B:41:0x00e9, B:45:0x00f3, B:48:0x00f9, B:50:0x0103, B:52:0x0108, B:63:0x012c, B:65:0x014d, B:67:0x015f, B:74:0x0172, B:76:0x0176, B:79:0x0180, B:83:0x018e, B:84:0x0191, B:94:0x01ba, B:95:0x01c8, B:55:0x010f, B:56:0x0119, B:60:0x011f, B:61:0x0129, B:44:0x00ef), top: B:110:0x00b8 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0198 A[Catch: Exception -> 0x01c9, SSLException -> 0x01e2, DataFormatException -> 0x01e4, TRY_LEAVE, TryCatch #3 {Exception -> 0x01c9, blocks: (B:26:0x00b8, B:86:0x0194, B:88:0x0198, B:33:0x00cd, B:34:0x00db, B:35:0x00dc, B:37:0x00e0, B:41:0x00e9, B:45:0x00f3, B:48:0x00f9, B:50:0x0103, B:52:0x0108, B:63:0x012c, B:65:0x014d, B:67:0x015f, B:74:0x0172, B:76:0x0176, B:79:0x0180, B:83:0x018e, B:84:0x0191, B:94:0x01ba, B:95:0x01c8, B:55:0x010f, B:56:0x0119, B:60:0x011f, B:61:0x0129, B:44:0x00ef), top: B:110:0x00b8 }] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int b(boolean r22) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 538
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.ab.b(boolean):int");
    }

    private int a(byte[] bArr, int i, int i2, int i3) throws IOException {
        int i4 = (i + i2) - 1;
        int i5 = bArr[i4];
        int i6 = (i5 & 255) + 1;
        if (i3 + i6 > i2) {
            i6 = 0;
        }
        if (this.q < 769) {
            return i2 - (i6 <= this.s ? i6 : 0);
        }
        int i7 = (i4 - i6) + 1;
        byte b = 0;
        while (i7 <= i4) {
            b = (byte) (b | (bArr[i7] ^ i5));
            i7++;
        }
        int i8 = (256 - i6) + i7;
        int i9 = b == 0 ? i6 : 0;
        while (i7 < i8) {
            b = (byte) ((bArr[i7] ^ i5) | b);
            i7++;
        }
        bArr[i4] = b;
        return i2 - i9;
    }

    void b(byte[] bArr) {
        if (bArr.length >= ((ByteArrayInputStream) this).buf.length) {
            ((ByteArrayInputStream) this).buf = bArr;
        } else {
            System.arraycopy(bArr, 0, ((ByteArrayInputStream) this).buf, 0, bArr.length);
        }
        ((ByteArrayInputStream) this).pos = 0;
        ((ByteArrayInputStream) this).count = bArr.length;
    }
}
