package iaik.utils;

import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class CollectionVector extends Vector {
    private static final long serialVersionUID = -6173085231557229205L;

    @Override // java.util.Vector, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(Object obj) {
        addElement(obj);
        return contains(obj);
    }

    @Override // java.util.Vector, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public void clear() {
        removeAllElements();
    }

    @Override // java.util.Vector, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean remove(Object obj) {
        return removeElement(obj);
    }

    @Override // java.util.Vector, java.util.AbstractCollection, java.util.Collection, java.util.List
    public Object[] toArray() {
        Object[] objArr = new Object[size()];
        copyInto(objArr);
        return objArr;
    }
}
