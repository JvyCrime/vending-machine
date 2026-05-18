package iaik.security.md;

import androidx.core.view.MotionEventCompat;
import com.felhr.usbserial.FTDISerialDevice;
import iaik.utils.CryptoUtils;

/* JADX INFO: loaded from: classes.dex */
abstract class u extends AbstractC0047p {
    private static final int[] k = {1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0};
    private static final int[] l = {0, 137, -2147483509, -2147450752, 139, 32768, -2147450744, -2147483518, 11, 10, 32898, FTDISerialDevice.FTDI_BAUDRATE_921600, 32907, -2147483637, -2147483510, -2147483519, -2147483519, -2147483640, 131, -2147450877, -2147450744, -2147483512, 32768, -2147450750};
    private final int[] m;
    private final int[] n;
    private final int o;
    private int[] p;
    private byte[] q;
    private byte[] r;

    u(int i, int i2, boolean z) {
        this(i, i2, z, false);
    }

    u(int i, int i2, boolean z, boolean z2) {
        super(i, i2, z, z2);
        this.m = new int[65536];
        this.n = new int[65536];
        this.p = new int[50];
        this.q = new byte[this.d];
        int i3 = i2 >>> 6;
        this.o = i3;
        this.r = new byte[i3 << 4];
        for (int i4 = 0; i4 < 65536; i4++) {
            int i5 = 0;
            for (int i6 = 0; i6 < 16; i6++) {
                if (((1 << i6) & i4) != 0) {
                    i5 |= 1 << ((i6 >>> 1) + ((i6 & 1) << 3));
                }
            }
            this.m[i4] = i5;
            this.n[i5] = i4;
        }
        engineReset();
    }

    private static int a(int i, int i2) {
        return (i >>> (32 - i2)) | (i << i2);
    }

    private void a(int i, byte[] bArr) {
        int[] iArr = this.p;
        int i2 = iArr[i];
        int[] iArr2 = this.m;
        int i3 = i << 2;
        int i4 = i3 + 7;
        int i5 = i3 + 6;
        int i6 = i3 + 5;
        int i7 = i3 + 4;
        int i8 = i3 + 3;
        int i9 = i3 + 2;
        int i10 = i3 + 1;
        iArr[i] = i2 ^ (((((iArr2[((bArr[i4] & 255) << 8) | (bArr[i5] & 255)] & 255) << 24) ^ ((iArr2[((bArr[i6] & 255) << 8) | (bArr[i7] & 255)] & 255) << 16)) ^ ((iArr2[((bArr[i8] & 255) << 8) | (bArr[i9] & 255)] & 255) << 8)) ^ (iArr2[((bArr[i10] & 255) << 8) | (bArr[i3] & 255)] & 255));
        int i11 = i + 1;
        iArr[i11] = (((iArr2[(bArr[i3] & 255) | ((bArr[i10] & 255) << 8)] >>> 8) & 255) ^ ((((iArr2[((bArr[i4] & 255) << 8) | (bArr[i5] & 255)] & MotionEventCompat.ACTION_POINTER_INDEX_MASK) << 16) ^ ((iArr2[((bArr[i6] & 255) << 8) | (bArr[i7] & 255)] & MotionEventCompat.ACTION_POINTER_INDEX_MASK) << 8)) ^ (65280 & iArr2[((bArr[i8] & 255) << 8) | (bArr[i9] & 255)]))) ^ iArr[i11];
    }

