package iaik.security.ssl;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

/* JADX INFO: loaded from: classes.dex */
class au extends as {
    private RSAPublicKey h;
    private String i;

    au(CipherSuite cipherSuite, byte[] bArr, byte[] bArr2, SSLTransport sSLTransport) {
        super(cipherSuite, sSLTransport);
        ((as) this).a = bArr;
        this.b = bArr2;
        this.i = cipherSuite.getKeyExchangeAlgorithm();
    }

    @Override // iaik.security.ssl.as
    void a(SSLServerContext sSLServerContext, KeyAndCert keyAndCert) throws SSLException {
        KeyPair keyPair;
        if (this.i.equals("RSA_EXPORT1024") || this.i.equals("RSA")) {
            keyPair = (KeyPair) sSLServerContext.a(this.f, 1);
        } else if (this.i.equals("RSA_EXPORT")) {
            keyPair = (KeyPair) sSLServerContext.a(this.f, 0);
        } else {
            StringBuffer stringBuffer = new StringBuffer("Invalid key exchange for RSA ");
            stringBuffer.append(this.i);
            throw new SSLException(stringBuffer.toString());
        }
        if (keyPair == null) {
            StringBuffer stringBuffer2 = new StringBuffer("No ");
            stringBuffer2.append(this.i);
            stringBuffer2.append(" temp KeyPair for Server key exchange available.");
            throw new SSLException(stringBuffer2.toString());
        }
        try {
            RSAPublicKey rSAPublicKey = (RSAPublicKey) keyPair.getPublic();
            this.h = rSAPublicKey;
            a(Utils.a(rSAPublicKey.getModulus()), Utils.a(this.h.getPublicExponent()));
            this.d = b(sSLServerContext, keyAndCert);
        } catch (ClassCastException unused) {
            throw new SSLException("Temp KeyPair is not a RSAKeyPair.");
        }
    }

    @Override // iaik.security.ssl.as
    Key b() {
        return this.h;
    }

    @Override // iaik.security.ssl.as
    String a() {
        RSAPublicKey rSAPublicKey = this.h;
        if (rSAPublicKey != null) {
            try {
                StringBuffer stringBuffer = new StringBuffer(String.valueOf(rSAPublicKey.getModulus().bitLength()));
                stringBuffer.append(" bit RSA");
                return stringBuffer.toString();
            } catch (Throwable unused) {
            }
        }
        return null;
    }

    private void a(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        int length2 = bArr2.length;
        this.c = new byte[length + 4 + length2];
        this.c[0] = (byte) (length >> 8);
        this.c[1] = (byte) length;
        System.arraycopy(bArr, 0, this.c, 2, length);
        int i = 2 + length;
        int i2 = i + 1;
        this.c[i] = (byte) (length2 >> 8);
        this.c[i2] = (byte) length2;
        System.arraycopy(bArr2, 0, this.c, i2 + 1, length2);
    }

    @Override // iaik.security.ssl.as
    void a(ab abVar) throws IOException {
        abVar.h();
        byte[] bArrG = abVar.g();
        byte[] bArrG2 = abVar.g();
        if (this.e != 0) {
            this.d = abVar.g();
        }
        BigInteger bigInteger = new BigInteger(1, bArrG2);
        BigInteger bigInteger2 = new BigInteger(1, bArrG);
        try {
            SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
            RSAPublicKey rSAPublicKey = securityProvider.getRSAPublicKey(bigInteger2, bigInteger);
            securityProvider.checkKeyLength(rSAPublicKey);
            this.h = rSAPublicKey;
            a(bArrG, bArrG2);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("SecurityProvider error generating RSA public key: ");
            stringBuffer.append(e.toString());
            throw new SSLException(stringBuffer.toString());
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Public key:\n");
        stringBuffer.append(this.h);
        return stringBuffer.toString();
    }
}
