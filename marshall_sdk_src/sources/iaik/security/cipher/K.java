package iaik.security.cipher;

import com.ftdi.j2xx.ft4222.FT_4222_Defines;
import iaik.security.ssl.SSLContext;
import iaik.utils.CriticalObject;
import iaik.utils.CryptoUtils;
import java.security.InvalidKeyException;

/* JADX INFO: loaded from: classes.dex */
final class K extends AbstractC0028i {
    private static int[] A;
    private static int[] B;
    private static int[] C;
    private static final int[] p = new int[256];
    private static final int[] q = new int[256];
    private static final int[] r = new int[256];
    private static final int[] s = new int[256];
    private static final int[] t = new int[256];
    private static final int[] u = new int[256];
    private static int[][] v;
    private static int[][] w;
    private static int[][] x;
    private static int[][] y;
    private static int[] z;
    private final int[] b;
    private final int[] c;
    private final int[] m;
    private final int[] n;
    private final int[] o;

    static {
        f();
    }

    K() {
        super("Twofish", 2);
        this.b = new int[40];
        this.c = new int[1024];
        this.m = new int[16];
        this.n = new int[16];
        this.o = new int[4];
    }

    private static int a(int i) {
        return B[i & 3] ^ ((i >> 2) ^ i);
    }

    private static int a(int i, int[] iArr, int i2) {
        int i3;
        int i4;
        if (i2 == 2) {
            int[] iArr2 = r;
            int[] iArr3 = p;
            int i5 = iArr2[iArr[0] ^ iArr3[iArr[4] ^ iArr3[i]]];
            int[] iArr4 = s;
            int[] iArr5 = q;
            i3 = (i5 ^ iArr4[iArr[1] ^ iArr3[iArr[5] ^ iArr5[i]]]) ^ t[iArr5[iArr[6] ^ iArr3[i]] ^ iArr[2]];
            i4 = u[iArr5[iArr[7] ^ iArr5[i]] ^ iArr[3]];
        } else if (i2 == 3) {
            int[] iArr6 = r;
            int[] iArr7 = p;
            int[] iArr8 = q;
            i3 = (iArr6[iArr[0] ^ iArr7[iArr[4] ^ iArr7[iArr[8] ^ iArr8[i]]]] ^ s[iArr[1] ^ iArr7[iArr[5] ^ iArr8[iArr8[i] ^ iArr[9]]]]) ^ t[iArr8[iArr[6] ^ iArr7[iArr7[i] ^ iArr[10]]] ^ iArr[2]];
            i4 = u[iArr8[iArr[7] ^ iArr8[iArr7[i] ^ iArr[11]]] ^ iArr[3]];
        } else {
            int[] iArr9 = r;
            int[] iArr10 = p;
            int[] iArr11 = q;
            i3 = (iArr9[iArr[0] ^ iArr10[iArr[4] ^ iArr10[iArr[8] ^ iArr11[iArr11[i] ^ iArr[12]]]]] ^ s[iArr[1] ^ iArr10[iArr[5] ^ iArr11[iArr11[iArr10[i] ^ iArr[13]] ^ iArr[9]]]]) ^ t[iArr11[iArr[6] ^ iArr10[iArr10[iArr10[i] ^ iArr[14]] ^ iArr[10]]] ^ iArr[2]];
            i4 = u[iArr11[iArr[7] ^ iArr11[iArr10[iArr11[i] ^ iArr[15]] ^ iArr[11]]] ^ iArr[3]];
        }
        return i3 ^ i4;
    }

    private void a(int[] iArr) {
        int i = iArr[0];
        int i2 = i & 255;
        int i3 = (i >>> 8) & 255;
        int i4 = (i >>> 16) & 255;
        int i5 = i >>> 24;
        int i6 = iArr[1];
        int i7 = i6 & 255;
        int i8 = (i6 >>> 8) & 255;
        int i9 = (i6 >>> 16) & 255;
        int i10 = i6 >>> 24;
        for (int i11 = 0; i11 < 256; i11++) {
            int[] iArr2 = this.c;
            int[] iArr3 = r;
            int[] iArr4 = p;
            iArr2[i11] = iArr3[iArr4[iArr4[i11] ^ i7] ^ i2];
            int[] iArr5 = s;
            int[] iArr6 = q;
            iArr2[i11 | 256] = iArr5[iArr4[iArr6[i11] ^ i8] ^ i3];
            iArr2[i11 | 512] = t[iArr6[iArr4[i11] ^ i9] ^ i4];
            iArr2[i11 | SSLContext.VERSION_SSL30] = u[iArr6[iArr6[i11] ^ i10] ^ i5];
        }
    }

