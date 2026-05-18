package iaik.security.md;

import androidx.core.internal.view.SupportMenu;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewCompat;
import iaik.utils.CryptoUtils;

/* JADX INFO: loaded from: classes.dex */
public final class GOST3411 extends AbstractMessageDigest {
    private static final int[][] g = {new int[]{4, 10, 9, 2, 13, 8, 0, 14, 6, 11, 1, 12, 7, 15, 5, 3}, new int[]{14, 11, 4, 12, 6, 13, 15, 10, 2, 3, 8, 1, 0, 7, 5, 9}, new int[]{5, 8, 1, 13, 10, 3, 4, 2, 14, 15, 12, 7, 6, 0, 9, 11}, new int[]{7, 13, 10, 1, 0, 8, 9, 15, 14, 4, 6, 12, 11, 2, 5, 3}, new int[]{6, 12, 7, 1, 5, 15, 13, 8, 4, 10, 9, 14, 0, 3, 11, 2}, new int[]{4, 11, 10, 0, 7, 2, 1, 13, 3, 6, 8, 5, 9, 12, 15, 14}, new int[]{13, 11, 4, 1, 3, 15, 5, 9, 0, 10, 14, 7, 6, 8, 2, 12}, new int[]{1, 15, 13, 0, 5, 7, 10, 4, 9, 2, 3, 14, 6, 11, 8, 12}};
    private static final int[] h = new int[256];
    private static final int[] i = new int[256];
    private static final int[] j = new int[256];
    private static final int[] k = new int[256];
    private int[] l;
    private int[] m;
    private int[] n;
    private int[] o;

    static {
        int i2 = 0;
        for (int i3 = 0; i3 < 16; i3++) {
            int[][] iArr = g;
            int i4 = iArr[1][i3] << 15;
            int i5 = iArr[3][i3] << 23;
            int i6 = iArr[5][i3];
            int i7 = (i6 << 31) | (i6 >>> 1);
            int i8 = iArr[7][i3] << 7;
            int i9 = 0;
            while (i9 < 16) {
                int[] iArr2 = h;
                int[][] iArr3 = g;
                iArr2[i2] = i4 | (iArr3[0][i9] << 11);
                i[i2] = i5 | (iArr3[2][i9] << 19);
                j[i2] = i7 | (iArr3[4][i9] << 27);
                k[i2] = i8 | (iArr3[6][i9] << 3);
                i9++;
                i2++;
            }
        }
    }

    public GOST3411() {
        super("GOST3411", 32, 32);
        this.l = new int[8];
        this.m = new int[8];
        this.n = new int[8];
        this.o = new int[8];
    }

