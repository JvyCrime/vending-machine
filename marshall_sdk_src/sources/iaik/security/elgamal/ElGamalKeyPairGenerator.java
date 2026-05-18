package iaik.security.elgamal;

import iaik.security.random.SecRandom;
import iaik.utils.NumberTheory;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public final class ElGamalKeyPairGenerator extends KeyPairGenerator {
    private static final BigInteger a = BigInteger.valueOf(2);
    private ElGamalParameterSpec b;
    private SecureRandom c;
    private boolean d;
    private BigInteger e;
    private BigInteger f;
    private int g;

    public ElGamalKeyPairGenerator() {
        super("ElGamal");
        this.c = new SecureRandom();
        this.d = false;
    }

    private BigInteger a() {
        int l = this.b.getL();
        if (l > 0) {
            return new BigInteger(l, this.c).setBit(l - 1);
        }
        while (true) {
            BigInteger bigInteger = new BigInteger(this.g, this.c);
            if (!bigInteger.equals(BigInteger.ZERO) && bigInteger.compareTo(this.f) <= 0) {
                return bigInteger;
            }
        }
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public KeyPair generateKeyPair() {
        if (!this.d) {
            throw new IllegalStateException("Not initialized");
        }
        BigInteger bigIntegerA = a();
        BigInteger bigIntegerModPow = this.b.getG().modPow(bigIntegerA, this.e);
        return new KeyPair(new ElGamalPublicKey(bigIntegerModPow, this.b), new ElGamalPrivateKey(bigIntegerA, this.b));
    }

    @Override // java.security.KeyPairGenerator
    public void initialize(int i) {
        initialize(i, (SecureRandom) null);
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public void initialize(int i, SecureRandom secureRandom) {
        if (i < 128) {
            throw new IllegalArgumentException("Invalid key size!");
        }
        this.c = secureRandom == null ? SecRandom.getDefault() : secureRandom;
        try {
            initialize(new ElGamalParameterSpec(NumberTheory.nextPrime(new BigInteger(i, 80, this.c)), a), secureRandom);
        } catch (InvalidAlgorithmParameterException unused) {
        }
    }

    @Override // java.security.KeyPairGenerator
    public void initialize(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        initialize(algorithmParameterSpec, (SecureRandom) null);
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi
    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        Objects.requireNonNull(algorithmParameterSpec);
        if (!(algorithmParameterSpec instanceof ElGamalParameterSpec)) {
            throw new InvalidAlgorithmParameterException("No ElGamal params!");
        }
        if (secureRandom == null) {
            secureRandom = SecRandom.getDefault();
        }
        this.c = secureRandom;
        ElGamalParameterSpec elGamalParameterSpec = (ElGamalParameterSpec) algorithmParameterSpec;
        this.b = elGamalParameterSpec;
        BigInteger p = elGamalParameterSpec.getP();
        this.e = p;
        this.f = p.subtract(BigInteger.ONE);
        this.g = this.e.bitLength();
        this.d = true;
    }
}
