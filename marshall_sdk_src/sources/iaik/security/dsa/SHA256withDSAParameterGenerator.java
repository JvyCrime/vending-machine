package iaik.security.dsa;

import iaik.security.md.SHA256;

/* JADX INFO: loaded from: classes.dex */
public class SHA256withDSAParameterGenerator extends SHA2withDSAParameterGenerator {
    public SHA256withDSAParameterGenerator() {
        super(new SHA256(), 256);
    }
}
