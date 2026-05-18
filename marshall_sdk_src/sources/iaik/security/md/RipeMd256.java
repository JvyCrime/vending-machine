package iaik.security.md;

import iaik.utils.CryptoUtils;

/* JADX INFO: loaded from: classes.dex */
public final class RipeMd256 extends AbstractMessageDigest {
    final transient byte[] g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private final transient int[] p;

    public RipeMd256() {
        super("RIPEMD256", 32, 64);
        this.p = new int[16];
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
        int[] iArr = this.p;
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        int i5 = ((bArr[i2] & 255) << 8) | (bArr[i] & 255) | ((bArr[i3] & 255) << 16);
        int i6 = i4 + 1;
        iArr[0] = i5 | (bArr[i4] << 24);
        int i7 = i6 + 1;
        int i8 = i7 + 1;
        int i9 = ((bArr[i7] & 255) << 8) | (bArr[i6] & 255);
        int i10 = i8 + 1;
        int i11 = i9 | ((bArr[i8] & 255) << 16);
        int i12 = i10 + 1;
        iArr[1] = i11 | (bArr[i10] << 24);
        int i13 = i12 + 1;
        int i14 = i13 + 1;
        int i15 = ((bArr[i13] & 255) << 8) | (bArr[i12] & 255);
        int i16 = i14 + 1;
        int i17 = i15 | ((bArr[i14] & 255) << 16);
        int i18 = i16 + 1;
        iArr[2] = i17 | (bArr[i16] << 24);
        int i19 = i18 + 1;
        int i20 = i19 + 1;
        int i21 = ((bArr[i19] & 255) << 8) | (bArr[i18] & 255);
        int i22 = i20 + 1;
        int i23 = i21 | ((bArr[i20] & 255) << 16);
        int i24 = i22 + 1;
        iArr[3] = i23 | (bArr[i22] << 24);
        int i25 = i24 + 1;
        int i26 = i25 + 1;
        int i27 = ((bArr[i25] & 255) << 8) | (bArr[i24] & 255);
        int i28 = i26 + 1;
        int i29 = i27 | ((bArr[i26] & 255) << 16);
        int i30 = i28 + 1;
        iArr[4] = i29 | (bArr[i28] << 24);
        int i31 = i30 + 1;
        int i32 = i31 + 1;
        int i33 = ((bArr[i31] & 255) << 8) | (bArr[i30] & 255);
        int i34 = i32 + 1;
        int i35 = i33 | ((bArr[i32] & 255) << 16);
        int i36 = i34 + 1;
        iArr[5] = i35 | (bArr[i34] << 24);
        int i37 = i36 + 1;
        int i38 = i37 + 1;
        int i39 = ((bArr[i37] & 255) << 8) | (bArr[i36] & 255);
        int i40 = i38 + 1;
        int i41 = i39 | ((bArr[i38] & 255) << 16);
        int i42 = i40 + 1;
        iArr[6] = i41 | (bArr[i40] << 24);
        int i43 = i42 + 1;
        int i44 = i43 + 1;
        int i45 = ((bArr[i43] & 255) << 8) | (bArr[i42] & 255);
        int i46 = i44 + 1;
        int i47 = i45 | ((bArr[i44] & 255) << 16);
        int i48 = i46 + 1;
        iArr[7] = i47 | (bArr[i46] << 24);
        int i49 = i48 + 1;
        int i50 = i49 + 1;
        int i51 = ((bArr[i49] & 255) << 8) | (bArr[i48] & 255);
        int i52 = i50 + 1;
        int i53 = i51 | ((bArr[i50] & 255) << 16);
        int i54 = i52 + 1;
        iArr[8] = i53 | (bArr[i52] << 24);
        int i55 = i54 + 1;
        int i56 = bArr[i54] & 255;
        int i57 = i55 + 1;
        int i58 = ((bArr[i55] & 255) << 8) | i56;
        int i59 = i57 + 1;
        int i60 = i58 | ((bArr[i57] & 255) << 16);
        int i61 = i59 + 1;
        iArr[9] = i60 | (bArr[i59] << 24);
        int i62 = i61 + 1;
        int i63 = i62 + 1;
        int i64 = ((bArr[i62] & 255) << 8) | (bArr[i61] & 255);
        int i65 = i63 + 1;
        int i66 = i64 | ((bArr[i63] & 255) << 16);
        int i67 = i65 + 1;
        iArr[10] = i66 | (bArr[i65] << 24);
        int i68 = i67 + 1;
        int i69 = i68 + 1;
        int i70 = ((bArr[i68] & 255) << 8) | (bArr[i67] & 255);
        int i71 = i69 + 1;
        int i72 = i70 | ((bArr[i69] & 255) << 16);
        int i73 = i71 + 1;
        iArr[11] = i72 | (bArr[i71] << 24);
        int i74 = i73 + 1;
        int i75 = i74 + 1;
        int i76 = ((bArr[i74] & 255) << 8) | (bArr[i73] & 255);
        int i77 = i75 + 1;
        int i78 = i77 + 1;
        iArr[12] = i76 | ((bArr[i75] & 255) << 16) | (bArr[i77] << 24);
        int i79 = i78 + 1;
        int i80 = i79 + 1;
        int i81 = ((bArr[i79] & 255) << 8) | (bArr[i78] & 255);
        int i82 = i80 + 1;
        int i83 = i82 + 1;
        iArr[13] = i81 | ((bArr[i80] & 255) << 16) | (bArr[i82] << 24);
        int i84 = i83 + 1;
        int i85 = i84 + 1;
        int i86 = ((bArr[i84] & 255) << 8) | (bArr[i83] & 255);
        int i87 = i85 + 1;
        int i88 = i87 + 1;
        iArr[14] = i86 | ((bArr[i85] & 255) << 16) | (bArr[i87] << 24);
        int i89 = i88 + 1;
        int i90 = i89 + 1;
        iArr[15] = ((bArr[i89] & 255) << 8) | (bArr[i88] & 255) | ((bArr[i90] & 255) << 16) | (bArr[i90 + 1] << 24);
        int i91 = this.h;
        int i92 = this.i;
        int i93 = this.j;
        int i94 = this.k;
        int i95 = this.l;
        int i96 = this.m;
        int i97 = this.n;
        int i98 = this.o;
        int iA = a(i91, i92, i93, i94, iArr[0], 11);
        int iA2 = a(i94, iA, i92, i93, this.p[1], 14);
        int iA3 = a(i93, iA2, iA, i92, this.p[2], 15);
        int iA4 = a(i92, iA3, iA2, iA, this.p[3], 12);
        int iA5 = a(iA, iA4, iA3, iA2, this.p[4], 5);
        int iA6 = a(iA2, iA5, iA4, iA3, this.p[5], 8);
        int iA7 = a(iA3, iA6, iA5, iA4, this.p[6], 7);
        int iA8 = a(iA4, iA7, iA6, iA5, this.p[7], 9);
        int iA9 = a(iA5, iA8, iA7, iA6, this.p[8], 11);
        int iA10 = a(iA6, iA9, iA8, iA7, this.p[9], 13);
        int iA11 = a(iA7, iA10, iA9, iA8, this.p[10], 14);
        int iA12 = a(iA8, iA11, iA10, iA9, this.p[11], 15);
        int iA13 = a(iA9, iA12, iA11, iA10, this.p[12], 6);
        int iA14 = a(iA10, iA13, iA12, iA11, this.p[13], 7);
        int iA15 = a(iA11, iA14, iA13, iA12, this.p[14], 9);
        int iA16 = a(iA12, iA15, iA14, iA13, this.p[15], 8);
        int iH = h(i95, i96, i97, i98, this.p[5], 8);
        int iH2 = h(i98, iH, i96, i97, this.p[14], 9);
        int iH3 = h(i97, iH2, iH, i96, this.p[7], 9);
        int iH4 = h(i96, iH3, iH2, iH, this.p[0], 11);
        int iH5 = h(iH, iH4, iH3, iH2, this.p[9], 13);
        int iH6 = h(iH2, iH5, iH4, iH3, this.p[2], 15);
        int iH7 = h(iH3, iH6, iH5, iH4, this.p[11], 15);
        int iH8 = h(iH4, iH7, iH6, iH5, this.p[4], 5);
        int iH9 = h(iH5, iH8, iH7, iH6, this.p[13], 7);
        int iH10 = h(iH6, iH9, iH8, iH7, this.p[6], 7);
        int iH11 = h(iH7, iH10, iH9, iH8, this.p[15], 8);
        int iH12 = h(iH8, iH11, iH10, iH9, this.p[8], 11);
        int iH13 = h(iH9, iH12, iH11, iH10, this.p[1], 14);
        int iH14 = h(iH10, iH13, iH12, iH11, this.p[10], 14);
        int iH15 = h(iH11, iH14, iH13, iH12, this.p[3], 12);
        int iH16 = h(iH12, iH15, iH14, iH13, this.p[12], 6);
        int i99 = iA13 ^ iH13;
        int i100 = iH13 ^ i99;
        int iB = b(i99 ^ i100, iA16, iA15, iA14, this.p[7], 7);
        int iB2 = b(iA14, iB, iA16, iA15, this.p[4], 6);
        int iB3 = b(iA15, iB2, iB, iA16, this.p[13], 8);
        int iB4 = b(iA16, iB3, iB2, iB, this.p[1], 13);
        int iB5 = b(iB, iB4, iB3, iB2, this.p[10], 11);
        int iB6 = b(iB2, iB5, iB4, iB3, this.p[6], 9);
        int iB7 = b(iB3, iB6, iB5, iB4, this.p[15], 7);
        int iB8 = b(iB4, iB7, iB6, iB5, this.p[3], 15);
        int iB9 = b(iB5, iB8, iB7, iB6, this.p[12], 7);
        int iB10 = b(iB6, iB9, iB8, iB7, this.p[0], 12);
        int iB11 = b(iB7, iB10, iB9, iB8, this.p[9], 15);
        int iB12 = b(iB8, iB11, iB10, iB9, this.p[5], 9);
        int iB13 = b(iB9, iB12, iB11, iB10, this.p[2], 11);
        int iB14 = b(iB10, iB13, iB12, iB11, this.p[14], 7);
        int iB15 = b(iB11, iB14, iB13, iB12, this.p[11], 13);
        int iB16 = b(iB12, iB15, iB14, iB13, this.p[8], 12);
        int iG = g(i100, iH16, iH15, iH14, this.p[6], 9);
        int iG2 = g(iH14, iG, iH16, iH15, this.p[11], 13);
        int iG3 = g(iH15, iG2, iG, iH16, this.p[3], 15);
        int iG4 = g(iH16, iG3, iG2, iG, this.p[7], 7);
        int iG5 = g(iG, iG4, iG3, iG2, this.p[0], 12);
        int iG6 = g(iG2, iG5, iG4, iG3, this.p[13], 8);
        int iG7 = g(iG3, iG6, iG5, iG4, this.p[5], 9);
        int iG8 = g(iG4, iG7, iG6, iG5, this.p[10], 11);
        int iG9 = g(iG5, iG8, iG7, iG6, this.p[14], 7);
        int iG10 = g(iG6, iG9, iG8, iG7, this.p[15], 7);
        int iG11 = g(iG7, iG10, iG9, iG8, this.p[8], 12);
        int iG12 = g(iG8, iG11, iG10, iG9, this.p[12], 7);
        int iG13 = g(iG9, iG12, iG11, iG10, this.p[4], 6);
        int iG14 = g(iG10, iG13, iG12, iG11, this.p[9], 15);
        int iG15 = g(iG11, iG14, iG13, iG12, this.p[1], 13);
        int iG16 = g(iG12, iG15, iG14, iG13, this.p[2], 11);
        int i101 = iB16 ^ iG16;
        int i102 = iG16 ^ i101;
        int i103 = i101 ^ i102;
        int iC = c(iB13, i103, iB15, iB14, this.p[3], 11);
        int iC2 = c(iB14, iC, i103, iB15, this.p[10], 13);
        int iC3 = c(iB15, iC2, iC, i103, this.p[14], 6);
        int iC4 = c(i103, iC3, iC2, iC, this.p[4], 7);
        int iC5 = c(iC, iC4, iC3, iC2, this.p[9], 14);
        int iC6 = c(iC2, iC5, iC4, iC3, this.p[15], 9);
        int iC7 = c(iC3, iC6, iC5, iC4, this.p[8], 13);
        int iC8 = c(iC4, iC7, iC6, iC5, this.p[1], 15);
        int iC9 = c(iC5, iC8, iC7, iC6, this.p[2], 14);
        int iC10 = c(iC6, iC9, iC8, iC7, this.p[7], 8);
        int iC11 = c(iC7, iC10, iC9, iC8, this.p[0], 13);
        int iC12 = c(iC8, iC11, iC10, iC9, this.p[6], 6);
        int iC13 = c(iC9, iC12, iC11, iC10, this.p[13], 5);
        int iC14 = c(iC10, iC13, iC12, iC11, this.p[11], 12);
        int iC15 = c(iC11, iC14, iC13, iC12, this.p[5], 7);
        int iC16 = c(iC12, iC15, iC14, iC13, this.p[12], 5);
        int iF = f(iG13, i102, iG15, iG14, this.p[15], 9);
        int iF2 = f(iG14, iF, i102, iG15, this.p[5], 7);
        int iF3 = f(iG15, iF2, iF, i102, this.p[1], 15);
        int iF4 = f(i102, iF3, iF2, iF, this.p[3], 11);
        int iF5 = f(iF, iF4, iF3, iF2, this.p[7], 8);
        int iF6 = f(iF2, iF5, iF4, iF3, this.p[14], 6);
        int iF7 = f(iF3, iF6, iF5, iF4, this.p[6], 6);
        int iF8 = f(iF4, iF7, iF6, iF5, this.p[9], 14);
        int iF9 = f(iF5, iF8, iF7, iF6, this.p[11], 12);
        int iF10 = f(iF6, iF9, iF8, iF7, this.p[8], 13);
        int iF11 = f(iF7, iF10, iF9, iF8, this.p[12], 5);
        int iF12 = f(iF8, iF11, iF10, iF9, this.p[2], 14);
        int iF13 = f(iF9, iF12, iF11, iF10, this.p[10], 13);
        int iF14 = f(iF10, iF13, iF12, iF11, this.p[0], 13);
        int iF15 = f(iF11, iF14, iF13, iF12, this.p[4], 7);
        int iF16 = f(iF12, iF15, iF14, iF13, this.p[13], 5);
        int i104 = iC15 ^ iF15;
        int i105 = iF15 ^ i104;
        int i106 = i104 ^ i105;
        int iD = d(iC13, iC16, i106, iC14, this.p[1], 11);
        int iD2 = d(iC14, iD, iC16, i106, this.p[9], 12);
        int iD3 = d(i106, iD2, iD, iC16, this.p[11], 14);
        int iD4 = d(iC16, iD3, iD2, iD, this.p[10], 15);
        int iD5 = d(iD, iD4, iD3, iD2, this.p[0], 14);
        int iD6 = d(iD2, iD5, iD4, iD3, this.p[8], 15);
        int iD7 = d(iD3, iD6, iD5, iD4, this.p[12], 9);
        int iD8 = d(iD4, iD7, iD6, iD5, this.p[4], 8);
        int iD9 = d(iD5, iD8, iD7, iD6, this.p[13], 9);
        int iD10 = d(iD6, iD9, iD8, iD7, this.p[3], 14);
        int iD11 = d(iD7, iD10, iD9, iD8, this.p[7], 5);
        int iD12 = d(iD8, iD11, iD10, iD9, this.p[15], 6);
        int iD13 = d(iD9, iD12, iD11, iD10, this.p[14], 8);
        int iD14 = d(iD10, iD13, iD12, iD11, this.p[5], 6);
        int iD15 = d(iD11, iD14, iD13, iD12, this.p[6], 5);
        int iD16 = d(iD12, iD15, iD14, iD13, this.p[2], 12);
        int iE = e(iF13, iF16, i105, iF14, this.p[8], 15);
        int iE2 = e(iF14, iE, iF16, i105, this.p[6], 5);
        int iE3 = e(i105, iE2, iE, iF16, this.p[4], 8);
        int iE4 = e(iF16, iE3, iE2, iE, this.p[1], 11);
        int iE5 = e(iE, iE4, iE3, iE2, this.p[3], 14);
        int iE6 = e(iE2, iE5, iE4, iE3, this.p[11], 14);
        int iE7 = e(iE3, iE6, iE5, iE4, this.p[15], 6);
        int iE8 = e(iE4, iE7, iE6, iE5, this.p[0], 14);
        int iE9 = e(iE5, iE8, iE7, iE6, this.p[5], 6);
        int iE10 = e(iE6, iE9, iE8, iE7, this.p[12], 9);
        int iE11 = e(iE7, iE10, iE9, iE8, this.p[2], 12);
        int iE12 = e(iE8, iE11, iE10, iE9, this.p[13], 9);
        int iE13 = e(iE9, iE12, iE11, iE10, this.p[9], 12);
        int iE14 = e(iE10, iE13, iE12, iE11, this.p[7], 5);
        int iE15 = e(iE11, iE14, iE13, iE12, this.p[10], 15);
        int iE16 = e(iE12, iE15, iE14, iE13, this.p[14], 8);
        int i107 = iD14 ^ iE14;
        int i108 = iE14 ^ i107;
        this.h += iD13;
        this.i += iD16;
        this.j += iD15;
        this.k += i107 ^ i108;
        this.l += iE13;
        this.m += iE16;
        this.n += iE15;
        this.o += i108;
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
        int i20 = i19 + 1;
        bArr[i19] = (byte) (i18 >> 16);
        int i21 = i20 + 1;
        bArr[i20] = (byte) (i18 >> 24);
        int i22 = i21 + 1;
        int i23 = this.l;
        bArr[i21] = (byte) i23;
        int i24 = i22 + 1;
        bArr[i22] = (byte) (i23 >> 8);
        int i25 = i24 + 1;
        bArr[i24] = (byte) (i23 >> 16);
        int i26 = i25 + 1;
        bArr[i25] = (byte) (i23 >> 24);
        int i27 = i26 + 1;
        int i28 = this.m;
        bArr[i26] = (byte) i28;
        int i29 = i27 + 1;
        bArr[i27] = (byte) (i28 >> 8);
        int i30 = i29 + 1;
        bArr[i29] = (byte) (i28 >> 16);
        int i31 = i30 + 1;
        bArr[i30] = (byte) (i28 >> 24);
        int i32 = i31 + 1;
        int i33 = this.n;
        bArr[i31] = (byte) i33;
        int i34 = i32 + 1;
        bArr[i32] = (byte) (i33 >> 8);
        int i35 = i34 + 1;
        bArr[i34] = (byte) (i33 >> 16);
        int i36 = i35 + 1;
        bArr[i35] = (byte) (i33 >> 24);
        int i37 = i36 + 1;
        int i38 = this.o;
        bArr[i36] = (byte) i38;
        int i39 = i37 + 1;
        bArr[i37] = (byte) (i38 >> 8);
        bArr[i39] = (byte) (i38 >> 16);
        bArr[i39 + 1] = (byte) (i38 >> 24);
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        RipeMd256 ripeMd256 = (RipeMd256) super.clone();
        ripeMd256.h = this.h;
        ripeMd256.i = this.i;
        ripeMd256.j = this.j;
        ripeMd256.k = this.k;
        ripeMd256.l = this.l;
        ripeMd256.m = this.m;
        ripeMd256.n = this.n;
        ripeMd256.o = this.o;
        System.arraycopy(this.p, 0, ripeMd256.p, 0, 16);
        System.arraycopy(this.g, 0, ripeMd256.g, 0, 8);
        return ripeMd256;
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    protected void engineReset() {
        this.f = 0L;
        this.h = 1732584193;
        this.i = -271733879;
        this.j = -1732584194;
        this.k = 271733878;
        this.l = 1985229328;
        this.m = -19088744;
        this.n = -1985229329;
        this.o = 19088743;
        CryptoUtils.zeroBlock(this.b);
        CryptoUtils.zeroBlock(this.g);
        CryptoUtils.zeroBlock(this.p);
    }
}
