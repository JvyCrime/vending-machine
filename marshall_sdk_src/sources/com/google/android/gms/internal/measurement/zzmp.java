package com.google.android.gms.internal.measurement;

import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzmp extends zzmr {
    zzmp(Unsafe unsafe) {
        super(unsafe);
    }

    @Override // com.google.android.gms.internal.measurement.zzmr
    public final double zza(Object obj, long j) {
        return Double.longBitsToDouble(zzk(obj, j));
    }

    @Override // com.google.android.gms.internal.measurement.zzmr
    public final float zzb(Object obj, long j) {
        return Float.intBitsToFloat(zzj(obj, j));
    }

    @Override // com.google.android.gms.internal.measurement.zzmr
    public final void zzc(Object obj, long j, boolean z) {
        if (zzms.zzb) {
            zzms.zzD(obj, j, z ? (byte) 1 : (byte) 0);
        } else {
            zzms.zzE(obj, j, z ? (byte) 1 : (byte) 0);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzmr
    public final void zzd(Object obj, long j, byte b) {
        if (zzms.zzb) {
            zzms.zzD(obj, j, b);
        } else {
            zzms.zzE(obj, j, b);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzmr
    public final void zze(Object obj, long j, double d) {
        zzo(obj, j, Double.doubleToLongBits(d));
    }

    @Override // com.google.android.gms.internal.measurement.zzmr
    public final void zzf(Object obj, long j, float f) {
        zzn(obj, j, Float.floatToIntBits(f));
    }

    @Override // com.google.android.gms.internal.measurement.zzmr
    public final boolean zzg(Object obj, long j) {
        return zzms.zzb ? zzms.zzt(obj, j) : zzms.zzu(obj, j);
    }
}
