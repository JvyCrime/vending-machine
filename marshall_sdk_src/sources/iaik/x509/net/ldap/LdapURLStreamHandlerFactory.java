package iaik.x509.net.ldap;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.HashMap;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class LdapURLStreamHandlerFactory implements URLStreamHandlerFactory {
    private HashMap a;

    public LdapURLStreamHandlerFactory() {
        HashMap map = new HashMap(5);
        this.a = map;
        map.put("ldap", new Handler());
    }

    public void addHandler(String str, URLStreamHandler uRLStreamHandler) {
        Objects.requireNonNull(str, "protocol must not be null!");
        Objects.requireNonNull(uRLStreamHandler, "handler must not be null!");
        this.a.put(str.toLowerCase(), uRLStreamHandler);
    }

    @Override // java.net.URLStreamHandlerFactory
    public URLStreamHandler createURLStreamHandler(String str) {
        Objects.requireNonNull(str, "protocol must not be null!");
        return (URLStreamHandler) this.a.get(str.toLowerCase());
    }
}
