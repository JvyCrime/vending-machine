package iaik.security.cipher;

import iaik.utils.IaikSecurity;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Locale;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.SecretKeyFactorySpi;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class GeneralKeyFactory extends SecretKeyFactorySpi {
    static Class a;
    static Class b;
    static Class c;

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // javax.crypto.SecretKeyFactorySpi
    protected javax.crypto.SecretKey engineGenerateSecret(KeySpec keySpec) throws InvalidKeySpecException {
        int iC;
        if (keySpec instanceof DESKeySpec) {
            return new SecretKey(((DESKeySpec) keySpec).getKey(), "DES");
        }
        if (keySpec instanceof DESedeKeySpec) {
            return new SecretKey(((DESedeKeySpec) keySpec).getKey(), "DESede");
        }
        try {
            if (!(keySpec instanceof SecretKeySpec)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Unsupported type of keyspec: ");
                stringBuffer.append(keySpec.getClass().getName());
                throw new InvalidKeySpecException(stringBuffer.toString());
            }
            SecretKeySpec secretKeySpec = (SecretKeySpec) keySpec;
            byte[] encoded = secretKeySpec.getEncoded();
            String algorithm = secretKeySpec.getAlgorithm();
            int length = encoded.length << 3;
            try {
                KeyGeneratorSpi keyGeneratorSpi = (KeyGeneratorSpi) new IaikSecurity(algorithm, "KeyGenerator", "IAIK").getImplementation();
                if (!(keyGeneratorSpi instanceof VarLengthKeyGenerator)) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Illegal key spec: ");
                    stringBuffer2.append(algorithm);
                    throw new InvalidKeySpecException(stringBuffer2.toString());
                }
                VarLengthKeyGenerator varLengthKeyGenerator = (VarLengthKeyGenerator) keyGeneratorSpi;
                int iA = varLengthKeyGenerator.a();
                if (iA <= 0 || length < iA) {
                    try {
                        varLengthKeyGenerator.engineInit(length, (SecureRandom) null);
                        iC = varLengthKeyGenerator.engineGenerateKey().getEncoded().length;
                    } catch (Exception unused) {
                        if (length < varLengthKeyGenerator.b()) {
                            throw new InvalidKeySpecException("Supplied key material too short!");
                        }
                        iC = (length >= varLengthKeyGenerator.c() ? varLengthKeyGenerator.c() : varLengthKeyGenerator.b()) >>> 3;
                    }
                } else {
                    iC = iA >> 3;
                }
                byte[] bArr = new byte[iC];
                System.arraycopy(encoded, 0, bArr, 0, encoded.length >= iC ? iC : encoded.length);
                String upperCase = algorithm.toUpperCase(Locale.US);
                if (upperCase.equals("DES") || upperCase.equals("3DES") || upperCase.equals("DESEDE")) {
                    for (int i = 0; i < iC; i += 8) {
                        DESKeyGenerator.adjustParity(bArr, i);
                    }
                }
                return new SecretKey(bArr, algorithm);
            } catch (NoSuchAlgorithmException unused2) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Illegal key spec: ");
                stringBuffer3.append(algorithm);
                throw new InvalidKeySpecException(stringBuffer3.toString());
            }
        } catch (Exception e) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("Could not convert key: ");
            stringBuffer4.append(e);
            throw new InvalidKeySpecException(stringBuffer4.toString());
        }
    }

    @Override // javax.crypto.SecretKeyFactorySpi
    protected KeySpec engineGetKeySpec(javax.crypto.SecretKey secretKey, Class cls) throws InvalidKeySpecException {
        try {
            Class clsClass$ = a;
            if (clsClass$ == null) {
                clsClass$ = class$("javax.crypto.spec.DESKeySpec");
                a = clsClass$;
            }
            if (clsClass$.isAssignableFrom(cls)) {
                return new DESKeySpec(secretKey.getEncoded());
            }
            Class clsClass$2 = b;
            if (clsClass$2 == null) {
                clsClass$2 = class$("javax.crypto.spec.DESedeKeySpec");
                b = clsClass$2;
            }
            if (clsClass$2.isAssignableFrom(cls)) {
                return new DESedeKeySpec(secretKey.getEncoded());
            }
            Class clsClass$3 = c;
            if (clsClass$3 == null) {
                clsClass$3 = class$("javax.crypto.spec.SecretKeySpec");
                c = clsClass$3;
            }
            if (clsClass$3.isAssignableFrom(cls)) {
                return new SecretKeySpec(secretKey.getEncoded(), secretKey.getAlgorithm());
            }
            throw new InvalidKeySpecException("Can't convert key to KeySpec.");
        } catch (InvalidKeyException unused) {
            throw new InvalidKeySpecException("Invalid KeySpec or key.");
        }
    }

    @Override // javax.crypto.SecretKeyFactorySpi
    protected javax.crypto.SecretKey engineTranslateKey(javax.crypto.SecretKey secretKey) throws InvalidKeyException {
        if (secretKey != null) {
            return new SecretKey(secretKey);
        }
        throw new InvalidKeyException("Cannot translate a null key!");
    }
}
