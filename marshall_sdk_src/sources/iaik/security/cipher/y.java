package iaik.security.cipher;

import androidx.core.app.FrameMetricsAggregator;
import iaik.utils.CriticalObject;
import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import java.security.InvalidKeyException;

/* JADX INFO: loaded from: classes.dex */
final class y extends AbstractC0028i {
    private static final int[] m = {-1532439173, 1532827963, -928501605, 1945741688};
    private static final int[] n;
    private static final int[] o;
    private static final int[] p;
    private static final int[] q;
    private final int[] b;
    private final int[] c;

    static {
        int[] iArrDecodeIntArray = Util.decodeIntArray("CdDEeSjI/+CEqmw5na1yh33/m+PUJoNhyW2h1Hl0zJOF0FguKktXBRyhamLDvSedDx8l5VFgNy/GlcH7TX/x5K5fa/QNcu5G/yPeirHPjoPxSQLiPpgeQov1PrZ/S/isg2MfgyWXAgV2r+eEOnkx1E+EZFBcZMP2IQpfGMaYaiYo9OgmOmCoHNNApmR+qCDEUmaHxX7d0SsyoR0dnJ7whoD26DGrbwStVvubU4suCVy2hVau0iULDSlKdyHiH7JTrhNnSegqroaTNlEEmUBKZninhNy2m6hLBARnkyPbXB5GyuHWL+KBNFoiOUIYY81bwZDG4wffuEZuuIgWLQ3MSqTMrlk3mGcNy/qUk09IHUXq/Iyo2xEp1rBEniAPVAf7YWfZqNH0V2NNqpbDO+xZWKuroBS2zNIBONYnnwJoIhWPN2zVCSwjfr/FZZMyiJ0shUs+lQW7m0N9zV3NoC6SbPrlJ+U2ocMwNBLhrvJX9GI8Tx1xMKLoCWjl9VGcYbpEXe0KuHXOCciWVPk+aYwMyiQ8s+QrBiuXDzuNngDgUN/8XWFm41+SiMB5VQ0Fka7ojlMedHX+NXgvbYKa9gshrpXo641mmUhrkB19m/1tbjEQkKzv4GcN2Nqy5pLNbUNl5Tk1FDrzRfBiQfxNRg2jo3vPNymL8dHgFKrAcBWH7VU6/X0+0vKeASmp0fbvsQxTzzuHD7QUk1xmRGXtAkrKx1mnRMEdKTan3FgKps9XTKgECnoQbNgYB4qYvkyszqBjwz6StdHg4D2zIlF+IJK9EzhrLEpS6N1YWGVt+1CCA3FBgRiW4zfvftOfsRnJfw32aP6gG6FQpuVVJYli62/0G9fJzXqmGc2evPCVdiZywHPwA/s8SrelCxSEEmpIe6mxpk/JxvaVfUk4sGp13YBfzWPQlM/1HJmeGqTTQ7hJUpTOn46Zv/zXcMfCdcw3hFOneyG+Mzl/Qb1OlNExkswfmFkV6lGZ+GG3yZgKiB10/V+wpJX4YU3u0LV3jupZQXkt+pDB+DP4JLTEllNyP/bVUEyl/sCGMOlkWz+71n2iakiyAyMaBCl1FC1jkwYusTFJFqRSclMkWaCOX0hy+WbH2QcSjcANRNtir8jVLQYxYTHYOOfOG8QdADoujA/qg4N+uYRzfRO6SJHE+LlJptass6IVzc6DWYOLa9GqMfV53VIhuT+T9RdngRh9/d7pSut2Kzj9VEMd4dqrOUglmtMEj9/qMqpllHPjYj94Y/M0bFmrOraFM0apC2tWRD7G3gH4jUIfwJsO0QyI8aHpVMHwKX3q1XuNe6QmTPUXilUafMoaml8I/NZRuSVgUYLhH8bDtv2WdjN7MCe3yOsUnl/QMGtX41StkTz3fhZojViHKmksL8ff44nMxjBzjfEIJKc04Xl6i6So1XtbXRk7yKgwm3P5qXhzOY0yD1lXPunfKwPopbbIhI0HBJjfk8JyCh3DaE8lmpQ7qEimNwFShjteo9F7l4ttm1jvCnAN1Kc9Nr+OaggphpW8FONbNEeTOsVoiJSwIi9RHCfd+8w8AGZithF8g/5OErQUwrynZjov7BD0ViQgVXkuKkb12FfO2iXOw2AdO2wAq0bvrJwos8NQR2Ed/uMlfDIH/dWEgjsU2E8jvstkoHXzowiPjq0HrfFYd5aUPPrKvz3AlzDN92eZadpE6e0shUwSNZNfoy8FfZ9pBiT4HLC6/XsNvcaBDyO7+pKaGm2WmhdnQpebdKx9BQEOZcSGo9lj+Qe1oNAEK9MVjX0DKHqCVbuoNm8JbtwzIZFqe3e1a4aVFiL5psXmUIzqF9HNjGK8o9Y0MzWKaP0Pm5081qopW/4zOErAAHOOzWfrL+LrbcKXM4sCBsnyRkGc8a0rg8BFNyPxistbMIkWC+rXXUlGVjX4p0seTmyeAAOZvWdGaIC0F0gxrPQjssqBWrNaY5XnMCpnxYvbRGsQj4+kECI+2pK4tIt/ONDuqycB1AJi1BWvIkows9iKuviyw6/a9+9wzJfTt+lhS2wrrr/0cPaHzzhskVbOCS7lAeh9pmzpHmq7e8yEx5IsIJ07cf0GDkHG11kPFU4Du0cYPBmOY+6yQC3b9JptXLpUkjdQr/nhQjZ4OBYrWXJscoG2Z2C7KSbBSKDODabASW2tQ1B7cY1Jap3wV69Esb3mBUNW3N587TXVGhOLYgiMyTWDAxHJbvyiaG+G7I53y2hj4da4yA+XeHnEkf0bTGfycmmNfV42jDH32V4uodNJP9zZQz6JbxVSS8TKeqbRuvSlqW3MC++LRqFp/ad030C3TiCIBJp1ZgcDjofIICEeRIt61L/GQD81GEjjbYC9sDgeYokcZD0hB78E1vghCSyM9kTziQd4QE57eK24osUtU0IVer6iJT4ue/P0roD1lPmVMZTnd+uS7bOBaTDajZM2v0R0afJtlIPub67VcTcSNd5CX3O05Z9Dfb4tTi03sYVJ3JpjmMOdmBMByaI4mxu/DBhYjaQhwbp6o4ZcceCFWDxc/Kp9I5ykApfZ3dfcKDBLN4ArdCirVK7uA0dLP7uFaS8vCBNOV4422eC/rotfz+25Ps8rJySOFw6x733Ff9Yedg8WsRNmAYZOG5vX6nMZOrhxvc+k12/jG9eCDb60aau5YGFTcPhd/7B+N9ow0PvryXe2C5i0DzpND+bfT8JrFZzyKsKY1uIreO9qYalKwKtWEYcU7qDw3w1BZBmvcO4=");
        n = iArrDecodeIntArray;
        o = iArrDecodeIntArray;
        int[] iArr = new int[256];
        p = iArr;
        int i = 0;
        System.arraycopy(iArrDecodeIntArray, 256, iArr, 0, 256);
        q = new int[37];
        while (true) {
            int[] iArr2 = q;
            if (i >= iArr2.length) {
                return;
            }
            iArr2[i] = i % 15;
            i++;
        }
    }

