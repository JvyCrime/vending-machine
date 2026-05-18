package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public final class SHA512SP80090Random extends SP80090Random {
    private static final long serialVersionUID = 6791724452922953359L;

    public SHA512SP80090Random() {
        super(new SecRandomSpi.SHA512SP80090RandomSpi());
    }
}
