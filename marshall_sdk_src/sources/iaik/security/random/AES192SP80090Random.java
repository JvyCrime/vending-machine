package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public final class AES192SP80090Random extends SP80090Random {
    private static final long serialVersionUID = 2407007089206746086L;

    public AES192SP80090Random() {
        super(new SecRandomSpi.AES192SP80090RandomSpi());
    }
}
