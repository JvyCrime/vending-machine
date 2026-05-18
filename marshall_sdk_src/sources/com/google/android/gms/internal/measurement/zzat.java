package com.google.android.gms.internal.measurement;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.text.Typography;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zzat implements Iterable<zzap>, zzap {
    private final String zza;

    public zzat(String str) {
        if (str == null) {
            throw new IllegalArgumentException("StringValue cannot be null.");
        }
        this.zza = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzat) {
            return this.zza.equals(((zzat) obj).zza);
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    @Override // java.lang.Iterable
    public final Iterator<zzap> iterator() {
        return new zzas(this);
    }

    public final String toString() {
        String str = this.zza;
        StringBuilder sb = new StringBuilder(str.length() + 2);
        sb.append(Typography.quote);
        sb.append(str);
        sb.append(Typography.quote);
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v101 */
    /* JADX WARN: Type inference failed for: r0v78 */
    /* JADX WARN: Type inference failed for: r0v79, types: [int] */
    @Override // com.google.android.gms.internal.measurement.zzap
    public final zzap zzbK(String str, zzg zzgVar, List<zzap> list) {
        String str2;
        String str3;
        String str4;
        String str5;
        byte b;
        zzat zzatVar;
        int i;
        zzap zzahVar;
        String strZzi;
        zzat zzatVar2;
        int i2;
        boolean z;
        zzg zzgVar2;
        int iZza;
        if ("charAt".equals(str) || "concat".equals(str) || "hasOwnProperty".equals(str) || "indexOf".equals(str) || "lastIndexOf".equals(str) || "match".equals(str) || "replace".equals(str) || FirebaseAnalytics.Event.SEARCH.equals(str) || "slice".equals(str) || "split".equals(str) || "substring".equals(str) || "toLowerCase".equals(str) || "toLocaleLowerCase".equals(str) || "toString".equals(str) || "toUpperCase".equals(str)) {
            str2 = "toLocaleUpperCase";
        } else {
            str2 = "toLocaleUpperCase";
            if (!str2.equals(str) && !"trim".equals(str)) {
                throw new IllegalArgumentException(String.format("%s is not a String function", str));
            }
        }
        switch (str.hashCode()) {
            case -1789698943:
                str3 = "charAt";
                str4 = "hasOwnProperty";
                if (str.equals(str4)) {
                    str5 = "toString";
                    b = 2;
                }
                b = -1;
                str5 = "toString";
                break;
            case -1776922004:
                str3 = "charAt";
                if (str.equals("toString")) {
                    b = 14;
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                }
                str4 = "hasOwnProperty";
                b = -1;
                str5 = "toString";
                break;
            case -1464939364:
                str3 = "charAt";
                if (str.equals("toLocaleLowerCase")) {
                    b = 12;
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                }
                str4 = "hasOwnProperty";
                b = -1;
                str5 = "toString";
                break;
            case -1361633751:
                str3 = "charAt";
                if (str.equals(str3)) {
                    str4 = "hasOwnProperty";
                    b = 0;
                    str5 = "toString";
                }
                str4 = "hasOwnProperty";
                b = -1;
                str5 = "toString";
                break;
            case -1354795244:
                if (str.equals("concat")) {
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    b = 1;
                    str5 = "toString";
                } else {
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    b = -1;
                    str5 = "toString";
                }
                break;
            case -1137582698:
                if (str.equals("toLowerCase")) {
                    b = 13;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                } else {
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    b = -1;
                    str5 = "toString";
                }
                break;
            case -906336856:
                if (str.equals(FirebaseAnalytics.Event.SEARCH)) {
                    b = 7;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                } else {
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    b = -1;
                    str5 = "toString";
                }
                break;
            case -726908483:
                if (str.equals(str2)) {
                    b = 11;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                } else {
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    b = -1;
                    str5 = "toString";
                }
                break;
            case -467511597:
                if (str.equals("lastIndexOf")) {
                    b = 4;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                } else {
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    b = -1;
                    str5 = "toString";
                }
                break;
            case -399551817:
                if (str.equals("toUpperCase")) {
                    b = 15;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                } else {
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    b = -1;
                    str5 = "toString";
                }
                break;
            case 3568674:
                if (str.equals("trim")) {
                    b = 16;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                } else {
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    b = -1;
                    str5 = "toString";
                }
                break;
            case 103668165:
                if (str.equals("match")) {
                    b = 5;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                } else {
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    b = -1;
                    str5 = "toString";
                }
                break;
            case 109526418:
                if (str.equals("slice")) {
                    b = 8;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                } else {
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    b = -1;
                    str5 = "toString";
                }
                break;
            case 109648666:
                if (str.equals("split")) {
                    b = 9;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                } else {
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    b = -1;
                    str5 = "toString";
                }
                break;
            case 530542161:
                if (str.equals("substring")) {
                    b = 10;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                } else {
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    b = -1;
                    str5 = "toString";
                }
                break;
            case 1094496948:
                if (str.equals("replace")) {
                    b = 6;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                } else {
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    b = -1;
                    str5 = "toString";
                }
                break;
            case 1943291465:
                if (str.equals("indexOf")) {
                    b = 3;
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    str5 = "toString";
                } else {
                    str3 = "charAt";
                    str4 = "hasOwnProperty";
                    b = -1;
                    str5 = "toString";
                }
                break;
            default:
                str3 = "charAt";
                str4 = "hasOwnProperty";
                b = -1;
                str5 = "toString";
                break;
        }
        String str6 = str3;
        switch (b) {
            case 0:
                zzh.zzj(str6, 1, list);
                int iZza2 = list.size() > 0 ? (int) zzh.zza(zzgVar.zzb(list.get(0)).zzh().doubleValue()) : 0;
                String str7 = this.zza;
                return (iZza2 < 0 || iZza2 >= str7.length()) ? zzap.zzm : new zzat(String.valueOf(str7.charAt(iZza2)));
            case 1:
                zzatVar = this;
                if (list.size() != 0) {
                    StringBuilder sb = new StringBuilder(zzatVar.zza);
                    for (int i3 = 0; i3 < list.size(); i3++) {
                        sb.append(zzgVar.zzb(list.get(i3)).zzi());
                    }
                    return new zzat(sb.toString());
                }
                return zzatVar;
            case 2:
                zzh.zzh(str4, 1, list);
                String str8 = this.zza;
                zzap zzapVarZzb = zzgVar.zzb(list.get(0));
                if ("length".equals(zzapVarZzb.zzi())) {
                    return zzaf.zzk;
                }
                double dDoubleValue = zzapVarZzb.zzh().doubleValue();
                return (dDoubleValue != Math.floor(dDoubleValue) || (i = (int) dDoubleValue) < 0 || i >= str8.length()) ? zzaf.zzl : zzaf.zzk;
            case 3:
                zzh.zzj("indexOf", 2, list);
                zzahVar = new zzah(Double.valueOf(this.zza.indexOf(list.size() > 0 ? zzgVar.zzb(list.get(0)).zzi() : "undefined", (int) zzh.zza(list.size() < 2 ? 0.0d : zzgVar.zzb(list.get(1)).zzh().doubleValue()))));
                return zzahVar;
            case 4:
                zzh.zzj("lastIndexOf", 2, list);
                String str9 = this.zza;
                String strZzi2 = list.size() > 0 ? zzgVar.zzb(list.get(0)).zzi() : "undefined";
                return new zzah(Double.valueOf(str9.lastIndexOf(strZzi2, (int) (Double.isNaN(list.size() < 2 ? Double.NaN : zzgVar.zzb(list.get(1)).zzh().doubleValue()) ? Double.POSITIVE_INFINITY : zzh.zza(r3)))));
            case 5:
                zzh.zzj("match", 1, list);
                Matcher matcher = Pattern.compile(list.size() > 0 ? zzgVar.zzb(list.get(0)).zzi() : "").matcher(this.zza);
                return matcher.find() ? new zzae(Arrays.asList(new zzat(matcher.group()))) : zzap.zzg;
            case 6:
                zzatVar = this;
                zzh.zzj("replace", 2, list);
                zzap zzapVarZza = zzap.zzf;
                if (list.size() > 0) {
                    strZzi = zzgVar.zzb(list.get(0)).zzi();
                    if (list.size() > 1) {
                        zzapVarZza = zzgVar.zzb(list.get(1));
                    }
                }
                String str10 = strZzi;
                String str11 = zzatVar.zza;
                int iIndexOf = str11.indexOf(str10);
                if (iIndexOf >= 0) {
                    if (zzapVarZza instanceof zzai) {
                        zzapVarZza = ((zzai) zzapVarZza).zza(zzgVar, Arrays.asList(new zzat(str10), new zzah(Double.valueOf(iIndexOf)), zzatVar));
                    }
                    String strSubstring = str11.substring(0, iIndexOf);
                    String strZzi3 = zzapVarZza.zzi();
                    String strSubstring2 = str11.substring(iIndexOf + str10.length());
                    StringBuilder sb2 = new StringBuilder(String.valueOf(strSubstring).length() + String.valueOf(strZzi3).length() + String.valueOf(strSubstring2).length());
                    sb2.append(strSubstring);
                    sb2.append(strZzi3);
                    sb2.append(strSubstring2);
                    zzahVar = new zzat(sb2.toString());
                    return zzahVar;
                }
                return zzatVar;
            case 7:
                zzh.zzj(FirebaseAnalytics.Event.SEARCH, 1, list);
                return Pattern.compile(list.size() > 0 ? zzgVar.zzb(list.get(0)).zzi() : "undefined").matcher(this.zza).find() ? new zzah(Double.valueOf(r0.start())) : new zzah(Double.valueOf(-1.0d));
            case 8:
                zzh.zzj("slice", 2, list);
                String str12 = this.zza;
                double dZza = zzh.zza(list.size() > 0 ? zzgVar.zzb(list.get(0)).zzh().doubleValue() : 0.0d);
                int iMax = (int) (dZza < 0.0d ? Math.max(((double) str12.length()) + dZza, 0.0d) : Math.min(dZza, str12.length()));
                double dZza2 = zzh.zza(list.size() > 1 ? zzgVar.zzb(list.get(1)).zzh().doubleValue() : str12.length());
                zzatVar2 = new zzat(str12.substring(iMax, Math.max(0, ((int) (dZza2 < 0.0d ? Math.max(((double) str12.length()) + dZza2, 0.0d) : Math.min(dZza2, str12.length()))) - iMax) + iMax));
                return zzatVar2;
            case 9:
                zzh.zzj("split", 2, list);
                String str13 = this.zza;
                if (str13.length() == 0) {
                    return new zzae(Arrays.asList(this));
                }
                ArrayList arrayList = new ArrayList();
                if (list.size() == 0) {
                    arrayList.add(this);
                } else {
                    String strZzi4 = zzgVar.zzb(list.get(0)).zzi();
                    long jZzd = list.size() > 1 ? zzh.zzd(zzgVar.zzb(list.get(1)).zzh().doubleValue()) : 2147483647L;
                    if (jZzd == 0) {
                        return new zzae();
                    }
                    String[] strArrSplit = str13.split(Pattern.quote(strZzi4), ((int) jZzd) + 1);
                    int length = strArrSplit.length;
                    if (!strZzi4.equals("") || length <= 0) {
                        i2 = length;
                        z = false;
                    } else {
                        boolean zEquals = strArrSplit[0].equals("");
                        i2 = length - 1;
                        if (!strArrSplit[i2].equals("")) {
                            i2 = length;
                        }
                        z = zEquals;
                    }
                    if (length > jZzd) {
                        i2--;
                    }
                    for (?? r0 = z; r0 < i2; r0++) {
                        arrayList.add(new zzat(strArrSplit[r0]));
                    }
                }
                return new zzae(arrayList);
            case 10:
                zzh.zzj("substring", 2, list);
                String str14 = this.zza;
                if (list.size() > 0) {
                    zzgVar2 = zzgVar;
                    iZza = (int) zzh.zza(zzgVar2.zzb(list.get(0)).zzh().doubleValue());
                } else {
                    zzgVar2 = zzgVar;
                    iZza = 0;
                }
                int iZza3 = list.size() > 1 ? (int) zzh.zza(zzgVar2.zzb(list.get(1)).zzh().doubleValue()) : str14.length();
                int iMin = Math.min(Math.max(iZza, 0), str14.length());
                int iMin2 = Math.min(Math.max(iZza3, 0), str14.length());
                zzatVar2 = new zzat(str14.substring(Math.min(iMin, iMin2), Math.max(iMin, iMin2)));
                return zzatVar2;
            case 11:
                zzh.zzh(str2, 0, list);
                return new zzat(this.zza.toUpperCase());
            case 12:
                zzh.zzh("toLocaleLowerCase", 0, list);
                return new zzat(this.zza.toLowerCase());
            case 13:
                zzh.zzh("toLowerCase", 0, list);
                return new zzat(this.zza.toLowerCase(Locale.ENGLISH));
            case 14:
                zzatVar = this;
                zzh.zzh(str5, 0, list);
                return zzatVar;
            case 15:
                zzh.zzh("toUpperCase", 0, list);
                return new zzat(this.zza.toUpperCase(Locale.ENGLISH));
            case 16:
                zzh.zzh("toUpperCase", 0, list);
                return new zzat(this.zza.trim());
            default:
                throw new IllegalArgumentException("Command not supported");
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzap
    public final zzap zzd() {
        return new zzat(this.zza);
    }

    @Override // com.google.android.gms.internal.measurement.zzap
    public final Boolean zzg() {
        return Boolean.valueOf(!this.zza.isEmpty());
    }

    @Override // com.google.android.gms.internal.measurement.zzap
    public final Double zzh() {
        if (this.zza.isEmpty()) {
            return Double.valueOf(0.0d);
        }
        try {
            return Double.valueOf(this.zza);
        } catch (NumberFormatException unused) {
            return Double.valueOf(Double.NaN);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zzap
    public final String zzi() {
        return this.zza;
    }

    @Override // com.google.android.gms.internal.measurement.zzap
    public final Iterator<zzap> zzl() {
        return new zzar(this);
    }
}
