package iaik.utils;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/* JADX INFO: loaded from: classes2.dex */
public class ArrayEnumeration implements Enumeration {
    private Object[] a;
    private int b = 0;
    private int c;

    public ArrayEnumeration(Object[] objArr) {
        this.a = objArr;
        this.c = objArr.length;
    }

    public ArrayEnumeration(Object[] objArr, int i) {
        this.a = objArr;
        this.c = i;
    }

    @Override // java.util.Enumeration
    public boolean hasMoreElements() {
        return this.b < this.c;
    }

    @Override // java.util.Enumeration
    public synchronized Object nextElement() {
        int i;
        Object[] objArr;
        i = this.b;
        if (i >= this.c) {
            throw new NoSuchElementException("ArrayEnumeration");
        }
        objArr = this.a;
        this.b = i + 1;
        return objArr[i];
    }
}
