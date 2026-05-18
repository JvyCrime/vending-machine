package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public class SHA256FIPS186Random extends FIPS186Random {
    private static final long serialVersionUID = -2456343206098279209L;

    public SHA256FIPS186Random() {
        super(new SecRandomSpi.SHA256FIPS186RandomSpi());
    }
}
