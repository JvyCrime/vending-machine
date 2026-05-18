package iaik.security.mac;

import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public final class HMacGOST extends HMac {
    public HMacGOST() throws NoSuchAlgorithmException {
        super("GOST3411");
    }
}
