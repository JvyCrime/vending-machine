package iaik.security.ssl;

import iaik.security.jsse.net.KeyTypeNames;
import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateCrtKey;

/* JADX INFO: loaded from: classes.dex */
abstract class as extends x {
    byte[] a;
    byte[] b;
    byte[] c;
    byte[] d;
    int e;
    SSLTransport f;
    SignatureAndHashAlgorithm g;

    abstract String a();

    abstract void a(SSLServerContext sSLServerContext, KeyAndCert keyAndCert) throws SSLException;

    abstract void a(ab abVar) throws IOException;

    abstract Key b();

    as(CipherSuite cipherSuite, SSLTransport sSLTransport) {
        SignatureAlgorithms signatureAlgorithms;
        super(12);
        SignatureAndHashAlgorithmList supportedAlgorithms = null;
        this.c = null;
        this.d = null;
        this.f = sSLTransport;
        String keyExchangeAlgorithm = cipherSuite.getKeyExchangeAlgorithm();
        ExtensionList activeExtensions = sSLTransport.getActiveExtensions();
        if (activeExtensions != null && (signatureAlgorithms = (SignatureAlgorithms) activeExtensions.getExtension(SignatureAlgorithms.TYPE)) != null) {
            supportedAlgorithms = signatureAlgorithms.getSupportedAlgorithms();
        }
        boolean z = sSLTransport.b().b() == 771;
        if (keyExchangeAlgorithm.startsWith(KeyTypeNames.DH_RSA) || keyExchangeAlgorithm.startsWith(KeyTypeNames.DH_DSS) || keyExchangeAlgorithm.startsWith("ECDH_RSA") || keyExchangeAlgorithm.startsWith("ECDH_ECDSA")) {
            StringBuffer stringBuffer = new StringBuffer("Invalid key exchange algorithm for ServerKeyExchange: ");
            stringBuffer.append(keyExchangeAlgorithm);
            throw new RuntimeException(stringBuffer.toString());
        }
        if (keyExchangeAlgorithm.endsWith("RSA") || keyExchangeAlgorithm.endsWith("RSA_EXPORT") || keyExchangeAlgorithm.endsWith("RSA_EXPORT1024")) {
            this.e = 36;
            if (z) {
                if (supportedAlgorithms == null) {
                    this.g = SignatureAndHashAlgorithm.SHA1withRSA;
                    return;
                } else {
                    this.g = supportedAlgorithms.a(1);
                    return;
                }
            }
            return;
        }
        if (keyExchangeAlgorithm.endsWith("DSS") || keyExchangeAlgorithm.endsWith("DSS_EXPORT") || keyExchangeAlgorithm.endsWith("DSS_EXPORT1024")) {
            this.e = 20;
            if (z) {
                if (supportedAlgorithms == null) {
                    this.g = SignatureAndHashAlgorithm.SHA1withDSA;
                    return;
                } else {
                    this.g = supportedAlgorithms.a(2);
                    return;
                }
            }
            return;
        }
        if (keyExchangeAlgorithm.endsWith("ECDSA")) {
            this.e = 32;
            if (z) {
                if (supportedAlgorithms == null) {
                    this.g = SignatureAndHashAlgorithm.SHA1withECDSA;
                    return;
                } else {
                    this.g = supportedAlgorithms.a(3);
                    return;
                }
            }
            return;
        }
        if (keyExchangeAlgorithm.startsWith("DH_anon") || keyExchangeAlgorithm.startsWith("ECDH_anon")) {
            this.e = 0;
        } else if (keyExchangeAlgorithm.endsWith("PSK")) {
            this.e = 0;
        } else {
            StringBuffer stringBuffer2 = new StringBuffer("Invalid key exchange algorithm: ");
            stringBuffer2.append(keyExchangeAlgorithm);
            throw new RuntimeException(stringBuffer2.toString());
        }
    }

    void a(SignatureAndHashAlgorithm signatureAndHashAlgorithm) throws SSLException {
        boolean zB;
        SignatureAlgorithms signatureAlgorithms;
        ExtensionList activeExtensions = this.f.getActiveExtensions();
        SignatureAndHashAlgorithmList supportedAlgorithms = (activeExtensions == null || (signatureAlgorithms = (SignatureAlgorithms) activeExtensions.getExtension(SignatureAlgorithms.TYPE)) == null) ? null : signatureAlgorithms.getSupportedAlgorithms();
        if (supportedAlgorithms != null) {
            zB = supportedAlgorithms.contains(signatureAndHashAlgorithm);
        } else {
            zB = signatureAndHashAlgorithm.b();
        }
        if (zB) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer("Unsupported SignatureAndHashAlgorithm: ");
        stringBuffer.append(signatureAndHashAlgorithm.toString(true));
        throw new SSLException(stringBuffer.toString(), 2, 40, false);
    }

    boolean a(PublicKey publicKey) {
        Signature signature;
        if (this.e == 0) {
            return true;
        }
        try {
            if (this.f.b().b() == 771) {
                signature = SecurityProvider.getSecurityProvider().getSignature(this.g.getName(), 2, publicKey, null);
            } else {
                int i = this.e;
                if (i == 36) {
                    al alVar = new al();
                    alVar.initVerify(publicKey);
                    signature = alVar;
                } else if (i == 20) {
                    signature = SecurityProvider.getSecurityProvider().getSignature(SecurityProvider.ALG_SIGNATURE_SHADSA, 2, publicKey, null);
                } else if (i == 32) {
                    signature = SecurityProvider.getSecurityProvider().getSignature("SHA1withECDSA", 2, publicKey, null);
                } else {
                    throw new RuntimeException("Internal Error in ServerKeyExchange!");
                }
            }
            signature.update(this.a);
            signature.update(this.b);
            signature.update(this.c);
            return signature.verify(this.d);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("Error verifying signature: ");
            stringBuffer.append(e.toString());
            throw new RuntimeException(stringBuffer.toString());
        }
    }

