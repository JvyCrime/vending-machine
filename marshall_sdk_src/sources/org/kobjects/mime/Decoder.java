package org.kobjects.mime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import org.kobjects.base64.Base64;

/* JADX INFO: loaded from: classes2.dex */
public class Decoder {
    String boundary;
    char[] buf;
    String characterEncoding;
    boolean consumed;
    boolean eof;
    Hashtable header;
    InputStream is;

    private final String readLine() throws IOException {
        int i = 0;
        while (true) {
            int i2 = this.is.read();
            if (i2 == -1 && i == 0) {
                return null;
            }
            if (i2 == -1 || i2 == 10) {
                break;
            }
            if (i2 != 13) {
                char[] cArr = this.buf;
                if (i >= cArr.length) {
                    char[] cArr2 = new char[(cArr.length * 3) / 2];
                    System.arraycopy(cArr, 0, cArr2, 0, cArr.length);
                    this.buf = cArr2;
                }
                this.buf[i] = (char) i2;
                i++;
            }
        }
        return new String(this.buf, 0, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0092 A[EDGE_INSN: B:39:0x0092->B:29:0x0092 BREAK  A[LOOP:0: B:3:0x000c->B:40:0x000c], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.Hashtable getHeaderElements(java.lang.String r9) {
        /*
            java.util.Hashtable r0 = new java.util.Hashtable
            r0.<init>()
            int r1 = r9.length()
            java.lang.String r2 = ""
            r3 = 0
        Lc:
            if (r3 >= r1) goto L19
            char r4 = r9.charAt(r3)
            r5 = 32
            if (r4 > r5) goto L19
            int r3 = r3 + 1
            goto Lc
        L19:
            if (r3 < r1) goto L1d
            goto L92
        L1d:
            char r4 = r9.charAt(r3)
            r5 = 59
            r6 = 34
            r7 = -1
            if (r4 != r6) goto L73
            int r3 = r3 + 1
            int r4 = r9.indexOf(r6, r3)
            if (r4 == r7) goto L5c
            java.lang.String r3 = r9.substring(r3, r4)
            r0.put(r2, r3)
            int r4 = r4 + 2
            if (r4 < r1) goto L3c
            goto L92
        L3c:
            int r2 = r4 + (-1)
            char r2 = r9.charAt(r2)
            if (r2 != r5) goto L45
            goto L8a
        L45:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "; expected in "
            r1.append(r2)
            r1.append(r9)
            java.lang.String r9 = r1.toString()
            r0.<init>(r9)
            throw r0
        L5c:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "End quote expected in "
            r1.append(r2)
            r1.append(r9)
            java.lang.String r9 = r1.toString()
            r0.<init>(r9)
            throw r0
        L73:
            int r4 = r9.indexOf(r5, r3)
            if (r4 != r7) goto L81
            java.lang.String r9 = r9.substring(r3)
            r0.put(r2, r9)
            goto L92
        L81:
            java.lang.String r3 = r9.substring(r3, r4)
            r0.put(r2, r3)
            int r4 = r4 + 1
        L8a:
            r2 = 61
            int r2 = r9.indexOf(r2, r4)
            if (r2 != r7) goto L93
        L92:
            return r0
        L93:
            java.lang.String r3 = r9.substring(r4, r2)
            java.lang.String r3 = r3.toLowerCase()
            java.lang.String r3 = r3.trim()
            int r2 = r2 + 1
            r8 = r3
            r3 = r2
            r2 = r8
            goto Lc
        */
        throw new UnsupportedOperationException("Method not decompiled: org.kobjects.mime.Decoder.getHeaderElements(java.lang.String):java.util.Hashtable");
    }

    public Decoder(InputStream inputStream, String str) throws IOException {
        this(inputStream, str, null);
    }

    public Decoder(InputStream inputStream, String str, String str2) throws IOException {
        String line;
        this.buf = new char[256];
        this.characterEncoding = str2;
        this.is = inputStream;
        this.boundary = "--" + str;
        do {
            line = readLine();
            if (line == null) {
                throw new IOException("Unexpected EOF");
            }
        } while (!line.startsWith(this.boundary));
        if (line.endsWith("--")) {
            this.eof = true;
            inputStream.close();
        }
        this.consumed = true;
    }

    public Enumeration getHeaderNames() {
        return this.header.keys();
    }

    public String getHeader(String str) {
        return (String) this.header.get(str.toLowerCase());
    }

    public String readContent() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        readContent(byteArrayOutputStream);
        String str = this.characterEncoding == null ? new String(byteArrayOutputStream.toByteArray()) : new String(byteArrayOutputStream.toByteArray(), this.characterEncoding);
        System.out.println("Field content: '" + str + "'");
        return str;
    }

    public void readContent(OutputStream outputStream) throws IOException {
        String line;
        if (this.consumed) {
            throw new RuntimeException("Content already consumed!");
        }
        getHeader("Content-Type");
        if ("base64".equals(getHeader("Content-Transfer-Encoding"))) {
            new ByteArrayOutputStream();
            while (true) {
                line = readLine();
                if (line == null) {
                    throw new IOException("Unexpected EOF");
                }
                if (line.startsWith(this.boundary)) {
                    break;
                } else {
                    Base64.decode(line, outputStream);
                }
            }
        } else {
            String str = "\r\n" + this.boundary;
            int i = 0;
            while (true) {
                int i2 = this.is.read();
                if (i2 == -1) {
                    throw new RuntimeException("Unexpected EOF");
                }
                char c = (char) i2;
                if (c == str.charAt(i)) {
                    i++;
                    if (i == str.length()) {
                        line = readLine();
                        break;
                    }
                } else {
                    if (i > 0) {
                        for (int i3 = 0; i3 < i; i3++) {
                            outputStream.write((byte) str.charAt(i3));
                        }
                        i = c == str.charAt(0) ? 1 : 0;
                    }
                    if (i == 0) {
                        outputStream.write((byte) i2);
                    }
                }
            }
        }
        if (line.endsWith("--")) {
            this.eof = true;
        }
        this.consumed = true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0060, code lost:
    
        r5.consumed = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0063, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean next() throws java.io.IOException {
        /*
            r5 = this;
            boolean r0 = r5.consumed
            if (r0 != 0) goto L8
            r0 = 0
            r5.readContent(r0)
        L8:
            boolean r0 = r5.eof
            r1 = 0
            if (r0 == 0) goto Le
            return r1
        Le:
            java.util.Hashtable r0 = new java.util.Hashtable
            r0.<init>()
            r5.header = r0
        L15:
            java.lang.String r0 = r5.readLine()
            if (r0 == 0) goto L60
            java.lang.String r2 = ""
            boolean r2 = r0.equals(r2)
            if (r2 == 0) goto L24
            goto L60
        L24:
            r2 = 58
            int r2 = r0.indexOf(r2)
            r3 = -1
            if (r2 == r3) goto L49
            java.util.Hashtable r3 = r5.header
            java.lang.String r4 = r0.substring(r1, r2)
            java.lang.String r4 = r4.trim()
            java.lang.String r4 = r4.toLowerCase()
            int r2 = r2 + 1
            java.lang.String r0 = r0.substring(r2)
            java.lang.String r0 = r0.trim()
            r3.put(r4, r0)
            goto L15
        L49:
            java.io.IOException r1 = new java.io.IOException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "colon missing in multipart header line: "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            throw r1
        L60:
            r5.consumed = r1
            r0 = 1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.kobjects.mime.Decoder.next():boolean");
    }
}
