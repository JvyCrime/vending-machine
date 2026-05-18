package iaik.security.rsa;

import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.MGF1;
import iaik.security.md.RipeMd128;
import iaik.security.random.RipeMd128Random;
import java.security.SecureRandom;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class RIPEMD128withRSAISO9796P2S2S3andMGF1Signature extends RSAISO9796P2S2S3Signature {
    public static final byte HASH_ID = 50;

    public RIPEMD128withRSAISO9796P2S2S3andMGF1Signature() {
        super("RIPEMD128andMGF1/RSA-ISO9796-2-2-3", 16, (byte) 50);
        this.hashEngine_ = new RipeMd128();
        this.mgfEngine_ = new MGF1((AlgorithmID) AlgorithmID.ripeMd128.clone(), this.hashEngine_);
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected boolean checkHashEngineName(String str) {
        String upperCase = str.toUpperCase(Locale.US);
        return upperCase.equals("RIPEMD128") || upperCase.equals("RIPEMD-128");
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected SecureRandom getSecureRandom() {
        if (this.secureRandom_ == null) {
            setSecureRandom(new RipeMd128Random());
        }
        return this.secureRandom_;
    }
}
