package iaik.security.cipher;

import iaik.utils.CryptoUtils;
import java.security.InvalidKeyException;

/* JADX INFO: loaded from: classes.dex */
final class G extends AbstractC0028i {
    private static int[] D;
    private static int[] E;
    private int B;
    private int[] C;
    static final int[] b = new int[256];
    static final int[] c = new int[256];
    static final int[] m = new int[256];
    static final int[] n = new int[256];
    static final int[] o = new int[256];
    static final int[] p = new int[256];
    static final int[] q = new int[256];
    static final int[] r = new int[256];
    static final int[] s = new int[256];
    static final int[] t = new int[256];
    static final int[] u = new int[256];
    static final int[] v = new int[256];
    static final int[] w = new int[256];
    static final int[] x = new int[256];
    static final int[] y = new int[256];
    static final int[] z = new int[256];
    static final int[] A = new int[30];

    static {
        f();
    }

    G() {
        super("Rijndael", 1);
    }

    protected static int[] a(int i, byte[] bArr, int[] iArr, int i2) throws InvalidKeyException {
        int i3;
        int[] iArr2 = iArr;
        int length = bArr.length >> 2;
        if ((bArr.length & 3) != 0 || length < 4 || length > 8) {
            throw new InvalidKeyException("Key must be a multiple of 32 bit between 128 and 256!");
        }
        if (i2 == 16) {
            i3 = ((length + 6) + 1) << 2;
        } else {
            if (i2 != 32) {
                throw new RuntimeException("Invalid blocksize, must be 16 or 32!");
            }
            i3 = 120;
        }
        if (iArr2 == null || iArr2.length != i3) {
            iArr2 = new int[i3];
        }
        int i4 = 0;
        CryptoUtils.squashBytesToInts(bArr, 0, iArr2, 0, length);
        int i5 = length;
        while (i5 < i3) {
            int i6 = iArr2[i5 - 1];
            int i7 = i4 + 1;
            int i8 = (A[i4] ^ (n[(i6 >>> 16) & 255] ^ ((b[i6 >>> 24] ^ c[i6 & 255]) ^ m[(i6 >>> 8) & 255]))) ^ iArr2[i5 - length];
            iArr2[i5] = i8;
            i5++;
            int i9 = 1;
            while (i5 < i3 && i9 < length) {
                if (length > 6 && i9 == 4) {
                    i8 = n[i8 >>> 24] ^ ((b[i8 & 255] ^ c[(i8 >>> 8) & 255]) ^ m[(i8 >>> 16) & 255]);
                }
                i9++;
                i8 ^= iArr2[i5 - length];
                iArr2[i5] = i8;
                i5++;
            }
            i4 = i7;
        }
        if (i == 2) {
            int i10 = i2 >> 2;
            for (int i11 = i10; i11 < i3 - i10; i11++) {
                int i12 = iArr2[i11];
                int i13 = ((i12 & 2139062143) << 1) ^ (((i12 & (-2139062144)) >>> 7) * 27);
                int i14 = ((i13 & 2139062143) << 1) ^ (((i13 & (-2139062144)) >>> 7) * 27);
                int i15 = ((2139062143 & i14) << 1) ^ ((((-2139062144) & i14) >>> 7) * 27);
                int i16 = i12 ^ i15;
                iArr2[i11] = c(i16, 24) ^ (((i15 ^ (i13 ^ i14)) ^ c(i13 ^ i16, 8)) ^ c(i14 ^ i16, 16));
            }
        }
        return iArr2;
    }

    private static int b(int i, int i2) {
        int i3 = i2 & 31;
        return (i << (32 - i3)) | (i >>> i3);
    }

    private static int c(int i, int i2) {
        int i3 = i2 & 31;
        return (i >>> (32 - i3)) | (i << i3);
    }

    private static final int d(int i, int i2) {
        if (i == 0 || i2 == 0) {
            return 0;
        }
        int[] iArr = D;
        int i3 = iArr[i] + iArr[i2];
        return i3 < 255 ? E[i3] : E[i3 - 255];
    }

