package iaik.security.md;

import iaik.utils.Util;
import java.security.MessageDigest;

/* JADX INFO: renamed from: iaik.security.md.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
abstract class AbstractC0032a extends MessageDigest {
    static final int a = Util.getJVMDataMode();
    private AbstractMessageDigest b;
    private final int c;

    AbstractC0032a(AbstractMessageDigest abstractMessageDigest) {
        super(abstractMessageDigest.getAlgorithm());
        this.b = abstractMessageDigest;
        this.c = abstractMessageDigest.getDigestLength();
    }

    @Override // java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() throws CloneNotSupportedException {
        AbstractC0032a abstractC0032a = (AbstractC0032a) super.clone();
        abstractC0032a.b = (AbstractMessageDigest) this.b.clone();
        return abstractC0032a;
    }

    protected void doPadding() {
        this.b.a();
    }

    protected void engineCompress(byte[] bArr, int i) {
        this.b.a(bArr, i);
    }

    protected void engineDigest(byte[] bArr, int i) {
        this.b.b(bArr, i);
    }

    @Override // java.security.MessageDigestSpi
    protected byte[] engineDigest() {
        return this.b.engineDigest();
    }

    @Override // java.security.MessageDigestSpi
    protected int engineGetDigestLength() {
        return this.c;
    }

    @Override // java.security.MessageDigestSpi
    protected void engineReset() {
        this.b.engineReset();
    }

    @Override // java.security.MessageDigestSpi
    protected void engineUpdate(byte b) {
        this.b.engineUpdate(b);
    }

    @Override // java.security.MessageDigestSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) {
        this.b.engineUpdate(bArr, i, i2);
    }
}
