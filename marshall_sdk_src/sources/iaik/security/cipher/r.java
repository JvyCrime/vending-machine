package iaik.security.cipher;

import iaik.pkcs.pkcs1.Padding;
import iaik.utils.CryptoUtils;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* JADX INFO: loaded from: classes.dex */
final class r extends n {
    private final int m;

    r(t tVar) {
        super(tVar);
        this.m = tVar.g();
    }

    @Override // iaik.security.cipher.t
    int a(int i, int i2, boolean z, boolean z2) throws IllegalBlockSizeException {
        int i3;
        int i4 = i + i2;
        int i5 = this.g << 1;
        if (i4 <= i5) {
            i3 = 0;
        } else {
            int i6 = i4 % this.g;
            i3 = i6 == 0 ? i4 - i5 : (i4 - i6) - this.g;
        }
        return z ? i4 : i3;
    }

    @Override // iaik.security.cipher.n, iaik.security.cipher.t
    boolean a(int i, int i2) {
        if (i == 9) {
            this.j = false;
            this.k = true;
        }
        boolean zA = super.a(i, i2);
        this.c.a(false);
        return zA;
    }

    @Override // iaik.security.cipher.t
    boolean a(String str) throws NoSuchPaddingException {
        if (this.d != 9 || str.equalsIgnoreCase(Padding.PADDING_NONE)) {
            return true;
        }
        throw new NoSuchPaddingException("Mode CTS must be used with Padding 'NoPadding'.");
    }

    @Override // iaik.security.cipher.t
    void c(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        if (i2 <= this.m) {
            throw new IllegalBlockSizeException("Input data length must be longer than blocksize.");
        }
        int i4 = 0;
        while (true) {
            int i5 = this.m;
            if ((i5 << 1) + i4 >= i2) {
                CryptoUtils.xorBlock(bArr, i + i4, this.a, 0, this.b, 0, this.m);
                this.c.b(this.b, 0, this.m, this.a, 0);
                int i6 = i2 - (this.m + i4);
                System.arraycopy(this.a, 0, bArr2, i3 + i4 + this.m, i6);
                int i7 = this.m;
                int i8 = i4 + i7;
                byte[] bArr3 = new byte[i7];
                System.arraycopy(bArr, i + i8, bArr3, 0, i6);
                CryptoUtils.xorBlock(bArr3, 0, this.a, 0, this.b, 0, this.m);
                this.c.b(this.b, 0, this.m, this.a, 0);
                byte[] bArr4 = this.a;
                int i9 = this.m;
                System.arraycopy(bArr4, 0, bArr2, (i3 + i8) - i9, i9);
                return;
            }
            b(bArr, i + i4, i5, bArr2, i3 + i4);
            i4 += this.m;
        }
    }

    @Override // iaik.security.cipher.t
    void d(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        if (i2 <= this.m) {
            throw new IllegalBlockSizeException("Input data length must be longer than blocksize.");
        }
        int i4 = 0;
        while (true) {
            int i5 = this.m;
            if ((i5 << 1) + i4 >= i2) {
                int i6 = i2 - (i4 + i5);
                byte[] bArr3 = new byte[i5];
                int i7 = i + i4;
                System.arraycopy(bArr, i5 + i7, bArr3, 0, i6);
                this.c.a(bArr, i7, this.m, this.b, 0);
                CryptoUtils.xorBlock(this.b, 0, bArr3, 0, this.b, 0, this.m);
                System.arraycopy(this.b, 0, bArr2, i3 + i4 + this.m, i6);
                int i8 = i4 + this.m;
                System.arraycopy(bArr, i + i8, this.b, 0, i6);
                this.c.a(this.b, 0, this.m, this.b, 0);
                CryptoUtils.xorBlock(this.b, 0, this.a, 0, this.b, 0, this.m);
                byte[] bArr4 = this.b;
                int i9 = this.m;
                System.arraycopy(bArr4, 0, bArr2, (i3 + i8) - i9, i9);
                return;
            }
            a(bArr, i + i4, i5, bArr2, i3 + i4);
            i4 += this.m;
        }
    }
}
