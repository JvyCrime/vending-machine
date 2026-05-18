package com.magtek.mobile.android.mtlib;

/* JADX INFO: loaded from: classes.dex */
public interface IMTSCRA {
    void clearBuffers();

    void closeDevice();

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

    int getDataFieldCount();

    String getDeviceConfig(String str);

    MTDeviceFeatures getDeviceFeatures();

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

    String getResponseData();

    String getResponseType();

    String getSDKVersion();

    String getSessionID();

    long getSwipeCount();

    String getTLVVersion();

    String getTagValue(String str, String str2);

    String getTrack1();

    String getTrack1Masked();

    String getTrack2();

    String getTrack2Masked();

    String getTrack3();

    String getTrack3Masked();

    String getTrackDecodeStatus();

    boolean isDeviceConnected();

    boolean isDeviceEMV();

    boolean isDeviceOEM();

    void openDevice();

    int sendCommandToDevice(String str);

    void setAddress(String str);

    void setConnectionRetry(boolean z);

    void setConnectionType(MTConnectionType mTConnectionType);

    void setDeviceConfiguration(String str);
}
