package iaik.protocol.https;

import java.io.IOException;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import org.java_websocket.WebSocketImpl;
import org.w3c.www.protocol.http.HttpURLConnection;

/* JADX INFO: loaded from: classes.dex */
public class Handler extends URLStreamHandler {
    @Override // java.net.URLStreamHandler
    protected int getDefaultPort() {
        return WebSocketImpl.DEFAULT_WSS_PORT;
    }

    @Override // java.net.URLStreamHandler
    protected URLConnection openConnection(URL url) throws IOException {
        return new HttpsURLConnection(url);
    }

    @Override // java.net.URLStreamHandler
    protected URLConnection openConnection(URL url, Proxy proxy) throws IOException {
        HttpURLConnection httpsURLConnection = new HttpsURLConnection(url);
        String str = (String) System.getProperties().get("java.version");
        if (str != null && str.compareTo("1.5") >= 0) {
            try {
                Proxy.Type type = proxy.type();
                Class<?> cls = Class.forName("java.net.Proxy$Type");
                if (cls.getDeclaredField("HTTP").get(null) == type) {
                    SocketAddress socketAddressAddress = proxy.address();
                    Class<?> cls2 = Class.forName("java.net.InetSocketAddress");
                    String str2 = (String) cls2.getDeclaredMethod("getHostName", new Class[0]).invoke(socketAddressAddress, null);
                    int iIntValue = ((Integer) cls2.getDeclaredMethod("getPort", new Class[0]).invoke(socketAddressAddress, null)).intValue();
                    StringBuffer stringBuffer = new StringBuffer(String.valueOf(str2));
                    stringBuffer.append(":");
                    stringBuffer.append(iIntValue);
                    httpsURLConnection.setRequestProperty("handler.https.proxy", stringBuffer.toString());
                } else if (cls.getDeclaredField("DIRECT").get(null) == type) {
                    httpsURLConnection.setRequestProperty("handler.https.proxy", "DIRECT:NP");
                }
            } catch (Throwable th) {
                StringBuffer stringBuffer2 = new StringBuffer("Cannot connect via proxy: ");
                stringBuffer2.append(th.toString());
                throw new IOException(stringBuffer2.toString());
            }
        }
        return httpsURLConnection;
    }

    @Override // java.net.URLStreamHandler
    protected void parseURL(URL url, String str, int i, int i2) {
        super.parseURL(url, str, i, i2);
        if (url.getPort() != -1) {
            return;
        }
        setURL(url, url.getProtocol(), url.getHost(), url.getProtocol().equalsIgnoreCase("http") ? 80 : WebSocketImpl.DEFAULT_WSS_PORT, url.getFile(), url.getRef());
    }
}
