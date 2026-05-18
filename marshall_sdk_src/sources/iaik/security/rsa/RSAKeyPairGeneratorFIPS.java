package iaik.security.rsa;

import com.ftdi.j2xx.ft4222.FT_4222_Defines;
import iaik.security.random.SHA1SP80090Random;
import iaik.security.random.SHA256SP80090Random;
import iaik.security.random.SHA384SP80090Random;
import iaik.security.random.SHA512SP80090Random;
import iaik.utils.NumberTheory;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.security.SecureRandom;

/* JADX INFO: loaded from: classes.dex */
public class RSAKeyPairGeneratorFIPS extends RSAKeyPairGenerator {
    public static final int KEYLENGTH_1024 = 1024;
    public static final int KEYLENGTH_2048 = 2048;
    public static final int KEYLENGTH_3072 = 3072;
    private int b;
    private int c;
    private static final BigDecimal a = new BigDecimal(Math.sqrt(2.0d));
    public static final BigInteger LOWER_PUBLIC_EXPONENT_BOUND = NumberTheory.ZERO.setBit(16).setBit(0);
    public static final BigInteger UPPER_PUBLIC_EXPONENT_BOUND = NumberTheory.ZERO.setBit(256).subtract(NumberTheory.ONE);

    public RSAKeyPairGeneratorFIPS() {
        super("RSA-FIPS-186-3");
        this.b = 0;
        this.c = 0;
    }

    RSAKeyPairGeneratorFIPS(String str) {
        super(str);
        this.b = 0;
        this.c = 0;
    }

