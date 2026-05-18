package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public interface IDeviceConfiguration {
    byte[] getChallengeToken(byte[] bArr);

    byte[] getConfigInfo(byte b, byte[] bArr);

    String getDeviceInfo(InfoType infoType);

    int getFile(byte[] bArr, IConfigurationCallback iConfigurationCallback);

    byte[] getKeyInfo(byte b, byte[] bArr);

    int sendFile(byte[] bArr, byte[] bArr2, IConfigurationCallback iConfigurationCallback);

    int sendImage(byte b, byte[] bArr, IConfigurationCallback iConfigurationCallback);

    int sendSecureFile(byte[] bArr, byte[] bArr2, IConfigurationCallback iConfigurationCallback);

    int setConfigInfo(byte b, byte[] bArr, IConfigurationCallback iConfigurationCallback);

    int setDisplayImage(byte b);

    int updateFirmware(int i, byte[] bArr, IConfigurationCallback iConfigurationCallback);

    int updateKeyInfo(byte b, byte[] bArr, IConfigurationCallback iConfigurationCallback);
}
