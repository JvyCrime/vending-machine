package com.google.android.gms.measurement.internal;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzfu implements Runnable {
    final /* synthetic */ zzgy zza;
    final /* synthetic */ zzfv zzb;

    zzfu(zzfv zzfvVar, zzgy zzgyVar) {
        this.zzb = zzfvVar;
        this.zza = zzgyVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfv.zzA(this.zzb, this.zza);
        this.zzb.zzH(this.zza.zzg);
    }
}
