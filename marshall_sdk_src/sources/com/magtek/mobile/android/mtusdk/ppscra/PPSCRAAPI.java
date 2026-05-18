package com.magtek.mobile.android.mtusdk.ppscra;

import android.content.Context;
import com.magtek.mobile.android.mtlib.MTParser;
import com.magtek.mobile.android.mtusdk.ConnectionInfo;
import com.magtek.mobile.android.mtusdk.ConnectionType;
import com.magtek.mobile.android.mtusdk.CoreAPI;
import com.magtek.mobile.android.mtusdk.DeviceInfo;
import com.magtek.mobile.android.mtusdk.DeviceType;
import com.magtek.mobile.android.mtusdk.IDevice;
import com.magtek.mobile.android.mtusdk.PINData;
import com.magtek.mobile.android.mtusdk.common.TLVParser;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PPSCRAAPI {
    public static IDevice createDevice(Context context, ConnectionType connectionType, String str, String str2, String str3, String str4) {
        String strGetConnectionTypeString = CoreAPI.GetConnectionTypeString(connectionType);
        return new PPSCRADevice(context, new ConnectionInfo(DeviceType.PPSCRA, connectionType, str), new DeviceInfo("[" + strGetConnectionTypeString + " " + str2 + "] " + str3, str2, str4));
    }

    public static List<IDevice> getDeviceList(Context context) {
        return getUSBDevices(context);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0062 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x001c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.util.List<com.magtek.mobile.android.mtusdk.IDevice> getUSBDevices(android.content.Context r11) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.String r1 = "usb"
            java.lang.Object r1 = r11.getSystemService(r1)
            android.hardware.usb.UsbManager r1 = (android.hardware.usb.UsbManager) r1
            if (r1 == 0) goto L66
            java.util.HashMap r1 = r1.getDeviceList()
            java.util.Collection r1 = r1.values()
            java.util.Iterator r1 = r1.iterator()
        L1c:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L66
            java.lang.Object r2 = r1.next()
            android.hardware.usb.UsbDevice r2 = (android.hardware.usb.UsbDevice) r2
            if (r2 == 0) goto L1c
            int r3 = r2.getVendorId()
            r4 = 2049(0x801, float:2.871E-42)
            if (r3 != r4) goto L1c
            int r3 = r2.getProductId()
            r4 = 12292(0x3004, float:1.7225E-41)
            if (r3 != r4) goto L1c
            java.lang.String r7 = r2.getDeviceName()
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 21
            if (r3 < r4) goto L53
            java.lang.String r2 = r2.getSerialNumber()
            if (r2 == 0) goto L55
            boolean r3 = r2.isEmpty()
            if (r3 != 0) goto L55
            r9 = r2
            r10 = r9
            goto L57
        L53:
            java.lang.String r2 = ""
        L55:
            r10 = r2
            r9 = r7
        L57:
            com.magtek.mobile.android.mtusdk.ConnectionType r6 = com.magtek.mobile.android.mtusdk.ConnectionType.USB
            java.lang.String r8 = "PPSCRA"
            r5 = r11
            com.magtek.mobile.android.mtusdk.IDevice r2 = createDevice(r5, r6, r7, r8, r9, r10)
            if (r2 == 0) goto L1c
            r0.add(r2)
            goto L1c
        L66:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.magtek.mobile.android.mtusdk.ppscra.PPSCRAAPI.getUSBDevices(android.content.Context):java.util.List");
    }

    public static PINData getPINData(byte[] bArr) {
        if (bArr != null) {
            String textString = TLVParser.getTextString(bArr, 0);
            if (textString.contains(",")) {
                String[] strArrSplit = textString.split(",");
                if (strArrSplit.length >= 2) {
                    return new PINData(MTParser.getByteArrayFromHexString(strArrSplit[0]), MTParser.getByteArrayFromHexString(strArrSplit[1]), (byte) 0, (byte) 0);
                }
            }
        }
        return null;
    }
}
