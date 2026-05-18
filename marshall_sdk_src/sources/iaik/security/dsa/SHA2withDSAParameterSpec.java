package iaik.security.dsa;

import java.math.BigInteger;
import java.security.spec.DSAParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class SHA2withDSAParameterSpec extends DSAParameterSpec {
    private int a;
    private byte[] b;

    public SHA2withDSAParameterSpec(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        super(bigInteger, bigInteger2, bigInteger3);
        this.a = -1;
    }

    void a(int i) {
        this.a = i;
    }

    void a(byte[] bArr) {
        this.b = bArr;
    }

    public int getCounter() {
        return this.a;
    }

    public byte[] getSeed() {
        return this.b;
    }
}
