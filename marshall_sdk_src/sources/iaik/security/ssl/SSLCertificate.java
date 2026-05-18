package iaik.security.ssl;

import java.io.IOException;
import java.lang.reflect.Array;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class SSLCertificate extends x {
    private X509Certificate[] a;

    public SSLCertificate(X509Certificate[] x509CertificateArr) {
        super(11);
        this.a = null;
        this.a = x509CertificateArr;
    }

    SSLCertificate(ab abVar) throws IOException {
        super(11);
        this.a = null;
        a(abVar);
    }

    public X509Certificate[] getCertificateChain() {
        return this.a;
    }

    PublicKey b() {
        return this.a[0].getPublicKey();
    }

    int a() {
        X509Certificate[] x509CertificateArr = this.a;
        if (x509CertificateArr == null) {
            return 0;
        }
        return x509CertificateArr.length;
    }

    @Override // iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        agVar.g(11);
        int length = this.a.length;
        byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) byte[].class, length);
        int length2 = 0;
        for (int i = 0; i < length; i++) {
            try {
                bArr[i] = this.a[i].getEncoded();
                length2 = length2 + bArr[i].length + 3;
            } catch (CertificateException e) {
                StringBuffer stringBuffer = new StringBuffer("Unable to encode certificate: ");
                stringBuffer.append(e);
                throw new RuntimeException(stringBuffer.toString());
            }
        }
        agVar.e(length2 + 3);
        agVar.e(length2);
        for (int i2 = 0; i2 < length; i2++) {
            agVar.b(bArr[i2]);
        }
    }

    void a(ab abVar) throws IOException {
        abVar.h();
        int iH = abVar.h();
        Vector vector = new Vector();
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        int length = 0;
        while (length < iH) {
            try {
                byte[] bArrI = abVar.i();
                length = length + bArrI.length + 3;
                X509Certificate x509Certificate = securityProvider.getX509Certificate(bArrI);
                securityProvider.checkKeyLength(x509Certificate.getPublicKey());
                vector.addElement(x509Certificate);
            } catch (Exception e) {
                StringBuffer stringBuffer = new StringBuffer("Error decoding Certificate: ");
                stringBuffer.append(e);
                throw new SSLException(stringBuffer.toString());
            }
        }
        X509Certificate[] x509CertificateArr = new X509Certificate[vector.size()];
        this.a = x509CertificateArr;
        vector.copyInto(x509CertificateArr);
    }

    public String toString() {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Certificate list: ");
        X509Certificate[] x509CertificateArr = this.a;
        if (x509CertificateArr == null) {
            string = "empty.";
        } else {
            StringBuffer stringBuffer2 = new StringBuffer(String.valueOf(x509CertificateArr.length));
            stringBuffer2.append(" elements.");
            string = stringBuffer2.toString();
        }
        stringBuffer.append(string);
        return stringBuffer.toString();
    }
}
