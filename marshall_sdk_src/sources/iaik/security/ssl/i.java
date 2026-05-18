package iaik.security.ssl;

import iaik.security.jsse.net.KeyTypeNames;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
class i extends x {
    SignatureAndHashAlgorithm a;
    private byte[] b;

    i(int i, PrivateKey privateKey, y yVar, SignatureAndHashAlgorithmList signatureAndHashAlgorithmList, SecureRandom secureRandom) throws SSLException {
        super(15);
        String strA = null;
        this.b = null;
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        if (i == 1) {
            if (signatureAndHashAlgorithmList != null) {
                SignatureAndHashAlgorithm signatureAndHashAlgorithmA = signatureAndHashAlgorithmList.a(1);
                this.a = signatureAndHashAlgorithmA;
                strA = signatureAndHashAlgorithmA.a();
            }
            try {
                byte[] bArrA = yVar.a(3, strA);
                if (yVar.l >= 771 && (bArrA = PKCS1v15DigestInfo.a(strA, bArrA, true)) == null) {
                    StringBuffer stringBuffer = new StringBuffer("Unsupported signature algorithm in Certificate verify message: ");
                    stringBuffer.append(this.a.toString(true));
                    throw new SSLException(stringBuffer.toString(), 2, 40, false);
                }
                this.b = securityProvider.calculateRawSignature(SecurityProvider.ALG_CIPHER_RSA_SIGN, bArrA, privateKey, secureRandom);
                return;
            } catch (SSLException e) {
                throw e;
            } catch (Exception e2) {
                StringBuffer stringBuffer2 = new StringBuffer("Unable to create RSA certificate verify: ");
                stringBuffer2.append(e2);
                throw new SSLException(stringBuffer2.toString());
            }
        }
        if (i == 2) {
            if (signatureAndHashAlgorithmList != null) {
                SignatureAndHashAlgorithm signatureAndHashAlgorithmA2 = signatureAndHashAlgorithmList.a(2);
                this.a = signatureAndHashAlgorithmA2;
                strA = signatureAndHashAlgorithmA2.a();
            }
            try {
                byte[] bArrA2 = yVar.a(4, strA);
                Signature signature = securityProvider.getSignature(SecurityProvider.ALG_SIGNATURE_RAWDSA, 1, privateKey, secureRandom);
                signature.update(bArrA2);
                this.b = signature.sign();
                return;
            } catch (Exception e3) {
                StringBuffer stringBuffer3 = new StringBuffer("Unable to create DSA certificate verify: ");
                stringBuffer3.append(e3);
                throw new SSLException(stringBuffer3.toString());
            }
        }
        if (i == 64 || i == 258) {
            if (signatureAndHashAlgorithmList != null) {
                SignatureAndHashAlgorithm signatureAndHashAlgorithmA3 = signatureAndHashAlgorithmList.a(3);
                this.a = signatureAndHashAlgorithmA3;
                strA = signatureAndHashAlgorithmA3.a();
            }
            try {
                byte[] bArrA3 = yVar.a(5, strA);
                Signature signature2 = securityProvider.getSignature(SecurityProvider.ALG_SIGNATURE_RAWECDSA, 1, privateKey, secureRandom);
                signature2.update(bArrA3);
                this.b = signature2.sign();
                return;
            } catch (Exception e4) {
                StringBuffer stringBuffer4 = new StringBuffer("Unable to create ECDSA certificate verify: ");
                stringBuffer4.append(e4);
                throw new SSLException(stringBuffer4.toString());
            }
        }
        StringBuffer stringBuffer5 = new StringBuffer("Invalid certificate verify cert type: ");
        stringBuffer5.append(i);
        throw new SSLException(stringBuffer5.toString());
    }

    i(ab abVar, int i) throws IOException {
        super(15);
        this.b = null;
        a(abVar, i);
    }

