package org.ksoap2.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class PropertyInfo implements Serializable {
    public static final int MULTI_REF = 2;
    public static final int REF_ONLY = 4;
    public static final int TRANSIENT = 1;
    public PropertyInfo elementType;
    public int flags;
    public boolean multiRef;
    public String name;
    public String namespace;
    public Object type = OBJECT_CLASS;
    protected Object value;
    public static final Class OBJECT_CLASS = new Object().getClass();
    public static final Class STRING_CLASS = "".getClass();
    public static final Class INTEGER_CLASS = new Integer(0).getClass();
    public static final Class LONG_CLASS = new Long(0).getClass();
    public static final Class BOOLEAN_CLASS = new Boolean(true).getClass();
    public static final Class VECTOR_CLASS = new Vector().getClass();
    public static final PropertyInfo OBJECT_TYPE = new PropertyInfo();

    public void clear() {
        this.type = OBJECT_CLASS;
        this.flags = 0;
        this.name = null;
        this.namespace = null;
    }

    public PropertyInfo getElementType() {
        return this.elementType;
    }

    public void setElementType(PropertyInfo propertyInfo) {
        this.elementType = propertyInfo;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int i) {
        this.flags = i;
    }

    public boolean isMultiRef() {
        return this.multiRef;
    }

    public void setMultiRef(boolean z) {
        this.multiRef = z;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public void setNamespace(String str) {
        this.namespace = str;
    }

    public Object getType() {
        return this.type;
    }

    public void setType(Object obj) {
        this.type = obj;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object obj) {
        this.value = obj;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.name);
        stringBuffer.append(" : ");
        Object obj = this.value;
        if (obj != null) {
            stringBuffer.append(obj);
        } else {
            stringBuffer.append("(not set)");
        }
        return stringBuffer.toString();
    }

    public Object clone() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
            return new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())).readObject();
        } catch (NotSerializableException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        } catch (ClassNotFoundException e3) {
            e3.printStackTrace();
            return null;
        }
    }
}