    y() {
        super("MARS", 2);
        this.b = new int[40];
        this.c = new int[15];
    }

    private static final int a(int i) {
        int i2 = ((i >>> 1) ^ (~i)) & Integer.MAX_VALUE;
        int i3 = i2 & (i2 >>> 1) & (i2 >>> 2);
        int i4 = i3 & (i3 >>> 3) & (i3 >>> 6);
        if (i4 == 0) {
            return 0;
        }
        int i5 = i4 << 1;
        int i6 = i5 | (i5 << 1);
        int i7 = i6 | (i6 << 2);
        return (i7 | (i7 << 4)) & 2147483644;
    }

    private static int b(int i, int i2) {
        int i3 = i2 & 31;
        return (i >>> (32 - i3)) | (i << i3);
    }

    @Override // iaik.security.cipher.AbstractC0028i
    protected void a(int i, byte[] bArr) throws InvalidKeyException {
        if ((bArr.length & 3) != 0 || bArr.length < 16 || bArr.length > 56) {
            throw new InvalidKeyException("Key length in bits must be a multiple of 32 and between 128 and 448!");
        }
        int[] iArr = this.c;
        int length = bArr.length >> 2;
        CryptoUtils.squashBytesToIntsLE(bArr, 0, iArr, 0, length);
        iArr[length] = length;
        for (int i2 = 0; i2 <= 3; i2++) {
            int i3 = i2;
            int i4 = 0;
            while (i4 <= 14) {
                int i5 = iArr[i4];
                int[] iArr2 = q;
                iArr[i4] = i5 ^ (b(iArr[iArr2[i4 + 13]] ^ iArr[iArr2[i4 + 8]], 3) ^ i3);
                i4++;
                i3 += 4;
            }
            for (int i6 = 0; i6 < 4; i6++) {
                for (int i7 = 0; i7 <= 14; i7++) {
                    iArr[i7] = b(iArr[i7] + n[iArr[q[i7 + 14]] & FrameMetricsAggregator.EVERY_DURATION], 9);
                }
            }
            int i8 = i2 * 10;
            for (int i9 = 0; i9 <= 36; i9 += 4) {
                this.b[i8] = iArr[q[i9]];
                i8++;
            }
        }
        for (int i10 = 5; i10 <= 35; i10 += 2) {
            int[] iArr3 = this.b;
            int i11 = iArr3[i10];
            int i12 = i11 | 3;
            this.b[i10] = (b(m[i11 & 3], iArr3[i10 - 1]) & a(i12)) ^ i12;
        }
        CriticalObject.destroy(iArr);
    }

