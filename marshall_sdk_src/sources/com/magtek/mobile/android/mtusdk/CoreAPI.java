package com.magtek.mobile.android.mtusdk;

import android.content.Context;
import android.os.Handler;
import com.magtek.mobile.android.mtcms.MTDevice;
import com.magtek.mobile.android.mtlib.MTSCRA;
import com.magtek.mobile.android.mtusdk.cms.CMSAPI;
import com.magtek.mobile.android.mtusdk.mms.MMSAPI;
import com.magtek.mobile.android.mtusdk.mmx.IMMXDeviceAdapter;
import com.magtek.mobile.android.mtusdk.mmx.MMXDevice;
import com.magtek.mobile.android.mtusdk.ppscra.PPSCRAAPI;
import com.magtek.mobile.android.mtusdk.scra.SCRAAPI;
import com.magtek.mobile.android.ppscra.MagTekPPSCRA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class CoreAPI {
    private static int CORE_API_VERSION = 118;
    static Map<ConnectionType, String> ConnectionTypeString = new HashMap();

    public CoreAPI() {
        ConnectionTypeString.put(ConnectionType.USB, "USB");
        ConnectionTypeString.put(ConnectionType.BLUETOOTH_LE_EMV, "BLE");
        ConnectionTypeString.put(ConnectionType.BLUETOOTH_LE_EMVT, "BLE_T");
        ConnectionTypeString.put(ConnectionType.TCP, "TCP");
        ConnectionTypeString.put(ConnectionType.TCP_TLS, "TLS");
        ConnectionTypeString.put(ConnectionType.TCP_TLS_TRUST, "TLS_TRUST");
        ConnectionTypeString.put(ConnectionType.WEBSOCKET, "WEBSOCKET");
        ConnectionTypeString.put(ConnectionType.SERIAL, "COM");
        ConnectionTypeString.put(ConnectionType.AIDL, "AIDL");
        ConnectionTypeString.put(ConnectionType.VIRTUAL, "VDEV");
    }

    public static int getAPIVersion() {
        return CORE_API_VERSION;
    }

    public static MTSCRA createSCRA(Context context, Handler handler) {
        return new MTSCRA(context, handler);
    }

    public static MagTekPPSCRA createPPSCRA(Context context, Handler handler) {
        return new MagTekPPSCRA(context, handler);
    }

    public static MTDevice createCMSDevice(Context context, Handler handler) {
        return new MTDevice(context, handler);
    }

    public static MMXDevice createMMSDevice(Context context, IMMXDeviceAdapter iMMXDeviceAdapter) {
        return new MMXDevice(context, iMMXDeviceAdapter);
    }

    public static List<IDevice> getDeviceList(Context context, IDeviceListCallback iDeviceListCallback) {
        List<IDevice> deviceList = MMSAPI.getDeviceList(context);
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(deviceList);
        return arrayList;
    }

    public static List<IDevice> getDeviceList(Context context, List<DeviceType> list, IDeviceListCallback iDeviceListCallback) {
        ArrayList arrayList = new ArrayList();
        ListIterator<DeviceType> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            List<IDevice> deviceList = null;
            int i = AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtusdk$DeviceType[listIterator.next().ordinal()];
            if (i == 1) {
                deviceList = SCRAAPI.getDeviceList(context);
            } else if (i == 2) {
                deviceList = PPSCRAAPI.getDeviceList(context);
            } else if (i == 3) {
                deviceList = CMSAPI.getDeviceList(context);
            } else if (i == 4) {
                deviceList = MMSAPI.getDeviceList(context);
            }
            if (deviceList != null) {
                arrayList.addAll(deviceList);
            }
        }
        if (iDeviceListCallback != null) {
            new DeviceScanningThread(context, list, arrayList, iDeviceListCallback).start();
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtusdk.CoreAPI$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceType;

        static {
            int[] iArr = new int[DeviceType.values().length];
            $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceType = iArr;
            try {
                iArr[DeviceType.SCRA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceType[DeviceType.PPSCRA.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceType[DeviceType.CMF.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceType[DeviceType.MMS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static List<IDevice> getDeviceList(Context context, DeviceType deviceType, IDeviceListCallback iDeviceListCallback) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(deviceType);
        return getDeviceList(context, arrayList, iDeviceListCallback);
    }

    public static IDevice createDevice(Context context, DeviceType deviceType, ConnectionType connectionType, String str, String str2, String str3, String str4) {
        int i = AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtusdk$DeviceType[deviceType.ordinal()];
        if (i == 1) {
            return SCRAAPI.createDevice(context, connectionType, str, str2, str3, str4);
        }
        if (i == 2) {
            return PPSCRAAPI.createDevice(context, connectionType, str, str2, str3, str4);
        }
        if (i == 3) {
            return CMSAPI.createDevice(context, connectionType, str, str2, str3, str4);
        }
        if (i != 4) {
            return null;
        }
        return MMSAPI.createDevice(context, connectionType, str, str2, str3, str4);
    }

    public static String GetConnectionTypeString(ConnectionType connectionType) {
        return ConnectionTypeString.containsKey(connectionType) ? ConnectionTypeString.get(connectionType) : "";
    }
}
