package com.magtek.mobile.android.mtlib;

/* JADX INFO: loaded from: classes.dex */
public class MTHIDCardData implements IMTCardData, IMTCardDataHandler {
    private static int REPORT_LENGTH_DEVICE_SERIAL_NUMBER = 15;
    private static int REPORT_LENGTH_DUKPT_SERIAL_NUMBER_COUNTER = 10;
    private static int REPORT_LENGTH_ENCRYPTION_SESSION_ID = 8;
    private static int REPORT_LENGTH_MAGNEPRINT_STATUS = 4;
    private static int REPORT_OFFSET_BATTERY_LEVEL = 930;
    private static int REPORT_OFFSET_CARD_ENCODE_TYPE = 6;
    private static int REPORT_OFFSET_CARD_STATUS = 343;
    private static int REPORT_OFFSET_DEVICE_SERIAL_NUMBER = 477;
    private static int REPORT_OFFSET_DUKPT_SERIAL_NUMBER_COUNTER = 495;
    private static int REPORT_OFFSET_ENCRYPTION_COUNTER = 856;
    private static int REPORT_OFFSET_ENCRYPTION_SESSION_ID = 844;
    private static int REPORT_OFFSET_HASHED_TRACK2_DATA = 867;
    private static int REPORT_OFFSET_MAGNEPRINT_ABSOLUTE_DATA_LENGTH = 855;
    private static int REPORT_OFFSET_MAGNEPRINT_DATA = 349;
    private static int REPORT_OFFSET_MAGNEPRINT_DATA_LENGTH = 348;
    private static int REPORT_OFFSET_MAGNEPRINT_KSN = 920;
    private static int REPORT_OFFSET_MAGNEPRINT_STATUS = 344;
    private static int REPORT_OFFSET_MAGNESAFE_VERSION_NUMBER = 859;
    private static int REPORT_OFFSET_READER_ENCRYPTION_STATUS = 493;
    private static int REPORT_OFFSET_REPORT_VERSION = 887;
    private static int REPORT_OFFSET_SHA256_HASHED_TRACK2_DATA = 888;
    private static int REPORT_OFFSET_TRACK1_ABSOLUTE_DATA_LENGTH = 852;
    private static int REPORT_OFFSET_TRACK1_DECODE_STATUS = 0;
    private static int REPORT_OFFSET_TRACK1_ENCRYPTED_DATA = 7;
    private static int REPORT_OFFSET_TRACK1_ENCRYPTED_DATA_LENGTH = 3;
    private static int REPORT_OFFSET_TRACK1_MASKED_DATA = 508;
    private static int REPORT_OFFSET_TRACK1_MASKED_DATA_LENGTH = 505;
    private static int REPORT_OFFSET_TRACK2_ABSOLUTE_DATA_LENGTH = 853;
    private static int REPORT_OFFSET_TRACK2_DECODE_STATUS = 1;
    private static int REPORT_OFFSET_TRACK2_ENCRYPTED_DATA = 119;
    private static int REPORT_OFFSET_TRACK2_ENCRYPTED_DATA_LENGTH = 4;
    private static int REPORT_OFFSET_TRACK2_MASKED_DATA = 620;
    private static int REPORT_OFFSET_TRACK2_MASKED_DATA_LENGTH = 506;
    private static int REPORT_OFFSET_TRACK3_ABSOLUTE_DATA_LENGTH = 854;
    private static int REPORT_OFFSET_TRACK3_DECODE_STATUS = 2;
    private static int REPORT_OFFSET_TRACK3_ENCRYPTED_DATA = 231;
    private static int REPORT_OFFSET_TRACK3_ENCRYPTED_DATA_LENGTH = 5;
    private static int REPORT_OFFSET_TRACK3_MASKED_DATA = 732;
    private static int REPORT_OFFSET_TRACK3_MASKED_DATA_LENGTH = 507;
    private String m_configuration;
    private byte[] m_hidData;
    private int m_threshold;

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapMSR() {
        return "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapMagStripeEncryption() {
        return "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapMagnePrint() {
        return "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapMagnePrintEncryption() {
        return "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapMagneSafe20Encryption() {
        return "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCapTracks() {
        return "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public long getCardDataCRC() {
        return 0L;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public int getDataFieldCount() {
        return 0;
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

    public MTHIDCardData() {
        clearData();
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardDataHandler
    public void clearData() {
        this.m_hidData = null;
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
        byte[] bArr = this.m_hidData;
        return bArr != null && bArr.length >= this.m_threshold;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardDataHandler
    public void setData(byte[] bArr) {
        byte[] bArr2 = this.m_hidData;
        if (bArr2 == null) {
            this.m_hidData = bArr;
            return;
        }
        byte[] bArr3 = new byte[bArr2.length + bArr.length];
        System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
        System.arraycopy(bArr, 0, bArr3, this.m_hidData.length, bArr.length);
        this.m_hidData = bArr3;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardDataHandler
    public void handleData(byte[] bArr) {
        setData(bArr);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public byte[] getData() {
        return this.m_hidData;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public void clearBuffers() {
        clearData();
    }

    protected byte[] getData(int i, int i2) {
        byte[] bArr = this.m_hidData;
        int i3 = (bArr == 0 || bArr.length < i) ? 0 : bArr[i];
        if (i3 <= 0) {
            return null;
        }
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        return bArr2;
    }

    protected String getDataAsHexString(int i, int i2) {
        byte[] data = getData(i, i2);
        return (data == null || data.length <= 0) ? "" : MTParser.getHexString(data);
    }

    protected String getDataAsString(int i, int i2) {
        byte[] data = getData(i, i2);
        return (data == null || data.length <= 0) ? "" : MTParser.getTextString(data, 0);
    }

    protected byte[] getDataWithLength(int i, int i2) {
        byte[] bArr;
        if (i2 <= 0 || (bArr = this.m_hidData) == null || bArr.length <= i + i2) {
            return null;
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    protected String getDataWithLengthAsHexString(int i, int i2) {
        byte[] dataWithLength = getDataWithLength(i, i2);
        return (dataWithLength == null || dataWithLength.length <= 0) ? "" : MTParser.getHexString(dataWithLength);
    }

    protected String getDataWithLengthAsString(int i, int i2) {
        byte[] dataWithLength = getDataWithLength(i, i2);
        return (dataWithLength == null || dataWithLength.length <= 0) ? "" : MTParser.getTextString(dataWithLength, 0);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getMaskedTracks() {
        return getTrack1Masked() + getTrack2Masked() + getTrack3Masked();
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack1() {
        byte[] bArr = this.m_hidData;
        if (bArr != null && bArr.length > REPORT_OFFSET_TRACK1_MASKED_DATA_LENGTH) {
            return getDataAsHexString(REPORT_OFFSET_TRACK1_ENCRYPTED_DATA_LENGTH, REPORT_OFFSET_TRACK1_ENCRYPTED_DATA);
        }
        return getDataAsString(REPORT_OFFSET_TRACK1_ENCRYPTED_DATA_LENGTH, REPORT_OFFSET_TRACK1_ENCRYPTED_DATA);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack2() {
        byte[] bArr = this.m_hidData;
        if (bArr != null && bArr.length > REPORT_OFFSET_TRACK2_MASKED_DATA_LENGTH) {
            return getDataAsHexString(REPORT_OFFSET_TRACK2_ENCRYPTED_DATA_LENGTH, REPORT_OFFSET_TRACK2_ENCRYPTED_DATA);
        }
        return getDataAsString(REPORT_OFFSET_TRACK2_ENCRYPTED_DATA_LENGTH, REPORT_OFFSET_TRACK2_ENCRYPTED_DATA - 2);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack3() {
        byte[] bArr = this.m_hidData;
        if (bArr != null && bArr.length > REPORT_OFFSET_TRACK3_MASKED_DATA_LENGTH) {
            return getDataAsHexString(REPORT_OFFSET_TRACK3_ENCRYPTED_DATA_LENGTH, REPORT_OFFSET_TRACK3_ENCRYPTED_DATA);
        }
        return getDataAsString(REPORT_OFFSET_TRACK3_ENCRYPTED_DATA_LENGTH, REPORT_OFFSET_TRACK3_ENCRYPTED_DATA - 4);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack1Masked() {
        return getDataAsString(REPORT_OFFSET_TRACK1_MASKED_DATA_LENGTH, REPORT_OFFSET_TRACK1_MASKED_DATA);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack2Masked() {
        return getDataAsString(REPORT_OFFSET_TRACK2_MASKED_DATA_LENGTH, REPORT_OFFSET_TRACK2_MASKED_DATA);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrack3Masked() {
        return getDataAsString(REPORT_OFFSET_TRACK3_MASKED_DATA_LENGTH, REPORT_OFFSET_TRACK3_MASKED_DATA);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getMagnePrint() {
        return getDataAsHexString(REPORT_OFFSET_MAGNEPRINT_DATA_LENGTH, REPORT_OFFSET_MAGNEPRINT_DATA);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getMagnePrintStatus() {
        return getDataWithLengthAsHexString(REPORT_OFFSET_MAGNEPRINT_STATUS, REPORT_LENGTH_MAGNEPRINT_STATUS);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getDeviceSerial() {
        return getDataWithLengthAsString(REPORT_OFFSET_DEVICE_SERIAL_NUMBER, REPORT_LENGTH_DEVICE_SERIAL_NUMBER);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getSessionID() {
        return getDataWithLengthAsHexString(REPORT_OFFSET_ENCRYPTION_SESSION_ID, REPORT_LENGTH_ENCRYPTION_SESSION_ID);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getKSN() {
        return getDataWithLengthAsHexString(REPORT_OFFSET_DUKPT_SERIAL_NUMBER_COUNTER, REPORT_LENGTH_DUKPT_SERIAL_NUMBER_COUNTER);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public long getBatteryLevel() {
        try {
            int i = REPORT_OFFSET_BATTERY_LEVEL;
            byte[] bArr = this.m_hidData;
            if (bArr != null && i < bArr.length) {
                return bArr[i];
            }
        } catch (Exception unused) {
        }
        return MTDeviceConstants.BATTERY_LEVEL_NA;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public long getSwipeCount() {
        return MTDeviceConstants.SWIPE_COUNT_NA;
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
        String maskedPAN = getMaskedPAN();
        if (maskedPAN.length() >= 3) {
            return maskedPAN.length();
        }
        return 0;
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCardServiceCode() {
        String additionalInfo = getAdditionalInfo();
        return additionalInfo.length() > 6 ? additionalInfo.substring(4) : "";
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getCardStatus() {
        return getDataWithLengthAsHexString(REPORT_OFFSET_CARD_STATUS, 1);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getEncryptionStatus() {
        return getDataWithLengthAsHexString(REPORT_OFFSET_READER_ENCRYPTION_STATUS, 2);
    }

    @Override // com.magtek.mobile.android.mtlib.IMTCardData
    public String getTrackDecodeStatus() {
        return getDataWithLengthAsHexString(REPORT_OFFSET_TRACK1_DECODE_STATUS, 3);
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
