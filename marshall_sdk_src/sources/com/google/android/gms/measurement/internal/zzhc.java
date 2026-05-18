package com.google.android.gms.measurement.internal;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzhc implements Runnable {
    final /* synthetic */ boolean zza;
    final /* synthetic */ zzia zzb;

    zzhc(zzia zziaVar, boolean z) {
        this.zzb = zziaVar;
        this.zza = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean zZzJ = this.zzb.zzs.zzJ();
        boolean zZzI = this.zzb.zzs.zzI();
        this.zzb.zzs.zzF(this.zza);
        if (zZzI == this.zza) {
            this.zzb.zzs.zzay().zzj().zzb("Default data collection state already set to", Boolean.valueOf(this.zza));
        }
        if (this.zzb.zzs.zzJ() == zZzJ || this.zzb.zzs.zzJ() != this.zzb.zzs.zzI()) {
            this.zzb.zzs.zzay().zzl().zzc("Default data collection is different than actual status", Boolean.valueOf(this.zza), Boolean.valueOf(zZzJ));
        }
        this.zzb.zzad();
    }
}
