package iaik.security.cipher;

import javax.crypto.NoSuchPaddingException;

/* JADX INFO: loaded from: classes.dex */
public class ARCFOUR extends AbstractC0022a {
    public ARCFOUR() {
        super(new C0027h());
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineSetPadding(String str) throws NoSuchPaddingException {
        this.g = null;
    }
}
