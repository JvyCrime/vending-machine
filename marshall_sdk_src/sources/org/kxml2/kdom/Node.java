package org.kxml2.kdom;

import java.io.IOException;
import java.util.Objects;
import java.util.Vector;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes2.dex */
public class Node {
    public static final int CDSECT = 5;
    public static final int COMMENT = 9;
    public static final int DOCDECL = 10;
    public static final int DOCUMENT = 0;
    public static final int ELEMENT = 2;
    public static final int ENTITY_REF = 6;
    public static final int IGNORABLE_WHITESPACE = 7;
    public static final int PROCESSING_INSTRUCTION = 8;
    public static final int TEXT = 4;
    protected Vector children;
    protected StringBuffer types;

    public void addChild(int i, int i2, Object obj) {
        Objects.requireNonNull(obj);
        if (this.children == null) {
            this.children = new Vector();
            this.types = new StringBuffer();
        }
        if (i2 == 2) {
            if (!(obj instanceof Element)) {
                throw new RuntimeException("Element obj expected)");
            }
            ((Element) obj).setParent(this);
        } else if (!(obj instanceof String)) {
            throw new RuntimeException("String expected");
        }
        this.children.insertElementAt(obj, i);
        this.types.insert(i, (char) i2);
    }

    public void addChild(int i, Object obj) {
        addChild(getChildCount(), i, obj);
    }

    public Element createElement(String str, String str2) {
        Element element = new Element();
        if (str == null) {
            str = "";
        }
        element.namespace = str;
        element.name = str2;
        return element;
    }

    public Object getChild(int i) {
        return this.children.elementAt(i);
    }

    public int getChildCount() {
        Vector vector = this.children;
        if (vector == null) {
            return 0;
        }
        return vector.size();
    }

    public Element getElement(int i) {
        Object child = getChild(i);
        if (child instanceof Element) {
            return (Element) child;
        }
        return null;
    }

    public Element getElement(String str, String str2) {
        int iIndexOf = indexOf(str, str2, 0);
        int iIndexOf2 = indexOf(str, str2, iIndexOf + 1);
        if (iIndexOf == -1 || iIndexOf2 != -1) {
            StringBuilder sb = new StringBuilder();
            sb.append("Element {");
            sb.append(str);
            sb.append("}");
            sb.append(str2);
            sb.append(iIndexOf == -1 ? " not found in " : " more than once in ");
            sb.append(this);
            throw new RuntimeException(sb.toString());
        }
        return getElement(iIndexOf);
    }

    public String getText(int i) {
        if (isText(i)) {
            return (String) getChild(i);
        }
        return null;
    }

    public int getType(int i) {
        return this.types.charAt(i);
    }

    public int indexOf(String str, String str2, int i) {
        int childCount = getChildCount();
        while (i < childCount) {
            Element element = getElement(i);
            if (element != null && str2.equals(element.getName()) && (str == null || str.equals(element.getNamespace()))) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public boolean isText(int i) {
        int type = getType(i);
        return type == 4 || type == 7 || type == 5;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0046  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void parse(org.xmlpull.v1.XmlPullParser r5) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r4 = this;
            r0 = 0
        L1:
            int r1 = r5.getEventType()
            r2 = 1
            if (r1 == r2) goto L46
            r3 = 2
            if (r1 == r3) goto L33
            r3 = 3
            if (r1 == r3) goto L46
            java.lang.String r2 = r5.getText()
            r3 = 6
            if (r2 == 0) goto L20
            if (r1 != r3) goto L18
            r1 = 4
        L18:
            java.lang.String r2 = r5.getText()
            r4.addChild(r1, r2)
            goto L2f
        L20:
            if (r1 != r3) goto L2f
            java.lang.String r1 = r5.getName()
            if (r1 == 0) goto L2f
            java.lang.String r1 = r5.getName()
            r4.addChild(r3, r1)
        L2f:
            r5.nextToken()
            goto L47
        L33:
            java.lang.String r1 = r5.getNamespace()
            java.lang.String r2 = r5.getName()
            org.kxml2.kdom.Element r1 = r4.createElement(r1, r2)
            r4.addChild(r3, r1)
            r1.parse(r5)
            goto L47
        L46:
            r0 = 1
        L47:
            if (r0 == 0) goto L1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.kxml2.kdom.Node.parse(org.xmlpull.v1.XmlPullParser):void");
    }

    public void removeChild(int i) {
        this.children.removeElementAt(i);
        int length = this.types.length() - 1;
        while (i < length) {
            StringBuffer stringBuffer = this.types;
            int i2 = i + 1;
            stringBuffer.setCharAt(i, stringBuffer.charAt(i2));
            i = i2;
        }
        this.types.setLength(length);
    }

    public void write(XmlSerializer xmlSerializer) throws IOException {
        writeChildren(xmlSerializer);
        xmlSerializer.flush();
    }

    public void writeChildren(XmlSerializer xmlSerializer) throws IOException {
        Vector vector = this.children;
        if (vector == null) {
            return;
        }
        int size = vector.size();
        for (int i = 0; i < size; i++) {
            int type = getType(i);
            Object objElementAt = this.children.elementAt(i);
            switch (type) {
                case 2:
                    ((Element) objElementAt).write(xmlSerializer);
                    break;
                case 3:
                default:
                    throw new RuntimeException("Illegal type: " + type);
                case 4:
                    xmlSerializer.text((String) objElementAt);
                    break;
                case 5:
                    xmlSerializer.cdsect((String) objElementAt);
                    break;
                case 6:
                    xmlSerializer.entityRef((String) objElementAt);
                    break;
                case 7:
                    xmlSerializer.ignorableWhitespace((String) objElementAt);
                    break;
                case 8:
                    xmlSerializer.processingInstruction((String) objElementAt);
                    break;
                case 9:
                    xmlSerializer.comment((String) objElementAt);
                    break;
                case 10:
                    xmlSerializer.docdecl((String) objElementAt);
                    break;
            }
        }
    }
}