    private static void f() {
        D = new int[256];
        E = new int[256];
        int[] iArr = new int[256];
        int[] iArr2 = new int[256];
        int i = 1;
        for (int i2 = 0; i2 < 256; i2++) {
            E[i2] = i;
            D[i] = i2;
            i = ((i & 128) != 0 ? 283 : 0) ^ ((i << 1) ^ i);
        }
        D[1] = 0;
        int i3 = 1;
        for (int i4 = 0; i4 < 30; i4++) {
            A[i4] = i3 << 24;
            i3 = ((i3 & 128) != 0 ? 27 : 0) ^ (i3 << 1);
        }
        int i5 = 0;
        while (i5 < 256) {
            int i6 = i5 != 0 ? E[255 - D[i5]] : 0;
            int i7 = (i6 >>> 7) | (i6 << 1);
            int i8 = i6 ^ i7;
            int i9 = (i7 << 1) | (i7 >>> 7);
            int i10 = i8 ^ i9;
            int i11 = (i9 << 1) | (i9 >>> 7);
            int i12 = ((i10 ^ i11) ^ (((i11 << 1) | (i11 >>> 7)) ^ 99)) & 255;
            iArr[i5] = i12;
            iArr2[i12] = i5;
            i5++;
        }
        for (int i13 = 0; i13 < 256; i13++) {
            int i14 = iArr[i13];
            b[i13] = i14;
            c[i13] = c(i14, 8);
            m[i13] = c(i14, 16);
            n[i13] = c(i14, 24);
            int iD = (d(2, i14) << 24) | d(3, i14) | (i14 << 8) | (i14 << 16);
            o[i13] = iD;
            p[i13] = b(iD, 8);
            q[i13] = b(iD, 16);
            r[i13] = b(iD, 24);
            int i15 = iArr2[i13];
            s[i13] = i15;
            t[i13] = c(i15, 8);
            u[i13] = c(i15, 16);
            v[i13] = c(i15, 24);
            int iD2 = (d(14, i15) << 24) | d(11, i15) | (d(13, i15) << 8) | (d(9, i15) << 16);
            w[i13] = iD2;
            x[i13] = b(iD2, 8);
            y[i13] = b(iD2, 16);
            z[i13] = b(iD2, 24);
        }
        D = null;
        E = null;
    }

    @Override // iaik.security.cipher.AbstractC0028i
    protected void a(int i, byte[] bArr) throws InvalidKeyException {
        this.C = a(i, bArr, this.C, 16);
        this.B = (bArr.length >> 2) + 6;
    }

