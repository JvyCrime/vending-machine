package iaik.security.random;

import java.security.SecureRandom;

/* JADX INFO: loaded from: classes.dex */
public class JDKSeedGenerator extends VarLengthSeedGenerator {
    private static SecureRandom h;
    private byte[] g;

    public JDKSeedGenerator() {
        this(256);
    }

    public JDKSeedGenerator(int i) {
        super(i);
    }

    public static synchronized SecureRandom getSecureRandom() {
        return h;
    }

    public static synchronized void setSecureRandom(SecureRandom secureRandom) {
        h = secureRandom;
    }

    @Override // iaik.security.random.SeedGenerator
    public synchronized byte[] getSeed() {
        if (this.g == null || hasSeedLengthChanged()) {
            SecureRandom secureRandom = h;
            this.g = secureRandom == null ? SecureRandom.getSeed(this.e) : secureRandom.generateSeed(this.e);
        }
        return (byte[]) this.g.clone();
    }

    @Override // iaik.security.random.SeedGenerator
    public int[] getStatus() {
        return new int[]{this.d, this.d};
    }
}
