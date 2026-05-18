package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public final class SHA1Random extends MessageDigestRandom {
    private static final long serialVersionUID = 7068506887290805959L;

    public SHA1Random() {
        super(new SecRandomSpi.SHA1RandomSpi());
    }
}
