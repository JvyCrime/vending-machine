package iaik.asn1.structures;

import iaik.asn1.ASN1Object;
import iaik.asn1.ObjectID;

/* JADX INFO: loaded from: classes.dex */
public class UnknownAttributeValue extends AttributeValue {
    private ObjectID a;
    private ASN1Object b;

    public UnknownAttributeValue(ObjectID objectID) {
        this.a = objectID;
    }

    UnknownAttributeValue(ObjectID objectID, ASN1Object aSN1Object) {
        this.a = objectID;
        this.b = aSN1Object;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) {
        this.b = aSN1Object;
    }

    @Override // iaik.asn1.structures.AttributeValue
    public ObjectID getAttributeType() {
        return this.a;
    }

    @Override // iaik.asn1.structures.AttributeValue
    public String getName() {
        return this.a.getID();
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        return this.b;
    }

    @Override // iaik.asn1.structures.AttributeValue
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.b.toString());
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
}
