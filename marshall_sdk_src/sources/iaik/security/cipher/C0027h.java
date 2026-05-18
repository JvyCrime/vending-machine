package iaik.security.cipher;

import iaik.utils.CryptoUtils;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

/* JADX INFO: renamed from: iaik.security.cipher.h, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
final class C0027h extends t {
    private final int[] a;
    private final int[] b;
    private int c;
    private int m;

    C0027h() {
        super("ARCFOUR", 1, 0);
        this.a = new int[256];
        this.b = new int[256];
    }

    private void a(byte[] bArr) {
        int i = 0;
        while (i < 256) {
            int[] iArr = this.b;
            int i2 = i + 1;
            iArr[i] = i;
            int i3 = i2 + 1;
            iArr[i2] = i2;
            int i4 = i3 + 1;
            iArr[i3] = i3;
            i = i4 + 1;
            iArr[i4] = i4;
        }
        this.c = 0;
        this.m = 0;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < 256; i7++) {
            byte b = bArr[i5];
            int[] iArr2 = this.b;
            i6 = (b + iArr2[i7] + i6) & 255;
            int i8 = iArr2[i7];
            iArr2[i7] = iArr2[i6];
            iArr2[i6] = i8;
            i5++;
            if (i5 == bArr.length) {
                i5 = 0;
            }
        }
        System.arraycopy(this.b, 0, this.a, 0, 256);
    }

    private void e(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        while (true) {
            int i4 = i2 - 1;
            if (i2 <= 0) {
                return;
            }
            int i5 = (this.c + 1) & 255;
            this.c = i5;
            int[] iArr = this.b;
            int i6 = iArr[i5];
            int i7 = (this.m + i6) & 255;
            this.m = i7;
            int i8 = iArr[i7];
            iArr[i5] = i8;
            iArr[i7] = i6;
            bArr2[i3] = (byte) (bArr[i] ^ iArr[(i6 + i8) & 255]);
            i2 = i4;
            i++;
            i3++;
        }
    }

    @Override // iaik.security.cipher.t
    void a() {
        System.arraycopy(this.a, 0, this.b, 0, 256);
        this.c = 0;
        this.m = 0;
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException {
        if (!key.getFormat().equals("RAW")) {
            throw new InvalidKeyException("Key must be RAW!");
        }
        byte[] encoded = key.getEncoded();
        if (encoded == null || encoded.length < 1) {
            throw new InvalidKeyException("Key must have a length of at least one byte!");
        }
        a(encoded);
    }

    @Override // iaik.security.cipher.t
    void a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        e(bArr, i, i2, bArr2, i3);
    }

    @Override // iaik.security.cipher.t
    boolean a(int i, int i2) {
        return true;
    }

    public void b() {
        CryptoUtils.zeroBlock(this.b);
        CryptoUtils.zeroBlock(this.a);
        this.c = 0;
        this.m = 0;
    }

    @Override // iaik.security.cipher.t
    void b(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        e(bArr, i, i2, bArr2, i3);
    }

    protected void finalize() {
        b();
    }
}
