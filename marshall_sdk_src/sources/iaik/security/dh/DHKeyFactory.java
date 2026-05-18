package iaik.security.dh;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactorySpi;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPrivateKeySpec;
import javax.crypto.spec.DHPublicKeySpec;

/* JADX INFO: loaded from: classes.dex */
public final class DHKeyFactory extends KeyFactorySpi {
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
            if (keySpec instanceof DHPrivateKeySpec) {
                return new DHPrivateKey((DHPrivateKeySpec) keySpec);
            }
            if (keySpec instanceof PKCS8EncodedKeySpec) {
                return new DHPrivateKey(((PKCS8EncodedKeySpec) keySpec).getEncoded());
            }
            throw new InvalidKeySpecException("Only DH key specs allowed.");
        } catch (InvalidKeyException unused) {
            throw new InvalidKeySpecException("Invalid KeySpec.");
        }
    }

    @Override // java.security.KeyFactorySpi
    protected PublicKey engineGeneratePublic(KeySpec keySpec) throws InvalidKeySpecException {
        try {
            if (keySpec instanceof DHPublicKeySpec) {
                return new DHPublicKey((DHPublicKeySpec) keySpec);
            }
            if (keySpec instanceof X509EncodedKeySpec) {
                return new DHPublicKey(((X509EncodedKeySpec) keySpec).getEncoded());
            }
            throw new InvalidKeySpecException("Only DH key specs allowed.");
        } catch (InvalidKeyException unused) {
            throw new InvalidKeySpecException("Invalid KeySpec.");
        }
    }

    @Override // java.security.KeyFactorySpi
    protected KeySpec engineGetKeySpec(Key key, Class cls) throws InvalidKeySpecException {
        if (key instanceof javax.crypto.interfaces.DHPublicKey) {
            Class clsClass$ = a;
            if (clsClass$ == null) {
                clsClass$ = class$("javax.crypto.spec.DHPublicKeySpec");
                a = clsClass$;
            }
            if (clsClass$.isAssignableFrom(cls)) {
                javax.crypto.interfaces.DHPublicKey dHPublicKey = (javax.crypto.interfaces.DHPublicKey) key;
                DHParameterSpec params = dHPublicKey.getParams();
                return new DHPublicKeySpec(dHPublicKey.getY(), params.getP(), params.getG());
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
        if (!(key instanceof javax.crypto.interfaces.DHPrivateKey)) {
            throw new InvalidKeySpecException("Can only convert DH keys.");
        }
        javax.crypto.interfaces.DHPrivateKey dHPrivateKey = (javax.crypto.interfaces.DHPrivateKey) key;
        Class clsClass$3 = c;
        if (clsClass$3 == null) {
            clsClass$3 = class$("javax.crypto.spec.DHPrivateKeySpec");
            c = clsClass$3;
        }
        if (clsClass$3.isAssignableFrom(cls)) {
            DHParameterSpec params2 = dHPrivateKey.getParams();
            return new DHPrivateKeySpec(dHPrivateKey.getX(), params2.getP(), params2.getG());
        }
        Class clsClass$4 = d;
        if (clsClass$4 == null) {
            clsClass$4 = class$("java.security.spec.PKCS8EncodedKeySpec");
            d = clsClass$4;
        }
        if (clsClass$4.isAssignableFrom(cls)) {
            return new PKCS8EncodedKeySpec(dHPrivateKey.getEncoded());
        }
        throw new InvalidKeySpecException("Can't convert key to KeySpec.");
    }

    @Override // java.security.KeyFactorySpi
    protected Key engineTranslateKey(Key key) throws InvalidKeyException {
        if (key instanceof javax.crypto.interfaces.DHPublicKey) {
            javax.crypto.interfaces.DHPublicKey dHPublicKey = (javax.crypto.interfaces.DHPublicKey) key;
            return new DHPublicKey(dHPublicKey.getY(), dHPublicKey.getParams());
        }
        if (!(key instanceof javax.crypto.interfaces.DHPrivateKey)) {
            throw new InvalidKeyException("Only keys of type DHPublicKey and DHPrivateKey can be translated.");
        }
        javax.crypto.interfaces.DHPrivateKey dHPrivateKey = (javax.crypto.interfaces.DHPrivateKey) key;
        return new DHPrivateKey(dHPrivateKey.getX(), dHPrivateKey.getParams());
    }
}
