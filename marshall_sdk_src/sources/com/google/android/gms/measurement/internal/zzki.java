package com.google.android.gms.measurement.internal;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
abstract class zzki extends zzkh {
    private boolean zza;

    zzki(zzks zzksVar) {
        super(zzksVar);
        this.zzf.zzL();
    }

    protected final void zzY() {
        if (!zzaa()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    public final void zzZ() {
        if (this.zza) {
            throw new IllegalStateException("Can't initialize twice");
        }
        zzb();
        this.zzf.zzG();
        this.zza = true;
    }

    final boolean zzaa() {
        return this.zza;
    }

    protected abstract boolean zzb();
}
