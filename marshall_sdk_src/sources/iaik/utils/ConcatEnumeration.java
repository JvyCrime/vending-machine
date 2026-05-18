package iaik.utils;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class ConcatEnumeration implements Enumeration {
    private Enumeration a;
    private Enumeration b;

    public ConcatEnumeration(Enumeration enumeration) {
        this.a = enumeration;
    }

    public ConcatEnumeration(Enumeration enumeration, Enumeration enumeration2) {
        Vector vector = new Vector();
        vector.addElement(enumeration);
        vector.addElement(enumeration2);
        this.a = vector.elements();
    }

    @Override // java.util.Enumeration
    public boolean hasMoreElements() {
        while (true) {
            Enumeration enumeration = this.b;
            if (enumeration != null && enumeration.hasMoreElements()) {
                return true;
            }
            this.b = null;
            Enumeration enumeration2 = this.a;
            if (enumeration2 == null || !enumeration2.hasMoreElements()) {
                break;
            }
            this.b = (Enumeration) this.a.nextElement();
        }
        this.a = null;
        return false;
    }

    @Override // java.util.Enumeration
    public Object nextElement() {
        if (hasMoreElements()) {
            return this.b.nextElement();
        }
        throw new NoSuchElementException("No more elements");
    }
}
