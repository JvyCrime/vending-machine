package iaik.security.rsa;

import iaik.security.md.RipeMd160;
import iaik.security.random.RipeMd160Random;
import java.security.SecureRandom;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class RIPEMD160withRSAISO9796P2Signature extends RSAISO9796P2Signature {
    public static final byte HASH_ID = 49;

    public RIPEMD160withRSAISO9796P2Signature() {
        super("RIPEMD160/RSA-ISO9796-2", 20, (byte) 49);
        this.hashEngine_ = new RipeMd160();
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected boolean checkHashEngineName(String str) {
        String upperCase = str.toUpperCase(Locale.US);
        return upperCase.equals("RIPEMD160") || upperCase.equals("RIPEMD-160");
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected SecureRandom getSecureRandom() {
        if (this.secureRandom_ == null) {
            setSecureRandom(new RipeMd160Random());
        }
        return this.secureRandom_;
    }
}
