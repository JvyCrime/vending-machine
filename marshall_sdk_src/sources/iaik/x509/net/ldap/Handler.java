package iaik.x509.net.ldap;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/* JADX INFO: loaded from: classes2.dex */
public class Handler extends URLStreamHandler {
    @Override // java.net.URLStreamHandler
    protected int getDefaultPort() {
        return 389;
    }

    @Override // java.net.URLStreamHandler
    protected URLConnection openConnection(URL url) throws IOException {
        return new LdapURLConnection(url);
    }
}
