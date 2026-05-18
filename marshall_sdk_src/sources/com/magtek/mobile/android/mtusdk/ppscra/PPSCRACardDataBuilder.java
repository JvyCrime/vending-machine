package com.magtek.mobile.android.mtusdk.ppscra;

import com.magtek.mobile.android.mtusdk.common.TLVParser;
import com.magtek.mobile.android.ppscra.CardDataInfo;

/* JADX INFO: loaded from: classes.dex */
public class PPSCRACardDataBuilder {
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

    public static String buildTLVPayload(byte[] bArr, CardDataInfo cardDataInfo) {
        String strBuildTLV = buildTLV(TAG_DEVICE_SN, TLVParser.getHexString(bArr));
        String strBuildTLV2 = buildTLV(TAG_MSR_DATA, buildTLV(TAG_TRACK1, TLVParser.getHexString(cardDataInfo.getTrack1Masked().getBytes())) + buildTLV(TAG_TRACK2, TLVParser.getHexString(cardDataInfo.getTrack2Masked().getBytes())) + buildTLV(TAG_TRACK3, TLVParser.getHexString(cardDataInfo.getTrack3Masked().getBytes())) + buildTLV(TAG_TRACK1_STATUS, TLVParser.getHexString(cardDataInfo.getTrack1DecodeStatus())) + buildTLV(TAG_TRACK2_STATUS, TLVParser.getHexString(cardDataInfo.getTrack2DecodeStatus())) + buildTLV(TAG_TRACK3_STATUS, TLVParser.getHexString(cardDataInfo.getTrack3DecodeStatus())) + buildTLV(TAG_ENC_TRACK1, cardDataInfo.getTrack1()) + buildTLV(TAG_ENC_TRACK2, cardDataInfo.getTrack2()) + buildTLV(TAG_ENC_TRACK3, cardDataInfo.getTrack3()) + buildTLV(TAG_ENC_MP, cardDataInfo.getMagnePrint()) + buildTLV(TAG_ENC_MP_STATUS, TLVParser.getHexString(cardDataInfo.MagnePrintStatus)) + buildTLV(TAG_KSN, TLVParser.getHexString(cardDataInfo.KSN)));
        return buildTLV(TAG_GENERIC_DATA, strBuildTLV + strBuildTLV2);
    }
}
