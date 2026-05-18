package iaik.pkcs.pkcs9;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.AttributeValue;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import iaik.x509.X509ExtensionInitException;
import iaik.x509.X509Extensions;
import java.util.Enumeration;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class ExtensionRequest extends AttributeValue {
    public static final ObjectID oid = ObjectID.extensionRequest;
    private X509Extensions a;

    public ExtensionRequest() {
    }

    public ExtensionRequest(ASN1Object aSN1Object) throws CodingException {
        decode(aSN1Object);
    }

    public void addExtension(V3Extension v3Extension) throws X509ExtensionException {
        if (this.a == null) {
            this.a = new X509Extensions(4, 4);
        }
        this.a.addExtension(v3Extension);
    }

    public int countExtensions() {
        X509Extensions x509Extensions = this.a;
        if (x509Extensions == null) {
            return 0;
        }
        return x509Extensions.countExtensions();
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        try {
            this.a = new X509Extensions(aSN1Object);
        } catch (X509ExtensionException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error decoding extensions: ");
            stringBuffer.append(e.toString());
            throw new CodingException(stringBuffer.toString());
        }
    }

    @Override // iaik.asn1.structures.AttributeValue
    public ObjectID getAttributeType() {
        return oid;
    }

    public Set getCriticalExtensionOIDs() {
        X509Extensions x509Extensions = this.a;
        if (x509Extensions == null) {
            return null;
        }
        return x509Extensions.getCriticalExtensionOIDs();
    }

    public V3Extension getExtension(ObjectID objectID) throws X509ExtensionInitException {
        X509Extensions x509Extensions = this.a;
        if (x509Extensions == null) {
            return null;
        }
        return x509Extensions.getExtension(objectID);
    }

    public byte[] getExtensionValue(String str) {
        X509Extensions x509Extensions = this.a;
        if (x509Extensions == null) {
            return null;
        }
        return x509Extensions.getExtensionValue(str);
    }

    public Set getNonCriticalExtensionOIDs() {
        X509Extensions x509Extensions = this.a;
        if (x509Extensions == null) {
            return null;
        }
        return x509Extensions.getNonCriticalExtensionOIDs();
    }

    public byte[] getRawExtensionValue(String str) {
        X509Extensions x509Extensions = this.a;
        if (x509Extensions == null) {
            return null;
        }
        return x509Extensions.getRawExtensionValue(str);
    }

    public boolean hasExtensions() {
        X509Extensions x509Extensions = this.a;
        if (x509Extensions == null) {
            return false;
        }
        return x509Extensions.hasExtensions();
    }

    public boolean hasUnsupportedCriticalExtension() {
        X509Extensions x509Extensions = this.a;
        if (x509Extensions == null) {
            return false;
        }
        return x509Extensions.hasUnsupportedCriticalExtension();
    }

    public Enumeration listExtensions() {
        X509Extensions x509Extensions = this.a;
        if (x509Extensions == null) {
            return null;
        }
        return x509Extensions.listExtensions();
    }

    public void removeAllExtensions() {
        X509Extensions x509Extensions = this.a;
        if (x509Extensions != null) {
            x509Extensions.removeAllExtensions();
        }
        this.a = null;
    }

    public boolean removeExtension(ObjectID objectID) {
        X509Extensions x509Extensions = this.a;
        if (x509Extensions == null) {
            return false;
        }
        return x509Extensions.removeExtension(objectID);
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        X509Extensions x509Extensions = this.a;
        if (x509Extensions == null || x509Extensions.countExtensions() == 0) {
            throw new CodingException("Extensions SEQUENCE is not allowed to be empty.");
        }
        try {
            return this.a.toASN1Object();
        } catch (X509ExtensionException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error encoding extensions: ");
            stringBuffer.append(e.toString());
            throw new CodingException(stringBuffer.toString());
        }
    }

    @Override // iaik.asn1.structures.AttributeValue
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        X509Extensions x509Extensions = this.a;
        if (x509Extensions != null) {
            stringBuffer.append(x509Extensions);
        }
        return stringBuffer.toString();
    }
}
