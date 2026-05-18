package iaik.security.dsa;

import iaik.security.random.SecRandom;
import iaik.utils.NumberTheory;
import java.math.BigInteger;
import java.security.AlgorithmParameterGeneratorSpi;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class SHA2withDSAParameterGenerator extends AlgorithmParameterGeneratorSpi {
    MessageDigest a;
    SecureRandom b;
    private int c = -1;
    private int d = -1;
    private int e = -1;
    private int f;

    SHA2withDSAParameterGenerator(MessageDigest messageDigest, int i) {
        this.a = messageDigest;
        this.f = i;
    }

    private synchronized SHA2withDSAParams a(int i, int i2, int i3, MessageDigest messageDigest, int i4) {
        BigInteger bigIntegerModPow;
        SHA2withDSAParams sHA2withDSAParams;
        BigInteger bigInteger;
        BigInteger bigInteger2;
        messageDigest.reset();
        int i5 = 1;
        BigInteger bigIntegerPow = NumberTheory.TWO.pow(i2 - 1);
        BigInteger bigIntegerAdd = bigIntegerPow.add(NumberTheory.ONE);
        int i6 = (i * 4) - 1;
        int i7 = i - 1;
        BigInteger bigIntegerPow2 = NumberTheory.TWO.pow(i7);
        int i8 = (((i + i4) - 1) / i4) - 1;
        BigInteger bigIntegerPow3 = NumberTheory.TWO.pow(i7 - (i8 * i4));
        byte[] bArr = new byte[i3 / 8];
        BigInteger bigIntegerSubtract = null;
        BigInteger bigIntegerSubtract2 = null;
        boolean z = true;
        int i9 = 0;
        while (z) {
            do {
                this.b.nextBytes(bArr);
                BigInteger bigIntegerMod = new BigInteger(i5, messageDigest.digest(bArr)).mod(bigIntegerPow);
                bigIntegerSubtract2 = bigIntegerAdd.add(bigIntegerMod).subtract(bigIntegerMod.mod(NumberTheory.TWO));
            } while (!NumberTheory.isProbablePrime(bigIntegerSubtract2));
            i9 = 0;
            int i10 = 1;
            while (true) {
                if (i9 > i6) {
                    bigInteger = bigIntegerPow;
                    bigInteger2 = bigIntegerAdd;
                    break;
                }
                BigInteger bigIntegerAdd2 = NumberTheory.ZERO;
                int i11 = 0;
                int i12 = 0;
                while (i11 < i8) {
                    int i13 = i12;
                    bigIntegerAdd2 = bigIntegerAdd2.add(new BigInteger(1, messageDigest.digest(a(bArr, i10 + i11))).shiftLeft(i13));
                    i12 = i13 + i4;
                    i11++;
                    bigIntegerPow = bigIntegerPow;
                    bigIntegerAdd = bigIntegerAdd;
                }
                bigInteger = bigIntegerPow;
                bigInteger2 = bigIntegerAdd;
                BigInteger bigIntegerAdd3 = bigIntegerAdd2.add(new BigInteger(1, messageDigest.digest(a(bArr, i10 + i8))).mod(bigIntegerPow3).shiftLeft(i12)).add(bigIntegerPow2);
                bigIntegerSubtract = bigIntegerAdd3.subtract(bigIntegerAdd3.mod(bigIntegerSubtract2.shiftLeft(1)).subtract(NumberTheory.ONE));
                if (bigIntegerSubtract.compareTo(bigIntegerPow2) >= 0 && NumberTheory.isProbablePrime(bigIntegerSubtract)) {
                    z = false;
                    break;
                }
                i10 += i8 + 1;
                i9++;
                bigIntegerPow = bigInteger;
                bigIntegerAdd = bigInteger2;
            }
            bigIntegerPow = bigInteger;
            bigIntegerAdd = bigInteger2;
            i5 = 1;
        }
        BigInteger bigIntegerDivide = bigIntegerSubtract.subtract(NumberTheory.ONE).divide(bigIntegerSubtract2);
        BigInteger bigIntegerAdd4 = NumberTheory.ONE;
        do {
            bigIntegerAdd4 = bigIntegerAdd4.add(NumberTheory.ONE);
            bigIntegerModPow = bigIntegerAdd4.modPow(bigIntegerDivide, bigIntegerSubtract);
        } while (bigIntegerModPow.compareTo(NumberTheory.ONE) == 0);
        sHA2withDSAParams = new SHA2withDSAParams(bigIntegerSubtract, bigIntegerSubtract2, bigIntegerModPow);
        sHA2withDSAParams.a(i9);
        sHA2withDSAParams.a(bArr);
        return sHA2withDSAParams;
    }

    private static byte[] a(byte[] bArr, int i) {
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        int i2 = length - 1;
        while (true) {
            if (i2 < 0) {
                break;
            }
            int i3 = i + (bArr[i2] & 255);
            bArr2[i2] = (byte) i3;
            i = i3 >>> 8;
            if (i == 0) {
                System.arraycopy(bArr, 0, bArr2, 0, i2);
                break;
            }
            i2--;
        }
        return bArr2;
    }

    public static void validateParameters(BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr, int i, MessageDigest messageDigest, int i2) throws InvalidAlgorithmParameterException {
        Objects.requireNonNull(messageDigest, "hashEngine must not be null!");
        int iBitLength = bigInteger.bitLength();
        int iBitLength2 = bigInteger2.bitLength();
        if (iBitLength != 1024) {
            if (iBitLength != 2048) {
                if (iBitLength != 3072) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Invalid (L,N) pair: (");
                    stringBuffer.append(iBitLength);
                    stringBuffer.append("/");
                    stringBuffer.append(iBitLength2);
                    stringBuffer.append(")");
                    throw new InvalidAlgorithmParameterException(stringBuffer.toString());
                }
                if (iBitLength2 != 256) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Invalid (L,N) pair: (");
                    stringBuffer2.append(iBitLength);
                    stringBuffer2.append("/");
                    stringBuffer2.append(iBitLength2);
                    stringBuffer2.append(")");
                    throw new InvalidAlgorithmParameterException(stringBuffer2.toString());
                }
            } else if (iBitLength2 != 224 && iBitLength2 != 256) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("Invalid (L,N) pair: (");
                stringBuffer3.append(iBitLength);
                stringBuffer3.append("/");
                stringBuffer3.append(iBitLength2);
                stringBuffer3.append(")");
                throw new InvalidAlgorithmParameterException(stringBuffer3.toString());
            }
        } else if (iBitLength2 != 160) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("Invalid (L,N) pair: (");
            stringBuffer4.append(iBitLength);
            stringBuffer4.append("/");
            stringBuffer4.append(iBitLength2);
            stringBuffer4.append(")");
            throw new InvalidAlgorithmParameterException(stringBuffer4.toString());
        }
        if (i > (iBitLength * 4) - 1) {
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("Invalid counter (");
            stringBuffer5.append(i);
            stringBuffer5.append("). Must not be > 4L-1!");
            throw new InvalidAlgorithmParameterException(stringBuffer5.toString());
        }
        if (bArr.length * 8 < iBitLength2) {
            throw new InvalidAlgorithmParameterException("Invalid seed. Must not be shorter than N!");
        }
        messageDigest.reset();
        BigInteger bigIntegerPow = NumberTheory.TWO.pow(iBitLength2 - 1);
        BigInteger bigIntegerMod = new BigInteger(1, messageDigest.digest(bArr)).mod(bigIntegerPow);
        BigInteger bigIntegerSubtract = bigIntegerPow.add(bigIntegerMod).add(NumberTheory.ONE).subtract(bigIntegerMod.mod(NumberTheory.TWO));
        if (bigIntegerSubtract.compareTo(bigInteger2) != 0) {
            throw new InvalidAlgorithmParameterException("Invalid q. Does not match to computed q!");
        }
        if (!NumberTheory.isProbablePrime(bigIntegerSubtract)) {
            throw new InvalidAlgorithmParameterException("Computed q not prime!");
        }
        int i3 = (((iBitLength + i2) - 1) / i2) - 1;
        int i4 = iBitLength - 1;
        BigInteger bigIntegerPow2 = NumberTheory.TWO.pow(i4 - (i3 * i2));
        BigInteger bigIntegerPow3 = NumberTheory.TWO.pow(i4);
        BigInteger bigIntegerSubtract2 = null;
        int i5 = 0;
        int i6 = 1;
        while (i5 <= i) {
            BigInteger bigIntegerAdd = NumberTheory.ZERO;
            int i7 = 0;
            for (int i8 = 0; i8 < i3; i8++) {
                bigIntegerAdd = bigIntegerAdd.add(new BigInteger(1, messageDigest.digest(a(bArr, i6 + i8))).shiftLeft(i7));
                i7 += i2;
            }
            BigInteger bigIntegerAdd2 = bigIntegerAdd.add(new BigInteger(1, messageDigest.digest(a(bArr, i6 + i3))).mod(bigIntegerPow2).shiftLeft(i7)).add(bigIntegerPow3);
            bigIntegerSubtract2 = bigIntegerAdd2.subtract(bigIntegerAdd2.mod(bigInteger2.shiftLeft(1)).subtract(NumberTheory.ONE));
            if (bigIntegerSubtract2.compareTo(bigIntegerPow3) >= 0 && NumberTheory.isProbablePrime(bigIntegerSubtract2)) {
                break;
            }
            i6 += i3 + 1;
            i5++;
        }
        if (i5 != i) {
            throw new InvalidAlgorithmParameterException("Invalid counter!");
        }
        if (bigIntegerSubtract2.compareTo(bigInteger) != 0) {
            throw new InvalidAlgorithmParameterException("Invalid p. Does not match to computed p!");
        }
        if (!NumberTheory.isProbablePrime(bigIntegerSubtract2)) {
            throw new InvalidAlgorithmParameterException("Computed p not prime!");
        }
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected AlgorithmParameters engineGenerateParameters() {
        if (this.b == null) {
            this.b = SecRandom.getDefault();
        }
        if (this.d == -1) {
            this.d = 2048;
            int i = this.f;
            this.c = i;
            this.e = i;
        }
        if (this.e == -1) {
            this.e = this.c;
        }
        SHA2withDSAParams sHA2withDSAParamsA = a(this.d, this.c, this.e, this.a, this.f);
        SHA2withDSAParameterSpec sHA2withDSAParameterSpec = new SHA2withDSAParameterSpec(sHA2withDSAParamsA.getP(), sHA2withDSAParamsA.getQ(), sHA2withDSAParamsA.getG());
        sHA2withDSAParameterSpec.a(sHA2withDSAParamsA.getCounter());
        sHA2withDSAParameterSpec.a(sHA2withDSAParamsA.getSeed());
        try {
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("DSA", "IAIK");
            algorithmParameters.init(sHA2withDSAParameterSpec);
            return algorithmParameters;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.toString());
        } catch (NoSuchProviderException e2) {
            throw new RuntimeException(e2.toString());
        } catch (InvalidParameterSpecException e3) {
            throw new RuntimeException(e3.toString());
        }
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected void engineInit(int i, SecureRandom secureRandom) {
        this.b = secureRandom;
        this.d = i;
        if (i == 1024) {
            this.c = 160;
        } else {
            if (i != 2048 && i != 3072) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid prime modulus length (");
                stringBuffer.append(i);
                stringBuffer.append("). Must be 1024, 2048 or 3072.");
                throw new IllegalArgumentException(stringBuffer.toString());
            }
            this.c = 256;
        }
        this.e = -1;
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        if (!(algorithmParameterSpec instanceof SHA2withDSAGenParameterSpec)) {
            throw new InvalidAlgorithmParameterException("Parameters must be SHA2withDSAGenParameterSpec.");
        }
        SHA2withDSAGenParameterSpec sHA2withDSAGenParameterSpec = (SHA2withDSAGenParameterSpec) algorithmParameterSpec;
        this.d = sHA2withDSAGenParameterSpec.getL();
        this.c = sHA2withDSAGenParameterSpec.getN();
        this.e = sHA2withDSAGenParameterSpec.getSeedlen();
        if (this.f >= this.c) {
            this.b = secureRandom;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Invalid parameters. Hash output length (");
        stringBuffer.append(this.f);
        stringBuffer.append(") must be greater than N (");
        stringBuffer.append(this.c);
        stringBuffer.append(")");
        throw new InvalidAlgorithmParameterException(stringBuffer.toString());
    }
}
