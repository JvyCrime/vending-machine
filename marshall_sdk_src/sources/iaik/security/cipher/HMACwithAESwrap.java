package iaik.security.cipher;

import iaik.security.random.SecRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/* JADX INFO: loaded from: classes.dex */
public class HMACwithAESwrap extends AESKeyWrap {
    @Override // iaik.security.cipher.AbstractC0024c
    byte[] a(byte[] bArr, int i, int i2) {
        byte[] bArrA = super.a(bArr, i, i2);
        if (this.d != 1 && this.d != 3) {
            return bArrA;
        }
        int length = bArrA.length;
        int i3 = length + 1;
        int i4 = i3 % 8;
        int i5 = i4 > 0 ? (8 - i4) + i3 : i3;
        byte[] bArr2 = new byte[i5];
        bArr2[0] = (byte) length;
        System.arraycopy(bArrA, 0, bArr2, 1, length);
        if (i3 < i5) {
            if (this.i == null) {
                this.i = SecRandom.getDefault();
            }
            int i6 = i5 - i3;
            byte[] bArr3 = new byte[i6];
            this.i.nextBytes(bArr3);
            System.arraycopy(bArr3, 0, bArr2, i3, i6);
        }
        return bArr2;
    }

    @Override // iaik.security.cipher.AbstractC0024c, iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public byte[] engineDoFinal(byte[] bArr, int i, int i2) throws BadPaddingException, IllegalBlockSizeException {
        byte[] bArrEngineDoFinal = super.engineDoFinal(bArr, i, i2);
        if (this.d != 2 && this.d != 4) {
            return bArrEngineDoFinal;
        }
        int i3 = bArrEngineDoFinal[0] & 255;
        if ((bArrEngineDoFinal.length - i3) - 1 > 7) {
            throw new BadPaddingException("LKEYPAD padding length has to be shorter than 8!");
        }
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArrEngineDoFinal, 1, bArr2, 0, i3);
        return bArr2;
    }
}
