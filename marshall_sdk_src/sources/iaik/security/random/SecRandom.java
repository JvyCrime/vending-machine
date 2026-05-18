package iaik.security.random;

import iaik.security.provider.IAIK;
import iaik.utils.InternalErrorException;
import iaik.utils.Util;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/* JADX INFO: loaded from: classes.dex */
public abstract class SecRandom extends SecureRandom {
    static Class b = null;
    private static Class c = null;
    private static String d = null;
    private static final long serialVersionUID = -8508692256943794879L;
    final SecRandomSpi a;

    static {
        Class clsClass$ = b;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.security.random.SHA256FIPS186Random");
            b = clsClass$;
        }
        c = clsClass$;
        d = null;
        Util.toString((byte[]) null, -1, 1);
    }

    protected SecRandom() {
        this.a = null;
    }

    SecRandom(SecRandomSpi secRandomSpi) {
        super(secRandomSpi, IAIK.getInstance());
        this.a = secRandomSpi;
    }

    private static SecureRandom a(Class cls) throws RandomException {
        try {
            return (SecureRandom) cls.newInstance();
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Error instantiating SecureRandom: ");
            stringBuffer.append(e.toString());
            throw new RandomException(stringBuffer.toString());
        }
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public static final SecureRandom getDefault() {
        Class cls = c;
        if (cls != null) {
            return a(cls);
        }
        try {
            return SecureRandom.getInstance(d);
        } catch (NoSuchAlgorithmException e) {
            throw new InternalErrorException("no such algorithm!", e);
        }
    }

    public static final void setDefault(Class cls) throws RandomException {
        a(cls);
        c = cls;
        d = null;
    }

    public static final void setDefault(String str) throws NoSuchAlgorithmException {
        SecureRandom.getInstance(str);
        d = str;
        c = null;
    }

    public final synchronized void setSeed() {
        setSeed(SeedGenerator.getDefault().getSeed());
    }
}
