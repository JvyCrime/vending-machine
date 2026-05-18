package iaik.security.ssl;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;

/* JADX INFO: loaded from: classes.dex */
class k extends p {
    Random a;
    BigInteger b;
    BigInteger c;
    BigInteger d;
    byte[] e;
    private boolean g;

    k(int i) {
        super(i);
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
    }

    k(Random random) {
        this(2);
        this.a = random;
    }

    k(ab abVar) throws IOException {
        this(2);
        a(abVar);
    }

    byte[] a(PublicKey publicKey) throws InvalidKeyException {
        DHPublicKey dHPublicKey = (DHPublicKey) publicKey;
        BigInteger y = dHPublicKey.getY();
        BigInteger g = dHPublicKey.getParams().getG();
        this.d = dHPublicKey.getParams().getP();
        SecurityProvider.getSecurityProvider().validateDHPublicKey(y, this.d, g);
        if (this.c == null) {
            int iBitLength = this.d.bitLength();
            int i = iBitLength > 7680 ? iBitLength > 15360 ? 512 : 384 : 300;
            this.c = new BigInteger(i, this.a).setBit(i - 1);
        }
        this.e = Utils.a(y.modPow(this.c, this.d));
        this.b = g.modPow(this.c, this.d);
        return this.e;
    }

    byte[] a(PrivateKey privateKey) throws InvalidKeyException {
        DHPrivateKey dHPrivateKey = (DHPrivateKey) privateKey;
        this.c = dHPrivateKey.getX();
        this.d = dHPrivateKey.getParams().getP();
        SecurityProvider.getSecurityProvider().validateDHPublicKey(this.b, this.d, null);
        byte[] bArrA = Utils.a(this.b.modPow(this.c, this.d));
        this.e = bArrA;
        return bArrA;
    }

    void a(BigInteger bigInteger) {
        this.c = bigInteger;
        this.g = true;
    }

    void b(BigInteger bigInteger) {
        this.b = bigInteger;
        this.g = true;
    }

    @Override // iaik.security.ssl.aj
    void a(ag agVar) throws IOException {
        agVar.g(16);
        if (this.g) {
            agVar.e(0);
            return;
        }
        byte[] bArrA = Utils.a(this.b);
        agVar.e(bArrA.length + 2);
        agVar.a(bArrA);
    }

    void a(ab abVar) throws IOException {
        byte[] bArr;
        int iH = abVar.h();
        if (iH == 0 || iH == 2) {
            this.b = null;
            this.g = true;
            return;
        }
        int iF = abVar.f();
        if (iF + 2 != iH) {
            bArr = new byte[iH];
            bArr[0] = (byte) (iF >> 8);
            bArr[1] = (byte) iF;
            abVar.a(bArr, 2, iH - 2);
        } else {
            bArr = new byte[iF];
            abVar.a(bArr);
        }
        this.b = new BigInteger(1, bArr);
    }

    boolean b() {
        return this.b == null;
    }

    @Override // iaik.security.ssl.p
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (!this.g) {
            BigInteger bigInteger = this.d;
            if (bigInteger != null) {
                stringBuffer.append(bigInteger.bitLength());
                stringBuffer.append(" bit DH");
            }
        } else {
            stringBuffer.append("empty");
        }
        return stringBuffer.toString();
    }
}
