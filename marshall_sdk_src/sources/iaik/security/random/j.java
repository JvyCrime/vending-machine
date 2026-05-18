package iaik.security.random;

import iaik.security.mac.HMacSha224;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
final class j extends h {
    public j() throws NoSuchAlgorithmException {
        super(new HMacSha224(), 55);
    }
}
