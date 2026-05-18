package iaik.security.cipher;

import iaik.utils.CriticalObject;
import java.lang.reflect.Array;
import java.security.InvalidKeyException;
import java.util.Locale;
import javax.crypto.NoSuchPaddingException;

/* JADX INFO: loaded from: classes.dex */
final class w extends AbstractC0030k {
    private static int[][] b;
    private static final byte[] q = {4, 10, 9, 2, 13, 8, 0, 14, 6, 11, 1, 12, 7, 15, 5, 3, 14, 11, 4, 12, 6, 13, 15, 10, 2, 3, 8, 1, 0, 7, 5, 9, 5, 8, 1, 13, 10, 3, 4, 2, 14, 15, 12, 7, 6, 0, 9, 11, 7, 13, 10, 1, 0, 8, 9, 15, 14, 4, 6, 12, 11, 2, 5, 3, 6, 12, 7, 1, 5, 15, 13, 8, 4, 10, 9, 14, 0, 3, 11, 2, 4, 11, 10, 0, 7, 2, 1, 13, 3, 6, 8, 5, 9, 12, 15, 14, 13, 11, 4, 1, 3, 15, 5, 9, 0, 10, 14, 7, 6, 8, 2, 12, 1, 15, 13, 0, 5, 7, 10, 4, 9, 2, 3, 14, 6, 11, 8, 12};
    private int[] c;
    private int[] m;
    private int[] n;
    private int[] o;
    private final int[] p;

    w() {
        super("GOST", 2);
        if (b == null) {
            b = a(q);
        }
        a(b);
        this.p = new int[8];
    }

    private void a(int[][] iArr) {
        this.c = iArr[0];
        this.m = iArr[1];
        this.n = iArr[2];
        this.o = iArr[3];
    }