    private void a(int i, byte[] bArr, int i2, int i3) {
        int[] iArr = this.n;
        int i4 = iArr[(i2 & 255) | ((i3 & 255) << 8)];
        int i5 = iArr[((i2 >>> 8) & 255) | (i3 & MotionEventCompat.ACTION_POINTER_INDEX_MASK)];
        int i6 = iArr[((i2 >>> 16) & 255) | ((i3 >>> 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)];
        int i7 = iArr[((i2 >>> 24) & 255) | ((i3 >>> 16) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)];
        int i8 = i << 2;
        bArr[i8] = (byte) i4;
        bArr[i8 + 1] = (byte) (i4 >>> 8);
        bArr[i8 + 2] = (byte) i5;
        bArr[i8 + 3] = (byte) (i5 >>> 8);
        bArr[i8 + 4] = (byte) i6;
        bArr[i8 + 5] = (byte) (i6 >>> 8);
        bArr[i8 + 6] = (byte) i7;
        bArr[i8 + 7] = (byte) (i7 >>> 8);
    }

    private void b(int i) {
        byte b;
        if (i > 2) {
            engineUpdate(h[0]);
            engineUpdate(a, 1, i - 2);
            b = h[1];
        } else if (i == 0) {
            engineUpdate(h[0]);
            engineUpdate(a, 1, this.d - 2);
            b = h[1];
        } else {
            if (i != 1) {
                engineUpdate(h, 0, 2);
                return;
            }
            b = -122;
        }
        engineUpdate(b);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a() {
        byte b;
        int i = ((int) (((((long) this.d) - this.f) % ((long) this.d)) + ((long) this.d))) % this.d;
        if (this.j) {
            b(i);
            return;
        }
        if (i > 2) {
            engineUpdate(g[0]);
            engineUpdate(a, 1, i - 2);
            b = g[1];
        } else if (i == 0) {
            engineUpdate(g[0]);
            engineUpdate(a, 1, this.d - 2);
            b = g[1];
        } else {
            if (i != 1) {
                engineUpdate(g, 0, 2);
                return;
            }
            b = -127;
        }
        engineUpdate(b);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a(byte[] bArr, int i) {
        int i2 = 1;
        if (i != 0) {
            if (bArr != null) {
                System.arraycopy(bArr, i, this.q, 0, this.d);
            }
            for (int i3 = 0; i3 < (this.o << 1); i3 += 2) {
                a(i3, this.q);
            }
        } else if (bArr != null) {
            for (int i4 = 0; i4 < (this.o << 1); i4 += 2) {
                a(i4, bArr);
            }
        }
        int[] iArr = this.p;
        int i5 = iArr[0];
        int i6 = iArr[1];
        int i7 = iArr[2];
        int i8 = iArr[3];
        int i9 = iArr[4];
        int i10 = iArr[5];
        int i11 = iArr[6];
        int i12 = iArr[7];
        int i13 = iArr[8];
        int i14 = iArr[9];
        int i15 = iArr[10];
        int i16 = iArr[11];
        int i17 = iArr[12];
        int i18 = iArr[13];
        int i19 = iArr[14];
        int i20 = iArr[15];
        int i21 = iArr[16];
        int i22 = iArr[17];
        int i23 = iArr[18];
        int i24 = iArr[19];
        int i25 = iArr[20];
        int i26 = iArr[21];
        int i27 = iArr[22];
        int i28 = iArr[23];
        int i29 = iArr[24];
        int i30 = iArr[25];
        int i31 = iArr[26];
        int i32 = iArr[27];
        int i33 = iArr[28];
        int i34 = iArr[29];
        int i35 = iArr[30];
        int i36 = iArr[31];
        int i37 = iArr[32];
        int i38 = iArr[33];
        int i39 = iArr[34];
        int i40 = iArr[35];
        int i41 = iArr[36];
        int i42 = iArr[37];
        int i43 = iArr[38];
        int i44 = iArr[39];
        int i45 = iArr[40];
        int i46 = iArr[41];
        int i47 = iArr[42];
        int i48 = iArr[43];
        int i49 = iArr[44];
        int i50 = iArr[45];
        int i51 = iArr[46];
        int i52 = iArr[47];
        int i53 = iArr[48];
        int i54 = iArr[49];
        int i55 = (((i5 ^ i15) ^ i25) ^ i35) ^ i45;
        int i56 = (((i7 ^ i17) ^ i27) ^ i37) ^ i47;
        int i57 = (((i9 ^ i19) ^ i29) ^ i39) ^ i49;
        int i58 = (((i11 ^ i21) ^ i31) ^ i41) ^ i51;
        int i59 = (((i13 ^ i23) ^ i33) ^ i43) ^ i53;
        int i60 = (((i6 ^ i16) ^ i26) ^ i36) ^ i46;
        int i61 = (((i8 ^ i18) ^ i28) ^ i38) ^ i48;
        int i62 = (((i10 ^ i20) ^ i30) ^ i40) ^ i50;
        int i63 = (((i12 ^ i22) ^ i32) ^ i42) ^ i52;
        int i64 = (((i14 ^ i24) ^ i34) ^ i44) ^ i54;
        int i65 = 0;
        while (i65 < 24) {
            int iA = i59 ^ a(i61, i2);
            int i66 = i64 ^ i56;
            int iA2 = i55 ^ a(i62, i2);
            int i67 = i60 ^ i57;
            int iA3 = i56 ^ a(i63, i2);
            int i68 = i61 ^ i58;
            int iA4 = i57 ^ a(i64, i2);
            int i69 = i62 ^ i59;
            int iA5 = i58 ^ a(i60, i2);
            int i70 = i63 ^ i55;
            int i71 = i5 ^ iA;
            int i72 = i10;
            int iA6 = a(i17 ^ iA2, 22);
            int i73 = i9;
            int iA7 = a(i30 ^ i68, 22);
            int i74 = i7;
            int iA8 = a(i42 ^ i69, 11);
            int i75 = i8;
            int iA9 = a(i53 ^ iA5, 7);
            int[] iArr2 = k;
            int i76 = ((iA6 | iA7) ^ i71) ^ iArr2[i65];
            int i77 = ((~iA7) | iA8) ^ iA6;
            int i78 = iA7 ^ (iA8 & iA9);
            int i79 = iA8 ^ (iA9 | i71);
            int i80 = (i71 & iA6) ^ iA9;
            int i81 = i6 ^ i66;
            int iA10 = a(i18 ^ i67, 22);
            int iA11 = a(i29 ^ iA3, 21);
            int iA12 = a(i41 ^ iA4, 10);
            int iA13 = a(i54 ^ i70, 7);
            int[] iArr3 = l;
            int i82 = ((iA10 | iA11) ^ i81) ^ iArr3[i65];
            int i83 = i65;
            int i84 = ((~iA11) | iA12) ^ iA10;
            int i85 = iA11 ^ (iA12 & iA13);
            int i86 = iA12 ^ (iA13 | i81);
            int i87 = iA13 ^ (i81 & iA10);
            int iA14 = a(i11 ^ iA4, 14);
            int iA15 = a(i23 ^ iA5, 10);
            int iA16 = a(i26 ^ i66, 2);
            int iA17 = a(i38 ^ i67, 23);
            int iA18 = a(i50 ^ i68, 31);
            int i88 = (iA15 | iA16) ^ iA14;
            int i89 = i76 ^ i88;
            int i90 = iA15 ^ (iA16 & iA17);
            int i91 = i77 ^ i90;
            int i92 = ((~iA18) | iA17) ^ iA16;
            int i93 = i78 ^ i92;
            int i94 = iA17 ^ (iA18 | iA14);
            int i95 = i79 ^ i94;
            int i96 = (iA14 & iA15) ^ iA18;
            int i97 = i80 ^ i96;
            int iA19 = a(i12 ^ i69, 14);
            int iA20 = a(i24 ^ i70, 10);
            int iA21 = a(i25 ^ iA, 1);
            int iA22 = a(i37 ^ iA2, 22);
            int iA23 = a(i49 ^ iA3, 30);
            int i98 = (iA20 | iA21) ^ iA19;
            int i99 = i82 ^ i98;
            int i100 = iA20 ^ (iA21 & iA22);
            int i101 = i84 ^ i100;
            int i102 = iA21 ^ ((~iA23) | iA22);
            int i103 = i85 ^ i102;
            int i104 = iA22 ^ (iA23 | iA19);
            int i105 = i86 ^ i104;
            int i106 = (iA20 & iA19) ^ iA23;
            int i107 = i87 ^ i106;
            int iA24 = a(i75 ^ i67, 1);
            int iA25 = a(i19 ^ iA3, 3);
            int iA26 = a(i32 ^ i69, 13);
            int iA27 = a(i43 ^ iA5, 4);
            int iA28 = a(i45 ^ iA, 9);
            int i108 = (iA25 | iA26) ^ iA24;
            int i109 = i89 ^ i108;
            int i110 = iA25 ^ (iA26 & iA27);
            int i111 = ~iA27;
            int i112 = iA26 ^ (i111 & iA28);
            int i113 = i111 ^ (iA28 | iA24);
            int i114 = i95 ^ i113;
            int i115 = (iA25 & iA24) ^ iA28;
            int i116 = i97 ^ i115;
            int i117 = i74 ^ iA2;
            int iA29 = a(i20 ^ i68, 3);
            int iA30 = a(i31 ^ iA4, 12);
            int iA31 = a(i44 ^ i70, 4);
            int iA32 = a(i46 ^ i66, 9);
            int i118 = (iA29 | iA30) ^ i117;
            int i119 = i99 ^ i118;
            int i120 = iA29 ^ (iA30 & iA31);
            int i121 = ~iA31;
            int i122 = iA30 ^ (i121 & iA32);
            int i123 = i121 ^ (iA32 | i117);
            int i124 = i105 ^ i123;
            int i125 = iA32 ^ (i117 & iA29);
            int i126 = i107 ^ i125;
            int iA33 = a(i14 ^ i70, 14);
            int iA34 = a(i15 ^ iA, 18);
            int iA35 = a(i27 ^ iA2, 5);
            int iA36 = a(i40 ^ i68, 8);
            int iA37 = a(i51 ^ iA4, 28);
            int i127 = (iA34 & iA35) ^ iA33;
            int i128 = i109 ^ i127;
            int i129 = iA34 ^ (iA35 | iA36);
            int i130 = ~iA36;
            int i131 = iA35 ^ (i130 | iA37);
            int i132 = (i93 ^ i112) ^ i131;
            int i133 = i130 ^ (iA37 & iA33);
            int i134 = iA37 ^ (iA33 | iA34);
            int i135 = i116 ^ i134;
            int iA38 = a(i13 ^ iA5, 13);
            int iA39 = a(i16 ^ i66, 18);
            int iA40 = a(i28 ^ i67, 5);
            int iA41 = a(i39 ^ iA3, 7);
            int iA42 = a(i52 ^ i69, 28);
            int i136 = (iA39 & iA40) ^ iA38;
            int i137 = i119 ^ i136;
            int i138 = iA39 ^ (iA40 | iA41);
            int i139 = ~iA41;
            int i140 = iA40 ^ (i139 | iA42);
            int i141 = (i103 ^ i122) ^ i140;
            int i142 = i139 ^ (iA42 & iA38);
            int i143 = iA42 ^ (iA38 | iA39);
            int i144 = i126 ^ i143;
            int iA43 = a(i73 ^ iA3, 31);
            int iA44 = a(i22 ^ i69, 28);
            int iA45 = a(i34 ^ i70, 20);
            int iA46 = a(i36 ^ i66, 21);
            int iA47 = a(i47 ^ iA2, 1);
            int i145 = ~iA44;
            int i146 = iA43 ^ (i145 & iA45);
            int i147 = i128 ^ i146;
            int i148 = i145 ^ (iA45 | iA46);
            int i149 = ((i91 ^ i110) ^ i129) ^ i148;
            int i150 = iA45 ^ (iA46 & iA47);
            int i151 = i132 ^ i150;
            int i152 = iA46 ^ (iA47 | iA43);
            int i153 = (i114 ^ i133) ^ i152;
            int i154 = iA47 ^ (iA43 & iA44);
            int i155 = i135 ^ i154;
            int iA48 = a(i72 ^ i68, 31);
            int iA49 = a(i21 ^ iA4, 27);
            int iA50 = a(i33 ^ iA5, 19);
            int iA51 = a(i35 ^ iA, 20);
            int iA52 = a(i48 ^ i67, 1);
            int i156 = ~iA49;
            int i157 = iA48 ^ (i156 & iA50);
            int i158 = i137 ^ i157;
            int i159 = i156 ^ (iA50 | iA51);
            int i160 = ((i101 ^ i120) ^ i138) ^ i159;
            int i161 = iA50 ^ (iA51 & iA52);
            int i162 = i141 ^ i161;
            int i163 = iA51 ^ (iA52 | iA48);
            int i164 = (i124 ^ i142) ^ i163;
            int i165 = (iA49 & iA48) ^ iA52;
            int i166 = i144 ^ i165;
            int iA53 = a(i160, 1) ^ i155;
            int i167 = i166 ^ i149;
            int iA54 = i147 ^ a(i162, 1);
            int i168 = i158 ^ i151;
            int iA55 = i149 ^ a(i164, 1);
            int i169 = i160 ^ i153;
            int iA56 = a(i166, 1) ^ i151;
            int i170 = i155 ^ i162;
            int iA57 = i153 ^ a(i158, 1);
            int i171 = i164 ^ i147;
            int i172 = i76 ^ iA53;
            int iA58 = a(i90 ^ iA54, 22);
            int iA59 = a(i122 ^ i169, 22);
            int iA60 = a(i142 ^ i170, 11);
            int iA61 = a(i154 ^ iA57, 7);
            int i173 = i83 + 1;
            int i174 = ((iA58 | iA59) ^ i172) ^ iArr2[i173];
            int i175 = ((~iA59) | iA60) ^ iA58;
            int i176 = iA59 ^ (iA60 & iA61);
            int i177 = iA60 ^ (iA61 | i172);
            int i178 = iA61 ^ (i172 & iA58);
            int i179 = i82 ^ i167;
            int iA62 = a(i100 ^ i168, 22);
            int iA63 = a(i112 ^ iA55, 21);
            int iA64 = a(i133 ^ iA56, 10);
            int iA65 = a(i165 ^ i171, 7);
            int i180 = ((iA62 | iA63) ^ i179) ^ iArr3[i173];
            int i181 = ((~iA63) | iA64) ^ iA62;
            i10 = iA63 ^ (iA64 & iA65);
            int i182 = iA64 ^ (iA65 | i179);
            int i183 = iA65 ^ (i179 & iA62);
            int iA66 = a(i79 ^ iA56, 14);
            int iA67 = a(i96 ^ iA57, 10);
            int iA68 = a(i118 ^ i167, 2);
            int iA69 = a(i138 ^ i168, 23);
            int iA70 = a(i161 ^ i169, 31);
            int i184 = (iA67 | iA68) ^ iA66;
            int i185 = i174 ^ i184;
            int i186 = iA67 ^ (iA68 & iA69);
            int i187 = iA68 ^ ((~iA70) | iA69);
            int i188 = i176 ^ i187;
            int i189 = iA69 ^ (iA70 | iA66);
            int i190 = i177 ^ i189;
            int i191 = iA70 ^ (iA66 & iA67);
            int i192 = i178 ^ i191;
            int iA71 = a(i86 ^ i170, 14);
            int iA72 = a(i106 ^ i171, 10);
            int iA73 = a(i108 ^ iA53, 1);
            int iA74 = a(i129 ^ iA54, 22);
            int iA75 = a(i150 ^ iA55, 30);
            int i193 = (iA72 | iA73) ^ iA71;
            int i194 = i180 ^ i193;
            int i195 = iA72 ^ (iA73 & iA74);
            int i196 = iA73 ^ ((~iA75) | iA74);
            int i197 = i10 ^ i196;
            int i198 = iA74 ^ (iA75 | iA71);
            int i199 = i182 ^ i198;
            int i200 = (iA72 & iA71) ^ iA75;
            int i201 = i183 ^ i200;
            int iA76 = a(i84 ^ i168, 1);
            int iA77 = a(i92 ^ iA55, 3);
            int iA78 = a(i123 ^ i170, 13);
            int iA79 = a(i134 ^ iA57, 4);
            int iA80 = a(i146 ^ iA53, 9);
            int i202 = (iA77 | iA78) ^ iA76;
            int i203 = i185 ^ i202;
            int i204 = iA77 ^ (iA78 & iA79);
            int i205 = ~iA79;
            int i206 = iA78 ^ (i205 & iA80);
            int i207 = i188 ^ i206;
            int i208 = i205 ^ (iA80 | iA76);
            int i209 = i190 ^ i208;
            int i210 = (iA77 & iA76) ^ iA80;
            int i211 = i192 ^ i210;
            int i212 = i77 ^ iA54;
            int iA81 = a(i102 ^ i169, 3);
            int iA82 = a(i113 ^ iA56, 12);
            int iA83 = a(i143 ^ i171, 4);
            int iA84 = a(i157 ^ i167, 9);
            int i213 = (iA81 | iA82) ^ i212;
            int i214 = i194 ^ i213;
            int i215 = iA81 ^ (iA82 & iA83);
            int i216 = ~iA83;
            int i217 = iA82 ^ (i216 & iA84);
            int i218 = i197 ^ i217;
            int i219 = i216 ^ (iA84 | i212);
            int i220 = i199 ^ i219;
            int i221 = iA84 ^ (i212 & iA81);
            int i222 = i201 ^ i221;
            int iA85 = a(i87 ^ i171, 14);
            int iA86 = a(i88 ^ iA53, 18);
            int iA87 = a(i110 ^ iA54, 5);
            int iA88 = a(i140 ^ i169, 8);
            int iA89 = a(i152 ^ iA56, 28);
            int i223 = (iA86 & iA87) ^ iA85;
            int i224 = i203 ^ i223;
            int i225 = iA86 ^ (iA87 | iA88);
            int i226 = ~iA88;
            int i227 = iA87 ^ (i226 | iA89);
            int i228 = i207 ^ i227;
            int i229 = i226 ^ (iA89 & iA85);
            int i230 = i209 ^ i229;
            int i231 = iA89 ^ (iA85 | iA86);
            int i232 = i211 ^ i231;
            int iA90 = a(i80 ^ iA57, 13);
            int iA91 = a(i98 ^ i167, 18);
            int iA92 = a(i120 ^ i168, 5);
            int iA93 = a(i131 ^ iA55, 7);
            int iA94 = a(i163 ^ i170, 28);
            int i233 = (iA91 & iA92) ^ iA90;
            int i234 = i214 ^ i233;
            int i235 = iA91 ^ (iA92 | iA93);
            int i236 = ~iA93;
            int i237 = iA92 ^ (i236 | iA94);
            int i238 = i218 ^ i237;
            int i239 = i236 ^ (iA94 & iA90);
            int i240 = i220 ^ i239;
            int i241 = iA94 ^ (iA90 | iA91);
            int i242 = i222 ^ i241;
            int iA95 = a(i78 ^ iA55, 31);
            int iA96 = a(i104 ^ i170, 28);
            int iA97 = a(i125 ^ i171, 20);
            int iA98 = a(i136 ^ i167, 21);
            int iA99 = a(i148 ^ iA54, 1);
            int i243 = ~iA96;
            int i244 = iA95 ^ (i243 & iA97);
            int i245 = i224 ^ i244;
            int i246 = i243 ^ (iA97 | iA98);
            int i247 = (((i175 ^ i186) ^ i204) ^ i225) ^ i246;
            i49 = iA97 ^ (iA98 & iA99);
            int i248 = i228 ^ i49;
            int i249 = iA98 ^ (iA99 | iA95);
            int i250 = i230 ^ i249;
            i53 = iA99 ^ (iA95 & iA96);
            int i251 = i232 ^ i53;
            int iA100 = a(i85 ^ i169, 31);
            int iA101 = a(i94 ^ iA56, 27);
            int iA102 = a(i115 ^ iA57, 19);
            int iA103 = a(i127 ^ iA53, 20);
            int iA104 = a(i159 ^ i168, 1);
            int i252 = ~iA101;
            int i253 = iA100 ^ (i252 & iA102);
            int i254 = i234 ^ i253;
            int i255 = i252 ^ (iA102 | iA103);
            int i256 = (((i181 ^ i195) ^ i215) ^ i235) ^ i255;
            int i257 = iA102 ^ (iA103 & iA104);
            int i258 = i238 ^ i257;
            i52 = iA103 ^ (iA104 | iA100);
            int i259 = iA104 ^ (iA100 & iA101);
            int i260 = i242 ^ i259;
            i65 = i83 + 2;
            i35 = i223;
            i42 = i239;
            i41 = i229;
            i6 = i180;
            i44 = i241;
            i55 = i245;
            i22 = i198;
            i20 = i196;
            i16 = i193;
            i24 = i200;
            i33 = i210;
            i28 = i215;
            i37 = i225;
            i50 = i257;
            i8 = i181;
            i14 = i183;
            i11 = i177;
            i46 = i253;
            i13 = i178;
            i26 = i213;
            i15 = i184;
            i18 = i195;
            i59 = i251;
            i36 = i233;
            i51 = i249;
            i12 = i182;
            i64 = i260;
            i62 = i258;
            i5 = i174;
            i7 = i175;
            i40 = i237;
            i61 = i256;
            i29 = i206;
            i19 = i187;
            i63 = i240 ^ i52;
            i45 = i244;
            i60 = i254;
            i43 = i231;
            i27 = i204;
            i25 = i202;
            i32 = i219;
            i21 = i189;
            i23 = i191;
            i57 = i248;
            i56 = i247;
            i17 = i186;
            i48 = i255;
            i34 = i221;
            i31 = i208;
            i54 = i259;
            i39 = i227;
            i30 = i217;
            i38 = i235;
            i58 = i250;
            i9 = i176;
            i47 = i246;
            i2 = 1;
        }
        int[] iArr4 = this.p;
        iArr4[0] = i5;
        iArr4[1] = i6;
        iArr4[2] = i7;
        iArr4[3] = i8;
        iArr4[4] = i9;
        iArr4[5] = i10;
        iArr4[6] = i11;
        iArr4[7] = i12;
        iArr4[8] = i13;
        iArr4[9] = i14;
        iArr4[10] = i15;
        iArr4[11] = i16;
        iArr4[12] = i17;
        iArr4[13] = i18;
        iArr4[14] = i19;
        iArr4[15] = i20;
        iArr4[16] = i21;
        iArr4[17] = i22;
        iArr4[18] = i23;
        iArr4[19] = i24;
        iArr4[20] = i25;
        iArr4[21] = i26;
        iArr4[22] = i27;
        iArr4[23] = i28;
        iArr4[24] = i29;
        iArr4[25] = i30;
        iArr4[26] = i31;
        iArr4[27] = i32;
        iArr4[28] = i33;
        iArr4[29] = i34;
        iArr4[30] = i35;
        iArr4[31] = i36;
        iArr4[32] = i37;
        iArr4[33] = i38;
        iArr4[34] = i39;
        iArr4[35] = i40;
        iArr4[36] = i41;
        iArr4[37] = i42;
        iArr4[38] = i43;
        iArr4[39] = i44;
        iArr4[40] = i45;
        iArr4[41] = i46;
        iArr4[42] = i47;
        iArr4[43] = i48;
        iArr4[44] = i49;
        iArr4[45] = i50;
        iArr4[46] = i51;
        iArr4[47] = i52;
        iArr4[48] = i53;
        iArr4[49] = i54;
    }

    void a(byte[] bArr, int i, int i2) {
        int i3;
        int i4 = 0;
        while (true) {
            i3 = this.o;
            if (i4 >= (i3 << 1)) {
                break;
            }
            byte[] bArr2 = this.r;
            int[] iArr = this.p;
            a(i4, bArr2, iArr[i4], iArr[i4 + 1]);
            i4 += 2;
        }
        byte[] bArr3 = this.r;
        bArr3[8] = (byte) (~bArr3[8]);
        bArr3[9] = (byte) (~bArr3[9]);
        bArr3[10] = (byte) (~bArr3[10]);
        bArr3[11] = (byte) (~bArr3[11]);
        bArr3[12] = (byte) (~bArr3[12]);
        bArr3[13] = (byte) (~bArr3[13]);
        bArr3[14] = (byte) (~bArr3[14]);
        bArr3[15] = (byte) (~bArr3[15]);
        bArr3[16] = (byte) (~bArr3[16]);
        bArr3[17] = (byte) (~bArr3[17]);
        bArr3[18] = (byte) (~bArr3[18]);
        bArr3[19] = (byte) (~bArr3[19]);
        bArr3[20] = (byte) (~bArr3[20]);
        bArr3[21] = (byte) (~bArr3[21]);
        bArr3[22] = (byte) (~bArr3[22]);
        bArr3[23] = (byte) (~bArr3[23]);
        if (i3 > 8) {
            bArr3[64] = (byte) (~bArr3[64]);
            bArr3[65] = (byte) (~bArr3[65]);
            bArr3[66] = (byte) (~bArr3[66]);
            bArr3[67] = (byte) (~bArr3[67]);
            bArr3[68] = (byte) (~bArr3[68]);
            bArr3[69] = (byte) (~bArr3[69]);
            bArr3[70] = (byte) (~bArr3[70]);
            bArr3[71] = (byte) (~bArr3[71]);
            if (i3 > 12) {
                bArr3[96] = (byte) (~bArr3[96]);
                bArr3[97] = (byte) (~bArr3[97]);
                bArr3[98] = (byte) (~bArr3[98]);
                bArr3[99] = (byte) (~bArr3[99]);
                bArr3[100] = (byte) (~bArr3[100]);
                bArr3[101] = (byte) (~bArr3[101]);
                bArr3[102] = (byte) (~bArr3[102]);
                bArr3[103] = (byte) (~bArr3[103]);
                if (i3 > 17) {
                    bArr3[136] = (byte) (~bArr3[136]);
                    bArr3[137] = (byte) (~bArr3[137]);
                    bArr3[138] = (byte) (~bArr3[138]);
                    bArr3[139] = (byte) (~bArr3[139]);
                    bArr3[140] = (byte) (~bArr3[140]);
                    bArr3[141] = (byte) (~bArr3[141]);
                    bArr3[142] = (byte) (~bArr3[142]);
                    bArr3[143] = (byte) (~bArr3[143]);
                    if (i3 > 20) {
                        bArr3[160] = (byte) (~bArr3[160]);
                        bArr3[161] = (byte) (~bArr3[161]);
                        bArr3[162] = (byte) (~bArr3[162]);
                        bArr3[163] = (byte) (~bArr3[163]);
                        bArr3[164] = (byte) (~bArr3[164]);
                        bArr3[165] = (byte) (~bArr3[165]);
                        bArr3[166] = (byte) (~bArr3[166]);
                        bArr3[167] = (byte) (~bArr3[167]);
                    }
                }
            }
        }
        System.arraycopy(bArr3, 0, bArr, i, i2);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void b(byte[] bArr, int i) {
        a(bArr, i, this.c);
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        u uVar = (u) super.clone();
        uVar.p = (int[]) this.p.clone();
        uVar.q = (byte[]) this.q.clone();
        uVar.r = (byte[]) this.r.clone();
        return uVar;
    }

    @Override // iaik.security.md.AbstractC0047p, iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    protected void engineReset() {
        super.engineReset();
        CryptoUtils.zeroBlock(this.p);
        CryptoUtils.zeroBlock(this.q);
        CryptoUtils.zeroBlock(this.r);
        int[] iArr = this.p;
        iArr[41] = -1;
        iArr[40] = -1;
        iArr[35] = -1;
        iArr[34] = -1;
        iArr[25] = -1;
        iArr[24] = -1;
        iArr[17] = -1;
        iArr[16] = -1;
        iArr[5] = -1;
        iArr[4] = -1;
        iArr[3] = -1;
        iArr[2] = -1;
    }
}
