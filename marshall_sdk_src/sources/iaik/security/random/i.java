package iaik.security.random;

import iaik.security.mac.HMacSha;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
final class i extends h {
    public i() throws NoSuchAlgorithmException {
        super(new HMacSha(), 55);
    }
}
