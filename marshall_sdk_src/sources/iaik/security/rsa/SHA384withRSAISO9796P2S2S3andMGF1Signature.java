package iaik.security.rsa;

import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.MGF1;
import iaik.security.md.SHA384;
import iaik.security.random.SHA384Random;
import iaik.security.ssl.SecurityProvider;
import java.security.SecureRandom;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class SHA384withRSAISO9796P2S2S3andMGF1Signature extends RSAISO9796P2S2S3Signature {
    public static final byte HASH_ID = 54;

    public SHA384withRSAISO9796P2S2S3andMGF1Signature() {
        super("SHA384andMGF1/RSA-ISO9796-2-2-3", 48, (byte) 54);
        this.hashEngine_ = new SHA384();
        this.mgfEngine_ = new MGF1((AlgorithmID) AlgorithmID.sha1.clone(), this.hashEngine_);
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
