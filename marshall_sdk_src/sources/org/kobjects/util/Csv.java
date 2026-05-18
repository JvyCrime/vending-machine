package org.kobjects.util;

import java.util.Vector;
import kotlin.text.Typography;

/* JADX INFO: loaded from: classes2.dex */
public class Csv {
    public static String encode(String str, char c) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == c || cCharAt == '^') {
                stringBuffer.append(cCharAt);
                stringBuffer.append(cCharAt);
            } else if (cCharAt < ' ') {
                stringBuffer.append('^');
                stringBuffer.append((char) (cCharAt + '@'));
            } else {
                stringBuffer.append(cCharAt);
            }
        }
        return stringBuffer.toString();
    }

    public static String encode(Object[] objArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < objArr.length; i++) {
            if (i != 0) {
                stringBuffer.append(',');
            }
            Object obj = objArr[i];
            if ((obj instanceof Number) || (obj instanceof Boolean)) {
                stringBuffer.append(obj.toString());
            } else {
                stringBuffer.append(Typography.quote);
                stringBuffer.append(encode(obj.toString(), Typography.quote));
                stringBuffer.append(Typography.quote);
            }
        }
        return stringBuffer.toString();
    }

    public static String[] decode(String str) {
        int i;
        Vector vector = new Vector();
        int length = str.length();
        int i2 = 0;
        while (true) {
            if (i2 < length && str.charAt(i2) <= ' ') {
                i2++;
            } else {
                if (i2 >= length) {
                    break;
                }
                if (str.charAt(i2) == '\"') {
                    int i3 = i2 + 1;
                    StringBuffer stringBuffer = new StringBuffer();
                    while (true) {
                        i = i3 + 1;
                        char cCharAt = str.charAt(i3);
                        if (cCharAt == '^' && i < length) {
                            i3 = i + 1;
                            char cCharAt2 = str.charAt(i);
                            if (cCharAt2 != '^') {
                                cCharAt2 = (char) (cCharAt2 - '@');
                            }
                            stringBuffer.append(cCharAt2);
                        } else {
                            if (cCharAt == '\"') {
                                if (i == length || str.charAt(i) != '\"') {
                                    break;
                                }
                                i++;
                            }
                            stringBuffer.append(cCharAt);
                            i3 = i;
                        }
                    }
                    vector.addElement(stringBuffer.toString());
                    while (i < length && str.charAt(i) <= ' ') {
                        i++;
                    }
                    if (i >= length) {
                        break;
                    }
                    if (str.charAt(i) != ',') {
                        throw new RuntimeException("Comma expected at " + i + " line: " + str);
                    }
                    i2 = i + 1;
                } else {
                    int iIndexOf = str.indexOf(44, i2);
                    if (iIndexOf == -1) {
                        vector.addElement(str.substring(i2).trim());
                        break;
                    }
                    vector.addElement(str.substring(i2, iIndexOf).trim());
                    i2 = iIndexOf + 1;
                }
            }
        }
        int size = vector.size();
        String[] strArr = new String[size];
        for (int i4 = 0; i4 < size; i4++) {
            strArr[i4] = (String) vector.elementAt(i4);
        }
        return strArr;
    }
}