    boolean a(PublicKey publicKey, y yVar) throws SSLException {
        String strA;
        ExtensionList activeExtensions;
        SignatureAlgorithms signatureAlgorithms;
        if (this.a != null) {
            SignatureAndHashAlgorithmList supportedAlgorithms = (yVar.l < 771 || (activeExtensions = yVar.n.getActiveExtensions()) == null || (signatureAlgorithms = (SignatureAlgorithms) activeExtensions.getExtension(SignatureAlgorithms.TYPE)) == null) ? null : signatureAlgorithms.getSupportedAlgorithms();
            if (supportedAlgorithms != null && !supportedAlgorithms.contains(this.a)) {
                StringBuffer stringBuffer = new StringBuffer("Unsupported signature algorithm in Certificate verify message: ");
                stringBuffer.append(this.a.toString(true));
                throw new SSLException(stringBuffer.toString(), 2, 40, false);
            }
            strA = this.a.a();
        } else {
            strA = null;
        }
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        if (publicKey instanceof RSAPublicKey) {
            try {
                byte[] bArrA = yVar.a(3, strA);
                if (yVar.l >= 771) {
                    byte[] bArrA2 = PKCS1v15DigestInfo.a(strA, bArrA, true);
                    if (bArrA2 == null) {
                        StringBuffer stringBuffer2 = new StringBuffer("Unsupported signature algorithm in Certificate verify message: ");
                        stringBuffer2.append(this.a.toString(true));
                        throw new SSLException(stringBuffer2.toString(), 2, 40, false);
                    }
                    boolean zVerifyRawSignature = securityProvider.verifyRawSignature(SecurityProvider.ALG_CIPHER_RSA_VERIFY, bArrA2, this.b, publicKey);
                    return !zVerifyRawSignature ? securityProvider.verifyRawSignature(SecurityProvider.ALG_CIPHER_RSA_VERIFY, PKCS1v15DigestInfo.a(strA, bArrA, false), this.b, publicKey) : zVerifyRawSignature;
                }
                return securityProvider.verifyRawSignature(SecurityProvider.ALG_CIPHER_RSA_VERIFY, bArrA, this.b, publicKey);
            } catch (Exception unused) {
                return false;
            }
        }
        if (publicKey instanceof DSAPublicKey) {
            try {
                byte[] bArrA3 = yVar.a(4, strA);
                Signature signature = securityProvider.getSignature(SecurityProvider.ALG_SIGNATURE_RAWDSA, 2, publicKey, null);
                signature.update(bArrA3);
                return signature.verify(this.b);
            } catch (Exception unused2) {
                return false;
            }
        }
        if (publicKey.getAlgorithm().toUpperCase(Locale.US).startsWith(KeyTypeNames.EC)) {
            try {
                byte[] bArrA4 = yVar.a(5, strA);
                Signature signature2 = securityProvider.getSignature(SecurityProvider.ALG_SIGNATURE_RAWECDSA, 2, publicKey, null);
                signature2.update(bArrA4);
                return signature2.verify(this.b);
            } catch (Exception unused3) {
            }
        }
        return false;
    }

    @Override // iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        agVar.g(15);
        int length = this.b.length + 2;
        if (this.a != null) {
            length += 2;
        }
        agVar.e(length);
        SignatureAndHashAlgorithm signatureAndHashAlgorithm = this.a;
        if (signatureAndHashAlgorithm != null) {
            signatureAndHashAlgorithm.a(agVar);
        }
        agVar.a(this.b);
    }

    void a(ab abVar, int i) throws IOException {
        int iH = abVar.h();
        if (i >= 771) {
            this.a = SignatureAndHashAlgorithm.a(abVar);
            iH -= 2;
        }
        byte[] bArrG = abVar.g();
        this.b = bArrG;
        if (iH != bArrG.length + 2) {
            throw new SSLException("Extension list size does not match to length field!", 2, 50, false);
        }
    }
}
