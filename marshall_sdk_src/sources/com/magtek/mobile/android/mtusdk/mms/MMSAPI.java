package com.magtek.mobile.android.mtusdk.mms;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import com.magtek.mobile.android.mtusdk.BarCodeData;
import com.magtek.mobile.android.mtusdk.ConnectionInfo;
import com.magtek.mobile.android.mtusdk.ConnectionType;
import com.magtek.mobile.android.mtusdk.CoreAPI;
import com.magtek.mobile.android.mtusdk.DeviceInfo;
import com.magtek.mobile.android.mtusdk.DeviceType;
import com.magtek.mobile.android.mtusdk.IDevice;
import com.magtek.mobile.android.mtusdk.PANData;
import com.magtek.mobile.android.mtusdk.PINData;
import com.magtek.mobile.android.mtusdk.common.TLVObject;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MMSAPI {
    public static IDevice createDevice(Context context, ConnectionType connectionType, String str, String str2, String str3, String str4) {
        String strGetConnectionTypeString = CoreAPI.GetConnectionTypeString(connectionType);
        return new MMSDevice(context, new ConnectionInfo(DeviceType.MMS, connectionType, str), new DeviceInfo("[" + strGetConnectionTypeString + " " + str2 + "] " + str3, str2, str4));
    }

    public static List<IDevice> getDeviceList(Context context) {
        return getUSBDevices(context);
    }

    private static List<IDevice> getUSBDevices(Context context) {
        ArrayList arrayList = new ArrayList();
        UsbManager usbManager = (UsbManager) context.getSystemService("usb");
        if (usbManager != null) {
            for (UsbDevice usbDevice : usbManager.getDeviceList().values()) {
                if (usbDevice != null && usbDevice.getVendorId() == 2049 && (usbDevice.getProductId() == 8224 || usbDevice.getProductId() == 8227)) {
                    String deviceName = usbDevice.getDeviceName();
                    IDevice iDeviceCreateDevice = createDevice(context, ConnectionType.USB, "", usbDevice.getProductId() == 8227 ? "DynaProx" : "DynaFlex", deviceName, "");
                    if (iDeviceCreateDevice != null) {
                        arrayList.add(iDeviceCreateDevice);
                    }
                }
            }
        }
        return arrayList;
    }

    public static PINData getPINData(byte[] bArr) {
        List<TLVObject> tLVByteArray;
        TLVObject tLVObjectFindFromListByTagHexString;
        byte[] valueByteArray;
        byte[] valueByteArray2;
        if (bArr == null || (tLVByteArray = TLVParser.parseTLVByteArray(bArr)) == null || (tLVObjectFindFromListByTagHexString = TLVParser.findFromListByTagHexString(tLVByteArray, "F5")) == null) {
            return null;
        }
        TLVObject tLVObjectFindChildByTagHexString = tLVObjectFindFromListByTagHexString.findChildByTagHexString("DFDF71");
        byte b = 0;
        byte b2 = (tLVObjectFindChildByTagHexString == null || (valueByteArray2 = tLVObjectFindChildByTagHexString.getValueByteArray()) == null || valueByteArray2.length <= 0) ? (byte) 0 : valueByteArray2[0];
        TLVObject tLVObjectFindChildByTagHexString2 = tLVObjectFindFromListByTagHexString.findChildByTagHexString("99");
        byte[] valueByteArray3 = tLVObjectFindChildByTagHexString2 != null ? tLVObjectFindChildByTagHexString2.getValueByteArray() : null;
        TLVObject tLVObjectFindChildByTagHexString3 = tLVObjectFindFromListByTagHexString.findChildByTagHexString("DFDF41");
        byte[] valueByteArray4 = tLVObjectFindChildByTagHexString3 != null ? tLVObjectFindChildByTagHexString3.getValueByteArray() : null;
        TLVObject tLVObjectFindChildByTagHexString4 = tLVObjectFindFromListByTagHexString.findChildByTagHexString("DFDF42");
        if (tLVObjectFindChildByTagHexString4 != null && (valueByteArray = tLVObjectFindChildByTagHexString4.getValueByteArray()) != null && valueByteArray.length > 0) {
            b = valueByteArray[0];
        }
        return new PINData(valueByteArray3, valueByteArray4, b2, b);
    }

    public static PANData getPANData(byte[] bArr) {
        List<TLVObject> tLVByteArray;
        byte[] valueByteArray;
        if (bArr == null || (tLVByteArray = TLVParser.parseTLVByteArray(bArr)) == null) {
            return null;
        }
        TLVObject tLVObjectFindFromListByTagHexString = TLVParser.findFromListByTagHexString(tLVByteArray, "DFDF59");
        byte[] valueByteArray2 = tLVObjectFindFromListByTagHexString != null ? tLVObjectFindFromListByTagHexString.getValueByteArray() : null;
        TLVObject tLVObjectFindFromListByTagHexString2 = TLVParser.findFromListByTagHexString(tLVByteArray, "DFDF56");
        byte[] valueByteArray3 = tLVObjectFindFromListByTagHexString2 != null ? tLVObjectFindFromListByTagHexString2.getValueByteArray() : null;
        TLVObject tLVObjectFindFromListByTagHexString3 = TLVParser.findFromListByTagHexString(tLVByteArray, "DFDF57");
        byte b = 0;
        if (tLVObjectFindFromListByTagHexString3 != null && (valueByteArray = tLVObjectFindFromListByTagHexString3.getValueByteArray()) != null && valueByteArray.length > 0) {
            b = valueByteArray[0];
        }
        return new PANData(valueByteArray2, valueByteArray3, b, getPINData(bArr));
    }

    public static BarCodeData getBarCodeData(byte[] bArr) {
        List<TLVObject> tLVByteArray;
        byte[] valueByteArray;
        if (bArr == null || (tLVByteArray = TLVParser.parseTLVByteArray(bArr)) == null) {
            return null;
        }
        TLVObject tLVObjectFindFromListByTagHexString = TLVParser.findFromListByTagHexString(tLVByteArray, "DF74");
        byte b = 0;
        if (tLVObjectFindFromListByTagHexString != null) {
            return new BarCodeData(tLVObjectFindFromListByTagHexString.getValueByteArray(), false);
        }
        TLVObject tLVObjectFindFromListByTagHexString2 = TLVParser.findFromListByTagHexString(tLVByteArray, "DFDF59");
        byte[] valueByteArray2 = tLVObjectFindFromListByTagHexString2 != null ? tLVObjectFindFromListByTagHexString2.getValueByteArray() : null;
        TLVObject tLVObjectFindFromListByTagHexString3 = TLVParser.findFromListByTagHexString(tLVByteArray, "DFDF50");
        byte[] valueByteArray3 = tLVObjectFindFromListByTagHexString3 != null ? tLVObjectFindFromListByTagHexString3.getValueByteArray() : null;
        TLVObject tLVObjectFindFromListByTagHexString4 = TLVParser.findFromListByTagHexString(tLVByteArray, "DFDF51");
        if (tLVObjectFindFromListByTagHexString4 != null && (valueByteArray = tLVObjectFindFromListByTagHexString4.getValueByteArray()) != null && valueByteArray.length > 0) {
            b = valueByteArray[0];
        }
        return new BarCodeData(valueByteArray2, true, b, valueByteArray3);
    }
}
