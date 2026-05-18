package iaik.security.dsa;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.INTEGER;
import iaik.asn1.SEQUENCE;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes.dex */
public class DSAParams implements java.security.interfaces.DSAParams {
    private BigInteger a;
    private BigInteger b;
    private BigInteger c;

    public DSAParams(ASN1Object aSN1Object) throws CodingException {
        a(aSN1Object);
    }

    public DSAParams(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.a = bigInteger;
        this.b = bigInteger2;
        this.c = bigInteger3;
    }

    public DSAParams(java.security.interfaces.DSAParams dSAParams) {
        this(dSAParams.getP(), dSAParams.getQ(), dSAParams.getG());
    }

    private void a(ASN1Object aSN1Object) throws CodingException {
        try {
            if (!aSN1Object.isA(ASN.SEQUENCE)) {
                throw new CodingException("DSA parameters must be ASN.1 SEQUENCE.");
            }
            if (aSN1Object.countComponents() < 3) {
                throw new CodingException("DSA parameeters must contain p, q and g.");
            }
            this.a = (BigInteger) aSN1Object.getComponentAt(0).getValue();
            this.b = (BigInteger) aSN1Object.getComponentAt(1).getValue();
            this.c = (BigInteger) aSN1Object.getComponentAt(2).getValue();
        } catch (Exception e) {
            throw new CodingException(e.toString());
        }
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof DSAParams)) {
            return false;
        }
        DSAParams dSAParams = (DSAParams) obj;
        return this.a.equals(dSAParams.getP()) && this.b.equals(dSAParams.getQ()) && this.c.equals(dSAParams.getG());
    }

    @Override // java.security.interfaces.DSAParams
    public BigInteger getG() {
        return this.c;
    }

    @Override // java.security.interfaces.DSAParams
    public BigInteger getP() {
        return this.a;
    }

    @Override // java.security.interfaces.DSAParams
    public BigInteger getQ() {
        return this.b;
    }

    public int hashCode() {
        return (this.a.hashCode() ^ this.b.hashCode()) ^ this.c.hashCode();
    }

    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(new INTEGER(this.a));
        sequence.addComponent(new INTEGER(this.b));
        sequence.addComponent(new INTEGER(this.c));
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("p: ");
        stringBuffer2.append(this.a.toString(16));
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("q: ");
        stringBuffer3.append(this.b.toString(16));
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        StringBuffer stringBuffer4 = new StringBuffer();
        stringBuffer4.append("g: ");
        stringBuffer4.append(this.c.toString(16));
        stringBuffer4.append("\n");
        stringBuffer.append(stringBuffer4.toString());
        return stringBuffer.toString();
    }
}
