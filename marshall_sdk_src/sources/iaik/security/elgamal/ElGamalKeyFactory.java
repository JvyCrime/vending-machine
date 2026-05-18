package iaik.security.elgamal;

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
public final class ElGamalKeyFactory extends KeyFactorySpi {
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
            if (keySpec instanceof ElGamalPrivateKeySpec) {
                return new ElGamalPrivateKey((ElGamalPrivateKeySpec) keySpec);
            }
            if (keySpec instanceof PKCS8EncodedKeySpec) {
                return new ElGamalPrivateKey(((PKCS8EncodedKeySpec) keySpec).getEncoded());
            }
            throw new InvalidKeySpecException("Only ElGamal key specs allowed.");
        } catch (InvalidKeyException unused) {
            throw new InvalidKeySpecException("Invalid KeySpec.");
        }
    }

    @Override // java.security.KeyFactorySpi
    protected PublicKey engineGeneratePublic(KeySpec keySpec) throws InvalidKeySpecException {
        try {
            if (keySpec instanceof ElGamalPublicKeySpec) {
                return new ElGamalPublicKey((ElGamalPublicKeySpec) keySpec);
            }
            if (keySpec instanceof X509EncodedKeySpec) {
                return new ElGamalPublicKey(((X509EncodedKeySpec) keySpec).getEncoded());
            }
            throw new InvalidKeySpecException("Only ElGamal key specs allowed.");
        } catch (InvalidKeyException unused) {
            throw new InvalidKeySpecException("Invalid KeySpec.");
        }
    }

    @Override // java.security.KeyFactorySpi
    protected KeySpec engineGetKeySpec(Key key, Class cls) throws InvalidKeySpecException {
        if (key instanceof ElGamalPublicKey) {
            Class clsClass$ = a;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.security.elgamal.ElGamalPublicKeySpec");
                a = clsClass$;
            }
            if (clsClass$.isAssignableFrom(cls)) {
                ElGamalPublicKey elGamalPublicKey = (ElGamalPublicKey) key;
                ElGamalParameterSpec params = elGamalPublicKey.getParams();
                return new ElGamalPublicKeySpec(elGamalPublicKey.getY(), params.getP(), params.getG());
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
        if (!(key instanceof ElGamalPrivateKey)) {
            throw new InvalidKeySpecException("Can only convert ElGamal keys.");
        }
        ElGamalPrivateKey elGamalPrivateKey = (ElGamalPrivateKey) key;
        Class clsClass$3 = c;
        if (clsClass$3 == null) {
            clsClass$3 = class$("iaik.security.elgamal.ElGamalPrivateKeySpec");
            c = clsClass$3;
        }
        if (clsClass$3.isAssignableFrom(cls)) {
            ElGamalParameterSpec params2 = elGamalPrivateKey.getParams();
            return new ElGamalPrivateKeySpec(elGamalPrivateKey.getX(), params2.getP(), params2.getG());
        }
        Class clsClass$4 = d;
        if (clsClass$4 == null) {
            clsClass$4 = class$("java.security.spec.PKCS8EncodedKeySpec");
            d = clsClass$4;
        }
        if (clsClass$4.isAssignableFrom(cls)) {
            return new PKCS8EncodedKeySpec(elGamalPrivateKey.getEncoded());
        }
        throw new InvalidKeySpecException("Can't convert key to KeySpec.");
    }

    @Override // java.security.KeyFactorySpi
    protected Key engineTranslateKey(Key key) throws InvalidKeyException {
        if (key instanceof ElGamalPublicKey) {
            ElGamalPublicKey elGamalPublicKey = (ElGamalPublicKey) key;
            return new ElGamalPublicKey(elGamalPublicKey.getY(), elGamalPublicKey.getParams());
        }
        if (!(key instanceof ElGamalPrivateKey)) {
            throw new InvalidKeyException("Only keys of type ElGamalPublicKey and ElGamalPrivateKey can be translated.");
        }
        ElGamalPrivateKey elGamalPrivateKey = (ElGamalPrivateKey) key;
        return new ElGamalPrivateKey(elGamalPrivateKey.getX(), elGamalPrivateKey.getParams());
    }
}
