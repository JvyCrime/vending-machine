package com.digitalmediavending.wallcoilmachine.utils;

/* JADX INFO: loaded from: classes.dex */
public class Constants {
    public static final int DATA_SIZE_2_BITS = 2;
    public static final int DATA_SIZE_3_BITS = 3;
    public static final int FUNCTION_ELECTROMAGNETIC_DOOR_BIT = 2;
    public static final int FUNCTION_HEARTBEAT_BIT = 0;
    public static final int FUNCTION_RESET_TRAY_BIT = 4;
    public static final int FUNCTION_SCAN_TRAY_BIT = 3;
    public static final int FUNCTION_VENDING_BIT = 1;
    public static final int HEADER_BIT = 170;

    public static String intToBin(int i) {
        String binaryString = Integer.toBinaryString(i);
        int length = 8 - binaryString.length();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < length; i2++) {
            sb.append("0");
        }
        return sb.toString().trim() + binaryString;
    }

    public static int BinToDec(String str) {
        int i = 1;
        int i2 = 0;
        for (int length = str.length() - 1; length >= 0; length--) {
            if (str.toCharArray()[length] == '1') {
                i2 += i;
            }
            i *= 2;
        }
        return i2;
    }

    public static String DecToBin(int i, boolean z) {
        char[] cArr = new char[16];
        for (int i2 = 0; i2 < 16; i2++) {
            int i3 = i % 2;
            if (i3 == 0) {
                cArr[15 - i2] = '0';
            } else if (i3 == 1) {
                cArr[15 - i2] = '1';
            }
            i /= 2;
        }
        if (z) {
            char[] cArr2 = new char[8];
            System.arraycopy(cArr, 8, cArr2, 0, 8);
            return new String(cArr2);
        }
        return new String(cArr);
    }
}
