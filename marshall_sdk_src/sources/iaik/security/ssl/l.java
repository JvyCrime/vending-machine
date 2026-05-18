package iaik.security.ssl;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Random;
import javax.crypto.SecretKey;

/* JADX INFO: loaded from: classes.dex */
class l extends m {
    Random a;
    private byte[] g;
    private String h;
    private SecretKey i;

    l(CipherSuite cipherSuite, SSLContext sSLContext, SSLTransport sSLTransport, byte[] bArr) throws SSLException {
        super(8);
        this.a = sSLContext.getRandomGenerator();
        PSKCredential pSKCredentialA = q.a(sSLContext, sSLTransport, bArr);
        this.i = pSKCredentialA.getPSK();
        this.h = pSKCredentialA.getIdentityString();
        if (this.i == null) {
            throw new SSLException("No pre shared key available!");
        }
        this.g = pSKCredentialA.getIdentity();
    }

    l(ab abVar, CipherSuite cipherSuite, SSLContext sSLContext, SSLTransport sSLTransport, PrivateKey privateKey, SupportedPointFormats supportedPointFormats) throws IOException {
        super(8);
        this.c = privateKey;
        this.e = supportedPointFormats;
        a(abVar);
        PSKCredential pSKCredentialA = q.a(sSLContext, sSLTransport, this.g);
        this.i = pSKCredentialA.getPSK();
        this.h = pSKCredentialA.getIdentityString();
        if (this.i == null) {
            SecureRandom randomGenerator = sSLContext.getRandomGenerator();
            this.a = randomGenerator;
            this.i = q.a(randomGenerator);
        }
    }

    String a() {
        return this.h;
    }

    @Override // iaik.security.ssl.m
    byte[] a(PublicKey publicKey) throws SSLException {
        this.d = q.a(super.a(publicKey), this.i.getEncoded());
        return this.d;
    }

    @Override // iaik.security.ssl.m
    byte[] b() throws SSLException {
        this.d = q.a(super.b(), this.i.getEncoded());
        return this.d;
    }

    @Override // iaik.security.ssl.m
    void a(ab abVar) throws IOException {
        abVar.h();
        this.g = abVar.g();
        try {
            this.b = SecurityProvider.getSecurityProvider().decodeECPublicKey(abVar.l(), this.c, this.e);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("Error decoding EC public key: ");
            stringBuffer.append(e.toString());
            throw new SSLException(stringBuffer.toString(), 2, 50, false);
        }
    }

    @Override // iaik.security.ssl.m, iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        agVar.g(16);
        try {
            byte[] bArrEncodeECPublicKey = SecurityProvider.getSecurityProvider().encodeECPublicKey(this.b, this.e);
            agVar.e(this.g.length + 3 + bArrEncodeECPublicKey.length);
            agVar.a(this.g);
            agVar.c(bArrEncodeECPublicKey);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("Error encoding EC public key: ");
            stringBuffer.append(e.toString());
            throw new SSLException(stringBuffer.toString(), 2, 80, false);
        }
    }

    @Override // iaik.security.ssl.m, iaik.security.ssl.p
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("PSK identity: ");
        stringBuffer.append(this.h);
        return stringBuffer.toString();
    }
}
