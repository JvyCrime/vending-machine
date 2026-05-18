package com.google.android.gms.internal.measurement;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzks extends zzku {
    private zzks() {
        super(null);
    }

    /* synthetic */ zzks(zzkr zzkrVar) {
        super(null);
    }

    @Override // com.google.android.gms.internal.measurement.zzku
    final void zza(Object obj, long j) {
        ((zzkg) zzms.zzf(obj, j)).zzb();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.google.android.gms.internal.measurement.zzkg] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r6v2, types: [com.google.android.gms.internal.measurement.zzkg, java.util.Collection] */
    /* JADX WARN: Type inference failed for: r6v3, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v4 */
    @Override // com.google.android.gms.internal.measurement.zzku
    final <E> void zzb(Object obj, Object obj2, long j) {
        zzkg zzkgVar = (zzkg) zzms.zzf(obj, j);
        ?? r6 = (zzkg) zzms.zzf(obj2, j);
        int size = zzkgVar.size();
        int size2 = r6.size();
        ?? r0 = zzkgVar;
        r0 = zzkgVar;
        if (size > 0 && size2 > 0) {
            boolean zZzc = zzkgVar.zzc();
            ?? Zzd = zzkgVar;
            if (!zZzc) {
                Zzd = zzkgVar.zzd(size2 + size);
            }
            Zzd.addAll(r6);
            r0 = Zzd;
        }
        if (size > 0) {
            r6 = r0;
        }
        zzms.zzs(obj, j, r6);
    }
}
