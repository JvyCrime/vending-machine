package org.ksoap2;

import java.io.IOException;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes2.dex */
public class SoapFault extends IOException {
    private static final long serialVersionUID = 1011001;
    public Node detail;
    public String faultactor;
    public String faultcode;
    public String faultstring;
    public int version;

    public SoapFault() {
        this.version = 110;
    }

    public SoapFault(int i) {
        this.version = i;
    }

    public void parse(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        xmlPullParser.require(2, SoapEnvelope.ENV, "Fault");
        while (xmlPullParser.nextTag() == 2) {
            String name = xmlPullParser.getName();
            if (name.equals("detail")) {
                Node node = new Node();
                this.detail = node;
                node.parse(xmlPullParser);
                if (xmlPullParser.getNamespace().equals(SoapEnvelope.ENV) && xmlPullParser.getName().equals("Fault")) {
                    break;
                }
            } else {
                if (name.equals("faultcode")) {
                    this.faultcode = xmlPullParser.nextText();
                } else if (name.equals("faultstring")) {
                    this.faultstring = xmlPullParser.nextText();
                } else if (name.equals("faultactor")) {
                    this.faultactor = xmlPullParser.nextText();
                } else {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("unexpected tag:");
                    stringBuffer.append(name);
                    throw new RuntimeException(stringBuffer.toString());
                }
                xmlPullParser.require(3, null, name);
            }
        }
        xmlPullParser.require(3, SoapEnvelope.ENV, "Fault");
        xmlPullParser.nextTag();
    }

    public void write(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startTag(SoapEnvelope.ENV, "Fault");
        xmlSerializer.startTag(null, "faultcode");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("");
        stringBuffer.append(this.faultcode);
        xmlSerializer.text(stringBuffer.toString());
        xmlSerializer.endTag(null, "faultcode");
        xmlSerializer.startTag(null, "faultstring");
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("");
        stringBuffer2.append(this.faultstring);
        xmlSerializer.text(stringBuffer2.toString());
        xmlSerializer.endTag(null, "faultstring");
        xmlSerializer.startTag(null, "detail");
        Node node = this.detail;
        if (node != null) {
            node.write(xmlSerializer);
        }
        xmlSerializer.endTag(null, "detail");
        xmlSerializer.endTag(SoapEnvelope.ENV, "Fault");
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.faultstring;
    }

    @Override // java.lang.Throwable
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SoapFault - faultcode: '");
        stringBuffer.append(this.faultcode);
        stringBuffer.append("' faultstring: '");
        stringBuffer.append(this.faultstring);
        stringBuffer.append("' faultactor: '");
        stringBuffer.append(this.faultactor);
        stringBuffer.append("' detail: ");
        stringBuffer.append(this.detail);
        return stringBuffer.toString();
    }
}
