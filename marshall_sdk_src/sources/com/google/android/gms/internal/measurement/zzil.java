package com.google.android.gms.internal.measurement;

import java.io.IOException;
import kotlin.jvm.internal.ByteCompanionObject;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzil {
    static int zza(byte[] bArr, int i, zzik zzikVar) throws zzkj {
        int iZzj = zzj(bArr, i, zzikVar);
        int i2 = zzikVar.zza;
        if (i2 < 0) {
            throw zzkj.zzd();
        }
        if (i2 > bArr.length - iZzj) {
            throw zzkj.zzf();
        }
        if (i2 == 0) {
            zzikVar.zzc = zziy.zzb;
            return iZzj;
        }
        zzikVar.zzc = zziy.zzl(bArr, iZzj, i2);
        return iZzj + i2;
    }

    static int zzb(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    static int zzc(zzlr zzlrVar, byte[] bArr, int i, int i2, int i3, zzik zzikVar) throws IOException {
        zzlj zzljVar = (zzlj) zzlrVar;
        Object objZze = zzljVar.zze();
        int iZzc = zzljVar.zzc(objZze, bArr, i, i2, i3, zzikVar);
        zzljVar.zzf(objZze);
        zzikVar.zzc = objZze;
        return iZzc;
    }

    static int zzd(zzlr zzlrVar, byte[] bArr, int i, int i2, zzik zzikVar) throws IOException {
        int iZzk = i + 1;
        int i3 = bArr[i];
        if (i3 < 0) {
            iZzk = zzk(i3, bArr, iZzk, zzikVar);
            i3 = zzikVar.zza;
        }
        int i4 = iZzk;
        if (i3 < 0 || i3 > i2 - i4) {
            throw zzkj.zzf();
        }
        Object objZze = zzlrVar.zze();
        int i5 = i3 + i4;
        zzlrVar.zzh(objZze, bArr, i4, i5, zzikVar);
        zzlrVar.zzf(objZze);
        zzikVar.zzc = objZze;
        return i5;
    }

    static int zze(zzlr<?> zzlrVar, int i, byte[] bArr, int i2, int i3, zzkg<?> zzkgVar, zzik zzikVar) throws IOException {
        int iZzd = zzd(zzlrVar, bArr, i2, i3, zzikVar);
        zzkgVar.add(zzikVar.zzc);
        while (iZzd < i3) {
            int iZzj = zzj(bArr, iZzd, zzikVar);
            if (i != zzikVar.zza) {
                break;
            }
            iZzd = zzd(zzlrVar, bArr, iZzj, i3, zzikVar);
            zzkgVar.add(zzikVar.zzc);
        }
        return iZzd;
    }

    static int zzf(byte[] bArr, int i, zzkg<?> zzkgVar, zzik zzikVar) throws IOException {
        zzka zzkaVar = (zzka) zzkgVar;
        int iZzj = zzj(bArr, i, zzikVar);
        int i2 = zzikVar.zza + iZzj;
        while (iZzj < i2) {
            iZzj = zzj(bArr, iZzj, zzikVar);
            zzkaVar.zzh(zzikVar.zza);
        }
        if (iZzj == i2) {
            return iZzj;
        }
        throw zzkj.zzf();
    }

    static int zzg(byte[] bArr, int i, zzik zzikVar) throws zzkj {
        int iZzj = zzj(bArr, i, zzikVar);
        int i2 = zzikVar.zza;
        if (i2 < 0) {
            throw zzkj.zzd();
        }
        if (i2 == 0) {
            zzikVar.zzc = "";
            return iZzj;
        }
        zzikVar.zzc = new String(bArr, iZzj, i2, zzkh.zza);
        return iZzj + i2;
    }

    static int zzh(byte[] bArr, int i, zzik zzikVar) throws zzkj {
        int iZzj = zzj(bArr, i, zzikVar);
        int i2 = zzikVar.zza;
        if (i2 < 0) {
            throw zzkj.zzd();
        }
        if (i2 == 0) {
            zzikVar.zzc = "";
            return iZzj;
        }
        zzikVar.zzc = zzmx.zzd(bArr, iZzj, i2);
        return iZzj + i2;
    }

    static int zzi(int i, byte[] bArr, int i2, int i3, zzmj zzmjVar, zzik zzikVar) throws zzkj {
        if ((i >>> 3) == 0) {
            throw zzkj.zzb();
        }
        int i4 = i & 7;
        if (i4 == 0) {
            int iZzm = zzm(bArr, i2, zzikVar);
            zzmjVar.zzh(i, Long.valueOf(zzikVar.zzb));
            return iZzm;
        }
        if (i4 == 1) {
            zzmjVar.zzh(i, Long.valueOf(zzn(bArr, i2)));
            return i2 + 8;
        }
        if (i4 == 2) {
            int iZzj = zzj(bArr, i2, zzikVar);
            int i5 = zzikVar.zza;
            if (i5 < 0) {
                throw zzkj.zzd();
            }
            if (i5 > bArr.length - iZzj) {
                throw zzkj.zzf();
            }
            if (i5 == 0) {
                zzmjVar.zzh(i, zziy.zzb);
            } else {
                zzmjVar.zzh(i, zziy.zzl(bArr, iZzj, i5));
            }
            return iZzj + i5;
        }
        if (i4 != 3) {
            if (i4 != 5) {
                throw zzkj.zzb();
            }
            zzmjVar.zzh(i, Integer.valueOf(zzb(bArr, i2)));
            return i2 + 4;
        }
        int i6 = (i & (-8)) | 4;
        zzmj zzmjVarZze = zzmj.zze();
        int i7 = 0;
        while (true) {
            if (i2 >= i3) {
                break;
            }
            int iZzj2 = zzj(bArr, i2, zzikVar);
            int i8 = zzikVar.zza;
            if (i8 == i6) {
                i7 = i8;
                i2 = iZzj2;
                break;
            }
            i7 = i8;
            i2 = zzi(i8, bArr, iZzj2, i3, zzmjVarZze, zzikVar);
        }
        if (i2 > i3 || i7 != i6) {
            throw zzkj.zze();
        }
        zzmjVar.zzh(i, zzmjVarZze);
        return i2;
    }

    static int zzj(byte[] bArr, int i, zzik zzikVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zzk(b, bArr, i2, zzikVar);
        }
        zzikVar.zza = b;
        return i2;
    }

    static int zzk(int i, byte[] bArr, int i2, zzik zzikVar) {
        int i3 = i & 127;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            zzikVar.zza = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & ByteCompanionObject.MAX_VALUE) << 7);
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            zzikVar.zza = i5 | (b2 << 14);
            return i6;
        }
        int i7 = i5 | ((b2 & ByteCompanionObject.MAX_VALUE) << 14);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzikVar.zza = i7 | (b3 << 21);
            return i8;
        }
        int i9 = i7 | ((b3 & ByteCompanionObject.MAX_VALUE) << 21);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzikVar.zza = i9 | (b4 << 28);
            return i10;
        }
        int i11 = i9 | ((b4 & ByteCompanionObject.MAX_VALUE) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzikVar.zza = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int zzl(int i, byte[] bArr, int i2, int i3, zzkg<?> zzkgVar, zzik zzikVar) {
        zzka zzkaVar = (zzka) zzkgVar;
        int iZzj = zzj(bArr, i2, zzikVar);
        zzkaVar.zzh(zzikVar.zza);
        while (iZzj < i3) {
            int iZzj2 = zzj(bArr, iZzj, zzikVar);
            if (i != zzikVar.zza) {
                break;
            }
            iZzj = zzj(bArr, iZzj2, zzikVar);
            zzkaVar.zzh(zzikVar.zza);
        }
        return iZzj;
    }

    static int zzm(byte[] bArr, int i, zzik zzikVar) {
        int i2 = i + 1;
        long j = bArr[i];
        if (j >= 0) {
            zzikVar.zzb = j;
            return i2;
        }
        int i3 = i2 + 1;
        byte b = bArr[i2];
        long j2 = (j & 127) | (((long) (b & ByteCompanionObject.MAX_VALUE)) << 7);
        int i4 = 7;
        while (b < 0) {
            int i5 = i3 + 1;
            byte b2 = bArr[i3];
            i4 += 7;
            j2 |= ((long) (b2 & ByteCompanionObject.MAX_VALUE)) << i4;
            b = b2;
            i3 = i5;
        }
        zzikVar.zzb = j2;
        return i3;
    }

    static long zzn(byte[] bArr, int i) {
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }
}
