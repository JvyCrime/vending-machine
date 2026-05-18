package iaik.security.rsa;

import iaik.security.md.Whirlpool;
import iaik.security.random.WhirlpoolRandom;
import java.security.SecureRandom;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class WHIRLPOOLwithRSAISO9796P2Signature extends RSAISO9796P2Signature {
    public static final byte HASH_ID = 55;

    public WHIRLPOOLwithRSAISO9796P2Signature() {
        super("WHIRLPOOL/RSA-ISO9796-2", 64, (byte) 55);
        this.hashEngine_ = new Whirlpool();
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected boolean checkHashEngineName(String str) {
        return str.toUpperCase(Locale.US).equals("WHIRLPOOL");
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected SecureRandom getSecureRandom() {
        if (this.secureRandom_ == null) {
            setSecureRandom(new WhirlpoolRandom());
        }
        return this.secureRandom_;
    }
}
