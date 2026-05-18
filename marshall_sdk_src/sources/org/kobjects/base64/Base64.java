package org.kobjects.base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes2.dex */
public class Base64 {
    static final char[] charTab = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

    public static String encode(byte[] bArr) {
        return encode(bArr, 0, bArr.length, null).toString();
    }

    public static StringBuffer encode(byte[] bArr, int i, int i2, StringBuffer stringBuffer) {
        if (stringBuffer == null) {
            stringBuffer = new StringBuffer((bArr.length * 3) / 2);
        }
        int i3 = i2 - 3;
        int i4 = i;
        loop0: while (true) {
            int i5 = 0;
            while (i4 <= i3) {
                int i6 = ((bArr[i4] & 255) << 16) | ((bArr[i4 + 1] & 255) << 8) | (bArr[i4 + 2] & 255);
                char[] cArr = charTab;
                stringBuffer.append(cArr[(i6 >> 18) & 63]);
                stringBuffer.append(cArr[(i6 >> 12) & 63]);
                stringBuffer.append(cArr[(i6 >> 6) & 63]);
                stringBuffer.append(cArr[i6 & 63]);
                i4 += 3;
                int i7 = i5 + 1;
                if (i5 >= 14) {
                    break;
                }
                i5 = i7;
            }
            stringBuffer.append("\r\n");
        }
        int i8 = i + i2;
        if (i4 == i8 - 2) {
            int i9 = ((bArr[i4 + 1] & 255) << 8) | ((bArr[i4] & 255) << 16);
            char[] cArr2 = charTab;
            stringBuffer.append(cArr2[(i9 >> 18) & 63]);
            stringBuffer.append(cArr2[(i9 >> 12) & 63]);
            stringBuffer.append(cArr2[(i9 >> 6) & 63]);
            stringBuffer.append("=");
        } else if (i4 == i8 - 1) {
            int i10 = (bArr[i4] & 255) << 16;
            char[] cArr3 = charTab;
            stringBuffer.append(cArr3[(i10 >> 18) & 63]);
            stringBuffer.append(cArr3[(i10 >> 12) & 63]);
            stringBuffer.append("==");
        }
        return stringBuffer;
    }

    static int decode(char c) {
        int i;
        if (c >= 'A' && c <= 'Z') {
            return c - 'A';
        }
        if (c >= 'a' && c <= 'z') {
            i = c - 'a';
        } else {
            if (c < '0' || c > '9') {
                if (c == '+') {
                    return 62;
                }
                if (c == '/') {
                    return 63;
                }
                if (c == '=') {
                    return 0;
                }
                throw new RuntimeException("unexpected code: " + c);
            }
            i = (c - '0') + 26;
        }
        return i + 26;
    }

    public static byte[] decode(String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            decode(str, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException unused) {
            throw new RuntimeException();
        }
    }

    public static void decode(String str, OutputStream outputStream) throws IOException {
        int length = str.length();
        int i = 0;
        while (true) {
            if (i < length && str.charAt(i) <= ' ') {
                i++;
            } else {
                if (i == length) {
                    return;
                }
                int i2 = i + 2;
                int i3 = i + 3;
                int iDecode = (decode(str.charAt(i)) << 18) + (decode(str.charAt(i + 1)) << 12) + (decode(str.charAt(i2)) << 6) + decode(str.charAt(i3));
                outputStream.write((iDecode >> 16) & 255);
                if (str.charAt(i2) == '=') {
                    return;
                }
                outputStream.write((iDecode >> 8) & 255);
                if (str.charAt(i3) == '=') {
                    return;
                }
                outputStream.write(iDecode & 255);
                i += 4;
            }
        }
    }
}
