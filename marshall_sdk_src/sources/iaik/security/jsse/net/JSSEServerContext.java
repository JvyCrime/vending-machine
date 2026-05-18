package iaik.security.jsse.net;

import iaik.security.jsse.utils.Debug;
import iaik.security.ssl.ExtensionList;
import iaik.security.ssl.KeyAndCert;
import iaik.security.ssl.SSLServerContext;
import iaik.security.ssl.SSLTransport;
import iaik.security.ssl.SecurityProvider;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Objects;
import javax.net.ssl.X509KeyManager;

/* JADX INFO: loaded from: classes.dex */
public class JSSEServerContext extends SSLServerContext {
    private static Debug a = Debug.getInstance();
    protected X509KeyManager keyManager_;

    public JSSEServerContext() {
    }

    public JSSEServerContext(SecureRandom secureRandom) {
        super(secureRandom);
    }

    public JSSEServerContext(JSSEServerContext jSSEServerContext) {
        super(jSSEServerContext);
        this.keyManager_ = jSSEServerContext.keyManager_;
    }

    protected void setJSSEKeyManager(X509KeyManager x509KeyManager) {
        this.keyManager_ = x509KeyManager;
        if (x509KeyManager != null) {
            a.println("Adding server keys/certificates");
            a("RSA");
            a("DSA");
            a(KeyTypeNames.DH_RSA);
            a(KeyTypeNames.DH_DSA);
            a(KeyTypeNames.DH_DSS);
            a("DH");
            a("ECDSA");
            a(SecurityProvider.ALG_KEYEX_ECDH);
            a(KeyTypeNames.EC);
            a(KeyTypeNames.EC_EC);
            a(KeyTypeNames.EC_RSA);
        }
    }

    @Override // iaik.security.ssl.SSLServerContext
    public KeyAndCert getServerCredentials(int i, ExtensionList extensionList, boolean z, SSLTransport sSLTransport) {
        KeyAndCert keyAndCert;
        PrivateKey privateKey;
        try {
            keyAndCert = super.getServerCredentials(i, extensionList, z, sSLTransport);
        } catch (Throwable unused) {
            keyAndCert = null;
        }
        if (keyAndCert == null) {
            Objects.requireNonNull(this.keyManager_, "Keymanager not set");
            String[] serverAliases = this.keyManager_.getServerAliases(c.a(i), null);
            Objects.requireNonNull(serverAliases, "No matching server certificate found");
            int i2 = 0;
            while (true) {
                if (i2 >= serverAliases.length) {
                    break;
                }
                X509Certificate[] certificateChain = this.keyManager_.getCertificateChain(serverAliases[i2]);
                if (certificateChain != null && (privateKey = this.keyManager_.getPrivateKey(serverAliases[i2])) != null) {
                    keyAndCert = new KeyAndCert(certificateChain, privateKey);
                    break;
                }
                i2++;
            }
        }
        Objects.requireNonNull(keyAndCert, "No matching server certificate found");
        return keyAndCert;
    }

    private void a(String str) {
        String[] serverAliases;
        X509KeyManager x509KeyManager = this.keyManager_;
        if (x509KeyManager == null || (serverAliases = x509KeyManager.getServerAliases(str, null)) == null) {
            return;
        }
        for (String str2 : serverAliases) {
            X509Certificate[] certificateChain = this.keyManager_.getCertificateChain(str2);
            if (certificateChain != null) {
                PrivateKey privateKey = this.keyManager_.getPrivateKey(str2);
                if (privateKey != null) {
                    try {
                        addServerCredentials(certificateChain, privateKey);
                        Debug debug = a;
                        StringBuffer stringBuffer = new StringBuffer("Added ");
                        stringBuffer.append(str2);
                        debug.println(stringBuffer.toString());
                    } catch (IllegalArgumentException e) {
                        Debug debug2 = a;
                        StringBuffer stringBuffer2 = new StringBuffer("Skipped ");
                        stringBuffer2.append(str2);
                        stringBuffer2.append(": ");
                        stringBuffer2.append(e.getMessage());
                        debug2.println(stringBuffer2.toString());
                    }
                } else {
                    Debug debug3 = a;
                    StringBuffer stringBuffer3 = new StringBuffer("Skipped ");
                    stringBuffer3.append(str2);
                    stringBuffer3.append(": No private key!");
                    debug3.println(stringBuffer3.toString());
                }
            }
        }
    }

    @Override // iaik.security.ssl.SSLServerContext, iaik.security.ssl.SSLClientContext, iaik.security.ssl.SSLContext
    public Object clone() {
        JSSEServerContext jSSEServerContext = (JSSEServerContext) super.clone();
        jSSEServerContext.keyManager_ = this.keyManager_;
        return jSSEServerContext;
    }
}
