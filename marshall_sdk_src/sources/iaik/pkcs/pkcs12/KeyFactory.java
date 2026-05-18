package iaik.pkcs.pkcs12;

import iaik.security.cipher.PBEKeyBMP;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactorySpi;
import javax.crypto.spec.PBEKeySpec;

/* JADX INFO: loaded from: classes.dex */
public final class KeyFactory extends SecretKeyFactorySpi {
    static Class a;

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // javax.crypto.SecretKeyFactorySpi
    protected SecretKey engineGenerateSecret(KeySpec keySpec) throws InvalidKeySpecException {
        if (keySpec instanceof PBEKeySpec) {
            return new PBEKeyBMP((PBEKeySpec) keySpec);
        }
        throw new InvalidKeySpecException("Only PBEKeySpec allowed.");
    }

    @Override // javax.crypto.SecretKeyFactorySpi
    protected KeySpec engineGetKeySpec(SecretKey secretKey, Class cls) throws InvalidKeySpecException {
        if (!(secretKey instanceof PBEKeyBMP)) {
            throw new InvalidKeySpecException("Only PBEKeys can be converted.");
        }
        Class clsClass$ = a;
        if (clsClass$ == null) {
            clsClass$ = class$("javax.crypto.spec.PBEKeySpec");
            a = clsClass$;
        }
        if (clsClass$.isAssignableFrom(cls)) {
            return new PBEKeySpec(((PBEKeyBMP) secretKey).getKey());
        }
        throw new InvalidKeySpecException("Can't convert key to KeySpec.");
    }

    @Override // javax.crypto.SecretKeyFactorySpi
    protected SecretKey engineTranslateKey(SecretKey secretKey) throws InvalidKeyException {
        if (secretKey == null) {
            throw new InvalidKeyException("Cannot translate a null key!");
        }
        if (secretKey instanceof PBEKeyBMP) {
            return secretKey;
        }
        byte[] encoded = secretKey.getEncoded();
        int length = (encoded.length / 2) - 1;
        char[] cArr = new char[length];
        for (int i = 0; i < length; i++) {
            cArr[i] = (char) (encoded[(i * 2) + 1] & 255);
        }
        return new PBEKeyBMP(cArr);
    }
}
