package org.ksoap2.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.ksoap2.HeaderProperty;

/* JADX INFO: loaded from: classes2.dex */
public class ServiceConnectionSE implements ServiceConnection {
    private HttpURLConnection connection;

    public ServiceConnectionSE(String str) throws IOException {
        this(null, str, ServiceConnection.DEFAULT_TIMEOUT);
    }

    public ServiceConnectionSE(Proxy proxy, String str) throws IOException {
        this(proxy, str, ServiceConnection.DEFAULT_TIMEOUT);
    }

    public ServiceConnectionSE(String str, int i) throws IOException {
        this(null, str, i);
    }

    public ServiceConnectionSE(Proxy proxy, String str, int i) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) (proxy == null ? new URL(str).openConnection() : new URL(str).openConnection(proxy));
        this.connection = httpURLConnection;
        httpURLConnection.setUseCaches(false);
        this.connection.setDoOutput(true);
        this.connection.setDoInput(true);
        this.connection.setConnectTimeout(i);
        this.connection.setReadTimeout(i);
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
        Map<String, List<String>> headerFields = this.connection.getHeaderFields();
        Set<String> setKeySet = headerFields.keySet();
        LinkedList linkedList = new LinkedList();
        for (String str : setKeySet) {
            List<String> list = headerFields.get(str);
            for (int i = 0; i < list.size(); i++) {
                linkedList.add(new HeaderProperty(str, list.get(i)));
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
}
