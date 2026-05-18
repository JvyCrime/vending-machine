package com.magtek.mobile.android.mtusdk;

import com.magtek.mobile.android.mtusdk.mms.MMSAPI;

/* JADX INFO: loaded from: classes.dex */
public class BarCodeDataBuilder {

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtusdk.BarCodeDataBuilder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceType;

        static {
            int[] iArr = new int[DeviceType.values().length];
            $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceType = iArr;
            try {
                iArr[DeviceType.MMS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public static BarCodeData GetBarCodeData(DeviceType deviceType, byte[] bArr) {
        if (AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtusdk$DeviceType[deviceType.ordinal()] != 1) {
            return null;
        }
        return MMSAPI.getBarCodeData(bArr);
    }
}
