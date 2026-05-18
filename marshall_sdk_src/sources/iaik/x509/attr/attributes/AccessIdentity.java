package iaik.x509.attr.attributes;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.GeneralName;

/* JADX INFO: loaded from: classes2.dex */
public class AccessIdentity extends SvceAuthInfo {
    public static final ObjectID oid = ObjectID.accessIdentity;

    public AccessIdentity() {
    }

    public AccessIdentity(ASN1Object aSN1Object) throws CodingException {
        super(aSN1Object);
    }

    public AccessIdentity(GeneralName generalName, GeneralName generalName2) {
        super(generalName, generalName2);
    }

    @Override // iaik.asn1.structures.AttributeValue
    public ObjectID getAttributeType() {
        return oid;
    }
}