    private void a(int[] iArr) {
        int i2;
        int i3;
        int i4;
        int[] iArr2 = this.l;
        int i5 = iArr2[0];
        int i6 = iArr2[1];
        int i7 = iArr2[2];
        int i8 = iArr2[3];
        int i9 = iArr2[4];
        int i10 = iArr2[5];
        int i11 = iArr2[6];
        int i12 = iArr2[7];
        int i13 = iArr[0];
        int i14 = iArr[1];
        int i15 = iArr[2];
        int i16 = iArr[3];
        int i17 = iArr[4];
        int i18 = iArr[5];
        int i19 = iArr[6];
        int i20 = iArr[7];
        int i21 = 0;
        while (true) {
            int i22 = i5 ^ i13;
            int i23 = i6 ^ i14;
            int i24 = i7 ^ i15;
            int i25 = i8 ^ i16;
            int i26 = i9 ^ i17;
            int i27 = i10 ^ i18;
            int i28 = i10;
            int i29 = i11 ^ i19;
            int i30 = i11;
            int i31 = i12 ^ i20;
            int i32 = i12;
            int i33 = i9;
            int i34 = (i22 & 255) | ((i24 & 255) << 8) | ((i26 & 255) << 16) | ((i29 & 255) << 24);
            int i35 = ((i22 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >>> 8) | (i24 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((i26 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) << 8) | ((i29 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) << 16);
            int i36 = ((i22 & 16711680) >>> 16) | ((i24 & 16711680) >>> 8) | (i26 & 16711680) | ((i29 & 16711680) << 8);
            int i37 = ((i26 & ViewCompat.MEASURED_STATE_MASK) >>> 8) | ((i24 & ViewCompat.MEASURED_STATE_MASK) >>> 16) | ((i22 & ViewCompat.MEASURED_STATE_MASK) >>> 24) | (i29 & ViewCompat.MEASURED_STATE_MASK);
            int i38 = (i23 & 255) | ((i25 & 255) << 8) | ((i27 & 255) << 16) | ((i31 & 255) << 24);
            int i39 = ((i23 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >>> 8) | (i25 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((i27 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) << 8) | ((65280 & i31) << 16);
            int i40 = ((i23 & 16711680) >>> 16) | ((i25 & 16711680) >>> 8) | (i27 & 16711680) | ((i31 & 16711680) << 8);
            int i41 = ((i25 & ViewCompat.MEASURED_STATE_MASK) >>> 16) | ((i23 & ViewCompat.MEASURED_STATE_MASK) >>> 24) | ((i27 & ViewCompat.MEASURED_STATE_MASK) >>> 8) | (i31 & ViewCompat.MEASURED_STATE_MASK);
            int[] iArr3 = this.l;
            int i42 = iArr3[i21];
            int i43 = i21 + 1;
            int i44 = iArr3[i43];
            int i45 = i6;
            int i46 = i34 + i42;
            int[] iArr4 = h;
            int i47 = i8;
            int i48 = iArr4[i46 & 255];
            int[] iArr5 = i;
            int i49 = i5;
            int i50 = iArr5[(i46 >>> 8) & 255] ^ i48;
            int[] iArr6 = j;
            int i51 = i7;
            int i52 = i50 ^ iArr6[(i46 >>> 16) & 255];
            int[] iArr7 = k;
            int i53 = (i52 ^ iArr7[(i46 >>> 24) & 255]) ^ i44;
            int i54 = i35 + i53;
            int i55 = (iArr7[(i54 >>> 24) & 255] ^ ((iArr5[(i54 >>> 8) & 255] ^ iArr4[i54 & 255]) ^ iArr6[(i54 >>> 16) & 255])) ^ i42;
            int i56 = i36 + i55;
            int i57 = i53 ^ (iArr7[(i56 >>> 24) & 255] ^ ((iArr4[i56 & 255] ^ iArr5[(i56 >>> 8) & 255]) ^ iArr6[(i56 >>> 16) & 255]));
            int i58 = i37 + i57;
            int i59 = i55 ^ (iArr7[(i58 >>> 24) & 255] ^ ((iArr4[i58 & 255] ^ iArr5[(i58 >>> 8) & 255]) ^ iArr6[(i58 >>> 16) & 255]));
            int i60 = i38 + i59;
            int i61 = i57 ^ (iArr7[(i60 >>> 24) & 255] ^ ((iArr4[i60 & 255] ^ iArr5[(i60 >>> 8) & 255]) ^ iArr6[(i60 >>> 16) & 255]));
            int i62 = i39 + i61;
            int i63 = i59 ^ (iArr7[(i62 >>> 24) & 255] ^ ((iArr4[i62 & 255] ^ iArr5[(i62 >>> 8) & 255]) ^ iArr6[(i62 >>> 16) & 255]));
            int i64 = i40 + i63;
            int i65 = i61 ^ (iArr7[(i64 >>> 24) & 255] ^ ((iArr4[i64 & 255] ^ iArr5[(i64 >>> 8) & 255]) ^ iArr6[(i64 >>> 16) & 255]));
            int i66 = i41 + i65;
            int i67 = i63 ^ (iArr7[(i66 >>> 24) & 255] ^ ((iArr4[i66 & 255] ^ iArr5[(i66 >>> 8) & 255]) ^ iArr6[(i66 >>> 16) & 255]));
            int i68 = i34 + i67;
            int i69 = i65 ^ (iArr7[(i68 >>> 24) & 255] ^ ((iArr4[i68 & 255] ^ iArr5[(i68 >>> 8) & 255]) ^ iArr6[(i68 >>> 16) & 255]));
            int i70 = i35 + i69;
            int i71 = i67 ^ (iArr7[(i70 >>> 24) & 255] ^ ((iArr4[i70 & 255] ^ iArr5[(i70 >>> 8) & 255]) ^ iArr6[(i70 >>> 16) & 255]));
            int i72 = i36 + i71;
            int i73 = i69 ^ (iArr7[(i72 >>> 24) & 255] ^ ((iArr4[i72 & 255] ^ iArr5[(i72 >>> 8) & 255]) ^ iArr6[(i72 >>> 16) & 255]));
            int i74 = i37 + i73;
            int i75 = i71 ^ (iArr7[(i74 >>> 24) & 255] ^ ((iArr4[i74 & 255] ^ iArr5[(i74 >>> 8) & 255]) ^ iArr6[(i74 >>> 16) & 255]));
            int i76 = i38 + i75;
            int i77 = i73 ^ (iArr7[(i76 >>> 24) & 255] ^ ((iArr4[i76 & 255] ^ iArr5[(i76 >>> 8) & 255]) ^ iArr6[(i76 >>> 16) & 255]));
            int i78 = i39 + i77;
            int i79 = i75 ^ (iArr7[(i78 >>> 24) & 255] ^ ((iArr4[i78 & 255] ^ iArr5[(i78 >>> 8) & 255]) ^ iArr6[(i78 >>> 16) & 255]));
            int i80 = i40 + i79;
            int i81 = i77 ^ (iArr7[(i80 >>> 24) & 255] ^ ((iArr4[i80 & 255] ^ iArr5[(i80 >>> 8) & 255]) ^ iArr6[(i80 >>> 16) & 255]));
            int i82 = i41 + i81;
            int i83 = i79 ^ (iArr7[(i82 >>> 24) & 255] ^ ((iArr4[i82 & 255] ^ iArr5[(i82 >>> 8) & 255]) ^ iArr6[(i82 >>> 16) & 255]));
            int i84 = i34 + i83;
            int i85 = i81 ^ (iArr7[(i84 >>> 24) & 255] ^ ((iArr4[i84 & 255] ^ iArr5[(i84 >>> 8) & 255]) ^ iArr6[(i84 >>> 16) & 255]));
            int i86 = i35 + i85;
            int i87 = i83 ^ (iArr7[(i86 >>> 24) & 255] ^ ((iArr4[i86 & 255] ^ iArr5[(i86 >>> 8) & 255]) ^ iArr6[(i86 >>> 16) & 255]));
            int i88 = i36 + i87;
            int i89 = i85 ^ (iArr7[(i88 >>> 24) & 255] ^ ((iArr4[i88 & 255] ^ iArr5[(i88 >>> 8) & 255]) ^ iArr6[(i88 >>> 16) & 255]));
            int i90 = i37 + i89;
            int i91 = i87 ^ (iArr7[(i90 >>> 24) & 255] ^ ((iArr4[i90 & 255] ^ iArr5[(i90 >>> 8) & 255]) ^ iArr6[(i90 >>> 16) & 255]));
            int i92 = i38 + i91;
            int i93 = i89 ^ (iArr7[(i92 >>> 24) & 255] ^ ((iArr4[i92 & 255] ^ iArr5[(i92 >>> 8) & 255]) ^ iArr6[(i92 >>> 16) & 255]));
            int i94 = i39 + i93;
            int i95 = i91 ^ (iArr7[(i94 >>> 24) & 255] ^ ((iArr4[i94 & 255] ^ iArr5[(i94 >>> 8) & 255]) ^ iArr6[(i94 >>> 16) & 255]));
            int i96 = i40 + i95;
            int i97 = i93 ^ (iArr7[(i96 >>> 24) & 255] ^ ((iArr4[i96 & 255] ^ iArr5[(i96 >>> 8) & 255]) ^ iArr6[(i96 >>> 16) & 255]));
            int i98 = i41 + i97;
            int i99 = i95 ^ (iArr7[(i98 >>> 24) & 255] ^ ((iArr4[i98 & 255] ^ iArr5[(i98 >>> 8) & 255]) ^ iArr6[(i98 >>> 16) & 255]));
            int i100 = i41 + i99;
            int i101 = i97 ^ (iArr7[(i100 >>> 24) & 255] ^ ((iArr4[i100 & 255] ^ iArr5[(i100 >>> 8) & 255]) ^ iArr6[(i100 >>> 16) & 255]));
            int i102 = i40 + i101;
            int i103 = (((iArr4[i102 & 255] ^ iArr5[(i102 >>> 8) & 255]) ^ iArr6[(i102 >>> 16) & 255]) ^ iArr7[(i102 >>> 24) & 255]) ^ i99;
            int i104 = i39 + i103;
            int i105 = i101 ^ (((iArr4[i104 & 255] ^ iArr5[(i104 >>> 8) & 255]) ^ iArr6[(i104 >>> 16) & 255]) ^ iArr7[(i104 >>> 24) & 255]);
            int i106 = i38 + i105;
            int i107 = i103 ^ (((iArr4[i106 & 255] ^ iArr5[(i106 >>> 8) & 255]) ^ iArr6[(i106 >>> 16) & 255]) ^ iArr7[(i106 >>> 24) & 255]);
            int i108 = i37 + i107;
            int i109 = (iArr7[(i108 >>> 24) & 255] ^ ((iArr4[i108 & 255] ^ iArr5[(i108 >>> 8) & 255]) ^ iArr6[(i108 >>> 16) & 255])) ^ i105;
            int i110 = i36 + i109;
            int i111 = (iArr7[(i110 >>> 24) & 255] ^ ((iArr4[i110 & 255] ^ iArr5[(i110 >>> 8) & 255]) ^ iArr6[(i110 >>> 16) & 255])) ^ i107;
            int i112 = i35 + i111;
            int i113 = i109 ^ (iArr7[(i112 >>> 24) & 255] ^ ((iArr4[i112 & 255] ^ iArr5[(i112 >>> 8) & 255]) ^ iArr6[(i112 >>> 16) & 255]));
            int i114 = i34 + i113;
            int i115 = (iArr7[(i114 >>> 24) & 255] ^ ((iArr4[i114 & 255] ^ iArr5[(i114 >>> 8) & 255]) ^ iArr6[(i114 >>> 16) & 255])) ^ i111;
            int[] iArr8 = this.n;
            iArr8[i21] = i113;
            iArr8[i43] = i115;
            if (i21 == 6) {
                int i116 = iArr[0] ^ iArr8[6];
                int i117 = iArr8[7] ^ iArr[1];
                int i118 = ((((((((((iArr8[0] << 16) ^ iArr[2]) ^ (iArr8[0] >>> 16)) ^ (iArr8[0] & 65535)) ^ (iArr8[1] & 65535)) ^ (iArr8[1] >>> 16)) ^ (iArr8[2] << 16)) ^ iArr8[6]) ^ (iArr8[6] << 16)) ^ (iArr8[7] & SupportMenu.CATEGORY_MASK)) ^ (iArr8[7] >>> 16);
                int i119 = (((((((((((((iArr8[0] << 16) ^ (iArr[3] ^ (iArr8[0] & 65535))) ^ (iArr8[1] & 65535)) ^ (iArr8[1] << 16)) ^ (iArr8[1] >>> 16)) ^ (iArr8[2] << 16)) ^ (iArr8[2] >>> 16)) ^ (iArr8[3] << 16)) ^ iArr8[6]) ^ (iArr8[6] << 16)) ^ (iArr8[6] >>> 16)) ^ (iArr8[7] & 65535)) ^ (iArr8[7] << 16)) ^ (iArr8[7] >>> 16);
                int i120 = (((((((((((((iArr8[0] >>> 16) ^ ((iArr[4] ^ (iArr8[0] & SupportMenu.CATEGORY_MASK)) ^ (iArr8[0] << 16))) ^ (iArr8[1] & SupportMenu.CATEGORY_MASK)) ^ (iArr8[1] >>> 16)) ^ (iArr8[2] << 16)) ^ (iArr8[2] >>> 16)) ^ (iArr8[3] << 16)) ^ (iArr8[3] >>> 16)) ^ (iArr8[4] << 16)) ^ (iArr8[6] << 16)) ^ (iArr8[6] >>> 16)) ^ (iArr8[7] & 65535)) ^ (iArr8[7] << 16)) ^ (iArr8[7] >>> 16);
                int i121 = ((((((((((((((iArr8[0] & SupportMenu.CATEGORY_MASK) ^ ((iArr[5] ^ (iArr8[0] << 16)) ^ (iArr8[0] >>> 16))) ^ (iArr8[1] & 65535)) ^ iArr8[2]) ^ (iArr8[2] >>> 16)) ^ (iArr8[3] << 16)) ^ (iArr8[3] >>> 16)) ^ (iArr8[4] << 16)) ^ (iArr8[4] >>> 16)) ^ (iArr8[5] << 16)) ^ (iArr8[6] << 16)) ^ (iArr8[6] >>> 16)) ^ (iArr8[7] & SupportMenu.CATEGORY_MASK)) ^ (iArr8[7] << 16)) ^ (iArr8[7] >>> 16);
                int i122 = ((((((((((((iArr[6] ^ iArr8[0]) ^ (iArr8[1] >>> 16)) ^ (iArr8[2] << 16)) ^ iArr8[3]) ^ (iArr8[3] >>> 16)) ^ (iArr8[4] << 16)) ^ (iArr8[4] >>> 16)) ^ (iArr8[5] << 16)) ^ (iArr8[5] >>> 16)) ^ iArr8[6]) ^ (iArr8[6] << 16)) ^ (iArr8[6] >>> 16)) ^ (iArr8[7] << 16);
                int i123 = (iArr8[7] >>> 16) ^ (((((((((((((iArr[7] ^ (iArr8[0] & SupportMenu.CATEGORY_MASK)) ^ (iArr8[0] << 16)) ^ (iArr8[1] & 65535)) ^ (iArr8[1] << 16)) ^ (iArr8[2] >>> 16)) ^ (iArr8[3] << 16)) ^ iArr8[4]) ^ (iArr8[4] >>> 16)) ^ (iArr8[5] << 16)) ^ (iArr8[5] >>> 16)) ^ (iArr8[6] >>> 16)) ^ (iArr8[7] & 65535)) ^ (iArr8[7] << 16));
                int i124 = i117 << 16;
                int i125 = (iArr3[0] ^ i124) ^ (i116 >>> 16);
                int i126 = ((i118 << 16) ^ iArr3[1]) ^ (i117 >>> 16);
                int i127 = i118 >>> 16;
                int i128 = i127 ^ ((i119 << 16) ^ iArr3[2]);
                int i129 = i119 >>> 16;
                int i130 = i129 ^ ((i120 << 16) ^ iArr3[3]);
                int i131 = (i120 >>> 16) ^ ((i121 << 16) ^ iArr3[4]);
                int i132 = i122 << 16;
                int i133 = (i121 >>> 16) ^ (iArr3[5] ^ i132);
                int i134 = (i122 >>> 16) ^ (iArr3[6] ^ (i123 << 16));
                int i135 = ((((((i116 << 16) ^ (iArr3[7] ^ (i116 & SupportMenu.CATEGORY_MASK))) ^ (i123 >>> 16)) ^ (i117 & SupportMenu.CATEGORY_MASK)) ^ i124) ^ i132) ^ (i123 & SupportMenu.CATEGORY_MASK);
                int i136 = i125 & SupportMenu.CATEGORY_MASK;
                int i137 = i125 << 16;
                int i138 = i136 ^ i137;
                int i139 = i125 >>> 16;
                int i140 = i126 >>> 16;
                int i141 = i126 & SupportMenu.CATEGORY_MASK;
                int i142 = i128 << 16;
                int i143 = i130 >>> 16;
                int i144 = i131 << 16;
                int i145 = i133 >>> 16;
                int i146 = i134 >>> 16;
                int i147 = i135 << 16;
                int i148 = i135 >>> 16;
                int i149 = i135 & 65535;
                iArr3[0] = (((((((((((i138 ^ i139) ^ i140) ^ i141) ^ i142) ^ i143) ^ i144) ^ i145) ^ i133) ^ i146) ^ i147) ^ i148) ^ i149;
                int i150 = i136 ^ (i137 ^ i139);
                int i151 = i128 >>> 16;
                int i152 = i130 << 16;
                int i153 = i131 >>> 16;
                int i154 = i133 << 16;
                int i155 = i134 << 16;
                int i156 = (-65536) & i135;
                iArr3[1] = ((((((((((i126 & 65535) ^ i150) ^ i128) ^ i151) ^ i152) ^ i153) ^ i154) ^ i155) ^ i134) ^ i156) ^ i148;
                int i157 = (65535 & i125) ^ i137;
                int i158 = i126 << 16;
                iArr3[2] = ((((((((((((i157 ^ i158) ^ i140) ^ i141) ^ i142) ^ i143) ^ i130) ^ i144) ^ i145) ^ i134) ^ i146) ^ i149) ^ i147) ^ i148;
                iArr3[3] = (((((((((((i150 ^ i141) ^ i140) ^ i142) ^ i151) ^ i128) ^ i152) ^ i153) ^ i131) ^ i154) ^ i155) ^ i149) ^ i148;
                iArr3[4] = ((((((((((((i139 ^ i158) ^ i126) ^ i151) ^ i128) ^ i152) ^ i143) ^ i130) ^ i144) ^ i145) ^ i133) ^ i155) ^ i146) ^ i147;
                iArr3[5] = ((((((((((((((((i138 ^ i158) ^ i140) ^ i141) ^ i142) ^ i128) ^ i143) ^ i130) ^ i144) ^ i153) ^ i131) ^ i154) ^ i155) ^ i146) ^ i134) ^ i147) ^ i148) ^ i156;
                iArr3[6] = (((((((((((((i125 ^ i128) ^ i151) ^ i130) ^ i152) ^ i131) ^ i153) ^ i154) ^ i145) ^ i133) ^ i155) ^ i146) ^ i134) ^ i147) ^ i135;
                iArr3[7] = i135 ^ (((((((((((((i125 ^ i139) ^ i158) ^ i140) ^ i142) ^ i143) ^ i130) ^ i144) ^ i131) ^ i145) ^ i133) ^ i155) ^ i146) ^ i147);
                return;
            }
            int i159 = i49 ^ i51;
            int i160 = i45 ^ i47;
            if (i21 == 2) {
                i4 = i51 ^ (-16711936);
                i3 = i47 ^ (-16711936);
                i8 = i28 ^ 16711935;
                i9 = i30 ^ 16776960;
                i2 = i32 ^ (-16776961);
                i11 = i159 ^ 255;
                i12 = i160 ^ (-16711681);
                i33 ^= 16711935;
            } else {
                i11 = i159;
                i12 = i160;
                i8 = i28;
                i9 = i30;
                i2 = i32;
                i3 = i47;
                i4 = i51;
            }
            int i161 = i13 ^ i15;
            int i162 = i17 ^ i15;
            int i163 = i14 ^ i16;
            int i164 = i18 ^ i16;
            i21 += 2;
            i6 = i3;
            i5 = i4;
            i10 = i2;
            i15 = i19;
            i16 = i20;
            i7 = i33;
            i19 = i162;
            i20 = i164;
            i14 = i18;
            i18 = i163;
            i13 = i17;
            i17 = i161;
        }
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a() {
        int[] iArr = new int[8];
        long j2 = this.f << 3;
        iArr[0] = (int) ((-1) & j2);
        iArr[1] = (int) (j2 >>> 32);
        int i2 = ((int) ((this.f % ((long) this.d)) + ((long) this.d))) % this.d;
        if (i2 > 0) {
            engineUpdate(a, 1, this.d - i2);
        }
        a(iArr);
        a(this.m);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a(byte[] bArr, int i2) {
        long j2 = 0;
        int i3 = 0;
        while (i3 < 8) {
            int i4 = i2 + 1;
            int i5 = i4 + 1;
            int i6 = i5 + 1;
            long j3 = bArr[i2] | ((bArr[i4] & 255) << 8) | ((bArr[i5] & 255) << 16) | ((bArr[i6] & 255) << 24);
            int[] iArr = this.m;
            long j4 = j2 + (((long) iArr[i3]) & 4294967295L) + j3;
            this.o[i3] = (int) (j3 & 4294967295L);
            iArr[i3] = (int) (j4 & 4294967295L);
            j2 = j4 >>> 32;
            i3++;
            i2 = i6 + 1;
        }
        a(this.o);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void b(byte[] bArr, int i2) {
        int[] iArr = this.l;
        CryptoUtils.spreadIntsToBytesLE(iArr, 0, bArr, i2, iArr.length);
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    protected void engineReset() {
        CryptoUtils.zeroBlock(this.m);
        CryptoUtils.zeroBlock(this.l);
        CryptoUtils.zeroBlock(this.b);
        this.f = 0L;
    }
}
