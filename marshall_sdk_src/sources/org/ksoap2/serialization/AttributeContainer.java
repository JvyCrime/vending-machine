package org.ksoap2.serialization;

import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class AttributeContainer {
    private Vector attributes = new Vector();

    public void getAttributeInfo(int i, AttributeInfo attributeInfo) {
        AttributeInfo attributeInfo2 = (AttributeInfo) this.attributes.elementAt(i);
        attributeInfo.name = attributeInfo2.name;
        attributeInfo.namespace = attributeInfo2.namespace;
        attributeInfo.flags = attributeInfo2.flags;
        attributeInfo.type = attributeInfo2.type;
        attributeInfo.elementType = attributeInfo2.elementType;
        attributeInfo.value = attributeInfo2.getValue();
    }

    public Object getAttribute(int i) {
        return ((AttributeInfo) this.attributes.elementAt(i)).getValue();
    }

    public String getAttributeAsString(int i) {
        return ((AttributeInfo) this.attributes.elementAt(i)).getValue().toString();
    }

    public Object getAttribute(String str) {
        Integer numAttributeIndex = attributeIndex(str);
        if (numAttributeIndex != null) {
            return getAttribute(numAttributeIndex.intValue());
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("illegal property: ");
        stringBuffer.append(str);
        throw new RuntimeException(stringBuffer.toString());
    }

    public String getAttributeAsString(String str) {
        Integer numAttributeIndex = attributeIndex(str);
        if (numAttributeIndex != null) {
            return getAttribute(numAttributeIndex.intValue()).toString();
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("illegal property: ");
        stringBuffer.append(str);
        throw new RuntimeException(stringBuffer.toString());
    }

    public boolean hasAttribute(String str) {
        return attributeIndex(str) != null;
    }

    public Object getAttributeSafely(String str) {
        Integer numAttributeIndex = attributeIndex(str);
        if (numAttributeIndex != null) {
            return getAttribute(numAttributeIndex.intValue());
        }
        return null;
    }

    public Object getAttributeSafelyAsString(String str) {
        Integer numAttributeIndex = attributeIndex(str);
        return numAttributeIndex != null ? getAttribute(numAttributeIndex.intValue()).toString() : "";
    }

    public Object safeGetAttribute(String str) {
        return getAttributeSafely(str);
    }

    private Integer attributeIndex(String str) {
        for (int i = 0; i < this.attributes.size(); i++) {
            if (str.equals(((AttributeInfo) this.attributes.elementAt(i)).getName())) {
                return new Integer(i);
            }
        }
        return null;
    }

    public int getAttributeCount() {
        return this.attributes.size();
    }

    protected boolean attributesAreEqual(AttributeContainer attributeContainer) {
        int attributeCount = getAttributeCount();
        if (attributeCount != attributeContainer.getAttributeCount()) {
            return false;
        }
        for (int i = 0; i < attributeCount; i++) {
            AttributeInfo attributeInfo = (AttributeInfo) this.attributes.elementAt(i);
            Object value = attributeInfo.getValue();
            if (!attributeContainer.hasAttribute(attributeInfo.getName()) || !value.equals(attributeContainer.safeGetAttribute(attributeInfo.getName()))) {
                return false;
            }
        }
        return true;
    }

    public void addAttribute(String str, Object obj) {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.name = str;
        attributeInfo.type = obj == null ? PropertyInfo.OBJECT_CLASS : obj.getClass();
        attributeInfo.value = obj;
        addAttribute(attributeInfo);
    }

    public void addAttributeIfValue(String str, Object obj) {
        if (obj != null) {
            addAttribute(str, obj);
        }
    }

    public void addAttribute(AttributeInfo attributeInfo) {
        this.attributes.addElement(attributeInfo);
    }

    public void addAttributeIfValue(AttributeInfo attributeInfo) {
        if (attributeInfo.value != null) {
            this.attributes.addElement(attributeInfo);
        }
    }
}
