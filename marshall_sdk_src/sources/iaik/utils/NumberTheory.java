package iaik.utils;

import androidx.core.app.FrameMetricsAggregator;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

/* JADX INFO: loaded from: classes2.dex */
public final class NumberTheory {
    public static final int SMALLEST_PRIME_SIZE = 160;
    private static BigInteger[] a;
    public static final BigInteger ZERO = BigInteger.ZERO;
    public static final BigInteger ONE = BigInteger.ONE;
    public static final BigInteger TWO = BigInteger.valueOf(2);
    private static final BigInteger b = BigInteger.valueOf(6541380665835015L);

    static {
        int length = Util.decodeByteArray("AAMABQAHAAsADQARABMAFwAdAB8AJQApACsALwA1ADsAPQBDAEcASQBPAFMAWQBhAGUAZwBrAG0AcQB/AIMAiQCLAJUAlwCdAKMApwCtALMAtQC/AMEAxQDHANMA3wDjAOUA6QDvAPEA+wEBAQcBDQEPARUBGQEbASUBMwE3ATkBPQFLAVEBWwFdAWEBZwFvAXUBewF/AYUBjQGRAZkBowGlAa8BsQG3AbsBwQHJAc0BzwHTAd8B5wHrAfMB9wH9AgkCCwIdAiMCLQIzAjkCOwJBAksCUQJXAlkCXwJlAmkCawJ3AoECgwKHAo0CkwKVAqECpQKrArMCvQLFAs8C1w==").length >> 1;
        a = new BigInteger[length];
        int i = 0;
        int i2 = 0;
        while (i < length) {
            a[i] = BigInteger.valueOf(((r0[i2] & 255) << 8) | (r0[r4] & 255));
            i++;
            i2 = i2 + 1 + 1;
        }
    }

    private NumberTheory() {
    }

    private static final BigInteger a(Random random) {
        byte[] bArr = new byte[13];
        random.nextBytes(bArr);
        bArr[0] = (byte) (bArr[0] & 31);
        bArr[0] = (byte) (bArr[0] | 16);
        return new BigInteger(1, bArr);
    }

    public static int[] extGcd(int i, int i2) {
        if (i2 == 0) {
            return new int[]{i, 1, 0};
        }
        int i3 = 1;
        int i4 = 0;
        int i5 = 0;
        int i6 = 1;
        int i7 = i;
        int i8 = i2;
        while (i8 > 0) {
            int i9 = i7 / i8;
            int i10 = i7 - (i9 * i8);
            i7 = i8;
            i8 = i10;
            int i11 = i5;
            i5 = i3 - (i9 * i5);
            i3 = i11;
            int i12 = i6;
            i6 = i4 - (i9 * i6);
            i4 = i12;
        }
        return new int[]{i7, i3, i4};
    }

    public static int gcd(int i, int i2) {
        while (true) {
            int i3 = i2;
            int i4 = i;
            i = i3;
            if (i == 0) {
                return i4;
            }
            i2 = i4 % i;
        }
    }

    public static BigInteger getStrongPrime(int i, Random random) {
        BigInteger bigInteger;
        int i2 = i * 128;
        BigInteger bit = ZERO.setBit(i2 + FrameMetricsAggregator.EVERY_DURATION);
        BigInteger bigInteger2 = new BigDecimal(bit).multiply(new BigDecimal(Math.sqrt(2.0d))).toBigInteger();
        do {
            bigInteger = new BigInteger(i2 + 512, random);
        } while (bigInteger.compareTo(bigInteger2) == -1);
        BigInteger bigIntegerNextPrime = nextPrime(a(random));
        BigInteger bigIntegerNextPrime2 = nextPrime(a(random));
        BigInteger bigIntegerSubtract = bigIntegerNextPrime2.modInverse(bigIntegerNextPrime).multiply(bigIntegerNextPrime2).subtract(bigIntegerNextPrime.modInverse(bigIntegerNextPrime2).multiply(bigIntegerNextPrime));
        BigInteger bigIntegerMultiply = bigIntegerNextPrime.multiply(bigIntegerNextPrime2);
        BigInteger bigIntegerAdd = bigInteger.add(bigIntegerSubtract.subtract(bigInteger).mod(bigIntegerMultiply));
        while (!isProbablePrime(bigIntegerAdd)) {
            bigIntegerAdd = bigIntegerAdd.add(bigIntegerMultiply);
        }
        return bigIntegerAdd;
    }

    public static boolean hasSmallFactors(BigInteger bigInteger) {
        if (!bigInteger.testBit(0)) {
            return true;
        }
        if (bigInteger.bitLength() < 59) {
            return false;
        }
        long jLongValue = bigInteger.mod(b).longValue();
        int i = (int) (jLongValue % 111546435);
        if (i % 3 != 0 && i % 5 != 0 && i % 7 != 0 && i % 11 != 0 && i % 13 != 0 && i % 17 != 0 && i % 19 != 0 && i % 23 != 0) {
            int i2 = (int) (jLongValue % 58642669);
            if (i2 % 29 != 0 && i2 % 31 != 0 && i2 % 37 != 0 && i2 % 41 != 0 && i2 % 43 != 0) {
                int i3 = 13;
                while (true) {
                    BigInteger[] bigIntegerArr = a;
                    if (i3 >= bigIntegerArr.length) {
                        return false;
                    }
                    if (bigInteger.mod(bigIntegerArr[i3]).signum() == 0) {
                        return true;
                    }
                    i3++;
                }
            }
        }
        return true;
    }

    public static boolean isProbablePrime(BigInteger bigInteger) {
        if (bigInteger.compareTo(ONE) > 0 && !hasSmallFactors(bigInteger)) {
            return millerRabin(bigInteger);
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:122:0x015f, code lost:
    
        if (r10 != false) goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x0161, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean millerRabin(java.math.BigInteger r14) {
        /*
            Method dump skipped, instruction units count: 355
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.utils.NumberTheory.millerRabin(java.math.BigInteger):boolean");
    }

    public static BigInteger nextPrime(BigInteger bigInteger) {
        BigInteger bit = bigInteger.setBit(0);
        while (!isProbablePrime(bit)) {
            bit = bit.add(TWO);
        }
        return bit;
    }
}
