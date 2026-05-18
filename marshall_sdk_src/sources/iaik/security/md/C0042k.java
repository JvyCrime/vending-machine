package iaik.security.md;

import iaik.security.ssl.SSLContext;
import iaik.utils.CryptoUtils;

/* JADX INFO: renamed from: iaik.security.md.k, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0042k extends AbstractC0041j {
    private long[] l;
    private long[] m;
    private long[] n;
    private long[] o;

    public C0042k(int i) {
        super(i, 16);
        this.l = new long[16];
        this.m = new long[16];
        this.n = new long[16];
        this.o = new long[16];
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
        jArr[7] = jArr[7] ^ (8070450532247928832L ^ j);
        jArr[8] = jArr[8] ^ (Long.MIN_VALUE ^ j);
        jArr[9] = jArr[9] ^ ((-8070450532247928832L) ^ j);
        jArr[10] = jArr[10] ^ ((-6917529027641081856L) ^ j);
        jArr[11] = jArr[11] ^ ((-5764607523034234880L) ^ j);
        jArr[12] = jArr[12] ^ ((-4611686018427387904L) ^ j);
        jArr[13] = jArr[13] ^ ((-3458764513820540928L) ^ j);
        jArr[14] = jArr[14] ^ ((-2305843009213693952L) ^ j);
        jArr[15] = (j ^ (-1152921504606846976L)) ^ jArr[15];
    }

    private static void a(long[] jArr, long[] jArr2) {
        jArr2[0] = ((((((i[((byte) (jArr[0] >>> 56)) & 255] ^ i[(((byte) (jArr[1] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[2] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[3] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[4] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[5] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[6] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[11]) & 255) + 1792];
        jArr2[1] = ((((((i[((byte) (jArr[1] >>> 56)) & 255] ^ i[(((byte) (jArr[2] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[3] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[4] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[5] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[6] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[7] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[12]) & 255) + 1792];
        jArr2[2] = ((((((i[((byte) (jArr[2] >>> 56)) & 255] ^ i[(((byte) (jArr[3] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[4] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[5] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[6] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[7] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[8] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[13]) & 255) + 1792];
        jArr2[3] = ((((((i[((byte) (jArr[3] >>> 56)) & 255] ^ i[(((byte) (jArr[4] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[5] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[6] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[7] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[8] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[9] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[14]) & 255) + 1792];
        jArr2[4] = ((((((i[((byte) (jArr[4] >>> 56)) & 255] ^ i[(((byte) (jArr[5] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[6] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[7] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[8] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[9] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[10] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[15]) & 255) + 1792];
        jArr2[5] = ((((((i[((byte) (jArr[5] >>> 56)) & 255] ^ i[(((byte) (jArr[6] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[7] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[8] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[9] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[10] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[11] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[0]) & 255) + 1792];
        jArr2[6] = ((((((i[((byte) (jArr[6] >>> 56)) & 255] ^ i[(((byte) (jArr[7] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[8] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[9] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[10] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[11] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[12] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[1]) & 255) + 1792];
        jArr2[7] = ((((((i[((byte) (jArr[7] >>> 56)) & 255] ^ i[(((byte) (jArr[8] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[9] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[10] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[11] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[12] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[13] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[2]) & 255) + 1792];
        jArr2[8] = ((((((i[((byte) (jArr[8] >>> 56)) & 255] ^ i[(((byte) (jArr[9] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[10] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[11] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[12] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[13] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[14] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[3]) & 255) + 1792];
        jArr2[9] = ((((((i[((byte) (jArr[9] >>> 56)) & 255] ^ i[(((byte) (jArr[10] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[11] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[12] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[13] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[14] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[15] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[4]) & 255) + 1792];
        jArr2[10] = ((((((i[((byte) (jArr[10] >>> 56)) & 255] ^ i[(((byte) (jArr[11] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[12] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[13] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[14] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[15] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[0] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[5]) & 255) + 1792];
        jArr2[11] = ((((((i[((byte) (jArr[11] >>> 56)) & 255] ^ i[(((byte) (jArr[12] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[13] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[14] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[15] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[0] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[1] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[6]) & 255) + 1792];
        jArr2[12] = ((((((i[((byte) (jArr[12] >>> 56)) & 255] ^ i[(((byte) (jArr[13] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[14] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[15] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[0] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[1] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[2] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[7]) & 255) + 1792];
        jArr2[13] = ((((((i[((byte) (jArr[13] >>> 56)) & 255] ^ i[(((byte) (jArr[14] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[15] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[0] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[1] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[2] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[3] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[8]) & 255) + 1792];
        jArr2[14] = ((((((i[((byte) (jArr[14] >>> 56)) & 255] ^ i[(((byte) (jArr[15] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[0] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[1] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[2] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[3] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[4] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[9]) & 255) + 1792];
        jArr2[15] = ((((((i[((byte) (jArr[15] >>> 56)) & 255] ^ i[(((byte) (jArr[0] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[1] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[2] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[3] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[4] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[5] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[10]) & 255) + 1792];
    }

    private static void b(long[] jArr, long j) {
        jArr[0] = jArr[0] ^ j;
        jArr[1] = jArr[1] ^ (16 ^ j);
        jArr[2] = jArr[2] ^ (32 ^ j);
        jArr[3] = jArr[3] ^ (48 ^ j);
        jArr[4] = jArr[4] ^ (64 ^ j);
        jArr[5] = jArr[5] ^ (80 ^ j);
        jArr[6] = jArr[6] ^ (96 ^ j);
        jArr[7] = jArr[7] ^ (112 ^ j);
        jArr[8] = jArr[8] ^ (128 ^ j);
        jArr[9] = jArr[9] ^ (144 ^ j);
        jArr[10] = jArr[10] ^ (160 ^ j);
        jArr[11] = jArr[11] ^ (176 ^ j);
        jArr[12] = jArr[12] ^ (192 ^ j);
        jArr[13] = jArr[13] ^ (208 ^ j);
        jArr[14] = jArr[14] ^ (224 ^ j);
        jArr[15] = (j ^ 240) ^ jArr[15];
    }

    private static void b(long[] jArr, long[] jArr2) {
        jArr2[0] = ((((((i[((byte) (jArr[1] >>> 56)) & 255] ^ i[(((byte) (jArr[3] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[5] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[11] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[0] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[2] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[4] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[6]) & 255) + 1792];
        jArr2[1] = ((((((i[((byte) (jArr[2] >>> 56)) & 255] ^ i[(((byte) (jArr[4] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[6] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[12] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[1] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[3] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[5] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[7]) & 255) + 1792];
        jArr2[2] = ((((((i[((byte) (jArr[3] >>> 56)) & 255] ^ i[(((byte) (jArr[5] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[7] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[13] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[2] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[4] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[6] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[8]) & 255) + 1792];
        jArr2[3] = ((((((i[((byte) (jArr[4] >>> 56)) & 255] ^ i[(((byte) (jArr[6] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[8] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[14] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[3] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[5] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[7] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[9]) & 255) + 1792];
        jArr2[4] = ((((((i[((byte) (jArr[5] >>> 56)) & 255] ^ i[(((byte) (jArr[7] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[9] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[15] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[4] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[6] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[8] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[10]) & 255) + 1792];
        jArr2[5] = ((((((i[((byte) (jArr[6] >>> 56)) & 255] ^ i[(((byte) (jArr[8] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[10] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[0] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[5] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[7] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[9] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[11]) & 255) + 1792];
        jArr2[6] = ((((((i[((byte) (jArr[7] >>> 56)) & 255] ^ i[(((byte) (jArr[9] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[11] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[1] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[6] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[8] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[10] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[12]) & 255) + 1792];
        jArr2[7] = ((((((i[((byte) (jArr[8] >>> 56)) & 255] ^ i[(((byte) (jArr[10] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[12] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[2] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[7] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[9] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[11] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[13]) & 255) + 1792];
        jArr2[8] = ((((((i[((byte) (jArr[9] >>> 56)) & 255] ^ i[(((byte) (jArr[11] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[13] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[3] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[8] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[10] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[12] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[14]) & 255) + 1792];
        jArr2[9] = ((((((i[((byte) (jArr[10] >>> 56)) & 255] ^ i[(((byte) (jArr[12] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[14] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[4] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[9] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[11] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[13] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[15]) & 255) + 1792];
        jArr2[10] = ((((((i[((byte) (jArr[11] >>> 56)) & 255] ^ i[(((byte) (jArr[13] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[15] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[5] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[10] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[12] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[14] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[0]) & 255) + 1792];
        jArr2[11] = ((((((i[((byte) (jArr[12] >>> 56)) & 255] ^ i[(((byte) (jArr[14] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[0] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[6] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[11] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[13] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[15] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[1]) & 255) + 1792];
        jArr2[12] = ((((((i[((byte) (jArr[13] >>> 56)) & 255] ^ i[(((byte) (jArr[15] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[1] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[7] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[12] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[14] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[0] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[2]) & 255) + 1792];
        jArr2[13] = ((((((i[((byte) (jArr[14] >>> 56)) & 255] ^ i[(((byte) (jArr[0] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[2] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[8] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[13] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[15] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[1] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[3]) & 255) + 1792];
        jArr2[14] = ((((((i[((byte) (jArr[15] >>> 56)) & 255] ^ i[(((byte) (jArr[1] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[3] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[9] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[14] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[0] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[2] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[4]) & 255) + 1792];
        jArr2[15] = ((((((i[((byte) (jArr[0] >>> 56)) & 255] ^ i[(((byte) (jArr[2] >>> 48)) & 255) + 256]) ^ i[(((byte) (jArr[4] >>> 40)) & 255) + 512]) ^ i[(((byte) (jArr[10] >>> 32)) & 255) + SSLContext.VERSION_SSL30]) ^ i[(((byte) (jArr[15] >>> 24)) & 255) + 1024]) ^ i[(((byte) (jArr[1] >>> 16)) & 255) + 1280]) ^ i[(((byte) (jArr[3] >>> 8)) & 255) + 1536]) ^ i[(((byte) jArr[5]) & 255) + 1792];
    }

    @Override // iaik.security.md.AbstractMessageDigest
    public final void a(byte[] bArr, int i) {
        long[] jArr = this.m;
        long[] jArr2 = this.l;
        long[] jArr3 = this.o;
        long[] jArr4 = this.n;
        long[] jArr5 = this.j;
        CryptoUtils.squashBytesToLongs(bArr, i, jArr, 0, 16);
        jArr3[0] = jArr5[0] ^ jArr[0];
        jArr3[1] = jArr5[1] ^ jArr[1];
        jArr3[2] = jArr5[2] ^ jArr[2];
        jArr3[3] = jArr5[3] ^ jArr[3];
        jArr3[4] = jArr5[4] ^ jArr[4];
        jArr3[5] = jArr5[5] ^ jArr[5];
        jArr3[6] = jArr5[6] ^ jArr[6];
        jArr3[7] = jArr5[7] ^ jArr[7];
        jArr3[8] = jArr5[8] ^ jArr[8];
        jArr3[9] = jArr5[9] ^ jArr[9];
        jArr3[10] = jArr5[10] ^ jArr[10];
        jArr3[11] = jArr5[11] ^ jArr[11];
        jArr3[12] = jArr5[12] ^ jArr[12];
        jArr3[13] = jArr5[13] ^ jArr[13];
        jArr3[14] = jArr5[14] ^ jArr[14];
        jArr3[15] = jArr5[15] ^ jArr[15];
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
        b(jArr2, jArr);
        b(jArr, -11L);
        b(jArr, jArr2);
        b(jArr2, -12L);
        b(jArr2, jArr);
        b(jArr, -13L);
        b(jArr, jArr2);
        b(jArr2, -14L);
        b(jArr2, jArr4);
        jArr3[1] = jArr3[1] ^ 1152921504606846976L;
        jArr3[2] = jArr3[2] ^ 2305843009213693952L;
        jArr3[3] = jArr3[3] ^ 3458764513820540928L;
        jArr3[4] = jArr3[4] ^ 4611686018427387904L;
        jArr3[5] = jArr3[5] ^ 5764607523034234880L;
        jArr3[6] = jArr3[6] ^ 6917529027641081856L;
        jArr3[7] = jArr3[7] ^ 8070450532247928832L;
        jArr3[8] = jArr3[8] ^ Long.MIN_VALUE;
        jArr3[9] = jArr3[9] ^ (-8070450532247928832L);
        jArr3[10] = jArr3[10] ^ (-6917529027641081856L);
        jArr3[11] = jArr3[11] ^ (-5764607523034234880L);
        jArr3[12] = jArr3[12] ^ (-4611686018427387904L);
        jArr3[13] = jArr3[13] ^ (-3458764513820540928L);
        jArr3[14] = jArr3[14] ^ (-2305843009213693952L);
        jArr3[15] = jArr3[15] ^ (-1152921504606846976L);
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
        a(jArr2, 720575940379279360L);
        a(jArr2, jArr);
        a(jArr, 792633534417207296L);
        a(jArr, jArr2);
        a(jArr2, 864691128455135232L);
        a(jArr2, jArr);
        a(jArr, 936748722493063168L);
        a(jArr, jArr2);
        jArr5[0] = jArr5[0] ^ (jArr4[0] ^ jArr2[0]);
        jArr5[1] = jArr5[1] ^ (jArr4[1] ^ jArr2[1]);
        jArr5[2] = jArr5[2] ^ (jArr4[2] ^ jArr2[2]);
        jArr5[3] = jArr5[3] ^ (jArr4[3] ^ jArr2[3]);
        jArr5[4] = jArr5[4] ^ (jArr4[4] ^ jArr2[4]);
        jArr5[5] = jArr5[5] ^ (jArr4[5] ^ jArr2[5]);
        jArr5[6] = jArr5[6] ^ (jArr4[6] ^ jArr2[6]);
        jArr5[7] = jArr5[7] ^ (jArr4[7] ^ jArr2[7]);
        jArr5[8] = jArr5[8] ^ (jArr4[8] ^ jArr2[8]);
        jArr5[9] = jArr5[9] ^ (jArr4[9] ^ jArr2[9]);
        jArr5[10] = jArr5[10] ^ (jArr4[10] ^ jArr2[10]);
        jArr5[11] = jArr5[11] ^ (jArr4[11] ^ jArr2[11]);
        jArr5[12] = jArr5[12] ^ (jArr4[12] ^ jArr2[12]);
        jArr5[13] = jArr5[13] ^ (jArr4[13] ^ jArr2[13]);
        jArr5[14] = jArr5[14] ^ (jArr4[14] ^ jArr2[14]);
        jArr5[15] = (jArr2[15] ^ jArr4[15]) ^ jArr5[15];
    }

    @Override // iaik.security.md.AbstractMessageDigest
    public void b(byte[] bArr, int i) {
        long[] jArr = (long[]) this.j.clone();
        a(jArr, 0L);
        a(jArr, this.l);
        for (long j = 1; j < 13; j += 2) {
            a(this.l, j << 56);
            a(this.l, this.m);
            a(this.m, (j + 1) << 56);
            a(this.m, this.l);
        }
        a(this.l, 936748722493063168L);
        a(this.l, jArr);
        for (int i2 = 0; i2 < 16; i2++) {
            long[] jArr2 = this.j;
            jArr2[i2] = jArr2[i2] ^ jArr[i2];
        }
        CryptoUtils.spreadLongsToBytes(this.j, this.j.length - (this.k.length >>> 3), this.k, 0, this.k.length >>> 3);
        System.arraycopy(this.k, this.k.length - this.c, bArr, i, this.c);
    }

    @Override // iaik.security.md.AbstractC0041j, iaik.security.md.AbstractC0037f, iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        C0042k c0042k = (C0042k) super.clone();
        c0042k.l = (long[]) this.l.clone();
        c0042k.m = (long[]) this.m.clone();
        c0042k.n = (long[]) this.n.clone();
        c0042k.o = (long[]) this.o.clone();
        return c0042k;
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
