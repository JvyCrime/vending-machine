package com.google.android.gms.internal.measurement;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
abstract class zzku {
    private static final zzku zza = new zzkq(null);
    private static final zzku zzb = new zzks(0 == true ? 1 : 0);

    /* synthetic */ zzku(zzkt zzktVar) {
    }

    static zzku zzc() {
        return zza;
    }

    static zzku zzd() {
        return zzb;
    }

    abstract void zza(Object obj, long j);

    abstract <L> void zzb(Object obj, Object obj2, long j);
}
