package net.idscan.components.android.hwreaders.common;

import kotlin.Metadata;

/* JADX INFO: compiled from: Observable.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00028\u00000\u0006H&J\u0018\u0010\u0007\u001a\u00020\b2\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0000\u0012\u00028\u00000\u0006H&¨\u0006\t"}, d2 = {"Lnet/idscan/components/android/hwreaders/common/Observable;", "T", "", "registerObserver", "Lnet/idscan/components/android/hwreaders/common/Disposable;", "observer", "Lnet/idscan/components/android/hwreaders/common/Observer;", "unregisterObserver", "", "component-common_release"}, k = 1, mv = {1, 4, 2})
public interface Observable<T> {
    Disposable registerObserver(Observer<? super T> observer);

    void unregisterObserver(Observer<? super T> observer);
}
