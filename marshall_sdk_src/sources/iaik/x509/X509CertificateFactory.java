package iaik.x509;

import iaik.pkcs.NetscapeCertList;
import iaik.pkcs.PKCS7CertList;
import iaik.pkcs.PKCSException;
import iaik.utils.ASN1InputStream;
import iaik.utils.Util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class X509CertificateFactory extends CertificateFactory {
    @Override // java.security.cert.CertificateFactorySpi
    public CertPath engineGenerateCertPath(InputStream inputStream) throws IOException, CertificateException {
        CertificateException certificateException;
        Objects.requireNonNull(inputStream, "inStream must not be null!");
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Util.readStream(inputStream));
            byteArrayInputStream.mark(byteArrayInputStream.available());
            X509Certificate[] pkiPath = null;
            try {
                certificateException = null;
                pkiPath = Util.readPkiPath(byteArrayInputStream);
            } catch (Exception e) {
                byteArrayInputStream.reset();
                byteArrayInputStream.mark(byteArrayInputStream.available());
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Error while reading certificates: ");
                stringBuffer.append(e.toString());
                certificateException = new CertificateException(stringBuffer.toString());
            }
            if (pkiPath == null) {
                try {
                    Collection collectionEngineGenerateCertificates = engineGenerateCertificates(byteArrayInputStream);
                    int size = collectionEngineGenerateCertificates.size();
                    X509Certificate[] x509CertificateArr = new X509Certificate[size];
                    Object[] array = collectionEngineGenerateCertificates.toArray();
                    for (int i = 0; i < size; i++) {
                        x509CertificateArr[i] = (X509Certificate) array[i];
                    }
                    pkiPath = x509CertificateArr;
                } catch (CertificateException e2) {
                    if (certificateException != null) {
                        throw certificateException;
                    }
                    throw e2;
                }
            }
            return new X509CertPath(pkiPath);
        } catch (IOException e3) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Error while reading certificates: ");
            stringBuffer2.append(e3.toString());
            throw new CertificateException(stringBuffer2.toString());
        }
    }

    @Override // java.security.cert.CertificateFactorySpi
    public CertPath engineGenerateCertPath(InputStream inputStream, String str) throws CertificateException {
        X509CertPath x509CertPath;
        Objects.requireNonNull(inputStream, "inStream must not be null!");
        Objects.requireNonNull(str, "encoding must not be null!");
        String upperCase = str.toUpperCase();
        try {
            if (upperCase.equals(X509CertPath.a)) {
                return new X509CertPath(Util.readPkiPath(inputStream));
            }
            ASN1InputStream aSN1InputStream = inputStream instanceof ASN1InputStream ? (ASN1InputStream) inputStream : new ASN1InputStream(inputStream);
            if (!upperCase.equals(X509CertPath.DER) && !upperCase.equals(X509CertPath.PEM)) {
                if (upperCase.equals(X509CertPath.PKCS7)) {
                    x509CertPath = new X509CertPath(new PKCS7CertList(aSN1InputStream).getCertificateList());
                } else {
                    if (!upperCase.equals(X509CertPath.NETSCAPE)) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Requested encoding format \"");
                        stringBuffer.append(str);
                        stringBuffer.append("\" not supported!");
                        throw new CertificateException(stringBuffer.toString());
                    }
                    x509CertPath = new X509CertPath(new NetscapeCertList(aSN1InputStream).getCertificateList());
                }
                return x509CertPath;
            }
            return new X509CertPath(Util.readCertificateChain(aSN1InputStream));
        } catch (PKCSException e) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Error reading certificates: ");
            stringBuffer2.append(e.toString());
            throw new CertificateException(stringBuffer2.toString());
        } catch (IOException e2) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Error reading certificates: ");
            stringBuffer3.append(e2.toString());
            throw new CertificateException(stringBuffer3.toString());
        }
    }

    @Override // java.security.cert.CertificateFactorySpi
    public CertPath engineGenerateCertPath(List list) throws CertificateException {
        return new X509CertPath(list);
    }

    @Override // java.security.cert.CertificateFactorySpi
    public Iterator engineGetCertPathEncodings() {
        return X509CertPath.a();
    }
}
