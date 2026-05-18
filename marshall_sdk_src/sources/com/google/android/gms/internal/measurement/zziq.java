package com.google.android.gms.internal.measurement;

import java.util.Comparator;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zziq implements Comparator<zziy> {
    zziq() {
    }

    @Override // java.util.Comparator
    public final /* bridge */ /* synthetic */ int compare(zziy zziyVar, zziy zziyVar2) {
        zziy zziyVar3 = zziyVar;
        zziy zziyVar4 = zziyVar2;
        zzio zzioVar = new zzio(zziyVar3);
        zzio zzioVar2 = new zzio(zziyVar4);
        while (zzioVar.hasNext() && zzioVar2.hasNext()) {
            int iZza = zzip.zza(zzioVar.zza() & 255, zzioVar2.zza() & 255);
            if (iZza != 0) {
                return iZza;
            }
        }
        return zzip.zza(zziyVar3.zzd(), zziyVar4.zzd());
    }
}
