package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public final class SHA256Random extends MessageDigestRandom {
    private static final long serialVersionUID = 2007757404371898912L;

    public SHA256Random() {
        super(new SecRandomSpi.SHA256RandomSpi());
    }
}
