package iaik.security.cipher;

import iaik.utils.CriticalObject;
import iaik.utils.CryptoUtils;
import java.security.InvalidKeyException;

/* JADX INFO: loaded from: classes.dex */
final class I extends AbstractC0028i {
    private final int[] b;
    private final int[] c;
    private final int[] m;

    I() {
        super("Serpent", 2);
        this.b = new int[132];
        this.c = new int[8];
        this.m = new int[4];
    }

    private static void a(int i, int[] iArr, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9 = iArr[i2];
        int i10 = i2 + 1;
        int i11 = iArr[i10];
        int i12 = i2 + 2;
        int i13 = iArr[i12];
        int i14 = i2 + 3;
        int i15 = iArr[i14];
        switch (i & 7) {
            case 0:
                int i16 = i9 ^ i15;
                int i17 = i13 ^ i16;
                int i18 = i16 & i11;
                int i19 = i11 ^ i17;
                int i20 = ~i17;
                int i21 = (i15 & i9) ^ i19;
                int i22 = i18 ^ i9;
                int i23 = ~i22;
                int i24 = i19 ^ (i13 | i22);
                int i25 = (i22 ^ i17) & i21;
                int i26 = i20 ^ i25;
                i3 = i23 ^ i25;
                i4 = i21;
                i5 = i24;
                i6 = i26;
                break;
            case 1:
                int i27 = (~i9) ^ i11;
                int i28 = i15 | i27;
                int i29 = (i9 | i27) ^ i13;
                i5 = i15 ^ i29;
                int i30 = i11 ^ i28;
                int i31 = i27 ^ i5;
                int i32 = (i29 & i30) ^ i31;
                int i33 = i30 ^ i29;
                i3 = i29 ^ (i31 & i33);
                i4 = i32;
                i6 = i32 ^ i33;
                break;
            case 2:
                int i34 = ~i9;
                int i35 = i11 ^ i15;
                int i36 = i15 | i34;
                i7 = (i13 & i34) ^ i35;
                int i37 = i34 ^ i13;
                int i38 = i11 & (i13 ^ i7);
                int i39 = i7 | i37;
                i4 = i37 ^ i38;
                i5 = i9 ^ ((i38 | i15) & i39);
                i6 = (i35 ^ i4) ^ (i5 ^ i36);
                i3 = i7;
                break;
            case 3:
                int i40 = (i9 ^ i13) ^ i15;
                int i41 = i9 & i40;
                int i42 = i15 ^ i41;
                int i43 = i40 ^ (i11 & i42);
                int i44 = i9 | i43;
                int i45 = i11 | i15;
                int i46 = i9 | i15;
                int i47 = i42 & i44;
                i8 = i45 ^ i47;
                int i48 = i46 ^ i11;
                int i49 = (i43 ^ i47) ^ i48;
                i3 = i48 ^ (i13 & (i41 ^ i45));
                i5 = i43;
                i4 = i49;
                i6 = i8;
                break;
            case 4:
                int i50 = i9 ^ i15;
                int i51 = i13 ^ (i15 & i50);
                int i52 = i11 | i51;
                int i53 = i50 ^ i52;
                int i54 = ~i11;
                i7 = (i50 | i54) ^ i51;
                int i55 = i50 ^ i54;
                i5 = (i9 & i7) ^ (i52 & i55);
                i6 = (i9 ^ i51) ^ (i55 & i5);
                i4 = i53;
                i3 = i7;
                break;
            case 5:
                int i56 = ~i9;
                int i57 = i9 ^ i11;
                int i58 = i9 ^ i15;
                int i59 = (i13 ^ i56) ^ (i57 | i58);
                int i60 = i15 & i59;
                i8 = (i57 ^ i59) ^ i60;
                int i61 = (i56 | i59) ^ i58;
                int i62 = i11 ^ i60;
                i5 = (i57 | i60) ^ i61;
                i4 = (i61 & i8) ^ i62;
                i3 = i59;
                i6 = i8;
                break;
            case 6:
                int i63 = ~i9;
                int i64 = i9 ^ i15;
                int i65 = i11 ^ i64;
                int i66 = (i63 | i64) ^ i13;
                i6 = i11 ^ i66;
                int i67 = ~i66;
                int i68 = (i64 | i6) ^ i15;
                i5 = (i66 & i68) ^ i65;
                int i69 = i66 ^ i68;
                i3 = i5 ^ i69;
                i4 = (i69 & i65) ^ i67;
                break;
            default:
                int i70 = ~i13;
                int i71 = i13 ^ i11;
                int i72 = i11 | i70;
                int i73 = i15 ^ i72;
                int i74 = i9 & i73;
                int i75 = i71 ^ i74;
                int i76 = (i11 ^ i74) | i71;
                i6 = i76 ^ (i9 ^ i15);
                int i77 = i74 ^ i6;
                i5 = (i75 & i77) ^ (i15 & i72);
                i3 = (i77 ^ i5) ^ (i70 | i73);
                i4 = i75;
                break;
        }
        iArr[i2] = i3;
        iArr[i10] = i6;
        iArr[i12] = i5;
        iArr[i14] = i4;
    }

