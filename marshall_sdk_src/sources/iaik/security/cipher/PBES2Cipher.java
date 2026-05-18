package iaik.security.cipher;

import iaik.asn1.structures.AlgorithmID;
import iaik.pkcs.pkcs5.PBES2ParameterSpec;
import iaik.pkcs.pkcs5.PBKDF2KeyAndParameterSpec;
import iaik.pkcs.pkcs5.PBKDF2ParameterSpec;
import iaik.security.random.SecRandom;
import iaik.security.ssl.SecurityProvider;
import iaik.utils.CriticalObject;
import iaik.utils.InternalErrorException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class PBES2Cipher extends AbstractC0022a {
    static Class k;
    static Class l;
    static Class m;
    static Class n;
    private PBES2ParameterSpec p;
    private PBES2ParameterSpec q;
    private AlgorithmID r;
    private int s;
    private AlgorithmID t;
    static final AlgorithmID j = (AlgorithmID) AlgorithmID.hMAC_SHA1_.clone();
    private static final AlgorithmID o = (AlgorithmID) AlgorithmID.aes128_CBC.clone();

    public static final class PBES2WithHmacSHA1AndAES extends PBES2Cipher {
        public PBES2WithHmacSHA1AndAES() {
            super(new G(), 16, AlgorithmID.hMAC_SHA1_, AlgorithmID.aes128_CBC);
        }
    }

    public static final class PBES2WithHmacSHA1AndDESede extends PBES2Cipher {
        public PBES2WithHmacSHA1AndDESede() {
            super(new J(), 24, AlgorithmID.hMAC_SHA1, AlgorithmID.des_EDE3_CBC);
        }
    }

    public static final class PBES2WithHmacSHA256AndAES extends PBES2Cipher {
        public PBES2WithHmacSHA256AndAES() {
            super(new G(), 16, AlgorithmID.hMAC_SHA256, AlgorithmID.aes128_CBC);
        }
    }

    public static final class PBES2WithHmacSHA384AndAES192 extends PBES2Cipher {
        public PBES2WithHmacSHA384AndAES192() {
            super(new G(), 24, AlgorithmID.hMAC_SHA384, AlgorithmID.aes192_CBC);
        }
    }

    public static final class PBES2WithHmacSHA512AndAES256 extends PBES2Cipher {
        public PBES2WithHmacSHA512AndAES256() {
            super(new G(), 32, AlgorithmID.hMAC_SHA512, AlgorithmID.aes256_CBC);
        }
    }

    public PBES2Cipher() {
    }

    PBES2Cipher(t tVar, int i, AlgorithmID algorithmID, AlgorithmID algorithmID2) {
        super(tVar);
        this.s = i;
        this.t = (AlgorithmID) algorithmID.clone();
        this.r = (AlgorithmID) algorithmID2.clone();
    }

    private String a(AlgorithmID algorithmID) throws NoSuchPaddingException, NoSuchAlgorithmException {
        String[] strArrA = a(algorithmID.getImplementationName());
        String str = strArrA[0];
        if (str == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Encryption scheme ");
            stringBuffer.append(algorithmID.getName());
            stringBuffer.append(" not supported!");
            throw new NoSuchAlgorithmException(stringBuffer.toString());
        }
        if (this.a == null) {
            a(b(str));
        }
        String str2 = strArrA[1];
        String str3 = strArrA[2];
        engineSetMode(str2);
        engineSetPadding(str3);
        return str;
    }

    private void a(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (!(key instanceof PBEKey)) {
            throw new InvalidKeyException("Must be a PBEKey in RAW format (iaik.security.cipher.PBEKey).");
        }
        if (secureRandom == null) {
            secureRandom = SecRandom.getDefault();
        }
        a(i, algorithmParameterSpec, secureRandom);
        AlgorithmID encryptionScheme = this.p.getEncryptionScheme();
        try {
            String strA = a(encryptionScheme);
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(SecurityProvider.ALG_KEYGEN_PBKDF2, "IAIK");
                byte[] encoded = key.getEncoded();
                byte[] salt = this.p.getSalt();
                int iterationCount = this.p.getIterationCount();
                int derivedKeyLength = this.p.getDerivedKeyLength();
                AlgorithmID prf = this.p.getPrf();
                try {
                    PBKDF2KeyAndParameterSpec pBKDF2KeyAndParameterSpec = new PBKDF2KeyAndParameterSpec(encoded, salt, iterationCount, derivedKeyLength);
                    pBKDF2KeyAndParameterSpec.setPrf(prf);
                    keyGenerator.init(pBKDF2KeyAndParameterSpec, secureRandom);
                    try {
                        javax.crypto.SecretKey secretKeyGenerateSecret = SecretKeyFactory.getInstance(strA, "IAIK").generateSecret(new SecretKeySpec(keyGenerator.generateKey().getEncoded(), strA));
                        try {
                            AlgorithmParameterSpec encryptionSchemeParameters = this.q.getEncryptionSchemeParameters();
                            if (encryptionSchemeParameters == null) {
                                super.engineInit(i, secretKeyGenerateSecret, secureRandom);
                            } else {
                                super.engineInit(i, secretKeyGenerateSecret, encryptionSchemeParameters, secureRandom);
                            }
                            AlgorithmParameters algorithmParametersEngineGetParameters = super.engineGetParameters();
                            if (algorithmParametersEngineGetParameters != null) {
                                encryptionScheme.setAlgorithmParameters(algorithmParametersEngineGetParameters);
                                if (encryptionSchemeParameters == null) {
                                    try {
                                        encryptionSchemeParameters = encryptionScheme.getAlgorithmParameterSpec();
                                    } catch (Exception unused) {
                                    }
                                }
                                Class<?> cls = encryptionSchemeParameters != null ? encryptionSchemeParameters.getClass() : null;
                                if (cls != null) {
                                    this.p.setEncryptionSchemeParameters(algorithmParametersEngineGetParameters.getParameterSpec(cls));
                                }
                            }
                        } catch (Exception e) {
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append("Error parsing encryption scheme parameters: ");
                            stringBuffer.append(e.toString());
                            throw new InvalidAlgorithmParameterException(stringBuffer.toString());
                        }
                    } catch (Exception e2) {
                        StringBuffer stringBuffer2 = new StringBuffer();
                        stringBuffer2.append("Error creating encryption scheme key: ");
                        stringBuffer2.append(e2.toString());
                        throw new InvalidKeyException(stringBuffer2.toString());
                    }
                } finally {
                    CriticalObject.destroy(encoded);
                }
            } catch (Exception e3) {
                throw new InvalidAlgorithmParameterException(e3.toString());
            }
        } catch (Exception e4) {
            throw new InvalidAlgorithmParameterException(e4.toString());
        }
    }

    private static String[] a(String str) {
        String[] strArr = new String[3];
        StringTokenizer stringTokenizer = new StringTokenizer(str, "/");
        for (int i = 0; i < 3; i++) {
            if (stringTokenizer.hasMoreTokens()) {
                strArr[i] = stringTokenizer.nextToken().trim();
            }
        }
        if (strArr[1] == null) {
            strArr[1] = "CBC";
        }
        if (strArr[2] == null) {
            strArr[2] = "PKCS5Padding";
        }
        return strArr;
    }

    private static t b(String str) throws NoSuchAlgorithmException {
        t c0027h;
        String str2;
        t mVar;
        String upperCase = str.toUpperCase();
        if (upperCase.startsWith(SecurityProvider.ALG_KEYGEN_AES)) {
            mVar = new G();
            str2 = "Rijndael";
        } else if (upperCase.equals("DESEDE")) {
            mVar = new J();
            str2 = "TripleDES";
        } else if (upperCase.startsWith("CAST")) {
            mVar = new m();
            str2 = "CAST128";
        } else {
            if (upperCase.equals("ARCFOUR") || upperCase.equals("RC4")) {
                c0027h = new C0027h();
            } else if (upperCase.equals("BLOWFISH")) {
                c0027h = new C0031l();
            } else if (upperCase.equals("CAMELLIA")) {
                c0027h = new s();
            } else if (upperCase.equals("DES")) {
                c0027h = new u();
            } else if (upperCase.equals("GOST")) {
                c0027h = new w();
            } else if (upperCase.equals("MARS")) {
                c0027h = new y();
            } else if (upperCase.equals("RC2")) {
                c0027h = new D();
            } else if (upperCase.equals("RIJNDAEl256")) {
                c0027h = new H();
            } else if (upperCase.equals("SERPENT")) {
                c0027h = new I();
            } else if (upperCase.equals("TWOFISH")) {
                c0027h = new K();
            } else {
                mVar = null;
                str2 = str;
            }
            t tVar = c0027h;
            str2 = upperCase;
            mVar = tVar;
        }
        if (mVar != null) {
            return mVar;
        }
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("iaik.security.cipher.Raw");
            stringBuffer.append(str2);
            return (t) Class.forName(stringBuffer.toString()).newInstance();
        } catch (Throwable th) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("No Cipher implementation for ");
            stringBuffer2.append(str);
            stringBuffer2.append(": ");
            stringBuffer2.append(th.toString());
            throw new NoSuchAlgorithmException(stringBuffer2.toString());
        }
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // iaik.security.cipher.AbstractC0022a
    void a() {
        this.p = (PBES2ParameterSpec) this.q.clone();
        super.a();
    }

    void a(int i, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        AlgorithmID encryptionScheme;
        PBES2ParameterSpec pBES2ParameterSpec;
        AlgorithmID algorithmID;
        int i2;
        int i3;
        byte[] bArr = null;
        if (algorithmParameterSpec instanceof PBES2ParameterSpec) {
            pBES2ParameterSpec = (PBES2ParameterSpec) algorithmParameterSpec;
            encryptionScheme = pBES2ParameterSpec.getEncryptionScheme();
            AlgorithmID algorithmID2 = this.r;
            if (algorithmID2 != null && !algorithmID2.equals(encryptionScheme)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Illegal encryption scheme (");
                stringBuffer.append(encryptionScheme.getAlgorithm().getName());
                stringBuffer.append("). Must be ");
                stringBuffer.append(this.r.getAlgorithm().getName());
                throw new InvalidAlgorithmParameterException(stringBuffer.toString());
            }
        } else {
            if (i != 1 && i != 3) {
                throw new InvalidAlgorithmParameterException("Params must be PBES2ParameterSpec!");
            }
            AlgorithmID algorithmID3 = this.r;
            if (algorithmID3 == null) {
                algorithmID3 = o;
            }
            encryptionScheme = (AlgorithmID) algorithmID3.clone();
            pBES2ParameterSpec = null;
        }
        int iterationCount = 0;
        if (algorithmParameterSpec instanceof PBKDF2ParameterSpec) {
            PBKDF2ParameterSpec pBKDF2ParameterSpec = (PBKDF2ParameterSpec) algorithmParameterSpec;
            AlgorithmID prf = pBKDF2ParameterSpec.getPrf();
            if (j.equals(prf)) {
                AlgorithmID algorithmID4 = this.t;
                if (algorithmID4 != null) {
                    prf = (AlgorithmID) algorithmID4.clone();
                }
            } else {
                AlgorithmID algorithmID5 = this.t;
                if (algorithmID5 != null && !algorithmID5.equals(prf)) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Illegal PRF (");
                    stringBuffer2.append(prf.getAlgorithm().getName());
                    stringBuffer2.append("). Must be ");
                    stringBuffer2.append(this.t.getAlgorithm().getName());
                    throw new InvalidAlgorithmParameterException(stringBuffer2.toString());
                }
            }
            int derivedKeyLength = pBKDF2ParameterSpec.getDerivedKeyLength();
            if (derivedKeyLength <= 0) {
                int i4 = this.s;
                if (i4 == 0) {
                    throw new InvalidAlgorithmParameterException("Derived key length not specified!");
                }
                i3 = i4;
            } else {
                int i5 = this.s;
                if (i5 > 0 && i5 != derivedKeyLength) {
                    StringBuffer stringBuffer3 = new StringBuffer();
                    stringBuffer3.append("Illegal key length (");
                    stringBuffer3.append(derivedKeyLength);
                    stringBuffer3.append("). Must be ");
                    stringBuffer3.append(this.s);
                    throw new InvalidAlgorithmParameterException(stringBuffer3.toString());
                }
                i3 = derivedKeyLength;
            }
            byte[] salt = pBKDF2ParameterSpec.getSalt();
            int i6 = i3;
            iterationCount = pBKDF2ParameterSpec.getIterationCount();
            algorithmID = prf;
            bArr = salt;
            i2 = i6;
        } else {
            if (algorithmParameterSpec instanceof IvParameterSpec) {
                encryptionScheme.setAlgorithmParameterSpec(algorithmParameterSpec);
            } else if (algorithmParameterSpec instanceof PBEParameterSpec) {
                PBEParameterSpec pBEParameterSpec = (PBEParameterSpec) algorithmParameterSpec;
                byte[] salt2 = pBEParameterSpec.getSalt();
                iterationCount = pBEParameterSpec.getIterationCount();
                algorithmID = null;
                bArr = salt2;
                i2 = 0;
            } else if (algorithmParameterSpec != null) {
                throw new InvalidAlgorithmParameterException("Params must be PBES2ParameterSpec or PBKDF2ParameterSpec or IvParameterSpec!");
            }
            algorithmID = null;
            i2 = 0;
        }
        if (pBES2ParameterSpec == null) {
            if (bArr == null) {
                bArr = new byte[32];
                secureRandom.nextBytes(bArr);
            }
            if (iterationCount <= 0) {
                iterationCount = 2000;
            }
            if (algorithmID == null) {
                AlgorithmID algorithmID6 = this.t;
                if (algorithmID6 == null) {
                    algorithmID6 = j;
                }
                algorithmID = (AlgorithmID) algorithmID6.clone();
            }
            if (i2 <= 0) {
                int i7 = this.s;
                i2 = i7 > 0 ? i7 : 16;
            }
            pBES2ParameterSpec = new PBES2ParameterSpec(bArr, iterationCount, i2, encryptionScheme);
            pBES2ParameterSpec.setPrf(algorithmID);
        }
        this.q = (PBES2ParameterSpec) pBES2ParameterSpec.clone();
        this.p = (PBES2ParameterSpec) pBES2ParameterSpec.clone();
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    protected int engineGetKeySize(Key key) throws InvalidKeyException {
        return this.s;
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public AlgorithmParameters engineGetParameters() {
        if (this.p == null) {
            throw new IllegalStateException("Cipher not initialized yet!");
        }
        try {
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("PBES2", "IAIK");
            algorithmParameters.init(this.p);
            return algorithmParameters;
        } catch (Exception e) {
            throw new InternalErrorException(e);
        }
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        Class clsClass$;
        AlgorithmParameterSpec parameterSpec;
        if (algorithmParameters != null) {
            try {
                String upperCase = algorithmParameters.getAlgorithm().toUpperCase(Locale.US);
                if (upperCase.startsWith("PBES2")) {
                    clsClass$ = k;
                    if (clsClass$ == null) {
                        clsClass$ = class$("iaik.pkcs.pkcs5.PBES2ParameterSpec");
                        k = clsClass$;
                    }
                } else if (SecurityProvider.ALG_KEYGEN_PBKDF2.equals(upperCase)) {
                    clsClass$ = l;
                    if (clsClass$ == null) {
                        clsClass$ = class$("iaik.pkcs.pkcs5.PBKDF2ParameterSpec");
                        l = clsClass$;
                    }
                } else if ("IV".equals(upperCase)) {
                    clsClass$ = m;
                    if (clsClass$ == null) {
                        clsClass$ = class$("javax.crypto.spec.IvParameterSpec");
                        m = clsClass$;
                    }
                } else {
                    if (!"PBE".equals(upperCase)) {
                        throw new InvalidAlgorithmParameterException("Parameters must be of type PBES2Parameters.");
                    }
                    clsClass$ = n;
                    if (clsClass$ == null) {
                        clsClass$ = class$("javax.crypto.spec.PBEParameterSpec");
                        n = clsClass$;
                    }
                }
                parameterSpec = algorithmParameters.getParameterSpec(clsClass$);
            } catch (InvalidParameterSpecException e) {
                throw new InvalidAlgorithmParameterException(e.toString());
            }
        } else {
            parameterSpec = null;
        }
        a(i, key, parameterSpec, secureRandom);
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineInit(int i, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        try {
            a(i, key, null, secureRandom);
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidKeyException(e.toString());
        }
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        a(i, key, algorithmParameterSpec, secureRandom);
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineSetMode(String str) throws NoSuchAlgorithmException {
        if (this.a != null) {
            super.engineSetMode(str);
        }
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineSetPadding(String str) throws NoSuchPaddingException {
        if (this.a != null) {
            super.engineSetPadding(str);
        }
    }
}
