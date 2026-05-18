package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public final class AES256SP80090Random extends SP80090Random {
    private static final long serialVersionUID = 1862030338581498230L;

    public AES256SP80090Random() {
        super(new SecRandomSpi.AES256SP80090RandomSpi());
    }
}
