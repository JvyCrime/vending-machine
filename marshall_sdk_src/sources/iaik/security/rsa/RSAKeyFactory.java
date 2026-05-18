package iaik.security.rsa;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

/* JADX INFO: loaded from: classes.dex */
public final class RSAKeyFactory extends KeyFactorySpi {
    static Class a;
    static Class b;
    static Class c;
    static Class d;
    static Class e;

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e2) {
            throw new NoClassDefFoundError(e2.getMessage());
        }
    }

    @Override // java.security.KeyFactorySpi
    protected PrivateKey engineGeneratePrivate(KeySpec keySpec) throws InvalidKeySpecException {
        try {
            if (keySpec instanceof RSAPrivateCrtKeySpec) {
                return new RSAPrivateKey((RSAPrivateCrtKeySpec) keySpec);
            }
            if (keySpec instanceof RSAPrivateKeySpec) {
                return new RSAPrivateKey((RSAPrivateKeySpec) keySpec);
            }
            if (keySpec instanceof PKCS8EncodedKeySpec) {
                return new RSAPrivateKey(((PKCS8EncodedKeySpec) keySpec).getEncoded());
            }
            throw new InvalidKeySpecException("Only RSA key specs allowed.");
        } catch (InvalidKeyException unused) {
            throw new InvalidKeySpecException("Invalid KeySpec.");
        }
    }

    @Override // java.security.KeyFactorySpi
    protected PublicKey engineGeneratePublic(KeySpec keySpec) throws InvalidKeySpecException {
        try {
            if (keySpec instanceof RSAPublicKeySpec) {
                return new RSAPublicKey((RSAPublicKeySpec) keySpec);
            }
            if (keySpec instanceof X509EncodedKeySpec) {
                return new RSAPublicKey(((X509EncodedKeySpec) keySpec).getEncoded());
            }
            throw new InvalidKeySpecException("Only RSA key specs allowed.");
        } catch (InvalidKeyException unused) {
            throw new InvalidKeySpecException("Invalid KeySpec.");
        }
    }

    @Override // java.security.KeyFactorySpi
    protected KeySpec engineGetKeySpec(Key key, Class cls) throws InvalidKeySpecException {
        if (key instanceof java.security.interfaces.RSAPublicKey) {
            Class clsClass$ = a;
            if (clsClass$ == null) {
                clsClass$ = class$("java.security.spec.RSAPublicKeySpec");
                a = clsClass$;
            }
            if (clsClass$.isAssignableFrom(cls)) {
                java.security.interfaces.RSAPublicKey rSAPublicKey = (java.security.interfaces.RSAPublicKey) key;
                return new RSAPublicKeySpec(rSAPublicKey.getModulus(), rSAPublicKey.getPublicExponent());
            }
            Class clsClass$2 = b;
            if (clsClass$2 == null) {
                clsClass$2 = class$("java.security.spec.X509EncodedKeySpec");
                b = clsClass$2;
            }
            if (clsClass$2.isAssignableFrom(cls)) {
                return new X509EncodedKeySpec(key.getEncoded());
            }
            throw new InvalidKeySpecException("Can't convert key to KeySpec.");
        }
        if (!(key instanceof java.security.interfaces.RSAPrivateKey)) {
            throw new InvalidKeySpecException("Can only convert RSA keys.");
        }
        java.security.interfaces.RSAPrivateKey rSAPrivateKey = (java.security.interfaces.RSAPrivateKey) key;
        Class clsClass$3 = c;
        if (clsClass$3 == null) {
            clsClass$3 = class$("java.security.spec.RSAPrivateCrtKeySpec");
            c = clsClass$3;
        }
        if (clsClass$3.isAssignableFrom(cls) && (key instanceof RSAPrivateCrtKey)) {
            RSAPrivateCrtKey rSAPrivateCrtKey = (RSAPrivateCrtKey) key;
            return new RSAPrivateCrtKeySpec(rSAPrivateCrtKey.getModulus(), rSAPrivateCrtKey.getPublicExponent(), rSAPrivateCrtKey.getPrivateExponent(), rSAPrivateCrtKey.getPrimeP(), rSAPrivateCrtKey.getPrimeQ(), rSAPrivateCrtKey.getPrimeExponentP(), rSAPrivateCrtKey.getPrimeExponentQ(), rSAPrivateCrtKey.getCrtCoefficient());
        }
        Class clsClass$4 = d;
        if (clsClass$4 == null) {
            clsClass$4 = class$("java.security.spec.RSAPrivateKeySpec");
            d = clsClass$4;
        }
        if (clsClass$4.isAssignableFrom(cls)) {
            return new RSAPrivateKeySpec(rSAPrivateKey.getModulus(), rSAPrivateKey.getPrivateExponent());
        }
        Class clsClass$5 = e;
        if (clsClass$5 == null) {
            clsClass$5 = class$("java.security.spec.PKCS8EncodedKeySpec");
            e = clsClass$5;
        }
        if (clsClass$5.isAssignableFrom(cls)) {
            return new PKCS8EncodedKeySpec(rSAPrivateKey.getEncoded());
        }
        throw new InvalidKeySpecException("Can't convert key to KeySpec.");
    }

    @Override // java.security.KeyFactorySpi
    protected Key engineTranslateKey(Key key) throws InvalidKeyException {
        if (key instanceof java.security.interfaces.RSAPublicKey) {
            java.security.interfaces.RSAPublicKey rSAPublicKey = (java.security.interfaces.RSAPublicKey) key;
            return new RSAPublicKey(rSAPublicKey.getModulus(), rSAPublicKey.getPublicExponent());
        }
        if (!(key instanceof java.security.interfaces.RSAPrivateKey)) {
            throw new InvalidKeyException("Only keys of type RSAPublicKey and RSAPrivateKey can be translated.");
        }
        java.security.interfaces.RSAPrivateKey rSAPrivateKey = (java.security.interfaces.RSAPrivateKey) key;
        BigInteger modulus = rSAPrivateKey.getModulus();
        BigInteger privateExponent = rSAPrivateKey.getPrivateExponent();
        if (!(key instanceof RSAPrivateCrtKey)) {
            return new RSAPrivateKey(modulus, privateExponent);
        }
        RSAPrivateCrtKey rSAPrivateCrtKey = (RSAPrivateCrtKey) key;
        return new RSAPrivateKey(modulus, rSAPrivateCrtKey.getPublicExponent(), privateExponent, rSAPrivateCrtKey.getPrimeP(), rSAPrivateCrtKey.getPrimeQ(), rSAPrivateCrtKey.getPrimeExponentP(), rSAPrivateCrtKey.getPrimeExponentQ(), rSAPrivateCrtKey.getCrtCoefficient());
    }
}
