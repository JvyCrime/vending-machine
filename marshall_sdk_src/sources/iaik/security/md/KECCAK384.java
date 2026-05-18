package iaik.security.md;

/* JADX INFO: loaded from: classes.dex */
public final class KECCAK384 extends AbstractC0032a {
    public KECCAK384() {
        super(a(false));
    }

    static AbstractC0047p a(boolean z) {
        return a == 32 ? new v(z) : new w(z);
    }
}
