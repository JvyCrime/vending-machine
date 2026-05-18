package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public class SHA1SP80090Random extends SP80090Random {
    private static final long serialVersionUID = 2865253215543812868L;

    public SHA1SP80090Random() {
        super(new SecRandomSpi.SHA1SP80090RandomSpi());
    }
}
