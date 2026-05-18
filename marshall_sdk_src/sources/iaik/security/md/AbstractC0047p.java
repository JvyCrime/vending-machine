package iaik.security.md;

import iaik.utils.CryptoUtils;

/* JADX INFO: renamed from: iaik.security.md.p, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
abstract class AbstractC0047p extends AbstractMessageDigest {
    static final byte[] g = {1, -128};
    static final byte[] h = {6, -128};
    static final byte[] i = {31, -128};
    boolean j;

    AbstractC0047p(int i2, int i3, boolean z, boolean z2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(z ? z2 ? "SHAKE" : "SHA3-" : "KECCAK");
        stringBuffer.append(i2 << 3);
        super(stringBuffer.toString(), i2, i3 >>> 3);
        this.j = z;
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    protected void engineReset() {
        this.f = 0L;
        CryptoUtils.zeroBlock(this.b);
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    protected void engineUpdate(byte[] bArr, int i2, int i3) {
        int i4 = ((int) this.f) % this.d;
        this.f += (long) i3;
        if (i4 != 0) {
            int i5 = this.d - i4;
            if (i5 > i3) {
                System.arraycopy(bArr, i2, this.b, i4, i3);
                return;
            }
            System.arraycopy(bArr, i2, this.b, i4, i5);
            a(this.b, 0);
            i3 -= i5;
            if (i3 == 0) {
                return;
            } else {
                i2 += i5;
            }
        }
        while (i3 >= this.d) {
            a(bArr, i2);
            i2 += this.d;
            i3 -= this.d;
        }
        if (i3 > 0) {
            System.arraycopy(bArr, i2, this.b, 0, i3);
        }
    }
}
