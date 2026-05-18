package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public class BaseDeviceConfiguration implements IDeviceConfiguration {
    @Override // com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public byte[] getChallengeToken(byte[] bArr) {
        return null;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public byte[] getConfigInfo(byte b, byte[] bArr) {
        return null;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public String getDeviceInfo(InfoType infoType) {
        return "";
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public int getFile(byte[] bArr, IConfigurationCallback iConfigurationCallback) {
        return -1;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public byte[] getKeyInfo(byte b, byte[] bArr) {
        return null;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public int sendFile(byte[] bArr, byte[] bArr2, IConfigurationCallback iConfigurationCallback) {
        return -1;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public int sendImage(byte b, byte[] bArr, IConfigurationCallback iConfigurationCallback) {
        return -1;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public int sendSecureFile(byte[] bArr, byte[] bArr2, IConfigurationCallback iConfigurationCallback) {
        return -1;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public int setConfigInfo(byte b, byte[] bArr, IConfigurationCallback iConfigurationCallback) {
        return -1;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public int setDisplayImage(byte b) {
        return -1;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public int updateFirmware(int i, byte[] bArr, IConfigurationCallback iConfigurationCallback) {
        return -1;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceConfiguration
    public int updateKeyInfo(byte b, byte[] bArr, IConfigurationCallback iConfigurationCallback) {
        return -1;
    }
}
