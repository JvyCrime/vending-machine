package iaik.security.rsa;

import com.bitmick.marshall.vmc.mdb_rsp_t;
import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import iaik.asn1.structures.AlgorithmID;
import iaik.security.md.RipeMd128;

/* JADX INFO: loaded from: classes.dex */
public class RipeMd128RSASignature extends RSASignature {
    static final AlgorithmID a = (AlgorithmID) AlgorithmID.ripeMd128.clone();
    static final AlgorithmID b = (AlgorithmID) AlgorithmID.ripeMd128_ISO.clone();
    static final byte[][] d = {new byte[]{48, 29, 48, 9, 6, 5, 43, 36, 3, 2, 2, 5, 0, 4, 16}, new byte[]{48, mdb_rsp_t.mdb_response_poll_ftl_req_to_rcv, 48, 7, 6, 5, 43, 36, 3, 2, 2, 4, 16}, new byte[]{48, 30, 48, 10, 6, 6, PPSCRADeviceValues.RESPONSE_SIGNATURE_CAPTURE_STATE, -49, 6, 3, 0, 50, 5, 0, 4, 16}, new byte[]{48, 28, 48, 8, 6, 6, PPSCRADeviceValues.RESPONSE_SIGNATURE_CAPTURE_STATE, -49, 6, 3, 0, 50, 4, 16}};

    public RipeMd128RSASignature() {
        super(a, new RipeMd128(), d);
    }
}
