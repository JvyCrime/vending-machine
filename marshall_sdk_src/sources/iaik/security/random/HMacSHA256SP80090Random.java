package iaik.security.random;

import iaik.security.random.SecRandomSpi;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public final class HMacSHA256SP80090Random extends SP80090Random {
    private static final long serialVersionUID = 2760441351306397067L;

    public HMacSHA256SP80090Random() throws NoSuchAlgorithmException {
        super(new SecRandomSpi.HMacSHA256SP80090RandomSpi());
    }
}
