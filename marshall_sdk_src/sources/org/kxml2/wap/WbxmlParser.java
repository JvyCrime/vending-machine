package org.kxml2.wap;

import androidx.core.os.EnvironmentCompat;
import com.felhr.usbserial.UsbSerialDebugger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Vector;
import kotlin.text.Typography;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes2.dex */
public class WbxmlParser implements XmlPullParser {
    static final String HEX_DIGITS = "0123456789abcdef";
    private static final String ILLEGAL_TYPE = "Wrong event type";
    private static final String UNEXPECTED_EOF = "Unexpected EOF";
    public static final int WAP_EXTENSION = 64;
    private String[] attrStartTable;
    private String[] attrValueTable;
    private int attributeCount;
    private boolean degenerated;
    private int depth;
    private String encoding;
    private InputStream in;
    private boolean isWhitespace;
    private String name;
    private String namespace;
    private String prefix;
    private boolean processNsp;
    private int publicIdentifierId;
    private byte[] stringTable;
    private String[] tagTable;
    private String text;
    private int type;
    private int version;
    private int wapCode;
    private Object wapExtensionData;
    private int TAG_TABLE = 0;
    private int ATTR_START_TABLE = 1;
    private int ATTR_VALUE_TABLE = 2;
    private Hashtable cacheStringTable = null;
    private String[] elementStack = new String[16];
    private String[] nspStack = new String[8];
    private int[] nspCounts = new int[4];
    private String[] attributes = new String[16];
    private int nextId = -2;
    private Vector tables = new Vector();

