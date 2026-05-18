package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.os.Binder;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.common.GoogleSignatureVerifier;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.internal.measurement.zzpo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zzgn extends zzea {
    private final zzks zza;
    private Boolean zzb;
    private String zzc;

    public zzgn(zzks zzksVar, String str) {
        Preconditions.checkNotNull(zzksVar);
        this.zza = zzksVar;
        this.zzc = null;
    }

    private final void zzA(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            this.zza.zzay().zzd().zza("Measurement Service called without app package");
            throw new SecurityException("Measurement Service called without app package");
        }
        if (z) {
            try {
                if (this.zzb == null) {
                    this.zzb = Boolean.valueOf("com.google.android.gms".equals(this.zzc) || UidVerifier.isGooglePlayServicesUid(this.zza.zzau(), Binder.getCallingUid()) || GoogleSignatureVerifier.getInstance(this.zza.zzau()).isUidGoogleSigned(Binder.getCallingUid()));
                }
                if (this.zzb.booleanValue()) {
                    return;
                }
            } catch (SecurityException e) {
                this.zza.zzay().zzd().zzb("Measurement Service called with invalid calling package. appId", zzel.zzn(str));
                throw e;
            }
        }
        if (this.zzc == null && GooglePlayServicesUtilLight.uidHasPackageName(this.zza.zzau(), Binder.getCallingUid(), str)) {
            this.zzc = str;
        }
        if (str.equals(this.zzc)) {
        } else {
            throw new SecurityException(String.format("Unknown calling package name '%s'.", str));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzB(zzat zzatVar, zzp zzpVar) {
        this.zza.zzA();
        this.zza.zzD(zzatVar, zzpVar);
    }

    private final void zzz(zzp zzpVar, boolean z) {
        Preconditions.checkNotNull(zzpVar);
        Preconditions.checkNotEmpty(zzpVar.zza);
        zzA(zzpVar.zza, false);
        this.zza.zzv().zzW(zzpVar.zzb, zzpVar.zzq, zzpVar.zzu);
    }

    final zzat zzb(zzat zzatVar, zzp zzpVar) {
        zzar zzarVar;
        if ("_cmp".equals(zzatVar.zza) && (zzarVar = zzatVar.zzb) != null && zzarVar.zza() != 0) {
            String strZzg = zzatVar.zzb.zzg("_cis");
            if ("referrer broadcast".equals(strZzg) || "referrer API".equals(strZzg)) {
                this.zza.zzay().zzi().zzb("Event has been filtered ", zzatVar.toString());
                return new zzat("_cmpx", zzatVar.zzb, zzatVar.zzc, zzatVar.zzd);
            }
        }
        return zzatVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final String zzd(zzp zzpVar) {
        zzz(zzpVar, false);
        return this.zza.zzx(zzpVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final List<zzkv> zze(zzp zzpVar, boolean z) {
        zzz(zzpVar, false);
        String str = zzpVar.zza;
        Preconditions.checkNotNull(str);
        try {
            List<zzkx> list = (List) this.zza.zzaz().zzh(new zzgk(this, str)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzkx zzkxVar : list) {
                if (z || !zzkz.zzag(zzkxVar.zzc)) {
                    arrayList.add(new zzkv(zzkxVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzay().zzd().zzc("Failed to get user properties. appId", zzel.zzn(zzpVar.zza), e);
            return null;
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final List<zzab> zzf(String str, String str2, zzp zzpVar) {
        zzz(zzpVar, false);
        String str3 = zzpVar.zza;
        Preconditions.checkNotNull(str3);
        try {
            return (List) this.zza.zzaz().zzh(new zzgb(this, str3, str, str2)).get();
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzay().zzd().zzb("Failed to get conditional user properties", e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final List<zzab> zzg(String str, String str2, String str3) {
        zzA(str, true);
        try {
            return (List) this.zza.zzaz().zzh(new zzgc(this, str, str2, str3)).get();
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzay().zzd().zzb("Failed to get conditional user properties as", e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final List<zzkv> zzh(String str, String str2, boolean z, zzp zzpVar) {
        zzz(zzpVar, false);
        String str3 = zzpVar.zza;
        Preconditions.checkNotNull(str3);
        try {
            List<zzkx> list = (List) this.zza.zzaz().zzh(new zzfz(this, str3, str, str2)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzkx zzkxVar : list) {
                if (z || !zzkz.zzag(zzkxVar.zzc)) {
                    arrayList.add(new zzkv(zzkxVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzay().zzd().zzc("Failed to query user properties. appId", zzel.zzn(zzpVar.zza), e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final List<zzkv> zzi(String str, String str2, String str3, boolean z) {
        zzA(str, true);
        try {
            List<zzkx> list = (List) this.zza.zzaz().zzh(new zzga(this, str, str2, str3)).get();
            ArrayList arrayList = new ArrayList(list.size());
            for (zzkx zzkxVar : list) {
                if (z || !zzkz.zzag(zzkxVar.zzc)) {
                    arrayList.add(new zzkv(zzkxVar));
                }
            }
            return arrayList;
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzay().zzd().zzc("Failed to get user properties as. appId", zzel.zzn(str), e);
            return Collections.emptyList();
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final void zzj(zzp zzpVar) {
        zzz(zzpVar, false);
        zzy(new zzgl(this, zzpVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final void zzk(zzat zzatVar, zzp zzpVar) {
        Preconditions.checkNotNull(zzatVar);
        zzz(zzpVar, false);
        zzy(new zzgg(this, zzatVar, zzpVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final void zzl(zzat zzatVar, String str, String str2) {
        Preconditions.checkNotNull(zzatVar);
        Preconditions.checkNotEmpty(str);
        zzA(str, true);
        zzy(new zzgh(this, zzatVar, str));
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final void zzm(zzp zzpVar) {
        Preconditions.checkNotEmpty(zzpVar.zza);
        zzA(zzpVar.zza, false);
        zzy(new zzgd(this, zzpVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final void zzn(zzab zzabVar, zzp zzpVar) {
        Preconditions.checkNotNull(zzabVar);
        Preconditions.checkNotNull(zzabVar.zzc);
        zzz(zzpVar, false);
        zzab zzabVar2 = new zzab(zzabVar);
        zzabVar2.zza = zzpVar.zza;
        zzy(new zzfx(this, zzabVar2, zzpVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final void zzo(zzab zzabVar) {
        Preconditions.checkNotNull(zzabVar);
        Preconditions.checkNotNull(zzabVar.zzc);
        Preconditions.checkNotEmpty(zzabVar.zza);
        zzA(zzabVar.zza, true);
        zzy(new zzfy(this, new zzab(zzabVar)));
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final void zzp(zzp zzpVar) {
        Preconditions.checkNotEmpty(zzpVar.zza);
        Preconditions.checkNotNull(zzpVar.zzv);
        zzgf zzgfVar = new zzgf(this, zzpVar);
        Preconditions.checkNotNull(zzgfVar);
        if (this.zza.zzaz().zzs()) {
            zzgfVar.run();
        } else {
            this.zza.zzaz().zzq(zzgfVar);
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final void zzq(long j, String str, String str2, String str3) {
        zzy(new zzgm(this, str2, str3, str, j));
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final void zzr(final Bundle bundle, zzp zzpVar) {
        zzz(zzpVar, false);
        final String str = zzpVar.zza;
        Preconditions.checkNotNull(str);
        zzy(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzfw
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzx(str, bundle);
            }
        });
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final void zzs(zzp zzpVar) {
        zzz(zzpVar, false);
        zzy(new zzge(this, zzpVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final void zzt(zzkv zzkvVar, zzp zzpVar) {
        Preconditions.checkNotNull(zzkvVar);
        zzz(zzpVar, false);
        zzy(new zzgj(this, zzkvVar, zzpVar));
    }

    @Override // com.google.android.gms.measurement.internal.zzeb
    public final byte[] zzu(zzat zzatVar, String str) {
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(zzatVar);
        zzA(str, true);
        this.zza.zzay().zzc().zzb("Log and bundle. event", this.zza.zzj().zzd(zzatVar.zza));
        long jNanoTime = this.zza.zzav().nanoTime() / 1000000;
        try {
            byte[] bArr = (byte[]) this.zza.zzaz().zzi(new zzgi(this, zzatVar, str)).get();
            if (bArr == null) {
                this.zza.zzay().zzd().zzb("Log and bundle returned null. appId", zzel.zzn(str));
                bArr = new byte[0];
            }
            this.zza.zzay().zzc().zzd("Log and bundle processed. event, size, time_ms", this.zza.zzj().zzd(zzatVar.zza), Integer.valueOf(bArr.length), Long.valueOf((this.zza.zzav().nanoTime() / 1000000) - jNanoTime));
            return bArr;
        } catch (InterruptedException | ExecutionException e) {
            this.zza.zzay().zzd().zzd("Failed to log and bundle. appId, event, error", zzel.zzn(str), this.zza.zzj().zzd(zzatVar.zza), e);
            return null;
        }
    }

    final void zzw(zzat zzatVar, zzp zzpVar) {
        if (!this.zza.zzo().zzl(zzpVar.zza)) {
            zzB(zzatVar, zzpVar);
            return;
        }
        this.zza.zzay().zzj().zzb("EES config found for", zzpVar.zza);
        zzfm zzfmVarZzo = this.zza.zzo();
        String str = zzpVar.zza;
        zzpo.zzc();
        com.google.android.gms.internal.measurement.zzc zzcVar = null;
        if (zzfmVarZzo.zzs.zzf().zzs(null, zzdy.zzav) && !TextUtils.isEmpty(str)) {
            zzcVar = zzfmVarZzo.zza.get(str);
        }
        if (zzcVar == null) {
            this.zza.zzay().zzj().zzb("EES not loaded for", zzpVar.zza);
            zzB(zzatVar, zzpVar);
            return;
        }
        try {
            Map<String, Object> mapZzt = this.zza.zzu().zzt(zzatVar.zzb.zzc(), true);
            String strZza = zzgs.zza(zzatVar.zza);
            if (strZza == null) {
                strZza = zzatVar.zza;
            }
            if (zzcVar.zze(new com.google.android.gms.internal.measurement.zzaa(strZza, zzatVar.zzd, mapZzt))) {
                if (zzcVar.zzg()) {
                    this.zza.zzay().zzj().zzb("EES edited event", zzatVar.zza);
                    zzB(this.zza.zzu().zzi(zzcVar.zza().zzb()), zzpVar);
                } else {
                    zzB(zzatVar, zzpVar);
                }
                if (zzcVar.zzf()) {
                    for (com.google.android.gms.internal.measurement.zzaa zzaaVar : zzcVar.zza().zzc()) {
                        this.zza.zzay().zzj().zzb("EES logging created event", zzaaVar.zzd());
                        zzB(this.zza.zzu().zzi(zzaaVar), zzpVar);
                    }
                    return;
                }
                return;
            }
        } catch (com.google.android.gms.internal.measurement.zzd unused) {
            this.zza.zzay().zzd().zzc("EES error. appId, eventName", zzpVar.zzb, zzatVar.zza);
        }
        this.zza.zzay().zzj().zzb("EES was not applied to event", zzatVar.zza);
        zzB(zzatVar, zzpVar);
    }

    final /* synthetic */ void zzx(String str, Bundle bundle) {
        zzaj zzajVarZzi = this.zza.zzi();
        zzajVarZzi.zzg();
        zzajVarZzi.zzY();
        byte[] bArrZzbs = zzajVarZzi.zzf.zzu().zzj(new zzao(zzajVarZzi.zzs, "", str, "dep", 0L, 0L, bundle)).zzbs();
        zzajVarZzi.zzs.zzay().zzj().zzc("Saving default event parameters, appId, data size", zzajVarZzi.zzs.zzj().zzd(str), Integer.valueOf(bArrZzbs.length));
        ContentValues contentValues = new ContentValues();
        contentValues.put("app_id", str);
        contentValues.put("parameters", bArrZzbs);
        try {
            if (zzajVarZzi.zzh().insertWithOnConflict("default_event_params", null, contentValues, 5) == -1) {
                zzajVarZzi.zzs.zzay().zzd().zzb("Failed to insert default event parameters (got -1). appId", zzel.zzn(str));
            }
        } catch (SQLiteException e) {
            zzajVarZzi.zzs.zzay().zzd().zzc("Error storing default event parameters. appId", zzel.zzn(str), e);
        }
    }

    final void zzy(Runnable runnable) {
        Preconditions.checkNotNull(runnable);
        if (this.zza.zzaz().zzs()) {
            runnable.run();
        } else {
            this.zza.zzaz().zzp(runnable);
        }
    }
}
