package iaik.security.rsa;

import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.MGF1;
import iaik.security.md.SHA;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class SHAwithRSAISO9796P2S2S3andMGF1Signature extends RSAISO9796P2S2S3Signature {
    public static final byte HASH_ID = 51;

    public SHAwithRSAISO9796P2S2S3andMGF1Signature() {
        super("SHA1andMGF1/RSA-ISO9796-2-2-3", 20, (byte) 51);
        this.hashEngine_ = new SHA();
        this.mgfEngine_ = new MGF1((AlgorithmID) AlgorithmID.sha1.clone(), this.hashEngine_);
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected boolean checkHashEngineName(String str) {
        String upperCase = str.toUpperCase(Locale.US);
        return upperCase.equals("SHA") || upperCase.equals("SHA-1") || upperCase.equals("SHA1");
    }
}
