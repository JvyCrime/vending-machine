package iaik.security.cipher;

import iaik.security.ssl.SecurityProvider;

/* JADX INFO: loaded from: classes.dex */
public class AESKeyGenerator extends VarLengthKeyGenerator {
    public AESKeyGenerator() {
        super(SecurityProvider.ALG_KEYGEN_AES, 128, 256, 128, 32);
    }
}
