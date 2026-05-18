package org.kobjects.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes2.dex */
public final class Util {
    public static OutputStream streamcopy(InputStream inputStream, OutputStream outputStream) throws IOException {
        int i = Runtime.getRuntime().freeMemory() >= 1048576 ? 16384 : 128;
        byte[] bArr = new byte[i];
        while (true) {
            int i2 = inputStream.read(bArr, 0, i);
            if (i2 != -1) {
                outputStream.write(bArr, 0, i2);
            } else {
                inputStream.close();
                return outputStream;
            }
        }
    }

    public static int indexOf(Object[] objArr, Object obj) {
        for (int i = 0; i < objArr.length; i++) {
            if (objArr[i].equals(obj)) {
                return i;
            }
        }
        return -1;
    }

    public static String buildUrl(String str, String str2) {
        int iIndexOf = str2.indexOf(58);
        String str3 = "file:///";
        if (str2.startsWith("/") || iIndexOf == 1) {
            return "file:///" + str2;
        }
        if (iIndexOf > 2 && iIndexOf < 6) {
            return str2;
        }
        if (str != null) {
            if (str.indexOf(58) == -1) {
                str = "file:///" + str;
            }
            if (str.endsWith("/")) {
                str3 = str;
            } else {
                str3 = str + "/";
            }
        }
        return str3 + str2;
    }

    public static void sort(Object[] objArr, int i, int i2) {
        int i3;
        int i4 = i2 - i;
        if (i4 <= 2) {
            if (i4 == 2) {
                int i5 = i + 1;
                if (objArr[i].toString().compareTo(objArr[i5].toString()) > 0) {
                    Object obj = objArr[i];
                    objArr[i] = objArr[i5];
                    objArr[i5] = obj;
                    return;
                }
                return;
            }
            return;
        }
        if (i4 == 3) {
            int i6 = i + 2;
            sort(objArr, i, i6);
            sort(objArr, i + 1, i + 3);
            sort(objArr, i, i6);
            return;
        }
        int i7 = (i + i2) / 2;
        sort(objArr, i, i7);
        sort(objArr, i7, i2);
        Object[] objArr2 = new Object[i4];
        int i8 = i;
        int i9 = i7;
        for (int i10 = 0; i10 < i4; i10++) {
            if (i8 == i7) {
                i3 = i9 + 1;
                objArr2[i10] = objArr[i9];
            } else if (i9 == i2 || objArr[i8].toString().compareTo(objArr[i9].toString()) < 0) {
                objArr2[i10] = objArr[i8];
                i8++;
            } else {
                i3 = i9 + 1;
                objArr2[i10] = objArr[i9];
            }
            i9 = i3;
        }
        System.arraycopy(objArr2, 0, objArr, i, i4);
    }
}
