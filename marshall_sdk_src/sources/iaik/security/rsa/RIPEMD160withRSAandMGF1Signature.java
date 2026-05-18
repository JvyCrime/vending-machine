package iaik.security.rsa;

import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.MGF1;
import iaik.security.md.RipeMd160;

/* JADX INFO: loaded from: classes.dex */
public class RIPEMD160withRSAandMGF1Signature extends a {
    public RIPEMD160withRSAandMGF1Signature() {
        super("RIPEMD160withRSAandMGF1");
        this.c = (AlgorithmID) AlgorithmID.ripeMd160.clone();
        this.a = (AlgorithmID) AlgorithmID.mgf1.clone();
        this.a.setParameter(this.c.toASN1Object());
        this.hash = new RipeMd160();
        this.b = new MGF1(this.c, this.hash);
        this.d = 20;
    }
}
