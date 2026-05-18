package com.google.android.gms.internal.measurement;

import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzlk<T> implements zzlr<T> {
    private final zzlg zza;
    private final zzmi<?, ?> zzb;
    private final boolean zzc;
    private final zzjm<?> zzd;

    private zzlk(zzmi<?, ?> zzmiVar, zzjm<?> zzjmVar, zzlg zzlgVar) {
        this.zzb = zzmiVar;
        this.zzc = zzjmVar.zzc(zzlgVar);
        this.zzd = zzjmVar;
        this.zza = zzlgVar;
    }

    static <T> zzlk<T> zzc(zzmi<?, ?> zzmiVar, zzjm<?> zzjmVar, zzlg zzlgVar) {
        return new zzlk<>(zzmiVar, zzjmVar, zzlgVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzlr
    public final int zza(T t) {
        zzmi<?, ?> zzmiVar = this.zzb;
        int iZzb = zzmiVar.zzb(zzmiVar.zzc(t));
        if (!this.zzc) {
            return iZzb;
        }
        this.zzd.zza(t);
        throw null;
    }

    @Override // com.google.android.gms.internal.measurement.zzlr
    public final int zzb(T t) {
        int iHashCode = this.zzb.zzc(t).hashCode();
        if (!this.zzc) {
            return iHashCode;
        }
        this.zzd.zza(t);
        throw null;
    }

    @Override // com.google.android.gms.internal.measurement.zzlr
    public final T zze() {
        return (T) this.zza.zzbC().zzaC();
    }

    @Override // com.google.android.gms.internal.measurement.zzlr
    public final void zzf(T t) {
        this.zzb.zzg(t);
        this.zzd.zzb(t);
    }

    @Override // com.google.android.gms.internal.measurement.zzlr
    public final void zzg(T t, T t2) {
        zzlt.zzF(this.zzb, t, t2);
        if (this.zzc) {
            zzlt.zzE(this.zzd, t, t2);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.measurement.zzlr
    public final void zzh(T t, byte[] bArr, int i, int i2, zzik zzikVar) throws IOException {
        zzjz zzjzVar = (zzjz) t;
        if (zzjzVar.zzc == zzmj.zzc()) {
            zzjzVar.zzc = zzmj.zze();
        }
        throw null;
    }

    @Override // com.google.android.gms.internal.measurement.zzlr
    public final boolean zzi(T t, T t2) {
        if (!this.zzb.zzc(t).equals(this.zzb.zzc(t2))) {
            return false;
        }
        if (!this.zzc) {
            return true;
        }
        this.zzd.zza(t);
        this.zzd.zza(t2);
        throw null;
    }

    @Override // com.google.android.gms.internal.measurement.zzlr
    public final boolean zzj(T t) {
        this.zzd.zza(t);
        throw null;
    }

    @Override // com.google.android.gms.internal.measurement.zzlr
    public final void zzm(T t, zzjh zzjhVar) throws IOException {
        this.zzd.zza(t);
        throw null;
    }
}
