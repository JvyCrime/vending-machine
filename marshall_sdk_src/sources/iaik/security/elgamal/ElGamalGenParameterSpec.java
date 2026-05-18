package iaik.security.elgamal;

import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class ElGamalGenParameterSpec implements AlgorithmParameterSpec {
    private int a;
    private int b;

    public ElGamalGenParameterSpec(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    public int getExponentSize() {
        return this.b;
    }

    public int getPrimeSize() {
        return this.a;
    }
}
