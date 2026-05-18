package org.kobjects.pim;

import java.io.IOException;
import java.io.Reader;
import org.kobjects.io.LookAheadReader;

/* JADX INFO: loaded from: classes2.dex */
public class PimParser {
    LookAheadReader reader;
    Class type;

    public PimParser(Reader reader, Class cls) {
        this.reader = new LookAheadReader(reader);
        this.type = cls;
    }

    public PimItem readItem() throws IOException {
        Object arrayValue;
        String name = readName();
        if (name == null) {
            return null;
        }
        if (!name.equals("begin")) {
            throw new RuntimeException("'begin:' expected");
        }
        try {
            PimItem pimItem = (PimItem) this.type.newInstance();
            this.reader.read();
            if (!pimItem.getType().equals(readStringValue().toLowerCase())) {
                throw new RuntimeException("item types do not match!");
            }
            while (true) {
                String name2 = readName();
                if (!name2.equals("end")) {
                    PimField pimField = new PimField(name2);
                    readProperties(pimField);
                    if (pimItem.getType(name2) == 1) {
                        arrayValue = readArrayValue(pimItem.getArraySize(name2));
                    } else {
                        arrayValue = readStringValue();
                    }
                    pimField.setValue(arrayValue);
                    System.out.println("value:" + arrayValue);
                    pimItem.addField(pimField);
                } else {
                    this.reader.read();
                    System.out.println("end:" + readStringValue());
                    return pimItem;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    String readName() throws IOException {
        String lowerCase = this.reader.readTo(":;").trim().toLowerCase();
        System.out.println("name:" + lowerCase);
        if (this.reader.peek(0) == -1) {
            return null;
        }
        return lowerCase;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    java.lang.String[] readArrayValue(int r8) throws java.io.IOException {
        /*
            r7 = this;
            java.util.Vector r0 = new java.util.Vector
            r0.<init>()
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
            r2 = 1
        Lb:
            org.kobjects.io.LookAheadReader r3 = r7.reader
            java.lang.String r4 = ";\n\r"
            java.lang.String r3 = r3.readTo(r4)
            r1.append(r3)
            org.kobjects.io.LookAheadReader r3 = r7.reader
            int r3 = r3.read()
            r4 = 10
            r5 = 0
            if (r3 == r4) goto L42
            r6 = 13
            if (r3 == r6) goto L35
            r4 = 59
            if (r3 == r4) goto L2a
            goto L53
        L2a:
            java.lang.String r3 = r1.toString()
            r0.addElement(r3)
            r1.setLength(r5)
            goto L53
        L35:
            org.kobjects.io.LookAheadReader r3 = r7.reader
            int r3 = r3.peek(r5)
            if (r3 != r4) goto L42
            org.kobjects.io.LookAheadReader r3 = r7.reader
            r3.read()
        L42:
            org.kobjects.io.LookAheadReader r3 = r7.reader
            int r3 = r3.peek(r5)
            r4 = 32
            if (r3 == r4) goto L4e
            r2 = 0
            goto L53
        L4e:
            org.kobjects.io.LookAheadReader r3 = r7.reader
            r3.read()
        L53:
            if (r2 != 0) goto Lb
            int r2 = r1.length()
            if (r2 == 0) goto L62
            java.lang.String r1 = r1.toString()
            r0.addElement(r1)
        L62:
            java.lang.String[] r1 = new java.lang.String[r8]
        L64:
            int r2 = r0.size()
            int r2 = java.lang.Math.min(r8, r2)
            if (r5 >= r2) goto L79
            java.lang.Object r2 = r0.elementAt(r5)
            java.lang.String r2 = (java.lang.String) r2
            r1[r5] = r2
            int r5 = r5 + 1
            goto L64
        L79:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.kobjects.pim.PimParser.readArrayValue(int):java.lang.String[]");
    }

    String readStringValue() throws IOException {
        String line = this.reader.readLine();
        while (this.reader.peek(0) == 32) {
            this.reader.read();
            line = line + this.reader.readLine();
        }
        return line;
    }

    void readProperties(PimField pimField) throws IOException {
        int i = this.reader.read();
        while (i == 32) {
            i = this.reader.read();
        }
        while (i != 58) {
            String lowerCase = this.reader.readTo(":;=").trim().toLowerCase();
            int i2 = this.reader.read();
            if (i2 == 61) {
                pimField.setProperty(lowerCase, this.reader.readTo(":;").trim().toLowerCase());
                i = this.reader.read();
            } else {
                pimField.setAttribute(lowerCase, true);
                i = i2;
            }
        }
    }
}
