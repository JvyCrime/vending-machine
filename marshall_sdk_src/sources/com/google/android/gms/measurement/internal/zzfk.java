package com.google.android.gms.measurement.internal;

import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzfk implements com.google.android.gms.internal.measurement.zzr {
    final /* synthetic */ zzfm zza;

    zzfk(zzfm zzfmVar) {
        this.zza = zzfmVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzr
    public final void zza(int i, String str, List<String> list, boolean z, boolean z2) {
        int i2 = i - 1;
        zzej zzejVarZzi = i2 != 0 ? i2 != 1 ? i2 != 3 ? i2 != 4 ? this.zza.zzs.zzay().zzi() : z ? this.zza.zzs.zzay().zzm() : !z2 ? this.zza.zzs.zzay().zzl() : this.zza.zzs.zzay().zzk() : this.zza.zzs.zzay().zzj() : z ? this.zza.zzs.zzay().zzh() : !z2 ? this.zza.zzs.zzay().zze() : this.zza.zzs.zzay().zzd() : this.zza.zzs.zzay().zzc();
        int size = list.size();
        if (size == 1) {
            zzejVarZzi.zzb(str, list.get(0));
            return;
        }
        if (size == 2) {
            zzejVarZzi.zzc(str, list.get(0), list.get(1));
        } else if (size != 3) {
            zzejVarZzi.zza(str);
        } else {
            zzejVarZzi.zzd(str, list.get(0), list.get(1), list.get(2));
        }
    }
}
