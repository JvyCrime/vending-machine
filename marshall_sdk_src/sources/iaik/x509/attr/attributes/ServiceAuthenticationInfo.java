package iaik.x509.attr.attributes;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.GeneralName;

/* JADX INFO: loaded from: classes2.dex */
public class ServiceAuthenticationInfo extends SvceAuthInfo {
    public static final ObjectID oid = ObjectID.authenticationInfo;

    public ServiceAuthenticationInfo() {
    }

    public ServiceAuthenticationInfo(ASN1Object aSN1Object) throws CodingException {
        super(aSN1Object);
    }

    public ServiceAuthenticationInfo(GeneralName generalName, GeneralName generalName2) {
        super(generalName, generalName2);
    }

    @Override // iaik.asn1.structures.AttributeValue
    public ObjectID getAttributeType() {
        return oid;
    }

    public byte[] getAuthInfo() {
        if (this.a != null) {
            return (byte[]) ((byte[]) this.a.getValue()).clone();
        }
        return null;
    }

    public void setAuthInfo(byte[] bArr) {
        if (bArr != null) {
            this.a = new OCTET_STRING(bArr);
        } else {
            this.a = null;
        }
    }
}
