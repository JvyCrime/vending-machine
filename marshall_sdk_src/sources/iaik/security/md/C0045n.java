package iaik.security.md;

import iaik.utils.CryptoUtils;

/* JADX INFO: renamed from: iaik.security.md.n, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0045n extends AbstractC0044m {
    private static final int[] i;
    private final int[] j;
    private int[] k;
    private byte[] l;
    private int[] m;

    static {
        int[] iArr = new int[336];
        i = iArr;
        CryptoUtils.squashBytesToIntsLE(g, 0, iArr, 0, iArr.length);
    }

    public C0045n(int i2, int i3, byte[] bArr) {
        super(i2, i3);
        int[] iArr = new int[32];
        this.j = iArr;
        this.k = new int[32];
        this.m = new int[16];
        CryptoUtils.squashBytesToIntsLE(bArr, 0, iArr, 0, iArr.length);
        this.l = new byte[((i2 + 7) >>> 3) << 3];
        engineReset();
    }

    private static int a(int i2) {
        return ((i2 & (-1431655766)) >>> 1) | ((1431655765 & i2) << 1);
    }

    private static void a(int[] iArr) {
        int i2;
        for (int i3 = 0; i3 < 42; i3 += 7) {
            int i4 = 0;
            while (true) {
                if (i4 >= 4) {
                    break;
                }
                int i5 = i4 + 8;
                int i6 = i4 + 16;
                int i7 = i4 + 24;
                int[] iArr2 = i;
                int i8 = (i3 << 3) + i4;
                int i9 = iArr2[i8];
                iArr[i7] = ~iArr[i7];
                iArr[i4] = iArr[i4] ^ ((~iArr[i6]) & i9);
                int i10 = i9 ^ (iArr[i4] & iArr[i5]);
                iArr[i4] = iArr[i4] ^ (iArr[i6] & iArr[i7]);
                iArr[i7] = iArr[i7] ^ ((~iArr[i5]) & iArr[i6]);
                iArr[i5] = iArr[i5] ^ (iArr[i4] & iArr[i6]);
                iArr[i6] = iArr[i6] ^ (iArr[i4] & (~iArr[i7]));
                iArr[i4] = iArr[i4] ^ (iArr[i5] | iArr[i7]);
                iArr[i7] = iArr[i7] ^ (iArr[i5] & iArr[i6]);
                iArr[i5] = iArr[i5] ^ (iArr[i4] & i10);
                iArr[i6] = i10 ^ iArr[i6];
                int i11 = i4 + 4;
                int i12 = i4 + 12;
                int i13 = i4 + 20;
                int i14 = i4 + 28;
                int i15 = iArr2[i8 + 4];
                iArr[i14] = ~iArr[i14];
                iArr[i11] = iArr[i11] ^ ((~iArr[i13]) & i15);
                int i16 = i15 ^ (iArr[i11] & iArr[i12]);
                iArr[i11] = iArr[i11] ^ (iArr[i13] & iArr[i14]);
                iArr[i14] = iArr[i14] ^ ((~iArr[i12]) & iArr[i13]);
                iArr[i12] = iArr[i12] ^ (iArr[i11] & iArr[i13]);
                iArr[i13] = iArr[i13] ^ (iArr[i11] & (~iArr[i14]));
                iArr[i11] = iArr[i11] ^ (iArr[i12] | iArr[i14]);
                iArr[i14] = iArr[i14] ^ (iArr[i12] & iArr[i13]);
                iArr[i12] = iArr[i12] ^ (iArr[i11] & i16);
                iArr[i13] = i16 ^ iArr[i13];
                a(iArr, i4, i5, i6, i7, i11, i12, i13, i14);
                iArr[i11] = a(iArr[i11]);
                iArr[i12] = a(iArr[i12]);
                iArr[i13] = a(iArr[i13]);
                iArr[i14] = a(iArr[i14]);
                i4++;
            }
            for (int i17 = 0; i17 < 4; i17++) {
                int i18 = i17 + 8;
                int i19 = i17 + 16;
                int i20 = i17 + 24;
                int[] iArr3 = i;
                int i21 = ((i3 + 1) << 3) + i17;
                int i22 = iArr3[i21];
                iArr[i20] = ~iArr[i20];
                iArr[i17] = iArr[i17] ^ ((~iArr[i19]) & i22);
                int i23 = i22 ^ (iArr[i17] & iArr[i18]);
                iArr[i17] = iArr[i17] ^ (iArr[i19] & iArr[i20]);
                iArr[i20] = iArr[i20] ^ ((~iArr[i18]) & iArr[i19]);
                iArr[i18] = iArr[i18] ^ (iArr[i17] & iArr[i19]);
                iArr[i19] = iArr[i19] ^ (iArr[i17] & (~iArr[i20]));
                iArr[i17] = iArr[i17] ^ (iArr[i18] | iArr[i20]);
                iArr[i20] = iArr[i20] ^ (iArr[i18] & iArr[i19]);
                iArr[i18] = iArr[i18] ^ (iArr[i17] & i23);
                iArr[i19] = i23 ^ iArr[i19];
                int i24 = i17 + 4;
                int i25 = i17 + 12;
                int i26 = i17 + 20;
                int i27 = i17 + 28;
                int i28 = iArr3[i21 + 4];
                iArr[i27] = ~iArr[i27];
                iArr[i24] = iArr[i24] ^ ((~iArr[i26]) & i28);
                int i29 = i28 ^ (iArr[i24] & iArr[i25]);
                iArr[i24] = iArr[i24] ^ (iArr[i26] & iArr[i27]);
                iArr[i27] = iArr[i27] ^ ((~iArr[i25]) & iArr[i26]);
                iArr[i25] = iArr[i25] ^ (iArr[i24] & iArr[i26]);
                iArr[i26] = iArr[i26] ^ (iArr[i24] & (~iArr[i27]));
                iArr[i24] = iArr[i24] ^ (iArr[i25] | iArr[i27]);
                iArr[i27] = iArr[i27] ^ (iArr[i25] & iArr[i26]);
                iArr[i25] = iArr[i25] ^ (iArr[i24] & i29);
                iArr[i26] = i29 ^ iArr[i26];
                a(iArr, i17, i18, i19, i20, i24, i25, i26, i27);
                iArr[i24] = b(iArr[i24]);
                iArr[i25] = b(iArr[i25]);
                iArr[i26] = b(iArr[i26]);
                iArr[i27] = b(iArr[i27]);
            }
            for (int i30 = 0; i30 < 4; i30++) {
                int i31 = i30 + 8;
                int i32 = i30 + 16;
                int i33 = i30 + 24;
                int[] iArr4 = i;
                int i34 = ((i3 + 2) << 3) + i30;
                int i35 = iArr4[i34];
                iArr[i33] = ~iArr[i33];
                iArr[i30] = iArr[i30] ^ ((~iArr[i32]) & i35);
                int i36 = i35 ^ (iArr[i30] & iArr[i31]);
                iArr[i30] = iArr[i30] ^ (iArr[i32] & iArr[i33]);
                iArr[i33] = iArr[i33] ^ ((~iArr[i31]) & iArr[i32]);
                iArr[i31] = iArr[i31] ^ (iArr[i30] & iArr[i32]);
                iArr[i32] = iArr[i32] ^ (iArr[i30] & (~iArr[i33]));
                iArr[i30] = iArr[i30] ^ (iArr[i31] | iArr[i33]);
                iArr[i33] = iArr[i33] ^ (iArr[i31] & iArr[i32]);
                iArr[i31] = iArr[i31] ^ (iArr[i30] & i36);
                iArr[i32] = i36 ^ iArr[i32];
                int i37 = i30 + 4;
                int i38 = i30 + 12;
                int i39 = i30 + 20;
                int i40 = i30 + 28;
                int i41 = iArr4[i34 + 4];
                iArr[i40] = ~iArr[i40];
                iArr[i37] = iArr[i37] ^ ((~iArr[i39]) & i41);
                int i42 = i41 ^ (iArr[i37] & iArr[i38]);
                iArr[i37] = iArr[i37] ^ (iArr[i39] & iArr[i40]);
                iArr[i40] = iArr[i40] ^ ((~iArr[i38]) & iArr[i39]);
                iArr[i38] = iArr[i38] ^ (iArr[i37] & iArr[i39]);
                iArr[i39] = iArr[i39] ^ (iArr[i37] & (~iArr[i40]));
                iArr[i37] = iArr[i37] ^ (iArr[i38] | iArr[i40]);
                iArr[i40] = iArr[i40] ^ (iArr[i38] & iArr[i39]);
                iArr[i38] = iArr[i38] ^ (iArr[i37] & i42);
                iArr[i39] = i42 ^ iArr[i39];
                a(iArr, i30, i31, i32, i33, i37, i38, i39, i40);
                iArr[i37] = c(iArr[i37]);
                iArr[i38] = c(iArr[i38]);
                iArr[i39] = c(iArr[i39]);
                iArr[i40] = c(iArr[i40]);
            }
            for (int i43 = 0; i43 < 4; i43++) {
                int i44 = i43 + 8;
                int i45 = i43 + 16;
                int i46 = i43 + 24;
                int[] iArr5 = i;
                int i47 = ((i3 + 3) << 3) + i43;
                int i48 = iArr5[i47];
                iArr[i46] = ~iArr[i46];
                iArr[i43] = iArr[i43] ^ ((~iArr[i45]) & i48);
                int i49 = i48 ^ (iArr[i43] & iArr[i44]);
                iArr[i43] = iArr[i43] ^ (iArr[i45] & iArr[i46]);
                iArr[i46] = iArr[i46] ^ ((~iArr[i44]) & iArr[i45]);
                iArr[i44] = iArr[i44] ^ (iArr[i43] & iArr[i45]);
                iArr[i45] = iArr[i45] ^ (iArr[i43] & (~iArr[i46]));
                iArr[i43] = iArr[i43] ^ (iArr[i44] | iArr[i46]);
                iArr[i46] = iArr[i46] ^ (iArr[i44] & iArr[i45]);
                iArr[i44] = iArr[i44] ^ (iArr[i43] & i49);
                iArr[i45] = i49 ^ iArr[i45];
                int i50 = i43 + 4;
                int i51 = i43 + 12;
                int i52 = i43 + 20;
                int i53 = i43 + 28;
                int i54 = iArr5[i47 + 4];
                iArr[i53] = ~iArr[i53];
                iArr[i50] = iArr[i50] ^ ((~iArr[i52]) & i54);
                int i55 = i54 ^ (iArr[i50] & iArr[i51]);
                iArr[i50] = iArr[i50] ^ (iArr[i52] & iArr[i53]);
                iArr[i53] = iArr[i53] ^ ((~iArr[i51]) & iArr[i52]);
                iArr[i51] = iArr[i51] ^ (iArr[i50] & iArr[i52]);
                iArr[i52] = iArr[i52] ^ (iArr[i50] & (~iArr[i53]));
                iArr[i50] = iArr[i50] ^ (iArr[i51] | iArr[i53]);
                iArr[i53] = iArr[i53] ^ (iArr[i51] & iArr[i52]);
                iArr[i51] = iArr[i51] ^ (iArr[i50] & i55);
                iArr[i52] = i55 ^ iArr[i52];
                a(iArr, i43, i44, i45, i46, i50, i51, i52, i53);
                iArr[i50] = d(iArr[i50]);
                iArr[i51] = d(iArr[i51]);
                iArr[i52] = d(iArr[i52]);
                iArr[i53] = d(iArr[i53]);
            }
            for (int i56 = 0; i56 < 4; i56++) {
                int i57 = i56 + 8;
                int i58 = i56 + 16;
                int i59 = i56 + 24;
                int[] iArr6 = i;
                int i60 = ((i3 + 4) << 3) + i56;
                int i61 = iArr6[i60];
                iArr[i59] = ~iArr[i59];
                iArr[i56] = iArr[i56] ^ ((~iArr[i58]) & i61);
                int i62 = i61 ^ (iArr[i56] & iArr[i57]);
                iArr[i56] = iArr[i56] ^ (iArr[i58] & iArr[i59]);
                iArr[i59] = iArr[i59] ^ ((~iArr[i57]) & iArr[i58]);
                iArr[i57] = iArr[i57] ^ (iArr[i56] & iArr[i58]);
                iArr[i58] = iArr[i58] ^ (iArr[i56] & (~iArr[i59]));
                iArr[i56] = iArr[i56] ^ (iArr[i57] | iArr[i59]);
                iArr[i59] = iArr[i59] ^ (iArr[i57] & iArr[i58]);
                iArr[i57] = iArr[i57] ^ (iArr[i56] & i62);
                iArr[i58] = i62 ^ iArr[i58];
                int i63 = i56 + 4;
                int i64 = i56 + 12;
                int i65 = i56 + 20;
                int i66 = i56 + 28;
                int i67 = iArr6[i60 + 4];
                iArr[i66] = ~iArr[i66];
                iArr[i63] = iArr[i63] ^ ((~iArr[i65]) & i67);
                int i68 = i67 ^ (iArr[i63] & iArr[i64]);
                iArr[i63] = iArr[i63] ^ (iArr[i65] & iArr[i66]);
                iArr[i66] = iArr[i66] ^ ((~iArr[i64]) & iArr[i65]);
                iArr[i64] = iArr[i64] ^ (iArr[i63] & iArr[i65]);
                iArr[i65] = iArr[i65] ^ (iArr[i63] & (~iArr[i66]));
                iArr[i63] = iArr[i63] ^ (iArr[i64] | iArr[i66]);
                iArr[i66] = iArr[i66] ^ (iArr[i64] & iArr[i65]);
                iArr[i64] = iArr[i64] ^ (iArr[i63] & i68);
                iArr[i65] = i68 ^ iArr[i65];
                a(iArr, i56, i57, i58, i59, i63, i64, i65, i66);
                iArr[i63] = e(iArr[i63]);
                iArr[i64] = e(iArr[i64]);
                iArr[i65] = e(iArr[i65]);
                iArr[i66] = e(iArr[i66]);
            }
            for (int i69 = 0; i69 < 4; i69++) {
                int i70 = i69 + 8;
                int i71 = i69 + 16;
                int i72 = i69 + 24;
                int[] iArr7 = i;
                int i73 = ((i3 + 5) << 3) + i69;
                int i74 = iArr7[i73];
                iArr[i72] = ~iArr[i72];
                iArr[i69] = iArr[i69] ^ ((~iArr[i71]) & i74);
                int i75 = i74 ^ (iArr[i69] & iArr[i70]);
                iArr[i69] = iArr[i69] ^ (iArr[i71] & iArr[i72]);
                iArr[i72] = iArr[i72] ^ ((~iArr[i70]) & iArr[i71]);
                iArr[i70] = iArr[i70] ^ (iArr[i69] & iArr[i71]);
                iArr[i71] = iArr[i71] ^ (iArr[i69] & (~iArr[i72]));
                iArr[i69] = iArr[i69] ^ (iArr[i70] | iArr[i72]);
                iArr[i72] = iArr[i72] ^ (iArr[i70] & iArr[i71]);
                iArr[i70] = iArr[i70] ^ (iArr[i69] & i75);
                iArr[i71] = i75 ^ iArr[i71];
                int i76 = i69 + 4;
                int i77 = i69 + 12;
                int i78 = i69 + 20;
                int i79 = i69 + 28;
                int i80 = iArr7[i73 + 4];
                iArr[i79] = ~iArr[i79];
                iArr[i76] = iArr[i76] ^ ((~iArr[i78]) & i80);
                int i81 = i80 ^ (iArr[i76] & iArr[i77]);
                iArr[i76] = iArr[i76] ^ (iArr[i78] & iArr[i79]);
                iArr[i79] = iArr[i79] ^ ((~iArr[i77]) & iArr[i78]);
                iArr[i77] = iArr[i77] ^ (iArr[i76] & iArr[i78]);
                iArr[i78] = iArr[i78] ^ (iArr[i76] & (~iArr[i79]));
                iArr[i76] = iArr[i76] ^ (iArr[i77] | iArr[i79]);
                iArr[i79] = iArr[i79] ^ (iArr[i77] & iArr[i78]);
                iArr[i77] = iArr[i77] ^ (iArr[i76] & i81);
                iArr[i78] = i81 ^ iArr[i78];
                a(iArr, i69, i70, i71, i72, i76, i77, i78, i79);
            }
            for (int i82 = 4; i82 < 32; i82 += 8) {
                int i83 = iArr[i82];
                int i84 = i82 + 1;
                iArr[i82] = iArr[i84];
                iArr[i84] = i83;
                int i85 = i82 + 2;
                int i86 = iArr[i85];
                int i87 = i82 + 3;
                iArr[i85] = iArr[i87];
                iArr[i87] = i86;
            }
            for (int i88 = 0; i88 < 4; i88++) {
                int i89 = i88 + 8;
                int i90 = i88 + 16;
                int i91 = i88 + 24;
                int[] iArr8 = i;
                int i92 = ((i3 + 6) << 3) + i88;
                int i93 = iArr8[i92];
                iArr[i91] = ~iArr[i91];
                iArr[i88] = iArr[i88] ^ ((~iArr[i90]) & i93);
                int i94 = i93 ^ (iArr[i88] & iArr[i89]);
                iArr[i88] = iArr[i88] ^ (iArr[i90] & iArr[i91]);
                iArr[i91] = iArr[i91] ^ ((~iArr[i89]) & iArr[i90]);
                iArr[i89] = iArr[i89] ^ (iArr[i88] & iArr[i90]);
                iArr[i90] = iArr[i90] ^ (iArr[i88] & (~iArr[i91]));
                iArr[i88] = iArr[i88] ^ (iArr[i89] | iArr[i91]);
                iArr[i91] = iArr[i91] ^ (iArr[i89] & iArr[i90]);
                iArr[i89] = iArr[i89] ^ (iArr[i88] & i94);
                iArr[i90] = i94 ^ iArr[i90];
                int i95 = i88 + 4;
                int i96 = i88 + 12;
                int i97 = i88 + 20;
                int i98 = i88 + 28;
                int i99 = iArr8[i92 + 4];
                iArr[i98] = ~iArr[i98];
                iArr[i95] = iArr[i95] ^ ((~iArr[i97]) & i99);
                int i100 = i99 ^ (iArr[i95] & iArr[i96]);
                iArr[i95] = iArr[i95] ^ (iArr[i97] & iArr[i98]);
                iArr[i98] = iArr[i98] ^ ((~iArr[i96]) & iArr[i97]);
                iArr[i96] = iArr[i96] ^ (iArr[i95] & iArr[i97]);
                iArr[i97] = iArr[i97] ^ (iArr[i95] & (~iArr[i98]));
                iArr[i95] = iArr[i95] ^ (iArr[i96] | iArr[i98]);
                iArr[i98] = iArr[i98] ^ (iArr[i96] & iArr[i97]);
                iArr[i96] = iArr[i96] ^ (iArr[i95] & i100);
                iArr[i97] = i100 ^ iArr[i97];
                a(iArr, i88, i89, i90, i91, i95, i96, i97, i98);
            }
            for (i2 = 4; i2 < 32; i2 += 8) {
                int i101 = iArr[i2];
                int i102 = i2 + 2;
                iArr[i2] = iArr[i102];
                iArr[i102] = i101;
                int i103 = i2 + 1;
                int i104 = iArr[i103];
                int i105 = i2 + 3;
                iArr[i103] = iArr[i105];
                iArr[i105] = i104;
            }
        }
    }

    private static void a(int[] iArr, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        iArr[i6] = iArr[i6] ^ iArr[i3];
        iArr[i7] = iArr[i7] ^ iArr[i4];
        iArr[i8] = iArr[i8] ^ (iArr[i2] ^ iArr[i5]);
        iArr[i9] = iArr[i9] ^ iArr[i2];
        iArr[i2] = iArr[i7] ^ iArr[i2];
        iArr[i3] = iArr[i3] ^ iArr[i8];
        iArr[i4] = iArr[i4] ^ (iArr[i6] ^ iArr[i9]);
        iArr[i5] = iArr[i5] ^ iArr[i6];
    }

    private static int b(int i2) {
        return ((i2 & (-858993460)) >>> 2) | ((858993459 & i2) << 2);
    }

    private static int c(int i2) {
        return ((i2 & (-252645136)) >>> 4) | ((252645135 & i2) << 4);
    }

    private static int d(int i2) {
        return ((i2 & (-16711936)) >>> 8) | ((16711935 & i2) << 8);
    }

    private static int e(int i2) {
        return (i2 >>> 16) | (i2 << 16);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a(byte[] bArr, int i2) {
        int[] iArr = this.m;
        CryptoUtils.squashBytesToIntsLE(bArr, i2, iArr, 0, iArr.length);
        for (int i3 = 0; i3 < 16; i3 += 4) {
            int[] iArr2 = this.k;
            int i4 = iArr2[i3];
            int[] iArr3 = this.m;
            iArr2[i3] = i4 ^ iArr3[i3];
            int i5 = i3 + 1;
            iArr2[i5] = iArr2[i5] ^ iArr3[i5];
            int i6 = i3 + 2;
            iArr2[i6] = iArr2[i6] ^ iArr3[i6];
            int i7 = i3 + 3;
            iArr2[i7] = iArr3[i7] ^ iArr2[i7];
        }
        a(this.k);
        for (int i8 = 0; i8 < 16; i8 += 4) {
            int[] iArr4 = this.k;
            int i9 = i8 + 16;
            int i10 = iArr4[i9];
            int[] iArr5 = this.m;
            iArr4[i9] = i10 ^ iArr5[i8];
            int i11 = i8 + 17;
            iArr4[i11] = iArr4[i11] ^ iArr5[i8 + 1];
            int i12 = i8 + 18;
            iArr4[i12] = iArr4[i12] ^ iArr5[i8 + 2];
            int i13 = i8 + 19;
            iArr4[i13] = iArr4[i13] ^ iArr5[i8 + 3];
        }
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void b(byte[] bArr, int i2) {
        int[] iArr = this.k;
        int length = iArr.length;
        byte[] bArr2 = this.l;
        CryptoUtils.spreadIntsToBytesLE(iArr, length - (bArr2.length >>> 2), bArr2, 0, bArr2.length >>> 2);
        byte[] bArr3 = this.l;
        System.arraycopy(bArr3, bArr3.length - this.c, bArr, i2, this.c);
    }

    @Override // iaik.security.md.AbstractC0044m, iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        C0045n c0045n = (C0045n) super.clone();
        c0045n.k = (int[]) this.k.clone();
        c0045n.l = (byte[]) this.l.clone();
        c0045n.m = (int[]) this.m.clone();
        return c0045n;
    }

    @Override // iaik.security.md.AbstractC0044m, iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    protected void engineReset() {
        super.engineReset();
        int[] iArr = this.j;
        System.arraycopy(iArr, 0, this.k, 0, iArr.length);
        CryptoUtils.zeroBlock(this.l);
        CryptoUtils.zeroBlock(this.m);
    }
}
