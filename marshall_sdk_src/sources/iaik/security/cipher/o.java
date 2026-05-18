package iaik.security.cipher;

import iaik.pkcs.pkcs1.Padding;
import iaik.security.random.SecRandom;
import iaik.utils.CryptoUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes.dex */
final class o extends t {
    static Class a;
    static Class b;
    private byte[] A;
    private BigInteger B;
    private final t c;
    private int m;
    private boolean n;
    private q o;
    private n p;
    private byte[] q;
    private byte[] r;
    private int s;
    private byte[] t;
    private byte[] u;
    private long v;
    private long w;
    private long x;
    private ByteArrayOutputStream y;
    private final byte[] z;

    public o(t tVar) throws NoSuchAlgorithmException {
        super(tVar.i(), 16, tVar.h());
        if (tVar.g() != 16) {
            throw new NoSuchAlgorithmException("Encryption algorithm must process a block-size of 128 bit.");
        }
        this.c = tVar;
        this.g = 16;
        this.z = new byte[16];
    }

    public static byte[] a(long j, long j2, byte[] bArr, int i) {
        if (j <= -1 || j2 <= -1) {
            return null;
        }
        byte[] bArr2 = new byte[32];
        if (j2 != 0) {
            bArr2[0] = 64;
        }
        bArr2[0] = (byte) (bArr2[0] | ((i - 2) << 2));
        int length = (15 - bArr.length) - 1;
        bArr2[0] = (byte) (bArr2[0] | length);
        System.arraycopy(bArr, 0, bArr2, 1, bArr.length);
        int i2 = length + 1;
        byte[] bArr3 = new byte[i2];
        long j3 = j;
        while (length >= 0) {
            bArr3[length] = (byte) j3;
            j3 >>= 8;
            length--;
        }
        System.arraycopy(bArr3, 0, bArr2, bArr.length + 1, i2);
        int i3 = 22;
        if (j2 == 0) {
            i3 = 16;
        } else if (j2 < 65280) {
            bArr2[16] = (byte) (j2 >> 8);
            bArr2[17] = (byte) j2;
            i3 = 18;
        } else if (j2 < ((long) Math.pow(2.0d, 32.0d))) {
            bArr2[16] = -1;
            bArr2[17] = -2;
            bArr2[18] = (byte) (j2 >> 24);
            bArr2[19] = (byte) (j2 >> 16);
            bArr2[20] = (byte) (j2 >> 8);
            bArr2[21] = (byte) j2;
        } else {
            bArr2[16] = -1;
            bArr2[17] = -1;
            bArr2[18] = (byte) (j2 >> 56);
            bArr2[19] = (byte) (j2 >> 48);
            bArr2[20] = (byte) (j2 >> 40);
            bArr2[21] = (byte) (j2 >> 32);
            bArr2[22] = (byte) (j2 >> 24);
            bArr2[23] = (byte) (j2 >> 16);
            bArr2[24] = (byte) (j2 >> 8);
            bArr2[25] = (byte) j2;
            i3 = 26;
        }
        byte[] bArr4 = new byte[i3];
        System.arraycopy(bArr2, 0, bArr4, 0, i3);
        return bArr4;
    }

