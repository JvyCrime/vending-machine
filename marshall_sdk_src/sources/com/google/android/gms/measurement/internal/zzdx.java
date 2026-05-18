package com.google.android.gms.measurement.internal;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zzdx<V> {
    private static final Object zza = new Object();
    private final String zzb;
    private final zzdu<V> zzc;
    private final V zzd;
    private final V zze;
    private final Object zzf = new Object();
    private volatile V zzg = null;
    private volatile V zzh = null;

    /* JADX WARN: Multi-variable type inference failed */
    /* synthetic */ zzdx(String str, Object obj, Object obj2, zzdu zzduVar, zzdw zzdwVar) {
        this.zzb = str;
        this.zzd = obj;
        this.zze = obj2;
        this.zzc = zzduVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x005d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final V zza(V r4) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.zzf
            monitor-enter(r0)
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6e
            if (r4 == 0) goto L7
            return r4
        L7:
            com.google.android.gms.measurement.internal.zzaa r4 = com.google.android.gms.measurement.internal.zzdv.zza
            if (r4 == 0) goto L6b
            java.lang.Object r4 = com.google.android.gms.measurement.internal.zzdx.zza
            monitor-enter(r4)
            boolean r0 = com.google.android.gms.measurement.internal.zzaa.zza()     // Catch: java.lang.Throwable -> L68
            if (r0 == 0) goto L1f
            V r0 = r3.zzh     // Catch: java.lang.Throwable -> L68
            if (r0 != 0) goto L1b
            V r0 = r3.zzd     // Catch: java.lang.Throwable -> L68
            goto L1d
        L1b:
            V r0 = r3.zzh     // Catch: java.lang.Throwable -> L68
        L1d:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L68
            return r0
        L1f:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L68
            java.util.List r4 = com.google.android.gms.measurement.internal.zzdy.zzb()     // Catch: java.lang.SecurityException -> L55
            java.util.Iterator r4 = r4.iterator()     // Catch: java.lang.SecurityException -> L55
        L28:
            boolean r0 = r4.hasNext()     // Catch: java.lang.SecurityException -> L55
            if (r0 == 0) goto L56
            java.lang.Object r0 = r4.next()     // Catch: java.lang.SecurityException -> L55
            com.google.android.gms.measurement.internal.zzdx r0 = (com.google.android.gms.measurement.internal.zzdx) r0     // Catch: java.lang.SecurityException -> L55
            boolean r1 = com.google.android.gms.measurement.internal.zzaa.zza()     // Catch: java.lang.SecurityException -> L55
            if (r1 != 0) goto L4d
            r1 = 0
            com.google.android.gms.measurement.internal.zzdu<V> r2 = r0.zzc     // Catch: java.lang.IllegalStateException -> L43 java.lang.SecurityException -> L55
            if (r2 == 0) goto L43
            java.lang.Object r1 = r2.zza()     // Catch: java.lang.IllegalStateException -> L43 java.lang.SecurityException -> L55
        L43:
            java.lang.Object r2 = com.google.android.gms.measurement.internal.zzdx.zza     // Catch: java.lang.SecurityException -> L55
            monitor-enter(r2)     // Catch: java.lang.SecurityException -> L55
            r0.zzh = r1     // Catch: java.lang.Throwable -> L4a
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L4a
            goto L28
        L4a:
            r4 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L4a
            throw r4     // Catch: java.lang.SecurityException -> L55
        L4d:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException     // Catch: java.lang.SecurityException -> L55
            java.lang.String r0 = "Refreshing flag cache must be done on a worker thread."
            r4.<init>(r0)     // Catch: java.lang.SecurityException -> L55
            throw r4     // Catch: java.lang.SecurityException -> L55
        L55:
        L56:
            com.google.android.gms.measurement.internal.zzdu<V> r4 = r3.zzc
            if (r4 != 0) goto L5d
            V r4 = r3.zzd
            return r4
        L5d:
            java.lang.Object r4 = r4.zza()     // Catch: java.lang.IllegalStateException -> L62 java.lang.SecurityException -> L65
            return r4
        L62:
            V r4 = r3.zzd
            return r4
        L65:
            V r4 = r3.zzd
            return r4
        L68:
            r0 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L68
            throw r0
        L6b:
            V r4 = r3.zzd
            return r4
        L6e:
            r4 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L6e
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzdx.zza(java.lang.Object):java.lang.Object");
    }

    public final String zzb() {
        return this.zzb;
    }
}
