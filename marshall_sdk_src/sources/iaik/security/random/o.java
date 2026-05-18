package iaik.security.random;

import iaik.utils.CryptoUtils;
import java.security.MessageDigest;

/* JADX INFO: loaded from: classes.dex */
class o extends v {
    private MessageDigest a;
    private byte[] b = new byte[0];
    private int c = 0;
    private byte[] d;

    protected o(MessageDigest messageDigest) {
        this.a = messageDigest;
        this.d = messageDigest.digest();
    }

    private void a() {
        this.a.update(this.b);
        this.b = CryptoUtils.incrementExtended(this.b);
        byte[] bArrDigest = this.a.digest();
        this.d = bArrDigest;
        this.c = bArrDigest.length;
    }

    @Override // iaik.security.random.v
    void b() {
        CryptoUtils.zeroBlock(this.b);
        CryptoUtils.zeroBlock(this.d);
    }

    @Override // iaik.security.random.v
    protected void engineNextBytes(byte[] bArr) {
        int length = bArr.length;
        int i = 0;
        while (length > 0) {
            if (this.c == 0) {
                a();
            }
            int iMin = Math.min(length, this.c);
            byte[] bArr2 = this.d;
            System.arraycopy(bArr2, bArr2.length - this.c, bArr, i, iMin);
            this.c -= iMin;
            length -= iMin;
            i += iMin;
        }
    }

    @Override // iaik.security.random.v
    protected void engineSetSeed(byte[] bArr) {
        if (bArr != null) {
            byte[] bArr2 = this.b;
            if (bArr2 != null) {
                this.a.update(bArr2);
            }
            this.b = this.a.digest(bArr);
        }
    }
}
