package iaik.security.random;

import iaik.security.cipher.SecretKey;
import iaik.security.mac.HMac;
import iaik.security.mac.HMacSha;
import iaik.security.mac.HMacSha224;
import iaik.security.mac.HMacSha256;
import iaik.security.mac.HMacSha384;
import iaik.utils.CryptoUtils;
import iaik.utils.InternalErrorException;
import java.security.InvalidKeyException;

/* JADX INFO: loaded from: classes.dex */
class h extends u {
    public static final int MAX_ADDITIONAL_INPUT_LENGTH = 65536;
    public static final int MAX_NUMBER_OF_BYTES_PER_REQUEST = 65536;
    private byte[] a;
    private byte[] b;
    private HMac c;
    private final String d;

    h(HMac hMac, int i) {
        super(a(hMac), i, hMac.engineGetMacLength(), a(hMac), 64, 65536, 65536, 65536, 16777216L, true);
        this.c = hMac;
        this.d = hMac instanceof HMacSha ? "HMAC/SHA" : hMac instanceof HMacSha224 ? "HMAC/SHA224" : hMac instanceof HMacSha256 ? "HMAC/SHA256" : hMac instanceof HMacSha384 ? "HMAC/SHA384" : "HMAC/SHA512";
        this.b = new byte[this.o];
        this.a = new byte[this.o];
    }

    private static int a(HMac hMac) {
        int iEngineGetMacLength = hMac.engineGetMacLength() >> 1;
        if (iEngineGetMacLength < 14) {
            return 14;
        }
        return iEngineGetMacLength;
    }

    private void b(byte[] bArr) {
        try {
            this.c.engineInit(d(), null);
            HMac hMac = this.c;
            byte[] bArr2 = this.a;
            hMac.engineUpdate(bArr2, 0, bArr2.length);
            this.c.engineUpdate((byte) 0);
            if (bArr != null) {
                this.c.engineUpdate(bArr, 0, bArr.length);
            }
            this.b = this.c.engineDoFinal();
            this.c.engineInit(d(), null);
            HMac hMac2 = this.c;
            byte[] bArr3 = this.a;
            hMac2.engineUpdate(bArr3, 0, bArr3.length);
            this.a = this.c.engineDoFinal();
            if (bArr != null) {
                this.c.engineInit(d(), null);
                HMac hMac3 = this.c;
                byte[] bArr4 = this.a;
                hMac3.engineUpdate(bArr4, 0, bArr4.length);
                this.c.engineUpdate((byte) 1);
                this.c.engineUpdate(bArr, 0, bArr.length);
                this.b = this.c.engineDoFinal();
                this.c.engineInit(d(), null);
                HMac hMac4 = this.c;
                byte[] bArr5 = this.a;
                hMac4.engineUpdate(bArr5, 0, bArr5.length);
                this.a = this.c.engineDoFinal();
            }
        } catch (InvalidKeyException e) {
            throw new InternalErrorException("Invalid key", e);
        }
    }

    private SecretKey d() {
        return new SecretKey(this.b, this.d);
    }

    @Override // iaik.security.random.u
    void a() {
        this.f = 0L;
        byte[] bArr = this.a;
        if (bArr != null) {
            CryptoUtils.zeroBlock(bArr);
        }
        byte[] bArr2 = this.b;
        if (bArr2 != null) {
            CryptoUtils.zeroBlock(bArr2);
        }
    }

    @Override // iaik.security.random.u
    void a(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        if (bArr2 != null) {
            length += bArr2.length;
        }
        byte[] bArr3 = new byte[length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        if (bArr2 != null) {
            System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        }
        b(bArr3);
        this.f = 1L;
    }

    @Override // iaik.security.random.u
    void a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArr4 = new byte[bArr.length + bArr2.length + bArr3.length];
        System.arraycopy(bArr, 0, bArr4, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr4, bArr.length, bArr2.length);
        System.arraycopy(bArr3, 0, bArr4, bArr.length + bArr2.length, bArr3.length);
        CryptoUtils.zeroBlock(this.b);
        CryptoUtils.fillBlock(this.a, (byte) 1);
        b(bArr4);
        this.f = 1L;
    }

    @Override // iaik.security.random.u
    byte[] a(int i) {
        if (this.f > this.l) {
            this.p = true;
        }
        byte[] bArr = new byte[this.o * ((int) Math.ceil(((double) i) / ((double) this.o)))];
        int i2 = 0;
        while (i2 < i) {
            try {
                this.c.engineInit(d(), null);
                HMac hMac = this.c;
                byte[] bArr2 = this.a;
                hMac.engineUpdate(bArr2, 0, bArr2.length);
                byte[] bArrEngineDoFinal = this.c.engineDoFinal();
                this.a = bArrEngineDoFinal;
                System.arraycopy(bArrEngineDoFinal, 0, bArr, i2, this.o);
                i2 += this.o;
            } catch (InvalidKeyException e) {
                throw new InternalErrorException("Invalid key", e);
            }
        }
        byte[] bArr3 = new byte[i];
        System.arraycopy(bArr, 0, bArr3, 0, i);
        b((byte[]) null);
        this.f++;
        return bArr3;
    }
}
