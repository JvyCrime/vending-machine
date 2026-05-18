package iaik.security.ssl;

import com.felhr.usbserial.UsbSerialDebugger;
import iaik.security.ssl.SupportedEllipticCurves;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

/* JADX INFO: loaded from: classes.dex */
class at extends as {
    private Key h;
    private String i;
    private byte[] j;
    private String k;

    at(CipherSuite cipherSuite, SSLTransport sSLTransport) throws SSLException {
        super(cipherSuite, sSLTransport);
        this.h = null;
        this.j = null;
        this.i = cipherSuite.getKeyExchangeAlgorithm();
        a(sSLTransport);
    }

    @Override // iaik.security.ssl.as
    void a(SSLServerContext sSLServerContext, KeyAndCert keyAndCert) throws SSLException {
        String str = this.i;
        if (str.startsWith("DHE_PSK")) {
            DHPrivateKey dHPrivateKeyA = an.a(sSLServerContext, this.f, 4);
            this.h = dHPrivateKeyA;
            DHParameterSpec params = dHPrivateKeyA.getParams();
            BigInteger p = params.getP();
            BigInteger g = params.getG();
            this.c = an.a(Utils.a(p), Utils.a(g), Utils.a(g.modPow(((DHPrivateKey) this.h).getX(), p)));
            return;
        }
        if (str.startsWith("ECDHE_PSK")) {
            ExtensionList peerExtensions = this.f.getPeerExtensions();
            ExtensionList activeExtensions = this.f.getActiveExtensions();
            SupportedPointFormats supportedPointFormats = null;
            SupportedEllipticCurves supportedEllipticCurves = activeExtensions != null ? (SupportedEllipticCurves) activeExtensions.getExtension(SupportedEllipticCurves.TYPE) : null;
            if (peerExtensions != null) {
                if (supportedEllipticCurves == null) {
                    supportedEllipticCurves = (SupportedEllipticCurves) peerExtensions.getExtension(SupportedEllipticCurves.TYPE);
                }
                supportedPointFormats = (SupportedPointFormats) peerExtensions.getExtension(SupportedPointFormats.TYPE);
            }
            SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
            try {
                KeyPair keyPairGenerateECKeyPair = securityProvider.generateECKeyPair(supportedEllipticCurves, supportedPointFormats);
                this.h = keyPairGenerateECKeyPair.getPrivate();
                PublicKey publicKey = keyPairGenerateECKeyPair.getPublic();
                try {
                    byte[] bArrEncodeECPublicKey = securityProvider.encodeECPublicKey(publicKey, supportedPointFormats);
                    SupportedEllipticCurves.NamedCurve curve = securityProvider.getCurve(keyPairGenerateECKeyPair.getPublic());
                    if (curve == null) {
                        throw new SSLException("Cannot determine curve for public EC key", 2, 80, false);
                    }
                    this.c = ao.a(3, curve.getID(), bArrEncodeECPublicKey);
                    if (this.f.b != null) {
                        this.k = a((Key) publicKey);
                    }
                } catch (Exception e) {
                    StringBuffer stringBuffer = new StringBuffer("Error encoding EC public: ");
                    stringBuffer.append(e.toString());
                    throw new SSLException(stringBuffer.toString(), 2, 80, false);
                }
            } catch (Exception e2) {
                StringBuffer stringBuffer2 = new StringBuffer("Error generating ECDH key pair: ");
                stringBuffer2.append(e2.toString());
                throw new SSLException(stringBuffer2.toString(), 2, 80, false);
            }
        }
    }

    byte[] c() {
        return this.j;
    }

    private void a(SSLTransport sSLTransport) throws SSLException {
        SSLContext context = sSLTransport.getContext();
        if (context instanceof SSLServerContext) {
            SSLServerContext sSLServerContext = (SSLServerContext) context;
            if (sSLServerContext.getSendIdentityHint()) {
                PSKCredential pSKCredential = sSLServerContext.getPSKCredential(null, sSLTransport);
                if (pSKCredential == null) {
                    throw new SSLException("No PSK identity hint available but should be sent!");
                }
                this.j = pSKCredential.getIdentityHint();
                if (sSLTransport.b != null) {
                    StringBuffer stringBuffer = new StringBuffer("PSK identity hint is: ");
                    stringBuffer.append(pSKCredential.getIdentityHintString());
                    sSLTransport.a(stringBuffer.toString());
                }
            }
        }
    }

    @Override // iaik.security.ssl.as
    Key b() {
        return this.h;
    }

    @Override // iaik.security.ssl.as
    String a() {
        return a(this.h);
    }

    private String a(Key key) {
        if (this.k == null && key != null) {
            if (key instanceof PublicKey) {
                this.k = Utils.a((PublicKey) key, true);
            } else {
                StringBuffer stringBuffer = new StringBuffer(String.valueOf(Utils.a((PrivateKey) key)));
                stringBuffer.append(" ");
                stringBuffer.append(key.getAlgorithm());
                this.k = stringBuffer.toString();
            }
        }
        return this.k;
    }

    @Override // iaik.security.ssl.as
    boolean a(PublicKey publicKey) {
        if (publicKey == null || this.h != null) {
            return true;
        }
        this.h = publicKey;
        return true;
    }

