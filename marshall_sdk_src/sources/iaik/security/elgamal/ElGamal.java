package iaik.security.elgamal;

import iaik.pkcs.pkcs1.PKCS1AlgorithmParameterSpec;
import iaik.pkcs.pkcs1.PKCS1v15Padding;
import iaik.pkcs.pkcs1.Padding;
import iaik.security.random.SecRandom;
import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Objects;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

/* JADX INFO: loaded from: classes.dex */
public class ElGamal extends CipherSpi {
    static Class a;
    private boolean b;
    private boolean c;
    private int d;
    private int e;
    private int f;
    private a g;
    private ElGamalParameterSpec h;
    private SecureRandom i;
    private BigInteger j;
    private BigInteger k;
    private Padding l;
    private String m;
    private ByteArrayOutputStream n;

    static {
        Util.toString((byte[]) null, -1, 1);
    }

    public ElGamal() {
        try {
            engineSetMode("ECB");
            engineSetPadding(Padding.PADDING_PKCS1);
        } catch (Exception unused) {
        }
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

    private BigInteger a() {
        while (true) {
            BigInteger bigInteger = new BigInteger(this.d, this.i);
            if (!bigInteger.equals(BigInteger.ZERO) && bigInteger.compareTo(this.j) < 0) {
                return bigInteger;
            }
        }
    }

    private byte[] a(BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        Padding padding = this.l;
        if (padding == null && byteArray[0] == 0) {
            int length = byteArray.length - 1;
            byte[] bArr = new byte[length];
            System.arraycopy(byteArray, 1, bArr, 0, length);
            return bArr;
        }
        if (padding == null || byteArray[0] == 0) {
            return byteArray;
        }
        byte[] bArr2 = new byte[byteArray.length + 1];
        System.arraycopy(byteArray, 0, bArr2, 1, byteArray.length);
        return bArr2;
    }

    private byte[] a(byte[] bArr) throws BadPaddingException {
        byte[] bArrPad = this.l.pad(bArr);
        byte[] bArrD = d(bArrPad);
        CryptoUtils.zeroBlock(bArrPad);
        int length = bArrD.length;
        int i = this.e;
        if (length >= i) {
            return bArrD;
        }
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArrD, 0, bArr2, i - bArrD.length, bArrD.length);
        CryptoUtils.zeroBlock(bArrD);
        return bArr2;
    }

    private byte[] b(byte[] bArr) throws BadPaddingException {
        byte[] bArrE = e(bArr);
        byte[] bArrUnpad = this.l.unpad(bArrE);
        CryptoUtils.zeroBlock(bArrE);
        return bArrUnpad;
    }

