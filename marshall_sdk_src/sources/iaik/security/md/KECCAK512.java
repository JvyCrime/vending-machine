package iaik.security.md;

/* JADX INFO: loaded from: classes.dex */
public final class KECCAK512 extends AbstractC0032a {
    public KECCAK512() {
        super(a(false));
    }

    static AbstractC0047p a(boolean z) {
        return a == 32 ? new x(z) : new y(z);
    }
}
