package com.google.android.gms.measurement.internal;

import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzz extends zzki {
    private String zza;
    private Set<Integer> zzb;
    private Map<Integer, zzt> zzc;
    private Long zzd;
    private Long zze;

    zzz(zzks zzksVar) {
        super(zzksVar);
    }

    private final zzt zzd(Integer num) {
        if (this.zzc.containsKey(num)) {
            return this.zzc.get(num);
        }
        zzt zztVar = new zzt(this, this.zza, null);
        this.zzc.put(num, zztVar);
        return zztVar;
    }

    private final boolean zzf(int i, int i2) {
        zzt zztVar = this.zzc.get(Integer.valueOf(i));
        if (zztVar == null) {
            return false;
        }
        return zztVar.zze.get(i2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:400:0x0a1e, code lost:
    
        r0 = r65.zzs.zzay().zzk();
        r6 = com.google.android.gms.measurement.internal.zzel.zzn(r65.zza);
     */
    /* JADX WARN: Code restructure failed: missing block: B:401:0x0a32, code lost:
    
        if (r7.zzj() == false) goto L403;
     */
    /* JADX WARN: Code restructure failed: missing block: B:402:0x0a34, code lost:
    
        r7 = java.lang.Integer.valueOf(r7.zza());
     */
    /* JADX WARN: Code restructure failed: missing block: B:403:0x0a3d, code lost:
    
        r7 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:404:0x0a3e, code lost:
    
        r0.zzc("Invalid property filter ID. appId, id", r6, java.lang.String.valueOf(r7));
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x02e1 A[PHI: r0 r5
  0x02e1: PHI (r0v64 java.util.Map) = (r0v46 java.util.Map), (r0v66 java.util.Map), (r0v40 java.util.Map) binds: [B:129:0x030e, B:118:0x02e9, B:115:0x02df] A[DONT_GENERATE, DONT_INLINE]
  0x02e1: PHI (r5v16 android.database.Cursor) = (r5v9 android.database.Cursor), (r5v17 android.database.Cursor), (r5v17 android.database.Cursor) binds: [B:129:0x030e, B:118:0x02e9, B:115:0x02df] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x03dc  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x03eb  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x059f  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x072a A[PHI: r0 r11 r13 r25 r28 r29
  0x072a: PHI (r0v97 java.util.Map) = (r0v99 java.util.Map), (r0v108 java.util.Map) binds: [B:294:0x0760, B:278:0x0728] A[DONT_GENERATE, DONT_INLINE]
  0x072a: PHI (r11v14 android.database.Cursor) = (r11v15 android.database.Cursor), (r11v17 android.database.Cursor) binds: [B:294:0x0760, B:278:0x0728] A[DONT_GENERATE, DONT_INLINE]
  0x072a: PHI (r13v12 java.lang.String) = (r13v13 java.lang.String), (r13v16 java.lang.String) binds: [B:294:0x0760, B:278:0x0728] A[DONT_GENERATE, DONT_INLINE]
  0x072a: PHI (r25v4 java.lang.String) = (r25v5 java.lang.String), (r25v7 java.lang.String) binds: [B:294:0x0760, B:278:0x0728] A[DONT_GENERATE, DONT_INLINE]
  0x072a: PHI (r28v7 java.lang.String) = (r28v8 java.lang.String), (r28v13 java.lang.String) binds: [B:294:0x0760, B:278:0x0728] A[DONT_GENERATE, DONT_INLINE]
  0x072a: PHI (r29v7 java.lang.String) = (r29v8 java.lang.String), (r29v14 java.lang.String) binds: [B:294:0x0760, B:278:0x0728] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:306:0x0785  */
    /* JADX WARN: Removed duplicated region for block: B:325:0x082d  */
    /* JADX WARN: Removed duplicated region for block: B:355:0x08f8 A[PHI: r0 r13 r67
  0x08f8: PHI (r0v150 java.util.Map) = (r0v152 java.util.Map), (r0v158 java.util.Map) binds: [B:365:0x091e, B:354:0x08f6] A[DONT_GENERATE, DONT_INLINE]
  0x08f8: PHI (r13v31 android.database.Cursor) = (r13v32 android.database.Cursor), (r13v33 android.database.Cursor) binds: [B:365:0x091e, B:354:0x08f6] A[DONT_GENERATE, DONT_INLINE]
  0x08f8: PHI (r67v7 java.util.Iterator<com.google.android.gms.internal.measurement.zzgh>) = 
  (r67v8 java.util.Iterator<com.google.android.gms.internal.measurement.zzgh>)
  (r67v11 java.util.Iterator<com.google.android.gms.internal.measurement.zzgh>)
 binds: [B:365:0x091e, B:354:0x08f6] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:413:0x0a7b  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x017a  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01b4 A[Catch: SQLiteException -> 0x0228, all -> 0x0b0b, TRY_LEAVE, TryCatch #12 {SQLiteException -> 0x0228, blocks: (B:61:0x01ae, B:63:0x01b4, B:67:0x01c4, B:68:0x01c9, B:69:0x01d3, B:70:0x01e3, B:72:0x01f2), top: B:450:0x01ae }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01c4 A[Catch: SQLiteException -> 0x0228, all -> 0x0b0b, TRY_ENTER, TryCatch #12 {SQLiteException -> 0x0228, blocks: (B:61:0x01ae, B:63:0x01b4, B:67:0x01c4, B:68:0x01c9, B:69:0x01d3, B:70:0x01e3, B:72:0x01f2), top: B:450:0x01ae }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0251  */
    /* JADX WARN: Type inference failed for: r4v45, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v39 */
    /* JADX WARN: Type inference failed for: r5v41, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r5v45, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v46 */
    /* JADX WARN: Type inference failed for: r5v47, types: [java.lang.String[]] */
    /* JADX WARN: Type inference failed for: r5v48 */
    /* JADX WARN: Type inference failed for: r5v49 */
    /* JADX WARN: Type inference failed for: r5v5, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v8, types: [android.database.Cursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final java.util.List<com.google.android.gms.internal.measurement.zzfk> zza(java.lang.String r66, java.util.List<com.google.android.gms.internal.measurement.zzfo> r67, java.util.List<com.google.android.gms.internal.measurement.zzgh> r68, java.lang.Long r69, java.lang.Long r70) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 2835
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzz.zza(java.lang.String, java.util.List, java.util.List, java.lang.Long, java.lang.Long):java.util.List");
    }

    @Override // com.google.android.gms.measurement.internal.zzki
    protected final boolean zzb() {
        return false;
    }
}
