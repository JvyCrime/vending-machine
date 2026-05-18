package iaik.security.random;

/* JADX INFO: loaded from: classes.dex */
public abstract class SeedGenerator {
    static int a = 256;
    static Class b;
    static Class c;
    private static Class d;
    protected SeedGenListener seedGenListener;

    protected SeedGenerator() {
    }

    static SeedGenerator a(Class cls) {
        try {
            return (SeedGenerator) cls.newInstance();
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Internal error instantiating SeedGenerator: ");
            stringBuffer.append(e.getMessage());
            throw new RandomException(stringBuffer.toString());
        }
    }

    static Class a() {
        Class clsClass$ = b;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.security.random.MetaSeedGenerator");
            b = clsClass$;
        }
        byte[] seed = new JDKSeedGenerator(256).getSeed();
        MetaSeedGenerator.setSeed(seed);
        a = seed.length << 3;
        return clsClass$;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public static synchronized SeedGenerator getDefault() {
        if (d == null) {
            d = a();
        }
        return a(d);
    }

    public static SeedGenerator getDefaultSeedGenerator() {
        return getDefault();
    }

    public static void setDefault(Class cls) {
        if (!a(cls).seedAvailable()) {
            throw new RandomException("This seed generator cannot be used as default.");
        }
        d = cls;
        Class clsClass$ = c;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.security.random.VarLengthSeedGenerator");
            c = clsClass$;
        }
        if (clsClass$.isAssignableFrom(cls)) {
            VarLengthSeedGenerator.b(cls);
        }
    }

    public static void setDefaultSeedGenerator(SeedGenerator seedGenerator) throws RandomException {
        setDefault(seedGenerator.getClass());
    }

    protected void callSeedGenListener() {
        if (this.seedGenListener != null) {
            int[] status = getStatus();
            this.seedGenListener.bitsGenerated(status[0], status[1]);
        }
    }

    public abstract byte[] getSeed();

    public abstract int[] getStatus();

    public final boolean seedAvailable() {
        int[] status = getStatus();
        return status[0] == status[1];
    }

    public void setSeedGenListener(SeedGenListener seedGenListener) {
        this.seedGenListener = seedGenListener;
        callSeedGenListener();
    }
}
