package com.magtek.mobile.android.mtusdk.ppscra;

import com.magtek.mobile.android.mtusdk.BaseDeviceControl;
import com.magtek.mobile.android.mtusdk.IData;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import com.magtek.mobile.android.ppscra.MagTekPPSCRA;

/* JADX INFO: loaded from: classes.dex */
public class PPSCRADeviceControl extends BaseDeviceControl {
    protected MagTekPPSCRA mPPSCRA;

    public PPSCRADeviceControl(MagTekPPSCRA magTekPPSCRA) {
        this.mPPSCRA = null;
        this.mPPSCRA = magTekPPSCRA;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean open() {
        this.mPPSCRA.openDevice();
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean close() {
        this.mPPSCRA.closeDevice();
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean send(IData iData) {
        byte[] byteArrayFromHexString;
        int length;
        if (iData != null && (byteArrayFromHexString = TLVParser.getByteArrayFromHexString(iData.StringValue())) != null && (length = byteArrayFromHexString.length - 1) > 0) {
            byte b = byteArrayFromHexString[0];
            byte[] bArr = new byte[length];
            System.arraycopy(byteArrayFromHexString, 1, bArr, 0, length);
            if (b == 0) {
                this.mPPSCRA.getSpecialCommand(bArr);
            } else if (b == 1) {
                this.mPPSCRA.sendSpecialCommand(bArr);
            }
        }
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean endSession() {
        this.mPPSCRA.endSession((byte) 0);
        return true;
    }
}