    @Override // iaik.security.cipher.AbstractC0028i
    protected void b() {
        int i = this.a[0] + this.b[0];
        int i2 = this.a[1] + this.b[1];
        int i3 = this.a[2] + this.b[2];
        int i4 = this.a[3] + this.b[3];
        for (int i5 = 0; i5 <= 7; i5 += 4) {
            int[] iArr = o;
            int i6 = i2 ^ iArr[i & 255];
            int[] iArr2 = p;
            int i7 = i6 + iArr2[(i >>> 8) & 255];
            int i8 = i3 + iArr[(i >>> 16) & 255];
            int i9 = i >>> 24;
            int i10 = i4 ^ iArr2[i9];
            int i11 = ((i << 8) | i9) + i10;
            int i12 = (i8 ^ iArr[i7 & 255]) + iArr2[(i7 >>> 8) & 255];
            int i13 = i10 + iArr[(i7 >>> 16) & 255];
            int i14 = i7 >>> 24;
            int i15 = i11 ^ iArr2[i14];
            int i16 = ((i7 << 8) | i14) + i12;
            int i17 = (i13 ^ iArr[i12 & 255]) + iArr2[(i12 >>> 8) & 255];
            int i18 = i15 + iArr[(i12 >>> 16) & 255];
            int i19 = i12 >>> 24;
            int i20 = i16 ^ iArr2[i19];
            i = (i18 ^ iArr[i17 & 255]) + iArr2[(i17 >>> 8) & 255];
            i2 = i20 + iArr[(i17 >>> 16) & 255];
            int i21 = i17 >>> 24;
            i3 = ((i12 << 8) | i19) ^ iArr2[i21];
            i4 = (i17 << 8) | i21;
        }
        int[] iArr3 = this.b;
        int i22 = iArr3[4] + i;
        int i23 = (i >>> 19) | (i << 13);
        int i24 = iArr3[5] * i23;
        int[] iArr4 = n;
        int i25 = iArr4[i22 & FrameMetricsAggregator.EVERY_DURATION];
        int i26 = (i24 << 5) | (i24 >>> 27);
        int iB = i3 + b(i22, i26);
        int i27 = i25 ^ i26;
        int i28 = (i26 >>> 27) | (i26 << 5);
        int iB2 = i2 + b(i27 ^ i28, i28);
        int i29 = i4 ^ i28;
        int[] iArr5 = this.b;
        int i30 = iArr5[6] + iB2;
        int i31 = (iB2 >>> 19) | (iB2 << 13);
        int i32 = iArr5[7] * i31;
        int i33 = iArr4[i30 & FrameMetricsAggregator.EVERY_DURATION];
        int i34 = (i32 << 5) | (i32 >>> 27);
        int iB3 = i29 + b(i30, i34);
        int i35 = i33 ^ i34;
        int i36 = (i34 >>> 27) | (i34 << 5);
        int iB4 = iB + b(i35 ^ i36, i36);
        int i37 = i23 ^ i36;
        int[] iArr6 = this.b;
        int i38 = iArr6[8] + iB4;
        int i39 = (iB4 >>> 19) | (iB4 << 13);
        int i40 = iArr6[9] * i39;
        int i41 = iArr4[i38 & FrameMetricsAggregator.EVERY_DURATION];
        int i42 = (i40 >>> 27) | (i40 << 5);
        int iB5 = i37 + b(i38, i42);
        int i43 = i41 ^ i42;
        int i44 = (i42 >>> 27) | (i42 << 5);
        int iB6 = iB3 + b(i43 ^ i44, i44);
        int i45 = i31 ^ i44;
        int[] iArr7 = this.b;
        int i46 = iArr7[10] + iB6;
        int i47 = (iB6 >>> 19) | (iB6 << 13);
        int i48 = iArr7[11] * i47;
        int i49 = iArr4[i46 & FrameMetricsAggregator.EVERY_DURATION];
        int i50 = (i48 >>> 27) | (i48 << 5);
        int iB7 = i45 + b(i46, i50);
        int i51 = i49 ^ i50;
        int i52 = (i50 >>> 27) | (i50 << 5);
        int iB8 = iB5 + b(i51 ^ i52, i52);
        int i53 = i39 ^ i52;
        int[] iArr8 = this.b;
        int i54 = iArr8[12] + iB8;
        int i55 = (iB8 >>> 19) | (iB8 << 13);
        int i56 = iArr8[13] * i55;
        int i57 = iArr4[i54 & FrameMetricsAggregator.EVERY_DURATION];
        int i58 = (i56 >>> 27) | (i56 << 5);
        int iB9 = i53 + b(i54, i58);
        int i59 = i57 ^ i58;
        int i60 = (i58 >>> 27) | (i58 << 5);
        int iB10 = iB7 + b(i59 ^ i60, i60);
        int i61 = i47 ^ i60;
        int[] iArr9 = this.b;
        int i62 = iArr9[14] + iB10;
        int i63 = (iB10 >>> 19) | (iB10 << 13);
        int i64 = iArr9[15] * i63;
        int i65 = iArr4[i62 & FrameMetricsAggregator.EVERY_DURATION];
        int i66 = (i64 >>> 27) | (i64 << 5);
        int iB11 = i61 + b(i62, i66);
        int i67 = i65 ^ i66;
        int i68 = (i66 >>> 27) | (i66 << 5);
        int iB12 = iB9 + b(i67 ^ i68, i68);
        int i69 = i55 ^ i68;
        int[] iArr10 = this.b;
        int i70 = iArr10[16] + iB12;
        int i71 = (iB12 >>> 19) | (iB12 << 13);
        int i72 = iArr10[17] * i71;
        int i73 = iArr4[i70 & FrameMetricsAggregator.EVERY_DURATION];
        int i74 = (i72 >>> 27) | (i72 << 5);
        int iB13 = i69 + b(i70, i74);
        int i75 = i73 ^ i74;
        int i76 = (i74 >>> 27) | (i74 << 5);
        int iB14 = iB11 + b(i75 ^ i76, i76);
        int i77 = i63 ^ i76;
        int[] iArr11 = this.b;
        int i78 = iArr11[18] + iB14;
        int i79 = (iB14 >>> 19) | (iB14 << 13);
        int i80 = iArr11[19] * i79;
        int i81 = iArr4[i78 & FrameMetricsAggregator.EVERY_DURATION];
        int i82 = (i80 >>> 27) | (i80 << 5);
        int iB15 = i77 + b(i78, i82);
        int i83 = i81 ^ i82;
        int i84 = (i82 >>> 27) | (i82 << 5);
        int iB16 = iB13 + b(i83 ^ i84, i84);
        int i85 = i71 ^ i84;
        int[] iArr12 = this.b;
        int i86 = iArr12[20] + iB16;
        int i87 = (iB16 >>> 19) | (iB16 << 13);
        int i88 = iArr12[21] * i87;
        int i89 = iArr4[i86 & FrameMetricsAggregator.EVERY_DURATION];
        int i90 = (i88 >>> 27) | (i88 << 5);
        int iB17 = i85 + b(i86, i90);
        int i91 = i89 ^ i90;
        int i92 = (i90 >>> 27) | (i90 << 5);
        int iB18 = i79 + b(i91 ^ i92, i92);
        int i93 = iB15 ^ i92;
        int[] iArr13 = this.b;
        int i94 = iArr13[22] + i93;
        int i95 = (i93 >>> 19) | (i93 << 13);
        int i96 = iArr13[23] * i95;
        int i97 = iArr4[i94 & FrameMetricsAggregator.EVERY_DURATION];
        int i98 = (i96 >>> 27) | (i96 << 5);
        int iB19 = iB18 + b(i94, i98);
        int i99 = i97 ^ i98;
        int i100 = (i98 >>> 27) | (i98 << 5);
        int iB20 = i87 + b(i99 ^ i100, i100);
        int i101 = iB17 ^ i100;
        int[] iArr14 = this.b;
        int i102 = iArr14[24] + i101;
        int i103 = (i101 >>> 19) | (i101 << 13);
        int i104 = iArr14[25] * i103;
        int i105 = iArr4[i102 & FrameMetricsAggregator.EVERY_DURATION];
        int i106 = (i104 << 5) | (i104 >>> 27);
        int iB21 = iB20 + b(i102, i106);
        int i107 = i105 ^ i106;
        int i108 = (i106 >>> 27) | (i106 << 5);
        int iB22 = i95 + b(i107 ^ i108, i108);
        int i109 = iB19 ^ i108;
        int[] iArr15 = this.b;
        int i110 = iArr15[26] + i109;
        int i111 = (i109 >>> 19) | (i109 << 13);
        int i112 = iArr15[27] * i111;
        int i113 = iArr4[i110 & FrameMetricsAggregator.EVERY_DURATION];
        int i114 = (i112 << 5) | (i112 >>> 27);
        int iB23 = iB22 + b(i110, i114);
        int i115 = i113 ^ i114;
        int i116 = (i114 >>> 27) | (i114 << 5);
        int iB24 = i103 + b(i115 ^ i116, i116);
        int i117 = iB21 ^ i116;
        int[] iArr16 = this.b;
        int i118 = iArr16[28] + i117;
        int i119 = (i117 >>> 19) | (i117 << 13);
        int i120 = iArr16[29] * i119;
        int i121 = iArr4[i118 & FrameMetricsAggregator.EVERY_DURATION];
        int i122 = (i120 << 5) | (i120 >>> 27);
        int iB25 = iB24 + b(i118, i122);
        int i123 = i121 ^ i122;
        int i124 = (i122 >>> 27) | (i122 << 5);
        int iB26 = i111 + b(i123 ^ i124, i124);
        int i125 = iB23 ^ i124;
        int[] iArr17 = this.b;
        int i126 = iArr17[30] + i125;
        int i127 = (i125 >>> 19) | (i125 << 13);
        int i128 = iArr17[31] * i127;
        int i129 = iArr4[i126 & FrameMetricsAggregator.EVERY_DURATION];
        int i130 = (i128 << 5) | (i128 >>> 27);
        int iB27 = iB26 + b(i126, i130);
        int i131 = i129 ^ i130;
        int i132 = (i130 >>> 27) | (i130 << 5);
        int iB28 = i119 + b(i131 ^ i132, i132);
        int i133 = iB25 ^ i132;
        int[] iArr18 = this.b;
        int i134 = iArr18[32] + i133;
        int i135 = (i133 >>> 19) | (i133 << 13);
        int i136 = iArr18[33] * i135;
        int i137 = iArr4[i134 & FrameMetricsAggregator.EVERY_DURATION];
        int i138 = (i136 << 5) | (i136 >>> 27);
        int iB29 = iB28 + b(i134, i138);
        int i139 = i137 ^ i138;
        int i140 = (i138 >>> 27) | (i138 << 5);
        int iB30 = i127 + b(i139 ^ i140, i140);
        int i141 = iB27 ^ i140;
        int[] iArr19 = this.b;
        int i142 = iArr19[34] + i141;
        int i143 = (i141 >>> 19) | (i141 << 13);
        int i144 = iArr19[35] * i143;
        int i145 = iArr4[i142 & FrameMetricsAggregator.EVERY_DURATION];
        int i146 = (i144 >>> 27) | (i144 << 5);
        int iB31 = iB30 + b(i142, i146);
        int i147 = i145 ^ i146;
        int i148 = (i146 >>> 27) | (i146 << 5);
        int iB32 = i135 + b(i147 ^ i148, i148);
        int i149 = iB29 ^ i148;
        for (int i150 = 0; i150 <= 7; i150 += 4) {
            int[] iArr20 = p;
            int i151 = iB31 ^ iArr20[i149 & 255];
            int[] iArr21 = o;
            int i152 = iB32 - iArr21[i149 >>> 24];
            int i153 = i149 >>> 8;
            int i154 = (i143 - iArr20[(i149 >>> 16) & 255]) ^ iArr21[i153 & 255];
            int i155 = i152 ^ iArr20[i151 & 255];
            int i156 = i154 - iArr21[i151 >>> 24];
            int i157 = ((i149 << 24) | i153) - iArr20[(i151 >>> 16) & 255];
            int i158 = i151 >>> 8;
            int i159 = i157 ^ iArr21[i158 & 255];
            int i160 = (i151 << 24) | i158;
            int i161 = i155 - i160;
            int i162 = i156 ^ iArr20[i161 & 255];
            int i163 = i159 - iArr21[i161 >>> 24];
            int i164 = i161 >>> 8;
            int i165 = (i160 - iArr20[(i161 >>> 16) & 255]) ^ iArr21[i164 & 255];
            int i166 = i162 - i163;
            i149 = i163 ^ iArr20[i166 & 255];
            iB31 = i165 - iArr21[i166 >>> 24];
            int i167 = ((i161 << 24) | i164) - iArr20[(i166 >>> 16) & 255];
            int i168 = i166 >>> 8;
            iB32 = i167 ^ iArr21[i168 & 255];
            i143 = (i166 << 24) | i168;
        }
        this.a[0] = i149 - this.b[36];
        this.a[1] = iB31 - this.b[37];
        this.a[2] = iB32 - this.b[38];
        this.a[3] = i143 - this.b[39];
    }

