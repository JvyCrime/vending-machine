package iaik.security.mac;

import iaik.security.ssl.SecurityProvider;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.NoSuchPaddingException;

/* JADX INFO: loaded from: classes.dex */
public class CMacAES extends CMac {
    public CMacAES() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        super(SecurityProvider.ALG_KEYGEN_AES);
    }
}
