package org.ksoap2.transport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPInputStream;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes2.dex */
public class HttpTransportSE extends Transport {
    private ServiceConnection connection;

    public HttpTransportSE(String str) {
        super((Proxy) null, str);
    }

    public HttpTransportSE(Proxy proxy, String str) {
        super(proxy, str);
    }

    public HttpTransportSE(String str, int i) {
        super(str, i);
    }

    public HttpTransportSE(Proxy proxy, String str, int i) {
        super(proxy, str, i);
    }

    @Override // org.ksoap2.transport.Transport
    public void call(String str, SoapEnvelope soapEnvelope) throws XmlPullParserException, IOException {
        call(str, soapEnvelope, null);
    }

    @Override // org.ksoap2.transport.Transport
    public List call(String str, SoapEnvelope soapEnvelope, List list) throws XmlPullParserException, IOException {
        InputStream byteArrayInputStream;
        boolean z;
        if (str == null) {
            str = "\"\"";
        }
        byte[] bArrCreateRequestData = createRequestData(soapEnvelope);
        List responseProperties = null;
        this.requestDump = this.debug ? new String(bArrCreateRequestData) : null;
        this.responseDump = null;
        ServiceConnection serviceConnection = getServiceConnection();
        this.connection = serviceConnection;
        serviceConnection.setRequestProperty("User-Agent", "ksoap2-android/2.6.0+");
        if (soapEnvelope.version != 120) {
            this.connection.setRequestProperty("SOAPAction", str);
        }
        if (soapEnvelope.version == 120) {
            this.connection.setRequestProperty("Content-Type", "application/soap+xml;charset=utf-8");
        } else {
            this.connection.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
        }
        this.connection.setRequestProperty("Connection", "close");
        this.connection.setRequestProperty("Accept-Encoding", "gzip");
        ServiceConnection serviceConnection2 = this.connection;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("");
        stringBuffer.append(bArrCreateRequestData.length);
        serviceConnection2.setRequestProperty("Content-Length", stringBuffer.toString());
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                HeaderProperty headerProperty = (HeaderProperty) list.get(i);
                this.connection.setRequestProperty(headerProperty.getKey(), headerProperty.getValue());
            }
        }
        this.connection.setRequestMethod("POST");
        OutputStream outputStreamOpenOutputStream = this.connection.openOutputStream();
        outputStreamOpenOutputStream.write(bArrCreateRequestData, 0, bArrCreateRequestData.length);
        outputStreamOpenOutputStream.flush();
        outputStreamOpenOutputStream.close();
        try {
            responseProperties = this.connection.getResponseProperties();
            int i2 = 0;
            while (true) {
                if (i2 >= responseProperties.size()) {
                    z = false;
                    break;
                }
                HeaderProperty headerProperty2 = (HeaderProperty) responseProperties.get(i2);
                if (headerProperty2.getKey() != null && headerProperty2.getKey().equals("Content-Encoding") && headerProperty2.getValue().equals("gzip")) {
                    z = true;
                    break;
                }
                i2++;
            }
            if (z) {
                byteArrayInputStream = new GZIPInputStream(this.connection.openInputStream());
            } else {
                byteArrayInputStream = this.connection.openInputStream();
            }
        } catch (IOException e) {
            InputStream errorStream = this.connection.getErrorStream();
            if (errorStream == null) {
                this.connection.disconnect();
                throw e;
            }
            byteArrayInputStream = errorStream;
        }
        if (this.debug) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[256];
            while (true) {
                int i3 = byteArrayInputStream.read(bArr, 0, 256);
                if (i3 == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, i3);
            }
            byteArrayOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            this.responseDump = new String(byteArray);
            byteArrayInputStream.close();
            byteArrayInputStream = new ByteArrayInputStream(byteArray);
        }
        parseResponse(soapEnvelope, byteArrayInputStream);
        return responseProperties;
    }

    public ServiceConnection getConnection() {
        return (ServiceConnectionSE) this.connection;
    }

    protected ServiceConnection getServiceConnection() throws IOException {
        return new ServiceConnectionSE(this.proxy, this.url, this.timeout);
    }

    @Override // org.ksoap2.transport.Transport
    public String getHost() {
        try {
            return new URL(this.url).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // org.ksoap2.transport.Transport
    public int getPort() {
        try {
            return new URL(this.url).getPort();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override // org.ksoap2.transport.Transport
    public String getPath() {
        try {
            return new URL(this.url).getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
