package iaik.security.cipher;

import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class CAST128WrapParameterSpec implements AlgorithmParameterSpec {
    private int a;

    public CAST128WrapParameterSpec(int i) {
        this.a = i;
    }

    public int getKeyLength() {
        return this.a;
    }
}
