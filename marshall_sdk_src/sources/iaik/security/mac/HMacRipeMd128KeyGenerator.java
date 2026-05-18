package iaik.security.mac;

import iaik.security.cipher.VarLengthKeyGenerator;

/* JADX INFO: loaded from: classes.dex */
public class HMacRipeMd128KeyGenerator extends VarLengthKeyGenerator {
    public HMacRipeMd128KeyGenerator() {
        super("HmacRipeMd128", 128, -1, 512);
    }
}
