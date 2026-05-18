package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zzjl {
    static final zzjl zza = new zzjl(true);
    private static volatile boolean zzb = false;
    private static volatile zzjl zzc;
    private static volatile zzjl zzd;
    private final Map<zzjk, zzjx<?, ?>> zze;

    zzjl() {
        this.zze = new HashMap();
    }

    public static zzjl zza() {
        zzjl zzjlVar = zzc;
        if (zzjlVar == null) {
            synchronized (zzjl.class) {
                zzjlVar = zzc;
                if (zzjlVar == null) {
                    zzjlVar = zza;
                    zzc = zzjlVar;
                }
            }
        }
        return zzjlVar;
    }

    public final <ContainingType extends zzlg> zzjx<ContainingType, ?> zzc(ContainingType containingtype, int i) {
        return (zzjx) this.zze.get(new zzjk(containingtype, i));
    }

    zzjl(boolean z) {
        this.zze = Collections.emptyMap();
    }

    public static zzjl zzb() {
        zzjl zzjlVar = zzd;
        if (zzjlVar != null) {
            return zzjlVar;
        }
        synchronized (zzjl.class) {
            zzjl zzjlVar2 = zzd;
            if (zzjlVar2 != null) {
                return zzjlVar2;
            }
            zzjl zzjlVarZzb = zzjt.zzb(zzjl.class);
            zzd = zzjlVarZzb;
            return zzjlVarZzb;
        }
    }
}