    @Override // iaik.security.cipher.AbstractC0028i
    protected void b() {
        int i;
        int i2 = this.a[0] ^ this.C[0];
        int i3 = this.a[1] ^ this.C[1];
        int i4 = 2;
        int i5 = this.a[2] ^ this.C[2];
        int i6 = this.a[3] ^ this.C[3];
        int i7 = 4;
        while (true) {
            i = this.B;
            if (i7 >= ((i - 1) << i4)) {
                break;
            }
            int[] iArr = o;
            int i8 = iArr[i2 >>> 24];
            int[] iArr2 = p;
            int i9 = i8 ^ iArr2[(i3 >>> 16) & 255];
            int[] iArr3 = q;
            int i10 = iArr3[(i5 >>> 8) & 255];
            int[] iArr4 = r;
            int i11 = (iArr4[i6 & 255] ^ i10) ^ i9;
            int[] iArr5 = this.C;
            int i12 = i7 + 1;
            int i13 = i11 ^ iArr5[i7];
            int i14 = i12 + 1;
            int i15 = ((iArr4[i2 & 255] ^ iArr3[(i6 >>> 8) & 255]) ^ (iArr2[(i5 >>> 16) & 255] ^ iArr[i3 >>> 24])) ^ iArr5[i12];
            int i16 = i14 + 1;
            int i17 = ((iArr4[i3 & 255] ^ iArr3[(i2 >>> 8) & 255]) ^ (iArr[i5 >>> 24] ^ iArr2[(i6 >>> 16) & 255])) ^ iArr5[i14];
            int i18 = (iArr2[(i2 >>> 16) & 255] ^ iArr[i6 >>> 24]) ^ (iArr3[(i3 >>> 8) & 255] ^ iArr4[i5 & 255]);
            int i19 = i16 + 1;
            int i20 = i18 ^ iArr5[i16];
            int i21 = i19 + 1;
            int i22 = iArr5[i19] ^ ((iArr[i13 >>> 24] ^ iArr2[(i15 >>> 16) & 255]) ^ (iArr3[(i17 >>> 8) & 255] ^ iArr4[i20 & 255]));
            int i23 = i21 + 1;
            int i24 = ((iArr[i15 >>> 24] ^ iArr2[(i17 >>> 16) & 255]) ^ (iArr3[(i20 >>> 8) & 255] ^ iArr4[i13 & 255])) ^ iArr5[i21];
            int i25 = i23 + 1;
            int i26 = ((iArr[i17 >>> 24] ^ iArr2[(i20 >>> 16) & 255]) ^ (iArr3[(i13 >>> 8) & 255] ^ iArr4[i15 & 255])) ^ iArr5[i23];
            i6 = ((iArr[i20 >>> 24] ^ iArr2[(i13 >>> 16) & 255]) ^ (iArr3[(i15 >>> 8) & 255] ^ iArr4[i17 & 255])) ^ iArr5[i25];
            i7 = i25 + 1;
            i2 = i22;
            i3 = i24;
            i5 = i26;
            i4 = 2;
        }
        if ((i & 1) == 0) {
            int[] iArr6 = o;
            int i27 = iArr6[i2 >>> 24];
            int[] iArr7 = p;
            int i28 = i27 ^ iArr7[(i3 >>> 16) & 255];
            int[] iArr8 = q;
            int i29 = i28 ^ iArr8[(i5 >>> 8) & 255];
            int[] iArr9 = r;
            int i30 = i29 ^ iArr9[i6 & 255];
            int[] iArr10 = this.C;
            int i31 = i7 + 1;
            int i32 = i30 ^ iArr10[i7];
            int i33 = i31 + 1;
            int i34 = (((iArr6[i3 >>> 24] ^ iArr7[(i5 >>> 16) & 255]) ^ iArr8[(i6 >>> 8) & 255]) ^ iArr9[i2 & 255]) ^ iArr10[i31];
            int i35 = i33 + 1;
            int i36 = (((iArr6[i5 >>> 24] ^ iArr7[(i6 >>> 16) & 255]) ^ iArr8[(i2 >>> 8) & 255]) ^ iArr9[i3 & 255]) ^ iArr10[i33];
            i6 = (((iArr7[(i2 >>> 16) & 255] ^ iArr6[i6 >>> 24]) ^ iArr8[(i3 >>> 8) & 255]) ^ iArr9[i5 & 255]) ^ iArr10[i35];
            i2 = i32;
            i3 = i34;
            i5 = i36;
            i7 = i35 + 1;
        }
        int[] iArr11 = this.a;
        int[] iArr12 = n;
        int i37 = iArr12[i2 >>> 24];
        int[] iArr13 = m;
        int i38 = i37 ^ iArr13[(i3 >>> 16) & 255];
        int[] iArr14 = c;
        int i39 = i38 ^ iArr14[(i5 >>> 8) & 255];
        int[] iArr15 = b;
        int i40 = i7 + 1;
        iArr11[0] = (i39 ^ iArr15[i6 & 255]) ^ this.C[i7];
        int i41 = i40 + 1;
        this.a[1] = (((iArr12[i3 >>> 24] ^ iArr13[(i5 >>> 16) & 255]) ^ iArr14[(i6 >>> 8) & 255]) ^ iArr15[i2 & 255]) ^ this.C[i40];
        this.a[2] = (((iArr12[i5 >>> 24] ^ iArr13[(i6 >>> 16) & 255]) ^ iArr14[(i2 >>> 8) & 255]) ^ iArr15[i3 & 255]) ^ this.C[i41];
        this.a[3] = (((iArr13[(i2 >>> 16) & 255] ^ iArr12[i6 >>> 24]) ^ iArr14[(i3 >>> 8) & 255]) ^ iArr15[i5 & 255]) ^ this.C[i41 + 1];
    }

