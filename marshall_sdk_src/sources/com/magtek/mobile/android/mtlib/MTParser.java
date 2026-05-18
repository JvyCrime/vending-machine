package com.magtek.mobile.android.mtlib;

import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: loaded from: classes.dex */
public class MTParser {
    public static List<HashMap<String, String>> parseTLVData(byte[] bArr, boolean z, String str) {
        int i;
        byte[] bArr2;
        byte[] bArr3 = bArr;
        ArrayList arrayList = new ArrayList();
        if (bArr3 != null && bArr3.length >= 2) {
            int length = bArr3.length;
            if (z) {
                int i2 = ((bArr3[0] & 255) << 8) + (bArr3[1] & 255);
                byte[] bArr4 = new byte[i2];
                System.arraycopy(bArr3, 2, bArr4, 0, i2);
                bArr3 = bArr4;
            }
            if (bArr3 != null) {
                byte[] bArr5 = new byte[50];
                byte[] bArr6 = null;
                int i3 = 0;
                loop0: while (true) {
                    boolean z2 = true;
                    while (i3 < bArr3.length) {
                        byte b = bArr3[i3];
                        if (z2) {
                            boolean z3 = true;
                            int i4 = 0;
                            while (z3 && i3 < bArr3.length) {
                                byte b2 = bArr3[i3];
                                i3++;
                                bArr5[i4] = b2;
                                i4++;
                                z3 = (b2 & (-128)) == -128;
                            }
                            byte[] bArr7 = new byte[i4];
                            System.arraycopy(bArr5, 0, bArr7, 0, i4);
                            bArr6 = bArr7;
                            z2 = false;
                        } else {
                            if ((b & (-128)) == -128) {
                                int i5 = b & ByteCompanionObject.MAX_VALUE;
                                i3++;
                                i = 0;
                                for (int i6 = 0; i6 < i5 && i3 < bArr3.length; i6++) {
                                    byte b3 = bArr3[i3];
                                    i3++;
                                    i = ((i & 255) << 8) + (b3 & 255);
                                }
                            } else {
                                i = b & ByteCompanionObject.MAX_VALUE;
                                i3++;
                            }
                            if (bArr6 != null) {
                                int length2 = bArr6.length;
                                byte b4 = bArr6[0];
                                boolean z4 = (b4 & 32) == 32;
                                boolean z5 = (b4 & PPSCRADeviceValues.EMV_TAG_TYPE_DRL_GROUP) == -64;
                                if (z4 || z5) {
                                    HashMap map = new HashMap();
                                    map.put("tag", getHexString(bArr6, 0, str));
                                    map.put("len", "" + i);
                                    map.put("value", "[Container]");
                                    arrayList.add(map);
                                } else {
                                    int i7 = i3 + i;
                                    int length3 = (i7 > bArr3.length ? bArr3.length : i7) - i3;
                                    if (length3 > 0) {
                                        bArr2 = new byte[length3];
                                        System.arraycopy(bArr3, i3, bArr2, 0, length3);
                                    } else {
                                        bArr2 = null;
                                    }
                                    HashMap map2 = new HashMap();
                                    map2.put("tag", getHexString(bArr6, 0, str));
                                    map2.put("len", "" + i);
                                    if (bArr2 != null) {
                                        map2.put("value", getHexString(bArr2, 0, str));
                                    } else {
                                        map2.put("value", "");
                                    }
                                    arrayList.add(map2);
                                    i3 = i7;
                                }
                            }
                        }
                    }
                    break loop0;
                }
            }
        }
        return arrayList;
    }

    public static List<HashMap<String, String>> parseEMVData(byte[] bArr, boolean z, String str) {
        int i;
        byte[] bArr2;
        byte[] bArr3 = bArr;
        ArrayList arrayList = new ArrayList();
        if (bArr3 != null && bArr3.length >= 2) {
            int length = bArr3.length;
            if (z) {
                int i2 = ((bArr3[0] & 255) << 8) + (bArr3[1] & 255);
                byte[] bArr4 = new byte[i2];
                System.arraycopy(bArr3, 2, bArr4, 0, i2);
                bArr3 = bArr4;
            }
            if (bArr3 != null) {
                byte[] bArr5 = new byte[50];
                byte[] bArr6 = null;
                int i3 = 0;
                loop0: while (true) {
                    boolean z2 = true;
                    while (i3 < bArr3.length) {
                        byte b = bArr3[i3];
                        if (z2) {
                            boolean z3 = true;
                            int i4 = 0;
                            while (z3 && i3 < bArr3.length) {
                                byte b2 = bArr3[i3];
                                i3++;
                                bArr5[i4] = b2;
                                z3 = i4 != 0 ? (b2 & (-128)) == -128 : (b2 & 31) == 31;
                                i4++;
                            }
                            byte[] bArr7 = new byte[i4];
                            System.arraycopy(bArr5, 0, bArr7, 0, i4);
                            bArr6 = bArr7;
                            z2 = false;
                        } else {
                            if ((b & (-128)) == -128) {
                                int i5 = b & ByteCompanionObject.MAX_VALUE;
                                i3++;
                                i = 0;
                                for (int i6 = 0; i6 < i5 && i3 < bArr3.length; i6++) {
                                    byte b3 = bArr3[i3];
                                    i3++;
                                    i = ((i & 255) << 8) + (b3 & 255);
                                }
                            } else {
                                i = b & ByteCompanionObject.MAX_VALUE;
                                i3++;
                            }
                            if (bArr6 != null) {
                                int length2 = bArr6.length;
                                if ((bArr6[0] & 32) == 32) {
                                    HashMap map = new HashMap();
                                    map.put("tag", getHexString(bArr6, 0, str));
                                    map.put("len", "" + i);
                                    map.put("value", "[Container]");
                                    arrayList.add(map);
                                } else {
                                    int i7 = i3 + i;
                                    int length3 = (i7 > bArr3.length ? bArr3.length : i7) - i3;
                                    if (length3 > 0) {
                                        bArr2 = new byte[length3];
                                        System.arraycopy(bArr3, i3, bArr2, 0, length3);
                                    } else {
                                        bArr2 = null;
                                    }
                                    HashMap map2 = new HashMap();
                                    map2.put("tag", getHexString(bArr6, 0, str));
                                    map2.put("len", "" + i);
                                    if (bArr2 != null) {
                                        map2.put("value", getHexString(bArr2, 0, str));
                                    } else {
                                        map2.put("value", "");
                                    }
                                    arrayList.add(map2);
                                    i3 = i7;
                                }
                            }
                        }
                    }
                    break loop0;
                }
            }
        }
        return arrayList;
    }

    public static String getTagValue(List<HashMap<String, String>> list, String str) {
        ListIterator<HashMap<String, String>> listIterator = list.listIterator();
        String str2 = "";
        while (listIterator.hasNext()) {
            HashMap<String, String> next = listIterator.next();
            if (next.get("tag").equalsIgnoreCase(str)) {
                str2 = next.get("value");
            }
        }
        return str2;
    }

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
}
