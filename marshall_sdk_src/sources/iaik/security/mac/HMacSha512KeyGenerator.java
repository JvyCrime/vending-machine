package iaik.security.mac;

import iaik.security.cipher.VarLengthKeyGenerator;
import iaik.security.ssl.SecurityProvider;

/* JADX INFO: loaded from: classes.dex */
public class HMacSha512KeyGenerator extends VarLengthKeyGenerator {
    public HMacSha512KeyGenerator() {
        super(SecurityProvider.ALG_HMAC_SHA512, 512, -1, 1024);
    }
}
