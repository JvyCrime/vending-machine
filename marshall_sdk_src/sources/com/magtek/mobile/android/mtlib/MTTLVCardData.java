package com.magtek.mobile.android.mtlib;

import android.util.Log;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MTTLVCardData implements IMTCardData, IMTCardDataHandler {
    private static final String CONFIG_PARAM_PAN_MOD10_CHECKDIGIT = "PAN_MOD10_CHECKDIGIT";
    private static int MAX_TK1_LENGTH = 77;
    private static int MAX_TK2_LENGTH = 38;
    private static final String TAG = "MTTLVCardData";
    private String m_AdditionalInfo;
    private String[] m_CardDataArray;
    private String m_DeviceConfig;
    private String m_DeviceInfo;
    private String m_DeviceStatus;
    private String m_FormatCode;
    private String m_MSLocalMerchantData;
    private String m_MSSecureData;
    private String m_MSSwipeStatus;
    private String m_MaskedTrackArray;
    private String m_MaskedTracks;
    private String m_PAN;
    private String m_ResponseData;
    private String m_ResponseType;
    private String m_TLVVersion;
    private String m_configuration = "";
    List<HashMap<String, String>> m_parsedTLVData;
    private int m_threshold;
    private byte[] m_tlvData;

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public long getCardDataCRC() {
        return 0L;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public int getDataFieldCount() {
        return 0;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardDataHandler
    public void clearData() {
        this.m_TLVVersion = "";
        this.m_MaskedTracks = "";
        this.m_FormatCode = "";
        this.m_ResponseData = "";
        this.m_ResponseType = "";
        this.m_CardDataArray = null;
        this.m_MaskedTracks = null;
        this.m_MSLocalMerchantData = "";
        this.m_MSSwipeStatus = "";
        this.m_MSSecureData = "";
        this.m_DeviceInfo = "";
        this.m_DeviceStatus = "";
        this.m_DeviceConfig = "";
        this.m_AdditionalInfo = "";
        this.m_PAN = "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardDataHandler
    public void setConfiguration(String str) {
        this.m_configuration = str;
        Log.i(TAG, "setConfiguration: " + str);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardDataHandler
    public void setDataThreshold(int i) {
        this.m_threshold = i;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardDataHandler
    public boolean isDataReady() {
        byte[] bArr = this.m_tlvData;
        return bArr != null && bArr.length > 0;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardDataHandler
    public void setData(byte[] bArr) {
        clearBuffers();
        this.m_tlvData = bArr;
        this.m_ResponseData = MTParser.getHexString(bArr);
        this.m_parsedTLVData = MTParser.parseTLVData(bArr, false, "");
        String responseType = getResponseType();
        if (responseType.equalsIgnoreCase(MTSCRAConstants.DEVICE_TAG_MSR_SWIPEDATA) || responseType.equalsIgnoreCase(MTSCRAConstants.DEVICE_TAG_V5_MSR_SWIPEDATA) || responseType.equalsIgnoreCase(MTSCRAConstants.DEVICE_TAG_DEVICE_INFORMATION)) {
            this.m_TLVVersion = getTagValue(MTSCRAConstants.DEVICE_TAG_TLVVERSION);
            this.m_MSSwipeStatus = getTagValue(MTSCRAConstants.DEVICE_TAG_MSR_SWIPESTATUS);
            this.m_MSLocalMerchantData = getTagValue(MTSCRAConstants.DEVICE_TAG_MSR_LOCALMERCHANTDATA);
            this.m_MSSecureData = getTagValue(MTSCRAConstants.DEVICE_TAG_MSR_SECUREDATA);
            this.m_DeviceInfo = getTagValue(MTSCRAConstants.DEVICE_TAG_DEVICE_INFORMATION);
            if (this.m_MSLocalMerchantData.length() > 0) {
                if (this.m_TLVVersion.length() > 0 && Integer.parseInt(this.m_TLVVersion, 16) >= 3) {
                    this.m_PAN = getMaskedPAN();
                    return;
                }
                String cardIIN = getCardIIN();
                String cardLast4 = getCardLast4();
                int cardPANLength = getCardPANLength();
                if (cardIIN.length() <= 0 || cardLast4.length() <= 0 || cardPANLength <= 0) {
                    return;
                }
                this.m_AdditionalInfo = getCardExpDate() + getCardServiceCode();
                this.m_PAN = cardIIN;
                int cardPANLength2 = getCardPANLength();
                for (int i = 0; i < (cardPANLength2 - cardIIN.length()) - cardLast4.length(); i++) {
                    this.m_PAN += "0";
                }
                this.m_PAN += cardLast4;
                if (cardPANLength2 > cardLast4.length() + cardIIN.length()) {
                    String str = TAG;
                    Log.i(str, "Check Config Param: DFM_vFixCheckDigit");
                    if (!getConfigurationParam(this.m_configuration, CONFIG_PARAM_PAN_MOD10_CHECKDIGIT).equalsIgnoreCase("FALSE")) {
                        int length = cardIIN.length() + ((cardPANLength2 - (cardIIN.length() + cardLast4.length())) / 2);
                        MTParser.getByteArrayFromHexString(this.m_PAN);
                        Log.i(str, "Before DFM_vFixCheckDigit, PAN=" + this.m_PAN);
                        this.m_PAN = DFM_vFixCheckDigit(this.m_PAN.getBytes(), cardPANLength2, (byte) length);
                        Log.i(str, "After DFM_vFixCheckDigit, PAN=" + this.m_PAN);
                        return;
                    }
                    Log.i(str, "Skip DFM_vFixCheckDigit");
                    return;
                }
                return;
            }
            return;
        }
        if (responseType.equalsIgnoreCase(MTSCRAConstants.DEVICE_TAG_DEVICETOHOST_RESPONSE)) {
            this.m_DeviceInfo = getTagValue(MTSCRAConstants.DEVICE_TAG_DEVICE_INFORMATION);
            this.m_TLVVersion = getTagValue(MTSCRAConstants.DEVICE_TAG_TLVVERSION);
            this.m_DeviceStatus = getTagValue(MTSCRAConstants.DEVICE_TAG_DEVICE_STATUS_L1);
            this.m_DeviceConfig = getTagValue(MTSCRAConstants.DEVICE_TAG_DEVICE_CONFIGURATION);
            this.m_MSSecureData = getTagValue(MTSCRAConstants.DEVICE_TAG_MSR_SECUREDATA);
            return;
        }
        if (responseType.equalsIgnoreCase(MTSCRAConstants.DEVICE_TAG_UNRECOGNIZED)) {
            this.m_DeviceInfo = getTagValue(MTSCRAConstants.DEVICE_TAG_DEVICE_INFORMATION);
            this.m_TLVVersion = getTagValue(MTSCRAConstants.DEVICE_TAG_TLVVERSION);
            this.m_DeviceStatus = getTagValue(MTSCRAConstants.DEVICE_TAG_DEVICE_STATUS_L1);
            this.m_DeviceConfig = getTagValue(MTSCRAConstants.DEVICE_TAG_DEVICE_CONFIGURATION);
            this.m_MSSecureData = getTagValue(MTSCRAConstants.DEVICE_TAG_MSR_SECUREDATA);
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardDataHandler
    public void handleData(byte[] bArr) {
        Log.i("", "31");
        setData(bArr);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public byte[] getData() {
        return this.m_tlvData;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public void clearBuffers() {
        clearData();
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getMaskedTracks() {
        return getTrack1Masked() + getTrack2Masked() + getTrack3Masked();
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack1() {
        return getTagValueFromMSSecureData(MTSCRAConstants.DEVICE_TAG_ENCRYPTED_TRACK1_RAW, MTSCRAConstants.DEVICE_TAG_ENCRYPTED_TRACK1);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack2() {
        return getTagValueFromMSSecureData(MTSCRAConstants.DEVICE_TAG_ENCRYPTED_TRACK2_RAW, MTSCRAConstants.DEVICE_TAG_ENCRYPTED_TRACK2);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack3() {
        return getTagValueFromMSSecureData(MTSCRAConstants.DEVICE_TAG_ENCRYPTED_TRACK3_RAW, MTSCRAConstants.DEVICE_TAG_ENCRYPTED_TRACK3);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack1Masked() {
        try {
            if (this.m_TLVVersion.length() > 0 && Integer.parseInt(this.m_TLVVersion) >= 3) {
                if (this.m_MSLocalMerchantData.length() <= 0) {
                    return "";
                }
                String tagValue = getTagValue(MTSCRAConstants.DEVICE_TAG_MASKED_TRACK1, this.m_MSLocalMerchantData);
                return tagValue.length() > 0 ? new String(MTParser.getByteArrayFromHexString(tagValue), "US-ASCII") : "";
            }
            if (this.m_PAN.length() <= 0) {
                return "";
            }
            String str = String.format("%%B%S^%S^%S", this.m_PAN, getCardName(), this.m_AdditionalInfo);
            for (int length = str.length(); length < MAX_TK1_LENGTH; length++) {
                str = str + "0";
            }
            return str + "?";
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack2Masked() {
        try {
            if (this.m_TLVVersion.length() > 0 && Integer.parseInt(this.m_TLVVersion) >= 3) {
                if (this.m_MSLocalMerchantData.length() <= 0) {
                    return "";
                }
                String tagValue = getTagValue(MTSCRAConstants.DEVICE_TAG_MASKED_TRACK2, this.m_MSLocalMerchantData);
                return tagValue.length() > 0 ? new String(MTParser.getByteArrayFromHexString(tagValue), "US-ASCII") : "";
            }
            if (this.m_PAN.length() <= 0) {
                return "";
            }
            String str = String.format(";%S=%S", this.m_PAN, this.m_AdditionalInfo);
            for (int length = str.length(); length < MAX_TK2_LENGTH; length++) {
                str = str + "0";
            }
            return str + "?";
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack3Masked() {
        try {
            String tagValueFromData = getTagValueFromData(MTSCRAConstants.DEVICE_TAG_MASKED_TRACK3, this.m_MSLocalMerchantData);
            return tagValueFromData.length() > 0 ? new String(MTParser.getByteArrayFromHexString(tagValueFromData), "US-ASCII") : "";
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getMagnePrint() {
        return getTagValueFromMSSecureData(MTSCRAConstants.DEVICE_TAG_ENCRYPTED_MAGNEPRINT_RAW, MTSCRAConstants.DEVICE_TAG_ENCRYPTED_MAGNEPRINT);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getMagnePrintStatus() {
        return getTagValueFromMSSecureData(MTSCRAConstants.DEVICE_TAG_ENCRYPTED_MAGNEPRINT_STS, "");
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getDeviceSerial() {
        try {
            if (this.m_TLVVersion.length() <= 0) {
                return "";
            }
            if (Integer.parseInt(this.m_TLVVersion) >= 3 && this.m_MSSecureData.length() > 0) {
                String tagValueFromData = getTagValueFromData(MTSCRAConstants.DEVICE_TAG_DEVICE_SERIAL_MAGTEK, this.m_MSSecureData);
                return tagValueFromData.length() > 0 ? new String(MTParser.getByteArrayFromHexString(tagValueFromData), "US-ASCII") : "";
            }
            if (getResponseType().equalsIgnoreCase(MTSCRAConstants.DEVICE_TAG_MSR_SWIPEDATA)) {
                return getTagValueFromData(MTSCRAConstants.DEVICE_TAG_DEVICE_SERIAL_INTERNAL, this.m_MSSecureData);
            }
            if (this.m_DeviceInfo.length() > 0) {
                return getTagValueFromData(MTSCRAConstants.DEVICE_TAG_DEVICE_SERIAL_INTERNAL, this.m_DeviceInfo);
            }
            return getTagValueFromData(MTSCRAConstants.DEVICE_TAG_DEVICE_SERIAL_INTERNAL, this.m_ResponseData);
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getSessionID() {
        return getTagValueFromData(MTSCRAConstants.DEVICE_TAG_ENCRYPTED_SESSIONID, this.m_MSSecureData);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getKSN() {
        return getTagValueFromData(MTSCRAConstants.DEVICE_TAG_MSR_KEYID, this.m_MSSecureData);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getDeviceName() {
        try {
            String tagValueFromData = getTagValueFromData(MTSCRAConstants.DEVICE_TAG_DEVICE_MODELNAME, this.m_DeviceInfo, this.m_ResponseData);
            return tagValueFromData.length() > 0 ? new String(MTParser.getByteArrayFromHexString(tagValueFromData), "US-ASCII") : "";
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public long getBatteryLevel() {
        return getTagLongValueFromData(MTSCRAConstants.DEVICE_TAG_DEVICE_STATUS_BATTERYLEVEL, this.m_DeviceInfo, this.m_ResponseData, MTDeviceConstants.BATTERY_LEVEL_NA);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public long getSwipeCount() {
        return getTagLongValueFromData(MTSCRAConstants.DEVICE_TAG_DEVICE_STATUS_SWIPECOUNT, this.m_DeviceInfo, this.m_ResponseData, MTDeviceConstants.SWIPE_COUNT_NA);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapMagnePrint() {
        return getTagValueFromData(MTSCRAConstants.DEVICE_TAG_CAPS_MAGNEPRINT, this.m_DeviceInfo);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapMagnePrintEncryption() {
        return getTagValueFromData(MTSCRAConstants.DEVICE_TAG_CAPS_MAGNEPRINT_ENCRYPTION, this.m_DeviceInfo);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapMagneSafe20Encryption() {
        return getTagValueFromData(MTSCRAConstants.DEVICE_TAG_CAPS_MAGNESAFE20, this.m_DeviceInfo);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapMagStripeEncryption() {
        return getTagValueFromData(MTSCRAConstants.DEVICE_TAG_CAPS_MSR_ENCRYPTION, this.m_DeviceInfo);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapMSR() {
        return getTagValueFromData(MTSCRAConstants.DEVICE_TAG_CAPS_MSR, this.m_DeviceInfo);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapTracks() {
        return getTagValueFromData(MTSCRAConstants.DEVICE_TAG_CAPS_TRACKS, this.m_DeviceInfo);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCardExpDate() {
        try {
            if (this.m_MSLocalMerchantData.length() <= 0) {
                return "";
            }
            if (this.m_TLVVersion.length() > 0 && Integer.parseInt(this.m_TLVVersion) >= 3) {
                String additionalInfo = getAdditionalInfo();
                return additionalInfo.length() > 6 ? additionalInfo.substring(0, 4) : "";
            }
            String tagValueFromData = getTagValueFromData(MTSCRAConstants.DEVICE_TAG_CARD_EXPDATE, this.m_MSLocalMerchantData);
            return tagValueFromData.length() > 0 ? new String(MTParser.getByteArrayFromHexString(tagValueFromData), "US-ASCII") : "";
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCardIIN() {
        try {
            if (this.m_MSLocalMerchantData.length() <= 0) {
                return "";
            }
            if (this.m_TLVVersion.length() > 0 && Integer.parseInt(this.m_TLVVersion) >= 3) {
                return this.m_PAN.length() >= 6 ? this.m_PAN.substring(0, 6) : "";
            }
            String tagValueFromData = getTagValueFromData(MTSCRAConstants.DEVICE_TAG_CARD_IIN, this.m_MSLocalMerchantData);
            return tagValueFromData.length() > 0 ? new String(MTParser.getByteArrayFromHexString(tagValueFromData), "US-ASCII") : "";
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCardLast4() {
        try {
            if (this.m_MSLocalMerchantData.length() <= 0) {
                return "";
            }
            if (this.m_TLVVersion.length() > 0 && Integer.parseInt(this.m_TLVVersion) >= 3) {
                if (this.m_PAN.length() <= 4) {
                    return "";
                }
                String str = this.m_PAN;
                return str.substring(str.length() - 4);
            }
            String tagValueFromData = getTagValueFromData(MTSCRAConstants.DEVICE_TAG_CARD_LAST4, this.m_MSLocalMerchantData);
            return tagValueFromData.length() > 0 ? new String(MTParser.getByteArrayFromHexString(tagValueFromData), "US-ASCII") : "";
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCardName() {
        try {
            if (this.m_MSLocalMerchantData.length() <= 0) {
                return "";
            }
            if (this.m_TLVVersion.length() > 0 && Integer.parseInt(this.m_TLVVersion) >= 3) {
                return getNameFromMaskedTrack1();
            }
            String tagValueFromData = getTagValueFromData(MTSCRAConstants.DEVICE_TAG_CARD_NAME, this.m_MSLocalMerchantData);
            return tagValueFromData.length() > 0 ? new String(MTParser.getByteArrayFromHexString(tagValueFromData), "US-ASCII") : "";
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCardPAN() {
        return getMaskedPAN();
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public int getCardPANLength() {
        try {
            if (this.m_MSSecureData.length() > 0 && this.m_TLVVersion.length() > 0 && Integer.parseInt(this.m_TLVVersion) >= 3) {
                return this.m_PAN.length();
            }
            if (this.m_MSLocalMerchantData.length() <= 0) {
                return 0;
            }
            String tagValueFromData = getTagValueFromData(MTSCRAConstants.DEVICE_TAG_PAN_LENGTH, this.m_MSLocalMerchantData);
            if (tagValueFromData.length() > 0) {
                return Integer.parseInt(tagValueFromData, 16);
            }
            return 0;
        } catch (Exception unused) {
            return 0;
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCardServiceCode() {
        try {
            if (this.m_MSLocalMerchantData.length() <= 0) {
                return "";
            }
            if (this.m_TLVVersion.length() > 0 && Integer.parseInt(this.m_TLVVersion) >= 3) {
                String additionalInfo = getAdditionalInfo();
                return additionalInfo.length() > 6 ? additionalInfo.substring(4) : "";
            }
            String tagValueFromData = getTagValueFromData(MTSCRAConstants.DEVICE_TAG_CARD_SVCCODE, this.m_MSLocalMerchantData);
            return tagValueFromData.length() > 0 ? new String(MTParser.getByteArrayFromHexString(tagValueFromData), "US-ASCII") : "";
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCardStatus() {
        return getTagValueFromData(MTSCRAConstants.DEVICE_TAG_STATUS_CARD, this.m_MSSwipeStatus);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getHashCode() {
        return getTagValueFromMSSecureData(MTSCRAConstants.DEVICE_TAG_HASHCODE, "");
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getDeviceConfig(String str) {
        return getTagValueFromData(str, this.m_DeviceInfo);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getEncryptionStatus() {
        return getTagValueFromData(MTSCRAConstants.DEVICE_TAG_STATUS_OPERATION, this.m_MSSwipeStatus);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getFirmware() {
        try {
            String tagValueFromData = getTagValueFromData(MTSCRAConstants.DEVICE_TAG_DEVICE_MAINFIRMWARE, this.m_DeviceInfo, this.m_ResponseData);
            return tagValueFromData.length() > 0 ? new String(MTParser.getByteArrayFromHexString(tagValueFromData), "US-ASCII") : "";
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getMagTekDeviceSerial() {
        try {
            if (this.m_TLVVersion.length() <= 0) {
                return "";
            }
            if (Integer.parseInt(this.m_TLVVersion, 16) >= 3 && this.m_MSSecureData.length() > 0) {
                String tagValueFromData = getTagValueFromData(MTSCRAConstants.DEVICE_TAG_DEVICE_SERIAL_MAGTEK, this.m_MSSecureData);
                return tagValueFromData.length() > 0 ? new String(MTParser.getByteArrayFromHexString(tagValueFromData), "US-ASCII") : "";
            }
            String tagValueFromData2 = getTagValueFromData(MTSCRAConstants.DEVICE_TAG_DEVICE_SERIAL_MAGTEK, this.m_DeviceInfo, this.m_ResponseData);
            return tagValueFromData2.length() > 0 ? new String(MTParser.getByteArrayFromHexString(tagValueFromData2), "US-ASCII") : "";
        } catch (Exception unused) {
            return "";
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getResponseType() {
        if (this.m_ResponseType.length() == 0 && this.m_ResponseData.length() >= 4) {
            this.m_ResponseType = this.m_ResponseData.substring(0, 4);
        }
        return this.m_ResponseType;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTagValue(String str, String str2) {
        return getTagValueFromData(str, str2);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTLVVersion() {
        if (this.m_TLVVersion.length() == 0) {
            this.m_TLVVersion = getTagValue(MTSCRAConstants.DEVICE_TAG_TLVVERSION);
        }
        return this.m_TLVVersion;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrackDecodeStatus() {
        return getTagValueFromData(MTSCRAConstants.DEVICE_TAG_STATUS_TRACKS, this.m_MSSwipeStatus);
    }

    private String getResponseData() {
        return this.m_ResponseData;
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

    private String getMaskedPAN() {
        String pANFromMaskedTrack2 = getPANFromMaskedTrack2();
        return pANFromMaskedTrack2.trim().length() == 0 ? getPANFromMaskedTrack1() : pANFromMaskedTrack2;
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

    private String getTagValueFromMSSecureData(String str, String str2) {
        if (this.m_MSSecureData.length() <= 0) {
            return "";
        }
        String tagValue = getTagValue(str, this.m_MSSecureData);
        return ((this.m_TLVVersion.length() <= 0 || Integer.parseInt(this.m_TLVVersion, 16) < 3) && tagValue.length() == 0 && str2.length() > 0) ? getTagValueFromData(str2, this.m_MSSecureData) : tagValue;
    }

    private String getTagValueFromParsedTLVData(String str) {
        for (HashMap<String, String> map : this.m_parsedTLVData) {
            if (str.equalsIgnoreCase(map.get("tag"))) {
                return map.get("value");
            }
        }
        return "";
    }

    private String getTagValue(String str) {
        return getTagValueFromParsedTLVData(str);
    }

    private String getTagValueFromData(String str, String str2, String str3) {
        String tagValueFromData = "";
        try {
            if (str2.length() > 0) {
                tagValueFromData = getTagValueFromData(str, str2);
                if (tagValueFromData != null && tagValueFromData.length() > 0) {
                    return tagValueFromData;
                }
            } else if (str3.length() > 0) {
                tagValueFromData = getTagValueFromData(str, str3);
            }
        } catch (Exception unused) {
        }
        return tagValueFromData;
    }

    private long getTagLongValueFromData(String str, String str2, String str3, long j) {
        try {
            return getTagValueFromData(str, str2, str3).length() > 0 ? Integer.parseInt(r1, 16) : j;
        } catch (Exception unused) {
            return j;
        }
    }

    private long getTagLongValueFromData(String str, String str2, String str3) {
        return getTagLongValueFromData(str, str2, str3, 0L);
    }

    private String getTagValueFromData(String str, String str2) {
        return getTagValueFromParsedTLVData(str);
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

    private String getConfigurationParam(String str, String str2) {
        Log.i(TAG, "Configuration: " + str);
        if (str == null || str.length() <= 0) {
            return "";
        }
        try {
            String[] strArrSplit = new String(str).split(",");
            if (strArrSplit.length <= 0) {
                return "";
            }
            for (int i = 0; i < strArrSplit.length; i++) {
                if (strArrSplit[i].toUpperCase().indexOf(str2.toUpperCase()) != -1) {
                    String[] strArrSplit2 = strArrSplit[i].toUpperCase().split("=");
                    if (strArrSplit2.length > 1) {
                        String strTrim = strArrSplit2[1].trim();
                        try {
                            Log.i(TAG, "Value: " + strTrim);
                        } catch (Exception unused) {
                        }
                        return strTrim;
                    }
                }
            }
            return "";
        } catch (Exception unused2) {
            return "";
        }
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTLVPayload() {
        return MTPayloadBuilder.buildTLVPayload(this);
    }
}
