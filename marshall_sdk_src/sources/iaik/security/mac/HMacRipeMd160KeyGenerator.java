package iaik.security.mac;

import iaik.security.cipher.VarLengthKeyGenerator;

/* JADX INFO: loaded from: classes.dex */
public class HMacRipeMd160KeyGenerator extends VarLengthKeyGenerator {
    public HMacRipeMd160KeyGenerator() {
        super("HmacRipeMd160", 160, -1, 512);
    }
}
