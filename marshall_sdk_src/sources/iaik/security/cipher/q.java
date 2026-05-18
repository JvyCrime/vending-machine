package iaik.security.cipher;

import iaik.pkcs.pkcs1.Padding;
import iaik.utils.CryptoUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/* JADX INFO: loaded from: classes.dex */
final class q extends t {
    private byte[] a;
    private byte[] b;
    private final t c;
    private int m;
    private int n;

    q(t tVar) {
        super(tVar.i(), -1, tVar.h());
        this.c = tVar;
    }

    @Override // iaik.security.cipher.t
    int a(Key key) throws InvalidKeyException {
        return this.c.a(key);
    }

    @Override // iaik.security.cipher.t
    void a() {
        this.c.a();
        if (this.d == 6) {
            System.arraycopy(this.e, 0, this.a, 0, this.m);
            this.n = this.m;
        }
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.d != 6) {
            this.c.a(i, key, algorithmParameterSpec, secureRandom);
            this.e = null;
        } else {
            this.c.a(1, key, algorithmParameterSpec, secureRandom);
            this.e = a(i, algorithmParameterSpec, secureRandom, this.m);
            System.arraycopy(this.e, 0, this.a, 0, this.m);
            this.n = this.m;
        }
    }

    @Override // iaik.security.cipher.t
    void a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        if (this.d != 6) {
            this.c.a(bArr, i, i2, bArr2, i3);
            return;
        }
        while (true) {
            int i4 = i2 - 1;
            if (i2 <= 0) {
                return;
            }
            int i5 = this.n;
            int i6 = this.m;
            if (i5 >= i6) {
                this.c.b(this.a, 0, i6, this.b, 0);
                CryptoUtils.increment(this.a);
                this.n = 0;
            }
            int i7 = i + 1;
            byte b = bArr[i];
            byte[] bArr3 = this.b;
            int i8 = this.n;
            this.n = i8 + 1;
            bArr2[i3] = (byte) (b ^ bArr3[i8]);
            i3++;
            i2 = i4;
            i = i7;
        }
    }

    @Override // iaik.security.cipher.t
    boolean a(int i, int i2) {
        if (i != 6 && i != 1) {
            this.d = 0;
            boolean zA = this.c.a(i, i2);
            this.g = this.c.g();
            this.c.a(false);
            return zA;
        }
        this.d = i;
        this.c.a(1, 0);
        int iH = this.c.h();
        this.m = iH;
        if (i == 6) {
            this.b = new byte[iH];
            this.a = new byte[iH];
            this.g = 1;
        } else {
            this.g = iH;
        }
        this.c.a(false);
        return true;
    }

    @Override // iaik.security.cipher.t
    boolean a(String str) throws NoSuchPaddingException {
        if (this.d != 6 || str.equalsIgnoreCase(Padding.PADDING_NONE)) {
            return true;
        }
        throw new NoSuchPaddingException("Mode CTR must be used with Padding 'NoPadding'.");
    }

    @Override // iaik.security.cipher.t
    byte[] a_() {
        if (this.d != 6) {
            return this.c.a_();
        }
        if (this.e != null) {
            return (byte[]) this.e.clone();
        }
        return null;
    }

    @Override // iaik.security.cipher.t
    void b(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        if (this.d != 6) {
            this.c.b(bArr, i, i2, bArr2, i3);
            return;
        }
        while (true) {
            int i4 = i2 - 1;
            if (i2 <= 0) {
                return;
            }
            int i5 = this.n;
            int i6 = this.m;
            if (i5 >= i6) {
                this.c.b(this.a, 0, i6, this.b, 0);
                CryptoUtils.increment(this.a);
                this.n = 0;
            }
            int i7 = i + 1;
            byte b = bArr[i];
            byte[] bArr3 = this.b;
            int i8 = this.n;
            this.n = i8 + 1;
            bArr2[i3] = (byte) (b ^ bArr3[i8]);
            i3++;
            i2 = i4;
            i = i7;
        }
    }
}
