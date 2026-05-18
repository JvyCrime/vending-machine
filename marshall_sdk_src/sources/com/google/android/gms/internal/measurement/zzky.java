package com.google.android.gms.internal.measurement;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzky implements zzls {
    private static final zzle zza = new zzkw();
    private final zzle zzb;

    public zzky() {
        zzle zzleVar;
        zzle[] zzleVarArr = new zzle[2];
        zzleVarArr[0] = zzju.zza();
        try {
            zzleVar = (zzle) Class.forName("com.google.protobuf.DescriptorMessageInfoFactory").getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
        } catch (Exception unused) {
            zzleVar = zza;
        }
        zzleVarArr[1] = zzleVar;
        zzkx zzkxVar = new zzkx(zzleVarArr);
        zzkh.zzf(zzkxVar, "messageInfoFactory");
        this.zzb = zzkxVar;
    }

    private static boolean zzb(zzld zzldVar) {
        return zzldVar.zzc() == 1;
    }

    @Override // com.google.android.gms.internal.measurement.zzls
    public final <T> zzlr<T> zza(Class<T> cls) {
        zzlt.zzG(cls);
        zzld zzldVarZzb = this.zzb.zzb(cls);
        return zzldVarZzb.zzb() ? zzjz.class.isAssignableFrom(cls) ? zzlk.zzc(zzlt.zzB(), zzjo.zzb(), zzldVarZzb.zza()) : zzlk.zzc(zzlt.zzz(), zzjo.zza(), zzldVarZzb.zza()) : zzjz.class.isAssignableFrom(cls) ? zzb(zzldVarZzb) ? zzlj.zzk(cls, zzldVarZzb, zzlm.zzb(), zzku.zzd(), zzlt.zzB(), zzjo.zzb(), zzlc.zzb()) : zzlj.zzk(cls, zzldVarZzb, zzlm.zzb(), zzku.zzd(), zzlt.zzB(), null, zzlc.zzb()) : zzb(zzldVarZzb) ? zzlj.zzk(cls, zzldVarZzb, zzlm.zza(), zzku.zzc(), zzlt.zzz(), zzjo.zza(), zzlc.zza()) : zzlj.zzk(cls, zzldVarZzb, zzlm.zza(), zzku.zzc(), zzlt.zzA(), null, zzlc.zza());
    }
}
