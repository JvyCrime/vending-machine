package iaik.security.md;

import iaik.security.ssl.SecurityProvider;

/* JADX INFO: loaded from: classes.dex */
public class SHA384 extends SHA64bit {
    private static final long[] p = {-3766243637369397544L, 7105036623409894663L, -7973340178411365097L, 1526699215303891257L, 7436329637833083697L, -8163818279084223215L, -2662702644619276377L, 5167115440072839076L};

    public SHA384() {
        super(SecurityProvider.ALG_DIGEST_SHA384, 48, 128, p);
    }
}
