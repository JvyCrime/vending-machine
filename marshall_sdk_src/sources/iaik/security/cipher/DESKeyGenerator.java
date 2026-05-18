package iaik.security.cipher;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.SecureRandom;
import javax.crypto.spec.DESKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class DESKeyGenerator extends VarLengthKeyGenerator {
    private static final byte[] a = {1, 2, 4, 8, 16, 32, 64, -128};

    public DESKeyGenerator() {
        super("DES", 64);
    }

    public static void adjustParity(byte[] bArr, int i) {
        for (int i2 = 0; i2 < 8; i2++) {
            int i3 = 1;
            int i4 = 0;
            while (true) {
                byte[] bArr2 = a;
                if (i3 >= bArr2.length) {
                    break;
                }
                if ((bArr[i2 + i] & bArr2[i3]) == bArr2[i3]) {
                    i4++;
                }
                i3++;
            }
            if ((i4 & 1) > 0) {
                int i5 = i2 + i;
                bArr[i5] = (byte) (bArr[i5] & (-2));
            } else {
                int i6 = i2 + i;
                bArr[i6] = (byte) (1 | bArr[i6]);
            }
        }
    }

    public static boolean checkParity(byte[] bArr, int i, boolean z) {
        for (int i2 = 0; i2 < 8; i2++) {
            int i3 = 0;
            int i4 = 0;
            while (true) {
                byte[] bArr2 = a;
                if (i3 >= bArr2.length) {
                    break;
                }
                if ((bArr[i2 + i] & bArr2[i3]) == bArr2[i3]) {
                    i4++;
                }
                i3++;
            }
            if ((i4 & 1) > 0) {
                if (!z) {
                    return false;
                }
            } else if (z) {
                return false;
            }
        }
        return true;
    }

    @Override // iaik.security.cipher.VarLengthKeyGenerator, javax.crypto.KeyGeneratorSpi
    protected javax.crypto.SecretKey engineGenerateKey() {
        byte[] encoded;
        do {
            try {
                encoded = super.engineGenerateKey().getEncoded();
                adjustParity(encoded, 0);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e.toString());
            }
        } while (DESKeySpec.isWeak(encoded, 0));
        return new SecretKey(encoded, "DES");
    }

    @Override // iaik.security.cipher.VarLengthKeyGenerator, javax.crypto.KeyGeneratorSpi
    protected void engineInit(int i, SecureRandom secureRandom) {
        if (i == -1 || i == 56) {
            i = 64;
        }
        if (i == 64) {
            super.engineInit(i, secureRandom);
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Invalid DES key length (");
        stringBuffer.append(i);
        stringBuffer.append("). Only 56 or 64 allowed!");
        throw new InvalidParameterException(stringBuffer.toString());
    }
}
