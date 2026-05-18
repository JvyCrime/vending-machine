package iaik.security.rsa;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public interface OaepKeyPairGenerator {
    void initialize(int i, BigInteger bigInteger, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidParameterException;

    void initialize(int i, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterException;

    void initialize(int i, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidParameterException;

    void initialize(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException;

    void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException;
}
