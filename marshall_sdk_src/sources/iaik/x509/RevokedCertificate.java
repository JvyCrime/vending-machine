package iaik.x509;

import iaik.asn1.ASN1Object;
import iaik.asn1.CodingException;
import iaik.asn1.DerCoder;
import iaik.asn1.INTEGER;
import iaik.asn1.OCTET_STRING;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.ChoiceOfTime;
import iaik.x509.extensions.ReasonCode;
import java.math.BigInteger;
import java.security.cert.CRLException;
import java.security.cert.X509CRLEntry;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class RevokedCertificate extends X509CRLEntry implements Cloneable {
    private BigInteger a;
    private ChoiceOfTime b;
    private X509Extensions c;
    private ReasonCode d;
    private int e = 2;

    RevokedCertificate() {
    }

    public RevokedCertificate(ASN1Object aSN1Object) throws CRLException {
        try {
            a(aSN1Object);
        } catch (CodingException e) {
            throw new CRLException(e.getMessage());
        }
    }

    public RevokedCertificate(X509Certificate x509Certificate, Date date) {
        this.a = x509Certificate.getSerialNumber();
        this.b = new ChoiceOfTime(date, true, false);
    }

    public RevokedCertificate(BigInteger bigInteger, Date date) {
        this.a = bigInteger;
        this.b = new ChoiceOfTime(date, true, false);
    }

    private void a(ASN1Object aSN1Object) throws CodingException {
        ReasonCode reasonCode;
        Objects.requireNonNull(aSN1Object, "Cannot parse revoked certificate from a null object!");
        try {
            BigInteger bigInteger = (BigInteger) aSN1Object.getComponentAt(0).getValue();
            ChoiceOfTime choiceOfTime = new ChoiceOfTime(aSN1Object.getComponentAt(1));
            X509Extensions x509Extensions = null;
            if (aSN1Object.countComponents() > 2) {
                int i = this.e;
                x509Extensions = new X509Extensions(i, i);
                x509Extensions.decode(aSN1Object.getComponentAt(2));
            }
            this.a = bigInteger;
            this.b = choiceOfTime;
            if (x509Extensions != null) {
                if (x509Extensions.countExtensions() != 1 || (reasonCode = (ReasonCode) x509Extensions.getExtension(ReasonCode.oid)) == null) {
                    this.c = x509Extensions;
                } else {
                    this.d = reasonCode;
                }
            }
        } catch (X509ExtensionException e) {
            throw new CodingException(e.getMessage());
        }
    }

    boolean a(ObjectID objectID) {
        if (this.d != null) {
            return objectID.equals(ReasonCode.oid);
        }
        X509Extensions x509Extensions = this.c;
        if (x509Extensions != null) {
            return x509Extensions.a(objectID);
        }
        return false;
    }

    public void addExtension(V3Extension v3Extension) throws X509ExtensionException {
        X509Extensions x509Extensions = this.c;
        if (x509Extensions == null) {
            if (v3Extension instanceof ReasonCode) {
                this.d = (ReasonCode) v3Extension;
                return;
            }
            if (x509Extensions == null) {
                int i = this.e;
                this.c = new X509Extensions(i, i);
            }
            ReasonCode reasonCode = this.d;
            if (reasonCode != null) {
                this.c.addExtension(reasonCode);
                this.d = null;
            }
            x509Extensions = this.c;
        }
        x509Extensions.addExtension(v3Extension);
    }

    public Object clone() {
        RevokedCertificate revokedCertificate = null;
        try {
            RevokedCertificate revokedCertificate2 = (RevokedCertificate) super.clone();
            try {
                revokedCertificate2.a(toASN1Object());
                return revokedCertificate2;
            } catch (CloneNotSupportedException | Exception unused) {
                revokedCertificate = revokedCertificate2;
                return revokedCertificate;
            }
        } catch (CloneNotSupportedException | Exception unused2) {
        }
    }

    public int countExtensions() {
        if (this.d != null) {
            return 1;
        }
        X509Extensions x509Extensions = this.c;
        if (x509Extensions != null) {
            return x509Extensions.countExtensions();
        }
        return 0;
    }

    @Override // java.security.cert.X509Extension
    public Set getCriticalExtensionOIDs() {
        Set criticalExtensionOIDs;
        X509Extensions x509Extensions;
        ReasonCode reasonCode = this.d;
        if (reasonCode == null || !reasonCode.isCritical()) {
            criticalExtensionOIDs = null;
        } else {
            criticalExtensionOIDs = new HashSet();
            criticalExtensionOIDs.add(this.d.getObjectID().getID());
        }
        if (criticalExtensionOIDs == null && (x509Extensions = this.c) != null) {
            criticalExtensionOIDs = x509Extensions.getCriticalExtensionOIDs();
        }
        return criticalExtensionOIDs == null ? new HashSet() : criticalExtensionOIDs;
    }

    @Override // java.security.cert.X509CRLEntry
    public byte[] getEncoded() throws CRLException {
        return DerCoder.encode(toASN1Object());
    }

    public V3Extension getExtension(ObjectID objectID) throws X509ExtensionInitException {
        X509Extensions x509Extensions = this.c;
        V3Extension extension = x509Extensions == null ? null : x509Extensions.getExtension(objectID);
        return (extension == null && this.d != null && objectID.equals(ReasonCode.oid)) ? this.d : extension;
    }

    @Override // java.security.cert.X509Extension
    public byte[] getExtensionValue(String str) {
        byte[] rawExtensionValue = getRawExtensionValue(str);
        return rawExtensionValue != null ? DerCoder.encode(new OCTET_STRING(rawExtensionValue)) : rawExtensionValue;
    }

    @Override // java.security.cert.X509Extension
    public Set getNonCriticalExtensionOIDs() {
        Set nonCriticalExtensionOIDs;
        X509Extensions x509Extensions;
        ReasonCode reasonCode = this.d;
        if (reasonCode == null || reasonCode.isCritical()) {
            nonCriticalExtensionOIDs = null;
        } else {
            nonCriticalExtensionOIDs = new HashSet();
            nonCriticalExtensionOIDs.add(this.d.getObjectID().getID());
        }
        if (nonCriticalExtensionOIDs == null && (x509Extensions = this.c) != null) {
            nonCriticalExtensionOIDs = x509Extensions.getNonCriticalExtensionOIDs();
        }
        return nonCriticalExtensionOIDs == null ? new HashSet() : nonCriticalExtensionOIDs;
    }

    public byte[] getRawExtensionValue(String str) {
        if (this.d != null && str.equals(ReasonCode.oid.getID())) {
            return DerCoder.encode(this.d.toASN1Object());
        }
        X509Extensions x509Extensions = this.c;
        if (x509Extensions != null) {
            return x509Extensions.getRawExtensionValue(str);
        }
        return null;
    }

    @Override // java.security.cert.X509CRLEntry
    public Date getRevocationDate() {
        return this.b.getDate();
    }

    @Override // java.security.cert.X509CRLEntry
    public BigInteger getSerialNumber() {
        return this.a;
    }

    @Override // java.security.cert.X509CRLEntry
    public boolean hasExtensions() {
        boolean z = this.d != null;
        if (z) {
            return z;
        }
        X509Extensions x509Extensions = this.c;
        return x509Extensions != null ? x509Extensions.hasExtensions() : false;
    }

    @Override // java.security.cert.X509Extension
    public boolean hasUnsupportedCriticalExtension() {
        X509Extensions x509Extensions = this.c;
        if (x509Extensions == null) {
            return false;
        }
        return x509Extensions.hasUnsupportedCriticalExtension();
    }

    public Enumeration listExtensions() {
        Vector vector;
        X509Extensions x509Extensions = this.c;
        Enumeration enumerationListExtensions = x509Extensions == null ? null : x509Extensions.listExtensions();
        if (enumerationListExtensions != null) {
            return enumerationListExtensions;
        }
        if (this.d != null) {
            vector = new Vector(1);
            vector.add(this.d);
        } else {
            vector = new Vector(0);
        }
        return vector.elements();
    }

    public void removeAllExtensions() {
        if (this.d != null) {
            this.d = null;
        }
        X509Extensions x509Extensions = this.c;
        if (x509Extensions != null) {
            x509Extensions.removeAllExtensions();
            this.c = null;
        }
    }

    public boolean removeExtension(ObjectID objectID) {
        if (this.d != null && objectID.equals(ReasonCode.oid)) {
            this.d = null;
            return true;
        }
        X509Extensions x509Extensions = this.c;
        int i = 0;
        boolean zRemoveExtension = x509Extensions == null ? false : x509Extensions.removeExtension(objectID);
        if (zRemoveExtension) {
            int iCountExtensions = this.c.countExtensions();
            if (iCountExtensions == 1) {
                try {
                    ReasonCode reasonCode = (ReasonCode) this.c.getExtension(ReasonCode.oid);
                    if (reasonCode != null) {
                        this.d = reasonCode;
                    } else {
                        i = iCountExtensions;
                    }
                    iCountExtensions = i;
                } catch (X509ExtensionInitException unused) {
                }
            }
            if (iCountExtensions == 0) {
                this.c = null;
            }
        }
        return zRemoveExtension;
    }

    public ASN1Object toASN1Object() throws CRLException {
        ASN1Object aSN1Object;
        try {
            SEQUENCE sequence = new SEQUENCE();
            sequence.addComponent(new INTEGER(this.a));
            sequence.addComponent(this.b.toASN1Object());
            X509Extensions x509Extensions = this.c;
            if (x509Extensions != null) {
                if (x509Extensions.countExtensions() > 0) {
                    aSN1Object = this.c.toASN1Object();
                    sequence.addComponent(aSN1Object);
                }
            } else if (this.d != null) {
                X509Extensions x509Extensions2 = new X509Extensions(2, 2);
                x509Extensions2.addExtension(this.d);
                aSN1Object = x509Extensions2.toASN1Object();
                sequence.addComponent(aSN1Object);
            }
            return sequence;
        } catch (X509ExtensionException e) {
            throw new CRLException(e.getMessage());
        }
    }

    @Override // java.security.cert.X509CRLEntry
    public String toString() {
        return toString(false);
    }

    public String toString(boolean z) {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("RevokedCertificate: ");
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("serial number: ");
        stringBuffer2.append(this.a);
        stringBuffer2.append("\n");
        stringBuffer.append(stringBuffer2.toString());
        StringBuffer stringBuffer3 = new StringBuffer();
        stringBuffer3.append("revocation date: ");
        stringBuffer3.append(this.b.getDate());
        stringBuffer3.append("\n");
        stringBuffer.append(stringBuffer3.toString());
        if (hasExtensions()) {
            if (!z) {
                X509Extensions x509Extensions = this.c;
                int iCountExtensions = x509Extensions != null ? x509Extensions.countExtensions() : this.d != null ? 1 : 0;
                StringBuffer stringBuffer4 = new StringBuffer();
                stringBuffer4.append("Extensions: ");
                stringBuffer4.append(iCountExtensions);
                stringBuffer4.append("\n");
                string = stringBuffer4.toString();
            } else if (this.d != null) {
                StringBuffer stringBuffer5 = new StringBuffer();
                stringBuffer5.append("Extensions: ReasonCode: ");
                stringBuffer5.append(this.d);
                stringBuffer5.append("\n");
                string = stringBuffer5.toString();
            } else {
                stringBuffer.append(this.c);
            }
            stringBuffer.append(string);
        } else {
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }
}
