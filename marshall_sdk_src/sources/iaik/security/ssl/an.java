package iaik.security.ssl;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;
import java.security.PrivateKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

/* JADX INFO: loaded from: classes.dex */
class an extends as {
    private Key h;
    private String i;

    an(CipherSuite cipherSuite, byte[] bArr, byte[] bArr2, SSLTransport sSLTransport) {
        super(cipherSuite, sSLTransport);
        this.h = null;
        ((as) this).a = bArr;
        this.b = bArr2;
        this.i = cipherSuite.getKeyExchangeAlgorithm();
    }

    @Override // iaik.security.ssl.as
    void a(SSLServerContext sSLServerContext, KeyAndCert keyAndCert) throws SSLException {
        int i;
        String str = this.i;
        if (str.startsWith("DHE_DSS_EXPORT1024")) {
            i = 3;
        } else if (str.startsWith("DH_anon_EXPORT") || str.startsWith("DHE_DSS_EXPORT") || str.startsWith("DHE_RSA_EXPORT")) {
            i = 2;
        } else {
            if (!str.startsWith("DH_anon") && !str.startsWith("DHE_DSS") && !str.startsWith("DHE_RSA")) {
                StringBuffer stringBuffer = new StringBuffer("Invalid key exchange for DH ");
                stringBuffer.append(this.i);
                throw new SSLException(stringBuffer.toString());
            }
            i = 4;
        }
        DHPrivateKey dHPrivateKeyA = a(sSLServerContext, this.f, i);
        this.h = dHPrivateKeyA;
        DHParameterSpec params = dHPrivateKeyA.getParams();
        BigInteger p = params.getP();
        BigInteger g = params.getG();
        this.c = a(Utils.a(p), Utils.a(g), Utils.a(g.modPow(((DHPrivateKey) this.h).getX(), p)));
        this.d = b(sSLServerContext, keyAndCert);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x002c, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x002d, code lost:
    
        r5 = new java.lang.StringBuffer("SecurityProvider error generating DH private key: ");
        r5.append(r3.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0044, code lost:
    
        throw new iaik.security.ssl.SSLException(r5.toString());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static javax.crypto.interfaces.DHPrivateKey a(iaik.security.ssl.SSLServerContext r3, iaik.security.ssl.SSLTransport r4, int r5) throws iaik.security.ssl.SSLException {
        /*
            java.lang.Object r4 = r3.a(r4, r5)
            javax.crypto.spec.DHParameterSpec r4 = (javax.crypto.spec.DHParameterSpec) r4
            java.math.BigInteger r5 = r4.getP()
            java.math.BigInteger r4 = r4.getG()
            int r0 = r5.bitLength()
            int r0 = r0 + (-1)
        L14:
            java.math.BigInteger r1 = new java.math.BigInteger
            java.security.SecureRandom r2 = r3.getRandomGenerator()
            r1.<init>(r0, r2)
            int r2 = r1.bitLength()
            if (r2 != r0) goto L14
            iaik.security.ssl.SecurityProvider r3 = iaik.security.ssl.SecurityProvider.getSecurityProvider()     // Catch: java.lang.Exception -> L2c
            javax.crypto.interfaces.DHPrivateKey r3 = r3.getDHPrivateKey(r1, r5, r4)     // Catch: java.lang.Exception -> L2c
            return r3
        L2c:
            r3 = move-exception
            iaik.security.ssl.SSLException r4 = new iaik.security.ssl.SSLException
            java.lang.StringBuffer r5 = new java.lang.StringBuffer
            java.lang.String r0 = "SecurityProvider error generating DH private key: "
            r5.<init>(r0)
            java.lang.String r3 = r3.toString()
            r5.append(r3)
            java.lang.String r3 = r5.toString()
            r4.<init>(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.ssl.an.a(iaik.security.ssl.SSLServerContext, iaik.security.ssl.SSLTransport, int):javax.crypto.interfaces.DHPrivateKey");
    }

    @Override // iaik.security.ssl.as
    Key b() {
        return this.h;
    }

    @Override // iaik.security.ssl.as
    String a() {
        int iBitLength;
        Key key = this.h;
        if (key != null) {
            try {
                if (key instanceof PrivateKey) {
                    iBitLength = ((DHPrivateKey) key).getParams().getP().bitLength();
                } else {
                    iBitLength = ((DHPublicKey) key).getParams().getP().bitLength();
                }
                StringBuffer stringBuffer = new StringBuffer(String.valueOf(iBitLength));
                stringBuffer.append(" bit DH");
                return stringBuffer.toString();
            } catch (Throwable unused) {
            }
        }
        return null;
    }

    static byte[] a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        int length = bArr.length;
        int length2 = bArr2.length;
        int length3 = bArr3.length;
        byte[] bArr4 = new byte[length + 6 + length2 + length3];
        bArr4[0] = (byte) (length >> 8);
        bArr4[1] = (byte) length;
        System.arraycopy(bArr, 0, bArr4, 2, length);
        int i = 2 + length;
        int i2 = i + 1;
        bArr4[i] = (byte) (length2 >> 8);
        int i3 = i2 + 1;
        bArr4[i2] = (byte) length2;
        System.arraycopy(bArr2, 0, bArr4, i3, length2);
        int i4 = i3 + length2;
        int i5 = i4 + 1;
        bArr4[i4] = (byte) (length3 >> 8);
        bArr4[i5] = (byte) length3;
        System.arraycopy(bArr3, 0, bArr4, i5 + 1, length3);
        return bArr4;
    }

    @Override // iaik.security.ssl.as
    void a(ab abVar) throws IOException {
        int iH = abVar.h();
        byte[] bArrG = abVar.g();
        byte[] bArrG2 = abVar.g();
        byte[] bArrG3 = abVar.g();
        int length = iH - (((bArrG.length + bArrG2.length) + bArrG3.length) + 6);
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
            throw new SSLException("Invalid length of DH ServerKeyExchange message!", 2, 50, false);
        }
        BigInteger bigInteger = new BigInteger(1, bArrG);
        BigInteger bigInteger2 = new BigInteger(1, bArrG2);
        BigInteger bigInteger3 = new BigInteger(1, bArrG3);
        try {
            SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
            DHPublicKey dHPublicKey = securityProvider.getDHPublicKey(bigInteger3, bigInteger, bigInteger2);
            securityProvider.checkKeyLength(dHPublicKey);
            this.h = dHPublicKey;
            this.c = a(bArrG, bArrG2, bArrG3);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer("SecurityProvider reading DH public key: ");
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
