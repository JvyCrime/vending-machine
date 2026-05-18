package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public class RipeMd160FIPS186Random extends FIPS186Random {
    private static final long serialVersionUID = 6287870002007481451L;

    public RipeMd160FIPS186Random() {
        super(new SecRandomSpi.RipeMd160FIPS186RandomSpi());
    }
}
