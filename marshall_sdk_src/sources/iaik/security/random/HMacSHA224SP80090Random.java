package iaik.security.random;

import iaik.security.random.SecRandomSpi;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public final class HMacSHA224SP80090Random extends SP80090Random {
    private static final long serialVersionUID = -4648490564302213091L;

    public HMacSHA224SP80090Random() throws NoSuchAlgorithmException {
        super(new SecRandomSpi.HMacSHA224SP80090RandomSpi());
    }
}
