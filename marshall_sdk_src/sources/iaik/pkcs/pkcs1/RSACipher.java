package iaik.pkcs.pkcs1;

import iaik.security.random.SecRandom;
import iaik.security.rsa.RSAPrivateKey;
import iaik.security.rsa.RSAPublicKey;
import iaik.utils.CryptoUtils;
import iaik.utils.NumberTheory;
import iaik.utils.Util;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.ProviderException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Objects;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

/* JADX INFO: loaded from: classes.dex */
public class RSACipher extends CipherSpi {
    static Class a = null;
    private static boolean b = true;
    private static boolean c = true;
    private int d;
    private String e;
    private int f;
    private SecureRandom g;
    private ByteArrayOutputStream k;
    protected RSAPrivateKey privKey;
    protected RSAPublicKey pubKey;
    private boolean j = true;
    private Padding h = new PKCS1v15Padding();
    private boolean i = b;

    static {
        Util.toString((byte[]) null, -1, 1);
    }

    private int a() {
        RSAPublicKey rSAPublicKey = this.pubKey;
        return ((rSAPublicKey != null ? rSAPublicKey.getModulus() : this.privKey.getModulus()).bitLength() + 7) / 8;
    }

    private static int a(int i) {
        if (i == 3) {
            return 1;
        }
        if (i == 4) {
            return 2;
        }
        return i;
    }

    private byte[] a(byte[] bArr) throws BadPaddingException {
        byte[] bArrPad = this.h.pad(bArr);
        try {
            byte[] bArrRawCrypt = rawCrypt(bArrPad);
            CryptoUtils.zeroBlock(bArrPad);
            int length = bArrRawCrypt.length;
            int i = this.f;
            if (length >= i) {
                return bArrRawCrypt;
            }
            byte[] bArr2 = new byte[i];
            System.arraycopy(bArrRawCrypt, 0, bArr2, i - bArrRawCrypt.length, bArrRawCrypt.length);
            CryptoUtils.zeroBlock(bArrRawCrypt);
            return bArr2;
        } catch (SecurityException e) {
            throw new BadPaddingException(e.toString());
        }
    }

