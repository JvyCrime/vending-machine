package com.magtek.mobile.android.mtlib;

/* JADX INFO: loaded from: classes.dex */
class MTHIDReportInfo {
    MTHIDCollectionInfo m_Collection;
    MTHIDFieldInfo[] m_Fields = new MTHIDFieldInfo[256];
    int m_Id;
    int m_MaxField;
    int m_Size;
    int m_Type;

    public MTHIDReportInfo(int i, int i2, MTHIDCollectionInfo mTHIDCollectionInfo) {
        this.m_Type = i;
        this.m_Id = i2;
        this.m_Collection = mTHIDCollectionInfo;
    }
}
