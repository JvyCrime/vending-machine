package iaik.security.ssl;

import iaik.security.ssl.SupportedEllipticCurves;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.PublicKey;

/* JADX INFO: loaded from: classes.dex */
class ao extends as {
    private Key h;
    private PublicKey i;
    private String j;
    private int k;
    private SupportedEllipticCurves.NamedCurve l;
    private SupportedEllipticCurves m;
    private SupportedPointFormats n;
    private byte[] o;

    ao(CipherSuite cipherSuite, byte[] bArr, byte[] bArr2, SSLTransport sSLTransport) {
        super(cipherSuite, sSLTransport);
        this.h = null;
        this.i = null;
        ((as) this).a = bArr;
        this.b = bArr2;
        this.j = cipherSuite.getKeyExchangeAlgorithm();
        this.k = 3;
    }

    void a(SupportedPointFormats supportedPointFormats) {
        this.n = supportedPointFormats;
    }

    void a(SupportedEllipticCurves supportedEllipticCurves) {
        this.m = supportedEllipticCurves;
    }

    @Override // iaik.security.ssl.as
    void a(SSLServerContext sSLServerContext, KeyAndCert keyAndCert) throws SSLException {
        ExtensionList activeExtensions = this.f.getActiveExtensions();
        SupportedPointFormats supportedPointFormats = null;
        SupportedEllipticCurves supportedEllipticCurves = activeExtensions != null ? (SupportedEllipticCurves) activeExtensions.getExtension(SupportedEllipticCurves.TYPE) : null;
        ExtensionList peerExtensions = this.f.getPeerExtensions();
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
            this.i = publicKey;
            try {
                this.o = securityProvider.encodeECPublicKey(publicKey, supportedPointFormats);
                SupportedEllipticCurves.NamedCurve curve = securityProvider.getCurve(keyPairGenerateECKeyPair.getPublic());
                if (curve == null) {
                    throw new SSLException("Cannot determine curve for public EC key", 2, 80, false);
                }
                this.c = a(this.k, curve.getID(), this.o);
                this.d = b(sSLServerContext, keyAndCert);
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

    @Override // iaik.security.ssl.as
    Key b() {
        return this.h;
    }

    @Override // iaik.security.ssl.as
    String a() {
        PublicKey publicKey = this.i;
        if (publicKey != null) {
            try {
                StringBuffer stringBuffer = new StringBuffer(String.valueOf(Utils.a(publicKey, false)));
                stringBuffer.append(" ECDH");
                return stringBuffer.toString();
            } catch (Throwable unused) {
            }
        }
        return null;
    }

    static byte[] a(int i, int i2, byte[] bArr) {
        int length = bArr.length;
        byte[] bArr2 = new byte[length + 4];
        bArr2[0] = (byte) i;
        bArr2[1] = (byte) (i2 >> 8);
        bArr2[2] = (byte) i2;
        bArr2[3] = (byte) length;
        System.arraycopy(bArr, 0, bArr2, 4, length);
        return bArr2;
    }

    @Override // iaik.security.ssl.as
    void a(ab abVar) throws IOException {
        int iH = abVar.h();
        int iK = abVar.k();
        this.k = iK;
        if (iK != 3) {
            StringBuffer stringBuffer = new StringBuffer("Unsupported EC curve type: ");
            stringBuffer.append(this.k);
            stringBuffer.append("! Only named_curve (3) is supported!");
            throw new SSLException(stringBuffer.toString(), 2, 80, false);
        }
        int i = iH - 1;
        int iF = abVar.f();
        if (iF == SupportedEllipticCurves.NC_ARBITRARY_EXPLICIT_PRIME.getID() || iF == SupportedEllipticCurves.NC_ARBITRARY_EXPLICIT_CHAR2.getID()) {
            throw new SSLException("Arbitrary curve types are not allowed in ECDH ServerKeyExchange!", 2, 47, false);
        }
        int i2 = i - 2;
        SupportedEllipticCurves.NamedCurve namedCurve = (SupportedEllipticCurves.NamedCurve) SupportedEllipticCurves.b.get(new Integer(iF));
        this.l = namedCurve;
        if (namedCurve == null) {
            try {
                this.l = new SupportedEllipticCurves.NamedCurve(iF);
            } catch (Exception e) {
                StringBuffer stringBuffer2 = new StringBuffer("Error decoding named curve: ");
                stringBuffer2.append(e.getMessage());
                throw new SSLException(stringBuffer2.toString(), 2, 50, false);
            }
        }
        byte[] bArrL = abVar.l();
        this.o = bArrL;
        int length = i2 - (bArrL.length + 1);
        if (this.e == 0) {
            this.d = new byte[0];
        } else {
            if (abVar.b() == 771) {
                this.g = SignatureAndHashAlgorithm.a(abVar);
                a(this.g);
                length -= 2;
            }
            this.d = abVar.g();
            length -= this.d.length + 2;
        }
        if (length != 0) {
            throw new SSLException("Invalid length of ECDH ServerKeyExchange message!", 2, 50, false);
        }
        try {
            SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
            PublicKey publicKeyDecodeECPublicKey = securityProvider.decodeECPublicKey(this.o, this.l, this.n, this.m);
            securityProvider.checkKeyLength(publicKeyDecodeECPublicKey);
            this.h = publicKeyDecodeECPublicKey;
            this.i = publicKeyDecodeECPublicKey;
            this.c = a(this.k, iF, this.o);
        } catch (Exception e2) {
            StringBuffer stringBuffer3 = new StringBuffer("Error decoding ECDH public key: ");
            stringBuffer3.append(e2.toString());
            throw new SSLException(stringBuffer3.toString(), 2, 50, false);
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Server key:\n");
        stringBuffer.append(this.h);
        return stringBuffer.toString();
    }
}