    private byte[] b(byte[] bArr) throws BadPaddingException {
        if (bArr.length != a()) {
            throw new BadPaddingException("Invalid PKCS#1 padding: encrypted message and modulus lengths do not match!");
        }
        byte[] bArrRawCrypt = rawCrypt(bArr);
        byte[] bArrUnpad = this.h.unpad(bArrRawCrypt);
        CryptoUtils.zeroBlock(bArrRawCrypt);
        return bArrUnpad;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public static boolean isUseBlindingDefault() {
        return b;
    }

    public static void setDoVerifyCRTSignature(boolean z) {
        c = z;
    }

    public static boolean setUseBlindingDefault(boolean z) {
        boolean z2 = b;
        b = z;
        return z2;
    }

    public static void setValidateAgainstOaepKeyParameters(boolean z) {
        a.a(z);
    }

    @Override // javax.crypto.CipherSpi
    protected int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws BadPaddingException, ShortBufferException {
        byte[] bArrEngineDoFinal = engineDoFinal(bArr, i, i2);
        try {
            System.arraycopy(bArrEngineDoFinal, 0, bArr2, i3, bArrEngineDoFinal.length);
            CryptoUtils.zeroBlock(bArrEngineDoFinal);
            return bArrEngineDoFinal.length;
        } catch (Exception unused) {
            throw new ShortBufferException("Buffer to short to hold result.");
        }
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineDoFinal(byte[] bArr, int i, int i2) throws BadPaddingException {
        byte[] bArrRawCrypt;
        ByteArrayOutputStream byteArrayOutputStream = this.k;
        if (byteArrayOutputStream != null && byteArrayOutputStream.size() > 0) {
            if (bArr != null) {
                this.k.write(bArr, i, i2);
            }
            bArr = this.k.toByteArray();
        } else if (bArr == null) {
            bArr = null;
        } else if (i != 0 || i2 != bArr.length) {
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i, bArr2, 0, i2);
            bArr = bArr2;
        }
        Objects.requireNonNull(bArr, "Null data to process!");
        if (this.h == null) {
            try {
                bArrRawCrypt = rawCrypt(bArr);
            } catch (SecurityException e) {
                throw new BadPaddingException(e.getMessage());
            }
        } else {
            bArrRawCrypt = this.d == 1 ? a(bArr) : b(bArr);
        }
        ByteArrayOutputStream byteArrayOutputStream2 = this.k;
        if (byteArrayOutputStream2 != null) {
            byteArrayOutputStream2.reset();
        }
        return bArrRawCrypt;
    }

    @Override // javax.crypto.CipherSpi
    protected int engineGetBlockSize() {
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineGetIV() {
        return null;
    }

    @Override // javax.crypto.CipherSpi
    protected int engineGetKeySize(Key key) throws InvalidKeyException {
        try {
            try {
                return Util.getRSAPublicKey(key).getModulus().bitLength();
            } catch (InvalidKeyException unused) {
                throw new InvalidKeyException("Not an RSA key!");
            }
        } catch (InvalidKeyException unused2) {
            return Util.getRSAPrivateKey(key).getModulus().bitLength();
        }
    }

    @Override // javax.crypto.CipherSpi
    protected int engineGetOutputSize(int i) {
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    protected AlgorithmParameters engineGetParameters() {
        Padding padding = this.h;
        if (padding != null) {
            return padding.getParameters();
        }
        return null;
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        AlgorithmParameterSpec parameterSpec;
        if (algorithmParameters != null) {
            Padding padding = this.h;
            if (padding == null || !padding.c().toUpperCase().startsWith(Padding.PADDING_OAEP)) {
                throw new InvalidAlgorithmParameterException("Parameters are only allowed for OAEP padding.");
            }
            try {
                Class clsClass$ = a;
                if (clsClass$ == null) {
                    clsClass$ = class$("iaik.pkcs.pkcs1.RSAOaepParameterSpec");
                    a = clsClass$;
                }
                parameterSpec = algorithmParameters.getParameterSpec(clsClass$);
            } catch (InvalidParameterSpecException unused) {
                throw new InvalidAlgorithmParameterException("Only RSAOaepParameters allowed.");
            }
        } else {
            parameterSpec = null;
        }
        engineInit(i, key, parameterSpec, secureRandom);
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(int i, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        try {
            engineInit(i, key, (AlgorithmParameterSpec) null, secureRandom);
        } catch (InvalidAlgorithmParameterException unused) {
        }
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        boolean z;
        SecureRandom secureRandom2;
        if (i == 100) {
            i = 1;
            z = false;
        } else {
            z = c;
        }
        this.j = z;
        this.d = a(i);
        this.g = secureRandom;
        this.k = null;
        this.pubKey = null;
        this.privKey = null;
        Objects.requireNonNull(key, "Key is null!");
        try {
            try {
                this.pubKey = Util.getRSAPublicKey(key);
            } catch (InvalidKeyException unused) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Class does not represent an RSA key: ");
                stringBuffer.append(key.getClass().getName());
                throw new InvalidKeyException(stringBuffer.toString());
            }
        } catch (InvalidKeyException unused2) {
            this.privKey = Util.getRSAPrivateKey(key);
        } catch (Exception e) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Unable to init RSA key: ");
            stringBuffer2.append(e.toString());
            throw new InvalidKeyException(stringBuffer2.toString());
        }
        this.f = a();
        Padding padding = this.h;
        if (padding != null) {
            Key key2 = this.pubKey;
            if (key2 == null) {
                key2 = this.privKey;
            }
            Key key3 = key2;
            if (algorithmParameterSpec != null) {
                if (!padding.c().toUpperCase().startsWith(Padding.PADDING_OAEP)) {
                    throw new InvalidAlgorithmParameterException("Parameters are only allowed for OAEP padding.");
                }
                if ((algorithmParameterSpec instanceof PKCS1AlgorithmParameterSpec) && (secureRandom2 = ((PKCS1AlgorithmParameterSpec) algorithmParameterSpec).getSecureRandom()) != null) {
                    this.g = secureRandom2;
                }
            }
            this.h.init(this.d, key3, this.f, algorithmParameterSpec, this.g);
        }
    }

    @Override // javax.crypto.CipherSpi
    protected void engineSetMode(String str) {
        this.e = str;
    }

    @Override // javax.crypto.CipherSpi
    protected void engineSetPadding(String str) throws NoSuchPaddingException {
        a aVarA;
        if (str.equalsIgnoreCase(Padding.PADDING_PKCS1) || str.equalsIgnoreCase(Padding.PADDING_PKCS1_SSL2)) {
            this.h = new PKCS1v15Padding(str, this.e);
            return;
        }
        if (str.toUpperCase().startsWith(Padding.PADDING_OAEP)) {
            aVarA = a.a(str);
        } else {
            if (!str.equalsIgnoreCase(Padding.PADDING_NONE)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Unknown padding: ");
                stringBuffer.append(str);
                throw new NoSuchPaddingException(stringBuffer.toString());
            }
            aVarA = null;
        }
        this.h = aVarA;
    }

    @Override // javax.crypto.CipherSpi
    protected Key engineUnwrap(byte[] bArr, String str, int i) throws NoSuchAlgorithmException, InvalidKeyException {
        try {
            return Util.decodeKey(i, str, engineDoFinal(bArr, 0, bArr.length));
        } catch (BadPaddingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not unwrap key: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    @Override // javax.crypto.CipherSpi
    protected int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (this.k == null) {
            if (this.f <= 0) {
                throw new IllegalStateException("Cipher not initialized!");
            }
            this.k = new ByteArrayOutputStream(this.f);
        }
        this.k.write(bArr, i, i2);
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineUpdate(byte[] bArr, int i, int i2) {
        if (this.k == null) {
            if (this.f <= 0) {
                throw new IllegalStateException("Cipher not initialized!");
            }
            this.k = new ByteArrayOutputStream(this.f);
        }
        this.k.write(bArr, i, i2);
        return null;
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineWrap(Key key) throws InvalidKeyException {
        byte[] encoded = key.getEncoded();
        if (encoded == null || encoded.length == 0) {
            throw new InvalidKeyException("Could not encode key for wrapping!");
        }
        try {
            return engineDoFinal(encoded, 0, encoded.length);
        } catch (BadPaddingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not wrap key: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    protected SecureRandom getSecureRandom() {
        if (this.g == null) {
            this.g = SecRandom.getDefault();
        }
        return this.g;
    }

    public boolean isUseBlinding() {
        return this.i;
    }

    protected byte[] rawCrypt(byte[] bArr) {
        byte[] bArr2;
        BigInteger bigInteger = new BigInteger(1, bArr);
        RSAPublicKey rSAPublicKey = this.pubKey;
        byte[] byteArray = (rSAPublicKey != null ? rawPublicRSA(bigInteger, rSAPublicKey) : rawPrivateRSA(bigInteger, this.privKey, this.g)).toByteArray();
        int iA = a();
        if (byteArray.length > iA) {
            bArr2 = new byte[iA];
            System.arraycopy(byteArray, byteArray.length - iA, bArr2, 0, iA);
        } else {
            if (byteArray.length >= iA) {
                return byteArray;
            }
            bArr2 = new byte[iA];
            System.arraycopy(byteArray, 0, bArr2, iA - byteArray.length, byteArray.length);
        }
        return bArr2;
    }

    public BigInteger rawPrivateRSA(BigInteger bigInteger, java.security.interfaces.RSAPrivateKey rSAPrivateKey, Random random) {
        RSAPrivateCrtKey rSAPrivateCrtKey;
        BigInteger publicExponent;
        BigInteger bigIntegerMod;
        BigInteger bigIntegerModPow;
        boolean z = false;
        BigInteger bigInteger2 = null;
        if (rSAPrivateKey instanceof RSAPrivateCrtKey) {
            rSAPrivateCrtKey = (RSAPrivateCrtKey) rSAPrivateKey;
            publicExponent = rSAPrivateCrtKey.getPublicExponent();
            if (publicExponent != null && publicExponent.bitCount() == 0) {
                publicExponent = null;
            } else if (this.i && publicExponent != null && !publicExponent.equals(NumberTheory.ZERO)) {
                z = true;
            }
            if (rSAPrivateCrtKey.getPrimeExponentP().bitCount() == 0) {
                rSAPrivateCrtKey = null;
            }
        } else {
            rSAPrivateCrtKey = null;
            publicExponent = null;
        }
        BigInteger modulus = rSAPrivateKey.getModulus();
        if (z) {
            if (random == null) {
                random = SecRandom.getDefault();
            }
            bigInteger2 = new BigInteger(modulus.bitLength() - 1, random);
            if (bigInteger2.equals(NumberTheory.ZERO) || bigInteger2.equals(NumberTheory.ONE)) {
                throw new ProviderException("Secure random seems to deliver non-random bits. Unable to generate random blinding factor.");
            }
            bigIntegerMod = bigInteger.multiply(bigInteger2.modPow(publicExponent, modulus)).mod(modulus);
        } else {
            bigIntegerMod = bigInteger;
        }
        if (rSAPrivateCrtKey != null) {
            BigInteger primeP = rSAPrivateCrtKey.getPrimeP();
            BigInteger primeQ = rSAPrivateCrtKey.getPrimeQ();
            BigInteger bigIntegerModPow2 = bigIntegerMod.mod(primeP).modPow(rSAPrivateCrtKey.getPrimeExponentP(), primeP);
            BigInteger bigIntegerModPow3 = bigIntegerMod.mod(primeQ).modPow(rSAPrivateCrtKey.getPrimeExponentQ(), primeQ);
            bigIntegerModPow = bigIntegerModPow2.subtract(bigIntegerModPow3).multiply(rSAPrivateCrtKey.getCrtCoefficient()).mod(primeP).multiply(primeQ).add(bigIntegerModPow3);
        } else {
            bigIntegerModPow = bigIntegerMod.modPow(rSAPrivateKey.getPrivateExponent(), modulus);
        }
        if (z) {
            bigIntegerModPow = bigIntegerModPow.multiply(bigInteger2.modInverse(modulus)).mod(modulus);
        }
        if (!this.j || this.d != 1 || publicExponent == null || bigInteger.equals(bigIntegerModPow.modPow(publicExponent, modulus))) {
            return bigIntegerModPow;
        }
        throw new SecurityException("RSA signing error!");
    }

    public BigInteger rawPublicRSA(BigInteger bigInteger, java.security.interfaces.RSAPublicKey rSAPublicKey) {
        return bigInteger.modPow(rSAPublicKey.getPublicExponent(), rSAPublicKey.getModulus());
    }

    protected void setSecureRandom(SecureRandom secureRandom) {
        this.g = secureRandom;
        Padding padding = this.h;
        if (padding != null) {
            padding.a(secureRandom);
        }
    }

    public boolean setUseBlinding(boolean z) {
        boolean z2 = this.i;
        this.i = z;
        return z2;
    }
}
