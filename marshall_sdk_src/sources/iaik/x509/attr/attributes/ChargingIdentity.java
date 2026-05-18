package iaik.x509.attr.attributes;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;

/* JADX INFO: loaded from: classes2.dex */
public class ChargingIdentity extends IetfAttrSyntax {
    public static final ObjectID oid = ObjectID.chargingIdentity;

    public ChargingIdentity() {
        super(true);
    }

    public ChargingIdentity(ASN1Object aSN1Object) throws CodingException {
        super(true);
        decode(aSN1Object);
    }

    public ChargingIdentity(ObjectID[] objectIDArr) throws IllegalArgumentException {
        super(objectIDArr, true);
    }

    public ChargingIdentity(String[] strArr) throws IllegalArgumentException {
        super(strArr, true);
    }

    public ChargingIdentity(byte[][] bArr) throws IllegalArgumentException {
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
