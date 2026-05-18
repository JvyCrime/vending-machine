package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.wrappers.Wrappers;
import com.google.android.gms.internal.measurement.zzoe;
import com.google.android.gms.internal.measurement.zzot;
import com.google.android.gms.internal.measurement.zzqd;
import iaik.security.ssl.SecurityProvider;
import java.security.MessageDigest;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zzec extends zzf {
    private String zza;
    private String zzb;
    private int zzc;
    private String zzd;
    private String zze;
    private long zzf;
    private final long zzg;
    private List<String> zzh;
    private int zzi;
    private String zzj;
    private String zzk;
    private String zzl;

    zzec(zzfv zzfvVar, long j) {
        super(zzfvVar);
        this.zzg = j;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(21:0|2|(1:4)(23:106|6|(1:10)(2:11|(1:13))|100|14|(4:16|(1:18)(1:20)|104|21)|26|(1:31)(1:30)|32|SW:33|43|(1:45)|46|102|47|(1:49)(1:50)|51|52|(6:54|(1:56)(1:57)|58|(1:60)|61|(1:65))(2:66|(3:68|(1:70)(1:71)|72))|(3:74|(1:76)(1:77)|78)|82|(2:85|(1:87)(4:88|(3:91|(1:109)(1:110)|89)|108|94))(1:94)|(2:96|97)(2:98|99))|5|26|(2:28|31)(0)|32|SW:33|43|(0)|46|102|47|(0)(0)|51|52|(0)(0)|(0)|82|(0)(0)|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0232, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0233, code lost:
    
        r13.zzs.zzay().zzd().zzc("Fetching Google App Id failed with exception. appId", com.google.android.gms.measurement.internal.zzel.zzn(r0), r3);
     */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01a7 A[Catch: IllegalStateException -> 0x0232, TRY_ENTER, TryCatch #1 {IllegalStateException -> 0x0232, blocks: (B:47:0x0177, B:51:0x0192, B:54:0x01a7, B:58:0x01c5, B:61:0x01d2, B:63:0x01da, B:74:0x0213, B:76:0x0229, B:78:0x022e, B:77:0x022c, B:65:0x01e0, B:57:0x01c1, B:66:0x01e7, B:68:0x01ed, B:72:0x020b, B:71:0x0207), top: B:102:0x0177 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01e7 A[Catch: IllegalStateException -> 0x0232, TryCatch #1 {IllegalStateException -> 0x0232, blocks: (B:47:0x0177, B:51:0x0192, B:54:0x01a7, B:58:0x01c5, B:61:0x01d2, B:63:0x01da, B:74:0x0213, B:76:0x0229, B:78:0x022e, B:77:0x022c, B:65:0x01e0, B:57:0x01c1, B:66:0x01e7, B:68:0x01ed, B:72:0x020b, B:71:0x0207), top: B:102:0x0177 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0213 A[Catch: IllegalStateException -> 0x0232, TryCatch #1 {IllegalStateException -> 0x0232, blocks: (B:47:0x0177, B:51:0x0192, B:54:0x01a7, B:58:0x01c5, B:61:0x01d2, B:63:0x01da, B:74:0x0213, B:76:0x0229, B:78:0x022e, B:77:0x022c, B:65:0x01e0, B:57:0x01c1, B:66:0x01e7, B:68:0x01ed, B:72:0x020b, B:71:0x0207), top: B:102:0x0177 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0291  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0295  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x02a2  */
    @Override // com.google.android.gms.measurement.internal.zzf
    @org.checkerframework.checker.nullness.qual.EnsuresNonNull({"appId", "appStore", "appName", "gmpAppId", "gaAppId"})
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected final void zzd() {
        /*
            Method dump skipped, instruction units count: 698
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzec.zzd():void");
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    protected final boolean zzf() {
        return true;
    }

    final int zzh() {
        zza();
        return this.zzi;
    }

    final int zzi() {
        zza();
        return this.zzc;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v34, types: [com.google.android.gms.measurement.internal.zzgo, com.google.android.gms.measurement.internal.zzkz] */
    /* JADX WARN: Type inference failed for: r9v35, types: [com.google.android.gms.measurement.internal.zzgo] */
    /* JADX WARN: Type inference failed for: r9v39 */
    /* JADX WARN: Type inference failed for: r9v44 */
    /* JADX WARN: Type inference failed for: r9v45 */
    /* JADX WARN: Type inference failed for: r9v46 */
    final zzp zzj(String str) {
        String str2;
        Class<?> clsLoadClass;
        Object objInvoke;
        String str3;
        long jMin;
        long j;
        zzg();
        String strZzl = zzl();
        String strZzn = zzn();
        zza();
        String str4 = this.zzb;
        zza();
        long j2 = this.zzc;
        zza();
        Preconditions.checkNotNull(this.zzd);
        String str5 = this.zzd;
        this.zzs.zzf().zzh();
        zza();
        zzg();
        long j3 = this.zzf;
        long j4 = j3;
        if (j3 == 0) {
            ?? Zzv = this.zzs.zzv();
            Context contextZzau = this.zzs.zzau();
            String packageName = this.zzs.zzau().getPackageName();
            Zzv.zzg();
            Preconditions.checkNotNull(contextZzau);
            Preconditions.checkNotEmpty(packageName);
            PackageManager packageManager = contextZzau.getPackageManager();
            MessageDigest messageDigestZzE = zzkz.zzE(SecurityProvider.ALG_DIGEST_MD5);
            long j5 = -1;
            j5 = -1;
            if (messageDigestZzE == null) {
                Zzv.zzs.zzay().zzd().zza("Could not get MD5 instance");
            } else {
                if (packageManager != null) {
                    try {
                        if (Zzv.zzaf(contextZzau, packageName)) {
                            j5 = 0;
                            Zzv = Zzv;
                        } else {
                            PackageInfo packageInfo = Wrappers.packageManager(contextZzau).getPackageInfo(Zzv.zzs.zzau().getPackageName(), 64);
                            if (packageInfo.signatures == null || packageInfo.signatures.length <= 0) {
                                Zzv.zzs.zzay().zzk().zza("Could not get signatures");
                                Zzv = Zzv;
                            } else {
                                long jZzp = zzkz.zzp(messageDigestZzE.digest(packageInfo.signatures[0].toByteArray()));
                                j5 = jZzp;
                                Zzv = jZzp;
                            }
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        Zzv.zzs.zzay().zzd().zzb("Package name not found", e);
                        j = 0;
                    }
                }
                j = 0;
                this.zzf = j;
                j4 = j;
            }
            j = j5;
            this.zzf = j;
            j4 = j;
        }
        long j6 = j4;
        boolean zZzJ = this.zzs.zzJ();
        boolean z = !this.zzs.zzm().zzk;
        zzg();
        if (this.zzs.zzJ()) {
            zzqd.zzc();
            if (this.zzs.zzf().zzs(null, zzdy.zzaf)) {
                this.zzs.zzay().zzj().zza("Disabled IID for tests.");
            } else {
                try {
                    clsLoadClass = this.zzs.zzau().getClassLoader().loadClass("com.google.firebase.analytics.FirebaseAnalytics");
                } catch (ClassNotFoundException unused) {
                }
                if (clsLoadClass != null) {
                    try {
                        objInvoke = clsLoadClass.getDeclaredMethod("getInstance", Context.class).invoke(null, this.zzs.zzau());
                    } catch (Exception unused2) {
                        this.zzs.zzay().zzm().zza("Failed to obtain Firebase Analytics instance");
                    }
                    if (objInvoke == null) {
                        str2 = null;
                    } else {
                        try {
                            str2 = (String) clsLoadClass.getDeclaredMethod("getFirebaseInstanceId", new Class[0]).invoke(objInvoke, new Object[0]);
                        } catch (Exception unused3) {
                            this.zzs.zzay().zzl().zza("Failed to retrieve Firebase Instance Id");
                            str2 = null;
                        }
                    }
                }
            }
            str2 = null;
        } else {
            str2 = null;
        }
        zzfv zzfvVar = this.zzs;
        long jZza = zzfvVar.zzm().zzc.zza();
        if (jZza == 0) {
            str3 = strZzl;
            jMin = zzfvVar.zzc;
        } else {
            str3 = strZzl;
            jMin = Math.min(zzfvVar.zzc, jZza);
        }
        zza();
        int i = this.zzi;
        boolean zZzr = this.zzs.zzf().zzr();
        zzfa zzfaVarZzm = this.zzs.zzm();
        zzfaVarZzm.zzg();
        boolean z2 = zzfaVarZzm.zza().getBoolean("deferred_analytics_collection", false);
        zza();
        String str6 = this.zzk;
        Boolean boolValueOf = this.zzs.zzf().zzk("google_analytics_default_allow_ad_personalization_signals") == null ? null : Boolean.valueOf(!r2.booleanValue());
        long j7 = this.zzg;
        List<String> list = this.zzh;
        zzot.zzc();
        return new zzp(str3, strZzn, str4, j2, str5, 43042L, j6, str, zZzJ, z, str2, 0L, jMin, i, zZzr, z2, str6, boolValueOf, j7, list, this.zzs.zzf().zzs(null, zzdy.zzad) ? zzm() : null, this.zzs.zzm().zzc().zzi());
    }

    final String zzk() {
        zza();
        return this.zzk;
    }

    final String zzl() {
        zza();
        Preconditions.checkNotNull(this.zza);
        return this.zza;
    }

    final String zzm() {
        zza();
        Preconditions.checkNotNull(this.zzl);
        return this.zzl;
    }

    final String zzn() {
        zzoe.zzc();
        if (this.zzs.zzf().zzs(null, zzdy.zzau)) {
            zzg();
        }
        zza();
        Preconditions.checkNotNull(this.zzj);
        return this.zzj;
    }

    final List<String> zzo() {
        return this.zzh;
    }
}
