package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public class SHA512FIPS186Random extends FIPS186Random {
    private static final long serialVersionUID = 9062374456356001146L;

    public SHA512FIPS186Random() {
        super(new SecRandomSpi.SHA512FIPS186RandomSpi());
    }
}
