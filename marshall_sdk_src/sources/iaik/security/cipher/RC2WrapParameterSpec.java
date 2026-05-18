package iaik.security.cipher;

import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class RC2WrapParameterSpec implements AlgorithmParameterSpec {
    private int a;

    public RC2WrapParameterSpec(int i) {
        this.a = i;
    }

    public int getEffectiveKeyBits() {
        return this.a;
    }
}
