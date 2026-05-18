package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public interface IDeviceControl {
    boolean close();

    boolean deviceReset();

    boolean displayMessage(byte b, byte b2);

    boolean endSession();

    boolean getInput(IData iData);

    boolean open();

    boolean playSound(IData iData);

    boolean send(IData iData);

    boolean sendExtendedCommand(IData iData);

    IResult sendSync(IData iData);

    boolean setDateTime(IData iData);

    boolean setLatch(boolean z);

    boolean showBarCode(BarCodeRequest barCodeRequest, byte b, IData iData);

    boolean showImage(byte b);

    boolean showImage(ImageData imageData, byte b);

    boolean startBarCodeReader(byte b, byte b2);

    boolean stopBarCodeReader();
}
