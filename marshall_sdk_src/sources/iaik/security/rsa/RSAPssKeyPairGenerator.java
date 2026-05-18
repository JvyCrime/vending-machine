package iaik.security.rsa;

import iaik.pkcs.pkcs1.RSAPssParameterSpec;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/* JADX INFO: loaded from: classes.dex */
public class RSAPssKeyPairGenerator extends RSAKeyPairGenerator implements PssKeyPairGenerator {
    private RSAPssParameterSpec a;

    public RSAPssKeyPairGenerator() {
        super("RSASSA-PSS");
    }

    @Override // iaik.security.rsa.RSAKeyPairGenerator
    RSAPrivateKey a(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, BigInteger bigInteger5, BigInteger bigInteger6, BigInteger bigInteger7, BigInteger bigInteger8) {
        try {
        } catch (InvalidParameterSpecException e) {
            e = e;
        }
        try {
            return new RSAPssPrivateKey(bigInteger, bigInteger2, bigInteger3, bigInteger4, bigInteger5, bigInteger6, bigInteger7, bigInteger8, this.a);
        } catch (InvalidParameterSpecException e2) {
            e = e2;
            throw new RuntimeException(e.toString());
        }
    }

    @Override // iaik.security.rsa.RSAKeyPairGenerator
    RSAPublicKey a(BigInteger bigInteger, BigInteger bigInteger2) {
        try {
            return new RSAPssPublicKey(bigInteger, bigInteger2, this.a);
        } catch (InvalidParameterSpecException e) {
            throw new RuntimeException(e.toString());
        }
    }

    @Override // iaik.security.rsa.PssKeyPairGenerator
    public void initialize(int i, BigInteger bigInteger, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidParameterException {
        if (!(algorithmParameterSpec instanceof RSAPssParameterSpec)) {
            throw new InvalidParameterException("Not a RSAPssParameterSpec");
        }
        this.a = (RSAPssParameterSpec) algorithmParameterSpec;
        super.initialize(i, bigInteger, secureRandom);
    }

    @Override // iaik.security.rsa.PssKeyPairGenerator
    public void initialize(int i, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterException {
        if (!(algorithmParameterSpec instanceof RSAPssParameterSpec)) {
            throw new InvalidParameterException("Not a RSAPssParameterSpec");
        }
        this.a = (RSAPssParameterSpec) algorithmParameterSpec;
        super.initialize(i, (BigInteger) null, (SecureRandom) null);
    }

    @Override // iaik.security.rsa.PssKeyPairGenerator
    public void initialize(int i, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidParameterException {
        if (!(algorithmParameterSpec instanceof RSAPssParameterSpec)) {
            throw new InvalidParameterException("Not a RSAPssParameterSpec");
        }
        this.a = (RSAPssParameterSpec) algorithmParameterSpec;
        super.initialize(i, (BigInteger) null, secureRandom);
    }

    @Override // java.security.KeyPairGenerator, iaik.security.rsa.PssKeyPairGenerator
    public void initialize(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        initialize(algorithmParameterSpec, (SecureRandom) null);
    }

    @Override // java.security.KeyPairGenerator, java.security.KeyPairGeneratorSpi, iaik.security.rsa.PssKeyPairGenerator
    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        if (!(algorithmParameterSpec instanceof RSAPssParameterSpec)) {
            throw new InvalidAlgorithmParameterException("Not a RSAPssParameterSpec");
        }
        this.a = (RSAPssParameterSpec) algorithmParameterSpec;
        a(secureRandom);
    }
}
