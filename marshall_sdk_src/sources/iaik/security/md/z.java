package iaik.security.md;

import iaik.utils.CryptoUtils;

/* JADX INFO: loaded from: classes.dex */
abstract class z extends AbstractC0047p {
    static final long[] k = {1, 32898, -9223372036854742902L, -9223372034707259392L, 32907, 2147483649L, -9223372034707259263L, -9223372036854743031L, 138, 136, 2147516425L, 2147483658L, 2147516555L, -9223372036854775669L, -9223372036854742903L, -9223372036854743037L, -9223372036854743038L, -9223372036854775680L, 32778, -9223372034707292150L, -9223372034707259263L, -9223372036854742912L, 2147483649L, -9223372034707259384L};
    long[] l;
    final int m;
    long[] n;
    byte[] o;

    z(int i, int i2, boolean z) {
        this(i, i2, z, false);
    }

    z(int i, int i2, boolean z, boolean z2) {
        super(i, i2, z, z2);
        this.l = new long[25];
        this.o = new byte[32];
        this.m = i >>> 3;
        this.n = new long[i2 >>> 6];
        engineReset();
    }

    static long a(long j, int i) {
        return (j >>> (64 - i)) | (j << i);
    }

    private void b(int i) {
        byte b;
        if (i > 2) {
            engineUpdate(h[0]);
            engineUpdate(a, 1, i - 2);
            b = h[1];
        } else if (i == 0) {
            engineUpdate(h[0]);
            engineUpdate(a, 1, this.d - 2);
            b = h[1];
        } else {
            if (i != 1) {
                engineUpdate(h, 0, 2);
                return;
            }
            b = -122;
        }
        engineUpdate(b);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a() {
        byte b;
        int i = ((int) (((((long) this.d) - this.f) % ((long) this.d)) + ((long) this.d))) % this.d;
        if (this.j) {
            b(i);
            return;
        }
        if (i > 2) {
            engineUpdate(g[0]);
            engineUpdate(a, 1, i - 2);
            b = g[1];
        } else if (i == 0) {
            engineUpdate(g[0]);
            engineUpdate(a, 1, this.d - 2);
            b = g[1];
        } else {
            if (i != 1) {
                engineUpdate(g, 0, 2);
                return;
            }
            b = -127;
        }
        engineUpdate(b);
    }

    void a(byte[] bArr, int i, int i2, int i3) {
        long[] jArr = this.l;
        jArr[1] = ~jArr[1];
        jArr[2] = ~jArr[2];
        jArr[8] = ~jArr[8];
        jArr[12] = ~jArr[12];
        jArr[17] = ~jArr[17];
        jArr[20] = ~jArr[20];
        if (this.c != 28) {
            CryptoUtils.spreadLongsToBytesLE(this.l, 0, bArr, i, i3);
        } else {
            CryptoUtils.spreadLongsToBytesLE(this.l, 0, this.o, 0, i3 + 1);
            System.arraycopy(this.o, 0, bArr, i, i2);
        }
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void b(byte[] bArr, int i) {
        a(bArr, i, this.c, this.m);
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        z zVar = (z) super.clone();
        zVar.l = (long[]) this.l.clone();
        zVar.n = (long[]) this.n.clone();
        zVar.o = (byte[]) this.o.clone();
        return zVar;
    }

    @Override // iaik.security.md.AbstractC0047p, iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    protected void engineReset() {
        super.engineReset();
        CryptoUtils.zeroBlock(this.l);
        long[] jArr = this.l;
        jArr[20] = -1;
        jArr[17] = -1;
        jArr[12] = -1;
        jArr[8] = -1;
        jArr[2] = -1;
        jArr[1] = -1;
    }
}
