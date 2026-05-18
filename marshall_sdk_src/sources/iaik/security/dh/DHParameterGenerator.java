package iaik.security.dh;

import java.math.BigInteger;
import java.security.AlgorithmParameterGeneratorSpi;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.DHGenParameterSpec;
import javax.crypto.spec.DHParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class DHParameterGenerator extends AlgorithmParameterGeneratorSpi {
    private SecureRandom a;
    private int b = 1024;
    private int c = 300;

    private void a() throws IllegalArgumentException {
        int i;
        if (this.b < 512 || ((i = this.c) != 0 && i < 160)) {
            throw new IllegalArgumentException("Prime must be at least 512, exponent at least 160 bits long!");
        }
    }

    private static void a(byte[] bArr) {
        int length = bArr.length;
        while (length > 0) {
            length--;
            byte b = (byte) (bArr[length] + 1);
            bArr[length] = b;
            if (b != 0) {
                return;
            }
        }
    }

    AlgorithmParameters a(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4, byte[] bArr, int i, int i2) throws NoSuchAlgorithmException, InvalidParameterSpecException, NoSuchProviderException {
        DHParameterSpec dHParameterSpec = i2 < 0 ? new DHParameterSpec(bigInteger, bigInteger2) : new DHParameterSpec(bigInteger, bigInteger2, i2);
        AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("DH", "IAIK");
        algorithmParameters.init(dHParameterSpec);
        return algorithmParameters;
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0167, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0171, code lost:
    
        throw new java.lang.RuntimeException(r0.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0172, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x017c, code lost:
    
        throw new java.lang.RuntimeException(r0.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x017d, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0187, code lost:
    
        throw new java.lang.RuntimeException(r0.toString());
     */
    @Override // java.security.AlgorithmParameterGeneratorSpi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected java.security.AlgorithmParameters engineGenerateParameters() {
        /*
            Method dump skipped, instruction units count: 426
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: iaik.security.dh.DHParameterGenerator.engineGenerateParameters():java.security.AlgorithmParameters");
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected void engineInit(int i, SecureRandom secureRandom) {
        this.a = secureRandom;
        this.b = i;
        this.c = -1;
        a();
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        this.a = secureRandom;
        if (!(algorithmParameterSpec instanceof DHGenParameterSpec)) {
            throw new InvalidAlgorithmParameterException("Parameter must be a DHGenParameterSpec.");
        }
        DHGenParameterSpec dHGenParameterSpec = (DHGenParameterSpec) algorithmParameterSpec;
        this.b = dHGenParameterSpec.getPrimeSize();
        int exponentSize = dHGenParameterSpec.getExponentSize();
        this.c = exponentSize;
        if (exponentSize > this.b) {
            throw new InvalidAlgorithmParameterException("The size of the exponent must be less than the size of the modulus");
        }
        a();
    }
}
