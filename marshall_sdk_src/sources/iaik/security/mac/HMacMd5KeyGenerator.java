package iaik.security.mac;

import iaik.security.cipher.VarLengthKeyGenerator;
import iaik.security.ssl.SecurityProvider;

/* JADX INFO: loaded from: classes.dex */
public class HMacMd5KeyGenerator extends VarLengthKeyGenerator {
    public HMacMd5KeyGenerator() {
        super(SecurityProvider.ALG_HMAC_MD5, 128, -1, 512);
    }
}
