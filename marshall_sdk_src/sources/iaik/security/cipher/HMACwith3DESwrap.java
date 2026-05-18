package iaik.security.cipher;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class HMACwith3DESwrap extends AbstractC0023b {
    public HMACwith3DESwrap() {
        super(new J(), "3DESWrapHMAC", -1, (byte[]) CMS_KEY_WRAP_IV.clone());
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineInit(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        engineInit(i, key, secureRandom);
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineInit(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        engineInit(i, key, secureRandom);
    }

    @Override // iaik.security.cipher.AbstractC0023b
    protected void engineInit(int i, Key key, byte[] bArr, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        super.engineInit(i, key, new IvParameterSpec(bArr), secureRandom);
    }

    @Override // iaik.security.cipher.AbstractC0023b
    protected Key finishUnWrap(byte[] bArr, String str, int i) throws InvalidKeyException {
        try {
            return new SecretKeySpec(decomposeLCEKPAD(bArr), str);
        } catch (BadPaddingException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not unwrap key: ");
            stringBuffer.append(e.getMessage());
            throw new InvalidKeyException(stringBuffer.toString());
        }
    }

    @Override // iaik.security.cipher.AbstractC0023b
    protected byte[] prepareWrap(Key key) throws InvalidKeyException {
        return computeLCEKPAD(key);
    }
}
