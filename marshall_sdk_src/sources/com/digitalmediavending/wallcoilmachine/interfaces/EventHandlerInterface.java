package com.digitalmediavending.wallcoilmachine.interfaces;

/* JADX INFO: loaded from: classes.dex */
public interface EventHandlerInterface {
    void causedException(String str);

    void onDoorResponse(String str);

    void onHeartBeatReceived(String str);

    void onResetTrays(String str);

    void onRxData(String str);

    void onScanTrays(String str);

    void onTxData(String str);

    void onVendingResponseReceived(String str);

    void unableToParse(String str);
}
