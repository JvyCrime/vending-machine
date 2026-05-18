package com.ftdi.j2xx;

import com.google.android.gms.common.util.GmsVersion;

/* JADX INFO: loaded from: classes.dex */
final class FT_BaudRate {
    private static final int FT_CLOCK_RATE = 3000000;
    private static final int FT_CLOCK_RATE_HI = 12000000;
    private static final int FT_SUB_INT_0_0 = 0;
    private static final int FT_SUB_INT_0_125 = 49152;
    private static final int FT_SUB_INT_0_25 = 32768;
    private static final int FT_SUB_INT_0_375 = 0;
    private static final int FT_SUB_INT_0_5 = 16384;
    private static final int FT_SUB_INT_0_625 = 16384;
    private static final int FT_SUB_INT_0_75 = 32768;
    private static final int FT_SUB_INT_0_875 = 49152;
    private static final int FT_SUB_INT_MASK = 49152;

    private FT_BaudRate() {
    }

    static byte FT_GetDivisor(int i, int[] iArr, boolean z) {
        int i2;
        int i3;
        byte bFT_CalcDivisor = FT_CalcDivisor(i, iArr, z);
        if (bFT_CalcDivisor == -1) {
            return (byte) -1;
        }
        if (bFT_CalcDivisor == 0) {
            iArr[0] = (iArr[0] & (-49153)) + 1;
        }
        int iFT_CalcBaudRate = FT_CalcBaudRate(iArr[0], iArr[1], z);
        if (i > iFT_CalcBaudRate) {
            i2 = ((i * 100) / iFT_CalcBaudRate) - 100;
            i3 = ((i % iFT_CalcBaudRate) * 100) % iFT_CalcBaudRate;
        } else {
            i2 = ((iFT_CalcBaudRate * 100) / i) - 100;
            i3 = ((iFT_CalcBaudRate % i) * 100) % i;
        }
        if (i2 < 3) {
            return (byte) 1;
        }
        return (i2 == 3 && i3 == 0) ? (byte) 1 : (byte) 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0045, code lost:
    
        if (r1 <= 75) goto L51;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x004c A[PHI: r2
  0x004c: PHI (r2v2 byte) = (r2v1 byte), (r2v1 byte), (r2v4 byte), (r2v1 byte) binds: [B:29:0x004a, B:38:0x0060, B:28:0x0048, B:18:0x0038] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static byte FT_CalcDivisor(int r7, int[] r8, boolean r9) {
        /*
            r0 = -1
            if (r7 != 0) goto L4
            return r0
        L4:
            r1 = 3000000(0x2dc6c0, float:4.203895E-39)
            int r2 = r1 / r7
            r3 = r2 & (-16384(0xffffffffffffc000, float:NaN))
            if (r3 <= 0) goto Le
            return r0
        Le:
            r0 = 0
            r8[r0] = r2
            r2 = 1
            r8[r2] = r0
            r3 = r8[r0]
            if (r3 != r2) goto L22
            int r3 = r1 % r7
            int r3 = r3 * 100
            int r3 = r3 / r7
            r4 = 3
            if (r3 > r4) goto L22
            r8[r0] = r0
        L22:
            r3 = r8[r0]
            if (r3 != 0) goto L27
            return r2
        L27:
            int r1 = r1 % r7
            int r1 = r1 * 100
            int r1 = r1 / r7
            r7 = 18
            r3 = 6
            r4 = 16384(0x4000, float:2.2959E-41)
            r5 = 32768(0x8000, float:4.5918E-41)
            r6 = 49152(0xc000, float:6.8877E-41)
            if (r9 != 0) goto L4a
            if (r1 > r3) goto L3b
            goto L4c
        L3b:
            if (r1 > r7) goto L3e
            goto L50
        L3e:
            r7 = 37
            if (r1 > r7) goto L43
            goto L58
        L43:
            r7 = 75
            if (r1 > r7) goto L48
            goto L7d
        L48:
            r2 = 0
            goto L4c
        L4a:
            if (r1 > r3) goto L4e
        L4c:
            r4 = 0
            goto L7d
        L4e:
            if (r1 > r7) goto L54
        L50:
            r4 = 49152(0xc000, float:6.8877E-41)
            goto L7d
        L54:
            r7 = 31
            if (r1 > r7) goto L5c
        L58:
            r4 = 32768(0x8000, float:4.5918E-41)
            goto L7d
        L5c:
            r7 = 43
            if (r1 > r7) goto L63
            r8[r2] = r2
            goto L4c
        L63:
            r7 = 56
            if (r1 > r7) goto L68
            goto L7d
        L68:
            r7 = 68
            if (r1 > r7) goto L6f
            r8[r2] = r2
            goto L7d
        L6f:
            r7 = 81
            if (r1 > r7) goto L76
            r8[r2] = r2
            goto L58
        L76:
            r7 = 93
            if (r1 > r7) goto L48
            r8[r2] = r2
            goto L50
        L7d:
            r7 = r8[r0]
            r7 = r7 | r4
            r8[r0] = r7
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ftdi.j2xx.FT_BaudRate.FT_CalcDivisor(int, int[], boolean):byte");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x001e  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0021  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final int FT_CalcBaudRate(int r4, int r5, boolean r6) {
        /*
            if (r4 != 0) goto L6
            r4 = 3000000(0x2dc6c0, float:4.203895E-39)
            return r4
        L6:
            r0 = -49153(0xffffffffffff3fff, float:NaN)
            r0 = r0 & r4
            int r0 = r0 * 100
            r1 = 32768(0x8000, float:4.5918E-41)
            r2 = 16384(0x4000, float:2.2959E-41)
            r3 = 49152(0xc000, float:6.8877E-41)
            if (r6 != 0) goto L27
            r4 = r4 & r3
            if (r4 == r2) goto L24
            if (r4 == r1) goto L21
            if (r4 == r3) goto L1e
            goto L46
        L1e:
            int r0 = r0 + 12
            goto L46
        L21:
            int r0 = r0 + 25
            goto L46
        L24:
            int r0 = r0 + 50
            goto L46
        L27:
            if (r5 != 0) goto L31
            r4 = r4 & r3
            if (r4 == r2) goto L24
            if (r4 == r1) goto L21
            if (r4 == r3) goto L1e
            goto L46
        L31:
            r4 = r4 & r3
            if (r4 == 0) goto L44
            if (r4 == r2) goto L41
            if (r4 == r1) goto L3e
            if (r4 == r3) goto L3b
            goto L46
        L3b:
            int r0 = r0 + 87
            goto L46
        L3e:
            int r0 = r0 + 75
            goto L46
        L41:
            int r0 = r0 + 62
            goto L46
        L44:
            int r0 = r0 + 37
        L46:
            r4 = 300000000(0x11e1a300, float:3.5599197E-28)
            int r4 = r4 / r0
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ftdi.j2xx.FT_BaudRate.FT_CalcBaudRate(int, int, boolean):int");
    }

    static final byte FT_GetDivisorHi(int i, int[] iArr) {
        int i2;
        int i3;
        byte bFT_CalcDivisorHi = FT_CalcDivisorHi(i, iArr);
        if (bFT_CalcDivisorHi == -1) {
            return (byte) -1;
        }
        if (bFT_CalcDivisorHi == 0) {
            iArr[0] = (iArr[0] & (-49153)) + 1;
        }
        int iFT_CalcBaudRateHi = FT_CalcBaudRateHi(iArr[0], iArr[1]);
        if (i > iFT_CalcBaudRateHi) {
            i2 = ((i * 100) / iFT_CalcBaudRateHi) - 100;
            i3 = ((i % iFT_CalcBaudRateHi) * 100) % iFT_CalcBaudRateHi;
        } else {
            i2 = ((iFT_CalcBaudRateHi * 100) / i) - 100;
            i3 = ((iFT_CalcBaudRateHi % i) * 100) % i;
        }
        if (i2 < 3) {
            return (byte) 1;
        }
        return (i2 == 3 && i3 == 0) ? (byte) 1 : (byte) 0;
    }

    private static byte FT_CalcDivisorHi(int i, int[] iArr) {
        if (i == 0) {
            return (byte) -1;
        }
        int i2 = FT_CLOCK_RATE_HI / i;
        if ((i2 & (-16384)) > 0) {
            return (byte) -1;
        }
        byte b = 1;
        iArr[1] = 2;
        if (i >= 11640000 && i <= 12360000) {
            iArr[0] = 0;
            return (byte) 1;
        }
        if (i >= 7760000 && i <= 8240000) {
            iArr[0] = 1;
            return (byte) 1;
        }
        iArr[0] = i2;
        iArr[1] = 2;
        if (iArr[0] == 1 && ((FT_CLOCK_RATE_HI % i) * 100) / i <= 3) {
            iArr[0] = 0;
        }
        if (iArr[0] == 0) {
            return (byte) 1;
        }
        int i3 = ((FT_CLOCK_RATE_HI % i) * 100) / i;
        int i4 = 16384;
        if (i3 <= 6) {
            i4 = 0;
        } else if (i3 <= 18) {
            i4 = 49152;
        } else if (i3 > 31) {
            if (i3 <= 43) {
                iArr[1] = iArr[1] | 1;
            } else if (i3 > 56) {
                if (i3 <= 68) {
                    iArr[1] = iArr[1] | 1;
                } else if (i3 <= 81) {
                    iArr[1] = iArr[1] | 1;
                    i4 = 32768;
                } else if (i3 <= 93) {
                    iArr[1] = iArr[1] | 1;
                    i4 = 49152;
                } else {
                    b = 0;
                }
            }
            i4 = 0;
        } else {
            i4 = 32768;
        }
        iArr[0] = iArr[0] | i4;
        return b;
    }

    private static int FT_CalcBaudRateHi(int i, int i2) {
        if (i == 0) {
            return FT_CLOCK_RATE_HI;
        }
        if (i == 1) {
            return GmsVersion.VERSION_SAGA;
        }
        int i3 = ((-49153) & i) * 100;
        if ((i2 & 65533) == 0) {
            int i4 = i & 49152;
            if (i4 == 16384) {
                i3 += 50;
            } else if (i4 == 32768) {
                i3 += 25;
            } else if (i4 == 49152) {
                i3 += 12;
            }
        } else {
            int i5 = i & 49152;
            if (i5 == 0) {
                i3 += 37;
            } else if (i5 == 16384) {
                i3 += 62;
            } else if (i5 == 32768) {
                i3 += 75;
            } else if (i5 == 49152) {
                i3 += 87;
            }
        }
        return 1200000000 / i3;
    }
}
