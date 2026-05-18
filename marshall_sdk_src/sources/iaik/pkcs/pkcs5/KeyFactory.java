package iaik.pkcs.pkcs5;

import iaik.asn1.UTF8String;
import iaik.security.cipher.PBEKey;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactorySpi;
import javax.crypto.spec.PBEKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class KeyFactory extends SecretKeyFactorySpi {
    static Class a;
    private String b;

    public static final class PBEWithMD5AndDESKeyFactory extends KeyFactory {
        public PBEWithMD5AndDESKeyFactory() {
            super("PBEWithMD5AndDES");
        }
    }

    public KeyFactory() {
    }

    KeyFactory(String str) {
        this.b = str;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // javax.crypto.SecretKeyFactorySpi
    protected SecretKey engineGenerateSecret(KeySpec keySpec) throws InvalidKeySpecException {
        if (!(keySpec instanceof PBEKeySpec)) {
            throw new InvalidKeySpecException("Only PBEKeySpec allowed.");
        }
        PBEKey pBEKey = new PBEKey((PBEKeySpec) keySpec);
        pBEKey.setAlgorithm(this.b);
        return pBEKey;
    }

    @Override // javax.crypto.SecretKeyFactorySpi
    protected KeySpec engineGetKeySpec(SecretKey secretKey, Class cls) throws InvalidKeySpecException {
        if (!(secretKey instanceof PBEKey)) {
            throw new InvalidKeySpecException("Only PBEKeys can be converted.");
        }
        Class clsClass$ = a;
        if (clsClass$ == null) {
            clsClass$ = class$("javax.crypto.spec.PBEKeySpec");
            a = clsClass$;
        }
        if (clsClass$.isAssignableFrom(cls)) {
            return new PBEKeySpec(((PBEKey) secretKey).getKey());
        }
        throw new InvalidKeySpecException("Can't convert key to KeySpec.");
    }

    @Override // javax.crypto.SecretKeyFactorySpi
    protected SecretKey engineTranslateKey(SecretKey secretKey) throws InvalidKeyException {
        char[] charArray;
        if (secretKey == null) {
            throw new InvalidKeyException("Cannot translate a null key!");
        }
        try {
            charArray = UTF8String.getCharsFromUTF8Encoding(secretKey.getEncoded());
        } catch (Exception unused) {
            charArray = new String(secretKey.getEncoded()).toCharArray();
        }
        return new PBEKey(charArray);
    }
}