    @Override // org.xmlpull.v1.XmlPullParser
    public void defineEntityReplacementText(String str, String str2) throws XmlPullParserException {
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributeType(int i) {
        return "CDATA";
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getColumnNumber() {
        return -1;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getLineNumber() {
        return -1;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public Object getProperty(String str) {
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isAttributeDefault(int i) {
        return false;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean getFeature(String str) {
        if (XmlPullParser.FEATURE_PROCESS_NAMESPACES.equals(str)) {
            return this.processNsp;
        }
        return false;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getInputEncoding() {
        return this.encoding;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getNamespaceCount(int i) {
        if (i > this.depth) {
            throw new IndexOutOfBoundsException();
        }
        return this.nspCounts[i];
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespacePrefix(int i) {
        return this.nspStack[i << 1];
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespaceUri(int i) {
        return this.nspStack[(i << 1) + 1];
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespace(String str) {
        if ("xml".equals(str)) {
            return "http://www.w3.org/XML/1998/namespace";
        }
        if ("xmlns".equals(str)) {
            return "http://www.w3.org/2000/xmlns/";
        }
        for (int namespaceCount = (getNamespaceCount(this.depth) << 1) - 2; namespaceCount >= 0; namespaceCount -= 2) {
            if (str == null) {
                String[] strArr = this.nspStack;
                if (strArr[namespaceCount] == null) {
                    return strArr[namespaceCount + 1];
                }
            } else if (str.equals(this.nspStack[namespaceCount])) {
                return this.nspStack[namespaceCount + 1];
            }
        }
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getDepth() {
        return this.depth;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getPositionDescription() {
        StringBuffer stringBuffer = new StringBuffer(this.type < TYPES.length ? TYPES[this.type] : EnvironmentCompat.MEDIA_UNKNOWN);
        stringBuffer.append(' ');
        int i = this.type;
        if (i == 2 || i == 3) {
            if (this.degenerated) {
                stringBuffer.append("(empty) ");
            }
            stringBuffer.append(Typography.less);
            if (this.type == 3) {
                stringBuffer.append('/');
            }
            if (this.prefix != null) {
                stringBuffer.append("{" + this.namespace + "}" + this.prefix + ":");
            }
            stringBuffer.append(this.name);
            int i2 = this.attributeCount << 2;
            for (int i3 = 0; i3 < i2; i3 += 4) {
                stringBuffer.append(' ');
                int i4 = i3 + 1;
                if (this.attributes[i4] != null) {
                    stringBuffer.append("{" + this.attributes[i3] + "}" + this.attributes[i4] + ":");
                }
                stringBuffer.append(this.attributes[i3 + 2] + "='" + this.attributes[i3 + 3] + "'");
            }
            stringBuffer.append(Typography.greater);
        } else if (i != 7) {
            if (i != 4) {
                stringBuffer.append(getText());
            } else if (this.isWhitespace) {
                stringBuffer.append("(whitespace)");
            } else {
                String text = getText();
                if (text.length() > 16) {
                    text = text.substring(0, 16) + "...";
                }
                stringBuffer.append(text);
            }
        }
        return stringBuffer.toString();
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isWhitespace() throws XmlPullParserException {
        int i = this.type;
        if (i != 4 && i != 7 && i != 5) {
            exception(ILLEGAL_TYPE);
        }
        return this.isWhitespace;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getText() {
        return this.text;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public char[] getTextCharacters(int[] iArr) {
        if (this.type >= 4) {
            iArr[0] = 0;
            iArr[1] = this.text.length();
            char[] cArr = new char[this.text.length()];
            String str = this.text;
            str.getChars(0, str.length(), cArr, 0);
            return cArr;
        }
        iArr[0] = -1;
        iArr[1] = -1;
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getNamespace() {
        return this.namespace;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getName() {
        return this.name;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getPrefix() {
        return this.prefix;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public boolean isEmptyElementTag() throws XmlPullParserException {
        if (this.type != 2) {
            exception(ILLEGAL_TYPE);
        }
        return this.degenerated;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getAttributeCount() {
        return this.attributeCount;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributeNamespace(int i) {
        if (i >= this.attributeCount) {
            throw new IndexOutOfBoundsException();
        }
        return this.attributes[i << 2];
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributeName(int i) {
        if (i >= this.attributeCount) {
            throw new IndexOutOfBoundsException();
        }
        return this.attributes[(i << 2) + 2];
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributePrefix(int i) {
        if (i >= this.attributeCount) {
            throw new IndexOutOfBoundsException();
        }
        return this.attributes[(i << 2) + 1];
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributeValue(int i) {
        if (i >= this.attributeCount) {
            throw new IndexOutOfBoundsException();
        }
        return this.attributes[(i << 2) + 3];
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String getAttributeValue(String str, String str2) {
        for (int i = (this.attributeCount << 2) - 4; i >= 0; i -= 4) {
            if (this.attributes[i + 2].equals(str2) && (str == null || this.attributes[i].equals(str))) {
                return this.attributes[i + 3];
            }
        }
        return null;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int getEventType() throws XmlPullParserException {
        return this.type;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int next() throws XmlPullParserException, IOException {
        this.isWhitespace = true;
        int i = 9999;
        while (true) {
            String str = this.text;
            nextImpl();
            int i2 = this.type;
            if (i2 < i) {
                i = i2;
            }
            if (i <= 5) {
                if (i >= 4) {
                    if (str != null) {
                        if (this.text != null) {
                            str = str + this.text;
                        }
                        this.text = str;
                    }
                    int iPeekId = peekId();
                    if (iPeekId != 2 && iPeekId != 3 && iPeekId != 4 && iPeekId != 68 && iPeekId != 196 && iPeekId != 131 && iPeekId != 132) {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        this.type = i;
        if (i > 4) {
            this.type = 4;
        }
        return this.type;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int nextToken() throws XmlPullParserException, IOException {
        this.isWhitespace = true;
        nextImpl();
        return this.type;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public int nextTag() throws XmlPullParserException, IOException {
        next();
        if (this.type == 4 && this.isWhitespace) {
            next();
        }
        int i = this.type;
        if (i != 3 && i != 2) {
            exception("unexpected type");
        }
        return this.type;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public String nextText() throws XmlPullParserException, IOException {
        String text;
        if (this.type != 2) {
            exception("precondition: START_TAG");
        }
        next();
        if (this.type == 4) {
            text = getText();
            next();
        } else {
            text = "";
        }
        if (this.type != 3) {
            exception("END_TAG expected");
        }
        return text;
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void require(int i, String str, String str2) throws XmlPullParserException, IOException {
        String str3;
        if (i == this.type && ((str == null || str.equals(getNamespace())) && (str2 == null || str2.equals(getName())))) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("expected: ");
        if (i == 64) {
            str3 = "WAP Ext.";
        } else {
            str3 = TYPES[i] + " {" + str + "}" + str2;
        }
        sb.append(str3);
        exception(sb.toString());
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setInput(Reader reader) throws XmlPullParserException {
        exception("InputStream required");
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setInput(InputStream inputStream, String str) throws XmlPullParserException {
        this.in = inputStream;
        try {
            this.version = readByte();
            int i = readInt();
            this.publicIdentifierId = i;
            if (i == 0) {
                readInt();
            }
            int i2 = readInt();
            if (str != null) {
                this.encoding = str;
            } else if (i2 == 4) {
                this.encoding = "ISO-8859-1";
            } else if (i2 == 106) {
                this.encoding = UsbSerialDebugger.ENCODING;
            } else {
                throw new UnsupportedEncodingException("" + i2);
            }
            int i3 = readInt();
            this.stringTable = new byte[i3];
            int i4 = 0;
            while (i4 < i3) {
                int i5 = inputStream.read(this.stringTable, i4, i3 - i4);
                if (i5 <= 0) {
                    break;
                } else {
                    i4 += i5;
                }
            }
            selectPage(0, true);
            selectPage(0, false);
        } catch (IOException unused) {
            exception("Illegal input format");
        }
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setFeature(String str, boolean z) throws XmlPullParserException {
        if (XmlPullParser.FEATURE_PROCESS_NAMESPACES.equals(str)) {
            this.processNsp = z;
            return;
        }
        exception("unsupported feature: " + str);
    }

    @Override // org.xmlpull.v1.XmlPullParser
    public void setProperty(String str, Object obj) throws XmlPullParserException {
        throw new XmlPullParserException("unsupported property: " + str);
    }

    private final boolean adjustNsp() throws XmlPullParserException {
        int i;
        String strSubstring;
        int i2 = 0;
        boolean z = false;
        while (true) {
            i = this.attributeCount;
            if (i2 >= (i << 2)) {
                break;
            }
            String str = this.attributes[i2 + 2];
            int iIndexOf = str.indexOf(58);
            if (iIndexOf != -1) {
                String strSubstring2 = str.substring(0, iIndexOf);
                strSubstring = str.substring(iIndexOf + 1);
                str = strSubstring2;
            } else if (str.equals("xmlns")) {
                strSubstring = null;
            } else {
                i2 += 4;
            }
            if (str.equals("xmlns")) {
                int[] iArr = this.nspCounts;
                int i3 = this.depth;
                int i4 = iArr[i3];
                iArr[i3] = i4 + 1;
                int i5 = i4 << 1;
                String[] strArrEnsureCapacity = ensureCapacity(this.nspStack, i5 + 2);
                this.nspStack = strArrEnsureCapacity;
                strArrEnsureCapacity[i5] = strSubstring;
                String[] strArr = this.attributes;
                int i6 = i2 + 3;
                strArrEnsureCapacity[i5 + 1] = strArr[i6];
                if (strSubstring != null && strArr[i6].equals("")) {
                    exception("illegal empty namespace");
                }
                String[] strArr2 = this.attributes;
                int i7 = this.attributeCount - 1;
                this.attributeCount = i7;
                System.arraycopy(strArr2, i2 + 4, strArr2, i2, (i7 << 2) - i2);
                i2 -= 4;
            } else {
                z = true;
            }
            i2 += 4;
        }
        if (z) {
            for (int i8 = (i << 2) - 4; i8 >= 0; i8 -= 4) {
                int i9 = i8 + 2;
                String str2 = this.attributes[i9];
                int iIndexOf2 = str2.indexOf(58);
                if (iIndexOf2 == 0) {
                    throw new RuntimeException("illegal attribute name: " + str2 + " at " + this);
                }
                if (iIndexOf2 != -1) {
                    String strSubstring3 = str2.substring(0, iIndexOf2);
                    String strSubstring4 = str2.substring(iIndexOf2 + 1);
                    String namespace = getNamespace(strSubstring3);
                    if (namespace == null) {
                        throw new RuntimeException("Undefined Prefix: " + strSubstring3 + " in " + this);
                    }
                    String[] strArr3 = this.attributes;
                    strArr3[i8] = namespace;
                    strArr3[i8 + 1] = strSubstring3;
                    strArr3[i9] = strSubstring4;
                    for (int i10 = (this.attributeCount << 2) - 4; i10 > i8; i10 -= 4) {
                        if (strSubstring4.equals(this.attributes[i10 + 2]) && namespace.equals(this.attributes[i10])) {
                            exception("Duplicate Attribute: {" + namespace + "}" + strSubstring4);
                        }
                    }
                }
            }
        }
        int iIndexOf3 = this.name.indexOf(58);
        if (iIndexOf3 == 0) {
            exception("illegal tag name: " + this.name);
        } else if (iIndexOf3 != -1) {
            this.prefix = this.name.substring(0, iIndexOf3);
            this.name = this.name.substring(iIndexOf3 + 1);
        }
        String namespace2 = getNamespace(this.prefix);
        this.namespace = namespace2;
        if (namespace2 == null) {
            if (this.prefix != null) {
                exception("undefined prefix: " + this.prefix);
            }
            this.namespace = "";
        }
        return z;
    }

    private final void setTable(int i, int i2, String[] strArr) {
        if (this.stringTable != null) {
            throw new RuntimeException("setXxxTable must be called before setInput!");
        }
        while (true) {
            int i3 = i * 3;
            if (this.tables.size() < i3 + 3) {
                this.tables.addElement(null);
            } else {
                this.tables.setElementAt(strArr, i3 + i2);
                return;
            }
        }
    }

    private final void exception(String str) throws XmlPullParserException {
        throw new XmlPullParserException(str, this, null);
    }

    private void selectPage(int i, boolean z) throws XmlPullParserException {
        if (this.tables.size() == 0 && i == 0) {
            return;
        }
        int i2 = i * 3;
        if (i2 > this.tables.size()) {
            exception("Code Page " + i + " undefined!");
        }
        if (z) {
            this.tagTable = (String[]) this.tables.elementAt(i2 + this.TAG_TABLE);
        } else {
            this.attrStartTable = (String[]) this.tables.elementAt(this.ATTR_START_TABLE + i2);
            this.attrValueTable = (String[]) this.tables.elementAt(i2 + this.ATTR_VALUE_TABLE);
        }
    }

    private final void nextImpl() throws XmlPullParserException, IOException {
        if (this.type == 3) {
            this.depth--;
        }
        if (this.degenerated) {
            this.type = 3;
            this.degenerated = false;
            return;
        }
        this.text = null;
        this.prefix = null;
        this.name = null;
        int iPeekId = peekId();
        while (iPeekId == 0) {
            this.nextId = -2;
            selectPage(readByte(), true);
            iPeekId = peekId();
        }
        this.nextId = -2;
        if (iPeekId == -1) {
            this.type = 1;
            return;
        }
        if (iPeekId == 1) {
            int i = (this.depth - 1) << 2;
            this.type = 3;
            String[] strArr = this.elementStack;
            this.namespace = strArr[i];
            this.prefix = strArr[i + 1];
            this.name = strArr[i + 2];
            return;
        }
        if (iPeekId == 2) {
            this.type = 6;
            char c = (char) readInt();
            this.text = "" + c;
            this.name = "#" + ((int) c);
            return;
        }
        if (iPeekId == 3) {
            this.type = 4;
            this.text = readStrI();
            return;
        }
        switch (iPeekId) {
            case 64:
            case 65:
            case 66:
                break;
            case 67:
                throw new RuntimeException("PI curr. not supp.");
            default:
                switch (iPeekId) {
                    case 128:
                    case 129:
                    case 130:
                        break;
                    case 131:
                        this.type = 4;
                        this.text = readStrT();
                        return;
                    default:
                        switch (iPeekId) {
                            case 192:
                            case Wbxml.EXT_1 /* 193 */:
                            case Wbxml.EXT_2 /* 194 */:
                            case Wbxml.OPAQUE /* 195 */:
                                break;
                            default:
                                parseElement(iPeekId);
                                return;
                        }
                        break;
                }
                break;
        }
        this.type = 64;
        this.wapCode = iPeekId;
        this.wapExtensionData = parseWapExtension(iPeekId);
    }

    public Object parseWapExtension(int i) throws XmlPullParserException, IOException {
        switch (i) {
            case 64:
            case 65:
            case 66:
                return readStrI();
            default:
                switch (i) {
                    case 128:
                    case 129:
                    case 130:
                        return new Integer(readInt());
                    default:
                        byte[] bArr = null;
                        switch (i) {
                            case Wbxml.OPAQUE /* 195 */:
                                int i2 = readInt();
                                bArr = new byte[i2];
                                int i3 = i2;
                                while (i3 > 0) {
                                    i3 -= this.in.read(bArr, i2 - i3, i3);
                                }
                            case 192:
                            case Wbxml.EXT_1 /* 193 */:
                            case Wbxml.EXT_2 /* 194 */:
                                return bArr;
                            default:
                                exception("illegal id: " + i);
                                return null;
                        }
                        break;
                }
                break;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void readAttr() throws XmlPullParserException, IOException {
        StringBuffer stringBuffer;
        int i = readByte();
        int i2 = 0;
        while (i != 1) {
            while (i == 0) {
                selectPage(readByte(), false);
                i = readByte();
            }
            String strResolveId = resolveId(this.attrStartTable, i);
            int iIndexOf = strResolveId.indexOf(61);
            if (iIndexOf == -1) {
                stringBuffer = new StringBuffer();
            } else {
                StringBuffer stringBuffer2 = new StringBuffer(strResolveId.substring(iIndexOf + 1));
                strResolveId = strResolveId.substring(0, iIndexOf);
                stringBuffer = stringBuffer2;
            }
            int i3 = readByte();
            while (true) {
                if (i3 > 128 || i3 == 0 || i3 == 2 || i3 == 3 || i3 == 131 || ((i3 >= 64 && i3 <= 66) || (i3 >= 128 && i3 <= 130))) {
                    if (i3 == 0) {
                        selectPage(readByte(), false);
                    } else if (i3 == 2) {
                        stringBuffer.append((char) readInt());
                    } else if (i3 == 3) {
                        stringBuffer.append(readStrI());
                    } else {
                        switch (i3) {
                            default:
                                switch (i3) {
                                    case 128:
                                    case 129:
                                    case 130:
                                        break;
                                    case 131:
                                        stringBuffer.append(readStrT());
                                        break;
                                    default:
                                        switch (i3) {
                                            case 192:
                                            case Wbxml.EXT_1 /* 193 */:
                                            case Wbxml.EXT_2 /* 194 */:
                                            case Wbxml.OPAQUE /* 195 */:
                                                break;
                                            default:
                                                stringBuffer.append(resolveId(this.attrValueTable, i3));
                                                break;
                                        }
                                        break;
                                }
                            case 64:
                            case 65:
                            case 66:
                                stringBuffer.append(resolveWapExtension(i3, parseWapExtension(i3)));
                                break;
                        }
                    }
                    i3 = readByte();
                }
            }
            String[] strArrEnsureCapacity = ensureCapacity(this.attributes, i2 + 4);
            this.attributes = strArrEnsureCapacity;
            int i4 = i2 + 1;
            strArrEnsureCapacity[i2] = "";
            int i5 = i4 + 1;
            strArrEnsureCapacity[i4] = null;
            int i6 = i5 + 1;
            strArrEnsureCapacity[i5] = strResolveId;
            i2 = i6 + 1;
            strArrEnsureCapacity[i6] = stringBuffer.toString();
            this.attributeCount++;
            i = i3;
        }
    }

    private int peekId() throws IOException {
        if (this.nextId == -2) {
            this.nextId = this.in.read();
        }
        return this.nextId;
    }

    protected String resolveWapExtension(int i, Object obj) {
        if (obj instanceof byte[]) {
            StringBuffer stringBuffer = new StringBuffer();
            byte[] bArr = (byte[]) obj;
            for (int i2 = 0; i2 < bArr.length; i2++) {
                stringBuffer.append(HEX_DIGITS.charAt((bArr[i2] >> 4) & 15));
                stringBuffer.append(HEX_DIGITS.charAt(bArr[i2] & 15));
            }
            return stringBuffer.toString();
        }
        return "$(" + obj + ")";
    }

    String resolveId(String[] strArr, int i) throws IOException {
        int i2 = (i & 127) - 5;
        if (i2 == -1) {
            this.wapCode = -1;
            return readStrT();
        }
        if (i2 < 0 || strArr == null || i2 >= strArr.length || strArr[i2] == null) {
            throw new IOException("id " + i + " undef.");
        }
        this.wapCode = i2 + 5;
        return strArr[i2];
    }

    void parseElement(int i) throws XmlPullParserException, IOException {
        this.type = 2;
        this.name = resolveId(this.tagTable, i & 63);
        this.attributeCount = 0;
        if ((i & 128) != 0) {
            readAttr();
        }
        this.degenerated = (i & 64) == 0;
        int i2 = this.depth;
        this.depth = i2 + 1;
        int i3 = i2 << 2;
        String[] strArrEnsureCapacity = ensureCapacity(this.elementStack, i3 + 4);
        this.elementStack = strArrEnsureCapacity;
        strArrEnsureCapacity[i3 + 3] = this.name;
        int i4 = this.depth;
        int[] iArr = this.nspCounts;
        if (i4 >= iArr.length) {
            int[] iArr2 = new int[i4 + 4];
            System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            this.nspCounts = iArr2;
        }
        int[] iArr3 = this.nspCounts;
        int i5 = this.depth;
        iArr3[i5] = iArr3[i5 - 1];
        for (int i6 = this.attributeCount - 1; i6 > 0; i6--) {
            for (int i7 = 0; i7 < i6; i7++) {
                if (getAttributeName(i6).equals(getAttributeName(i7))) {
                    exception("Duplicate Attribute: " + getAttributeName(i6));
                }
            }
        }
        if (this.processNsp) {
            adjustNsp();
        } else {
            this.namespace = "";
        }
        String[] strArr = this.elementStack;
        strArr[i3] = this.namespace;
        strArr[i3 + 1] = this.prefix;
        strArr[i3 + 2] = this.name;
    }

    private final String[] ensureCapacity(String[] strArr, int i) {
        if (strArr.length >= i) {
            return strArr;
        }
        String[] strArr2 = new String[i + 16];
        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
        return strArr2;
    }

    int readByte() throws IOException {
        int i = this.in.read();
        if (i != -1) {
            return i;
        }
        throw new IOException(UNEXPECTED_EOF);
    }

    int readInt() throws IOException {
        int i;
        int i2 = 0;
        do {
            i = readByte();
            i2 = (i2 << 7) | (i & 127);
        } while ((i & 128) != 0);
        return i2;
    }

    String readStrI() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        boolean z = true;
        while (true) {
            int i = this.in.read();
            if (i == 0) {
                this.isWhitespace = z;
                String str = new String(byteArrayOutputStream.toByteArray(), this.encoding);
                byteArrayOutputStream.close();
                return str;
            }
            if (i == -1) {
                throw new IOException(UNEXPECTED_EOF);
            }
            if (i > 32) {
                z = false;
            }
            byteArrayOutputStream.write(i);
        }
    }

    String readStrT() throws IOException {
        int i = readInt();
        if (this.cacheStringTable == null) {
            this.cacheStringTable = new Hashtable();
        }
        String str = (String) this.cacheStringTable.get(new Integer(i));
        if (str != null) {
            return str;
        }
        int i2 = i;
        while (true) {
            byte[] bArr = this.stringTable;
            if (i2 >= bArr.length || bArr[i2] == 0) {
                break;
            }
            i2++;
        }
        String str2 = new String(this.stringTable, i, i2 - i, this.encoding);
        this.cacheStringTable.put(new Integer(i), str2);
        return str2;
    }

    public void setTagTable(int i, String[] strArr) {
        setTable(i, this.TAG_TABLE, strArr);
    }

    public void setAttrStartTable(int i, String[] strArr) {
        setTable(i, this.ATTR_START_TABLE, strArr);
    }

    public void setAttrValueTable(int i, String[] strArr) {
        setTable(i, this.ATTR_VALUE_TABLE, strArr);
    }

    public int getWapCode() {
        return this.wapCode;
    }

    public Object getWapExtensionData() {
        return this.wapExtensionData;
    }
}
