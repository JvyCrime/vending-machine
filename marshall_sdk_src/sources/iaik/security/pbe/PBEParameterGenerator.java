package iaik.security.pbe;

import iaik.security.random.SecRandom;
import java.security.AlgorithmParameterGeneratorSpi;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.PBEParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public class PBEParameterGenerator extends AlgorithmParameterGeneratorSpi {
    private SecureRandom a;
    private int b = 8;
    private int c = 2000;

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected AlgorithmParameters engineGenerateParameters() {
        if (this.a == null) {
            this.a = SecRandom.getDefault();
        }
        byte[] bArr = new byte[this.b];
        this.a.nextBytes(bArr);
        PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(bArr, this.c);
        try {
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("PBE", "IAIK");
            algorithmParameters.init(pBEParameterSpec);
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
        this.a = secureRandom;
        this.b = i;
    }

    @Override // java.security.AlgorithmParameterGeneratorSpi
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        this.a = secureRandom;
        if (!(algorithmParameterSpec instanceof PBEGenParameterSpec)) {
            throw new InvalidAlgorithmParameterException("param must be an instance of PBEGenParameterSpec.");
        }
        PBEGenParameterSpec pBEGenParameterSpec = (PBEGenParameterSpec) algorithmParameterSpec;
        this.b = pBEGenParameterSpec.getSaltLength();
        this.c = pBEGenParameterSpec.getIterationCount();
    }
}
