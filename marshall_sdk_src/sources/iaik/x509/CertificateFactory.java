package iaik.x509;

import iaik.pkcs.PKCS7CertList;
import iaik.utils.CollectionVector;
import iaik.utils.Util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactorySpi;
import java.util.Collection;

/* JADX INFO: loaded from: classes2.dex */
public class CertificateFactory extends CertificateFactorySpi {
    private static Collection a(Object[] objArr) {
        CollectionVector collectionVector = new CollectionVector();
        if (objArr != null) {
            for (Object obj : objArr) {
                collectionVector.add(obj);
            }
        }
        return collectionVector;
    }

    @Override // java.security.cert.CertificateFactorySpi
    public CRL engineGenerateCRL(InputStream inputStream) throws CRLException {
        try {
            return new X509CRL(inputStream);
        } catch (IOException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error reading from InputStream: ");
            stringBuffer.append(e.getMessage());
            throw new CRLException(stringBuffer.toString());
        }
    }

    @Override // java.security.cert.CertificateFactorySpi
    public Collection engineGenerateCRLs(InputStream inputStream) throws Throwable {
        ByteArrayInputStream byteArrayInputStream;
        ByteArrayInputStream byteArrayInputStream2 = null;
        X509CRL[] cRLChain = null;
        try {
            try {
                byteArrayInputStream = new ByteArrayInputStream(Util.readStream(inputStream));
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e) {
            e = e;
        }
        try {
            byteArrayInputStream.mark(byteArrayInputStream.available());
            boolean z = false;
            try {
                cRLChain = new PKCS7CertList(byteArrayInputStream).getCRLList();
                z = true;
            } catch (Exception unused) {
            }
            if (!z) {
                byteArrayInputStream.reset();
                byteArrayInputStream.mark(byteArrayInputStream.available());
                cRLChain = Util.readCRLChain(byteArrayInputStream);
            }
            Collection collectionA = a(cRLChain);
            try {
                byteArrayInputStream.close();
            } catch (IOException unused2) {
            }
            return collectionA;
        } catch (IOException e2) {
            e = e2;
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error reading object: ");
            stringBuffer.append(e.getMessage());
            throw new CRLException(stringBuffer.toString());
        } catch (Throwable th2) {
            th = th2;
            byteArrayInputStream2 = byteArrayInputStream;
            if (byteArrayInputStream2 != null) {
                try {
                    byteArrayInputStream2.close();
                } catch (IOException unused3) {
                }
            }
            throw th;
        }
    }

    @Override // java.security.cert.CertificateFactorySpi
    public Certificate engineGenerateCertificate(InputStream inputStream) throws CertificateException {
        try {
            return new X509Certificate(inputStream);
        } catch (IOException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error reading from InputStream: ");
            stringBuffer.append(e.getMessage());
            throw new CertificateException(stringBuffer.toString());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:60:0x009f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.security.cert.CertificateFactorySpi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.Collection engineGenerateCertificates(java.io.InputStream r7) throws java.lang.Throwable {
        /*
            r6 = this;
            boolean r0 = r7 instanceof iaik.utils.ASN1InputStream
            if (r0 == 0) goto L7
            iaik.utils.ASN1InputStream r7 = (iaik.utils.ASN1InputStream) r7
            goto Ld
        L7:
            iaik.utils.ASN1InputStream r0 = new iaik.utils.ASN1InputStream     // Catch: java.io.IOException -> La3
            r0.<init>(r7)     // Catch: java.io.IOException -> La3
            r7 = r0
        Ld:
            r0 = 0
            byte[] r7 = iaik.utils.Util.readStream(r7)     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L7a
            java.io.ByteArrayInputStream r1 = new java.io.ByteArrayInputStream     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L7a
            r1.<init>(r7)     // Catch: java.lang.Throwable -> L78 java.lang.Exception -> L7a
            int r7 = r1.available()     // Catch: java.lang.Exception -> L7b java.lang.Throwable -> L9b
            r1.mark(r7)     // Catch: java.lang.Exception -> L7b java.lang.Throwable -> L9b
            r7 = 0
            r2 = 1
            iaik.pkcs.PKCS7CertList r3 = new iaik.pkcs.PKCS7CertList     // Catch: java.lang.Exception -> L2c java.io.IOException -> L2e java.lang.Throwable -> L9b
            r3.<init>(r1)     // Catch: java.lang.Exception -> L2c java.io.IOException -> L2e java.lang.Throwable -> L9b
            iaik.x509.X509Certificate[] r7 = r3.getCertificateList()     // Catch: java.lang.Exception -> L2c java.io.IOException -> L2e java.lang.Throwable -> L9b
            r3 = r7
            r7 = 1
            goto L32
        L2c:
            r3 = r0
            goto L32
        L2e:
            r3 = move-exception
            r5 = r3
            r3 = r0
            r0 = r5
        L32:
            if (r7 != 0) goto L45
            r1.reset()     // Catch: java.lang.Exception -> L7b java.lang.Throwable -> L9b
            int r4 = r1.available()     // Catch: java.lang.Exception -> L7b java.lang.Throwable -> L9b
            r1.mark(r4)     // Catch: java.lang.Exception -> L7b java.lang.Throwable -> L9b
            iaik.x509.X509Certificate[] r3 = iaik.utils.Util.readCertificateChain(r1)     // Catch: java.lang.Exception -> L44 java.lang.Throwable -> L9b
            r7 = 1
            goto L45
        L44:
        L45:
            if (r7 != 0) goto L5a
            r1.reset()     // Catch: java.lang.Exception -> L7b java.lang.Throwable -> L9b
            int r4 = r1.available()     // Catch: java.lang.Exception -> L7b java.lang.Throwable -> L9b
            r1.mark(r4)     // Catch: java.lang.Exception -> L7b java.lang.Throwable -> L9b
            byte[] r4 = iaik.utils.Util.readStream(r1)     // Catch: java.lang.Exception -> L5a java.lang.Throwable -> L9b
            iaik.x509.X509Certificate[] r3 = iaik.utils.Util.decodePkiPath(r4)     // Catch: java.lang.Exception -> L5a java.lang.Throwable -> L9b
            goto L5b
        L5a:
            r2 = r7
        L5b:
            if (r2 != 0) goto L70
            r1.reset()     // Catch: java.lang.Exception -> L7b java.lang.Throwable -> L9b
            int r7 = r1.available()     // Catch: java.lang.Exception -> L7b java.lang.Throwable -> L9b
            r1.mark(r7)     // Catch: java.lang.Exception -> L7b java.lang.Throwable -> L9b
            iaik.pkcs.NetscapeCertList r7 = new iaik.pkcs.NetscapeCertList     // Catch: java.lang.Exception -> L7b java.lang.Throwable -> L9b
            r7.<init>(r1)     // Catch: java.lang.Exception -> L7b java.lang.Throwable -> L9b
            iaik.x509.X509Certificate[] r3 = r7.getCertificateList()     // Catch: java.lang.Exception -> L7b java.lang.Throwable -> L9b
        L70:
            java.util.Collection r7 = a(r3)     // Catch: java.lang.Exception -> L7b java.lang.Throwable -> L9b
            r1.close()     // Catch: java.io.IOException -> L77
        L77:
            return r7
        L78:
            r7 = move-exception
            goto L9d
        L7a:
            r1 = r0
        L7b:
            java.security.cert.CertificateException r7 = new java.security.cert.CertificateException     // Catch: java.lang.Throwable -> L9b
            java.lang.StringBuffer r2 = new java.lang.StringBuffer     // Catch: java.lang.Throwable -> L9b
            r2.<init>()     // Catch: java.lang.Throwable -> L9b
            java.lang.String r3 = "Error parsing certificates! "
            r2.append(r3)     // Catch: java.lang.Throwable -> L9b
            if (r0 != 0) goto L8c
            java.lang.String r0 = ""
            goto L90
        L8c:
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L9b
        L90:
            r2.append(r0)     // Catch: java.lang.Throwable -> L9b
            java.lang.String r0 = r2.toString()     // Catch: java.lang.Throwable -> L9b
            r7.<init>(r0)     // Catch: java.lang.Throwable -> L9b
            throw r7     // Catch: java.lang.Throwable -> L9b
        L9b:
            r7 = move-exception
            r0 = r1
        L9d:
            if (r0 == 0) goto La2
            r0.close()     // Catch: java.io.IOException -> La2
        La2:
            throw r7
        La3:
            r7 = move-exception
            java.security.cert.CertificateException r0 = new java.security.cert.CertificateException
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
            java.lang.String r2 = "Error reading certificates: "
            r1.append(r2)
            java.lang.String r7 = r7.toString()
            r1.append(r7)
            java.lang.String r7 = r1.toString()
            r0.<init>(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.x509.CertificateFactory.engineGenerateCertificates(java.io.InputStream):java.util.Collection");
    }
}
