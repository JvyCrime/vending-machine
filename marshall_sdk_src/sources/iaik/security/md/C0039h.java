package iaik.security.md;

import com.ftdi.j2xx.ft4222.FT_4222_Defines;
import com.magtek.mobile.android.mtusdk.cms.DataTypeTag;
import iaik.utils.CryptoUtils;

/* JADX INFO: renamed from: iaik.security.md.h, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0039h extends AbstractC0038g {
    private int[] k;
    private int[] l;
    private int[] m;
    private int[] n;

    public C0039h(int i) {
        super(i, 16);
        this.k = new int[32];
        this.l = new int[32];
        this.m = new int[32];
        this.n = new int[32];
        engineReset();
    }

    private static void a(int[] iArr, int i) {
        iArr[0] = iArr[0] ^ i;
        iArr[2] = iArr[2] ^ (268435456 ^ i);
        iArr[4] = iArr[4] ^ (536870912 ^ i);
        iArr[6] = iArr[6] ^ (805306368 ^ i);
        iArr[8] = iArr[8] ^ (1073741824 ^ i);
        iArr[10] = iArr[10] ^ (1342177280 ^ i);
        iArr[12] = iArr[12] ^ (1610612736 ^ i);
        iArr[14] = iArr[14] ^ (1879048192 ^ i);
        iArr[16] = iArr[16] ^ (Integer.MIN_VALUE ^ i);
        iArr[18] = iArr[18] ^ ((-1879048192) ^ i);
        iArr[20] = iArr[20] ^ ((-1610612736) ^ i);
        iArr[22] = iArr[22] ^ ((-1342177280) ^ i);
        iArr[24] = iArr[24] ^ ((-1073741824) ^ i);
        iArr[26] = iArr[26] ^ ((-805306368) ^ i);
        iArr[28] = iArr[28] ^ ((-536870912) ^ i);
        iArr[30] = (i ^ (-268435456)) ^ iArr[30];
    }

    private static void a(int[] iArr, int[] iArr2) {
        a(iArr, iArr2, 0, 0, 2, 4, 6, 9, 11, 13, 23);
        a(iArr, iArr2, 2, 2, 4, 6, 8, 11, 13, 15, 25);
        a(iArr, iArr2, 4, 4, 6, 8, 10, 13, 15, 17, 27);
        a(iArr, iArr2, 6, 6, 8, 10, 12, 15, 17, 19, 29);
        a(iArr, iArr2, 8, 8, 10, 12, 14, 17, 19, 21, 31);
        a(iArr, iArr2, 10, 10, 12, 14, 16, 19, 21, 23, 1);
        a(iArr, iArr2, 12, 12, 14, 16, 18, 21, 23, 25, 3);
        a(iArr, iArr2, 14, 14, 16, 18, 20, 23, 25, 27, 5);
        a(iArr, iArr2, 16, 16, 18, 20, 22, 25, 27, 29, 7);
        a(iArr, iArr2, 18, 18, 20, 22, 24, 27, 29, 31, 9);
        a(iArr, iArr2, 20, 20, 22, 24, 26, 29, 31, 1, 11);
        a(iArr, iArr2, 22, 22, 24, 26, 28, 31, 1, 3, 13);
        a(iArr, iArr2, 24, 24, 26, 28, 30, 1, 3, 5, 15);
        a(iArr, iArr2, 26, 26, 28, 30, 0, 3, 5, 7, 17);
        a(iArr, iArr2, 28, 28, 30, 0, 2, 5, 7, 9, 19);
        a(iArr, iArr2, 30, 30, 0, 2, 4, 7, 9, 11, 21);
        a(iArr, iArr2, 1, 9, 11, 13, 23, 0, 2, 4, 6);
        a(iArr, iArr2, 3, 11, 13, 15, 25, 2, 4, 6, 8);
        a(iArr, iArr2, 5, 13, 15, 17, 27, 4, 6, 8, 10);
        a(iArr, iArr2, 7, 15, 17, 19, 29, 6, 8, 10, 12);
        a(iArr, iArr2, 9, 17, 19, 21, 31, 8, 10, 12, 14);
        a(iArr, iArr2, 11, 19, 21, 23, 1, 10, 12, 14, 16);
        a(iArr, iArr2, 13, 21, 23, 25, 3, 12, 14, 16, 18);
        a(iArr, iArr2, 15, 23, 25, 27, 5, 14, 16, 18, 20);
        a(iArr, iArr2, 17, 25, 27, 29, 7, 16, 18, 20, 22);
        a(iArr, iArr2, 19, 27, 29, 31, 9, 18, 20, 22, 24);
        a(iArr, iArr2, 21, 29, 31, 1, 11, 20, 22, 24, 26);
        a(iArr, iArr2, 23, 31, 1, 3, 13, 22, 24, 26, 28);
        a(iArr, iArr2, 25, 1, 3, 5, 15, 24, 26, 28, 30);
        a(iArr, iArr2, 27, 3, 5, 7, 17, 26, 28, 30, 0);
        a(iArr, iArr2, 29, 5, 7, 9, 19, 28, 30, 0, 2);
        a(iArr, iArr2, 31, 7, 9, 11, 21, 30, 0, 2, 4);
    }

    private static void b(int[] iArr, int i) {
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
        iArr[16] = ~iArr[16];
        iArr[17] = iArr[17] ^ (i ^ 128);
        iArr[18] = ~iArr[18];
        iArr[19] = iArr[19] ^ (i ^ 144);
        iArr[20] = ~iArr[20];
        iArr[21] = iArr[21] ^ (i ^ 160);
        iArr[22] = ~iArr[22];
        iArr[23] = iArr[23] ^ (i ^ FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_GET_DESC_STRING);
        iArr[24] = ~iArr[24];
        iArr[25] = iArr[25] ^ (i ^ 192);
        iArr[26] = ~iArr[26];
        iArr[27] = iArr[27] ^ (i ^ 208);
        iArr[28] = ~iArr[28];
        iArr[29] = iArr[29] ^ (i ^ DataTypeTag.CONSTRUCTIVE);
        iArr[30] = ~iArr[30];
        iArr[31] = (i ^ 240) ^ iArr[31];
    }

    private static void b(int[] iArr, int[] iArr2) {
        a(iArr, iArr2, 0, 2, 6, 10, 22, 1, 5, 9, 13);
        a(iArr, iArr2, 1, 1, 5, 9, 13, 2, 6, 10, 22);
        a(iArr, iArr2, 2, 4, 8, 12, 24, 3, 7, 11, 15);
        a(iArr, iArr2, 3, 3, 7, 11, 15, 4, 8, 12, 24);
        a(iArr, iArr2, 4, 6, 10, 14, 26, 5, 9, 13, 17);
        a(iArr, iArr2, 5, 5, 9, 13, 17, 6, 10, 14, 26);
        a(iArr, iArr2, 6, 8, 12, 16, 28, 7, 11, 15, 19);
        a(iArr, iArr2, 7, 7, 11, 15, 19, 8, 12, 16, 28);
        a(iArr, iArr2, 8, 10, 14, 18, 30, 9, 13, 17, 21);
        a(iArr, iArr2, 9, 9, 13, 17, 21, 10, 14, 18, 30);
        a(iArr, iArr2, 10, 12, 16, 20, 0, 11, 15, 19, 23);
        a(iArr, iArr2, 11, 11, 15, 19, 23, 12, 16, 20, 0);
        a(iArr, iArr2, 12, 14, 18, 22, 2, 13, 17, 21, 25);
        a(iArr, iArr2, 13, 13, 17, 21, 25, 14, 18, 22, 2);
        a(iArr, iArr2, 14, 16, 20, 24, 4, 15, 19, 23, 27);
        a(iArr, iArr2, 15, 15, 19, 23, 27, 16, 20, 24, 4);
        a(iArr, iArr2, 16, 18, 22, 26, 6, 17, 21, 25, 29);
        a(iArr, iArr2, 17, 17, 21, 25, 29, 18, 22, 26, 6);
        a(iArr, iArr2, 18, 20, 24, 28, 8, 19, 23, 27, 31);
        a(iArr, iArr2, 19, 19, 23, 27, 31, 20, 24, 28, 8);
        a(iArr, iArr2, 20, 22, 26, 30, 10, 21, 25, 29, 1);
        a(iArr, iArr2, 21, 21, 25, 29, 1, 22, 26, 30, 10);
        a(iArr, iArr2, 22, 24, 28, 0, 12, 23, 27, 31, 3);
        a(iArr, iArr2, 23, 23, 27, 31, 3, 24, 28, 0, 12);
        a(iArr, iArr2, 24, 26, 30, 2, 14, 25, 29, 1, 5);
        a(iArr, iArr2, 25, 25, 29, 1, 5, 26, 30, 2, 14);
        a(iArr, iArr2, 26, 28, 0, 4, 16, 27, 31, 3, 7);
        a(iArr, iArr2, 27, 27, 31, 3, 7, 28, 0, 4, 16);
        a(iArr, iArr2, 28, 30, 2, 6, 18, 29, 1, 5, 9);
        a(iArr, iArr2, 29, 29, 1, 5, 9, 30, 2, 6, 18);
        a(iArr, iArr2, 30, 0, 4, 8, 20, 31, 3, 7, 11);
        a(iArr, iArr2, 31, 31, 3, 7, 11, 0, 4, 8, 20);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    public void a(byte[] bArr, int i) {
        CryptoUtils.squashBytesToInts(bArr, i, this.l, 0, 32);
        for (int i2 = 0; i2 < 32; i2++) {
            this.n[i2] = this.i[i2] ^ this.l[i2];
        }
        b(this.l, -1);
        b(this.l, this.k);
        b(this.k, -2);
        b(this.k, this.l);
        b(this.l, -3);
        b(this.l, this.k);
        b(this.k, -4);
        b(this.k, this.l);
        b(this.l, -5);
        b(this.l, this.k);
        b(this.k, -6);
        b(this.k, this.l);
        b(this.l, -7);
        b(this.l, this.k);
        b(this.k, -8);
        b(this.k, this.l);
        b(this.l, -9);
        b(this.l, this.k);
        b(this.k, -10);
        b(this.k, this.l);
        b(this.l, -11);
        b(this.l, this.k);
        b(this.k, -12);
        b(this.k, this.l);
        b(this.l, -13);
        b(this.l, this.k);
        b(this.k, -14);
        b(this.k, this.m);
        a(this.n, 0);
        a(this.n, this.k);
        a(this.k, 16777216);
        a(this.k, this.l);
        a(this.l, 33554432);
        a(this.l, this.k);
        a(this.k, 50331648);
        a(this.k, this.l);
        a(this.l, 67108864);
        a(this.l, this.k);
        a(this.k, 83886080);
        a(this.k, this.l);
        a(this.l, 100663296);
        a(this.l, this.k);
        a(this.k, 117440512);
        a(this.k, this.l);
        a(this.l, 134217728);
        a(this.l, this.k);
        a(this.k, 150994944);
        a(this.k, this.l);
        a(this.l, 167772160);
        a(this.l, this.k);
        a(this.k, 184549376);
        a(this.k, this.l);
        a(this.l, 201326592);
        a(this.l, this.k);
        a(this.k, 218103808);
        a(this.k, this.n);
        for (int i3 = 0; i3 < 32; i3++) {
            int[] iArr = this.i;
            iArr[i3] = iArr[i3] ^ (this.n[i3] ^ this.m[i3]);
        }
    }

    @Override // iaik.security.md.AbstractMessageDigest
    public void b(byte[] bArr, int i) {
        int length = this.i.length;
        int[] iArr = new int[length];
        System.arraycopy(this.i, 0, iArr, 0, length);
        a(iArr, 0);
        a(iArr, this.k);
        a(this.k, 16777216);
        a(this.k, this.l);
        a(this.l, 33554432);
        a(this.l, this.k);
        a(this.k, 50331648);
        a(this.k, this.l);
        a(this.l, 67108864);
        a(this.l, this.k);
        a(this.k, 83886080);
        a(this.k, this.l);
        a(this.l, 100663296);
        a(this.l, this.k);
        a(this.k, 117440512);
        a(this.k, this.l);
        a(this.l, 134217728);
        a(this.l, this.k);
        a(this.k, 150994944);
        a(this.k, this.l);
        a(this.l, 167772160);
        a(this.l, this.k);
        a(this.k, 184549376);
        a(this.k, this.l);
        a(this.l, 201326592);
        a(this.l, this.k);
        a(this.k, 218103808);
        a(this.k, iArr);
        for (int i2 = 0; i2 < 32; i2++) {
            int[] iArr2 = this.i;
            iArr2[i2] = iArr2[i2] ^ iArr[i2];
        }
        CryptoUtils.spreadIntsToBytes(this.i, this.i.length - (this.j.length >>> 2), this.j, 0, this.j.length >>> 2);
        System.arraycopy(this.j, this.j.length - this.c, bArr, i, this.c);
    }

    @Override // iaik.security.md.AbstractC0038g, iaik.security.md.AbstractC0037f, iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        C0039h c0039h = (C0039h) super.clone();
        c0039h.k = (int[]) this.k.clone();
        c0039h.l = (int[]) this.l.clone();
        c0039h.m = (int[]) this.m.clone();
        c0039h.n = (int[]) this.n.clone();
        return c0039h;
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
