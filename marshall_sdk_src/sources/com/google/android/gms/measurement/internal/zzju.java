package com.google.android.gms.measurement.internal;

import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.measurement.internal.zzjt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zzju<T extends Context & zzjt> {
    private final T zza;

    public zzju(T t) {
        Preconditions.checkNotNull(t);
        this.zza = t;
    }

    private final zzel zzk() {
        return zzfv.zzp(this.zza, null, null).zzay();
    }

    public final int zza(final Intent intent, int i, final int i2) {
        zzfv zzfvVarZzp = zzfv.zzp(this.zza, null, null);
        final zzel zzelVarZzay = zzfvVarZzp.zzay();
        if (intent == null) {
            zzelVarZzay.zzk().zza("AppMeasurementService started with null intent");
            return 2;
        }
        String action = intent.getAction();
        zzfvVarZzp.zzaw();
        zzelVarZzay.zzj().zzc("Local AppMeasurementService called. startId, action", Integer.valueOf(i2), action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            zzh(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzjq
                @Override // java.lang.Runnable
                public final void run() {
                    this.zza.zzc(i2, zzelVarZzay, intent);
                }
            });
        }
        return 2;
    }

    public final IBinder zzb(Intent intent) {
        if (intent == null) {
            zzk().zzd().zza("onBind called with null intent");
            return null;
        }
        String action = intent.getAction();
        if ("com.google.android.gms.measurement.START".equals(action)) {
            return new zzgn(zzks.zzt(this.zza), null);
        }
        zzk().zzk().zzb("onBind received unknown action", action);
        return null;
    }

    final /* synthetic */ void zzc(int i, zzel zzelVar, Intent intent) {
        if (this.zza.zzc(i)) {
            zzelVar.zzj().zzb("Local AppMeasurementService processed last upload request. StartId", Integer.valueOf(i));
            zzk().zzj().zza("Completed wakeful intent.");
            this.zza.zza(intent);
        }
    }

    final /* synthetic */ void zzd(zzel zzelVar, JobParameters jobParameters) {
        zzelVar.zzj().zza("AppMeasurementJobService processed last upload request.");
        this.zza.zzb(jobParameters, false);
    }

    public final void zze() {
        zzfv zzfvVarZzp = zzfv.zzp(this.zza, null, null);
        zzel zzelVarZzay = zzfvVarZzp.zzay();
        zzfvVarZzp.zzaw();
        zzelVarZzay.zzj().zza("Local AppMeasurementService is starting up");
    }

    public final void zzf() {
        zzfv zzfvVarZzp = zzfv.zzp(this.zza, null, null);
        zzel zzelVarZzay = zzfvVarZzp.zzay();
        zzfvVarZzp.zzaw();
        zzelVarZzay.zzj().zza("Local AppMeasurementService is shutting down");
    }

    public final void zzg(Intent intent) {
        if (intent == null) {
            zzk().zzd().zza("onRebind called with null intent");
        } else {
            zzk().zzj().zzb("onRebind called. action", intent.getAction());
        }
    }

    public final void zzh(Runnable runnable) {
        zzks zzksVarZzt = zzks.zzt(this.zza);
        zzksVarZzt.zzaz().zzp(new zzjs(this, zzksVarZzt, runnable));
    }

    public final boolean zzi(final JobParameters jobParameters) {
        zzfv zzfvVarZzp = zzfv.zzp(this.zza, null, null);
        final zzel zzelVarZzay = zzfvVarZzp.zzay();
        String string = jobParameters.getExtras().getString("action");
        zzfvVarZzp.zzaw();
        zzelVarZzay.zzj().zzb("Local AppMeasurementJobService called. action", string);
        if (!"com.google.android.gms.measurement.UPLOAD".equals(string)) {
            return true;
        }
        zzh(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzjr
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzd(zzelVarZzay, jobParameters);
            }
        });
        return true;
    }

    public final boolean zzj(Intent intent) {
        if (intent == null) {
            zzk().zzd().zza("onUnbind called with null intent");
            return true;
        }
        zzk().zzj().zzb("onUnbind called for intent. action", intent.getAction());
        return true;
    }
}
