package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public class PINRequest {
    private byte mFormat;
    private byte mMaxLength;
    private byte mMinLength;
    private String mPAN;
    private byte mPINMode;
    private byte mTimeout;
    private byte mTone;

    public byte Timeout() {
        return this.mTimeout;
    }

    public byte PINMode() {
        return this.mPINMode;
    }

    public byte MinLength() {
        return this.mMinLength;
    }

    public byte MaxLength() {
        return this.mMaxLength;
    }

    public byte Tone() {
        return this.mTone;
    }

    public byte Format() {
        return this.mFormat;
    }

    public String PAN() {
        return this.mPAN;
    }

    public PINRequest() {
        this.mTimeout = (byte) 60;
        this.mPINMode = (byte) 0;
        this.mMinLength = (byte) 4;
        this.mMaxLength = (byte) 12;
        this.mTone = (byte) 1;
        this.mFormat = (byte) 1;
        this.mPAN = "";
    }

    public PINRequest(byte b, byte b2, byte b3, byte b4, byte b5, byte b6, String str) {
        this.mTimeout = b;
        this.mPINMode = b2;
        this.mMinLength = b3;
        this.mMaxLength = b4;
        this.mTone = b5;
        this.mFormat = b6;
        this.mPAN = str;
    }
}
