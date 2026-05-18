package iaik.security.mac;

import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class HMacWhirlpool extends HMac {
    public HMacWhirlpool() throws NoSuchAlgorithmException {
        super("WHIRLPOOL", 128);
    }
}
