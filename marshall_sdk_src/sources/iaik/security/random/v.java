package iaik.security.random;

/* JADX INFO: loaded from: classes.dex */
abstract class v {
    private boolean a;
    private boolean b;

    protected v() {
        this.a = false;
        this.b = false;
        this.a = true;
    }

    v(boolean z) {
        this();
        this.b = z;
    }

    abstract void b();

    protected abstract void engineNextBytes(byte[] bArr);

    protected abstract void engineSetSeed(byte[] bArr);

    protected void finalize() throws Throwable {
        b();
        super.finalize();
    }

    public final synchronized void nextBytes(byte[] bArr) {
        if (!this.b) {
            setSeed();
        }
        engineNextBytes(bArr);
    }

    public final void setSeed() {
        setSeed(SeedGenerator.getDefault().getSeed());
    }

    public final synchronized void setSeed(byte[] bArr) {
        if (this.a) {
            engineSetSeed(bArr);
            this.b = true;
        }
    }
}
