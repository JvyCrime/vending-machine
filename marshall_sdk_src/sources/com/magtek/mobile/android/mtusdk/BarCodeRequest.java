package com.magtek.mobile.android.mtusdk;

import com.magtek.mobile.android.ppscra.PPSCRADeviceValues;

/* JADX INFO: loaded from: classes.dex */
public class BarCodeRequest {
    private byte[] mBackgroundColor;
    private byte[] mBlockColor;
    private byte[] mData;
    private byte mErrorCorrection;
    private BarCodeFormat mFormat;
    private byte mMaskPattern;
    private byte mMaxVersion;
    private byte mMinVersion;
    private BarCodeType mType;

    public BarCodeType Type() {
        return this.mType;
    }

    public BarCodeFormat Format() {
        return this.mFormat;
    }

    public byte[] Data() {
        return this.mData;
    }

    public byte[] BlockColor() {
        return this.mBlockColor;
    }

    public byte[] BackgroundColor() {
        return this.mBackgroundColor;
    }

    public byte ErrorCorrection() {
        return this.mErrorCorrection;
    }

    public byte MaskPattern() {
        return this.mMaskPattern;
    }

    public byte MinVersion() {
        return this.mMinVersion;
    }

    public byte MaxVersion() {
        return this.mMaxVersion;
    }

    public BarCodeRequest(BarCodeType barCodeType, BarCodeFormat barCodeFormat, byte[] bArr) {
        this.mType = barCodeType;
        this.mFormat = barCodeFormat;
        this.mData = bArr;
        this.mBlockColor = new byte[]{0, 0, 0};
        this.mBackgroundColor = new byte[]{-1, -1, -1};
        this.mErrorCorrection = (byte) 0;
        this.mMaskPattern = (byte) -1;
        this.mMinVersion = (byte) 1;
        this.mMaxVersion = PPSCRADeviceValues.RESPONSE_SIGNATURE_CAPTURE_STATE;
    }

    public BarCodeRequest(BarCodeType barCodeType, BarCodeFormat barCodeFormat, byte[] bArr, byte[] bArr2, byte[] bArr3, byte b, byte b2, byte b3, byte b4) {
        this.mType = barCodeType;
        this.mFormat = barCodeFormat;
        this.mData = bArr;
        this.mBlockColor = bArr2;
        this.mBackgroundColor = bArr3;
        this.mErrorCorrection = b;
        this.mMaskPattern = b2;
        this.mMinVersion = b3;
        this.mMaxVersion = b4;
    }
}
