package iaik.security.ssl;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.zip.DataFormatException;
import javax.crypto.Cipher;

/* JADX INFO: loaded from: classes.dex */
class ag extends OutputStream implements ak {
    private byte[] A;
    private SecureRandom B;
    private boolean C = true;
    int a;
    int b;
    int c;
    int d;
    int e;
    boolean f;
    private byte[] g;
    private byte[] h;
    private int i;
    private OutputStream j;
    private Cipher k;
    private ad l;
    private a m;
    private CompressionMethod n;
    private int o;
    private SSLTransport p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private av z;

    ag(SSLTransport sSLTransport, int i, int i2, boolean z) throws IOException {
        this.g = new byte[i];
        if (!z) {
            this.j = new BufferedOutputStream(sSLTransport.f(), i >= 8192 ? 8192 : i);
        } else {
            this.j = sSLTransport.f();
        }
        this.p = sSLTransport;
        this.r = 1;
        this.u = 0;
        this.w = i2;
        d(SSLContext.VERSION_SSL30);
        this.y = -1;
        this.f = this.p.b != null;
    }

    void a(av avVar) {
        this.z = avVar;
    }

    void d(int i) {
        this.q = i;
        a();
        d();
    }

    int c() {
        return this.u;
    }

    void a() {
        if (this.q >= 768) {
            this.t = 5;
            this.v = this.w;
            if (this.m != null) {
                this.B = this.p.getContext().getRandomGenerator();
                this.v -= this.m.a() + 8;
                return;
            }
            Cipher cipher = this.k;
            if (cipher != null) {
                int blockSize = cipher.getBlockSize();
                this.s = blockSize;
                if (this.q < 770 || this.r != 2) {
                    return;
                }
                byte[] bArr = this.A;
                if (bArr == null || bArr.length != blockSize) {
                    this.A = new byte[blockSize];
                }
                this.B = this.p.getContext().getRandomGenerator();
                int i = this.t;
                int i2 = this.s;
                this.t = i + i2;
                this.v -= i2;
                return;
            }
            return;
        }
        ad adVar = this.l;
        int iA = adVar != null ? adVar.a() + 2 : 2;
        if (this.r == 2) {
            iA++;
            this.v = 8192;
        } else {
            this.v = 16384;
        }
        this.t = iA;
    }

    void c(int i) {
        this.w = i;
        this.v = i;
    }

    int b() {
        return this.g.length;
    }

    void d() {
        int i = this.t;
        this.i = i;
        this.o = i;
        this.x = 0;
    }

    @Override // java.io.OutputStream
    public void write(int i) throws IOException {
        byte[] bArr = this.g;
        int i2 = this.i;
        this.i = i2 + 1;
        bArr[i2] = (byte) i;
        int i3 = this.x + 1;
        this.x = i3;
        if (i3 >= this.v) {
            g();
        }
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) throws IOException {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        while (i2 > 0) {
            int iA = Utils.a(i2, this.v - this.x);
            System.arraycopy(bArr, i, this.g, this.i, iA);
            i2 -= iA;
            i += iA;
            this.i += iA;
            int i3 = this.x + iA;
            this.x = i3;
            if (i3 >= this.v) {
                g();
            }
        }
    }

    private void g() throws IOException {
        int i;
        if (this.p.a == 3) {
            i = 23;
        } else if (this.p.a == 2 || this.p.a == 4) {
            i = 22;
            if (this.z != null) {
                try {
                    e();
                } catch (ArrayIndexOutOfBoundsException unused) {
                    throw new SSLException("Invalid SSL message (EOF)!");
                }
            }
        } else {
            i = 21;
        }
        b(i);
    }

    public void g(int i) throws IOException {
        write(i);
    }

    @Override // iaik.security.ssl.ak
    public void a(int i) throws IOException {
        write((byte) (i >> 8));
        write((byte) i);
    }

    public void e(int i) throws IOException {
        write((byte) (i >> 16));
        write((byte) (i >> 8));
        write((byte) i);
    }

    public void f(int i) throws IOException {
        write((byte) (i >> 24));
        write((byte) (i >> 16));
        write((byte) (i >> 8));
        write((byte) i);
    }

    public void c(byte[] bArr) throws IOException {
        if (bArr == null || bArr.length == 0) {
            g(0);
        } else {
            g(bArr.length);
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
            e(0);
        } else {
            e(bArr.length);
            write(bArr, 0, bArr.length);
        }
    }

    void a(Cipher cipher, ad adVar, a aVar, int i, CompressionMethod compressionMethod) {
        this.k = cipher;
        this.l = adVar;
        this.m = aVar;
        this.r = i;
        if (compressionMethod.getID() != 0) {
            this.n = compressionMethod;
        }
        a();
        d();
    }

    void e() {
        int i = this.i;
        int i2 = this.o;
        int i3 = i - i2;
        if (i3 > 0) {
            this.z.update(this.g, i2, i3);
            this.o = this.i;
        }
    }

    void b(int i, int i2) throws IOException {
        if (this.p.a <= 2) {
            g(0);
            a(i2);
            b(257);
        }
        if (i == 2) {
            this.p.a(false, false);
        }
    }

