package iaik.security.random;

import iaik.security.md.RawHash;
import iaik.utils.CryptoUtils;

/* JADX INFO: loaded from: classes.dex */
class g extends v {
    private static final byte[] a = {1};
    private RawHash b;
    private int c;
    private byte[] d;

    protected g(RawHash rawHash) {
        this.b = rawHash;
        int blockSize = rawHash.getBlockSize();
        this.c = blockSize;
        this.d = new byte[blockSize];
    }

    byte[] a(byte[] bArr) {
        byte[] bArrCompress = this.b.compress(CryptoUtils.addModBlockSize(this.c, this.d, bArr));
        this.b.reset();
        this.d = CryptoUtils.addModBlockSize(this.c, CryptoUtils.addModBlockSize(this.c, bArrCompress, a), this.d);
        return bArrCompress;
    }

    @Override // iaik.security.random.v
    void b() {
        CryptoUtils.zeroBlock(this.d);
    }

    @Override // iaik.security.random.v
    protected void engineNextBytes(byte[] bArr) {
        int length = bArr.length;
        int i = 0;
        while (length > 0) {
            byte[] bArrA = a(null);
            int iMin = Math.min(length, bArrA.length);
            System.arraycopy(bArrA, 0, bArr, i, iMin);
            CryptoUtils.zeroBlock(bArrA);
            length -= iMin;
            i += iMin;
        }
    }

    @Override // iaik.security.random.v
    protected void engineSetSeed(byte[] bArr) {
        a(bArr);
    }
}
