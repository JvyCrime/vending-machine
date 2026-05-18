package iaik.x509.extensions;

import iaik.asn1.ASN1Object;
import iaik.asn1.ObjectID;
import iaik.x509.V3Extension;

/* JADX INFO: loaded from: classes2.dex */
public class HoldInstructionCode extends V3Extension {
    private ObjectID a;
    public static final ObjectID oid = ObjectID.crlExt_HoldInstructionCode;
    public static final ObjectID holdInstruction = new ObjectID("1.2.840.10040.2", "holdInstruction", null, false);
    public static final ObjectID holdInstructionNone = new ObjectID("1.2.840.10040.2.1", "holdInstructionNone", null, false);
    public static final ObjectID holdInstructionCallIssuer = new ObjectID("1.2.840.10040.2.2", "holdInstructionCallIssuer", null, false);
    public static final ObjectID holdInstructionReject = new ObjectID("1.2.840.10040.2.3", "holdInstructionReject", null, false);

    public HoldInstructionCode() {
    }

    public HoldInstructionCode(ObjectID objectID) {
        this.a = objectID;
    }

    public ObjectID getHoldInstructionCode() {
        return this.a;
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
    public void init(ASN1Object aSN1Object) {
        this.a = (ObjectID) aSN1Object;
    }

    public void setInstructionCode(ObjectID objectID) {
        this.a = objectID;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        return this.a;
    }

    public String toString() {
        ObjectID objectID = this.a;
        return objectID != null ? objectID.toString() : "";
    }
}
