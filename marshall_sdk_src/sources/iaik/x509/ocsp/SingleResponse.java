package iaik.x509.ocsp;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.CON_SPEC;
import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.ChoiceOfTime;
import iaik.asn1.structures.GeneralName;
import iaik.utils.Util;
import iaik.x509.V3Extension;
import iaik.x509.X509Certificate;
import iaik.x509.X509ExtensionException;
import iaik.x509.X509ExtensionInitException;
import iaik.x509.ocsp.extensions.ArchiveCutoff;
import iaik.x509.ocsp.extensions.CrlID;
import java.util.Date;
import java.util.Enumeration;

/* JADX INFO: loaded from: classes2.dex */
public class SingleResponse implements CertificateResponse {
    ReqCert a;
    CertStatus b;
    ChoiceOfTime c;
    ChoiceOfTime d;
    OCSPExtensions e;

    public SingleResponse() {
    }

    public SingleResponse(ASN1Object aSN1Object) throws X509ExtensionException, CodingException {
        decode(aSN1Object);
    }

    public SingleResponse(ReqCert reqCert, CertStatus certStatus, Date date) {
        if (reqCert == null) {
            throw new IllegalArgumentException("Cannot create SingleResponse. Missing ReqCert!");
        }
        this.a = reqCert;
        if (certStatus == null) {
            throw new IllegalArgumentException("Cannot create SingleResponse. Missing CertStatus!");
        }
        this.b = certStatus;
        if (date == null) {
            throw new IllegalArgumentException("Cannot create SingleResponse. Missing thisUpdate date!");
        }
        this.c = new ChoiceOfTime(date, ASN.GeneralizedTime, false);
    }

    public void addExtension(V3Extension v3Extension) throws X509ExtensionException {
        if (this.e == null) {
            this.e = new OCSPExtensions();
        }
        this.e.addExtension(v3Extension);
    }

    public int countExtensions() {
        OCSPExtensions oCSPExtensions = this.e;
        if (oCSPExtensions == null) {
            return 0;
        }
        return oCSPExtensions.countExtensions();
    }