    @Override // iaik.security.ssl.as
    void a(ab abVar) throws IOException {
        SupportedEllipticCurves supportedEllipticCurves;
        int iH = abVar.h();
        byte[] bArrG = abVar.g();
        this.j = bArrG;
        int length = iH - 2;
        SupportedPointFormats supportedPointFormats = null;
        if (bArrG == null || bArrG.length == 0) {
            this.j = null;
        }
        byte[] bArr = this.j;
        if (bArr != null) {
            length -= bArr.length;
        }
        String str = this.i;
        if (str.startsWith("DHE_PSK")) {
            byte[] bArrG2 = abVar.g();
            byte[] bArrG3 = abVar.g();
            byte[] bArrG4 = abVar.g();
            BigInteger bigInteger = new BigInteger(1, bArrG2);
            BigInteger bigInteger2 = new BigInteger(1, bArrG3);
            BigInteger bigInteger3 = new BigInteger(1, bArrG4);
            try {
                SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
                DHPublicKey dHPublicKey = securityProvider.getDHPublicKey(bigInteger3, bigInteger, bigInteger2);
                securityProvider.checkKeyLength(dHPublicKey);
                this.h = dHPublicKey;
                this.c = an.a(bArrG2, bArrG3, bArrG4);
                return;
            } catch (Exception e) {
                StringBuffer stringBuffer = new StringBuffer("SecurityProvider error generating DH public key: ");
                stringBuffer.append(e.toString());
                throw new SSLException(stringBuffer.toString());
            }
        }
        if (str.startsWith("ECDHE_PSK")) {
            int iK = abVar.k();
            if (iK != 3) {
                StringBuffer stringBuffer2 = new StringBuffer("Unsupported EC curve type: ");
                stringBuffer2.append(iK);
                stringBuffer2.append("! Only named_curve (3) is supported!");
                throw new SSLException(stringBuffer2.toString(), 2, 80, false);
            }
            int i = length - 1;
            int iF = abVar.f();
            if (iF == SupportedEllipticCurves.NC_ARBITRARY_EXPLICIT_PRIME.getID() || iF == SupportedEllipticCurves.NC_ARBITRARY_EXPLICIT_CHAR2.getID()) {
                throw new SSLException("Arbitrary curve types are not allowed in ECDH ServerKeyExchange!", 2, 47, false);
            }
            int i2 = i - 2;
            SupportedEllipticCurves.NamedCurve namedCurve = (SupportedEllipticCurves.NamedCurve) SupportedEllipticCurves.b.get(new Integer(iF));
            if (namedCurve == null) {
                try {
                    namedCurve = new SupportedEllipticCurves.NamedCurve(iF);
                } catch (Exception e2) {
                    StringBuffer stringBuffer3 = new StringBuffer("Error decoding named curve: ");
                    stringBuffer3.append(e2.getMessage());
                    throw new SSLException(stringBuffer3.toString(), 2, 50, false);
                }
            }
            byte[] bArrL = abVar.l();
            if (i2 - (bArrL.length + 1) != 0) {
                throw new SSLException("Invalid length of ECDH ServerKeyExchange message!", 2, 50, false);
            }
            ExtensionList extensionListI = this.f.getContext().i();
            if (extensionListI != null) {
                SupportedEllipticCurves supportedEllipticCurves2 = (SupportedEllipticCurves) extensionListI.getExtension(SupportedEllipticCurves.TYPE);
                supportedPointFormats = (SupportedPointFormats) extensionListI.getExtension(SupportedPointFormats.TYPE);
                supportedEllipticCurves = supportedEllipticCurves2;
            } else {
                supportedEllipticCurves = null;
            }
            try {
                SecurityProvider securityProvider2 = SecurityProvider.getSecurityProvider();
                PublicKey publicKeyDecodeECPublicKey = securityProvider2.decodeECPublicKey(bArrL, namedCurve, supportedPointFormats, supportedEllipticCurves);
                securityProvider2.checkKeyLength(publicKeyDecodeECPublicKey);
                this.h = publicKeyDecodeECPublicKey;
                this.c = ao.a(iK, iF, bArrL);
            } catch (Exception e3) {
                StringBuffer stringBuffer4 = new StringBuffer("Error decoding ECDH public key: ");
                stringBuffer4.append(e3.toString());
                throw new SSLException(stringBuffer4.toString(), 2, 50, false);
            }
        }
    }

    @Override // iaik.security.ssl.as, iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        byte[] bArr = this.j;
        int length = bArr == null ? 0 : bArr.length;
        agVar.g(12);
        if (this.c != null) {
            agVar.e(length + 2 + this.c.length);
            agVar.a(this.j);
            agVar.write(this.c);
        } else {
            agVar.e((length != 0 ? 2 : 0) + length);
            agVar.a(this.j);
        }
    }

    public String toString() {
        String str;
        StringBuffer stringBuffer = new StringBuffer();
        if (this.j != null) {
            stringBuffer.append("PSK Identity Hint: ");
            String str2 = null;
            try {
                try {
                    str = new String(this.j, "UTF8");
                } catch (UnsupportedEncodingException unused) {
                    str = new String(this.j, UsbSerialDebugger.ENCODING);
                }
                str2 = str;
            } catch (Throwable unused2) {
            }
            if (str2 != null) {
                stringBuffer.append(str2);
            }
        }
        if (this.h != null) {
            stringBuffer.append("\nPublic key:\n");
            stringBuffer.append(this.h);
        }
        return stringBuffer.toString();
    }
}
