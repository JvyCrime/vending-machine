package com.google.android.gms.internal.measurement;

import com.felhr.usbserial.UsbSerialDebugger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zzkh {
    static final Charset zza = Charset.forName(UsbSerialDebugger.ENCODING);
    static final Charset zzb = Charset.forName("ISO-8859-1");
    public static final byte[] zzc;
    public static final ByteBuffer zzd;
    public static final zzjc zze;

    static {
        byte[] bArr = new byte[0];
        zzc = bArr;
        zzd = ByteBuffer.wrap(bArr);
        int i = zzjc.zza;
        zzja zzjaVar = new zzja(bArr, 0, 0, false, null);
        try {
            zzjaVar.zza(0);
            zze = zzjaVar;
        } catch (zzkj e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static int zza(boolean z) {
        return z ? 1231 : 1237;
    }

    public static int zzb(byte[] bArr) {
        int length = bArr.length;
        int iZzd = zzd(length, bArr, 0, length);
        if (iZzd == 0) {
            return 1;
        }
        return iZzd;
    }

    public static int zzc(long j) {
        return (int) (j ^ (j >>> 32));
    }

    static int zzd(int i, byte[] bArr, int i2, int i3) {
        for (int i4 = 0; i4 < i3; i4++) {
            i = (i * 31) + bArr[i4];
        }
        return i;
    }

    static <T> T zze(T t) {
        Objects.requireNonNull(t);
        return t;
    }

    static <T> T zzf(T t, String str) {
        Objects.requireNonNull(t, str);
        return t;
    }

    static Object zzg(Object obj, Object obj2) {
        return ((zzlg) obj).zzbD().zzau((zzlg) obj2).zzaC();
    }

    public static String zzh(byte[] bArr) {
        return new String(bArr, zza);
    }

    public static boolean zzi(byte[] bArr) {
        return zzmx.zze(bArr);
    }
}
