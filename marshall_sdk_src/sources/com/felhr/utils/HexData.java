package com.felhr.utils;

import com.bitmick.marshall.vmc.marshall_t;

/* JADX INFO: loaded from: classes.dex */
public class HexData {
    private static final String HEXES = "0123456789ABCDEF";
    private static final String HEX_INDICATOR = "0x";
    private static final String SPACE = " ";

    private HexData() {
    }

    public static String hexToString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i = 0; i <= bArr.length - 1; i++) {
            byte b = bArr[i];
            sb.append(HEX_INDICATOR);
            sb.append(HEXES.charAt((b & marshall_t.marshall_packet_option_rfu_mask) >> 4));
            sb.append(HEXES.charAt(b & 15));
            sb.append(SPACE);
        }
        return sb.toString();
    }

    public static byte[] stringTobytes(String str) {
        String strReplaceAll = str.trim().replaceAll(HEX_INDICATOR, "").replaceAll("\\s+", "");
        byte[] bArr = new byte[strReplaceAll.length() / 2];
        int i = 0;
        int i2 = 0;
        while (i <= strReplaceAll.length() - 1) {
            int i3 = i + 2;
            bArr[i2] = (byte) Integer.parseInt(strReplaceAll.substring(i, i3), 16);
            i2++;
            i = i3;
        }
        return bArr;
    }

    public static String hex4digits(String str) {
        if (str.length() == 1) {
            return "000" + str;
        }
        if (str.length() == 2) {
            return "00" + str;
        }
        if (str.length() != 3) {
            return str;
        }
        return "0" + str;
    }
}
