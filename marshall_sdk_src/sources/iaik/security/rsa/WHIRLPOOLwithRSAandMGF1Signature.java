package iaik.security.rsa;

import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.MGF1;
import iaik.security.md.Whirlpool;

/* JADX INFO: loaded from: classes.dex */
public class WHIRLPOOLwithRSAandMGF1Signature extends a {
    public WHIRLPOOLwithRSAandMGF1Signature() {
        super("WHIRLPOOLwithRSAandMGF1");
        this.c = (AlgorithmID) AlgorithmID.whirlpool.clone();
        this.a = (AlgorithmID) AlgorithmID.mgf1.clone();
        this.a.setParameter(this.c.toASN1Object());
        this.hash = new Whirlpool();
        this.b = new MGF1(this.c, this.hash);
        this.d = 64;
    }
}
