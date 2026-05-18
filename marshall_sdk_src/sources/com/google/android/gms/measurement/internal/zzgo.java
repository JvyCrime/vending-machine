package com.google.android.gms.measurement.internal;

import android.content.Context;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Clock;
import org.checkerframework.dataflow.qual.Pure;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@19.0.2 */
/* JADX INFO: loaded from: classes.dex */
class zzgo implements zzgq {
    protected final zzfv zzs;

    zzgo(zzfv zzfvVar) {
        Preconditions.checkNotNull(zzfvVar);
        this.zzs = zzfvVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzgq
    @Pure
    public final Context zzau() {
        throw null;
    }

    @Override // com.google.android.gms.measurement.internal.zzgq
    @Pure
    public final Clock zzav() {
        throw null;
    }

    @Override // com.google.android.gms.measurement.internal.zzgq
    @Pure
    public final zzaa zzaw() {
        throw null;
    }

    public void zzax() {
        this.zzs.zzaz().zzax();
    }

    @Override // com.google.android.gms.measurement.internal.zzgq
    @Pure
    public final zzel zzay() {
        throw null;
    }

    @Override // com.google.android.gms.measurement.internal.zzgq
    @Pure
    public final zzfs zzaz() {
        throw null;
    }

    public void zzg() {
        this.zzs.zzaz().zzg();
    }
}
