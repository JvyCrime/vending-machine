package iaik.security.md;

import iaik.utils.CryptoUtils;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes.dex */
public class Whirlpool extends AbstractMessageDigest {
    private static final long[][] g = (long[][]) Array.newInstance((Class<?>) long.class, 8, 256);
    private static final long[] h = new long[10];
    private long i;
    private long j;
    private long k;
    private long l;
    private long m;
    private long n;
    private long o;
    private long p;

    static {
        for (int i = 0; i < 256; i++) {
            char cCharAt = "ᠣ웨螸ŏ㚦틵祯酒悼鮎ꌌ笵ᷠퟂ⹋﹗ᕷ㟥鿰䫚壉⤊놠殅뵝ჴ쬾է\ue427䆋Ᵹ闘ﯮ籦\udd17䞞쨭뼇굚茳挂ꩱ젙䧙\uf2e3守騦㊰\ue90f햀뻍㑈ｺ遟\u2068\u1aae둔錢擱猒䀈쏬\udba1贽需켫皂혛떯橐䗳ワ㽕ꋪ斺⿀\ude1c﵍鉵ڊ닦ฟ拔ꢖ暈╙葲㥌幸㢌톥\ue261댡鰞䏇ﰄ写洍\ufadf縤㮫츑轎럫㲁铷뤓ⳓ\ue76e쐃噄義⪻셓\udc0b鵬ㅴ\uf646겉ᓡᘺ椉炶탭챂颤⡜\uf886".charAt(i >> 1);
            long j = (i & 1) == 0 ? cCharAt >>> '\b' : cCharAt & 255;
            long j2 = j << 1;
            if (j2 >= 256) {
                j2 ^= 285;
            }
            long j3 = j2 << 1;
            if (j3 >= 256) {
                j3 ^= 285;
            }
            long j4 = j3 ^ j;
            long j5 = j3 << 1;
            if (j5 >= 256) {
                j5 ^= 285;
            }
            g[0][i] = (j << 32) | (j << 56) | (j << 48) | (j3 << 40) | (j5 << 24) | (j4 << 16) | (j2 << 8) | (j5 ^ j);
            int i2 = 0;
            while (i2 < 7) {
                long[][] jArr = g;
                int i3 = i2 + 1;
                jArr[i3][i] = (jArr[i2][i] >>> 8) | (jArr[i2][i] << 56);
                i2 = i3;
            }
        }
        for (int i4 = 0; i4 < 10; i4++) {
            int i5 = i4 << 3;
            long[] jArr2 = h;
            long[][] jArr3 = g;
            int i6 = i5 + 1;
            int i7 = i6 + 1;
            int i8 = i7 + 1;
            int i9 = i8 + 1;
            long j6 = (((jArr3[0][i5] & (-72057594037927936L)) ^ (jArr3[1][i6] & 71776119061217280L)) ^ (jArr3[2][i7] & 280375465082880L)) ^ (jArr3[3][i8] & 1095216660480L);
            int i10 = i9 + 1;
            long j7 = j6 ^ (jArr3[4][i9] & 4278190080L);
            int i11 = i10 + 1;
            jArr2[i4] = ((j7 ^ (jArr3[5][i10] & 16711680)) ^ (jArr3[6][i11] & 65280)) ^ (jArr3[7][i11 + 1] & 255);
        }
    }

