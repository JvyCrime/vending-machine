package org.ksoap2.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.ksoap2.HeaderProperty;

/* JADX INFO: loaded from: classes2.dex */
public class HttpsServiceConnectionSE implements ServiceConnection {
    private HttpsURLConnection connection;

    public HttpsServiceConnectionSE(String str, int i, String str2, int i2) throws IOException {
        this.connection = (HttpsURLConnection) new URL("https", str, i, str2).openConnection();
        updateConnectionParameters(i2);
    }

    private void updateConnectionParameters(int i) {
        this.connection.setConnectTimeout(i);
        this.connection.setReadTimeout(i);
        this.connection.setUseCaches(false);
        this.connection.setDoOutput(true);
        this.connection.setDoInput(true);
    }

    @Override // org.ksoap2.transport.ServiceConnection
    public void connect() throws IOException {
        this.connection.connect();
    }

    @Override // org.ksoap2.transport.ServiceConnection
    public void disconnect() {
        this.connection.disconnect();
    }

    @Override // org.ksoap2.transport.ServiceConnection
    public List getResponseProperties() {
        Map headerFields = this.connection.getHeaderFields();
        Set<String> setKeySet = headerFields.keySet();
        LinkedList linkedList = new LinkedList();
        for (String str : setKeySet) {
            List list = (List) headerFields.get(str);
            for (int i = 0; i < list.size(); i++) {
                linkedList.add(new HeaderProperty(str, (String) list.get(i)));
            }
        }
        return linkedList;
    }

    @Override // org.ksoap2.transport.ServiceConnection
    public void setRequestProperty(String str, String str2) {
        this.connection.setRequestProperty(str, str2);
    }

    @Override // org.ksoap2.transport.ServiceConnection
    public void setRequestMethod(String str) throws IOException {
        this.connection.setRequestMethod(str);
    }

    @Override // org.ksoap2.transport.ServiceConnection
    public OutputStream openOutputStream() throws IOException {
        return this.connection.getOutputStream();
    }

    @Override // org.ksoap2.transport.ServiceConnection
    public InputStream openInputStream() throws IOException {
        return this.connection.getInputStream();
    }

    @Override // org.ksoap2.transport.ServiceConnection
    public InputStream getErrorStream() {
        return this.connection.getErrorStream();
    }

    @Override // org.ksoap2.transport.ServiceConnection
    public String getHost() {
        return this.connection.getURL().getHost();
    }

    @Override // org.ksoap2.transport.ServiceConnection
    public int getPort() {
        return this.connection.getURL().getPort();
    }

    @Override // org.ksoap2.transport.ServiceConnection
    public String getPath() {
        return this.connection.getURL().getPath();
    }

    public void setSSLSocketFactory(SSLSocketFactory sSLSocketFactory) {
        this.connection.setSSLSocketFactory(sSLSocketFactory);
    }
}
