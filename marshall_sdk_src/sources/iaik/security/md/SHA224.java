package iaik.security.md;

import iaik.security.ssl.SecurityProvider;

/* JADX INFO: loaded from: classes.dex */
public class SHA224 extends SHA32bit {
    private static final int[] h = {-1056596264, 914150663, 812702999, -150054599, -4191439, 1750603025, 1694076839, -1090891868};

    public SHA224() {
        super(SecurityProvider.ALG_DIGEST_SHA224, 28, 64, h);
    }
}
