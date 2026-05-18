package iaik.security.ssl;

import iaik.asn1.ASN;
import iaik.asn1.ASN1Object;
import iaik.asn1.BIT_STRING;
import iaik.asn1.DerCoder;
import iaik.asn1.ObjectID;
import iaik.asn1.SEQUENCE;
import iaik.asn1.structures.GeneralName;
import iaik.asn1.structures.Name;
import iaik.asn1.structures.RDN;
import iaik.utils.Util;
import iaik.x509.X509Certificate;
import iaik.x509.extensions.SubjectAltName;
import iaik.x509.extensions.netscape.NetscapeSSLServerName;
import java.security.PublicKey;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
class aa {
    static Class a;
    private static final String[] b = new String[0];

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    static String[] a(X509Certificate x509Certificate, boolean z) {
        Vector vector = new Vector();
        try {
            SubjectAltName subjectAltName = (SubjectAltName) x509Certificate.getExtension(SubjectAltName.oid);
            if (subjectAltName != null) {
                Enumeration names = subjectAltName.getGeneralNames().getNames();
                while (names.hasMoreElements()) {
                    GeneralName generalName = (GeneralName) names.nextElement();
                    int type = generalName.getType();
                    if (type == 2) {
                        vector.addElement(generalName.getName().toString());
                    } else if (z && type == 7) {
                        vector.addElement(generalName.getName().toString());
                    }
                }
            }
            NetscapeSSLServerName netscapeSSLServerName = (NetscapeSSLServerName) x509Certificate.getExtension(NetscapeSSLServerName.oid);
            if (netscapeSSLServerName != null) {
                vector.addElement(netscapeSSLServerName.getSSLServerName());
            }
            Object[] rDNValues = ((Name) x509Certificate.getSubjectDN()).getRDNValues(ObjectID.commonName);
            if (rDNValues != null) {
                for (Object obj : rDNValues) {
                    vector.addElement(obj.toString());
                }
            }
        } catch (Exception unused) {
        }
        String[] strArr = new String[vector.size()];
        vector.copyInto(strArr);
        return strArr;
    }

    static final byte[] a(PublicKey publicKey) throws Exception {
        return SecurityProvider.getSecurityProvider().getMessageDigest("SHA").digest((byte[]) ((BIT_STRING) DerCoder.decode(publicKey.getEncoded()).getComponentAt(1)).getValue());
    }

    static final X509Certificate[] a(byte[] bArr) throws Exception {
        Objects.requireNonNull(bArr, "Cannot parse null pki path!");
        ASN1Object aSN1ObjectDecode = DerCoder.decode(bArr);
        Class clsClass$ = a;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.x509.X509Certificate");
            a = clsClass$;
        }
        X509Certificate[] x509CertificateArr = (X509Certificate[]) ASN.parseSequenceOf(aSN1ObjectDecode, clsClass$);
        int length = x509CertificateArr.length;
        X509Certificate[] x509CertificateArr2 = new X509Certificate[length];
        for (int i = 0; i < length; i++) {
            x509CertificateArr2[i] = x509CertificateArr[(length - i) - 1];
        }
        return x509CertificateArr2;
    }

    static byte[] a(java.security.cert.X509Certificate[] x509CertificateArr) throws Exception {
        Objects.requireNonNull(x509CertificateArr, "certificate must not be null!");
        X509Certificate[] x509CertificateArrArrangeCertificateChain = Util.arrangeCertificateChain(Util.convertCertificateChain(x509CertificateArr), true);
        if (x509CertificateArrArrangeCertificateChain == null) {
            throw new Exception("Cannot top-down arrange certificates!");
        }
        SEQUENCE sequence = new SEQUENCE();
        for (X509Certificate x509Certificate : x509CertificateArrArrangeCertificateChain) {
            sequence.addComponent(x509Certificate.toASN1Object());
        }
        return DerCoder.encode(sequence);
    }

    static void a(String str, Name name, SSLTransport sSLTransport, StringBuffer stringBuffer) {
        String string;
        RDN[] rDNs = name.getRDNs();
        boolean z = true;
        for (int length = rDNs.length - 1; length >= 0; length--) {
            RDN rdn = rDNs[length];
            try {
                string = rdn.getRFC2253String(false);
            } catch (Exception unused) {
                string = rdn.toString();
            }
            if (z) {
                StringBuffer stringBuffer2 = new StringBuffer(String.valueOf(str));
                stringBuffer2.append(string);
                sSLTransport.a(stringBuffer2.toString(), stringBuffer);
                z = false;
            } else {
                StringBuffer stringBuffer3 = new StringBuffer("           ");
                stringBuffer3.append(string);
                sSLTransport.a(stringBuffer3.toString(), stringBuffer);
            }
        }
    }
}
