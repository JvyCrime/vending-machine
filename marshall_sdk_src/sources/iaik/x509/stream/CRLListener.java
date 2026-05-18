package iaik.x509.stream;

import iaik.asn1.structures.AlgorithmID;
import iaik.asn1.structures.Name;
import iaik.x509.RevokedCertificate;
import iaik.x509.X509ExtensionException;
import iaik.x509.X509Extensions;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.cert.CRLException;
import java.util.Date;

/* JADX INFO: loaded from: classes2.dex */
public interface CRLListener {
    void extensions(X509Extensions x509Extensions) throws X509ExtensionException, CRLException;

    Signature getSignature(AlgorithmID algorithmID) throws NoSuchAlgorithmException, InvalidKeyException;

    void header(int i, Name name, Date date, Date date2) throws CRLException;

    void revokedCertificate(RevokedCertificate revokedCertificate) throws X509ExtensionException, CRLException;

    void signature(AlgorithmID algorithmID, byte[] bArr, boolean z) throws CRLException;
}
