package iaik.security.mac;

import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class HMacRipeMd160 extends HMac {
    public HMacRipeMd160() throws NoSuchAlgorithmException {
        super("RIPEMD160");
    }
}
