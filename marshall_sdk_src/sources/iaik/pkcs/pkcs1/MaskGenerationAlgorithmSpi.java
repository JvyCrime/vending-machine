package iaik.pkcs.pkcs1;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public abstract class MaskGenerationAlgorithmSpi {
    protected MaskGenerationAlgorithmSpi() {
    }

    public Object clone() throws CloneNotSupportedException {
        if (this instanceof Cloneable) {
            return super.clone();
        }
        throw new CloneNotSupportedException();
    }

    protected abstract AlgorithmParameters engineGetParameters();

    protected abstract void engineMask(byte[] bArr, int i, int i2, int i3, byte[] bArr2, int i4);

    protected abstract void engineReset();

    protected abstract void engineSetParameters(AlgorithmParameters algorithmParameters) throws InvalidAlgorithmParameterException;

    protected abstract void engineSetParameters(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException;
}
