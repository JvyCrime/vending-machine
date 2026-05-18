package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzlb {
    zzlb() {
    }

    public static final int zza(int i, Object obj, Object obj2) {
        zzla zzlaVar = (zzla) obj;
        if (zzlaVar.isEmpty()) {
            return 0;
        }
        Iterator it = zzlaVar.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw null;
    }

    public static final Object zzb(Object obj, Object obj2) {
        zzla zzlaVarZzb = (zzla) obj;
        zzla zzlaVar = (zzla) obj2;
        if (!zzlaVar.isEmpty()) {
            if (!zzlaVarZzb.zze()) {
                zzlaVarZzb = zzlaVarZzb.zzb();
            }
            zzlaVarZzb.zzd(zzlaVar);
        }
        return zzlaVarZzb;
    }
}
