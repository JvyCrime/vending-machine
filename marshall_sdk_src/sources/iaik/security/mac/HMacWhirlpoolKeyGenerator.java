package iaik.security.mac;

import iaik.security.cipher.VarLengthKeyGenerator;

/* JADX INFO: loaded from: classes.dex */
public class HMacWhirlpoolKeyGenerator extends VarLengthKeyGenerator {
    public HMacWhirlpoolKeyGenerator() {
        super("HmacWhirlpool", 512, -1, 1024);
    }
}
