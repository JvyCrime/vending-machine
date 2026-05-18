package com.magtek.mobile.android.mtusdk.cms;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import com.magtek.mobile.android.mtusdk.ConnectionInfo;
import com.magtek.mobile.android.mtusdk.ConnectionType;
import com.magtek.mobile.android.mtusdk.CoreAPI;
import com.magtek.mobile.android.mtusdk.DeviceInfo;
import com.magtek.mobile.android.mtusdk.DeviceType;
import com.magtek.mobile.android.mtusdk.IDevice;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CMSAPI {
    public static IDevice createDevice(Context context, ConnectionType connectionType, String str, String str2, String str3, String str4) {
        String strGetConnectionTypeString = CoreAPI.GetConnectionTypeString(connectionType);
        return new CMSDevice(context, new ConnectionInfo(DeviceType.CMF, connectionType, str), new DeviceInfo("[" + strGetConnectionTypeString + " " + str2 + "] " + str3, str2, str4));
    }

    public static List<IDevice> getDeviceList(Context context) {
        return getUSBDevices(context);
    }

    private static List<IDevice> getUSBDevices(Context context) {
        ArrayList arrayList = new ArrayList();
        UsbManager usbManager = (UsbManager) context.getSystemService("usb");
        if (usbManager != null) {
            for (UsbDevice usbDevice : usbManager.getDeviceList().values()) {
                if (usbDevice != null && usbDevice.getVendorId() == 2049 && usbDevice.getProductId() == 27) {
                    IDevice iDeviceCreateDevice = createDevice(context, ConnectionType.USB, "", "oDynamo", usbDevice.getDeviceName(), "");
                    if (iDeviceCreateDevice != null) {
                        arrayList.add(iDeviceCreateDevice);
                    }
                }
            }
        }
        return arrayList;
    }
}
