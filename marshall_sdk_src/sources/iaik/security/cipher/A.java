package iaik.security.cipher;

import iaik.utils.CryptoUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.IllegalBlockSizeException;

/* JADX INFO: loaded from: classes.dex */
final class A extends t {
    private byte[] a;
    private byte[] b;
    private final t c;
    private int m;

    A(t tVar) {
        super(tVar.i(), -1, tVar.h());
        this.c = tVar;
    }

    @Override // iaik.security.cipher.t
    int a(int i, int i2, boolean z, boolean z2) {
        int i3 = i + i2;
        return z ? i3 : i3 - (i3 % this.g);
    }

    @Override // iaik.security.cipher.t
    int a(Key key) throws InvalidKeyException {
        return this.c.a(key);
    }

    @Override // iaik.security.cipher.t
    void a() {
        this.c.a();
        if (this.d == 3) {
            System.arraycopy(this.e, 0, this.a, 0, this.m);
        }
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.d != 3) {
            this.c.a(i, key, algorithmParameterSpec, secureRandom);
            this.e = null;
        } else {
            this.c.a(1, key, algorithmParameterSpec, secureRandom);
            this.e = a(i, algorithmParameterSpec, secureRandom, this.m);
            System.arraycopy(this.e, 0, this.a, 0, this.m);
        }
    }

    @Override // iaik.security.cipher.t
    void a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        if (this.d != 3) {
            this.c.a(bArr, i, i2, bArr2, i3);
            return;
        }
        while (i2 > 0) {
            this.c.b(this.a, 0, this.m, this.b, 0);
            CryptoUtils.xorBlock(bArr, i, this.b, 0, bArr2, i3, this.g);
            System.arraycopy(this.a, this.g, this.a, 0, this.m - this.g);
            System.arraycopy(this.b, 0, this.a, this.m - this.g, this.g);
            i2 -= this.g;
            i += this.g;
            i3 += this.g;
        }
    }

    @Override // iaik.security.cipher.t
    boolean a(int i, int i2) {
        if (i == 3) {
            this.j = false;
            this.k = true;
        }
        if (i != 3 && i != 1) {
            this.d = 0;
            boolean zA = this.c.a(i, i2);
            this.g = this.c.g();
            this.c.a(false);
            return zA;
        }
        this.d = i;
        this.c.a(1, 0);
        int iG = this.c.g();
        this.m = iG;
        if (i == 3) {
            if (i2 == -1) {
                this.g = iG;
            } else {
                this.g = i2;
            }
            int i3 = this.m;
            this.b = new byte[i3];
            this.a = new byte[i3];
        } else {
            this.g = iG;
        }
        this.c.a(false);
        return true;
    }

    @Override // iaik.security.cipher.t
    byte[] a_() {
        return this.d == 3 ? this.e : this.c.a_();
    }

    @Override // iaik.security.cipher.t
    void b(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        if (this.d != 3) {
            this.c.b(bArr, i, i2, bArr2, i3);
            return;
        }
        while (i2 > 0) {
            this.c.b(this.a, 0, this.m, this.b, 0);
            CryptoUtils.xorBlock(bArr, i, this.b, 0, bArr2, i3, this.g);
            System.arraycopy(this.a, this.g, this.a, 0, this.m - this.g);
            System.arraycopy(this.b, 0, this.a, this.m - this.g, this.g);
            i2 -= this.g;
            i += this.g;
            i3 += this.g;
        }
    }

    @Override // iaik.security.cipher.t
    void c(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        if (this.d != 3) {
            this.c.b(bArr, i, i2, bArr2, i3);
            return;
        }
        int i4 = i;
        int i5 = i2;
        int i6 = i3;
        while (i5 - this.g >= 0) {
            this.c.b(this.a, 0, this.m, this.b, 0);
            CryptoUtils.xorBlock(bArr, i4, this.b, 0, bArr2, i6, this.g);
            System.arraycopy(this.a, this.g, this.a, 0, this.m - this.g);
            System.arraycopy(this.b, 0, this.a, this.m - this.g, this.g);
            i5 -= this.g;
            i4 += this.g;
            i6 += this.g;
        }
        this.c.b(this.a, 0, this.m, this.b, 0);
        CryptoUtils.xorBlock(bArr, i4, this.b, 0, bArr2, i6, i5);
    }

    @Override // iaik.security.cipher.t
    void d(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        if (this.d != 3) {
            this.c.a(bArr, i, i2, bArr2, i3);
            return;
        }
        int i4 = i;
        int i5 = i2;
        int i6 = i3;
        while (i5 - this.g >= 0) {
            this.c.b(this.a, 0, this.m, this.b, 0);
            CryptoUtils.xorBlock(bArr, i4, this.b, 0, bArr2, i6, this.g);
            System.arraycopy(this.a, this.g, this.a, 0, this.m - this.g);
            System.arraycopy(this.b, 0, this.a, this.m - this.g, this.g);
            i5 -= this.g;
            i4 += this.g;
            i6 += this.g;
        }
        this.c.b(this.a, 0, this.m, this.b, 0);
        CryptoUtils.xorBlock(bArr, i4, this.b, 0, bArr2, i6, i5);
    }
}
