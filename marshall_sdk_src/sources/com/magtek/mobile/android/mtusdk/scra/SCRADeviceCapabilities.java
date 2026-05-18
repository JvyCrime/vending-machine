package com.magtek.mobile.android.mtusdk.scra;

import com.magtek.mobile.android.mtusdk.BaseDeviceCapabilities;
import com.magtek.mobile.android.mtusdk.TransactionBuilder;

/* JADX INFO: loaded from: classes.dex */
public class SCRADeviceCapabilities extends BaseDeviceCapabilities {
    public SCRADeviceCapabilities(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        super(TransactionBuilder.GetPaymentMethods(z, z2, z3, z4), false, false, false, false, false, z5, z6);
    }
}