    private void c() throws IllegalBlockSizeException {
        byte[] bArrA;
        long length = this.q == null ? 0L : r1.length;
        byte[] bArr = new byte[16];
        if (this.m == 1) {
            bArrA = a(this.v, length, this.r, this.s);
        } else {
            bArrA = a(this.n ? this.v : this.v - ((long) this.s), length, this.r, this.s);
        }
        this.p.b(bArrA, 0, 16, this.z, 0);
        if (length > 0) {
            int length2 = bArrA.length - 16;
            System.arraycopy(bArrA, 16, bArr, 0, length2);
            int i = 16 - length2;
            if (i >= length) {
                System.arraycopy(this.q, 0, bArr, length2, (int) length);
                this.p.b(bArr, 0, 16, this.z, 0);
                return;
            }
            System.arraycopy(this.q, 0, bArr, length2, i);
            this.p.b(bArr, 0, 16, this.z, 0);
            while (true) {
                int i2 = i + 16;
                if (i2 > length) {
                    break;
                }
                this.p.b(this.q, i, 16, this.z, 0);
                i = i2;
            }
            long j = i;
            if (j < length) {
                byte[] bArr2 = new byte[16];
                System.arraycopy(this.q, i, bArr2, 0, (int) (length - j));
                this.p.b(bArr2, 0, 16, this.z, 0);
            }
        }
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // iaik.security.cipher.t
    int a(int i, int i2, boolean z, boolean z2) {
        int i3 = i + i2;
        int i4 = i3 - (i3 % 16);
        int i5 = this.s;
        int i6 = (i3 - i5) - ((i3 - i5) % 16);
        if (i6 < 0) {
            i6 = 0;
        }
        if (this.n) {
            return z ? i3 : i4;
        }
        if (!z2) {
            return this.m == 1 ? z ? i3 : i4 : z ? i3 : i6;
        }
        if (this.m == 1) {
            return z ? i3 + i5 : i4;
        }
        if (!z) {
            return i6;
        }
        if (i3 < i5) {
            return 0;
        }
        return i3 - i5;
    }

    @Override // iaik.security.cipher.t
    int a(Key key) throws InvalidKeyException {
        return this.c.a(key);
    }

    @Override // iaik.security.cipher.t
    void a() {
        this.c.a();
        if (this.d == 7) {
            this.o.a();
            this.p.a();
            this.y.reset();
            this.x = -1L;
            this.v = this.w;
            byte[] bArr = this.u;
            if (bArr != null) {
                byte[] bArr2 = this.t;
                System.arraycopy(bArr, 0, bArr2, 0, bArr2.length);
            } else {
                this.t = null;
            }
            CryptoUtils.zeroBlock(this.z);
        }
    }

    void a(int i) throws IllegalBlockSizeException {
        long j = this.v;
        if (j != -1) {
            long j2 = this.x;
            if (((long) i) + j2 > j) {
                throw new IllegalBlockSizeException("Input data not of specified input length!");
            }
            if (j2 != -1) {
                return;
            } else {
                c();
            }
        } else if (this.x != -1) {
            return;
        }
        this.x = 0L;
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.d != 7) {
            this.c.a(i, key, algorithmParameters, secureRandom);
            this.e = null;
            return;
        }
        try {
            try {
                Class clsClass$ = a;
                if (clsClass$ == null) {
                    clsClass$ = class$("iaik.security.cipher.CCMParameterSpec");
                    a = clsClass$;
                }
                a(i, key, algorithmParameters.getParameterSpec(clsClass$), secureRandom);
            } catch (InvalidParameterSpecException unused) {
                Class clsClass$2 = b;
                if (clsClass$2 == null) {
                    clsClass$2 = class$("iaik.security.cipher.CCMCMSParameterSpec");
                    b = clsClass$2;
                }
                a(i, key, algorithmParameters.getParameterSpec(clsClass$2), secureRandom);
            }
        } catch (InvalidParameterSpecException unused2) {
            super.a(i, key, algorithmParameters, secureRandom);
        }
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.d == 7) {
            this.n = false;
            this.m = i;
            this.x = -1L;
            this.s = -1;
            this.v = -1L;
            this.r = null;
            this.q = null;
            this.t = null;
            this.A = new byte[16];
            this.c.a(1, key, algorithmParameterSpec, secureRandom);
            if (algorithmParameterSpec instanceof CCMParameterSpec) {
                CCMParameterSpec cCMParameterSpec = (CCMParameterSpec) algorithmParameterSpec;
                this.q = cCMParameterSpec.getAssociatedData();
                this.r = cCMParameterSpec.getNonce();
                this.s = cCMParameterSpec.getMacLength();
                this.v = cCMParameterSpec.getInputLength();
            } else if (algorithmParameterSpec instanceof CCMCMSParameterSpec) {
                this.n = true;
                CCMCMSParameterSpec cCMCMSParameterSpec = (CCMCMSParameterSpec) algorithmParameterSpec;
                this.q = cCMCMSParameterSpec.getAssociatedData();
                this.r = cCMCMSParameterSpec.getNonce();
                this.s = cCMCMSParameterSpec.getMacLength();
                this.v = cCMCMSParameterSpec.getInputLength();
                this.t = cCMCMSParameterSpec.getMac();
            } else if (algorithmParameterSpec != null) {
                byte[] bArrA = t.a(i, algorithmParameterSpec, secureRandom, -1);
                this.r = bArrA;
                if (bArrA == null) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("CCM can not be used with ");
                    stringBuffer.append(algorithmParameterSpec.getClass());
                    throw new InvalidAlgorithmParameterException(stringBuffer.toString());
                }
            }
            byte[] bArr = this.r;
            if (bArr == null || bArr.length == 0) {
                if (i == 2) {
                    throw new InvalidAlgorithmParameterException("nonce has to be specified for decryption.");
                }
                long j = this.v;
                if (j > -1) {
                    this.r = j < 2147483647L ? new byte[11] : new byte[7];
                } else {
                    this.r = new byte[7];
                }
                (secureRandom == null ? SecRandom.getDefault() : secureRandom).nextBytes(this.r);
            } else if (bArr.length < 7 || bArr.length > 13) {
                throw new InvalidAlgorithmParameterException("nonce length not between 7 and 13 bytes!");
            }
            if (this.v != -1) {
                BigInteger bigIntegerSubtract = BigInteger.ZERO.setBit((15 - this.r.length) << 3).subtract(BigInteger.ONE);
                this.B = bigIntegerSubtract;
                if (i == 1 && bigIntegerSubtract.compareTo(BigInteger.valueOf(this.v)) < 0) {
                    throw new InvalidAlgorithmParameterException("parameter inputLength or nonce too long");
                }
            } else {
                this.y = new ByteArrayOutputStream();
            }
            if (this.n) {
                if (i == 1) {
                    byte[] bArr2 = this.t;
                    if (bArr2 != null && bArr2.length != 0) {
                        throw new InvalidAlgorithmParameterException("Authentication block must not be specified for encryption");
                    }
                    this.t = new byte[this.s];
                } else {
                    byte[] bArr3 = this.t;
                    if (bArr3 == null || bArr3.length == 0) {
                        throw new InvalidAlgorithmParameterException("Authentication block (MAC) has to be specified for decryption");
                    }
                    this.s = bArr3.length;
                }
            }
            if (this.s == -1) {
                this.s = 12;
            }
            int i2 = this.s;
            if (i2 < 4 || i2 > 16) {
                throw new InvalidAlgorithmParameterException("Specified MAC-length not between 4 and 16 bytes!");
            }
            this.e = new byte[this.r.length];
            System.arraycopy(this.r, 0, this.e, 0, this.r.length);
            byte[] bArr4 = new byte[16];
            byte[] bArr5 = this.r;
            bArr4[0] = (byte) (((15 - bArr5.length) - 1) | bArr4[0]);
            System.arraycopy(bArr5, 0, bArr4, 1, bArr5.length);
            System.arraycopy(bArr4, 0, this.A, 0, 16);
            CryptoUtils.increment(bArr4);
            this.o.a(1, key, new IvParameterSpec(bArr4), (SecureRandom) null);
            this.p.a(1, key, new IvParameterSpec(new byte[16]), (SecureRandom) null);
            this.w = this.v;
            byte[] bArr6 = this.t;
            this.u = bArr6 != null ? (byte[]) bArr6.clone() : null;
        } else {
            this.c.a(i, key, algorithmParameterSpec, secureRandom);
            this.e = null;
        }
        if (this.B == null) {
            this.B = BigInteger.ZERO.setBit((15 - this.r.length) << 3).subtract(BigInteger.ONE);
        }
    }

    @Override // iaik.security.cipher.t
    void a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        if (this.d != 7) {
            this.c.b(bArr, i, i2, bArr2, i3);
            return;
        }
        a(i2);
        this.o.b(bArr, i, i2, bArr2, i3);
        if (this.v != -1) {
            this.p.b(bArr2, i3, i2, this.z, 0);
        } else {
            this.y.write(bArr2, i3, i2);
        }
        this.x += (long) i2;
    }

    @Override // iaik.security.cipher.t
    boolean a(int i, int i2) {
        if (i != 7) {
            if (i != 1) {
                this.d = 0;
                this.c.a(false);
                return this.c.a(i, i2);
            }
            this.d = i;
            this.c.a(1, 0);
            this.c.a(false);
            return true;
        }
        this.d = i;
        q qVar = new q(this.c);
        this.o = qVar;
        qVar.a(6, 0);
        n nVar = new n(this.c);
        this.p = nVar;
        nVar.a(2, 0);
        this.c.a(1, 0);
        this.j = false;
        this.k = true;
        this.c.a(false);
        this.o.a(false);
        this.p.a(false);
        return true;
    }

    @Override // iaik.security.cipher.t
    boolean a(String str) throws NoSuchPaddingException {
        if (this.d != 7 || str.equalsIgnoreCase(Padding.PADDING_NONE)) {
            return true;
        }
        throw new NoSuchPaddingException("Mode CCM must be used with Padding 'NoPadding'.");
    }

    @Override // iaik.security.cipher.t
    void b(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        if (this.d != 7) {
            this.c.b(bArr, i, i2, bArr2, i3);
            return;
        }
        a(i2);
        this.o.b(bArr, i, i2, bArr2, i3);
        this.x += (long) i2;
        if (this.v != -1) {
            this.p.b(bArr, i, i2, this.z, 0);
        } else {
            this.y.write(bArr, i, i2);
        }
    }

    @Override // iaik.security.cipher.t
    void c(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        a(i2);
        int i4 = 0;
        while (true) {
            int i5 = i4 + 16;
            if (i5 > i2) {
                break;
            }
            int i6 = i + i4;
            this.o.b(bArr, i6, 16, bArr2, i3 + i4);
            if (this.v != -1) {
                this.p.b(bArr, i6, 16, this.z, 0);
            } else {
                this.y.write(bArr, i6, 16);
                this.x += 16;
            }
            i4 = i5;
        }
        int i7 = i2 - i4;
        int i8 = i + i4;
        int i9 = i3 + i4;
        if (i7 > 0) {
            this.o.b(bArr, i8, i7, bArr2, i9);
            i9 += i7;
            byte[] bArr3 = new byte[16];
            System.arraycopy(bArr, i8, bArr3, 0, i7);
            if (this.v != -1) {
                this.p.b(bArr3, 0, 16, this.z, 0);
            } else {
                this.y.write(bArr3, 0, 16);
                this.x += (long) i7;
            }
        }
        if (this.v == -1) {
            long j = this.x;
            this.v = j;
            if (this.B.compareTo(BigInteger.valueOf(j)) < 0) {
                throw new IllegalBlockSizeException("inputLength too long for being encoded with given nonce");
            }
            try {
                this.y.flush();
                c();
                byte[] byteArray = this.y.toByteArray();
                for (int i10 = 0; i10 < byteArray.length; i10 += 16) {
                    this.p.b(byteArray, i10, 16, this.z, 0);
                }
            } catch (IOException unused) {
                throw new IllegalBlockSizeException("I/O-Exception when writing input data.");
            }
        }
        byte[] bArr4 = new byte[16];
        this.c.b(this.A, 0, 16, bArr4, 0);
        int i11 = this.s;
        byte[] bArr5 = new byte[i11];
        CryptoUtils.xorBlock(bArr4, 0, this.z, 0, bArr5, 0, i11);
        if (this.n) {
            System.arraycopy(bArr5, 0, this.t, 0, this.s);
        } else {
            System.arraycopy(bArr5, 0, bArr2, i9, this.s);
        }
    }

    @Override // iaik.security.cipher.t
    void d(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        a(i2);
        int i4 = this.n ? 0 : this.s;
        if (i2 < i4) {
            throw new IllegalBlockSizeException("Input too short - no MAC verification possible!");
        }
        int i5 = 0;
        while (true) {
            int i6 = i5 + 16;
            if (i6 >= i2 - i4) {
                break;
            }
            int i7 = i3 + i5;
            this.o.b(bArr, i + i5, 16, bArr2, i7);
            if (this.v != -1) {
                this.p.b(bArr2, i7, 16, this.z, 0);
            } else {
                this.y.write(bArr2, i7, 16);
            }
            i5 = i6;
        }
        this.x += (long) i5;
        int i8 = i2 - i5;
        int i9 = i + i5;
        int i10 = i3 + i5;
        if (i8 > i4) {
            int i11 = i8 - i4;
            this.o.b(bArr, i9, i11, bArr2, i10);
            i9 += i11;
            byte[] bArr3 = new byte[16];
            System.arraycopy(bArr2, i10, bArr3, 0, i11);
            if (this.v != -1) {
                this.p.b(bArr3, 0, 16, this.z, 0);
            } else {
                this.y.write(bArr3, 0, 16);
                this.x += (long) i8;
            }
        }
        int i12 = i9;
        if (this.v == -1) {
            long j = this.x;
            this.v = j;
            if (this.B.compareTo(BigInteger.valueOf(j - ((long) this.s))) < 0) {
                throw new IllegalBlockSizeException("inputLength too long for being decoded with given nonce");
            }
            try {
                this.y.flush();
                c();
                byte[] byteArray = this.y.toByteArray();
                for (int i13 = 0; i13 < byteArray.length; i13 += 16) {
                    this.p.b(byteArray, i13, 16, this.z, 0);
                }
            } catch (IOException unused) {
                throw new IllegalBlockSizeException("I/O-Exception when writing input data.");
            }
        }
        byte[] bArr4 = new byte[16];
        this.c.b(this.A, 0, 16, bArr4, 0);
        int i14 = this.s;
        byte[] bArr5 = new byte[i14];
        if (this.n) {
            CryptoUtils.xorBlock(bArr4, 0, this.t, 0, bArr5, 0, i14);
        } else {
            CryptoUtils.xorBlock(bArr4, 0, bArr, i12, bArr5, 0, i14);
        }
        if (!CryptoUtils.secureEqualsBlock(this.z, 0, bArr5, 0, this.s)) {
            throw new IllegalBlockSizeException("MAC verification not successful!");
        }
    }

    @Override // iaik.security.cipher.t
    AlgorithmParameters e() {
        if (this.d == 7) {
            try {
                if (!this.n) {
                    CCMParameterSpec cCMParameterSpec = new CCMParameterSpec(this.v, this.q, this.r, this.s);
                    AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("CCM", "IAIK");
                    algorithmParameters.init(cCMParameterSpec);
                    return algorithmParameters;
                }
                byte[] bArr = this.t;
                CCMCMSParameterSpec cCMCMSParameterSpec = bArr != null ? new CCMCMSParameterSpec(this.v, this.q, this.r, bArr) : new CCMCMSParameterSpec(this.v, this.q, this.r, this.s);
                AlgorithmParameters algorithmParameters2 = AlgorithmParameters.getInstance("CCMCMS", "IAIK");
                algorithmParameters2.init(cCMCMSParameterSpec);
                return algorithmParameters2;
            } catch (Exception unused) {
            }
        }
        return null;
    }
}
