package iaik.security.md;

/* JADX INFO: loaded from: classes.dex */
abstract class P extends AbstractC0032a {
    private H b;

    /* JADX WARN: Multi-variable type inference failed */
    P(AbstractC0047p abstractC0047p) {
        super(abstractC0047p);
        if (!(abstractC0047p instanceof H)) {
            throw new IllegalArgumentException("Expected RawSHAKE instance!");
        }
        this.b = (H) abstractC0047p;
    }

    public void a(int i) {
        this.b.a(i);
    }
}
