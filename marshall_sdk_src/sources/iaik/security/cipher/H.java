package iaik.security.cipher;

import iaik.utils.CryptoUtils;
import java.security.InvalidKeyException;

/* JADX INFO: loaded from: classes.dex */
final class H extends AbstractC0029j {
    private int[] b;
    private static final int[] c = G.b;
    private static final int[] m = G.c;
    private static final int[] n = G.m;
    private static final int[] o = G.n;
    private static final int[] p = G.o;
    private static final int[] q = G.p;
    private static final int[] r = G.q;
    private static final int[] s = G.r;
    private static final int[] t = G.s;
    private static final int[] u = G.t;
    private static final int[] v = G.u;
    private static final int[] w = G.v;
    private static final int[] x = G.w;
    private static final int[] y = G.x;
    private static final int[] z = G.y;
    private static final int[] A = G.z;

    H() {
        super("Rijndael-256", 1);
    }

    @Override // iaik.security.cipher.AbstractC0029j
    protected void a(int i, byte[] bArr) throws InvalidKeyException {
        this.b = G.a(i, bArr, this.b, 32);
    }

    @Override // iaik.security.cipher.AbstractC0029j
    protected void b() {
        H h = this;
        int i = h.a[0] ^ h.b[0];
        int i2 = h.a[1] ^ h.b[1];
        int i3 = h.a[2] ^ h.b[2];
        int i4 = h.a[3] ^ h.b[3];
        int i5 = h.a[4] ^ h.b[4];
        int i6 = h.a[5] ^ h.b[5];
        int i7 = h.a[6] ^ h.b[6];
        int i8 = h.b[7] ^ h.a[7];
        int i9 = 8;
        while (i9 < 104) {
            int[] iArr = p;
            int i10 = iArr[i >>> 24];
            int[] iArr2 = q;
            int i11 = i10 ^ iArr2[(i2 >>> 16) & 255];
            int[] iArr3 = r;
            int i12 = iArr3[(i4 >>> 8) & 255];
            int[] iArr4 = s;
            int i13 = (iArr4[i5 & 255] ^ i12) ^ i11;
            int[] iArr5 = h.b;
            int i14 = i9 + 1;
            int i15 = i13 ^ iArr5[i9];
            int i16 = (iArr2[(i3 >>> 16) & 255] ^ iArr[i2 >>> 24]) ^ (iArr3[(i5 >>> 8) & 255] ^ iArr4[i6 & 255]);
            int i17 = i14 + 1;
            int i18 = i16 ^ iArr5[i14];
            int i19 = (iArr4[i7 & 255] ^ iArr3[(i6 >>> 8) & 255]) ^ (iArr[i3 >>> 24] ^ iArr2[(i4 >>> 16) & 255]);
            int i20 = i17 + 1;
            int i21 = i19 ^ iArr5[i17];
            int i22 = (iArr4[i8 & 255] ^ iArr3[(i7 >>> 8) & 255]) ^ (iArr[i4 >>> 24] ^ iArr2[(i5 >>> 16) & 255]);
            int i23 = i20 + 1;
            int i24 = i22 ^ iArr5[i20];
            int i25 = (iArr[i5 >>> 24] ^ iArr2[(i6 >>> 16) & 255]) ^ (iArr3[(i8 >>> 8) & 255] ^ iArr4[i & 255]);
            int i26 = i23 + 1;
            int i27 = i25 ^ iArr5[i23];
            int i28 = i26 + 1;
            int i29 = iArr5[i26] ^ ((iArr[i6 >>> 24] ^ iArr2[(i7 >>> 16) & 255]) ^ (iArr3[(i >>> 8) & 255] ^ iArr4[i2 & 255]));
            int i30 = (iArr3[(i2 >>> 8) & 255] ^ iArr4[i3 & 255]) ^ (iArr[i7 >>> 24] ^ iArr2[(i8 >>> 16) & 255]);
            int i31 = i28 + 1;
            int i32 = i30 ^ iArr5[i28];
            int i33 = (iArr2[(i >>> 16) & 255] ^ iArr[i8 >>> 24]) ^ (iArr3[(i3 >>> 8) & 255] ^ iArr4[i4 & 255]);
            int i34 = i31 + 1;
            int i35 = i33 ^ iArr5[i31];
            int i36 = i34 + 1;
            int i37 = iArr5[i34] ^ ((iArr[i15 >>> 24] ^ iArr2[(i18 >>> 16) & 255]) ^ (iArr3[(i24 >>> 8) & 255] ^ iArr4[i27 & 255]));
            int i38 = i36 + 1;
            int i39 = ((iArr[i18 >>> 24] ^ iArr2[(i21 >>> 16) & 255]) ^ (iArr3[(i27 >>> 8) & 255] ^ iArr4[i29 & 255])) ^ iArr5[i36];
            int i40 = i38 + 1;
            int i41 = ((iArr[i21 >>> 24] ^ iArr2[(i24 >>> 16) & 255]) ^ (iArr3[(i29 >>> 8) & 255] ^ iArr4[i32 & 255])) ^ iArr5[i38];
            int i42 = i40 + 1;
            int i43 = ((iArr[i24 >>> 24] ^ iArr2[(i27 >>> 16) & 255]) ^ (iArr3[(i32 >>> 8) & 255] ^ iArr4[i35 & 255])) ^ iArr5[i40];
            int i44 = i42 + 1;
            int i45 = ((iArr[i27 >>> 24] ^ iArr2[(i29 >>> 16) & 255]) ^ (iArr3[(i35 >>> 8) & 255] ^ iArr4[i15 & 255])) ^ iArr5[i42];
            int i46 = i44 + 1;
            int i47 = ((iArr[i29 >>> 24] ^ iArr2[(i32 >>> 16) & 255]) ^ (iArr3[(i15 >>> 8) & 255] ^ iArr4[i18 & 255])) ^ iArr5[i44];
            int i48 = (iArr3[(i18 >>> 8) & 255] ^ iArr4[i21 & 255]) ^ (iArr[i32 >>> 24] ^ iArr2[(i35 >>> 16) & 255]);
            int i49 = i46 + 1;
            int i50 = i48 ^ iArr5[i46];
            int i51 = (iArr4[i24 & 255] ^ iArr3[(i21 >>> 8) & 255]) ^ (iArr[i35 >>> 24] ^ iArr2[(i15 >>> 16) & 255]);
            int i52 = i49 + 1;
            i8 = i51 ^ iArr5[i49];
            i7 = i50;
            i2 = i39;
            i4 = i43;
            h = this;
            i9 = i52;
            i = i37;
            i3 = i41;
            i6 = i47;
            i5 = i45;
        }
        int[] iArr6 = p;
        int i53 = iArr6[i >>> 24];
        int[] iArr7 = q;
        int i54 = i53 ^ iArr7[(i2 >>> 16) & 255];
        int[] iArr8 = r;
        int i55 = iArr8[(i4 >>> 8) & 255];
        int[] iArr9 = s;
        int i56 = i54 ^ (i55 ^ iArr9[i5 & 255]);
        int[] iArr10 = this.b;
        int i57 = i56 ^ iArr10[104];
        int i58 = ((iArr7[(i3 >>> 16) & 255] ^ iArr6[i2 >>> 24]) ^ (iArr9[i6 & 255] ^ iArr8[(i5 >>> 8) & 255])) ^ iArr10[105];
        int i59 = ((iArr9[i7 & 255] ^ iArr8[(i6 >>> 8) & 255]) ^ (iArr6[i3 >>> 24] ^ iArr7[(i4 >>> 16) & 255])) ^ iArr10[106];
        int i60 = ((iArr9[i8 & 255] ^ iArr8[(i7 >>> 8) & 255]) ^ (iArr6[i4 >>> 24] ^ iArr7[(i5 >>> 16) & 255])) ^ iArr10[107];
        int i61 = ((iArr6[i5 >>> 24] ^ iArr7[(i6 >>> 16) & 255]) ^ (iArr8[(i8 >>> 8) & 255] ^ iArr9[i & 255])) ^ iArr10[108];
        int i62 = ((iArr6[i6 >>> 24] ^ iArr7[(i7 >>> 16) & 255]) ^ (iArr8[(i >>> 8) & 255] ^ iArr9[i2 & 255])) ^ iArr10[109];
        int i63 = ((iArr8[(i2 >>> 8) & 255] ^ iArr9[i3 & 255]) ^ (iArr6[i7 >>> 24] ^ iArr7[(i8 >>> 16) & 255])) ^ iArr10[110];
        int i64 = ((iArr6[i8 >>> 24] ^ iArr7[(i >>> 16) & 255]) ^ (iArr8[(i3 >>> 8) & 255] ^ iArr9[i4 & 255])) ^ iArr10[111];
        int[] iArr11 = this.a;
        int[] iArr12 = o;
        int i65 = iArr12[i57 >>> 24];
        int[] iArr13 = n;
        int i66 = i65 ^ iArr13[(i58 >>> 16) & 255];
        int[] iArr14 = m;
        int i67 = i66 ^ iArr14[(i60 >>> 8) & 255];
        int[] iArr15 = c;
        iArr11[0] = (i67 ^ iArr15[i61 & 255]) ^ this.b[112];
        this.a[1] = (((iArr12[i58 >>> 24] ^ iArr13[(i59 >>> 16) & 255]) ^ iArr14[(i61 >>> 8) & 255]) ^ iArr15[i62 & 255]) ^ this.b[113];
        this.a[2] = (((iArr12[i59 >>> 24] ^ iArr13[(i60 >>> 16) & 255]) ^ iArr14[(i62 >>> 8) & 255]) ^ iArr15[i63 & 255]) ^ this.b[114];
        this.a[3] = (((iArr12[i60 >>> 24] ^ iArr13[(i61 >>> 16) & 255]) ^ iArr14[(i63 >>> 8) & 255]) ^ iArr15[i64 & 255]) ^ this.b[115];
        this.a[4] = (((iArr12[i61 >>> 24] ^ iArr13[(i62 >>> 16) & 255]) ^ iArr14[(i64 >>> 8) & 255]) ^ iArr15[i57 & 255]) ^ this.b[116];
        this.a[5] = (((iArr12[i62 >>> 24] ^ iArr13[(i63 >>> 16) & 255]) ^ iArr14[(i57 >>> 8) & 255]) ^ iArr15[i58 & 255]) ^ this.b[117];
        this.a[6] = (((iArr12[i63 >>> 24] ^ iArr13[(i64 >>> 16) & 255]) ^ iArr14[(i58 >>> 8) & 255]) ^ iArr15[i59 & 255]) ^ this.b[118];
        this.a[7] = (((iArr12[i64 >>> 24] ^ iArr13[(i57 >>> 16) & 255]) ^ iArr14[(i59 >>> 8) & 255]) ^ iArr15[i60 & 255]) ^ this.b[119];
    }

