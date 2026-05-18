package com.magtek.mobile.android.mtusdk.scra;

import com.magtek.mobile.android.mtlib.IMTCardData;

/* JADX INFO: loaded from: classes.dex */
public interface SCRASpiMsrAdapter {
    void OnCardDataReceived(IMTCardData iMTCardData);

    void OnDebugInfo(String str);
}
