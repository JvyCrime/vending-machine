package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzkq extends zzku {
    private static final Class<?> zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzkq() {
        super(null);
    }

    /* synthetic */ zzkq(zzkp zzkpVar) {
        super(null);
    }

    @Override // com.google.android.gms.internal.measurement.zzku
    final void zza(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zzms.zzf(obj, j);
        if (list instanceof zzko) {
            objUnmodifiableList = ((zzko) list).zze();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzln) && (list instanceof zzkg)) {
                zzkg zzkgVar = (zzkg) list;
                if (zzkgVar.zzc()) {
                    zzkgVar.zzb();
                    return;
                }
                return;
            }
            objUnmodifiableList = Collections.unmodifiableList(list);
        }
        zzms.zzs(obj, j, objUnmodifiableList);
    }

    @Override // com.google.android.gms.internal.measurement.zzku
    final <E> void zzb(Object obj, Object obj2, long j) {
        List list;
        List list2;
        List list3 = (List) zzms.zzf(obj2, j);
        int size = list3.size();
        List list4 = (List) zzms.zzf(obj, j);
        if (list4.isEmpty()) {
            List zzknVar = list4 instanceof zzko ? new zzkn(size) : ((list4 instanceof zzln) && (list4 instanceof zzkg)) ? ((zzkg) list4).zzd(size) : new ArrayList(size);
            zzms.zzs(obj, j, zzknVar);
            list2 = zzknVar;
        } else {
            if (zza.isAssignableFrom(list4.getClass())) {
                ArrayList arrayList = new ArrayList(list4.size() + size);
                arrayList.addAll(list4);
                zzms.zzs(obj, j, arrayList);
                list = arrayList;
            } else if (list4 instanceof zzmn) {
                zzkn zzknVar2 = new zzkn(list4.size() + size);
                zzknVar2.addAll(zzknVar2.size(), (zzmn) list4);
                zzms.zzs(obj, j, zzknVar2);
                list = zzknVar2;
            } else {
                boolean z = list4 instanceof zzln;
                list2 = list4;
                if (z) {
                    boolean z2 = list4 instanceof zzkg;
                    list2 = list4;
                    if (z2) {
                        zzkg zzkgVar = (zzkg) list4;
                        list2 = list4;
                        if (!zzkgVar.zzc()) {
                            zzkg<E> zzkgVarZzd = zzkgVar.zzd(list4.size() + size);
                            zzms.zzs(obj, j, zzkgVarZzd);
                            list2 = zzkgVarZzd;
                        }
                    }
                }
            }
            list2 = list;
        }
        int size2 = list2.size();
        int size3 = list3.size();
        if (size2 > 0 && size3 > 0) {
            list2.addAll(list3);
        }
        if (size2 > 0) {
            list3 = list2;
        }
        zzms.zzs(obj, j, list3);
    }
}
