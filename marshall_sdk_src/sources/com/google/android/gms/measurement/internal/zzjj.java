package com.google.android.gms.measurement.internal;

import android.content.ComponentName;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzjj implements Runnable {
    final /* synthetic */ ComponentName zza;
    final /* synthetic */ zzjn zzb;

    zzjj(zzjn zzjnVar, ComponentName componentName) {
        this.zzb = zzjnVar;
        this.zza = componentName;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzjo.zzo(this.zzb.zza, this.zza);
    }
}
