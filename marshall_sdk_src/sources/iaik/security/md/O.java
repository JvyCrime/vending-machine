package iaik.security.md;

import iaik.utils.CryptoUtils;

/* JADX INFO: loaded from: classes.dex */
final class O extends AbstractMessageDigest {
    private long g;
    private long h;
    private long[] i;
    private long[] j;
    private long[] k;
    private final long[] l;
    private boolean m;
    private int n;
    private byte[] o;

    O(int i, long[] jArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Skein");
        stringBuffer.append(i << 3);
        super(stringBuffer.toString(), i, 64);
        this.i = new long[8];
        this.j = new long[5];
        this.k = new long[17];
        this.l = jArr;
        this.o = new byte[((i + 7) >>> 3) << 3];
        engineReset();
    }

    private static long a(long j, int i, long j2) {
        return ((j >>> (64 - i)) | (j << i)) ^ j2;
    }

    private void a(long j) {
        this.g = 0L;
        this.h = j | 4611686018427387904L;
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a() {
        int i = ((int) ((this.f % 64) + 64)) % 64;
        this.n = i;
        if (i != 0) {
            engineUpdate(a, 1, 64 - this.n);
        } else if (this.f == 0) {
            engineUpdate(a, 1, 64);
        } else if (this.m) {
            this.n = 64;
        }
        this.h |= Long.MIN_VALUE;
        a(this.b, 0);
        a(-4683743612465315840L);
        this.n = 8;
        a(a, 1);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a(byte[] bArr, int i) {
        O o = this;
        long j = o.g + ((long) o.n);
        o.g = j;
        long[] jArr = o.j;
        jArr[0] = j;
        jArr[3] = j;
        long j2 = o.h;
        jArr[1] = j2;
        jArr[4] = j2;
        jArr[2] = j ^ j2;
        long j3 = 0;
        for (int i2 = 0; i2 < 8; i2++) {
            long[] jArr2 = o.k;
            long j4 = o.i[i2];
            jArr2[i2] = j4;
            jArr2[i2 + 9] = j4;
            j3 ^= j4;
        }
        o.k[8] = j3 ^ 2004413935125273122L;
        CryptoUtils.squashBytesToLongsLE(bArr, i, o.i, 0, 8);
        long[] jArr3 = o.i;
        long j5 = jArr3[0];
        long[] jArr4 = o.k;
        long j6 = j5 + jArr4[0];
        long j7 = jArr3[1] + jArr4[1];
        long j8 = jArr3[2] + jArr4[2];
        long j9 = jArr3[3] + jArr4[3];
        long j10 = jArr3[4] + jArr4[4];
        long j11 = jArr3[5] + jArr4[5] + o.g;
        long j12 = jArr3[6] + jArr4[6] + o.h;
        long j13 = j9;
        long j14 = j11;
        long j15 = jArr3[7] + jArr4[7];
        int i3 = 1;
        while (i3 <= 18) {
            int i4 = i3 % 9;
            int i5 = i3 % 3;
            long j16 = j6 + j7;
            long jA = a(j7, 46, j16);
            long j17 = j8 + j13;
            long jA2 = a(j13, 36, j17);
            long j18 = j10 + j14;
            long jA3 = a(j14, 19, j18);
            int i6 = i3;
            long j19 = j12 + j15;
            long jA4 = a(j15, 37, j19);
            long j20 = j17 + jA;
            long jA5 = a(jA, 33, j20);
            long j21 = j18 + jA4;
            long jA6 = a(jA4, 27, j21);
            long j22 = j19 + jA3;
            long jA7 = a(jA3, 14, j22);
            long j23 = j16 + jA2;
            long jA8 = a(jA2, 42, j23);
            long j24 = j21 + jA5;
            long jA9 = a(jA5, 17, j24);
            long j25 = j22 + jA8;
            long jA10 = a(jA8, 49, j25);
            long j26 = j23 + jA7;
            long jA11 = a(jA7, 36, j26);
            long j27 = j20 + jA6;
            long jA12 = a(jA6, 39, j27);
            long j28 = j25 + jA9;
            int i7 = i4 + 1;
            long jA13 = a(jA9, 44, j28) + this.k[i7];
            long j29 = j26 + jA12;
            int i8 = i4 + 7;
            long j30 = i6;
            long jA14 = a(jA12, 9, j29) + this.k[i8] + j30;
            long j31 = j27 + jA11;
            int i9 = i4 + 5;
            long jA15 = a(jA11, 54, j31) + this.k[i9] + this.j[i5];
            long j32 = j24 + jA10;
            long jA16 = a(jA10, 56, j32);
            long[] jArr5 = this.k;
            int i10 = i4 + 3;
            long j33 = jA16 + jArr5[i10];
            long j34 = j29 + jA13 + jArr5[i4];
            long jA17 = a(jA13, 39, j34);
            int i11 = i4 + 2;
            long j35 = j31 + j33 + this.k[i11];
            long jA18 = a(j33, 30, j35);
            int i12 = i4 + 4;
            long j36 = j32 + jA15 + this.k[i12];
            long jA19 = a(jA15, 34, j36);
            int i13 = i4 + 6;
            int i14 = i5 + 1;
            long j37 = j28 + jA14 + this.k[i13] + this.j[i14];
            long jA20 = a(jA14, 24, j37);
            long j38 = j35 + jA17;
            long jA21 = a(jA17, 13, j38);
            long j39 = j36 + jA20;
            long jA22 = a(jA20, 50, j39);
            long j40 = j37 + jA19;
            long jA23 = a(jA19, 10, j40);
            long j41 = j34 + jA18;
            long jA24 = a(jA18, 17, j41);
            long j42 = j39 + jA21;
            long jA25 = a(jA21, 25, j42);
            long j43 = j40 + jA24;
            long jA26 = a(jA24, 29, j43);
            long j44 = j41 + jA23;
            long jA27 = a(jA23, 39, j44);
            long j45 = j38 + jA22;
            long jA28 = a(jA22, 43, j45);
            long j46 = j43 + jA25;
            long jA29 = a(jA25, 8, j46) + this.k[i11];
            long j47 = j44 + jA28;
            long jA30 = a(jA28, 35, j47) + this.k[i4 + 8] + j30 + 1;
            long j48 = j45 + jA27;
            long jA31 = a(jA27, 56, j48) + this.k[i13] + this.j[i14];
            long j49 = j42 + jA26;
            long jA32 = a(jA26, 22, j49);
            long[] jArr6 = this.k;
            long j50 = jA32 + jArr6[i12];
            long j51 = j47 + jArr6[i7];
            j8 = j48 + jArr6[i10];
            long j52 = j49 + jArr6[i9];
            j12 = j46 + jArr6[i8] + this.j[i5 + 2];
            j10 = j52;
            i3 = i6 + 2;
            j6 = j51;
            o = this;
            j7 = jA29;
            j13 = j50;
            j14 = jA31;
            j15 = jA30;
        }
        long[] jArr7 = o.i;
        jArr7[0] = jArr7[0] ^ j6;
        jArr7[1] = jArr7[1] ^ j7;
        jArr7[2] = jArr7[2] ^ j8;
        jArr7[3] = jArr7[3] ^ j13;
        jArr7[4] = jArr7[4] ^ j10;
        jArr7[5] = jArr7[5] ^ j14;
        jArr7[6] = jArr7[6] ^ j12;
        jArr7[7] = jArr7[7] ^ j15;
        o.h &= -4611686018427387905L;
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void b(byte[] bArr, int i) {
        long[] jArr = this.i;
        byte[] bArr2 = this.o;
        CryptoUtils.spreadLongsToBytesLE(jArr, 0, bArr2, 0, bArr2.length >>> 3);
        System.arraycopy(this.o, 0, bArr, i, this.c);
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigest, java.security.MessageDigestSpi
    public Object clone() {
        O o = (O) super.clone();
        o.i = (long[]) this.i.clone();
        o.j = (long[]) this.j.clone();
        o.k = (long[]) this.k.clone();
        o.o = (byte[]) this.o.clone();
        return o;
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    protected void engineReset() {
        this.f = 0L;
        CryptoUtils.zeroBlock(this.b);
        long[] jArr = this.l;
        System.arraycopy(jArr, 0, this.i, 0, jArr.length);
        CryptoUtils.zeroBlock(this.k);
        CryptoUtils.zeroBlock(this.j);
        CryptoUtils.zeroBlock(this.o);
        this.n = 64;
        this.m = false;
        a(3458764513820540928L);
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) {
        if (this.m) {
            a(this.b, 0);
        }
        int i3 = (int) (this.f & ((long) this.e));
        this.f += (long) i2;
        if (i3 != 0) {
            int i4 = 64 - i3;
            if (i4 > i2) {
                System.arraycopy(bArr, i, this.b, i3, i2);
                this.m = i3 + i2 == 64;
                return;
            }
            System.arraycopy(bArr, i, this.b, i3, i4);
            i2 -= i4;
            if (i2 == 0) {
                this.m = i3 + i4 == 64;
                return;
            }
            i += i4;
        }
        while (i2 > 64) {
            a(bArr, i);
            i += 64;
            i2 -= 64;
        }
        if (i2 > 0) {
            System.arraycopy(bArr, i, this.b, 0, i2);
            this.m = i2 == 64;
        }
    }
}
