package iaik.security.rsa;

import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.MGF1;
import iaik.security.md.SHA224;

/* JADX INFO: loaded from: classes.dex */
public class SHA224withRSAandMGF1Signature extends a {
    public SHA224withRSAandMGF1Signature() {
        super("SHA224withRSAandMGF1");
        this.c = (AlgorithmID) AlgorithmID.sha224.clone();
        this.a = (AlgorithmID) AlgorithmID.mgf1.clone();
        this.a.setParameter(this.c.toASN1Object());
        this.hash = new SHA224();
        this.b = new MGF1(this.c, this.hash);
        this.d = 28;
    }
}
