package iaik.x509.extensions.priv;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.NULL;
import iaik.asn1.ObjectID;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;

/* JADX INFO: loaded from: classes2.dex */
public class IdentityLinkIssuer extends V3Extension {
    public static final ObjectID oid = new ObjectID("1.2.40.0.10.1.7.1", "IdentityLinkIssuer", null, false);
    private ASN1Object a;

    public boolean equals(Object obj) {
        return obj instanceof IdentityLinkIssuer;
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
        if (aSN1Object.isA(ASN.BOOLEAN)) {
            if (!((Boolean) aSN1Object.getValue()).booleanValue()) {
                throw new X509ExtensionException("Boolean value in IdentitiyLinkIssuer extension must be true but is false!");
            }
        } else if (!aSN1Object.isA(ASN.NULL)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Invalid value (");
            stringBuffer.append(aSN1Object.getAsnType().getName());
            stringBuffer.append(") of IdentityLinkIssuer; must be NULL!");
            throw new X509ExtensionException(stringBuffer.toString());
        }
        this.a = aSN1Object;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        ASN1Object aSN1Object = this.a;
        return aSN1Object == null ? new NULL() : aSN1Object;
    }

    public String toString() {
        return this.a == null ? "" : "isIdentityLinkIssuer : true";
    }
}
