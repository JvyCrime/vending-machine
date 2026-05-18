package iaik.security.dsa;

import iaik.security.md.SHA224;
import iaik.security.ssl.SecurityProvider;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class SHA224withDSA extends DSA {
    public SHA224withDSA() throws NoSuchAlgorithmException {
        super(SecurityProvider.ALG_DIGEST_SHA224, new SHA224());
    }
}
