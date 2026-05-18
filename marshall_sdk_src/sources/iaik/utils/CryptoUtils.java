package iaik.utils;

import java.math.BigInteger;
import java.util.Random;

/* JADX INFO: loaded from: classes2.dex */
public final class CryptoUtils {
    static {
        Util.toString((byte[]) null, -1, 1);
    }

    private CryptoUtils() {
    }

    public static byte[] addModBlockSize(int i, byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[i];
        if (bArr == null) {
            int iMax = Math.max(Math.min(bArr2.length, i), 0);
            System.arraycopy(bArr2, 0, bArr3, i - iMax, iMax);
        } else if (bArr2 != null) {
            int length = bArr.length;
            int length2 = bArr2.length;
            if (length < length2) {
                bArr2 = bArr;
                bArr = bArr2;
                length2 = length;
                length = length2;
            }
            int i2 = i - 1;
            int i3 = length - 1;
            int i4 = length2 - 1;
            int i5 = 0;
            while (i3 >= 0 && i2 >= 0) {
                int i6 = bArr[i3] & 255;
                int i7 = i4 >= 0 ? bArr2[i4] & 255 : 0;
                int i8 = (i6 + i7 + i5) & 255;
                int i9 = (i8 < i6 || i8 < i7) ? 1 : 0;
                bArr3[i2] = (byte) i8;
                i3--;
                i4--;
                i2--;
                i5 = i9;
            }
            if (i5 > 0 && i2 >= 0) {
                bArr3[i2] = (byte) i5;
            }
        } else {
            int iMax2 = Math.max(Math.min(bArr.length, i), 0);
            System.arraycopy(bArr, 0, bArr3, i - iMax2, iMax2);
        }
        return bArr3;
    }

    public static int compareBlock(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        for (int i4 = i; i4 < i + i3; i4++) {
            if (bArr[i4] != bArr2[i2]) {
                return i4 - i;
            }
            i2++;
        }
        return -1;
    }

    public static int compareBlock(byte[] bArr, byte[] bArr2) {
        int length = bArr.length < bArr2.length ? bArr.length : bArr2.length;
        int iCompareBlock = compareBlock(bArr, 0, bArr2, 0, length);
        if (iCompareBlock != -1) {
            return iCompareBlock;
        }
        if (bArr.length != bArr2.length) {
            return length;
        }
        return -1;
    }

    public static int compareBlock(int[] iArr, int i, int[] iArr2, int i2, int i3) {
        for (int i4 = i; i4 < i + i3; i4++) {
            if (iArr[i4] != iArr2[i2]) {
                return i4 - i;
            }
            i2++;
        }
        return -1;
    }

    public static int compareBlock(int[] iArr, int[] iArr2) {
        int length = iArr.length < iArr2.length ? iArr.length : iArr2.length;
        int iCompareBlock = compareBlock(iArr, 0, iArr2, 0, length);
        if (iCompareBlock != -1) {
            return iCompareBlock;
        }
        if (iArr.length != iArr2.length) {
            return length;
        }
        return -1;
    }

    public static int compareBlock(long[] jArr, int i, long[] jArr2, int i2, int i3) {
        for (int i4 = i; i4 < i + i3; i4++) {
            if (jArr[i4] != jArr2[i2]) {
                return i4 - i;
            }
            i2++;
        }
        return -1;
    }

    public static int compareBlock(long[] jArr, long[] jArr2) {
        int length = jArr.length < jArr2.length ? jArr.length : jArr2.length;
        int iCompareBlock = compareBlock(jArr, 0, jArr2, 0, length);
        if (iCompareBlock != -1) {
            return iCompareBlock;
        }
        if (jArr.length != jArr2.length) {
            return length;
        }
        return -1;
    }

    public static byte[] concatenate(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        int length2 = bArr2.length;
        byte[] bArr3 = new byte[length + length2];
        System.arraycopy(bArr, 0, bArr3, 0, length);
        System.arraycopy(bArr2, 0, bArr3, length, length2);
        return bArr3;
    }

    public static int[] concatenate(int[] iArr, int[] iArr2) {
        int length = iArr.length;
        int length2 = iArr2.length;
        int[] iArr3 = new int[length + length2];
        System.arraycopy(iArr, 0, iArr3, 0, length);
        System.arraycopy(iArr2, 0, iArr3, length, length2);
        return iArr3;
    }

