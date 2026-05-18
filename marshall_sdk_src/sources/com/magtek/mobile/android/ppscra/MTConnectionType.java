package com.magtek.mobile.android.ppscra;

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
    BLEEMVT(10);

    private int a;

    MTConnectionType(int i) {
        this.a = i;
    }
}
