package iaik.security.md;

import iaik.security.ssl.SecurityProvider;

/* JADX INFO: loaded from: classes.dex */
public class SHA512 extends SHA64bit {
    private static final long[] p = {7640891576956012808L, -4942790177534073029L, 4354685564936845355L, -6534734903238641935L, 5840696475078001361L, -7276294671716946913L, 2270897969802886507L, 6620516959819538809L};

    public SHA512() {
        super(SecurityProvider.ALG_DIGEST_SHA512, 64, 128, p);
    }
}
