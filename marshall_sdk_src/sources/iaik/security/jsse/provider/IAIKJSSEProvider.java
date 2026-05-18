package iaik.security.jsse.provider;

import iaik.security.jsse.utils.Debug;
import java.security.Provider;
import java.security.Security;

/* JADX INFO: loaded from: classes.dex */
public final class IAIKJSSEProvider extends Provider {
    private static final String[] a = {"SSLContext.SSL", "SSLContext.SSLv2", "SSLContext.SSLv3", "SSLContext.TLS", "SSLContext.TLSv1", "SSLContext.TLSv1.1", "SSLContext.TLSv1.2"};
    private static final String[] b = {"iaik.security.jsse.IAIKSSLContextSSL", "iaik.security.jsse.IAIKSSLContextSSLv2", "iaik.security.jsse.IAIKSSLContextSSLv3", "iaik.security.jsse.IAIKSSLContextTLS", "iaik.security.jsse.IAIKSSLContextTLSv1", "iaik.security.jsse.IAIKSSLContextTLSv11", "iaik.security.jsse.IAIKSSLContextTLSv12"};

    public IAIKJSSEProvider() {
        super("IAIK_JSSE", 5.1015d, "IAIK_JSSE 5.1015 - Support for SSLv2, SSLv3, TLSv1, TLSv1.1 and TLSv1.2");
        Debug debug = Debug.getInstance();
        debug.println("_____________________________________________");
        debug.println("");
        debug.println("installing IAIK JSSE provider 5.1015");
        debug.println("Make sure that:");
        debug.println("* you have already installed sun's JSSE provider");
        debug.println("* you have already installed IAIK security Provider");
        debug.println("_____________________________________________");
        debug.println("");
        a();
    }

    private void a() {
        int i = 0;
        while (true) {
            String[] strArr = a;
            if (i >= strArr.length) {
                return;
            }
            put(strArr[i], b[i]);
            i++;
        }
    }

    public static void addAsProvider() {
        Security.insertProviderAt(new IAIKJSSEProvider(), 1);
    }
}
