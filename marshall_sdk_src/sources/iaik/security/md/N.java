package iaik.security.md;

import iaik.utils.CryptoUtils;

/* JADX INFO: loaded from: classes.dex */
abstract class N extends z implements H {
    private int p;
    private long[] q;
    private int r;

    N(int i, int i2) {
        super(i, i2, true, true);
        this.p = this.d >>> 3;
        this.q = new long[6];
    }

    @Override // iaik.security.md.z, iaik.security.md.AbstractMessageDigest
    void a() {
        byte b;
        int i = ((int) (((((long) this.d) - this.f) % ((long) this.d)) + ((long) this.d))) % this.d;
        if (i > 2) {
            engineUpdate(i[0]);
            engineUpdate(a, 1, i - 2);
            b = i[1];
        } else if (i == 0) {
            engineUpdate(i[0]);
            engineUpdate(a, 1, this.d - 2);
            b = i[1];
        } else {
            if (i != 1) {
                engineUpdate(i, 0, 2);
                return;
            }
            b = -97;
        }
        engineUpdate(b);
    }

    @Override // iaik.security.md.H
    public void a(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("outputLength must be > 0!");
        }
        this.r = i;
    }

    @Override // iaik.security.md.H
    public void a(byte[] bArr, boolean z) {
        if (z) {
            a();
        } else {
            a((byte[]) null, 0);
        }
        this.q[0] = this.l[1];
        this.q[1] = this.l[2];
        this.q[2] = this.l[8];
        this.q[3] = this.l[12];
        this.q[4] = this.l[17];
        this.q[5] = this.l[20];
        a(bArr, 0, this.d, this.p);
        this.l[1] = this.q[0];
        this.l[2] = this.q[1];
        this.l[8] = this.q[2];
        this.l[12] = this.q[3];
        this.l[17] = this.q[4];
        this.l[20] = this.q[5];
    }

    @Override // iaik.security.md.z, iaik.security.md.AbstractMessageDigest
    void b(byte[] bArr, int i) {
        a(this.b, true);
        int length = bArr.length - i;
        while (length >= this.d) {
            System.arraycopy(this.b, 0, bArr, i, this.d);
            length -= this.d;
            i += this.d;
            a(this.b, false);
        }
        System.arraycopy(this.b, 0, bArr, i, length);
    }

    @Override // iaik.security.md.z, iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        N n = (N) super.clone();
        n.q = (long[]) this.q.clone();
        n.r = this.r;
        return n;
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    protected byte[] engineDigest() {
        int i = this.r;
        if (i < 0) {
            i = this.c;
        }
        byte[] bArr = new byte[i];
        b(bArr, 0);
        engineReset();
        return bArr;
    }

    @Override // iaik.security.md.z, iaik.security.md.AbstractC0047p, iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    protected void engineReset() {
        super.engineReset();
        long[] jArr = this.q;
        if (jArr != null) {
            CryptoUtils.zeroBlock(jArr);
        }
    }
}
