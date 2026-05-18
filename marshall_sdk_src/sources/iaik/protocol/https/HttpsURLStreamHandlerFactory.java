package iaik.protocol.https;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class HttpsURLStreamHandlerFactory implements URLStreamHandlerFactory {
    private HashMap a;

    public HttpsURLStreamHandlerFactory() {
        HashMap map = new HashMap();
        this.a = map;
        map.put("https", new Handler());
    }

    @Override // java.net.URLStreamHandlerFactory
    public URLStreamHandler createURLStreamHandler(String str) {
        return (URLStreamHandler) this.a.get(str.toLowerCase());
    }

    public void addHandler(String str, URLStreamHandler uRLStreamHandler) {
        this.a.put(str.toLowerCase(), uRLStreamHandler);
    }
}
