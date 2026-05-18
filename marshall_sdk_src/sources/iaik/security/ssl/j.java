package iaik.security.ssl;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;

/* JADX INFO: loaded from: classes.dex */
class j extends k {
    private byte[] g;
    private String h;
    private SecretKey i;

    j(CipherSuite cipherSuite, SSLContext sSLContext, SSLTransport sSLTransport, byte[] bArr) throws SSLException {
        super(5);
        ((k) this).a = sSLContext.getRandomGenerator();
        PSKCredential pSKCredentialA = q.a(sSLContext, sSLTransport, bArr);
        this.i = pSKCredentialA.getPSK();
        this.h = pSKCredentialA.getIdentityString();
        if (this.i == null) {
            throw new SSLException("No pre shared key available!");
        }
        this.g = pSKCredentialA.getIdentity();
    }

    j(ab abVar, CipherSuite cipherSuite, SSLContext sSLContext, SSLTransport sSLTransport) throws IOException {
        super(5);
        a(abVar);
        PSKCredential pSKCredentialA = q.a(sSLContext, sSLTransport, this.g);
        this.i = pSKCredentialA.getPSK();
        this.h = pSKCredentialA.getIdentityString();
        if (this.i == null) {
            ((k) this).a = sSLContext.getRandomGenerator();
            this.i = q.a(((k) this).a);
        }
    }

    String a() {
        return this.h;
    }

    @Override // iaik.security.ssl.k
    byte[] a(PublicKey publicKey) throws InvalidKeyException {
        this.e = q.a(super.a(publicKey), this.i.getEncoded());
        return this.e;
    }

    @Override // iaik.security.ssl.k
    byte[] a(PrivateKey privateKey) throws InvalidKeyException {
        this.e = q.a(super.a(privateKey), this.i.getEncoded());
        return this.e;
    }

    @Override // iaik.security.ssl.k
    void a(ab abVar) throws IOException {
        abVar.h();
        this.g = abVar.g();
        this.b = new BigInteger(1, abVar.g());
    }

    @Override // iaik.security.ssl.k, iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        agVar.g(16);
        byte[] bArrA = Utils.a(this.b);
        agVar.e(this.g.length + 4 + bArrA.length);
        agVar.a(this.g);
        agVar.a(bArrA);
    }

    @Override // iaik.security.ssl.k, iaik.security.ssl.p
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("PSK identity: ");
        stringBuffer.append(this.h);
        return stringBuffer.toString();
    }
}
