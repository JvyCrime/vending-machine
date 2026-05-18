package iaik.x509.attr.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.NULL;
import iaik.asn1.ObjectID;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;

/* JADX INFO: loaded from: classes2.dex */
public class NoRevAvail extends V3Extension {
    public static final ObjectID oid = ObjectID.attrCertExt_NoRevAvail;

    public boolean equals(Object obj) {
        return obj instanceof NoRevAvail;
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
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        if (aSN1Object.isA(ASN.NULL)) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Invalid value (");
        stringBuffer.append(aSN1Object.getAsnType().getName());
        stringBuffer.append(") of NoRevAvail; must be NULL!");
        throw new X509ExtensionException(stringBuffer.toString());
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        return new NULL();
    }

    public String toString() {
        return "\"null\"";
    }
}
