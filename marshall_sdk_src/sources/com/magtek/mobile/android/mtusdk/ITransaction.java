package com.magtek.mobile.android.mtusdk;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface ITransaction {
    String Amount();

    String CashBack();

    byte[] CurrencyCode();

    byte[] CurrencyExponent();

    boolean EMVOnly();

    byte EMVResponseFormat();

    byte ManualEntryFormat();

    byte ManualEntrySound();

    byte ManualEntryType();

    byte[] MerchantCategory();

    byte[] MerchantCustomData();

    byte[] MerchantID();

    byte OverrideFinalTransactionMessage();

    List<PaymentMethod> PaymentMethods();

    boolean PreventMSRSignatureForCardWithICC();

    boolean QuickChip();

    boolean SuppressThankYouMessage();

    byte Timeout();

    byte[] TransactionCategory();

    byte TransactionType();
}
