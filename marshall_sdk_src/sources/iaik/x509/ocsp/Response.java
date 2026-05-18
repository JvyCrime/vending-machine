package iaik.x509.ocsp;

import iaik.asn1.CodingException;
import iaik.asn1.ObjectID;
import iaik.asn1.structures.GeneralName;
import iaik.x509.X509Certificate;

/* JADX INFO: loaded from: classes2.dex */
public abstract class Response {
    public abstract void decode(byte[] bArr) throws CodingException;

    public abstract CertificateResponse getCertificateResponse(X509Certificate x509Certificate, X509Certificate x509Certificate2, GeneralName generalName) throws OCSPException;

    public abstract CertificateResponse getCertificateResponse(ReqCert reqCert) throws OCSPException;

    public abstract byte[] getEncoded();

    public String getName() {
        return getResponseType().getName();
    }

    public abstract ObjectID getResponseType();

    public abstract String toString();
}
