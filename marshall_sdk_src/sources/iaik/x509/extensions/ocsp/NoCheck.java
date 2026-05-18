package iaik.x509.extensions.ocsp;

import iaik.asn1.ASN1Object;
import iaik.asn1.NULL;
import iaik.asn1.ObjectID;
import iaik.x509.V3Extension;

/* JADX INFO: loaded from: classes2.dex */
public class NoCheck extends V3Extension {
    public static final ObjectID oid = ObjectID.certExt_NoCheck;

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) {
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        return new NULL();
    }

    public String toString() {
        return "";
    }
}
