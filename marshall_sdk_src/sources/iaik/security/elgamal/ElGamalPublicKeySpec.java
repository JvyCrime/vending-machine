package iaik.security.elgamal;

import java.math.BigInteger;
import java.security.spec.KeySpec;

/* JADX INFO: loaded from: classes.dex */
public class ElGamalPublicKeySpec implements KeySpec {
    protected BigInteger g_;
    protected BigInteger p_;
    protected BigInteger y_;

    public ElGamalPublicKeySpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        this.y_ = bigInteger;
        this.p_ = bigInteger2;
        this.g_ = bigInteger3;
    }

    public BigInteger getG() {
        return this.g_;
    }

    public BigInteger getP() {
        return this.p_;
    }

    public BigInteger getY() {
        return this.y_;
    }
}
