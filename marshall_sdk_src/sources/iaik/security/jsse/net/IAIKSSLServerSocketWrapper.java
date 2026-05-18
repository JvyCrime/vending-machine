package iaik.security.jsse.net;

import iaik.security.jsse.utils.Debug;
import iaik.security.jsse.utils.Util;
import iaik.security.ssl.CipherSuite;
import iaik.security.ssl.CipherSuiteList;
import iaik.security.ssl.SSLServerContext;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLServerSocket;

/* JADX INFO: loaded from: classes.dex */
public class IAIKSSLServerSocketWrapper extends SSLServerSocket {
    private static Debug b = Debug.getInstance();
    private String[] a;
    private SSLServerContext c;
    private boolean d;
    private boolean e;
    private boolean f;

    protected IAIKSSLServerSocketWrapper(int i, SSLServerContext sSLServerContext) throws IOException {
        super(i);
        this.c = null;
        this.d = false;
        this.e = true;
        this.f = true;
        this.c = sSLServerContext;
    }

    protected IAIKSSLServerSocketWrapper(int i, int i2, SSLServerContext sSLServerContext) throws IOException {
        super(i, i2);
        this.c = null;
        this.d = false;
        this.e = true;
        this.f = true;
        this.c = sSLServerContext;
    }

    protected IAIKSSLServerSocketWrapper(int i, int i2, InetAddress inetAddress, SSLServerContext sSLServerContext) throws IOException {
        super(i, i2, inetAddress);
        this.c = null;
        this.d = false;
        this.e = true;
        this.f = true;
        this.c = sSLServerContext;
    }

    @Override // java.net.ServerSocket
    public Socket accept() throws IOException {
        Socket socketAccept = super.accept();
        IAIKSSLSocketWrapper iAIKSSLSocketWrapper = new IAIKSSLSocketWrapper(socketAccept, this.c);
        iAIKSSLSocketWrapper.init(socketAccept, this.c, false);
        iAIKSSLSocketWrapper.setEnableSessionCreation(this.e);
        return iAIKSSLSocketWrapper;
    }

    @Override // javax.net.ssl.SSLServerSocket
    public String[] getEnabledCipherSuites() {
        CipherSuite[] array = this.c.getEnabledCipherSuiteList().toArray();
        int length = array.length;
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = array[i].toString();
        }
        return strArr;
    }

    @Override // javax.net.ssl.SSLServerSocket
    public void setEnabledCipherSuites(String[] strArr) {
        this.c.setEnabledCipherSuiteList(Util.fromStringArray(strArr));
        this.c.updateCipherSuites();
    }

    @Override // javax.net.ssl.SSLServerSocket
    public String[] getSupportedCipherSuites() {
        CipherSuiteList cipherSuiteList = new CipherSuiteList(3);
        String[] strArr = new String[cipherSuiteList.size()];
        for (int i = 0; i < cipherSuiteList.size(); i++) {
            strArr[i] = cipherSuiteList.elementAt(i).getName();
        }
        return strArr;
    }

    @Override // javax.net.ssl.SSLServerSocket
    public String[] getSupportedProtocols() {
        return Util.supportedProtocolVersions_;
    }

    @Override // javax.net.ssl.SSLServerSocket
    public String[] getEnabledProtocols() {
        String[] strArr = this.a;
        return strArr != null ? strArr : Util.toProtocol(this.c.getAllowedProtocolVersions());
    }

    @Override // javax.net.ssl.SSLServerSocket
    public void setEnabledProtocols(String[] strArr) {
        int[] protocolVersion = Util.toProtocolVersion(strArr);
        this.c.setAllowedProtocolVersions(protocolVersion[0], protocolVersion[1]);
        this.a = strArr;
    }

    @Override // javax.net.ssl.SSLServerSocket
    public void setNeedClientAuth(boolean z) {
        this.c.setRequestClientCertificate(z);
        if (z) {
            this.c.getChainVerifier().removeTrustedCertificate(null);
            this.f = false;
        }
    }

    @Override // javax.net.ssl.SSLServerSocket
    public boolean getNeedClientAuth() {
        return this.c.getRequestClientCertificate() && !this.f;
    }

    @Override // javax.net.ssl.SSLServerSocket
    public void setUseClientMode(boolean z) {
        this.d = z;
    }

    @Override // javax.net.ssl.SSLServerSocket
    public boolean getUseClientMode() {
        return this.d;
    }

    @Override // javax.net.ssl.SSLServerSocket
    public void setEnableSessionCreation(boolean z) {
        this.e = z;
    }

    @Override // javax.net.ssl.SSLServerSocket
    public boolean getEnableSessionCreation() {
        return this.e;
    }

    @Override // javax.net.ssl.SSLServerSocket
    public void setWantClientAuth(boolean z) {
        this.c.setRequestClientCertificate(z);
        if (z) {
            this.c.getChainVerifier().addTrustedCertificate(null);
            this.f = true;
        }
    }

    @Override // javax.net.ssl.SSLServerSocket
    public boolean getWantClientAuth() {
        return this.c.getRequestClientCertificate() && this.f;
    }
}
