package iaik.security.jsse.net;

import iaik.security.jsse.utils.Debug;
import iaik.security.jsse.utils.Util;
import iaik.security.ssl.CertificateStatusRequest;
import iaik.security.ssl.CipherSuite;
import iaik.security.ssl.CipherSuiteList;
import iaik.security.ssl.ClientCertificateURL;
import iaik.security.ssl.ExtensionList;
import iaik.security.ssl.SSLContext;
import iaik.security.ssl.SessionTicket;
import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Vector;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: loaded from: classes.dex */
public class IAIKSSLServerSocketFactory extends SSLServerSocketFactory {
    private static Debug b = Debug.getInstance();
    private JSSEServerContext a;
    private X509KeyManager c;
    private SecureRandom d;
    private X509TrustManager e;
    private ExtensionList f;
    private Boolean g;
    private Boolean h;
    private Boolean i;
    private Boolean j;

    public IAIKSSLServerSocketFactory() {
        this(Util.getDefaultTrustManager(), Util.getDefaultKeyManager(), null);
        b.println("Default Factory");
    }

    public IAIKSSLServerSocketFactory(X509TrustManager x509TrustManager, X509KeyManager x509KeyManager, SecureRandom secureRandom) {
        this(x509TrustManager, x509KeyManager, secureRandom, null);
    }

    public IAIKSSLServerSocketFactory(X509TrustManager x509TrustManager, X509KeyManager x509KeyManager, SecureRandom secureRandom, JSSESessionManager jSSESessionManager) {
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.e = x509TrustManager;
        this.c = x509KeyManager;
        this.d = secureRandom;
        JSSEServerContext jSSEServerContext = secureRandom != null ? new JSSEServerContext(this.d) : new JSSEServerContext();
        this.a = jSSEServerContext;
        jSSEServerContext.setJSSEKeyManager(this.c);
        this.a.setEnabledCipherSuiteList(new CipherSuiteList(3));
        this.a.setSessionManager(jSSESessionManager == null ? new JSSESessionManager() : jSSESessionManager);
        this.a.setDebugStream((Writer) null);
        if (this.c == null) {
            b.println("No KeyManager present, no ServerCredentials added");
        }
        this.a.updateCipherSuites();
        X509TrustManager x509TrustManager2 = this.e;
        if (x509TrustManager2 != null) {
            this.a.setChainVerifier(new JSSEChainVerifier(x509TrustManager2));
        } else {
            this.a.setChainVerifier(null);
            b.println("No TrustManager present, ChainVerifier disabled, all Certificates will be accepted");
        }
    }

    public static ServerSocketFactory getDefault() {
        return new IAIKSSLServerSocketFactory();
    }

    @Override // javax.net.ssl.SSLServerSocketFactory
    public String[] getDefaultCipherSuites() {
        CipherSuiteList enabledCipherSuiteList = this.a.getEnabledCipherSuiteList();
        Vector vector = new Vector();
        Enumeration enumerationElements = enabledCipherSuiteList.elements();
        while (enumerationElements.hasMoreElements()) {
            String strIsPlugable = Util.isPlugable((CipherSuite) enumerationElements.nextElement());
            if (strIsPlugable != null) {
                vector.addElement(strIsPlugable);
            }
        }
        String[] strArr = new String[vector.size()];
        vector.copyInto(strArr);
        return strArr;
    }

    @Override // javax.net.ssl.SSLServerSocketFactory
    public String[] getSupportedCipherSuites() {
        String property = System.getProperty("java.version");
        if (property != null && property.startsWith("1.5")) {
            return Util.getJSSEPlugableCipherSuites();
        }
        return Util.getImplementedCipherSuites();
    }

    private void a(JSSEServerContext jSSEServerContext) {
        Boolean bool = this.g;
        if (bool != null) {
            jSSEServerContext.setDisableRenegotiation(bool.booleanValue());
        }
        Boolean bool2 = this.h;
        if (bool2 != null) {
            jSSEServerContext.setAllowLegacyRenegotiation(bool2.booleanValue());
        }
        Boolean bool3 = this.i;
        if (bool3 != null) {
            jSSEServerContext.setUseNoRenegotiationWarnings(bool3.booleanValue());
        }
        Boolean bool4 = this.j;
        if (bool4 != null) {
            jSSEServerContext.setAllowIdentityChangeDuringRenegotiation(bool4.booleanValue());
        }
    }

