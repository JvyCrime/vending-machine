package iaik.security.random;

import iaik.security.random.SecRandomSpi;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public final class HMacSHA512SP80090Random extends SP80090Random {
    private static final long serialVersionUID = 6147784646358630548L;

    public HMacSHA512SP80090Random() throws NoSuchAlgorithmException {
        super(new SecRandomSpi.HMacSHA512SP80090RandomSpi());
    }
}
