package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public class Result implements IResult {
    private IData mData;
    private StatusCode mStatus;

    public Result(StatusCode statusCode) {
        this.mData = null;
        this.mStatus = statusCode;
        this.mData = null;
    }

    public Result(StatusCode statusCode, IData iData) {
        this.mData = null;
        this.mStatus = statusCode;
        this.mData = null;
        if (iData != null) {
            this.mData = iData.Clone();
        }
    }

    @Override // com.magtek.mobile.android.mtusdk.IResult
    public StatusCode Status() {
        return this.mStatus;
    }

    @Override // com.magtek.mobile.android.mtusdk.IResult
    public IData Data() {
        return this.mData;
    }
}
