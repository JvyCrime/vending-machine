package iaik.pkcs;

import iaik.utils.Util;

/* JADX INFO: loaded from: classes.dex */
public class PKCSException extends Exception {
    private static final long serialVersionUID = 4812201100942787146L;

    static {
        Util.toString((byte[]) null, -1, 1);
    }

    public PKCSException() {
    }

    public PKCSException(String str) {
        super(str);
    }
}
