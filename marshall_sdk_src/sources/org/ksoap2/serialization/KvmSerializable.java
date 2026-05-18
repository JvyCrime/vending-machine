package org.ksoap2.serialization;

import java.util.Hashtable;

/* JADX INFO: loaded from: classes2.dex */
public interface KvmSerializable {
    Object getProperty(int i);

    int getPropertyCount();

    void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo);

    void setProperty(int i, Object obj);
}
