package com.magtek.mobile.android.mtcms;

/* JADX INFO: loaded from: classes.dex */
class MTHIDFieldInfo {
    int m_Application;
    MTHIDCollectionInfo m_Collection;
    int m_Flags;
    int m_Logical;
    int m_LogicalMaximum;
    int m_LogicalMinimum;
    int m_Physical;
    int m_PhysicalMaximum;
    int m_PhysicalMinimum;
    MTHIDReportInfo m_Report;
    int m_ReportOffset;
    int m_ReportSize;
    int m_ReportType;
    int m_Unit;
    int m_UnitExponent;
    int m_Usage;

    MTHIDFieldInfo(MTHIDCollectionInfo mTHIDCollectionInfo) {
        this.m_Collection = mTHIDCollectionInfo;
        mTHIDCollectionInfo.add(this);
    }
}
