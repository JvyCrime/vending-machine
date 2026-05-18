package iaik.security.cipher;

import iaik.utils.CryptoUtils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: renamed from: iaik.security.cipher.j, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
abstract class AbstractC0029j extends t {
    protected final int[] a;
    private final int[] b;
    private final int[] c;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private final boolean u;

    protected AbstractC0029j(String str, int i) {
        super(str, 32, 32);
        this.b = new int[16];
        int[] iArr = new int[16];
        this.c = iArr;
        this.a = iArr;
        this.u = i == 1;
    }

    @Override // iaik.security.cipher.t
    void a() {
        if (this.d == 2) {
            System.arraycopy(this.b, 0, this.c, 0, 16);
            int[] iArr = this.c;
            this.m = iArr[0];
            this.n = iArr[1];
            this.o = iArr[2];
            this.p = iArr[3];
            this.q = iArr[4];
            this.r = iArr[5];
            this.s = iArr[6];
            this.t = iArr[7];
        }
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (!key.getFormat().equals("RAW")) {
            throw new InvalidKeyException("Key must be RAW!");
        }
        if (this.d == 2) {
            this.e = a(i, algorithmParameterSpec, secureRandom, 32);
            if (this.u) {
                CryptoUtils.squashBytesToInts(this.e, 0, this.c, 0, 8);
            } else {
                CryptoUtils.squashBytesToIntsLE(this.e, 0, this.c, 0, 8);
            }
            System.arraycopy(this.c, 0, this.b, 0, 8);
            int[] iArr = this.c;
            this.m = iArr[0];
            this.n = iArr[1];
            this.o = iArr[2];
            this.p = iArr[3];
            this.q = iArr[4];
            this.r = iArr[5];
            this.s = iArr[6];
            this.t = iArr[7];
        } else {
            this.e = null;
        }
        byte[] encoded = key.getEncoded();
        a(i, encoded);
        CryptoUtils.zeroBlock(encoded);
    }

    protected abstract void a(int i, byte[] bArr) throws InvalidKeyException;

    @Override // iaik.security.cipher.t
    void a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (this.u) {
            CryptoUtils.squashBytesToInts(bArr, i, this.c, 0, 8);
        } else {
            CryptoUtils.squashBytesToIntsLE(bArr, i, this.c, 0, 8);
        }
        if (this.d == 2) {
            int[] iArr = this.c;
            iArr[8] = iArr[0];
            iArr[9] = iArr[1];
            iArr[10] = iArr[2];
            iArr[11] = iArr[3];
            iArr[12] = iArr[4];
            iArr[13] = iArr[5];
            iArr[14] = iArr[6];
            iArr[15] = iArr[7];
            c();
            int[] iArr2 = this.c;
            iArr2[0] = iArr2[0] ^ this.m;
            iArr2[1] = this.n ^ iArr2[1];
            iArr2[2] = iArr2[2] ^ this.o;
            iArr2[3] = iArr2[3] ^ this.p;
            iArr2[4] = iArr2[4] ^ this.q;
            iArr2[5] = iArr2[5] ^ this.r;
            iArr2[6] = iArr2[6] ^ this.s;
            iArr2[7] = iArr2[7] ^ this.t;
            this.m = iArr2[8];
            this.n = iArr2[9];
            this.o = iArr2[10];
            this.p = iArr2[11];
            this.q = iArr2[12];
            this.r = iArr2[13];
            this.s = iArr2[14];
            this.t = iArr2[15];
        } else {
            c();
        }
        if (this.u) {
            CryptoUtils.spreadIntsToBytes(this.c, 0, bArr2, i3, 8);
        } else {
            CryptoUtils.spreadIntsToBytesLE(this.c, 0, bArr2, i3, 8);
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

    protected abstract void b();

    @Override // iaik.security.cipher.t
    void b(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        if (this.u) {
            CryptoUtils.squashBytesToInts(bArr, i, this.c, 0, 8);
        } else {
            CryptoUtils.squashBytesToIntsLE(bArr, i, this.c, 0, 8);
        }
        if (this.d == 2) {
            int[] iArr = this.c;
            iArr[0] = iArr[0] ^ this.m;
            iArr[1] = iArr[1] ^ this.n;
            iArr[2] = iArr[2] ^ this.o;
            iArr[3] = iArr[3] ^ this.p;
            iArr[4] = iArr[4] ^ this.q;
            iArr[5] = iArr[5] ^ this.r;
            iArr[6] = iArr[6] ^ this.s;
            iArr[7] = iArr[7] ^ this.t;
            b();
            int[] iArr2 = this.c;
            this.m = iArr2[0];
            this.n = iArr2[1];
            this.o = iArr2[2];
            this.p = iArr2[3];
            this.q = iArr2[4];
            this.r = iArr2[5];
            this.s = iArr2[6];
            this.t = iArr2[7];
        } else {
            b();
        }
        if (this.u) {
            CryptoUtils.spreadIntsToBytes(this.c, 0, bArr2, i3, 8);
        } else {
            CryptoUtils.spreadIntsToBytesLE(this.c, 0, bArr2, i3, 8);
        }
    }

    protected abstract void c();

    public void d() {
        CryptoUtils.zeroBlock(this.c);
        CryptoUtils.zeroBlock(this.b);
        if (this.e != null) {
            CryptoUtils.zeroBlock(this.e);
        }
        this.p = 0;
        this.o = 0;
        this.n = 0;
        this.m = 0;
        this.t = 0;
        this.s = 0;
        this.r = 0;
        this.q = 0;
    }

    protected void finalize() {
        d();
    }
}
