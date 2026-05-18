package com.magtek.mobile.android.mtusdk.cms;

import com.magtek.mobile.android.mtusdk.BaseDeviceCapabilities;
import com.magtek.mobile.android.mtusdk.TransactionBuilder;

/* JADX INFO: loaded from: classes.dex */
public class CMSDeviceCapabilities extends BaseDeviceCapabilities {
    public CMSDeviceCapabilities() {
        super(TransactionBuilder.GetPaymentMethods(true, true, false, false), false, false, false, false, false, false, true);
    }
}
