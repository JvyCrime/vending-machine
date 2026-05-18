package iaik.security.mac;

import iaik.security.ssl.SecurityProvider;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class HMacSha256 extends HMac {
    public HMacSha256() throws NoSuchAlgorithmException {
        super(SecurityProvider.ALG_DIGEST_SHA256);
    }
}
