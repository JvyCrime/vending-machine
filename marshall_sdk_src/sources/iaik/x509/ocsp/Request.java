package iaik.x509.ocsp;

import iaik.asn1.ASN1Object;
import iaik.asn1.ASN1Type;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.utils.Util;
import iaik.x509.V3Extension;
import iaik.x509.X509ExtensionException;
import iaik.x509.X509ExtensionInitException;
import iaik.x509.ocsp.extensions.ServiceLocator;
import java.util.Enumeration;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class Request implements ASN1Type {
    ReqCert a;
    OCSPExtensions b;

    public Request() {
    }

    public Request(ASN1Object aSN1Object) throws X509ExtensionException, CodingException {
        decode(aSN1Object);
    }

    public Request(ReqCert reqCert) {
        Objects.requireNonNull(reqCert, "Cannot create OCSP request from null ReqCert!");
        this.a = reqCert;
    }

    public void addExtension(V3Extension v3Extension) throws X509ExtensionException {
        if (this.b == null) {
            this.b = new OCSPExtensions();
        }
        this.b.addExtension(v3Extension);
    }

    public int countExtensions() {
        OCSPExtensions oCSPExtensions = this.b;
        if (oCSPExtensions == null) {
            return 0;
        }
        return oCSPExtensions.countExtensions();
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.a = new ReqCert(aSN1Object.getComponentAt(0));
        if (aSN1Object.countComponents() == 2) {
            try {
                this.b = new OCSPExtensions((ASN1Object) aSN1Object.getComponentAt(1).getValue());
            } catch (X509ExtensionException e) {
                throw new CodingException(e.getMessage());
            }
        }
    }

    public V3Extension getExtension(ObjectID objectID) throws X509ExtensionInitException {
        OCSPExtensions oCSPExtensions = this.b;
        if (oCSPExtensions == null) {
            return null;
        }
        return oCSPExtensions.getExtension(objectID);
    }

    public ReqCert getReqCert() {
        return this.a;
    }

    public ServiceLocator getServiceLocator() throws X509ExtensionInitException {
        return (ServiceLocator) getExtension(ServiceLocator.oid);
    }

    public boolean hasExtensions() {
        OCSPExtensions oCSPExtensions = this.b;
        if (oCSPExtensions == null) {
            return false;
        }
        return oCSPExtensions.hasExtensions();
    }

    public boolean hasUnsupportedCriticalExtension() {
        OCSPExtensions oCSPExtensions = this.b;
        if (oCSPExtensions == null) {
            return false;
        }
        return oCSPExtensions.hasUnsupportedCriticalExtension();
    }

    public Enumeration listExtensions() {
        OCSPExtensions oCSPExtensions = this.b;
        if (oCSPExtensions == null) {
            return null;
        }
        return oCSPExtensions.listExtensions();
    }

    public void removeAllExtensions() {
        OCSPExtensions oCSPExtensions = this.b;
        if (oCSPExtensions != null) {
            oCSPExtensions.removeAllExtensions();
        }
        this.b = null;
    }

    public boolean removeExtension(ObjectID objectID) {
        OCSPExtensions oCSPExtensions = this.b;
        if (oCSPExtensions == null) {
            return false;
        }
        return oCSPExtensions.removeExtension(objectID);
    }

    public void setServiceLocator(ServiceLocator serviceLocator) throws X509ExtensionException {
        addExtension(serviceLocator);
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        SEQUENCE sequence = new SEQUENCE();
        sequence.addComponent(this.a.toASN1Object());
        if (this.b != null) {
            try {
                sequence.addComponent(new CON_SPEC(0, this.b.toASN1Object(), false));
            } catch (X509ExtensionException e) {
                throw new CodingException(e.getMessage());
            }
        }
        return sequence;
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("reqCert: {\n");
        Util.printIndented(this.a.toString(), true, "  ", stringBuffer);
        stringBuffer.append("\n}");
        if (this.b != null) {
            if (z) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("\n");
                stringBuffer2.append(this.b);
                stringBuffer.append(stringBuffer2.toString());
                stringBuffer.setLength(stringBuffer.length() - 1);
            } else {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("\nExtensions: ");
                stringBuffer3.append(this.b.countExtensions());
                stringBuffer.append(stringBuffer3.toString());
            }
        }
        return stringBuffer.toString();
    }
}
