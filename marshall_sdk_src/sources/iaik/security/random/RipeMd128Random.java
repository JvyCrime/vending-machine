package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public final class RipeMd128Random extends MessageDigestRandom {
    private static final long serialVersionUID = -5506485579448210383L;

    public RipeMd128Random() {
        super(new SecRandomSpi.RipeMd128RandomSpi());
    }
}
