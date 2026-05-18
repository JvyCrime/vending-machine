package iaik.security.cipher;

import iaik.security.pbe.PBEGenParameterSpec;
import iaik.security.ssl.SecurityProvider;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class PbeWithMD5AndDES_CBC extends DES {
    static Class j;
    protected AlgorithmParameters params;

    public PbeWithMD5AndDES_CBC() throws NoSuchPaddingException, NoSuchAlgorithmException {
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
        return 56;
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
        initCipher(i, key);
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineInit(int i, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        try {
            AlgorithmParameterGenerator algorithmParameterGeneratorA = a("PBE");
            algorithmParameterGeneratorA.init(8, secureRandom);
            this.params = algorithmParameterGeneratorA.generateParameters();
            initCipher(i, key);
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
        initCipher(i, key);
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineSetMode(String str) {
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineSetPadding(String str) {
    }

    protected void initCipher(int i, Key key) throws InvalidKeyException, InvalidAlgorithmParameterException {
        MessageDigest messageDigest;
        if (key == null || !key.getAlgorithm().equals("PBE") || !key.getFormat().equals("RAW")) {
            throw new InvalidKeyException("Must be a PBEKey in RAW format.");
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
            byte[] salt = pBEParameterSpec.getSalt();
            int iterationCount = pBEParameterSpec.getIterationCount();
            if (salt.length != 8) {
                throw new InvalidAlgorithmParameterException("Salt must be 8 bytes long.");
            }
            byte[] bArrDigest = new byte[encoded.length + salt.length];
            System.arraycopy(encoded, 0, bArrDigest, 0, encoded.length);
            System.arraycopy(salt, 0, bArrDigest, encoded.length, salt.length);
            try {
                try {
                    messageDigest = MessageDigest.getInstance(SecurityProvider.ALG_DIGEST_MD5, "IAIK");
                } catch (Exception unused) {
                    messageDigest = MessageDigest.getInstance(SecurityProvider.ALG_DIGEST_MD5);
                }
                for (int i2 = 0; i2 < iterationCount; i2++) {
                    bArrDigest = messageDigest.digest(bArrDigest);
                }
                DESKeyGenerator.adjustParity(bArrDigest, 0);
                super.engineInit(i, new SecretKey(bArrDigest, 0, 8, "DES"), new IvParameterSpec(bArrDigest, 8, 8), (SecureRandom) null);
            } catch (NoSuchAlgorithmException e) {
                throw new InvalidKeyException(e.toString());
            }
        } catch (InvalidParameterSpecException e2) {
            throw new InvalidAlgorithmParameterException(e2.getMessage());
        }
    }
}
