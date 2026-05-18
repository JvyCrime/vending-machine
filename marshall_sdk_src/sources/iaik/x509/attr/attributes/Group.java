package iaik.x509.attr.attributes;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;

/* JADX INFO: loaded from: classes2.dex */
public class Group extends IetfAttrSyntax {
    public static final ObjectID oid = ObjectID.group;

    public Group() {
        super(true);
    }

    public Group(ASN1Object aSN1Object) throws CodingException {
        super(true);
        decode(aSN1Object);
    }

    public Group(ObjectID[] objectIDArr) throws IllegalArgumentException {
        super(objectIDArr, true);
    }

    public Group(String[] strArr) throws IllegalArgumentException {
        super(strArr, true);
    }

    public Group(byte[][] bArr) throws IllegalArgumentException {
        super(bArr, true);
    }

    @Override // iaik.asn1.structures.AttributeValue
    public ObjectID getAttributeType() {
        return oid;
    }

    @Override // iaik.asn1.structures.AttributeValue
    public boolean multipleAllowed() {
        return false;
    }
}
