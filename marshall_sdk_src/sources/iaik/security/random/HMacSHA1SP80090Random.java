package iaik.security.random;

import iaik.security.random.SecRandomSpi;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public final class HMacSHA1SP80090Random extends SP80090Random {
    private static final long serialVersionUID = 8652536304648117586L;

    public HMacSHA1SP80090Random() throws NoSuchAlgorithmException {
        super(new SecRandomSpi.HMacSHA1SP80090RandomSpi());
    }
}
