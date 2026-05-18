package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public final class SHA384Random extends MessageDigestRandom {
    private static final long serialVersionUID = -1062133497861870541L;

    public SHA384Random() {
        super(new SecRandomSpi.SHA384RandomSpi());
    }
}
