package com.magtek.mobile.android.mtusdk;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PANRequest {
    private List<PaymentMethod> mPaymentMethods;
    private byte mTimeout;

    public byte Timeout() {
        return this.mTimeout;
    }

    public List<PaymentMethod> PaymentMethods() {
        return this.mPaymentMethods;
    }

    public PANRequest() {
        this.mTimeout = (byte) 60;
        this.mPaymentMethods = null;
    }

    public PANRequest(byte b, List<PaymentMethod> list) {
        this.mTimeout = b;
        this.mPaymentMethods = list;
    }
}
