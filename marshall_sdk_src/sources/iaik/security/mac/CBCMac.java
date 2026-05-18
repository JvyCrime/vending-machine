package iaik.security.mac;

import iaik.security.ssl.SecurityProvider;
import iaik.utils.CryptoUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.ProviderException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.MacSpi;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

/* JADX INFO: loaded from: classes.dex */
public abstract class CBCMac extends MacSpi {
    private Cipher a;
    private Key b;
    private int c;
    private byte[] d;
    private int e;

    public static final class CBCMacAES extends CBCMac {
        public CBCMacAES() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
            super(SecurityProvider.ALG_KEYGEN_AES);
        }
    }

    public static final class CBCMacDES extends CBCMac {
        public CBCMacDES() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
            super("DES");
        }
    }

    public static final class CBCMacDESede extends CBCMac {
        public CBCMacDESede() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
            super("DESede");
        }
    }

    CBCMac(String str) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append("/CBC/NoPadding");
        Cipher cipher = Cipher.getInstance(stringBuffer.toString(), "IAIK");
        this.a = cipher;
        int blockSize = cipher.getBlockSize();
        this.c = blockSize;
        this.d = new byte[blockSize];
    }

    private void a(Key key) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.a.init(1, key, new IvParameterSpec(new byte[this.c]));
    }

    @Override // javax.crypto.MacSpi
    protected byte[] engineDoFinal() {
        int i = this.e;
        byte[] bArr = this.d;
        if (i != bArr.length) {
            this.e = i + 1;
            bArr[i] = 0;
            while (true) {
                int i2 = this.e;
                byte[] bArr2 = this.d;
                if (i2 < bArr2.length) {
                    this.e = i2 + 1;
                    bArr2[i2] = 0;
                }
            }
        }
        try {
            byte[] bArrDoFinal = this.a.doFinal(this.d);
            engineReset();
            return bArrDoFinal;
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("encryption error: ");
            stringBuffer.append(e);
            throw new ProviderException(stringBuffer.toString());
        }
    }

    @Override // javax.crypto.MacSpi
    protected int engineGetMacLength() {
        return this.c;
    }

    @Override // javax.crypto.MacSpi
    protected void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameterSpec instanceof IvParameterSpec) {
            byte[] iv = ((IvParameterSpec) algorithmParameterSpec).getIV();
            int length = iv.length;
            if (length != this.c) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Invalid iv length (");
                stringBuffer.append(length);
                stringBuffer.append("). Expected ");
                stringBuffer.append(this.c);
                throw new InvalidAlgorithmParameterException(stringBuffer.toString());
            }
            for (byte b : iv) {
                if (b != 0) {
                    throw new InvalidAlgorithmParameterException("Invalid iv! All iv bytes must be zero!");
                }
            }
        }
        a(key);
        this.b = key;
    }

    @Override // javax.crypto.MacSpi
    protected void engineReset() {
        try {
            a(this.b);
            this.e = 0;
            CryptoUtils.zeroBlock(this.d);
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("cipher initialization error: ");
            stringBuffer.append(e);
            throw new ProviderException(stringBuffer.toString());
        }
    }

    @Override // javax.crypto.MacSpi
    protected void engineUpdate(byte b) {
        engineUpdate(new byte[]{b}, 0, 1);
    }

    @Override // javax.crypto.MacSpi
    protected void engineUpdate(byte[] bArr, int i, int i2) {
        if (i2 == 0) {
            return;
        }
        int i3 = this.e;
        if (i3 > 0) {
            int iMin = Math.min(i2, this.c - i3);
            System.arraycopy(bArr, i, this.d, this.e, iMin);
            i += iMin;
            this.e += iMin;
            i2 -= iMin;
        }
        if (i2 > 0) {
            if (this.e == this.c) {
                this.a.update(this.d);
                this.e = 0;
            }
            int i4 = this.c;
            int i5 = ((i2 - 1) / i4) * i4;
            if (i5 > 0) {
                this.a.update(bArr, i, i5);
                i += i5;
                i2 -= i5;
            }
            System.arraycopy(bArr, i, this.d, this.e, i2);
            this.e += i2;
        }
    }
}