    @Override // iaik.security.cipher.AbstractC0028i
    protected void a(int i, byte[] bArr) throws InvalidKeyException {
        int length = bArr.length;
        if (length != 16 && length != 24 && length != 32) {
            throw new InvalidKeyException("Key must be 128, 192, or 256 bits long!");
        }
        int i2 = length >> 2;
        int i3 = 0;
        CryptoUtils.squashBytesToIntsLE(bArr, 0, this.c, 0, i2);
        if (i2 != 8) {
            this.c[i2] = 1;
        }
        int[] iArr = this.c;
        int i4 = 3;
        int i5 = ((((iArr[0] ^ iArr[3]) ^ iArr[5]) ^ iArr[7]) ^ (-1640531527)) ^ 0;
        int[] iArr2 = this.b;
        iArr2[0] = (i5 >>> 21) | (i5 << 11);
        int i6 = ((((iArr[1] ^ iArr[4]) ^ iArr[6]) ^ iArr2[0]) ^ (-1640531527)) ^ 1;
        iArr2[1] = (i6 >>> 21) | (i6 << 11);
        int i7 = ((((iArr[2] ^ iArr[5]) ^ iArr[7]) ^ iArr2[1]) ^ (-1640531527)) ^ 2;
        iArr2[2] = (i7 >>> 21) | (i7 << 11);
        int i8 = ((((iArr[3] ^ iArr[6]) ^ iArr2[0]) ^ iArr2[2]) ^ (-1640531527)) ^ 3;
        iArr2[3] = (i8 >>> 21) | (i8 << 11);
        int i9 = ((((iArr[4] ^ iArr[7]) ^ iArr2[1]) ^ iArr2[3]) ^ (-1640531527)) ^ 4;
        iArr2[4] = (i9 >>> 21) | (i9 << 11);
        int i10 = ((((iArr[5] ^ iArr2[0]) ^ iArr2[2]) ^ iArr2[4]) ^ (-1640531527)) ^ 5;
        iArr2[5] = (i10 >>> 21) | (i10 << 11);
        int i11 = ((((iArr[6] ^ iArr2[1]) ^ iArr2[3]) ^ iArr2[5]) ^ (-1640531527)) ^ 6;
        iArr2[6] = (i11 >>> 21) | (i11 << 11);
        int i12 = ((((iArr[7] ^ iArr2[2]) ^ iArr2[4]) ^ iArr2[6]) ^ (-1640531527)) ^ 7;
        iArr2[7] = (i12 >>> 21) | (i12 << 11);
        for (int i13 = 8; i13 < 132; i13++) {
            int[] iArr3 = this.b;
            int i14 = ((((iArr3[i13 - 8] ^ iArr3[i13 - 5]) ^ iArr3[i13 - 3]) ^ iArr3[i13 - 1]) ^ (-1640531527)) ^ i13;
            iArr3[i13] = (i14 >>> 21) | (i14 << 11);
        }
        while (i3 < 132) {
            a(i4, this.b, i3);
            i3 += 4;
            i4 = (i4 - 1) & 7;
        }
        CriticalObject.destroy(this.c);
        CriticalObject.destroy(this.m);
    }

