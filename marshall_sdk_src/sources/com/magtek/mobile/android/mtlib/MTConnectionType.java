package com.magtek.mobile.android.mtlib;

/* JADX INFO: loaded from: classes.dex */
public enum MTConnectionType {
    Unknown(0),
    Audio(1),
    BLE(2),
    BLEEMV(3),
    Bluetooth(4),
    USB(5),
    Serial(6),
    Net(7),
    Net_TLS12(8),
    Net_TLS12_Trust_All(9),
    BLEEMVT(10),
    AIDL(11);

    private int mValue;

    MTConnectionType(int i) {
        this.mValue = i;
    }
}
