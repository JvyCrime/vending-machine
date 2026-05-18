package iaik.security.rsa;

import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import iaik.asn1.structures.AlgorithmID;
import iaik.security.md.SHA;

/* JADX INFO: loaded from: classes.dex */
public class ShaRSASignature extends RSASignature {
    static final AlgorithmID a = (AlgorithmID) AlgorithmID.sha.clone();
    static final byte[][] b = {new byte[]{48, 33, 48, 9, 6, 5, 43, 14, 3, 2, PPSCRADeviceValues.COMMAND_REQUEST_DEVICE_INFORMATION, 5, 0, 4, 20}, new byte[]{48, 31, 48, 7, 6, 5, 43, 14, 3, 2, PPSCRADeviceValues.COMMAND_REQUEST_DEVICE_INFORMATION, 4, 20}};

    public ShaRSASignature() {
        super(a, new SHA(), b);
    }
}
