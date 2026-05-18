package iaik.security.md;

/* JADX INFO: loaded from: classes.dex */
final class D extends B {
    D() {
    }

    private static int a(int i) {
        return (i >>> 22) | (i << 10);
    }

    private static int a(int i, int i2) {
        return (i >>> (32 - i2)) | (i << i2);
    }

    private static int a(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    private static int a(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(i + a(i2, i3, i4) + i6, i7) + i5;
    }

    private static int b(int i, int i2, int i3) {
        return ((~i) & i3) | (i2 & i);
    }

    private static int b(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(i + b(i2, i3, i4) + i6 + 1518500249, i7) + i5;
    }

    private static int c(int i, int i2, int i3) {
        return (i | (~i2)) ^ i3;
    }

    private static int c(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(i + c(i2, i3, i4) + i6 + 1859775393, i7) + i5;
    }

    private static int d(int i, int i2, int i3) {
        return (i & i3) | (i2 & (~i3));
    }

    private static int d(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(((i + d(i2, i3, i4)) + i6) - 1894007588, i7) + i5;
    }

    private static int e(int i, int i2, int i3) {
        return i ^ (i2 | (~i3));
    }

    private static int e(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(((i + e(i2, i3, i4)) + i6) - 1454113458, i7) + i5;
    }

    private static int f(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(i + a(i2, i3, i4) + i6, i7) + i5;
    }

    private static int g(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(i + b(i2, i3, i4) + i6 + 2053994217, i7) + i5;
    }

    private static int h(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(i + c(i2, i3, i4) + i6 + 1836072691, i7) + i5;
    }

    private static int i(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(i + d(i2, i3, i4) + i6 + 1548603684, i7) + i5;
    }

    private static int j(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        return a(i + e(i2, i3, i4) + i6 + 1352829926, i7) + i5;
    }

    @Override // iaik.security.md.A
    public void a(byte[] bArr, int i) {
        int[] iArr = this.f;
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = ((bArr[i2] & 255) << 8) | (bArr[i] & 255);
        int i5 = i3 + 1;
        int i6 = i4 | ((bArr[i3] & 255) << 16);
        int i7 = i5 + 1;
        iArr[0] = i6 | (bArr[i5] << 24);
        int[] iArr2 = this.f;
        int i8 = i7 + 1;
        int i9 = i8 + 1;
        int i10 = ((bArr[i8] & 255) << 8) | (bArr[i7] & 255);
        int i11 = i9 + 1;
        int i12 = i10 | ((bArr[i9] & 255) << 16);
        int i13 = i11 + 1;
        iArr2[1] = i12 | (bArr[i11] << 24);
        int[] iArr3 = this.f;
        int i14 = i13 + 1;
        int i15 = i14 + 1;
        int i16 = ((bArr[i14] & 255) << 8) | (bArr[i13] & 255);
        int i17 = i15 + 1;
        int i18 = i16 | ((bArr[i15] & 255) << 16);
        int i19 = i17 + 1;
        iArr3[2] = i18 | (bArr[i17] << 24);
        int[] iArr4 = this.f;
        int i20 = i19 + 1;
        int i21 = i20 + 1;
        int i22 = ((bArr[i20] & 255) << 8) | (bArr[i19] & 255);
        int i23 = i21 + 1;
        int i24 = i22 | ((bArr[i21] & 255) << 16);
        int i25 = i23 + 1;
        iArr4[3] = i24 | (bArr[i23] << 24);
        int[] iArr5 = this.f;
        int i26 = i25 + 1;
        int i27 = i26 + 1;
        int i28 = ((bArr[i26] & 255) << 8) | (bArr[i25] & 255);
        int i29 = i27 + 1;
        int i30 = i28 | ((bArr[i27] & 255) << 16);
        int i31 = i29 + 1;
        iArr5[4] = i30 | (bArr[i29] << 24);
        int[] iArr6 = this.f;
        int i32 = i31 + 1;
        int i33 = i32 + 1;
        int i34 = ((bArr[i32] & 255) << 8) | (bArr[i31] & 255);
        int i35 = i33 + 1;
        int i36 = i34 | ((bArr[i33] & 255) << 16);
        int i37 = i35 + 1;
        iArr6[5] = i36 | (bArr[i35] << 24);
        int[] iArr7 = this.f;
        int i38 = i37 + 1;
        int i39 = i38 + 1;
        int i40 = ((bArr[i38] & 255) << 8) | (bArr[i37] & 255);
        int i41 = i39 + 1;
        int i42 = i40 | ((bArr[i39] & 255) << 16);
        int i43 = i41 + 1;
        iArr7[6] = i42 | (bArr[i41] << 24);
        int[] iArr8 = this.f;
        int i44 = i43 + 1;
        int i45 = i44 + 1;
        int i46 = ((bArr[i44] & 255) << 8) | (bArr[i43] & 255);
        int i47 = i45 + 1;
        int i48 = i46 | ((bArr[i45] & 255) << 16);
        int i49 = i47 + 1;
        iArr8[7] = i48 | (bArr[i47] << 24);
        int[] iArr9 = this.f;
        int i50 = i49 + 1;
        int i51 = i50 + 1;
        int i52 = ((bArr[i50] & 255) << 8) | (bArr[i49] & 255);
        int i53 = i51 + 1;
        int i54 = i52 | ((bArr[i51] & 255) << 16);
        int i55 = i53 + 1;
        iArr9[8] = i54 | (bArr[i53] << 24);
        int[] iArr10 = this.f;
        int i56 = i55 + 1;
        int i57 = bArr[i55] & 255;
        int i58 = i56 + 1;
        int i59 = ((bArr[i56] & 255) << 8) | i57;
        int i60 = i58 + 1;
        int i61 = i59 | ((bArr[i58] & 255) << 16);
        int i62 = i60 + 1;
        iArr10[9] = i61 | (bArr[i60] << 24);
        int[] iArr11 = this.f;
        int i63 = i62 + 1;
        int i64 = i63 + 1;
        int i65 = ((bArr[i63] & 255) << 8) | (bArr[i62] & 255);
        int i66 = i64 + 1;
        int i67 = i65 | ((bArr[i64] & 255) << 16);
        int i68 = i66 + 1;
        iArr11[10] = i67 | (bArr[i66] << 24);
        int[] iArr12 = this.f;
        int i69 = i68 + 1;
        int i70 = i69 + 1;
        int i71 = ((bArr[i69] & 255) << 8) | (bArr[i68] & 255);
        int i72 = i70 + 1;
        int i73 = i71 | ((bArr[i70] & 255) << 16);
        int i74 = i72 + 1;
        iArr12[11] = i73 | (bArr[i72] << 24);
        int[] iArr13 = this.f;
        int i75 = i74 + 1;
        int i76 = i75 + 1;
        int i77 = ((bArr[i75] & 255) << 8) | (bArr[i74] & 255);
        int i78 = i76 + 1;
        int i79 = i78 + 1;
        iArr13[12] = i77 | ((bArr[i76] & 255) << 16) | (bArr[i78] << 24);
        int[] iArr14 = this.f;
        int i80 = i79 + 1;
        int i81 = i80 + 1;
        int i82 = ((bArr[i80] & 255) << 8) | (bArr[i79] & 255);
        int i83 = i81 + 1;
        int i84 = i83 + 1;
        iArr14[13] = i82 | ((bArr[i81] & 255) << 16) | (bArr[i83] << 24);
        int[] iArr15 = this.f;
        int i85 = i84 + 1;
        int i86 = i85 + 1;
        int i87 = ((bArr[i85] & 255) << 8) | (bArr[i84] & 255);
        int i88 = i86 + 1;
        int i89 = i88 + 1;
        iArr15[14] = i87 | ((bArr[i86] & 255) << 16) | (bArr[i88] << 24);
        int i90 = i89 + 1;
        int i91 = i90 + 1;
        this.f[15] = ((bArr[i90] & 255) << 8) | (bArr[i89] & 255) | ((bArr[i91] & 255) << 16) | (bArr[i91 + 1] << 24);
        int i92 = this.a;
        int i93 = this.b;
        int i94 = this.c;
        int i95 = this.d;
        int i96 = this.e;
        int i97 = this.a;
        int i98 = this.b;
        int i99 = this.c;
        int i100 = this.d;
        int i101 = this.e;
        int iA = a(i92, i93, i94, i95, i96, this.f[0], 11);
        int iA2 = a(i94);
        int iA3 = a(i96, iA, i93, iA2, i95, this.f[1], 14);
        int iA4 = a(i93);
        int iA5 = a(i95, iA3, iA, iA4, iA2, this.f[2], 15);
        int iA6 = a(iA);
        int iA7 = a(iA2, iA5, iA3, iA6, iA4, this.f[3], 12);
        int iA8 = a(iA3);
        int iA9 = a(iA4, iA7, iA5, iA8, iA6, this.f[4], 5);
        int iA10 = a(iA5);
        int iA11 = a(iA6, iA9, iA7, iA10, iA8, this.f[5], 8);
        int iA12 = a(iA7);
        int iA13 = a(iA8, iA11, iA9, iA12, iA10, this.f[6], 7);
        int iA14 = a(iA9);
        int iA15 = a(iA10, iA13, iA11, iA14, iA12, this.f[7], 9);
        int iA16 = a(iA11);
        int iA17 = a(iA12, iA15, iA13, iA16, iA14, this.f[8], 11);
        int iA18 = a(iA13);
        int iA19 = a(iA14, iA17, iA15, iA18, iA16, this.f[9], 13);
        int iA20 = a(iA15);
        int iA21 = a(iA16, iA19, iA17, iA20, iA18, this.f[10], 14);
        int iA22 = a(iA17);
        int iA23 = a(iA18, iA21, iA19, iA22, iA20, this.f[11], 15);
        int iA24 = a(iA19);
        int iA25 = a(iA20, iA23, iA21, iA24, iA22, this.f[12], 6);
        int iA26 = a(iA21);
        int iA27 = a(iA22, iA25, iA23, iA26, iA24, this.f[13], 7);
        int iA28 = a(iA23);
        int iA29 = a(iA24, iA27, iA25, iA28, iA26, this.f[14], 9);
        int iA30 = a(iA25);
        int iA31 = a(iA26, iA29, iA27, iA30, iA28, this.f[15], 8);
        int iA32 = a(iA27);
        int iJ = j(i97, i98, i99, i100, i101, this.f[5], 8);
        int iA33 = a(i99);
        int iJ2 = j(i101, iJ, i98, iA33, i100, this.f[14], 9);
        int iA34 = a(i98);
        int iJ3 = j(i100, iJ2, iJ, iA34, iA33, this.f[7], 9);
        int iA35 = a(iJ);
        int iJ4 = j(iA33, iJ3, iJ2, iA35, iA34, this.f[0], 11);
        int iA36 = a(iJ2);
        int iJ5 = j(iA34, iJ4, iJ3, iA36, iA35, this.f[9], 13);
        int iA37 = a(iJ3);
        int iJ6 = j(iA35, iJ5, iJ4, iA37, iA36, this.f[2], 15);
        int iA38 = a(iJ4);
        int iJ7 = j(iA36, iJ6, iJ5, iA38, iA37, this.f[11], 15);
        int iA39 = a(iJ5);
        int iJ8 = j(iA37, iJ7, iJ6, iA39, iA38, this.f[4], 5);
        int iA40 = a(iJ6);
        int iJ9 = j(iA38, iJ8, iJ7, iA40, iA39, this.f[13], 7);
        int iA41 = a(iJ7);
        int iJ10 = j(iA39, iJ9, iJ8, iA41, iA40, this.f[6], 7);
        int iA42 = a(iJ8);
        int iJ11 = j(iA40, iJ10, iJ9, iA42, iA41, this.f[15], 8);
        int iA43 = a(iJ9);
        int iJ12 = j(iA41, iJ11, iJ10, iA43, iA42, this.f[8], 11);
        int iA44 = a(iJ10);
        int iJ13 = j(iA42, iJ12, iJ11, iA44, iA43, this.f[1], 14);
        int iA45 = a(iJ11);
        int iJ14 = j(iA43, iJ13, iJ12, iA45, iA44, this.f[10], 14);
        int iA46 = a(iJ12);
        int iJ15 = j(iA44, iJ14, iJ13, iA46, iA45, this.f[3], 12);
        int iA47 = a(iJ13);
        int iJ16 = j(iA45, iJ15, iJ14, iA47, iA46, this.f[12], 6);
        int iA48 = a(iJ14);
        int iB = b(iA28, iA31, iA29, iA32, iA30, this.f[7], 7);
        int iA49 = a(iA29);
        int iB2 = b(iA30, iB, iA31, iA49, iA32, this.f[4], 6);
        int iA50 = a(iA31);
        int iB3 = b(iA32, iB2, iB, iA50, iA49, this.f[13], 8);
        int iA51 = a(iB);
        int iB4 = b(iA49, iB3, iB2, iA51, iA50, this.f[1], 13);
        int iA52 = a(iB2);
        int iB5 = b(iA50, iB4, iB3, iA52, iA51, this.f[10], 11);
        int iA53 = a(iB3);
        int iB6 = b(iA51, iB5, iB4, iA53, iA52, this.f[6], 9);
        int iA54 = a(iB4);
        int iB7 = b(iA52, iB6, iB5, iA54, iA53, this.f[15], 7);
        int iA55 = a(iB5);
        int iB8 = b(iA53, iB7, iB6, iA55, iA54, this.f[3], 15);
        int iA56 = a(iB6);
        int iB9 = b(iA54, iB8, iB7, iA56, iA55, this.f[12], 7);
        int iA57 = a(iB7);
        int iB10 = b(iA55, iB9, iB8, iA57, iA56, this.f[0], 12);
        int iA58 = a(iB8);
        int iB11 = b(iA56, iB10, iB9, iA58, iA57, this.f[9], 15);
        int iA59 = a(iB9);
        int iB12 = b(iA57, iB11, iB10, iA59, iA58, this.f[5], 9);
        int iA60 = a(iB10);
        int iB13 = b(iA58, iB12, iB11, iA60, iA59, this.f[2], 11);
        int iA61 = a(iB11);
        int iB14 = b(iA59, iB13, iB12, iA61, iA60, this.f[14], 7);
        int iA62 = a(iB12);
        int iB15 = b(iA60, iB14, iB13, iA62, iA61, this.f[11], 13);
        int iA63 = a(iB13);
        int iB16 = b(iA61, iB15, iB14, iA63, iA62, this.f[8], 12);
        int iA64 = a(iB14);
        int i102 = i(iA46, iJ16, iJ15, iA48, iA47, this.f[6], 9);
        int iA65 = a(iJ15);
        int i103 = i(iA47, i102, iJ16, iA65, iA48, this.f[11], 13);
        int iA66 = a(iJ16);
        int i104 = i(iA48, i103, i102, iA66, iA65, this.f[3], 15);
        int iA67 = a(i102);
        int i105 = i(iA65, i104, i103, iA67, iA66, this.f[7], 7);
        int iA68 = a(i103);
        int i106 = i(iA66, i105, i104, iA68, iA67, this.f[0], 12);
        int iA69 = a(i104);
        int i107 = i(iA67, i106, i105, iA69, iA68, this.f[13], 8);
        int iA70 = a(i105);
        int i108 = i(iA68, i107, i106, iA70, iA69, this.f[5], 9);
        int iA71 = a(i106);
        int i109 = i(iA69, i108, i107, iA71, iA70, this.f[10], 11);
        int iA72 = a(i107);
        int i110 = i(iA70, i109, i108, iA72, iA71, this.f[14], 7);
        int iA73 = a(i108);
        int i111 = i(iA71, i110, i109, iA73, iA72, this.f[15], 7);
        int iA74 = a(i109);
        int i112 = i(iA72, i111, i110, iA74, iA73, this.f[8], 12);
        int iA75 = a(i110);
        int i113 = i(iA73, i112, i111, iA75, iA74, this.f[12], 7);
        int iA76 = a(i111);
        int i114 = i(iA74, i113, i112, iA76, iA75, this.f[4], 6);
        int iA77 = a(i112);
        int i115 = i(iA75, i114, i113, iA77, iA76, this.f[9], 15);
        int iA78 = a(i113);
        int i116 = i(iA76, i115, i114, iA78, iA77, this.f[1], 13);
        int iA79 = a(i114);
        int i117 = i(iA77, i116, i115, iA79, iA78, this.f[2], 11);
        int iA80 = a(i115);
        int iC = c(iA62, iB16, iB15, iA64, iA63, this.f[3], 11);
        int iA81 = a(iB15);
        int iC2 = c(iA63, iC, iB16, iA81, iA64, this.f[10], 13);
        int iA82 = a(iB16);
        int iC3 = c(iA64, iC2, iC, iA82, iA81, this.f[14], 6);
        int iA83 = a(iC);
        int iC4 = c(iA81, iC3, iC2, iA83, iA82, this.f[4], 7);
        int iA84 = a(iC2);
        int iC5 = c(iA82, iC4, iC3, iA84, iA83, this.f[9], 14);
        int iA85 = a(iC3);
        int iC6 = c(iA83, iC5, iC4, iA85, iA84, this.f[15], 9);
        int iA86 = a(iC4);
        int iC7 = c(iA84, iC6, iC5, iA86, iA85, this.f[8], 13);
        int iA87 = a(iC5);
        int iC8 = c(iA85, iC7, iC6, iA87, iA86, this.f[1], 15);
        int iA88 = a(iC6);
        int iC9 = c(iA86, iC8, iC7, iA88, iA87, this.f[2], 14);
        int iA89 = a(iC7);
        int iC10 = c(iA87, iC9, iC8, iA89, iA88, this.f[7], 8);
        int iA90 = a(iC8);
        int iC11 = c(iA88, iC10, iC9, iA90, iA89, this.f[0], 13);
        int iA91 = a(iC9);
        int iC12 = c(iA89, iC11, iC10, iA91, iA90, this.f[6], 6);
        int iA92 = a(iC10);
        int iC13 = c(iA90, iC12, iC11, iA92, iA91, this.f[13], 5);
        int iA93 = a(iC11);
        int iC14 = c(iA91, iC13, iC12, iA93, iA92, this.f[11], 12);
        int iA94 = a(iC12);
        int iC15 = c(iA92, iC14, iC13, iA94, iA93, this.f[5], 7);
        int iA95 = a(iC13);
        int iC16 = c(iA93, iC15, iC14, iA95, iA94, this.f[12], 5);
        int iA96 = a(iC14);
        int iH = h(iA78, i117, i116, iA80, iA79, this.f[15], 9);
        int iA97 = a(i116);
        int iH2 = h(iA79, iH, i117, iA97, iA80, this.f[5], 7);
        int iA98 = a(i117);
        int iH3 = h(iA80, iH2, iH, iA98, iA97, this.f[1], 15);
        int iA99 = a(iH);
        int iH4 = h(iA97, iH3, iH2, iA99, iA98, this.f[3], 11);
        int iA100 = a(iH2);
        int iH5 = h(iA98, iH4, iH3, iA100, iA99, this.f[7], 8);
        int iA101 = a(iH3);
        int iH6 = h(iA99, iH5, iH4, iA101, iA100, this.f[14], 6);
        int iA102 = a(iH4);
        int iH7 = h(iA100, iH6, iH5, iA102, iA101, this.f[6], 6);
        int iA103 = a(iH5);
        int iH8 = h(iA101, iH7, iH6, iA103, iA102, this.f[9], 14);
        int iA104 = a(iH6);
        int iH9 = h(iA102, iH8, iH7, iA104, iA103, this.f[11], 12);
        int iA105 = a(iH7);
        int iH10 = h(iA103, iH9, iH8, iA105, iA104, this.f[8], 13);
        int iA106 = a(iH8);
        int iH11 = h(iA104, iH10, iH9, iA106, iA105, this.f[12], 5);
        int iA107 = a(iH9);
        int iH12 = h(iA105, iH11, iH10, iA107, iA106, this.f[2], 14);
        int iA108 = a(iH10);
        int iH13 = h(iA106, iH12, iH11, iA108, iA107, this.f[10], 13);
        int iA109 = a(iH11);
        int iH14 = h(iA107, iH13, iH12, iA109, iA108, this.f[0], 13);
        int iA110 = a(iH12);
        int iH15 = h(iA108, iH14, iH13, iA110, iA109, this.f[4], 7);
        int iA111 = a(iH13);
        int iH16 = h(iA109, iH15, iH14, iA111, iA110, this.f[13], 5);
        int iA112 = a(iH14);
        int iD = d(iA94, iC16, iC15, iA96, iA95, this.f[1], 11);
        int iA113 = a(iC15);
        int iD2 = d(iA95, iD, iC16, iA113, iA96, this.f[9], 12);
        int iA114 = a(iC16);
        int iD3 = d(iA96, iD2, iD, iA114, iA113, this.f[11], 14);
        int iA115 = a(iD);
        int iD4 = d(iA113, iD3, iD2, iA115, iA114, this.f[10], 15);
        int iA116 = a(iD2);
        int iD5 = d(iA114, iD4, iD3, iA116, iA115, this.f[0], 14);
        int iA117 = a(iD3);
        int iD6 = d(iA115, iD5, iD4, iA117, iA116, this.f[8], 15);
        int iA118 = a(iD4);
        int iD7 = d(iA116, iD6, iD5, iA118, iA117, this.f[12], 9);
        int iA119 = a(iD5);
        int iD8 = d(iA117, iD7, iD6, iA119, iA118, this.f[4], 8);
        int iA120 = a(iD6);
        int iD9 = d(iA118, iD8, iD7, iA120, iA119, this.f[13], 9);
        int iA121 = a(iD7);
        int iD10 = d(iA119, iD9, iD8, iA121, iA120, this.f[3], 14);
        int iA122 = a(iD8);
        int iD11 = d(iA120, iD10, iD9, iA122, iA121, this.f[7], 5);
        int iA123 = a(iD9);
        int iD12 = d(iA121, iD11, iD10, iA123, iA122, this.f[15], 6);
        int iA124 = a(iD10);
        int iD13 = d(iA122, iD12, iD11, iA124, iA123, this.f[14], 8);
        int iA125 = a(iD11);
        int iD14 = d(iA123, iD13, iD12, iA125, iA124, this.f[5], 6);
        int iA126 = a(iD12);
        int iD15 = d(iA124, iD14, iD13, iA126, iA125, this.f[6], 5);
        int iA127 = a(iD13);
        int iD16 = d(iA125, iD15, iD14, iA127, iA126, this.f[2], 12);
        int iA128 = a(iD14);
        int iG = g(iA110, iH16, iH15, iA112, iA111, this.f[8], 15);
        int iA129 = a(iH15);
        int iG2 = g(iA111, iG, iH16, iA129, iA112, this.f[6], 5);
        int iA130 = a(iH16);
        int iG3 = g(iA112, iG2, iG, iA130, iA129, this.f[4], 8);
        int iA131 = a(iG);
        int iG4 = g(iA129, iG3, iG2, iA131, iA130, this.f[1], 11);
        int iA132 = a(iG2);
        int iG5 = g(iA130, iG4, iG3, iA132, iA131, this.f[3], 14);
        int iA133 = a(iG3);
        int iG6 = g(iA131, iG5, iG4, iA133, iA132, this.f[11], 14);
        int iA134 = a(iG4);
        int iG7 = g(iA132, iG6, iG5, iA134, iA133, this.f[15], 6);
        int iA135 = a(iG5);
        int iG8 = g(iA133, iG7, iG6, iA135, iA134, this.f[0], 14);
        int iA136 = a(iG6);
        int iG9 = g(iA134, iG8, iG7, iA136, iA135, this.f[5], 6);
        int iA137 = a(iG7);
        int iG10 = g(iA135, iG9, iG8, iA137, iA136, this.f[12], 9);
        int iA138 = a(iG8);
        int iG11 = g(iA136, iG10, iG9, iA138, iA137, this.f[2], 12);
        int iA139 = a(iG9);
        int iG12 = g(iA137, iG11, iG10, iA139, iA138, this.f[13], 9);
        int iA140 = a(iG10);
        int iG13 = g(iA138, iG12, iG11, iA140, iA139, this.f[9], 12);
        int iA141 = a(iG11);
        int iG14 = g(iA139, iG13, iG12, iA141, iA140, this.f[7], 5);
        int iA142 = a(iG12);
        int iG15 = g(iA140, iG14, iG13, iA142, iA141, this.f[10], 15);
        int iA143 = a(iG13);
        int iG16 = g(iA141, iG15, iG14, iA143, iA142, this.f[14], 8);
        int iA144 = a(iG14);
        int iE = e(iA126, iD16, iD15, iA128, iA127, this.f[4], 9);
        int iA145 = a(iD15);
        int iE2 = e(iA127, iE, iD16, iA145, iA128, this.f[0], 15);
        int iA146 = a(iD16);
        int iE3 = e(iA128, iE2, iE, iA146, iA145, this.f[5], 5);
        int iA147 = a(iE);
        int iE4 = e(iA145, iE3, iE2, iA147, iA146, this.f[9], 11);
        int iA148 = a(iE2);
        int iE5 = e(iA146, iE4, iE3, iA148, iA147, this.f[7], 6);
        int iA149 = a(iE3);
        int iE6 = e(iA147, iE5, iE4, iA149, iA148, this.f[12], 8);
        int iA150 = a(iE4);
        int iE7 = e(iA148, iE6, iE5, iA150, iA149, this.f[2], 13);
        int iA151 = a(iE5);
        int iE8 = e(iA149, iE7, iE6, iA151, iA150, this.f[10], 12);
        int iA152 = a(iE6);
        int iE9 = e(iA150, iE8, iE7, iA152, iA151, this.f[14], 5);
        int iA153 = a(iE7);
        int iE10 = e(iA151, iE9, iE8, iA153, iA152, this.f[1], 12);
        int iA154 = a(iE8);
        int iE11 = e(iA152, iE10, iE9, iA154, iA153, this.f[3], 13);
        int iA155 = a(iE9);
        int iE12 = e(iA153, iE11, iE10, iA155, iA154, this.f[8], 14);
        int iA156 = a(iE10);
        int iE13 = e(iA154, iE12, iE11, iA156, iA155, this.f[11], 11);
        int iA157 = a(iE11);
        int iE14 = e(iA155, iE13, iE12, iA157, iA156, this.f[6], 8);
        int iA158 = a(iE12);
        int iE15 = e(iA156, iE14, iE13, iA158, iA157, this.f[15], 5);
        int iA159 = a(iE13);
        int iE16 = e(iA157, iE15, iE14, iA159, iA158, this.f[13], 6);
        int iA160 = a(iE14);
        int iF = f(iA142, iG16, iG15, iA144, iA143, this.f[12], 8);
        int iA161 = a(iG15);
        int iF2 = f(iA143, iF, iG16, iA161, iA144, this.f[15], 5);
        int iA162 = a(iG16);
        int iF3 = f(iA144, iF2, iF, iA162, iA161, this.f[10], 12);
        int iA163 = a(iF);
        int iF4 = f(iA161, iF3, iF2, iA163, iA162, this.f[4], 9);
        int iA164 = a(iF2);
        int iF5 = f(iA162, iF4, iF3, iA164, iA163, this.f[1], 12);
        int iA165 = a(iF3);
        int iF6 = f(iA163, iF5, iF4, iA165, iA164, this.f[5], 5);
        int iA166 = a(iF4);
        int iF7 = f(iA164, iF6, iF5, iA166, iA165, this.f[8], 14);
        int iA167 = a(iF5);
        int iF8 = f(iA165, iF7, iF6, iA167, iA166, this.f[7], 6);
        int iA168 = a(iF6);
        int iF9 = f(iA166, iF8, iF7, iA168, iA167, this.f[6], 8);
        int iA169 = a(iF7);
        int iF10 = f(iA167, iF9, iF8, iA169, iA168, this.f[2], 13);
        int iA170 = a(iF8);
        int iF11 = f(iA168, iF10, iF9, iA170, iA169, this.f[13], 6);
        int iA171 = a(iF9);
        int iF12 = f(iA169, iF11, iF10, iA171, iA170, this.f[14], 5);
        int iA172 = a(iF10);
        int iF13 = f(iA170, iF12, iF11, iA172, iA171, this.f[0], 15);
        int iA173 = a(iF11);
        int iF14 = f(iA171, iF13, iF12, iA173, iA172, this.f[3], 13);
        int iA174 = a(iF12);
        int iF15 = f(iA172, iF14, iF13, iA174, iA173, this.f[9], 11);
        int iA175 = a(iF13);
        int iF16 = f(iA173, iF15, iF14, iA175, iA174, this.f[11], 11);
        int iA176 = a(iF14) + iE15 + this.b;
        this.b = this.c + iA160 + iA175;
        this.c = this.d + iA159 + iA174;
        this.d = this.e + iA158 + iF16;
        this.e = this.a + iE16 + iF15;
        this.a = iA176;
    }
}
