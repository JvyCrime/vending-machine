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
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Vector;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: loaded from: classes.dex */
public class IAIKSSLSocketFactory extends SSLSocketFactory {
    private static Debug a = Debug.getInstance();
    private c b;
    private X509KeyManager c;
    private SecureRandom d;
    private X509TrustManager e;
    private ExtensionList f;
    private Boolean g;
    private Boolean h;
    private Boolean i;
    private Boolean j;

    public IAIKSSLSocketFactory() {
        this(Util.getDefaultTrustManager(), Util.getDefaultKeyManager(), null);
        a.println("Default Factory");
    }

    public IAIKSSLSocketFactory(X509TrustManager x509TrustManager, X509KeyManager x509KeyManager, SecureRandom secureRandom) {
        this(x509TrustManager, x509KeyManager, secureRandom, null);
    }

    public IAIKSSLSocketFactory(X509TrustManager x509TrustManager, X509KeyManager x509KeyManager, SecureRandom secureRandom, JSSESessionManager jSSESessionManager) {
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        a.println("SSLSocketFactory Constructor called");
        this.e = x509TrustManager;
        this.c = x509KeyManager;
        this.d = secureRandom;
        this.b = secureRandom != null ? new c(this.d) : new c();
        if (this.c != null) {
            a.println("New Context : KeyManager -> add CLientCredentials to Context");
        } else {
            a.println("No KeyManager present, no ClientCredentials added");
        }
        this.b.a(this.c);
        this.b.setEnabledCipherSuiteList(new CipherSuiteList(2));
        this.b.setSessionManager(jSSESessionManager == null ? new JSSESessionManager() : jSSESessionManager);
        this.b.setDebugStream((Writer) null);
        this.b.updateCipherSuites();
        X509TrustManager x509TrustManager2 = this.e;
        if (x509TrustManager2 != null) {
            Debug debug = a;
            StringBuffer stringBuffer = new StringBuffer("Setting Trust Manager: ");
            stringBuffer.append(this.e);
            debug.println(stringBuffer.toString());
            this.b.setChainVerifier(new JSSEChainVerifier(this.e));
            return;
        }
        this.b.setChainVerifier(new JSSEChainVerifier(x509TrustManager2));
        a.println("No TrustManager present, all Certificates will be rejected");
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        String property = System.getProperty("java.version");
        if (property != null && property.startsWith("1.5")) {
            return Util.getJSSEPlugableCipherSuites();
        }
        return Util.getImplementedCipherSuites();
    }

