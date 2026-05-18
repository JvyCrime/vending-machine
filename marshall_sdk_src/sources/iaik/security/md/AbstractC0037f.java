package iaik.security.md;

import iaik.utils.CryptoUtils;

/* JADX INFO: renamed from: iaik.security.md.f, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
abstract class AbstractC0037f extends AbstractMessageDigest {
    final int g;
    byte[] h;

    AbstractC0037f(int i, int i2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Groestl");
        stringBuffer.append(i << 3);
        super(stringBuffer.toString(), i, i2);
        this.h = new byte[8];
        this.g = i2 << 3;
    }

    @Override // iaik.security.md.AbstractMessageDigest
    protected void a() {
        long j = this.f << 3;
        int i = this.g;
        int i2 = ((((int) ((-j) - 65)) % i) + i) % i;
        long j2 = ((j + ((long) i2)) + 65) / ((long) i);
        int i3 = 0;
        while (true) {
            byte[] bArr = this.h;
            if (i3 >= bArr.length) {
                engineUpdate(a, 0, (i2 + 1) >>> 3);
                byte[] bArr2 = this.h;
                engineUpdate(bArr2, 0, bArr2.length);
                return;
            }
            bArr[7 - i3] = (byte) (j2 >>> (i3 << 3));
            i3++;
        }
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        AbstractC0037f abstractC0037f = (AbstractC0037f) super.clone();
        abstractC0037f.h = (byte[]) this.h.clone();
        return abstractC0037f;
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    protected void engineReset() {
        this.f = 0L;
        CryptoUtils.zeroBlock(this.h);
        CryptoUtils.zeroBlock(this.b);
    }
}
