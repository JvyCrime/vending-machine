package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public final class MD5Random extends MessageDigestRandom {
    private static final long serialVersionUID = 2701660308570913308L;

    public MD5Random() {
        super(new SecRandomSpi.MD5RandomSpi());
    }
}
