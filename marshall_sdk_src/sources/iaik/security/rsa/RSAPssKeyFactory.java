package iaik.security.rsa;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/* JADX INFO: loaded from: classes.dex */
public final class RSAPssKeyFactory extends KeyFactorySpi {
    static Class a;
    static Class b;

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // java.security.KeyFactorySpi
    protected PrivateKey engineGeneratePrivate(KeySpec keySpec) throws InvalidKeySpecException {
        try {
            if (keySpec instanceof PKCS8EncodedKeySpec) {
                return new RSAPssPrivateKey(((PKCS8EncodedKeySpec) keySpec).getEncoded());
            }
            throw new InvalidKeySpecException("Only PKCS8EncodedKeySpec allowed.");
        } catch (InvalidKeyException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Parsing error: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeySpecException(stringBuffer.toString());
        }
    }

    @Override // java.security.KeyFactorySpi
    protected PublicKey engineGeneratePublic(KeySpec keySpec) throws InvalidKeySpecException {
        try {
            if (keySpec instanceof X509EncodedKeySpec) {
                return new RSAPssPublicKey(((X509EncodedKeySpec) keySpec).getEncoded());
            }
            throw new InvalidKeySpecException("Only X509EncodedKeySpec allowed.");
        } catch (InvalidKeyException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Parsing error: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeySpecException(stringBuffer.toString());
        }
    }

    @Override // java.security.KeyFactorySpi
    protected KeySpec engineGetKeySpec(Key key, Class cls) throws InvalidKeySpecException {
        if (key instanceof RSAPssPublicKey) {
            Class clsClass$ = a;
            if (clsClass$ == null) {
                clsClass$ = class$("java.security.spec.X509EncodedKeySpec");
                a = clsClass$;
            }
            if (clsClass$.isAssignableFrom(cls)) {
                return new X509EncodedKeySpec(key.getEncoded());
            }
            throw new InvalidKeySpecException("Only can convert public RSA-PSS key to X509EncodedKeySpec.");
        }
        if (!(key instanceof RSAPssPrivateKey)) {
            throw new InvalidKeySpecException("Only can convert RSA-PSS keys.");
        }
        Class clsClass$2 = b;
        if (clsClass$2 == null) {
            clsClass$2 = class$("java.security.spec.PKCS8EncodedKeySpec");
            b = clsClass$2;
        }
        if (clsClass$2.isAssignableFrom(cls)) {
            return new PKCS8EncodedKeySpec(key.getEncoded());
        }
        throw new InvalidKeySpecException("Only can convert private RSA-PSS key to PKCS8EncodedKeySpec.");
    }

    @Override // java.security.KeyFactorySpi
    protected Key engineTranslateKey(Key key) throws InvalidKeyException {
        if (key instanceof java.security.interfaces.RSAPublicKey) {
            return new RSAPssPublicKey(key.getEncoded());
        }
        if (key instanceof java.security.interfaces.RSAPrivateKey) {
            return new RSAPssPrivateKey(key.getEncoded());
        }
        throw new InvalidKeyException("Only keys of type RSAPublicKey and RSAPrivateKey can be translated.");
    }
}
