package com.google.android.gms.measurement.internal;

import android.os.Bundle;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzjy implements Runnable {
    final long zza;
    final long zzb;
    final /* synthetic */ zzjz zzc;

    zzjy(zzjz zzjzVar, long j, long j2) {
        this.zzc = zzjzVar;
        this.zza = j;
        this.zzb = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzc.zza.zzs.zzaz().zzp(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzjx
            @Override // java.lang.Runnable
            public final void run() {
                zzjy zzjyVar = this.zza;
                zzjz zzjzVar = zzjyVar.zzc;
                long j = zzjyVar.zza;
                long j2 = zzjyVar.zzb;
                zzjzVar.zza.zzg();
                zzjzVar.zza.zzs.zzay().zzc().zza("Application going to the background");
                boolean z = true;
                zzjzVar.zza.zzs.zzm().zzl.zza(true);
                Bundle bundle = new Bundle();
                if (!zzjzVar.zza.zzs.zzf().zzu()) {
                    zzjzVar.zza.zzb.zzb(j2);
                    if (zzjzVar.zza.zzs.zzf().zzs(null, zzdy.zzag)) {
                        zzkb zzkbVar = zzjzVar.zza.zzb;
                        long j3 = zzkbVar.zzb;
                        zzkbVar.zzb = j2;
                        bundle.putLong("_et", j2 - j3);
                        zzkz.zzJ(zzjzVar.zza.zzs.zzs().zzj(true), bundle, true);
                    } else {
                        z = false;
                    }
                    zzjzVar.zza.zzb.zzd(false, z, j2);
                }
                zzjzVar.zza.zzs.zzq().zzH("auto", "_ab", j, bundle);
            }
        });
    }
}
