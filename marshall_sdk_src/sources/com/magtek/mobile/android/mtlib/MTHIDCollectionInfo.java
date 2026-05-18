package com.magtek.mobile.android.mtlib;

import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
class MTHIDCollectionInfo {
    LinkedList<MTHIDCollectionInfo> m_Children = new LinkedList<>();
    LinkedList<MTHIDFieldInfo> m_Fields;
    MTHIDCollectionInfo m_Parent;
    int m_Type;
    int m_Usage;

    MTHIDCollectionInfo(MTHIDCollectionInfo mTHIDCollectionInfo, int i, int i2) {
        this.m_Parent = mTHIDCollectionInfo;
        this.m_Usage = i;
        this.m_Type = i2;
        if (mTHIDCollectionInfo != null) {
            mTHIDCollectionInfo.m_Children.add(this);
        }
        this.m_Fields = new LinkedList<>();
    }

    void add(MTHIDFieldInfo mTHIDFieldInfo) {
        this.m_Fields.add(mTHIDFieldInfo);
    }
}
