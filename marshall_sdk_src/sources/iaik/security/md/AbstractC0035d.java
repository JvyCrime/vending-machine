package iaik.security.md;

import iaik.utils.CryptoUtils;

/* JADX INFO: renamed from: iaik.security.md.d, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
abstract class AbstractC0035d extends AbstractC0034c {
    private static final int[] j = {608135816, -2052912941, 320440878, 57701188, -1542899678, 698298832, 137296536, -330404727, 1160258022, 953160567, -1101764913, 887688300, -1062458953, -914599715, 1065670069, -1253635817};
    private int[] k;
    private byte[] l;
    private int m;
    private int n;
    private boolean o;
    private final int[] p;
    private int[] q;
    private int[] r;

    public AbstractC0035d(int i, int[] iArr, byte b) {
        super(i, 64, b, i >>> 2);
        this.k = new int[8];
        this.l = new byte[9];
        this.o = true;
        this.q = new int[16];
        this.r = new int[16];
        this.p = iArr;
        engineReset();
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a() {
        int i = ((int) ((this.f % 64) + 64)) % 64;
        int i2 = this.m + (i << 3);
        this.m = i2;
        int i3 = this.n;
        byte[] bArr = this.l;
        int length = ((128 - i) - bArr.length) % 64;
        if (length == 0) {
            bArr = (byte[]) bArr.clone();
            bArr[0] = (byte) (this.i | (-128));
        } else if (length >= 55) {
            this.m = i2 - 512;
            engineUpdate(a, 0, length);
            this.o = false;
        } else {
            this.m = i2 - 512;
            engineUpdate(a, 0, length);
        }
        CryptoUtils.spreadIntsToBytes(new int[]{i3, i2}, 0, bArr, 1, 2);
        this.m = i2 - 512;
        engineUpdate(bArr, 0, bArr.length);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a(byte[] bArr, int i) {
        int[] iArr = this.r;
        CryptoUtils.squashBytesToInts(bArr, i, iArr, 0, iArr.length);
        int[] iArr2 = this.q;
        int[] iArr3 = this.k;
        iArr2[0] = iArr3[0];
        iArr2[1] = iArr3[1];
        iArr2[2] = iArr3[2];
        iArr2[3] = iArr3[3];
        iArr2[4] = iArr3[4];
        iArr2[5] = iArr3[5];
        iArr2[6] = iArr3[6];
        iArr2[7] = iArr3[7];
        iArr2[8] = 608135816;
        iArr2[9] = -2052912941;
        iArr2[10] = 320440878;
        iArr2[11] = 57701188;
        iArr2[12] = -1542899678;
        iArr2[13] = 698298832;
        iArr2[14] = 137296536;
        iArr2[15] = -330404727;
        if (this.o) {
            int i2 = this.m;
            if (i2 == -512) {
                this.n++;
            }
            int i3 = i2 + 512;
            this.m = i3;
            iArr2[12] = iArr2[12] ^ i3;
            iArr2[13] = i3 ^ iArr2[13];
            int i4 = iArr2[14];
            int i5 = this.n;
            iArr2[14] = i4 ^ i5;
            iArr2[15] = iArr2[15] ^ i5;
        }
        for (int i6 = 0; i6 < 224; i6 += 16) {
            int[] iArr4 = this.q;
            int i7 = iArr4[0];
            int i8 = iArr4[4];
            int i9 = this.r[g[i6]];
            int[] iArr5 = j;
            int i10 = i6 + 1;
            iArr4[0] = i7 + i8 + (i9 ^ iArr5[g[i10]]);
            int[] iArr6 = this.q;
            iArr6[12] = ((iArr6[12] ^ iArr6[0]) << 16) | ((iArr6[12] ^ iArr6[0]) >>> 16);
            iArr6[8] = iArr6[8] + iArr6[12];
            iArr6[4] = ((iArr6[4] ^ iArr6[8]) << 20) | ((iArr6[4] ^ iArr6[8]) >>> 12);
            iArr6[0] = iArr6[0] + iArr6[4] + (this.r[g[i10]] ^ iArr5[g[i6]]);
            int[] iArr7 = this.q;
            iArr7[12] = ((iArr7[12] ^ iArr7[0]) << 24) | ((iArr7[12] ^ iArr7[0]) >>> 8);
            iArr7[8] = iArr7[8] + iArr7[12];
            iArr7[4] = ((iArr7[4] ^ iArr7[8]) << 25) | ((iArr7[4] ^ iArr7[8]) >>> 7);
            int i11 = i6 + 2;
            int i12 = i6 + 3;
            iArr7[1] = iArr7[1] + iArr7[5] + (this.r[g[i11]] ^ iArr5[g[i12]]);
            int[] iArr8 = this.q;
            iArr8[13] = ((iArr8[13] ^ iArr8[1]) << 16) | ((iArr8[13] ^ iArr8[1]) >>> 16);
            iArr8[9] = iArr8[9] + iArr8[13];
            iArr8[5] = ((iArr8[5] ^ iArr8[9]) << 20) | ((iArr8[5] ^ iArr8[9]) >>> 12);
            iArr8[1] = iArr8[1] + iArr8[5] + (this.r[g[i12]] ^ iArr5[g[i11]]);
            int[] iArr9 = this.q;
            iArr9[13] = ((iArr9[13] ^ iArr9[1]) << 24) | ((iArr9[13] ^ iArr9[1]) >>> 8);
            iArr9[9] = iArr9[9] + iArr9[13];
            iArr9[5] = ((iArr9[5] ^ iArr9[9]) << 25) | ((iArr9[5] ^ iArr9[9]) >>> 7);
            int i13 = i6 + 4;
            int i14 = i6 + 5;
            iArr9[2] = iArr9[2] + iArr9[6] + (this.r[g[i13]] ^ iArr5[g[i14]]);
            int[] iArr10 = this.q;
            iArr10[14] = ((iArr10[14] ^ iArr10[2]) << 16) | ((iArr10[14] ^ iArr10[2]) >>> 16);
            iArr10[10] = iArr10[10] + iArr10[14];
            iArr10[6] = ((iArr10[6] ^ iArr10[10]) >>> 12) | ((iArr10[6] ^ iArr10[10]) << 20);
            iArr10[2] = iArr10[2] + iArr10[6] + (this.r[g[i14]] ^ iArr5[g[i13]]);
            int[] iArr11 = this.q;
            iArr11[14] = ((iArr11[14] ^ iArr11[2]) << 24) | ((iArr11[14] ^ iArr11[2]) >>> 8);
            iArr11[10] = iArr11[10] + iArr11[14];
            iArr11[6] = ((iArr11[6] ^ iArr11[10]) >>> 7) | ((iArr11[6] ^ iArr11[10]) << 25);
            int i15 = i6 + 6;
            int i16 = i6 + 7;
            iArr11[3] = iArr11[3] + iArr11[7] + (this.r[g[i15]] ^ iArr5[g[i16]]);
            int[] iArr12 = this.q;
            iArr12[15] = ((iArr12[15] ^ iArr12[3]) >>> 16) | ((iArr12[15] ^ iArr12[3]) << 16);
            iArr12[11] = iArr12[11] + iArr12[15];
            iArr12[7] = ((iArr12[7] ^ iArr12[11]) >>> 12) | ((iArr12[7] ^ iArr12[11]) << 20);
            iArr12[3] = iArr12[3] + iArr12[7] + (this.r[g[i16]] ^ iArr5[g[i15]]);
            int[] iArr13 = this.q;
            iArr13[15] = ((iArr13[15] ^ iArr13[3]) >>> 8) | ((iArr13[15] ^ iArr13[3]) << 24);
            iArr13[11] = iArr13[11] + iArr13[15];
            iArr13[7] = ((iArr13[7] ^ iArr13[11]) >>> 7) | ((iArr13[7] ^ iArr13[11]) << 25);
            int i17 = i6 + 14;
            int i18 = i6 + 15;
            iArr13[3] = iArr13[3] + iArr13[4] + (this.r[g[i17]] ^ iArr5[g[i18]]);
            int[] iArr14 = this.q;
            iArr14[14] = ((iArr14[14] ^ iArr14[3]) >>> 16) | ((iArr14[14] ^ iArr14[3]) << 16);
            iArr14[9] = iArr14[9] + iArr14[14];
            iArr14[4] = ((iArr14[4] ^ iArr14[9]) << 20) | ((iArr14[4] ^ iArr14[9]) >>> 12);
            iArr14[3] = iArr14[3] + iArr14[4] + (this.r[g[i18]] ^ iArr5[g[i17]]);
            int[] iArr15 = this.q;
            iArr15[14] = ((iArr15[14] ^ iArr15[3]) >>> 8) | ((iArr15[14] ^ iArr15[3]) << 24);
            iArr15[9] = iArr15[9] + iArr15[14];
            iArr15[4] = ((iArr15[4] ^ iArr15[9]) << 25) | ((iArr15[4] ^ iArr15[9]) >>> 7);
            int i19 = i6 + 12;
            int i20 = i6 + 13;
            iArr15[2] = iArr15[2] + iArr15[7] + (this.r[g[i19]] ^ iArr5[g[i20]]);
            int[] iArr16 = this.q;
            iArr16[13] = ((iArr16[13] ^ iArr16[2]) << 16) | ((iArr16[13] ^ iArr16[2]) >>> 16);
            iArr16[8] = iArr16[8] + iArr16[13];
            iArr16[7] = ((iArr16[7] ^ iArr16[8]) << 20) | ((iArr16[7] ^ iArr16[8]) >>> 12);
            iArr16[2] = iArr16[2] + iArr16[7] + (this.r[g[i20]] ^ iArr5[g[i19]]);
            int[] iArr17 = this.q;
            iArr17[13] = ((iArr17[13] ^ iArr17[2]) << 24) | ((iArr17[13] ^ iArr17[2]) >>> 8);
            iArr17[8] = iArr17[8] + iArr17[13];
            iArr17[7] = ((iArr17[7] ^ iArr17[8]) << 25) | ((iArr17[7] ^ iArr17[8]) >>> 7);
            int i21 = i6 + 8;
            int i22 = i6 + 9;
            iArr17[0] = iArr17[0] + iArr17[5] + (this.r[g[i21]] ^ iArr5[g[i22]]);
            int[] iArr18 = this.q;
            iArr18[15] = ((iArr18[15] ^ iArr18[0]) << 16) | ((iArr18[15] ^ iArr18[0]) >>> 16);
            iArr18[10] = iArr18[10] + iArr18[15];
            iArr18[5] = ((iArr18[5] ^ iArr18[10]) >>> 12) | ((iArr18[5] ^ iArr18[10]) << 20);
            iArr18[0] = iArr18[0] + iArr18[5] + (this.r[g[i22]] ^ iArr5[g[i21]]);
            int[] iArr19 = this.q;
            iArr19[15] = ((iArr19[15] ^ iArr19[0]) << 24) | ((iArr19[15] ^ iArr19[0]) >>> 8);
            iArr19[10] = iArr19[10] + iArr19[15];
            iArr19[5] = ((iArr19[5] ^ iArr19[10]) >>> 7) | ((iArr19[5] ^ iArr19[10]) << 25);
            int i23 = i6 + 10;
            int i24 = i6 + 11;
            iArr19[1] = iArr19[1] + iArr19[6] + (this.r[g[i23]] ^ iArr5[g[i24]]);
            int[] iArr20 = this.q;
            iArr20[12] = ((iArr20[12] ^ iArr20[1]) << 16) | ((iArr20[12] ^ iArr20[1]) >>> 16);
            iArr20[11] = iArr20[11] + iArr20[12];
            iArr20[6] = ((iArr20[6] ^ iArr20[11]) >>> 12) | ((iArr20[6] ^ iArr20[11]) << 20);
            iArr20[1] = iArr20[1] + iArr20[6] + (this.r[g[i24]] ^ iArr5[g[i23]]);
            int[] iArr21 = this.q;
            iArr21[12] = ((iArr21[12] ^ iArr21[1]) << 24) | ((iArr21[12] ^ iArr21[1]) >>> 8);
            iArr21[11] = iArr21[11] + iArr21[12];
            iArr21[6] = ((iArr21[6] ^ iArr21[11]) >>> 7) | ((iArr21[6] ^ iArr21[11]) << 25);
        }
        int[] iArr22 = this.k;
        int i25 = iArr22[0];
        int[] iArr23 = this.q;
        iArr22[0] = i25 ^ (iArr23[8] ^ iArr23[0]);
        iArr22[1] = iArr22[1] ^ (iArr23[1] ^ iArr23[9]);
        iArr22[2] = iArr22[2] ^ (iArr23[2] ^ iArr23[10]);
        iArr22[3] = iArr22[3] ^ (iArr23[3] ^ iArr23[11]);
        iArr22[4] = iArr22[4] ^ (iArr23[4] ^ iArr23[12]);
        iArr22[5] = iArr22[5] ^ (iArr23[5] ^ iArr23[13]);
        iArr22[6] = iArr22[6] ^ (iArr23[6] ^ iArr23[14]);
        iArr22[7] = iArr22[7] ^ (iArr23[7] ^ iArr23[15]);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void b(byte[] bArr, int i) {
        CryptoUtils.spreadIntsToBytes(this.k, 0, bArr, i, this.h);
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        AbstractC0035d abstractC0035d = (AbstractC0035d) super.clone();
        abstractC0035d.k = (int[]) this.k.clone();
        abstractC0035d.q = (int[]) this.q.clone();
        abstractC0035d.r = (int[]) this.r.clone();
        abstractC0035d.l = (byte[]) this.l.clone();
        return abstractC0035d;
    }

    @Override // iaik.security.md.AbstractC0034c, iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    protected void engineReset() {
        super.engineReset();
        int[] iArr = this.p;
        System.arraycopy(iArr, 0, this.k, 0, iArr.length);
        this.o = true;
        this.n = 0;
        this.m = 0;
        CryptoUtils.zeroBlock(this.l);
        CryptoUtils.zeroBlock(this.q);
        CryptoUtils.zeroBlock(this.r);
        this.l[0] = this.i;
    }
}
