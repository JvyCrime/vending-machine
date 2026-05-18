package iaik.security.md;

/* JADX INFO: loaded from: classes.dex */
public class RipeMd160 extends AbstractC0033b {
    public RipeMd160() {
        super("RIPEMD160", 20, 64);
        this.h = g ? new D() : new C();
        engineReset();
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        RipeMd160 ripeMd160 = (RipeMd160) super.clone();
        ripeMd160.h = (B) this.h.clone();
        System.arraycopy(this.i, 0, ripeMd160.i, 0, 8);
        return ripeMd160;
    }
}
