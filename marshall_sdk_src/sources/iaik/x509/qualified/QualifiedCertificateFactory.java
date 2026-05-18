package iaik.x509.qualified;

import iaik.utils.CollectionVector;
import iaik.utils.Util;
import iaik.x509.CertificateFactory;
import iaik.x509.X509Certificate;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Collection;

/* JADX INFO: loaded from: classes2.dex */
public class QualifiedCertificateFactory extends CertificateFactory {
    private static boolean a = false;

    @Override // iaik.x509.CertificateFactory, java.security.cert.CertificateFactorySpi
    public Certificate engineGenerateCertificate(InputStream inputStream) throws CertificateException {
        try {
            byte[] stream = Util.readStream(inputStream);
            try {
                return new QualifiedCertificate(stream);
            } catch (Exception unused) {
                if (a) {
                    System.out.println("engineGenerateCertificate: no qualified cert");
                }
                return new X509Certificate(stream);
            }
        } catch (IOException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error reading certificate: ");
            stringBuffer.append(e.getMessage());
            throw new CertificateException(stringBuffer.toString());
        }
    }

    @Override // iaik.x509.CertificateFactory, java.security.cert.CertificateFactorySpi
    public Collection engineGenerateCertificates(InputStream inputStream) throws CertificateException {
        Object[] array = super.engineGenerateCertificates(inputStream).toArray();
        CollectionVector collectionVector = new CollectionVector();
        for (Object obj : array) {
            X509Certificate x509Certificate = (X509Certificate) obj;
            try {
                collectionVector.add(QualifiedCertificate.isQualifedCertificate(x509Certificate));
            } catch (QualifiedCertificateException unused) {
                collectionVector.add(x509Certificate);
            }
        }
        return collectionVector;
    }
}
