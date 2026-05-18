package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public final class SHA512Random extends MessageDigestRandom {
    private static final long serialVersionUID = -1375948473271478960L;

    public SHA512Random() {
        super(new SecRandomSpi.SHA512RandomSpi());
    }
}
