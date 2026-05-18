package com.magtek.mobile.android.mtlib;

import android.util.Log;
import org.slf4j.Marker;

/* JADX INFO: loaded from: classes.dex */
public class MTASCCardData implements IMTCardData, IMTCardDataHandler {
    public static final int CARDDATA_DEVICE_ENCRYPTION_STATUS = 1;
    public static final int CARDDATA_DEVICE_KSN = 9;
    public static final int CARDDATA_DEVICE_SERIAL_NUMBER = 7;
    public static final int CARDDATA_ENCRYPTED_MAGNEPRINT = 6;
    public static final int CARDDATA_ENCRYPTED_SESSIONCOUNTER = 9;
    public static final int CARDDATA_ENCRYPTED_SESSIONID = 8;
    public static final int CARDDATA_ENCRYPTED_TRACK1 = 2;
    public static final int CARDDATA_ENCRYPTED_TRACK2 = 3;
    public static final int CARDDATA_ENCRYPTED_TRACK3 = 4;
    public static int CARDDATA_FORMATINDEX = 0;
    public static final int CARDDATA_HASHOFPAN = 9;
    public static final int CARDDATA_MAGNEPRINT_STATUS = 5;
    public static final int CARDDATA_MASKEDTRACKS = 0;
    public static final int CARDDATA_MIN_FIELDCOUNT = 13;
    private static final String TAG = "MTASCCardData";
    private static String m_formatCode = "";
    private byte BYTE_EOF = 13;
    private String[] m_ascArray;
    private byte[] m_ascData;
    private String m_ascString;
    private String m_configuration;
    private boolean m_eofFound;
    private String[] m_maskedArray;
    private String m_maskedString;
    private int m_threshold;
    public static final int CARDDATA_CRC = 0 + 10;
    public static final int CARDDATA_FORMATCODE = 0 + 11;

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapMSR() {
        return "01";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapMagStripeEncryption() {
        return "0001";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapMagnePrint() {
        return "01";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapMagnePrintEncryption() {
        return "01";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapMagneSafe20Encryption() {
        return "00";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapTracks() {
        return "07";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCardStatus() {
        return "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getDeviceConfig(String str) {
        return "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getDeviceName() {
        return "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getFirmware() {
        return "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getHashCode() {
        return "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getMagTekDeviceSerial() {
        return "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getResponseType() {
        return "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTLVVersion() {
        return "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTagValue(String str, String str2) {
        return "";
    }

    public MTASCCardData() {
        clearData();
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardDataHandler
    public void clearData() {
        this.m_ascData = null;
        this.m_ascString = "";
        this.m_ascArray = null;
        this.m_maskedString = "";
        this.m_maskedArray = null;
        this.m_eofFound = false;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardDataHandler
    public void setConfiguration(String str) {
        this.m_configuration = str;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardDataHandler
    public void setDataThreshold(int i) {
        this.m_threshold = i;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardDataHandler
    public boolean isDataReady() {
        return this.m_eofFound;
    }

    private String getFieldValue(int i) {
        try {
            String[] strArr = this.m_ascArray;
            return strArr.length >= 13 ? strArr[i] : "";
        } catch (Exception unused) {
            return "";
        }
    }

    private String getFieldValueFromMaskedArray(String str) {
        int i = 0;
        while (true) {
            try {
                String[] strArr = this.m_maskedArray;
                if (i >= strArr.length) {
                    return "";
                }
                String str2 = strArr[i];
                if (str2.startsWith(str)) {
                    return str2;
                }
                i++;
            } catch (Exception unused) {
                return "";
            }
        }
    }

    private void parseASCData() {
        byte[] bArr = this.m_ascData;
        if (bArr != null && bArr.length >= 1) {
            byte[] bArr2 = this.m_ascData;
            String str = new String(bArr2, 0, bArr2.length);
            this.m_ascString = str;
            this.m_ascArray = str.split("\\|");
            String str2 = TAG;
            Log.i(str2, "m_ascString=" + this.m_ascString);
            Log.i(str2, "m_ascArray.length=" + this.m_ascArray.length);
            String[] strArr = this.m_ascArray;
            if (strArr.length >= 13) {
                m_formatCode = strArr[strArr.length - 1].replace("\r", "");
                String str3 = this.m_ascArray[0];
                this.m_maskedString = str3;
                if (str3.length() > 0) {
                    this.m_maskedArray = this.m_maskedString.split("\\?");
                    int i = 0;
                    while (true) {
                        String[] strArr2 = this.m_maskedArray;
                        if (i >= strArr2.length) {
                            break;
                        }
                        if (strArr2[i].length() > 0) {
                            StringBuilder sb = new StringBuilder();
                            String[] strArr3 = this.m_maskedArray;
                            sb.append(strArr3[i]);
                            sb.append("?");
                            strArr3[i] = sb.toString();
                        }
                        i++;
                    }
                    Log.i(TAG, "m_maskedArray.length=" + this.m_maskedArray.length);
                }
                if (m_formatCode.equalsIgnoreCase("0001")) {
                    CARDDATA_FORMATINDEX = 1;
                    return;
                }
                if (m_formatCode.equalsIgnoreCase("0008")) {
                    CARDDATA_FORMATINDEX = 1;
                } else if (m_formatCode.equalsIgnoreCase("0009")) {
                    CARDDATA_FORMATINDEX = 2;
                } else {
                    CARDDATA_FORMATINDEX = 0;
                }
            }
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardDataHandler
    public void setData(byte[] bArr) {
        int length;
        this.m_ascData = null;
        this.m_eofFound = true;
        if (bArr == null || (length = bArr.length) <= 0) {
            return;
        }
        byte[] bArr2 = new byte[length];
        this.m_ascData = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, length);
        parseASCData();
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardDataHandler
    public void handleData(byte[] bArr) {
        byte[] bArr2 = this.m_ascData;
        if (bArr2 == null) {
            this.m_eofFound = false;
            this.m_ascData = bArr;
        } else {
            byte[] bArr3 = new byte[bArr2.length + bArr.length];
            System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
            System.arraycopy(bArr, 0, bArr3, this.m_ascData.length, bArr.length);
            this.m_ascData = bArr3;
        }
        if (bArr == null || this.m_eofFound) {
            return;
        }
        for (byte b : bArr) {
            if (b == this.BYTE_EOF) {
                parseASCData();
                this.m_eofFound = true;
            }
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public byte[] getData() {
        return this.m_ascData;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public void clearBuffers() {
        clearData();
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getMaskedTracks() {
        return getFieldValue(0);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack1() {
        return getFieldValue(2);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack2() {
        return getFieldValue(3);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack3() {
        return getFieldValue(4);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack1Masked() {
        return getFieldValueFromMaskedArray("%");
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack2Masked() {
        return getFieldValueFromMaskedArray(";");
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack3Masked() {
        return getFieldValueFromMaskedArray(Marker.ANY_NON_NULL_MARKER);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getMagnePrint() {
        return getFieldValue(6);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getMagnePrintStatus() {
        return getFieldValue(5);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getDeviceSerial() {
        return getFieldValue(7);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getSessionID() {
        return getFieldValue(8);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getKSN() {
        return getFieldValue(9);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public long getBatteryLevel() {
        return MTDeviceConstants.BATTERY_LEVEL_NA;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public long getSwipeCount() {
        return MTDeviceConstants.SWIPE_COUNT_NA;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public long getCardDataCRC() {
        try {
            String[] strArr = this.m_ascArray;
            if (strArr.length >= 13) {
                return Long.valueOf(strArr[CARDDATA_CRC], 16).longValue();
            }
            return 0L;
        } catch (NumberFormatException | Exception unused) {
            return 0L;
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCardExpDate() {
        String additionalInfo = getAdditionalInfo();
        return additionalInfo.length() > 6 ? additionalInfo.substring(0, 4) : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCardIIN() {
        String maskedPAN = getMaskedPAN();
        return maskedPAN.length() >= 6 ? maskedPAN.substring(0, 6) : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCardLast4() {
        String maskedPAN = getMaskedPAN();
        return maskedPAN.length() >= 4 ? maskedPAN.substring(maskedPAN.length() - 4) : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCardName() {
        return getNameFromMaskedTrack1();
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCardPAN() {
        return getMaskedPAN();
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public int getCardPANLength() {
        return getMaskedPAN().length();
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCardServiceCode() {
        String additionalInfo = getAdditionalInfo();
        return additionalInfo.length() > 6 ? additionalInfo.substring(4) : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public int getDataFieldCount() {
        try {
            String[] strArr = this.m_ascArray;
            if (strArr == null) {
                return 0;
            }
            return strArr.length;
        } catch (Exception unused) {
            return 0;
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getEncryptionStatus() {
        return getFieldValue(1);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrackDecodeStatus() {
        String str;
        String str2;
        try {
            String track1 = getTrack1();
            String track2 = getTrack2();
            String track3 = getTrack3();
            String str3 = "01";
            if (track1.equalsIgnoreCase("%E?")) {
                str = "01";
            } else {
                track1.equalsIgnoreCase("");
                str = "00";
            }
            if (track2.equalsIgnoreCase(";E?")) {
                str2 = "01";
            } else {
                track2.equalsIgnoreCase("");
                str2 = "00";
            }
            if (!track3.equalsIgnoreCase("+E?") && !track3.equalsIgnoreCase(";E?")) {
                track3.equalsIgnoreCase("");
                str3 = "00";
            }
            return str + str2 + str3;
        } catch (Exception unused) {
            return "";
        }
    }

    private String getMaskedPAN() {
        String pANFromMaskedTrack2 = getPANFromMaskedTrack2();
        return pANFromMaskedTrack2.trim().length() == 0 ? getPANFromMaskedTrack1() : pANFromMaskedTrack2;
    }

    private String getNameFromMaskedTrack1() {
        try {
            String track1Masked = getTrack1Masked();
            if (track1Masked.length() <= 0) {
                return "";
            }
            int iIndexOf = track1Masked.indexOf("^");
            int iIndexOf2 = iIndexOf != -1 ? track1Masked.indexOf("^", iIndexOf + 1) : -1;
            return (iIndexOf == -1 || iIndexOf2 == -1) ? "" : track1Masked.substring(iIndexOf + 1, iIndexOf2);
        } catch (Exception unused) {
            return "";
        }
    }

    private String getPANFromMaskedTrack1() {
        try {
            String track1Masked = getTrack1Masked();
            if (track1Masked.length() <= 0) {
                return "";
            }
            int iIndexOf = track1Masked.indexOf("%");
            int iIndexOf2 = track1Masked.indexOf("^");
            return (iIndexOf == -1 || iIndexOf2 == -1) ? "" : track1Masked.substring(iIndexOf + 2, iIndexOf2);
        } catch (Exception unused) {
            return "";
        }
    }

    private String getPANFromMaskedTrack2() {
        try {
            String track2Masked = getTrack2Masked();
            if (track2Masked.length() <= 0) {
                return "";
            }
            int iIndexOf = track2Masked.indexOf(";");
            int iIndexOf2 = track2Masked.indexOf("=");
            return (iIndexOf == -1 || iIndexOf2 == -1) ? "" : track2Masked.substring(iIndexOf + 1, iIndexOf2);
        } catch (Exception unused) {
            return "";
        }
    }

    private String getAdditionalInfo() {
        String additionalInfoFromMaskedTrack2 = getAdditionalInfoFromMaskedTrack2();
        return additionalInfoFromMaskedTrack2.trim().length() == 0 ? getAdditionalInfoFromMaskedTrack1() : additionalInfoFromMaskedTrack2;
    }

    private String getAdditionalInfoFromMaskedTrack1() {
        String[] strArrSplit;
        try {
            String track1Masked = getTrack1Masked();
            return (track1Masked.length() <= 0 || (strArrSplit = track1Masked.split("\\^")) == null || strArrSplit.length <= 2 || strArrSplit[1].length() <= 7) ? "" : strArrSplit[2].substring(0, 7);
        } catch (Exception unused) {
            return "";
        }
    }

    private String getAdditionalInfoFromMaskedTrack2() {
        int iIndexOf;
        try {
            String track2Masked = getTrack2Masked();
            return (track2Masked.length() <= 0 || (iIndexOf = track2Masked.indexOf("=")) == -1) ? "" : track2Masked.substring(iIndexOf + 1, iIndexOf + 8);
        } catch (Exception unused) {
            return "";
        }
    }

    private String DFM_vFixCheckDigit(byte[] bArr, int i, byte b) {
        int i2;
        byte b2 = (byte) i;
        int i3 = b2 - 1;
        byte b3 = (byte) (bArr[i3] - 48);
        int i4 = b2 - 2;
        while (true) {
            byte b4 = (byte) i4;
            byte b5 = (byte) (((byte) (bArr[b4] - 48)) * 2);
            if (b5 > 9) {
                b3 = (byte) (b3 + 1);
                b5 = (byte) (b5 - 10);
            }
            b3 = (byte) (b3 + b5);
            if (b4 == 0) {
                break;
            }
            byte b6 = (byte) (b4 - 1);
            b3 = (byte) (b3 + (bArr[b6] - 48));
            if (b6 == 0) {
                break;
            }
            i4 = b6 - 1;
        }
        byte b7 = (byte) (b3 % 10);
        if (b7 != 0) {
            b7 = (byte) (10 - b7);
            if ((i3 - b) % 2 > 0) {
                if (b7 % 2 > 0) {
                    i2 = (b7 + 9) / 2;
                } else {
                    i2 = b7 / 2;
                }
                b7 = (byte) i2;
            }
        }
        if (b7 != 0) {
            bArr[b] = (byte) (b7 + 48);
        }
        return MTParser.getTextString(bArr, 0, bArr.length);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTLVPayload() {
        return MTPayloadBuilder.buildTLVPayload(this);
    }
}
