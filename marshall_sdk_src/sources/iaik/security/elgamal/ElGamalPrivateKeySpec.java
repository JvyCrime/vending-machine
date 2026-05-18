package iaik.security.elgamal;

import java.math.BigInteger;
import java.security.spec.KeySpec;

/* JADX INFO: loaded from: classes.dex */
public class ElGamalPrivateKeySpec implements KeySpec {
    protected BigInteger g;
    protected BigInteger p;
    protected BigInteger x;

    public ElGamalPrivateKeySpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.x = bigInteger;
        this.p = bigInteger2;
        this.g = bigInteger3;
    }

    public BigInteger getG() {
        return this.g;
    }

    public BigInteger getP() {
        return this.p;
    }

    public BigInteger getX() {
        return this.x;
    }
}
