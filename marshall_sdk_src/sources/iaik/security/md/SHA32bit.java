package iaik.security.md;

import iaik.utils.CryptoUtils;

/* JADX INFO: loaded from: classes.dex */
public abstract class SHA32bit extends AbstractMessageDigest {
    private static final int[] r = {1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998};
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
    private final int[] q;

    SHA32bit(String str, int i, int i2, int[] iArr) {
        super(str, i, i2);
        this.p = new int[64];
        this.g = new byte[8];
        this.q = iArr;
        engineReset();
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a() {
        long j = this.f << 3;
        for (int i = 0; i < 8; i++) {
            this.g[7 - i] = (byte) (j >>> (i << 3));
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
        int i4 = 16;
        int i5 = i3 + 1;
        int i6 = ((bArr[i2] & 255) << 16) | (bArr[i] << 24) | ((bArr[i3] & 255) << 8);
        int i7 = i5 + 1;
        iArr[0] = i6 | (bArr[i5] & 255);
        int i8 = i7 + 1;
        int i9 = bArr[i7] << 24;
        int i10 = i8 + 1;
        int i11 = ((bArr[i8] & 255) << 16) | i9;
        int i12 = i10 + 1;
        int i13 = i11 | ((bArr[i10] & 255) << 8);
        int i14 = i12 + 1;
        iArr[1] = i13 | (bArr[i12] & 255);
        int i15 = i14 + 1;
        int i16 = i15 + 1;
        int i17 = ((bArr[i15] & 255) << 16) | (bArr[i14] << 24);
        int i18 = i16 + 1;
        int i19 = i17 | ((bArr[i16] & 255) << 8);
        int i20 = i18 + 1;
        iArr[2] = i19 | (bArr[i18] & 255);
        int i21 = i20 + 1;
        int i22 = i21 + 1;
        int i23 = ((bArr[i21] & 255) << 16) | (bArr[i20] << 24);
        int i24 = i22 + 1;
        int i25 = i23 | ((bArr[i22] & 255) << 8);
        int i26 = i24 + 1;
        iArr[3] = i25 | (bArr[i24] & 255);
        int i27 = i26 + 1;
        int i28 = i27 + 1;
        int i29 = ((bArr[i27] & 255) << 16) | (bArr[i26] << 24);
        int i30 = i28 + 1;
        int i31 = i29 | ((bArr[i28] & 255) << 8);
        int i32 = i30 + 1;
        iArr[4] = i31 | (bArr[i30] & 255);
        int i33 = i32 + 1;
        int i34 = bArr[i32] << 24;
        int i35 = i33 + 1;
        int i36 = ((bArr[i33] & 255) << 16) | i34;
        int i37 = i35 + 1;
        int i38 = i36 | ((bArr[i35] & 255) << 8);
        int i39 = i37 + 1;
        iArr[5] = i38 | (bArr[i37] & 255);
        int i40 = i39 + 1;
        int i41 = bArr[i39] << 24;
        int i42 = i40 + 1;
        int i43 = ((bArr[i40] & 255) << 16) | i41;
        int i44 = i42 + 1;
        int i45 = i43 | ((bArr[i42] & 255) << 8);
        int i46 = i44 + 1;
        iArr[6] = i45 | (bArr[i44] & 255);
        int i47 = i46 + 1;
        int i48 = i47 + 1;
        int i49 = ((bArr[i47] & 255) << 16) | (bArr[i46] << 24);
        int i50 = i48 + 1;
        int i51 = i49 | ((bArr[i48] & 255) << 8);
        int i52 = i50 + 1;
        iArr[7] = i51 | (bArr[i50] & 255);
        int i53 = i52 + 1;
        int i54 = i53 + 1;
        int i55 = ((bArr[i53] & 255) << 16) | (bArr[i52] << 24);
        int i56 = i54 + 1;
        int i57 = i55 | ((bArr[i54] & 255) << 8);
        int i58 = i56 + 1;
        iArr[8] = i57 | (bArr[i56] & 255);
        int i59 = i58 + 1;
        int i60 = bArr[i58] << 24;
        int i61 = i59 + 1;
        int i62 = ((bArr[i59] & 255) << 16) | i60;
        int i63 = i61 + 1;
        int i64 = i62 | ((bArr[i61] & 255) << 8);
        int i65 = i63 + 1;
        iArr[9] = i64 | (bArr[i63] & 255);
        int i66 = i65 + 1;
        int i67 = bArr[i65] << 24;
        int i68 = i66 + 1;
        int i69 = ((bArr[i66] & 255) << 16) | i67;
        int i70 = i68 + 1;
        int i71 = i69 | ((bArr[i68] & 255) << 8);
        int i72 = i70 + 1;
        iArr[10] = i71 | (bArr[i70] & 255);
        int i73 = i72 + 1;
        int i74 = i73 + 1;
        int i75 = ((bArr[i73] & 255) << 16) | (bArr[i72] << 24);
        int i76 = i74 + 1;
        int i77 = i75 | ((bArr[i74] & 255) << 8);
        int i78 = i76 + 1;
        iArr[11] = i77 | (bArr[i76] & 255);
        int i79 = i78 + 1;
        int i80 = i79 + 1;
        int i81 = ((bArr[i79] & 255) << 16) | (bArr[i78] << 24);
        int i82 = i80 + 1;
        int i83 = i81 | ((bArr[i80] & 255) << 8);
        int i84 = i82 + 1;
        iArr[12] = i83 | (bArr[i82] & 255);
        int i85 = i84 + 1;
        int i86 = bArr[i84] << 24;
        int i87 = i85 + 1;
        int i88 = ((bArr[i85] & 255) << 16) | i86;
        int i89 = i87 + 1;
        int i90 = i88 | ((bArr[i87] & 255) << 8);
        int i91 = i89 + 1;
        iArr[13] = i90 | (bArr[i89] & 255);
        int i92 = i91 + 1;
        int i93 = i92 + 1;
        int i94 = ((bArr[i92] & 255) << 16) | (bArr[i91] << 24);
        int i95 = i93 + 1;
        int i96 = i94 | ((bArr[i93] & 255) << 8);
        int i97 = i95 + 1;
        iArr[14] = i96 | (bArr[i95] & 255);
        int i98 = i97 + 1;
        int i99 = i98 + 1;
        iArr[15] = ((bArr[i98] & 255) << 16) | (bArr[i97] << 24) | ((bArr[i99] & 255) << 8) | (bArr[i99 + 1] & 255);
        int i100 = iArr[0];
        int i101 = iArr[1];
        int i102 = iArr[2];
        int i103 = iArr[3];
        int i104 = iArr[4];
        int i105 = iArr[5];
        int i106 = iArr[6];
        int i107 = iArr[7];
        int i108 = iArr[8];
        int i109 = iArr[9];
        int i110 = iArr[10];
        int i111 = iArr[11];
        int i112 = iArr[12];
        int i113 = iArr[13];
        int i114 = iArr[14];
        int i115 = iArr[15];
        while (i4 < 64) {
            int[] iArr2 = this.p;
            int i116 = i4 + 1;
            i100 = i100 + i109 + ((((i114 >>> 17) | (i114 << 15)) ^ ((i114 >>> 19) | (i114 << 13))) ^ (i114 >>> 10)) + ((((i101 >>> 7) | (i101 << 25)) ^ ((i101 >>> 18) | (i101 << 14))) ^ (i101 >>> 3));
            iArr2[i4] = i100;
            int i117 = i116 + 1;
            i101 = i101 + ((((i115 >>> 17) | (i115 << 15)) ^ ((i115 >>> 19) | (i115 << 13))) ^ (i115 >>> 10)) + i110 + ((((i102 >>> 7) | (i102 << 25)) ^ ((i102 >>> 18) | (i102 << 14))) ^ (i102 >>> 3));
            iArr2[i116] = i101;
            int i118 = i117 + 1;
            i102 = i102 + i111 + ((((i100 >>> 17) | (i100 << 15)) ^ ((i100 >>> 19) | (i100 << 13))) ^ (i100 >>> 10)) + ((((i103 >>> 7) | (i103 << 25)) ^ ((i103 >>> 18) | (i103 << 14))) ^ (i103 >>> 3));
            iArr2[i117] = i102;
            int i119 = i118 + 1;
            i103 = i103 + i112 + ((((i101 >>> 17) | (i101 << 15)) ^ ((i101 >>> 19) | (i101 << 13))) ^ (i101 >>> 10)) + ((((i104 >>> 7) | (i104 << 25)) ^ ((i104 >>> 18) | (i104 << 14))) ^ (i104 >>> 3));
            iArr2[i118] = i103;
            int i120 = i119 + 1;
            i104 = i104 + i113 + ((((i102 >>> 17) | (i102 << 15)) ^ ((i102 >>> 19) | (i102 << 13))) ^ (i102 >>> 10)) + ((((i105 >>> 7) | (i105 << 25)) ^ ((i105 >>> 18) | (i105 << 14))) ^ (i105 >>> 3));
            iArr2[i119] = i104;
            int i121 = i120 + 1;
            i105 = i105 + ((((i103 >>> 17) | (i103 << 15)) ^ ((i103 >>> 19) | (i103 << 13))) ^ (i103 >>> 10)) + i114 + ((((i106 >>> 7) | (i106 << 25)) ^ ((i106 >>> 18) | (i106 << 14))) ^ (i106 >>> 3));
            iArr2[i120] = i105;
            int i122 = i121 + 1;
            i106 = i106 + i115 + ((((i104 >>> 17) | (i104 << 15)) ^ ((i104 >>> 19) | (i104 << 13))) ^ (i104 >>> 10)) + ((((i107 >>> 7) | (i107 << 25)) ^ ((i107 >>> 18) | (i107 << 14))) ^ (i107 >>> 3));
            iArr2[i121] = i106;
            int i123 = i122 + 1;
            i107 = i107 + ((((i105 >>> 17) | (i105 << 15)) ^ ((i105 >>> 19) | (i105 << 13))) ^ (i105 >>> 10)) + i100 + ((((i108 >>> 7) | (i108 << 25)) ^ ((i108 >>> 18) | (i108 << 14))) ^ (i108 >>> 3));
            iArr2[i122] = i107;
            int i124 = i123 + 1;
            i108 = i108 + i101 + ((((i106 >>> 17) | (i106 << 15)) ^ ((i106 >>> 19) | (i106 << 13))) ^ (i106 >>> 10)) + ((((i109 >>> 7) | (i109 << 25)) ^ ((i109 >>> 18) | (i109 << 14))) ^ (i109 >>> 3));
            iArr2[i123] = i108;
            int i125 = i124 + 1;
            i109 = i109 + ((((i107 >>> 17) | (i107 << 15)) ^ ((i107 >>> 19) | (i107 << 13))) ^ (i107 >>> 10)) + i102 + ((((i110 >>> 7) | (i110 << 25)) ^ ((i110 >>> 18) | (i110 << 14))) ^ (i110 >>> 3));
            iArr2[i124] = i109;
            int i126 = i125 + 1;
            i110 = i110 + i103 + ((((i108 >>> 17) | (i108 << 15)) ^ ((i108 >>> 19) | (i108 << 13))) ^ (i108 >>> 10)) + ((((i111 >>> 7) | (i111 << 25)) ^ ((i111 >>> 18) | (i111 << 14))) ^ (i111 >>> 3));
            iArr2[i125] = i110;
            int i127 = i126 + 1;
            i111 = i111 + ((((i109 >>> 17) | (i109 << 15)) ^ ((i109 >>> 19) | (i109 << 13))) ^ (i109 >>> 10)) + i104 + ((((i112 >>> 7) | (i112 << 25)) ^ ((i112 >>> 18) | (i112 << 14))) ^ (i112 >>> 3));
            iArr2[i126] = i111;
            int i128 = i127 + 1;
            i112 = i112 + i105 + ((((i110 >>> 17) | (i110 << 15)) ^ ((i110 >>> 19) | (i110 << 13))) ^ (i110 >>> 10)) + ((((i113 >>> 7) | (i113 << 25)) ^ ((i113 >>> 18) | (i113 << 14))) ^ (i113 >>> 3));
            iArr2[i127] = i112;
            int i129 = i128 + 1;
            i113 = i113 + ((((i111 >>> 17) | (i111 << 15)) ^ ((i111 >>> 19) | (i111 << 13))) ^ (i111 >>> 10)) + i106 + ((((i114 >>> 7) | (i114 << 25)) ^ ((i114 >>> 18) | (i114 << 14))) ^ (i114 >>> 3));
            iArr2[i128] = i113;
            int i130 = i129 + 1;
            i114 = i114 + i107 + ((((i112 >>> 17) | (i112 << 15)) ^ ((i112 >>> 19) | (i112 << 13))) ^ (i112 >>> 10)) + ((((i115 >>> 7) | (i115 << 25)) ^ ((i115 >>> 18) | (i115 << 14))) ^ (i115 >>> 3));
            iArr2[i129] = i114;
            i4 = i130 + 1;
            i115 = i115 + ((((i113 >>> 17) | (i113 << 15)) ^ ((i113 >>> 19) | (i113 << 13))) ^ (i113 >>> 10)) + i108 + ((((i100 >>> 7) | (i100 << 25)) ^ ((i100 >>> 18) | (i100 << 14))) ^ (i100 >>> 3));
            iArr2[i130] = i115;
        }
        int i131 = this.h;
        int i132 = this.i;
        int i133 = this.j;
        int i134 = this.k;
        int i135 = this.l;
        int i136 = this.m;
        int i137 = this.n;
        int i138 = this.o;
        int i139 = 0;
        while (i139 <= 56) {
            int[] iArr3 = r;
            int i140 = iArr3[i139];
            int[] iArr4 = this.p;
            int i141 = i139 + 1;
            int i142 = i138 + i140 + iArr4[i139] + ((((i135 >>> 6) | (i135 << 26)) ^ ((i135 >>> 11) | (i135 << 21))) ^ ((i135 >>> 25) | (i135 << 7))) + ((i135 & i136) ^ ((~i135) & i137));
            int i143 = i134 + i142;
            int i144 = i131 & i132;
            int i145 = i142 + ((((i131 >>> 2) | (i131 << 30)) ^ ((i131 >>> 13) | (i131 << 19))) ^ ((i131 >>> 22) | (i131 << 10))) + (((i131 & i133) ^ i144) ^ (i132 & i133));
            int i146 = i141 + 1;
            int i147 = i137 + iArr3[i141] + iArr4[i141] + ((((i143 >>> 6) | (i143 << 26)) ^ ((i143 >>> 11) | (i143 << 21))) ^ ((i143 >>> 25) | (i143 << 7))) + ((i143 & i135) ^ ((~i143) & i136));
            int i148 = i133 + i147;
            int i149 = i145 & i131;
            int i150 = i147 + ((((i145 >>> 2) | (i145 << 30)) ^ ((i145 >>> 13) | (i145 << 19))) ^ ((i145 >>> 22) | (i145 << 10))) + (i144 ^ ((i145 & i132) ^ i149));
            int i151 = i146 + 1;
            int i152 = i136 + iArr3[i146] + iArr4[i146] + ((((i148 >>> 6) | (i148 << 26)) ^ ((i148 >>> 11) | (i148 << 21))) ^ ((i148 >>> 25) | (i148 << 7))) + ((i148 & i143) ^ ((~i148) & i135));
            int i153 = i132 + i152;
            int i154 = i150 & i145;
            int i155 = i152 + ((((i150 >>> 2) | (i150 << 30)) ^ ((i150 >>> 13) | (i150 << 19))) ^ ((i150 >>> 22) | (i150 << 10))) + (i149 ^ ((i150 & i131) ^ i154));
            int i156 = i151 + 1;
            int i157 = i135 + iArr3[i151] + iArr4[i151] + ((((i153 >>> 6) | (i153 << 26)) ^ ((i153 >>> 11) | (i153 << 21))) ^ ((i153 >>> 25) | (i153 << 7))) + ((i153 & i148) ^ ((~i153) & i143));
            int i158 = i131 + i157;
            int i159 = i155 & i150;
            int i160 = i157 + ((((i155 >>> 2) | (i155 << 30)) ^ ((i155 >>> 13) | (i155 << 19))) ^ ((i155 >>> 22) | (i155 << 10))) + (i154 ^ ((i155 & i145) ^ i159));
            int i161 = i156 + 1;
            int i162 = i143 + iArr3[i156] + iArr4[i156] + ((((i158 >>> 6) | (i158 << 26)) ^ ((i158 >>> 11) | (i158 << 21))) ^ ((i158 >>> 25) | (i158 << 7))) + ((i158 & i153) ^ ((~i158) & i148));
            i138 = i145 + i162;
            int i163 = i160 & i155;
            i134 = i162 + ((((i160 >>> 2) | (i160 << 30)) ^ ((i160 >>> 13) | (i160 << 19))) ^ ((i160 >>> 22) | (i160 << 10))) + (i159 ^ ((i160 & i150) ^ i163));
            int i164 = i161 + 1;
            int i165 = i148 + iArr3[i161] + iArr4[i161] + ((((i138 >>> 6) | (i138 << 26)) ^ ((i138 >>> 11) | (i138 << 21))) ^ ((i138 >>> 25) | (i138 << 7))) + ((i138 & i158) ^ ((~i138) & i153));
            i137 = i150 + i165;
            int i166 = i134 & i160;
            i133 = i165 + ((((i134 >>> 2) | (i134 << 30)) ^ ((i134 >>> 13) | (i134 << 19))) ^ ((i134 >>> 22) | (i134 << 10))) + (i163 ^ ((i134 & i155) ^ i166));
            int i167 = i164 + 1;
            int i168 = i153 + iArr3[i164] + iArr4[i164] + ((((i137 >>> 6) | (i137 << 26)) ^ ((i137 >>> 11) | (i137 << 21))) ^ ((i137 >>> 25) | (i137 << 7))) + ((i137 & i138) ^ ((~i137) & i158));
            i136 = i155 + i168;
            int i169 = i133 & i134;
            i132 = i168 + ((((i133 >>> 2) | (i133 << 30)) ^ ((i133 >>> 13) | (i133 << 19))) ^ ((i133 >>> 22) | (i133 << 10))) + (i166 ^ ((i133 & i160) ^ i169));
            int i170 = i158 + iArr3[i167] + iArr4[i167] + ((((i136 >>> 6) | (i136 << 26)) ^ ((i136 >>> 11) | (i136 << 21))) ^ ((i136 >>> 25) | (i136 << 7))) + ((i136 & i137) ^ ((~i136) & i138));
            i135 = i160 + i170;
            i131 = i170 + ((((i132 >>> 2) | (i132 << 30)) ^ ((i132 >>> 13) | (i132 << 19))) ^ ((i132 >>> 22) | (i132 << 10))) + (i169 ^ ((i132 & i133) ^ (i132 & i134)));
            i139 = i167 + 1;
        }
        this.h += i131;
        this.i += i132;
        this.j += i133;
        this.k += i134;
        this.l += i135;
        this.m += i136;
        this.n += i137;
        this.o += i138;
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void b(byte[] bArr, int i) {
        int i2 = 0;
        int[] iArr = {this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o};
        int i3 = i;
        while (i3 < this.c + i) {
            int i4 = i3 + 1;
            bArr[i3] = (byte) (iArr[i2] >> 24);
            int i5 = i4 + 1;
            bArr[i4] = (byte) (iArr[i2] >> 16);
            int i6 = i5 + 1;
            bArr[i5] = (byte) (iArr[i2] >> 8);
            i3 = i6 + 1;
            bArr[i6] = (byte) iArr[i2];
            i2++;
        }
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        SHA32bit sHA32bit = (SHA32bit) super.clone();
        sHA32bit.h = this.h;
        sHA32bit.i = this.i;
        sHA32bit.j = this.j;
        sHA32bit.k = this.k;
        sHA32bit.l = this.l;
        sHA32bit.m = this.m;
        sHA32bit.n = this.n;
        sHA32bit.o = this.o;
        System.arraycopy(this.p, 0, sHA32bit.p, 0, 64);
        System.arraycopy(this.g, 0, sHA32bit.g, 0, 8);
        return sHA32bit;
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    public void engineReset() {
        int[] iArr = this.q;
        this.h = iArr[0];
        this.i = iArr[1];
        this.j = iArr[2];
        this.k = iArr[3];
        this.l = iArr[4];
        this.m = iArr[5];
        this.n = iArr[6];
        this.o = iArr[7];
        CryptoUtils.zeroBlock(this.b);
        CryptoUtils.zeroBlock(this.g);
        CryptoUtils.zeroBlock(this.p);
        this.f = 0L;
    }
}
