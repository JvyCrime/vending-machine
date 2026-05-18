package iaik.security.ssl;

import java.io.IOException;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
class am {
    private byte[] a;
    private byte[] b;

    am(Random random, int i) {
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        int i2 = jCurrentTimeMillis < 2147483647L ? (int) jCurrentTimeMillis : Integer.MAX_VALUE;
        byte[] bArr = new byte[i < 768 ? 16 : 32];
        this.a = bArr;
        random.nextBytes(bArr);
        byte[] bArr2 = this.a;
        bArr2[0] = (byte) (i2 >> 24);
        bArr2[1] = (byte) (i2 >> 16);
        bArr2[2] = (byte) (i2 >> 8);
        bArr2[3] = (byte) i2;
    }

    am(ab abVar) throws IOException {
        this(abVar, 32);
    }

    am(ab abVar, int i) throws IOException {
        byte[] bArr = new byte[i];
        this.a = bArr;
        abVar.a(bArr);
    }

    int c() {
        return this.a.length;
    }

    byte[] a() {
        byte[] bArr = this.b;
        if (bArr != null) {
            return bArr;
        }
        byte[] bArr2 = this.a;
        int length = bArr2.length;
        if (length == 32) {
            return bArr2;
        }
        int i = length > 32 ? 32 : length;
        byte[] bArr3 = new byte[32];
        System.arraycopy(bArr2, length - i, bArr3, 32 - i, i);
        return bArr3;
    }

    byte[] b() {
        return this.a;
    }

    void a(ag agVar) throws IOException {
        agVar.write(this.a);
    }
}
