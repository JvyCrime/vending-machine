package iaik.utils;

import java.util.HashMap;

/* JADX INFO: loaded from: classes2.dex */
public class ObjectFactory {
    private HashMap a;
    private HashMap b;
    private Class c;

    public ObjectFactory() {
        this(20);
    }

    public ObjectFactory(int i) {
        this(i, null);
    }

    public ObjectFactory(int i, Class cls) {
        this.a = new HashMap(i);
        if (cls != null) {
            this.c = cls;
            this.b = new HashMap(i);
        }
    }

    public Object create(Class cls) throws IllegalAccessException, InstantiationException {
        try {
            return cls.newInstance();
        } catch (IllegalAccessException e) {
            throw new InternalErrorException("Error creating local implementation!", e);
        } catch (InstantiationException e2) {
            throw new InternalErrorException("Error creating local implementation!", e2);
        }
    }

    public Object create(Object obj) throws InstantiationException {
        String str;
        Class<?> cls = (Class) this.a.get(obj);
        if (cls == null && this.c != null && (str = (String) this.b.get(obj)) != null) {
            try {
                cls = Class.forName(str);
                if (cls != null) {
                    if (!this.c.isAssignableFrom(cls)) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Class ");
                        stringBuffer.append(cls);
                        stringBuffer.append(" is no ");
                        stringBuffer.append(this.c);
                        stringBuffer.append(" implementation!");
                        throw new InstantiationException(stringBuffer.toString());
                    }
                    register(obj, cls);
                }
            } catch (ClassNotFoundException e) {
                throw new InstantiationException(e.toString());
            }
        }
        if (cls == null) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("No known implementation for ");
            stringBuffer2.append(obj);
            throw new InstantiationException(stringBuffer2.toString());
        }
        try {
            return create((Class) cls);
        } catch (IllegalAccessException e2) {
            StringBuffer stringBuffer3 = new StringBuffer();
            stringBuffer3.append("Error while creating an instance for ");
            stringBuffer3.append(obj);
            stringBuffer3.append(" using class ");
            stringBuffer3.append(cls);
            stringBuffer3.append(": ");
            stringBuffer3.append(e2.getMessage());
            throw new InstantiationException(stringBuffer3.toString());
        }
    }

    public void register(Object obj, Class cls) {
        this.a.put(obj, cls);
    }

    public void register(Object obj, String str) {
        if (this.c == null) {
            throw new IllegalArgumentException("Class name based registration not possible. No object class specified for this ObjectFactory!");
        }
        this.b.put(obj, str);
    }
}
