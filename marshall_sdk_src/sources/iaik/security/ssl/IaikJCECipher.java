package iaik.security.ssl;

import iaik.utils.IaikSecurity;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/* JADX INFO: loaded from: classes.dex */
public class IaikJCECipher extends ac {
    @Override // iaik.security.ssl.ac
    public Cipher getCipher(String str, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        if ("IAIK".equals(str2)) {
            return IaikSecurity.getCipher(str);
        }
        return super.getCipher(str, str2);
    }
}
