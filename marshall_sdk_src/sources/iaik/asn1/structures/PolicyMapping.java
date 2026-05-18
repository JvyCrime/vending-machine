package iaik.asn1.structures;

import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;

/* JADX INFO: loaded from: classes.dex */
public class PolicyMapping implements ASN1Type {
    ObjectID a;
    ObjectID b;

    public PolicyMapping() {
    }

    public PolicyMapping(ASN1Object aSN1Object) throws CodingException {
        decode(aSN1Object);
    }

    public PolicyMapping(ObjectID objectID, ObjectID objectID2) {
        this.a = objectID;
        this.b = objectID2;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        try {
            this.a = (ObjectID) aSN1Object.getComponentAt(0);
            this.b = (ObjectID) aSN1Object.getComponentAt(1);
        } catch (ClassCastException unused) {
            throw new CodingException("No PolicyMapping!");
        }
    }

    public ObjectID getIssuerDomainPolicy() {
        return this.a;
    }

    public ObjectID getSubjectDomainPolicy() {
        return this.b;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.a);
        sequence.addComponent(this.b);
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.a.getName());
        stringBuffer.append(" <=> ");
        stringBuffer.append(this.b.getName());
        return stringBuffer.toString();
    }
}
