package com.magtek.mobile.android.mtusdk;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: loaded from: classes.dex */
public class EventPublisher implements IEventPublisher {
    private List<IEventSubscriber> mEventSubscriberList = new ArrayList();

    @Override // com.magtek.mobile.android.mtusdk.IEventPublisher
    public boolean addSubscriber(IEventSubscriber iEventSubscriber) {
        this.mEventSubscriberList.add(iEventSubscriber);
        return true;
    }

    @Override // com.magtek.mobile.android.mtusdk.IEventPublisher
    public boolean removeSubscriber(IEventSubscriber iEventSubscriber) {
        this.mEventSubscriberList.remove(iEventSubscriber);
        return true;
    }

    protected void sendEvent(EventType eventType, IData iData) {
        ListIterator<IEventSubscriber> listIterator = this.mEventSubscriberList.listIterator();
        while (listIterator.hasNext()) {
            try {
                IEventSubscriber next = listIterator.next();
                if (next != null) {
                    next.OnEvent(eventType, iData);
                }
            } catch (Exception unused) {
            }
        }
    }
}
