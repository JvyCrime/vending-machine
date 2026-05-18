package com.magtek.mobile.android.mtusdk;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class BaseDeviceCapabilities implements IDeviceCapabilities {
    private boolean mAutoSignatureCapture;
    private boolean mBatteryBackedClock;
    private boolean mDisplay;
    private boolean mMSRPowerSaver;
    private boolean mPINPad;
    private List<PaymentMethod> mPaymentMethods;
    private boolean mSRED;
    private boolean mSignature;

    @Override // com.magtek.mobile.android.mtusdk.IDeviceCapabilities
    public List<PaymentMethod> PaymentMethods() {
        return this.mPaymentMethods;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceCapabilities
    public boolean Display() {
        return this.mDisplay;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceCapabilities
    public boolean PINPad() {
        return this.mPINPad;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceCapabilities
    public boolean Signature() {
        return this.mSignature;
    }

    public boolean AutoSignatureCapture() {
        return this.mAutoSignatureCapture;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceCapabilities
    public boolean SRED() {
        return this.mSRED;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceCapabilities
    public boolean MSRPowerSaver() {
        return this.mMSRPowerSaver;
    }

    @Override // com.magtek.mobile.android.mtusdk.IDeviceCapabilities
    public boolean BatteryBackedClock() {
        return this.mBatteryBackedClock;
    }

    public BaseDeviceCapabilities(List<PaymentMethod> list, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
        this.mPaymentMethods = list;
        this.mDisplay = z;
        this.mPINPad = z2;
        this.mSignature = z3;
        this.mAutoSignatureCapture = z4;
        this.mSRED = z5;
        this.mMSRPowerSaver = z6;
        this.mBatteryBackedClock = z7;
    }
}