    private byte[] c(byte[] bArr) {
        if (bArr.length <= engineGetBlockSize()) {
            return this.c ? d(bArr) : e(bArr);
        }
        throw new IllegalStateException("Wrong input size!");
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    private byte[] d(byte[] bArr) {
        BigInteger bigInteger = new BigInteger(1, bArr);
        if (bigInteger.bitLength() >= this.j.bitLength()) {
            throw new IllegalArgumentException("Input too large!");
        }
        ElGamalPublicKey elGamalPublicKey = (ElGamalPublicKey) this.g;
        BigInteger bigIntegerA = a();
        BigInteger bigIntegerModPow = elGamalPublicKey.getParams().getG().modPow(bigIntegerA, this.j);
        BigInteger bigIntegerMod = bigInteger.multiply(elGamalPublicKey.getY().modPow(bigIntegerA, this.j)).mod(this.j);
        byte[] byteArray = bigIntegerModPow.toByteArray();
        byte[] byteArray2 = bigIntegerMod.toByteArray();
        int i = this.f;
        byte[] bArr2 = new byte[i];
        int i2 = i / 2;
        if (byteArray.length > i2) {
            System.arraycopy(byteArray, 1, bArr2, i2 - (byteArray.length - 1), byteArray.length - 1);
        } else {
            System.arraycopy(byteArray, 0, bArr2, i2 - byteArray.length, byteArray.length);
        }
        if (byteArray2.length > i2) {
            System.arraycopy(byteArray2, 1, bArr2, i - (byteArray2.length - 1), byteArray2.length - 1);
        } else {
            System.arraycopy(byteArray2, 0, bArr2, i - byteArray2.length, byteArray2.length);
        }
        return bArr2;
    }

    private byte[] e(byte[] bArr) {
        int length = bArr.length >>> 1;
        byte[] bArr2 = new byte[length];
        byte[] bArr3 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        System.arraycopy(bArr, length, bArr3, 0, length);
        BigInteger bigInteger = new BigInteger(1, bArr2);
        return a(bigInteger.modPow(this.k.subtract(((ElGamalPrivateKey) this.g).getX()), this.j).multiply(new BigInteger(1, bArr3)).mod(this.j));
    }

    @Override // javax.crypto.CipherSpi
    protected int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws BadPaddingException, IllegalBlockSizeException, ShortBufferException {
        if (bArr == null || bArr2 == null) {
            throw null;
        }
        if (i < 0 || i2 <= 0 || i3 < 0) {
            throw new IllegalArgumentException();
        }
        byte[] bArr3 = new byte[i2];
        System.arraycopy(bArr, i, bArr3, 0, i2);
        byte[] bArrEngineUpdate = engineUpdate(bArr3, 0, i2);
        System.arraycopy(bArrEngineUpdate, 0, bArr2, 0, bArrEngineUpdate.length);
        return bArrEngineUpdate.length;
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineDoFinal(byte[] bArr, int i, int i2) throws BadPaddingException, IllegalBlockSizeException {
        ByteArrayOutputStream byteArrayOutputStream = this.n;
        if (byteArrayOutputStream != null && byteArrayOutputStream.size() > 0) {
            if (bArr != null) {
                this.n.write(bArr, i, i2);
            }
            bArr = this.n.toByteArray();
        } else if (bArr == null) {
            bArr = null;
        } else if (i != 0 || i2 != bArr.length) {
            byte[] bArr2 = new byte[i2];
            System.arraycopy(bArr, i, bArr2, 0, i2);
            bArr = bArr2;
        }
        Objects.requireNonNull(bArr, "Null data to process!");
        if (!this.b) {
            throw new IllegalStateException("Not initialized!");
        }
        byte[] bArrC = this.l == null ? c(bArr) : this.c ? a(bArr) : b(bArr);
        ByteArrayOutputStream byteArrayOutputStream2 = this.n;
        if (byteArrayOutputStream2 != null) {
            byteArrayOutputStream2.reset();
        }
        return bArrC;
    }

    @Override // javax.crypto.CipherSpi
    protected int engineGetBlockSize() {
        return this.c ? (this.d - 1) / 8 : ((this.d + 7) / 8) * 2;
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineGetIV() {
        return null;
    }

    @Override // javax.crypto.CipherSpi
    protected int engineGetOutputSize(int i) {
        return this.c ? ((this.d + 7) / 8) * 2 : (this.d - 1) / 8;
    }

    @Override // javax.crypto.CipherSpi
    protected AlgorithmParameters engineGetParameters() {
        Padding padding = this.l;
        if (padding != null) {
            return padding.getParameters();
        }
        return null;
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            Class clsClass$ = a;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.security.elgamal.ElGamalParameterSpec");
                a = clsClass$;
            }
            engineInit(i, key, algorithmParameters.getParameterSpec(clsClass$), secureRandom);
        } catch (InvalidParameterSpecException e) {
            throw new InvalidAlgorithmParameterException(e.toString());
        }
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(int i, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        try {
            engineInit(i, key, ((a) new ElGamalKeyFactory().engineTranslateKey(key)).getParams(), secureRandom);
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidKeyException(e.toString());
        }
    }

    @Override // javax.crypto.CipherSpi
    protected void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (key == null || algorithmParameterSpec == null) {
            throw null;
        }
        if (!(algorithmParameterSpec instanceof ElGamalParameterSpec)) {
            throw new InvalidAlgorithmParameterException();
        }
        boolean z = a(i) == 1;
        this.c = z;
        if ((z && (key instanceof ElGamalPrivateKey)) || (!z && (key instanceof ElGamalPublicKey))) {
            throw new InvalidKeyException();
        }
        a aVar = (a) new ElGamalKeyFactory().engineTranslateKey(key);
        this.g = aVar;
        if (!algorithmParameterSpec.equals(aVar.getParams())) {
            throw new InvalidKeyException("Given params do not match with key params!");
        }
        this.d = this.g.getParams().getP().bitLength();
        this.f = engineGetOutputSize(1);
        ElGamalParameterSpec elGamalParameterSpec = (ElGamalParameterSpec) algorithmParameterSpec;
        this.h = elGamalParameterSpec;
        BigInteger p = elGamalParameterSpec.getP();
        this.j = p;
        this.k = p.subtract(BigInteger.ONE);
        this.e = this.j.bitLength() >>> 3;
        this.n = null;
        if (this.l != null) {
            if (algorithmParameterSpec instanceof PKCS1AlgorithmParameterSpec) {
                secureRandom = ((PKCS1AlgorithmParameterSpec) algorithmParameterSpec).getSecureRandom();
            }
            this.l.init(i, (Key) this.g, this.e, algorithmParameterSpec, this.i);
        }
        if (secureRandom == null) {
            secureRandom = SecRandom.getDefault();
        }
        this.i = secureRandom;
        this.b = true;
    }

    @Override // javax.crypto.CipherSpi
    protected void engineSetMode(String str) throws NoSuchAlgorithmException {
        this.m = str;
    }

    @Override // javax.crypto.CipherSpi
    protected void engineSetPadding(String str) throws NoSuchPaddingException {
        if (str.equalsIgnoreCase(Padding.PADDING_PKCS1)) {
            this.l = new PKCS1v15Padding(str, this.m);
        } else {
            if (str.equalsIgnoreCase(Padding.PADDING_NONE)) {
                this.l = null;
                return;
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Unknown padding: ");
            stringBuffer.append(str);
            throw new NoSuchPaddingException(stringBuffer.toString());
        }
    }

    @Override // javax.crypto.CipherSpi
    protected int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (this.n == null) {
            if (!this.b) {
                throw new IllegalStateException("Cipher not initialized!");
            }
            this.n = new ByteArrayOutputStream(this.e);
        }
        this.n.write(bArr, i, i2);
        return 0;
    }

    @Override // javax.crypto.CipherSpi
    protected byte[] engineUpdate(byte[] bArr, int i, int i2) {
        if (this.n == null) {
            if (!this.b) {
                throw new IllegalStateException("Cipher not initialized!");
            }
            this.n = new ByteArrayOutputStream(this.e);
        }
        this.n.write(bArr, i, i2);
        return null;
    }
}
