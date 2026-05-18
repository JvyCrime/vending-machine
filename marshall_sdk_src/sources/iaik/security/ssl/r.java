package iaik.security.ssl;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

/* JADX INFO: loaded from: classes.dex */
class r extends p {
    byte[] a;
    byte[] b;
    SecureRandom c;
    int d;
    int e;
    boolean g;

    r(int i) {
        super(i);
        this.a = null;
        this.b = null;
    }

    r(SecureRandom secureRandom, int i, int i2, boolean z) {
        this(1);
        this.c = secureRandom;
        this.d = i;
        this.e = i2;
        this.g = z;
        a();
    }

    r(ab abVar, SecureRandom secureRandom, int i, int i2, boolean z) throws IOException {
        this(1);
        this.c = secureRandom;
        this.d = i;
        this.e = i2;
        this.g = z;
        a(abVar);
    }

    void a() {
        byte[] bArr = new byte[48];
        this.b = bArr;
        this.c.nextBytes(bArr);
        int i = this.g ? this.e : this.d;
        byte[] bArr2 = this.b;
        bArr2[0] = (byte) (i >> 8);
        bArr2[1] = (byte) (i & 255);
    }

    byte[] a(PublicKey publicKey) throws SSLException {
        try {
            this.a = SecurityProvider.getSecurityProvider().getCipher(SecurityProvider.ALG_CIPHER_RSA_ENCRYPT, 1, publicKey, null, this.c).doFinal(this.b);
            return this.b;
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("Unable to encrypt preMasterSecret: ");
            stringBuffer.append(e);
            throw new SSLException(stringBuffer.toString());
        }
    }

    byte[] a(PrivateKey privateKey) throws SSLException {
        try {
            byte[] bArrDoFinal = SecurityProvider.getSecurityProvider().getCipher(SecurityProvider.ALG_CIPHER_RSA_DECRYPT, 2, privateKey, null, null).doFinal(this.a);
            this.b = bArrDoFinal;
            int i = (bArrDoFinal[1] & 255) | ((bArrDoFinal[0] & 255) << 8);
            if (this.g && i != this.e) {
                throw new SSLException("Client pre-master-secret version number does not match!");
            }
        } catch (Exception unused) {
            byte[] bArr = new byte[48];
            this.b = bArr;
            this.c.nextBytes(bArr);
            byte[] bArr2 = this.b;
            int i2 = this.d;
            bArr2[0] = (byte) (i2 >> 8);
            bArr2[1] = (byte) (i2 & 255);
        }
        return this.b;
    }

    @Override // iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        agVar.g(16);
        if (this.d >= 769) {
            agVar.e(this.a.length + 2);
            agVar.a(this.a.length);
        } else {
            agVar.e(this.a.length);
        }
        agVar.write(this.a);
    }

    void a(ab abVar) throws IOException {
        int iH = abVar.h();
        if (this.d >= 769) {
            iH = abVar.f();
        }
        byte[] bArr = new byte[iH];
        this.a = bArr;
        abVar.a(bArr);
    }
}
