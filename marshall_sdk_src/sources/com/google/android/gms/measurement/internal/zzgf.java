package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzgf implements Runnable {
    final /* synthetic */ zzp zza;
    final /* synthetic */ zzgn zzb;

    zzgf(zzgn zzgnVar, zzp zzpVar) {
        this.zzb = zzgnVar;
        this.zza = zzpVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zza.zzA();
        zzks zzksVar = this.zzb.zza;
        zzp zzpVar = this.zza;
        zzksVar.zzaz().zzg();
        zzksVar.zzB();
        Preconditions.checkNotEmpty(zzpVar.zza);
        zzag zzagVarZzb = zzag.zzb(zzpVar.zzv);
        zzag zzagVarZzh = zzksVar.zzh(zzpVar.zza);
        zzksVar.zzay().zzj().zzc("Setting consent, package, consent", zzpVar.zza, zzagVarZzb);
        zzksVar.zzT(zzpVar.zza, zzagVarZzb);
        if (zzagVarZzb.zzm(zzagVarZzh)) {
            zzksVar.zzP(zzpVar);
        }
    }
}
