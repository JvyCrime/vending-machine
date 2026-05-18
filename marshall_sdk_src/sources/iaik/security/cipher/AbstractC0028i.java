package iaik.security.cipher;

import iaik.pkcs.pkcs1.Padding;
import iaik.utils.CryptoUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.NoSuchPaddingException;

/* JADX INFO: renamed from: iaik.security.cipher.i, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
abstract class AbstractC0028i extends t {
    protected final int[] a;
    private final int[] b;
    private final int[] c;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    private byte[] r;
    private byte[] s;
    private final boolean t;

    protected AbstractC0028i(String str, int i) {
        super(str, 16, 16);
        this.b = new int[8];
        int[] iArr = new int[8];
        this.c = iArr;
        this.a = iArr;
        this.t = i == 1;
    }

    private void e(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        while (i2 > 0) {
            if (this.q == 16) {
                int[] iArr = this.c;
                iArr[0] = this.m;
                iArr[1] = this.n;
                iArr[2] = this.o;
                iArr[3] = this.p;
                b();
                if (this.t) {
                    CryptoUtils.spreadIntsToBytes(this.c, 0, this.s, 0, 4);
                    int i4 = this.p + 1;
                    this.p = i4;
                    if (i4 == 0) {
                        int i5 = this.o + 1;
                        this.o = i5;
                        if (i5 == 0) {
                            int i6 = this.n + 1;
                            this.n = i6;
                            if (i6 == 0) {
                                this.m++;
                            }
                        }
                    }
                } else {
                    CryptoUtils.spreadIntsToBytesLE(this.c, 0, this.s, 0, 4);
                    CryptoUtils.increment(this.r);
                    CryptoUtils.squashBytesToIntsLE(this.r, 0, this.c, 0, 4);
                    int[] iArr2 = this.c;
                    this.m = iArr2[0];
                    this.n = iArr2[1];
                    this.o = iArr2[2];
                    this.p = iArr2[3];
                }
                this.q = 0;
            }
            int i7 = this.q;
            int i8 = i2 <= 16 - i7 ? i2 : 16 - i7;
            i2 -= i8;
            while (true) {
                int i9 = i8 - 1;
                if (i8 > 0) {
                    int i10 = i + 1;
                    byte b = bArr[i];
                    byte[] bArr3 = this.s;
                    int i11 = this.q;
                    this.q = i11 + 1;
                    bArr2[i3] = (byte) (b ^ bArr3[i11]);
                    i3++;
                    i8 = i9;
                    i = i10;
                }
            }
        }
    }

    @Override // iaik.security.cipher.t
    void a() {
        if (this.d != 1) {
            int[] iArr = this.b;
            int[] iArr2 = this.c;
            System.arraycopy(iArr, 0, iArr2, 0, iArr2.length);
            if (!this.t && this.d == 6) {
                System.arraycopy(this.e, 0, this.r, 0, 16);
            }
            int[] iArr3 = this.c;
            this.m = iArr3[0];
            this.n = iArr3[1];
            this.o = iArr3[2];
            this.p = iArr3[3];
            this.q = 16;
        }
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (!key.getFormat().equals("RAW")) {
            throw new InvalidKeyException("Key must be RAW!");
        }
        if (this.d != 1) {
            this.e = a(i, algorithmParameterSpec, secureRandom, 16);
            if (this.t) {
                CryptoUtils.squashBytesToInts(this.e, 0, this.c, 0, 4);
            } else {
                CryptoUtils.squashBytesToIntsLE(this.e, 0, this.c, 0, 4);
                if (this.d == 6) {
                    System.arraycopy(this.e, 0, this.r, 0, 16);
                }
            }
            System.arraycopy(this.c, 0, this.b, 0, 4);
            int[] iArr = this.c;
            this.m = iArr[0];
            this.n = iArr[1];
            this.o = iArr[2];
            this.p = iArr[3];
            this.q = 16;
        } else {
            this.e = null;
        }
        if (this.d == 6) {
            i = 1;
        }
        byte[] encoded = key.getEncoded();
        a(i, encoded);
        CryptoUtils.zeroBlock(encoded);
    }

    protected abstract void a(int i, byte[] bArr) throws InvalidKeyException;

    @Override // iaik.security.cipher.t
    void a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (this.d == 6) {
            e(bArr, i, i2, bArr2, i3);
            return;
        }
        if (this.t) {
            CryptoUtils.squashBytesToInts(bArr, i, this.c, 0, 4);
        } else {
            CryptoUtils.squashBytesToIntsLE(bArr, i, this.c, 0, 4);
        }
        if (this.d == 2) {
            int[] iArr = this.c;
            iArr[4] = iArr[0];
            iArr[5] = iArr[1];
            iArr[6] = iArr[2];
            iArr[7] = iArr[3];
            c();
            int[] iArr2 = this.c;
            iArr2[0] = iArr2[0] ^ this.m;
            iArr2[1] = iArr2[1] ^ this.n;
            iArr2[2] = iArr2[2] ^ this.o;
            iArr2[3] = iArr2[3] ^ this.p;
            this.m = iArr2[4];
            this.n = iArr2[5];
            this.o = iArr2[6];
            this.p = iArr2[7];
        } else {
            c();
        }
        if (this.t) {
            CryptoUtils.spreadIntsToBytes(this.c, 0, bArr2, i3, 4);
        } else {
            CryptoUtils.spreadIntsToBytesLE(this.c, 0, bArr2, i3, 4);
        }
    }

    @Override // iaik.security.cipher.t
    boolean a(int i, int i2) {
        this.g = 16;
        if (i == 1 || i == 2) {
            this.d = i;
            return true;
        }
        if (i != 6) {
            this.d = 0;
            return false;
        }
        if (!this.t) {
            this.r = new byte[16];
        }
        this.s = new byte[16];
        this.g = 1;
        this.d = i;
        return true;
    }

    @Override // iaik.security.cipher.t
    boolean a(String str) throws NoSuchPaddingException {
        if (str.equalsIgnoreCase(Padding.PADDING_NONE) || this.d != 6) {
            return true;
        }
        throw new NoSuchPaddingException("Mode CTR must be used with Padding 'NoPadding'.");
    }

    protected abstract void b();

    @Override // iaik.security.cipher.t
    void b(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (this.d == 6) {
            e(bArr, i, i2, bArr2, i3);
            return;
        }
        if (this.t) {
            CryptoUtils.squashBytesToInts(bArr, i, this.c, 0, 4);
        } else {
            CryptoUtils.squashBytesToIntsLE(bArr, i, this.c, 0, 4);
        }
        if (this.d == 2) {
            int[] iArr = this.c;
            iArr[0] = iArr[0] ^ this.m;
            iArr[1] = iArr[1] ^ this.n;
            iArr[2] = iArr[2] ^ this.o;
            iArr[3] = iArr[3] ^ this.p;
            b();
            int[] iArr2 = this.c;
            this.m = iArr2[0];
            this.n = iArr2[1];
            this.o = iArr2[2];
            this.p = iArr2[3];
        } else {
            b();
        }
        if (this.t) {
            CryptoUtils.spreadIntsToBytes(this.c, 0, bArr2, i3, 4);
        } else {
            CryptoUtils.spreadIntsToBytesLE(this.c, 0, bArr2, i3, 4);
        }
    }

    protected abstract void c();

    public void d() {
        CryptoUtils.zeroBlock(this.c);
        CryptoUtils.zeroBlock(this.b);
        if (this.e != null) {
            CryptoUtils.zeroBlock(this.e);
        }
        this.p = 0;
        this.o = 0;
        this.n = 0;
        this.m = 0;
        this.q = 0;
        byte[] bArr = this.s;
        if (bArr != null) {
            CryptoUtils.zeroBlock(bArr);
        }
        byte[] bArr2 = this.r;
        if (bArr2 != null) {
            CryptoUtils.zeroBlock(bArr2);
        }
    }

    protected void finalize() {
        d();
    }
}
