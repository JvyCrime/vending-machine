package iaik.security.cipher;

import com.bitmick.marshall.vmc.marshall_t;
import iaik.pkcs.pkcs1.Padding;
import iaik.security.md.SHA;
import iaik.security.random.SecRandom;
import iaik.utils.CriticalObject;
import iaik.utils.CryptoUtils;
import iaik.utils.InternalErrorException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: renamed from: iaik.security.cipher.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
abstract class AbstractC0023b extends AbstractC0022a {
    public static final byte[] CMS_KEY_WRAP_IV = {marshall_t.status_vpos_please_insert_or_swipe_card, -35, -94, 44, 121, -24, 33, 5};
    protected int cipherTextLength;
    private String j;
    protected Key kek;
    protected byte[] keyWrapIV;
    protected SecureRandom random;

    AbstractC0023b(t tVar, String str, int i, byte[] bArr) {
        super(tVar);
        try {
            engineSetMode("CBC");
            engineSetPadding(Padding.PADDING_NONE);
            this.j = str;
            this.cipherTextLength = i;
            this.keyWrapIV = bArr;
            if (bArr == null) {
                this.keyWrapIV = CMS_KEY_WRAP_IV;
            }
        } catch (NoSuchAlgorithmException e) {
            throw new InternalErrorException("Cipher mode CBC not supported", e);
        } catch (NoSuchPaddingException e2) {
            throw new InternalErrorException("Padding scheme 'NoPadding' not supported", e2);
        }
    }

    private static final void a(byte[] bArr, byte[] bArr2, int i, int i2) {
        if (i2 < 0) {
            i2 = 8;
        }
        if (i2 > 20) {
            throw new IndexOutOfBoundsException("Only 20 bytes available from SHA-1!");
        }
        System.arraycopy(new SHA().digest(bArr), 0, bArr2, i, i2);
    }

    protected byte[] computeLCEKPAD(Key key) {
        byte[] encoded = key.getEncoded();
        int length = encoded.length;
        int i = length + 1;
        int i2 = i % 8;
        int i3 = i2 > 0 ? (8 - i2) + i : i;
        byte[] bArr = new byte[i3];
        bArr[0] = (byte) length;
        System.arraycopy(encoded, 0, bArr, 1, length);
        if (i < i3) {
            SecureRandom random = getRandom();
            this.random = random;
            int i4 = i3 - i;
            byte[] bArr2 = new byte[i4];
            random.nextBytes(bArr2);
            System.arraycopy(bArr2, 0, bArr, i, i4);
        }
        return bArr;
    }

