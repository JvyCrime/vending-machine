package iaik.security.cipher;

import iaik.pkcs.pkcs1.Padding;
import iaik.utils.CryptoUtils;
import iaik.utils.InternalErrorException;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

/* JADX INFO: renamed from: iaik.security.cipher.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
abstract class AbstractC0024c extends AbstractC0022a {
    public static final byte[] NIST_KEY_WRAP_IV = {-90, -90, -90, -90, -90, -90, -90, -90};
    private ByteArrayOutputStream j;

    AbstractC0024c(AbstractC0028i abstractC0028i) {
        super(abstractC0028i);
        try {
            super.engineSetMode("ECB");
            super.engineSetPadding(Padding.PADDING_NONE);
            this.j = new ByteArrayOutputStream();
        } catch (NoSuchAlgorithmException e) {
            throw new InternalErrorException("Cipher mode CBC not supported", e);
        } catch (NoSuchPaddingException e2) {
            throw new InternalErrorException("Padding scheme 'NoPadding' not supported", e2);
        }
    }

    byte[] a(byte[] bArr, int i, int i2) {
        if (this.j.size() != 0) {
            if (bArr != null) {
                this.j.write(bArr, i, i2);
            }
            byte[] byteArray = this.j.toByteArray();
            this.j.reset();
            return byteArray;
        }
        if (bArr == null) {
            return null;
        }
        if (i == 0 && i2 == bArr.length) {
            return bArr;
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws BadPaddingException, IllegalBlockSizeException, ShortBufferException {
        byte[] bArrEngineDoFinal = engineDoFinal(bArr, i, i2);
        if (bArrEngineDoFinal == null) {
            return 0;
        }
        if (bArrEngineDoFinal.length > bArr2.length - i3) {
            throw new ShortBufferException();
        }
        System.arraycopy(bArrEngineDoFinal, 0, bArr2, i3, bArrEngineDoFinal.length);
        return bArrEngineDoFinal.length;
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public byte[] engineDoFinal(byte[] bArr, int i, int i2) throws BadPaddingException, IllegalBlockSizeException {
        byte[] bArrA = a(bArr, i, i2);
        if (bArrA == null || bArrA.length % 8 != 0) {
            throw new IllegalBlockSizeException("Input data must be a multiple of 8!");
        }
        if (this.d != 1 && this.d != 3) {
            byte[] bArr2 = new byte[8];
            int length = (bArrA.length / 8) - 1;
            byte[][] bArr3 = (byte[][]) Array.newInstance((Class<?>) byte.class, length, 8);
            byte[] bArr4 = new byte[length * 8];
            if (bArrA.length == 16) {
                byte[] bArrEngineDoFinal = super.engineDoFinal(bArrA, 0, bArrA.length);
                if (CryptoUtils.compareBlock(bArrEngineDoFinal, 0, NIST_KEY_WRAP_IV, 0, 8) >= 0) {
                    throw new BadPaddingException();
                }
                System.arraycopy(bArrEngineDoFinal, 8, bArr4, 0, 8);
            } else {
                System.arraycopy(bArrA, 0, bArr2, 0, 8);
                int i3 = 0;
                while (i3 < length) {
                    int i4 = i3 + 1;
                    System.arraycopy(bArrA, i4 * 8, bArr3[i3], 0, 8);
                    i3 = i4;
                }
                for (int i5 = 5; i5 >= 0; i5--) {
                    for (int i6 = length; i6 > 0; i6--) {
                        byte[] bArr5 = new byte[8];
                        CryptoUtils.spreadIntsToBytes(new int[]{0, (i5 * length) + i6}, 0, bArr5, 0, 2);
                        byte[] bArr6 = new byte[8];
                        CryptoUtils.xorBlock(bArr5, bArr2, bArr6);
                        byte[] bArr7 = new byte[16];
                        System.arraycopy(bArr6, 0, bArr7, 0, 8);
                        int i7 = i6 - 1;
                        System.arraycopy(bArr3[i7], 0, bArr7, 8, 8);
                        byte[] bArrEngineDoFinal2 = super.engineDoFinal(bArr7, 0, 16);
                        System.arraycopy(bArrEngineDoFinal2, 0, bArr2, 0, 8);
                        System.arraycopy(bArrEngineDoFinal2, 8, bArr3[i7], 0, 8);
                    }
                }
                if (CryptoUtils.compareBlock(bArr2, NIST_KEY_WRAP_IV) >= 0) {
                    throw new BadPaddingException();
                }
                for (int i8 = 0; i8 < length; i8++) {
                    System.arraycopy(bArr3[i8], 0, bArr4, i8 * 8, 8);
                }
            }
            return bArr4;
        }
        byte[] bArr8 = new byte[8];
        byte[] bArr9 = new byte[16];
        int length2 = bArrA.length / 8;
        byte[][] bArr10 = (byte[][]) Array.newInstance((Class<?>) byte.class, length2, 8);
        if (bArrA.length == 8) {
            byte[] bArr11 = NIST_KEY_WRAP_IV;
            System.arraycopy(bArr11, 0, bArr9, 0, bArr11.length);
            System.arraycopy(bArrA, 0, bArr9, bArr11.length, bArrA.length);
            return super.engineDoFinal(bArr9, 0, 16);
        }
        byte[] bArr12 = NIST_KEY_WRAP_IV;
        System.arraycopy(bArr12, 0, bArr8, 0, bArr12.length);
        for (int i9 = 0; i9 < length2; i9++) {
            System.arraycopy(bArrA, i9 * 8, bArr10[i9], 0, 8);
        }
        for (int i10 = 0; i10 < 6; i10++) {
            for (int i11 = 1; i11 <= length2; i11++) {
                byte[] bArr13 = new byte[8];
                CryptoUtils.spreadIntsToBytes(new int[]{0, (i10 * length2) + i11}, 0, bArr13, 0, 2);
                byte[] bArr14 = new byte[16];
                System.arraycopy(bArr8, 0, bArr14, 0, 8);
                int i12 = i11 - 1;
                System.arraycopy(bArr10[i12], 0, bArr14, 8, bArr10[i12].length);
                byte[] bArrEngineDoFinal3 = super.engineDoFinal(bArr14, 0, 16);
                byte[] bArr15 = new byte[8];
                System.arraycopy(bArrEngineDoFinal3, 0, bArr15, 0, 8);
                CryptoUtils.xorBlock(bArr13, bArr15, bArr8);
                System.arraycopy(bArrEngineDoFinal3, 8, bArr10[i12], 0, 8);
            }
        }
        byte[] bArr16 = new byte[(length2 + 1) * 8];
        System.arraycopy(bArr8, 0, bArr16, 0, 8);
        int i13 = 0;
        while (i13 < length2) {
            byte[] bArr17 = bArr10[i13];
            i13++;
            System.arraycopy(bArr17, 0, bArr16, i13 * 8, 8);
        }
        return bArr16;
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineSetMode(String str) throws NoSuchAlgorithmException {
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineSetPadding(String str) throws NoSuchPaddingException {
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws ShortBufferException {
        if (bArr == null) {
            return 0;
        }
        this.j.write(bArr, i, i2);
        return 0;
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public byte[] engineUpdate(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            return null;
        }
        this.j.write(bArr, i, i2);
        return null;
    }
}
