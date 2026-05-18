package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zziq implements Runnable {
    final /* synthetic */ zzp zza;
    final /* synthetic */ boolean zzb;
    final /* synthetic */ zzkv zzc;
    final /* synthetic */ zzjo zzd;

    zziq(zzjo zzjoVar, zzp zzpVar, boolean z, zzkv zzkvVar) {
        this.zzd = zzjoVar;
        this.zza = zzpVar;
        this.zzb = z;
        this.zzc = zzkvVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzeb zzebVar = this.zzd.zzb;
        if (zzebVar == null) {
            this.zzd.zzs.zzay().zzd().zza("Discarding data. Failed to set user property");
            return;
        }
        Preconditions.checkNotNull(this.zza);
        this.zzd.zzD(zzebVar, this.zzb ? null : this.zzc, this.zza);
        this.zzd.zzQ();
    }
}
