package iaik.security.dsa;

import iaik.security.md.SHA512;
import iaik.security.ssl.SecurityProvider;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class SHA512withDSA extends DSA {
    public SHA512withDSA() throws NoSuchAlgorithmException {
        super(SecurityProvider.ALG_DIGEST_SHA512, new SHA512());
    }
}
