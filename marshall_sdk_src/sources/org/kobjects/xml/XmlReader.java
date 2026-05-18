package org.kobjects.xml;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import kotlin.text.Typography;

/* JADX INFO: loaded from: classes2.dex */
public class XmlReader {
    static final int CDSECT = 5;
    public static final int END_DOCUMENT = 1;
    public static final int END_TAG = 3;
    static final int ENTITY_REF = 6;
    private static final int LEGACY = 999;
    public static final int START_DOCUMENT = 0;
    public static final int START_TAG = 2;
    public static final int TEXT = 4;
    private static final String UNEXPECTED_EOF = "Unexpected EOF";
    private String[] TYPES;
    private int attributeCount;
    private String[] attributes;
    private int column;
    private boolean degenerated;
    private int depth;
    private String[] elementStack = new String[4];
    private Hashtable entityMap;
    private boolean eof;
    private boolean isWhitespace;
    private int line;
    private String name;
    private int peek0;
    private int peek1;
    private Reader reader;
    public boolean relaxed;
    private char[] srcBuf;
    private int srcCount;
    private int srcPos;
    private String text;
    private char[] txtBuf;
    private int txtPos;
    private int type;

    private final int read() throws IOException {
        int i = this.peek0;
        int i2 = this.peek1;
        this.peek0 = i2;
        if (i2 == -1) {
            this.eof = true;
            return i;
        }
        if (i == 10 || i == 13) {
            this.line++;
            this.column = 0;
            if (i == 13 && i2 == 10) {
                this.peek0 = 0;
            }
        }
        this.column++;
        if (this.srcPos >= this.srcCount) {
            Reader reader = this.reader;
            char[] cArr = this.srcBuf;
            int i3 = reader.read(cArr, 0, cArr.length);
            this.srcCount = i3;
            if (i3 <= 0) {
                this.peek1 = -1;
                return i;
            }
            this.srcPos = 0;
        }
        char[] cArr2 = this.srcBuf;
        int i4 = this.srcPos;
        this.srcPos = i4 + 1;
        this.peek1 = cArr2[i4];
        return i;
    }

    private final void exception(String str) throws IOException {
        throw new IOException(str + " pos: " + getPositionDescription());
    }

    private final void push(int i) {
        if (i == 0) {
            return;
        }
        int i2 = this.txtPos;
        char[] cArr = this.txtBuf;
        if (i2 == cArr.length) {
            char[] cArr2 = new char[((i2 * 4) / 3) + 4];
            System.arraycopy(cArr, 0, cArr2, 0, i2);
            this.txtBuf = cArr2;
        }
        char[] cArr3 = this.txtBuf;
        int i3 = this.txtPos;
        this.txtPos = i3 + 1;
        cArr3[i3] = (char) i;
    }

    private final void read(char c) throws IOException {
        if (read() != c) {
            if (this.relaxed) {
                if (c <= ' ') {
                    skip();
                    read();
                    return;
                }
                return;
            }
            exception("expected: '" + c + "'");
        }
    }

    private final void skip() throws IOException {
        while (!this.eof && this.peek0 <= 32) {
            read();
        }
    }

    private final String pop(int i) {
        String str = new String(this.txtBuf, i, this.txtPos - i);
        this.txtPos = i;
        return str;
    }

    private final String readName() throws IOException {
        int i = this.txtPos;
        int i2 = this.peek0;
        if ((i2 < 97 || i2 > 122) && ((i2 < 65 || i2 > 90) && i2 != 95 && i2 != 58 && !this.relaxed)) {
            exception("name expected");
        }
        while (true) {
            push(read());
            int i3 = this.peek0;
            if (i3 < 97 || i3 > 122) {
                if (i3 < 65 || i3 > 90) {
                    if (i3 < 48 || i3 > 57) {
                        if (i3 != 95 && i3 != 45 && i3 != 58 && i3 != 46) {
                            return pop(i);
                        }
                    }
                }
            }
        }
    }

    private final void parseLegacy(boolean z) throws IOException {
        String str;
        read();
        int i = read();
        int i2 = 45;
        if (i == 63) {
            str = "";
            i2 = 63;
        } else if (i != 33) {
            if (i != 91) {
                exception("cantreachme: " + i);
            }
            i2 = 93;
            str = "CDATA[";
        } else if (this.peek0 == 45) {
            str = "--";
        } else {
            str = "DOCTYPE";
            i2 = -1;
        }
        for (int i3 = 0; i3 < str.length(); i3++) {
            read(str.charAt(i3));
        }
        if (i2 == -1) {
            parseDoctype();
            return;
        }
        while (true) {
            if (this.eof) {
                exception(UNEXPECTED_EOF);
            }
            int i4 = read();
            if (z) {
                push(i4);
            }
            if (i2 == 63 || i4 == i2) {
                if (this.peek0 == i2 && this.peek1 == 62) {
                    break;
                }
            }
        }
        read();
        read();
        if (!z || i2 == 63) {
            return;
        }
        pop(this.txtPos - 1);
    }

