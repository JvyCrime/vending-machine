package iaik.security.dsa;

import iaik.security.md.SHA;
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
import java.security.spec.DSAParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/* JADX INFO: loaded from: classes.dex */
public class DSAParameterGenerator extends AlgorithmParameterGeneratorSpi {
    MessageDigest a = new SHA();
    SecureRandom b = null;
    private int c = -1;

    private DSAParams a(int i) {
        BigInteger[] bigIntegerArrB = b((i - 512) / 64);
        BigInteger bigInteger = bigIntegerArrB[0];
        BigInteger bigInteger2 = bigIntegerArrB[1];
        return new DSAParams(bigInteger, bigInteger2, a(bigInteger, bigInteger2));
    }

    private BigInteger a(BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger bigIntegerModPow;
        BigInteger bigIntegerDivide = bigInteger.subtract(NumberTheory.ONE).divide(bigInteger2);
        BigInteger bigIntegerAdd = NumberTheory.ONE;
        do {
            bigIntegerAdd = bigIntegerAdd.add(NumberTheory.ONE);
            bigIntegerModPow = bigIntegerAdd.modPow(bigIntegerDivide, bigInteger);
        } while (bigIntegerModPow.compareTo(NumberTheory.ONE) == 0);
        return bigIntegerModPow;
    }

    private void a() throws IllegalArgumentException {
        int i = this.c;
        if (i < 512 || i > 4096 || i % 64 != 0) {
            throw new IllegalArgumentException("Prime must be at least 512, at most 4096, and a multiple of 64!");
        }
    }

    private synchronized BigInteger[] b(int i) {
        BigInteger bigIntegerOr;
        int i2;
        BigInteger bigIntegerSubtract;
        int i3 = 1;
        int i4 = ((i * 64) + 512) - 1;
        int i5 = i4 / 160;
        int i6 = i4 % 160;
        BigInteger bigIntegerPow = NumberTheory.TWO.pow(159);
        int i7 = 160;
        BigInteger bigIntegerPow2 = NumberTheory.TWO.pow(160);
        this.a.reset();
        while (true) {
            BigInteger bigIntegerOr2 = new BigInteger(i7, this.b).or(bigIntegerPow);
            bigIntegerOr = new BigInteger(i3, this.a.digest(bigIntegerOr2.toByteArray())).xor(new BigInteger(i3, this.a.digest(bigIntegerOr2.add(NumberTheory.ONE).mod(bigIntegerPow2).toByteArray()))).or(bigIntegerPow).or(NumberTheory.ONE);
            if (NumberTheory.isProbablePrime(bigIntegerOr)) {
                int i8 = 2;
                int i9 = 0;
                do {
                    int i10 = i5 + 1;
                    BigInteger[] bigIntegerArr = new BigInteger[i10];
                    BigInteger bigIntegerValueOf = BigInteger.valueOf(i8);
                    int i11 = 0;
                    while (true) {
                        i2 = i5;
                        if (i11 > i5) {
                            break;
                        }
                        bigIntegerArr[i11] = new BigInteger(1, this.a.digest(bigIntegerOr2.add(bigIntegerValueOf).add(BigInteger.valueOf(i11)).mod(bigIntegerPow2).toByteArray()));
                        i11++;
                        i5 = i2;
                        bigIntegerPow = bigIntegerPow;
                    }
                    BigInteger bigInteger = bigIntegerPow;
                    bigIntegerArr[i2] = bigIntegerArr[i2].mod(NumberTheory.TWO.pow(i6));
                    BigInteger bigIntegerAdd = NumberTheory.ZERO;
                    i5 = i2;
                    int i12 = 0;
                    for (int i13 = 0; i13 <= i5; i13++) {
                        bigIntegerAdd = bigIntegerAdd.add(bigIntegerArr[i13].shiftLeft(i12));
                        i12 += 160;
                    }
                    BigInteger bigIntegerAdd2 = bigIntegerAdd.add(NumberTheory.TWO.pow(i4));
                    bigIntegerSubtract = bigIntegerAdd2.subtract(bigIntegerAdd2.mod(bigIntegerOr.shiftLeft(1)).subtract(NumberTheory.ONE));
                    if (bigIntegerSubtract.compareTo(NumberTheory.TWO.pow(i4)) < 0 || !NumberTheory.isProbablePrime(bigIntegerSubtract)) {
                        i3 = 1;
                        i9++;
                        i8 += i10;
                        bigIntegerPow = bigInteger;
                        i7 = 160;
                    }
                } while (i9 < 4096);
            }
        }
        return new BigInteger[]{bigIntegerSubtract, bigIntegerOr};
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected AlgorithmParameters engineGenerateParameters() {
        if (this.b == null) {
            this.b = SecRandom.getDefault();
        }
        if (this.c == -1) {
            this.c = 1024;
        }
        if (this.c < 512) {
            this.c = 512;
        }
        if (this.c > 4096) {
            this.c = 4096;
        }
        int i = this.c & 65472;
        this.c = i;
        DSAParams dSAParamsA = a(i);
        DSAParameterSpec dSAParameterSpec = new DSAParameterSpec(dSAParamsA.getP(), dSAParamsA.getQ(), dSAParamsA.getG());
        try {
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("DSA", "IAIK");
            algorithmParameters.init(dSAParameterSpec);
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
        this.c = i;
        a();
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        this.b = secureRandom;
        if (algorithmParameterSpec != null) {
            throw new InvalidAlgorithmParameterException("Parameter must be null.");
        }
        a();
    }
}
