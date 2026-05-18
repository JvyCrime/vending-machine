package iaik.security.md;

/* JADX INFO: loaded from: classes.dex */
final class C extends B {
    private static final int[] g = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 7, 4, 13, 1, 10, 6, 15, 3, 12, 0, 9, 5, 2, 14, 11, 8, 3, 10, 14, 4, 9, 15, 8, 1, 2, 7, 0, 6, 13, 11, 5, 12, 1, 9, 11, 10, 0, 8, 12, 4, 13, 3, 7, 15, 14, 5, 6, 2, 4, 0, 5, 9, 7, 12, 2, 10, 14, 1, 3, 8, 11, 6, 15, 13};
    private static final int[] h = {5, 14, 7, 0, 9, 2, 11, 4, 13, 6, 15, 8, 1, 10, 3, 12, 6, 11, 3, 7, 0, 13, 5, 10, 14, 15, 8, 12, 4, 9, 1, 2, 15, 5, 1, 3, 7, 14, 6, 9, 11, 8, 12, 2, 10, 0, 4, 13, 8, 6, 4, 1, 3, 11, 15, 0, 5, 12, 2, 13, 9, 7, 10, 14, 12, 15, 10, 4, 1, 5, 8, 7, 6, 2, 13, 14, 0, 3, 9, 11};
    private static final int[] i = {11, 14, 15, 12, 5, 8, 7, 9, 11, 13, 14, 15, 6, 7, 9, 8, 7, 6, 8, 13, 11, 9, 7, 15, 7, 12, 15, 9, 11, 7, 13, 12, 11, 13, 6, 7, 14, 9, 13, 15, 14, 8, 13, 6, 5, 12, 7, 5, 11, 12, 14, 15, 14, 15, 9, 8, 9, 14, 5, 6, 8, 6, 5, 12, 9, 15, 5, 11, 6, 8, 13, 12, 5, 12, 13, 14, 11, 8, 5, 6};
    private static final int[] j = {8, 9, 9, 11, 13, 15, 15, 5, 7, 7, 8, 11, 14, 14, 12, 6, 9, 13, 15, 7, 12, 8, 9, 11, 7, 7, 12, 7, 6, 15, 13, 11, 9, 7, 15, 11, 8, 6, 6, 14, 12, 13, 5, 14, 13, 13, 7, 5, 15, 5, 8, 11, 14, 14, 6, 14, 6, 9, 12, 9, 12, 5, 15, 8, 8, 5, 12, 9, 12, 5, 14, 6, 8, 13, 6, 5, 15, 13, 11, 11};

    C() {
    }

    @Override // iaik.security.md.A
    public void a(byte[] bArr, int i2) {
        int i3;
        int i4;
        int i5;
        int i6 = 0;
        int i7 = i2;
        int i8 = 0;
        while (true) {
            i3 = 16;
            if (i8 >= 16) {
                break;
            }
            this.f[i8] = ((bArr[i7 + 2] & 255) << 16) | (bArr[i7] & 255) | ((bArr[i7 + 1] & 255) << 8) | (bArr[i7 + 3] << 24);
            i8++;
            i7 += 4;
        }
        int i9 = this.a;
        int i10 = this.b;
        int i11 = this.c;
        int i12 = this.d;
        int i13 = this.e;
        int i14 = this.a;
        int i15 = this.b;
        int i16 = this.c;
        int i17 = this.d;
        int i18 = this.e;
        while (i6 < 16) {
            int i19 = i9 + ((i10 ^ i11) ^ i12) + this.f[i6];
            int[] iArr = i;
            int i20 = ((i19 >>> (32 - iArr[i6])) | (i19 << iArr[i6])) + i13;
            int i21 = i14 + (((~i17) | i16) ^ i15) + this.f[h[i6]] + 1352829926;
            int[] iArr2 = j;
            int i22 = ((i21 >>> (32 - iArr2[i6])) | (i21 << iArr2[i6])) + i18;
            i6++;
            int i23 = i10;
            i10 = i20;
            i9 = i13;
            i13 = i12;
            i12 = (i11 >>> 22) | (i11 << 10);
            i11 = i23;
            int i24 = i15;
            i15 = i22;
            i14 = i18;
            i18 = i17;
            i17 = (i16 >>> 22) | (i16 << 10);
            i16 = i24;
        }
        while (i3 < 32) {
            int i25 = i9 + ((i10 & i11) | ((~i10) & i12)) + this.f[g[i3]] + 1518500249;
            int i26 = (i11 << 10) | (i11 >>> 22);
            int[] iArr3 = i;
            int i27 = ((i25 >>> (32 - iArr3[i3])) | (i25 << iArr3[i3])) + i13;
            int i28 = i14 + ((i15 & i17) | ((~i17) & i16)) + this.f[h[i3]] + 1548603684;
            int i29 = (i16 << 10) | (i16 >>> 22);
            int[] iArr4 = j;
            int i30 = ((i28 >>> (32 - iArr4[i3])) | (i28 << iArr4[i3])) + i18;
            i3++;
            i16 = i15;
            i15 = i30;
            i14 = i18;
            i18 = i17;
            i17 = i29;
            i11 = i10;
            i10 = i27;
            i9 = i13;
            i13 = i12;
            i12 = i26;
        }
        int i31 = 32;
        while (true) {
            i4 = 48;
            if (i31 >= 48) {
                break;
            }
            int i32 = i9 + (((~i11) | i10) ^ i12) + this.f[g[i31]] + 1859775393;
            int i33 = (i11 << 10) | (i11 >>> 22);
            int[] iArr5 = i;
            int i34 = ((i32 >>> (32 - iArr5[i31])) | (i32 << iArr5[i31])) + i13;
            int i35 = i14 + (((~i16) | i15) ^ i17) + this.f[h[i31]] + 1836072691;
            int i36 = (i16 << 10) | (i16 >>> 22);
            int[] iArr6 = j;
            int i37 = ((i35 >>> (32 - iArr6[i31])) | (i35 << iArr6[i31])) + i18;
            i31++;
            i16 = i15;
            i15 = i37;
            i14 = i18;
            i18 = i17;
            i17 = i36;
            i11 = i10;
            i10 = i34;
            i9 = i13;
            i13 = i12;
            i12 = i33;
        }
        while (true) {
            i5 = 64;
            if (i4 >= 64) {
                break;
            }
            int i38 = ((i9 + ((i10 & i12) | ((~i12) & i11))) + this.f[g[i4]]) - 1894007588;
            int i39 = (i11 << 10) | (i11 >>> 22);
            int[] iArr7 = i;
            int i40 = ((i38 >>> (32 - iArr7[i4])) | (i38 << iArr7[i4])) + i13;
            int i41 = i14 + ((i15 & i16) | ((~i15) & i17)) + this.f[h[i4]] + 2053994217;
            int i42 = (i16 << 10) | (i16 >>> 22);
            int[] iArr8 = j;
            int i43 = ((i41 >>> (32 - iArr8[i4])) | (i41 << iArr8[i4])) + i18;
            i4++;
            i16 = i15;
            i15 = i43;
            i14 = i18;
            i18 = i17;
            i17 = i42;
            i11 = i10;
            i10 = i40;
            i9 = i13;
            i13 = i12;
            i12 = i39;
        }
        int i44 = i10;
        int i45 = i9;
        int i46 = i13;
        int i47 = i14;
        int i48 = i18;
        while (i5 < 80) {
            int i49 = ((i45 + (((~i12) | i11) ^ i44)) + this.f[g[i5]]) - 1454113458;
            int[] iArr9 = i;
            int i50 = ((i49 >>> (32 - iArr9[i5])) | (i49 << iArr9[i5])) + i46;
            int i51 = i47 + ((i15 ^ i16) ^ i17) + this.f[h[i5]];
            int[] iArr10 = j;
            int i52 = ((i51 >>> (32 - iArr10[i5])) | (i51 << iArr10[i5])) + i48;
            i5++;
            i45 = i46;
            i46 = i12;
            i12 = (i11 >>> 22) | (i11 << 10);
            i11 = i44;
            i44 = i50;
            int i53 = i15;
            i15 = i52;
            i47 = i48;
            i48 = i17;
            i17 = (i16 >>> 22) | (i16 << 10);
            i16 = i53;
        }
        int i54 = i17 + i11 + this.b;
        this.b = this.c + i12 + i48;
        this.c = this.d + i46 + i47;
        this.d = this.e + i45 + i15;
        this.e = this.a + i44 + i16;
        this.a = i54;
    }
}
