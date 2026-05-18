package iaik.security.random;

import iaik.security.mac.HMacSha384;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
final class l extends h {
    public l() throws NoSuchAlgorithmException {
        super(new HMacSha384(), 111);
    }
}
