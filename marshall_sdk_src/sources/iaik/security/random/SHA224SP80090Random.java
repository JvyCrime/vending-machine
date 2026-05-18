package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public final class SHA224SP80090Random extends SP80090Random {
    private static final long serialVersionUID = -8162222978273948065L;

    public SHA224SP80090Random() {
        super(new SecRandomSpi.SHA224SP80090RandomSpi());
    }
}
