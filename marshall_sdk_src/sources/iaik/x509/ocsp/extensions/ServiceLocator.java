package iaik.x509.ocsp.extensions;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.Name;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import iaik.x509.extensions.AuthorityInfoAccess;

/* JADX INFO: loaded from: classes2.dex */
public class ServiceLocator extends V3Extension {
    public static final ObjectID oid = ObjectID.ocspExt_ServiceLocator;
    private Name a;
    private AuthorityInfoAccess b;

    public ServiceLocator() {
    }

    public ServiceLocator(Name name) {
        this.a = name;
    }

    public Name getIssuer() {
        return this.a;
    }

    public AuthorityInfoAccess getLocator() {
        return this.b;
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
            Name name = new Name(aSN1Object.getComponentAt(0));
            this.a = name;
            if (name == null) {
                throw new X509ExtensionException("Cannot create ServiceLocator. Missing issuer!");
            }
            if (aSN1Object.countComponents() == 2) {
                AuthorityInfoAccess authorityInfoAccess = new AuthorityInfoAccess();
                this.b = authorityInfoAccess;
                authorityInfoAccess.init(aSN1Object.getComponentAt(1));
            }
        } catch (CodingException e) {
            throw new X509ExtensionException(e.getMessage());
        }
    }

    public void setIssuer(Name name) {
        this.a = name;
    }

    public void setLocator(AuthorityInfoAccess authorityInfoAccess) {
        this.b = authorityInfoAccess;
    }

    @Override // iaik.x509.V3Extension
    public ASN1Object toASN1Object() throws X509ExtensionException {
        if (this.a == null) {
            throw new X509ExtensionException("Cannot create ASN.1 object. Missing issuer!");
        }
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.a.toASN1Object());
        AuthorityInfoAccess authorityInfoAccess = this.b;
        if (authorityInfoAccess != null) {
            sequence.addComponent(authorityInfoAccess.toASN1Object());
        }
        return sequence;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("issuer: ");
        stringBuffer2.append(this.a);
        stringBuffer.append(stringBuffer2.toString());
        if (this.b != null) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("\nlocator: ");
            stringBuffer3.append(this.b);
            stringBuffer.append(stringBuffer3.toString());
        }
        return stringBuffer.toString();
    }
}
