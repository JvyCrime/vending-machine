package iaik.security.md;

/* JADX INFO: loaded from: classes.dex */
final class F extends E {
    private static final int[] l = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 7, 4, 13, 1, 10, 6, 15, 3, 12, 0, 9, 5, 2, 14, 11, 8, 3, 10, 14, 4, 9, 15, 8, 1, 2, 7, 0, 6, 13, 11, 5, 12, 1, 9, 11, 10, 0, 8, 12, 4, 13, 3, 7, 15, 14, 5, 6, 2, 4, 0, 5, 9, 7, 12, 2, 10, 14, 1, 3, 8, 11, 6, 15, 13};
    private static final int[] m = {5, 14, 7, 0, 9, 2, 11, 4, 13, 6, 15, 8, 1, 10, 3, 12, 6, 11, 3, 7, 0, 13, 5, 10, 14, 15, 8, 12, 4, 9, 1, 2, 15, 5, 1, 3, 7, 14, 6, 9, 11, 8, 12, 2, 10, 0, 4, 13, 8, 6, 4, 1, 3, 11, 15, 0, 5, 12, 2, 13, 9, 7, 10, 14, 12, 15, 10, 4, 1, 5, 8, 7, 6, 2, 13, 14, 0, 3, 9, 11};
    private static final int[] n = {11, 14, 15, 12, 5, 8, 7, 9, 11, 13, 14, 15, 6, 7, 9, 8, 7, 6, 8, 13, 11, 9, 7, 15, 7, 12, 15, 9, 11, 7, 13, 12, 11, 13, 6, 7, 14, 9, 13, 15, 14, 8, 13, 6, 5, 12, 7, 5, 11, 12, 14, 15, 14, 15, 9, 8, 9, 14, 5, 6, 8, 6, 5, 12, 9, 15, 5, 11, 6, 8, 13, 12, 5, 12, 13, 14, 11, 8, 5, 6};
    private static final int[] o = {8, 9, 9, 11, 13, 15, 15, 5, 7, 7, 8, 11, 14, 14, 12, 6, 9, 13, 15, 7, 12, 8, 9, 11, 7, 7, 12, 7, 6, 15, 13, 11, 9, 7, 15, 11, 8, 6, 6, 14, 12, 13, 5, 14, 13, 13, 7, 5, 15, 5, 8, 11, 14, 14, 6, 14, 6, 9, 12, 9, 12, 5, 15, 8, 8, 5, 12, 9, 12, 5, 14, 6, 8, 13, 6, 5, 15, 13, 11, 11};

    F() {
    }

    @Override // iaik.security.md.A
    public void a(byte[] bArr, int i) {
        int i2;
        int i3;
        int i4 = 0;
        int i5 = i;
        int i6 = 0;
        while (true) {
            i2 = 16;
            if (i6 >= 16) {
                break;
            }
            this.k[i6] = ((bArr[i5 + 2] & 255) << 16) | (bArr[i5] & 255) | ((bArr[i5 + 1] & 255) << 8) | (bArr[i5 + 3] << 24);
            i6++;
            i5 += 4;
        }
        int i7 = this.a;
        int i8 = this.b;
        int i9 = this.c;
        int i10 = this.d;
        int i11 = this.e;
        int i12 = this.f;
        int i13 = this.g;
        int i14 = this.h;
        int i15 = this.i;
        int i16 = this.j;
        while (i4 < 16) {
            int i17 = i7 + ((i8 ^ i9) ^ i10) + this.k[i4];
            int[] iArr = n;
            int i18 = ((i17 >>> (32 - iArr[i4])) | (i17 << iArr[i4])) + i11;
            int i19 = i12 + (((~i15) | i14) ^ i13) + this.k[m[i4]] + 1352829926;
            int[] iArr2 = o;
            int i20 = ((i19 >>> (32 - iArr2[i4])) | (i19 << iArr2[i4])) + i16;
            i4++;
            int i21 = i8;
            i8 = i18;
            i7 = i11;
            i11 = i10;
            i10 = (i9 >>> 22) | (i9 << 10);
            i9 = i21;
            int i22 = i13;
            i13 = i20;
            i12 = i16;
            i16 = i15;
            i15 = (i14 >>> 22) | (i14 << 10);
            i14 = i22;
        }
        int i23 = i8 ^ i13;
        int i24 = i13 ^ i23;
        int i25 = i9;
        int i26 = i23 ^ i24;
        int i27 = i25;
        int i28 = i14;
        int i29 = i24;
        int i30 = i28;
        while (i2 < 32) {
            int i31 = i7 + ((i26 & i27) | ((~i26) & i10)) + this.k[l[i2]] + 1518500249;
            int[] iArr3 = n;
            int i32 = ((i31 >>> (32 - iArr3[i2])) | (i31 << iArr3[i2])) + i11;
            int i33 = i12 + ((i29 & i15) | ((~i15) & i30)) + this.k[m[i2]] + 1548603684;
            int[] iArr4 = o;
            int i34 = ((i33 >>> (32 - iArr4[i2])) | (i33 << iArr4[i2])) + i16;
            i2++;
            int i35 = i10;
            i10 = (i27 >>> 22) | (i27 << 10);
            i27 = i26;
            i26 = i32;
            i7 = i11;
            i11 = i35;
            int i36 = i15;
            i15 = (i30 >>> 22) | (i30 << 10);
            i30 = i29;
            i29 = i34;
            i12 = i16;
            i16 = i36;
        }
        int i37 = i10 ^ i15;
        int i38 = i15 ^ i37;
        int i39 = i37 ^ i38;
        int i40 = 32;
        while (true) {
            i3 = 48;
            if (i40 >= 48) {
                break;
            }
            int i41 = i7 + (((~i27) | i26) ^ i39) + this.k[l[i40]] + 1859775393;
            int[] iArr5 = n;
            int i42 = ((i41 >>> (32 - iArr5[i40])) | (i41 << iArr5[i40])) + i11;
            int i43 = i12 + (((~i30) | i29) ^ i38) + this.k[m[i40]] + 1836072691;
            int[] iArr6 = o;
            int i44 = ((i43 >>> (32 - iArr6[i40])) | (i43 << iArr6[i40])) + i16;
            i40++;
            int i45 = i39;
            i39 = (i27 >>> 22) | (i27 << 10);
            i27 = i26;
            i26 = i42;
            i7 = i11;
            i11 = i45;
            int i46 = i38;
            i38 = (i30 >>> 22) | (i30 << 10);
            i30 = i29;
            i29 = i44;
            i12 = i16;
            i16 = i46;
        }
        int i47 = i7 ^ i12;
        int i48 = i12 ^ i47;
        int i49 = i47 ^ i48;
        int i50 = i11;
        int i51 = i39;
        int i52 = i50;
        while (i3 < 64) {
            int i53 = ((i49 + ((i26 & i51) | ((~i51) & i27))) + this.k[l[i3]]) - 1894007588;
            int[] iArr7 = n;
            int i54 = ((i53 >>> (32 - iArr7[i3])) | (i53 << iArr7[i3])) + i52;
            int i55 = i48 + ((i29 & i30) | ((~i29) & i38)) + this.k[m[i3]] + 2053994217;
            int[] iArr8 = o;
            int i56 = ((i55 >>> (32 - iArr8[i3])) | (i55 << iArr8[i3])) + i16;
            i3++;
            int i57 = i51;
            i51 = (i27 >>> 22) | (i27 << 10);
            i27 = i26;
            i26 = i54;
            i49 = i52;
            i52 = i57;
            int i58 = i38;
            i38 = (i30 >>> 22) | (i30 << 10);
            i30 = i29;
            i29 = i56;
            i48 = i16;
            i16 = i58;
        }
        int i59 = i27 ^ i30;
        int i60 = i30 ^ i59;
        int i61 = i59 ^ i60;
        int i62 = i48;
        int i63 = i29;
        int i64 = i16;
        int i65 = 64;
        while (i65 < 80) {
            int i66 = ((i49 + (((~i51) | i61) ^ i26)) + this.k[l[i65]]) - 1454113458;
            int[] iArr9 = n;
            int i67 = ((i66 >>> (32 - iArr9[i65])) | (i66 << iArr9[i65])) + i52;
            int i68 = i62 + ((i63 ^ i60) ^ i38) + this.k[m[i65]];
            int[] iArr10 = o;
            int i69 = ((i68 >>> (32 - iArr10[i65])) | (i68 << iArr10[i65])) + i64;
            i65++;
            int i70 = i51;
            i51 = (i61 >>> 22) | (i61 << 10);
            i61 = i26;
            i26 = i67;
            i49 = i52;
            i52 = i70;
            int i71 = i38;
            i38 = (i60 >>> 22) | (i60 << 10);
            i60 = i63;
            i63 = i69;
            i62 = i64;
            i64 = i71;
        }
        this.a += i49;
        this.b += i26;
        this.c += i61;
        this.d += i51;
        this.e += i64;
        this.f += i62;
        this.g += i63;
        this.h += i60;
        this.i += i38;
        this.j += i52;
    }
}
