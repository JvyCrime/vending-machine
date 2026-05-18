package iaik.security.ssl;

import java.io.IOException;
import java.util.Random;
import javax.crypto.SecretKey;

/* JADX INFO: loaded from: classes.dex */
class q extends p {
    private byte[] a;
    private String b;
    private SecretKey c;

    static PSKCredential a(SSLContext sSLContext, SSLTransport sSLTransport, byte[] bArr) throws SSLException {
        PSKCredential pSKCredential = sSLContext.getPSKCredential(bArr, sSLTransport);
        if (pSKCredential != null) {
            return pSKCredential;
        }
        throw new SSLException("No pre shared key available!");
    }

    static byte[] a(byte[] bArr, byte[] bArr2) {
        int length = bArr != null ? bArr.length : bArr2.length;
        int length2 = bArr2.length;
        byte[] bArr3 = new byte[length + 4 + length2];
        bArr3[0] = (byte) (length >> 8);
        bArr3[1] = (byte) length;
        if (bArr != null) {
            System.arraycopy(bArr, 0, bArr3, 2, length);
        }
        int i = 2 + length;
        int i2 = i + 1;
        bArr3[i] = (byte) (length2 >> 8);
        bArr3[i2] = (byte) length2;
        System.arraycopy(bArr2, 0, bArr3, i2 + 1, length2);
        return bArr3;
    }

    static PreSharedKey a(Random random) {
        byte[] bArr = new byte[64];
        random.nextBytes(bArr);
        return new PreSharedKey(bArr);
    }

    private q() {
        super(4);
    }

    q(CipherSuite cipherSuite, SSLContext sSLContext, SSLTransport sSLTransport, byte[] bArr) throws SSLException {
        this();
        PSKCredential pSKCredentialA = a(sSLContext, sSLTransport, bArr);
        SecretKey psk = pSKCredentialA.getPSK();
        this.c = psk;
        if (psk == null) {
            throw new SSLException("No pre shared key available!");
        }
        this.a = pSKCredentialA.getIdentity();
        this.b = pSKCredentialA.getIdentityString();
    }

    q(ab abVar, CipherSuite cipherSuite, SSLContext sSLContext, SSLTransport sSLTransport) throws IOException {
        this();
        a(abVar);
        PSKCredential pSKCredentialA = a(sSLContext, sSLTransport, this.a);
        this.c = pSKCredentialA.getPSK();
        this.b = pSKCredentialA.getIdentityString();
        if (this.c == null) {
            this.c = a(sSLContext.getRandomGenerator());
        }
    }

    String b() {
        return this.b;
    }

    byte[] a() {
        return a(null, this.c.getEncoded());
    }

    byte[] c() {
        return a(null, this.c.getEncoded());
    }

    void a(ab abVar) throws IOException {
        abVar.h();
        this.a = abVar.g();
    }

    @Override // iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        agVar.g(16);
        agVar.e(this.a.length + 2);
        agVar.a(this.a);
    }

    @Override // iaik.security.ssl.p
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("PSK identity: ");
        stringBuffer.append(this.b);
        return stringBuffer.toString();
    }
}