    private final void parseDoctype() throws IOException {
        int i = 1;
        while (true) {
            int i2 = read();
            if (i2 == -1) {
                exception(UNEXPECTED_EOF);
            } else if (i2 != 60) {
                if (i2 == 62 && i - 1 == 0) {
                    return;
                }
            }
            i++;
        }
    }

    private final void parseEndTag() throws IOException {
        read();
        read();
        this.name = readName();
        if (this.depth == 0 && !this.relaxed) {
            exception("element stack empty");
        }
        if (this.name.equals(this.elementStack[this.depth - 1])) {
            this.depth--;
        } else if (!this.relaxed) {
            exception("expected: " + this.elementStack[this.depth]);
        }
        skip();
        read(Typography.greater);
    }

    private final int peekType() {
        int i = this.peek0;
        if (i == -1) {
            return 1;
        }
        if (i == 38) {
            return 6;
        }
        if (i != 60) {
            return 4;
        }
        int i2 = this.peek1;
        if (i2 == 33) {
            return LEGACY;
        }
        if (i2 != 47) {
            return i2 != 63 ? i2 != 91 ? 2 : 5 : LEGACY;
        }
        return 3;
    }

    private static final String[] ensureCapacity(String[] strArr, int i) {
        if (strArr.length >= i) {
            return strArr;
        }
        String[] strArr2 = new String[i + 16];
        System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
        return strArr2;
    }

    private final void parseStartTag() throws IOException {
        read();
        this.name = readName();
        String[] strArrEnsureCapacity = ensureCapacity(this.elementStack, this.depth + 1);
        this.elementStack = strArrEnsureCapacity;
        int i = this.depth;
        this.depth = i + 1;
        strArrEnsureCapacity[i] = this.name;
        while (true) {
            skip();
            int i2 = this.peek0;
            if (i2 == 47) {
                this.degenerated = true;
                read();
                skip();
                read(Typography.greater);
                return;
            }
            if (i2 == 62) {
                read();
                return;
            }
            if (i2 == -1) {
                exception(UNEXPECTED_EOF);
            }
            String name = readName();
            if (name.length() == 0) {
                exception("attr name expected");
            }
            skip();
            read('=');
            skip();
            int i3 = read();
            if (i3 != 39 && i3 != 34) {
                if (!this.relaxed) {
                    exception("<" + this.name + ">: invalid delimiter: " + ((char) i3));
                }
                i3 = 32;
            }
            int i4 = this.attributeCount;
            this.attributeCount = i4 + 1;
            int i5 = i4 << 1;
            String[] strArrEnsureCapacity2 = ensureCapacity(this.attributes, i5 + 4);
            this.attributes = strArrEnsureCapacity2;
            strArrEnsureCapacity2[i5] = name;
            int i6 = this.txtPos;
            pushText(i3);
            this.attributes[i5 + 1] = pop(i6);
            if (i3 != 32) {
                read();
            }
        }
    }

    public final boolean pushEntity() throws IOException {
        read();
        int i = this.txtPos;
        while (!this.eof && this.peek0 != 59) {
            push(read());
        }
        String strPop = pop(i);
        read();
        boolean z = true;
        if (strPop.length() > 0 && strPop.charAt(0) == '#') {
            int i2 = strPop.charAt(1) == 'x' ? Integer.parseInt(strPop.substring(2), 16) : Integer.parseInt(strPop.substring(1));
            push(i2);
            return i2 <= 32;
        }
        String str = (String) this.entityMap.get(strPop);
        if (str == null) {
            str = "&" + strPop + ";";
        }
        for (int i3 = 0; i3 < str.length(); i3++) {
            char cCharAt = str.charAt(i3);
            if (cCharAt > ' ') {
                z = false;
            }
            push(cCharAt);
        }
        return z;
    }

    private final boolean pushText(int i) throws IOException {
        int i2 = this.peek0;
        boolean z = true;
        while (!this.eof && i2 != i && (i != 32 || (i2 > 32 && i2 != 62))) {
            if (i2 == 38) {
                if (!pushEntity()) {
                    z = false;
                }
            } else {
                if (i2 > 32) {
                    z = false;
                }
                push(read());
            }
            i2 = this.peek0;
        }
        return z;
    }

