package iaik.security.md;

import iaik.utils.CryptoUtils;

/* JADX INFO: loaded from: classes.dex */
abstract class B implements A {
    int a;
    int b;
    int c;
    int d;
    int e;
    final transient int[] f = new int[16];

    B() {
    }

    @Override // iaik.security.md.A
    public void a() {
        this.a = 1732584193;
        this.b = -271733879;
        this.c = -1732584194;
        this.d = 271733878;
        this.e = -1009589776;
        CryptoUtils.zeroBlock(this.f);
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
        bArr[i24] = (byte) (i23 >> 16);
        bArr[i24 + 1] = (byte) (i23 >> 24);
    }

    @Override // iaik.security.md.A
    public Object clone() {
        try {
            B b = (B) super.clone();
            System.arraycopy(this.f, 0, b.f, 0, 16);
            return b;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }
}
