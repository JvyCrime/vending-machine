package org.ksoap2.transport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.util.List;
import org.ksoap2.SoapEnvelope;
import org.kxml2.io.KXmlParser;
import org.kxml2.io.KXmlSerializer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes2.dex */
public abstract class Transport {
    protected static final String CONTENT_TYPE_SOAP_XML_CHARSET_UTF_8 = "application/soap+xml;charset=utf-8";
    protected static final String CONTENT_TYPE_XML_CHARSET_UTF_8 = "text/xml;charset=utf-8";
    protected static final String USER_AGENT = "ksoap2-android/2.6.0+";
    public boolean debug;
    protected Proxy proxy;
    public String requestDump;
    public String responseDump;
    protected int timeout;
    protected String url;
    private String xmlVersionTag;

    public abstract List call(String str, SoapEnvelope soapEnvelope, List list) throws XmlPullParserException, IOException;

    public abstract String getHost();

    public abstract String getPath();

    public abstract int getPort();

    public void reset() {
    }

    public Transport() {
        this.timeout = ServiceConnection.DEFAULT_TIMEOUT;
        this.xmlVersionTag = "";
    }

    public Transport(String str) {
        this((Proxy) null, str);
    }

    public Transport(String str, int i) {
        this.timeout = ServiceConnection.DEFAULT_TIMEOUT;
        this.xmlVersionTag = "";
        this.url = str;
        this.timeout = i;
    }

    public Transport(Proxy proxy, String str) {
        this.timeout = ServiceConnection.DEFAULT_TIMEOUT;
        this.xmlVersionTag = "";
        this.proxy = proxy;
        this.url = str;
    }

    public Transport(Proxy proxy, String str, int i) {
        this.timeout = ServiceConnection.DEFAULT_TIMEOUT;
        this.xmlVersionTag = "";
        this.proxy = proxy;
        this.url = str;
        this.timeout = i;
    }

    protected void parseResponse(SoapEnvelope soapEnvelope, InputStream inputStream) throws XmlPullParserException, IOException {
        KXmlParser kXmlParser = new KXmlParser();
        kXmlParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
        kXmlParser.setInput(inputStream, null);
        soapEnvelope.parse(kXmlParser);
    }

    protected byte[] createRequestData(SoapEnvelope soapEnvelope) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(this.xmlVersionTag.getBytes());
        XmlSerializer kXmlSerializer = new KXmlSerializer();
        kXmlSerializer.setOutput(byteArrayOutputStream, null);
        soapEnvelope.write(kXmlSerializer);
        kXmlSerializer.flush();
        byteArrayOutputStream.write(13);
        byteArrayOutputStream.write(10);
        byteArrayOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public void setXmlVersionTag(String str) {
        this.xmlVersionTag = str;
    }

    public void call(String str, SoapEnvelope soapEnvelope) throws XmlPullParserException, IOException {
        call(str, soapEnvelope, null);
    }
}
