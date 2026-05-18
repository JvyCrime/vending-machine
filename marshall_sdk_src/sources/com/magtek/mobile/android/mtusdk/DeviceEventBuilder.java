package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public class DeviceEventBuilder {
    public static String DEVICE_RESET_OCCURRED = "device_reset_occurred";
    public static String DEVICE_RESET_WILL_OCCUR = "device_reset_will_occur";

    public static DeviceEvent GetEventValue(String str) {
        DeviceEvent deviceEvent;
        DeviceEvent deviceEvent2 = DeviceEvent.None;
        if (str.contains(",")) {
            String[] strArrSplit = str.split(",");
            if (strArrSplit.length > 0) {
                str = strArrSplit[0];
            }
        }
        if (str == null) {
            return deviceEvent2;
        }
        try {
            if (str.equalsIgnoreCase(DEVICE_RESET_OCCURRED)) {
                deviceEvent = DeviceEvent.DeviceResetOccurred;
            } else {
                if (!str.equalsIgnoreCase(DEVICE_RESET_WILL_OCCUR)) {
                    return deviceEvent2;
                }
                deviceEvent = DeviceEvent.DeviceResetWillOccur;
            }
            return deviceEvent;
        } catch (Exception unused) {
            return deviceEvent2;
        }
    }

    public static String GetDetail(String str) {
        if (str.contains(",")) {
            String[] strArrSplit = str.split(",");
            if (strArrSplit.length > 1) {
                return strArrSplit[1];
            }
        }
        return "";
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtusdk.DeviceEventBuilder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceEvent;

        static {
            int[] iArr = new int[DeviceEvent.values().length];
            $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceEvent = iArr;
            try {
                iArr[DeviceEvent.DeviceResetOccurred.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceEvent[DeviceEvent.DeviceResetWillOccur.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static String GetString(DeviceEvent deviceEvent) {
        int i = AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtusdk$DeviceEvent[deviceEvent.ordinal()];
        if (i != 1) {
            return i != 2 ? "" : DEVICE_RESET_WILL_OCCUR;
        }
        return DEVICE_RESET_OCCURRED;
    }
}
