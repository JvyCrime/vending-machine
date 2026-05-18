package iaik.security.rsa;

import iaik.asn1.structures.AlgorithmID;
import iaik.security.md.RipeMd256;

/* JADX INFO: loaded from: classes.dex */
public final class RipeMd256RSASignature extends RSASignature {
    static final AlgorithmID a = (AlgorithmID) AlgorithmID.ripeMd256.clone();
    static final byte[][] b = {new byte[]{48, 45, 48, 9, 6, 5, 43, 36, 3, 2, 3, 5, 0, 4, 32}, new byte[]{48, 43, 48, 7, 6, 5, 43, 36, 3, 2, 3, 4, 32}};

    public RipeMd256RSASignature() {
        super(a, new RipeMd256(), b);
    }
}
