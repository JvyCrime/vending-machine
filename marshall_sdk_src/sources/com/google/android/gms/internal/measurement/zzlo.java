package com.google.android.gms.internal.measurement;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzlo {
    private static final zzlo zza = new zzlo();
    private final ConcurrentMap<Class<?>, zzlr<?>> zzc = new ConcurrentHashMap();
    private final zzls zzb = new zzky();

    private zzlo() {
    }

    public static zzlo zza() {
        return zza;
    }

    public final <T> zzlr<T> zzb(Class<T> cls) {
        zzkh.zzf(cls, "messageType");
        zzlr<T> zzlrVarZza = (zzlr) this.zzc.get(cls);
        if (zzlrVarZza == null) {
            zzlrVarZza = this.zzb.zza(cls);
            zzkh.zzf(cls, "messageType");
            zzkh.zzf(zzlrVarZza, "schema");
            zzlr<T> zzlrVar = (zzlr) this.zzc.putIfAbsent(cls, zzlrVarZza);
            if (zzlrVar != null) {
                return zzlrVar;
            }
        }
        return zzlrVarZza;
    }
}
