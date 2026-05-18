package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public class SHA1FIPS186Random extends FIPS186Random {
    private static final long serialVersionUID = 6953029527558039332L;

    public SHA1FIPS186Random() {
        super(new SecRandomSpi.SHA1FIPS186RandomSpi());
    }
}
