package iaik.security.cipher;

import iaik.security.pbe.PBEGenParameterSpec;
import iaik.security.spec.PBEKeyAndParameterSpec;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class PbeWithSHAAnd40BitRC2_CBC extends RC2 {
    static Class j;
    protected AlgorithmParameters params;

    public PbeWithSHAAnd40BitRC2_CBC() throws NoSuchPaddingException, NoSuchAlgorithmException {
        super.engineSetMode("CBC");
        try {
            this.params = AlgorithmParameters.getInstance("PBE", "IAIK");
        } catch (Exception unused) {
            this.params = AlgorithmParameters.getInstance("PBE");
        }
    }

    private AlgorithmParameterGenerator a(String str) throws NoSuchAlgorithmException {
        try {
            return AlgorithmParameterGenerator.getInstance(str, "IAIK");
        } catch (Exception unused) {
            return AlgorithmParameterGenerator.getInstance("PBE");
        }
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    protected int engineGetKeySize(Key key) throws InvalidKeyException {
        return 40;
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public AlgorithmParameters engineGetParameters() {
        return this.params;
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameters == null || algorithmParameters.getAlgorithm() != "PBE") {
            throw new InvalidAlgorithmParameterException("Parameters must be of type PBEParameters.");
        }
        this.params = algorithmParameters;
        initCipher(i, key, secureRandom);
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineInit(int i, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        try {
            AlgorithmParameterGenerator algorithmParameterGeneratorA = a("PBE");
            algorithmParameterGeneratorA.init(8, secureRandom);
            this.params = algorithmParameterGeneratorA.generateParameters();
            initCipher(i, key, secureRandom);
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidKeyException(e.getMessage());
        } catch (NoSuchAlgorithmException e2) {
            throw new InvalidKeyException(e2.getMessage());
        }
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameterSpec == null) {
            engineInit(i, key, secureRandom);
            return;
        }
        if (algorithmParameterSpec instanceof PBEParameterSpec) {
            try {
                this.params.init(algorithmParameterSpec);
            } catch (InvalidParameterSpecException unused) {
                throw new InvalidAlgorithmParameterException("Error setting algorithm parameters.");
            }
        } else {
            if (!(algorithmParameterSpec instanceof PBEGenParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Parameters must be an instance of PBEParameterSpec or PBEGenParameterSpec.");
            }
            try {
                AlgorithmParameterGenerator algorithmParameterGeneratorA = a("PBE");
                algorithmParameterGeneratorA.init(algorithmParameterSpec, secureRandom);
                this.params = algorithmParameterGeneratorA.generateParameters();
            } catch (NoSuchAlgorithmException unused2) {
                throw new InvalidAlgorithmParameterException("Error setting algorithm parameters.");
            }
        }
        initCipher(i, key, secureRandom);
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineSetMode(String str) throws NoSuchAlgorithmException {
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineSetPadding(String str) throws NoSuchPaddingException {
    }

    protected void initCipher(int i, Key key, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (key == null || !key.getAlgorithm().equals("PBE") || !key.getFormat().equals("RAW_BMP")) {
            throw new InvalidKeyException("Must be a PBEKey in RAW_BMP format.");
        }
        byte[] encoded = key.getEncoded();
        try {
            AlgorithmParameters algorithmParameters = this.params;
            Class clsClass$ = j;
            if (clsClass$ == null) {
                clsClass$ = class$("javax.crypto.spec.PBEParameterSpec");
                j = clsClass$;
            }
            PBEParameterSpec pBEParameterSpec = (PBEParameterSpec) algorithmParameters.getParameterSpec(clsClass$);
            PBEKeyAndParameterSpec pBEKeyAndParameterSpec = new PBEKeyAndParameterSpec(encoded, pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount(), 5);
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("PKCS#12", "IAIK");
                keyGenerator.init(pBEKeyAndParameterSpec, secureRandom);
                SecretKey secretKey = (SecretKey) keyGenerator.generateKey();
                secretKey.setAlgorithm("RC2");
                KeyGenerator keyGenerator2 = KeyGenerator.getInstance("PKCS#12-IV", "IAIK");
                keyGenerator2.init(new PBEKeyAndParameterSpec(encoded, pBEParameterSpec.getSalt(), pBEParameterSpec.getIterationCount(), 8), secureRandom);
                super.engineInit(i, secretKey, new IvParameterSpec(((SecretKey) keyGenerator2.generateKey()).getEncoded()), secureRandom);
            } catch (NoSuchAlgorithmException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Algorithm not available: ");
                stringBuffer.append(e.getMessage());
                throw new InvalidKeyException(stringBuffer.toString());
            } catch (NoSuchProviderException unused) {
                throw new InvalidKeyException("Provider IAIK not installed!");
            }
        } catch (InvalidParameterSpecException e2) {
            throw new InvalidAlgorithmParameterException(e2.getMessage());
        }
    }
}
