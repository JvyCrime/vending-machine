package net.idscan.components.android.hwreaders.common;

import java.util.HashSet;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Observable.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B%\u0012\u001e\b\u0002\u0010\u0003\u001a\u0018\u0012\f\u0012\n\u0012\u0006\b\u0000\u0012\u00028\u00000\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004¢\u0006\u0002\u0010\u0007J\u0013\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00028\u0000¢\u0006\u0002\u0010\rJ\u0018\u0010\u000e\u001a\u00020\u000f2\u000e\u0010\u0010\u001a\n\u0012\u0006\b\u0000\u0012\u00028\u00000\u0005H\u0016J\u0018\u0010\u0011\u001a\u00020\u00062\u000e\u0010\u0010\u001a\n\u0012\u0006\b\u0000\u0012\u00028\u00000\u0005H\u0016R.\u0010\b\u001a\"\u0012\f\u0012\n\u0012\u0006\b\u0000\u0012\u00028\u00000\u00050\tj\u0010\u0012\f\u0012\n\u0012\u0006\b\u0000\u0012\u00028\u00000\u0005`\nX\u0082\u0004¢\u0006\u0002\n\u0000R$\u0010\u0003\u001a\u0018\u0012\f\u0012\n\u0012\u0006\b\u0000\u0012\u00028\u00000\u0005\u0012\u0004\u0012\u00020\u0006\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lnet/idscan/components/android/hwreaders/common/MutableObservable;", "T", "Lnet/idscan/components/android/hwreaders/common/Observable;", "onSubscribe", "Lkotlin/Function1;", "Lnet/idscan/components/android/hwreaders/common/Observer;", "", "(Lkotlin/jvm/functions/Function1;)V", "_observers", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "notify", "value", "(Ljava/lang/Object;)V", "registerObserver", "Lnet/idscan/components/android/hwreaders/common/Disposable;", "observer", "unregisterObserver", "component-common_release"}, k = 1, mv = {1, 4, 2})
public final class MutableObservable<T> implements Observable<T> {
    private final HashSet<Observer<? super T>> _observers;
    private final Function1<Observer<? super T>, Unit> onSubscribe;

    /* JADX WARN: Multi-variable type inference failed */
    public MutableObservable() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public MutableObservable(Function1<? super Observer<? super T>, Unit> function1) {
        this.onSubscribe = function1;
        this._observers = new HashSet<>();
    }

    public /* synthetic */ MutableObservable(Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? (Function1) null : function1);
    }

    @Override // net.idscan.components.android.hwreaders.common.Observable
    public Disposable registerObserver(final Observer<? super T> observer) {
        Intrinsics.checkNotNullParameter(observer, "observer");
        if (this._observers.add(observer)) {
            Function1<Observer<? super T>, Unit> function1 = this.onSubscribe;
            if (function1 != null) {
                function1.invoke(observer);
            }
            return new Disposable() { // from class: net.idscan.components.android.hwreaders.common.MutableObservable.registerObserver.1
                @Override // net.idscan.components.android.hwreaders.common.Disposable
                public void dispose() {
                    MutableObservable.this.unregisterObserver(observer);
                }
            };
        }
        return EmptyDisposable.INSTANCE;
    }

    @Override // net.idscan.components.android.hwreaders.common.Observable
    public void unregisterObserver(Observer<? super T> observer) {
        Intrinsics.checkNotNullParameter(observer, "observer");
        this._observers.remove(observer);
    }

    public final void notify(T value) {
        Iterator<T> it = this._observers.iterator();
        while (it.hasNext()) {
            ((Observer) it.next()).onNotify(value);
        }
    }
}
