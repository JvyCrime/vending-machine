package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public final class WhirlpoolRandom extends MessageDigestRandom {
    private static final long serialVersionUID = -4833094607463976885L;

    public WhirlpoolRandom() {
        super(new SecRandomSpi.WhirlpoolRandomSpi());
    }
}
