package com.google.android.gms.measurement.internal;

import android.os.RemoteException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zziz implements Runnable {
    final /* synthetic */ zzat zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ com.google.android.gms.internal.measurement.zzcf zzc;
    final /* synthetic */ zzjo zzd;

    zziz(zzjo zzjoVar, zzat zzatVar, String str, com.google.android.gms.internal.measurement.zzcf zzcfVar) {
        this.zzd = zzjoVar;
        this.zza = zzatVar;
        this.zzb = str;
        this.zzc = zzcfVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfv zzfvVar;
        byte[] bArrZzu = null;
        try {
            try {
                zzeb zzebVar = this.zzd.zzb;
                if (zzebVar == null) {
                    this.zzd.zzs.zzay().zzd().zza("Discarding data. Failed to send event to service to bundle");
                    zzfvVar = this.zzd.zzs;
                } else {
                    bArrZzu = zzebVar.zzu(this.zza, this.zzb);
                    this.zzd.zzQ();
                    zzfvVar = this.zzd.zzs;
                }
            } catch (RemoteException e) {
                this.zzd.zzs.zzay().zzd().zzb("Failed to send event to the service to bundle", e);
                zzfvVar = this.zzd.zzs;
            }
            zzfvVar.zzv().zzR(this.zzc, bArrZzu);
        } catch (Throwable th) {
            this.zzd.zzs.zzv().zzR(this.zzc, bArrZzu);
            throw th;
        }
    }
}
