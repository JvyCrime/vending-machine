package iaik.utils;

import java.security.Provider;

/* JADX INFO: loaded from: classes2.dex */
public abstract class ExtendedProvider extends Provider {
    private static final long serialVersionUID = -6000466464088493215L;

    static {
        Util.toString((byte[]) null, -1, 1);
    }

    protected ExtendedProvider(String str, double d, String str2) {
        super(str, d, str2);
    }

    protected static boolean isAvailable(String str) {
        try {
            return Class.forName(str) != null;
        } catch (Throwable unused) {
            return false;
        }
    }

    protected void puta(String str, String str2) {
        put(str, str2);
    }

    protected void putc(String str, String str2) {
        putc(str, str2, isAvailable(str2));
    }

    protected void putc(String str, String str2, boolean z) {
        if (z) {
            puta(str, str2);
        }
    }
}
