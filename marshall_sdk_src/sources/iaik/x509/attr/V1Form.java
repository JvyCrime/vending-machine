package iaik.x509.attr;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.structures.GeneralName;
import iaik.asn1.structures.GeneralNames;
import iaik.asn1.structures.Name;
import iaik.utils.InternalErrorException;

/* JADX INFO: loaded from: classes2.dex */
public class V1Form implements AttCertIssuer {
    GeneralNames a;

    public V1Form(ASN1Object aSN1Object) throws CodingException {
        decode(aSN1Object);
    }

    public V1Form(GeneralNames generalNames) {
        if (generalNames == null) {
            throw new IllegalArgumentException("Cannot create V1Form: Missing GeneralNames!");
        }
        this.a = generalNames;
    }

    public V1Form(Name name) {
        if (name == null) {
            throw new IllegalArgumentException("Cannot create V1Form: Missing issuerName!");
        }
        this.a = new GeneralNames(new GeneralName(4, name));
    }

    @Override // iaik.x509.attr.AttCertIssuer
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.a = new GeneralNames(aSN1Object);
    }

    @Override // iaik.x509.attr.AttCertIssuer
    public boolean equals(Object obj) {
        GeneralNames generalNames;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof V1Form)) {
            return false;
        }
        V1Form v1Form = (V1Form) obj;
        GeneralNames generalNames2 = this.a;
        return (generalNames2 == null || (generalNames = v1Form.a) == null) ? generalNames2 == null && v1Form.a == null : generalNames2.equals(generalNames);
    }

    public GeneralNames getGeneralNames() {
        return this.a;
    }

    public Name getIssuerDN() {
        GeneralName[] names;
        GeneralNames generalNames = this.a;
        if (generalNames == null || (names = generalNames.getNames(4)) == null || names.length <= 0) {
            return null;
        }
        return (Name) names[0].getName();
    }

    @Override // iaik.x509.attr.AttCertIssuer
    public int getVForm() {
        return 1;
    }

    @Override // iaik.x509.attr.AttCertIssuer
    public int hashCode() {
        return this.a.hashCode();
    }

    public boolean isV1FormFor(Name name) {
        return equals(new V1Form(name));
    }

    @Override // iaik.x509.attr.AttCertIssuer
    public ASN1Object toASN1Object() {
        try {
            return this.a.toASN1Object();
        } catch (CodingException e) {
            throw new InternalErrorException(e.getMessage(), e);
        }
    }

    @Override // iaik.x509.attr.AttCertIssuer
    public String toString() {
        return this.a.toString();
    }
}
