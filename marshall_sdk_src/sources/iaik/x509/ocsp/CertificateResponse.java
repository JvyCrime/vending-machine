package iaik.x509.ocsp;

import iaik.asn1.ASN1Type;
import iaik.asn1.structures.GeneralName;
import iaik.x509.X509Certificate;

/* JADX INFO: loaded from: classes2.dex */
public interface CertificateResponse extends ASN1Type {
    ReqCert getReqCert();

    boolean isResponseFor(X509Certificate x509Certificate, X509Certificate x509Certificate2, GeneralName generalName) throws OCSPException;

    boolean isResponseFor(ReqCert reqCert);

    String toString();
}