    private BigInteger a(int i) {
        BigInteger bit = new BigInteger(i, this.random).setBit(0);
        while (!a(bit, i)) {
            bit = bit.add(NumberTheory.TWO);
        }
        return bit;
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x0087, code lost:
    
        if (r6 == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0089, code lost:
    
        return false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean a(java.math.BigInteger r11, int r12) {
        /*
            r10 = this;
            boolean r0 = iaik.utils.NumberTheory.hasSmallFactors(r11)
            r1 = 0
            if (r0 == 0) goto L8
            return r1
        L8:
            r0 = 512(0x200, float:7.17E-43)
            if (r12 != r0) goto Le
            r0 = 7
            goto L30
        Le:
            r0 = 1024(0x400, float:1.435E-42)
            if (r12 != r0) goto L14
            r0 = 4
            goto L30
        L14:
            r0 = 1536(0x600, float:2.152E-42)
            if (r12 != r0) goto L1a
            r0 = 3
            goto L30
        L1a:
            r0 = 100
            if (r12 <= r0) goto L2f
            r0 = 140(0x8c, float:1.96E-43)
            if (r12 > r0) goto L25
            r0 = 38
            goto L30
        L25:
            r0 = 170(0xaa, float:2.38E-43)
            if (r12 > r0) goto L2c
            r0 = 32
            goto L30
        L2c:
            r0 = 27
            goto L30
        L2f:
            r0 = 0
        L30:
            java.math.BigInteger r2 = iaik.utils.NumberTheory.ONE
            java.math.BigInteger r2 = r11.subtract(r2)
            int r3 = r2.getLowestSetBit()
            java.math.BigInteger r4 = r2.shiftRight(r3)
            r5 = 0
        L3f:
            r6 = 1
            if (r5 >= r0) goto L8d
        L42:
            java.math.BigInteger r7 = new java.math.BigInteger
            java.security.SecureRandom r8 = r10.random
            r7.<init>(r12, r8)
            int r8 = r7.compareTo(r2)
            if (r8 >= 0) goto L42
            java.math.BigInteger r8 = iaik.utils.NumberTheory.ONE
            int r8 = r7.compareTo(r8)
            if (r8 <= 0) goto L42
            java.math.BigInteger r7 = r7.modPow(r4, r11)
            java.math.BigInteger r8 = iaik.utils.NumberTheory.ONE
            int r8 = r7.compareTo(r8)
            if (r8 == 0) goto L8a
            int r8 = r7.compareTo(r2)
            if (r8 != 0) goto L6a
            goto L8a
        L6a:
            r8 = 1
        L6b:
            if (r8 >= r3) goto L87
            java.math.BigInteger r9 = iaik.utils.NumberTheory.TWO
            java.math.BigInteger r7 = r7.modPow(r9, r11)
            int r9 = r7.compareTo(r2)
            if (r9 != 0) goto L7b
            r6 = 0
            goto L87
        L7b:
            java.math.BigInteger r9 = iaik.utils.NumberTheory.ONE
            int r9 = r7.compareTo(r9)
            if (r9 != 0) goto L84
            return r1
        L84:
            int r8 = r8 + 1
            goto L6b
        L87:
            if (r6 == 0) goto L8a
            return r1
        L8a:
            int r5 = r5 + 1
            goto L3f
        L8d:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.rsa.RSAKeyPairGeneratorFIPS.a(java.math.BigInteger, int):boolean");
    }

    private BigInteger[] a(BigInteger bigInteger, BigInteger bigInteger2, int i) {
        BigInteger bigIntegerShiftLeft = bigInteger.shiftLeft(1);
        if (bigIntegerShiftLeft.gcd(bigInteger2).compareTo(NumberTheory.ONE) != 0) {
            throw new RuntimeException("RSA keypair generation error!");
        }
        BigInteger bigIntegerSubtract = bigInteger2.modInverse(bigIntegerShiftLeft).multiply(bigInteger2).subtract(bigIntegerShiftLeft.modInverse(bigInteger2).multiply(bigIntegerShiftLeft));
        int i2 = i >> 1;
        BigInteger bigIntegerShiftLeft2 = NumberTheory.ONE.shiftLeft(i2);
        BigInteger bigInteger3 = new BigDecimal(bigIntegerShiftLeft2.shiftRight(1)).multiply(a).toBigInteger();
        BigInteger bigIntegerMultiply = bigIntegerShiftLeft.multiply(bigInteger2);
        int i3 = i2 * 5;
        while (true) {
            BigInteger bigInteger4 = new BigInteger(i2, this.random);
            if (bigInteger3.compareTo(bigInteger4) <= 0) {
                int i4 = 0;
                for (BigInteger bigIntegerAdd = bigIntegerSubtract.subtract(bigInteger4).mod(bigIntegerMultiply).add(bigInteger4); bigIntegerAdd.compareTo(bigIntegerShiftLeft2) < 0; bigIntegerAdd = bigIntegerAdd.add(bigIntegerMultiply)) {
                    if (bigIntegerAdd.subtract(NumberTheory.ONE).gcd(this.public_exponent).compareTo(NumberTheory.ONE) == 0 && a(bigIntegerAdd, i2)) {
                        return new BigInteger[]{bigInteger4, bigIntegerAdd};
                    }
                    i4++;
                    if (i4 >= i3) {
                        throw new RuntimeException("RSA keypair generation error!");
                    }
                }
            }
        }
    }

    @Override // iaik.security.rsa.RSAKeyPairGenerator
    void a(int i, BigInteger bigInteger, SecureRandom secureRandom) {
        int i2;
        if (i != 1024 && i != 2048 && i != 3072) {
            throw new InvalidParameterException("Key size not supported!");
        }
        if (bigInteger != null && (bigInteger.compareTo(LOWER_PUBLIC_EXPONENT_BOUND) < 0 || bigInteger.compareTo(UPPER_PUBLIC_EXPONENT_BOUND) > 0)) {
            throw new InvalidParameterException("The public exponent must lie in the range between 2^16 + 1 and 2^256 - 1");
        }
        b();
        if (i == 1024) {
            this.b = 104;
            i2 = 80;
        } else if (i == 2048) {
            this.b = 144;
            i2 = 112;
        } else {
            if (i != 3072) {
                return;
            }
            this.b = FT_4222_Defines.CHIPTOP_CMD.CHIPTOP_GET_DESC_STRING;
            i2 = 128;
        }
        this.c = i2;
    }

    @Override // iaik.security.rsa.RSAKeyPairGenerator
    BigInteger[] a() {
        BigInteger[] bigIntegerArrA = a(a(this.b), a(this.b), this.keylen);
        BigInteger bigInteger = bigIntegerArrA[0];
        BigInteger bigInteger2 = bigIntegerArrA[1];
        int i = this.keylen >> (-99);
        while (true) {
            BigInteger[] bigIntegerArrA2 = a(a(this.b), a(this.b), this.keylen);
            BigInteger bigInteger3 = bigIntegerArrA2[0];
            BigInteger bigInteger4 = bigIntegerArrA2[1];
            if (bigInteger.subtract(bigInteger3).abs().bitLength() > i && bigInteger2.subtract(bigInteger4).abs().bitLength() > i) {
                return new BigInteger[]{bigInteger2, bigInteger4};
            }
        }
    }

    void b() {
        int i = this.c;
        a(i < 112 ? new SHA1SP80090Random() : i <= 128 ? new SHA256SP80090Random() : i <= 192 ? new SHA384SP80090Random() : new SHA512SP80090Random());
    }
}
