package iaik.security.md;

import iaik.security.ssl.SecurityProvider;

/* JADX INFO: loaded from: classes.dex */
public class SHA256 extends SHA32bit {
    private static final int[] h = {1779033703, -1150833019, 1013904242, -1521486534, 1359893119, -1694144372, 528734635, 1541459225};

    public SHA256() {
        super(SecurityProvider.ALG_DIGEST_SHA256, 32, 64, h);
    }
}
