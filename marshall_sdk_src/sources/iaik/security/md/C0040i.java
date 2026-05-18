package iaik.security.md;

import iaik.utils.CryptoUtils;

/* JADX INFO: renamed from: iaik.security.md.i, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0040i extends AbstractC0038g {
    private int[] k;
    private int[] l;
    private int[] m;
    private int[] n;

    public C0040i(int i) {
        super(i, 8);
        this.k = new int[16];
        this.l = new int[16];
        this.m = new int[16];
        this.n = new int[16];
        engineReset();
    }

    private static void a(int[] iArr, int[] iArr2, int i) {
        iArr[0] = iArr[0] ^ i;
        iArr[2] = iArr[2] ^ (268435456 ^ i);
        iArr[4] = iArr[4] ^ (536870912 ^ i);
        iArr[6] = iArr[6] ^ (805306368 ^ i);
        iArr[8] = iArr[8] ^ (1073741824 ^ i);
        iArr[10] = iArr[10] ^ (1342177280 ^ i);
        iArr[12] = iArr[12] ^ (1610612736 ^ i);
        iArr[14] = iArr[14] ^ (1879048192 ^ i);
        a(iArr, iArr2, 0, 0, 2, 4, 6, 9, 11, 13, 15);
        a(iArr, iArr2, 1, 9, 11, 13, 15, 0, 2, 4, 6);
        a(iArr, iArr2, 2, 2, 4, 6, 8, 11, 13, 15, 1);
        a(iArr, iArr2, 3, 11, 13, 15, 1, 2, 4, 6, 8);
        a(iArr, iArr2, 4, 4, 6, 8, 10, 13, 15, 1, 3);
        a(iArr, iArr2, 5, 13, 15, 1, 3, 4, 6, 8, 10);
        a(iArr, iArr2, 6, 6, 8, 10, 12, 15, 1, 3, 5);
        a(iArr, iArr2, 7, 15, 1, 3, 5, 6, 8, 10, 12);
        a(iArr, iArr2, 8, 8, 10, 12, 14, 1, 3, 5, 7);
        a(iArr, iArr2, 9, 1, 3, 5, 7, 8, 10, 12, 14);
        a(iArr, iArr2, 10, 10, 12, 14, 0, 3, 5, 7, 9);
        a(iArr, iArr2, 11, 3, 5, 7, 9, 10, 12, 14, 0);
        a(iArr, iArr2, 12, 12, 14, 0, 2, 5, 7, 9, 11);
        a(iArr, iArr2, 13, 5, 7, 9, 11, 12, 14, 0, 2);
        a(iArr, iArr2, 14, 14, 0, 2, 4, 7, 9, 11, 13);
        a(iArr, iArr2, 15, 7, 9, 11, 13, 14, 0, 2, 4);
    }

    private static void b(int[] iArr, int[] iArr2, int i) {
        iArr[0] = ~iArr[0];
        iArr[1] = iArr[1] ^ i;
        iArr[2] = ~iArr[2];
        iArr[3] = iArr[3] ^ (i ^ 16);
        iArr[4] = ~iArr[4];
        iArr[5] = iArr[5] ^ (i ^ 32);
        iArr[6] = ~iArr[6];
        iArr[7] = iArr[7] ^ (i ^ 48);
        iArr[8] = ~iArr[8];
        iArr[9] = iArr[9] ^ (i ^ 64);
        iArr[10] = ~iArr[10];
        iArr[11] = iArr[11] ^ (i ^ 80);
        iArr[12] = ~iArr[12];
        iArr[13] = iArr[13] ^ (i ^ 96);
        iArr[14] = ~iArr[14];
        iArr[15] = iArr[15] ^ (i ^ 112);
        a(iArr, iArr2, 0, 2, 6, 10, 14, 1, 5, 9, 13);
        a(iArr, iArr2, 1, 1, 5, 9, 13, 2, 6, 10, 14);
        a(iArr, iArr2, 2, 4, 8, 12, 0, 3, 7, 11, 15);
        a(iArr, iArr2, 3, 3, 7, 11, 15, 4, 8, 12, 0);
        a(iArr, iArr2, 4, 6, 10, 14, 2, 5, 9, 13, 1);
        a(iArr, iArr2, 5, 5, 9, 13, 1, 6, 10, 14, 2);
        a(iArr, iArr2, 6, 8, 12, 0, 4, 7, 11, 15, 3);
        a(iArr, iArr2, 7, 7, 11, 15, 3, 8, 12, 0, 4);
        a(iArr, iArr2, 8, 10, 14, 2, 6, 9, 13, 1, 5);
        a(iArr, iArr2, 9, 9, 13, 1, 5, 10, 14, 2, 6);
        a(iArr, iArr2, 10, 12, 0, 4, 8, 11, 15, 3, 7);
        a(iArr, iArr2, 11, 11, 15, 3, 7, 12, 0, 4, 8);
        a(iArr, iArr2, 12, 14, 2, 6, 10, 13, 1, 5, 9);
        a(iArr, iArr2, 13, 13, 1, 5, 9, 14, 2, 6, 10);
        a(iArr, iArr2, 14, 0, 4, 8, 12, 15, 3, 7, 11);
        a(iArr, iArr2, 15, 15, 3, 7, 11, 0, 4, 8, 12);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    public void a(byte[] bArr, int i) {
        CryptoUtils.squashBytesToInts(bArr, i, this.l, 0, 16);
        for (int i2 = 0; i2 < 16; i2++) {
            this.n[i2] = this.i[i2] ^ this.l[i2];
        }
        b(this.l, this.k, -1);
        b(this.k, this.l, -2);
        b(this.l, this.k, -3);
        b(this.k, this.l, -4);
        b(this.l, this.k, -5);
        b(this.k, this.l, -6);
        b(this.l, this.k, -7);
        b(this.k, this.l, -8);
        b(this.l, this.k, -9);
        b(this.k, this.m, -10);
        a(this.n, this.k, 0);
        a(this.k, this.l, 16777216);
        a(this.l, this.k, 33554432);
        a(this.k, this.l, 50331648);
        a(this.l, this.k, 67108864);
        a(this.k, this.l, 83886080);
        a(this.l, this.k, 100663296);
        a(this.k, this.l, 117440512);
        a(this.l, this.k, 134217728);
        a(this.k, this.n, 150994944);
        for (int i3 = 0; i3 < 16; i3++) {
            int[] iArr = this.i;
            iArr[i3] = iArr[i3] ^ (this.n[i3] ^ this.m[i3]);
        }
    }

    @Override // iaik.security.md.AbstractMessageDigest
    public void b(byte[] bArr, int i) {
        int length = this.i.length;
        int[] iArr = new int[length];
        System.arraycopy(this.i, 0, iArr, 0, length);
        a(iArr, this.k, 0);
        a(this.k, this.l, 16777216);
        a(this.l, this.k, 33554432);
        a(this.k, this.l, 50331648);
        a(this.l, this.k, 67108864);
        a(this.k, this.l, 83886080);
        a(this.l, this.k, 100663296);
        a(this.k, this.l, 117440512);
        a(this.l, this.k, 134217728);
        a(this.k, iArr, 150994944);
        for (int i2 = 0; i2 < 16; i2++) {
            int[] iArr2 = this.i;
            iArr2[i2] = iArr2[i2] ^ iArr[i2];
        }
        CryptoUtils.spreadIntsToBytes(this.i, this.i.length - (this.j.length >>> 2), this.j, 0, this.j.length >>> 2);
        System.arraycopy(this.j, this.j.length - this.c, bArr, i, this.c);
    }

    @Override // iaik.security.md.AbstractC0038g, iaik.security.md.AbstractC0037f, iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        C0040i c0040i = (C0040i) super.clone();
        c0040i.k = (int[]) this.k.clone();
        c0040i.l = (int[]) this.l.clone();
        c0040i.m = (int[]) this.m.clone();
        c0040i.n = (int[]) this.n.clone();
        return c0040i;
    }

    @Override // iaik.security.md.AbstractC0038g, iaik.security.md.AbstractC0037f, iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    public void engineReset() {
        super.engineReset();
        CryptoUtils.zeroBlock(this.k);
        CryptoUtils.zeroBlock(this.l);
        CryptoUtils.zeroBlock(this.m);
        CryptoUtils.zeroBlock(this.n);
    }
}
