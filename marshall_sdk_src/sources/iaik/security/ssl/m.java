package iaik.security.ssl;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/* JADX INFO: loaded from: classes.dex */
class m extends p {
    private boolean a;
    PublicKey b;
    PrivateKey c;
    byte[] d;
    SupportedPointFormats e;

    m(int i) {
        super(i);
    }

    m() {
        this(7);
    }

    m(ab abVar, PrivateKey privateKey, SupportedPointFormats supportedPointFormats) throws IOException {
        this(7);
        this.c = privateKey;
        this.e = supportedPointFormats;
        a(abVar);
    }

    void a(SupportedPointFormats supportedPointFormats) {
        this.e = supportedPointFormats;
    }

    byte[] a(PublicKey publicKey) throws SSLException {
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        if (this.c == null) {
            try {
                KeyPair keyPairGenerateECKeyPair = securityProvider.generateECKeyPair(publicKey);
                this.c = keyPairGenerateECKeyPair.getPrivate();
                this.b = keyPairGenerateECKeyPair.getPublic();
            } catch (Exception e) {
                StringBuffer stringBuffer = new StringBuffer("Error generating ECDH key pair: ");
                stringBuffer.append(e.toString());
                throw new SSLException(stringBuffer.toString(), 2, 80, false);
            }
        }
        try {
            return securityProvider.createSharedECDHSecret(this.c, publicKey);
        } catch (Exception e2) {
            StringBuffer stringBuffer2 = new StringBuffer("Error generating shared ECDH secret: ");
            stringBuffer2.append(e2.toString());
            throw new SSLException(stringBuffer2.toString(), 2, 80, false);
        }
    }

    byte[] b() throws SSLException {
        try {
            return SecurityProvider.getSecurityProvider().createSharedECDHSecret(this.c, this.b);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("Error generating shared ECDH secret: ");
            stringBuffer.append(e.toString());
            throw new SSLException(stringBuffer.toString(), 2, 80, false);
        }
    }

    void a(PrivateKey privateKey) {
        this.c = privateKey;
        this.a = true;
    }

    void b(PublicKey publicKey) {
        if (this.b == null) {
            this.b = publicKey;
            this.a = true;
        }
    }

    @Override // iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        agVar.g(16);
        if (this.a) {
            agVar.e(0);
            return;
        }
        try {
            byte[] bArrEncodeECPublicKey = SecurityProvider.getSecurityProvider().encodeECPublicKey(this.b, this.e);
            agVar.e(bArrEncodeECPublicKey.length + 1);
            agVar.c(bArrEncodeECPublicKey);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("Error encoding EC public key: ");
            stringBuffer.append(e.toString());
            throw new SSLException(stringBuffer.toString(), 2, 80, false);
        }
    }

    void a(ab abVar) throws IOException {
        int iH = abVar.h();
        if (iH == 0 || iH == 2) {
            this.b = null;
            this.a = true;
            return;
        }
        byte[] bArr = new byte[abVar.k()];
        abVar.a(bArr);
        try {
            this.b = SecurityProvider.getSecurityProvider().decodeECPublicKey(bArr, this.c, this.e);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("Error decoding EC public key: ");
            stringBuffer.append(e.toString());
            throw new SSLException(stringBuffer.toString(), 2, 50, false);
        }
    }

    boolean c() {
        return this.b == null;
    }

    @Override // iaik.security.ssl.p
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (!this.a) {
            PublicKey publicKey = this.b;
            if (publicKey != null) {
                try {
                    stringBuffer.append(Utils.a(publicKey, false));
                    stringBuffer.append(" ECDH");
                } catch (Throwable unused) {
                }
            }
        } else {
            stringBuffer.append("empty");
        }
        return stringBuffer.toString();
    }
}
