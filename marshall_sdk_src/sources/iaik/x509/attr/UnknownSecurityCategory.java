package iaik.x509.attr;

import iaik.asn1.ASN1Object;
import iaik.asn1.ObjectID;

/* JADX INFO: loaded from: classes2.dex */
public class UnknownSecurityCategory extends SecurityCategory {
    private ObjectID b;
    private ASN1Object c;

    public UnknownSecurityCategory(ObjectID objectID) {
        this.b = objectID;
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) {
        this.c = aSN1Object;
    }

    @Override // iaik.x509.attr.SecurityCategory
    public ObjectID getType() {
        return this.b;
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() {
        return this.c;
    }

    @Override // iaik.x509.attr.SecurityCategory
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Unknown SecurityCategory:\n");
        stringBuffer.append(this.c.toString());
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
}
