package iaik.security.md;

/* JADX INFO: loaded from: classes.dex */
public final class RipeMd320 extends AbstractC0033b {
    public RipeMd320() {
        super("RIPEMD320", 40, 64);
        this.h = g ? new G() : new F();
        engineReset();
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        RipeMd320 ripeMd320 = (RipeMd320) super.clone();
        ripeMd320.h = (E) this.h.clone();
        System.arraycopy(this.i, 0, ripeMd320.i, 0, 8);
        return ripeMd320;
    }
}
