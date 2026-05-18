package com.magtek.mobile.android.mtusdk;

import com.magtek.mobile.android.mtusdk.mms.MMSAPI;
import com.magtek.mobile.android.mtusdk.ppscra.PPSCRAAPI;

/* JADX INFO: loaded from: classes.dex */
public class PINDataBuilder {

    /* JADX INFO: renamed from: com.magtek.mobile.android.mtusdk.PINDataBuilder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceType;

        static {
            int[] iArr = new int[DeviceType.values().length];
            $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceType = iArr;
            try {
                iArr[DeviceType.PPSCRA.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$magtek$mobile$android$mtusdk$DeviceType[DeviceType.MMS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static PINData GetPINData(DeviceType deviceType, byte[] bArr) {
        int i = AnonymousClass1.$SwitchMap$com$magtek$mobile$android$mtusdk$DeviceType[deviceType.ordinal()];
        if (i == 1) {
            return PPSCRAAPI.getPINData(bArr);
        }
        if (i != 2) {
            return null;
        }
        return MMSAPI.getPINData(bArr);
    }
}
