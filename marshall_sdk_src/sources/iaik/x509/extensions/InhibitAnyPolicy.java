package iaik.x509.extensions;

import iaik.asn1.ASN1Object;
import iaik.asn1.INTEGER;
import iaik.asn1.ObjectID;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes2.dex */
public class InhibitAnyPolicy extends V3Extension {
    public static final ObjectID oid = ObjectID.certExt_InhibitAnyPolicy;
    int a;

    public InhibitAnyPolicy() {
        this.a = 0;
    }

    public InhibitAnyPolicy(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Invalid skipCerts value. Must be >= 0!");
        }
        this.a = i;
    }

    @Override // iaik.x509.V3Extension
    public ObjectID getObjectID() {
        return oid;
    }

    public int getSkipCerts() {
        return this.a;
    }

    @Override // iaik.x509.V3Extension
    public int hashCode() {
        return oid.hashCode();
    }

    @Override // iaik.x509.V3Extension
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        int iIntValue = ((BigInteger) aSN1Object.getValue()).intValue();
        this.a = iIntValue;
        if (iIntValue < 0) {
            throw new X509ExtensionException("Invalid skipCerts value. Must be >= 0!");
        }
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        return new INTEGER(this.a);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("skipCerts: ");
        stringBuffer.append(this.a);
        return stringBuffer.toString();
    }
}
