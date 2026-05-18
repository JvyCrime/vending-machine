package iaik.security.cipher;

import iaik.utils.CryptoUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.IllegalBlockSizeException;

/* JADX INFO: loaded from: classes.dex */
final class C extends t {
    private final byte[] a;
    private final byte[] b;
    private final t c;

    C(t tVar) {
        super(tVar.i(), tVar.g(), tVar.h());
        this.c = tVar;
        this.b = new byte[this.g];
        this.a = new byte[this.g];
    }

    @Override // iaik.security.cipher.t
    int a(Key key) throws InvalidKeyException {
        return this.c.a(key);
    }

    @Override // iaik.security.cipher.t
    void a() {
        this.c.a();
        if (this.d == 5) {
            System.arraycopy(this.e, 0, this.a, 0, this.g);
        }
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.c.a(i, key, algorithmParameterSpec, secureRandom);
        if (this.d != 5) {
            this.e = null;
        } else {
            this.e = a(i, algorithmParameterSpec, secureRandom, this.g);
            System.arraycopy(this.e, 0, this.a, 0, this.g);
        }
    }

    @Override // iaik.security.cipher.t
    void a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        if (this.d != 5) {
            this.c.a(bArr, i, i2, this.b, 0);
            return;
        }
        this.c.a(bArr, i, i2, this.b, 0);
        CryptoUtils.xorBlock(this.b, 0, this.a, 0, bArr2, i3, i2);
        System.arraycopy(bArr, i, this.a, 0, i2);
        byte[] bArr3 = this.a;
        CryptoUtils.xorBlock(bArr3, 0, bArr2, i3, bArr3, 0, i2);
    }

    @Override // iaik.security.cipher.t
    boolean a(int i, int i2) {
        if (i != 5 && i != 1) {
            this.c.a(false);
            this.d = 0;
            return this.c.a(i, i2);
        }
        this.d = i;
        this.c.a(1, 0);
        this.c.a(false);
        return true;
    }

    @Override // iaik.security.cipher.t
    byte[] a_() {
        return this.d == 5 ? this.e : this.c.a_();
    }

    @Override // iaik.security.cipher.t
    void b(byte[] bArr, int i, int i2, byte[] bArr2, int i3) throws IllegalBlockSizeException {
        if (this.d != 5) {
            this.c.b(this.b, 0, i2, bArr2, i3);
            return;
        }
        CryptoUtils.xorBlock(bArr, i, this.a, 0, this.b, 0, i2);
        this.c.b(this.b, 0, i2, bArr2, i3);
        System.arraycopy(bArr2, i3, this.a, 0, i2);
        byte[] bArr3 = this.a;
        CryptoUtils.xorBlock(bArr3, 0, bArr, i, bArr3, 0, i2);
    }
}
