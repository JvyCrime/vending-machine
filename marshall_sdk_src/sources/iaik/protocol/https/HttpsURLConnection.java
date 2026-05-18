package iaik.protocol.https;

import iaik.security.ssl.SSLClientContext;
import iaik.security.ssl.SSLContext;
import iaik.security.ssl.SSLSocket;
import java.io.IOException;
import java.net.URL;
import org.w3c.www.protocol.http.HttpURLConnection;

/* JADX INFO: loaded from: classes.dex */
public class HttpsURLConnection extends HttpURLConnection {
    private static SSLContext a = new SSLClientContext();

    public HttpsURLConnection(URL url) throws IOException {
        super(url);
        ((HttpURLConnection) this).sslContext = (SSLContext) a.clone();
    }

    public static void setDefaultSSLContext(SSLContext sSLContext) {
        if (sSLContext == null) {
            throw new IllegalArgumentException("Context may not be null!");
        }
        a = sSLContext;
    }

    public static SSLContext getDefaultSSLContext() {
        return a;
    }

    public void setSSLContext(SSLContext sSLContext) {
        if (sSLContext == null) {
            throw new IllegalArgumentException("Context may not be null!");
        }
        ((HttpURLConnection) this).sslContext = sSLContext;
    }

    public SSLContext getSSLContext() {
        return ((HttpURLConnection) this).sslContext;
    }

    public SSLSocket getSSLSocket() throws IOException {
        if (((HttpURLConnection) this).reply == null) {
            throw new IOException("Not yet connected or connection failed!");
        }
        return (SSLSocket) ((HttpURLConnection) this).reply.getSocket();
    }
}
