package com.magtek.mobile.android.mtlib;

/* JADX INFO: loaded from: classes.dex */
class MTPayloadBuilder {
    protected static String TAG_DEVICE_SN = "DFDF25";
    protected static String TAG_ENC_MP = "DFDF3C";
    protected static String TAG_ENC_MP_STATUS = "DFDF43";
    protected static String TAG_ENC_TRACK1 = "DFDF37";
    protected static String TAG_ENC_TRACK2 = "DFDF39";
    protected static String TAG_ENC_TRACK3 = "DFDF3B";
    protected static String TAG_GENERIC_DATA = "FA";
    protected static String TAG_KSN = "DFDF50";
    protected static String TAG_MSR_DATA = "F4";

    MTPayloadBuilder() {
    }

    protected static String buildTLV(String str, String str2) {
        byte[] bArr;
        StringBuilder sb = new StringBuilder();
        int length = str2.length() / 2;
        int i = 0;
        int i2 = 1;
        if (length < 128) {
            bArr = new byte[]{(byte) length};
        } else {
            while (((double) length) / Math.pow(256.0d, i2) >= 1.0d) {
                i2++;
            }
            bArr = new byte[i2 + 1];
            bArr[0] = (byte) (i2 + 128);
            int i3 = i2;
            while (i < i2) {
                i3--;
                i++;
                bArr[i] = (byte) ((length >> (i3 * 8)) & 255);
            }
        }
        String hexString = MTParser.getHexString(bArr);
        sb.append(str);
        sb.append(hexString);
        sb.append(str2);
        return sb.toString();
    }

    public static String buildTLVPayload(IMTCardData iMTCardData) {
        String strBuildTLV = buildTLV(TAG_DEVICE_SN, MTParser.getHexString(iMTCardData.getDeviceSerial().getBytes()));
        StringBuilder sb = new StringBuilder();
        String track1 = iMTCardData.getTrack1();
        String track2 = iMTCardData.getTrack2();
        String track3 = iMTCardData.getTrack3();
        String magnePrint = iMTCardData.getMagnePrint();
        String magnePrintStatus = iMTCardData.getMagnePrintStatus();
        String ksn = iMTCardData.getKSN();
        sb.append(buildTLV(TAG_ENC_TRACK1, track1));
        sb.append(buildTLV(TAG_ENC_TRACK2, track2));
        sb.append(buildTLV(TAG_ENC_TRACK3, track3));
        sb.append(buildTLV(TAG_ENC_MP, magnePrint));
        sb.append(buildTLV(TAG_ENC_MP_STATUS, magnePrintStatus));
        sb.append(buildTLV(TAG_KSN, ksn));
        String strBuildTLV2 = buildTLV(TAG_MSR_DATA, sb.toString());
        return buildTLV(TAG_GENERIC_DATA, strBuildTLV + strBuildTLV2);
    }
}
