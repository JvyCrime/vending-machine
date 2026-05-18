package iaik.pkcs.pkcs1;

import iaik.asn1.structures.AlgorithmID;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class RSASSAPkcs1v15ParameterSpec extends PKCS1AlgorithmParameterSpec {
    private AlgorithmID a;

    public RSASSAPkcs1v15ParameterSpec(AlgorithmID algorithmID) {
        Objects.requireNonNull(algorithmID, "Cannot create RSASSAPkcs1v15ParameterSpec from null hashAlgorithm ID!");
        this.a = algorithmID;
    }

    public AlgorithmID getHashAlgorithm() {
        return this.a;
    }

    public String toString() {
        return this.a.toString();
    }
}
