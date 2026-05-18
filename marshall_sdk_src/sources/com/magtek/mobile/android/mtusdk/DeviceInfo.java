package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public class DeviceInfo {
    protected String mModel;
    protected String mName;
    protected String mSerial;

    public DeviceInfo(String str, String str2, String str3) {
        this.mName = str;
        this.mModel = str2;
        this.mSerial = str3;
    }

    public String getName() {
        return this.mName;
    }

    public String getModel() {
        return this.mModel;
    }

    public String getSerial() {
        return this.mSerial;
    }
}
