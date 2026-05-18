package com.magtek.mobile.android.mtusdk;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface IDeviceCapabilities {
    boolean BatteryBackedClock();

    boolean Display();

    boolean MSRPowerSaver();

    boolean PINPad();

    List<PaymentMethod> PaymentMethods();

    boolean SRED();

    boolean Signature();
}
