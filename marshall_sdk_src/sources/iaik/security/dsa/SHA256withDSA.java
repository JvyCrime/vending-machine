package iaik.security.dsa;

import iaik.security.md.SHA256;
import iaik.security.ssl.SecurityProvider;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class SHA256withDSA extends DSA {
    public SHA256withDSA() throws NoSuchAlgorithmException {
        super(SecurityProvider.ALG_DIGEST_SHA256, new SHA256());
    }
}
