package iaik.security.mac;

import iaik.security.cipher.VarLengthKeyGenerator;
import iaik.security.ssl.SecurityProvider;

/* JADX INFO: loaded from: classes.dex */
public class HMacSha384KeyGenerator extends VarLengthKeyGenerator {
    public HMacSha384KeyGenerator() {
        super(SecurityProvider.ALG_HMAC_SHA384, 384, -1, 1024);
    }
}