    private static int[] a(long j) {
        int[] iArr = new int[16];
        for (int i = 15; i >= 0; i--) {
            iArr[i] = ((int) j) & 15;
            j >>= 4;
        }
        return iArr;
    }

    private static int b(int i) {
        return C[i & 3] ^ (((i >> 1) ^ i) ^ (i >> 2));
    }

    private static int b(int i, int i2) {
        for (int i3 = 0; i3 < 8; i3++) {
            int i4 = i2 >>> 24;
            int i5 = (i2 << 8) | (i >>> 24);
            i <<= 8;
            int i6 = i4 << 1;
            if ((i4 & 128) != 0) {
                i6 ^= 333;
            }
            int i7 = i5 ^ ((i6 << 16) ^ i4);
            int i8 = i6 ^ (i4 >>> 1);
            if ((i4 & 1) != 0) {
                i8 ^= FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_SET_CLK30K_TRIM_REG;
            }
            i2 = i7 ^ ((i8 << 8) | (i8 << 24));
        }
        return i2;
    }

    private void b(int[] iArr) {
        int i = iArr[0];
        int i2 = i & 255;
        int i3 = (i >>> 8) & 255;
        int i4 = (i >>> 16) & 255;
        int i5 = i >>> 24;
        int i6 = iArr[1];
        int i7 = i6 & 255;
        int i8 = (i6 >>> 8) & 255;
        int i9 = (i6 >>> 16) & 255;
        int i10 = i6 >>> 24;
        int i11 = iArr[2];
        int i12 = i11 & 255;
        int i13 = (i11 >>> 8) & 255;
        int i14 = (i11 >>> 16) & 255;
        int i15 = i11 >>> 24;
        for (int i16 = 0; i16 < 256; i16++) {
            int[] iArr2 = this.c;
            int[] iArr3 = r;
            int[] iArr4 = p;
            int[] iArr5 = q;
            iArr2[i16] = iArr3[iArr4[iArr4[iArr5[i16] ^ i12] ^ i7] ^ i2];
            iArr2[i16 | 256] = s[iArr4[iArr5[iArr5[i16] ^ i13] ^ i8] ^ i3];
            iArr2[i16 | 512] = t[iArr5[iArr4[iArr4[i16] ^ i14] ^ i9] ^ i4];
            iArr2[i16 | SSLContext.VERSION_SSL30] = u[iArr5[iArr5[iArr4[i16] ^ i15] ^ i10] ^ i5];
        }
    }

    private static int c(int i, int i2) {
        int i3 = i2 >> 4;
        int i4 = i2 & 15;
        int i5 = i3 ^ i4;
        int[] iArr = z;
        int i6 = iArr[i4];
        int[] iArr2 = A;
        int i7 = i6 ^ iArr2[i3];
        int i8 = v[i][i5];
        int i9 = w[i][i7];
        int i10 = i8 ^ i9;
        int i11 = iArr[i9] ^ iArr2[i8];
        return (y[i][i11] << 4) | x[i][i10];
    }

    private void c(int[] iArr) {
        int i = iArr[0];
        int i2 = i & 255;
        int i3 = (i >>> 8) & 255;
        int i4 = (i >>> 16) & 255;
        int i5 = i >>> 24;
        int i6 = iArr[1];
        int i7 = i6 & 255;
        int i8 = (i6 >>> 8) & 255;
        int i9 = (i6 >>> 16) & 255;
        int i10 = i6 >>> 24;
        int i11 = iArr[2];
        int i12 = i11 & 255;
        int i13 = (i11 >>> 8) & 255;
        int i14 = (i11 >>> 16) & 255;
        int i15 = i11 >>> 24;
        int i16 = iArr[3];
        int i17 = i16 & 255;
        int i18 = (i16 >>> 8) & 255;
        int i19 = (i16 >>> 16) & 255;
        int i20 = i16 >>> 24;
        int i21 = 0;
        while (i21 < 256) {
            int i22 = i15;
            int[] iArr2 = this.c;
            int[] iArr3 = r;
            int[] iArr4 = p;
            int[] iArr5 = q;
            iArr2[i21] = iArr3[iArr4[iArr4[iArr5[iArr5[i21] ^ i17] ^ i12] ^ i7] ^ i2];
            iArr2[i21 | 256] = s[iArr4[iArr5[iArr5[iArr4[i21] ^ i18] ^ i13] ^ i8] ^ i3];
            iArr2[i21 | 512] = t[iArr5[iArr4[iArr4[iArr4[i21] ^ i19] ^ i14] ^ i9] ^ i4];
            iArr2[i21 | SSLContext.VERSION_SSL30] = u[iArr5[iArr5[iArr4[iArr5[i21] ^ i20] ^ i22] ^ i10] ^ i5];
            i21++;
            i15 = i22;
            i2 = i2;
        }
    }

