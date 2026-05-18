package com.magtek.mobile.android.mtusdk.scra;

import com.magtek.mobile.android.mtlib.IMTCardData;
import com.magtek.mobile.android.mtusdk.common.TLVParser;

/* JADX INFO: loaded from: classes.dex */
public class SCRACardDataBuilder {
    protected static String TAG_DEVICE_SN = "DFDF25";
    protected static String TAG_ENC_MP = "DFDF3C";
    protected static String TAG_ENC_MP_STATUS = "DFDF43";
    protected static String TAG_ENC_TRACK1 = "DFDF37";
    protected static String TAG_ENC_TRACK2 = "DFDF39";
    protected static String TAG_ENC_TRACK3 = "DFDF3B";
    protected static String TAG_GENERIC_DATA = "FA";
    protected static String TAG_KSN = "DFDF50";
    protected static String TAG_MSR_DATA = "F4";
    protected static String TAG_TRACK1 = "DFDF31";
    protected static String TAG_TRACK1_STATUS = "DFDF30";
    protected static String TAG_TRACK2 = "DFDF33";
    protected static String TAG_TRACK2_STATUS = "DFDF32";
    protected static String TAG_TRACK3 = "DFDF35";
    protected static String TAG_TRACK3_STATUS = "DFDF34";

    protected static String buildTLV(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        String hexString = TLVParser.getHexString(TLVParser.getLengthByteArray(str2.length() / 2));
        sb.append(str);
        sb.append(hexString);
        sb.append(str2);
        return sb.toString();
    }

    public static String buildTLVPayload(IMTCardData iMTCardData) {
        String strBuildTLV = buildTLV(TAG_DEVICE_SN, TLVParser.getHexString(iMTCardData.getDeviceSerial().getBytes()));
        String hexString = TLVParser.getHexString(iMTCardData.getTrack1Masked().getBytes());
        String hexString2 = TLVParser.getHexString(iMTCardData.getTrack2Masked().getBytes());
        String hexString3 = TLVParser.getHexString(iMTCardData.getTrack3Masked().getBytes());
        String trackDecodeStatus = iMTCardData.getTrackDecodeStatus();
        String strBuildTLV2 = buildTLV(TAG_MSR_DATA, buildTLV(TAG_TRACK1, hexString) + buildTLV(TAG_TRACK2, hexString2) + buildTLV(TAG_TRACK3, hexString3) + buildTLV(TAG_TRACK1_STATUS, trackDecodeStatus.substring(0, 2)) + buildTLV(TAG_TRACK2_STATUS, trackDecodeStatus.substring(2, 4)) + buildTLV(TAG_TRACK3_STATUS, trackDecodeStatus.substring(4, 6)) + buildTLV(TAG_ENC_TRACK1, iMTCardData.getTrack1()) + buildTLV(TAG_ENC_TRACK2, iMTCardData.getTrack2()) + buildTLV(TAG_ENC_TRACK3, iMTCardData.getTrack3()) + buildTLV(TAG_ENC_MP, iMTCardData.getMagnePrint()) + buildTLV(TAG_ENC_MP_STATUS, iMTCardData.getMagnePrintStatus()) + buildTLV(TAG_KSN, iMTCardData.getKSN()));
        return buildTLV(TAG_GENERIC_DATA, strBuildTLV + strBuildTLV2);
    }
}
