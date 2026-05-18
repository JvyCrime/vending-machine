package org.kobjects.pim;

import java.util.Enumeration;
import java.util.Hashtable;

/* JADX INFO: loaded from: classes2.dex */
public class PimField {
    String name;
    Hashtable properties;
    Object value;

    public PimField(PimField pimField) {
        this(pimField.name);
        Object obj = pimField.value;
        if (obj instanceof String[]) {
            int length = ((String[]) obj).length;
            String[] strArr = new String[length];
            System.arraycopy((String[]) obj, 0, strArr, 0, length);
            this.value = strArr;
        } else {
            this.value = obj;
        }
        if (pimField.properties != null) {
            this.properties = new Hashtable();
            Enumeration enumerationKeys = pimField.properties.keys();
            while (enumerationKeys.hasMoreElements()) {
                String str = (String) enumerationKeys.nextElement();
                this.properties.put(str, pimField.properties.get(str));
            }
        }
    }

    public PimField(String str) {
        this.name = str;
    }

    public Enumeration propertyNames() {
        return this.properties.keys();
    }

    public void setProperty(String str, String str2) {
        if (this.properties == null) {
            if (str2 == null) {
                return;
            } else {
                this.properties = new Hashtable();
            }
        }
        if (str2 == null) {
            this.properties.remove(str);
        } else {
            this.properties.put(str, str2);
        }
    }

    public void setValue(Object obj) {
        this.value = obj;
    }

    public Object getValue() {
        return this.value;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        if (this.properties != null) {
            str = ";" + this.properties;
        } else {
            str = "";
        }
        sb.append(str);
        sb.append(":");
        sb.append(this.value);
        return sb.toString();
    }

    public String getProperty(String str) {
        Hashtable hashtable = this.properties;
        if (hashtable == null) {
            return null;
        }
        return (String) hashtable.get(str);
    }

    public boolean getAttribute(String str) {
        String property = getProperty("type");
        return (property == null || property.indexOf(str) == -1) ? false : true;
    }

    public void setAttribute(String str, boolean z) {
        if (getAttribute(str) == z) {
            return;
        }
        String property = getProperty("type");
        if (z) {
            if (property != null && property.length() != 0) {
                str = property + str;
            }
        } else {
            int iIndexOf = property.indexOf(str);
            if (iIndexOf > 0) {
                iIndexOf--;
            }
            if (iIndexOf != -1) {
                str = property.substring(0, iIndexOf) + property.substring(iIndexOf + str.length() + 1);
            } else {
                str = property;
            }
        }
        setProperty("type", str);
    }
}
