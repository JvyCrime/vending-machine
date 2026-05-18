package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public class ImageData {
    private byte[] mBackgroundColor;
    private byte[] mData;
    private ImageType mType;

    public ImageType Type() {
        return this.mType;
    }

    public byte[] Data() {
        return this.mData;
    }

    public byte[] BackgroundColor() {
        return this.mBackgroundColor;
    }

    public ImageData(ImageType imageType, byte[] bArr) {
        this.mType = imageType;
        this.mData = bArr;
        this.mBackgroundColor = new byte[]{-1, -1, -1};
    }

    public ImageData(ImageType imageType, byte[] bArr, byte[] bArr2) {
        this.mType = imageType;
        this.mData = bArr;
        this.mBackgroundColor = bArr2;
    }
}
