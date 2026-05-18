package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public enum TransactionStatus {
    NoStatus,
    NoTransaction,
    CardSwiped,
    CardInserted,
    CardRemoved,
    CardDetected,
    CardCollision,
    TimedOut,
    HostCancelled,
    TransactionCancelled,
    TransactionInProgress,
    TransactionError,
    TransactionApproved,
    TransactionDeclined,
    TransactionCompleted,
    TransactionFailed,
    TransactionNotAccepted,
    SignatureCaptureRequested,
    TechnicalFallback,
    QuickChipDeferred,
    DataEntered,
    TryAnotherInterface
}
