package iaik.security.random;

import iaik.security.cipher.Rijndael;
import iaik.security.cipher.SecretKey;
import iaik.security.ssl.SecurityProvider;
import iaik.utils.CryptoUtils;
import iaik.utils.InternalErrorException;
import iaik.utils.Util;
import java.security.InvalidKeyException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/* JADX INFO: loaded from: classes.dex */
class f extends u {
    public static final int MAX_ADDITIONAL_INPUT_LENGTH = 65536;
    public static final int MAX_NUMBER_OF_BYTES_PER_REQUEST = 65536;
    private final byte[] a;
    private final Rijndael b;
    private final String c;
    private final int d;
    private byte[] e;
    private final byte[] r;

    f(int i) {
        super(i, b(i), 16, b(i), b(i), b(i), b(i), 65536, 16777216L, true);
        this.a = new byte[]{1};
        this.b = new Rijndael();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(SecurityProvider.ALG_KEYGEN_AES);
        stringBuffer.append(i << 3);
        stringBuffer.append("/ECB/NoPadding");
        this.c = stringBuffer.toString();
        this.d = i;
        this.e = new byte[this.o];
        this.r = new byte[i];
    }

    private static int b(int i) {
        return i + 16;
    }

    private void b(byte[] bArr) {
        byte[] bArr2 = new byte[this.o * ((int) Math.ceil(((double) this.g) / ((double) this.o)))];
        int i = 0;
        while (i < this.g) {
            try {
                this.e = CryptoUtils.addModBlockSize(this.o, this.e, this.a);
                this.b.engineInit(1, d(), null);
                Rijndael rijndael = this.b;
                byte[] bArr3 = this.e;
                System.arraycopy(rijndael.engineDoFinal(bArr3, 0, bArr3.length), 0, bArr2, i, this.o);
                i += this.o;
            } catch (IllegalStateException e) {
                throw new InternalErrorException("Illegal state!", e);
            } catch (InvalidKeyException e2) {
                throw new InternalErrorException("Invalid key!", e2);
            } catch (BadPaddingException e3) {
                throw new InternalErrorException("Bad padding!", e3);
            } catch (IllegalBlockSizeException e4) {
                throw new InternalErrorException("Illegal block size!", e4);
            }
        }
        byte[] bArrResizeArray = Util.resizeArray(bArr2, this.g);
        CryptoUtils.xorBlock(bArrResizeArray, bArr, bArrResizeArray);
        System.arraycopy(bArrResizeArray, 0, this.r, 0, this.d);
        System.arraycopy(bArrResizeArray, bArrResizeArray.length - this.o, this.e, 0, this.o);
    }

    private SecretKey d() {
        return new SecretKey(this.r, this.c);
    }

    @Override // iaik.security.random.u
    void a() {
        this.f = 0L;
        byte[] bArr = this.e;
        if (bArr != null) {
            CryptoUtils.zeroBlock(bArr);
        }
        byte[] bArr2 = this.r;
        if (bArr2 != null) {
            CryptoUtils.zeroBlock(bArr2);
        }
    }

    @Override // iaik.security.random.u
    void a(byte[] bArr, byte[] bArr2) {
        if (bArr2 != null) {
            if (bArr2.length < this.g) {
                bArr2 = Util.resizeArray(bArr2, this.g);
            }
            byte[] bArr3 = new byte[bArr.length];
            CryptoUtils.xorBlock(bArr, bArr2, bArr3);
            b(bArr3);
        } else {
            b(bArr);
        }
        this.f = 1L;
    }

    @Override // iaik.security.random.u
    void a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        if (bArr3.length < this.g) {
            bArr3 = Util.resizeArray(bArr3, this.g);
        }
        byte[] bArr4 = new byte[bArr3.length];
        CryptoUtils.xorBlock(bArr, bArr3, bArr4);
        CryptoUtils.zeroBlock(this.r);
        CryptoUtils.zeroBlock(this.e);
        b(bArr4);
        this.f = 1L;
    }

    @Override // iaik.security.random.u
    byte[] a(int i) {
        if (this.f > this.l) {
            this.p = true;
        }
        byte[] bArr = new byte[this.g];
        byte[] bArr2 = new byte[this.o * ((int) Math.ceil(((double) i) / ((double) this.o)))];
        int i2 = 0;
        while (i2 < i) {
            try {
                this.e = CryptoUtils.addModBlockSize(this.o, this.e, this.a);
                this.b.engineInit(1, d(), null);
                Rijndael rijndael = this.b;
                byte[] bArr3 = this.e;
                System.arraycopy(rijndael.engineDoFinal(bArr3, 0, bArr3.length), 0, bArr2, i2, this.o);
                i2 += this.o;
            } catch (IllegalStateException e) {
                throw new InternalErrorException("Illegal state!", e);
            } catch (InvalidKeyException e2) {
                throw new InternalErrorException("Invalid key!", e2);
            } catch (BadPaddingException e3) {
                throw new InternalErrorException("Bad padding!", e3);
            } catch (IllegalBlockSizeException e4) {
                throw new InternalErrorException("Illegal block size!", e4);
            }
        }
        byte[] bArr4 = new byte[i];
        System.arraycopy(bArr2, 0, bArr4, 0, i);
        b(bArr);
        this.f++;
        return bArr4;
    }
}
