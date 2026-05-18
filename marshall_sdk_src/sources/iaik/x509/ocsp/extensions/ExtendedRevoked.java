package iaik.x509.ocsp.extensions;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.NULL;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.ChoiceOfTime;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import iaik.x509.extensions.ReasonCode;

/* JADX INFO: loaded from: classes2.dex */
public class ExtendedRevoked extends V3Extension {
    public static final ReasonCode REVOCATION_REASON;
    public static final ChoiceOfTime REVOCATION_TIME;
    private static ChoiceOfTime a;
    public static final ObjectID oid = ObjectID.ocspExt_ExtendedRevoked;

    static {
        try {
            a = new ChoiceOfTime("19700101000000Z", ASN.GeneralizedTime);
        } catch (Exception unused) {
        }
        REVOCATION_TIME = a;
        REVOCATION_REASON = new ReasonCode(6);
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
        if (!aSN1Object.isA(ASN.NULL)) {
            throw new X509ExtensionException("Invalid ASN.1 representation. Expected ASN.1 NULL!");
        }
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() {
        return new NULL();
    }

    public String toString() {
        return "";
    }
}
