package org.kobjects.util;

import java.util.Enumeration;

/* JADX INFO: loaded from: classes2.dex */
public class SingleEnumeration implements Enumeration {
    Object object;

    public SingleEnumeration(Object obj) {
        this.object = obj;
    }

    @Override // java.util.Enumeration
    public boolean hasMoreElements() {
        return this.object != null;
    }

    @Override // java.util.Enumeration
    public Object nextElement() {
        Object obj = this.object;
        this.object = null;
        return obj;
    }
}
