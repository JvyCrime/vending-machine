package com.magtek.mobile.android.mtusdk.common;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TLVParser {
    private static byte ConstructedFlag = 32;
    private static byte MoreTagBytesFlag1 = 31;
    private static byte MoreTagBytesFlag2 = -128;
    private static byte MultiByteLengthFlag = -128;
    private static byte OneByteLengthMask = 127;
    private static String hexDigits = "0123456789ABCDEF";

    public static String getTextString(byte[] bArr, int i) {
        return (bArr == null || bArr.length <= 0) ? "" : getTextString(bArr, i, bArr.length);
    }

    public static String getTextString(byte[] bArr, int i, int i2) {
        if (bArr == null || bArr.length <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bArr.length + 1);
        while (i < i2) {
            try {
                sb.append(String.format("%c", Byte.valueOf(bArr[i])));
            } catch (Exception unused) {
                sb.append("<?>");
            }
            i++;
        }
        return sb.toString();
    }

    public static String getHexString(byte b) {
        return getHexString(new byte[]{b}, 0, "");
    }

    public static String getHexString(byte[] bArr) {
        return getHexString(bArr, 0, "");
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
                sb.append("  ");
            }
            if (str != null) {
                sb.append(str);
            }
            i++;
        }
        return sb.toString();
    }

    public static byte[] getByteArrayFromHexString(String str) {
        return getByteArrayFromHexString(str, "");
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

    public static String getLengthHexString(int i) {
        return getHexString(getLengthByteArray(i));
    }

    public static byte[] getLengthByteArray(int i) {
        int i2 = 0;
        int i3 = 1;
        if (i < 128) {
            return new byte[]{(byte) i};
        }
        while (((double) i) / Math.pow(256.0d, i3) >= 1.0d) {
            i3++;
        }
        byte[] bArr = new byte[i3 + 1];
        bArr[0] = (byte) (i3 + 128);
        int i4 = i3;
        while (i2 < i3) {
            i4--;
            i2++;
            bArr[i2] = (byte) ((i >> (i4 * 8)) & 255);
        }
        return bArr;
    }

    public static boolean isPrimitiveTagByteArray(byte[] bArr) {
        return !isConstructedTagByteArray(bArr);
    }

    public static boolean isConstructedTagHexString(String str) {
        return isConstructedTagByteArray(getByteArrayFromHexString(str));
    }

    public static boolean isConstructedTagByteArray(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return false;
        }
        byte b = bArr[0];
        byte b2 = ConstructedFlag;
        return (b & b2) == b2;
    }

    public static TLVObject findFromListByTagHexString(List<TLVObject> list, String str) {
        return findFromListByTagByteArray(list, getByteArrayFromHexString(str));
    }

    public static TLVObject findFromListByTagByteArray(List<TLVObject> list, byte[] bArr) {
        TLVObject tLVObjectFindByTagByteArray = null;
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                TLVObject tLVObject = list.get(i);
                if (tLVObject != null && (tLVObjectFindByTagByteArray = tLVObject.findByTagByteArray(bArr)) != null) {
                    return tLVObjectFindByTagByteArray;
                }
            }
        }
        return tLVObjectFindByTagByteArray;
    }

    public static List<TLVObject> parseTLVHexString(String str) {
        return parseTLVByteArray(getByteArrayFromHexString(str));
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.List<com.magtek.mobile.android.mtusdk.common.TLVObject> parseTLVByteArray(byte[] r10) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            if (r10 == 0) goto Lae
            r1 = 0
            r2 = 0
            r3 = 1
            r6 = r1
            r4 = 0
        Lc:
            r5 = 1
        Ld:
            int r7 = r10.length
            if (r4 >= r7) goto Lae
            if (r5 == 0) goto L3f
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream
            r5.<init>()
            r6 = 1
            r7 = 0
        L19:
            if (r6 == 0) goto L39
            int r6 = r10.length
            if (r4 >= r6) goto L39
            r6 = r10[r4]
            r5.write(r6)
            int r4 = r4 + 1
            if (r7 != 0) goto L30
            byte r8 = com.magtek.mobile.android.mtusdk.common.TLVParser.MoreTagBytesFlag1
            r6 = r6 & r8
            if (r6 != r8) goto L2e
        L2c:
            r6 = 1
            goto L36
        L2e:
            r6 = 0
            goto L36
        L30:
            byte r8 = com.magtek.mobile.android.mtusdk.common.TLVParser.MoreTagBytesFlag2
            r6 = r6 & r8
            if (r6 != r8) goto L2e
            goto L2c
        L36:
            int r7 = r7 + 1
            goto L19
        L39:
            byte[] r6 = r5.toByteArray()
            r5 = 0
            goto Ld
        L3f:
            r5 = r10[r4]
            byte r7 = com.magtek.mobile.android.mtusdk.common.TLVParser.MultiByteLengthFlag
            r8 = r5 & r7
            if (r8 != r7) goto L5f
            byte r7 = com.magtek.mobile.android.mtusdk.common.TLVParser.OneByteLengthMask
            r5 = r5 & r7
            int r4 = r4 + 1
            r7 = 0
            r8 = 0
        L4e:
            if (r7 >= r5) goto L65
            int r9 = r10.length
            if (r4 >= r9) goto L65
            r9 = r10[r4]
            int r4 = r4 + 1
            int r8 = r8 << 8
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r8 = r8 + r9
            int r7 = r7 + 1
            goto L4e
        L5f:
            byte r7 = com.magtek.mobile.android.mtusdk.common.TLVParser.OneByteLengthMask
            r8 = r5 & r7
            int r4 = r4 + 1
        L65:
            if (r8 <= 0) goto L78
            int r8 = r8 + r4
            int r5 = r10.length
            if (r8 <= r5) goto L6c
            int r8 = r10.length
        L6c:
            int r8 = r8 - r4
            if (r8 <= 0) goto L75
            byte[] r5 = new byte[r8]
            java.lang.System.arraycopy(r10, r4, r5, r2, r8)
            goto L76
        L75:
            r5 = r1
        L76:
            int r4 = r4 + r8
            goto L79
        L78:
            r5 = r1
        L79:
            boolean r7 = isConstructedTagByteArray(r6)
            if (r7 == 0) goto La4
            com.magtek.mobile.android.mtusdk.common.TLVObject r7 = new com.magtek.mobile.android.mtusdk.common.TLVObject
            r7.<init>(r6)
            if (r5 == 0) goto L9f
            java.util.List r5 = parseTLVByteArray(r5)
            if (r5 == 0) goto L9f
            r8 = 0
        L8d:
            int r9 = r5.size()
            if (r8 >= r9) goto L9f
            java.lang.Object r9 = r5.get(r8)
            com.magtek.mobile.android.mtusdk.common.TLVObject r9 = (com.magtek.mobile.android.mtusdk.common.TLVObject) r9
            r7.addTLVObject(r9)
            int r8 = r8 + 1
            goto L8d
        L9f:
            r0.add(r7)
            goto Lc
        La4:
            com.magtek.mobile.android.mtusdk.common.TLVObject r7 = new com.magtek.mobile.android.mtusdk.common.TLVObject
            r7.<init>(r6, r5)
            r0.add(r7)
            goto Lc
        Lae:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.magtek.mobile.android.mtusdk.common.TLVParser.parseTLVByteArray(byte[]):java.util.List");
    }
}
