package net.idscan.components.android.hwreaders.common;

import kotlin.Metadata;

/* JADX INFO: compiled from: Disposable.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lnet/idscan/components/android/hwreaders/common/EmptyDisposable;", "Lnet/idscan/components/android/hwreaders/common/Disposable;", "()V", "dispose", "", "component-common_release"}, k = 1, mv = {1, 4, 2})
public final class EmptyDisposable implements Disposable {
    public static final EmptyDisposable INSTANCE = new EmptyDisposable();

    @Override // net.idscan.components.android.hwreaders.common.Disposable
    public void dispose() {
    }

    private EmptyDisposable() {
    }
}
