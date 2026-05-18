package iaik.security.mac;

import iaik.security.cipher.VarLengthKeyGenerator;

/* JADX INFO: loaded from: classes.dex */
public class HMacShaKeyGenerator extends VarLengthKeyGenerator {
    public HMacShaKeyGenerator() {
        super("HmacSHA1", 160, -1, 512);
    }
}
