package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public final class AES128SP80090Random extends SP80090Random {
    private static final long serialVersionUID = 6352628877652506372L;

    public AES128SP80090Random() {
        super(new SecRandomSpi.AES128SP80090RandomSpi());
    }
}