    byte[] b(SSLServerContext sSLServerContext, KeyAndCert keyAndCert) {
        Signature signature;
        PrivateKey privateKey = keyAndCert != null ? keyAndCert.getPrivateKey() : null;
        try {
            if (this.e == 0) {
                return new byte[0];
            }
            SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
            if (this.f.b().b() == 771) {
                if (privateKey == null) {
                    privateKey = sSLServerContext.a(64, this.f.a(false), this.f);
                }
                if ((privateKey instanceof RSAPrivateCrtKey) && securityProvider.checkCreatedRSAServerKeyExchangeSignature()) {
                    return a(sSLServerContext, privateKey, keyAndCert != null ? keyAndCert.getCertificateChain()[0].getPublicKey() : null, true);
                }
                signature = securityProvider.getSignature(this.g.getName(), 1, privateKey, sSLServerContext.getRandomGenerator());
            } else {
                int i = this.e;
                if (i == 36) {
                    if (privateKey == null) {
                        privateKey = sSLServerContext.a(1, this.f.a(false), this.f);
                    }
                    if ((privateKey instanceof RSAPrivateCrtKey) && securityProvider.checkCreatedRSAServerKeyExchangeSignature()) {
                        return a(sSLServerContext, privateKey, keyAndCert != null ? keyAndCert.getCertificateChain()[0].getPublicKey() : null, false);
                    }
                    signature = new al();
                    signature.initSign(privateKey);
                } else if (i == 20) {
                    if (privateKey == null) {
                        privateKey = sSLServerContext.a(2, this.f.a(false), this.f);
                    }
                    signature = securityProvider.getSignature(SecurityProvider.ALG_SIGNATURE_SHADSA, 1, privateKey, sSLServerContext.getRandomGenerator());
                } else if (i == 32) {
                    if (privateKey == null) {
                        privateKey = sSLServerContext.a(64, this.f.a(false), this.f);
                    }
                    signature = securityProvider.getSignature("SHA1withECDSA", 1, privateKey, sSLServerContext.getRandomGenerator());
                } else {
                    throw new RuntimeException("Internal Error in signing key setup of ServerKeyExchange!");
                }
            }
            signature.update(this.a);
            signature.update(this.b);
            signature.update(this.c);
            return signature.sign();
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("Error creating signature: ");
            stringBuffer.append(e.toString());
            throw new RuntimeException(stringBuffer.toString());
        }
    }

    byte[] a(SSLServerContext sSLServerContext, PrivateKey privateKey, PublicKey publicKey, boolean z) {
        byte[] bArrA;
        try {
            SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
            if (z) {
                String strA = this.g.a();
                MessageDigest messageDigest = securityProvider.getMessageDigest(strA);
                messageDigest.update(this.a);
                messageDigest.update(this.b);
                bArrA = PKCS1v15DigestInfo.a(strA, messageDigest.digest(this.c), true);
                if (bArrA == null) {
                    StringBuffer stringBuffer = new StringBuffer("Unsupported signature algorithm in Certificate verify message: ");
                    stringBuffer.append(this.g.toString(true));
                    throw new SSLException(stringBuffer.toString());
                }
            } else {
                MessageDigest messageDigest2 = securityProvider.getMessageDigest(SecurityProvider.ALG_DIGEST_MD5);
                MessageDigest messageDigest3 = securityProvider.getMessageDigest("SHA");
                messageDigest2.update(this.a);
                messageDigest3.update(this.a);
                messageDigest2.update(this.b);
                messageDigest3.update(this.b);
                bArrA = Utils.a(messageDigest2.digest(this.c), messageDigest3.digest(this.c));
            }
            byte[] bArrCalculateRawSignature = securityProvider.calculateRawSignature(SecurityProvider.ALG_CIPHER_RSA_SIGN, bArrA, privateKey, sSLServerContext.getRandomGenerator());
            if (publicKey == null) {
                try {
                    RSAPrivateCrtKey rSAPrivateCrtKey = (RSAPrivateCrtKey) privateKey;
                    publicKey = securityProvider.getRSAPublicKey(rSAPrivateCrtKey.getModulus(), rSAPrivateCrtKey.getPublicExponent());
                } catch (Throwable unused) {
                }
            }
            if (publicKey != null && !securityProvider.verifyRawSignature(SecurityProvider.ALG_CIPHER_RSA_VERIFY, bArrA, bArrCalculateRawSignature, publicKey)) {
                throw new SSLException("RSA Signature creation failed!");
            }
            return bArrCalculateRawSignature;
        } catch (Exception e) {
            StringBuffer stringBuffer2 = new StringBuffer("Error creating signature: ");
            stringBuffer2.append(e.toString());
            throw new RuntimeException(stringBuffer2.toString());
        }
    }

    @Override // iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        agVar.g(12);
        if (this.e != 0) {
            int length = this.c.length + 2 + this.d.length;
            if (this.f.b().b() == 771) {
                length += 2;
            }
            agVar.e(length);
            agVar.write(this.c);
            if (this.f.b().b() == 771) {
                this.g.a(agVar);
            }
            agVar.a(this.d);
            return;
        }
        agVar.e(this.c.length);
        agVar.write(this.c);
    }
}