    @Override // iaik.security.cipher.AbstractC0028i
    protected void c() {
        int i = this.a[0] + this.b[36];
        int i2 = this.a[1] + this.b[37];
        int i3 = this.a[2] + this.b[38];
        int i4 = this.a[3] + this.b[39];
        for (int i5 = 7; i5 >= 0; i5 -= 4) {
            int i6 = (i4 << 8) | (i4 >>> 24);
            int[] iArr = o;
            int i7 = i3 ^ iArr[(i6 >>> 8) & 255];
            int[] iArr2 = p;
            int i8 = i7 + iArr2[(i6 >>> 16) & 255];
            int i9 = i2 + iArr[i6 >>> 24];
            int i10 = i ^ iArr2[i6 & 255];
            int i11 = i6 + i10;
            int i12 = (i8 << 8) | (i8 >>> 24);
            int i13 = (i9 ^ iArr[(i12 >>> 8) & 255]) + iArr2[(i12 >>> 16) & 255];
            int i14 = i10 + iArr[i12 >>> 24];
            int i15 = i11 ^ iArr2[i12 & 255];
            int i16 = i12 + i13;
            int i17 = (i13 << 8) | (i13 >>> 24);
            int i18 = (i14 ^ iArr[(i17 >>> 8) & 255]) + iArr2[(i17 >>> 16) & 255];
            int i19 = i15 + iArr[i17 >>> 24];
            int i20 = i16 ^ iArr2[i17 & 255];
            i = (i18 << 8) | (i18 >>> 24);
            i4 = (i19 ^ iArr[(i >>> 8) & 255]) + iArr2[(i >>> 16) & 255];
            i3 = i20 + iArr[i >>> 24];
            i2 = i17 ^ iArr2[i & 255];
        }
        int[] iArr3 = this.b;
        int i21 = iArr3[35] * i4;
        int i22 = (i4 << 19) | (i4 >>> 13);
        int i23 = iArr3[34] + i22;
        int[] iArr4 = n;
        int i24 = iArr4[i23 & FrameMetricsAggregator.EVERY_DURATION];
        int i25 = (i21 << 5) | (i21 >>> 27);
        int iB = i2 - b(i23, i25);
        int i26 = i24 ^ i25;
        int i27 = (i25 >>> 27) | (i25 << 5);
        int iB2 = i3 - b(i26 ^ i27, i27);
        int i28 = i ^ i27;
        int[] iArr5 = this.b;
        int i29 = iArr5[33] * iB2;
        int i30 = (iB2 << 19) | (iB2 >>> 13);
        int i31 = iArr5[32] + i30;
        int i32 = iArr4[i31 & FrameMetricsAggregator.EVERY_DURATION];
        int i33 = (i29 << 5) | (i29 >>> 27);
        int iB3 = i28 - b(i31, i33);
        int i34 = i32 ^ i33;
        int i35 = (i33 >>> 27) | (i33 << 5);
        int iB4 = iB - b(i34 ^ i35, i35);
        int i36 = i22 ^ i35;
        int[] iArr6 = this.b;
        int i37 = iArr6[31] * iB4;
        int i38 = (iB4 << 19) | (iB4 >>> 13);
        int i39 = iArr6[30] + i38;
        int i40 = iArr4[i39 & FrameMetricsAggregator.EVERY_DURATION];
        int i41 = (i37 << 5) | (i37 >>> 27);
        int iB5 = i36 - b(i39, i41);
        int i42 = i40 ^ i41;
        int i43 = (i41 >>> 27) | (i41 << 5);
        int iB6 = iB3 - b(i42 ^ i43, i43);
        int i44 = i30 ^ i43;
        int[] iArr7 = this.b;
        int i45 = iArr7[29] * iB6;
        int i46 = (iB6 << 19) | (iB6 >>> 13);
        int i47 = iArr7[28] + i46;
        int i48 = iArr4[i47 & FrameMetricsAggregator.EVERY_DURATION];
        int i49 = (i45 << 5) | (i45 >>> 27);
        int iB7 = i44 - b(i47, i49);
        int i50 = i48 ^ i49;
        int i51 = (i49 >>> 27) | (i49 << 5);
        int iB8 = iB5 - b(i50 ^ i51, i51);
        int i52 = i38 ^ i51;
        int[] iArr8 = this.b;
        int i53 = iArr8[27] * iB8;
        int i54 = (iB8 << 19) | (iB8 >>> 13);
        int i55 = iArr8[26] + i54;
        int i56 = iArr4[i55 & FrameMetricsAggregator.EVERY_DURATION];
        int i57 = (i53 << 5) | (i53 >>> 27);
        int iB9 = i52 - b(i55, i57);
        int i58 = i56 ^ i57;
        int i59 = (i57 >>> 27) | (i57 << 5);
        int iB10 = iB7 - b(i58 ^ i59, i59);
        int i60 = i46 ^ i59;
        int[] iArr9 = this.b;
        int i61 = iArr9[25] * iB10;
        int i62 = (iB10 << 19) | (iB10 >>> 13);
        int i63 = iArr9[24] + i62;
        int i64 = iArr4[i63 & FrameMetricsAggregator.EVERY_DURATION];
        int i65 = (i61 << 5) | (i61 >>> 27);
        int iB11 = i60 - b(i63, i65);
        int i66 = i64 ^ i65;
        int i67 = (i65 >>> 27) | (i65 << 5);
        int iB12 = iB9 - b(i66 ^ i67, i67);
        int i68 = i54 ^ i67;
        int[] iArr10 = this.b;
        int i69 = iArr10[23] * iB12;
        int i70 = (iB12 << 19) | (iB12 >>> 13);
        int i71 = iArr10[22] + i70;
        int i72 = iArr4[i71 & FrameMetricsAggregator.EVERY_DURATION];
        int i73 = (i69 << 5) | (i69 >>> 27);
        int iB13 = i68 - b(i71, i73);
        int i74 = i72 ^ i73;
        int i75 = (i73 >>> 27) | (i73 << 5);
        int iB14 = iB11 - b(i74 ^ i75, i75);
        int i76 = i62 ^ i75;
        int[] iArr11 = this.b;
        int i77 = iArr11[21] * iB14;
        int i78 = (iB14 << 19) | (iB14 >>> 13);
        int i79 = iArr11[20] + i78;
        int i80 = iArr4[i79 & FrameMetricsAggregator.EVERY_DURATION];
        int i81 = (i77 << 5) | (i77 >>> 27);
        int iB15 = i76 - b(i79, i81);
        int i82 = i80 ^ i81;
        int i83 = (i81 >>> 27) | (i81 << 5);
        int iB16 = iB13 - b(i82 ^ i83, i83);
        int i84 = i70 ^ i83;
        int[] iArr12 = this.b;
        int i85 = iArr12[19] * iB16;
        int i86 = (iB16 << 19) | (iB16 >>> 13);
        int i87 = iArr12[18] + i86;
        int i88 = iArr4[i87 & FrameMetricsAggregator.EVERY_DURATION];
        int i89 = (i85 << 5) | (i85 >>> 27);
        int iB17 = i84 - b(i87, i89);
        int i90 = i88 ^ i89;
        int i91 = (i89 >>> 27) | (i89 << 5);
        int iB18 = i78 - b(i90 ^ i91, i91);
        int i92 = iB15 ^ i91;
        int[] iArr13 = this.b;
        int i93 = iArr13[17] * i92;
        int i94 = (i92 << 19) | (i92 >>> 13);
        int i95 = iArr13[16] + i94;
        int i96 = iArr4[i95 & FrameMetricsAggregator.EVERY_DURATION];
        int i97 = (i93 << 5) | (i93 >>> 27);
        int iB19 = iB18 - b(i95, i97);
        int i98 = i96 ^ i97;
        int i99 = (i97 >>> 27) | (i97 << 5);
        int iB20 = i86 - b(i98 ^ i99, i99);
        int i100 = iB17 ^ i99;
        int[] iArr14 = this.b;
        int i101 = iArr14[15] * i100;
        int i102 = (i100 << 19) | (i100 >>> 13);
        int i103 = iArr14[14] + i102;
        int i104 = iArr4[i103 & FrameMetricsAggregator.EVERY_DURATION];
        int i105 = (i101 << 5) | (i101 >>> 27);
        int iB21 = iB20 - b(i103, i105);
        int i106 = i104 ^ i105;
        int i107 = (i105 >>> 27) | (i105 << 5);
        int iB22 = i94 - b(i106 ^ i107, i107);
        int i108 = iB19 ^ i107;
        int[] iArr15 = this.b;
        int i109 = iArr15[13] * i108;
        int i110 = (i108 << 19) | (i108 >>> 13);
        int i111 = iArr15[12] + i110;
        int i112 = iArr4[i111 & FrameMetricsAggregator.EVERY_DURATION];
        int i113 = (i109 << 5) | (i109 >>> 27);
        int iB23 = iB22 - b(i111, i113);
        int i114 = i112 ^ i113;
        int i115 = (i113 >>> 27) | (i113 << 5);
        int iB24 = i102 - b(i114 ^ i115, i115);
        int i116 = iB21 ^ i115;
        int[] iArr16 = this.b;
        int i117 = iArr16[11] * i116;
        int i118 = (i116 << 19) | (i116 >>> 13);
        int i119 = iArr16[10] + i118;
        int i120 = iArr4[i119 & FrameMetricsAggregator.EVERY_DURATION];
        int i121 = (i117 << 5) | (i117 >>> 27);
        int iB25 = iB24 - b(i119, i121);
        int i122 = i120 ^ i121;
        int i123 = (i121 >>> 27) | (i121 << 5);
        int iB26 = i110 - b(i122 ^ i123, i123);
        int i124 = iB23 ^ i123;
        int[] iArr17 = this.b;
        int i125 = iArr17[9] * i124;
        int i126 = (i124 << 19) | (i124 >>> 13);
        int i127 = iArr17[8] + i126;
        int i128 = iArr4[i127 & FrameMetricsAggregator.EVERY_DURATION];
        int i129 = (i125 << 5) | (i125 >>> 27);
        int iB27 = iB26 - b(i127, i129);
        int i130 = i128 ^ i129;
        int i131 = (i129 >>> 27) | (i129 << 5);
        int iB28 = i118 - b(i130 ^ i131, i131);
        int i132 = iB25 ^ i131;
        int[] iArr18 = this.b;
        int i133 = iArr18[7] * i132;
        int i134 = (i132 << 19) | (i132 >>> 13);
        int i135 = iArr18[6] + i134;
        int i136 = iArr4[i135 & FrameMetricsAggregator.EVERY_DURATION];
        int i137 = (i133 << 5) | (i133 >>> 27);
        int iB29 = iB28 - b(i135, i137);
        int i138 = i136 ^ i137;
        int i139 = (i137 >>> 27) | (i137 << 5);
        int iB30 = i126 - b(i138 ^ i139, i139);
        int i140 = iB27 ^ i139;
        int[] iArr19 = this.b;
        int i141 = iArr19[5] * i140;
        int i142 = (i140 << 19) | (i140 >>> 13);
        int i143 = iArr19[4] + i142;
        int i144 = iArr4[i143 & FrameMetricsAggregator.EVERY_DURATION];
        int i145 = (i141 >>> 27) | (i141 << 5);
        int iB31 = iB30 - b(i143, i145);
        int i146 = i144 ^ i145;
        int i147 = (i145 >>> 27) | (i145 << 5);
        int iB32 = i134 - b(i146 ^ i147, i147);
        int i148 = iB29 ^ i147;
        for (int i149 = 7; i149 >= 0; i149 -= 4) {
            int i150 = (i148 >>> 8) | (i148 << 24);
            int[] iArr20 = p;
            int i151 = iB31 ^ iArr20[i150 >>> 24];
            int[] iArr21 = o;
            int i152 = iB32 - iArr21[(i150 >>> 16) & 255];
            int i153 = (i142 - iArr20[(i150 >>> 8) & 255]) ^ iArr21[i150 & 255];
            int i154 = (i151 >>> 8) | (i151 << 24);
            int i155 = i152 ^ iArr20[i154 >>> 24];
            int i156 = i153 - iArr21[(i154 >>> 16) & 255];
            int i157 = (i150 - iArr20[(i154 >>> 8) & 255]) ^ iArr21[i154 & 255];
            int i158 = i155 - i154;
            int i159 = (i158 >>> 8) | (i158 << 24);
            int i160 = i156 ^ iArr20[i159 >>> 24];
            int i161 = i157 - iArr21[(i159 >>> 16) & 255];
            int i162 = (i154 - iArr20[(i159 >>> 8) & 255]) ^ iArr21[i159 & 255];
            int i163 = i160 - i161;
            i142 = (i163 >>> 8) | (i163 << 24);
            i148 = i161 ^ iArr20[i142 >>> 24];
            iB31 = i162 - iArr21[(i142 >>> 16) & 255];
            iB32 = (i159 - iArr20[(i142 >>> 8) & 255]) ^ iArr21[i142 & 255];
        }
        this.a[0] = i142 - this.b[0];
        this.a[1] = iB32 - this.b[1];
        this.a[2] = iB31 - this.b[2];
        this.a[3] = i148 - this.b[3];
    }

    @Override // iaik.security.cipher.AbstractC0028i
    public void d() {
        super.d();
        CriticalObject.destroy(this.b);
        CriticalObject.destroy(this.c);
    }
}
