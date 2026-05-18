package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public final class RipeMd160Random extends MessageDigestRandom {
    private static final long serialVersionUID = -7988005994316750053L;

    public RipeMd160Random() {
        super(new SecRandomSpi.RipeMd160RandomSpi());
    }
}
