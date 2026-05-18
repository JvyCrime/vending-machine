package iaik.security.md;

/* JADX INFO: loaded from: classes.dex */
public class RawHash implements Cloneable {
    AbstractMessageDigest a;

    public RawHash(AbstractMessageDigest abstractMessageDigest) {
        this.a = abstractMessageDigest;
    }

    public Object clone() {
        try {
            RawHash rawHash = (RawHash) super.clone();
            rawHash.a = (AbstractMessageDigest) this.a.clone();
            return rawHash;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    public byte[] compress(byte[] bArr) {
        if (bArr.length != this.a.d) {
            throw new IllegalArgumentException("The input array must have block size.");
        }
        byte[] bArr2 = new byte[getDigetLength()];
        this.a.a(bArr, 0);
        this.a.b(bArr2, 0);
        return bArr2;
    }

    public int getBlockSize() {
        return this.a.d;
    }

    public int getDigetLength() {
        return this.a.engineGetDigestLength();
    }

    public void reset() {
        this.a.engineReset();
    }
}
