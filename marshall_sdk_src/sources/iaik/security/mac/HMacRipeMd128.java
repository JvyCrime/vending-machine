package iaik.security.mac;

import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class HMacRipeMd128 extends HMac {
    public HMacRipeMd128() throws NoSuchAlgorithmException {
        super("RIPEMD128");
    }
}
