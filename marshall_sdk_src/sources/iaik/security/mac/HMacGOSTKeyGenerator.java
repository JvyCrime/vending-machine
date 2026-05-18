package iaik.security.mac;

import iaik.security.cipher.VarLengthKeyGenerator;

/* JADX INFO: loaded from: classes.dex */
public class HMacGOSTKeyGenerator extends VarLengthKeyGenerator {
    public HMacGOSTKeyGenerator() {
        super("HMacGOST", 256, 256, 256);
    }
}
