package iaik.security.elgamal;

import java.math.BigInteger;
import java.security.AlgorithmParameterGeneratorSpi;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public final class ElGamalParameterGenerator extends AlgorithmParameterGeneratorSpi {
    private static final BigInteger a = BigInteger.valueOf(2);
    private SecureRandom b;
    private int c = 1024;
    private int d = 300;

    static BigInteger a(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        BigInteger bigIntegerSubtract = bigInteger.subtract(a);
        while (true) {
            BigInteger bigInteger3 = new BigInteger(bigInteger.bitLength(), secureRandom);
            if (bigInteger3.compareTo(a) >= 0 && bigInteger3.compareTo(bigIntegerSubtract) <= 0) {
                BigInteger bigIntegerMod = bigInteger3.multiply(bigInteger3).mod(bigInteger);
                if (!bigIntegerMod.equals(BigInteger.ONE)) {
                    return bigIntegerMod;
                }
            }
        }
    }

    private void a() throws IllegalArgumentException {
        int i;
        if (this.c < 512 || ((i = this.d) != 0 && i < 160)) {
            throw new IllegalArgumentException("Prime must be at least 512, exponent at least 160 bits long!");
        }
    }

    private static BigInteger[] a(int i, int i2, SecureRandom secureRandom) {
        int i3 = i - 1;
        while (true) {
            BigInteger bigInteger = new BigInteger(i3, 2, secureRandom);
            BigInteger bigIntegerAdd = bigInteger.shiftLeft(1).add(BigInteger.ONE);
            if (bigIntegerAdd.isProbablePrime(i2) && bigInteger.isProbablePrime(i2)) {
                return new BigInteger[]{bigIntegerAdd, bigInteger};
            }
        }
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected AlgorithmParameters engineGenerateParameters() {
        BigInteger[] bigIntegerArrA = a(this.c, 80, this.b);
        BigInteger bigInteger = bigIntegerArrA[0];
        BigInteger bigIntegerA = a(bigInteger, bigIntegerArrA[1], this.b);
        try {
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("ElGamal", "IAIK");
            algorithmParameters.init(new ElGamalParameterSpec(bigInteger, bigIntegerA, this.d));
            return algorithmParameters;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected void engineInit(int i, SecureRandom secureRandom) {
        this.c = i;
        this.b = secureRandom;
        a();
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        if (!(algorithmParameterSpec instanceof ElGamalGenParameterSpec)) {
            throw new InvalidAlgorithmParameterException("DH parameter generator requires a DHGenParameterSpec for initialisation");
        }
        ElGamalGenParameterSpec elGamalGenParameterSpec = (ElGamalGenParameterSpec) algorithmParameterSpec;
        this.c = elGamalGenParameterSpec.getPrimeSize();
        this.d = elGamalGenParameterSpec.getExponentSize();
        this.b = secureRandom;
        a();
    }
}
