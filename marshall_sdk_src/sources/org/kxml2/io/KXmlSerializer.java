package org.kxml2.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes2.dex */
public class KXmlSerializer implements XmlSerializer {
    private int auto;
    private int depth;
    private String encoding;
    private boolean pending;
    private boolean unicode;
    private Writer writer;
    private String[] elementStack = new String[12];
    private int[] nspCounts = new int[4];
    private String[] nspStack = new String[8];
    private boolean[] indent = new boolean[4];

    private final void check(boolean z) throws IOException {
        if (!this.pending) {
            return;
        }
        int i = this.depth + 1;
        this.depth = i;
        this.pending = false;
        boolean[] zArr = this.indent;
        if (zArr.length <= i) {
            boolean[] zArr2 = new boolean[i + 4];
            System.arraycopy(zArr, 0, zArr2, 0, i);
            this.indent = zArr2;
        }
        boolean[] zArr3 = this.indent;
        int i2 = this.depth;
        zArr3[i2] = zArr3[i2 - 1];
        int i3 = this.nspCounts[i2 - 1];
        while (true) {
            int[] iArr = this.nspCounts;
            int i4 = this.depth;
            if (i3 < iArr[i4]) {
                this.writer.write(32);
                this.writer.write("xmlns");
                int i5 = i3 * 2;
                if (!"".equals(this.nspStack[i5])) {
                    this.writer.write(58);
                    this.writer.write(this.nspStack[i5]);
                } else if ("".equals(getNamespace()) && !"".equals(this.nspStack[i5 + 1])) {
                    throw new IllegalStateException("Cannot set default namespace for elements in no namespace");
                }
                this.writer.write("=\"");
                writeEscaped(this.nspStack[i5 + 1], 34);
                this.writer.write(34);
                i3++;
            } else {
                if (iArr.length <= i4 + 1) {
                    int[] iArr2 = new int[i4 + 8];
                    System.arraycopy(iArr, 0, iArr2, 0, i4 + 1);
                    this.nspCounts = iArr2;
                }
                int[] iArr3 = this.nspCounts;
                int i6 = this.depth;
                iArr3[i6 + 1] = iArr3[i6];
                this.writer.write(z ? " />" : ">");
                return;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final void writeEscaped(java.lang.String r6, int r7) throws java.io.IOException {
        /*
            r5 = this;
            r0 = 0
        L1:
            int r1 = r6.length()
            if (r0 >= r1) goto Lac
            char r1 = r6.charAt(r0)
            r2 = 9
            java.lang.String r3 = "&#"
            if (r1 == r2) goto L86
            r2 = 10
            if (r1 == r2) goto L86
            r2 = 13
            if (r1 == r2) goto L86
            r2 = 34
            if (r1 == r2) goto L47
            r4 = 60
            if (r1 == r4) goto L3f
            r4 = 62
            if (r1 == r4) goto L37
            r4 = 38
            if (r1 == r4) goto L2e
            r4 = 39
            if (r1 == r4) goto L47
            goto L56
        L2e:
            java.io.Writer r1 = r5.writer
            java.lang.String r2 = "&amp;"
            r1.write(r2)
            goto La8
        L37:
            java.io.Writer r1 = r5.writer
            java.lang.String r2 = "&gt;"
            r1.write(r2)
            goto La8
        L3f:
            java.io.Writer r1 = r5.writer
            java.lang.String r2 = "&lt;"
            r1.write(r2)
            goto La8
        L47:
            if (r1 != r7) goto L56
            java.io.Writer r3 = r5.writer
            if (r1 != r2) goto L50
            java.lang.String r1 = "&quot;"
            goto L52
        L50:
            java.lang.String r1 = "&apos;"
        L52:
            r3.write(r1)
            goto La8
        L56:
            r2 = 32
            if (r1 < r2) goto L6c
            r2 = 64
            if (r1 == r2) goto L6c
            r2 = 127(0x7f, float:1.78E-43)
            if (r1 < r2) goto L66
            boolean r2 = r5.unicode
            if (r2 == 0) goto L6c
        L66:
            java.io.Writer r2 = r5.writer
            r2.write(r1)
            goto La8
        L6c:
            java.io.Writer r2 = r5.writer
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r3)
            r4.append(r1)
            java.lang.String r1 = ";"
            r4.append(r1)
            java.lang.String r1 = r4.toString()
            r2.write(r1)
            goto La8
        L86:
            r2 = -1
            if (r7 != r2) goto L8f
            java.io.Writer r2 = r5.writer
            r2.write(r1)
            goto La8
        L8f:
            java.io.Writer r2 = r5.writer
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r3)
            r4.append(r1)
            r1 = 59
            r4.append(r1)
            java.lang.String r1 = r4.toString()
            r2.write(r1)
        La8:
            int r0 = r0 + 1
            goto L1
        Lac:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.kxml2.io.KXmlSerializer.writeEscaped(java.lang.String, int):void");
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void docdecl(String str) throws IOException {
        this.writer.write("<!DOCTYPE");
        this.writer.write(str);
        this.writer.write(">");
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void endDocument() throws IOException {
        while (true) {
            if (this.depth > 0) {
                String[] strArr = this.elementStack;
                endTag(strArr[(r0 * 3) - 3], strArr[(r0 * 3) - 1]);
            } else {
                flush();
                return;
            }
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void entityRef(String str) throws IOException {
        check(false);
        this.writer.write(38);
        this.writer.write(str);
        this.writer.write(59);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public boolean getFeature(String str) {
        if ("http://xmlpull.org/v1/doc/features.html#indent-output".equals(str)) {
            return this.indent[this.depth];
        }
        return false;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getPrefix(String str, boolean z) {
        try {
            return getPrefix(str, false, z);
        } catch (IOException e) {
            throw new RuntimeException(e.toString());
        }
    }

    private final String getPrefix(String str, boolean z, boolean z2) throws IOException {
        int i = this.nspCounts[this.depth + 1] * 2;
        while (true) {
            i -= 2;
            String str2 = null;
            String str3 = "";
            if (i < 0) {
                if (!z2) {
                    return null;
                }
                if (!"".equals(str)) {
                    do {
                        StringBuilder sb = new StringBuilder();
                        sb.append("n");
                        int i2 = this.auto;
                        this.auto = i2 + 1;
                        sb.append(i2);
                        String string = sb.toString();
                        int i3 = (this.nspCounts[this.depth + 1] * 2) - 2;
                        while (true) {
                            if (i3 < 0) {
                                str3 = string;
                                break;
                            }
                            if (string.equals(this.nspStack[i3])) {
                                str3 = null;
                                break;
                            }
                            i3 -= 2;
                        }
                    } while (str3 == null);
                }
                boolean z3 = this.pending;
                this.pending = false;
                setPrefix(str3, str);
                this.pending = z3;
                return str3;
            }
            if (this.nspStack[i + 1].equals(str) && (z || !this.nspStack[i].equals(""))) {
                String str4 = this.nspStack[i];
                int i4 = i + 2;
                while (true) {
                    if (i4 >= this.nspCounts[this.depth + 1] * 2) {
                        str2 = str4;
                        break;
                    }
                    if (this.nspStack[i4].equals(str4)) {
                        break;
                    }
                    i4++;
                }
                if (str2 != null) {
                    return str2;
                }
            }
        }
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public Object getProperty(String str) {
        throw new RuntimeException("Unsupported property");
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void ignorableWhitespace(String str) throws IOException {
        text(str);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setFeature(String str, boolean z) {
        if ("http://xmlpull.org/v1/doc/features.html#indent-output".equals(str)) {
            this.indent[this.depth] = z;
            return;
        }
        throw new RuntimeException("Unsupported Feature");
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setProperty(String str, Object obj) {
        throw new RuntimeException("Unsupported Property:" + obj);
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setPrefix(String str, String str2) throws IOException {
        check(false);
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        if (str.equals(getPrefix(str2, true, false))) {
            return;
        }
        int[] iArr = this.nspCounts;
        int i = this.depth + 1;
        int i2 = iArr[i];
        iArr[i] = i2 + 1;
        int i3 = i2 << 1;
        String[] strArr = this.nspStack;
        int i4 = i3 + 1;
        if (strArr.length < i4) {
            String[] strArr2 = new String[strArr.length + 16];
            System.arraycopy(strArr, 0, strArr2, 0, i3);
            this.nspStack = strArr2;
        }
        String[] strArr3 = this.nspStack;
        strArr3[i3] = str;
        strArr3[i4] = str2;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(Writer writer) {
        this.writer = writer;
        int[] iArr = this.nspCounts;
        iArr[0] = 2;
        iArr[1] = 2;
        String[] strArr = this.nspStack;
        strArr[0] = "";
        strArr[1] = "";
        strArr[2] = "xml";
        strArr[3] = "http://www.w3.org/XML/1998/namespace";
        this.pending = false;
        this.auto = 0;
        this.depth = 0;
        this.unicode = false;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void setOutput(OutputStream outputStream, String str) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException();
        }
        setOutput(str == null ? new OutputStreamWriter(outputStream) : new OutputStreamWriter(outputStream, str));
        this.encoding = str;
        if (str == null || !str.toLowerCase().startsWith("utf")) {
            return;
        }
        this.unicode = true;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void startDocument(String str, Boolean bool) throws IOException {
        this.writer.write("<?xml version='1.0' ");
        if (str != null) {
            this.encoding = str;
            if (str.toLowerCase().startsWith("utf")) {
                this.unicode = true;
            }
        }
        if (this.encoding != null) {
            this.writer.write("encoding='");
            this.writer.write(this.encoding);
            this.writer.write("' ");
        }
        if (bool != null) {
            this.writer.write("standalone='");
            this.writer.write(bool.booleanValue() ? "yes" : "no");
            this.writer.write("' ");
        }
        this.writer.write("?>");
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer startTag(String str, String str2) throws IOException {
        check(false);
        if (this.indent[this.depth]) {
            this.writer.write("\r\n");
            for (int i = 0; i < this.depth; i++) {
                this.writer.write("  ");
            }
        }
        int i2 = this.depth * 3;
        String[] strArr = this.elementStack;
        if (strArr.length < i2 + 3) {
            String[] strArr2 = new String[strArr.length + 12];
            System.arraycopy(strArr, 0, strArr2, 0, i2);
            this.elementStack = strArr2;
        }
        String prefix = str == null ? "" : getPrefix(str, true, true);
        if ("".equals(str)) {
            for (int i3 = this.nspCounts[this.depth]; i3 < this.nspCounts[this.depth + 1]; i3++) {
                int i4 = i3 * 2;
                if ("".equals(this.nspStack[i4]) && !"".equals(this.nspStack[i4 + 1])) {
                    throw new IllegalStateException("Cannot set default namespace for elements in no namespace");
                }
            }
        }
        String[] strArr3 = this.elementStack;
        int i5 = i2 + 1;
        strArr3[i2] = str;
        strArr3[i5] = prefix;
        strArr3[i5 + 1] = str2;
        this.writer.write(60);
        if (!"".equals(prefix)) {
            this.writer.write(prefix);
            this.writer.write(58);
        }
        this.writer.write(str2);
        this.pending = true;
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer attribute(String str, String str2, String str3) throws IOException {
        if (!this.pending) {
            throw new IllegalStateException("illegal position for attribute");
        }
        if (str == null) {
            str = "";
        }
        String prefix = "".equals(str) ? "" : getPrefix(str, false, true);
        this.writer.write(32);
        if (!"".equals(prefix)) {
            this.writer.write(prefix);
            this.writer.write(58);
        }
        this.writer.write(str2);
        this.writer.write(61);
        int i = str3.indexOf(34) != -1 ? 39 : 34;
        this.writer.write(i);
        writeEscaped(str3, i);
        this.writer.write(i);
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void flush() throws IOException {
        check(false);
        this.writer.flush();
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer endTag(String str, String str2) throws IOException {
        if (!this.pending) {
            this.depth--;
        }
        if ((str == null && this.elementStack[this.depth * 3] != null) || ((str != null && !str.equals(this.elementStack[this.depth * 3])) || !this.elementStack[(this.depth * 3) + 2].equals(str2))) {
            throw new IllegalArgumentException("</{" + str + "}" + str2 + "> does not match start");
        }
        if (this.pending) {
            check(true);
            this.depth--;
        } else {
            if (this.indent[this.depth + 1]) {
                this.writer.write("\r\n");
                for (int i = 0; i < this.depth; i++) {
                    this.writer.write("  ");
                }
            }
            this.writer.write("</");
            String str3 = this.elementStack[(this.depth * 3) + 1];
            if (!"".equals(str3)) {
                this.writer.write(str3);
                this.writer.write(58);
            }
            this.writer.write(str2);
            this.writer.write(62);
        }
        int[] iArr = this.nspCounts;
        int i2 = this.depth;
        iArr[i2 + 1] = iArr[i2];
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getNamespace() {
        if (getDepth() == 0) {
            return null;
        }
        return this.elementStack[(getDepth() * 3) - 3];
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public String getName() {
        if (getDepth() == 0) {
            return null;
        }
        return this.elementStack[(getDepth() * 3) - 1];
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public int getDepth() {
        return this.pending ? this.depth + 1 : this.depth;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(String str) throws IOException {
        check(false);
        this.indent[this.depth] = false;
        writeEscaped(str, -1);
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public XmlSerializer text(char[] cArr, int i, int i2) throws IOException {
        text(new String(cArr, i, i2));
        return this;
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void cdsect(String str) throws IOException {
        check(false);
        this.writer.write("<![CDATA[");
        this.writer.write(str);
        this.writer.write("]]>");
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void comment(String str) throws IOException {
        check(false);
        this.writer.write("<!--");
        this.writer.write(str);
        this.writer.write("-->");
    }

    @Override // org.xmlpull.v1.XmlSerializer
    public void processingInstruction(String str) throws IOException {
        check(false);
        this.writer.write("<?");
        this.writer.write(str);
        this.writer.write("?>");
    }
}
