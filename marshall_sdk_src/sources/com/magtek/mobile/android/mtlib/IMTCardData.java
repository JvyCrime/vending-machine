package com.magtek.mobile.android.mtlib;

/* JADX INFO: loaded from: classes.dex */
public interface IMTCardData {
    void clearBuffers();

    long getBatteryLevel();

    String getCapMSR();

    String getCapMagStripeEncryption();

    String getCapMagnePrint();

    String getCapMagnePrintEncryption();

    String getCapMagneSafe20Encryption();

    String getCapTracks();

    long getCardDataCRC();

    String getCardExpDate();

    String getCardIIN();

    String getCardLast4();

    String getCardName();

    String getCardPAN();

    int getCardPANLength();

    String getCardServiceCode();

    String getCardStatus();

    byte[] getData();

    int getDataFieldCount();

    String getDeviceConfig(String str);

    String getDeviceName();

    String getDeviceSerial();

    String getEncryptionStatus();

    String getFirmware();

    String getHashCode();

    String getKSN();

    String getMagTekDeviceSerial();

    String getMagnePrint();

    String getMagnePrintStatus();

    String getMaskedTracks();

    String getResponseType();

    String getSessionID();

    long getSwipeCount();

    String getTLVPayload();

    String getTLVVersion();

    String getTagValue(String str, String str2);

    String getTrack1();

    String getTrack1Masked();

    String getTrack2();

    String getTrack2Masked();

    String getTrack3();

    String getTrack3Masked();

    String getTrackDecodeStatus();
}
