package org.kxml2.kdom;

import java.io.IOException;
import java.util.Objects;
import java.util.Vector;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes2.dex */
public class Element extends Node {
    protected Vector attributes;
    protected String name;
    protected String namespace;
    protected Node parent;
    protected Vector prefixes;

    public void init() {
    }

    public void clear() {
        this.attributes = null;
        this.children = null;
    }

    @Override // org.kxml2.kdom.Node
    public Element createElement(String str, String str2) {
        Node node = this.parent;
        return node == null ? super.createElement(str, str2) : node.createElement(str, str2);
    }

    public int getAttributeCount() {
        Vector vector = this.attributes;
        if (vector == null) {
            return 0;
        }
        return vector.size();
    }

    public String getAttributeNamespace(int i) {
        return ((String[]) this.attributes.elementAt(i))[0];
    }

    public String getAttributeName(int i) {
        return ((String[]) this.attributes.elementAt(i))[1];
    }

    public String getAttributeValue(int i) {
        return ((String[]) this.attributes.elementAt(i))[2];
    }

    public String getAttributeValue(String str, String str2) {
        for (int i = 0; i < getAttributeCount(); i++) {
            if (str2.equals(getAttributeName(i)) && (str == null || str.equals(getAttributeNamespace(i)))) {
                return getAttributeValue(i);
            }
        }
        return null;
    }

    public Node getRoot() {
        Element element = this;
        while (true) {
            Node node = element.parent;
            if (node == null) {
                return element;
            }
            if (!(node instanceof Element)) {
                return node;
            }
            element = (Element) node;
        }
    }

    public String getName() {
        return this.name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getNamespaceUri(String str) {
        int namespaceCount = getNamespaceCount();
        for (int i = 0; i < namespaceCount; i++) {
            if (str == getNamespacePrefix(i) || (str != null && str.equals(getNamespacePrefix(i)))) {
                return getNamespaceUri(i);
            }
        }
        Node node = this.parent;
        if (node instanceof Element) {
            return ((Element) node).getNamespaceUri(str);
        }
        return null;
    }

    public int getNamespaceCount() {
        Vector vector = this.prefixes;
        if (vector == null) {
            return 0;
        }
        return vector.size();
    }

    public String getNamespacePrefix(int i) {
        return ((String[]) this.prefixes.elementAt(i))[0];
    }

    public String getNamespaceUri(int i) {
        return ((String[]) this.prefixes.elementAt(i))[1];
    }

    public Node getParent() {
        return this.parent;
    }

    @Override // org.kxml2.kdom.Node
    public void parse(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        for (int namespaceCount = xmlPullParser.getNamespaceCount(xmlPullParser.getDepth() - 1); namespaceCount < xmlPullParser.getNamespaceCount(xmlPullParser.getDepth()); namespaceCount++) {
            setPrefix(xmlPullParser.getNamespacePrefix(namespaceCount), xmlPullParser.getNamespaceUri(namespaceCount));
        }
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            setAttribute(xmlPullParser.getAttributeNamespace(i), xmlPullParser.getAttributeName(i), xmlPullParser.getAttributeValue(i));
        }
        init();
        if (xmlPullParser.isEmptyElementTag()) {
            xmlPullParser.nextToken();
        } else {
            xmlPullParser.nextToken();
            super.parse(xmlPullParser);
            if (getChildCount() == 0) {
                addChild(7, "");
            }
        }
        xmlPullParser.require(3, getNamespace(), getName());
        xmlPullParser.nextToken();
    }

    public void setAttribute(String str, String str2, String str3) {
        if (this.attributes == null) {
            this.attributes = new Vector();
        }
        if (str == null) {
            str = "";
        }
        for (int size = this.attributes.size() - 1; size >= 0; size--) {
            String[] strArr = (String[]) this.attributes.elementAt(size);
            if (strArr[0].equals(str) && strArr[1].equals(str2)) {
                if (str3 == null) {
                    this.attributes.removeElementAt(size);
                    return;
                } else {
                    strArr[2] = str3;
                    return;
                }
            }
        }
        this.attributes.addElement(new String[]{str, str2, str3});
    }

    public void setPrefix(String str, String str2) {
        if (this.prefixes == null) {
            this.prefixes = new Vector();
        }
        this.prefixes.addElement(new String[]{str, str2});
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setNamespace(String str) {
        Objects.requireNonNull(str, "Use \"\" for empty namespace");
        this.namespace = str;
    }

    protected void setParent(Node node) {
        this.parent = node;
    }

    @Override // org.kxml2.kdom.Node
    public void write(XmlSerializer xmlSerializer) throws IOException {
        if (this.prefixes != null) {
            for (int i = 0; i < this.prefixes.size(); i++) {
                xmlSerializer.setPrefix(getNamespacePrefix(i), getNamespaceUri(i));
            }
        }
        xmlSerializer.startTag(getNamespace(), getName());
        int attributeCount = getAttributeCount();
        for (int i2 = 0; i2 < attributeCount; i2++) {
            xmlSerializer.attribute(getAttributeNamespace(i2), getAttributeName(i2), getAttributeValue(i2));
        }
        writeChildren(xmlSerializer);
        xmlSerializer.endTag(getNamespace(), getName());
    }
}
