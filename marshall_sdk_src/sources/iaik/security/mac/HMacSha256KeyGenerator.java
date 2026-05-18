package iaik.security.mac;

import iaik.security.cipher.VarLengthKeyGenerator;

/* JADX INFO: loaded from: classes.dex */
public class HMacSha256KeyGenerator extends VarLengthKeyGenerator {
    public HMacSha256KeyGenerator() {
        super("HmacSHA256", 256, -1, 512);
    }
}
