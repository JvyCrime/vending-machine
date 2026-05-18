package iaik.security.md;

import iaik.utils.CryptoUtils;

/* JADX INFO: loaded from: classes.dex */
abstract class E implements A {
    int a;
    int b;
    int c;
    int d;
    int e;
    int f;
    int g;
    int h;
    int i;
    int j;
    final transient int[] k = new int[16];

    E() {
    }

    @Override // iaik.security.md.A
    public void a() {
        this.a = 1732584193;
        this.b = -271733879;
        this.c = -1732584194;
        this.d = 271733878;
        this.e = -1009589776;
        this.f = 1985229328;
        this.g = -19088744;
        this.h = -1985229329;
        this.i = 19088743;
        this.j = 1009589775;
        CryptoUtils.zeroBlock(this.k);
    }

    @Override // iaik.security.md.A
    public void b(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = this.a;
        bArr[i] = (byte) i3;
        int i4 = i2 + 1;
        bArr[i2] = (byte) (i3 >> 8);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i3 >> 16);
        int i6 = i5 + 1;
        bArr[i5] = (byte) (i3 >> 24);
        int i7 = i6 + 1;
        int i8 = this.b;
        bArr[i6] = (byte) i8;
        int i9 = i7 + 1;
        bArr[i7] = (byte) (i8 >> 8);
        int i10 = i9 + 1;
        bArr[i9] = (byte) (i8 >> 16);
        int i11 = i10 + 1;
        bArr[i10] = (byte) (i8 >> 24);
        int i12 = i11 + 1;
        int i13 = this.c;
        bArr[i11] = (byte) i13;
        int i14 = i12 + 1;
        bArr[i12] = (byte) (i13 >> 8);
        int i15 = i14 + 1;
        bArr[i14] = (byte) (i13 >> 16);
        int i16 = i15 + 1;
        bArr[i15] = (byte) (i13 >> 24);
        int i17 = i16 + 1;
        int i18 = this.d;
        bArr[i16] = (byte) i18;
        int i19 = i17 + 1;
        bArr[i17] = (byte) (i18 >> 8);
        int i20 = i19 + 1;
        bArr[i19] = (byte) (i18 >> 16);
        int i21 = i20 + 1;
        bArr[i20] = (byte) (i18 >> 24);
        int i22 = i21 + 1;
        int i23 = this.e;
        bArr[i21] = (byte) i23;
        int i24 = i22 + 1;
        bArr[i22] = (byte) (i23 >> 8);
        int i25 = i24 + 1;
        bArr[i24] = (byte) (i23 >> 16);
        int i26 = i25 + 1;
        bArr[i25] = (byte) (i23 >> 24);
        int i27 = i26 + 1;
        int i28 = this.f;
        bArr[i26] = (byte) i28;
        int i29 = i27 + 1;
        bArr[i27] = (byte) (i28 >> 8);
        int i30 = i29 + 1;
        bArr[i29] = (byte) (i28 >> 16);
        int i31 = i30 + 1;
        bArr[i30] = (byte) (i28 >> 24);
        int i32 = i31 + 1;
        int i33 = this.g;
        bArr[i31] = (byte) i33;
        int i34 = i32 + 1;
        bArr[i32] = (byte) (i33 >> 8);
        int i35 = i34 + 1;
        bArr[i34] = (byte) (i33 >> 16);
        int i36 = i35 + 1;
        bArr[i35] = (byte) (i33 >> 24);
        int i37 = i36 + 1;
        int i38 = this.h;
        bArr[i36] = (byte) i38;
        int i39 = i37 + 1;
        bArr[i37] = (byte) (i38 >> 8);
        int i40 = i39 + 1;
        bArr[i39] = (byte) (i38 >> 16);
        int i41 = i40 + 1;
        bArr[i40] = (byte) (i38 >> 24);
        int i42 = i41 + 1;
        int i43 = this.i;
        bArr[i41] = (byte) i43;
        int i44 = i42 + 1;
        bArr[i42] = (byte) (i43 >> 8);
        int i45 = i44 + 1;
        bArr[i44] = (byte) (i43 >> 16);
        int i46 = i45 + 1;
        bArr[i45] = (byte) (i43 >> 24);
        int i47 = i46 + 1;
        int i48 = this.j;
        bArr[i46] = (byte) i48;
        int i49 = i47 + 1;
        bArr[i47] = (byte) (i48 >> 8);
        bArr[i49] = (byte) (i48 >> 16);
        bArr[i49 + 1] = (byte) (i48 >> 24);
    }

    @Override // iaik.security.md.A
    public Object clone() {
        try {
            E e = (E) super.clone();
            System.arraycopy(this.k, 0, e.k, 0, 16);
            return e;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }
}
