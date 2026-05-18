package iaik.security.rsa;

import iaik.security.md.SHA;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class SHAwithRSAISO9796P2Signature extends RSAISO9796P2Signature {
    public static final byte HASH_ID = 51;

    public SHAwithRSAISO9796P2Signature() {
        super("SHA1/RSA-ISO9796-2", 20, (byte) 51);
        this.hashEngine_ = new SHA();
    }

    @Override // iaik.iso.iso9796.ISO9796P2Signature
    protected boolean checkHashEngineName(String str) {
        String upperCase = str.toUpperCase(Locale.US);
        return upperCase.equals("SHA") || upperCase.equals("SHA-1") || upperCase.equals("SHA1");
    }
}
