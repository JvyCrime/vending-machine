package iaik.security.jsse.net;

import iaik.security.jsse.utils.Debug;
import iaik.security.ssl.ChainVerifier;
import iaik.security.ssl.SSLCertificateRuntimeException;
import iaik.security.ssl.SSLTransport;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: loaded from: classes.dex */
public class JSSEChainVerifier extends ChainVerifier {
    private X509TrustManager a;
    private Debug b = Debug.getInstance();

    public JSSEChainVerifier(X509TrustManager x509TrustManager) {
        this.a = x509TrustManager;
    }

    @Override // iaik.security.ssl.ChainVerifier
    public boolean verifyChain(X509Certificate[] x509CertificateArr, SSLTransport sSLTransport) {
        this.b.println("Verify chain ...");
        if (x509CertificateArr == null) {
            return this.nullTrusted;
        }
        dumpCertificateChain(x509CertificateArr, sSLTransport);
        if (this.a != null) {
            try {
                if (sSLTransport.getUseClientMode()) {
                    if (!verifyServer(x509CertificateArr, sSLTransport)) {
                        throw new SSLCertificateRuntimeException("Invalid server name in certificate!");
                    }
                    this.a.checkServerTrusted(x509CertificateArr, "UNKNOWN");
                } else {
                    this.a.checkClientTrusted(x509CertificateArr, "UNKNOWN");
                }
                return true;
            } catch (SSLCertificateRuntimeException e) {
                throw e;
            } catch (Exception e2) {
                throw new SSLCertificateRuntimeException(e2);
            }
        }
        this.b.println("No TrustManager installed, rejecting connection");
        return false;
    }

    @Override // iaik.security.ssl.ChainVerifier
    public Enumeration getTrustedPrincipals() {
        Vector vector;
        if (this.a != null) {
            vector = new Vector(Arrays.asList(this.a.getAcceptedIssuers()));
        } else {
            vector = new Vector(Collections.EMPTY_LIST);
        }
        return vector.elements();
    }

    @Override // iaik.security.ssl.ChainVerifier
    public Principal[] getTrustedPrincipalsArray() {
        X509TrustManager x509TrustManager = this.a;
        if (x509TrustManager == null) {
            return new Principal[0];
        }
        X509Certificate[] acceptedIssuers = x509TrustManager.getAcceptedIssuers();
        if (acceptedIssuers == null) {
            return new Principal[0];
        }
        int length = acceptedIssuers.length;
        Principal[] principalArr = new Principal[length];
        for (int i = 0; i < length; i++) {
            principalArr[i] = acceptedIssuers[i].getSubjectDN();
        }
        return principalArr;
    }

    @Override // iaik.security.ssl.ChainVerifier
    public X509Certificate[] getTrustedCertificatesArray() {
        X509TrustManager x509TrustManager = this.a;
        return x509TrustManager == null ? new X509Certificate[0] : x509TrustManager.getAcceptedIssuers();
    }

    @Override // iaik.security.ssl.ChainVerifier
    public Enumeration getTrustedCertificates() {
        X509Certificate[] trustedCertificatesArray = getTrustedCertificatesArray();
        Vector vector = new Vector(trustedCertificatesArray.length);
        for (X509Certificate x509Certificate : trustedCertificatesArray) {
            vector.addElement(x509Certificate);
        }
        return vector.elements();
    }
}
