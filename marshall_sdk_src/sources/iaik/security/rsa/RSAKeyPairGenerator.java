package iaik.security.rsa;

import iaik.pkcs.pkcs1.RSACipher;
import iaik.security.random.SHA256FIPS186Random;
import iaik.utils.NumberTheory;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

/* JADX INFO: loaded from: classes.dex */
public class RSAKeyPairGenerator extends KeyPairGenerator {
    private static final BigDecimal a = BigDecimal.valueOf(2L);
    private static final BigInteger b = BigInteger.valueOf(65537);
    private static final BigInteger c = new BigInteger("1234567890ABCDEF1234567890ABCDEF", 16);
    protected boolean initialized;
    protected int keylen;
    protected BigInteger public_exponent;
    protected SecureRandom random;

    public RSAKeyPairGenerator() {
        super("RSA");
        this.public_exponent = null;
        this.initialized = false;
    }

    RSAKeyPairGenerator(String str) {
        super(str);
        this.public_exponent = null;
        this.initialized = false;
    }

    private BigInteger b(BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger bigInteger3 = this.public_exponent;
        BigInteger bigInteger4 = new BigDecimal(bigInteger.subtract(NumberTheory.ONE)).divide(a, 2).toBigInteger();
        BigInteger bigIntegerShiftRight = bigInteger2.subtract(NumberTheory.ONE).shiftRight(1);
        int iBitLength = (bigIntegerShiftRight.bitLength() + bigInteger4.bitLength()) >> 1;
        while (true) {
            BigInteger bigInteger5 = new BigInteger(iBitLength, this.random);
            if (bigInteger5.compareTo(bigInteger4) >= 0 && bigInteger5.compareTo(bigIntegerShiftRight) <= 0) {
                BigInteger bigIntegerShiftLeft = bigInteger5.shiftLeft(1);
                BigInteger bigIntegerAdd = bigIntegerShiftLeft.add(NumberTheory.ONE);
                if (bigIntegerShiftLeft.gcd(bigInteger3).compareTo(NumberTheory.ONE) == 0 && NumberTheory.millerRabin(bigIntegerAdd)) {
                    return bigIntegerAdd;
                }
            }
        }
    }

    RSAPrivateKey a(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, BigInteger bigInteger6, BigInteger bigInteger7, BigInteger bigInteger8) {
        return new RSAPrivateKey(bigInteger, bigInteger2, bigInteger3, bigInteger4, bigInteger5, bigInteger6, bigInteger7, bigInteger8);
    }

    RSAPublicKey a(BigInteger bigInteger, BigInteger bigInteger2) {
        return new RSAPublicKey(bigInteger, bigInteger2);
    }

    void a(int i, BigInteger bigInteger, SecureRandom secureRandom) throws InvalidParameterException {
        if (i < 320) {
            throw new InvalidParameterException("Unsupported key size!");
        }
        if (bigInteger != null && bigInteger.compareTo(NumberTheory.ONE) <= 0) {
            throw new InvalidParameterException("The public exponent must be greater than 1!");
        }
    }

    void a(SecureRandom secureRandom) {
        if (secureRandom == null) {
            secureRandom = new SHA256FIPS186Random();
        }
        this.random = secureRandom;
    }

    BigInteger[] a() {
        BigInteger bit = NumberTheory.ZERO.setBit(((this.keylen + 1) >> 1) - 1);
        BigInteger bigIntegerB = b(bit, bit.shiftLeft(1).subtract(NumberTheory.ONE));
        BigDecimal bigDecimalDivide = new BigDecimal(NumberTheory.ZERO.setBit(this.keylen - 1)).divide(new BigDecimal(bigIntegerB), 1, 4);
        return new BigInteger[]{bigIntegerB, b(bigDecimalDivide.toBigInteger().add(NumberTheory.ONE), bigDecimalDivide.multiply(a).toBigInteger())};
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public KeyPair generateKeyPair() {
        BigInteger bigInteger;
        BigInteger bigInteger2;
        BigInteger bigIntegerSubtract;
        BigInteger bigIntegerSubtract2;
        BigInteger bigIntegerMultiply;
        if (!this.initialized) {
            initialize(1024);
        }
        do {
            BigInteger[] bigIntegerArrA = a();
            BigInteger bigInteger3 = bigIntegerArrA[0];
            BigInteger bigInteger4 = bigIntegerArrA[1];
            if (bigInteger3.compareTo(bigInteger4) < 0) {
                bigInteger2 = bigInteger4;
                bigInteger = bigInteger3;
            } else {
                bigInteger = bigInteger4;
                bigInteger2 = bigInteger3;
            }
            bigIntegerSubtract = bigInteger2.subtract(NumberTheory.ONE);
            bigIntegerSubtract2 = bigInteger.subtract(NumberTheory.ONE);
            bigIntegerMultiply = bigIntegerSubtract.multiply(bigIntegerSubtract2);
        } while (this.public_exponent.gcd(bigIntegerMultiply).compareTo(NumberTheory.ONE) != 0);
        BigInteger bigIntegerMultiply2 = bigInteger2.multiply(bigInteger);
        BigInteger bigIntegerModInverse = this.public_exponent.modInverse(bigIntegerMultiply);
        BigInteger bigIntegerMod = bigIntegerModInverse.mod(bigIntegerSubtract);
        BigInteger bigIntegerMod2 = bigIntegerModInverse.mod(bigIntegerSubtract2);
        BigInteger bigIntegerModInverse2 = bigInteger.modInverse(bigInteger2);
        RSAPublicKey rSAPublicKeyA = a(bigIntegerMultiply2, this.public_exponent);
        RSAPrivateKey rSAPrivateKeyA = a(bigIntegerMultiply2, this.public_exponent, bigIntegerModInverse, bigInteger2, bigInteger, bigIntegerMod, bigIntegerMod2, bigIntegerModInverse2);
        RSACipher rSACipher = new RSACipher();
        BigInteger bigInteger5 = c;
        BigInteger bigIntegerRawPublicRSA = rSACipher.rawPublicRSA(bigInteger5, rSAPublicKeyA);
        RSACipher rSACipher2 = new RSACipher();
        rSACipher2.setUseBlinding(false);
        if (rSACipher2.rawPrivateRSA(bigIntegerRawPublicRSA, rSAPrivateKeyA, this.random).compareTo(bigInteger5) == 0) {
            return new KeyPair(rSAPublicKeyA, rSAPrivateKeyA);
        }
        throw new RuntimeException("RSA keypair generation error!");
    }

    @Override // java.security.KeyPairGenerator
    public void initialize(int i) {
        initialize(i, null, null);
    }

    public void initialize(int i, BigInteger bigInteger, SecureRandom secureRandom) {
        this.initialized = false;
        this.public_exponent = null;
        this.random = null;
        this.keylen = 0;
        a(i, bigInteger, secureRandom);
        if (this.public_exponent == null) {
            if (bigInteger == null) {
                bigInteger = b;
            } else if (!bigInteger.testBit(0)) {
                throw new InvalidParameterException("The public exponent must be odd");
            }
            this.public_exponent = bigInteger;
        }
        if (this.random == null) {
            a(secureRandom);
        }
        this.initialized = true;
        this.keylen = i;
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public void initialize(int i, SecureRandom secureRandom) {
        initialize(i, null, secureRandom);
    }
}
