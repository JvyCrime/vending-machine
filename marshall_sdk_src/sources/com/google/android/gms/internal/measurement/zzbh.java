package com.google.android.gms.internal.measurement;

import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zzbh extends zzaw {
    protected zzbh() {
        this.zza.add(zzbl.FOR_IN);
        this.zza.add(zzbl.FOR_IN_CONST);
        this.zza.add(zzbl.FOR_IN_LET);
        this.zza.add(zzbl.FOR_LET);
        this.zza.add(zzbl.FOR_OF);
        this.zza.add(zzbl.FOR_OF_CONST);
        this.zza.add(zzbl.FOR_OF_LET);
        this.zza.add(zzbl.WHILE);
    }

    private static zzap zzc(zzbf zzbfVar, Iterator<zzap> it, zzap zzapVar) {
        if (it != null) {
            while (it.hasNext()) {
                zzap zzapVarZzc = zzbfVar.zza(it.next()).zzc((zzae) zzapVar);
                if (zzapVarZzc instanceof zzag) {
                    zzag zzagVar = (zzag) zzapVarZzc;
                    if ("break".equals(zzagVar.zzc())) {
                        return zzap.zzf;
                    }
                    if ("return".equals(zzagVar.zzc())) {
                        return zzagVar;
                    }
                }
            }
        }
        return zzap.zzf;
    }

    private static zzap zzd(zzbf zzbfVar, zzap zzapVar, zzap zzapVar2) {
        return zzc(zzbfVar, zzapVar.zzl(), zzapVar2);
    }

    private static zzap zze(zzbf zzbfVar, zzap zzapVar, zzap zzapVar2) {
        if (zzapVar instanceof Iterable) {
            return zzc(zzbfVar, ((Iterable) zzapVar).iterator(), zzapVar2);
        }
        throw new IllegalArgumentException("Non-iterable type in for...of loop.");
    }

    @Override // com.google.android.gms.internal.measurement.zzaw
    public final zzap zza(String str, zzg zzgVar, List<zzap> list) {
        zzbl zzblVar = zzbl.ADD;
        int iOrdinal = zzh.zze(str).ordinal();
        if (iOrdinal == 65) {
            zzh.zzh(zzbl.WHILE.name(), 4, list);
            zzap zzapVar = list.get(0);
            zzap zzapVar2 = list.get(1);
            zzap zzapVar3 = list.get(2);
            zzap zzapVarZzb = zzgVar.zzb(list.get(3));
            if (zzgVar.zzb(zzapVar3).zzg().booleanValue()) {
                zzap zzapVarZzc = zzgVar.zzc((zzae) zzapVarZzb);
                if (zzapVarZzc instanceof zzag) {
                    zzag zzagVar = (zzag) zzapVarZzc;
                    if ("break".equals(zzagVar.zzc())) {
                        return zzap.zzf;
                    }
                    if ("return".equals(zzagVar.zzc())) {
                        return zzagVar;
                    }
                }
            }
            while (zzgVar.zzb(zzapVar).zzg().booleanValue()) {
                zzap zzapVarZzc2 = zzgVar.zzc((zzae) zzapVarZzb);
                if (zzapVarZzc2 instanceof zzag) {
                    zzag zzagVar2 = (zzag) zzapVarZzc2;
                    if ("break".equals(zzagVar2.zzc())) {
                        return zzap.zzf;
                    }
                    if ("return".equals(zzagVar2.zzc())) {
                        return zzagVar2;
                    }
                }
                zzgVar.zzb(zzapVar2);
            }
            return zzap.zzf;
        }
        switch (iOrdinal) {
            case 26:
                zzh.zzh(zzbl.FOR_IN.name(), 3, list);
                if (!(list.get(0) instanceof zzat)) {
                    throw new IllegalArgumentException("Variable name in FOR_IN must be a string");
                }
                String strZzi = list.get(0).zzi();
                return zzd(new zzbg(zzgVar, strZzi), zzgVar.zzb(list.get(1)), zzgVar.zzb(list.get(2)));
            case 27:
                zzh.zzh(zzbl.FOR_IN_CONST.name(), 3, list);
                if (!(list.get(0) instanceof zzat)) {
                    throw new IllegalArgumentException("Variable name in FOR_IN_CONST must be a string");
                }
                String strZzi2 = list.get(0).zzi();
                return zzd(new zzbd(zzgVar, strZzi2), zzgVar.zzb(list.get(1)), zzgVar.zzb(list.get(2)));
            case 28:
                zzh.zzh(zzbl.FOR_IN_LET.name(), 3, list);
                if (!(list.get(0) instanceof zzat)) {
                    throw new IllegalArgumentException("Variable name in FOR_IN_LET must be a string");
                }
                String strZzi3 = list.get(0).zzi();
                return zzd(new zzbe(zzgVar, strZzi3), zzgVar.zzb(list.get(1)), zzgVar.zzb(list.get(2)));
            case 29:
                zzh.zzh(zzbl.FOR_LET.name(), 4, list);
                zzap zzapVarZzb2 = zzgVar.zzb(list.get(0));
                if (!(zzapVarZzb2 instanceof zzae)) {
                    throw new IllegalArgumentException("Initializer variables in FOR_LET must be an ArrayList");
                }
                zzae zzaeVar = (zzae) zzapVarZzb2;
                zzap zzapVar4 = list.get(1);
                zzap zzapVar5 = list.get(2);
                zzap zzapVarZzb3 = zzgVar.zzb(list.get(3));
                zzg zzgVarZza = zzgVar.zza();
                for (int i = 0; i < zzaeVar.zzc(); i++) {
                    String strZzi4 = zzaeVar.zze(i).zzi();
                    zzgVarZza.zzg(strZzi4, zzgVar.zzd(strZzi4));
                }
                while (zzgVar.zzb(zzapVar4).zzg().booleanValue()) {
                    zzap zzapVarZzc3 = zzgVar.zzc((zzae) zzapVarZzb3);
                    if (zzapVarZzc3 instanceof zzag) {
                        zzag zzagVar3 = (zzag) zzapVarZzc3;
                        if ("break".equals(zzagVar3.zzc())) {
                            return zzap.zzf;
                        }
                        if ("return".equals(zzagVar3.zzc())) {
                            return zzagVar3;
                        }
                    }
                    zzg zzgVarZza2 = zzgVar.zza();
                    for (int i2 = 0; i2 < zzaeVar.zzc(); i2++) {
                        String strZzi5 = zzaeVar.zze(i2).zzi();
                        zzgVarZza2.zzg(strZzi5, zzgVarZza.zzd(strZzi5));
                    }
                    zzgVarZza2.zzb(zzapVar5);
                    zzgVarZza = zzgVarZza2;
                }
                return zzap.zzf;
            case 30:
                zzh.zzh(zzbl.FOR_OF.name(), 3, list);
                if (!(list.get(0) instanceof zzat)) {
                    throw new IllegalArgumentException("Variable name in FOR_OF must be a string");
                }
                String strZzi6 = list.get(0).zzi();
                return zze(new zzbg(zzgVar, strZzi6), zzgVar.zzb(list.get(1)), zzgVar.zzb(list.get(2)));
            case 31:
                zzh.zzh(zzbl.FOR_OF_CONST.name(), 3, list);
                if (!(list.get(0) instanceof zzat)) {
                    throw new IllegalArgumentException("Variable name in FOR_OF_CONST must be a string");
                }
                String strZzi7 = list.get(0).zzi();
                return zze(new zzbd(zzgVar, strZzi7), zzgVar.zzb(list.get(1)), zzgVar.zzb(list.get(2)));
            case 32:
                zzh.zzh(zzbl.FOR_OF_LET.name(), 3, list);
                if (!(list.get(0) instanceof zzat)) {
                    throw new IllegalArgumentException("Variable name in FOR_OF_LET must be a string");
                }
                String strZzi8 = list.get(0).zzi();
                return zze(new zzbe(zzgVar, strZzi8), zzgVar.zzb(list.get(1)), zzgVar.zzb(list.get(2)));
            default:
                return super.zzb(str);
        }
    }
}
