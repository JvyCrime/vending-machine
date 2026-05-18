package com.google.android.gms.measurement.internal;

import android.util.Pair;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import iaik.security.ssl.SecurityProvider;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Locale;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zzjp extends zzki {
    public final zzew zza;
    public final zzew zzb;
    public final zzew zzc;
    public final zzew zzd;
    public final zzew zze;
    private String zzg;
    private boolean zzh;
    private long zzi;

    zzjp(zzks zzksVar) {
        super(zzksVar);
        zzfa zzfaVarZzm = this.zzs.zzm();
        zzfaVarZzm.getClass();
        this.zza = new zzew(zzfaVarZzm, "last_delete_stale", 0L);
        zzfa zzfaVarZzm2 = this.zzs.zzm();
        zzfaVarZzm2.getClass();
        this.zzb = new zzew(zzfaVarZzm2, "backoff", 0L);
        zzfa zzfaVarZzm3 = this.zzs.zzm();
        zzfaVarZzm3.getClass();
        this.zzc = new zzew(zzfaVarZzm3, "last_upload", 0L);
        zzfa zzfaVarZzm4 = this.zzs.zzm();
        zzfaVarZzm4.getClass();
        this.zzd = new zzew(zzfaVarZzm4, "last_upload_attempt", 0L);
        zzfa zzfaVarZzm5 = this.zzs.zzm();
        zzfaVarZzm5.getClass();
        this.zze = new zzew(zzfaVarZzm5, "midnight_offset", 0L);
    }

    @Deprecated
    final Pair<String, Boolean> zza(String str) {
        zzg();
        long jElapsedRealtime = this.zzs.zzav().elapsedRealtime();
        String str2 = this.zzg;
        if (str2 != null && jElapsedRealtime < this.zzi) {
            return new Pair<>(str2, Boolean.valueOf(this.zzh));
        }
        this.zzi = jElapsedRealtime + this.zzs.zzf().zzi(str, zzdy.zza);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        try {
            AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.zzs.zzau());
            this.zzg = "";
            String id = advertisingIdInfo.getId();
            if (id != null) {
                this.zzg = id;
            }
            this.zzh = advertisingIdInfo.isLimitAdTrackingEnabled();
        } catch (Exception e) {
            this.zzs.zzay().zzc().zzb("Unable to get advertising id", e);
            this.zzg = "";
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair<>(this.zzg, Boolean.valueOf(this.zzh));
    }

    @Override // com.google.android.gms.measurement.internal.zzki
    protected final boolean zzb() {
        return false;
    }

    final Pair<String, Boolean> zzd(String str, zzag zzagVar) {
        return zzagVar.zzj() ? zza(str) : new Pair<>("", false);
    }

    @Deprecated
    final String zzf(String str) {
        zzg();
        String str2 = (String) zza(str).first;
        MessageDigest messageDigestZzE = zzkz.zzE(SecurityProvider.ALG_DIGEST_MD5);
        if (messageDigestZzE == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, messageDigestZzE.digest(str2.getBytes())));
    }
}
