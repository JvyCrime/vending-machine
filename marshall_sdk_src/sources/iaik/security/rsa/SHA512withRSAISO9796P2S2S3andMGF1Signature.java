package iaik.security.rsa;

import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.MGF1;
import iaik.security.md.SHA512;
import iaik.security.random.SHA512Random;
import iaik.security.ssl.SecurityProvider;
import java.security.SecureRandom;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class SHA512withRSAISO9796P2S2S3andMGF1Signature extends RSAISO9796P2S2S3Signature {
    public static final byte HASH_ID = 53;

    public SHA512withRSAISO9796P2S2S3andMGF1Signature() {
        super("SHA512andMGF1/RSA-ISO9796-2-2-3", 64, (byte) 53);
        this.hashEngine_ = new SHA512();
        this.mgfEngine_ = new MGF1((AlgorithmID) AlgorithmID.sha1.clone(), this.hashEngine_);
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
