package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.android.gms.internal.measurement.zzlf;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import iaik.security.ssl.SecurityProvider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zzku extends zzki {
    zzku(zzks zzksVar) {
        super(zzksVar);
    }

    static final void zzA(com.google.android.gms.internal.measurement.zzfn zzfnVar, String str, Object obj) {
        List<com.google.android.gms.internal.measurement.zzfs> listZzp = zzfnVar.zzp();
        int i = 0;
        while (true) {
            if (i >= listZzp.size()) {
                i = -1;
                break;
            } else if (str.equals(listZzp.get(i).zzg())) {
                break;
            } else {
                i++;
            }
        }
        com.google.android.gms.internal.measurement.zzfr zzfrVarZze = com.google.android.gms.internal.measurement.zzfs.zze();
        zzfrVarZze.zzj(str);
        if (obj instanceof Long) {
            zzfrVarZze.zzi(((Long) obj).longValue());
        } else if (obj instanceof String) {
            zzfrVarZze.zzk((String) obj);
        } else if (obj instanceof Double) {
            zzfrVarZze.zzh(((Double) obj).doubleValue());
        } else if (obj instanceof Bundle[]) {
            zzfrVarZze.zzb(zzq((Bundle[]) obj));
        }
        if (i >= 0) {
            zzfnVar.zzj(i, zzfrVarZze);
        } else {
            zzfnVar.zze(zzfrVarZze);
        }
    }

    static final boolean zzB(zzat zzatVar, zzp zzpVar) {
        Preconditions.checkNotNull(zzatVar);
        Preconditions.checkNotNull(zzpVar);
        return (TextUtils.isEmpty(zzpVar.zzb) && TextUtils.isEmpty(zzpVar.zzq)) ? false : true;
    }

    static final com.google.android.gms.internal.measurement.zzfs zzC(com.google.android.gms.internal.measurement.zzfo zzfoVar, String str) {
        for (com.google.android.gms.internal.measurement.zzfs zzfsVar : zzfoVar.zzi()) {
            if (zzfsVar.zzg().equals(str)) {
                return zzfsVar;
            }
        }
        return null;
    }

    static final Object zzD(com.google.android.gms.internal.measurement.zzfo zzfoVar, String str) {
        com.google.android.gms.internal.measurement.zzfs zzfsVarZzC = zzC(zzfoVar, str);
        if (zzfsVarZzC == null) {
            return null;
        }
        if (zzfsVarZzC.zzy()) {
            return zzfsVarZzC.zzh();
        }
        if (zzfsVarZzC.zzw()) {
            return Long.valueOf(zzfsVarZzC.zzd());
        }
        if (zzfsVarZzC.zzu()) {
            return Double.valueOf(zzfsVarZzC.zza());
        }
        if (zzfsVarZzC.zzc() <= 0) {
            return null;
        }
        List<com.google.android.gms.internal.measurement.zzfs> listZzi = zzfsVarZzC.zzi();
        ArrayList arrayList = new ArrayList();
        for (com.google.android.gms.internal.measurement.zzfs zzfsVar : listZzi) {
            if (zzfsVar != null) {
                Bundle bundle = new Bundle();
                for (com.google.android.gms.internal.measurement.zzfs zzfsVar2 : zzfsVar.zzi()) {
                    if (zzfsVar2.zzy()) {
                        bundle.putString(zzfsVar2.zzg(), zzfsVar2.zzh());
                    } else if (zzfsVar2.zzw()) {
                        bundle.putLong(zzfsVar2.zzg(), zzfsVar2.zzd());
                    } else if (zzfsVar2.zzu()) {
                        bundle.putDouble(zzfsVar2.zzg(), zzfsVar2.zza());
                    }
                }
                if (!bundle.isEmpty()) {
                    arrayList.add(bundle);
                }
            }
        }
        return (Bundle[]) arrayList.toArray(new Bundle[arrayList.size()]);
    }

    private final void zzE(StringBuilder sb, int i, List<com.google.android.gms.internal.measurement.zzfs> list) {
        if (list == null) {
            return;
        }
        int i2 = i + 1;
        for (com.google.android.gms.internal.measurement.zzfs zzfsVar : list) {
            if (zzfsVar != null) {
                zzG(sb, i2);
                sb.append("param {\n");
                zzJ(sb, i2, AppMeasurementSdk.ConditionalUserProperty.NAME, zzfsVar.zzx() ? this.zzs.zzj().zze(zzfsVar.zzg()) : null);
                zzJ(sb, i2, "string_value", zzfsVar.zzy() ? zzfsVar.zzh() : null);
                zzJ(sb, i2, "int_value", zzfsVar.zzw() ? Long.valueOf(zzfsVar.zzd()) : null);
                zzJ(sb, i2, "double_value", zzfsVar.zzu() ? Double.valueOf(zzfsVar.zza()) : null);
                if (zzfsVar.zzc() > 0) {
                    zzE(sb, i2, zzfsVar.zzi());
                }
                zzG(sb, i2);
                sb.append("}\n");
            }
        }
    }

    private final void zzF(StringBuilder sb, int i, com.google.android.gms.internal.measurement.zzel zzelVar) {
        String str;
        if (zzelVar == null) {
            return;
        }
        zzG(sb, i);
        sb.append("filter {\n");
        if (zzelVar.zzh()) {
            zzJ(sb, i, "complement", Boolean.valueOf(zzelVar.zzg()));
        }
        if (zzelVar.zzj()) {
            zzJ(sb, i, "param_name", this.zzs.zzj().zze(zzelVar.zze()));
        }
        if (zzelVar.zzk()) {
            int i2 = i + 1;
            com.google.android.gms.internal.measurement.zzex zzexVarZzd = zzelVar.zzd();
            if (zzexVarZzd != null) {
                zzG(sb, i2);
                sb.append("string_filter {\n");
                if (zzexVarZzd.zzi()) {
                    switch (zzexVarZzd.zzj()) {
                        case 1:
                            str = "UNKNOWN_MATCH_TYPE";
                            break;
                        case 2:
                            str = "REGEXP";
                            break;
                        case 3:
                            str = "BEGINS_WITH";
                            break;
                        case 4:
                            str = "ENDS_WITH";
                            break;
                        case 5:
                            str = "PARTIAL";
                            break;
                        case 6:
                            str = "EXACT";
                            break;
                        default:
                            str = "IN_LIST";
                            break;
                    }
                    zzJ(sb, i2, "match_type", str);
                }
                if (zzexVarZzd.zzh()) {
                    zzJ(sb, i2, "expression", zzexVarZzd.zzd());
                }
                if (zzexVarZzd.zzg()) {
                    zzJ(sb, i2, "case_sensitive", Boolean.valueOf(zzexVarZzd.zzf()));
                }
                if (zzexVarZzd.zza() > 0) {
                    zzG(sb, i2 + 1);
                    sb.append("expression_list {\n");
                    for (String str2 : zzexVarZzd.zze()) {
                        zzG(sb, i2 + 2);
                        sb.append(str2);
                        sb.append("\n");
                    }
                    sb.append("}\n");
                }
                zzG(sb, i2);
                sb.append("}\n");
            }
        }
        if (zzelVar.zzi()) {
            zzK(sb, i + 1, "number_filter", zzelVar.zzc());
        }
        zzG(sb, i);
        sb.append("}\n");
    }

    private static final void zzG(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("  ");
        }
    }

    private static final String zzH(boolean z, boolean z2, boolean z3) {
        StringBuilder sb = new StringBuilder();
        if (z) {
            sb.append("Dynamic ");
        }
        if (z2) {
            sb.append("Sequence ");
        }
        if (z3) {
            sb.append("Session-Scoped ");
        }
        return sb.toString();
    }

    private static final void zzI(StringBuilder sb, int i, String str, com.google.android.gms.internal.measurement.zzgd zzgdVar) {
        if (zzgdVar == null) {
            return;
        }
        zzG(sb, 3);
        sb.append(str);
        sb.append(" {\n");
        if (zzgdVar.zzb() != 0) {
            zzG(sb, 4);
            sb.append("results: ");
            int i2 = 0;
            for (Long l : zzgdVar.zzk()) {
                int i3 = i2 + 1;
                if (i2 != 0) {
                    sb.append(", ");
                }
                sb.append(l);
                i2 = i3;
            }
            sb.append('\n');
        }
        if (zzgdVar.zzd() != 0) {
            zzG(sb, 4);
            sb.append("status: ");
            int i4 = 0;
            for (Long l2 : zzgdVar.zzn()) {
                int i5 = i4 + 1;
                if (i4 != 0) {
                    sb.append(", ");
                }
                sb.append(l2);
                i4 = i5;
            }
            sb.append('\n');
        }
        if (zzgdVar.zza() != 0) {
            zzG(sb, 4);
            sb.append("dynamic_filter_timestamps: {");
            int i6 = 0;
            for (com.google.android.gms.internal.measurement.zzfm zzfmVar : zzgdVar.zzj()) {
                int i7 = i6 + 1;
                if (i6 != 0) {
                    sb.append(", ");
                }
                sb.append(zzfmVar.zzh() ? Integer.valueOf(zzfmVar.zza()) : null);
                sb.append(":");
                sb.append(zzfmVar.zzg() ? Long.valueOf(zzfmVar.zzb()) : null);
                i6 = i7;
            }
            sb.append("}\n");
        }
        if (zzgdVar.zzc() != 0) {
            zzG(sb, 4);
            sb.append("sequence_filter_timestamps: {");
            int i8 = 0;
            for (com.google.android.gms.internal.measurement.zzgf zzgfVar : zzgdVar.zzm()) {
                int i9 = i8 + 1;
                if (i8 != 0) {
                    sb.append(", ");
                }
                sb.append(zzgfVar.zzi() ? Integer.valueOf(zzgfVar.zzb()) : null);
                sb.append(": [");
                Iterator<Long> it = zzgfVar.zzf().iterator();
                int i10 = 0;
                while (it.hasNext()) {
                    long jLongValue = it.next().longValue();
                    int i11 = i10 + 1;
                    if (i10 != 0) {
                        sb.append(", ");
                    }
                    sb.append(jLongValue);
                    i10 = i11;
                }
                sb.append("]");
                i8 = i9;
            }
            sb.append("}\n");
        }
        zzG(sb, 3);
        sb.append("}\n");
    }

    private static final void zzJ(StringBuilder sb, int i, String str, Object obj) {
        if (obj == null) {
            return;
        }
        zzG(sb, i + 1);
        sb.append(str);
        sb.append(": ");
        sb.append(obj);
        sb.append('\n');
    }

    private static final void zzK(StringBuilder sb, int i, String str, com.google.android.gms.internal.measurement.zzeq zzeqVar) {
        if (zzeqVar == null) {
            return;
        }
        zzG(sb, i);
        sb.append(str);
        sb.append(" {\n");
        if (zzeqVar.zzg()) {
            int iZzm = zzeqVar.zzm();
            zzJ(sb, i, "comparison_type", iZzm != 1 ? iZzm != 2 ? iZzm != 3 ? iZzm != 4 ? "BETWEEN" : "EQUAL" : "GREATER_THAN" : "LESS_THAN" : "UNKNOWN_COMPARISON_TYPE");
        }
        if (zzeqVar.zzi()) {
            zzJ(sb, i, "match_as_float", Boolean.valueOf(zzeqVar.zzf()));
        }
        if (zzeqVar.zzh()) {
            zzJ(sb, i, "comparison_value", zzeqVar.zzc());
        }
        if (zzeqVar.zzk()) {
            zzJ(sb, i, "min_comparison_value", zzeqVar.zze());
        }
        if (zzeqVar.zzj()) {
            zzJ(sb, i, "max_comparison_value", zzeqVar.zzd());
        }
        zzG(sb, i);
        sb.append("}\n");
    }

    static int zza(com.google.android.gms.internal.measurement.zzfx zzfxVar, String str) {
        for (int i = 0; i < zzfxVar.zzb(); i++) {
            if (str.equals(zzfxVar.zzak(i).zzf())) {
                return i;
            }
        }
        return -1;
    }

    static <Builder extends zzlf> Builder zzl(Builder builder, byte[] bArr) throws com.google.android.gms.internal.measurement.zzkj {
        com.google.android.gms.internal.measurement.zzjl zzjlVarZzb = com.google.android.gms.internal.measurement.zzjl.zzb();
        return zzjlVarZzb != null ? (Builder) builder.zzaw(bArr, zzjlVarZzb) : (Builder) builder.zzav(bArr);
    }

    static List<com.google.android.gms.internal.measurement.zzfs> zzq(Bundle[] bundleArr) {
        ArrayList arrayList = new ArrayList();
        for (Bundle bundle : bundleArr) {
            if (bundle != null) {
                com.google.android.gms.internal.measurement.zzfr zzfrVarZze = com.google.android.gms.internal.measurement.zzfs.zze();
                for (String str : bundle.keySet()) {
                    com.google.android.gms.internal.measurement.zzfr zzfrVarZze2 = com.google.android.gms.internal.measurement.zzfs.zze();
                    zzfrVarZze2.zzj(str);
                    Object obj = bundle.get(str);
                    if (obj instanceof Long) {
                        zzfrVarZze2.zzi(((Long) obj).longValue());
                    } else if (obj instanceof String) {
                        zzfrVarZze2.zzk((String) obj);
                    } else if (obj instanceof Double) {
                        zzfrVarZze2.zzh(((Double) obj).doubleValue());
                    }
                    zzfrVarZze.zzc(zzfrVarZze2);
                }
                if (zzfrVarZze.zza() > 0) {
                    arrayList.add(zzfrVarZze.zzaA());
                }
            }
        }
        return arrayList;
    }

    static List<Long> zzs(BitSet bitSet) {
        int length = (bitSet.length() + 63) / 64;
        ArrayList arrayList = new ArrayList(length);
        for (int i = 0; i < length; i++) {
            long j = 0;
            for (int i2 = 0; i2 < 64; i2++) {
                int i3 = (i * 64) + i2;
                if (i3 >= bitSet.length()) {
                    break;
                }
                if (bitSet.get(i3)) {
                    j |= 1 << i2;
                }
            }
            arrayList.add(Long.valueOf(j));
        }
        return arrayList;
    }

    static boolean zzw(List<Long> list, int i) {
        if (i < list.size() * 64) {
            return ((1 << (i % 64)) & list.get(i / 64).longValue()) != 0;
        }
        return false;
    }

    static boolean zzy(String str) {
        return str != null && str.matches("([+-])?([0-9]+\\.?[0-9]*|[0-9]*\\.?[0-9]+)") && str.length() <= 310;
    }

    @Override // com.google.android.gms.measurement.internal.zzki
    protected final boolean zzb() {
        return false;
    }

    final long zzd(byte[] bArr) {
        Preconditions.checkNotNull(bArr);
        this.zzs.zzv().zzg();
        MessageDigest messageDigestZzE = zzkz.zzE(SecurityProvider.ALG_DIGEST_MD5);
        if (messageDigestZzE != null) {
            return zzkz.zzp(messageDigestZzE.digest(bArr));
        }
        this.zzs.zzay().zzd().zza("Failed to get MD5");
        return 0L;
    }

    final Bundle zzf(Map<String, Object> map, boolean z) {
        Bundle bundle = new Bundle();
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            if (obj == null) {
                bundle.putString(str, null);
            } else if (obj instanceof Long) {
                bundle.putLong(str, ((Long) obj).longValue());
            } else if (obj instanceof Double) {
                bundle.putDouble(str, ((Double) obj).doubleValue());
            } else if (!(obj instanceof ArrayList)) {
                bundle.putString(str, obj.toString());
            } else if (z) {
                ArrayList arrayList = (ArrayList) obj;
                ArrayList<? extends Parcelable> arrayList2 = new ArrayList<>();
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    arrayList2.add(zzf((Map) arrayList.get(i), false));
                }
                bundle.putParcelableArrayList(str, arrayList2);
            }
        }
        return bundle;
    }

    final <T extends Parcelable> T zzh(byte[] bArr, Parcelable.Creator<T> creator) {
        if (bArr == null) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.unmarshall(bArr, 0, bArr.length);
            parcelObtain.setDataPosition(0);
            return creator.createFromParcel(parcelObtain);
        } catch (SafeParcelReader.ParseException unused) {
            this.zzs.zzay().zzd().zza("Failed to load parcelable from buffer");
            return null;
        } finally {
            parcelObtain.recycle();
        }
    }

    final zzat zzi(com.google.android.gms.internal.measurement.zzaa zzaaVar) {
        Object obj;
        Bundle bundleZzf = zzf(zzaaVar.zze(), true);
        String string = (!bundleZzf.containsKey("_o") || (obj = bundleZzf.get("_o")) == null) ? "app" : obj.toString();
        String strZzb = zzgs.zzb(zzaaVar.zzd());
        if (strZzb == null) {
            strZzb = zzaaVar.zzd();
        }
        return new zzat(strZzb, new zzar(bundleZzf), string, zzaaVar.zza());
    }

    final com.google.android.gms.internal.measurement.zzfo zzj(zzao zzaoVar) {
        com.google.android.gms.internal.measurement.zzfn zzfnVarZze = com.google.android.gms.internal.measurement.zzfo.zze();
        zzfnVarZze.zzl(zzaoVar.zze);
        zzaq zzaqVar = new zzaq(zzaoVar.zzf);
        while (zzaqVar.hasNext()) {
            String next = zzaqVar.next();
            com.google.android.gms.internal.measurement.zzfr zzfrVarZze = com.google.android.gms.internal.measurement.zzfs.zze();
            zzfrVarZze.zzj(next);
            Object objZzf = zzaoVar.zzf.zzf(next);
            Preconditions.checkNotNull(objZzf);
            zzu(zzfrVarZze, objZzf);
            zzfnVarZze.zze(zzfrVarZze);
        }
        return zzfnVarZze.zzaA();
    }

    final String zzm(com.google.android.gms.internal.measurement.zzfw zzfwVar) {
        if (zzfwVar == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nbatch {\n");
        for (com.google.android.gms.internal.measurement.zzfy zzfyVar : zzfwVar.zzd()) {
            if (zzfyVar != null) {
                zzG(sb, 1);
                sb.append("bundle {\n");
                if (zzfyVar.zzbh()) {
                    zzJ(sb, 1, "protocol_version", Integer.valueOf(zzfyVar.zzd()));
                }
                zzJ(sb, 1, "platform", zzfyVar.zzK());
                if (zzfyVar.zzbd()) {
                    zzJ(sb, 1, "gmp_version", Long.valueOf(zzfyVar.zzn()));
                }
                if (zzfyVar.zzbn()) {
                    zzJ(sb, 1, "uploading_gmp_version", Long.valueOf(zzfyVar.zzs()));
                }
                if (zzfyVar.zzbb()) {
                    zzJ(sb, 1, "dynamite_version", Long.valueOf(zzfyVar.zzk()));
                }
                if (zzfyVar.zzaY()) {
                    zzJ(sb, 1, "config_version", Long.valueOf(zzfyVar.zzi()));
                }
                zzJ(sb, 1, "gmp_app_id", zzfyVar.zzH());
                zzJ(sb, 1, "admob_app_id", zzfyVar.zzx());
                zzJ(sb, 1, "app_id", zzfyVar.zzy());
                zzJ(sb, 1, "app_version", zzfyVar.zzB());
                if (zzfyVar.zzaW()) {
                    zzJ(sb, 1, "app_version_major", Integer.valueOf(zzfyVar.zza()));
                }
                zzJ(sb, 1, "firebase_instance_id", zzfyVar.zzF());
                if (zzfyVar.zzba()) {
                    zzJ(sb, 1, "dev_cert_hash", Long.valueOf(zzfyVar.zzj()));
                }
                zzJ(sb, 1, "app_store", zzfyVar.zzA());
                if (zzfyVar.zzbm()) {
                    zzJ(sb, 1, "upload_timestamp_millis", Long.valueOf(zzfyVar.zzr()));
                }
                if (zzfyVar.zzbk()) {
                    zzJ(sb, 1, "start_timestamp_millis", Long.valueOf(zzfyVar.zzq()));
                }
                if (zzfyVar.zzbc()) {
                    zzJ(sb, 1, "end_timestamp_millis", Long.valueOf(zzfyVar.zzm()));
                }
                if (zzfyVar.zzbg()) {
                    zzJ(sb, 1, "previous_bundle_start_timestamp_millis", Long.valueOf(zzfyVar.zzp()));
                }
                if (zzfyVar.zzbf()) {
                    zzJ(sb, 1, "previous_bundle_end_timestamp_millis", Long.valueOf(zzfyVar.zzo()));
                }
                zzJ(sb, 1, "app_instance_id", zzfyVar.zzz());
                zzJ(sb, 1, "resettable_device_id", zzfyVar.zzL());
                zzJ(sb, 1, "ds_id", zzfyVar.zzE());
                if (zzfyVar.zzbe()) {
                    zzJ(sb, 1, "limited_ad_tracking", Boolean.valueOf(zzfyVar.zzaT()));
                }
                zzJ(sb, 1, "os_version", zzfyVar.zzJ());
                zzJ(sb, 1, "device_model", zzfyVar.zzD());
                zzJ(sb, 1, "user_default_language", zzfyVar.zzM());
                if (zzfyVar.zzbl()) {
                    zzJ(sb, 1, "time_zone_offset_minutes", Integer.valueOf(zzfyVar.zzf()));
                }
                if (zzfyVar.zzaX()) {
                    zzJ(sb, 1, "bundle_sequential_index", Integer.valueOf(zzfyVar.zzb()));
                }
                if (zzfyVar.zzbj()) {
                    zzJ(sb, 1, "service_upload", Boolean.valueOf(zzfyVar.zzaU()));
                }
                zzJ(sb, 1, "health_monitor", zzfyVar.zzI());
                if (!this.zzs.zzf().zzs(null, zzdy.zzan) && zzfyVar.zzaV() && zzfyVar.zzh() != 0) {
                    zzJ(sb, 1, "android_id", Long.valueOf(zzfyVar.zzh()));
                }
                if (zzfyVar.zzbi()) {
                    zzJ(sb, 1, "retry_counter", Integer.valueOf(zzfyVar.zze()));
                }
                if (zzfyVar.zzaZ()) {
                    zzJ(sb, 1, "consent_signals", zzfyVar.zzC());
                }
                List<com.google.android.gms.internal.measurement.zzgh> listZzP = zzfyVar.zzP();
                if (listZzP != null) {
                    for (com.google.android.gms.internal.measurement.zzgh zzghVar : listZzP) {
                        if (zzghVar != null) {
                            zzG(sb, 2);
                            sb.append("user_property {\n");
                            zzJ(sb, 2, "set_timestamp_millis", zzghVar.zzs() ? Long.valueOf(zzghVar.zzc()) : null);
                            zzJ(sb, 2, AppMeasurementSdk.ConditionalUserProperty.NAME, this.zzs.zzj().zzf(zzghVar.zzf()));
                            zzJ(sb, 2, "string_value", zzghVar.zzg());
                            zzJ(sb, 2, "int_value", zzghVar.zzr() ? Long.valueOf(zzghVar.zzb()) : null);
                            zzJ(sb, 2, "double_value", zzghVar.zzq() ? Double.valueOf(zzghVar.zza()) : null);
                            zzG(sb, 2);
                            sb.append("}\n");
                        }
                    }
                }
                List<com.google.android.gms.internal.measurement.zzfk> listZzN = zzfyVar.zzN();
                if (listZzN != null) {
                    for (com.google.android.gms.internal.measurement.zzfk zzfkVar : listZzN) {
                        if (zzfkVar != null) {
                            zzG(sb, 2);
                            sb.append("audience_membership {\n");
                            if (zzfkVar.zzk()) {
                                zzJ(sb, 2, "audience_id", Integer.valueOf(zzfkVar.zza()));
                            }
                            if (zzfkVar.zzm()) {
                                zzJ(sb, 2, "new_audience", Boolean.valueOf(zzfkVar.zzj()));
                            }
                            zzI(sb, 2, "current_data", zzfkVar.zzd());
                            if (zzfkVar.zzn()) {
                                zzI(sb, 2, "previous_data", zzfkVar.zze());
                            }
                            zzG(sb, 2);
                            sb.append("}\n");
                        }
                    }
                }
                List<com.google.android.gms.internal.measurement.zzfo> listZzO = zzfyVar.zzO();
                if (listZzO != null) {
                    for (com.google.android.gms.internal.measurement.zzfo zzfoVar : listZzO) {
                        if (zzfoVar != null) {
                            zzG(sb, 2);
                            sb.append("event {\n");
                            zzJ(sb, 2, AppMeasurementSdk.ConditionalUserProperty.NAME, this.zzs.zzj().zzd(zzfoVar.zzh()));
                            if (zzfoVar.zzu()) {
                                zzJ(sb, 2, "timestamp_millis", Long.valueOf(zzfoVar.zzd()));
                            }
                            if (zzfoVar.zzt()) {
                                zzJ(sb, 2, "previous_timestamp_millis", Long.valueOf(zzfoVar.zzc()));
                            }
                            if (zzfoVar.zzs()) {
                                zzJ(sb, 2, "count", Integer.valueOf(zzfoVar.zza()));
                            }
                            if (zzfoVar.zzb() != 0) {
                                zzE(sb, 2, zzfoVar.zzi());
                            }
                            zzG(sb, 2);
                            sb.append("}\n");
                        }
                    }
                }
                zzG(sb, 1);
                sb.append("}\n");
            }
        }
        sb.append("}\n");
        return sb.toString();
    }

    final String zzo(com.google.android.gms.internal.measurement.zzej zzejVar) {
        if (zzejVar == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nevent_filter {\n");
        if (zzejVar.zzp()) {
            zzJ(sb, 0, "filter_id", Integer.valueOf(zzejVar.zzb()));
        }
        zzJ(sb, 0, "event_name", this.zzs.zzj().zzd(zzejVar.zzg()));
        String strZzH = zzH(zzejVar.zzk(), zzejVar.zzm(), zzejVar.zzn());
        if (!strZzH.isEmpty()) {
            zzJ(sb, 0, "filter_type", strZzH);
        }
        if (zzejVar.zzo()) {
            zzK(sb, 1, "event_count_filter", zzejVar.zzf());
        }
        if (zzejVar.zza() > 0) {
            sb.append("  filters {\n");
            Iterator<com.google.android.gms.internal.measurement.zzel> it = zzejVar.zzh().iterator();
            while (it.hasNext()) {
                zzF(sb, 2, it.next());
            }
        }
        zzG(sb, 1);
        sb.append("}\n}\n");
        return sb.toString();
    }

    final String zzp(com.google.android.gms.internal.measurement.zzes zzesVar) {
        if (zzesVar == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\nproperty_filter {\n");
        if (zzesVar.zzj()) {
            zzJ(sb, 0, "filter_id", Integer.valueOf(zzesVar.zza()));
        }
        zzJ(sb, 0, "property_name", this.zzs.zzj().zzf(zzesVar.zze()));
        String strZzH = zzH(zzesVar.zzg(), zzesVar.zzh(), zzesVar.zzi());
        if (!strZzH.isEmpty()) {
            zzJ(sb, 0, "filter_type", strZzH);
        }
        zzF(sb, 1, zzesVar.zzb());
        sb.append("}\n");
        return sb.toString();
    }

    final List<Long> zzr(List<Long> list, List<Integer> list2) {
        int i;
        ArrayList arrayList = new ArrayList(list);
        for (Integer num : list2) {
            if (num.intValue() < 0) {
                this.zzs.zzay().zzk().zzb("Ignoring negative bit index to be cleared", num);
            } else {
                int iIntValue = num.intValue() / 64;
                if (iIntValue >= arrayList.size()) {
                    this.zzs.zzay().zzk().zzc("Ignoring bit index greater than bitSet size", num, Integer.valueOf(arrayList.size()));
                } else {
                    arrayList.set(iIntValue, Long.valueOf(((Long) arrayList.get(iIntValue)).longValue() & (~(1 << (num.intValue() % 64)))));
                }
            }
        }
        int size = arrayList.size();
        int size2 = arrayList.size() - 1;
        while (true) {
            int i2 = size2;
            i = size;
            size = i2;
            if (size < 0 || ((Long) arrayList.get(size)).longValue() != 0) {
                break;
            }
            size2 = size - 1;
        }
        return arrayList.subList(0, i);
    }

    final Map<String, Object> zzt(Bundle bundle, boolean z) {
        HashMap map = new HashMap();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if ((obj instanceof Bundle[]) || (obj instanceof ArrayList) || (obj instanceof Bundle)) {
                if (z) {
                    ArrayList arrayList = new ArrayList();
                    if (obj instanceof Parcelable[]) {
                        for (Parcelable parcelable : (Parcelable[]) obj) {
                            if (parcelable instanceof Bundle) {
                                arrayList.add(zzt((Bundle) parcelable, false));
                            }
                        }
                    } else if (obj instanceof ArrayList) {
                        ArrayList arrayList2 = (ArrayList) obj;
                        int size = arrayList2.size();
                        for (int i = 0; i < size; i++) {
                            Object obj2 = arrayList2.get(i);
                            if (obj2 instanceof Bundle) {
                                arrayList.add(zzt((Bundle) obj2, false));
                            }
                        }
                    } else if (obj instanceof Bundle) {
                        arrayList.add(zzt((Bundle) obj, false));
                    }
                    map.put(str, arrayList);
                }
            } else if (obj != null) {
                map.put(str, obj);
            }
        }
        return map;
    }

    final void zzu(com.google.android.gms.internal.measurement.zzfr zzfrVar, Object obj) {
        Preconditions.checkNotNull(obj);
        zzfrVar.zzg();
        zzfrVar.zze();
        zzfrVar.zzd();
        zzfrVar.zzf();
        if (obj instanceof String) {
            zzfrVar.zzk((String) obj);
            return;
        }
        if (obj instanceof Long) {
            zzfrVar.zzi(((Long) obj).longValue());
            return;
        }
        if (obj instanceof Double) {
            zzfrVar.zzh(((Double) obj).doubleValue());
        } else if (obj instanceof Bundle[]) {
            zzfrVar.zzb(zzq((Bundle[]) obj));
        } else {
            this.zzs.zzay().zzd().zzb("Ignoring invalid (type) event param value", obj);
        }
    }

    final void zzv(com.google.android.gms.internal.measurement.zzgg zzggVar, Object obj) {
        Preconditions.checkNotNull(obj);
        zzggVar.zzc();
        zzggVar.zzb();
        zzggVar.zza();
        if (obj instanceof String) {
            zzggVar.zzh((String) obj);
            return;
        }
        if (obj instanceof Long) {
            zzggVar.zze(((Long) obj).longValue());
        } else if (obj instanceof Double) {
            zzggVar.zzd(((Double) obj).doubleValue());
        } else {
            this.zzs.zzay().zzd().zzb("Ignoring invalid (type) user attribute value", obj);
        }
    }

    final boolean zzx(long j, long j2) {
        return j == 0 || j2 <= 0 || Math.abs(this.zzs.zzav().currentTimeMillis() - j) > j2;
    }

    final byte[] zzz(byte[] bArr) throws IOException {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            this.zzs.zzay().zzd().zzb("Failed to gzip content", e);
            throw e;
        }
    }
}
