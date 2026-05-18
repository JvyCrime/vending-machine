package iaik.x509.extensions;

import iaik.asn1.ObjectID;
import iaik.asn1.structures.AccessDescription;

/* JADX INFO: loaded from: classes2.dex */
public class SubjectInfoAccess extends InfoAccess {
    public static final ObjectID oid = ObjectID.certExt_SubjectInfoAccess;

    public SubjectInfoAccess() {
    }

    public SubjectInfoAccess(AccessDescription accessDescription) throws IllegalArgumentException {
        super(accessDescription);
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }
}
