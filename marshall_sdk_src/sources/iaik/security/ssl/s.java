package iaik.security.ssl;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.SecretKey;

/* JADX INFO: loaded from: classes.dex */
class s extends r {
    private byte[] h;
    private String i;
    private SecretKey j;

    s(CipherSuite cipherSuite, int i, int i2, SSLContext sSLContext, SSLTransport sSLTransport, byte[] bArr) throws SSLException {
        super(6);
        this.d = i;
        this.c = sSLContext.getRandomGenerator();
        this.e = i2;
        this.g = ((SSLClientContext) sSLContext).b();
        PSKCredential pSKCredentialA = q.a(sSLContext, sSLTransport, bArr);
        SecretKey psk = pSKCredentialA.getPSK();
        this.j = psk;
        if (psk == null) {
            throw new SSLException("No pre shared key available!");
        }
        this.h = pSKCredentialA.getIdentity();
        this.i = pSKCredentialA.getIdentityString();
        a();
    }

    s(ab abVar, CipherSuite cipherSuite, int i, int i2, SSLContext sSLContext, SSLTransport sSLTransport) throws IOException {
        super(6);
        this.d = i;
        this.c = sSLContext.getRandomGenerator();
        this.e = i2;
        this.g = ((SSLServerContext) sSLContext).n();
        a(abVar);
        PSKCredential pSKCredentialA = q.a(sSLContext, sSLTransport, this.h);
        this.j = pSKCredentialA.getPSK();
        this.i = pSKCredentialA.getIdentityString();
        if (this.j == null) {
            this.j = q.a(this.c);
        }
    }

    String b() {
        return this.i;
    }

    @Override // iaik.security.ssl.r
    byte[] a(PublicKey publicKey) throws SSLException {
        this.b = q.a(super.a(publicKey), this.j.getEncoded());
        return this.b;
    }

    @Override // iaik.security.ssl.r
    byte[] a(PrivateKey privateKey) throws SSLException {
        this.b = q.a(super.a(privateKey), this.j.getEncoded());
        return this.b;
    }

    @Override // iaik.security.ssl.r, iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        agVar.g(16);
        agVar.e(this.h.length + 4 + ((r) this).a.length);
        agVar.a(this.h);
        agVar.a(((r) this).a);
    }

    @Override // iaik.security.ssl.r
    void a(ab abVar) throws IOException {
        abVar.h();
        this.h = abVar.g();
        ((r) this).a = abVar.g();
    }

    @Override // iaik.security.ssl.p
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("PSK identity: ");
        stringBuffer.append(this.i);
        return stringBuffer.toString();
    }
}