    private static void f() {
        v = new int[][]{a(-9116007809998525276L), a(2935774584765680325L)};
        w = new int[][]{a(-1389340462096682851L), a(2173915046082902280L)};
        x = new int[][]{a(-5017452466230057871L), a(5509334570087492415L)};
        y = new int[][]{a(-2885661194978294326L), a(-5093074343643111286L)};
        z = a(583544060893818495L);
        A = a(660706911651612135L);
        B = new int[]{0, 90, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_GET_SFR, 238};
        C = new int[]{0, 238, FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_GET_SFR, 90};
        for (int i = 0; i < 256; i++) {
            p[i] = c(0, i);
            q[i] = c(1, i);
        }
        for (int i2 = 0; i2 < 256; i2++) {
            int i3 = q[i2];
            int iA = a(i3);
            int iB = b(i3);
            int i4 = iB << 24;
            r[i2] = (iA << 8) + i3 + (iB << 16) + i4;
            t[i2] = iA + (iB << 8) + (i3 << 16) + i4;
            int i5 = p[i2];
            int iA2 = a(i5);
            int iB2 = b(i5);
            s[i2] = (iB2 << 8) + iB2 + (iA2 << 16) + (i5 << 24);
            u[i2] = (i5 << 8) + iA2 + (iB2 << 16) + (iA2 << 24);
        }
        int[][] iArr = (int[][]) null;
        y = iArr;
        x = iArr;
        w = iArr;
        v = iArr;
        C = null;
        B = null;
        A = null;
        z = null;
    }

    @Override // iaik.security.cipher.AbstractC0028i
    protected void a(int i, byte[] bArr) throws InvalidKeyException {
        int length = bArr.length;
        int i2 = length >> 3;
        if (length != 16 && length != 24 && length != 32) {
            throw new InvalidKeyException("Key must be 128, 192, or 256 bit long!");
        }
        int i3 = 0;
        CryptoUtils.squashBytesToIntsLE(bArr, 0, this.b, 0, i2 * 2);
        int i4 = i2 - 1;
        int i5 = 0;
        while (i4 >= 0) {
            int[] iArr = this.o;
            int[] iArr2 = this.b;
            int i6 = i5 + 1;
            iArr[i4] = b(iArr2[i5], iArr2[i6]);
            i4--;
            i5 = i6 + 1;
        }
        if (i2 == 2) {
            a(this.o);
        } else if (i2 != 3) {
            c(this.o);
        } else {
            b(this.o);
        }
        int i7 = 0;
        int i8 = 0;
        while (i7 < length) {
            int[] iArr3 = this.m;
            int i9 = i8 + 1;
            int i10 = i7 + 1;
            iArr3[i8] = bArr[i7] & 255;
            int i11 = i9 + 1;
            int i12 = i10 + 1;
            iArr3[i9] = bArr[i10] & 255;
            int i13 = i11 + 1;
            int i14 = i12 + 1;
            iArr3[i11] = bArr[i12] & 255;
            int i15 = i14 + 1;
            iArr3[i13] = bArr[i14] & 255;
            int i16 = i13 - 3;
            int[] iArr4 = this.n;
            int i17 = i16 + 1;
            int i18 = i15 + 1;
            iArr4[i16] = bArr[i15] & 255;
            int i19 = i17 + 1;
            int i20 = i18 + 1;
            iArr4[i17] = bArr[i18] & 255;
            int i21 = i19 + 1;
            int i22 = i20 + 1;
            iArr4[i19] = bArr[i20] & 255;
            iArr4[i21] = bArr[i22] & 255;
            i8 = i21 + 1;
            i7 = i22 + 1;
        }
        while (i3 < 40) {
            int iA = a(i3, this.m, i2);
            int i23 = i3 + 1;
            int iA2 = a(i23, this.n, i2);
            int i24 = (iA2 >>> 24) | (iA2 << 8);
            int i25 = iA + i24;
            int[] iArr5 = this.b;
            iArr5[i3] = i25;
            int i26 = i24 + i25;
            i3 = i23 + 1;
            iArr5[i23] = (i26 << 9) | (i26 >>> 23);
        }
    }

