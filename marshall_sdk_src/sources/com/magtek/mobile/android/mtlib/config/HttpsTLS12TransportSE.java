package com.magtek.mobile.android.mtlib.config;

import android.os.Build;
import android.util.Log;
import java.io.IOException;
import java.security.KeyStore;
import org.ksoap2.transport.HttpsServiceConnectionSE;
import org.ksoap2.transport.HttpsTransportSE;
import org.ksoap2.transport.ServiceConnection;

/* JADX INFO: loaded from: classes.dex */
public class HttpsTLS12TransportSE extends HttpsTransportSE {
    private final String fileSE;
    private final String hostSE;
    private final int portSE;
    private final int timeoutSE;

    public HttpsTLS12TransportSE(String str, int i, String str2, int i2) {
        super(str, i, str2, i2);
        this.hostSE = str;
        this.portSE = i;
        this.fileSE = str2;
        this.timeoutSE = i2;
    }

    @Override // org.ksoap2.transport.HttpsTransportSE, org.ksoap2.transport.HttpTransportSE
    public ServiceConnection getServiceConnection() throws IOException {
        HttpsServiceConnectionSE httpsServiceConnectionSE = new HttpsServiceConnectionSE(this.hostSE, this.portSE, this.fileSE, this.timeoutSE);
        httpsServiceConnectionSE.setRequestProperty("Connection", "keep-alive");
        try {
            if (Build.VERSION.SDK_INT == 19) {
                KeyStore.getInstance("AndroidCAStore").load(null, null);
                httpsServiceConnectionSE.setSSLSocketFactory(new Tls12SocketFactory());
            }
        } catch (Exception e) {
            Log.e("HttpsTLS12TransportSE", "Exception: " + e.getMessage());
        }
        return httpsServiceConnectionSE;
    }
}
