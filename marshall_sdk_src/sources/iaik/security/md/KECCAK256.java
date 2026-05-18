package iaik.security.md;

/* JADX INFO: loaded from: classes.dex */
public final class KECCAK256 extends AbstractC0032a {
    public KECCAK256() {
        super(a(false));
    }

    static AbstractC0047p a(boolean z) {
        return a == 32 ? new s(z) : new t(z);
    }
}
