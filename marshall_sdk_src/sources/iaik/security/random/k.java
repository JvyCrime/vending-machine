package iaik.security.random;

import iaik.security.mac.HMacSha256;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
final class k extends h {
    public k() throws NoSuchAlgorithmException {
        super(new HMacSha256(), 55);
    }
}