    public XmlReader(Reader reader) throws IOException {
        this.srcBuf = new char[Runtime.getRuntime().freeMemory() >= 1048576 ? 8192 : 128];
        this.txtBuf = new char[128];
        this.attributes = new String[16];
        this.TYPES = new String[]{"Start Document", "End Document", "Start Tag", "End Tag", "Text"};
        this.reader = reader;
        this.peek0 = reader.read();
        this.peek1 = reader.read();
        this.eof = this.peek0 == -1;
        Hashtable hashtable = new Hashtable();
        this.entityMap = hashtable;
        hashtable.put("amp", "&");
        this.entityMap.put("apos", "'");
        this.entityMap.put("gt", ">");
        this.entityMap.put("lt", "<");
        this.entityMap.put("quot", "\"");
        this.line = 1;
        this.column = 1;
    }

    public void defineCharacterEntity(String str, String str2) {
        this.entityMap.put(str, str2);
    }

    public int getDepth() {
        return this.depth;
    }

    public String getPositionDescription() {
        int i = this.type;
        String[] strArr = this.TYPES;
        StringBuffer stringBuffer = new StringBuffer(i < strArr.length ? strArr[i] : "Other");
        stringBuffer.append(" @" + this.line + ":" + this.column + ": ");
        int i2 = this.type;
        if (i2 == 2 || i2 == 3) {
            stringBuffer.append(Typography.less);
            if (this.type == 3) {
                stringBuffer.append('/');
            }
            stringBuffer.append(this.name);
            stringBuffer.append(Typography.greater);
        } else if (this.isWhitespace) {
            stringBuffer.append("[whitespace]");
        } else {
            stringBuffer.append(getText());
        }
        return stringBuffer.toString();
    }

    public int getLineNumber() {
        return this.line;
    }

    public int getColumnNumber() {
        return this.column;
    }

    public boolean isWhitespace() {
        return this.isWhitespace;
    }

    public String getText() {
        if (this.text == null) {
            this.text = pop(0);
        }
        return this.text;
    }

    public String getName() {
        return this.name;
    }

    public boolean isEmptyElementTag() {
        return this.degenerated;
    }

    public int getAttributeCount() {
        return this.attributeCount;
    }

    public String getAttributeName(int i) {
        if (i >= this.attributeCount) {
            throw new IndexOutOfBoundsException();
        }
        return this.attributes[i << 1];
    }

    public String getAttributeValue(int i) {
        if (i >= this.attributeCount) {
            throw new IndexOutOfBoundsException();
        }
        return this.attributes[(i << 1) + 1];
    }

    public String getAttributeValue(String str) {
        for (int i = (this.attributeCount << 1) - 2; i >= 0; i -= 2) {
            if (this.attributes[i].equals(str)) {
                return this.attributes[i + 1];
            }
        }
        return null;
    }

    public int getType() {
        return this.type;
    }

    public int next() throws IOException {
        if (this.degenerated) {
            this.type = 3;
            this.degenerated = false;
            this.depth--;
            return 3;
        }
        this.txtPos = 0;
        this.isWhitespace = true;
        while (true) {
            this.attributeCount = 0;
            this.name = null;
            this.text = null;
            int iPeekType = peekType();
            this.type = iPeekType;
            switch (iPeekType) {
                case 1:
                    break;
                case 2:
                    parseStartTag();
                    break;
                case 3:
                    parseEndTag();
                    break;
                case 4:
                    this.isWhitespace &= pushText(60);
                    break;
                case 5:
                    parseLegacy(true);
                    this.isWhitespace = false;
                    this.type = 4;
                    break;
                case 6:
                    this.isWhitespace &= pushEntity();
                    this.type = 4;
                    break;
                default:
                    parseLegacy(false);
                    break;
            }
            int i = this.type;
            if (i > 4 || (i == 4 && peekType() >= 4)) {
            }
        }
        boolean z = this.isWhitespace;
        int i2 = this.type;
        this.isWhitespace = z & (i2 == 4);
        return i2;
    }

    public void require(int i, String str) throws IOException {
        if (this.type == 4 && i != 4 && isWhitespace()) {
            next();
        }
        if (i == this.type && (str == null || str.equals(getName()))) {
            return;
        }
        exception("expected: " + this.TYPES[i] + "/" + str);
    }

    public String readText() throws IOException {
        if (this.type != 4) {
            return "";
        }
        String text = getText();
        next();
        return text;
    }
}
