package com.magtek.mobile.android.mtusdk.mms;

import com.magtek.mobile.android.mtusdk.BaseDeviceControl;
import com.magtek.mobile.android.mtusdk.IData;
import com.magtek.mobile.android.mtusdk.ImageData;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import com.magtek.mobile.android.mtusdk.messages.Command;
import com.magtek.mobile.android.mtusdk.messages.Message;
import com.magtek.mobile.android.mtusdk.messages.MessageBuilder;
import com.magtek.mobile.android.mtusdk.mmx.MMXDevice;
import com.magtek.mobile.android.mtusdk.mmx.MMXMessage;

/* JADX INFO: loaded from: classes.dex */
public class MMSDeviceControl extends BaseDeviceControl {
    private MMXDevice mMMXDevice;
    private String mPath;

    public MMSDeviceControl(MMXDevice mMXDevice, String str) {
        this.mMMXDevice = null;
        this.mPath = "";
        this.mMMXDevice = mMXDevice;
        this.mPath = str;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean open() {
        this.mMMXDevice.setAddress(this.mPath);
        this.mMMXDevice.open();
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean close() {
        this.mMMXDevice.close();
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean displayMessage(byte b, byte b2) {
        sendCommand(MMSCommandBuilder.DisplayMessageCommand(b, b2));
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean showImage(byte b) {
        sendCommand(MMSCommandBuilder.ShowImageCommand(b, (byte) 0));
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean showImage(ImageData imageData, byte b) {
        sendCommand(MMSCommandBuilder.ShowBitmapImageCommand(imageData.Data(), b, imageData.BackgroundColor()));
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0011  */
    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean showBarCode(com.magtek.mobile.android.mtusdk.BarCodeRequest r10, byte r11, com.magtek.mobile.android.mtusdk.IData r12) {
        /*
            r9 = this;
            if (r12 == 0) goto L11
            java.lang.String r12 = r12.StringValue()
            int r0 = r12.length()
            if (r0 <= 0) goto L11
            byte[] r12 = r12.getBytes()
            goto L12
        L11:
            r12 = 0
        L12:
            r8 = r12
            byte[] r0 = r10.Data()
            byte r2 = r10.ErrorCorrection()
            byte r3 = r10.MaskPattern()
            byte r4 = r10.MinVersion()
            byte r5 = r10.MaxVersion()
            byte[] r6 = r10.BlockColor()
            byte[] r7 = r10.BackgroundColor()
            r1 = r11
            com.magtek.mobile.android.mtusdk.messages.Command r10 = com.magtek.mobile.android.mtusdk.mms.MMSCommandBuilder.ShowQRCode(r0, r1, r2, r3, r4, r5, r6, r7, r8)
            r9.sendCommand(r10)
            r10 = 1
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.magtek.mobile.android.mtusdk.mms.MMSDeviceControl.showBarCode(com.magtek.mobile.android.mtusdk.BarCodeRequest, byte, com.magtek.mobile.android.mtusdk.IData):boolean");
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean startBarCodeReader(byte b, byte b2) {
        sendCommand(MMSCommandBuilder.EnableBarCodeReader(b, b2));
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean stopBarCodeReader() {
        sendCommand(MMSCommandBuilder.DisableBarCodeReader());
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean deviceReset() {
        sendCommand(MMSCommandBuilder.ResetDeviceCommand());
        return true;
    }

    protected void sendCommand(Command command) {
        Message messageBuildMessage = MessageBuilder.BuildMessage();
        messageBuildMessage.addMessageInfoForCommand(command.getTagByteArray());
        messageBuildMessage.addPayload(command.getByteArray());
        byte[] byteArray = messageBuildMessage.getByteArray();
        if (byteArray != null) {
            this.mMMXDevice.sendMessage(new MMXMessage(48, byteArray));
        }
    }

    @Override // com.magtek.mobile.android.mtusdk.BaseDeviceControl, com.magtek.mobile.android.mtusdk.IDeviceControl
    public boolean send(IData iData) {
        byte[] byteArrayFromHexString;
        if (iData == null || (byteArrayFromHexString = TLVParser.getByteArrayFromHexString(iData.StringValue())) == null || byteArrayFromHexString.length <= 0) {
            return true;
        }
        this.mMMXDevice.sendMessage(new MMXMessage(48, byteArrayFromHexString));
        return true;
    }
}
