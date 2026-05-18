package org.ksoap2.transport;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/* JADX INFO: loaded from: classes2.dex */
public class HttpsTransportSE extends HttpTransportSE {
    static final String PROTOCOL = "https";
    private HttpsServiceConnectionSE conn;
    private final String file;
    private final String host;
    private final int port;
    private final int timeout;

    public HttpsTransportSE(String str, int i, String str2, int i2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("https://");
        stringBuffer.append(str);
        stringBuffer.append(":");
        stringBuffer.append(i);
        stringBuffer.append(str2);
        super(stringBuffer.toString());
        this.conn = null;
        this.host = str;
        this.port = i;
        this.file = str2;
        this.timeout = i2;
    }

    @Override // org.ksoap2.transport.HttpTransportSE
    public ServiceConnection getConnection() {
        return this.conn;
    }

    @Override // org.ksoap2.transport.HttpTransportSE
    protected ServiceConnection getServiceConnection() throws IOException {
        HttpsServiceConnectionSE httpsServiceConnectionSE = new HttpsServiceConnectionSE(this.host, this.port, this.file, this.timeout);
        this.conn = httpsServiceConnectionSE;
        return httpsServiceConnectionSE;
    }

    @Override // org.ksoap2.transport.HttpTransportSE, org.ksoap2.transport.Transport
    public String getHost() {
        try {
            return new URL(this.url).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // org.ksoap2.transport.HttpTransportSE, org.ksoap2.transport.Transport
    public int getPort() {
        try {
            return new URL(this.url).getPort();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // org.ksoap2.transport.HttpTransportSE, org.ksoap2.transport.Transport
    public String getPath() {
        try {
            return new URL(this.url).getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
