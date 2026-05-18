package com.google.android.gms.internal.measurement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zzw extends zzai {
    final Map<String, zzai> zza;
    private final zzj zzb;

    public zzw(zzj zzjVar) {
        super("require");
        this.zza = new HashMap();
        this.zzb = zzjVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzai
    public final zzap zza(zzg zzgVar, List<zzap> list) {
        zzai zzaiVarCall;
        zzh.zzh("require", 1, list);
        String strZzi = zzgVar.zzb(list.get(0)).zzi();
        if (this.zza.containsKey(strZzi)) {
            return this.zza.get(strZzi);
        }
        zzj zzjVar = this.zzb;
        if (zzjVar.zza.containsKey(strZzi)) {
            try {
                zzaiVarCall = zzjVar.zza.get(strZzi).call();
            } catch (Exception unused) {
                String strValueOf = String.valueOf(strZzi);
                throw new IllegalStateException(strValueOf.length() != 0 ? "Failed to create API implementation: ".concat(strValueOf) : new String("Failed to create API implementation: "));
            }
        } else {
            zzaiVarCall = zzap.zzf;
        }
        if (zzaiVarCall instanceof zzai) {
            this.zza.put(strZzi, (zzai) zzaiVarCall);
        }
        return zzaiVarCall;
    }
}
