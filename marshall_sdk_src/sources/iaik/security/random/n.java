package iaik.security.random;

import iaik.security.md.SHA;
import iaik.security.md.SHA32bit;
import iaik.security.md.SHA64bit;
import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import java.security.MessageDigest;

/* JADX INFO: loaded from: classes.dex */
class n extends u {
    public static final int MAX_ADDITIONAL_INPUT_LENGTH = 65536;
    public static final int MAX_NUMBER_OF_BYTES_PER_REQUEST = 65536;
    private static final byte[] a = {1};
    private byte[] b;
    private byte[] c;
    private final MessageDigest d;

    n(SHA32bit sHA32bit) {
        this(sHA32bit, 55);
    }

    n(SHA64bit sHA64bit) {
        this(sHA64bit, 111);
    }

    n(SHA sha) {
        this(sha, 55);
    }

    private n(MessageDigest messageDigest, int i) {
        super(a(messageDigest), i, Util.getDigestLength(messageDigest.getAlgorithm()), a(messageDigest), 64, 65536, 65536, 65536, 16777216L, true);
        this.d = messageDigest;
    }

    private static int a(MessageDigest messageDigest) {
        int digestLength = Util.getDigestLength(messageDigest.getAlgorithm()) >> 1;
        if (digestLength < 14) {
            return 14;
        }
        return digestLength;
    }

    private byte[] a(byte[] bArr, int i) {
        int iCeil = (int) Math.ceil(((double) i) / ((double) this.o));
        byte[] bArr2 = new byte[this.o * iCeil];
        byte[] bArr3 = {1};
        byte[] byteArray = Util.toByteArray(i << 3);
        for (int i2 = 0; i2 < iCeil; i2++) {
            this.d.update(bArr3[0]);
            this.d.update(byteArray);
            System.arraycopy(this.d.digest(bArr), 0, bArr2, this.o * i2, this.o);
            bArr3[0] = (byte) (bArr3[0] + 1);
        }
        byte[] bArr4 = new byte[i];
        System.arraycopy(bArr2, 0, bArr4, 0, i);
        return bArr4;
    }

    private void b(byte[] bArr) {
        this.b = a(bArr, this.g);
        byte[] bArr2 = new byte[this.g + 1];
        System.arraycopy(this.b, 0, bArr2, 1, this.g);
        this.c = a(bArr2, this.g);
        this.f = 1L;
    }

    private byte[] b(int i) {
        int iCeil = (int) Math.ceil(((double) i) / ((double) this.o));
        byte[] bArrAddModBlockSize = this.b;
        byte[] bArr = new byte[this.o * iCeil];
        for (int i2 = 0; i2 < iCeil; i2++) {
            System.arraycopy(this.d.digest(bArrAddModBlockSize), 0, bArr, this.o * i2, this.o);
            bArrAddModBlockSize = CryptoUtils.addModBlockSize(this.g, bArrAddModBlockSize, a);
        }
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        return bArr2;
    }

    @Override // iaik.security.random.u
    void a() {
        this.f = 0L;
        byte[] bArr = this.b;
        if (bArr != null) {
            CryptoUtils.zeroBlock(bArr);
        }
        byte[] bArr2 = this.c;
        if (bArr2 != null) {
            CryptoUtils.zeroBlock(bArr2);
        }
    }

    @Override // iaik.security.random.u
    void a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = this.b;
        int length = bArr3.length + 1 + bArr.length;
        if (bArr2 != null) {
            length += bArr2.length;
        }
        byte[] bArr4 = new byte[length];
        bArr4[0] = 1;
        System.arraycopy(bArr3, 0, bArr4, 1, bArr3.length);
        int length2 = this.b.length + 1;
        System.arraycopy(bArr, 0, bArr4, length2, bArr.length);
        if (bArr2 != null) {
            System.arraycopy(bArr2, 0, bArr4, length2 + bArr.length, bArr2.length);
        }
        b(bArr4);
    }

    @Override // iaik.security.random.u
    void a(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        byte[] bArr4 = new byte[bArr.length + bArr2.length + bArr3.length];
        System.arraycopy(bArr, 0, bArr4, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr4, bArr.length, bArr2.length);
        System.arraycopy(bArr3, 0, bArr4, bArr.length + bArr2.length, bArr3.length);
        b(bArr4);
    }

    @Override // iaik.security.random.u
    byte[] a(int i) {
        if (this.f > this.l) {
            this.p = true;
        }
        byte[] bArrB = b(i);
        this.d.update((byte) 3);
        this.b = CryptoUtils.addModBlockSize(this.g, this.b, CryptoUtils.addModBlockSize(this.g, this.d.digest(this.b), CryptoUtils.addModBlockSize(this.g, this.c, Util.toByteArray(this.f))));
        this.f++;
        return bArrB;
    }
}
