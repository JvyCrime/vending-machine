package iaik.security.rsa;

import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import iaik.asn1.structures.AlgorithmID;
import iaik.security.md.RipeMd160;

/* JADX INFO: loaded from: classes.dex */
public class RipeMd160RSASignature extends RSASignature {
    static final AlgorithmID a = (AlgorithmID) AlgorithmID.ripeMd160.clone();
    static final AlgorithmID b = (AlgorithmID) AlgorithmID.ripeMd160_ISO.clone();
    static final byte[][] d = {new byte[]{48, 33, 48, 9, 6, 5, 43, 36, 3, 2, 1, 5, 0, 4, 20}, new byte[]{48, 31, 48, 7, 6, 5, 43, 36, 3, 2, 1, 4, 20}, new byte[]{48, 34, 48, 10, 6, 6, PPSCRADeviceValues.RESPONSE_SIGNATURE_CAPTURE_STATE, -49, 6, 3, 0, 49, 5, 0, 4, 20}, new byte[]{48, 32, 48, 8, 6, 6, PPSCRADeviceValues.RESPONSE_SIGNATURE_CAPTURE_STATE, -49, 6, 3, 0, 49, 4, 20}};

    public RipeMd160RSASignature() {
        super(a, new RipeMd160(), d);
    }
}
