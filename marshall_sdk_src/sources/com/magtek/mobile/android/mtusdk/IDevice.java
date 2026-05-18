package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public interface IDevice {
    String Name();

    boolean cancelTransaction();

    IDeviceCapabilities getCapabilities();

    ConnectionInfo getConnectionInfo();

    ConnectionState getConnectionState();

    IDeviceConfiguration getDeviceConfiguration();

    IDeviceControl getDeviceControl();

    DeviceInfo getDeviceInfo();

    boolean requestPAN(PANRequest pANRequest, PINRequest pINRequest);

    boolean requestPIN(PINRequest pINRequest);

    boolean requestSignature();

    boolean sendAuthorization(IData iData);

    boolean sendSelection(IData iData);

    boolean startTransaction(ITransaction iTransaction);

    boolean subscribeAll(IEventSubscriber iEventSubscriber);

    boolean unsubscribeAll(IEventSubscriber iEventSubscriber);
}
