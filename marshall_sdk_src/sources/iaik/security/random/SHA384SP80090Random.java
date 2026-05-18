package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public final class SHA384SP80090Random extends SP80090Random {
    private static final long serialVersionUID = -8899900152485438178L;

    public SHA384SP80090Random() {
        super(new SecRandomSpi.SHA384SP80090RandomSpi());
    }
}
