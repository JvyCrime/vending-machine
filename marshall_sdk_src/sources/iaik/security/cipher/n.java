package iaik.security.cipher;

import iaik.utils.CryptoUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.IllegalBlockSizeException;

/* JADX INFO: loaded from: classes.dex */
class n extends t {
    protected final byte[] a;
    protected final byte[] b;
    protected final t c;

    n(t tVar) {
        super(tVar.i(), tVar.g(), tVar.h());
        this.c = tVar;
        this.b = new byte[this.g];
        this.a = new byte[this.h];
    }

    @Override // iaik.security.cipher.t
    int a(Key key) throws InvalidKeyException {
        return this.c.a(key);
    }

    @Override // iaik.security.cipher.t
    void a() {
        this.c.a();
        if (this.d == 2 || this.d == 9) {
            System.arraycopy(this.e, 0, this.a, 0, this.h);
        }
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.c.a(i, key, algorithmParameterSpec, secureRandom);
        if (this.d != 2 && this.d != 9) {
            this.e = null;
        } else {
            this.e = a(i, algorithmParameterSpec, secureRandom, this.h);
            System.arraycopy(this.e, 0, this.a, 0, this.h);
        }
    }

    @Override // iaik.security.cipher.t
    void a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        if (this.d != 2 && this.d != 9) {
            this.c.a(bArr, i, i2, this.b, 0);
            return;
        }
        this.c.a(bArr, i, i2, this.b, 0);
        byte[] bArr3 = this.b;
        CryptoUtils.xorBlock(bArr3, 0, this.a, 0, bArr3, 0, i2);
        System.arraycopy(bArr, i, this.a, 0, i2);
        System.arraycopy(this.b, 0, bArr2, i3, i2);
    }

    @Override // iaik.security.cipher.t
    boolean a(int i, int i2) {
        if (i != 2 && i != 9 && i != 1) {
            this.d = 0;
            return this.c.a(i, i2);
        }
        this.d = i;
        this.c.a(1, 0);
        return true;
    }

    @Override // iaik.security.cipher.t
    byte[] a_() {
        return (this.d == 2 || this.d == 9) ? this.e : this.c.a_();
    }

    @Override // iaik.security.cipher.t
    void b(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        if (this.d != 2 && this.d != 9) {
            this.c.b(this.b, 0, i2, bArr2, i3);
            return;
        }
        CryptoUtils.xorBlock(bArr, i, this.a, 0, this.b, 0, i2);
        this.c.b(this.b, 0, i2, bArr2, i3);
        System.arraycopy(bArr2, i3, this.a, 0, i2);
    }
}
