package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public class TransactionStatusBuilder {
    public static String CARD_COLLISION = "card_collision";
    public static String CARD_DETECTED = "card_detected";
    public static String CARD_INSERTED = "card_inserted";
    public static String CARD_REMOVED = "card_removed";
    public static String CARD_SWIPED = "card_swiped";
    public static String DATA_ENTERED = "data_entered";
    public static String HOST_CANCELLED = "host_cancelled";
    public static String QUICK_CHIP_DEFERRED = "quick_chip_deferred";
    public static String SIGNATURE_CAPTURE_REQUESTED = "signature_capture_requested";
    public static String TECHNICAL_FALLBACK = "technical_fallback";
    public static String TIMED_OUT = "timed_out";
    public static String TRANSACTION_APPROVED = "transaction_approved";
    public static String TRANSACTION_CANCELLED = "transaction_cancelled";
    public static String TRANSACTION_COMPLETED = "transaction_completed";
    public static String TRANSACTION_DECLINED = "transaction_declined";
    public static String TRANSACTION_ERROR = "transaction_error";
    public static String TRANSACTION_FAILED = "transaction_failed";
    public static String TRANSACTION_IN_PROGRESS = "transaction_in_progress";
    public static String TRANSACTION_NOT_ACCEPTED = "transaction_not_accepted";
    public static String TRY_ANOTHER_INTERFACE = "try_another_interface";

    public static TransactionStatus GetStatusCode(String str) {
        TransactionStatus transactionStatus;
        TransactionStatus transactionStatus2 = TransactionStatus.NoStatus;
        if (str.contains(",")) {
            String[] strArrSplit = str.split(",");
            if (strArrSplit.length > 0) {
                str = strArrSplit[0];
            }
        }
        if (str == null) {
            return transactionStatus2;
        }
        try {
            if (str.equalsIgnoreCase(CARD_SWIPED)) {
                transactionStatus = TransactionStatus.CardSwiped;
            } else if (str.equalsIgnoreCase(CARD_INSERTED)) {
                transactionStatus = TransactionStatus.CardInserted;
            } else if (str.equalsIgnoreCase(CARD_REMOVED)) {
                transactionStatus = TransactionStatus.CardRemoved;
            } else if (str.equalsIgnoreCase(CARD_DETECTED)) {
                transactionStatus = TransactionStatus.CardDetected;
            } else if (str.equalsIgnoreCase(CARD_COLLISION)) {
                transactionStatus = TransactionStatus.CardCollision;
            } else if (str.equalsIgnoreCase(TIMED_OUT)) {
                transactionStatus = TransactionStatus.TimedOut;
            } else if (str.equalsIgnoreCase(HOST_CANCELLED)) {
                transactionStatus = TransactionStatus.HostCancelled;
            } else if (str.equalsIgnoreCase(TRANSACTION_CANCELLED)) {
                transactionStatus = TransactionStatus.TransactionCancelled;
            } else if (str.equalsIgnoreCase(TRANSACTION_IN_PROGRESS)) {
                transactionStatus = TransactionStatus.TransactionInProgress;
            } else if (str.equalsIgnoreCase(TRANSACTION_ERROR)) {
                transactionStatus = TransactionStatus.TransactionError;
            } else if (str.equalsIgnoreCase(TRANSACTION_APPROVED)) {
                transactionStatus = TransactionStatus.TransactionApproved;
            } else if (str.equalsIgnoreCase(TRANSACTION_DECLINED)) {
                transactionStatus = TransactionStatus.TransactionDeclined;
            } else if (str.equalsIgnoreCase(TRANSACTION_COMPLETED)) {
                transactionStatus = TransactionStatus.TransactionCompleted;
            } else if (str.equalsIgnoreCase(TRANSACTION_FAILED)) {
                transactionStatus = TransactionStatus.TransactionFailed;
            } else if (str.equalsIgnoreCase(TRANSACTION_NOT_ACCEPTED)) {
                transactionStatus = TransactionStatus.TransactionNotAccepted;
            } else if (str.equalsIgnoreCase(SIGNATURE_CAPTURE_REQUESTED)) {
                transactionStatus = TransactionStatus.SignatureCaptureRequested;
            } else if (str.equalsIgnoreCase(TECHNICAL_FALLBACK)) {
                transactionStatus = TransactionStatus.TechnicalFallback;
            } else if (str.equalsIgnoreCase(QUICK_CHIP_DEFERRED)) {
                transactionStatus = TransactionStatus.QuickChipDeferred;
            } else if (str.equalsIgnoreCase(DATA_ENTERED)) {
                transactionStatus = TransactionStatus.DataEntered;
            } else {
                if (!str.equalsIgnoreCase(TRY_ANOTHER_INTERFACE)) {
                    return transactionStatus2;
                }
                transactionStatus = TransactionStatus.TryAnotherInterface;
            }
            return transactionStatus;
        } catch (Exception unused) {
            return transactionStatus2;
        }
    }

    public static String GetStatusDetail(String str) {
        if (str.contains(",")) {
            String[] strArrSplit = str.split(",");
            if (strArrSplit.length > 1) {
                return strArrSplit[1];
            }
        }
        return "";
    }

    public static String GetDeviceDetail(String str) {
        if (str.contains(",")) {
            String[] strArrSplit = str.split(",");
            if (strArrSplit.length > 2) {
                return strArrSplit[2];
            }
        }
        return "";
    }

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtusdk.TransactionStatusBuilder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus;

        static {
            int[] iArr = new int[TransactionStatus.values().length];
            $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus = iArr;
            try {
                iArr[TransactionStatus.CardSwiped.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.CardInserted.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.CardRemoved.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.CardDetected.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.CardCollision.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.TimedOut.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.HostCancelled.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.TransactionCancelled.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.TransactionInProgress.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.TransactionError.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.TransactionApproved.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.TransactionDeclined.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.TransactionCompleted.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.TransactionFailed.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.TransactionNotAccepted.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.SignatureCaptureRequested.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.TechnicalFallback.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.QuickChipDeferred.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.DataEntered.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[TransactionStatus.TryAnotherInterface.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
        }
    }

    public static String GetString(TransactionStatus transactionStatus) {
        switch (AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtusdk$TransactionStatus[transactionStatus.ordinal()]) {
            case 1:
                return CARD_SWIPED;
            case 2:
                return CARD_INSERTED;
            case 3:
                return CARD_REMOVED;
            case 4:
                return CARD_DETECTED;
            case 5:
                return CARD_COLLISION;
            case 6:
                return TIMED_OUT;
            case 7:
                return HOST_CANCELLED;
            case 8:
                return TRANSACTION_CANCELLED;
            case 9:
                return TRANSACTION_IN_PROGRESS;
            case 10:
                return TRANSACTION_ERROR;
            case 11:
                return TRANSACTION_APPROVED;
            case 12:
                return TRANSACTION_DECLINED;
            case 13:
                return TRANSACTION_COMPLETED;
            case 14:
                return TRANSACTION_FAILED;
            case 15:
                return TRANSACTION_NOT_ACCEPTED;
            case 16:
                return SIGNATURE_CAPTURE_REQUESTED;
            case 17:
                return TECHNICAL_FALLBACK;
            case 18:
                return QUICK_CHIP_DEFERRED;
            case 19:
                return DATA_ENTERED;
            case 20:
                return TRY_ANOTHER_INTERFACE;
            default:
                return "";
        }
    }
}
