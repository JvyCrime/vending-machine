package iaik.security.md;

import iaik.security.ssl.SSLContext;
import iaik.utils.CryptoUtils;

/* JADX INFO: renamed from: iaik.security.md.l, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0043l extends AbstractC0041j {
    private long[] l;
    private long[] m;
    private long[] n;
    private long[] o;

    public C0043l(int i) {
        super(i, 8);
        this.l = new long[8];
        this.m = new long[8];
        this.n = new long[8];
        this.o = new long[8];
        engineReset();
    }

    private static void a(long[] jArr, long j) {
        jArr[0] = jArr[0] ^ j;
        jArr[1] = jArr[1] ^ (1152921504606846976L ^ j);
        jArr[2] = jArr[2] ^ (2305843009213693952L ^ j);
        jArr[3] = jArr[3] ^ (3458764513820540928L ^ j);
        jArr[4] = jArr[4] ^ (4611686018427387904L ^ j);
        jArr[5] = jArr[5] ^ (5764607523034234880L ^ j);
        jArr[6] = jArr[6] ^ (6917529027641081856L ^ j);
        jArr[7] = (j ^ 8070450532247928832L) ^ jArr[7];
    }

    private static void a(long[] jArr, long[] jArr2) {
        jArr2[0] = ((((((i[((byte) (jArr[0] >>> 56)) & 255] ^ i[(((byte) (jArr[1] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[2] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[3] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[4] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[5] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[6] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[7]) & 255) + 1792];
        jArr2[1] = ((((((i[((byte) (jArr[1] >>> 56)) & 255] ^ i[(((byte) (jArr[2] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[3] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[4] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[5] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[6] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[7] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[0]) & 255) + 1792];
        jArr2[2] = ((((((i[((byte) (jArr[2] >>> 56)) & 255] ^ i[(((byte) (jArr[3] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[4] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[5] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[6] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[7] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[0] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[1]) & 255) + 1792];
        jArr2[3] = ((((((i[((byte) (jArr[3] >>> 56)) & 255] ^ i[(((byte) (jArr[4] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[5] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[6] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[7] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[0] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[1] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[2]) & 255) + 1792];
        jArr2[4] = ((((((i[((byte) (jArr[4] >>> 56)) & 255] ^ i[(((byte) (jArr[5] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[6] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[7] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[0] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[1] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[2] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[3]) & 255) + 1792];
        jArr2[5] = ((((((i[((byte) (jArr[5] >>> 56)) & 255] ^ i[(((byte) (jArr[6] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[7] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[0] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[1] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[2] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[3] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[4]) & 255) + 1792];
        jArr2[6] = ((((((i[((byte) (jArr[6] >>> 56)) & 255] ^ i[(((byte) (jArr[7] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[0] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[1] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[2] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[3] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[4] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[5]) & 255) + 1792];
        jArr2[7] = ((((((i[((byte) (jArr[7] >>> 56)) & 255] ^ i[(((byte) (jArr[0] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[1] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[2] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[3] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[4] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[5] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[6]) & 255) + 1792];
    }

    private static void b(long[] jArr, long j) {
        jArr[0] = jArr[0] ^ j;
        jArr[1] = jArr[1] ^ (16 ^ j);
        jArr[2] = jArr[2] ^ (32 ^ j);
        jArr[3] = jArr[3] ^ (48 ^ j);
        jArr[4] = jArr[4] ^ (64 ^ j);
        jArr[5] = jArr[5] ^ (80 ^ j);
        jArr[6] = jArr[6] ^ (96 ^ j);
        jArr[7] = (j ^ 112) ^ jArr[7];
    }

    private static void b(long[] jArr, long[] jArr2) {
        jArr2[0] = ((((((i[((byte) (jArr[1] >>> 56)) & 255] ^ i[(((byte) (jArr[3] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[5] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[7] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[0] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[2] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[4] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[6]) & 255) + 1792];
        jArr2[1] = ((((((i[((byte) (jArr[2] >>> 56)) & 255] ^ i[(((byte) (jArr[4] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[6] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[0] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[1] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[3] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[5] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[7]) & 255) + 1792];
        jArr2[2] = ((((((i[((byte) (jArr[3] >>> 56)) & 255] ^ i[(((byte) (jArr[5] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[7] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[1] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[2] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[4] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[6] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[0]) & 255) + 1792];
        jArr2[3] = ((((((i[((byte) (jArr[4] >>> 56)) & 255] ^ i[(((byte) (jArr[6] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[0] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[2] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[3] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[5] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[7] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[1]) & 255) + 1792];
        jArr2[4] = ((((((i[((byte) (jArr[5] >>> 56)) & 255] ^ i[(((byte) (jArr[7] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[1] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[3] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[4] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[6] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[0] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[2]) & 255) + 1792];
        jArr2[5] = ((((((i[((byte) (jArr[6] >>> 56)) & 255] ^ i[(((byte) (jArr[0] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[2] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[4] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[5] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[7] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[1] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[3]) & 255) + 1792];
        jArr2[6] = ((((((i[((byte) (jArr[7] >>> 56)) & 255] ^ i[(((byte) (jArr[1] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[3] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[5] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[6] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[0] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[2] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[4]) & 255) + 1792];
        jArr2[7] = ((((((i[((byte) (jArr[0] >>> 56)) & 255] ^ i[(((byte) (jArr[2] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[4] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[6] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[7] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[1] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[3] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[5]) & 255) + 1792];
    }

    @Override // iaik.security.md.AbstractMessageDigest
    public final void a(byte[] bArr, int i) {
        long[] jArr = this.m;
        long[] jArr2 = this.l;
        long[] jArr3 = this.o;
        long[] jArr4 = this.n;
        long[] jArr5 = this.j;
        CryptoUtils.squashBytesToLongs(bArr, i, jArr, 0, 8);
        for (int i2 = 0; i2 < 8; i2++) {
            jArr3[i2] = jArr5[i2] ^ jArr[i2];
        }
        b(jArr, -1L);
        b(jArr, jArr2);
        b(jArr2, -2L);
        b(jArr2, jArr);
        b(jArr, -3L);
        b(jArr, jArr2);
        b(jArr2, -4L);
        b(jArr2, jArr);
        b(jArr, -5L);
        b(jArr, jArr2);
        b(jArr2, -6L);
        b(jArr2, jArr);
        b(jArr, -7L);
        b(jArr, jArr2);
        b(jArr2, -8L);
        b(jArr2, jArr);
        b(jArr, -9L);
        b(jArr, jArr2);
        b(jArr2, -10L);
        b(jArr2, jArr4);
        jArr3[1] = jArr3[1] ^ 1152921504606846976L;
        jArr3[2] = jArr3[2] ^ 2305843009213693952L;
        jArr3[3] = jArr3[3] ^ 3458764513820540928L;
        jArr3[4] = jArr3[4] ^ 4611686018427387904L;
        jArr3[5] = jArr3[5] ^ 5764607523034234880L;
        jArr3[6] = jArr3[6] ^ 6917529027641081856L;
        jArr3[7] = jArr3[7] ^ 8070450532247928832L;
        a(jArr3, jArr);
        a(jArr, 72057594037927936L);
        a(jArr, jArr2);
        a(jArr2, 144115188075855872L);
        a(jArr2, jArr);
        a(jArr, 216172782113783808L);
        a(jArr, jArr2);
        a(jArr2, 288230376151711744L);
        a(jArr2, jArr);
        a(jArr, 360287970189639680L);
        a(jArr, jArr2);
        a(jArr2, 432345564227567616L);
        a(jArr2, jArr);
        a(jArr, 504403158265495552L);
        a(jArr, jArr2);
        a(jArr2, 576460752303423488L);
        a(jArr2, jArr);
        a(jArr, 648518346341351424L);
        a(jArr, jArr2);
        for (int i3 = 0; i3 < 8; i3++) {
            jArr5[i3] = jArr5[i3] ^ (jArr4[i3] ^ jArr2[i3]);
        }
    }

    @Override // iaik.security.md.AbstractMessageDigest
    public void b(byte[] bArr, int i) {
        long[] jArr = (long[]) this.j.clone();
        a(jArr, 0L);
        a(jArr, this.m);
        a(this.m, 72057594037927936L);
        a(this.m, this.l);
        a(this.l, 144115188075855872L);
        a(this.l, this.m);
        a(this.m, 216172782113783808L);
        a(this.m, this.l);
        a(this.l, 288230376151711744L);
        a(this.l, this.m);
        a(this.m, 360287970189639680L);
        a(this.m, this.l);
        a(this.l, 432345564227567616L);
        a(this.l, this.m);
        a(this.m, 504403158265495552L);
        a(this.m, this.l);
        a(this.l, 576460752303423488L);
        a(this.l, this.m);
        a(this.m, 648518346341351424L);
        a(this.m, jArr);
        for (int i2 = 0; i2 < 8; i2++) {
            long[] jArr2 = this.j;
            jArr2[i2] = jArr2[i2] ^ jArr[i2];
        }
        CryptoUtils.spreadLongsToBytes(this.j, this.j.length - (this.k.length >>> 3), this.k, 0, this.k.length >>> 3);
        System.arraycopy(this.k, this.k.length - this.c, bArr, i, this.c);
    }

    @Override // iaik.security.md.AbstractC0041j, iaik.security.md.AbstractC0037f, iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        C0043l c0043l = (C0043l) super.clone();
        c0043l.l = (long[]) this.l.clone();
        c0043l.m = (long[]) this.m.clone();
        c0043l.n = (long[]) this.n.clone();
        c0043l.o = (long[]) this.o.clone();
        return c0043l;
    }

    @Override // iaik.security.md.AbstractC0041j, iaik.security.md.AbstractC0037f, iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    public void engineReset() {
        super.engineReset();
        CryptoUtils.zeroBlock(this.l);
        CryptoUtils.zeroBlock(this.m);
        CryptoUtils.zeroBlock(this.n);
        CryptoUtils.zeroBlock(this.o);
    }
}
