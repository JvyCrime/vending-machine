package org.kobjects.util;

/* JADX INFO: loaded from: classes2.dex */
public class Strings {
    public static String replace(String str, String str2, String str3) {
        int iIndexOf = str.indexOf(str2);
        if (iIndexOf == -1) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(str.substring(0, iIndexOf));
        while (true) {
            stringBuffer.append(str3);
            int length = iIndexOf + str2.length();
            int iIndexOf2 = str.indexOf(str2, length);
            if (iIndexOf2 != -1) {
                stringBuffer.append(str.substring(length, iIndexOf2));
                iIndexOf = iIndexOf2;
            } else {
                stringBuffer.append(str.substring(length));
                return stringBuffer.toString();
            }
        }
    }

    public static String toAscii(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt <= ' ') {
                stringBuffer.append(' ');
            } else if (cCharAt < 127) {
                stringBuffer.append(cCharAt);
            } else if (cCharAt == 196) {
                stringBuffer.append("Ae");
            } else if (cCharAt == 214) {
                stringBuffer.append("Oe");
            } else if (cCharAt == 220) {
                stringBuffer.append("Ue");
            } else if (cCharAt == 223) {
                stringBuffer.append("ss");
            } else if (cCharAt == 228) {
                stringBuffer.append("ae");
            } else if (cCharAt == 246) {
                stringBuffer.append("oe");
            } else if (cCharAt == 252) {
                stringBuffer.append("ue");
            } else {
                stringBuffer.append('?');
            }
        }
        return stringBuffer.toString();
    }

    public static String fill(String str, int i, char c) {
        boolean z = i < 0;
        int iAbs = Math.abs(i);
        if (str.length() >= iAbs) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int length = iAbs - str.length(); length > 0; length--) {
            stringBuffer.append(c);
        }
        if (z) {
            stringBuffer.append(str);
            return stringBuffer.toString();
        }
        return str + stringBuffer.toString();
    }

    public static String beautify(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str.length() > 0) {
            stringBuffer.append(Character.toUpperCase(str.charAt(0)));
            for (int i = 1; i < str.length() - 1; i++) {
                char cCharAt = str.charAt(i);
                if (Character.isUpperCase(cCharAt) && Character.isLowerCase(str.charAt(i - 1)) && Character.isLowerCase(str.charAt(i + 1))) {
                    stringBuffer.append(" ");
                }
                stringBuffer.append(cCharAt);
            }
            if (str.length() > 1) {
                stringBuffer.append(str.charAt(str.length() - 1));
            }
        }
        return stringBuffer.toString();
    }

    public static String lTrim(String str, String str2) {
        int length = str.length();
        int i = 0;
        while (i < length) {
            char cCharAt = str.charAt(i);
            if (str2 != null) {
                if (str2.indexOf(cCharAt) == -1) {
                    break;
                }
                i++;
            } else {
                if (cCharAt > ' ') {
                    break;
                }
                i++;
            }
        }
        return i == 0 ? str : str.substring(i);
    }

    public static String rTrim(String str, String str2) {
        int length = str.length() - 1;
        while (length >= 0) {
            char cCharAt = str.charAt(length);
            if (str2 != null) {
                if (str2.indexOf(cCharAt) == -1) {
                    break;
                }
                length--;
            } else {
                if (cCharAt > ' ') {
                    break;
                }
                length--;
            }
        }
        return length == str.length() + (-1) ? str : str.substring(0, length + 1);
    }
}