    /*  JADX ERROR: Type inference failed with stack overflow
        jadx.core.utils.exceptions.JadxOverflowException
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    @Override // iaik.security.cipher.AbstractC0028i
    protected void b() {
        /*
            Method dump skipped, instruction units count: 2624
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.cipher.I.b():void");
    }

    @Override // iaik.security.cipher.AbstractC0028i
    protected void c() {
        int[] iArr = this.b;
        int i = this.a[0] ^ iArr[128];
        int i2 = this.a[1] ^ iArr[129];
        int i3 = this.a[2] ^ iArr[130];
        int i4 = this.a[3] ^ iArr[131];
        int i5 = (i & i2) | i3;
        int i6 = (i | i2) & i4;
        int i7 = i5 ^ i6;
        int i8 = i2 ^ i6;
        int i9 = i3 ^ i8;
        int i10 = (i8 | (i7 ^ (~i4))) ^ i;
        int i11 = i9 ^ (i4 | i10);
        int i12 = ((i & i7) ^ i11) ^ (i5 ^ i10);
        int i13 = iArr[124] ^ i11;
        int i14 = iArr[125] ^ i10;
        int i15 = iArr[126] ^ i12;
        int i16 = iArr[127] ^ i7;
        int i17 = 120;
        while (true) {
            if (i17 < 0) {
                break;
            }
            int i18 = ((i15 >>> 22) | (i15 << 10)) ^ ((i14 << 7) ^ i16);
            int i19 = ((i13 >>> 5) | (i13 << 27)) ^ (i14 ^ i16);
            int i20 = ((i16 >>> 7) | (i16 << 25)) ^ ((i19 << 3) ^ i18);
            int i21 = ((i14 >>> 1) | (i14 << 31)) ^ (i19 ^ i18);
            int i22 = (i18 << 29) | (i18 >>> 3);
            int i23 = (i19 << 19) | (i19 >>> 13);
            int i24 = ~i23;
            int i25 = i23 ^ i21;
            int i26 = i22 ^ i25;
            int i27 = (i22 | i24) ^ i20;
            int i28 = i20 & i24;
            int i29 = i26 ^ i27;
            int i30 = i25 ^ (i26 & i27);
            int i31 = i27 ^ (i21 | i30);
            int i32 = i21 | i31;
            int i33 = i30 ^ i32;
            int i34 = i28 ^ (i32 ^ i26);
            int i35 = i17 + 1;
            int i36 = i33 ^ iArr[i17];
            int i37 = i35 + 1;
            int i38 = iArr[i35] ^ i29;
            int i39 = i37 + 1;
            int i40 = i34 ^ iArr[i37];
            int i41 = i31 ^ iArr[i39];
            int i42 = i39 - 7;
            int i43 = ((i40 << 10) | (i40 >>> 22)) ^ ((i38 << 7) ^ i41);
            int i44 = ((i36 << 27) | (i36 >>> 5)) ^ (i38 ^ i41);
            int i45 = ((i41 << 25) | (i41 >>> 7)) ^ ((i44 << 3) ^ i43);
            int i46 = ((i38 << 31) | (i38 >>> 1)) ^ (i44 ^ i43);
            int i47 = (i43 << 29) | (i43 >>> 3);
            int i48 = (i44 << 19) | (i44 >>> 13);
            int i49 = ~i47;
            int i50 = (i46 & i49) ^ i45;
            int i51 = i48 & i50;
            int i52 = (i46 ^ i49) ^ i51;
            int i53 = i46 | i52;
            int i54 = i50 ^ (i48 & i53);
            int i55 = i45 | i48;
            int i56 = (i49 ^ i53) ^ i55;
            int i57 = (i55 & i46) ^ ((i48 ^ i47) | i51);
            int i58 = i42 + 1;
            int i59 = iArr[i42] ^ i56;
            int i60 = i58 + 1;
            int i61 = iArr[i58] ^ i54;
            int i62 = i60 + 1;
            int i63 = i57 ^ iArr[i60];
            int i64 = iArr[i62] ^ i52;
            int i65 = i62 - 7;
            int i66 = ((i63 << 10) | (i63 >>> 22)) ^ ((i61 << 7) ^ i64);
            int i67 = ((i59 << 27) | (i59 >>> 5)) ^ (i61 ^ i64);
            int i68 = ((i64 << 25) | (i64 >>> 7)) ^ ((i67 << 3) ^ i66);
            int i69 = ((i61 << 31) | (i61 >>> 1)) ^ (i67 ^ i66);
            int i70 = (i66 << 29) | (i66 >>> 3);
            int i71 = (i67 << 19) | (i67 >>> 13);
            int i72 = (i70 | i68) ^ i69;
            int i73 = i71 & i72;
            int i74 = (i70 ^ i68) ^ i73;
            int i75 = i69 | i68;
            int i76 = ((i71 ^ i68) & i75) ^ i72;
            int i77 = ~i71;
            int i78 = i72 ^ ((i70 ^ i76) | i77);
            int i79 = ((i70 | i73) ^ i75) ^ (i76 | i77);
            int i80 = i65 + 1;
            int i81 = iArr[i65] ^ i78;
            int i82 = i80 + 1;
            int i83 = iArr[i80] ^ i74;
            int i84 = i82 + 1;
            int i85 = i79 ^ iArr[i82];
            int i86 = i76 ^ iArr[i84];
            int i87 = i84 - 7;
            int i88 = ((i85 << 10) | (i85 >>> 22)) ^ ((i83 << 7) ^ i86);
            int i89 = ((i81 << 27) | (i81 >>> 5)) ^ (i83 ^ i86);
            int i90 = ((i86 << 25) | (i86 >>> 7)) ^ ((i89 << 3) ^ i88);
            int i91 = ((i83 << 31) | (i83 >>> 1)) ^ (i89 ^ i88);
            int i92 = (i88 << 29) | (i88 >>> 3);
            int i93 = (i89 << 19) | (i89 >>> 13);
            int i94 = i91 ^ i92;
            int i95 = i91 | i92;
            int i96 = i93 ^ i90;
            int i97 = (i92 ^ i93) ^ i95;
            int i98 = i90 | i97;
            int i99 = i96 ^ i95;
            int i100 = i94 ^ i98;
            int i101 = (i98 | i94) ^ i99;
            int i102 = (i93 & i97) ^ (i99 | i100);
            int i103 = i97 ^ ((i95 ^ (i93 & i101)) & i100);
            int i104 = i87 + 1;
            int i105 = iArr[i87] ^ i100;
            int i106 = i104 + 1;
            int i107 = iArr[i104] ^ i102;
            int i108 = i106 + 1;
            int i109 = i101 ^ iArr[i106];
            int i110 = i103 ^ iArr[i108];
            int i111 = i108 - 7;
            int i112 = ((i109 << 10) | (i109 >>> 22)) ^ ((i107 << 7) ^ i110);
            int i113 = ((i105 << 27) | (i105 >>> 5)) ^ (i107 ^ i110);
            int i114 = ((i110 << 25) | (i110 >>> 7)) ^ ((i113 << 3) ^ i112);
            int i115 = ((i107 << 31) | (i107 >>> 1)) ^ (i113 ^ i112);
            int i116 = (i112 << 29) | (i112 >>> 3);
            int i117 = (i113 << 19) | (i113 >>> 13);
            int i118 = i115 ^ i114;
            int i119 = i117 ^ i116;
            int i120 = i116 ^ i118;
            int i121 = i115 & i120;
            int i122 = (i117 | (~i118)) ^ i114;
            int i123 = ~i120;
            int i124 = i121 ^ i119;
            int i125 = i114 & i123;
            int i126 = (i122 | i119) ^ i118;
            int i127 = i124 | i126;
            int i128 = i123 ^ i127;
            int i129 = i125 ^ (i127 ^ i119);
            int i130 = i111 + 1;
            int i131 = i124 ^ iArr[i111];
            int i132 = i130 + 1;
            int i133 = i128 ^ iArr[i130];
            int i134 = i132 + 1;
            int i135 = i129 ^ iArr[i132];
            int i136 = i126 ^ iArr[i134];
            int i137 = i134 - 7;
            int i138 = ((i135 << 10) | (i135 >>> 22)) ^ ((i133 << 7) ^ i136);
            int i139 = ((i131 << 27) | (i131 >>> 5)) ^ (i133 ^ i136);
            int i140 = ((i136 << 25) | (i136 >>> 7)) ^ ((i139 << 3) ^ i138);
            int i141 = ((i133 << 31) | (i133 >>> 1)) ^ (i139 ^ i138);
            int i142 = (i138 << 29) | (i138 >>> 3);
            int i143 = (i139 << 19) | (i139 >>> 13);
            int i144 = i143 ^ i140;
            int i145 = i143 & i141;
            int i146 = (i141 ^ i142) ^ i143;
            int i147 = (i141 | i140) ^ i146;
            int i148 = (i141 ^ (i142 | i144)) & i146;
            int i149 = i144 ^ i148;
            int i150 = i148 ^ (~i145);
            int i151 = (i147 & i149) ^ i150;
            int i152 = (i142 ^ i150) ^ (i143 & i140);
            int i153 = i137 + 1;
            int i154 = i152 ^ iArr[i137];
            int i155 = i153 + 1;
            int i156 = iArr[i153] ^ i149;
            int i157 = i155 + 1;
            int i158 = iArr[i155] ^ i151;
            int i159 = iArr[i157] ^ i147;
            int i160 = i157 - 7;
            int i161 = ((i158 << 10) | (i158 >>> 22)) ^ ((i156 << 7) ^ i159);
            int i162 = ((i154 << 27) | (i154 >>> 5)) ^ (i156 ^ i159);
            int i163 = ((i159 << 25) | (i159 >>> 7)) ^ ((i162 << 3) ^ i161);
            int i164 = ((i156 << 31) | (i156 >>> 1)) ^ (i162 ^ i161);
            int i165 = (i162 << 19) | (i162 >>> 13);
            int i166 = ~i165;
            int i167 = i164 ^ i165;
            int i168 = (i166 | i167) ^ i163;
            int i169 = ((i161 << 29) | (i161 >>> 3)) ^ i168;
            int i170 = (i163 & i167) ^ i166;
            int i171 = i167 ^ i169;
            int i172 = (i171 & i170) ^ i168;
            i7 = (i165 & i168) ^ (i169 | i172);
            i11 = i7 ^ (i170 ^ i169);
            if (i160 == 0) {
                i12 = i171;
                i10 = i172;
                break;
            }
            int i173 = i160 + 1;
            int i174 = i11 ^ iArr[i160];
            int i175 = i173 + 1;
            int i176 = iArr[i173] ^ i172;
            int i177 = i175 + 1;
            int i178 = i171 ^ iArr[i175];
            int i179 = iArr[i177] ^ i7;
            int i180 = i177 - 7;
            int i181 = ((i178 << 10) | (i178 >>> 22)) ^ ((i176 << 7) ^ i179);
            int i182 = ((i174 << 27) | (i174 >>> 5)) ^ (i176 ^ i179);
            int i183 = ((i179 << 25) | (i179 >>> 7)) ^ ((i182 << 3) ^ i181);
            int i184 = ((i176 << 31) | (i176 >>> 1)) ^ (i182 ^ i181);
            int i185 = (i181 << 29) | (i181 >>> 3);
            int i186 = (i182 << 19) | (i182 >>> 13);
            int i187 = (i186 & i184) | i185;
            int i188 = (i186 | i184) & i183;
            int i189 = i187 ^ i188;
            int i190 = i184 ^ i188;
            int i191 = i185 ^ i190;
            int i192 = (i190 | (i189 ^ (~i183))) ^ i186;
            int i193 = i191 ^ (i183 | i192);
            int i194 = ((i186 & i189) ^ i193) ^ (i187 ^ i192);
            int i195 = i180 + 1;
            int i196 = iArr[i180] ^ i193;
            int i197 = i195 + 1;
            int i198 = iArr[i195] ^ i192;
            int i199 = i197 + 1;
            i15 = iArr[i197] ^ i194;
            int i200 = iArr[i199] ^ i189;
            i10 = i192;
            i12 = i194;
            i11 = i193;
            i14 = i198;
            i13 = i196;
            i17 = i199 - 7;
            i7 = i189;
            i16 = i200;
        }
        this.a[0] = i11 ^ iArr[0];
        this.a[1] = i10 ^ iArr[1];
        this.a[2] = i12 ^ iArr[2];
        this.a[3] = iArr[3] ^ i7;
    }

    @Override // iaik.security.cipher.AbstractC0028i
    public void d() {
        super.d();
        CriticalObject.destroy(this.b);
        CriticalObject.destroy(this.c);
        CriticalObject.destroy(this.m);
    }
}
