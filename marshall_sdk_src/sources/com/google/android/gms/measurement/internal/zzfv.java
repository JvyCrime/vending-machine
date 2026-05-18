package com.google.android.gms.measurement.internal;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzob;
import com.google.android.gms.internal.measurement.zzoe;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.checkerframework.dataflow.qual.Pure;
import org.checkerframework.dataflow.qual.SideEffectFree;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zzfv implements zzgq {
    private static volatile zzfv zzd;
    private zzec zzA;
    private Boolean zzC;
    private long zzD;
    private volatile Boolean zzE;
    private volatile boolean zzF;
    private int zzG;
    protected Boolean zza;
    protected Boolean zzb;
    final long zzc;
    private final Context zze;
    private final String zzf;
    private final String zzg;
    private final String zzh;
    private final boolean zzi;
    private final zzaa zzj;
    private final zzaf zzk;
    private final zzfa zzl;
    private final zzel zzm;
    private final zzfs zzn;
    private final zzkd zzo;
    private final zzkz zzp;
    private final zzeg zzq;
    private final Clock zzr;
    private final zzio zzs;
    private final zzia zzt;
    private final zzd zzu;
    private final zzie zzv;
    private final String zzw;
    private zzee zzx;
    private zzjo zzy;
    private zzan zzz;
    private boolean zzB = false;
    private final AtomicInteger zzH = new AtomicInteger(0);

    zzfv(zzgy zzgyVar) {
        Bundle bundle;
        Preconditions.checkNotNull(zzgyVar);
        zzaa zzaaVar = new zzaa(zzgyVar.zza);
        this.zzj = zzaaVar;
        zzdv.zza = zzaaVar;
        Context context = zzgyVar.zza;
        this.zze = context;
        this.zzf = zzgyVar.zzb;
        this.zzg = zzgyVar.zzc;
        this.zzh = zzgyVar.zzd;
        this.zzi = zzgyVar.zzh;
        this.zzE = zzgyVar.zze;
        this.zzw = zzgyVar.zzj;
        this.zzF = true;
        com.google.android.gms.internal.measurement.zzcl zzclVar = zzgyVar.zzg;
        if (zzclVar != null && (bundle = zzclVar.zzg) != null) {
            Object obj = bundle.get("measurementEnabled");
            if (obj instanceof Boolean) {
                this.zza = (Boolean) obj;
            }
            Object obj2 = zzclVar.zzg.get("measurementDeactivated");
            if (obj2 instanceof Boolean) {
                this.zzb = (Boolean) obj2;
            }
        }
        com.google.android.gms.internal.measurement.zzhu.zzd(context);
        Clock defaultClock = DefaultClock.getInstance();
        this.zzr = defaultClock;
        Long l = zzgyVar.zzi;
        this.zzc = l != null ? l.longValue() : defaultClock.currentTimeMillis();
        this.zzk = new zzaf(this);
        zzfa zzfaVar = new zzfa(this);
        zzfaVar.zzv();
        this.zzl = zzfaVar;
        zzel zzelVar = new zzel(this);
        zzelVar.zzv();
        this.zzm = zzelVar;
        zzkz zzkzVar = new zzkz(this);
        zzkzVar.zzv();
        this.zzp = zzkzVar;
        this.zzq = new zzeg(new zzgx(zzgyVar, this));
        this.zzu = new zzd(this);
        zzio zzioVar = new zzio(this);
        zzioVar.zzb();
        this.zzs = zzioVar;
        zzia zziaVar = new zzia(this);
        zziaVar.zzb();
        this.zzt = zziaVar;
        zzkd zzkdVar = new zzkd(this);
        zzkdVar.zzb();
        this.zzo = zzkdVar;
        zzie zzieVar = new zzie(this);
        zzieVar.zzv();
        this.zzv = zzieVar;
        zzfs zzfsVar = new zzfs(this);
        zzfsVar.zzv();
        this.zzn = zzfsVar;
        com.google.android.gms.internal.measurement.zzcl zzclVar2 = zzgyVar.zzg;
        boolean z = zzclVar2 == null || zzclVar2.zzb == 0;
        if (context.getApplicationContext() instanceof Application) {
            zzia zziaVarZzq = zzq();
            if (zziaVarZzq.zzs.zze.getApplicationContext() instanceof Application) {
                Application application = (Application) zziaVarZzq.zzs.zze.getApplicationContext();
                if (zziaVarZzq.zza == null) {
                    zziaVarZzq.zza = new zzhz(zziaVarZzq, null);
                }
                if (z) {
                    application.unregisterActivityLifecycleCallbacks(zziaVarZzq.zza);
                    application.registerActivityLifecycleCallbacks(zziaVarZzq.zza);
                    zziaVarZzq.zzs.zzay().zzj().zza("Registered activity lifecycle callback");
                }
            }
        } else {
            zzay().zzk().zza("Application context is not an Application");
        }
        zzfsVar.zzp(new zzfu(this, zzgyVar));
    }

    static /* bridge */ /* synthetic */ void zzA(zzfv zzfvVar, zzgy zzgyVar) {
        zzfvVar.zzaz().zzg();
        zzfvVar.zzk.zzn();
        zzan zzanVar = new zzan(zzfvVar);
        zzanVar.zzv();
        zzfvVar.zzz = zzanVar;
        zzec zzecVar = new zzec(zzfvVar, zzgyVar.zzf);
        zzecVar.zzb();
        zzfvVar.zzA = zzecVar;
        zzee zzeeVar = new zzee(zzfvVar);
        zzeeVar.zzb();
        zzfvVar.zzx = zzeeVar;
        zzjo zzjoVar = new zzjo(zzfvVar);
        zzjoVar.zzb();
        zzfvVar.zzy = zzjoVar;
        zzfvVar.zzp.zzw();
        zzfvVar.zzl.zzw();
        zzfvVar.zzA.zzc();
        zzej zzejVarZzi = zzfvVar.zzay().zzi();
        zzfvVar.zzk.zzh();
        zzejVarZzi.zzb("App measurement initialized, version", 43042L);
        zzfvVar.zzay().zzi().zza("To enable debug logging run: adb shell setprop log.tag.FA VERBOSE");
        String strZzl = zzecVar.zzl();
        if (TextUtils.isEmpty(zzfvVar.zzf)) {
            if (zzfvVar.zzv().zzad(strZzl)) {
                zzfvVar.zzay().zzi().zza("Faster debug mode event logging enabled. To disable, run:\n  adb shell setprop debug.firebase.analytics.app .none.");
            } else {
                zzej zzejVarZzi2 = zzfvVar.zzay().zzi();
                String strValueOf = String.valueOf(strZzl);
                zzejVarZzi2.zza(strValueOf.length() != 0 ? "To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app ".concat(strValueOf) : new String("To enable faster debug mode event logging run:\n  adb shell setprop debug.firebase.analytics.app "));
            }
        }
        zzfvVar.zzay().zzc().zza("Debug-level message logging enabled");
        if (zzfvVar.zzG != zzfvVar.zzH.get()) {
            zzfvVar.zzay().zzd().zzc("Not all components initialized", Integer.valueOf(zzfvVar.zzG), Integer.valueOf(zzfvVar.zzH.get()));
        }
        zzfvVar.zzB = true;
    }

    static final void zzO() {
        throw new IllegalStateException("Unexpected call on client side");
    }

    private static final void zzP(zzgo zzgoVar) {
        if (zzgoVar == null) {
            throw new IllegalStateException("Component not created");
        }
    }

    private static final void zzQ(zzf zzfVar) {
        if (zzfVar == null) {
            throw new IllegalStateException("Component not created");
        }
        if (zzfVar.zze()) {
            return;
        }
        String strValueOf = String.valueOf(zzfVar.getClass());
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 27);
        sb.append("Component not initialized: ");
        sb.append(strValueOf);
        throw new IllegalStateException(sb.toString());
    }

    private static final void zzR(zzgp zzgpVar) {
        if (zzgpVar == null) {
            throw new IllegalStateException("Component not created");
        }
        if (zzgpVar.zzx()) {
            return;
        }
        String strValueOf = String.valueOf(zzgpVar.getClass());
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 27);
        sb.append("Component not initialized: ");
        sb.append(strValueOf);
        throw new IllegalStateException(sb.toString());
    }

    public static zzfv zzp(Context context, com.google.android.gms.internal.measurement.zzcl zzclVar, Long l) {
        Bundle bundle;
        if (zzclVar != null && (zzclVar.zze == null || zzclVar.zzf == null)) {
            zzclVar = new com.google.android.gms.internal.measurement.zzcl(zzclVar.zza, zzclVar.zzb, zzclVar.zzc, zzclVar.zzd, null, null, zzclVar.zzg, null);
        }
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(context.getApplicationContext());
        if (zzd == null) {
            synchronized (zzfv.class) {
                if (zzd == null) {
                    zzd = new zzfv(new zzgy(context, zzclVar, l));
                }
            }
        } else if (zzclVar != null && (bundle = zzclVar.zzg) != null && bundle.containsKey("dataCollectionDefaultEnabled")) {
            Preconditions.checkNotNull(zzd);
            zzd.zzE = Boolean.valueOf(zzclVar.zzg.getBoolean("dataCollectionDefaultEnabled"));
        }
        Preconditions.checkNotNull(zzd);
        return zzd;
    }

    final void zzB() {
        this.zzH.incrementAndGet();
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0019  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final /* synthetic */ void zzC(java.lang.String r7, int r8, java.lang.Throwable r9, byte[] r10, java.util.Map r11) {
        /*
            Method dump skipped, instruction units count: 290
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzfv.zzC(java.lang.String, int, java.lang.Throwable, byte[], java.util.Map):void");
    }

    final void zzD() {
        this.zzG++;
    }

    public final void zzE() {
        zzaz().zzg();
        zzR(zzr());
        String strZzl = zzh().zzl();
        Pair<String, Boolean> pairZzb = zzm().zzb(strZzl);
        if (!this.zzk.zzr() || ((Boolean) pairZzb.second).booleanValue() || TextUtils.isEmpty((CharSequence) pairZzb.first)) {
            zzay().zzc().zza("ADID unavailable to retrieve Deferred Deep Link. Skipping");
            return;
        }
        zzie zzieVarZzr = zzr();
        zzieVarZzr.zzu();
        ConnectivityManager connectivityManager = (ConnectivityManager) zzieVarZzr.zzs.zze.getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            try {
                activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            } catch (SecurityException unused) {
            }
        }
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            zzay().zzk().zza("Network is not available for Deferred Deep Link request. Skipping");
            return;
        }
        zzkz zzkzVarZzv = zzv();
        zzh().zzs.zzk.zzh();
        URL urlZzD = zzkzVarZzv.zzD(43042L, strZzl, (String) pairZzb.first, zzm().zzn.zza() - 1);
        if (urlZzD != null) {
            zzie zzieVarZzr2 = zzr();
            zzft zzftVar = new zzft(this);
            zzieVarZzr2.zzg();
            zzieVarZzr2.zzu();
            Preconditions.checkNotNull(urlZzD);
            Preconditions.checkNotNull(zzftVar);
            zzieVarZzr2.zzs.zzaz().zzo(new zzid(zzieVarZzr2, strZzl, urlZzD, null, null, zzftVar, null));
        }
    }

    final void zzF(boolean z) {
        this.zzE = Boolean.valueOf(z);
    }

    public final void zzG(boolean z) {
        zzaz().zzg();
        this.zzF = z;
    }

    protected final void zzH(com.google.android.gms.internal.measurement.zzcl zzclVar) {
        zzag zzagVar;
        zzaz().zzg();
        zzag zzagVarZzc = zzm().zzc();
        zzfa zzfaVarZzm = zzm();
        zzfv zzfvVar = zzfaVarZzm.zzs;
        zzfaVarZzm.zzg();
        int i = 100;
        int i2 = zzfaVarZzm.zza().getInt("consent_source", 100);
        zzaf zzafVar = this.zzk;
        zzfv zzfvVar2 = zzafVar.zzs;
        Boolean boolZzk = zzafVar.zzk("google_analytics_default_allow_ad_storage");
        zzaf zzafVar2 = this.zzk;
        zzfv zzfvVar3 = zzafVar2.zzs;
        Boolean boolZzk2 = zzafVar2.zzk("google_analytics_default_allow_analytics_storage");
        if (!(boolZzk == null && boolZzk2 == null) && zzm().zzl(-10)) {
            zzagVar = new zzag(boolZzk, boolZzk2);
            i = -10;
        } else {
            if (TextUtils.isEmpty(zzh().zzn()) || !(i2 == 0 || i2 == 30 || i2 == 10 || i2 == 30 || i2 == 30 || i2 == 40)) {
                zzoe.zzc();
                if ((!this.zzk.zzs(null, zzdy.zzat) || TextUtils.isEmpty(zzh().zzn())) && zzclVar != null && zzclVar.zzg != null && zzm().zzl(30)) {
                    zzagVar = zzag.zza(zzclVar.zzg);
                    if (!zzagVar.equals(zzag.zza)) {
                        i = 30;
                    }
                }
            } else {
                zzq().zzS(zzag.zza, -10, this.zzc);
            }
            zzagVar = null;
        }
        if (zzagVar != null) {
            zzq().zzS(zzagVar, i, this.zzc);
            zzagVarZzc = zzagVar;
        }
        zzq().zzW(zzagVarZzc);
        if (zzm().zzc.zza() == 0) {
            zzay().zzj().zzb("Persisting first open", Long.valueOf(this.zzc));
            zzm().zzc.zzb(this.zzc);
        }
        zzq().zzb.zzc();
        if (zzM()) {
            if (!TextUtils.isEmpty(zzh().zzn()) || !TextUtils.isEmpty(zzh().zzk())) {
                zzkz zzkzVarZzv = zzv();
                String strZzn = zzh().zzn();
                zzfa zzfaVarZzm2 = zzm();
                zzfaVarZzm2.zzg();
                String string = zzfaVarZzm2.zza().getString("gmp_app_id", null);
                String strZzk = zzh().zzk();
                zzfa zzfaVarZzm3 = zzm();
                zzfaVarZzm3.zzg();
                if (zzkzVarZzv.zzam(strZzn, string, strZzk, zzfaVarZzm3.zza().getString("admob_app_id", null))) {
                    zzay().zzi().zza("Rechecking which service to use due to a GMP App Id change");
                    zzfa zzfaVarZzm4 = zzm();
                    zzfaVarZzm4.zzg();
                    Boolean boolZzd = zzfaVarZzm4.zzd();
                    SharedPreferences.Editor editorEdit = zzfaVarZzm4.zza().edit();
                    editorEdit.clear();
                    editorEdit.apply();
                    if (boolZzd != null) {
                        zzfaVarZzm4.zzh(boolZzd);
                    }
                    zzi().zzj();
                    this.zzy.zzs();
                    this.zzy.zzr();
                    zzm().zzc.zzb(this.zzc);
                    zzm().zze.zzb(null);
                }
                zzfa zzfaVarZzm5 = zzm();
                String strZzn2 = zzh().zzn();
                zzfaVarZzm5.zzg();
                SharedPreferences.Editor editorEdit2 = zzfaVarZzm5.zza().edit();
                editorEdit2.putString("gmp_app_id", strZzn2);
                editorEdit2.apply();
                zzfa zzfaVarZzm6 = zzm();
                String strZzk2 = zzh().zzk();
                zzfaVarZzm6.zzg();
                SharedPreferences.Editor editorEdit3 = zzfaVarZzm6.zza().edit();
                editorEdit3.putString("admob_app_id", strZzk2);
                editorEdit3.apply();
            }
            if (!zzm().zzc().zzk()) {
                zzm().zze.zzb(null);
            }
            zzq().zzO(zzm().zze.zza());
            zzob.zzc();
            if (this.zzk.zzs(null, zzdy.zzai)) {
                try {
                    zzv().zzs.zze.getClassLoader().loadClass("com.google.firebase.remoteconfig.FirebaseRemoteConfig");
                } catch (ClassNotFoundException unused) {
                    if (!TextUtils.isEmpty(zzm().zzo.zza())) {
                        zzay().zzk().zza("Remote config removed with active feature rollouts");
                        zzm().zzo.zzb(null);
                    }
                }
            }
            if (!TextUtils.isEmpty(zzh().zzn()) || !TextUtils.isEmpty(zzh().zzk())) {
                boolean zZzJ = zzJ();
                if (!zzm().zzj() && !this.zzk.zzv()) {
                    zzm().zzi(!zZzJ);
                }
                if (zZzJ) {
                    zzq().zzy();
                }
                zzu().zza.zza();
                zzt().zzu(new AtomicReference<>());
                zzt().zzH(zzm().zzr.zza());
            }
        } else if (zzJ()) {
            if (!zzv().zzac("android.permission.INTERNET")) {
                zzay().zzd().zza("App is missing INTERNET permission");
            }
            if (!zzv().zzac("android.permission.ACCESS_NETWORK_STATE")) {
                zzay().zzd().zza("App is missing ACCESS_NETWORK_STATE permission");
            }
            if (!Wrappers.packageManager(this.zze).isCallerInstantApp() && !this.zzk.zzx()) {
                if (!zzkz.zzai(this.zze)) {
                    zzay().zzd().zza("AppMeasurementReceiver not registered/enabled");
                }
                if (!zzkz.zzaj(this.zze, false)) {
                    zzay().zzd().zza("AppMeasurementService not registered/enabled");
                }
            }
            zzay().zzd().zza("Uploading is not possible. App measurement disabled");
        }
        zzm().zzi.zza(true);
    }

    public final boolean zzI() {
        return this.zzE != null && this.zzE.booleanValue();
    }

    public final boolean zzJ() {
        return zza() == 0;
    }

    public final boolean zzK() {
        zzaz().zzg();
        return this.zzF;
    }

    @Pure
    public final boolean zzL() {
        return TextUtils.isEmpty(this.zzf);
    }

    protected final boolean zzM() {
        if (!this.zzB) {
            throw new IllegalStateException("AppMeasurement is not initialized");
        }
        zzaz().zzg();
        Boolean bool = this.zzC;
        if (bool == null || this.zzD == 0 || (!bool.booleanValue() && Math.abs(this.zzr.elapsedRealtime() - this.zzD) > 1000)) {
            this.zzD = this.zzr.elapsedRealtime();
            boolean z = true;
            Boolean boolValueOf = Boolean.valueOf(zzv().zzac("android.permission.INTERNET") && zzv().zzac("android.permission.ACCESS_NETWORK_STATE") && (Wrappers.packageManager(this.zze).isCallerInstantApp() || this.zzk.zzx() || (zzkz.zzai(this.zze) && zzkz.zzaj(this.zze, false))));
            this.zzC = boolValueOf;
            if (boolValueOf.booleanValue()) {
                if (!zzv().zzW(zzh().zzn(), zzh().zzk(), zzh().zzm()) && TextUtils.isEmpty(zzh().zzk())) {
                    z = false;
                }
                this.zzC = Boolean.valueOf(z);
            }
        }
        return this.zzC.booleanValue();
    }

    @Pure
    public final boolean zzN() {
        return this.zzi;
    }

    public final int zza() {
        zzaz().zzg();
        if (this.zzk.zzv()) {
            return 1;
        }
        Boolean bool = this.zzb;
        if (bool != null && bool.booleanValue()) {
            return 2;
        }
        zzaz().zzg();
        if (!this.zzF) {
            return 8;
        }
        Boolean boolZzd = zzm().zzd();
        if (boolZzd != null) {
            return boolZzd.booleanValue() ? 0 : 3;
        }
        zzaf zzafVar = this.zzk;
        zzaa zzaaVar = zzafVar.zzs.zzj;
        Boolean boolZzk = zzafVar.zzk("firebase_analytics_collection_enabled");
        if (boolZzk != null) {
            return boolZzk.booleanValue() ? 0 : 4;
        }
        Boolean bool2 = this.zza;
        return bool2 != null ? bool2.booleanValue() ? 0 : 5 : (!this.zzk.zzs(null, zzdy.zzS) || this.zzE == null || this.zzE.booleanValue()) ? 0 : 7;
    }

    @Override // com.google.android.gms.measurement.internal.zzgq
    @Pure
    public final Context zzau() {
        return this.zze;
    }

    @Override // com.google.android.gms.measurement.internal.zzgq
    @Pure
    public final Clock zzav() {
        return this.zzr;
    }

    @Override // com.google.android.gms.measurement.internal.zzgq
    @Pure
    public final zzaa zzaw() {
        return this.zzj;
    }

    @Override // com.google.android.gms.measurement.internal.zzgq
    @Pure
    public final zzel zzay() {
        zzR(this.zzm);
        return this.zzm;
    }

    @Override // com.google.android.gms.measurement.internal.zzgq
    @Pure
    public final zzfs zzaz() {
        zzR(this.zzn);
        return this.zzn;
    }

    @Pure
    public final zzd zzd() {
        zzd zzdVar = this.zzu;
        if (zzdVar != null) {
            return zzdVar;
        }
        throw new IllegalStateException("Component not created");
    }

    @Pure
    public final zzaf zzf() {
        return this.zzk;
    }

    @Pure
    public final zzan zzg() {
        zzR(this.zzz);
        return this.zzz;
    }

    @Pure
    public final zzec zzh() {
        zzQ(this.zzA);
        return this.zzA;
    }

    @Pure
    public final zzee zzi() {
        zzQ(this.zzx);
        return this.zzx;
    }

    @Pure
    public final zzeg zzj() {
        return this.zzq;
    }

    public final zzel zzl() {
        zzel zzelVar = this.zzm;
        if (zzelVar == null || !zzelVar.zzx()) {
            return null;
        }
        return this.zzm;
    }

    @Pure
    public final zzfa zzm() {
        zzP(this.zzl);
        return this.zzl;
    }

    @SideEffectFree
    final zzfs zzo() {
        return this.zzn;
    }

    @Pure
    public final zzia zzq() {
        zzQ(this.zzt);
        return this.zzt;
    }

    @Pure
    public final zzie zzr() {
        zzR(this.zzv);
        return this.zzv;
    }

    @Pure
    public final zzio zzs() {
        zzQ(this.zzs);
        return this.zzs;
    }

    @Pure
    public final zzjo zzt() {
        zzQ(this.zzy);
        return this.zzy;
    }

    @Pure
    public final zzkd zzu() {
        zzQ(this.zzo);
        return this.zzo;
    }

    @Pure
    public final zzkz zzv() {
        zzP(this.zzp);
        return this.zzp;
    }

    @Pure
    public final String zzw() {
        return this.zzf;
    }

    @Pure
    public final String zzx() {
        return this.zzg;
    }

    @Pure
    public final String zzy() {
        return this.zzh;
    }

    @Pure
    public final String zzz() {
        return this.zzw;
    }
}
