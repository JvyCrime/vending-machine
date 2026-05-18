package iaik.security.rsa;

import iaik.security.md.SHA384;
import iaik.security.random.SHA384Random;
import iaik.security.ssl.SecurityProvider;
import java.security.SecureRandom;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class SHA384withRSAISO9796P2Signature extends RSAISO9796P2Signature {
    public static final byte HASH_ID = 54;

    public SHA384withRSAISO9796P2Signature() {
        super("SHA384/RSA-ISO9796-2", 48, (byte) 54);
        this.hashEngine_ = new SHA384();
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected boolean checkHashEngineName(String str) {
        String upperCase = str.toUpperCase(Locale.US);
        return upperCase.equals(SecurityProvider.ALG_DIGEST_SHA384) || upperCase.equals("SHA-384");
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected SecureRandom getSecureRandom() {
        if (this.secureRandom_ == null) {
            setSecureRandom(new SHA384Random());
        }
        return this.secureRandom_;
    }
}
