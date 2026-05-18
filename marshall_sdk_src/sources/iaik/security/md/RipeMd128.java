package iaik.security.md;

import iaik.utils.CryptoUtils;

/* JADX INFO: loaded from: classes.dex */
public class RipeMd128 extends AbstractMessageDigest {
    final transient byte[] g;
    private int h;
    private int i;
    private int j;
    private int k;
    private final transient int[] l;

    public RipeMd128() {
        super("RIPEMD128", 16, 64);
        this.l = new int[16];
        this.g = new byte[8];
        engineReset();
    }

    private static int a(int i, int i2) {
        return (i >>> (32 - i2)) | (i << i2);
    }

    private static int a(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    private static int a(int i, int i2, int i3, int i4, int i5, int i6) {
        return a(i + a(i2, i3, i4) + i5, i6);
    }

    private static int b(int i, int i2, int i3) {
        return ((~i) & i3) | (i2 & i);
    }

    private static int b(int i, int i2, int i3, int i4, int i5, int i6) {
        return a(i + b(i2, i3, i4) + i5 + 1518500249, i6);
    }

    private static int c(int i, int i2, int i3) {
        return (i | (~i2)) ^ i3;
    }

    private static int c(int i, int i2, int i3, int i4, int i5, int i6) {
        return a(i + c(i2, i3, i4) + i5 + 1859775393, i6);
    }

    private static int d(int i, int i2, int i3) {
        return (i & i3) | (i2 & (~i3));
    }

    private static int d(int i, int i2, int i3, int i4, int i5, int i6) {
        return a(((i + d(i2, i3, i4)) + i5) - 1894007588, i6);
    }

    private static int e(int i, int i2, int i3, int i4, int i5, int i6) {
        return a(i + a(i2, i3, i4) + i5, i6);
    }

    private static int f(int i, int i2, int i3, int i4, int i5, int i6) {
        return a(i + b(i2, i3, i4) + i5 + 1836072691, i6);
    }

    private static int g(int i, int i2, int i3, int i4, int i5, int i6) {
        return a(i + c(i2, i3, i4) + i5 + 1548603684, i6);
    }

    private static int h(int i, int i2, int i3, int i4, int i5, int i6) {
        return a(i + d(i2, i3, i4) + i5 + 1352829926, i6);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a() {
        long j = this.f << 3;
        for (int i = 0; i < 8; i++) {
            this.g[i] = (byte) (j >>> (i << 3));
        }
        int i2 = (int) (this.f & 63);
        engineUpdate(a, 0, i2 < 56 ? 56 - i2 : 120 - i2);
        engineUpdate(this.g, 0, 8);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a(byte[] bArr, int i) {
        int[] iArr = this.l;
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = ((bArr[i2] & 255) << 8) | (bArr[i] & 255);
        int i5 = i3 + 1;
        int i6 = i4 | ((bArr[i3] & 255) << 16);
        int i7 = i5 + 1;
        iArr[0] = i6 | (bArr[i5] << 24);
        int i8 = i7 + 1;
        int i9 = i8 + 1;
        int i10 = ((bArr[i8] & 255) << 8) | (bArr[i7] & 255);
        int i11 = i9 + 1;
        int i12 = i10 | ((bArr[i9] & 255) << 16);
        int i13 = i11 + 1;
        iArr[1] = i12 | (bArr[i11] << 24);
        int i14 = i13 + 1;
        int i15 = i14 + 1;
        int i16 = ((bArr[i14] & 255) << 8) | (bArr[i13] & 255);
        int i17 = i15 + 1;
        int i18 = i16 | ((bArr[i15] & 255) << 16);
        int i19 = i17 + 1;
        iArr[2] = i18 | (bArr[i17] << 24);
        int i20 = i19 + 1;
        int i21 = i20 + 1;
        int i22 = ((bArr[i20] & 255) << 8) | (bArr[i19] & 255);
        int i23 = i21 + 1;
        int i24 = i22 | ((bArr[i21] & 255) << 16);
        int i25 = i23 + 1;
        iArr[3] = i24 | (bArr[i23] << 24);
        int i26 = i25 + 1;
        int i27 = i26 + 1;
        int i28 = ((bArr[i26] & 255) << 8) | (bArr[i25] & 255);
        int i29 = i27 + 1;
        int i30 = i28 | ((bArr[i27] & 255) << 16);
        int i31 = i29 + 1;
        iArr[4] = i30 | (bArr[i29] << 24);
        int i32 = i31 + 1;
        int i33 = i32 + 1;
        int i34 = ((bArr[i32] & 255) << 8) | (bArr[i31] & 255);
        int i35 = i33 + 1;
        int i36 = i34 | ((bArr[i33] & 255) << 16);
        int i37 = i35 + 1;
        iArr[5] = i36 | (bArr[i35] << 24);
        int i38 = i37 + 1;
        int i39 = i38 + 1;
        int i40 = ((bArr[i38] & 255) << 8) | (bArr[i37] & 255);
        int i41 = i39 + 1;
        int i42 = i40 | ((bArr[i39] & 255) << 16);
        int i43 = i41 + 1;
        iArr[6] = i42 | (bArr[i41] << 24);
        int i44 = i43 + 1;
        int i45 = i44 + 1;
        int i46 = ((bArr[i44] & 255) << 8) | (bArr[i43] & 255);
        int i47 = i45 + 1;
        int i48 = i46 | ((bArr[i45] & 255) << 16);
        int i49 = i47 + 1;
        iArr[7] = i48 | (bArr[i47] << 24);
        int i50 = i49 + 1;
        int i51 = i50 + 1;
        int i52 = ((bArr[i50] & 255) << 8) | (bArr[i49] & 255);
        int i53 = i51 + 1;
        int i54 = i52 | ((bArr[i51] & 255) << 16);
        int i55 = i53 + 1;
        iArr[8] = i54 | (bArr[i53] << 24);
        int i56 = i55 + 1;
        int i57 = bArr[i55] & 255;
        int i58 = i56 + 1;
        int i59 = ((bArr[i56] & 255) << 8) | i57;
        int i60 = i58 + 1;
        int i61 = i59 | ((bArr[i58] & 255) << 16);
        int i62 = i60 + 1;
        iArr[9] = i61 | (bArr[i60] << 24);
        int i63 = i62 + 1;
        int i64 = i63 + 1;
        int i65 = ((bArr[i63] & 255) << 8) | (bArr[i62] & 255);
        int i66 = i64 + 1;
        int i67 = i65 | ((bArr[i64] & 255) << 16);
        int i68 = i66 + 1;
        iArr[10] = i67 | (bArr[i66] << 24);
        int i69 = i68 + 1;
        int i70 = i69 + 1;
        int i71 = ((bArr[i69] & 255) << 8) | (bArr[i68] & 255);
        int i72 = i70 + 1;
        int i73 = i71 | ((bArr[i70] & 255) << 16);
        int i74 = i72 + 1;
        iArr[11] = i73 | (bArr[i72] << 24);
        int i75 = i74 + 1;
        int i76 = i75 + 1;
        int i77 = ((bArr[i75] & 255) << 8) | (bArr[i74] & 255);
        int i78 = i76 + 1;
        int i79 = i78 + 1;
        iArr[12] = i77 | ((bArr[i76] & 255) << 16) | (bArr[i78] << 24);
        int i80 = i79 + 1;
        int i81 = i80 + 1;
        int i82 = ((bArr[i80] & 255) << 8) | (bArr[i79] & 255);
        int i83 = i81 + 1;
        int i84 = i83 + 1;
        iArr[13] = i82 | ((bArr[i81] & 255) << 16) | (bArr[i83] << 24);
        int i85 = i84 + 1;
        int i86 = i85 + 1;
        int i87 = ((bArr[i85] & 255) << 8) | (bArr[i84] & 255);
        int i88 = i86 + 1;
        int i89 = i88 + 1;
        iArr[14] = i87 | ((bArr[i86] & 255) << 16) | (bArr[i88] << 24);
        int i90 = i89 + 1;
        int i91 = i90 + 1;
        iArr[15] = ((bArr[i90] & 255) << 8) | (bArr[i89] & 255) | ((bArr[i91] & 255) << 16) | (bArr[i91 + 1] << 24);
        int i92 = this.h;
        int i93 = this.i;
        int i94 = this.j;
        int i95 = this.k;
        int iA = a(i92, i93, i94, i95, iArr[0], 11);
        int iA2 = a(i95, iA, i93, i94, this.l[1], 14);
        int iA3 = a(i94, iA2, iA, i93, this.l[2], 15);
        int iA4 = a(i93, iA3, iA2, iA, this.l[3], 12);
        int iA5 = a(iA, iA4, iA3, iA2, this.l[4], 5);
        int iA6 = a(iA2, iA5, iA4, iA3, this.l[5], 8);
        int iA7 = a(iA3, iA6, iA5, iA4, this.l[6], 7);
        int iA8 = a(iA4, iA7, iA6, iA5, this.l[7], 9);
        int iA9 = a(iA5, iA8, iA7, iA6, this.l[8], 11);
        int iA10 = a(iA6, iA9, iA8, iA7, this.l[9], 13);
        int iA11 = a(iA7, iA10, iA9, iA8, this.l[10], 14);
        int iA12 = a(iA8, iA11, iA10, iA9, this.l[11], 15);
        int iA13 = a(iA9, iA12, iA11, iA10, this.l[12], 6);
        int iA14 = a(iA10, iA13, iA12, iA11, this.l[13], 7);
        int iA15 = a(iA11, iA14, iA13, iA12, this.l[14], 9);
        int iA16 = a(iA12, iA15, iA14, iA13, this.l[15], 8);
        int iH = h(i92, i93, i94, i95, this.l[5], 8);
        int iH2 = h(i95, iH, i93, i94, this.l[14], 9);
        int iH3 = h(i94, iH2, iH, i93, this.l[7], 9);
        int iH4 = h(i93, iH3, iH2, iH, this.l[0], 11);
        int iH5 = h(iH, iH4, iH3, iH2, this.l[9], 13);
        int iH6 = h(iH2, iH5, iH4, iH3, this.l[2], 15);
        int iH7 = h(iH3, iH6, iH5, iH4, this.l[11], 15);
        int iH8 = h(iH4, iH7, iH6, iH5, this.l[4], 5);
        int iH9 = h(iH5, iH8, iH7, iH6, this.l[13], 7);
        int iH10 = h(iH6, iH9, iH8, iH7, this.l[6], 7);
        int iH11 = h(iH7, iH10, iH9, iH8, this.l[15], 8);
        int iH12 = h(iH8, iH11, iH10, iH9, this.l[8], 11);
        int iH13 = h(iH9, iH12, iH11, iH10, this.l[1], 14);
        int iH14 = h(iH10, iH13, iH12, iH11, this.l[10], 14);
        int iH15 = h(iH11, iH14, iH13, iH12, this.l[3], 12);
        int iH16 = h(iH12, iH15, iH14, iH13, this.l[12], 6);
        int iB = b(iA13, iA16, iA15, iA14, this.l[7], 7);
        int iB2 = b(iA14, iB, iA16, iA15, this.l[4], 6);
        int iB3 = b(iA15, iB2, iB, iA16, this.l[13], 8);
        int iB4 = b(iA16, iB3, iB2, iB, this.l[1], 13);
        int iB5 = b(iB, iB4, iB3, iB2, this.l[10], 11);
        int iB6 = b(iB2, iB5, iB4, iB3, this.l[6], 9);
        int iB7 = b(iB3, iB6, iB5, iB4, this.l[15], 7);
        int iB8 = b(iB4, iB7, iB6, iB5, this.l[3], 15);
        int iB9 = b(iB5, iB8, iB7, iB6, this.l[12], 7);
        int iB10 = b(iB6, iB9, iB8, iB7, this.l[0], 12);
        int iB11 = b(iB7, iB10, iB9, iB8, this.l[9], 15);
        int iB12 = b(iB8, iB11, iB10, iB9, this.l[5], 9);
        int iB13 = b(iB9, iB12, iB11, iB10, this.l[2], 11);
        int iB14 = b(iB10, iB13, iB12, iB11, this.l[14], 7);
        int iB15 = b(iB11, iB14, iB13, iB12, this.l[11], 13);
        int iB16 = b(iB12, iB15, iB14, iB13, this.l[8], 12);
        int iG = g(iH13, iH16, iH15, iH14, this.l[6], 9);
        int iG2 = g(iH14, iG, iH16, iH15, this.l[11], 13);
        int iG3 = g(iH15, iG2, iG, iH16, this.l[3], 15);
        int iG4 = g(iH16, iG3, iG2, iG, this.l[7], 7);
        int iG5 = g(iG, iG4, iG3, iG2, this.l[0], 12);
        int iG6 = g(iG2, iG5, iG4, iG3, this.l[13], 8);
        int iG7 = g(iG3, iG6, iG5, iG4, this.l[5], 9);
        int iG8 = g(iG4, iG7, iG6, iG5, this.l[10], 11);
        int iG9 = g(iG5, iG8, iG7, iG6, this.l[14], 7);
        int iG10 = g(iG6, iG9, iG8, iG7, this.l[15], 7);
        int iG11 = g(iG7, iG10, iG9, iG8, this.l[8], 12);
        int iG12 = g(iG8, iG11, iG10, iG9, this.l[12], 7);
        int iG13 = g(iG9, iG12, iG11, iG10, this.l[4], 6);
        int iG14 = g(iG10, iG13, iG12, iG11, this.l[9], 15);
        int iG15 = g(iG11, iG14, iG13, iG12, this.l[1], 13);
        int iG16 = g(iG12, iG15, iG14, iG13, this.l[2], 11);
        int iC = c(iB13, iB16, iB15, iB14, this.l[3], 11);
        int iC2 = c(iB14, iC, iB16, iB15, this.l[10], 13);
        int iC3 = c(iB15, iC2, iC, iB16, this.l[14], 6);
        int iC4 = c(iB16, iC3, iC2, iC, this.l[4], 7);
        int iC5 = c(iC, iC4, iC3, iC2, this.l[9], 14);
        int iC6 = c(iC2, iC5, iC4, iC3, this.l[15], 9);
        int iC7 = c(iC3, iC6, iC5, iC4, this.l[8], 13);
        int iC8 = c(iC4, iC7, iC6, iC5, this.l[1], 15);
        int iC9 = c(iC5, iC8, iC7, iC6, this.l[2], 14);
        int iC10 = c(iC6, iC9, iC8, iC7, this.l[7], 8);
        int iC11 = c(iC7, iC10, iC9, iC8, this.l[0], 13);
        int iC12 = c(iC8, iC11, iC10, iC9, this.l[6], 6);
        int iC13 = c(iC9, iC12, iC11, iC10, this.l[13], 5);
        int iC14 = c(iC10, iC13, iC12, iC11, this.l[11], 12);
        int iC15 = c(iC11, iC14, iC13, iC12, this.l[5], 7);
        int iC16 = c(iC12, iC15, iC14, iC13, this.l[12], 5);
        int iF = f(iG13, iG16, iG15, iG14, this.l[15], 9);
        int iF2 = f(iG14, iF, iG16, iG15, this.l[5], 7);
        int iF3 = f(iG15, iF2, iF, iG16, this.l[1], 15);
        int iF4 = f(iG16, iF3, iF2, iF, this.l[3], 11);
        int iF5 = f(iF, iF4, iF3, iF2, this.l[7], 8);
        int iF6 = f(iF2, iF5, iF4, iF3, this.l[14], 6);
        int iF7 = f(iF3, iF6, iF5, iF4, this.l[6], 6);
        int iF8 = f(iF4, iF7, iF6, iF5, this.l[9], 14);
        int iF9 = f(iF5, iF8, iF7, iF6, this.l[11], 12);
        int iF10 = f(iF6, iF9, iF8, iF7, this.l[8], 13);
        int iF11 = f(iF7, iF10, iF9, iF8, this.l[12], 5);
        int iF12 = f(iF8, iF11, iF10, iF9, this.l[2], 14);
        int iF13 = f(iF9, iF12, iF11, iF10, this.l[10], 13);
        int iF14 = f(iF10, iF13, iF12, iF11, this.l[0], 13);
        int iF15 = f(iF11, iF14, iF13, iF12, this.l[4], 7);
        int iF16 = f(iF12, iF15, iF14, iF13, this.l[13], 5);
        int iD = d(iC13, iC16, iC15, iC14, this.l[1], 11);
        int iD2 = d(iC14, iD, iC16, iC15, this.l[9], 12);
        int iD3 = d(iC15, iD2, iD, iC16, this.l[11], 14);
        int iD4 = d(iC16, iD3, iD2, iD, this.l[10], 15);
        int iD5 = d(iD, iD4, iD3, iD2, this.l[0], 14);
        int iD6 = d(iD2, iD5, iD4, iD3, this.l[8], 15);
        int iD7 = d(iD3, iD6, iD5, iD4, this.l[12], 9);
        int iD8 = d(iD4, iD7, iD6, iD5, this.l[4], 8);
        int iD9 = d(iD5, iD8, iD7, iD6, this.l[13], 9);
        int iD10 = d(iD6, iD9, iD8, iD7, this.l[3], 14);
        int iD11 = d(iD7, iD10, iD9, iD8, this.l[7], 5);
        int iD12 = d(iD8, iD11, iD10, iD9, this.l[15], 6);
        int iD13 = d(iD9, iD12, iD11, iD10, this.l[14], 8);
        int iD14 = d(iD10, iD13, iD12, iD11, this.l[5], 6);
        int iD15 = d(iD11, iD14, iD13, iD12, this.l[6], 5);
        int iD16 = d(iD12, iD15, iD14, iD13, this.l[2], 12);
        int iE = e(iF13, iF16, iF15, iF14, this.l[8], 15);
        int iE2 = e(iF14, iE, iF16, iF15, this.l[6], 5);
        int iE3 = e(iF15, iE2, iE, iF16, this.l[4], 8);
        int iE4 = e(iF16, iE3, iE2, iE, this.l[1], 11);
        int iE5 = e(iE, iE4, iE3, iE2, this.l[3], 14);
        int iE6 = e(iE2, iE5, iE4, iE3, this.l[11], 14);
        int iE7 = e(iE3, iE6, iE5, iE4, this.l[15], 6);
        int iE8 = e(iE4, iE7, iE6, iE5, this.l[0], 14);
        int iE9 = e(iE5, iE8, iE7, iE6, this.l[5], 6);
        int iE10 = e(iE6, iE9, iE8, iE7, this.l[12], 9);
        int iE11 = e(iE7, iE10, iE9, iE8, this.l[2], 12);
        int iE12 = e(iE8, iE11, iE10, iE9, this.l[13], 9);
        int iE13 = e(iE9, iE12, iE11, iE10, this.l[9], 12);
        int iE14 = e(iE10, iE13, iE12, iE11, this.l[7], 5);
        int iE15 = e(iE11, iE14, iE13, iE12, this.l[10], 15);
        int iE16 = e(iE12, iE15, iE14, iE13, this.l[14], 8);
        int i96 = iE14 + iD15 + this.i;
        this.i = this.j + iD14 + iE13;
        this.j = this.k + iD13 + iE16;
        this.k = this.h + iD16 + iE15;
        this.h = i96;
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void b(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = this.h;
        bArr[i] = (byte) i3;
        int i4 = i2 + 1;
        bArr[i2] = (byte) (i3 >> 8);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i3 >> 16);
        int i6 = i5 + 1;
        bArr[i5] = (byte) (i3 >> 24);
        int i7 = i6 + 1;
        int i8 = this.i;
        bArr[i6] = (byte) i8;
        int i9 = i7 + 1;
        bArr[i7] = (byte) (i8 >> 8);
        int i10 = i9 + 1;
        bArr[i9] = (byte) (i8 >> 16);
        int i11 = i10 + 1;
        bArr[i10] = (byte) (i8 >> 24);
        int i12 = i11 + 1;
        int i13 = this.j;
        bArr[i11] = (byte) i13;
        int i14 = i12 + 1;
        bArr[i12] = (byte) (i13 >> 8);
        int i15 = i14 + 1;
        bArr[i14] = (byte) (i13 >> 16);
        int i16 = i15 + 1;
        bArr[i15] = (byte) (i13 >> 24);
        int i17 = i16 + 1;
        int i18 = this.k;
        bArr[i16] = (byte) i18;
        int i19 = i17 + 1;
        bArr[i17] = (byte) (i18 >> 8);
        bArr[i19] = (byte) (i18 >> 16);
        bArr[i19 + 1] = (byte) (i18 >> 24);
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        RipeMd128 ripeMd128 = (RipeMd128) super.clone();
        ripeMd128.h = this.h;
        ripeMd128.i = this.i;
        ripeMd128.j = this.j;
        ripeMd128.k = this.k;
        System.arraycopy(this.l, 0, ripeMd128.l, 0, 16);
        System.arraycopy(this.g, 0, ripeMd128.g, 0, 8);
        return ripeMd128;
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    protected void engineReset() {
        this.f = 0L;
        this.h = 1732584193;
        this.i = -271733879;
        this.j = -1732584194;
        this.k = 271733878;
        CryptoUtils.zeroBlock(this.b);
        CryptoUtils.zeroBlock(this.g);
        CryptoUtils.zeroBlock(this.l);
    }
}
