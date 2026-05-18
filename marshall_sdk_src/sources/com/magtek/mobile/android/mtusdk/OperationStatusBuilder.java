package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public class OperationStatusBuilder {
    public static String OPERATION_DONE = "operation_done";
    public static String OPERATION_FAILED = "operation_failed";
    public static String OPERATION_STARTED = "operation_started";
    public static String OPERATION_WARNING = "operation_warning";

    public static OperationStatus GetStatusCode(String str) {
        OperationStatus operationStatus;
        OperationStatus operationStatus2 = OperationStatus.NoStatus;
        if (str.contains(",")) {
            String[] strArrSplit = str.split(",");
            if (strArrSplit.length > 0) {
                str = strArrSplit[0];
            }
        }
        if (str == null) {
            return operationStatus2;
        }
        try {
            if (str.equalsIgnoreCase(OPERATION_STARTED)) {
                operationStatus = OperationStatus.Started;
            } else if (str.equalsIgnoreCase(OPERATION_WARNING)) {
                operationStatus = OperationStatus.Warning;
            } else if (str.equalsIgnoreCase(OPERATION_FAILED)) {
                operationStatus = OperationStatus.Failed;
            } else {
                if (!str.equalsIgnoreCase(OPERATION_DONE)) {
                    return operationStatus2;
                }
                operationStatus = OperationStatus.Done;
            }
            return operationStatus;
        } catch (Exception unused) {
            return operationStatus2;
        }
    }

    public static String GetOperationDetail(String str) {
        if (str.contains(",")) {
            String[] strArrSplit = str.split(",");
            if (strArrSplit.length > 1) {
                return strArrSplit[1];
            }
        }
        return "";
    }

    public static String GetStatusDetail(String str) {
        if (str.contains(",")) {
            String[] strArrSplit = str.split(",");
            if (strArrSplit.length > 2) {
                return strArrSplit[2];
            }
        }
        return "";
    }

    public static String GetDeviceDetail(String str) {
        if (str.contains(",")) {
            String[] strArrSplit = str.split(",");
            if (strArrSplit.length > 3) {
                return strArrSplit[3];
            }
        }
        return "";
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtusdk.OperationStatusBuilder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtusdk$OperationStatus;

        static {
            int[] iArr = new int[OperationStatus.values().length];
            $SwitchMap$com$magtek$mobile$android$mtusdk$OperationStatus = iArr;
            try {
                iArr[OperationStatus.Started.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$OperationStatus[OperationStatus.Warning.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$OperationStatus[OperationStatus.Failed.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$OperationStatus[OperationStatus.Done.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static String GetString(OperationStatus operationStatus) {
        int i = AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtusdk$OperationStatus[operationStatus.ordinal()];
        if (i == 1) {
            return OPERATION_STARTED;
        }
        if (i == 2) {
            return OPERATION_WARNING;
        }
        if (i != 3) {
            return i != 4 ? "" : OPERATION_DONE;
        }
        return OPERATION_FAILED;
    }
}
