package iaik.security.jsse.net;

import iaik.security.jsse.utils.Debug;
import iaik.security.ssl.KeyAndCert;
import iaik.security.ssl.SSLClientContext;
import iaik.security.ssl.SecurityProvider;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.net.ssl.X509KeyManager;

/* JADX INFO: loaded from: classes.dex */
class c extends SSLClientContext implements KeyTypeNames {
    private static Debug b = Debug.getInstance();
    private static Boolean c = null;
    protected X509KeyManager a;

    static boolean a() {
        if (c == null) {
            c = new Boolean("iaik.security.ssl.IaikEccProvider".equals(SecurityProvider.getSecurityProvider().getClass().getName()));
        }
        return c.booleanValue();
    }

    protected static String a(int i) {
        if (i == 1) {
            return "RSA";
        }
        if (i == 2) {
            return "DSA";
        }
        if (i == 3) {
            return KeyTypeNames.DH_RSA;
        }
        if (i == 4) {
            return KeyTypeNames.DH_DSA;
        }
        if (i == 257) {
            return "RSA";
        }
        switch (i) {
            case 64:
            case 66:
                return a() ? "ECDSA" : KeyTypeNames.EC_EC;
            case 65:
                return a() ? "ECDSA" : KeyTypeNames.EC_RSA;
            default:
                return null;
        }
    }

    public c() {
    }

    public c(SecureRandom secureRandom) {
        super(secureRandom);
    }

    protected void a(X509KeyManager x509KeyManager) {
        this.a = x509KeyManager;
        if (x509KeyManager != null) {
            b.println("Adding client keys/certificates");
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

    @Override // iaik.security.ssl.SSLClientContext
    protected KeyAndCert[] getClientCredentials(Principal[] principalArr, byte[] bArr, PublicKey publicKey) {
        KeyAndCert[] clientCredentials;
        PrivateKey privateKey;
        try {
            clientCredentials = super.getClientCredentials(principalArr, bArr, publicKey);
        } catch (Throwable unused) {
            clientCredentials = null;
        }
        if (clientCredentials == null && this.a != null) {
            for (int i = 0; i < bArr.length; i++) {
                String[] clientAliases = this.a.getClientAliases(a((int) bArr[i]), principalArr);
                if (clientAliases != null) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= clientAliases.length) {
                            break;
                        }
                        X509Certificate[] certificateChain = this.a.getCertificateChain(clientAliases[i2]);
                        if (certificateChain != null && (privateKey = this.a.getPrivateKey(clientAliases[i2])) != null && a(publicKey, certificateChain, bArr[i]) && b(certificateChain[0], bArr[i]) && a(certificateChain[0], bArr[i])) {
                            clientCredentials = new KeyAndCert[]{new KeyAndCert(certificateChain, privateKey)};
                            break;
                        }
                        i2++;
                    }
                    if (clientCredentials != null) {
                        break;
                    }
                }
            }
        }
        return clientCredentials == null ? new KeyAndCert[0] : clientCredentials;
    }

    private static boolean a(PublicKey publicKey, X509Certificate[] x509CertificateArr, int i) {
        boolean zEquals;
        if (publicKey != null && (publicKey instanceof DHPublicKey) && (i == 3 || i == 4)) {
            DHParameterSpec params = ((DHPublicKey) publicKey).getParams();
            DHParameterSpec params2 = ((DHPublicKey) x509CertificateArr[0].getPublicKey()).getParams();
            zEquals = params.getG().equals(params2.getG());
            if (!params.getP().equals(params2.getP())) {
                zEquals = false;
            }
        } else {
            zEquals = true;
        }
        if ((i == 65 || i == 66) && !SecurityProvider.getSecurityProvider().checkIfOnSameCurve(publicKey, x509CertificateArr[0].getPublicKey())) {
            return false;
        }
        return zEquals;
    }

    private static boolean b(X509Certificate x509Certificate, int i) {
        boolean[] keyUsage = x509Certificate.getKeyUsage();
        if (keyUsage == null) {
            return true;
        }
        boolean z = keyUsage[0] || keyUsage[1];
        boolean z2 = (keyUsage[2] || keyUsage[3] || keyUsage[4]) || !(i == 66 || i == 65 || i == 3 || i == 4);
        if (z2 && !z && (i == 64 || i == 2 || i == 1)) {
            return false;
        }
        return z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static boolean a(java.security.cert.X509Certificate r4, int r5) {
        /*
            java.lang.String r4 = r4.getSigAlgName()
            java.util.Locale r0 = java.util.Locale.US
            java.lang.String r4 = r4.toUpperCase(r0)
            r0 = -1
            r1 = 0
            r2 = 1
            if (r5 == r2) goto L34
            r3 = 2
            if (r5 == r3) goto L2b
            r3 = 3
            if (r5 == r3) goto L34
            r3 = 4
            if (r5 == r3) goto L2b
            r3 = 257(0x101, float:3.6E-43)
            if (r5 == r3) goto L34
            switch(r5) {
                case 64: goto L20;
                case 65: goto L34;
                case 66: goto L20;
                default: goto L1f;
            }
        L1f:
            goto L3c
        L20:
            java.lang.String r5 = "ECDSA"
            int r4 = r4.indexOf(r5)
            if (r4 != r0) goto L29
            goto L3c
        L29:
            r1 = 1
            goto L3c
        L2b:
            java.lang.String r5 = "DSA"
            int r4 = r4.indexOf(r5)
            if (r4 != r0) goto L29
            goto L3c
        L34:
            java.lang.String r5 = "RSA"
            int r4 = r4.indexOf(r5)
            if (r4 != r0) goto L29
        L3c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.jsse.net.c.a(java.security.cert.X509Certificate, int):boolean");
    }

    private void a(String str) {
        String[] clientAliases;
        X509KeyManager x509KeyManager = this.a;
        if (x509KeyManager == null || (clientAliases = x509KeyManager.getClientAliases(str, null)) == null) {
            return;
        }
        for (String str2 : clientAliases) {
            X509Certificate[] certificateChain = this.a.getCertificateChain(str2);
            if (certificateChain != null) {
                PrivateKey privateKey = this.a.getPrivateKey(str2);
                if (privateKey != null) {
                    try {
                        addClientCredentials(certificateChain, privateKey);
                        Debug debug = b;
                        StringBuffer stringBuffer = new StringBuffer("Added ");
                        stringBuffer.append(str2);
                        debug.println(stringBuffer.toString());
                    } catch (IllegalArgumentException e) {
                        Debug debug2 = b;
                        StringBuffer stringBuffer2 = new StringBuffer("Skipped ");
                        stringBuffer2.append(str2);
                        stringBuffer2.append(": ");
                        stringBuffer2.append(e.getMessage());
                        debug2.println(stringBuffer2.toString());
                    }
                } else {
                    Debug debug3 = b;
                    StringBuffer stringBuffer3 = new StringBuffer("Skipped ");
                    stringBuffer3.append(str2);
                    stringBuffer3.append(": No private key!");
                    debug3.println(stringBuffer3.toString());
                }
            }
        }
    }

    @Override // iaik.security.ssl.SSLClientContext, iaik.security.ssl.SSLContext
    public Object clone() {
        c cVar = (c) super.clone();
        cVar.a = this.a;
        return cVar;
    }
}
