package org.ksoap2;

import java.io.IOException;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes2.dex */
public class SoapEnvelope {
    public static final String ENC = "http://schemas.xmlsoap.org/soap/encoding/";
    public static final String ENC2003 = "http://www.w3.org/2003/05/soap-encoding";
    public static final String ENV = "http://schemas.xmlsoap.org/soap/envelope/";
    public static final String ENV2003 = "http://www.w3.org/2003/05/soap-envelope";
    public static final int VER10 = 100;
    public static final int VER11 = 110;
    public static final int VER12 = 120;
    public static final String XSD = "http://www.w3.org/2001/XMLSchema";
    public static final String XSD1999 = "http://www.w3.org/1999/XMLSchema";
    public static final String XSI = "http://www.w3.org/2001/XMLSchema-instance";
    public static final String XSI1999 = "http://www.w3.org/1999/XMLSchema-instance";
    public Object bodyIn;
    public Object bodyOut;
    public String enc;
    public String encodingStyle;
    public String env;
    public Element[] headerIn;
    public Element[] headerOut;
    public int version;
    public String xsd;
    public String xsi;

    public static boolean stringToBoolean(String str) {
        if (str == null) {
            return false;
        }
        String lowerCase = str.trim().toLowerCase();
        return lowerCase.equals("1") || lowerCase.equals("true");
    }

    public SoapEnvelope(int i) {
        this.version = i;
        if (i == 100) {
            this.xsi = XSI1999;
            this.xsd = XSD1999;
        } else {
            this.xsi = XSI;
            this.xsd = XSD;
        }
        if (i < 120) {
            this.enc = ENC;
            this.env = ENV;
        } else {
            this.enc = ENC2003;
            this.env = ENV2003;
        }
    }

    public void parse(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        xmlPullParser.nextTag();
        xmlPullParser.require(2, this.env, "Envelope");
        this.encodingStyle = xmlPullParser.getAttributeValue(this.env, "encodingStyle");
        xmlPullParser.nextTag();
        if (xmlPullParser.getEventType() == 2 && xmlPullParser.getNamespace().equals(this.env) && xmlPullParser.getName().equals("Header")) {
            parseHeader(xmlPullParser);
            xmlPullParser.require(3, this.env, "Header");
            xmlPullParser.nextTag();
        }
        xmlPullParser.require(2, this.env, "Body");
        this.encodingStyle = xmlPullParser.getAttributeValue(this.env, "encodingStyle");
        parseBody(xmlPullParser);
        xmlPullParser.require(3, this.env, "Body");
        xmlPullParser.nextTag();
        xmlPullParser.require(3, this.env, "Envelope");
    }

    public void parseHeader(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        xmlPullParser.nextTag();
        Node node = new Node();
        node.parse(xmlPullParser);
        int i = 0;
        for (int i2 = 0; i2 < node.getChildCount(); i2++) {
            if (node.getElement(i2) != null) {
                i++;
            }
        }
        this.headerIn = new Element[i];
        int i3 = 0;
        for (int i4 = 0; i4 < node.getChildCount(); i4++) {
            Element element = node.getElement(i4);
            if (element != null) {
                this.headerIn[i3] = element;
                i3++;
            }
        }
    }

    public void parseBody(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        SoapFault soapFault12;
        xmlPullParser.nextTag();
        if (xmlPullParser.getEventType() == 2 && xmlPullParser.getNamespace().equals(this.env) && xmlPullParser.getName().equals("Fault")) {
            if (this.version < 120) {
                soapFault12 = new SoapFault(this.version);
            } else {
                soapFault12 = new SoapFault12(this.version);
            }
            soapFault12.parse(xmlPullParser);
            this.bodyIn = soapFault12;
            return;
        }
        Object obj = this.bodyIn;
        Node node = obj instanceof Node ? (Node) obj : new Node();
        node.parse(xmlPullParser);
        this.bodyIn = node;
    }

    public void write(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.setPrefix("i", this.xsi);
        xmlSerializer.setPrefix("d", this.xsd);
        xmlSerializer.setPrefix("c", this.enc);
        xmlSerializer.setPrefix("v", this.env);
        xmlSerializer.startTag(this.env, "Envelope");
        xmlSerializer.startTag(this.env, "Header");
        writeHeader(xmlSerializer);
        xmlSerializer.endTag(this.env, "Header");
        xmlSerializer.startTag(this.env, "Body");
        writeBody(xmlSerializer);
        xmlSerializer.endTag(this.env, "Body");
        xmlSerializer.endTag(this.env, "Envelope");
    }

    public void writeHeader(XmlSerializer xmlSerializer) throws IOException {
        if (this.headerOut == null) {
            return;
        }
        int i = 0;
        while (true) {
            Element[] elementArr = this.headerOut;
            if (i >= elementArr.length) {
                return;
            }
            elementArr[i].write(xmlSerializer);
            i++;
        }
    }

    public void writeBody(XmlSerializer xmlSerializer) throws IOException {
        String str = this.encodingStyle;
        if (str != null) {
            xmlSerializer.attribute(this.env, "encodingStyle", str);
        }
        ((Node) this.bodyOut).write(xmlSerializer);
    }

    public void setOutputSoapObject(Object obj) {
        this.bodyOut = obj;
    }
}
