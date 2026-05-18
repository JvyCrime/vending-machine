package iaik.x509;

import iaik.pkcs.NetscapeCertList;
import iaik.pkcs.PKCS7CertList;
import iaik.pkcs.PKCSException;
import iaik.utils.Util;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.cert.CertPath;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class X509CertPath extends CertPath {
    public static final String DEFAULT_ENCODING_FORMAT = "PkiPath";
    public static final String DER = "DER";
    public static final String NETSCAPE = "NETSCAPE";
    public static final String PEM = "PEM";
    public static final String PKCS7 = "PKCS7";
    public static final String PKI_PATH = "PkiPath";
    static final String a = "PkiPath".toUpperCase();
    private static final List b;
    private X509Certificate[] c;

    static {
        ArrayList arrayList = new ArrayList();
        arrayList.add("PkiPath");
        arrayList.add(DER);
        arrayList.add(PEM);
        arrayList.add(PKCS7);
        arrayList.add(NETSCAPE);
        b = Collections.unmodifiableList(arrayList);
    }

    public X509CertPath(List list) throws CertificateException {
        X509Certificate[] x509CertificateArrArrangeCertificateChain;
        super("X.509");
        Objects.requireNonNull(list, "Certificates must not be null!");
        this.c = new X509Certificate[list.size()];
        int i = 0;
        for (Object obj : list) {
            if (!(obj instanceof java.security.cert.X509Certificate)) {
                throw new CertificateException("Certificate list must contain X509Certificate objects only!");
            }
            this.c[i] = Util.convertCertificate((java.security.cert.X509Certificate) obj);
            i++;
        }
        X509Certificate[] x509CertificateArr = this.c;
        if (x509CertificateArr.length <= 0 || (x509CertificateArrArrangeCertificateChain = Util.arrangeCertificateChain(x509CertificateArr, false)) == null) {
            return;
        }
        this.c = x509CertificateArrArrangeCertificateChain;
    }

    public X509CertPath(X509Certificate[] x509CertificateArr) {
        super("X.509");
        Objects.requireNonNull(x509CertificateArr, "Certificates must not be null!");
        X509Certificate[] x509CertificateArrArrangeCertificateChain = x509CertificateArr.length > 0 ? Util.arrangeCertificateChain(x509CertificateArr, false) : null;
        this.c = x509CertificateArrArrangeCertificateChain != null ? x509CertificateArrArrangeCertificateChain : x509CertificateArr;
    }

    static Iterator a() {
        return b.iterator();
    }

    @Override // java.security.cert.CertPath
    public List getCertificates() {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            X509Certificate[] x509CertificateArr = this.c;
            if (i >= x509CertificateArr.length) {
                return Collections.unmodifiableList(arrayList);
            }
            arrayList.add(x509CertificateArr[i]);
            i++;
        }
    }

    @Override // java.security.cert.CertPath
    public byte[] getEncoded() throws CertificateEncodingException {
        try {
            X509Certificate[] x509CertificateArr = this.c;
            int length = x509CertificateArr.length;
            X509Certificate[] x509CertificateArr2 = new X509Certificate[length];
            if (length > 0) {
                System.arraycopy(x509CertificateArr, 0, x509CertificateArr2, 0, x509CertificateArr.length);
                Util.reverseArray(x509CertificateArr2, 0, length);
            }
            return Util.encodeAsPkiPath(x509CertificateArr2, false);
        } catch (CertificateException e) {
            throw new CertificateEncodingException(e.toString());
        }
    }

    @Override // java.security.cert.CertPath
    public byte[] getEncoded(String str) throws CertificateEncodingException {
        Objects.requireNonNull(str, "encoding must not be null!");
        String upperCase = str.toUpperCase();
        if (upperCase.equals(a)) {
            return getEncoded();
        }
        int i = 0;
        if (upperCase.equals(DER)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
            while (true) {
                try {
                    X509Certificate[] x509CertificateArr = this.c;
                    if (i >= x509CertificateArr.length) {
                        return byteArrayOutputStream.toByteArray();
                    }
                    x509CertificateArr[i].writeTo(byteArrayOutputStream);
                    i++;
                } catch (IOException e) {
                    throw new CertificateEncodingException(e.toString());
                }
            }
        } else {
            if (!upperCase.equals(PEM)) {
                if (upperCase.equals(PKCS7)) {
                    PKCS7CertList pKCS7CertList = new PKCS7CertList();
                    pKCS7CertList.setCertificateList(this.c);
                    try {
                        return pKCS7CertList.toByteArray();
                    } catch (PKCSException e2) {
                        throw new CertificateEncodingException(e2.toString());
                    }
                }
                if (upperCase.equals(NETSCAPE)) {
                    NetscapeCertList netscapeCertList = new NetscapeCertList();
                    netscapeCertList.setCertificateList(this.c);
                    try {
                        return netscapeCertList.toByteArray();
                    } catch (PKCSException e3) {
                        throw new CertificateEncodingException(e3.toString());
                    }
                }
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Requested encoding format \"");
                stringBuffer.append(str);
                stringBuffer.append("\" not supported!");
                throw new CertificateEncodingException(stringBuffer.toString());
            }
            ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream(2048);
            while (true) {
                try {
                    X509Certificate[] x509CertificateArr2 = this.c;
                    if (i >= x509CertificateArr2.length) {
                        return byteArrayOutputStream2.toByteArray();
                    }
                    byteArrayOutputStream2.write(Util.toPemArray(x509CertificateArr2[i]));
                    i++;
                } catch (IOException e4) {
                    throw new CertificateEncodingException(e4.toString());
                }
            }
        }
    }

    @Override // java.security.cert.CertPath
    public Iterator getEncodings() {
        return b.iterator();
    }
}
