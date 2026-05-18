package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public class BaseDeviceControl implements IDeviceControl {
    protected IData mDeviceResponseData = null;
    protected Object mDeviceResponseEvent;

    @Override // com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean close() {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean deviceReset() {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean displayMessage(byte b, byte b2) {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean endSession() {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean getInput(IData iData) {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean open() {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean playSound(IData iData) {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean send(IData iData) {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean sendExtendedCommand(IData iData) {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean setDateTime(IData iData) {
        return false;
    }

    public boolean setDisplay(IData iData) {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean setLatch(boolean z) {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean showBarCode(BarCodeRequest barCodeRequest, byte b, IData iData) {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean showImage(byte b) {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean showImage(ImageData imageData, byte b) {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean startBarCodeReader(byte b, byte b2) {
        return false;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean stopBarCodeReader() {
        return false;
    }

    public BaseDeviceControl() {
        this.mDeviceResponseEvent = null;
        this.mDeviceResponseEvent = new Object();
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceControl
    public IResult sendSync(IData iData) {
        send(iData);
        try {
            this.mDeviceResponseEvent.wait(3000L);
            if (this.mDeviceResponseData != null) {
                return new Result(StatusCode.SUCCESS, this.mDeviceResponseData);
            }
            return null;
        } catch (Exception unused) {
            return new Result(StatusCode.TIMEOUT);
        }
    }
}
