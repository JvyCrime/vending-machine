package iaik.security.rsa;

import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import iaik.asn1.structures.AlgorithmID;
import iaik.security.md.Md2;

/* JADX INFO: loaded from: classes.dex */
public class Md2RSASignature extends RSASignature {
    static final AlgorithmID a = (AlgorithmID) AlgorithmID.md2.clone();
    static final byte[][] b = {new byte[]{48, 32, 48, 12, 6, 8, PPSCRADeviceValues.RESPONSE_DELAYED_ACK, -122, 72, -122, -9, 13, 2, 2, 5, 0, 4, 16}, new byte[]{48, 30, 48, 10, 6, 8, PPSCRADeviceValues.RESPONSE_DELAYED_ACK, -122, 72, -122, -9, 13, 2, 2, 4, 16}};

    public Md2RSASignature() {
        super(a, new Md2(), b);
    }
}
