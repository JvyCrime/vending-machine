package iaik.security.ssl;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/* JADX INFO: loaded from: classes.dex */
class a {
    private byte[] a = new byte[8];
    private int b = 16;
    private Cipher c;
    private SecretKey d;
    private byte[] e;
    private byte[] f;

    a(Cipher cipher, SecretKey secretKey, byte[] bArr) {
        this.c = cipher;
        this.d = secretKey;
        this.e = bArr;
    }

    int a() {
        return this.b;
    }

    private void b() {
        int length = this.a.length - 1;
        while (true) {
            byte[] bArr = this.a;
            int i = length - 1;
            byte b = (byte) (bArr[length] + 1);
            bArr[length] = b;
            if (b != 0 || i < 0) {
                return;
            } else {
                length = i;
            }
        }
    }

    private byte[] a(byte[] bArr, int i, int i2) {
        byte[] bArr2 = this.f;
        if (bArr2 == null || bArr2.length < i2) {
            this.f = new byte[i2];
        }
        System.arraycopy(bArr, i, this.f, 0, i2);
        return this.f;
    }

    int a(byte[] bArr, int i, int i2, byte[] bArr2, int i3, SecureRandom secureRandom) throws Exception {
        byte[] bArrA = a(bArr, i, i2);
        byte[] bArr3 = new byte[13];
        System.arraycopy(this.a, 0, bArr3, 0, 8);
        System.arraycopy(bArr, 0, bArr3, 8, 3);
        bArr3[11] = (byte) (i2 >> 8);
        bArr3[12] = (byte) i2;
        byte[] bArr4 = this.e;
        byte[] bArr5 = new byte[bArr4.length + 8];
        System.arraycopy(bArr4, 0, bArr5, 0, bArr4.length);
        System.arraycopy(this.a, 0, bArr5, this.e.length, 8);
        byte[] bArr6 = this.a;
        int length = bArr6.length;
        System.arraycopy(bArr6, 0, bArr2, i3, length);
        b();
        SecurityProvider securityProvider = SecurityProvider.getSecurityProvider();
        return length + securityProvider.aeadEncrypt(this.c, this.d, bArrA, 0, i2, bArr2, i3 + length, bArr3, bArr5, this.b, secureRandom == null ? securityProvider.getSecureRandom() : secureRandom);
    }

    int a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws Exception {
        int i4 = this.b + 8;
        if (i2 < i4) {
            throw new SSLException("Invalid length of AEAD message. Too short!");
        }
        int i5 = i2 - i4;
        byte[] bArr3 = new byte[13];
        System.arraycopy(this.a, 0, bArr3, 0, 8);
        System.arraycopy(bArr, 0, bArr3, 8, 3);
        bArr3[11] = (byte) (i5 >> 8);
        bArr3[12] = (byte) i5;
        byte[] bArr4 = this.e;
        byte[] bArr5 = new byte[bArr4.length + 8];
        System.arraycopy(bArr4, 0, bArr5, 0, bArr4.length);
        System.arraycopy(bArr, i, bArr5, this.e.length, 8);
        b();
        int i6 = i5 + this.b;
        return SecurityProvider.getSecurityProvider().aeadDecrypt(this.c, this.d, a(bArr, i + 8, i6), 0, i6, bArr2, i3, bArr3, bArr5, this.b);
    }
}
