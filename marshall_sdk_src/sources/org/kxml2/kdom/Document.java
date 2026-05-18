package org.kxml2.kdom;

import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes2.dex */
public class Document extends Node {
    String encoding;
    protected int rootIndex = -1;
    Boolean standalone;

    public String getName() {
        return "#document";
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String str) {
        this.encoding = str;
    }

    public void setStandalone(Boolean bool) {
        this.standalone = bool;
    }

    public Boolean getStandalone() {
        return this.standalone;
    }

    @Override // org.kxml2.kdom.Node
    public void addChild(int i, int i2, Object obj) {
        if (i2 == 2) {
            this.rootIndex = i;
        } else {
            int i3 = this.rootIndex;
            if (i3 >= i) {
                this.rootIndex = i3 + 1;
            }
        }
        super.addChild(i, i2, obj);
    }

    @Override // org.kxml2.kdom.Node
    public void parse(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        xmlPullParser.require(0, null, null);
        xmlPullParser.nextToken();
        this.encoding = xmlPullParser.getInputEncoding();
        this.standalone = (Boolean) xmlPullParser.getProperty("http://xmlpull.org/v1/doc/properties.html#xmldecl-standalone");
        super.parse(xmlPullParser);
        if (xmlPullParser.getEventType() != 1) {
            throw new RuntimeException("Document end expected!");
        }
    }

    @Override // org.kxml2.kdom.Node
    public void removeChild(int i) {
        int i2 = this.rootIndex;
        if (i == i2) {
            this.rootIndex = -1;
        } else if (i < i2) {
            this.rootIndex = i2 - 1;
        }
        super.removeChild(i);
    }

    public Element getRootElement() {
        int i = this.rootIndex;
        if (i == -1) {
            throw new RuntimeException("Document has no root element!");
        }
        return (Element) getChild(i);
    }

    @Override // org.kxml2.kdom.Node
    public void write(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startDocument(this.encoding, this.standalone);
        writeChildren(xmlSerializer);
        xmlSerializer.endDocument();
    }
}
