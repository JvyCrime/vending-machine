package iaik.security.random;

import java.security.SecureRandom;

/* JADX INFO: loaded from: classes.dex */
public class MetaSeedGenerator extends VarLengthSeedGenerator {
    private static SecureRandom g = null;
    private static int h = 4096;
    private static int i = 4096;
    private byte[] j;

    public MetaSeedGenerator() {
        this(256);
    }

    public MetaSeedGenerator(int i2) throws RandomException {
        super(i2);
        if (g == null) {
            throw new RandomException("Initial seed not set!");
        }
    }

    public static synchronized void setSeed(byte[] bArr) {
        SecureRandom secureRandom = SecRandom.getDefault();
        g = secureRandom;
        secureRandom.setSeed(bArr);
    }

    @Override // iaik.security.random.SeedGenerator
    public synchronized byte[] getSeed() {
        if (this.j == null || hasSeedLengthChanged()) {
            int i2 = i;
            if (i2 == 0) {
                g.setSeed(new JDKSeedGenerator(256).getSeed());
                i = h;
            } else {
                i = i2 - 1;
            }
            byte[] bArr = new byte[this.e];
            this.j = bArr;
            g.nextBytes(bArr);
        }
        return (byte[]) this.j.clone();
    }

    @Override // iaik.security.random.SeedGenerator
    public int[] getStatus() {
        return new int[]{this.d, this.d};
    }
}
