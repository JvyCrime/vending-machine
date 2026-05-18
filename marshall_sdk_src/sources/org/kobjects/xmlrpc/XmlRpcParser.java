package org.kobjects.xmlrpc;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.kobjects.base64.Base64;
import org.kobjects.isodate.IsoDate;
import org.kobjects.xml.XmlReader;

/* JADX INFO: loaded from: classes2.dex */
public class XmlRpcParser {
    private XmlReader parser;

    public XmlRpcParser(XmlReader xmlReader) {
        this.parser = null;
        this.parser = xmlReader;
    }

    private final Hashtable parseStruct() throws IOException {
        Hashtable hashtable = new Hashtable();
        int iNextTag = nextTag();
        while (iNextTag != 3) {
            nextTag();
            String strNextText = nextText();
            nextTag();
            hashtable.put(strNextText, parseValue());
            iNextTag = nextTag();
        }
        nextTag();
        return hashtable;
    }

    private final Object parseValue() throws IOException {
        Object struct;
        Object num;
        int next = this.parser.next();
        if (next == 4) {
            String text = this.parser.getText();
            struct = text;
            next = this.parser.next();
        } else {
            struct = null;
        }
        if (next == 2) {
            String name = this.parser.getName();
            if (name.equals("array")) {
                struct = parseArray();
            } else if (name.equals("struct")) {
                struct = parseStruct();
            } else {
                if (name.equals(TypedValues.Custom.S_STRING)) {
                    num = nextText();
                } else if (name.equals("i4") || name.equals("int")) {
                    num = new Integer(Integer.parseInt(nextText().trim()));
                } else if (name.equals(TypedValues.Custom.S_BOOLEAN)) {
                    num = new Boolean(nextText().trim().equals("1"));
                } else if (name.equals("dateTime.iso8601")) {
                    num = IsoDate.stringToDate(nextText(), 3);
                } else if (name.equals("base64")) {
                    num = Base64.decode(nextText());
                } else {
                    if (name.equals("double")) {
                        num = nextText();
                    }
                    nextTag();
                }
                struct = num;
                nextTag();
            }
        }
        nextTag();
        return struct;
    }

    private final Vector parseArray() throws IOException {
        nextTag();
        int iNextTag = nextTag();
        Vector vector = new Vector();
        while (iNextTag != 3) {
            vector.addElement(parseValue());
            iNextTag = this.parser.getType();
        }
        nextTag();
        nextTag();
        return vector;
    }

    private final Object parseFault() throws IOException {
        nextTag();
        Object value = parseValue();
        nextTag();
        return value;
    }

    private final Object parseParams() throws IOException {
        Vector vector = new Vector();
        int iNextTag = nextTag();
        while (iNextTag != 3) {
            nextTag();
            vector.addElement(parseValue());
            iNextTag = nextTag();
        }
        nextTag();
        return vector;
    }

    public final Object parseResponse() throws IOException {
        nextTag();
        if (nextTag() == 2) {
            if ("fault".equals(this.parser.getName())) {
                return parseFault();
            }
            if ("params".equals(this.parser.getName())) {
                return parseParams();
            }
        }
        return null;
    }

    private final int nextTag() throws IOException {
        this.parser.getType();
        int next = this.parser.next();
        if (next == 4 && this.parser.isWhitespace()) {
            next = this.parser.next();
        }
        if (next == 3 || next == 2) {
            return next;
        }
        throw new IOException("unexpected type: " + next);
    }

    private final String nextText() throws IOException {
        String str;
        if (this.parser.getType() != 2) {
            throw new IOException("precondition: START_TAG");
        }
        int next = this.parser.next();
        if (next == 4) {
            String text = this.parser.getText();
            str = text;
            next = this.parser.next();
        } else {
            str = "";
        }
        if (next == 3) {
            return str;
        }
        throw new IOException("END_TAG expected");
    }
}
