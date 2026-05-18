package iaik.security.random;

import iaik.security.random.SecRandomSpi;

/* JADX INFO: loaded from: classes.dex */
public class SHA384FIPS186Random extends FIPS186Random {
    private static final long serialVersionUID = -7806306349617795337L;

    public SHA384FIPS186Random() {
        super(new SecRandomSpi.SHA384FIPS186RandomSpi());
    }
}
