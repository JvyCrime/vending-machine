package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
public final class zzat extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzat> CREATOR = new zzau();
    public final String zza;
    public final zzar zzb;
    public final String zzc;
    public final long zzd;

    zzat(zzat zzatVar, long j) {
        Preconditions.checkNotNull(zzatVar);
        this.zza = zzatVar.zza;
        this.zzb = zzatVar.zzb;
        this.zzc = zzatVar.zzc;
        this.zzd = j;
    }

    public final String toString() {
        String str = this.zzc;
        String str2 = this.zza;
        String strValueOf = String.valueOf(this.zzb);
        int length = String.valueOf(str).length();
        StringBuilder sb = new StringBuilder(length + 21 + String.valueOf(str2).length() + String.valueOf(strValueOf).length());
        sb.append("origin=");
        sb.append(str);
        sb.append(",name=");
        sb.append(str2);
        sb.append(",params=");
        sb.append(strValueOf);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        zzau.zza(this, parcel, i);
    }

    public zzat(String str, zzar zzarVar, String str2, long j) {
        this.zza = str;
        this.zzb = zzarVar;
        this.zzc = str2;
        this.zzd = j;
    }
}
