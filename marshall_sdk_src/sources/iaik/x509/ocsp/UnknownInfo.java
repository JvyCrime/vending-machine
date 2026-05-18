package iaik.x509.ocsp;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ENUMERATED;
import iaik.asn1.NULL;

/* JADX INFO: loaded from: classes2.dex */
public class UnknownInfo {
    int a;
    boolean b;

    public UnknownInfo() {
        this.b = true;
    }

    public UnknownInfo(int i) {
        this.a = i;
        this.b = false;
    }

    public UnknownInfo(ASN1Object aSN1Object) throws CodingException {
        this();
        a(aSN1Object);
    }

    private void a(ASN1Object aSN1Object) {
        if (aSN1Object.getAsnType().equals(ASN.ENUMERATED)) {
            this.a = ((Integer) aSN1Object.getValue()).intValue();
            this.b = false;
        }
    }

    public Object getValue() {
        if (this.b) {
            return null;
        }
        return new Integer(this.a);
    }

    public ASN1Object toASN1Object() {
        return this.b ? new NULL() : new ENUMERATED(this.a);
    }

    public String toString() {
        return !this.b ? Integer.toString(this.a) : "";
    }
}
