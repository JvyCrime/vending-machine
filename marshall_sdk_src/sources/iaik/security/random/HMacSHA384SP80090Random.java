package iaik.security.random;

import iaik.security.random.SecRandomSpi;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public final class HMacSHA384SP80090Random extends SP80090Random {
    private static final long serialVersionUID = 8522660167328634979L;

    public HMacSHA384SP80090Random() throws NoSuchAlgorithmException {
        super(new SecRandomSpi.HMacSHA512SP80090RandomSpi());
    }
}
