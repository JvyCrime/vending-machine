package iaik.security.mac;

import iaik.security.ssl.SecurityProvider;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class HMacMd5 extends HMac {
    public HMacMd5() throws NoSuchAlgorithmException {
        super(SecurityProvider.ALG_DIGEST_MD5);
    }
}
