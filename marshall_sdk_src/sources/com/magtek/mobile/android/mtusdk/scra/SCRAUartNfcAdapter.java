package com.magtek.mobile.android.mtusdk.scra;

/* JADX INFO: loaded from: classes.dex */
public interface SCRAUartNfcAdapter {
    void OnUartARQCReceived(byte[] bArr);

    void OnUartDebugInfo(String str);

    void OnUartDeviceResponse(String str);

    void OnUartDisplayMessageRequest(byte[] bArr);

    void OnUartTransactionResult(byte[] bArr);

    void OnUartTransactionStatus(byte[] bArr);

    void OnUartUserSelectionRequest(byte[] bArr);
}
