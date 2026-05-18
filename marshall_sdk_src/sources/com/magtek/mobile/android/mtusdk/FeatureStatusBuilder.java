package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public class FeatureStatusBuilder {
    public static String FEATURE_PAN_ENTRY = "feature_pan_entry";
    public static String FEATURE_PIN_ENTRY = "feature_pin_entry";
    public static String FEATURE_SCAN_BAR_CODE = "feature_scan_bar_code";
    public static String FEATURE_SHOW_BAR_CODE = "feature_show_bar_code";
    public static String FEATURE_SIGNATURE_CAPTURE = "feature_signature_capture";
    public static String STATUS_CANCELLED = "status_cancelled";
    public static String STATUS_ERROR = "status_error";
    public static String STATUS_FAILED = "status_failed";
    public static String STATUS_HARDWARE_NA = "status_hardware_na";
    public static String STATUS_SUCCESS = "status_success";
    public static String STATUS_TIMED_OUT = "status_timed_out";

    public static DeviceFeature GetDeviceFeature(String str) {
        DeviceFeature deviceFeature;
        DeviceFeature deviceFeature2 = DeviceFeature.None;
        if (str.contains(",")) {
            String[] strArrSplit = str.split(",");
            if (strArrSplit.length > 0) {
                str = strArrSplit[0];
            }
        }
        if (str == null) {
            return deviceFeature2;
        }
        try {
            if (str.equalsIgnoreCase(FEATURE_SIGNATURE_CAPTURE)) {
                deviceFeature = DeviceFeature.SignatureCapture;
            } else if (str.equalsIgnoreCase(FEATURE_PIN_ENTRY)) {
                deviceFeature = DeviceFeature.PINEntry;
            } else if (str.equalsIgnoreCase(FEATURE_PAN_ENTRY)) {
                deviceFeature = DeviceFeature.PANEntry;
            } else if (str.equalsIgnoreCase(FEATURE_SHOW_BAR_CODE)) {
                deviceFeature = DeviceFeature.ShowBarCode;
            } else {
                if (!str.equalsIgnoreCase(FEATURE_SCAN_BAR_CODE)) {
                    return deviceFeature2;
                }
                deviceFeature = DeviceFeature.ScanBarCode;
            }
            return deviceFeature;
        } catch (Exception unused) {
            return deviceFeature2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static com.magtek.mobile.android.mtusdk.FeatureStatus GetFeatureStatus(java.lang.String r3) {
        /*
            com.magtek.mobile.android.mtusdk.FeatureStatus r0 = com.magtek.mobile.android.mtusdk.FeatureStatus.NoStatus
            java.lang.String r1 = ","
            boolean r2 = r3.contains(r1)
            if (r2 == 0) goto L15
            java.lang.String[] r3 = r3.split(r1)
            int r1 = r3.length
            if (r1 <= 0) goto L15
            r1 = 1
            r3 = r3[r1]
            goto L17
        L15:
            java.lang.String r3 = ""
        L17:
            if (r3 == 0) goto L5c
            java.lang.String r1 = com.magtek.mobile.android.mtusdk.FeatureStatusBuilder.STATUS_SUCCESS     // Catch: java.lang.Exception -> L5c
            boolean r1 = r3.equalsIgnoreCase(r1)     // Catch: java.lang.Exception -> L5c
            if (r1 == 0) goto L25
            com.magtek.mobile.android.mtusdk.FeatureStatus r3 = com.magtek.mobile.android.mtusdk.FeatureStatus.Success     // Catch: java.lang.Exception -> L5c
        L23:
            r0 = r3
            goto L5c
        L25:
            java.lang.String r1 = com.magtek.mobile.android.mtusdk.FeatureStatusBuilder.STATUS_FAILED     // Catch: java.lang.Exception -> L5c
            boolean r1 = r3.equalsIgnoreCase(r1)     // Catch: java.lang.Exception -> L5c
            if (r1 == 0) goto L30
            com.magtek.mobile.android.mtusdk.FeatureStatus r3 = com.magtek.mobile.android.mtusdk.FeatureStatus.Failed     // Catch: java.lang.Exception -> L5c
            goto L23
        L30:
            java.lang.String r1 = com.magtek.mobile.android.mtusdk.FeatureStatusBuilder.STATUS_TIMED_OUT     // Catch: java.lang.Exception -> L5c
            boolean r1 = r3.equalsIgnoreCase(r1)     // Catch: java.lang.Exception -> L5c
            if (r1 == 0) goto L3b
            com.magtek.mobile.android.mtusdk.FeatureStatus r3 = com.magtek.mobile.android.mtusdk.FeatureStatus.TimedOut     // Catch: java.lang.Exception -> L5c
            goto L23
        L3b:
            java.lang.String r1 = com.magtek.mobile.android.mtusdk.FeatureStatusBuilder.STATUS_CANCELLED     // Catch: java.lang.Exception -> L5c
            boolean r1 = r3.equalsIgnoreCase(r1)     // Catch: java.lang.Exception -> L5c
            if (r1 == 0) goto L46
            com.magtek.mobile.android.mtusdk.FeatureStatus r3 = com.magtek.mobile.android.mtusdk.FeatureStatus.Cancelled     // Catch: java.lang.Exception -> L5c
            goto L23
        L46:
            java.lang.String r1 = com.magtek.mobile.android.mtusdk.FeatureStatusBuilder.STATUS_ERROR     // Catch: java.lang.Exception -> L5c
            boolean r1 = r3.equalsIgnoreCase(r1)     // Catch: java.lang.Exception -> L5c
            if (r1 == 0) goto L51
            com.magtek.mobile.android.mtusdk.FeatureStatus r3 = com.magtek.mobile.android.mtusdk.FeatureStatus.Error     // Catch: java.lang.Exception -> L5c
            goto L23
        L51:
            java.lang.String r1 = com.magtek.mobile.android.mtusdk.FeatureStatusBuilder.STATUS_HARDWARE_NA     // Catch: java.lang.Exception -> L5c
            boolean r3 = r3.equalsIgnoreCase(r1)     // Catch: java.lang.Exception -> L5c
            if (r3 == 0) goto L5c
            com.magtek.mobile.android.mtusdk.FeatureStatus r3 = com.magtek.mobile.android.mtusdk.FeatureStatus.HardwareNA     // Catch: java.lang.Exception -> L5c
            goto L23
        L5c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.magtek.mobile.android.mtusdk.FeatureStatusBuilder.GetFeatureStatus(java.lang.String):com.magtek.mobile.android.mtusdk.FeatureStatus");
    }

    public static String GetFeatureString(DeviceFeature deviceFeature) {
        int i = AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtusdk$DeviceFeature[deviceFeature.ordinal()];
        if (i == 1) {
            return FEATURE_SIGNATURE_CAPTURE;
        }
        if (i == 2) {
            return FEATURE_PIN_ENTRY;
        }
        if (i == 3) {
            return FEATURE_PAN_ENTRY;
        }
        if (i != 4) {
            return i != 5 ? "" : FEATURE_SCAN_BAR_CODE;
        }
        return FEATURE_SHOW_BAR_CODE;
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtusdk.FeatureStatusBuilder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceFeature;
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtusdk$FeatureStatus;

        static {
            int[] iArr = new int[FeatureStatus.values().length];
            $SwitchMap$com$magtek$mobile$android$mtusdk$FeatureStatus = iArr;
            try {
                iArr[FeatureStatus.Success.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$FeatureStatus[FeatureStatus.Failed.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$FeatureStatus[FeatureStatus.TimedOut.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$FeatureStatus[FeatureStatus.Cancelled.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$FeatureStatus[FeatureStatus.Error.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$FeatureStatus[FeatureStatus.HardwareNA.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            int[] iArr2 = new int[DeviceFeature.values().length];
            $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceFeature = iArr2;
            try {
                iArr2[DeviceFeature.SignatureCapture.ordinal()] = 1;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceFeature[DeviceFeature.PINEntry.ordinal()] = 2;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceFeature[DeviceFeature.PANEntry.ordinal()] = 3;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceFeature[DeviceFeature.ShowBarCode.ordinal()] = 4;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceFeature[DeviceFeature.ScanBarCode.ordinal()] = 5;
            } catch (NoSuchFieldError unused11) {
            }
        }
    }

    public static String GetStatusString(FeatureStatus featureStatus) {
        switch (AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtusdk$FeatureStatus[featureStatus.ordinal()]) {
            case 1:
                return STATUS_SUCCESS;
            case 2:
                return STATUS_FAILED;
            case 3:
                return STATUS_TIMED_OUT;
            case 4:
                return STATUS_CANCELLED;
            case 5:
                return STATUS_ERROR;
            case 6:
                return STATUS_HARDWARE_NA;
            default:
                return "";
        }
    }
}
