package org.kobjects.rss;

import java.io.IOException;
import java.io.Reader;
import org.kobjects.xml.XmlReader;

/* JADX INFO: loaded from: classes2.dex */
public class RssReader {
    public static final int AUTHOR = 4;
    public static final int DATE = 3;
    public static final int DESCRIPTION = 2;
    public static final int LINK = 1;
    public static final int TITLE = 0;
    XmlReader xr;

    public RssReader(Reader reader) throws IOException {
        this.xr = new XmlReader(reader);
    }

    void readText(StringBuffer stringBuffer) throws IOException {
        while (this.xr.next() != 3) {
            int type = this.xr.getType();
            if (type == 2) {
                readText(stringBuffer);
            } else if (type == 4) {
                stringBuffer.append(this.xr.getText());
            }
        }
    }

    public String[] next() throws IOException {
        String[] strArr = new String[5];
        while (this.xr.next() != 1) {
            if (this.xr.getType() == 2) {
                String lowerCase = this.xr.getName().toLowerCase();
                if (lowerCase.equals("item") || lowerCase.endsWith(":item")) {
                    while (this.xr.next() != 3) {
                        if (this.xr.getType() == 2) {
                            String lowerCase2 = this.xr.getName().toLowerCase();
                            int iIndexOf = lowerCase2.indexOf(":");
                            if (iIndexOf != -1) {
                                lowerCase2 = lowerCase2.substring(iIndexOf + 1);
                            }
                            StringBuffer stringBuffer = new StringBuffer();
                            readText(stringBuffer);
                            String string = stringBuffer.toString();
                            if (lowerCase2.equals("title")) {
                                strArr[0] = string;
                            } else if (lowerCase2.equals("link")) {
                                strArr[1] = string;
                            } else if (lowerCase2.equals("description")) {
                                strArr[2] = string;
                            } else if (lowerCase2.equals("date")) {
                                strArr[3] = string;
                            } else if (lowerCase2.equals("author")) {
                                strArr[4] = string;
                            }
                        }
                    }
                    return strArr;
                }
            }
        }
        return null;
    }
}