    @Override // iaik.security.cipher.AbstractC0029j
    protected void c() {
        H h = this;
        int i = h.a[0] ^ h.b[112];
        int i2 = h.a[1] ^ h.b[113];
        int i3 = h.a[2] ^ h.b[114];
        int i4 = h.a[3] ^ h.b[115];
        int i5 = h.a[4] ^ h.b[116];
        int i6 = h.a[5] ^ h.b[117];
        int i7 = h.a[6] ^ h.b[118];
        int i8 = h.b[119] ^ h.a[7];
        int i9 = 104;
        while (i9 >= 24) {
            int[] iArr = x;
            int i10 = iArr[i >>> 24];
            int[] iArr2 = y;
            int i11 = i10 ^ iArr2[(i8 >>> 16) & 255];
            int[] iArr3 = z;
            int i12 = iArr3[(i6 >>> 8) & 255];
            int[] iArr4 = A;
            int i13 = (iArr4[i5 & 255] ^ i12) ^ i11;
            int[] iArr5 = h.b;
            int i14 = i9 + 1;
            int i15 = i13 ^ iArr5[i9];
            int i16 = i14 + 1;
            int i17 = ((iArr4[i6 & 255] ^ iArr3[(i7 >>> 8) & 255]) ^ (iArr2[(i >>> 16) & 255] ^ iArr[i2 >>> 24])) ^ iArr5[i14];
            int i18 = i16 + 1;
            int i19 = iArr5[i16] ^ ((iArr[i3 >>> 24] ^ iArr2[(i2 >>> 16) & 255]) ^ (iArr4[i7 & 255] ^ iArr3[(i8 >>> 8) & 255]));
            int i20 = i18 + 1;
            int i21 = ((iArr4[i8 & 255] ^ iArr3[(i >>> 8) & 255]) ^ (iArr[i4 >>> 24] ^ iArr2[(i3 >>> 16) & 255])) ^ iArr5[i18];
            int i22 = i20 + 1;
            int i23 = ((iArr4[i & 255] ^ iArr3[(i2 >>> 8) & 255]) ^ (iArr[i5 >>> 24] ^ iArr2[(i4 >>> 16) & 255])) ^ iArr5[i20];
            int i24 = i22 + 1;
            int i25 = ((iArr4[i2 & 255] ^ iArr3[(i3 >>> 8) & 255]) ^ (iArr[i6 >>> 24] ^ iArr2[(i5 >>> 16) & 255])) ^ iArr5[i22];
            int i26 = (iArr4[i3 & 255] ^ iArr3[(i4 >>> 8) & 255]) ^ (iArr2[(i6 >>> 16) & 255] ^ iArr[i7 >>> 24]);
            int i27 = i24 + 1;
            int i28 = i26 ^ iArr5[i24];
            int i29 = ((iArr4[i4 & 255] ^ iArr3[(i5 >>> 8) & 255]) ^ (iArr[i8 >>> 24] ^ iArr2[(i7 >>> 16) & 255])) ^ iArr5[i27];
            int i30 = i27 - 15;
            int i31 = i30 + 1;
            int i32 = ((iArr[i15 >>> 24] ^ iArr2[(i29 >>> 16) & 255]) ^ (iArr3[(i25 >>> 8) & 255] ^ iArr4[i23 & 255])) ^ iArr5[i30];
            int i33 = i31 + 1;
            int i34 = iArr5[i31] ^ ((iArr[i17 >>> 24] ^ iArr2[(i15 >>> 16) & 255]) ^ (iArr3[(i28 >>> 8) & 255] ^ iArr4[i25 & 255]));
            int i35 = i33 + 1;
            int i36 = ((iArr[i19 >>> 24] ^ iArr2[(i17 >>> 16) & 255]) ^ (iArr3[(i29 >>> 8) & 255] ^ iArr4[i28 & 255])) ^ iArr5[i33];
            int i37 = i35 + 1;
            int i38 = ((iArr[i21 >>> 24] ^ iArr2[(i19 >>> 16) & 255]) ^ (iArr3[(i15 >>> 8) & 255] ^ iArr4[i29 & 255])) ^ iArr5[i35];
            int i39 = i37 + 1;
            int i40 = ((iArr4[i15 & 255] ^ iArr3[(i17 >>> 8) & 255]) ^ (iArr[i23 >>> 24] ^ iArr2[(i21 >>> 16) & 255])) ^ iArr5[i37];
            int i41 = i39 + 1;
            int i42 = ((iArr4[i17 & 255] ^ iArr3[(i19 >>> 8) & 255]) ^ (iArr[i25 >>> 24] ^ iArr2[(i23 >>> 16) & 255])) ^ iArr5[i39];
            int i43 = (iArr2[(i25 >>> 16) & 255] ^ iArr[i28 >>> 24]) ^ (iArr4[i19 & 255] ^ iArr3[(i21 >>> 8) & 255]);
            int i44 = i41 + 1;
            i7 = iArr5[i41] ^ i43;
            i8 = ((iArr4[i21 & 255] ^ iArr3[(i23 >>> 8) & 255]) ^ (iArr[i29 >>> 24] ^ iArr2[(i28 >>> 16) & 255])) ^ iArr5[i44];
            i9 = i44 - 15;
            i = i32;
            i2 = i34;
            i3 = i36;
            i4 = i38;
            h = this;
            i6 = i42;
            i5 = i40;
        }
        int[] iArr6 = x;
        int i45 = iArr6[i >>> 24];
        int[] iArr7 = y;
        int i46 = i45 ^ iArr7[(i8 >>> 16) & 255];
        int[] iArr8 = z;
        int i47 = iArr8[(i6 >>> 8) & 255];
        int[] iArr9 = A;
        int i48 = i46 ^ (i47 ^ iArr9[i5 & 255]);
        int[] iArr10 = this.b;
        int i49 = i48 ^ iArr10[8];
        int i50 = ((iArr7[(i >>> 16) & 255] ^ iArr6[i2 >>> 24]) ^ (iArr9[i6 & 255] ^ iArr8[(i7 >>> 8) & 255])) ^ iArr10[9];
        int i51 = ((iArr9[i7 & 255] ^ iArr8[(i8 >>> 8) & 255]) ^ (iArr6[i3 >>> 24] ^ iArr7[(i2 >>> 16) & 255])) ^ iArr10[10];
        int i52 = ((iArr9[i8 & 255] ^ iArr8[(i >>> 8) & 255]) ^ (iArr6[i4 >>> 24] ^ iArr7[(i3 >>> 16) & 255])) ^ iArr10[11];
        int i53 = ((iArr9[i & 255] ^ iArr8[(i2 >>> 8) & 255]) ^ (iArr6[i5 >>> 24] ^ iArr7[(i4 >>> 16) & 255])) ^ iArr10[12];
        int i54 = ((iArr9[i2 & 255] ^ iArr8[(i3 >>> 8) & 255]) ^ (iArr6[i6 >>> 24] ^ iArr7[(i5 >>> 16) & 255])) ^ iArr10[13];
        int i55 = ((iArr9[i3 & 255] ^ iArr8[(i4 >>> 8) & 255]) ^ (iArr6[i7 >>> 24] ^ iArr7[(i6 >>> 16) & 255])) ^ iArr10[14];
        int i56 = ((iArr6[i8 >>> 24] ^ iArr7[(i7 >>> 16) & 255]) ^ (iArr8[(i5 >>> 8) & 255] ^ iArr9[i4 & 255])) ^ iArr10[15];
        int[] iArr11 = this.a;
        int[] iArr12 = w;
        int i57 = iArr12[i49 >>> 24];
        int[] iArr13 = v;
        int i58 = i57 ^ iArr13[(i56 >>> 16) & 255];
        int[] iArr14 = u;
        int i59 = i58 ^ iArr14[(i54 >>> 8) & 255];
        int[] iArr15 = t;
        iArr11[0] = (i59 ^ iArr15[i53 & 255]) ^ this.b[0];
        this.a[1] = (((iArr12[i50 >>> 24] ^ iArr13[(i49 >>> 16) & 255]) ^ iArr14[(i55 >>> 8) & 255]) ^ iArr15[i54 & 255]) ^ this.b[1];
        this.a[2] = (((iArr12[i51 >>> 24] ^ iArr13[(i50 >>> 16) & 255]) ^ iArr14[(i56 >>> 8) & 255]) ^ iArr15[i55 & 255]) ^ this.b[2];
        this.a[3] = (((iArr12[i52 >>> 24] ^ iArr13[(i51 >>> 16) & 255]) ^ iArr14[(i49 >>> 8) & 255]) ^ iArr15[i56 & 255]) ^ this.b[3];
        this.a[4] = (((iArr12[i53 >>> 24] ^ iArr13[(i52 >>> 16) & 255]) ^ iArr14[(i50 >>> 8) & 255]) ^ iArr15[i49 & 255]) ^ this.b[4];
        this.a[5] = (((iArr12[i54 >>> 24] ^ iArr13[(i53 >>> 16) & 255]) ^ iArr14[(i51 >>> 8) & 255]) ^ iArr15[i50 & 255]) ^ this.b[5];
        this.a[6] = (((iArr13[(i54 >>> 16) & 255] ^ iArr12[i55 >>> 24]) ^ iArr14[(i52 >>> 8) & 255]) ^ iArr15[i51 & 255]) ^ this.b[6];
        this.a[7] = (((iArr12[i56 >>> 24] ^ iArr13[(i55 >>> 16) & 255]) ^ iArr14[(i53 >>> 8) & 255]) ^ iArr15[i52 & 255]) ^ this.b[7];
    }

    @Override // iaik.security.cipher.AbstractC0029j
    public void d() {
        super.d();
        CryptoUtils.zeroBlock(this.b);
        this.b = null;
    }
}