    public static synchronized SocketFactory getDefault() {
        return new IAIKSSLSocketFactory(Util.getDefaultTrustManager(), Util.getDefaultKeyManager(), null);
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        CipherSuiteList enabledCipherSuiteList = this.b.getEnabledCipherSuiteList();
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

    private void a(c cVar) {
        Boolean bool = this.g;
        if (bool != null) {
            cVar.setDisableRenegotiation(bool.booleanValue());
        }
        Boolean bool2 = this.h;
        if (bool2 != null) {
            cVar.setAllowLegacyRenegotiation(bool2.booleanValue());
        }
        Boolean bool3 = this.i;
        if (bool3 != null) {
            cVar.setUseNoRenegotiationWarnings(bool3.booleanValue());
        }
        Boolean bool4 = this.j;
        if (bool4 != null) {
            cVar.setAllowIdentityChangeDuringRenegotiation(bool4.booleanValue());
        }
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket() throws IOException {
        c cVar = (c) this.b.clone();
        a(cVar);
        ExtensionList extensionList = this.f;
        if (extensionList == null || !extensionList.hasExtensions()) {
            return new IAIKSSLSocketWrapper(cVar);
        }
        a(cVar, (ExtensionList) this.f.clone());
        IAIKSSLSocketWrapper iAIKSSLSocketWrapper = new IAIKSSLSocketWrapper(cVar);
        iAIKSSLSocketWrapper.setEnabledProtocols(new String[]{Util.TLSv1, Util.TLSv12});
        return iAIKSSLSocketWrapper;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        c cVar = (c) this.b.clone();
        a(cVar);
        ExtensionList extensionList = this.f;
        if (extensionList == null || !extensionList.hasExtensions()) {
            return new IAIKSSLSocketWrapper(socket, str, i, z, cVar);
        }
        a(cVar, (ExtensionList) this.f.clone());
        IAIKSSLSocketWrapper iAIKSSLSocketWrapper = new IAIKSSLSocketWrapper(socket, str, i, z, cVar);
        iAIKSSLSocketWrapper.setEnabledProtocols(new String[]{Util.TLSv1, Util.TLSv12});
        return iAIKSSLSocketWrapper;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        c cVar = (c) this.b.clone();
        a(cVar);
        ExtensionList extensionList = this.f;
        if (extensionList == null || !extensionList.hasExtensions()) {
            return new IAIKSSLSocketWrapper(inetAddress, i, inetAddress2, i2, cVar);
        }
        a(cVar, (ExtensionList) this.f.clone());
        IAIKSSLSocketWrapper iAIKSSLSocketWrapper = new IAIKSSLSocketWrapper(inetAddress, i, inetAddress2, i2, cVar);
        iAIKSSLSocketWrapper.setEnabledProtocols(new String[]{Util.TLSv1, Util.TLSv12});
        return iAIKSSLSocketWrapper;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        c cVar = (c) this.b.clone();
        a(cVar);
        ExtensionList extensionList = this.f;
        if (extensionList == null || !extensionList.hasExtensions()) {
            return new IAIKSSLSocketWrapper(inetAddress, i, cVar);
        }
        a(cVar, (ExtensionList) this.f.clone());
        IAIKSSLSocketWrapper iAIKSSLSocketWrapper = new IAIKSSLSocketWrapper(inetAddress, i, cVar);
        iAIKSSLSocketWrapper.setEnabledProtocols(new String[]{Util.TLSv1, Util.TLSv12});
        return iAIKSSLSocketWrapper;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException {
        c cVar = (c) this.b.clone();
        a(cVar);
        ExtensionList extensionList = this.f;
        if (extensionList == null || !extensionList.hasExtensions()) {
            return new IAIKSSLSocketWrapper(str, i, inetAddress, i2, cVar);
        }
        a(cVar, (ExtensionList) this.f.clone());
        IAIKSSLSocketWrapper iAIKSSLSocketWrapper = new IAIKSSLSocketWrapper(str, i, inetAddress, i2, cVar);
        iAIKSSLSocketWrapper.setEnabledProtocols(new String[]{Util.TLSv1, Util.TLSv12});
        return iAIKSSLSocketWrapper;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i) throws IOException {
        Debug debug = a;
        StringBuffer stringBuffer = new StringBuffer("Creating Socket ");
        stringBuffer.append(str);
        stringBuffer.append(":");
        stringBuffer.append(i);
        debug.println(stringBuffer.toString());
        c cVar = (c) this.b.clone();
        a(cVar);
        ExtensionList extensionList = this.f;
        if (extensionList == null || !extensionList.hasExtensions()) {
            return new IAIKSSLSocketWrapper(str, i, cVar);
        }
        a(cVar, (ExtensionList) this.f.clone());
        IAIKSSLSocketWrapper iAIKSSLSocketWrapper = new IAIKSSLSocketWrapper(str, i, cVar);
        iAIKSSLSocketWrapper.setEnabledProtocols(new String[]{Util.TLSv1, Util.TLSv12});
        return iAIKSSLSocketWrapper;
    }

    public void setVersion(int i) {
        switch (i) {
            case 0:
                this.b.setAllowedProtocolVersions(2, SSLContext.VERSION_TLS11);
                break;
            case 1:
                this.b.setAllowedProtocolVersions(2, 2);
                break;
            case 2:
                this.b.setAllowedProtocolVersions(SSLContext.VERSION_SSL30, SSLContext.VERSION_SSL30);
                break;
            case 3:
                this.b.setAllowedProtocolVersions(SSLContext.VERSION_TLS10, SSLContext.VERSION_TLS10);
                break;
            case 4:
                this.b.setAllowedProtocolVersions(SSLContext.VERSION_TLS11, SSLContext.VERSION_TLS11);
                break;
            case 5:
                this.b.setAllowedProtocolVersions(SSLContext.VERSION_TLS12, SSLContext.VERSION_TLS12);
                break;
            case 6:
                this.b.setAllowedProtocolVersions(SSLContext.VERSION_SSL30, SSLContext.VERSION_TLS12);
                break;
        }
    }

    public void setCacheTerminatedSessions(boolean z) {
        this.b.setCacheTerminatedSessions(z);
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

    private final c a(c cVar, ExtensionList extensionList) {
        if (extensionList.getExtension(ClientCertificateURL.TYPE) != null) {
            throw new IllegalArgumentException("client_certificate_url extension not supported! Use iSaSiLk API!");
        }
        if (extensionList.getExtension(SessionTicket.TYPE) != null) {
            throw new IllegalArgumentException("session_ticket extension not supported! Use iSaSiLk API!");
        }
        if (extensionList.getExtension(CertificateStatusRequest.TYPE) != null) {
            throw new IllegalArgumentException("cert_status_request extension not supported! Use iSaSiLk API!");
        }
        cVar.setExtensions(extensionList);
        return cVar;
    }
}