    protected byte[] decomposeLCEKPAD(byte[] bArr) throws BadPaddingException {
        int i = bArr[0] & 255;
        if ((bArr.length - i) - 1 > 7) {
            throw new BadPaddingException("LCEKPAD padding length has to be shorter than 8!");
        }
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 1, bArr2, 0, i);
        return bArr2;
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public int engineDoFinal(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws BadPaddingException, IllegalBlockSizeException, ShortBufferException {
        throw new RuntimeException("doFinal not supported by this key wrap cipher!");
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public byte[] engineDoFinal(byte[] bArr, int i, int i2) throws BadPaddingException, IllegalBlockSizeException {
        throw new RuntimeException("doFinal not supported by this key wrap cipher!");
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public byte[] engineGetIV() {
        return null;
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public AlgorithmParameters engineGetParameters() {
        return null;
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineInit(int i, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        this.kek = key;
        this.random = secureRandom;
        this.d = a(i);
        try {
            this.a.a(this.d, key, this.d == 2 ? new IvParameterSpec(this.keyWrapIV) : null, getRandom());
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidKeyException(e.toString());
        }
    }

    protected abstract void engineInit(int i, Key key, byte[] bArr, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException;

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineSetMode(String str) throws NoSuchAlgorithmException {
        super.engineSetMode("CBC");
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public void engineSetPadding(String str) throws NoSuchPaddingException {
        super.engineSetPadding(Padding.PADDING_NONE);
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    protected Key engineUnwrap(byte[] bArr, String str, int i) throws NoSuchAlgorithmException, InvalidKeyException {
        if (this.d != 2) {
            throw new NoSuchAlgorithmException("Invalid mode used for initializing the cipher. Must be DECRYPT_MODE or UNWRAP_MODE!");
        }
        if (i != 3) {
            throw new InvalidKeyException("Wrong key type. Only secret keys may be wrapped by this cipher!");
        }
        int i2 = this.cipherTextLength;
        if (i2 > 0 && bArr.length != i2) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Wrapped key has invalid length; expected ");
            stringBuffer.append(this.cipherTextLength);
            throw new InvalidKeyException(stringBuffer.toString());
        }
        if (bArr.length % this.a.g != 0) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Wrapped key has invalid length; expected multiple of ");
            stringBuffer2.append(this.a.g);
            throw new InvalidKeyException(stringBuffer2.toString());
        }
        try {
            byte[] bArrEngineDoFinal = super.engineDoFinal(bArr, 0, bArr.length);
            CryptoUtils.reverseArray(bArrEngineDoFinal, 0, bArrEngineDoFinal.length);
            int i3 = this.a.g;
            byte[] bArr2 = new byte[i3];
            System.arraycopy(bArrEngineDoFinal, 0, bArr2, 0, this.a.g);
            int length = bArrEngineDoFinal.length - i3;
            byte[] bArr3 = new byte[length];
            System.arraycopy(bArrEngineDoFinal, i3, bArr3, 0, length);
            engineInit(2, this.kek, bArr2, getRandom());
            byte[] bArrEngineDoFinal2 = super.engineDoFinal(bArr3, 0, length);
            int length2 = bArrEngineDoFinal2.length - this.a.g;
            byte[] bArr4 = new byte[length2];
            System.arraycopy(bArrEngineDoFinal2, 0, bArr4, 0, length2);
            a(bArr4, bArr3, 0, this.a.g);
            if (!CryptoUtils.secureEqualsBlock(bArr3, 0, bArrEngineDoFinal2, length2, this.a.g)) {
                throw new InvalidKeyException("Could not unwrap key: checksum error!");
            }
            CriticalObject.destroy(bArrEngineDoFinal);
            CriticalObject.destroy(bArr3);
            CriticalObject.destroy(bArrEngineDoFinal2);
            return finishUnWrap(bArr4, str, i);
        } catch (InvalidAlgorithmParameterException e) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Could not unwrap key: ");
            stringBuffer3.append(e.toString());
            throw new InvalidKeyException(stringBuffer3.toString());
        } catch (BadPaddingException e2) {
            StringBuffer stringBuffer4 = new StringBuffer();
            stringBuffer4.append("Could not unwrap key: ");
            stringBuffer4.append(e2.toString());
            throw new InvalidKeyException(stringBuffer4.toString());
        } catch (IllegalBlockSizeException e3) {
            StringBuffer stringBuffer5 = new StringBuffer();
            stringBuffer5.append("Could not unwrap key: ");
            stringBuffer5.append(e3.toString());
            throw new InvalidKeyException(stringBuffer5.toString());
        }
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public int engineUpdate(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws ShortBufferException {
        throw new RuntimeException("update not supported by this key wrap cipher!");
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    public byte[] engineUpdate(byte[] bArr, int i, int i2) {
        throw new RuntimeException("update not supported by this key wrap cipher!");
    }

    @Override // iaik.security.cipher.AbstractC0022a, javax.crypto.CipherSpi
    protected byte[] engineWrap(Key key) throws IllegalBlockSizeException, InvalidKeyException {
        if (this.d != 1) {
            throw new InvalidKeyException("Invalid mode used for initializing the cipher. Must be ENCRYPT_MODE or WRAP_MODE!");
        }
        byte[] encoded = key.getEncoded();
        if (encoded == null || encoded.length == 0) {
            throw new InvalidKeyException("Could not encode key for wrapping!");
        }
        try {
            byte[] bArrPrepareWrap = prepareWrap(key);
            int length = bArrPrepareWrap.length + this.a.g;
            byte[] bArr = new byte[length];
            System.arraycopy(bArrPrepareWrap, 0, bArr, 0, bArrPrepareWrap.length);
            a(bArrPrepareWrap, bArr, bArrPrepareWrap.length, this.a.g);
            int iA = this.a.g + a(length, true, true);
            byte[] bArr2 = new byte[iA];
            super.engineDoFinal(bArr, 0, length, bArr2, this.a.g);
            System.arraycopy(super.engineGetIV(), 0, bArr2, 0, this.a.g);
            CryptoUtils.reverseArray(bArr2, 0, iA);
            engineInit(1, this.kek, this.keyWrapIV, getRandom());
            byte[] bArrEngineDoFinal = super.engineDoFinal(bArr2, 0, iA);
            CriticalObject.destroy(bArrPrepareWrap);
            CriticalObject.destroy(bArr);
            CriticalObject.destroy(bArr2);
            int i = this.cipherTextLength;
            if (i > 0 && bArrEngineDoFinal.length != i) {
                throw new InvalidKeyException("Error wrapping key: invalid cipher text length!");
            }
            return bArrEngineDoFinal;
        } catch (InvalidAlgorithmParameterException e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Could not wrap key: ");
            stringBuffer.append(e.toString());
            throw new InvalidKeyException(stringBuffer.toString());
        } catch (BadPaddingException e2) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Could not wrap key: ");
            stringBuffer2.append(e2.toString());
            throw new InvalidKeyException(stringBuffer2.toString());
        } catch (ShortBufferException e3) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Could not wrap key: ");
            stringBuffer3.append(e3.toString());
            throw new InvalidKeyException(stringBuffer3.toString());
        }
    }

    protected abstract Key finishUnWrap(byte[] bArr, String str, int i) throws InvalidKeyException;

    protected SecureRandom getRandom() {
        if (this.random == null) {
            this.random = SecRandom.getDefault();
        }
        return this.random;
    }

    protected abstract byte[] prepareWrap(Key key) throws InvalidKeyException;

    @Override // iaik.security.cipher.AbstractC0022a
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CMS ");
        stringBuffer.append(this.j);
        return stringBuffer.toString();
    }
}
