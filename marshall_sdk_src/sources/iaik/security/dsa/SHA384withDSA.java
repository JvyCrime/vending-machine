package iaik.security.dsa;

import iaik.security.md.SHA384;
import iaik.security.ssl.SecurityProvider;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class SHA384withDSA extends DSA {
    public SHA384withDSA() throws NoSuchAlgorithmException {
        super(SecurityProvider.ALG_DIGEST_SHA384, new SHA384());
    }
}
