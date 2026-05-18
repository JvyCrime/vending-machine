package iaik.security.rsa;

import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.MGF1;
import iaik.security.md.Whirlpool;
import iaik.security.random.WhirlpoolRandom;
import java.security.SecureRandom;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class WHIRLPOOLwithRSAISO9796P2S2S3andMGF1Signature extends RSAISO9796P2S2S3Signature {
    public static final byte HASH_ID = 55;

    public WHIRLPOOLwithRSAISO9796P2S2S3andMGF1Signature() {
        super("WHIRLPOOLandMGF1/RSA-ISO9796-2-2-3", 64, (byte) 55);
        this.hashEngine_ = new Whirlpool();
        this.mgfEngine_ = new MGF1((AlgorithmID) AlgorithmID.whirlpool.clone(), this.hashEngine_);
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