    public Whirlpool() {
        super("Whirlpool", 64, 64);
        engineReset();
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a() {
        long j = this.f << 3;
        byte[] bArr = new byte[8];
        for (int i = 0; i < 8; i++) {
            bArr[7 - i] = (byte) (j >>> (i << 3));
        }
        int i2 = (int) (this.f & 63);
        engineUpdate(a, 0, i2 < 32 ? 56 - i2 : 120 - i2);
        engineUpdate(bArr, 0, 8);
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void a(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        int i5 = i4 + 1;
        int i6 = i5 + 1;
        int i7 = i6 + 1;
        int i8 = i7 + 1;
        int i9 = i8 + 1;
        long j = (((((((((long) bArr[i]) << 56) ^ ((((long) bArr[i2]) & 255) << 48)) ^ ((((long) bArr[i3]) & 255) << 40)) ^ ((((long) bArr[i4]) & 255) << 32)) ^ ((((long) bArr[i5]) & 255) << 24)) ^ ((((long) bArr[i6]) & 255) << 16)) ^ ((((long) bArr[i7]) & 255) << 8)) ^ (((long) bArr[i8]) & 255);
        int i10 = i9 + 1;
        int i11 = i10 + 1;
        long j2 = ((((long) bArr[i10]) & 255) << 48) ^ (((long) bArr[i9]) << 56);
        int i12 = i11 + 1;
        int i13 = i12 + 1;
        int i14 = i13 + 1;
        long j3 = (((((long) bArr[i12]) & 255) << 32) ^ (j2 ^ ((((long) bArr[i11]) & 255) << 40))) ^ ((((long) bArr[i13]) & 255) << 24);
        int i15 = i14 + 1;
        long j4 = j3 ^ ((((long) bArr[i14]) & 255) << 16);
        int i16 = i15 + 1;
        long j5 = j4 ^ ((((long) bArr[i15]) & 255) << 8);
        int i17 = i16 + 1;
        long j6 = j5 ^ (((long) bArr[i16]) & 255);
        int i18 = i17 + 1;
        int i19 = i18 + 1;
        long j7 = ((((long) bArr[i18]) & 255) << 48) ^ (((long) bArr[i17]) << 56);
        int i20 = i19 + 1;
        int i21 = i20 + 1;
        long j8 = (j7 ^ ((((long) bArr[i19]) & 255) << 40)) ^ ((((long) bArr[i20]) & 255) << 32);
        int i22 = i21 + 1;
        long j9 = j8 ^ ((((long) bArr[i21]) & 255) << 24);
        int i23 = i22 + 1;
        long j10 = j9 ^ ((((long) bArr[i22]) & 255) << 16);
        int i24 = i23 + 1;
        long j11 = j10 ^ ((((long) bArr[i23]) & 255) << 8);
        int i25 = i24 + 1;
        long j12 = j11 ^ (((long) bArr[i24]) & 255);
        int i26 = i25 + 1;
        int i27 = i26 + 1;
        long j13 = (((long) bArr[i25]) << 56) ^ ((((long) bArr[i26]) & 255) << 48);
        int i28 = i27 + 1;
        long j14 = j13 ^ ((((long) bArr[i27]) & 255) << 40);
        int i29 = i28 + 1;
        long j15 = ((((long) bArr[i28]) & 255) << 32) ^ j14;
        int i30 = i29 + 1;
        int i31 = i30 + 1;
        long j16 = (j15 ^ ((((long) bArr[i29]) & 255) << 24)) ^ ((((long) bArr[i30]) & 255) << 16);
        int i32 = i31 + 1;
        long j17 = j16 ^ ((((long) bArr[i31]) & 255) << 8);
        int i33 = i32 + 1;
        long j18 = j17 ^ (((long) bArr[i32]) & 255);
        int i34 = i33 + 1;
        long j19 = ((long) bArr[i33]) << 56;
        int i35 = i34 + 1;
        long j20 = j19 ^ ((((long) bArr[i34]) & 255) << 48);
        int i36 = i35 + 1;
        long j21 = j20 ^ ((((long) bArr[i35]) & 255) << 40);
        int i37 = i36 + 1;
        long j22 = j21 ^ ((((long) bArr[i36]) & 255) << 32);
        int i38 = i37 + 1;
        long j23 = j22 ^ ((((long) bArr[i37]) & 255) << 24);
        int i39 = i38 + 1;
        long j24 = j23 ^ ((((long) bArr[i38]) & 255) << 16);
        int i40 = i39 + 1;
        long j25 = j24 ^ ((((long) bArr[i39]) & 255) << 8);
        int i41 = i40 + 1;
        long j26 = j25 ^ (((long) bArr[i40]) & 255);
        int i42 = i41 + 1;
        long j27 = ((long) bArr[i41]) << 56;
        int i43 = i42 + 1;
        long j28 = j27 ^ ((((long) bArr[i42]) & 255) << 48);
        int i44 = i43 + 1;
        int i45 = i44 + 1;
        int i46 = i45 + 1;
        long j29 = ((((((long) bArr[i43]) & 255) << 40) ^ j28) ^ ((((long) bArr[i44]) & 255) << 32)) ^ ((((long) bArr[i45]) & 255) << 24);
        int i47 = i46 + 1;
        long j30 = j29 ^ ((((long) bArr[i46]) & 255) << 16);
        int i48 = i47 + 1;
        long j31 = j30 ^ ((((long) bArr[i47]) & 255) << 8);
        int i49 = i48 + 1;
        long j32 = j31 ^ (((long) bArr[i48]) & 255);
        int i50 = i49 + 1;
        int i51 = i50 + 1;
        int i52 = i51 + 1;
        int i53 = i52 + 1;
        long j33 = (((((long) bArr[i49]) << 56) ^ ((((long) bArr[i50]) & 255) << 48)) ^ ((((long) bArr[i51]) & 255) << 40)) ^ ((((long) bArr[i52]) & 255) << 32);
        int i54 = i53 + 1;
        long j34 = j33 ^ ((((long) bArr[i53]) & 255) << 24);
        int i55 = i54 + 1;
        long j35 = j34 ^ ((((long) bArr[i54]) & 255) << 16);
        int i56 = i55 + 1;
        long j36 = j35 ^ ((((long) bArr[i55]) & 255) << 8);
        int i57 = i56 + 1;
        long j37 = j36 ^ (((long) bArr[i56]) & 255);
        int i58 = i57 + 1;
        int i59 = i58 + 1;
        long j38 = (((long) bArr[i57]) << 56) ^ ((((long) bArr[i58]) & 255) << 48);
        int i60 = i59 + 1;
        long j39 = j38 ^ ((((long) bArr[i59]) & 255) << 40);
        int i61 = i60 + 1;
        long j40 = j39 ^ ((((long) bArr[i60]) & 255) << 32);
        int i62 = i61 + 1;
        long j41 = j40 ^ ((((long) bArr[i61]) & 255) << 24);
        int i63 = i62 + 1;
        long j42 = (((long) bArr[i63 + 1]) & 255) ^ ((j41 ^ ((((long) bArr[i62]) & 255) << 16)) ^ ((((long) bArr[i63]) & 255) << 8));
        long j43 = this.i;
        long j44 = j ^ j43;
        long j45 = this.j;
        long j46 = this.k;
        long j47 = this.l;
        long j48 = j18 ^ j47;
        long j49 = this.m;
        long j50 = j26 ^ j49;
        long j51 = this.n;
        long j52 = j32 ^ j51;
        long j53 = this.o;
        long j54 = j37 ^ j53;
        long j55 = this.p;
        long j56 = j6 ^ j45;
        long j57 = j45;
        long j58 = j12 ^ j46;
        long j59 = j48;
        long j60 = j50;
        long j61 = j52;
        long j62 = j54;
        long j63 = j42 ^ j55;
        long j64 = j44;
        long j65 = j47;
        long j66 = j49;
        long j67 = j51;
        long j68 = j55;
        long j69 = j43;
        long j70 = j46;
        long j71 = j53;
        int i64 = 0;
        while (i64 < 10) {
            long[][] jArr = g;
            long j72 = j70;
            long j73 = j69;
            long j74 = j71;
            long j75 = (((((jArr[0][((int) (j69 >>> 56)) & 255] ^ jArr[1][((int) (j68 >>> 48)) & 255]) ^ jArr[2][((int) (j71 >>> 40)) & 255]) ^ jArr[3][((int) (j67 >>> 32)) & 255]) ^ jArr[4][((int) (j66 >>> 24)) & 255]) ^ jArr[5][((int) (j65 >>> 16)) & 255]) ^ jArr[6][((int) (j72 >>> 8)) & 255];
            long j76 = j57;
            long j77 = j75 ^ jArr[7][((int) j76) & 255];
            long j78 = j65;
            int i65 = i64;
            long j79 = ((((((jArr[0][((int) (j76 >>> 56)) & 255] ^ jArr[1][((int) (j73 >>> 48)) & 255]) ^ jArr[2][((int) (j68 >>> 40)) & 255]) ^ jArr[3][((int) (j74 >>> 32)) & 255]) ^ jArr[4][((int) (j67 >>> 24)) & 255]) ^ jArr[5][((int) (j66 >>> 16)) & 255]) ^ jArr[6][((int) (j78 >>> 8)) & 255]) ^ jArr[7][((int) j72) & 255];
            long j80 = ((((((jArr[0][((int) (j72 >>> 56)) & 255] ^ jArr[1][((int) (j76 >>> 48)) & 255]) ^ jArr[2][((int) (j73 >>> 40)) & 255]) ^ jArr[3][((int) (j68 >>> 32)) & 255]) ^ jArr[4][((int) (j74 >>> 24)) & 255]) ^ jArr[5][((int) (j67 >>> 16)) & 255]) ^ jArr[6][((int) (j66 >>> 8)) & 255]) ^ jArr[7][((int) j78) & 255];
            long j81 = j68;
            long j82 = ((((((jArr[1][((int) (j72 >>> 48)) & 255] ^ jArr[0][((int) (j78 >>> 56)) & 255]) ^ jArr[2][((int) (j76 >>> 40)) & 255]) ^ jArr[3][((int) (j73 >>> 32)) & 255]) ^ jArr[4][((int) (j81 >>> 24)) & 255]) ^ jArr[5][((int) (j74 >>> 16)) & 255]) ^ jArr[6][((int) (j67 >>> 8)) & 255]) ^ jArr[7][((int) j66) & 255];
            long j83 = ((((((jArr[1][((int) (j78 >>> 48)) & 255] ^ jArr[0][((int) (j66 >>> 56)) & 255]) ^ jArr[2][((int) (j72 >>> 40)) & 255]) ^ jArr[3][((int) (j76 >>> 32)) & 255]) ^ jArr[4][((int) (j73 >>> 24)) & 255]) ^ jArr[5][((int) (j81 >>> 16)) & 255]) ^ jArr[6][((int) (j74 >>> 8)) & 255]) ^ jArr[7][((int) j67) & 255];
            long j84 = ((((((jArr[1][((int) (j66 >>> 48)) & 255] ^ jArr[0][((int) (j67 >>> 56)) & 255]) ^ jArr[2][((int) (j78 >>> 40)) & 255]) ^ jArr[3][((int) (j72 >>> 32)) & 255]) ^ jArr[4][((int) (j76 >>> 24)) & 255]) ^ jArr[5][((int) (j73 >>> 16)) & 255]) ^ jArr[6][((int) (j81 >>> 8)) & 255]) ^ jArr[7][((int) j74) & 255];
            long j85 = ((((((jArr[1][((int) (j67 >>> 48)) & 255] ^ jArr[0][((int) (j74 >>> 56)) & 255]) ^ jArr[2][((int) (j66 >>> 40)) & 255]) ^ jArr[3][((int) (j78 >>> 32)) & 255]) ^ jArr[4][((int) (j72 >>> 24)) & 255]) ^ jArr[5][((int) (j76 >>> 16)) & 255]) ^ jArr[6][((int) (j73 >>> 8)) & 255]) ^ jArr[7][((int) j81) & 255];
            long j86 = ((((((jArr[1][((int) (j74 >>> 48)) & 255] ^ jArr[0][((int) (j81 >>> 56)) & 255]) ^ jArr[2][((int) (j67 >>> 40)) & 255]) ^ jArr[3][((int) (j66 >>> 32)) & 255]) ^ jArr[4][((int) (j78 >>> 24)) & 255]) ^ jArr[5][((int) (j72 >>> 16)) & 255]) ^ jArr[6][((int) (j76 >>> 8)) & 255]) ^ jArr[7][((int) j73) & 255];
            long j87 = j77 ^ h[i65];
            long j88 = j63;
            long j89 = j62;
            long j90 = j61;
            long j91 = j60;
            long j92 = j59;
            long j93 = j58;
            long j94 = (((jArr[3][((int) (j90 >>> 32)) & 255] ^ ((jArr[1][((int) (j88 >>> 48)) & 255] ^ (j87 ^ jArr[0][((int) (j64 >>> 56)) & 255])) ^ jArr[2][((int) (j89 >>> 40)) & 255])) ^ jArr[4][((int) (j91 >>> 24)) & 255]) ^ jArr[5][((int) (j92 >>> 16)) & 255]) ^ jArr[6][((int) (j93 >>> 8)) & 255];
            long j95 = j56;
            long j96 = j94 ^ jArr[7][((int) j95) & 255];
            long j97 = ((((((jArr[1][((int) (j64 >>> 48)) & 255] ^ (j79 ^ jArr[0][((int) (j95 >>> 56)) & 255])) ^ jArr[2][((int) (j88 >>> 40)) & 255]) ^ jArr[3][((int) (j89 >>> 32)) & 255]) ^ jArr[4][((int) (j90 >>> 24)) & 255]) ^ jArr[5][((int) (j91 >>> 16)) & 255]) ^ jArr[6][((int) (j92 >>> 8)) & 255]) ^ jArr[7][((int) j93) & 255];
            j58 = ((((((jArr[1][((int) (j95 >>> 48)) & 255] ^ (j80 ^ jArr[0][((int) (j93 >>> 56)) & 255])) ^ jArr[2][((int) (j64 >>> 40)) & 255]) ^ jArr[3][((int) (j88 >>> 32)) & 255]) ^ jArr[4][((int) (j89 >>> 24)) & 255]) ^ jArr[5][((int) (j90 >>> 16)) & 255]) ^ jArr[6][((int) (j91 >>> 8)) & 255]) ^ jArr[7][((int) j92) & 255];
            long j98 = (((((((j82 ^ jArr[0][((int) (j92 >>> 56)) & 255]) ^ jArr[1][((int) (j93 >>> 48)) & 255]) ^ jArr[2][((int) (j95 >>> 40)) & 255]) ^ jArr[3][((int) (j64 >>> 32)) & 255]) ^ jArr[4][((int) (j88 >>> 24)) & 255]) ^ jArr[5][((int) (j89 >>> 16)) & 255]) ^ jArr[6][((int) (j90 >>> 8)) & 255]) ^ jArr[7][((int) j91) & 255];
            long j99 = (((((((j83 ^ jArr[0][((int) (j91 >>> 56)) & 255]) ^ jArr[1][((int) (j92 >>> 48)) & 255]) ^ jArr[2][((int) (j93 >>> 40)) & 255]) ^ jArr[3][((int) (j95 >>> 32)) & 255]) ^ jArr[4][((int) (j64 >>> 24)) & 255]) ^ jArr[5][((int) (j88 >>> 16)) & 255]) ^ jArr[6][((int) (j89 >>> 8)) & 255]) ^ jArr[7][((int) j90) & 255];
            long j100 = (((((jArr[2][((int) (j92 >>> 40)) & 255] ^ ((j84 ^ jArr[0][((int) (j90 >>> 56)) & 255]) ^ jArr[1][((int) (j91 >>> 48)) & 255])) ^ jArr[3][((int) (j93 >>> 32)) & 255]) ^ jArr[4][((int) (j95 >>> 24)) & 255]) ^ jArr[5][((int) (j64 >>> 16)) & 255]) ^ jArr[6][((int) (j88 >>> 8)) & 255]) ^ jArr[7][((int) j89) & 255];
            long j101 = ((((((jArr[1][((int) (j90 >>> 48)) & 255] ^ (j85 ^ jArr[0][((int) (j89 >>> 56)) & 255])) ^ jArr[2][((int) (j91 >>> 40)) & 255]) ^ jArr[3][((int) (j92 >>> 32)) & 255]) ^ jArr[4][((int) (j93 >>> 24)) & 255]) ^ jArr[5][((int) (j95 >>> 16)) & 255]) ^ jArr[6][((int) (j64 >>> 8)) & 255]) ^ jArr[7][((int) j88) & 255];
            j63 = ((((((jArr[1][((int) (j89 >>> 48)) & 255] ^ (j86 ^ jArr[0][((int) (j88 >>> 56)) & 255])) ^ jArr[2][((int) (j90 >>> 40)) & 255]) ^ jArr[3][((int) (j91 >>> 32)) & 255]) ^ jArr[4][((int) (j92 >>> 24)) & 255]) ^ jArr[5][((int) (j93 >>> 16)) & 255]) ^ jArr[6][((int) (j95 >>> 8)) & 255]) ^ jArr[7][((int) j64) & 255];
            i64 = i65 + 1;
            j69 = j87;
            j68 = j86;
            j65 = j82;
            j71 = j85;
            j66 = j83;
            j64 = j96;
            j59 = j98;
            j56 = j97;
            j67 = j84;
            j60 = j99;
            j61 = j100;
            j62 = j101;
            j70 = j80;
            j57 = j79;
        }
        this.i ^= j64 ^ j;
        this.j ^= j56 ^ j6;
        this.k ^= j58 ^ j12;
        this.l ^= j59 ^ j18;
        this.m ^= j60 ^ j26;
        this.n ^= j61 ^ j32;
        this.o ^= j62 ^ j37;
        this.p ^= j63 ^ j42;
    }

    @Override // iaik.security.md.AbstractMessageDigest
    void b(byte[] bArr, int i) {
        int i2 = i + 1;
        long j = this.i;
        bArr[i] = (byte) (j >> 56);
        int i3 = i2 + 1;
        bArr[i2] = (byte) (j >> 48);
        int i4 = i3 + 1;
        bArr[i3] = (byte) (j >> 40);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (j >> 32);
        int i6 = i5 + 1;
        bArr[i5] = (byte) (j >> 24);
        int i7 = i6 + 1;
        bArr[i6] = (byte) (j >> 16);
        int i8 = i7 + 1;
        bArr[i7] = (byte) (j >> 8);
        int i9 = i8 + 1;
        bArr[i8] = (byte) j;
        int i10 = i9 + 1;
        long j2 = this.j;
        bArr[i9] = (byte) (j2 >> 56);
        int i11 = i10 + 1;
        bArr[i10] = (byte) (j2 >> 48);
        int i12 = i11 + 1;
        bArr[i11] = (byte) (j2 >> 40);
        int i13 = i12 + 1;
        bArr[i12] = (byte) (j2 >> 32);
        int i14 = i13 + 1;
        bArr[i13] = (byte) (j2 >> 24);
        int i15 = i14 + 1;
        bArr[i14] = (byte) (j2 >> 16);
        int i16 = i15 + 1;
        bArr[i15] = (byte) (j2 >> 8);
        int i17 = i16 + 1;
        bArr[i16] = (byte) j2;
        int i18 = i17 + 1;
        long j3 = this.k;
        bArr[i17] = (byte) (j3 >> 56);
        int i19 = i18 + 1;
        bArr[i18] = (byte) (j3 >> 48);
        int i20 = i19 + 1;
        bArr[i19] = (byte) (j3 >> 40);
        int i21 = i20 + 1;
        bArr[i20] = (byte) (j3 >> 32);
        int i22 = i21 + 1;
        bArr[i21] = (byte) (j3 >> 24);
        int i23 = i22 + 1;
        bArr[i22] = (byte) (j3 >> 16);
        int i24 = i23 + 1;
        bArr[i23] = (byte) (j3 >> 8);
        int i25 = i24 + 1;
        bArr[i24] = (byte) j3;
        int i26 = i25 + 1;
        long j4 = this.l;
        bArr[i25] = (byte) (j4 >> 56);
        int i27 = i26 + 1;
        bArr[i26] = (byte) (j4 >> 48);
        int i28 = i27 + 1;
        bArr[i27] = (byte) (j4 >> 40);
        int i29 = i28 + 1;
        bArr[i28] = (byte) (j4 >> 32);
        int i30 = i29 + 1;
        bArr[i29] = (byte) (j4 >> 24);
        int i31 = i30 + 1;
        bArr[i30] = (byte) (j4 >> 16);
        int i32 = i31 + 1;
        bArr[i31] = (byte) (j4 >> 8);
        int i33 = i32 + 1;
        bArr[i32] = (byte) j4;
        int i34 = i33 + 1;
        long j5 = this.m;
        bArr[i33] = (byte) (j5 >> 56);
        int i35 = i34 + 1;
        bArr[i34] = (byte) (j5 >> 48);
        int i36 = i35 + 1;
        bArr[i35] = (byte) (j5 >> 40);
        int i37 = i36 + 1;
        bArr[i36] = (byte) (j5 >> 32);
        int i38 = i37 + 1;
        bArr[i37] = (byte) (j5 >> 24);
        int i39 = i38 + 1;
        bArr[i38] = (byte) (j5 >> 16);
        int i40 = i39 + 1;
        bArr[i39] = (byte) (j5 >> 8);
        int i41 = i40 + 1;
        bArr[i40] = (byte) j5;
        int i42 = i41 + 1;
        long j6 = this.n;
        bArr[i41] = (byte) (j6 >> 56);
        int i43 = i42 + 1;
        bArr[i42] = (byte) (j6 >> 48);
        int i44 = i43 + 1;
        bArr[i43] = (byte) (j6 >> 40);
        int i45 = i44 + 1;
        bArr[i44] = (byte) (j6 >> 32);
        int i46 = i45 + 1;
        bArr[i45] = (byte) (j6 >> 24);
        int i47 = i46 + 1;
        bArr[i46] = (byte) (j6 >> 16);
        int i48 = i47 + 1;
        bArr[i47] = (byte) (j6 >> 8);
        int i49 = i48 + 1;
        bArr[i48] = (byte) j6;
        int i50 = i49 + 1;
        long j7 = this.o;
        bArr[i49] = (byte) (j7 >> 56);
        int i51 = i50 + 1;
        bArr[i50] = (byte) (j7 >> 48);
        int i52 = i51 + 1;
        bArr[i51] = (byte) (j7 >> 40);
        int i53 = i52 + 1;
        bArr[i52] = (byte) (j7 >> 32);
        int i54 = i53 + 1;
        bArr[i53] = (byte) (j7 >> 24);
        int i55 = i54 + 1;
        bArr[i54] = (byte) (j7 >> 16);
        int i56 = i55 + 1;
        bArr[i55] = (byte) (j7 >> 8);
        int i57 = i56 + 1;
        bArr[i56] = (byte) j7;
        int i58 = i57 + 1;
        long j8 = this.p;
        bArr[i57] = (byte) (j8 >> 56);
        int i59 = i58 + 1;
        bArr[i58] = (byte) (j8 >> 48);
        int i60 = i59 + 1;
        bArr[i59] = (byte) (j8 >> 40);
        int i61 = i60 + 1;
        bArr[i60] = (byte) (j8 >> 32);
        int i62 = i61 + 1;
        bArr[i61] = (byte) (j8 >> 24);
        int i63 = i62 + 1;
        bArr[i62] = (byte) (j8 >> 16);
        bArr[i63] = (byte) (j8 >> 8);
        bArr[i63 + 1] = (byte) j8;
    }

    @Override // iaik.security.md.AbstractMessageDigest, java.security.MessageDigestSpi
    public void engineReset() {
        CryptoUtils.zeroBlock(this.b);
        this.i = 0L;
        this.j = 0L;
        this.k = 0L;
        this.l = 0L;
        this.m = 0L;
        this.n = 0L;
        this.o = 0L;
        this.p = 0L;
        this.f = 0L;
    }
}
