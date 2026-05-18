package iaik.security.jsse;

import iaik.security.jsse.net.IAIKSSLServerSocketFactory;
import iaik.security.jsse.net.IAIKSSLSocketFactory;
import iaik.security.jsse.net.JSSESessionManager;
import iaik.security.jsse.utils.Debug;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContextSpi;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: loaded from: classes.dex */
public abstract class IAIKSSLContext extends SSLContextSpi {
    public static final int SSL = 0;
    public static final int SSLv2 = 1;
    public static final int SSLv3 = 2;
    public static final int TLS = 6;
    public static final int TLSv1 = 3;
    public static final int TLSv11 = 4;
    public static final int TLSv12 = 5;
    private SecureRandom a = null;
    private KeyManager[] b = null;
    private TrustManager[] c = null;
    private Debug f = Debug.getInstance();
    private JSSESessionManager d = new JSSESessionManager();
    private JSSESessionManager e = new JSSESessionManager();

    @Override // javax.net.ssl.SSLContextSpi
    protected SSLEngine engineCreateSSLEngine() {
        return null;
    }

    @Override // javax.net.ssl.SSLContextSpi
    protected SSLEngine engineCreateSSLEngine(String str, int i) {
        return null;
    }

    public abstract int getSSLVersion();

    @Override // javax.net.ssl.SSLContextSpi
    protected void engineInit(KeyManager[] keyManagerArr, TrustManager[] trustManagerArr, SecureRandom secureRandom) throws KeyManagementException {
        this.f.println("IAIKSSLContext.enigneInit()");
        this.b = keyManagerArr;
        this.c = trustManagerArr;
        this.a = secureRandom;
        if (keyManagerArr != null && keyManagerArr.length > 0) {
            if (a() == null) {
                throw new KeyManagementException("At present only X509KeyManagers are supported");
            }
        } else {
            try {
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
                keyManagerFactory.init(null, null);
                this.b = keyManagerFactory.getKeyManagers();
            } catch (Exception unused) {
            }
        }
        if (this.c == null) {
            try {
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("PKIX");
                trustManagerFactory.init((KeyStore) null);
                this.c = trustManagerFactory.getTrustManagers();
            } catch (Exception unused2) {
            }
        }
    }

    @Override // javax.net.ssl.SSLContextSpi
    protected SSLSocketFactory engineGetSocketFactory() {
        this.f.println("IAIKSSLContext.engineGetSocketFactory()");
        IAIKSSLSocketFactory iAIKSSLSocketFactory = new IAIKSSLSocketFactory(b(), a(), this.a, this.d);
        iAIKSSLSocketFactory.setVersion(getSSLVersion());
        return iAIKSSLSocketFactory;
    }

    @Override // javax.net.ssl.SSLContextSpi
    protected SSLServerSocketFactory engineGetServerSocketFactory() {
        this.f.println("IAIKSSLContext.engineGetServerSocketFactory()");
        IAIKSSLServerSocketFactory iAIKSSLServerSocketFactory = new IAIKSSLServerSocketFactory(b(), a(), this.a, this.e);
        iAIKSSLServerSocketFactory.setVersion(getSSLVersion());
        return iAIKSSLServerSocketFactory;
    }

    private X509TrustManager b() {
        if (this.c == null) {
            return null;
        }
        int i = 0;
        while (true) {
            TrustManager[] trustManagerArr = this.c;
            if (i >= trustManagerArr.length) {
                return null;
            }
            if (trustManagerArr[i] instanceof X509TrustManager) {
                return (X509TrustManager) trustManagerArr[i];
            }
            i++;
        }
    }

    private X509KeyManager a() {
        if (this.b == null) {
            return null;
        }
        int i = 0;
        while (true) {
            KeyManager[] keyManagerArr = this.b;
            if (i >= keyManagerArr.length) {
                return null;
            }
            if (keyManagerArr[i] instanceof X509KeyManager) {
                return (X509KeyManager) keyManagerArr[i];
            }
            i++;
        }
    }

    @Override // javax.net.ssl.SSLContextSpi
    protected SSLSessionContext engineGetServerSessionContext() {
        return this.e;
    }

    @Override // javax.net.ssl.SSLContextSpi
    protected SSLSessionContext engineGetClientSessionContext() {
        return this.d;
    }
}
