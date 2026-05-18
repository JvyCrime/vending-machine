package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.text.TextUtils;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
final class zzkp implements zzky {
    final /* synthetic */ zzks zza;

    zzkp(zzks zzksVar) {
        this.zza = zzksVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzky
    public final void zza(String str, String str2, Bundle bundle) {
        if (!TextUtils.isEmpty(str)) {
            this.zza.zzaz().zzp(new zzko(this, str, "_err", bundle));
        } else if (this.zza.zzn != null) {
            this.zza.zzn.zzay().zzd().zzb("AppId not known when logging event", "_err");
        }
    }
}
