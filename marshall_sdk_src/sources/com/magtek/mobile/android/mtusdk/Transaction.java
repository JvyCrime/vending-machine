package com.magtek.mobile.android.mtusdk;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Transaction implements ITransaction {
    private String mAmount;
    private String mCashBack;
    private byte[] mCurrencyCode;
    private byte[] mCurrencyExponent;
    private boolean mEMVOnly;
    private byte mEMVResponseFormat;
    private byte mManualEntryFormat;
    private byte mManualEntrySound;
    private byte mManualEntryType;
    private byte[] mMerchantCategory;
    private byte[] mMerchantCustomData;
    private byte[] mMerchantID;
    private byte mOverrideFinalTransactionMessage;
    private List<PaymentMethod> mPaymentMethods;
    private boolean mPreventMSRSignatureForCardWithICC;
    private boolean mQuickChip;
    private boolean mSuppressThankYouMessage;
    private byte mTimeout;
    private byte[] mTransactionCategory;
    private byte mTransactionType;

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public byte Timeout() {
        return this.mTimeout;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public List<PaymentMethod> PaymentMethods() {
        return this.mPaymentMethods;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public boolean QuickChip() {
        return this.mQuickChip;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public boolean EMVOnly() {
        return this.mEMVOnly;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public boolean PreventMSRSignatureForCardWithICC() {
        return this.mPreventMSRSignatureForCardWithICC;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public boolean SuppressThankYouMessage() {
        return this.mSuppressThankYouMessage;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public byte OverrideFinalTransactionMessage() {
        return this.mOverrideFinalTransactionMessage;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public byte EMVResponseFormat() {
        return this.mEMVResponseFormat;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public byte TransactionType() {
        return this.mTransactionType;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public String Amount() {
        return this.mAmount;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public String CashBack() {
        return this.mCashBack;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public byte[] CurrencyCode() {
        return this.mCurrencyCode;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public byte[] CurrencyExponent() {
        return this.mCurrencyExponent;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public byte[] TransactionCategory() {
        return this.mTransactionCategory;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public byte[] MerchantCategory() {
        return this.mMerchantCategory;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public byte[] MerchantID() {
        return this.mMerchantID;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public byte[] MerchantCustomData() {
        return this.mMerchantCustomData;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public byte ManualEntryType() {
        return this.mManualEntryType;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public byte ManualEntryFormat() {
        return this.mManualEntryFormat;
    }

    @Override // com.magtek.mobile.android.mtusdk.ITransaction
    public byte ManualEntrySound() {
        return this.mManualEntrySound;
    }

    public void setTimeout(byte b) {
        this.mTimeout = b;
    }

    public void setPaymentMethods(List<PaymentMethod> list) {
        this.mPaymentMethods = list;
    }

    public void setQuickChip(boolean z) {
        this.mQuickChip = z;
    }

    public void setEMVOnly(boolean z) {
        this.mEMVOnly = z;
    }

    public void setPreventMSRSignatureForCardWithICC(boolean z) {
        this.mPreventMSRSignatureForCardWithICC = z;
    }

    public void setSuppressThankYouMessage(boolean z) {
        this.mSuppressThankYouMessage = z;
    }

    public void setOverrideFinalTransactionMessage(byte b) {
        this.mOverrideFinalTransactionMessage = b;
    }

    public void setEMVResponseFormat(byte b) {
        this.mEMVResponseFormat = b;
    }

    public void setTransactionType(byte b) {
        this.mTransactionType = b;
    }

    public void setAmount(String str) {
        this.mAmount = str;
    }

    public void setCashBack(String str) {
        this.mCashBack = str;
    }

    public void setCurrencyCode(byte[] bArr) {
        this.mCurrencyCode = bArr;
    }

    public void setCurrencyExponent(byte[] bArr) {
        this.mCurrencyExponent = bArr;
    }

    public void setTransactionCategory(byte[] bArr) {
        this.mTransactionCategory = bArr;
    }

    public void setMerchantCategory(byte[] bArr) {
        this.mMerchantCategory = bArr;
    }

    public void setMerchantID(byte[] bArr) {
        this.mMerchantID = bArr;
    }

    public void setMerchantCustomData(byte[] bArr) {
        this.mMerchantCustomData = bArr;
    }

    public void setManualEntryType(byte b) {
        this.mManualEntryType = b;
    }

    public void setManualEntryFormat(byte b) {
        this.mManualEntryFormat = b;
    }

    public void setManualEntrySound(byte b) {
        this.mManualEntrySound = b;
    }

    public Transaction() {
        this.mQuickChip = false;
        this.mEMVOnly = false;
        this.mPreventMSRSignatureForCardWithICC = false;
        this.mSuppressThankYouMessage = false;
        init();
    }

    public Transaction(List<PaymentMethod> list, String str, String str2, boolean z, boolean z2) {
        this.mQuickChip = false;
        this.mEMVOnly = false;
        this.mPreventMSRSignatureForCardWithICC = false;
        this.mSuppressThankYouMessage = false;
        init();
        this.mPaymentMethods = list;
        this.mAmount = str;
        this.mCashBack = str2;
        this.mQuickChip = z;
        this.mEMVOnly = z2;
    }

    public Transaction(byte b, List<PaymentMethod> list, String str, String str2, boolean z, boolean z2, byte b2) {
        this.mQuickChip = false;
        this.mEMVOnly = false;
        this.mPreventMSRSignatureForCardWithICC = false;
        this.mSuppressThankYouMessage = false;
        init();
        this.mTimeout = b;
        this.mPaymentMethods = list;
        this.mAmount = str;
        this.mCashBack = str2;
        this.mQuickChip = z;
        this.mEMVOnly = z2;
        this.mTransactionType = b2;
    }

    protected void init() {
        this.mTimeout = (byte) 60;
        this.mQuickChip = true;
        this.mEMVOnly = true;
        this.mPreventMSRSignatureForCardWithICC = false;
        this.mSuppressThankYouMessage = false;
        this.mOverrideFinalTransactionMessage = (byte) 0;
        this.mEMVResponseFormat = (byte) 0;
        this.mTransactionType = (byte) 0;
        this.mCurrencyCode = null;
        this.mCurrencyExponent = null;
        this.mTransactionCategory = null;
        this.mMerchantCategory = null;
        this.mMerchantID = null;
        this.mMerchantCustomData = null;
        this.mManualEntryType = (byte) 0;
        this.mManualEntryFormat = (byte) 0;
        this.mManualEntrySound = (byte) 0;
    }
}
