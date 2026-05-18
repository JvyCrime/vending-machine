package iaik.security.rsa;

import com.bitmick.marshall.vmc.marshall_t;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import iaik.asn1.structures.AlgorithmID;
import iaik.security.md.Whirlpool;

/* JADX INFO: loaded from: classes.dex */
public class WhirlpoolRSASignature extends RSASignature {
    static final AlgorithmID a = (AlgorithmID) AlgorithmID.whirlpool.clone();
    static final byte[][] b = {new byte[]{48, marshall_t.status_vpos_try_another_card, 48, 10, 6, 6, PPSCRADeviceValues.RESPONSE_SIGNATURE_CAPTURE_STATE, -49, 6, 3, 0, 55, 5, 0, 4, 64}, new byte[]{48, 76, 48, 8, 6, 6, PPSCRADeviceValues.RESPONSE_SIGNATURE_CAPTURE_STATE, -49, 6, 3, 0, 55, 4, 64}};

    public WhirlpoolRSASignature() {
        super(a, new Whirlpool(), b);
    }
}