    @Override // iaik.security.cipher.AbstractC0028i
    protected void b() {
        int[] iArr = this.c;
        int i = this.a[0] ^ this.b[0];
        int i2 = this.a[1] ^ this.b[1];
        int i3 = this.a[2] ^ this.b[2];
        int i4 = this.a[3];
        int[] iArr2 = this.b;
        int i5 = i4 ^ iArr2[3];
        int i6 = 8;
        while (i6 < 40) {
            int i7 = ((iArr[i2 >>> 24] ^ iArr[(i2 & 255) | 256]) ^ iArr[((i2 >>> 8) & 255) | 512]) ^ iArr[((i2 >>> 16) & 255) | SSLContext.VERSION_SSL30];
            int i8 = ((iArr[i & 255] ^ iArr[((i >>> 8) & 255) | 256]) ^ iArr[((i >>> 16) & 255) | 512]) ^ iArr[(i >>> 24) | SSLContext.VERSION_SSL30];
            int i9 = i6 + 1;
            int i10 = i3 ^ ((iArr2[i6] + i8) + i7);
            int i11 = (i5 << 1) | (i5 >>> 31);
            i3 = (i10 << 31) | (i10 >>> 1);
            int i12 = i9 + 1;
            i5 = i11 ^ ((iArr2[i9] + i8) + (i7 << 1));
            int i13 = ((iArr[i5 >>> 24] ^ iArr[(i5 & 255) | 256]) ^ iArr[((i5 >>> 8) & 255) | 512]) ^ iArr[((i5 >>> 16) & 255) | SSLContext.VERSION_SSL30];
            int i14 = ((iArr[i3 & 255] ^ iArr[((i3 >>> 8) & 255) | 256]) ^ iArr[((i3 >>> 16) & 255) | 512]) ^ iArr[(i3 >>> 24) | SSLContext.VERSION_SSL30];
            int i15 = i12 + 1;
            int i16 = i ^ ((iArr2[i12] + i14) + i13);
            int i17 = (i2 << 1) | (i2 >>> 31);
            i = (i16 << 31) | (i16 >>> 1);
            i6 = i15 + 1;
            i2 = i17 ^ ((iArr2[i15] + i14) + (i13 << 1));
        }
        this.a[0] = i3 ^ iArr2[4];
        this.a[1] = iArr2[5] ^ i5;
        this.a[2] = i ^ iArr2[6];
        this.a[3] = iArr2[7] ^ i2;
    }

    @Override // iaik.security.cipher.AbstractC0028i
    protected void c() {
        int[] iArr = this.c;
        int i = this.a[0] ^ this.b[4];
        int i2 = this.a[1] ^ this.b[5];
        int i3 = this.a[2] ^ this.b[6];
        int i4 = this.a[3];
        int[] iArr2 = this.b;
        int i5 = i4 ^ iArr2[7];
        int i6 = 39;
        while (i6 >= 11) {
            int i7 = ((iArr[i2 >>> 24] ^ iArr[(i2 & 255) | 256]) ^ iArr[((i2 >>> 8) & 255) | 512]) ^ iArr[((i2 >>> 16) & 255) | SSLContext.VERSION_SSL30];
            int i8 = ((iArr[i & 255] ^ iArr[((i >>> 8) & 255) | 256]) ^ iArr[((i >>> 16) & 255) | 512]) ^ iArr[(i >>> 24) | SSLContext.VERSION_SSL30];
            int i9 = i6 - 1;
            int i10 = i5 ^ ((iArr2[i6] + i8) + (i7 << 1));
            i5 = (i10 >>> 1) | (i10 << 31);
            int i11 = i9 - 1;
            i3 = ((i3 >>> 31) | (i3 << 1)) ^ ((iArr2[i9] + i8) + i7);
            int i12 = ((iArr[i5 >>> 24] ^ iArr[(i5 & 255) | 256]) ^ iArr[((i5 >>> 8) & 255) | 512]) ^ iArr[((i5 >>> 16) & 255) | SSLContext.VERSION_SSL30];
            int i13 = ((iArr[i3 & 255] ^ iArr[((i3 >>> 8) & 255) | 256]) ^ iArr[((i3 >>> 16) & 255) | 512]) ^ iArr[(i3 >>> 24) | SSLContext.VERSION_SSL30];
            int i14 = i11 - 1;
            int i15 = i2 ^ ((iArr2[i11] + i13) + (i12 << 1));
            i2 = (i15 >>> 1) | (i15 << 31);
            i6 = i14 - 1;
            i = ((i >>> 31) | (i << 1)) ^ ((iArr2[i14] + i13) + i12);
        }
        this.a[0] = i3 ^ iArr2[0];
        this.a[1] = iArr2[1] ^ i5;
        this.a[2] = i ^ iArr2[2];
        this.a[3] = iArr2[3] ^ i2;
    }

    @Override // iaik.security.cipher.AbstractC0028i
    public void d() {
        super.d();
        CriticalObject.destroy(this.b);
        CriticalObject.destroy(this.c);
        CriticalObject.destroy(this.m);
        CriticalObject.destroy(this.n);
        CriticalObject.destroy(this.o);
    }
}
