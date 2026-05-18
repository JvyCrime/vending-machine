package iaik.x509;

import java.io.Serializable;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Hashtable;

/* JADX INFO: loaded from: classes2.dex */
public class SimpleChainVerifier extends ChainVerifier implements Serializable {
    private static final long serialVersionUID = 7032874732503605656L;
    protected Hashtable signers = new Hashtable();

    public void addTrustedCertificate(java.security.cert.X509Certificate x509Certificate) {
        this.signers.put(x509Certificate, x509Certificate);
    }

    @Override // iaik.x509.ChainVerifier
    public boolean isTrustedCertificate(java.security.cert.X509Certificate x509Certificate) throws CertificateException {
        return this.signers.get(x509Certificate) != null;
    }

    public java.security.cert.X509Certificate removeTrustedCertificate(java.security.cert.X509Certificate x509Certificate) {
        return (java.security.cert.X509Certificate) this.signers.remove(x509Certificate);
    }

    public void setTrustedCertificates(java.security.cert.X509Certificate[] x509CertificateArr) {
        this.signers.clear();
        for (java.security.cert.X509Certificate x509Certificate : x509CertificateArr) {
            addTrustedCertificate(x509Certificate);
        }
    }

    public Enumeration trustedCertificates() {
        return this.signers.keys();
    }
}
