package iaik.security.random;

/* JADX INFO: loaded from: classes.dex */
public abstract class VarLengthSeedGenerator extends SeedGenerator {
    static Class f;
    private static Class g;
    int d = 0;
    int e = 0;
    private boolean h = false;

    VarLengthSeedGenerator() {
    }

    protected VarLengthSeedGenerator(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("numBits must be greater than zero!");
        }
        reinit(i);
    }

    static void b(Class cls) {
        g = cls;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public static synchronized VarLengthSeedGenerator getDefault(int i) {
        VarLengthSeedGenerator varLengthSeedGenerator;
        if (i <= 0) {
            throw new IllegalArgumentException("numBits must be greater than zero!");
        }
        if (g == null) {
            g = a();
        }
        a = i;
        varLengthSeedGenerator = (VarLengthSeedGenerator) a(g);
        varLengthSeedGenerator.reinit(i);
        return varLengthSeedGenerator;
    }

    public static void setDefault(Class cls) {
        if (!a(cls).seedAvailable()) {
            throw new RandomException("This seed generator cannot be used as default.");
        }
        Class clsClass$ = f;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.security.random.VarLengthSeedGenerator");
            f = clsClass$;
        }
        if (!clsClass$.isAssignableFrom(cls)) {
            throw new RandomException("This seed generator does not support seeds of variable length.");
        }
        g = cls;
    }

    public int getSeedLength() {
        return this.d;
    }

    protected boolean hasSeedLengthChanged() {
        if (!this.h) {
            return false;
        }
        this.h = false;
        return true;
    }

    public void reinit(int i) {
        boolean z = this.d != i;
        this.h = z;
        if (z) {
            setSeedLength(i);
        }
    }

    protected void setSeedLength(int i) {
        this.d = i;
        this.e = i >> 3;
    }
}
