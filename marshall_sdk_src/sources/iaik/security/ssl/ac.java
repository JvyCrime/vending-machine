package iaik.security.ssl;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/* JADX INFO: loaded from: classes.dex */
class ac {
    static Class a;
    static Class b;
    private static Class c;

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    static {
        Class clsClass$ = a;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.security.ssl.ac");
            a = clsClass$;
        }
        c = clsClass$;
        try {
            String str = (String) System.getProperties().get("java.version");
            Provider provider = Security.getProvider("IAIK");
            if (provider != null) {
                if (str.compareTo("1.2") <= 0 || provider.getVersion() < 3.17d) {
                    return;
                }
                c = Class.forName("iaik.security.ssl.IaikJCECipher");
                return;
            }
            Class<?> cls = Class.forName("iaik.utils.IaikSecurity");
            Class<?>[] clsArr = new Class[1];
            Class<?> clsClass$2 = b;
            if (clsClass$2 == null) {
                clsClass$2 = class$("java.lang.String");
                b = clsClass$2;
            }
            clsArr[0] = clsClass$2;
            cls.getDeclaredMethod("getCipher", clsArr);
            c = Class.forName("iaik.security.ssl.IaikJCECipher");
        } catch (Throwable unused) {
            Class clsClass$3 = a;
            if (clsClass$3 == null) {
                clsClass$3 = class$("iaik.security.ssl.ac");
                a = clsClass$3;
            }
            c = clsClass$3;
        }
    }

    static ac a() {
        try {
            return (ac) c.newInstance();
        } catch (Throwable unused) {
            return new ac();
        }
    }

    public Cipher getCipher(String str, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        if (str2 == null) {
            return Cipher.getInstance(str);
        }
        return Cipher.getInstance(str, str2);
    }
}
