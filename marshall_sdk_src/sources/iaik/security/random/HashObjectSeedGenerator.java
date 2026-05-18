package iaik.security.random;

import iaik.security.md.SHA;
import iaik.utils.Util;
import java.security.MessageDigest;

/* JADX INFO: loaded from: classes.dex */
public abstract class HashObjectSeedGenerator extends VarLengthSeedGenerator {
    private int g;
    private int h;
    private byte[] i;
    private MessageDigest j;

    protected HashObjectSeedGenerator(int i) {
        super(i);
        reinit(i);
        MessageDigest messageDigest = getMessageDigest();
        this.j = messageDigest;
        messageDigest.reset();
    }

    public final boolean addSeedObject(Object obj) throws RandomException {
        int i = this.h;
        if (i <= 0) {
            return false;
        }
        int iExtractSeedData = i - extractSeedData(obj);
        this.h = iExtractSeedData;
        if (iExtractSeedData < 0) {
            this.h = 0;
        }
        callSeedGenListener();
        return this.h <= 0;
    }

    protected abstract int extractSeedData(Object obj) throws RandomException;

    protected MessageDigest getMessageDigest() {
        return new SHA();
    }

    @Override // iaik.security.random.SeedGenerator
    public final byte[] getSeed() {
        if (this.i == null || hasSeedLengthChanged()) {
            if (this.h > 0) {
                return null;
            }
            this.i = this.j.digest();
        }
        return (byte[]) this.i.clone();
    }

    @Override // iaik.security.random.SeedGenerator
    public final int[] getStatus() {
        int i = this.g;
        return new int[]{i - this.h, i};
    }

    @Override // iaik.security.random.VarLengthSeedGenerator
    public void reinit(int i) {
        super.reinit(i);
        this.g = i;
        this.h = i;
    }

    protected final void updateHash(long j) {
        this.j.update(Util.toByteArray(j));
    }

    protected final void updateHash(String str) {
        this.j.update(str.getBytes());
    }

    protected final void updateHash(byte[] bArr) {
        this.j.update(bArr);
    }
}