    synchronized void a(int i, int i2) throws IOException {
        d();
        if (this.p.a < 6) {
            int i3 = this.q;
            if (i3 < 768) {
                b(2, 0);
                return;
            }
            b bVar = new b(i3, i, i2);
            if (this.f) {
                SSLTransport sSLTransport = this.p;
                StringBuffer stringBuffer = new StringBuffer("Sending alert: ");
                stringBuffer.append(bVar);
                sSLTransport.a(stringBuffer.toString());
            }
            bVar.a(this);
            b(21);
        }
        if (i == 2) {
            this.p.a(false, false);
        }
    }

    void b(int i) throws IOException {
        a(i, true, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00c8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void a(int r9, boolean r10, boolean r11) throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 210
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.ag.a(int, boolean, boolean):void");
    }

    private void f() throws IOException {
        int iA;
        int i = this.x;
        try {
            if (this.k != null) {
                if (this.r == 2) {
                    iA = a(this.g, this.t, i);
                    this.g[2] = (byte) iA;
                    i += iA;
                    this.i += iA;
                } else {
                    iA = -1;
                }
                this.l.a(this.g, this.t, i);
                int iA2 = this.l.a();
                i += iA2;
                Cipher cipher = this.k;
                byte[] bArr = this.g;
                int i2 = this.t;
                cipher.update(bArr, i2 - iA2, i, bArr, i2 - iA2);
            } else {
                iA = -1;
            }
            if (iA == -1) {
                byte[] bArr2 = this.g;
                bArr2[0] = (byte) ((i >> 8) | 128);
                bArr2[1] = (byte) (i & 255);
            } else {
                byte[] bArr3 = this.g;
                bArr3[0] = (byte) (i >> 8);
                bArr3[1] = (byte) (i & 255);
            }
            this.j.write(this.g, 0, this.i);
            this.j.flush();
            d();
            this.u++;
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("Unable to encrypt SSL message: ");
            stringBuffer.append(e);
            throw new SSLException(stringBuffer.toString());
        }
    }

    private void b(int i, boolean z, boolean z2) throws IOException {
        a(i, z, z2, this.g, this.x);
    }

    private void a(int i, boolean z, boolean z2, byte[] bArr, int i2) throws IOException {
        int iUpdate;
        try {
            int i3 = this.t;
            bArr[0] = (byte) i;
            try {
                try {
                    CompressionMethod compressionMethod = this.n;
                    int iCompress = compressionMethod != null ? compressionMethod.compress(bArr, i3, i2, bArr, i3) : i2;
                    a aVar = this.m;
                    if (aVar != null) {
                        iUpdate = aVar.a(bArr, i3, iCompress, bArr, i3, this.B);
                    } else {
                        ad adVar = this.l;
                        if (adVar != null) {
                            iCompress = adVar.a(bArr, i3, iCompress);
                        }
                        if (this.k != null) {
                            if (this.r == 2) {
                                if (this.q >= 770) {
                                    this.B.nextBytes(this.A);
                                    int i4 = this.s;
                                    i3 -= i4;
                                    System.arraycopy(this.A, 0, bArr, i3, i4);
                                    iCompress += this.s;
                                }
                                iCompress += a(bArr, i3, iCompress);
                            }
                            iUpdate = this.k.update(bArr, i3, iCompress, bArr, i3);
                        } else {
                            iUpdate = iCompress;
                        }
                    }
                    int i5 = this.q;
                    bArr[1] = (byte) (i5 >> 8);
                    bArr[2] = (byte) i5;
                    bArr[3] = (byte) (iUpdate >> 8);
                    bArr[4] = (byte) iUpdate;
                    this.j.write(bArr, 0, i3 + iUpdate);
                    if (z) {
                        this.j.flush();
                    }
                    if (i == 23) {
                        this.a++;
                        this.b += iUpdate + 5;
                        this.c += i2;
                    } else {
                        this.d++;
                        this.e += iUpdate + 5;
                    }
                    if (z2) {
                        d();
                    }
                } catch (DataFormatException e) {
                    StringBuffer stringBuffer = new StringBuffer("Unable to compress SSL message: ");
                    stringBuffer.append(e.toString());
                    throw new SSLException(stringBuffer.toString());
                }
            } catch (Exception e2) {
                StringBuffer stringBuffer2 = new StringBuffer("Unable to encrypt SSL message: ");
                stringBuffer2.append(e2.toString());
                throw new SSLException(stringBuffer2.toString());
            }
        } catch (IOException e3) {
            if (this.p.a < 5) {
                if (this.f) {
                    SSLTransport sSLTransport = this.p;
                    StringBuffer stringBuffer3 = new StringBuffer("Exception sending message: ");
                    stringBuffer3.append(e3.toString());
                    sSLTransport.a(stringBuffer3.toString());
                }
                this.p.a(false, false);
            }
            throw e3;
        }
    }

    private int a(byte[] bArr, int i, int i2) {
        int iH = h(i2) - 1;
        int i3 = this.q >= 769 ? iH : 0;
        for (int i4 = 0; i4 < iH; i4++) {
            bArr[i + i2 + i4] = (byte) i3;
        }
        bArr[i + i2 + iH] = (byte) iH;
        return iH + 1;
    }

    private int h(int i) {
        int i2 = this.s;
        return i2 - (i % i2);
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() throws IOException {
        b(23);
    }
}
