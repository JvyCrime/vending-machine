package com.magtek.mobile.android.mtusdk.scra;

import com.magtek.mobile.android.mtlib.MTSCRA;
import com.magtek.mobile.android.mtusdk.BaseData;
import com.magtek.mobile.android.mtusdk.BaseDeviceControl;
import com.magtek.mobile.android.mtusdk.IData;

/* JADX INFO: loaded from: classes.dex */
public class SCRADeviceControl extends BaseDeviceControl {
    protected MTSCRA mSCRA;

    public SCRADeviceControl(MTSCRA mtscra) {
        this.mSCRA = null;
        this.mSCRA = mtscra;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean open() {
        this.mSCRA.openDevice();
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean close() {
        this.mSCRA.closeDevice();
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean send(IData iData) {
        if (iData == null) {
            return true;
        }
        this.mSCRA.sendCommandToDevice(iData.StringValue());
        return true;
    }

    public void OnDeviceResponse(String str) {
        if (str != null) {
            this.mDeviceResponseData = new BaseData(str);
            this.mDeviceResponseEvent.notifyAll();
        }
    }
}
