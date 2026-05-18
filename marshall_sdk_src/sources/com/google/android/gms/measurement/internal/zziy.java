package com.google.android.gms.measurement.internal;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zziy extends zzam {
    final /* synthetic */ zzjo zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zziy(zzjo zzjoVar, zzgq zzgqVar) {
        super(zzgqVar);
        this.zza = zzjoVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzam
    public final void zzc() {
        zzjo zzjoVar = this.zza;
        zzjoVar.zzg();
        if (zzjoVar.zzL()) {
            zzjoVar.zzs.zzay().zzj().zza("Inactivity, disconnecting from the service");
            zzjoVar.zzs();
        }
    }
}
