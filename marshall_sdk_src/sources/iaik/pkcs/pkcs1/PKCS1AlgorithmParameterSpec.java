package iaik.pkcs.pkcs1;

import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class PKCS1AlgorithmParameterSpec implements AlgorithmParameterSpec {
    private SecureRandom a;

    public SecureRandom getSecureRandom() {
        return this.a;
    }

    public void setSecureRandom(SecureRandom secureRandom) {
        this.a = secureRandom;
    }
}
