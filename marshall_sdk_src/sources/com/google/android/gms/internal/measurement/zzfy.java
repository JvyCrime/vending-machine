package com.google.android.gms.internal.measurement;

import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zzfy extends zzjz<zzfy, zzfx> implements zzlh {
    public static final /* synthetic */ int zza = 0;
    private static final zzfy zze;
    private boolean zzA;
    private long zzC;
    private int zzD;
    private boolean zzG;
    private int zzJ;
    private int zzK;
    private int zzL;
    private long zzN;
    private long zzO;
    private int zzR;
    private zzgb zzT;
    private long zzV;
    private long zzW;
    private int zzZ;
    private boolean zzaa;
    private boolean zzac;
    private zzfu zzad;
    private int zzf;
    private int zzg;
    private int zzh;
    private long zzk;
    private long zzl;
    private long zzm;
    private long zzn;
    private long zzo;
    private int zzt;
    private long zzx;
    private long zzy;
    private zzkg<zzfo> zzi = zzbA();
    private zzkg<zzgh> zzj = zzbA();
    private String zzp = "";
    private String zzq = "";
    private String zzr = "";
    private String zzs = "";
    private String zzu = "";
    private String zzv = "";
    private String zzw = "";
    private String zzz = "";
    private String zzB = "";
    private String zzE = "";
    private String zzF = "";
    private zzkg<zzfk> zzH = zzbA();
    private String zzI = "";
    private String zzM = "";
    private String zzP = "";
    private String zzQ = "";
    private String zzS = "";
    private zzke zzU = zzbx();
    private String zzX = "";
    private String zzY = "";
    private String zzab = "";

    static {
        zzfy zzfyVar = new zzfy();
        zze = zzfyVar;
        zzjz.zzbG(zzfy.class, zzfyVar);
    }

    private zzfy() {
    }

    static /* synthetic */ void zzQ(zzfy zzfyVar, long j) {
        zzfyVar.zzf |= 1073741824;
        zzfyVar.zzO = j;
    }

    static /* synthetic */ void zzR(zzfy zzfyVar) {
        zzfyVar.zzf &= Integer.MAX_VALUE;
        zzfyVar.zzP = zze.zzP;
    }

    static /* synthetic */ void zzS(zzfy zzfyVar, int i) {
        zzfyVar.zzg |= 2;
        zzfyVar.zzR = i;
    }

    static /* synthetic */ void zzT(zzfy zzfyVar, int i, zzfo zzfoVar) {
        zzfoVar.getClass();
        zzfyVar.zzbI();
        zzfyVar.zzi.set(i, zzfoVar);
    }

    static /* synthetic */ void zzU(zzfy zzfyVar, String str) {
        str.getClass();
        zzfyVar.zzg |= 4;
        zzfyVar.zzS = str;
    }

    static /* synthetic */ void zzV(zzfy zzfyVar, zzgb zzgbVar) {
        zzgbVar.getClass();
        zzfyVar.zzT = zzgbVar;
        zzfyVar.zzg |= 8;
    }

    static /* synthetic */ void zzW(zzfy zzfyVar, Iterable iterable) {
        zzke zzkeVar = zzfyVar.zzU;
        if (!zzkeVar.zzc()) {
            int size = zzkeVar.size();
            zzfyVar.zzU = zzkeVar.zzd(size == 0 ? 10 : size + size);
        }
        zzih.zzbq(iterable, zzfyVar.zzU);
    }

    static /* synthetic */ void zzX(zzfy zzfyVar, zzfo zzfoVar) {
        zzfoVar.getClass();
        zzfyVar.zzbI();
        zzfyVar.zzi.add(zzfoVar);
    }

    static /* synthetic */ void zzY(zzfy zzfyVar, long j) {
        zzfyVar.zzg |= 16;
        zzfyVar.zzV = j;
    }

    static /* synthetic */ void zzZ(zzfy zzfyVar, long j) {
        zzfyVar.zzg |= 32;
        zzfyVar.zzW = j;
    }

    static /* synthetic */ void zzaA(zzfy zzfyVar, String str) {
        str.getClass();
        zzfyVar.zzf |= 65536;
        zzfyVar.zzz = str;
    }

    static /* synthetic */ void zzaB(zzfy zzfyVar) {
        zzfyVar.zzf &= -65537;
        zzfyVar.zzz = zze.zzz;
    }

    static /* synthetic */ void zzaC(zzfy zzfyVar, boolean z) {
        zzfyVar.zzf |= 131072;
        zzfyVar.zzA = z;
    }

    static /* synthetic */ void zzaD(zzfy zzfyVar) {
        zzfyVar.zzf &= -131073;
        zzfyVar.zzA = false;
    }

    static /* synthetic */ void zzaE(zzfy zzfyVar, String str) {
        str.getClass();
        zzfyVar.zzf |= 262144;
        zzfyVar.zzB = str;
    }

    static /* synthetic */ void zzaF(zzfy zzfyVar) {
        zzfyVar.zzf &= -262145;
        zzfyVar.zzB = zze.zzB;
    }

    static /* synthetic */ void zzaG(zzfy zzfyVar, long j) {
        zzfyVar.zzf |= 524288;
        zzfyVar.zzC = j;
    }

    static /* synthetic */ void zzaH(zzfy zzfyVar, int i) {
        zzfyVar.zzf |= 1048576;
        zzfyVar.zzD = i;
    }

    static /* synthetic */ void zzaI(zzfy zzfyVar, String str) {
        zzfyVar.zzf |= 2097152;
        zzfyVar.zzE = str;
    }

    static /* synthetic */ void zzaJ(zzfy zzfyVar) {
        zzfyVar.zzf &= -2097153;
        zzfyVar.zzE = zze.zzE;
    }

    static /* synthetic */ void zzaK(zzfy zzfyVar, String str) {
        str.getClass();
        zzfyVar.zzf |= 4194304;
        zzfyVar.zzF = str;
    }

    static /* synthetic */ void zzaL(zzfy zzfyVar, boolean z) {
        zzfyVar.zzf |= 8388608;
        zzfyVar.zzG = z;
    }

    static /* synthetic */ void zzaM(zzfy zzfyVar, Iterable iterable) {
        zzkg<zzfk> zzkgVar = zzfyVar.zzH;
        if (!zzkgVar.zzc()) {
            zzfyVar.zzH = zzjz.zzbB(zzkgVar);
        }
        zzih.zzbq(iterable, zzfyVar.zzH);
    }

    static /* synthetic */ void zzaO(zzfy zzfyVar, String str) {
        str.getClass();
        zzfyVar.zzf |= 16777216;
        zzfyVar.zzI = str;
    }

    static /* synthetic */ void zzaP(zzfy zzfyVar, int i) {
        zzfyVar.zzf |= 33554432;
        zzfyVar.zzJ = i;
    }

    static /* synthetic */ void zzaQ(zzfy zzfyVar, int i) {
        zzfyVar.zzf |= 1;
        zzfyVar.zzh = 1;
    }

    static /* synthetic */ void zzaR(zzfy zzfyVar) {
        zzfyVar.zzf &= -268435457;
        zzfyVar.zzM = zze.zzM;
    }

    static /* synthetic */ void zzaS(zzfy zzfyVar, long j) {
        zzfyVar.zzf |= 536870912;
        zzfyVar.zzN = j;
    }

    static /* synthetic */ void zzaa(zzfy zzfyVar, String str) {
        str.getClass();
        zzfyVar.zzg |= 64;
        zzfyVar.zzX = str;
    }

    static /* synthetic */ void zzab(zzfy zzfyVar, String str) {
        str.getClass();
        zzfyVar.zzg |= 128;
        zzfyVar.zzY = str;
    }

    static /* synthetic */ void zzac(zzfy zzfyVar, Iterable iterable) {
        zzfyVar.zzbI();
        zzih.zzbq(iterable, zzfyVar.zzi);
    }

    static /* synthetic */ void zzae(zzfy zzfyVar, int i) {
        zzfyVar.zzbI();
        zzfyVar.zzi.remove(i);
    }

    static /* synthetic */ void zzaf(zzfy zzfyVar, int i, zzgh zzghVar) {
        zzghVar.getClass();
        zzfyVar.zzbJ();
        zzfyVar.zzj.set(i, zzghVar);
    }

    static /* synthetic */ void zzag(zzfy zzfyVar, zzgh zzghVar) {
        zzghVar.getClass();
        zzfyVar.zzbJ();
        zzfyVar.zzj.add(zzghVar);
    }

    static /* synthetic */ void zzah(zzfy zzfyVar, Iterable iterable) {
        zzfyVar.zzbJ();
        zzih.zzbq(iterable, zzfyVar.zzj);
    }

    static /* synthetic */ void zzai(zzfy zzfyVar, int i) {
        zzfyVar.zzbJ();
        zzfyVar.zzj.remove(i);
    }

    static /* synthetic */ void zzaj(zzfy zzfyVar, long j) {
        zzfyVar.zzf |= 2;
        zzfyVar.zzk = j;
    }

    static /* synthetic */ void zzak(zzfy zzfyVar, long j) {
        zzfyVar.zzf |= 4;
        zzfyVar.zzl = j;
    }

    static /* synthetic */ void zzal(zzfy zzfyVar, long j) {
        zzfyVar.zzf |= 8;
        zzfyVar.zzm = j;
    }

    static /* synthetic */ void zzam(zzfy zzfyVar, long j) {
        zzfyVar.zzf |= 16;
        zzfyVar.zzn = j;
    }

    static /* synthetic */ void zzan(zzfy zzfyVar) {
        zzfyVar.zzf &= -17;
        zzfyVar.zzn = 0L;
    }

    static /* synthetic */ void zzao(zzfy zzfyVar, long j) {
        zzfyVar.zzf |= 32;
        zzfyVar.zzo = j;
    }

    static /* synthetic */ void zzap(zzfy zzfyVar) {
        zzfyVar.zzf &= -33;
        zzfyVar.zzo = 0L;
    }

    static /* synthetic */ void zzaq(zzfy zzfyVar, String str) {
        zzfyVar.zzf |= 64;
        zzfyVar.zzp = "android";
    }

    static /* synthetic */ void zzar(zzfy zzfyVar, String str) {
        str.getClass();
        zzfyVar.zzf |= 128;
        zzfyVar.zzq = str;
    }

    static /* synthetic */ void zzas(zzfy zzfyVar, String str) {
        str.getClass();
        zzfyVar.zzf |= 256;
        zzfyVar.zzr = str;
    }

    static /* synthetic */ void zzat(zzfy zzfyVar, String str) {
        str.getClass();
        zzfyVar.zzf |= 512;
        zzfyVar.zzs = str;
    }

    static /* synthetic */ void zzau(zzfy zzfyVar, int i) {
        zzfyVar.zzf |= 1024;
        zzfyVar.zzt = i;
    }

    static /* synthetic */ void zzav(zzfy zzfyVar, String str) {
        str.getClass();
        zzfyVar.zzf |= 2048;
        zzfyVar.zzu = str;
    }

    static /* synthetic */ void zzaw(zzfy zzfyVar, String str) {
        str.getClass();
        zzfyVar.zzf |= 4096;
        zzfyVar.zzv = str;
    }

    static /* synthetic */ void zzax(zzfy zzfyVar, String str) {
        str.getClass();
        zzfyVar.zzf |= 8192;
        zzfyVar.zzw = str;
    }

    static /* synthetic */ void zzay(zzfy zzfyVar, long j) {
        zzfyVar.zzf |= 16384;
        zzfyVar.zzx = j;
    }

    static /* synthetic */ void zzaz(zzfy zzfyVar, long j) {
        zzfyVar.zzf |= 32768;
        zzfyVar.zzy = 43042L;
    }

    private final void zzbI() {
        zzkg<zzfo> zzkgVar = this.zzi;
        if (zzkgVar.zzc()) {
            return;
        }
        this.zzi = zzjz.zzbB(zzkgVar);
    }

    private final void zzbJ() {
        zzkg<zzgh> zzkgVar = this.zzj;
        if (zzkgVar.zzc()) {
            return;
        }
        this.zzj = zzjz.zzbB(zzkgVar);
    }

    public static zzfx zzu() {
        return zze.zzbu();
    }

    public final String zzA() {
        return this.zzu;
    }

    public final String zzB() {
        return this.zzw;
    }

    public final String zzC() {
        return this.zzY;
    }

    public final String zzD() {
        return this.zzr;
    }

    public final String zzE() {
        return this.zzP;
    }

    public final String zzF() {
        return this.zzI;
    }

    public final String zzG() {
        return this.zzX;
    }

    public final String zzH() {
        return this.zzF;
    }

    public final String zzI() {
        return this.zzE;
    }

    public final String zzJ() {
        return this.zzq;
    }

    public final String zzK() {
        return this.zzp;
    }

    public final String zzL() {
        return this.zzz;
    }

    public final String zzM() {
        return this.zzs;
    }

    public final List<zzfk> zzN() {
        return this.zzH;
    }

    public final List<zzfo> zzO() {
        return this.zzi;
    }

    public final List<zzgh> zzP() {
        return this.zzj;
    }

    public final int zza() {
        return this.zzJ;
    }

    public final boolean zzaT() {
        return this.zzA;
    }

    public final boolean zzaU() {
        return this.zzG;
    }

    public final boolean zzaV() {
        return (this.zzf & 1073741824) != 0;
    }

    public final boolean zzaW() {
        return (this.zzf & 33554432) != 0;
    }

    public final boolean zzaX() {
        return (this.zzf & 1048576) != 0;
    }

    public final boolean zzaY() {
        return (this.zzf & 536870912) != 0;
    }

    public final boolean zzaZ() {
        return (this.zzg & 128) != 0;
    }

    public final int zzb() {
        return this.zzD;
    }

    public final boolean zzba() {
        return (this.zzf & 524288) != 0;
    }

    public final boolean zzbb() {
        return (this.zzg & 16) != 0;
    }

    public final boolean zzbc() {
        return (this.zzf & 8) != 0;
    }

    public final boolean zzbd() {
        return (this.zzf & 16384) != 0;
    }

    public final boolean zzbe() {
        return (this.zzf & 131072) != 0;
    }

    public final boolean zzbf() {
        return (this.zzf & 32) != 0;
    }

    public final boolean zzbg() {
        return (this.zzf & 16) != 0;
    }

    public final boolean zzbh() {
        return (this.zzf & 1) != 0;
    }

    public final boolean zzbi() {
        return (this.zzg & 2) != 0;
    }

    public final boolean zzbj() {
        return (this.zzf & 8388608) != 0;
    }

    public final boolean zzbk() {
        return (this.zzf & 4) != 0;
    }

    public final boolean zzbl() {
        return (this.zzf & 1024) != 0;
    }

    public final boolean zzbm() {
        return (this.zzf & 2) != 0;
    }

    public final boolean zzbn() {
        return (this.zzf & 32768) != 0;
    }

    public final int zzc() {
        return this.zzi.size();
    }

    public final int zzd() {
        return this.zzh;
    }

    public final int zze() {
        return this.zzR;
    }

    public final int zzf() {
        return this.zzt;
    }

    public final int zzg() {
        return this.zzj.size();
    }

    public final long zzh() {
        return this.zzO;
    }

    public final long zzi() {
        return this.zzN;
    }

    public final long zzj() {
        return this.zzC;
    }

    public final long zzk() {
        return this.zzV;
    }

    @Override // com.google.android.gms.internal.measurement.zzjz
    protected final Object zzl(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzbF(zze, "\u00011\u0000\u0002\u0001;1\u0000\u0004\u0000\u0001င\u0000\u0002\u001b\u0003\u001b\u0004ဂ\u0001\u0005ဂ\u0002\u0006ဂ\u0003\u0007ဂ\u0005\bဈ\u0006\tဈ\u0007\nဈ\b\u000bဈ\t\fင\n\rဈ\u000b\u000eဈ\f\u0010ဈ\r\u0011ဂ\u000e\u0012ဂ\u000f\u0013ဈ\u0010\u0014ဇ\u0011\u0015ဈ\u0012\u0016ဂ\u0013\u0017င\u0014\u0018ဈ\u0015\u0019ဈ\u0016\u001aဂ\u0004\u001cဇ\u0017\u001d\u001b\u001eဈ\u0018\u001fင\u0019 င\u001a!င\u001b\"ဈ\u001c#ဂ\u001d$ဂ\u001e%ဈ\u001f&ဈ 'င!)ဈ\",ဉ#-\u001d.ဂ$/ဂ%2ဈ&4ဈ'5ဌ(7ဇ)9ဈ*:ဇ+;ဉ,", new Object[]{"zzf", "zzg", "zzh", "zzi", zzfo.class, "zzj", zzgh.class, "zzk", "zzl", "zzm", "zzo", "zzp", "zzq", "zzr", "zzs", "zzt", "zzu", "zzv", "zzw", "zzx", "zzy", "zzz", "zzA", "zzB", "zzC", "zzD", "zzE", "zzF", "zzn", "zzG", "zzH", zzfk.class, "zzI", "zzJ", "zzK", "zzL", "zzM", "zzN", "zzO", "zzP", "zzQ", "zzR", "zzS", "zzT", "zzU", "zzV", "zzW", "zzX", "zzY", "zzZ", zzfg.zza, "zzaa", "zzab", "zzac", "zzad"});
        }
        if (i2 == 3) {
            return new zzfy();
        }
        zzff zzffVar = null;
        if (i2 == 4) {
            return new zzfx(zzffVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zze;
    }

    public final long zzm() {
        return this.zzm;
    }

    public final long zzn() {
        return this.zzx;
    }

    public final long zzo() {
        return this.zzo;
    }

    public final long zzp() {
        return this.zzn;
    }

    public final long zzq() {
        return this.zzl;
    }

    public final long zzr() {
        return this.zzk;
    }

    public final long zzs() {
        return this.zzy;
    }

    public final zzfo zzt(int i) {
        return this.zzi.get(i);
    }

    public final zzgh zzw(int i) {
        return this.zzj.get(i);
    }

    public final String zzx() {
        return this.zzS;
    }

    public final String zzy() {
        return this.zzv;
    }

    public final String zzz() {
        return this.zzB;
    }
}
