package iaik.security.random;

import iaik.security.mac.HMacSha512;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
final class m extends h {
    public m() throws NoSuchAlgorithmException {
        super(new HMacSha512(), 111);
    }
}
