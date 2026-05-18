package iaik.x509.extensions;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.GeneralNames;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class IssuerAltName extends V3Extension {
    public static final ObjectID oid = ObjectID.certExt_IssuerAltName;
    private GeneralNames a;

    public IssuerAltName() {
        this.a = null;
    }

    public IssuerAltName(GeneralNames generalNames) {
        this.a = null;
        this.a = generalNames;
    }

    public GeneralNames getGeneralNames() {
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
    public void init(ASN1Object aSN1Object) throws X509ExtensionException {
        try {
            this.a = new GeneralNames(aSN1Object);
        } catch (CodingException e) {
            throw new X509ExtensionException(e.getMessage());
        }
    }

    public void setGeneralNames(GeneralNames generalNames) {
        this.a = generalNames;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        GeneralNames generalNames = this.a;
        Objects.requireNonNull(generalNames, "No GeneralNames set!");
        try {
            return generalNames.toASN1Object();
        } catch (CodingException e) {
            throw new X509ExtensionException(e.getMessage());
        }
    }

    public String toString() {
        GeneralNames generalNames = this.a;
        return generalNames != null ? generalNames.toString() : "";
    }
}
