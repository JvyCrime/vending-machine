package iaik.security.random;

import java.io.InputStream;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class RandomInputStream extends InputStream {
    private Random a;

    public RandomInputStream(Random random) {
        this.a = random;
    }

    @Override // java.io.InputStream
    public int available() {
        return Integer.MAX_VALUE;
    }

    @Override // java.io.InputStream
    public int read() {
        byte[] bArr = new byte[1];
        read(bArr);
        return bArr[0] & 255;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) {
        this.a.nextBytes(bArr);
        return bArr.length;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) {
        if (i == 0 && i2 == bArr.length) {
            return read(bArr);
        }
        byte[] bArr2 = new byte[i2];
        read(bArr2);
        System.arraycopy(bArr2, 0, bArr, i, i2);
        return i2;
    }
}
