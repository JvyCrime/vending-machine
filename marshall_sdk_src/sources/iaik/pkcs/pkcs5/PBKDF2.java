package iaik.pkcs.pkcs5;

import iaik.asn1.structures.AlgorithmID;
import iaik.security.spec.PBEKeyAndParameterSpec;
import iaik.security.ssl.SecurityProvider;
import iaik.utils.InternalErrorException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.ProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class PBKDF2 extends KeyGeneratorSpi {
    static final AlgorithmID a = (AlgorithmID) AlgorithmID.hMAC_SHA1_.clone();
    private int b;
    private int c;
    private byte[] d;
    private byte[] e;
    private AlgorithmID f;

    public static final class PBKDF2WithHmacSHA1 extends PBKDF2 {
        public PBKDF2WithHmacSHA1() {
            super(AlgorithmID.hMAC_SHA1_);
        }
    }

    public static final class PBKDF2WithHmacSHA224 extends PBKDF2 {
        public PBKDF2WithHmacSHA224() {
            super(AlgorithmID.hMAC_SHA224);
        }
    }

    public static final class PBKDF2WithHmacSHA256 extends PBKDF2 {
        public PBKDF2WithHmacSHA256() {
            super(AlgorithmID.hMAC_SHA256);
        }
    }

    public static final class PBKDF2WithHmacSHA384 extends PBKDF2 {
        public PBKDF2WithHmacSHA384() {
            super(AlgorithmID.hMAC_SHA384);
        }
    }

    public static final class PBKDF2WithHmacSHA512 extends PBKDF2 {
        public PBKDF2WithHmacSHA512() {
            super(AlgorithmID.hMAC_SHA512);
        }
    }

    public PBKDF2() {
        this(a);
    }

    PBKDF2(AlgorithmID algorithmID) {
        this.b = 32;
        this.c = 2000;
        this.f = (AlgorithmID) algorithmID.clone();
    }

    private static final Mac a(AlgorithmID algorithmID) throws NoSuchAlgorithmException {
        try {
            return algorithmID.getMacInstance("IAIK");
        } catch (NoSuchAlgorithmException e) {
            try {
                return algorithmID.getMacInstance();
            } catch (NoSuchAlgorithmException unused) {
                throw e;
            }
        }
    }

    private byte[] a() {
        try {
            Mac macA = a(this.f);
            int macLength = macA.getMacLength();
            try {
                macA.init(new SecretKeySpec(this.e, macA.getAlgorithm()));
                int i = this.b;
                int i2 = i % macLength;
                int i3 = i2 == 0 ? i / macLength : ((i - i2) / macLength) + 1;
                byte[] bArr = new byte[macLength * i3];
                for (int i4 = 1; i4 <= i3; i4++) {
                    byte[] bArr2 = new byte[this.d.length + 4];
                    byte[] bArrA = a(i4);
                    byte[] bArr3 = this.d;
                    System.arraycopy(bArr3, 0, bArr2, 0, bArr3.length);
                    System.arraycopy(bArrA, 0, bArr2, this.d.length, 4);
                    byte[] bArrDoFinal = macA.doFinal(bArr2);
                    byte[] bArr4 = new byte[macLength];
                    System.arraycopy(bArrDoFinal, 0, bArr4, 0, bArrDoFinal.length);
                    for (int i5 = 1; i5 < this.c; i5++) {
                        bArrDoFinal = macA.doFinal(bArrDoFinal);
                        for (int i6 = 0; i6 < macLength; i6++) {
                            bArr4[i6] = (byte) (bArr4[i6] ^ bArrDoFinal[i6]);
                        }
                    }
                    System.arraycopy(bArr4, 0, bArr, (i4 - 1) * macLength, macLength);
                }
                int i7 = this.b;
                byte[] bArr5 = new byte[i7];
                System.arraycopy(bArr, 0, bArr5, 0, i7);
                return bArr5;
            } catch (InvalidKeyException e) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Mac initialization failed: ");
                stringBuffer.append(e.toString());
                throw new ProviderException(stringBuffer.toString());
            }
        } catch (NoSuchAlgorithmException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Cannot get Mac implementation: ");
            stringBuffer2.append(e2.toString());
            throw new InternalErrorException(stringBuffer2.toString());
        }
    }

    private byte[] a(int i) {
        byte[] bArr = new byte[4];
        for (int i2 = 3; i2 >= 0; i2--) {
            bArr[i2] = (byte) (i & 255);
            i >>>= 8;
        }
        return bArr;
    }

    @Override // javax.crypto.KeyGeneratorSpi
    public SecretKey engineGenerateKey() {
        if (this.e == null || this.d == null) {
            throw new IllegalStateException("PBKDF2 is not initialized yet. ");
        }
        return new iaik.security.cipher.SecretKey(a(), "RAW");
    }

    @Override // javax.crypto.KeyGeneratorSpi
    public void engineInit(int i, SecureRandom secureRandom) {
        throw new RuntimeException("iaik.pkcs.pkcs5.pBKDF2.engineInit(int, SecureRandom) method is not implemented. ");
    }

    @Override // javax.crypto.KeyGeneratorSpi
    public void engineInit(SecureRandom secureRandom) {
        throw new RuntimeException("iaik.pkcs.pkcs5.pBKDF2.engineInit(SecureRandom) method is not implemented. ");
    }

    @Override // javax.crypto.KeyGeneratorSpi
    public void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        AlgorithmID prf;
        if (!(algorithmParameterSpec instanceof PBEKeyAndParameterSpec)) {
            throw new InvalidAlgorithmParameterException("AlgorithmParameterSpec must be an instance of iaik.security.PBEKeyAndParameterSpec");
        }
        PBEKeyAndParameterSpec pBEKeyAndParameterSpec = (PBEKeyAndParameterSpec) algorithmParameterSpec;
        byte[] password = pBEKeyAndParameterSpec.getPassword();
        this.e = password;
        if (password == null) {
            throw new InvalidAlgorithmParameterException("PBEKeyAndParameterSpec does not contain a valid password. ");
        }
        byte[] salt = pBEKeyAndParameterSpec.getSalt();
        this.d = salt;
        if (salt == null) {
            throw new InvalidAlgorithmParameterException("PBEKeyAndParameterSpec does not contain a valid salt. ");
        }
        int iterationCount = pBEKeyAndParameterSpec.getIterationCount();
        if (iterationCount < 1) {
            throw new InvalidAlgorithmParameterException("PBEKeyAndParameterSpec does not contain a valid iterationCount. ");
        }
        this.c = iterationCount;
        int derivedKeyLength = pBEKeyAndParameterSpec.getDerivedKeyLength();
        if (derivedKeyLength < 1) {
            throw new InvalidAlgorithmParameterException("PBEKeyAndParameterSpec does not contain a valid dKLength. ");
        }
        this.b = derivedKeyLength;
        if (!(algorithmParameterSpec instanceof PBKDF2KeyAndParameterSpec) || (prf = ((PBKDF2KeyAndParameterSpec) algorithmParameterSpec).getPrf()) == null) {
            return;
        }
        if (!getClass().getName().endsWith(SecurityProvider.ALG_KEYGEN_PBKDF2) && !prf.equals(this.f)) {
            if (!a.equals(prf)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Illegal prf (");
                stringBuffer.append(prf.getAlgorithm().getName());
                stringBuffer.append("). Only ");
                stringBuffer.append(this.f.getAlgorithm().getName());
                stringBuffer.append(" allowed by this engine!");
                throw new InvalidAlgorithmParameterException(stringBuffer.toString());
            }
            prf = this.f;
        }
        try {
            a(prf);
            this.f = (AlgorithmID) prf.clone();
        } catch (NoSuchAlgorithmException e) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Cannot get Mac implementation: ");
            stringBuffer2.append(e.toString());
            throw new InvalidAlgorithmParameterException(stringBuffer2.toString());
        }
    }
}
