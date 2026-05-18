package com.magtek.mobile.android.mtusdk;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TransactionBuilder {
    public static List<PaymentMethod> GetPaymentMethods(boolean z, boolean z2, boolean z3, boolean z4) {
        ArrayList arrayList = new ArrayList();
        if (z) {
            arrayList.add(PaymentMethod.MSR);
        }
        if (z2) {
            arrayList.add(PaymentMethod.Contact);
        }
        if (z3) {
            arrayList.add(PaymentMethod.Contactless);
        }
        if (z4) {
            arrayList.add(PaymentMethod.ManualEntry);
        }
        return arrayList;
    }
}
