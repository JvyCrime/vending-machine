package com.google.android.gms.internal.measurement;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zzfl extends zzjv<zzfm, zzfl> implements zzlh {
    private zzfl() {
        super(zzfm.zza);
    }

    public final zzfl zza(long j) {
        if (this.zzb) {
            zzaE();
            this.zzb = false;
        }
        zzfm.zzf((zzfm) this.zza, j);
        return this;
    }

    public final zzfl zzb(int i) {
        if (this.zzb) {
            zzaE();
            this.zzb = false;
        }
        zzfm.zze((zzfm) this.zza, i);
        return this;
    }

    /* synthetic */ zzfl(zzff zzffVar) {
        super(zzfm.zza);
    }
}
