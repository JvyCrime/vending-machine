package iaik.security.md;

/* JADX INFO: loaded from: classes.dex */
abstract class M extends u implements H {
    private int k;

    M(int i, int i2) {
        super(i, i2, true, true);
    }

    @Override // iaik.security.md.u, iaik.security.md.AbstractMessageDigest
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
        this.k = i;
    }

    @Override // iaik.security.md.H
    public void a(byte[] bArr, boolean z) {
        if (z) {
            a();
        } else {
            a((byte[]) null, 0);
        }
        a(bArr, 0, this.d);
    }

    @Override // iaik.security.md.u, iaik.security.md.AbstractMessageDigest
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

    @Override // iaik.security.md.u, iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        M m = (M) super.clone();
        m.k = this.k;
        return m;
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    protected byte[] engineDigest() {
        int i = this.k;
        if (i < 0) {
            i = this.c;
        }
        byte[] bArr = new byte[i];
        b(bArr, 0);
        engineReset();
        return bArr;
    }
}
