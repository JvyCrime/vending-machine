package iaik.security.mac;

import com.magtek.mobile.android.mtusdk.cms.EMVL2CommandID;
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
public abstract class CMac extends MacSpi {
    private String a;
    private Cipher b;
    private Key c;
    private byte[] d;
    private byte[] e;
    private int f;
    private byte[] g;
    private int h;
    private int i;

    CMac(String str) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        int i;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append("/CBC/NoPadding");
        Cipher cipher = Cipher.getInstance(stringBuffer.toString(), "IAIK");
        this.b = cipher;
        this.a = str;
        int blockSize = cipher.getBlockSize();
        this.f = blockSize;
        this.g = new byte[blockSize];
        if (blockSize == 8) {
            i = 27;
        } else {
            if (blockSize != 16) {
                throw new ProviderException("CMAC only supports ciphers with block size 64 or 128 bit");
            }
            i = EMVL2CommandID.EMV_L2_PIN_ENTRY_SHOW_PROMPT;
        }
        this.i = i;
    }

    private void a(Key key) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.b.init(1, key, new IvParameterSpec(new byte[this.f]));
    }

    private byte[] a() {
        if (this.d == null) {
            byte[] bArr = new byte[this.f];
            try {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(this.a);
                stringBuffer.append("/ECB/NoPadding");
                Cipher cipher = Cipher.getInstance(stringBuffer.toString(), "IAIK");
                cipher.init(1, this.c);
                byte[] bArrDoFinal = cipher.doFinal(bArr);
                byte[] bArrShiftLeft = CryptoUtils.shiftLeft(bArrDoFinal);
                if ((bArrDoFinal[0] & 128) != 0) {
                    int length = bArrShiftLeft.length - 1;
                    bArrShiftLeft[length] = (byte) (bArrShiftLeft[length] ^ this.i);
                }
                this.d = bArrShiftLeft;
            } catch (Exception e) {
                StringBuffer stringBuffer2 = new StringBuffer();
                stringBuffer2.append("unxecpected internal error: ");
                stringBuffer2.append(e);
                throw new ProviderException(stringBuffer2.toString());
            }
        }
        return this.d;
    }

    private byte[] b() {
        if (this.e == null) {
            byte[] bArrA = a();
            byte[] bArrShiftLeft = CryptoUtils.shiftLeft(bArrA);
            if ((bArrA[0] & 128) != 0) {
                int length = bArrShiftLeft.length - 1;
                bArrShiftLeft[length] = (byte) (bArrShiftLeft[length] ^ this.i);
            }
            this.e = bArrShiftLeft;
        }
        return this.e;
    }

    @Override // javax.crypto.MacSpi
    protected byte[] engineDoFinal() {
        byte[] bArrA;
        int i = this.h;
        byte[] bArr = this.g;
        if (i != bArr.length) {
            this.h = i + 1;
            bArr[i] = -128;
            while (true) {
                int i2 = this.h;
                byte[] bArr2 = this.g;
                if (i2 >= bArr2.length) {
                    break;
                }
                this.h = i2 + 1;
                bArr2[i2] = 0;
            }
            bArrA = b();
        } else {
            bArrA = a();
        }
        byte[] bArr3 = this.g;
        CryptoUtils.xorBlock(bArr3, 0, bArrA, 0, bArr3, 0, this.f);
        try {
            byte[] bArrDoFinal = this.b.doFinal(this.g);
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
        return this.f;
    }

    @Override // javax.crypto.MacSpi
    protected void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        a(key);
        this.c = key;
        this.d = null;
        this.e = null;
    }

    @Override // javax.crypto.MacSpi
    protected void engineReset() {
        try {
            a(this.c);
            this.h = 0;
            CryptoUtils.zeroBlock(this.g);
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
        int i3 = this.h;
        if (i3 > 0) {
            int iMin = Math.min(i2, this.f - i3);
            System.arraycopy(bArr, i, this.g, this.h, iMin);
            i += iMin;
            this.h += iMin;
            i2 -= iMin;
        }
        if (i2 > 0) {
            if (this.h == this.f) {
                this.b.update(this.g);
                this.h = 0;
            }
            int i4 = this.f;
            int i5 = ((i2 - 1) / i4) * i4;
            if (i5 > 0) {
                this.b.update(bArr, i, i5);
                i += i5;
                i2 -= i5;
            }
            System.arraycopy(bArr, i, this.g, this.h, i2);
            this.h += i2;
        }
    }
}
