package com.magtek.mobile.android.mtcms;

/* JADX INFO: loaded from: classes.dex */
interface IMTCMSDevice {
    void close();

    boolean initialize(IMTService iMTService);

    boolean open();

    boolean sendCommand(byte[] bArr);
}
