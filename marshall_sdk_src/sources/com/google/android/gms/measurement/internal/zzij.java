package com.google.android.gms.measurement.internal;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzij implements Runnable {
    final /* synthetic */ zzih zza;
    final /* synthetic */ zzih zzb;
    final /* synthetic */ long zzc;
    final /* synthetic */ boolean zzd;
    final /* synthetic */ zzio zze;

    zzij(zzio zzioVar, zzih zzihVar, zzih zzihVar2, long j, boolean z) {
        this.zze = zzioVar;
        this.zza = zzihVar;
        this.zzb = zzihVar2;
        this.zzc = j;
        this.zzd = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zze.zzB(this.zza, this.zzb, this.zzc, this.zzd, null);
    }
}
