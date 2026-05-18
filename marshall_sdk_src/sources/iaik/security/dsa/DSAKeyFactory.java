package iaik.security.dsa;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/* JADX INFO: loaded from: classes.dex */
public final class DSAKeyFactory extends KeyFactorySpi {
    static Class a;
    static Class b;
    static Class c;
    static Class d;

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
            if (keySpec instanceof DSAPrivateKeySpec) {
                return new DSAPrivateKey((DSAPrivateKeySpec) keySpec);
            }
            if (keySpec instanceof PKCS8EncodedKeySpec) {
                return new DSAPrivateKey(((PKCS8EncodedKeySpec) keySpec).getEncoded());
            }
            throw new InvalidKeySpecException("Only DSA key specs allowed.");
        } catch (InvalidKeyException unused) {
            throw new InvalidKeySpecException("Invalid KeySpec.");
        }
    }

    @Override // java.security.KeyFactorySpi
    protected PublicKey engineGeneratePublic(KeySpec keySpec) throws InvalidKeySpecException {
        try {
            if (keySpec instanceof DSAPublicKeySpec) {
                return new DSAPublicKey((DSAPublicKeySpec) keySpec);
            }
            if (keySpec instanceof X509EncodedKeySpec) {
                return new DSAPublicKey(((X509EncodedKeySpec) keySpec).getEncoded());
            }
            throw new InvalidKeySpecException("Only DSA key specs allowed.");
        } catch (InvalidKeyException unused) {
            throw new InvalidKeySpecException("Invalid KeySpec.");
        }
    }

    @Override // java.security.KeyFactorySpi
    protected KeySpec engineGetKeySpec(Key key, Class cls) throws InvalidKeySpecException {
        if (key instanceof java.security.interfaces.DSAPublicKey) {
            Class clsClass$ = a;
            if (clsClass$ == null) {
                clsClass$ = class$("java.security.spec.DSAPublicKeySpec");
                a = clsClass$;
            }
            if (clsClass$.isAssignableFrom(cls)) {
                java.security.interfaces.DSAPublicKey dSAPublicKey = (java.security.interfaces.DSAPublicKey) key;
                java.security.interfaces.DSAParams params = dSAPublicKey.getParams();
                return new DSAPublicKeySpec(dSAPublicKey.getY(), params.getP(), params.getQ(), params.getG());
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
        if (!(key instanceof java.security.interfaces.DSAPrivateKey)) {
            throw new InvalidKeySpecException("Can only convert DSA keys.");
        }
        java.security.interfaces.DSAPrivateKey dSAPrivateKey = (java.security.interfaces.DSAPrivateKey) key;
        Class clsClass$3 = c;
        if (clsClass$3 == null) {
            clsClass$3 = class$("java.security.spec.DSAPrivateKeySpec");
            c = clsClass$3;
        }
        if (clsClass$3.isAssignableFrom(cls)) {
            java.security.interfaces.DSAParams params2 = dSAPrivateKey.getParams();
            return new DSAPrivateKeySpec(dSAPrivateKey.getX(), params2.getP(), params2.getQ(), params2.getG());
        }
        Class clsClass$4 = d;
        if (clsClass$4 == null) {
            clsClass$4 = class$("java.security.spec.PKCS8EncodedKeySpec");
            d = clsClass$4;
        }
        if (clsClass$4.isAssignableFrom(cls)) {
            return new PKCS8EncodedKeySpec(dSAPrivateKey.getEncoded());
        }
        throw new InvalidKeySpecException("Can't convert key to KeySpec.");
    }

    @Override // java.security.KeyFactorySpi
    protected Key engineTranslateKey(Key key) throws InvalidKeyException {
        if (key instanceof java.security.interfaces.DSAPublicKey) {
            java.security.interfaces.DSAPublicKey dSAPublicKey = (java.security.interfaces.DSAPublicKey) key;
            return new DSAPublicKey(dSAPublicKey.getY(), dSAPublicKey.getParams());
        }
        if (!(key instanceof java.security.interfaces.DSAPrivateKey)) {
            throw new InvalidKeyException("Only keys of type DSAPublicKey and DSAPrivateKey can be translated.");
        }
        java.security.interfaces.DSAPrivateKey dSAPrivateKey = (java.security.interfaces.DSAPrivateKey) key;
        return new DSAPrivateKey(dSAPrivateKey.getX(), dSAPrivateKey.getParams());
    }
}
