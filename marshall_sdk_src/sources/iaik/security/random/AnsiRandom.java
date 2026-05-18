package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public class AnsiRandom extends SecRandom {
    private static final long serialVersionUID = 5061335397925168058L;

    public AnsiRandom() throws RandomException {
        super(new SecRandomSpi.AnsiRandomSpi());
    }

    public void setI() {
        ((e) this.a.a()).a();
    }

    public void setI(long j) {
        ((e) this.a.a()).a(j);
    }
}
