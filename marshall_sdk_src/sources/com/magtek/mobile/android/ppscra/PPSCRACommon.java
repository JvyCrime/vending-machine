package com.magtek.mobile.android.ppscra;

/* JADX INFO: loaded from: classes.dex */
public class PPSCRACommon {
    public static String getHexString(byte[] bArr) {
        return getHexString(bArr, 0, " ");
    }

    public static String getHexString(byte[] bArr, int i) {
        return getHexString(bArr, i, " ");
    }

    public static String getHexString(byte[] bArr, int i, String str) {
        if (bArr == null || bArr.length <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder((bArr.length * (str != null ? 2 + str.length() : 2)) + 1);
        while (i < bArr.length) {
            try {
                sb.append(String.format("%02X", Byte.valueOf(bArr[i])));
            } catch (Exception unused) {
                sb.append("XX");
            }
            if (str != null) {
                sb.append(str);
            }
            i++;
        }
        return sb.toString();
    }

    public static String getTextString(byte[] bArr, int i) {
        if (bArr == null || bArr.length <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArr.length + 1);
        while (i < bArr.length) {
            try {
                sb.append(String.format("%c", Byte.valueOf(bArr[i])));
            } catch (Exception unused) {
                sb.append("<?>");
            }
            i++;
        }
        return sb.toString();
    }

    public static byte[] getByteArrayFromHexString(String str, String str2) {
        int length = str2 != null ? 2 + str2.length() : 2;
        if (str == null) {
            return null;
        }
        int length2 = str.length() / length;
        byte[] bArr = new byte[length2];
        char[] charArray = str.toUpperCase().toCharArray();
        for (int i = 0; i < length2; i++) {
            StringBuffer stringBuffer = new StringBuffer("");
            int i2 = i * length;
            stringBuffer.append(String.valueOf(charArray[i2]));
            stringBuffer.append(String.valueOf(charArray[i2 + 1]));
            try {
                bArr[i] = (byte) Integer.parseInt(stringBuffer.toString(), 16);
            } catch (Exception unused) {
            }
        }
        return bArr;
    }
}
