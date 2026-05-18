package iaik.security.cipher;

import iaik.utils.CryptoUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: renamed from: iaik.security.cipher.k, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
abstract class AbstractC0030k extends t {
    final int[] a;
    private final int[] b;
    private final int[] c;
    private int m;
    private int n;
    private final boolean o;

    AbstractC0030k(String str, int i) {
        super(str, 8, 8);
        this.b = new int[4];
        int[] iArr = new int[4];
        this.c = iArr;
        this.a = iArr;
        this.o = i == 1;
    }

    @Override // iaik.security.cipher.t
    void a() {
        if (this.d == 2) {
            System.arraycopy(this.b, 0, this.c, 0, 2);
            int[] iArr = this.c;
            this.m = iArr[0];
            this.n = iArr[1];
        }
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (!key.getFormat().equals("RAW")) {
            throw new InvalidKeyException("Key must be RAW!");
        }
        if (this.d == 2) {
            this.e = a(i, algorithmParameterSpec, secureRandom, 8);
            if (this.o) {
                CryptoUtils.squashBytesToInts(this.e, 0, this.c, 0, 2);
            } else {
                CryptoUtils.squashBytesToIntsLE(this.e, 0, this.c, 0, 2);
            }
            System.arraycopy(this.c, 0, this.b, 0, 2);
            int[] iArr = this.c;
            this.m = iArr[0];
            this.n = iArr[1];
        } else {
            this.e = null;
        }
        byte[] encoded = key.getEncoded();
        a(i, encoded);
        CryptoUtils.zeroBlock(encoded);
    }

    abstract void a(int i, byte[] bArr) throws InvalidKeyException;

    @Override // iaik.security.cipher.t
    void a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (this.o) {
            CryptoUtils.squashBytesToInts(bArr, i, this.c, 0, 2);
        } else {
            CryptoUtils.squashBytesToIntsLE(bArr, i, this.c, 0, 2);
        }
        if (this.d == 2) {
            int[] iArr = this.c;
            iArr[2] = iArr[0];
            iArr[3] = iArr[1];
            c();
            int[] iArr2 = this.c;
            iArr2[0] = iArr2[0] ^ this.m;
            iArr2[1] = iArr2[1] ^ this.n;
            this.m = iArr2[2];
            this.n = iArr2[3];
        } else {
            c();
        }
        if (this.o) {
            CryptoUtils.spreadIntsToBytes(this.c, 0, bArr2, i3, 2);
        } else {
            CryptoUtils.spreadIntsToBytesLE(this.c, 0, bArr2, i3, 2);
        }
    }

    @Override // iaik.security.cipher.t
    boolean a(int i, int i2) {
        if (i == 1 || i == 2) {
            this.d = i;
            return true;
        }
        this.d = 0;
        return false;
    }

    abstract void b();

    @Override // iaik.security.cipher.t
    void b(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (this.o) {
            CryptoUtils.squashBytesToInts(bArr, i, this.c, 0, 2);
        } else {
            CryptoUtils.squashBytesToIntsLE(bArr, i, this.c, 0, 2);
        }
        if (this.d == 2) {
            int[] iArr = this.c;
            iArr[0] = iArr[0] ^ this.m;
            iArr[1] = iArr[1] ^ this.n;
            b();
            int[] iArr2 = this.c;
            this.m = iArr2[0];
            this.n = iArr2[1];
        } else {
            b();
        }
        if (this.o) {
            CryptoUtils.spreadIntsToBytes(this.c, 0, bArr2, i3, 2);
        } else {
            CryptoUtils.spreadIntsToBytesLE(this.c, 0, bArr2, i3, 2);
        }
    }

    abstract void c();

    public void d() {
        CryptoUtils.zeroBlock(this.c);
        CryptoUtils.zeroBlock(this.b);
        if (this.e != null) {
            CryptoUtils.zeroBlock(this.e);
        }
        this.n = 0;
        this.m = 0;
    }

    protected void finalize() {
        d();
    }
}
