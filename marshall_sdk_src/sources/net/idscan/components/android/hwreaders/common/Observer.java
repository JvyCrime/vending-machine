package net.idscan.components.android.hwreaders.common;

import kotlin.Metadata;

/* JADX INFO: compiled from: Observable.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bæ\u0080\u0001\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lnet/idscan/components/android/hwreaders/common/Observer;", "T", "", "onNotify", "", "value", "(Ljava/lang/Object;)V", "component-common_release"}, k = 1, mv = {1, 4, 2})
public interface Observer<T> {
    void onNotify(T value);
}
