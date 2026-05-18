package com.google.android.gms.measurement.internal;

import android.os.Bundle;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zzag {
    public static final zzag zza = new zzag(null, null);
    private final Boolean zzb;
    private final Boolean zzc;

    public zzag(Boolean bool, Boolean bool2) {
        this.zzb = bool;
        this.zzc = bool2;
    }

    public static zzag zza(Bundle bundle) {
        return bundle == null ? zza : new zzag(zzo(bundle.getString("ad_storage")), zzo(bundle.getString("analytics_storage")));
    }

    public static zzag zzb(String str) {
        Boolean boolZzp;
        if (str != null) {
            Boolean boolZzp2 = str.length() >= 3 ? zzp(str.charAt(2)) : null;
            boolZzp = str.length() >= 4 ? zzp(str.charAt(3)) : null;
            bool = boolZzp2;
        } else {
            boolZzp = null;
        }
        return new zzag(bool, boolZzp);
    }

    static Boolean zzg(Boolean bool, Boolean bool2) {
        if (bool == null) {
            return bool2;
        }
        if (bool2 == null) {
            return bool;
        }
        boolean z = false;
        if (bool.booleanValue() && bool2.booleanValue()) {
            z = true;
        }
        return Boolean.valueOf(z);
    }

    public static String zzh(Bundle bundle) {
        String string = bundle.getString("ad_storage");
        if (string != null && zzo(string) == null) {
            return string;
        }
        String string2 = bundle.getString("analytics_storage");
        if (string2 == null || zzo(string2) != null) {
            return null;
        }
        return string2;
    }

    public static boolean zzl(int i, int i2) {
        return i <= i2;
    }

    static final int zzn(Boolean bool) {
        if (bool == null) {
            return 0;
        }
        return bool.booleanValue() ? 1 : 2;
    }

    private static Boolean zzo(String str) {
        if (str == null) {
            return null;
        }
        if (str.equals("granted")) {
            return Boolean.TRUE;
        }
        if (str.equals("denied")) {
            return Boolean.FALSE;
        }
        return null;
    }

    private static Boolean zzp(char c) {
        if (c == '0') {
            return Boolean.FALSE;
        }
        if (c != '1') {
            return null;
        }
        return Boolean.TRUE;
    }

    private static final char zzq(Boolean bool) {
        if (bool == null) {
            return '-';
        }
        return bool.booleanValue() ? '1' : '0';
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzag)) {
            return false;
        }
        zzag zzagVar = (zzag) obj;
        return zzn(this.zzb) == zzn(zzagVar.zzb) && zzn(this.zzc) == zzn(zzagVar.zzc);
    }

    public final int hashCode() {
        return ((zzn(this.zzb) + 527) * 31) + zzn(this.zzc);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ConsentSettings: ");
        sb.append("adStorage=");
        Boolean bool = this.zzb;
        if (bool == null) {
            sb.append("uninitialized");
        } else {
            sb.append(true != bool.booleanValue() ? "denied" : "granted");
        }
        sb.append(", analyticsStorage=");
        Boolean bool2 = this.zzc;
        if (bool2 == null) {
            sb.append("uninitialized");
        } else {
            sb.append(true == bool2.booleanValue() ? "granted" : "denied");
        }
        return sb.toString();
    }

    public final zzag zzc(zzag zzagVar) {
        return new zzag(zzg(this.zzb, zzagVar.zzb), zzg(this.zzc, zzagVar.zzc));
    }

    public final zzag zzd(zzag zzagVar) {
        Boolean bool = this.zzb;
        if (bool == null) {
            bool = zzagVar.zzb;
        }
        Boolean bool2 = this.zzc;
        if (bool2 == null) {
            bool2 = zzagVar.zzc;
        }
        return new zzag(bool, bool2);
    }

    public final Boolean zze() {
        return this.zzb;
    }

    public final Boolean zzf() {
        return this.zzc;
    }

    public final String zzi() {
        return "G1" + zzq(this.zzb) + zzq(this.zzc);
    }

    public final boolean zzj() {
        Boolean bool = this.zzb;
        return bool == null || bool.booleanValue();
    }

    public final boolean zzk() {
        Boolean bool = this.zzc;
        return bool == null || bool.booleanValue();
    }

    public final boolean zzm(zzag zzagVar) {
        if (this.zzb != Boolean.FALSE || zzagVar.zzb == Boolean.FALSE) {
            return this.zzc == Boolean.FALSE && zzagVar.zzc != Boolean.FALSE;
        }
        return true;
    }
}
