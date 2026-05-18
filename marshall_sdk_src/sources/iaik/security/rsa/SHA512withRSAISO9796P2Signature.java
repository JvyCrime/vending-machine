package iaik.security.rsa;

import iaik.security.md.SHA512;
import iaik.security.random.SHA512Random;
import iaik.security.ssl.SecurityProvider;
import java.security.SecureRandom;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class SHA512withRSAISO9796P2Signature extends RSAISO9796P2Signature {
    public static final byte HASH_ID = 53;

    public SHA512withRSAISO9796P2Signature() {
        super("SHA512/RSA-ISO9796-2", 64, (byte) 53);
        this.hashEngine_ = new SHA512();
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected boolean checkHashEngineName(String str) {
        String upperCase = str.toUpperCase(Locale.US);
        return upperCase.equals(SecurityProvider.ALG_DIGEST_SHA512) || upperCase.equals("SHA-512");
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected SecureRandom getSecureRandom() {
        if (this.secureRandom_ == null) {
            setSecureRandom(new SHA512Random());
        }
        return this.secureRandom_;
    }
}
