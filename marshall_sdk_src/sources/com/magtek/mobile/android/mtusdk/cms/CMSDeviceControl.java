package com.magtek.mobile.android.mtusdk.cms;

import com.magtek.mobile.android.mtcms.MTDevice;
import com.magtek.mobile.android.mtusdk.BaseDeviceControl;
import com.magtek.mobile.android.mtusdk.IData;

/* JADX INFO: loaded from: classes.dex */
public class CMSDeviceControl extends BaseDeviceControl {
    private MTDevice mDevice;

    public CMSDeviceControl(MTDevice mTDevice) {
        this.mDevice = null;
        this.mDevice = mTDevice;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean open() {
        this.mDevice.openDevice();
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean close() {
        this.mDevice.closeDevice();
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean send(IData iData) {
        this.mDevice.sendDataString(iData.StringValue());
        return true;
    }
}
