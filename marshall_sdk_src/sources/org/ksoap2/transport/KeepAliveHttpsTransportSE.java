package org.ksoap2.transport;

import java.io.IOException;

/* JADX INFO: loaded from: classes2.dex */
public class KeepAliveHttpsTransportSE extends HttpsTransportSE {
    private HttpsServiceConnectionSE conn;
    private final String file;
    private final String host;
    private final int port;
    private final int timeout;

    public KeepAliveHttpsTransportSE(String str, int i, String str2, int i2) {
        super(str, i, str2, i2);
        this.conn = null;
        this.host = str;
        this.port = i;
        this.file = str2;
        this.timeout = i2;
    }

    @Override // org.ksoap2.transport.HttpsTransportSE, org.ksoap2.transport.HttpTransportSE
    protected ServiceConnection getServiceConnection() throws IOException {
        HttpsServiceConnectionSEIgnoringConnectionClose httpsServiceConnectionSEIgnoringConnectionClose = new HttpsServiceConnectionSEIgnoringConnectionClose(this.host, this.port, this.file, this.timeout);
        this.conn = httpsServiceConnectionSEIgnoringConnectionClose;
        httpsServiceConnectionSEIgnoringConnectionClose.setRequestProperty("Connection", "keep-alive");
        return this.conn;
    }
}
