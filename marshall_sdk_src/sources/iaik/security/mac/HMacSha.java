package iaik.security.mac;

import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class HMacSha extends HMac {
    public HMacSha() throws NoSuchAlgorithmException {
        super("SHA1");
    }
}
