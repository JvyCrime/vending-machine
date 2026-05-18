package iaik.x509.extensions;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.GeneralName;
import iaik.asn1.structures.GeneralNames;
import iaik.asn1.structures.Name;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;

/* JADX INFO: loaded from: classes2.dex */
public class CertificateIssuer extends V3Extension {
    public static final ObjectID oid = ObjectID.crlExt_CertificateIssuer;
    private GeneralNames a;

    public CertificateIssuer() {
    }

    public CertificateIssuer(GeneralNames generalNames) {
        this.a = generalNames;
    }

    public CertificateIssuer(Name name) {
        setIssuerDN(name);
    }

    public GeneralNames getIssuer() {
        return this.a;
    }

    public Name getIssuerDN() {
        GeneralNames generalNames = this.a;
        if (generalNames != null) {
            GeneralName[] names = generalNames.getNames(4);
            if (names.length > 0) {
                return (Name) names[0].getName();
            }
        }
        return null;
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
        try {
            this.a = new GeneralNames(aSN1Object);
        } catch (CodingException e) {
            throw new X509ExtensionException(e.getMessage());
        }
    }

    public void setIssuer(GeneralNames generalNames) {
        this.a = generalNames;
    }

    public void setIssuerDN(Name name) {
        this.a = new GeneralNames(new GeneralName(4, name));
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        try {
            return this.a.toASN1Object();
        } catch (CodingException e) {
            throw new X509ExtensionException(e.getMessage());
        }
    }

    public String toString() {
        GeneralNames generalNames = this.a;
        return generalNames != null ? generalNames.toString() : "";
    }
}
