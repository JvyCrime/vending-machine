package iaik.security.mac;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.NoSuchPaddingException;

/* JADX INFO: loaded from: classes.dex */
public class CMacDESede extends CMac {
    public CMacDESede() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        super("DESede");
    }
}
