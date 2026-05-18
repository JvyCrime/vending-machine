package org.ksoap2.serialization;

import java.util.Hashtable;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class SoapObject extends AttributeContainer implements KvmSerializable {
    private static final String EMPTY_STRING = "";
    static /* synthetic */ Class class$java$lang$String;
    static /* synthetic */ Class class$org$ksoap2$serialization$SoapObject;
    protected String name;
    protected String namespace;
    protected Vector properties = new Vector();

    public SoapObject(String str, String str2) {
        this.namespace = str;
        this.name = str2;
    }

    public boolean equals(Object obj) {
        int size;
        if (!(obj instanceof SoapObject)) {
            return false;
        }
        SoapObject soapObject = (SoapObject) obj;
        if (!this.name.equals(soapObject.name) || !this.namespace.equals(soapObject.namespace) || (size = this.properties.size()) != soapObject.properties.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!soapObject.isPropertyEqual(this.properties.elementAt(i), i)) {
                return false;
            }
        }
        return attributesAreEqual(soapObject);
    }

    public boolean isPropertyEqual(Object obj, int i) {
        if (i >= getPropertyCount()) {
            return false;
        }
        Object objElementAt = this.properties.elementAt(i);
        if ((obj instanceof PropertyInfo) && (objElementAt instanceof PropertyInfo)) {
            PropertyInfo propertyInfo = (PropertyInfo) obj;
            PropertyInfo propertyInfo2 = (PropertyInfo) objElementAt;
            return propertyInfo.getName().equals(propertyInfo2.getName()) && propertyInfo.getValue().equals(propertyInfo2.getValue());
        }
        if ((obj instanceof SoapObject) && (objElementAt instanceof SoapObject)) {
            return ((SoapObject) obj).equals((SoapObject) objElementAt);
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public Object getNestedSoap(int i) {
        return getProperty(i);
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public Object getProperty(int i) {
        Object objElementAt = this.properties.elementAt(i);
        if (objElementAt instanceof PropertyInfo) {
            return ((PropertyInfo) objElementAt).getValue();
        }
        return (SoapObject) objElementAt;
    }

    public String getPropertyAsString(int i) {
        return ((PropertyInfo) this.properties.elementAt(i)).getValue().toString();
    }

    public Object getProperty(String str) {
        Integer numPropertyIndex = propertyIndex(str);
        if (numPropertyIndex != null) {
            return getProperty(numPropertyIndex.intValue());
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("illegal property: ");
        stringBuffer.append(str);
        throw new RuntimeException(stringBuffer.toString());
    }

    public String getPropertyAsString(String str) {
        Integer numPropertyIndex = propertyIndex(str);
        if (numPropertyIndex != null) {
            return getProperty(numPropertyIndex.intValue()).toString();
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("illegal property: ");
        stringBuffer.append(str);
        throw new RuntimeException(stringBuffer.toString());
    }

    public boolean hasProperty(String str) {
        return propertyIndex(str) != null;
    }

    public Object getPropertySafely(String str) {
        Integer numPropertyIndex = propertyIndex(str);
        if (numPropertyIndex != null) {
            return getProperty(numPropertyIndex.intValue());
        }
        return new NullSoapObject();
    }

    public String getPropertySafelyAsString(String str) {
        Object property;
        Integer numPropertyIndex = propertyIndex(str);
        return (numPropertyIndex == null || (property = getProperty(numPropertyIndex.intValue())) == null) ? "" : property.toString();
    }

    public Object safeGetProperty(String str) {
        return getPropertySafely(str);
    }

    public Object getPropertySafely(String str, Object obj) {
        Integer numPropertyIndex = propertyIndex(str);
        return numPropertyIndex != null ? getProperty(numPropertyIndex.intValue()) : obj;
    }

    public String getPropertySafelyAsString(String str, Object obj) {
        Integer numPropertyIndex = propertyIndex(str);
        if (numPropertyIndex == null) {
            return obj != null ? obj.toString() : "";
        }
        Object property = getProperty(numPropertyIndex.intValue());
        return property != null ? property.toString() : "";
    }

    public Object safeGetProperty(String str, Object obj) {
        return getPropertySafely(str, obj);
    }

    public Object getPrimitiveProperty(String str) {
        Integer numPropertyIndex = propertyIndex(str);
        if (numPropertyIndex != null) {
            PropertyInfo propertyInfo = (PropertyInfo) this.properties.elementAt(numPropertyIndex.intValue());
            Object type = propertyInfo.getType();
            Class clsClass$ = class$org$ksoap2$serialization$SoapObject;
            if (clsClass$ == null) {
                clsClass$ = class$("org.ksoap2.serialization.SoapObject");
                class$org$ksoap2$serialization$SoapObject = clsClass$;
            }
            if (type != clsClass$) {
                return propertyInfo.getValue();
            }
            PropertyInfo propertyInfo2 = new PropertyInfo();
            Class clsClass$2 = class$java$lang$String;
            if (clsClass$2 == null) {
                clsClass$2 = class$("java.lang.String");
                class$java$lang$String = clsClass$2;
            }
            propertyInfo2.setType(clsClass$2);
            propertyInfo2.setValue("");
            propertyInfo2.setName(str);
            return propertyInfo2.getValue();
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("illegal property: ");
        stringBuffer.append(str);
        throw new RuntimeException(stringBuffer.toString());
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public String getPrimitivePropertyAsString(String str) {
        Integer numPropertyIndex = propertyIndex(str);
        if (numPropertyIndex != null) {
            PropertyInfo propertyInfo = (PropertyInfo) this.properties.elementAt(numPropertyIndex.intValue());
            Object type = propertyInfo.getType();
            Class clsClass$ = class$org$ksoap2$serialization$SoapObject;
            if (clsClass$ == null) {
                clsClass$ = class$("org.ksoap2.serialization.SoapObject");
                class$org$ksoap2$serialization$SoapObject = clsClass$;
            }
            return type != clsClass$ ? propertyInfo.getValue().toString() : "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("illegal property: ");
        stringBuffer.append(str);
        throw new RuntimeException(stringBuffer.toString());
    }

    public Object getPrimitivePropertySafely(String str) {
        Integer numPropertyIndex = propertyIndex(str);
        if (numPropertyIndex != null) {
            PropertyInfo propertyInfo = (PropertyInfo) this.properties.elementAt(numPropertyIndex.intValue());
            Object type = propertyInfo.getType();
            Class clsClass$ = class$org$ksoap2$serialization$SoapObject;
            if (clsClass$ == null) {
                clsClass$ = class$("org.ksoap2.serialization.SoapObject");
                class$org$ksoap2$serialization$SoapObject = clsClass$;
            }
            if (type != clsClass$) {
                return propertyInfo.getValue().toString();
            }
            PropertyInfo propertyInfo2 = new PropertyInfo();
            Class clsClass$2 = class$java$lang$String;
            if (clsClass$2 == null) {
                clsClass$2 = class$("java.lang.String");
                class$java$lang$String = clsClass$2;
            }
            propertyInfo2.setType(clsClass$2);
            propertyInfo2.setValue("");
            propertyInfo2.setName(str);
            return propertyInfo2.getValue();
        }
        return new NullSoapObject();
    }

    public String getPrimitivePropertySafelyAsString(String str) {
        Integer numPropertyIndex = propertyIndex(str);
        if (numPropertyIndex != null) {
            PropertyInfo propertyInfo = (PropertyInfo) this.properties.elementAt(numPropertyIndex.intValue());
            Object type = propertyInfo.getType();
            Class clsClass$ = class$org$ksoap2$serialization$SoapObject;
            if (clsClass$ == null) {
                clsClass$ = class$("org.ksoap2.serialization.SoapObject");
                class$org$ksoap2$serialization$SoapObject = clsClass$;
            }
            if (type != clsClass$) {
                return propertyInfo.getValue().toString();
            }
        }
        return "";
    }

    private Integer propertyIndex(String str) {
        if (str == null) {
            return null;
        }
        for (int i = 0; i < this.properties.size(); i++) {
            if (str.equals(((PropertyInfo) this.properties.elementAt(i)).getName())) {
                return new Integer(i);
            }
        }
        return null;
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public int getPropertyCount() {
        return this.properties.size();
    }

    public int getNestedSoapCount() {
        int i = 0;
        for (int i2 = 0; i2 < this.properties.size(); i2++) {
            if (this.properties.elementAt(i2) instanceof SoapObject) {
                i++;
            }
        }
        return i;
    }

    @Override // org.ksoap2.serialization.KvmSerializable
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        getPropertyInfo(i, propertyInfo);
    }

    public void getPropertyInfo(int i, PropertyInfo propertyInfo) {
        Object objElementAt = this.properties.elementAt(i);
        if (objElementAt instanceof PropertyInfo) {
            PropertyInfo propertyInfo2 = (PropertyInfo) objElementAt;
            propertyInfo.name = propertyInfo2.name;
            propertyInfo.namespace = propertyInfo2.namespace;
            propertyInfo.flags = propertyInfo2.flags;
            propertyInfo.type = propertyInfo2.type;
            propertyInfo.elementType = propertyInfo2.elementType;
            propertyInfo.value = propertyInfo2.value;
            propertyInfo.multiRef = propertyInfo2.multiRef;
            return;
        }
        propertyInfo.name = null;
        propertyInfo.namespace = null;
        propertyInfo.flags = 0;
        propertyInfo.type = null;
        propertyInfo.elementType = null;
        propertyInfo.value = objElementAt;
        propertyInfo.multiRef = false;
    }

    public SoapObject newInstance() {
        SoapObject soapObject = new SoapObject(this.namespace, this.name);
        for (int i = 0; i < this.properties.size(); i++) {
            Object objElementAt = this.properties.elementAt(i);
            if (objElementAt instanceof PropertyInfo) {
                soapObject.addProperty((PropertyInfo) ((PropertyInfo) this.properties.elementAt(i)).clone());
            } else if (objElementAt instanceof SoapObject) {
                soapObject.addSoapObject(((SoapObject) objElementAt).newInstance());
            }
        }
        for (int i2 = 0; i2 < getAttributeCount(); i2++) {
            AttributeInfo attributeInfo = new AttributeInfo();
            getAttributeInfo(i2, attributeInfo);
            soapObject.addAttribute(attributeInfo);
        }
        return soapObject;
    }

    public void setProperty(int i, Object obj) {
        Object objElementAt = this.properties.elementAt(i);
        if (objElementAt instanceof PropertyInfo) {
            ((PropertyInfo) objElementAt).setValue(obj);
        }
    }

    public SoapObject addProperty(String str, Object obj) {
        PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.name = str;
        propertyInfo.type = obj == null ? PropertyInfo.OBJECT_CLASS : obj.getClass();
        propertyInfo.value = obj;
        return addProperty(propertyInfo);
    }

    public SoapObject addPropertyIfValue(String str, Object obj) {
        return obj != null ? addProperty(str, obj) : this;
    }

    public SoapObject addProperty(PropertyInfo propertyInfo, Object obj) {
        propertyInfo.setValue(obj);
        addProperty(propertyInfo);
        return this;
    }

    public SoapObject addPropertyIfValue(PropertyInfo propertyInfo, Object obj) {
        return obj != null ? addProperty(propertyInfo, obj) : this;
    }

    public SoapObject addProperty(PropertyInfo propertyInfo) {
        this.properties.addElement(propertyInfo);
        return this;
    }

    public SoapObject addPropertyIfValue(PropertyInfo propertyInfo) {
        if (propertyInfo.value != null) {
            this.properties.addElement(propertyInfo);
        }
        return this;
    }

    public SoapObject addSoapObject(SoapObject soapObject) {
        this.properties.addElement(soapObject);
        return this;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("");
        stringBuffer.append(this.name);
        stringBuffer.append("{");
        StringBuffer stringBuffer2 = new StringBuffer(stringBuffer.toString());
        for (int i = 0; i < getPropertyCount(); i++) {
            Object objElementAt = this.properties.elementAt(i);
            if (objElementAt instanceof PropertyInfo) {
                StringBuffer stringBuffer3 = new StringBuffer();
                stringBuffer3.append("");
                stringBuffer3.append(((PropertyInfo) objElementAt).getName());
                stringBuffer3.append("=");
                stringBuffer3.append(getProperty(i));
                stringBuffer3.append("; ");
                stringBuffer2.append(stringBuffer3.toString());
            } else {
                stringBuffer2.append(((SoapObject) objElementAt).toString());
            }
        }
        stringBuffer2.append("}");
        return stringBuffer2.toString();
    }
}
