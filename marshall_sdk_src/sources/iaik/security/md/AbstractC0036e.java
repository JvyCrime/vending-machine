package iaik.security.md;

import iaik.utils.CryptoUtils;

/* JADX INFO: renamed from: iaik.security.md.e, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
abstract class AbstractC0036e extends AbstractC0034c {
    private static final long[] j = {2611923443488327891L, 1376283091369227076L, -6626703657320631856L, 589684135938649225L, 4983270260364809079L, -4732044268327596948L, -4563226453097033507L, 4577018097722394903L, -7919907764393346277L, -3372901835766516308L, 3458046377305235383L, -5124621466747896170L, -5008970055469465703L, 2639559389850201335L, 577009281997405206L, 7163292796296056425L};
    private long[] k;
    private byte[] l;
    private long m;
    private long n;
    private boolean o;
    private final long[] p;
    private long[] q;
    private long[] r;

    public AbstractC0036e(int i, long[] jArr, byte b) {
        super(i, 128, b, i >> 3);
        this.k = new long[8];
        this.l = new byte[17];
        this.o = true;
        this.q = new long[16];
        this.r = new long[16];
        this.p = jArr;
        engineReset();
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a() {
        long j2 = ((this.f % 128) + 128) % 128;
        long j3 = this.m + (j2 << 3);
        this.m = j3;
        long j4 = this.n;
        long j5 = 256 - j2;
        byte[] bArr = this.l;
        int length = ((int) (j5 - ((long) bArr.length))) % 128;
        if (length == 0) {
            bArr = (byte[]) bArr.clone();
            bArr[0] = (byte) (this.i | (-128));
        } else if (length >= 111) {
            this.m = j3 - 1024;
            engineUpdate(a, 0, length);
            this.o = false;
        } else {
            this.m = j3 - 1024;
            engineUpdate(a, 0, length);
        }
        CryptoUtils.spreadLongsToBytes(new long[]{j4, j3}, 0, bArr, 1, 2);
        this.m = j3 - 1024;
        engineUpdate(bArr, 0, bArr.length);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a(byte[] bArr, int i) {
        long[] jArr = this.r;
        char c = 0;
        CryptoUtils.squashBytesToLongs(bArr, i, jArr, 0, jArr.length);
        long[] jArr2 = this.q;
        long[] jArr3 = this.k;
        jArr2[0] = jArr3[0];
        jArr2[1] = jArr3[1];
        jArr2[2] = jArr3[2];
        jArr2[3] = jArr3[3];
        jArr2[4] = jArr3[4];
        jArr2[5] = jArr3[5];
        jArr2[6] = jArr3[6];
        jArr2[7] = jArr3[7];
        jArr2[8] = 2611923443488327891L;
        jArr2[9] = 1376283091369227076L;
        jArr2[10] = -6626703657320631856L;
        jArr2[11] = 589684135938649225L;
        jArr2[12] = 4983270260364809079L;
        jArr2[13] = -4732044268327596948L;
        jArr2[14] = -4563226453097033507L;
        jArr2[15] = 4577018097722394903L;
        if (this.o) {
            long j2 = this.m;
            if (j2 == -1024) {
                this.n++;
            }
            long j3 = j2 + 1024;
            this.m = j3;
            jArr2[12] = jArr2[12] ^ j3;
            jArr2[13] = jArr2[13] ^ j3;
            long j4 = jArr2[14];
            long j5 = this.n;
            jArr2[14] = j4 ^ j5;
            jArr2[15] = jArr2[15] ^ j5;
        }
        int i2 = 0;
        while (i2 < 256) {
            long[] jArr4 = this.q;
            long j6 = jArr4[c];
            long j7 = jArr4[4];
            long j8 = this.r[g[i2]];
            long[] jArr5 = j;
            int i3 = i2 + 1;
            jArr4[c] = j6 + j7 + (j8 ^ jArr5[g[i3]]);
            long[] jArr6 = this.q;
            jArr6[12] = ((jArr6[12] ^ jArr6[c]) << 32) | ((jArr6[12] ^ jArr6[c]) >>> 32);
            jArr6[8] = jArr6[8] + jArr6[12];
            jArr6[4] = ((jArr6[4] ^ jArr6[8]) << 39) | ((jArr6[4] ^ jArr6[8]) >>> 25);
            jArr6[c] = jArr6[c] + jArr6[4] + (this.r[g[i3]] ^ jArr5[g[i2]]);
            long[] jArr7 = this.q;
            jArr7[12] = ((jArr7[12] ^ jArr7[c]) << 48) | ((jArr7[12] ^ jArr7[c]) >>> 16);
            jArr7[8] = jArr7[8] + jArr7[12];
            jArr7[4] = ((jArr7[4] ^ jArr7[8]) << 53) | ((jArr7[4] ^ jArr7[8]) >>> 11);
            int i4 = i2 + 2;
            int i5 = i2 + 3;
            jArr7[1] = jArr7[1] + jArr7[5] + (this.r[g[i4]] ^ jArr5[g[i5]]);
            long[] jArr8 = this.q;
            jArr8[13] = ((jArr8[13] ^ jArr8[1]) << 32) | ((jArr8[13] ^ jArr8[1]) >>> 32);
            jArr8[9] = jArr8[9] + jArr8[13];
            jArr8[5] = ((jArr8[5] ^ jArr8[9]) << 39) | ((jArr8[5] ^ jArr8[9]) >>> 25);
            jArr8[1] = jArr8[1] + jArr8[5] + (this.r[g[i5]] ^ jArr5[g[i4]]);
            long[] jArr9 = this.q;
            jArr9[13] = ((jArr9[13] ^ jArr9[1]) << 48) | ((jArr9[13] ^ jArr9[1]) >>> 16);
            jArr9[9] = jArr9[9] + jArr9[13];
            jArr9[5] = ((jArr9[5] ^ jArr9[9]) << 53) | ((jArr9[5] ^ jArr9[9]) >>> 11);
            int i6 = i2 + 4;
            int i7 = i2 + 5;
            jArr9[2] = jArr9[2] + jArr9[6] + (this.r[g[i6]] ^ jArr5[g[i7]]);
            long[] jArr10 = this.q;
            jArr10[14] = ((jArr10[14] ^ jArr10[2]) << 32) | ((jArr10[14] ^ jArr10[2]) >>> 32);
            jArr10[10] = jArr10[10] + jArr10[14];
            jArr10[6] = ((jArr10[6] ^ jArr10[10]) << 39) | ((jArr10[6] ^ jArr10[10]) >>> 25);
            jArr10[2] = jArr10[2] + jArr10[6] + (this.r[g[i7]] ^ jArr5[g[i6]]);
            long[] jArr11 = this.q;
            jArr11[14] = ((jArr11[14] ^ jArr11[2]) << 48) | ((jArr11[14] ^ jArr11[2]) >>> 16);
            jArr11[10] = jArr11[10] + jArr11[14];
            jArr11[6] = ((jArr11[6] ^ jArr11[10]) << 53) | ((jArr11[6] ^ jArr11[10]) >>> 11);
            int i8 = i2 + 6;
            int i9 = i2 + 7;
            jArr11[3] = jArr11[3] + jArr11[7] + (this.r[g[i8]] ^ jArr5[g[i9]]);
            long[] jArr12 = this.q;
            jArr12[15] = ((jArr12[15] ^ jArr12[3]) << 32) | ((jArr12[15] ^ jArr12[3]) >>> 32);
            jArr12[11] = jArr12[11] + jArr12[15];
            jArr12[7] = ((jArr12[7] ^ jArr12[11]) << 39) | ((jArr12[7] ^ jArr12[11]) >>> 25);
            jArr12[3] = jArr12[3] + jArr12[7] + (this.r[g[i9]] ^ jArr5[g[i8]]);
            long[] jArr13 = this.q;
            jArr13[15] = ((jArr13[15] ^ jArr13[3]) << 48) | ((jArr13[15] ^ jArr13[3]) >>> 16);
            jArr13[11] = jArr13[11] + jArr13[15];
            jArr13[7] = ((jArr13[7] ^ jArr13[11]) << 53) | ((jArr13[7] ^ jArr13[11]) >>> 11);
            int i10 = i2 + 14;
            int i11 = i2 + 15;
            jArr13[3] = jArr13[3] + jArr13[4] + (this.r[g[i10]] ^ jArr5[g[i11]]);
            long[] jArr14 = this.q;
            jArr14[14] = ((jArr14[14] ^ jArr14[3]) << 32) | ((jArr14[14] ^ jArr14[3]) >>> 32);
            jArr14[9] = jArr14[9] + jArr14[14];
            jArr14[4] = ((jArr14[4] ^ jArr14[9]) << 39) | ((jArr14[4] ^ jArr14[9]) >>> 25);
            jArr14[3] = jArr14[3] + jArr14[4] + (this.r[g[i11]] ^ jArr5[g[i10]]);
            long[] jArr15 = this.q;
            jArr15[14] = ((jArr15[14] ^ jArr15[3]) << 48) | ((jArr15[14] ^ jArr15[3]) >>> 16);
            jArr15[9] = jArr15[9] + jArr15[14];
            jArr15[4] = ((jArr15[4] ^ jArr15[9]) << 53) | ((jArr15[4] ^ jArr15[9]) >>> 11);
            int i12 = i2 + 12;
            int i13 = i2 + 13;
            jArr15[2] = jArr15[2] + jArr15[7] + (this.r[g[i12]] ^ jArr5[g[i13]]);
            long[] jArr16 = this.q;
            jArr16[13] = ((jArr16[13] ^ jArr16[2]) << 32) | ((jArr16[13] ^ jArr16[2]) >>> 32);
            jArr16[8] = jArr16[8] + jArr16[13];
            jArr16[7] = ((jArr16[7] ^ jArr16[8]) << 39) | ((jArr16[7] ^ jArr16[8]) >>> 25);
            jArr16[2] = jArr16[2] + jArr16[7] + (this.r[g[i13]] ^ jArr5[g[i12]]);
            long[] jArr17 = this.q;
            jArr17[13] = ((jArr17[13] ^ jArr17[2]) << 48) | ((jArr17[13] ^ jArr17[2]) >>> 16);
            jArr17[8] = jArr17[8] + jArr17[13];
            jArr17[7] = ((jArr17[7] ^ jArr17[8]) << 53) | ((jArr17[7] ^ jArr17[8]) >>> 11);
            int i14 = i2 + 8;
            int i15 = i2 + 9;
            jArr17[0] = jArr17[0] + jArr17[5] + (this.r[g[i14]] ^ jArr5[g[i15]]);
            long[] jArr18 = this.q;
            jArr18[15] = ((jArr18[15] ^ jArr18[0]) << 32) | ((jArr18[15] ^ jArr18[0]) >>> 32);
            jArr18[10] = jArr18[10] + jArr18[15];
            jArr18[5] = ((jArr18[5] ^ jArr18[10]) << 39) | ((jArr18[5] ^ jArr18[10]) >>> 25);
            jArr18[0] = jArr18[0] + jArr18[5] + (this.r[g[i15]] ^ jArr5[g[i14]]);
            long[] jArr19 = this.q;
            jArr19[15] = ((jArr19[15] ^ jArr19[0]) << 48) | ((jArr19[15] ^ jArr19[0]) >>> 16);
            jArr19[10] = jArr19[10] + jArr19[15];
            jArr19[5] = ((jArr19[5] ^ jArr19[10]) << 53) | ((jArr19[5] ^ jArr19[10]) >>> 11);
            int i16 = i2 + 10;
            int i17 = i2 + 11;
            jArr19[1] = jArr19[1] + jArr19[6] + (this.r[g[i16]] ^ jArr5[g[i17]]);
            long[] jArr20 = this.q;
            jArr20[12] = ((jArr20[12] ^ jArr20[1]) << 32) | ((jArr20[12] ^ jArr20[1]) >>> 32);
            jArr20[11] = jArr20[11] + jArr20[12];
            jArr20[6] = ((jArr20[6] ^ jArr20[11]) << 39) | ((jArr20[6] ^ jArr20[11]) >>> 25);
            jArr20[1] = jArr20[1] + jArr20[6] + (this.r[g[i17]] ^ jArr5[g[i16]]);
            long[] jArr21 = this.q;
            jArr21[12] = ((jArr21[12] ^ jArr21[1]) << 48) | ((jArr21[12] ^ jArr21[1]) >>> 16);
            jArr21[11] = jArr21[11] + jArr21[12];
            jArr21[6] = ((jArr21[6] ^ jArr21[11]) << 53) | ((jArr21[6] ^ jArr21[11]) >>> 11);
            i2 += 16;
            c = 0;
        }
        long[] jArr22 = this.k;
        long j9 = jArr22[0];
        long[] jArr23 = this.q;
        jArr22[0] = j9 ^ (jArr23[0] ^ jArr23[8]);
        jArr22[1] = jArr22[1] ^ (jArr23[1] ^ jArr23[9]);
        jArr22[2] = jArr22[2] ^ (jArr23[2] ^ jArr23[10]);
        jArr22[3] = jArr22[3] ^ (jArr23[3] ^ jArr23[11]);
        jArr22[4] = jArr22[4] ^ (jArr23[4] ^ jArr23[12]);
        jArr22[5] = jArr22[5] ^ (jArr23[5] ^ jArr23[13]);
        jArr22[6] = jArr22[6] ^ (jArr23[6] ^ jArr23[14]);
        jArr22[7] = jArr22[7] ^ (jArr23[15] ^ jArr23[7]);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void b(byte[] bArr, int i) {
        CryptoUtils.spreadLongsToBytes(this.k, 0, bArr, i, this.h);
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        AbstractC0036e abstractC0036e = (AbstractC0036e) super.clone();
        abstractC0036e.k = (long[]) this.k.clone();
        abstractC0036e.q = (long[]) this.q.clone();
        abstractC0036e.r = (long[]) this.r.clone();
        abstractC0036e.l = (byte[]) this.l.clone();
        return abstractC0036e;
    }

    @Override // iaik.security.md.AbstractC0034c, iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    protected void engineReset() {
        super.engineReset();
        long[] jArr = this.p;
        System.arraycopy(jArr, 0, this.k, 0, jArr.length);
        this.o = true;
        this.n = 0L;
        this.m = 0L;
        CryptoUtils.zeroBlock(this.l);
        CryptoUtils.zeroBlock(this.q);
        CryptoUtils.zeroBlock(this.r);
        this.l[0] = this.i;
    }
}
