package iaik.security.rsa;

import iaik.pkcs.pkcs1.RSACipher;
import iaik.utils.Util;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/* JADX INFO: loaded from: classes.dex */
public class RSA extends RSACipher {
    public static final int DECRYPT_MODE = 2;
    public static final int ENCRYPT_MODE = 1;
    private static RSACipherFactory b;

    static {
        Util.toString((byte[]) null, -1, 1);
    }

    protected RSA() {
    }

    static synchronized RSA a() {
        RSACipherFactory rSACipherFactory;
        rSACipherFactory = b;
        return rSACipherFactory == null ? new RSA() : rSACipherFactory.getInstance();
    }

    public static synchronized void setRSACipherFactory(RSACipherFactory rSACipherFactory) {
        b = rSACipherFactory;
    }

    protected byte[] doFinal(byte[] bArr, int i, int i2) throws Exception {
        return super.engineDoFinal(bArr, i, i2);
    }

    @Override // iaik.pkcs.pkcs1.RSACipher
    public SecureRandom getSecureRandom() {
        return super.getSecureRandom();
    }

    protected void init(int i, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        super.engineInit(i, key, secureRandom);
    }

    protected void setMode(String str) throws NoSuchAlgorithmException {
        super.engineSetMode(str);
    }

    protected void setPadding(String str) throws Exception {
        super.engineSetPadding(str);
    }

    @Override // iaik.pkcs.pkcs1.RSACipher
    public void setSecureRandom(SecureRandom secureRandom) {
        super.setSecureRandom(secureRandom);
    }
}
