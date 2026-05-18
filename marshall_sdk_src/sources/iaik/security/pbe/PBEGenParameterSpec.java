package iaik.security.pbe;

import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class PBEGenParameterSpec implements AlgorithmParameterSpec {
    private int a;
    private int b;

    public PBEGenParameterSpec(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    public int getIterationCount() {
        return this.b;
    }

    public int getSaltLength() {
        return this.a;
    }
}