    @Override // iaik.asn1.ASN1Type
    public void decode(ASN1Object aSN1Object) throws CodingException {
        this.a = new ReqCert(aSN1Object.getComponentAt(0));
        this.b = new CertStatus(aSN1Object.getComponentAt(1));
        this.c = new ChoiceOfTime(aSN1Object.getComponentAt(2));
        for (int i = 3; i < aSN1Object.countComponents(); i++) {
            CON_SPEC con_spec = (CON_SPEC) aSN1Object.getComponentAt(i);
            int tag = con_spec.getAsnType().getTag();
            if (tag == 0) {
                this.d = new ChoiceOfTime((ASN1Object) con_spec.getValue());
            } else {
                if (tag != 1) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Invalid context specific tag in SingleResponse encoding: ");
                    stringBuffer.append(tag);
                    throw new CodingException(stringBuffer.toString());
                }
                try {
                    this.e = new OCSPExtensions((ASN1Object) con_spec.getValue());
                } catch (X509ExtensionException e) {
                    throw new CodingException(e.getMessage());
                }
            }
        }
    }

    public Date getArchiveCutoff() throws X509ExtensionInitException {
        ArchiveCutoff archiveCutoff = (ArchiveCutoff) getExtension(ArchiveCutoff.oid);
        if (archiveCutoff == null) {
            return null;
        }
        return archiveCutoff.getCutoffTime();
    }

    public CertStatus getCertStatus() {
        return this.b;
    }

    public CrlID getCrlID() throws X509ExtensionInitException {
        return (CrlID) getExtension(CrlID.oid);
    }

    public V3Extension getExtension(ObjectID objectID) throws X509ExtensionInitException {
        OCSPExtensions oCSPExtensions = this.e;
        if (oCSPExtensions == null) {
            return null;
        }
        return oCSPExtensions.getExtension(objectID);
    }

    public Date getNextUpdate() {
        ChoiceOfTime choiceOfTime = this.d;
        if (choiceOfTime == null) {
            return null;
        }
        return choiceOfTime.getDate();
    }

    @Override // iaik.x509.ocsp.CertificateResponse
    public ReqCert getReqCert() {
        return this.a;
    }

    public Date getThisUpdate() {
        ChoiceOfTime choiceOfTime = this.c;
        if (choiceOfTime == null) {
            return null;
        }
        return choiceOfTime.getDate();
    }

    public boolean hasExtensions() {
        OCSPExtensions oCSPExtensions = this.e;
        if (oCSPExtensions == null) {
            return false;
        }
        return oCSPExtensions.hasExtensions();
    }

    public boolean hasUnsupportedCriticalExtension() {
        OCSPExtensions oCSPExtensions = this.e;
        if (oCSPExtensions == null) {
            return false;
        }
        return oCSPExtensions.hasUnsupportedCriticalExtension();
    }

    @Override // iaik.x509.ocsp.CertificateResponse
    public boolean isResponseFor(X509Certificate x509Certificate, X509Certificate x509Certificate2, GeneralName generalName) throws OCSPException {
        return this.a.isReqCertFor(x509Certificate, x509Certificate2, generalName);
    }

    @Override // iaik.x509.ocsp.CertificateResponse
    public boolean isResponseFor(ReqCert reqCert) {
        return this.a.equals(reqCert);
    }

    public Enumeration listExtensions() {
        OCSPExtensions oCSPExtensions = this.e;
        if (oCSPExtensions == null) {
            return null;
        }
        return oCSPExtensions.listExtensions();
    }

    public void removeAllExtensions() {
        OCSPExtensions oCSPExtensions = this.e;
        if (oCSPExtensions != null) {
            oCSPExtensions.removeAllExtensions();
        }
        this.e = null;
    }

    public boolean removeExtension(ObjectID objectID) {
        OCSPExtensions oCSPExtensions = this.e;
        if (oCSPExtensions == null) {
            return false;
        }
        return oCSPExtensions.removeExtension(objectID);
    }

    public void setArchiveCutoff(Date date) throws X509ExtensionException {
        addExtension(new ArchiveCutoff(date));
    }

    public void setCrlID(CrlID crlID) throws X509ExtensionException {
        addExtension(crlID);
    }

    public void setNextUpdate(Date date) {
        this.d = new ChoiceOfTime(date, ASN.GeneralizedTime, false);
    }

    @Override // iaik.asn1.ASN1Type
    public ASN1Object toASN1Object() throws CodingException {
        try {
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(this.a.toASN1Object());
            sequence.addComponent(this.b.toASN1Object());
            sequence.addComponent(this.c.toASN1Object());
            if (this.d != null) {
                sequence.addComponent(new CON_SPEC(0, this.d.toASN1Object()));
            }
            if (this.e != null) {
                sequence.addComponent(new CON_SPEC(1, this.e.toASN1Object()));
            }
            return sequence;
        } catch (Exception e) {
            throw new CodingException(e.getMessage());
        }
    }

    @Override // iaik.x509.ocsp.CertificateResponse
    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("reqCert: {\n");
        Util.printIndented(this.a.toString(), true, "  ", stringBuffer);
        stringBuffer.append("\n}");
        stringBuffer.append("\ncertStatus: {\n");
        Util.printIndented(this.b.toString(), true, "  ", stringBuffer);
        stringBuffer.append("\n}");
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("\nthisUpdate: ");
        stringBuffer2.append(this.c);
        stringBuffer.append(stringBuffer2.toString());
        if (this.d != null) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("\nnextUpdate: ");
            stringBuffer3.append(this.d);
            stringBuffer.append(stringBuffer3.toString());
        }
        if (this.e != null) {
            if (z) {
                stringBuffer.append("\nextensions:\n");
                Util.printIndented(this.e.toString(), true, "  ", stringBuffer);
                stringBuffer.append("\n}");
            } else {
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("\nExtensions: ");
                stringBuffer4.append(this.e.countExtensions());
                stringBuffer.append(stringBuffer4.toString());
            }
        }
        return stringBuffer.toString();
    }
}
