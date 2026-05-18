package iaik.x509;

import iaik.asn1.ObjectID;
import iaik.utils.CryptoUtils;
import iaik.x509.extensions.AuthorityKeyIdentifier;
import iaik.x509.extensions.BasicConstraints;
import iaik.x509.extensions.CRLDistributionPoints;
import iaik.x509.extensions.CRLNumber;
import iaik.x509.extensions.ExtendedKeyUsage;
import iaik.x509.extensions.IssuerAltName;
import iaik.x509.extensions.KeyUsage;
import iaik.x509.extensions.PolicyConstraints;
import iaik.x509.extensions.PolicyMappings;
import iaik.x509.extensions.ReasonCode;
import iaik.x509.extensions.SubjectAltName;
import iaik.x509.extensions.SubjectKeyIdentifier;
import iaik.x509.extensions.netscape.NetscapeBaseUrl;
import iaik.x509.extensions.netscape.NetscapeCaPolicyUrl;
import iaik.x509.extensions.netscape.NetscapeCaRevocationUrl;
import iaik.x509.extensions.netscape.NetscapeCertRenewalUrl;
import iaik.x509.extensions.netscape.NetscapeComment;
import iaik.x509.extensions.netscape.NetscapeRevocationUrl;
import iaik.x509.extensions.netscape.NetscapeSSLServerName;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ChainVerifier {
    protected ChainVerifier() {
    }

    public static java.security.cert.X509Certificate[] orderCertificateChain(java.security.cert.X509Certificate x509Certificate, java.security.cert.X509Certificate[] x509CertificateArr) throws CertificateException {
        Principal issuerDN;
        int i;
        java.security.cert.X509Certificate[] x509CertificateArr2 = (java.security.cert.X509Certificate[]) x509CertificateArr.clone();
        Vector vector = new Vector();
        vector.addElement(x509Certificate);
        Enumeration enumerationElements = vector.elements();
        do {
            java.security.cert.X509Certificate x509Certificate2 = (java.security.cert.X509Certificate) enumerationElements.nextElement();
            Principal subjectDN = x509Certificate2.getSubjectDN();
            issuerDN = x509Certificate2.getIssuerDN();
            if (!subjectDN.equals(issuerDN)) {
                i = 0;
                while (true) {
                    if (i < x509CertificateArr2.length) {
                        if (x509CertificateArr2[i] != null && x509CertificateArr2[i].getSubjectDN().equals(issuerDN)) {
                            vector.addElement(x509CertificateArr2[i]);
                            x509CertificateArr2[i] = null;
                            break;
                        }
                        i++;
                    } else {
                        break;
                    }
                }
            } else {
                java.security.cert.X509Certificate[] x509CertificateArr3 = new java.security.cert.X509Certificate[vector.size()];
                vector.copyInto(x509CertificateArr3);
                return x509CertificateArr3;
            }
        } while (i != x509CertificateArr.length);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Certificate chain incomplete, no certificate found for ");
        stringBuffer.append(issuerDN);
        throw new CertificateException(stringBuffer.toString());
    }

    protected void checkExtensions(java.security.cert.X509Certificate[] x509CertificateArr, int i) throws CertificateException {
        Enumeration enumerationListExtensions;
        if ((x509CertificateArr[i] instanceof X509Certificate) && (enumerationListExtensions = ((X509Certificate) x509CertificateArr[i]).listExtensions()) != null) {
            while (enumerationListExtensions.hasMoreElements()) {
                V3Extension v3Extension = (V3Extension) enumerationListExtensions.nextElement();
                ObjectID objectID = v3Extension.getObjectID();
                if (objectID.equals(BasicConstraints.oid)) {
                    BasicConstraints basicConstraints = (BasicConstraints) v3Extension;
                    if (basicConstraints.ca()) {
                        if (i == 0) {
                            throw new CertificateException("Extension error: certificate at index 0 is marked CA certificate");
                        }
                        int pathLenConstraint = basicConstraints.getPathLenConstraint();
                        if (pathLenConstraint != -1 && pathLenConstraint < i - 1) {
                            throw new CertificateException("Extension error: pathLenConstraint violated!");
                        }
                    } else if (i != 0) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Extension error: certificate at index ");
                        stringBuffer.append(i);
                        stringBuffer.append(" is marked as non-CA certificate");
                        throw new CertificateException(stringBuffer.toString());
                    }
                } else if (objectID.equals(KeyUsage.oid)) {
                    KeyUsage keyUsage = (KeyUsage) v3Extension;
                    if (i > 0 && (keyUsage.get() & 32) == 0) {
                        throw new CertificateException("Extension error: keyusage does not allow certificate signing");
                    }
                } else if (!objectID.equals(AuthorityKeyIdentifier.oid) && !objectID.equals(CRLDistributionPoints.oid) && !objectID.equals(CRLNumber.oid) && !objectID.equals(ExtendedKeyUsage.oid) && !objectID.equals(IssuerAltName.oid) && !objectID.equals(PolicyMappings.oid) && !objectID.equals(ReasonCode.oid) && !objectID.equals(PolicyConstraints.oid) && !objectID.equals(SubjectAltName.oid) && !objectID.equals(SubjectKeyIdentifier.oid) && !objectID.equals(NetscapeBaseUrl.oid) && !objectID.equals(NetscapeCaPolicyUrl.oid) && !objectID.equals(NetscapeCaRevocationUrl.oid) && !objectID.equals(NetscapeCertRenewalUrl.oid) && !objectID.equals(NetscapeComment.oid) && !objectID.equals(NetscapeRevocationUrl.oid) && !objectID.equals(NetscapeSSLServerName.oid) && v3Extension.isCritical()) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Unhandled CRITICAL extension: ");
                    stringBuffer2.append(v3Extension.getObjectID());
                    throw new CertificateException(stringBuffer2.toString());
                }
            }
        }
    }

    public abstract boolean isTrustedCertificate(java.security.cert.X509Certificate x509Certificate) throws CertificateException;

    public boolean verifyChain(java.security.cert.X509Certificate[] x509CertificateArr) throws CertificateException {
        return verifyChain(x509CertificateArr, false);
    }

    public boolean verifyChain(java.security.cert.X509Certificate[] x509CertificateArr, boolean z) throws CertificateException {
        int length = x509CertificateArr.length;
        if (z) {
            java.security.cert.X509Certificate[] x509CertificateArr2 = new java.security.cert.X509Certificate[length];
            for (int i = 0; i < length; i++) {
                x509CertificateArr2[i] = x509CertificateArr[(length - i) - 1];
            }
            x509CertificateArr = x509CertificateArr2;
        }
        for (int i2 = 0; i2 < length; i2++) {
            if (i2 > 0) {
                try {
                    int i3 = i2 - 1;
                    if (!x509CertificateArr[i2].getSubjectDN().equals(x509CertificateArr[i3].getIssuerDN())) {
                        throw new CertificateException("Certificate chain broken: not linked correctly");
                    }
                    x509CertificateArr[i3].verify(x509CertificateArr[i2].getPublicKey());
                    if (CryptoUtils.equalsBlock(x509CertificateArr[i3].getSignature(), x509CertificateArr[i2].getSignature()) && !x509CertificateArr[i3].equals(x509CertificateArr[i2])) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Certificate ");
                        stringBuffer.append(i3);
                        stringBuffer.append(" and ");
                        stringBuffer.append(i2);
                        stringBuffer.append(" have same signature value!");
                        throw new CertificateException(stringBuffer.toString());
                    }
                } catch (CertificateException e) {
                    throw e;
                } catch (Exception unused) {
                    throw new CertificateException("Error in certificate chain");
                }
            }
            if (x509CertificateArr[i2].getSubjectDN().equals(x509CertificateArr[i2].getIssuerDN())) {
                x509CertificateArr[i2].verify(x509CertificateArr[i2].getPublicKey());
            }
            checkExtensions(x509CertificateArr, i2);
            if (isTrustedCertificate(x509CertificateArr[i2])) {
                return true;
            }
            x509CertificateArr[i2].checkValidity();
        }
        return false;
    }
}
