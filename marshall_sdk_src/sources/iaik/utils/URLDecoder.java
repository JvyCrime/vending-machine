package iaik.utils;

import com.magtek.mobile.android.mtusdk.cms.DataTypeTag;
import java.io.IOException;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class URLDecoder {
    public static String decode(String str) throws IOException {
        Objects.requireNonNull(str, "Parameter \"url\" must not be null.");
        try {
            return decodeUTF8(str);
        } catch (IOException unused) {
            return decodeISO8859_1(str);
        }
    }

    public static String decodeISO8859_1(String str) throws IOException {
        StringBuffer stringBuffer = new StringBuffer(str.length());
        int i = 0;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt != '%') {
                stringBuffer.append(cCharAt);
            } else {
                try {
                    stringBuffer.append((char) new int[]{Integer.parseInt(str.substring(i + 1, i + 3), 16), 0, 0}[0]);
                    i += 2;
                } catch (NumberFormatException unused) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("Error while ISO8859-1 decoding url. Invalid escape sequence (%");
                    stringBuffer2.append(str.substring(i + 1, i + 3));
                    stringBuffer2.append(") in URL.");
                    throw new IOException(stringBuffer2.toString());
                }
            }
            i++;
        }
        return stringBuffer.toString();
    }

    public static String decodeUTF8(String str) throws IOException {
        Objects.requireNonNull(str, "Parameter \"url\" must not be null.");
        StringBuffer stringBuffer = new StringBuffer(str.length());
        int i = 0;
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (cCharAt != '%') {
                stringBuffer.append(cCharAt);
            } else {
                try {
                    int[] iArr = new int[3];
                    iArr[0] = Integer.parseInt(str.substring(i + 1, i + 3), 16);
                    if ((iArr[0] & DataTypeTag.CONSTRUCTIVE) == 224) {
                        iArr[1] = Integer.parseInt(str.substring(i + 4, i + 6), 16);
                        iArr[2] = Integer.parseInt(str.substring(i + 7, i + 9), 16);
                        stringBuffer.append((char) (((iArr[0] & 15) << 12) + ((iArr[1] & 63) << 6) + (iArr[2] & 63)));
                        i += 8;
                    } else if ((iArr[0] & 192) == 192) {
                        iArr[1] = Integer.parseInt(str.substring(i + 4, i + 6), 16);
                        stringBuffer.append((char) (((iArr[0] & 31) << 6) + (iArr[1] & 63)));
                        i += 5;
                    } else {
                        if ((iArr[0] & 128) != 0) {
                            throw new IOException("Invlaid URI.");
                        }
                        stringBuffer.append((char) iArr[0]);
                        i += 2;
                    }
                } catch (NumberFormatException unused) {
                    throw new IOException("Invalid UTF-8 encoding.");
                }
            }
            i++;
        }
        return stringBuffer.toString();
    }
}
