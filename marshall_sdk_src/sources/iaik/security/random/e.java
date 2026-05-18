package iaik.security.random;

import iaik.security.cipher.SecretKey;
import iaik.security.cipher.TripleDES;
import iaik.utils.CryptoUtils;
import iaik.utils.Util;
import java.security.InvalidKeyException;

/* JADX INFO: loaded from: classes.dex */
final class e extends v {
    private static final byte[] b = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private TripleDES a;
    private byte[] c = new byte[8];
    private byte[] d = new byte[8];
    private int e = 0;
    private byte[] f = new byte[8];

    public e() throws RandomException {
        TripleDES tripleDES = new TripleDES();
        this.a = tripleDES;
        try {
            tripleDES.engineInit(1, new SecretKey(b, "DESede"), null);
            a();
        } catch (InvalidKeyException unused) {
            throw new RandomException("Could not get TripleDES, invalid key");
        }
    }

    static byte[] a(byte[] bArr, byte[] bArr2) {
        int length = bArr.length;
        byte[] bArr3 = new byte[length];
        for (int i = 0; i < length; i++) {
            bArr3[i] = (byte) (bArr[i] ^ bArr2[i]);
        }
        return bArr3;
    }

    private byte c() {
        if (this.e == 0) {
            byte[] bArrA = a(this.c, this.d);
            byte[] bArrEngineUpdate = this.a.engineUpdate(bArrA, 0, bArrA.length);
            this.f = bArrEngineUpdate;
            byte[] bArrA2 = a(bArrEngineUpdate, this.c);
            this.d = this.a.engineUpdate(bArrA2, 0, bArrA2.length);
            this.e = this.f.length;
        }
        byte[] bArr = this.f;
        int length = bArr.length;
        int i = this.e;
        this.e = i - 1;
        return bArr[length - i];
    }

    public void a() {
        a(System.currentTimeMillis());
    }

    public void a(long j) {
        this.c = Util.toByteArray(j);
    }

    @Override // iaik.security.random.v
    void b() {
        CryptoUtils.zeroBlock(b);
        CryptoUtils.zeroBlock(this.c);
        CryptoUtils.zeroBlock(this.d);
        CryptoUtils.zeroBlock(this.f);
    }

    @Override // iaik.security.random.v
    protected void engineNextBytes(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = c();
        }
    }

    @Override // iaik.security.random.v
    protected void engineSetSeed(byte[] bArr) {
        if (bArr == null || bArr.length < 8) {
            return;
        }
        System.arraycopy(bArr, 0, this.d, 0, 8);
    }
}
