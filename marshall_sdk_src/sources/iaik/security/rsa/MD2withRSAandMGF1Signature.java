package iaik.security.rsa;

import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs1.MGF1;
import iaik.security.md.Md2;

/* JADX INFO: loaded from: classes.dex */
public class MD2withRSAandMGF1Signature extends a {
    public MD2withRSAandMGF1Signature() {
        super("MD2withRSAandMGF1");
        this.c = (AlgorithmID) AlgorithmID.md2.clone();
        this.a = (AlgorithmID) AlgorithmID.mgf1.clone();
        this.a.setParameter(this.c.toASN1Object());
        this.hash = new Md2();
        this.b = new MGF1(this.c, this.hash);
        this.d = 16;
    }
}
