package iaik.security.rsa;

import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.MGF1;
import iaik.security.md.SHA256;
import iaik.security.random.SHA256Random;
import iaik.security.ssl.SecurityProvider;
import java.security.SecureRandom;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class SHA256withRSAISO9796P2S2S3andMGF1Signature extends RSAISO9796P2S2S3Signature {
    public static final byte HASH_ID = 52;

    public SHA256withRSAISO9796P2S2S3andMGF1Signature() {
        super("SHA256andMGF1/RSA-ISO9796-2-2-3", 32, (byte) 52);
        this.hashEngine_ = new SHA256();
        this.mgfEngine_ = new MGF1((AlgorithmID) AlgorithmID.sha256.clone(), this.hashEngine_);
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected boolean checkHashEngineName(String str) {
        String upperCase = str.toUpperCase(Locale.US);
        return upperCase.equals(SecurityProvider.ALG_DIGEST_SHA256) || upperCase.equals("SHA-256");
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected SecureRandom getSecureRandom() {
        if (this.secureRandom_ == null) {
            setSecureRandom(new SHA256Random());
        }
        return this.secureRandom_;
    }
}