    private int[][] a(byte[] bArr) {
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) int.class, 4, 256);
        for (int i = 0; i < 8; i += 2) {
            for (int i2 = 0; i2 < 256; i2++) {
                iArr[i >> 1][i2] = b(bArr[(i << 4) + (i2 & 15)] | (bArr[((i + 1) << 4) + (i2 >> 4)] << 4), (i << 2) + 11);
            }
        }
        return iArr;
    }

    private static int b(int i, int i2) {
        int i3 = i2 & 31;
        return (i >>> (32 - i3)) | (i << i3);
    }

    @Override // iaik.security.cipher.AbstractC0030k
    void a(int i, byte[] bArr) throws InvalidKeyException {
        if (bArr.length != 32) {
            throw new InvalidKeyException("Key must be 256 bit long!");
        }
        int i2 = 0;
        int i3 = 0;
        while (i2 < 8) {
            int[] iArr = this.p;
            int i4 = i3 + 1;
            int i5 = i4 + 1;
            int i6 = i5 + 1;
            int i7 = (bArr[i3] & 255) | ((bArr[i4] & 255) << 8) | ((bArr[i5] & 255) << 16);
            iArr[i2] = i7 | (bArr[i6] << 24);
            i2++;
            i3 = i6 + 1;
        }
    }

    @Override // iaik.security.cipher.t
    boolean a(String str) throws NoSuchPaddingException {
        String upperCase = str.toUpperCase(Locale.US);
        return upperCase.equals("PKCS5Padding") || upperCase.equals("ZEROPADDING") || upperCase.equals("RANDOMPADDING");
    }

    @Override // iaik.security.cipher.AbstractC0030k
    void b() {
        int i = this.a[0];
        int i2 = this.a[1];
        int i3 = 0;
        while (i3 < 8) {
            int[] iArr = this.p;
            int i4 = i3 + 1;
            int i5 = iArr[i3] + i;
            int[] iArr2 = this.c;
            int i6 = i2 ^ iArr2[i5 & 255];
            int[] iArr3 = this.m;
            int i7 = i6 ^ iArr3[(i5 >>> 8) & 255];
            int[] iArr4 = this.n;
            int i8 = i7 ^ iArr4[(i5 >>> 16) & 255];
            int[] iArr5 = this.o;
            i2 = i8 ^ iArr5[i5 >>> 24];
            i3 = i4 + 1;
            int i9 = iArr[i4] + i2;
            i = (((i ^ iArr2[i9 & 255]) ^ iArr3[(i9 >>> 8) & 255]) ^ iArr4[(i9 >>> 16) & 255]) ^ iArr5[i9 >>> 24];
        }
        int i10 = 0;
        while (i10 < 8) {
            int[] iArr6 = this.p;
            int i11 = i10 + 1;
            int i12 = iArr6[i10] + i;
            int[] iArr7 = this.c;
            int i13 = i2 ^ iArr7[i12 & 255];
            int[] iArr8 = this.m;
            int i14 = i13 ^ iArr8[(i12 >>> 8) & 255];
            int[] iArr9 = this.n;
            int i15 = i14 ^ iArr9[(i12 >>> 16) & 255];
            int[] iArr10 = this.o;
            i2 = i15 ^ iArr10[i12 >>> 24];
            i10 = i11 + 1;
            int i16 = iArr6[i11] + i2;
            i = (((i ^ iArr7[i16 & 255]) ^ iArr8[(i16 >>> 8) & 255]) ^ iArr9[(i16 >>> 16) & 255]) ^ iArr10[i16 >>> 24];
        }
        int i17 = 0;
        while (i17 < 8) {
            int[] iArr11 = this.p;
            int i18 = i17 + 1;
            int i19 = iArr11[i17] + i;
            int[] iArr12 = this.c;
            int i20 = i2 ^ iArr12[i19 & 255];
            int[] iArr13 = this.m;
            int i21 = i20 ^ iArr13[(i19 >>> 8) & 255];
            int[] iArr14 = this.n;
            int i22 = i21 ^ iArr14[(i19 >>> 16) & 255];
            int[] iArr15 = this.o;
            i2 = i22 ^ iArr15[i19 >>> 24];
            i17 = i18 + 1;
            int i23 = iArr11[i18] + i2;
            i = (((i ^ iArr12[i23 & 255]) ^ iArr13[(i23 >>> 8) & 255]) ^ iArr14[(i23 >>> 16) & 255]) ^ iArr15[i23 >>> 24];
        }
        int i24 = 7;
        while (i24 > 0) {
            int[] iArr16 = this.p;
            int i25 = i24 - 1;
            int i26 = iArr16[i24] + i;
            int[] iArr17 = this.c;
            int i27 = i2 ^ iArr17[i26 & 255];
            int[] iArr18 = this.m;
            int i28 = i27 ^ iArr18[(i26 >>> 8) & 255];
            int[] iArr19 = this.n;
            int i29 = i28 ^ iArr19[(i26 >>> 16) & 255];
            int[] iArr20 = this.o;
            i2 = i29 ^ iArr20[i26 >>> 24];
            i24 = i25 - 1;
            int i30 = iArr16[i25] + i2;
            i = (((i ^ iArr17[i30 & 255]) ^ iArr18[(i30 >>> 8) & 255]) ^ iArr19[(i30 >>> 16) & 255]) ^ iArr20[i30 >>> 24];
        }
        this.a[0] = i2;
        this.a[1] = i;
    }

    @Override // iaik.security.cipher.AbstractC0030k
    void c() {
        int i = this.a[0];
        int i2 = this.a[1];
        int i3 = 0;
        while (i3 < 8) {
            int[] iArr = this.p;
            int i4 = i3 + 1;
            int i5 = iArr[i3] + i;
            int[] iArr2 = this.c;
            int i6 = i2 ^ iArr2[i5 & 255];
            int[] iArr3 = this.m;
            int i7 = i6 ^ iArr3[(i5 >>> 8) & 255];
            int[] iArr4 = this.n;
            int i8 = i7 ^ iArr4[(i5 >>> 16) & 255];
            int[] iArr5 = this.o;
            i2 = i8 ^ iArr5[i5 >>> 24];
            i3 = i4 + 1;
            int i9 = iArr[i4] + i2;
            i = (((i ^ iArr2[i9 & 255]) ^ iArr3[(i9 >>> 8) & 255]) ^ iArr4[(i9 >>> 16) & 255]) ^ iArr5[i9 >>> 24];
        }
        int i10 = 7;
        int i11 = 7;
        while (i11 > 0) {
            int[] iArr6 = this.p;
            int i12 = i11 - 1;
            int i13 = iArr6[i11] + i;
            int[] iArr7 = this.c;
            int i14 = i2 ^ iArr7[i13 & 255];
            int[] iArr8 = this.m;
            int i15 = i14 ^ iArr8[(i13 >>> 8) & 255];
            int[] iArr9 = this.n;
            int i16 = i15 ^ iArr9[(i13 >>> 16) & 255];
            int[] iArr10 = this.o;
            i2 = i16 ^ iArr10[i13 >>> 24];
            i11 = i12 - 1;
            int i17 = iArr6[i12] + i2;
            i = (((i ^ iArr7[i17 & 255]) ^ iArr8[(i17 >>> 8) & 255]) ^ iArr9[(i17 >>> 16) & 255]) ^ iArr10[i17 >>> 24];
        }
        int i18 = 7;
        while (i18 > 0) {
            int[] iArr11 = this.p;
            int i19 = i18 - 1;
            int i20 = iArr11[i18] + i;
            int[] iArr12 = this.c;
            int i21 = i2 ^ iArr12[i20 & 255];
            int[] iArr13 = this.m;
            int i22 = i21 ^ iArr13[(i20 >>> 8) & 255];
            int[] iArr14 = this.n;
            int i23 = i22 ^ iArr14[(i20 >>> 16) & 255];
            int[] iArr15 = this.o;
            i2 = i23 ^ iArr15[i20 >>> 24];
            i18 = i19 - 1;
            int i24 = iArr11[i19] + i2;
            i = (((i ^ iArr12[i24 & 255]) ^ iArr13[(i24 >>> 8) & 255]) ^ iArr14[(i24 >>> 16) & 255]) ^ iArr15[i24 >>> 24];
        }
        while (i10 > 0) {
            int[] iArr16 = this.p;
            int i25 = i10 - 1;
            int i26 = iArr16[i10] + i;
            int[] iArr17 = this.c;
            int i27 = i2 ^ iArr17[i26 & 255];
            int[] iArr18 = this.m;
            int i28 = i27 ^ iArr18[(i26 >>> 8) & 255];
            int[] iArr19 = this.n;
            int i29 = i28 ^ iArr19[(i26 >>> 16) & 255];
            int[] iArr20 = this.o;
            i2 = i29 ^ iArr20[i26 >>> 24];
            i10 = i25 - 1;
            int i30 = iArr16[i25] + i2;
            i = (((i ^ iArr17[i30 & 255]) ^ iArr18[(i30 >>> 8) & 255]) ^ iArr19[(i30 >>> 16) & 255]) ^ iArr20[i30 >>> 24];
        }
        this.a[0] = i2;
        this.a[1] = i;
    }

    @Override // iaik.security.cipher.AbstractC0030k
    public void d() {
        super.d();
        CriticalObject.destroy(this.p);
    }

    @Override // iaik.security.cipher.AbstractC0030k
    protected void finalize() {
        d();
        super.finalize();
    }
}
