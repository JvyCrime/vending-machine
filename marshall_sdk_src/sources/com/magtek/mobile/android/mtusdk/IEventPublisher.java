package com.magtek.mobile.android.mtusdk;

/* JADX INFO: loaded from: classes.dex */
public interface IEventPublisher {
    boolean addSubscriber(IEventSubscriber iEventSubscriber);

    boolean removeSubscriber(IEventSubscriber iEventSubscriber);
}
