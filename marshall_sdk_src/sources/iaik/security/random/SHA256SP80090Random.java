package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public final class SHA256SP80090Random extends SP80090Random {
    private static final long serialVersionUID = 3802979498502719057L;

    public SHA256SP80090Random() {
        super(new SecRandomSpi.SHA256SP80090RandomSpi());
    }
}
