package iaik.x509.extensions;

import iaik.asn1.ObjectID;
import iaik.asn1.structures.DistributionPoint;

/* JADX INFO: loaded from: classes2.dex */
public class FreshestCRL extends CRLDistPointsSyntax {
    public static final ObjectID oid = ObjectID.certExt_FreshestCRL;

    public FreshestCRL() {
    }

    public FreshestCRL(DistributionPoint distributionPoint) {
        super(distributionPoint);
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
