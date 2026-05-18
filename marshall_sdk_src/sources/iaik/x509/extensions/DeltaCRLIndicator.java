package iaik.x509.extensions;

import iaik.asn1.ASN1Object;
import iaik.asn1.INTEGER;
import iaik.asn1.ObjectID;
import iaik.x509.V3Extension;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes2.dex */
public class DeltaCRLIndicator extends V3Extension {
    public static final ObjectID oid = ObjectID.crlExt_DeltaCRLIndicator;
    private BigInteger a;

    public DeltaCRLIndicator() {
        this.a = BigInteger.valueOf(0L);
    }

    public DeltaCRLIndicator(BigInteger bigInteger) {
        this();
        this.a = bigInteger;
    }

    public BigInteger getBaseCRLNumber() {
        return this.a;
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) {
        this.a = (BigInteger) aSN1Object.getValue();
    }

    public void setBaseCRLNumber(BigInteger bigInteger) {
        this.a = bigInteger;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        return new INTEGER(this.a);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("0x");
        stringBuffer.append(this.a.toString(16));
        return stringBuffer.toString();
    }
}