    @Override // javax.net.ServerSocketFactory
    public ServerSocket createServerSocket(int i) throws IOException {
        JSSEServerContext jSSEServerContext = (JSSEServerContext) this.a.clone();
        a(jSSEServerContext);
        ExtensionList extensionList = this.f;
        if (extensionList == null || !extensionList.hasExtensions()) {
            return new IAIKSSLServerSocketWrapper(i, jSSEServerContext);
        }
        IAIKSSLServerSocketWrapper iAIKSSLServerSocketWrapper = new IAIKSSLServerSocketWrapper(i, a(jSSEServerContext, (ExtensionList) this.f.clone()));
        iAIKSSLServerSocketWrapper.setEnabledProtocols(new String[]{Util.TLSv1, Util.TLSv12});
        return iAIKSSLServerSocketWrapper;
    }

    @Override // javax.net.ServerSocketFactory
    public ServerSocket createServerSocket(int i, int i2) throws IOException {
        JSSEServerContext jSSEServerContext = (JSSEServerContext) this.a.clone();
        a(jSSEServerContext);
        ExtensionList extensionList = this.f;
        if (extensionList == null || !extensionList.hasExtensions()) {
            return new IAIKSSLServerSocketWrapper(i, i2, jSSEServerContext);
        }
        IAIKSSLServerSocketWrapper iAIKSSLServerSocketWrapper = new IAIKSSLServerSocketWrapper(i, i2, a(jSSEServerContext, (ExtensionList) this.f.clone()));
        iAIKSSLServerSocketWrapper.setEnabledProtocols(new String[]{Util.TLSv1, Util.TLSv12});
        return iAIKSSLServerSocketWrapper;
    }

    @Override // javax.net.ServerSocketFactory
    public ServerSocket createServerSocket(int i, int i2, InetAddress inetAddress) throws IOException {
        JSSEServerContext jSSEServerContext = (JSSEServerContext) this.a.clone();
        a(jSSEServerContext);
        ExtensionList extensionList = this.f;
        if (extensionList == null || !extensionList.hasExtensions()) {
            return new IAIKSSLServerSocketWrapper(i, i2, inetAddress, jSSEServerContext);
        }
        IAIKSSLServerSocketWrapper iAIKSSLServerSocketWrapper = new IAIKSSLServerSocketWrapper(i, i2, inetAddress, a(jSSEServerContext, (ExtensionList) this.f.clone()));
        iAIKSSLServerSocketWrapper.setEnabledProtocols(new String[]{Util.TLSv1, Util.TLSv12});
        return iAIKSSLServerSocketWrapper;
    }

    public void setVersion(int i) {
        switch (i) {
            case 0:
                this.a.setAllowedProtocolVersions(2, SSLContext.VERSION_TLS11);
                break;
            case 1:
                this.a.setAllowedProtocolVersions(2, 2);
                break;
            case 2:
                this.a.setAllowedProtocolVersions(SSLContext.VERSION_SSL30, SSLContext.VERSION_SSL30);
                break;
            case 3:
                this.a.setAllowedProtocolVersions(SSLContext.VERSION_TLS10, SSLContext.VERSION_TLS10);
                break;
            case 4:
                this.a.setAllowedProtocolVersions(SSLContext.VERSION_TLS11, SSLContext.VERSION_TLS11);
                break;
            case 5:
                this.a.setAllowedProtocolVersions(SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12);
                break;
            case 6:
                this.a.setAllowedProtocolVersions(SSLContext.VERSION_SSL30, SSLContext.VERSION_TLS12);
                break;
        }
    }

    public void setDisableRenegotiation(boolean z) {
        this.g = new Boolean(z);
    }

    public void setAllowLegacyRenegotiation(boolean z) {
        this.h = new Boolean(z);
    }

    public void setUseNoRenegotiationWarnings(boolean z) {
        this.i = new Boolean(z);
    }

    public void setAllowIdentityChangeDuringRenegotiation(boolean z) {
        this.j = new Boolean(z);
    }

    public void setExtensions(ExtensionList extensionList) {
        this.f = extensionList;
    }

    private final JSSEServerContext a(JSSEServerContext jSSEServerContext, ExtensionList extensionList) {
        if (extensionList.getExtension(ClientCertificateURL.TYPE) != null) {
            throw new IllegalArgumentException("client_certificate_url extension not supported! Use iSaSiLk API!");
        }
        if (extensionList.getExtension(SessionTicket.TYPE) != null) {
            throw new IllegalArgumentException("session_ticket extension not supported! Use iSaSiLk API!");
        }
        if (extensionList.getExtension(CertificateStatusRequest.TYPE) != null) {
            throw new IllegalArgumentException("cert_status_request extension not supported! Use iSaSiLk API!");
        }
        jSSEServerContext.setExtensions(extensionList);
        return jSSEServerContext;
    }
}