    public static long[] concatenate(long[] jArr, long[] jArr2) {
        int length = jArr.length;
        int length2 = jArr2.length;
        long[] jArr3 = new long[length + length2];
        System.arraycopy(jArr, 0, jArr3, 0, length);
        System.arraycopy(jArr2, 0, jArr3, length, length2);
        return jArr3;
    }

    public static void copyBlock(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        int i4 = i2;
        while (i4 < i2 + i3) {
            bArr2[i4] = bArr[i];
            i4++;
            i++;
        }
    }

    public static void copyBlock(byte[] bArr, byte[] bArr2) {
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = bArr[i];
        }
    }

    public static void copyBlock(int[] iArr, int i, int[] iArr2, int i2, int i3) {
        int i4 = i2;
        while (i4 < i2 + i3) {
            iArr2[i4] = iArr[i];
            i4++;
            i++;
        }
    }

    public static void copyBlock(int[] iArr, int[] iArr2) {
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i] = iArr[i];
        }
    }

    public static void copyBlock(long[] jArr, int i, long[] jArr2, int i2, int i3) {
        int i4 = i2;
        while (i4 < i2 + i3) {
            jArr2[i4] = jArr[i];
            i4++;
            i++;
        }
    }

    public static void copyBlock(long[] jArr, long[] jArr2) {
        for (int i = 0; i < jArr.length; i++) {
            jArr2[i] = jArr[i];
        }
    }

    public static boolean equalsBlock(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        for (int i4 = i; i4 < i + i3; i4++) {
            if (bArr[i4] != bArr2[i2]) {
                return false;
            }
            i2++;
        }
        return true;
    }

    public static boolean equalsBlock(byte[] bArr, byte[] bArr2) {
        if (bArr.length != bArr2.length) {
            return false;
        }
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean equalsBlock(int[] iArr, int i, int[] iArr2, int i2, int i3) {
        for (int i4 = i; i4 < i + i3; i4++) {
            if (iArr[i4] != iArr2[i2]) {
                return false;
            }
            i2++;
        }
        return true;
    }

    public static boolean equalsBlock(int[] iArr, int[] iArr2) {
        if (iArr.length != iArr2.length) {
            return false;
        }
        for (int i = 0; i < iArr.length; i++) {
            if (iArr[i] != iArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean equalsBlock(long[] jArr, int i, long[] jArr2, int i2, int i3) {
        for (int i4 = i; i4 < i + i3; i4++) {
            if (jArr[i4] != jArr2[i2]) {
                return false;
            }
            i2++;
        }
        return true;
    }

    public static boolean equalsBlock(long[] jArr, long[] jArr2) {
        if (jArr.length != jArr2.length) {
            return false;
        }
        for (int i = 0; i < jArr.length; i++) {
            if (jArr[i] != jArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] extGcd(int i, int i2) {
        return NumberTheory.extGcd(i, i2);
    }

    public static void fillBlock(byte[] bArr, byte b) {
        fillBlock(bArr, 0, b, bArr.length);
    }

    public static void fillBlock(byte[] bArr, int i, byte b, int i2) {
        for (int i3 = i; i3 < i + i2; i3++) {
            bArr[i3] = b;
        }
    }

    public static void fillBlock(int[] iArr, int i) {
        fillBlock(iArr, 0, i, iArr.length);
    }

    public static void fillBlock(int[] iArr, int i, int i2, int i3) {
        for (int i4 = i; i4 < i + i3; i4++) {
            iArr[i4] = i2;
        }
    }

    public static void fillBlock(long[] jArr, int i, long j, int i2) {
        for (int i3 = i; i3 < i + i2; i3++) {
            jArr[i3] = j;
        }
    }

    public static void fillBlock(long[] jArr, long j) {
        fillBlock(jArr, 0, j, jArr.length);
    }

    public static int gcd(int i, int i2) {
        return NumberTheory.gcd(i, i2);
    }

    public static BigInteger getStrongPrime(int i, Random random) {
        return NumberTheory.getStrongPrime(i, random);
    }

    public static byte[] increment(byte[] bArr) {
        for (int length = bArr.length - 1; length >= 0; length--) {
            byte b = (byte) (bArr[length] + 1);
            bArr[length] = b;
            if (b != 0) {
                break;
            }
        }
        return bArr;
    }

    public static byte[] incrementExtended(byte[] bArr) {
        int length = bArr.length;
        int i = length - 1;
        while (i >= 0) {
            byte b = (byte) (bArr[i] + 1);
            bArr[i] = b;
            if (b != 0) {
                break;
            }
            i--;
        }
        if (i != -1) {
            return bArr;
        }
        byte[] bArr2 = new byte[length + 1];
        bArr2[0] = 1;
        System.arraycopy(bArr, 0, bArr2, 1, length);
        return bArr2;
    }

    public static void randomBlock(byte[] bArr) {
        randomBlock(bArr, 0, bArr.length);
    }

    public static void randomBlock(byte[] bArr, int i, int i2) {
        for (int i3 = i; i3 < i + i2; i3++) {
            bArr[i3] = (byte) (Math.random() * 256.0d);
        }
    }

    public static byte[] resizeArray(byte[] bArr, int i) {
        if (i >= 0) {
            byte[] bArr2 = new byte[i];
            System.arraycopy(bArr, 0, bArr2, 0, Math.min(i, bArr.length));
            return bArr2;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Invalid length: ");
        stringBuffer.append(i);
        throw new NegativeArraySizeException(stringBuffer.toString());
    }

    public static void reverseArray(byte[] bArr, int i, int i2) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        int i3 = (i + i2) - 1;
        int i4 = (i2 >>> 1) + i;
        while (i < i4) {
            byte b = bArr[i];
            bArr[i] = bArr[i3];
            bArr[i3] = b;
            i++;
            i3--;
        }
    }

    public static boolean secureEqualsBlock(byte[] bArr, int i, byte[] bArr2, int i2, int i3) {
        int i4 = i;
        byte b = 0;
        while (i4 < i + i3) {
            b = (byte) ((bArr2[i2] ^ bArr[i4]) | b);
            i4++;
            i2++;
        }
        return b == 0;
    }

    public static boolean secureEqualsBlock(byte[] bArr, byte[] bArr2) {
        if (bArr.length != bArr2.length) {
            return false;
        }
        byte b = 0;
        for (int i = 0; i < bArr.length; i++) {
            b = (byte) (b | (bArr[i] ^ bArr2[i]));
        }
        return b == 0;
    }

    public static boolean secureEqualsBlock(int[] iArr, int i, int[] iArr2, int i2, int i3) {
        int i4 = i;
        int i5 = 0;
        while (i4 < i + i3) {
            i5 |= iArr2[i2] ^ iArr[i4];
            i4++;
            i2++;
        }
        return i5 == 0;
    }

    public static boolean secureEqualsBlock(int[] iArr, int[] iArr2) {
        if (iArr.length != iArr2.length) {
            return false;
        }
        int i = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            i |= iArr[i2] ^ iArr2[i2];
        }
        return i == 0;
    }

    public static boolean secureEqualsBlock(long[] jArr, int i, long[] jArr2, int i2, int i3) {
        long j = 0;
        for (int i4 = i; i4 < i + i3; i4++) {
            j |= jArr[i4] ^ jArr2[i2];
            i2++;
        }
        return j == 0;
    }

    public static boolean secureEqualsBlock(long[] jArr, long[] jArr2) {
        if (jArr.length != jArr2.length) {
            return false;
        }
        long j = 0;
        for (int i = 0; i < jArr.length; i++) {
            j |= jArr[i] ^ jArr2[i];
        }
        return j == 0;
    }

    public static byte[] shiftLeft(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        int i = 0;
        for (int length = bArr.length - 1; length >= 0; length--) {
            bArr2[length] = (byte) (i | ((bArr[length] & 255) << 1));
            i = (bArr[length] >>> 7) & 1;
        }
        return bArr2;
    }

    public static int[] shiftLeft(int[] iArr) {
        int[] iArr2 = new int[iArr.length];
        int i = 0;
        for (int length = iArr.length - 1; length >= 0; length--) {
            iArr2[length] = i | (iArr[length] << 1);
            i = (iArr[length] >>> 31) & 1;
        }
        return iArr2;
    }

    public static long[] shiftLeft(long[] jArr) {
        long[] jArr2 = new long[jArr.length];
        long j = 0;
        for (int length = jArr.length - 1; length >= 0; length--) {
            jArr2[length] = j | (jArr[length] << 1);
            j = (jArr[length] >>> 63) & 1;
        }
        return jArr2;
    }

    public static byte[] shiftRight(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr2[i2] = (byte) (i | ((bArr[i2] & 255) >>> 1));
            i = bArr[i2] << 7;
        }
        return bArr2;
    }

    public static int[] shiftRight(int[] iArr) {
        int[] iArr2 = new int[iArr.length];
        int i = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr2[i2] = i | (iArr[i2] >>> 1);
            i = iArr[i2] << 31;
        }
        return iArr2;
    }

    public static long[] shiftRight(long[] jArr) {
        long[] jArr2 = new long[jArr.length];
        long j = 0;
        for (int i = 0; i < jArr.length; i++) {
            jArr2[i] = j | (jArr[i] >>> 1);
            j = jArr[i] << 63;
        }
        return jArr2;
    }

    public static void spreadIntsToBytes(int[] iArr, int i, byte[] bArr, int i2, int i3) {
        int i4 = i2;
        while (i4 < (i3 << 2) + i2) {
            int i5 = i4 + 1;
            bArr[i4] = (byte) (iArr[i] >>> 24);
            int i6 = i5 + 1;
            bArr[i5] = (byte) (iArr[i] >>> 16);
            int i7 = i6 + 1;
            bArr[i6] = (byte) (iArr[i] >>> 8);
            i4 = i7 + 1;
            bArr[i7] = (byte) iArr[i];
            i++;
        }
    }

    public static void spreadIntsToBytesLE(int[] iArr, int i, byte[] bArr, int i2, int i3) {
        int i4 = i2;
        while (i4 < (i3 << 2) + i2) {
            int i5 = i4 + 1;
            bArr[i4] = (byte) iArr[i];
            int i6 = i5 + 1;
            bArr[i5] = (byte) (iArr[i] >>> 8);
            int i7 = i6 + 1;
            bArr[i6] = (byte) (iArr[i] >>> 16);
            i4 = i7 + 1;
            bArr[i7] = (byte) (iArr[i] >>> 24);
            i++;
        }
    }

    public static void spreadLongsToBytes(long[] jArr, int i, byte[] bArr, int i2, int i3) {
        int i4 = i2;
        while (i4 < (i3 << 3) + i2) {
            int i5 = i4 + 1;
            bArr[i4] = (byte) (jArr[i] >>> 56);
            int i6 = i5 + 1;
            bArr[i5] = (byte) (jArr[i] >>> 48);
            int i7 = i6 + 1;
            bArr[i6] = (byte) (jArr[i] >>> 40);
            int i8 = i7 + 1;
            bArr[i7] = (byte) (jArr[i] >>> 32);
            int i9 = i8 + 1;
            bArr[i8] = (byte) (jArr[i] >>> 24);
            int i10 = i9 + 1;
            bArr[i9] = (byte) (jArr[i] >>> 16);
            int i11 = i10 + 1;
            bArr[i10] = (byte) (jArr[i] >>> 8);
            i4 = i11 + 1;
            bArr[i11] = (byte) jArr[i];
            i++;
        }
    }

    public static void spreadLongsToBytesLE(long[] jArr, int i, byte[] bArr, int i2, int i3) {
        int i4 = i2;
        while (i4 < (i3 << 3) + i2) {
            int i5 = i4 + 1;
            bArr[i4] = (byte) jArr[i];
            int i6 = i5 + 1;
            bArr[i5] = (byte) (jArr[i] >>> 8);
            int i7 = i6 + 1;
            bArr[i6] = (byte) (jArr[i] >>> 16);
            int i8 = i7 + 1;
            bArr[i7] = (byte) (jArr[i] >>> 24);
            int i9 = i8 + 1;
            bArr[i8] = (byte) (jArr[i] >>> 32);
            int i10 = i9 + 1;
            bArr[i9] = (byte) (jArr[i] >>> 40);
            int i11 = i10 + 1;
            bArr[i10] = (byte) (jArr[i] >>> 48);
            i4 = i11 + 1;
            bArr[i11] = (byte) (jArr[i] >>> 56);
            i++;
        }
    }

    public static void spreadShortsToBytesBE(int[] iArr, int i, byte[] bArr, int i2, int i3) {
        int i4 = i2;
        while (i4 < (i3 << 1) + i2) {
            int i5 = i4 + 1;
            bArr[i4] = (byte) (iArr[i] >>> 8);
            i4 = i5 + 1;
            bArr[i5] = (byte) iArr[i];
            i++;
        }
    }

    public static void spreadShortsToBytesLE(int[] iArr, int i, byte[] bArr, int i2, int i3) {
        int i4 = i2;
        while (i4 < (i3 << 1) + i2) {
            int i5 = i4 + 1;
            bArr[i4] = (byte) iArr[i];
            i4 = i5 + 1;
            bArr[i5] = (byte) (iArr[i] >>> 8);
            i++;
        }
    }

    public static void squashBytesToInts(byte[] bArr, int i, int[] iArr, int i2, int i3) {
        int i4 = i;
        while (i4 < (i3 << 2) + i) {
            int i5 = i4 + 1;
            int i6 = i5 + 1;
            int i7 = ((bArr[i4] & 255) << 24) | ((bArr[i5] & 255) << 16);
            int i8 = i6 + 1;
            int i9 = i7 | ((bArr[i6] & 255) << 8);
            iArr[i2] = i9 | (bArr[i8] & 255);
            i2++;
            i4 = i8 + 1;
        }
    }

    public static void squashBytesToIntsLE(byte[] bArr, int i, int[] iArr, int i2, int i3) {
        int i4 = i;
        while (i4 < (i3 << 2) + i) {
            int i5 = i4 + 1;
            int i6 = i5 + 1;
            int i7 = (bArr[i4] & 255) | ((bArr[i5] & 255) << 8);
            int i8 = i6 + 1;
            int i9 = i7 | ((bArr[i6] & 255) << 16);
            iArr[i2] = i9 | (bArr[i8] << 24);
            i2++;
            i4 = i8 + 1;
        }
    }

    public static int squashBytesToIntsLEPadZero(byte[] bArr, int i, int[] iArr, int i2, int i3) {
        int i4 = i3 >> 2;
        int i5 = i3 & 3;
        if (i4 != 0) {
            squashBytesToIntsLE(bArr, i, iArr, i2, i4);
        }
        if (i5 == 0) {
            return i4;
        }
        int i6 = i + (i3 - i5);
        int i7 = i2 + i4;
        if (i5 == 1) {
            iArr[i7] = bArr[i6] & 255;
        } else if (i5 == 2) {
            int i8 = i6 + 1;
            iArr[i7] = ((bArr[i8] & 255) << 8) | (bArr[i6] & 255);
        } else if (i5 == 3) {
            int i9 = i6 + 1;
            int i10 = (bArr[i6] & 255) | ((bArr[i9] & 255) << 8);
            iArr[i7] = ((bArr[i9 + 1] & 255) << 16) | i10;
        }
        return i4 + 1;
    }

    public static void squashBytesToIntsPadZero(byte[] bArr, int i, int[] iArr, int i2, int i3) {
        int i4 = i3 >> 2;
        int i5 = i3 & 3;
        if (i4 != 0) {
            squashBytesToInts(bArr, i, iArr, i2, i4);
        }
        if (i5 != 0) {
            int i6 = i + (i3 - i5);
            int i7 = i2 + i4;
            if (i5 == 1) {
                iArr[i7] = (bArr[i6] & 255) << 24;
                return;
            }
            if (i5 == 2) {
                int i8 = i6 + 1;
                iArr[i7] = ((bArr[i8] & 255) << 16) | ((bArr[i6] & 255) << 24);
            } else {
                if (i5 != 3) {
                    return;
                }
                int i9 = i6 + 1;
                int i10 = ((bArr[i6] & 255) << 24) | ((bArr[i9] & 255) << 16);
                iArr[i7] = ((bArr[i9 + 1] & 255) << 8) | i10;
            }
        }
    }

    public static void squashBytesToLongs(byte[] bArr, int i, long[] jArr, int i2, int i3) {
        int i4 = i;
        while (i4 < (i3 << 3) + i) {
            int i5 = i4 + 1;
            long j = ((long) (bArr[i4] & 255)) << 56;
            int i6 = i5 + 1;
            int i7 = i6 + 1;
            long j2 = j | (((long) (bArr[i5] & 255)) << 48) | (((long) (bArr[i6] & 255)) << 40);
            int i8 = i7 + 1;
            long j3 = j2 | (((long) (bArr[i7] & 255)) << 32);
            int i9 = i8 + 1;
            long j4 = j3 | (((long) (bArr[i8] & 255)) << 24);
            int i10 = i9 + 1;
            long j5 = j4 | (((long) (bArr[i9] & 255)) << 16);
            int i11 = i10 + 1;
            long j6 = j5 | (((long) (bArr[i10] & 255)) << 8);
            i4 = i11 + 1;
            jArr[i2] = j6 | ((long) (bArr[i11] & 255));
            i2++;
        }
    }

    public static void squashBytesToLongsLE(byte[] bArr, int i, long[] jArr, int i2, int i3) {
        int i4 = i;
        while (i4 < (i3 << 3) + i) {
            int i5 = i4 + 1;
            long j = bArr[i4] & 255;
            int i6 = i5 + 1;
            int i7 = i6 + 1;
            long j2 = j | (((long) (bArr[i5] & 255)) << 8) | (((long) (bArr[i6] & 255)) << 16);
            int i8 = i7 + 1;
            long j3 = j2 | (((long) (bArr[i7] & 255)) << 24);
            int i9 = i8 + 1;
            long j4 = j3 | (((long) (bArr[i8] & 255)) << 32);
            int i10 = i9 + 1;
            long j5 = j4 | (((long) (bArr[i9] & 255)) << 40);
            int i11 = i10 + 1;
            long j6 = j5 | (((long) (bArr[i10] & 255)) << 48);
            i4 = i11 + 1;
            jArr[i2] = j6 | (((long) (bArr[i11] & 255)) << 56);
            i2++;
        }
    }

    public static void squashBytesToShortsBE(byte[] bArr, int i, int[] iArr, int i2, int i3) {
        int i4 = i;
        while (i4 < (i3 << 1) + i) {
            int i5 = i4 + 1;
            iArr[i2] = ((bArr[i4] & 255) << 8) | (bArr[i5] & 255);
            i2++;
            i4 = i5 + 1;
        }
    }

    public static void squashBytesToShortsLE(byte[] bArr, int i, int[] iArr, int i2, int i3) {
        int i4 = i;
        while (i4 < (i3 << 1) + i) {
            int i5 = i4 + 1;
            iArr[i2] = (bArr[i4] & 255) | ((bArr[i5] & 255) << 8);
            i2++;
            i4 = i5 + 1;
        }
    }

    public static void xorBlock(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, int i3, int i4) {
        int i5 = i3;
        while (i5 < i3 + i4) {
            bArr3[i5] = (byte) (bArr[i] ^ bArr2[i2]);
            i5++;
            i++;
            i2++;
        }
    }

    public static void xorBlock(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        for (int i = 0; i < bArr.length; i++) {
            bArr3[i] = (byte) (bArr[i] ^ bArr2[i]);
        }
    }

    public static void xorBlock(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3, int i4) {
        int i5 = i3;
        while (i5 < i3 + i4) {
            iArr3[i5] = iArr[i] ^ iArr2[i2];
            i5++;
            i++;
            i2++;
        }
    }

    public static void xorBlock(int[] iArr, int[] iArr2, int[] iArr3) {
        for (int i = 0; i < iArr.length; i++) {
            iArr3[i] = iArr[i] ^ iArr2[i];
        }
    }

    public static void xorBlock(long[] jArr, int i, long[] jArr2, int i2, long[] jArr3, int i3, int i4) {
        int i5 = i3;
        while (i5 < i3 + i4) {
            jArr3[i5] = jArr[i] ^ jArr2[i2];
            i2++;
            i5++;
            i++;
        }
    }

    public static void xorBlock(long[] jArr, long[] jArr2, long[] jArr3) {
        for (int i = 0; i < jArr.length; i++) {
            jArr3[i] = jArr[i] ^ jArr2[i];
        }
    }

    public static void zeroBlock(byte[] bArr) {
        zeroBlock(bArr, 0, bArr.length);
    }

    public static void zeroBlock(byte[] bArr, int i, int i2) {
        for (int i3 = i; i3 < i + i2; i3++) {
            bArr[i3] = 0;
        }
    }

    public static void zeroBlock(int[] iArr) {
        zeroBlock(iArr, 0, iArr.length);
    }

    public static void zeroBlock(int[] iArr, int i, int i2) {
        for (int i3 = i; i3 < i + i2; i3++) {
            iArr[i3] = 0;
        }
    }

    public static void zeroBlock(long[] jArr) {
        zeroBlock(jArr, 0, jArr.length);
    }

    public static void zeroBlock(long[] jArr, int i, int i2) {
        for (int i3 = i; i3 < i + i2; i3++) {
            jArr[i3] = 0;
        }
    }
}
