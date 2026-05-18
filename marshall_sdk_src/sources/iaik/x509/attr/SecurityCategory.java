package iaik.x509.attr;

import iaik.asn1.ASN1Type;
import iaik.asn1.ObjectID;
import java.util.HashMap;

/* JADX INFO: loaded from: classes2.dex */
public abstract class SecurityCategory implements ASN1Type {
    static Class a;
    private static HashMap b = new HashMap(5);

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public static SecurityCategory create(ObjectID objectID) throws InstantiationException {
        Class cls = (Class) b.get(objectID);
        if (cls == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("No known implementation for ");
            stringBuffer.append(objectID.getName());
            throw new InstantiationException(stringBuffer.toString());
        }
        try {
            return (SecurityCategory) cls.newInstance();
        } catch (IllegalAccessException e) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append("Error when trying to create a ");
            stringBuffer2.append(cls);
            stringBuffer2.append("instance for ");
            stringBuffer2.append(objectID);
            stringBuffer2.append(": ");
            stringBuffer2.append(e.getMessage());
            throw new InstantiationException(stringBuffer2.toString());
        }
    }

    public static synchronized void register(ObjectID objectID, Class cls) throws IllegalArgumentException {
        Class clsClass$ = a;
        if (clsClass$ == null) {
            clsClass$ = class$("iaik.x509.attr.SecurityCategory");
            a = clsClass$;
        }
        if (!clsClass$.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Only classes extended from SecurityCategory can be registered!");
        }
        b.put(objectID, cls);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SecurityCategory) {
            return getType().equals(((SecurityCategory) obj).getType());
        }
        return false;
    }

    public String getName() {
        return getType().getName();
    }

    public abstract ObjectID getType();

    public int hashCode() {
        return getType().hashCode();
    }

    public abstract String toString();
}
