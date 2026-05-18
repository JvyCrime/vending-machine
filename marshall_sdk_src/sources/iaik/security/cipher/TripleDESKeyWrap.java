package iaik.security.cipher;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class TripleDESKeyWrap extends AbstractC0023b {
    private static boolean j = true;

    public TripleDESKeyWrap() {
        super(new J(), "3DESWrap3DES", 40, (byte[]) CMS_KEY_WRAP_IV.clone());
    }

    public static final void checkForOddParity(boolean z) {
        j = z;
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
        if (bArr.length != 24) {
            throw new InvalidKeyException("Invalid TripleDESKey; wrong length!");
        }
        if (j) {
            for (int i2 = 0; i2 < 3; i2++) {
                if (!DESKeyGenerator.checkParity(bArr, i2 * 8, true)) {
                    throw new InvalidKeyException("TripleDESKey not (odd) parity adjusted!");
                }
            }
        }
        return new SecretKeySpec(bArr, "DESede");
    }

    @Override // iaik.security.cipher.AbstractC0023b
    protected byte[] prepareWrap(Key key) throws InvalidKeyException {
        byte[] encoded = key.getEncoded();
        if (!key.getFormat().equals("RAW")) {
            throw new InvalidKeyException("Content encryption key format must be RAW");
        }
        byte[] bArr = new byte[24];
        if (encoded.length == 16) {
            System.arraycopy(encoded, 0, bArr, 0, 16);
            System.arraycopy(encoded, 0, bArr, 16, 8);
        } else {
            if (encoded.length != 24) {
                throw new InvalidKeyException("Triple-DES content encryption key must be 16 or 24 bytes long!");
            }
            System.arraycopy(encoded, 0, bArr, 0, 24);
        }
        DESKeyGenerator.adjustParity(bArr, 0);
        DESKeyGenerator.adjustParity(bArr, 8);
        DESKeyGenerator.adjustParity(bArr, 16);
        return bArr;
    }
}
