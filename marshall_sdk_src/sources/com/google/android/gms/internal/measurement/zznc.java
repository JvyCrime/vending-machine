package com.google.android.gms.internal.measurement;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zznc implements zznb {
    public static final zzhu<Boolean> zza;
    public static final zzhu<Boolean> zzb;
    public static final zzhu<Boolean> zzc;

    static {
        zzhr zzhrVarZza = new zzhr(zzhk.zza("com.google.android.gms.measurement")).zza();
        zza = zzhrVarZza.zze("measurement.adid_zero.service", false);
        zzb = zzhrVarZza.zze("measurement.adid_zero.adid_uid", false);
        zzc = zzhrVarZza.zze("measurement.adid_zero.remove_lair_if_adidzero_false", true);
    }

    @Override // com.google.android.gms.internal.measurement.zznb
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zznb
    public final boolean zzb() {
        return zza.zzb().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zznb
    public final boolean zzc() {
        return zzb.zzb().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zznb
    public final boolean zzd() {
        return zzc.zzb().booleanValue();
    }
}
