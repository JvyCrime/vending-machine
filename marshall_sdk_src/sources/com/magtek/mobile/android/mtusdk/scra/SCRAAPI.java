package com.magtek.mobile.android.mtusdk.scra;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.os.ParcelUuid;
import android.util.Log;
import com.magtek.mobile.android.mtlib.MTDeviceConstants;
import com.magtek.mobile.android.mtusdk.ConnectionInfo;
import com.magtek.mobile.android.mtusdk.ConnectionType;
import com.magtek.mobile.android.mtusdk.CoreAPI;
import com.magtek.mobile.android.mtusdk.DeviceInfo;
import com.magtek.mobile.android.mtusdk.DeviceType;
import com.magtek.mobile.android.mtusdk.IDevice;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class SCRAAPI {
    private static final String TAG = "SCRAAPI";

    public static IDevice createDevice(Context context, ConnectionType connectionType, String str, String str2, String str3, String str4) {
        String strGetConnectionTypeString = CoreAPI.GetConnectionTypeString(connectionType);
        return new SCRADevice(context, new ConnectionInfo(DeviceType.SCRA, connectionType, str), new DeviceInfo("[" + strGetConnectionTypeString + " " + str2 + "] " + str3, str2, str4));
    }

    public static List<IDevice> getDeviceList(Context context) {
        List<IDevice> uSBDevices = getUSBDevices(context);
        List<IDevice> bLEDevices = getBLEDevices(context);
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(uSBDevices);
        arrayList.addAll(bLEDevices);
        return arrayList;
    }

    private static List<IDevice> getBLEDevices(Context context) {
        ParcelUuid[] uuids;
        ArrayList arrayList = new ArrayList();
        BluetoothAdapter adapter = ((BluetoothManager) context.getSystemService("bluetooth")).getAdapter();
        if (adapter != null) {
            String str = TAG;
            Log.i(str, "Get bonded BLE devices");
            Set<BluetoothDevice> bondedDevices = adapter.getBondedDevices();
            if (bondedDevices != null) {
                Log.i(str, "Bonded BLE devices=" + bondedDevices.size());
                for (BluetoothDevice bluetoothDevice : bondedDevices) {
                    if (bluetoothDevice != null && (uuids = bluetoothDevice.getUuids()) != null) {
                        Log.i(TAG, "UUID Count=" + uuids.length);
                        for (ParcelUuid parcelUuid : uuids) {
                            String str2 = TAG;
                            Log.i(str2, "UUID=" + parcelUuid.getUuid().toString());
                            if (parcelUuid.getUuid().compareTo(MTDeviceConstants.UUID_SCRA_BLE_EMV_DEVICE_READER_SERVICE) == 0) {
                                Log.i(str2, "Found eDynamo BLE");
                                arrayList.add(createDevice(context, ConnectionType.BLUETOOTH_LE_EMV, "(BLE address)", "eDynamo", "eDynamo BLE", ""));
                            } else {
                                Log.i(str2, "UUID=" + parcelUuid.getUuid().toString());
                            }
                        }
                    }
                }
            }
        }
        return arrayList;
    }

    private static boolean isDeviceFound(UsbDevice usbDevice) {
        if (usbDevice.getVendorId() == MTDeviceConstants.VID_MAGTEK) {
            int productId = usbDevice.getProductId();
            int length = MTDeviceConstants.PID_SCRA.length;
            for (int i = 0; i < length; i++) {
                if (productId == MTDeviceConstants.PID_SCRA[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0058 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x001c A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.util.List<com.magtek.mobile.android.mtusdk.IDevice> getUSBDevices(android.content.Context r10) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.String r1 = "usb"
            java.lang.Object r1 = r10.getSystemService(r1)
            android.hardware.usb.UsbManager r1 = (android.hardware.usb.UsbManager) r1
            if (r1 == 0) goto L5c
            java.util.HashMap r1 = r1.getDeviceList()
            java.util.Collection r1 = r1.values()
            java.util.Iterator r1 = r1.iterator()
        L1c:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L5c
            java.lang.Object r2 = r1.next()
            android.hardware.usb.UsbDevice r2 = (android.hardware.usb.UsbDevice) r2
            if (r2 == 0) goto L1c
            boolean r3 = isDeviceFound(r2)
            if (r3 == 0) goto L1c
            java.lang.String r6 = r2.getDeviceName()
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 21
            if (r3 < r4) goto L49
            java.lang.String r2 = r2.getSerialNumber()
            if (r2 == 0) goto L4b
            boolean r3 = r2.isEmpty()
            if (r3 != 0) goto L4b
            r8 = r2
            r9 = r8
            goto L4d
        L49:
            java.lang.String r2 = ""
        L4b:
            r9 = r2
            r8 = r6
        L4d:
            com.magtek.mobile.android.mtusdk.ConnectionType r5 = com.magtek.mobile.android.mtusdk.ConnectionType.USB
            java.lang.String r7 = "SCRA"
            r4 = r10
            com.magtek.mobile.android.mtusdk.IDevice r2 = createDevice(r4, r5, r6, r7, r8, r9)
            if (r2 == 0) goto L1c
            r0.add(r2)
            goto L1c
        L5c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.magtek.mobile.android.mtusdk.scra.SCRAAPI.getUSBDevices(android.content.Context):java.util.List");
    }
}
