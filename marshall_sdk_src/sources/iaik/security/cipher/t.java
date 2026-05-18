package iaik.security.cipher;

import iaik.security.random.SecRandom;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;

/* JADX INFO: loaded from: classes.dex */
abstract class t {
    static Class l;
    private final boolean a;
    int d;
    byte[] e;
    String f;
    int g;
    final int h;
    boolean i;
    boolean j;
    boolean k;

    t(String str, int i, int i2) {
        this(str, i, i2, false);
    }

    t(String str, int i, int i2, boolean z) {
        this.d = 1;
        this.i = false;
        this.j = true;
        this.k = false;
        this.f = str;
        this.g = i;
        this.h = i2;
        this.e = null;
        this.a = z;
    }

    static byte[] a(int i, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom, int i2) throws InvalidAlgorithmParameterException {
        byte[] iv;
        byte[] bArr = null;
        if (algorithmParameterSpec != null) {
            if (algorithmParameterSpec instanceof IvParameterSpec) {
                iv = ((IvParameterSpec) algorithmParameterSpec).getIV();
            } else if (algorithmParameterSpec instanceof RC2ParameterSpec) {
                iv = ((RC2ParameterSpec) algorithmParameterSpec).getIV();
            } else if (algorithmParameterSpec instanceof RC5ParameterSpec) {
                iv = ((RC5ParameterSpec) algorithmParameterSpec).getIV();
            } else if (algorithmParameterSpec instanceof DESParameterSpec) {
                iv = ((DESParameterSpec) algorithmParameterSpec).getIV();
            } else if (algorithmParameterSpec instanceof GOSTParameterSpec) {
                iv = ((GOSTParameterSpec) algorithmParameterSpec).getIV();
            } else {
                if (algorithmParameterSpec instanceof CAST128ParameterSpec) {
                    iv = ((CAST128ParameterSpec) algorithmParameterSpec).getIV();
                }
                if (bArr != null && i2 != -1 && bArr.length != i2) {
                    throw new InvalidAlgorithmParameterException("IV must be the same length as block size of the underlying cipher.");
                }
            }
            bArr = iv;
            if (bArr != null) {
                throw new InvalidAlgorithmParameterException("IV must be the same length as block size of the underlying cipher.");
            }
        }
        if (bArr != null || i2 == -1) {
            return bArr;
        }
        if (i != 1) {
            throw new InvalidAlgorithmParameterException("You have to specify an IV in decrypt mode!");
        }
        if (secureRandom == null) {
            secureRandom = SecRandom.getDefault();
        }
        byte[] bArr2 = new byte[i2];
        secureRandom.nextBytes(bArr2);
        return bArr2;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    int a(int i, int i2, boolean z, boolean z2) throws IllegalBlockSizeException {
        int i3 = i + i2;
        return i3 - (i3 % this.g);
    }

    int a(Key key) throws InvalidKeyException {
        return key.getEncoded().length << 3;
    }

    abstract void a();

    void a(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        AlgorithmParameterSpec parameterSpec;
        if (algorithmParameters == null) {
            parameterSpec = null;
        } else {
            try {
                Class clsClass$ = l;
                if (clsClass$ == null) {
                    clsClass$ = class$("javax.crypto.spec.IvParameterSpec");
                    l = clsClass$;
                }
                parameterSpec = algorithmParameters.getParameterSpec(clsClass$);
            } catch (InvalidParameterSpecException e) {
                throw new InvalidAlgorithmParameterException(e.toString());
            }
        }
        a(i, key, parameterSpec, secureRandom);
    }

    abstract void a(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException;

    abstract void a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException;

    boolean a(int i, int i2) {
        if (i == 1) {
            this.d = i;
            return true;
        }
        this.d = 0;
        return false;
    }

    boolean a(String str) throws NoSuchPaddingException {
        return true;
    }

    boolean a(boolean z) {
        if (!this.a) {
            z = false;
        }
        this.i = z;
        return z;
    }

    byte[] a_() {
        byte[] bArr = this.e;
        if (bArr != null) {
            return (byte[]) bArr.clone();
        }
        return null;
    }

    abstract void b(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException;

    void c(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
    }

    void d(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
    }

    AlgorithmParameters e() {
        byte[] bArrA_ = a_();
        if (bArrA_ == null) {
            return null;
        }
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArrA_);
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance(this.f, "IAIK");
            algorithmParameters.init(ivParameterSpec);
            return algorithmParameters;
        } catch (Exception unused) {
            return null;
        }
    }

    final int g() {
        return this.g;
    }

    final int h() {
        return this.h;
    }

    final String i() {
        StringBuffer stringBuffer;
        String str;
        switch (this.d) {
            case 2:
                stringBuffer = new StringBuffer();
                stringBuffer.append(this.f);
                str = " in CBC mode";
                break;
            case 3:
                stringBuffer = new StringBuffer();
                stringBuffer.append(this.f);
                stringBuffer.append(" in ");
                stringBuffer.append(this.g * 8);
                str = "-bit OFB mode";
                break;
            case 4:
                stringBuffer = new StringBuffer();
                stringBuffer.append(this.f);
                stringBuffer.append(" in ");
                stringBuffer.append(this.g * 8);
                str = "-bit CFB mode";
                break;
            case 5:
                stringBuffer = new StringBuffer();
                stringBuffer.append(this.f);
                str = " in PCBC mode";
                break;
            case 6:
                stringBuffer = new StringBuffer();
                stringBuffer.append(this.f);
                str = " in CTR mode";
                break;
            case 7:
                stringBuffer = new StringBuffer();
                stringBuffer.append(this.f);
                str = " in CCM mode";
                break;
            case 8:
                stringBuffer = new StringBuffer();
                stringBuffer.append(this.f);
                str = " in GCM mode";
                break;
            case 9:
                stringBuffer = new StringBuffer();
                stringBuffer.append(this.f);
                str = " in CTS mode";
                break;
            default:
                return this.f;
        }
        stringBuffer.append(str);
        return stringBuffer.toString();
    }
}
