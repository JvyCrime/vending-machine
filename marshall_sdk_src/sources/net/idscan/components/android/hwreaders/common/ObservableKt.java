package net.idscan.components.android.hwreaders.common;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Observable.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u001a*\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00060\u0005¨\u0006\u0007"}, d2 = {"registerObserver", "Lnet/idscan/components/android/hwreaders/common/Disposable;", "T", "Lnet/idscan/components/android/hwreaders/common/Observable;", "observer", "Lkotlin/Function1;", "", "component-common_release"}, k = 2, mv = {1, 4, 2})
public final class ObservableKt {
    public static final <T> Disposable registerObserver(Observable<T> registerObserver, final Function1<? super T, Unit> observer) {
        Intrinsics.checkNotNullParameter(registerObserver, "$this$registerObserver");
        Intrinsics.checkNotNullParameter(observer, "observer");
        return registerObserver.registerObserver(new Observer<T>() { // from class: net.idscan.components.android.hwreaders.common.ObservableKt.registerObserver.1
            @Override // net.idscan.components.android.hwreaders.common.Observer
            public final void onNotify(T t) {
                observer.invoke(t);
            }
        });
    }
}