    @Override // iaik.security.cipher.AbstractC0028i
    protected void c() {
        int length = this.C.length - 1;
        int i = this.a[3] ^ this.C[length];
        int i2 = length - 1;
        int i3 = this.a[2] ^ this.C[i2];
        int i4 = i2 - 1;
        int i5 = this.a[1] ^ this.C[i4];
        int i6 = i4 - 1;
        int i7 = this.a[0] ^ this.C[i6];
        int i8 = i6 - 4;
        while (i8 >= 8) {
            int[] iArr = w;
            int i9 = iArr[i7 >>> 24];
            int[] iArr2 = x;
            int i10 = i9 ^ iArr2[(i >>> 16) & 255];
            int[] iArr3 = y;
            int i11 = iArr3[(i3 >>> 8) & 255];
            int[] iArr4 = z;
            int i12 = (i11 ^ iArr4[i5 & 255]) ^ i10;
            int[] iArr5 = this.C;
            int i13 = i8 + 1;
            int i14 = iArr5[i8] ^ i12;
            int i15 = i13 + 1;
            int i16 = ((iArr[i5 >>> 24] ^ iArr2[(i7 >>> 16) & 255]) ^ (iArr3[(i >>> 8) & 255] ^ iArr4[i3 & 255])) ^ iArr5[i13];
            int i17 = i15 + 1;
            int i18 = ((iArr4[i & 255] ^ iArr3[(i7 >>> 8) & 255]) ^ (iArr[i3 >>> 24] ^ iArr2[(i5 >>> 16) & 255])) ^ iArr5[i15];
            int i19 = ((iArr[i >>> 24] ^ iArr2[(i3 >>> 16) & 255]) ^ (iArr3[(i5 >>> 8) & 255] ^ iArr4[i7 & 255])) ^ iArr5[i17];
            int i20 = i17 - 7;
            int i21 = i20 + 1;
            i7 = iArr5[i20] ^ ((iArr[i14 >>> 24] ^ iArr2[(i19 >>> 16) & 255]) ^ (iArr3[(i18 >>> 8) & 255] ^ iArr4[i16 & 255]));
            int i22 = i21 + 1;
            i5 = iArr5[i21] ^ ((iArr[i16 >>> 24] ^ iArr2[(i14 >>> 16) & 255]) ^ (iArr3[(i19 >>> 8) & 255] ^ iArr4[i18 & 255]));
            int i23 = i22 + 1;
            i3 = ((iArr[i18 >>> 24] ^ iArr2[(i16 >>> 16) & 255]) ^ (iArr3[(i14 >>> 8) & 255] ^ iArr4[i19 & 255])) ^ iArr5[i22];
            i = iArr5[i23] ^ ((iArr4[i14 & 255] ^ iArr3[(i16 >>> 8) & 255]) ^ (iArr[i19 >>> 24] ^ iArr2[(i18 >>> 16) & 255]));
            i8 = i23 - 7;
        }
        if ((this.B & 1) == 0) {
            int[] iArr6 = w;
            int i24 = iArr6[i7 >>> 24];
            int[] iArr7 = x;
            int i25 = i24 ^ iArr7[(i >>> 16) & 255];
            int[] iArr8 = y;
            int i26 = i25 ^ iArr8[(i3 >>> 8) & 255];
            int[] iArr9 = z;
            int i27 = i26 ^ iArr9[i5 & 255];
            int[] iArr10 = this.C;
            int i28 = i27 ^ iArr10[4];
            int i29 = (((iArr6[i5 >>> 24] ^ iArr7[(i7 >>> 16) & 255]) ^ iArr8[(i >>> 8) & 255]) ^ iArr9[i3 & 255]) ^ iArr10[5];
            int i30 = (((iArr6[i3 >>> 24] ^ iArr7[(i5 >>> 16) & 255]) ^ iArr8[(i7 >>> 8) & 255]) ^ iArr9[i & 255]) ^ iArr10[6];
            i = iArr10[7] ^ (((iArr6[i >>> 24] ^ iArr7[(i3 >>> 16) & 255]) ^ iArr8[(i5 >>> 8) & 255]) ^ iArr9[i7 & 255]);
            i7 = i28;
            i5 = i29;
            i3 = i30;
        }
        int[] iArr11 = this.a;
        int[] iArr12 = v;
        int i31 = iArr12[i7 >>> 24];
        int[] iArr13 = u;
        int i32 = i31 ^ iArr13[(i >>> 16) & 255];
        int[] iArr14 = t;
        int i33 = i32 ^ iArr14[(i3 >>> 8) & 255];
        int[] iArr15 = s;
        iArr11[0] = (i33 ^ iArr15[i5 & 255]) ^ this.C[0];
        this.a[1] = (((iArr12[i5 >>> 24] ^ iArr13[(i7 >>> 16) & 255]) ^ iArr14[(i >>> 8) & 255]) ^ iArr15[i3 & 255]) ^ this.C[1];
        this.a[2] = (((iArr12[i3 >>> 24] ^ iArr13[(i5 >>> 16) & 255]) ^ iArr14[(i7 >>> 8) & 255]) ^ iArr15[i & 255]) ^ this.C[2];
        this.a[3] = (((iArr12[i >>> 24] ^ iArr13[(i3 >>> 16) & 255]) ^ iArr14[(i5 >>> 8) & 255]) ^ iArr15[i7 & 255]) ^ this.C[3];
    }

    @Override // iaik.security.cipher.AbstractC0028i
    public void d() {
        super.d();
        CryptoUtils.zeroBlock(this.C);
        this.C = null;
        this.B = 0;
    }
}
