package iaik.pkcs.pkcs1;

import iaik.utils.IaikSecurity;
import iaik.utils.InternalErrorException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public abstract class MaskGenerationAlgorithm extends MaskGenerationAlgorithmSpi {
    private String a;

    protected MaskGenerationAlgorithm(String str) {
        this.a = str;
    }

    public static final MaskGenerationAlgorithm getInstance(String str) throws NoSuchAlgorithmException {
        try {
            return getInstance(str, null);
        } catch (NoSuchProviderException e) {
            throw new InternalErrorException(e);
        }
    }

    public static final MaskGenerationAlgorithm getInstance(String str, String str2) throws NoSuchAlgorithmException, NoSuchProviderException {
        return (MaskGenerationAlgorithm) new IaikSecurity(str, "MaskGenerationAlgorithm", str2).getImplementation();
    }

    public final String getAlgorithm() {
        return this.a;
    }

    public final AlgorithmParameters getParameters() {
        return engineGetParameters();
    }

    public final void mask(byte[] bArr, int i, int i2, int i3, byte[] bArr2, int i4) {
        engineMask(bArr, i, i2, i3, bArr2, i4);
        reset();
    }

    public final void reset() {
        engineReset();
    }

    public final void setParameters(AlgorithmParameters algorithmParameters) throws InvalidAlgorithmParameterException {
        engineSetParameters(algorithmParameters);
    }

    public final void setParameters(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        engineSetParameters(algorithmParameterSpec);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Mask generation algorithm: ");
        stringBuffer.append(getAlgorithm());
        return stringBuffer.toString();
    }
}
