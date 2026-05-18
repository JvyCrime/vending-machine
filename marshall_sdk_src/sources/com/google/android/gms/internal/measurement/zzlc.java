package com.google.android.gms.internal.measurement;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzlc {
    private static final zzlb zza;
    private static final zzlb zzb;

    static {
        zzlb zzlbVar;
        try {
            zzlbVar = (zzlb) Class.forName("com.google.protobuf.MapFieldSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzlbVar = null;
        }
        zza = zzlbVar;
        zzb = new zzlb();
    }

    static zzlb zza() {
        return zza;
    }

    static zzlb zzb() {
        return zzb;
    }
}
