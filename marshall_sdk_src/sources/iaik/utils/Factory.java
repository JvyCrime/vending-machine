package iaik.utils;

import java.util.Hashtable;

/* JADX INFO: loaded from: classes2.dex */
public abstract class Factory {
    protected static final Hashtable interfaces = new Hashtable();
    protected static final Hashtable singletons = new Hashtable();

    protected Factory() {
    }

    protected abstract Object create(Class cls) throws IllegalAccessException, InstantiationException;

    protected synchronized Object create(Class cls, Object obj, boolean z) throws InstantiationException {
        Object obj2;
        Hashtable hashtable = (Hashtable) interfaces.get(cls);
        if (hashtable == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No implementations known for class ");
            stringBuffer.append(cls.getName());
            stringBuffer.append(".");
            throw new InstantiationException(stringBuffer.toString());
        }
        Class cls2 = (Class) hashtable.get(obj);
        if (cls2 == null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("No known implementation for ");
            stringBuffer2.append(obj);
            throw new InstantiationException(stringBuffer2.toString());
        }
        if (z && (obj2 = singletons.get(cls2)) != null) {
            return obj2;
        }
        try {
            Object objCreate = create(cls2);
            if (z) {
                singletons.put(cls2, objCreate);
            }
            return objCreate;
        } catch (IllegalAccessException e) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Error while creating an instance for ");
            stringBuffer3.append(obj);
            stringBuffer3.append(" using class ");
            stringBuffer3.append(cls2);
            stringBuffer3.append(": ");
            stringBuffer3.append(e.getMessage());
            throw new InstantiationException(stringBuffer3.toString());
        }
    }

    protected synchronized void register(Class cls, Object obj, Class cls2) {
        Hashtable hashtable = interfaces;
        Hashtable hashtable2 = (Hashtable) hashtable.get(cls);
        if (hashtable2 == null) {
            hashtable2 = new Hashtable();
            hashtable.put(cls, hashtable2);
        }
        hashtable2.put(obj, cls2);
    }
}
